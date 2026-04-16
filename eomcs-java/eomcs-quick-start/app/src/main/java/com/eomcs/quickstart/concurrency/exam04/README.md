# Exam04 - Lock

## 개념

`synchronized`는 간결하지만 락 획득 실패 시 무한 대기, 대기 중 인터럽트 불가 등의 한계가 있다.
`java.util.concurrent.locks` 패키지는 더 유연하고 세밀한 락 제어를 제공한다.

### java.util.concurrent.locks 주요 구성요소

| 인터페이스 / 클래스 | 설명 |
|---|---|
| `Lock` | 락의 기본 인터페이스. `lock()`, `unlock()`, `tryLock()` 등을 정의 |
| `ReentrantLock` | `Lock` 구현체. `synchronized`와 동일한 상호 배제 보장 + 추가 기능 |
| `ReadWriteLock` | 읽기 락과 쓰기 락을 분리하는 인터페이스 |
| `ReentrantReadWriteLock` | `ReadWriteLock` 구현체. 다중 읽기 허용, 배타적 쓰기 |
| `Condition` | `Lock`에 연결된 조건 변수. `Object.wait()/notify()` 대체 |

### synchronized vs ReentrantLock

| 기능 | `synchronized` | `ReentrantLock` |
|---|---|---|
| 기본 상호 배제 | O | O |
| 락 해제 방식 | 자동 (블록 종료 시) | 수동 — `unlock()` 명시 필수 |
| 비차단 획득 | X | `tryLock()` |
| 타임아웃 획득 | X | `tryLock(time, unit)` |
| 인터럽트 가능 대기 | X | `lockInterruptibly()` |
| 공정 락 | X | `new ReentrantLock(true)` |
| 조건 변수 | 하나 (`wait/notify`) | 여러 개 (`newCondition()`) |

### ReentrantLock 기본 패턴

```java
Lock lock = new ReentrantLock();

lock.lock();           // try 블록 바깥에서 호출
try {
  // 임계 구역
} finally {
  lock.unlock();       // 예외 발생 시에도 반드시 해제
}
```

- `lock()`을 `try` 바깥에서 호출해야 한다. `try` 안에서 호출하면 `lock()` 자체가 예외를 던질 때 `finally`에서 잠기지 않은 락을 해제하는 오류가 발생한다.
- `finally`에서 `unlock()`을 보장하지 않으면 예외 발생 시 락이 영구히 잠겨 데드락이 된다.

### ReadWriteLock 상태표

| 현재 락 상태 | 읽기 락 추가 획득 | 쓰기 락 추가 획득 |
|---|---|---|
| 아무 락 없음 | O | O |
| 읽기 락 보유 중 | O (동시 허용) | X (쓰기는 대기) |
| 쓰기 락 보유 중 | X (읽기도 대기) | X (쓰기도 대기) |

### Condition 주요 메서드

| 메서드 | 설명 | `Object` 대응 |
|---|---|---|
| `await()` | 락을 해제하고 대기. 깨어나면 락 재획득 후 진행 | `wait()` |
| `signal()` | 대기 중인 스레드 하나를 깨운다 | `notify()` |
| `signalAll()` | 대기 중인 모든 스레드를 깨운다 | `notifyAll()` |

`await()`는 반드시 `while` 루프 안에서 호출한다. 조건이 충족되지 않은 채 깨어나는 spurious wakeup을 방어하기 위해서다.

```java
while (!조건) {
  condition.await(); // 조건 충족까지 반복 확인
}
```

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `ReentrantLock` 기본 — Bank 예제, `try-finally` 패턴 |
| `App2` | `ReentrantLock` 고급 — `tryLock()`, `tryLock(timeout)`, `lockInterruptibly()` |
| `App3` | `ReentrantReadWriteLock` — 다중 읽기 동시 허용, 배타적 쓰기, 성능 비교 |
| `App4` | `Condition` — `await()`/`signal()`, 생산자-소비자 BoundedBuffer |

## App - ReentrantLock 기본

```java
static class Bank {
  private int balance;
  private final Lock lock = new ReentrantLock();

  public void deposit(int amount) {
    lock.lock();                        // 락 획득 (점유 중이면 대기)
    try {
      int current = balance;
      Thread.yield();                   // 락 보유 중 → 다른 스레드가 임계 구역 진입 불가
      balance = current + amount;
    } finally {
      lock.unlock();                    // 예외 발생 시에도 반드시 해제
    }
  }

  public void withdraw(int amount) {
    lock.lock();
    try {
      if (balance >= amount) {
        Thread.yield();
        balance -= amount;
      }
    } finally {
      lock.unlock();
    }
  }
}

// 해결 1: 갱신 분실 해결 → 최종 잔액 항상 10,000원 ✓
// 해결 2: Check-Then-Act 해결 → 잔액 음수 발생 0회 ✓
// 해결 3: 입금 + 출금 동시 진행 → 잔액 일관성 보장 ✓
```

- `ReentrantLock`은 `synchronized`와 동일한 상호 배제를 보장한다. 두 방식으로 만든 Bank 클래스의 동작 결과는 동일하다.
- `lock()`은 반드시 `try` 블록 바깥에서 호출하고, `unlock()`은 `finally`에서 호출한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam04.App
  ```

## App2 - ReentrantLock 고급 기능

```java
Lock lock = new ReentrantLock();

// [1] tryLock() - 비차단 획득
boolean acquired = lock.tryLock(); // 락 점유 중이면 즉시 false 반환
if (acquired) {
  try { /* 임계 구역 */ } finally { lock.unlock(); }
} else {
  // 대기 없이 다른 작업 수행 가능
}

// [2] tryLock(timeout, unit) - 타임아웃 있는 획득
boolean got = lock.tryLock(2, TimeUnit.SECONDS); // 최대 2초 대기
if (got) {
  try { /* 임계 구역 */ } finally { lock.unlock(); }
} else {
  // 2초 내 획득 실패 → 포기
}

// [3] lockInterruptibly() - 인터럽트 가능한 대기
// synchronized: 락 대기 중 인터럽트를 무시하고 계속 대기
// lockInterruptibly(): 대기 중 인터럽트 시 InterruptedException 발생 → 대기 취소 가능
try {
  lock.lockInterruptibly();
  try { /* 임계 구역 */ } finally { lock.unlock(); }
} catch (InterruptedException e) {
  // 락 대기 중 취소됨
}
```

- `tryLock()`은 락이 비어 있으면 `true`, 점유 중이면 즉시 `false`를 반환한다. 대기 없이 "빠른 경로"와 "대체 경로"를 분기할 때 유용하다.
- `tryLock(timeout, unit)`은 지정 시간 동안 락 획득을 시도한다. 데드락 방어나 SLA가 있는 작업에 사용한다.
- `lockInterruptibly()`는 락 대기 중 `interrupt()` 수신 시 `InterruptedException`을 던진다. `synchronized`는 인터럽트를 무시하므로 작업 취소가 불가능하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam04.App2
  ```

## App3 - ReentrantReadWriteLock

```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock  = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

// 읽기 - 여러 스레드가 동시에 readLock 획득 가능
public String get(String key) {
  readLock.lock();
  try {
    Thread.sleep(300); // 읽기 작업
    return store.get(key);
  } finally {
    readLock.unlock();
  }
}

// 쓰기 - writeLock은 배타적 (모든 읽기·쓰기 스레드 대기)
public void put(String key, String value) {
  writeLock.lock();
  try {
    Thread.sleep(200); // 쓰기 작업
    store.put(key, value);
  } finally {
    writeLock.unlock();
  }
}

// 성능 비교 (읽기 작업 300ms, 스레드 5개):
//   ReadWriteLock: 5개 동시 실행 → 총 시간 ≈  300ms
//   ReentrantLock: 5개 순차 실행 → 총 시간 ≈ 1,500ms
```

- 읽기 락끼리는 동시에 획득할 수 있다. 쓰기 락이 없는 한 얼마든지 많은 스레드가 동시에 읽을 수 있다.
- 쓰기 락은 읽기 락과 쓰기 락 모두를 배제한다. 쓰기 스레드는 완전히 혼자 실행된다.
- 읽기가 잦고 쓰기가 드문 공유 캐시, 설정, 사전 등에 사용하면 처리량을 크게 높일 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam04.App3
  ```

## App4 - Condition

```java
Lock lock = new ReentrantLock();
Condition notFull  = lock.newCondition(); // 생산자 대기 큐
Condition notEmpty = lock.newCondition(); // 소비자 대기 큐

// 생산자: 버퍼가 가득 차면 대기
public void put(int item) throws InterruptedException {
  lock.lock();
  try {
    while (count == buffer.length) { // if 대신 while: spurious wakeup 방어
      notFull.await();               // 락 해제 + 대기 → 소비자가 signal할 때까지
    }
    buffer[putIndex] = item;
    putIndex = (putIndex + 1) % buffer.length;
    count++;
    notEmpty.signal();               // 소비자 대기 큐에서 하나만 깨우기
  } finally {
    lock.unlock();
  }
}

// 소비자: 버퍼가 비어 있으면 대기
public int take() throws InterruptedException {
  lock.lock();
  try {
    while (count == 0) {
      notEmpty.await();              // 락 해제 + 대기 → 생산자가 signal할 때까지
    }
    int item = buffer[takeIndex];
    takeIndex = (takeIndex + 1) % buffer.length;
    count--;
    notFull.signal();               // 생산자 대기 큐에서 하나만 깨우기
    return item;
  } finally {
    lock.unlock();
  }
}
```

- `await()`는 락을 자동으로 해제하고 대기한다. 깨어나면 락을 재획득한 뒤 진행한다. `synchronized` 블록의 `Object.wait()`와 동일한 역할이다.
- Condition을 `notFull`과 `notEmpty`로 분리하면 생산자와 소비자를 각각의 대기 큐에서 관리할 수 있다. `Object.notify()`를 사용할 때는 두 그룹이 같은 큐에 섞여 불필요한 스레드가 깨어날 수 있다.
- `while` 루프 안에서 `await()`를 호출하는 것은 관례이자 필수다. OS가 조건과 무관하게 스레드를 깨우는 spurious wakeup이 발생할 수 있기 때문이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam04.App4
  ```
