# Concurrency - 병행 프로그래밍

## 학습 목표

- 스레드의 생명주기와 상태 전환을 이해하고, 스레드를 생성·제어할 수 있다.
- 경쟁 조건(Race Condition)이 발생하는 원인을 이해하고, `synchronized`로 임계 구역을 보호할 수 있다.
- `volatile`의 가시성(Visibility) 보장 원리를 이해하고, 적절한 상황에 활용할 수 있다.
- `ReentrantLock`, `ReadWriteLock`을 사용하여 `synchronized`보다 유연한 동기화를 구현할 수 있다.
- `Semaphore`로 동시 접근 스레드 수를 제한하고, 신호(Signal) 패턴으로 스레드 실행 순서를 제어할 수 있다.
- `ExecutorService`와 스레드 풀을 사용하여 작업을 효율적으로 병렬 처리할 수 있다.
- `ForkJoinPool`과 분할 정복(Divide and Conquer) 방식으로 대용량 데이터를 병렬 처리할 수 있다.
- `CompletableFuture`로 비동기 작업을 체인으로 연결하고 결과를 조합할 수 있다.
- Spring WebFlux의 리액티브 파이프라인에서 예외를 처리하고 복구할 수 있다.
- 가상 스레드(Virtual Thread)의 동작 원리를 이해하고, I/O 집약적 작업에 적용할 수 있다.

---

## Exam01 - Thread 생성과 제어

### 개념

스레드(Thread)는 프로세스 내에서 독립적으로 실행되는 실행 흐름이다.
Java의 `Thread` 클래스는 `java.lang` 패키지에 소속되어 있어 `import` 없이 사용할 수 있다.
JVM이 시작하면 main 스레드가 자동으로 생성되며, 추가 스레드를 직접 생성하여 병렬 실행을 구현할 수 있다.

#### Thread 생성 방법

| 방법 | 특징 | 사용 방식 |
|---|---|---|
| `Thread` 클래스 상속 | `run()` 재정의. 다른 클래스 상속 중이면 사용 불가 | `class MyThread extends Thread` |
| `Runnable` 인터페이스 구현 | 상속 제한 없음. Thread와 실행 로직 분리 | `new Thread(new MyRunnable())` |
| 람다로 `Runnable` 구현 | Java 8+. 가장 간결한 표현 | `new Thread(() -> { ... })` |

#### Thread 주요 메서드

| 메서드 | 설명 |
|---|---|
| `start()` | 새 스레드를 생성하고 `run()`을 실행한다. `run()`을 직접 호출하면 새 스레드가 생성되지 않는다 |
| `run()` | 스레드가 실행할 코드. `start()`에 의해 새 스레드에서 호출된다 |
| `join()` | 호출한 스레드가 종료될 때까지 현재 스레드를 대기시킨다 |
| `join(ms)` | 최대 ms 밀리초 동안만 대기한다 |
| `Thread.sleep(ms)` | 현재 스레드를 지정 시간(밀리초) 동안 일시 정지한다 |
| `interrupt()` | 대상 스레드에 인터럽트 신호를 보낸다 |
| `isInterrupted()` | 인터럽트 플래그를 반환한다 (플래그 유지) |
| `Thread.interrupted()` | 인터럽트 플래그를 반환하고 `false`로 초기화한다 |
| `isAlive()` | 스레드가 실행 중이면 `true`를 반환한다 |
| `getName()` / `setName(s)` | 스레드 이름 반환 / 설정 |
| `threadId()` | 스레드 고유 ID 반환 |
| `getPriority()` / `setPriority(n)` | 우선순위 반환 / 설정 (1~10, 기본값 5) |
| `Thread.currentThread()` | 현재 실행 중인 스레드 객체 반환 |

#### Thread 상태 (Thread.State)

| 상태 | 설명 | 전환 조건 |
|---|---|---|
| `NEW` | 객체 생성 후 `start()` 호출 전 | — |
| `RUNNABLE` | 실행 중이거나 CPU 할당 대기 중 | `start()` 호출 |
| `BLOCKED` | `synchronized` 락 획득 대기 중 | 다른 스레드가 락 보유 중 |
| `WAITING` | 무한정 대기 중 | `join()`, `Object.wait()` 호출 |
| `TIMED_WAITING` | 지정 시간 동안 대기 중 | `sleep(ms)`, `join(ms)`, `wait(ms)` 호출 |
| `TERMINATED` | `run()` 완료로 종료됨 | `run()` 정상/예외 종료 |

```
NEW ──start()──> RUNNABLE ──run() 완료──> TERMINATED
                   │  ↑
                   │  │ 락 획득 / 시간 만료 / notify()
                   ↓  │
                BLOCKED / WAITING / TIMED_WAITING
```

#### interrupt() 동작 방식

| 대상 스레드 상태 | `interrupt()` 효과 |
|---|---|
| `sleep()` / `join()` / `wait()` 대기 중 | `InterruptedException` 발생 (인터럽트 플래그 초기화됨) |
| 루프 등 일반 실행 중 | 인터럽트 플래그만 `true`로 설정. `isInterrupted()`로 확인 필요 |

#### 데몬 스레드 vs 일반 스레드

| 구분 | 일반 스레드 | 데몬 스레드 |
|---|---|---|
| 설정 | 기본값 | `setDaemon(true)` — `start()` 전에 호출 |
| JVM 종료 조건 | 모든 일반 스레드가 종료되면 JVM 종료 | 일반 스레드가 모두 종료되면 강제 종료됨 |
| 용도 | 일반 작업 | 백그라운드 보조 작업 (GC, 모니터링 등) |

### App - Thread 생성 방법

```java
// 1. Thread 클래스 상속
static class MyThread extends Thread {
  public MyThread(String name) {
    super(name); // 스레드 이름 설정
  }

  @Override
  public void run() {
    for (int i = 1; i <= 3; i++) {
      System.out.println(getName() + " 실행 중: " + i);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        return; // 인터럽트 시 종료
      }
    }
  }
}

MyThread t1 = new MyThread("Thread-A");
t1.start(); // 새 스레드 시작. run()을 직접 호출하면 안 된다.
t1.join();  // t1이 종료될 때까지 main 대기

// 2. Runnable 인터페이스 구현
Thread t2 = new Thread(new MyRunnable("Thread-B"));
t2.start();
t2.join();

// 3. 람다로 Runnable 구현 (Java 8+)
Thread t3 = new Thread(() -> {
  for (int i = 1; i <= 3; i++) {
    System.out.println(Thread.currentThread().getName() + " 실행 중: " + i);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      return;
    }
  }
}, "Thread-C");
t3.start();
t3.join();

// 4. 여러 스레드 동시 실행
Thread[] threads = { new MyThread("T1"), new MyThread("T2"), new MyThread("T3") };
for (Thread t : threads) t.start(); // 세 스레드가 동시에 실행됨 - 출력 순서가 섞일 수 있다
for (Thread t : threads) t.join();  // 모든 스레드 종료 대기

// 5. 스레드 기본 정보
Thread main = Thread.currentThread();
main.getName();     // "main"
main.threadId();    // 스레드 고유 ID
main.getPriority(); // 5 (NORM_PRIORITY)

new Thread(() -> {}).getName();          // "Thread-N" 형식 자동 부여
new Thread(() -> {}, "my-worker").getName(); // "my-worker"
```

- `start()`를 호출하면 JVM이 새 OS 스레드를 생성하고, 그 스레드에서 `run()`을 실행한다. `run()`을 직접 호출하면 새 스레드가 생성되지 않고 현재 스레드에서 실행된다.
- `Thread` 클래스를 상속하면 단일 상속 제한으로 다른 클래스를 상속할 수 없다. `Runnable`을 구현하면 이 제한이 없고, 실행 로직을 Thread와 분리할 수 있어 더 선호된다.
- 여러 스레드를 동시에 시작하면 출력 순서는 OS 스케줄러에 따라 실행마다 달라질 수 있다.
- 이름을 지정하지 않으면 `Thread-0`, `Thread-1`처럼 자동으로 부여된다. 디버깅 시 이름을 지정하면 스레드를 식별하기 쉽다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam01.App
  ```

### App2 - Thread 제어 메서드

```java
// 1. sleep() - 일시 정지
Thread.sleep(300); // 현재 스레드를 300ms 동안 TIMED_WAITING 상태로 전환

// 2. join() - 종료 대기
Thread worker = new Thread(() -> {
  Thread.sleep(200);
  System.out.println("worker: 종료");
}, "Worker");
worker.start();
worker.join(); // worker가 종료될 때까지 main 대기
System.out.println("main: worker 종료 확인, 계속 진행");

// 3. join(ms) - 제한 시간 대기
Thread longTask = new Thread(() -> Thread.sleep(1000), "LongTask");
longTask.start();
longTask.join(200); // 최대 200ms만 대기
if (longTask.isAlive()) {
  System.out.println("200ms 초과 - longTask가 아직 실행 중");
}
longTask.interrupt(); // 정리

// 4. interrupt() - sleep 중 인터럽트
Thread target = new Thread(() -> {
  try {
    Thread.sleep(5000); // 대기 중 인터럽트 수신
  } catch (InterruptedException e) {
    System.out.println("sleep 중 인터럽트 수신, 작업 취소");
  }
}, "InterruptTarget");
target.start();
Thread.sleep(100);
target.interrupt(); // InterruptedException 발생시킴
target.join();

// 5. isInterrupted() - 루프 중 인터럽트 감지
Thread flagCheck = new Thread(() -> {
  int count = 0;
  while (!Thread.currentThread().isInterrupted()) { // 플래그 확인
    count++;
  }
  System.out.println("인터럽트 플래그 감지, 종료. count=" + count);
}, "FlagCheck");
flagCheck.start();
Thread.sleep(50);
flagCheck.interrupt(); // 플래그 설정 (sleep 없으므로 예외 없음)
flagCheck.join();

// 6. 스레드 우선순위 (1~10, 기본 5)
Thread low  = new Thread(() -> {}, "Low");
Thread high = new Thread(() -> {}, "High");
low.setPriority(Thread.MIN_PRIORITY);  // 1
high.setPriority(Thread.MAX_PRIORITY); // 10
// 우선순위가 높다고 실행 순서가 보장되지는 않는다
```

- `sleep()`은 `static` 메서드로 **현재 스레드**를 정지한다. 다른 스레드 객체 참조로 호출해도 현재 스레드가 정지된다.
- `join()`으로 여러 스레드를 순서대로 기다릴 수 있다. `join(ms)`를 사용하면 타임아웃 후 `isAlive()`로 종료 여부를 확인할 수 있다.
- `interrupt()` 효과는 두 가지로 나뉜다. 대상이 `sleep`/`join`/`wait` 중이면 `InterruptedException`을 발생시키고, 일반 실행 중이면 인터럽트 플래그만 `true`로 설정한다. 루프에서는 `isInterrupted()`로 플래그를 직접 확인해야 한다.
- `InterruptedException`이 발생하면 인터럽트 플래그가 자동으로 `false`로 초기화된다. 예외를 catch 후 계속 실행해야 하는 경우 `Thread.currentThread().interrupt()`로 플래그를 복원해야 한다.
- 스레드 우선순위는 OS 스케줄러에 대한 힌트일 뿐, 실행 순서를 보장하지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam01.App2
  ```

### App3 - Thread 상태

```java
// 1. NEW 상태 - start() 전
Thread t = new Thread(() -> {}, "StateCheck");
t.getState(); // NEW

// 2. TERMINATED 상태 - run() 완료
t.start();
t.join();
t.getState(); // TERMINATED

// 3. TIMED_WAITING 상태 - sleep 중
Thread sleeper = new Thread(() -> Thread.sleep(500), "Sleeper");
sleeper.start();
Thread.sleep(50); // main: sleeper가 sleep에 진입할 시간
sleeper.getState(); // TIMED_WAITING

// 4. WAITING 상태 - join 중
Thread longRunner = new Thread(() -> Thread.sleep(500), "LongRunner");
Thread waiter = new Thread(() -> longRunner.join(), "Waiter"); // join → WAITING
longRunner.start();
waiter.start();
Thread.sleep(50);
waiter.getState(); // WAITING

// 5. BLOCKED 상태 - synchronized 락 대기
Object lock = new Object();
Thread lockHolder = new Thread(() -> {
  synchronized (lock) { Thread.sleep(500); } // 락 점유
}, "LockHolder");
Thread lockWaiter = new Thread(() -> {
  synchronized (lock) { } // 락 대기 → BLOCKED
}, "LockWaiter");
lockHolder.start();
Thread.sleep(50);  // lockHolder가 락을 잡을 시간
lockWaiter.start();
Thread.sleep(50);  // lockWaiter가 대기 상태에 진입할 시간
lockWaiter.getState(); // BLOCKED

// 6. isAlive() - 생존 여부
Thread alive = new Thread(() -> Thread.sleep(300), "Alive");
alive.isAlive(); // false (start() 전)
alive.start();
Thread.sleep(50);
alive.isAlive(); // true  (실행 중)
alive.join();
alive.isAlive(); // false (종료 후)
```

- `getState()`는 `Thread.State` 열거형 값을 반환한다. 주로 디버깅·모니터링 목적으로 사용한다.
- `RUNNABLE` 상태는 실제로 CPU를 사용하는 "실행 중"과 CPU 할당을 기다리는 "실행 대기" 두 경우를 모두 포함한다.
- `BLOCKED`는 `synchronized` 키워드로 보호된 영역의 락을 기다릴 때만 발생한다. `java.util.concurrent.locks.Lock`을 사용할 때는 `WAITING` 상태가 된다.
- `WAITING`과 `TIMED_WAITING`의 차이는 대기 시간 제한 여부이다. `WAITING`은 다른 스레드의 `notify()` / `join()` 완료 없이는 깨어나지 않는다.
- `TERMINATED` 상태의 스레드에 `start()`를 다시 호출하면 `IllegalThreadStateException`이 발생한다. 스레드는 재사용할 수 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam01.App3
  ```

---

## Exam02 - 동기화(synchronized)

### 개념

멀티스레드 환경에서 여러 스레드가 공유 자원에 동시에 접근하면 데이터가 손상되거나 일관성이 깨진다.
Java는 `synchronized` 키워드로 임계 구역(Critical Section)을 보호하여 이 문제를 해결한다.

#### 동기화 없을 때 발생하는 문제

| 문제 | 설명 | 발생 조건 |
|---|---|---|
| 갱신 분실 (Lost Update) | 한 스레드의 쓰기 결과가 다른 스레드의 쓰기에 덮어씌워짐 | 읽기 → 계산 → 쓰기 사이에 다른 스레드가 끼어들 때 |
| Check-Then-Act | 조건 확인 후 행동하는 사이에 다른 스레드가 상태를 변경 | 잔액 확인과 출금 사이에 다른 스레드가 먼저 출금할 때 |

**갱신 분실 예시** (balance = 100, 두 스레드가 각각 10을 입금):
```
Thread-A: balance 읽기 → 100
Thread-B: balance 읽기 → 100          ← A의 읽기와 겹침
Thread-A: 100 + 10 = 110, 쓰기 → balance = 110
Thread-B: 100 + 10 = 110, 쓰기 → balance = 110  ← A의 결과 덮어쓰기
최종 balance = 110 (기대값 120, 10이 사라짐)
```

#### synchronized 방식 비교

| 방식 | 문법 | 락 객체 | 특징 |
|---|---|---|---|
| synchronized 메서드 | `public synchronized void f()` | `this` | 메서드 전체가 임계 구역 |
| synchronized 블록 | `synchronized(obj) { ... }` | 지정한 객체 | 블록 내부만 임계 구역. 락 범위 최소화 가능 |

`synchronized` 메서드는 `synchronized(this) { 메서드 전체 }` 와 동일하다.

#### 동기화 동작 원리 (모니터 락)

모든 Java 객체는 내부에 **모니터 락(Monitor Lock)**을 가진다.

```
스레드 A가 synchronized 진입
  → 락 획득(lock) → 임계 구역 실행 → 락 해제(unlock)

스레드 B가 같은 락으로 보호된 구역 진입 시도
  → 락이 잠겨 있음 → BLOCKED 상태에서 대기
  → A가 락 해제 → B 락 획득 → 임계 구역 실행
```

- 락 획득·해제는 JVM이 자동으로 처리한다.
- 예외가 발생해도 락은 반드시 해제된다.
- 한 스레드가 `synchronized` 메서드 실행 중이면 같은 객체의 다른 `synchronized` 메서드에도 다른 스레드가 진입할 수 없다.

#### App / App2 / App3 / App4 비교

| 클래스 | Bank.deposit() | Bank.withdraw() | 핵심 주제 |
|---|---|---|---|
| `App` | 동기화 없음 | 동기화 없음 | 갱신 분실, 잔액 음수, 잔액 불일치 문제 재현 |
| `App2` | `synchronized` 메서드 | `synchronized` 메서드 | 메서드 전체를 임계 구역으로 보호 |
| `App3` | `synchronized` 블록 (`lock` 객체) | `synchronized` 블록 (`lock` 객체) | 블록으로 임계 구역 선택적 보호 |
| `App4` | — | — | 락 범위 최소화 — 공유 자원 접근만 보호 |

### App - 동기화 없는 Bank (문제 재현)

```java
static class Bank {
  private int balance;

  // 입금 - 동기화 없음: 읽기-쓰기 사이에 다른 스레드가 끼어들 수 있음
  public void deposit(int amount) {
    int current = balance;      // (1) 잔액 읽기
    Thread.yield();             // 스레드 전환 유도 → 경쟁 조건 재현
    balance = current + amount; // (2) 다른 스레드의 쓰기를 덮어쓸 수 있음
  }

  // 출금 - 동기화 없음: 확인-출금 사이에 다른 스레드가 끼어들 수 있음
  public void withdraw(int amount) {
    if (balance >= amount) { // (1) 잔액 확인 (check)
      Thread.yield();        // 스레드 전환 유도
      balance -= amount;     // (2) 이미 잔액이 부족해졌을 수 있음 (act)
    }
  }
}

// 문제 1: 갱신 분실 - 10개 스레드 × 1,000회 입금(1원) → 기대 10,000원
// 실제: 1,100 ~ 3,000원대 (스레드마다 다름) ← 갱신 분실 발생!

// 문제 2: Check-Then-Act - 3개 스레드가 각 10회 × 1,000원 출금 (잔액 10,000원)
// 두 스레드가 동시에 잔액 확인 후 출금 → 잔액 음수 발생!

// 문제 3: 입금 + 출금 동시 진행 → 잔액 불일치 발생!
```

- `Thread.yield()`는 스레드 전환을 유도하여 경쟁 조건을 쉽게 재현한다. 실제 코드에서는 `yield()` 없이도 스레드 전환이 발생한다.
- `balance += amount`같은 복합 연산은 소스 코드 한 줄이지만 CPU 명령어 수준에서 "읽기 → 더하기 → 쓰기" 세 단계로 나뉜다. 이 사이에 스레드가 전환되면 갱신 분실이 발생한다.
- `if (balance >= amount) { balance -= amount; }`는 조건 확인과 출금이 원자적이지 않아 두 스레드가 동시에 조건을 통과하면 잔액이 음수가 된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam02.App
  ```

### App2 - synchronized 메서드로 해결

```java
static class Bank {
  private int balance;

  // 입금 - synchronized 메서드: 한 번에 한 스레드만 진입 가능
  public synchronized void deposit(int amount) {
    int current = balance;
    Thread.yield();             // 락 보유 중 → 다른 스레드가 진입 불가
    balance = current + amount; // 원자적으로 처리됨
  }

  // 출금 - synchronized 메서드: 확인-출금이 원자적 연산으로 처리
  public synchronized void withdraw(int amount) {
    if (balance >= amount) {
      Thread.yield();      // 락 보유 중 → 다른 스레드가 끼어들 수 없음
      balance -= amount;
    }
  }
}

// 해결 1: 갱신 분실 해결 → 최종 잔액 항상 10,000원 ✓
// 해결 2: Check-Then-Act 해결 → 잔액 음수 발생 0회 ✓
// 해결 3: 입금 + 출금 동시 진행 → 잔액 일관성 보장 ✓

// [참고] synchronized 블록으로 같은 효과
Object lock = new Object();
int[] sharedValue = {0};

new Thread(() -> {
  for (int i = 0; i < 1000; i++) {
    synchronized (lock) { // lock 객체를 모니터로 사용
      sharedValue[0]++;
    }
  }
}).start();
// 두 스레드 각 1,000회 증가 → 기대 2,000 ✓
```

- `synchronized` 메서드는 `this` 객체를 모니터 락으로 사용한다. `deposit()`이 실행 중인 동안 같은 `Bank` 객체의 `withdraw()`에도 다른 스레드가 진입할 수 없다.
- `Thread.yield()`가 그대로 있어도 다른 스레드가 **락을 획득할 수 없으므로** 경쟁 조건이 발생하지 않는다.
- `synchronized` 메서드는 오버헤드가 있으므로 락을 보유하는 시간을 최소화하는 것이 성능에 유리하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam02.App2
  ```

### App3 - synchronized 블록으로 해결

```java
static class Bank {
  private int balance;
  private final Object lock = new Object(); // 전용 락 객체

  // 입금 - synchronized 블록: 임계 구역만 선택적으로 보호
  public void deposit(int amount) {
    // 블록 바깥: 락 없이 실행 가능 (유효성 검사, 로깅 등)
    synchronized (lock) {         // lock 객체를 모니터로 사용
      int current = balance;      // ← 임계 구역 시작
      Thread.yield();
      balance = current + amount; // ← 임계 구역 끝
    }                             // 블록 종료 시 락 자동 해제
  }

  // 출금 - synchronized 블록: deposit()과 동일한 lock → 상호 배제 보장
  public void withdraw(int amount) {
    synchronized (lock) {
      if (balance >= amount) {
        Thread.yield();
        balance -= amount;
      }
    }
  }
}

// 해결 1: 갱신 분실 해결 → 최종 잔액 항상 10,000원 ✓
// 해결 2: Check-Then-Act 해결 → 잔액 음수 발생 0회 ✓
// 해결 3: 입금 + 출금 동시 진행 → 잔액 일관성 보장 ✓
```

- `synchronized` 블록에서 락 객체로 `this` 대신 전용 `Object lock`을 사용하면 외부에서 같은 객체를 락으로 사용하는 코드와 충돌을 피할 수 있다.
- `deposit()`과 `withdraw()`가 **같은 `lock` 객체**를 사용하므로 두 메서드는 서로 상호 배제된다. 다른 `lock` 객체를 사용하면 상호 배제가 되지 않는다.
- 락 범위를 최소화하면 대기 시간이 줄어 처리량(throughput)이 높아진다. 락을 보유한 채로 I/O, 네트워크 호출, 장시간 계산을 하지 않도록 주의한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam02.App3
  ```

### App4 - synchronized 블록 락 범위 최소화

```java
Object lock = new Object();
int[] count = {0};

new Thread(() -> {
  for (int i = 0; i < 1000; i++) {
    int value = i * 2;         // 락 바깥: 준비 작업 (동시 실행 허용)
    synchronized (lock) {
      count[0] += value;       // 공유 자원 접근만 보호
    }                          // 락 해제 → 다른 스레드 즉시 진입 가능
  }
}, "T1").start();

new Thread(() -> {
  for (int i = 0; i < 1000; i++) {
    int value = i * 2;
    synchronized (lock) {
      count[0] += value;
    }
  }
}, "T2").start();

// 두 스레드 각각 0+2+4+...+1998 = 999,000 기여
// 합산 결과: 1,998,000 ✓
```

- 공유 자원(`count`)에 접근하는 구간만 `synchronized` 블록으로 감싸고, 락이 필요 없는 계산(`value = i * 2`)은 블록 바깥에서 실행한다.
- 락 바깥의 코드는 두 스레드가 동시에 실행할 수 있어 대기 시간이 줄어든다.
- 락을 보유한 채로 I/O, 네트워크 호출, 장시간 계산을 하지 않도록 주의한다.

| 위치 | 실행 방식 | 이유 |
|---|---|---|
| `int value = i * 2` (락 바깥) | 병렬 (동시 실행) | 지역 변수만 사용 — 공유 자원 없음 |
| `count[0] += value` (락 안) | 직렬 (한 번에 한 스레드) | 공유 배열 `count` 접근 |

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam02.App4
  ```

---

## Exam03 - volatile

### 개념

멀티스레드 환경에서 `synchronized` 없이 공유 변수를 읽고 쓰면 **가시성(Visibility) 문제**가 발생할 수 있다.
Java는 `volatile` 키워드로 이 문제를 해결하지만, 원자성(Atomicity)은 보장하지 않는다.

#### 가시성(Visibility) 문제

현대 CPU와 JIT 컴파일러는 성능을 위해 변수 접근을 두 가지 방식으로 최적화한다.

| 레벨 | 원인 | 설명 |
|---|---|---|
| JIT 컴파일러 | **레지스터 최적화 (register hoisting)** | tight loop에서 값이 바뀌지 않는다고 판단하면 변수를 CPU 레지스터에 한 번만 로드하고 이후 메모리 접근을 생략한다 |
| CPU 하드웨어 | **코어별 캐시(L1/L2) 불일치** | 멀티코어에서 각 코어가 독립 캐시를 가지므로, Thread A의 쓰기가 Thread B의 캐시에 즉시 반영되지 않을 수 있다 |

**이 예제(tight loop)에서 무한루프의 직접적인 원인은 JIT 레지스터 최적화다.**

```
JIT 최적화 전:               JIT 최적화 후 (의사 코드):
while (running) {             boolean r = running; // 한 번만 읽어 레지스터에 고정
    count++;                  while (r) {           // 이후 메모리 접근 생략
}                                 count++;
                              }
```

JIT 최적화 이후 main이 `running = false`를 써도 Worker는 레지스터의 `true`만 보므로 루프가 끝나지 않는다.
CPU 캐시 문제는 멀티코어 환경에서 더 일반적인 가시성 문제의 하드웨어 원인이다.

```
Core 1 (Main 스레드)           Core 2 (Worker 스레드)
  running = false 쓰기
  → Core 1의 L1 캐시에 반영      → Core 2의 L1 캐시: running=true (stale)
  → 메인 메모리에 반영            → 자기 캐시에서만 읽음 → 변경 인식 불가
```

#### volatile 키워드

`volatile`은 변수 접근을 항상 메인 메모리를 통하도록 강제하여 두 가지 문제를 모두 해결한다.

| 동작 | 설명 |
|---|---|
| JIT 최적화 방지 | 레지스터 hoisting 금지 — 루프마다 메모리에서 재읽기 강제 |
| 쓰기 | 메모리 배리어 삽입 — CPU 캐시를 즉시 메인 메모리에 플러시 |
| 읽기 | 메모리 배리어 삽입 — 다른 코어의 캐시를 무효화하고 메인 메모리에서 최신 값 로드 |
| 재정렬 방지 | volatile 접근을 기준으로 앞뒤 명령어가 재정렬되지 않음 |

#### volatile의 한계 — 원자성(Atomicity) 미보장

`volatile`은 가시성만 보장한다. `count++` 같은 복합 연산(Read-Modify-Write)은 원자적이지 않다.

```
count++의 실제 CPU 명령어 순서:
  (1) 메인 메모리에서 count 읽기   ← volatile이 최신 값 보장
  (2) 값에 1 더하기
  (3) 결과를 메인 메모리에 쓰기    ← volatile이 즉시 반영 보장
  → 하지만 (1)~(3) 전체는 원자적이지 않다

두 스레드가 동시에 실행하면:
  Thread-A: (1) count 읽기 → 100
  Thread-B: (1) count 읽기 → 100    ← A가 (3)을 완료하기 전에 읽음
  Thread-A: (3) 101 쓰기 → count=101
  Thread-B: (3) 101 쓰기 → count=101  ← A의 결과 덮어쓰기 (갱신 분실!)
  기대: 102,  실제: 101
```

#### volatile이 적합한 상황

```java
// O 적합: 한 스레드만 쓰고, 나머지는 읽기만 하는 플래그
volatile boolean stopFlag = false;

// X 부적합: 여러 스레드가 동시에 읽고 쓰는 카운터
volatile int counter = 0;
counter++; // 원자적이지 않다 → 갱신 분실 발생
```

#### volatile vs synchronized vs AtomicInteger

| 방식 | 가시성 | 원자성 | 적합한 상황 |
|---|---|---|---|
| 없음 | X | X | (멀티스레드 공유 자원에 부적합) |
| `volatile` | O | X | 단순 플래그 (한 스레드 쓰기, 여러 스레드 읽기) |
| `synchronized` | O | O | 복합 연산, 여러 변수 동시 보호 |
| `AtomicInteger` | O | O | 단일 숫자 카운터, 락 없어 성능 우수 |

#### App / App2 / App3 비교

| 클래스 | 변수 | 핵심 주제 |
|---|---|---|
| `App` | `static boolean running` | volatile 없음 — JIT 레지스터 최적화로 가시성 문제 재현 |
| `App2` | `static volatile boolean running` | volatile로 가시성 문제 해결 |
| `App3` | `static volatile int counter` | volatile의 한계 — counter++ 원자성 미보장, synchronized·AtomicInteger로 해결 |

### App - volatile 없음: 가시성 문제 재현

```java
static boolean running = true; // volatile 없음 → JIT 캐싱 대상

Thread worker = new Thread(() -> {
  long count = 0;
  while (running) { // JIT 최적화 후 running이 레지스터에 고정될 수 있다
    count++;        // 아무 메서드도 호출하지 않는 tight loop → JIT 최적화 대상
  }
  System.out.println("Worker 종료. count=" + count);
}, "Worker");

worker.start();
Thread.sleep(500); // JIT이 최적화를 수행할 시간을 준다 (warm-up)
running = false;   // main이 변경 → Worker가 인식 못할 수 있다

worker.join(3000); // 최대 3초 대기
if (worker.isAlive()) {
  System.out.println("Worker가 여전히 실행 중! → 가시성 문제 재현 성공");
  worker.interrupt(); // 프로그램 종료를 위해 강제 인터럽트
}
```

- `Thread.sleep(500)`은 JIT warm-up을 위한 것이다. 약 10,000회 반복 후 JIT이 루프를 최적화하여 `running`을 레지스터에 고정한다.
- 이후 `running = false`를 써도 Worker는 레지스터의 `true`만 확인하므로 루프를 탈출하지 못한다.
- 재현 여부는 JVM 구현·버전·OS·하드웨어에 따라 다를 수 있다. `-server` 플래그 또는 충분한 반복 횟수가 있을 때 더 잘 재현된다.
- 프로그램이 무한 루프에 빠지지 않도록 3초 타임아웃 후 `interrupt()`로 강제 종료한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam03.App
  ```

### App2 - volatile: 가시성 문제 해결

```java
static volatile boolean running = true; // volatile → 항상 메인 메모리에서 읽고 씀

Thread worker = new Thread(() -> {
  long count = 0;
  while (running) { // 루프마다 메인 메모리에서 running을 읽는다
    count++;        // JIT이 최적화해도 running은 레지스터에 캐싱하지 않는다
  }
  System.out.println("Worker 종료. count=" + count);
}, "Worker");

worker.start();
Thread.sleep(500);           // App과 동일한 조건
running = false;             // volatile → 메인 메모리에 즉시 반영 → Worker가 즉시 인식

worker.join(3000);
// Worker 정상 종료 ✓
```

- `volatile`은 JIT이 `running`을 레지스터에 캐싱하는 것을 금지한다. 루프마다 메인 메모리에서 최신 값을 읽는다.
- `running = false` 직후 Worker는 다음 루프 조건 확인 시 메모리에서 `false`를 읽어 즉시 탈출한다.
- 단순 boolean 상태 신호(stop/pause 플래그)처럼 **한 스레드만 쓰고 나머지는 읽기만 하는 변수**에 `volatile`이 적합하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam03.App2
  ```

### App3 - volatile의 한계: 원자성 미보장

```java
static volatile int counter = 0; // volatile: 가시성 보장, 원자성은 미보장

// [문제] volatile int에 counter++ → 갱신 분실 발생
// 10개 스레드 × 각 10,000회 counter++ → 기대 100,000
for (int j = 0; j < 10_000; j++) {
  counter++; // 읽기→증가→쓰기 사이에 다른 스레드 끼어들 수 있음 → 갱신 분실
}
// 실제: 100,000보다 작은 값

// [해결 1] synchronized 블록 - 원자성 보장
Object lock = new Object();
int[] syncCounter = {0};
synchronized (lock) {
  syncCounter[0]++; // 읽기→증가→쓰기가 원자적으로 처리됨
}
// 결과 항상 100,000 ✓

// [해결 2] AtomicInteger - CAS 기반 원자적 연산
AtomicInteger atomicCounter = new AtomicInteger(0);
atomicCounter.incrementAndGet(); // CAS(Compare-And-Swap): 원자적, 락 없음
// 결과 항상 100,000 ✓
```

- `volatile`은 읽기와 쓰기 각각의 가시성만 보장한다. 읽기→쓰기 사이에 다른 스레드가 끼어드는 것을 막지 못한다.
- `AtomicInteger`는 CAS(Compare-And-Swap) 하드웨어 명령을 사용하여 락 없이 원자성을 보장한다.
- 단일 숫자 카운터에는 `synchronized`보다 `AtomicInteger`가 성능상 유리하다.

| 방식 | 갱신 분실 | 이유 |
|---|---|---|
| `volatile int counter++` | 발생 | 읽기→증가→쓰기가 원자적이지 않음 |
| `synchronized { counter++ }` | 없음 | 블록 전체가 임계 구역 |
| `AtomicInteger.incrementAndGet()` | 없음 | CAS 명령으로 원자적 보장 |

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam03.App3
  ```

---

## Exam04 - Lock

### 개념

`synchronized`는 간결하지만 락 획득 실패 시 무한 대기, 대기 중 인터럽트 불가 등의 한계가 있다.
`java.util.concurrent.locks` 패키지는 더 유연하고 세밀한 락 제어를 제공한다.

#### java.util.concurrent.locks 주요 구성요소

| 인터페이스 / 클래스 | 설명 |
|---|---|
| `Lock` | 락의 기본 인터페이스. `lock()`, `unlock()`, `tryLock()` 등을 정의 |
| `ReentrantLock` | `Lock` 구현체. `synchronized`와 동일한 상호 배제 보장 + 추가 기능 |
| `ReadWriteLock` | 읽기 락과 쓰기 락을 분리하는 인터페이스 |
| `ReentrantReadWriteLock` | `ReadWriteLock` 구현체. 다중 읽기 허용, 배타적 쓰기 |
| `Condition` | `Lock`에 연결된 조건 변수. `Object.wait()/notify()` 대체 |

#### synchronized vs ReentrantLock

| 기능 | `synchronized` | `ReentrantLock` |
|---|---|---|
| 기본 상호 배제 | O | O |
| 락 해제 방식 | 자동 (블록 종료 시) | 수동 — `unlock()` 명시 필수 |
| 비차단 획득 | X | `tryLock()` |
| 타임아웃 획득 | X | `tryLock(time, unit)` |
| 인터럽트 가능 대기 | X | `lockInterruptibly()` |
| 공정 락 | X | `new ReentrantLock(true)` |
| 조건 변수 | 하나 (`wait/notify`) | 여러 개 (`newCondition()`) |

#### ReentrantLock 기본 패턴

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

#### ReadWriteLock 상태표

| 현재 락 상태 | 읽기 락 추가 획득 | 쓰기 락 추가 획득 |
|---|---|---|
| 아무 락 없음 | O | O |
| 읽기 락 보유 중 | O (동시 허용) | X (쓰기는 대기) |
| 쓰기 락 보유 중 | X (읽기도 대기) | X (쓰기도 대기) |

#### Condition 주요 메서드

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

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `ReentrantLock` 기본 — Bank 예제, `try-finally` 패턴 |
| `App2` | `ReentrantLock` 고급 — `tryLock()`, `tryLock(timeout)`, `lockInterruptibly()` |
| `App3` | `ReentrantReadWriteLock` — 다중 읽기 동시 허용, 배타적 쓰기, 성능 비교 |
| `App4` | `Condition` — `await()`/`signal()`, 생산자-소비자 BoundedBuffer |

### App - ReentrantLock 기본

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam04.App
  ```

### App2 - ReentrantLock 고급 기능

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam04.App2
  ```

### App3 - ReentrantReadWriteLock

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam04.App3
  ```

### App4 - Condition

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam04.App4
  ```

---

## Exam05 - Semaphore

### 개념

`Semaphore`는 동시에 실행할 수 있는 스레드의 수를 N개로 제한하는 동기화 도구다.
`synchronized` / `Lock`이 한 번에 한 스레드만 허용하는 뮤텍스라면,
`Semaphore(N)`은 한 번에 최대 N개의 스레드를 허용하는 카운팅 세마포어다.

#### 핵심 개념 — permits(허가)

```
Semaphore(3) 초기 상태: permits = 3

Thread-A acquire() → permits = 2
Thread-B acquire() → permits = 1
Thread-C acquire() → permits = 0
Thread-D acquire() → permits = 0이므로 WAITING 대기...

Thread-A release() → permits = 1 → Thread-D 깨어남
Thread-D acquire() 완료 → permits = 0
```

#### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `acquire()` | permits 1 감소. 0이면 양수가 될 때까지 블로킹 대기 |
| `release()` | permits 1 증가. 대기 중인 스레드 하나를 깨운다 |
| `acquire(int n)` | permits n개 감소 (한 번에 n개 획득) |
| `release(int n)` | permits n개 증가 (한 번에 n개 반납) |
| `tryAcquire()` | 즉시 획득 시도. 실패 시 false 반환 (대기 없음) |
| `tryAcquire(timeout, unit)` | 지정 시간 동안 획득 시도. 초과 시 false 반환 |
| `availablePermits()` | 현재 사용 가능한 permits 수 반환 |

#### synchronized / Lock vs Semaphore

| 방식 | 동시 허용 수 | 소유권 | 적합한 상황 |
|---|---|---|---|
| `synchronized` / `Lock` | 1개 (뮤텍스) | 있음 (획득한 스레드만 해제) | 임계 구역 보호 |
| `Semaphore(1)` | 1개 (뮤텍스) | **없음** (어느 스레드든 release 가능) | 신호 패턴, 단순 뮤텍스 |
| `Semaphore(N)` | N개 | 없음 | 동시 접근 수 제한, 자원 풀 |

#### 소유권 없음 — 신호(Signal) 패턴

`Semaphore`는 소유권이 없으므로 **acquire한 스레드가 아닌 다른 스레드도 release를 호출**할 수 있다.
이 특성을 이용해 스레드 간 이벤트 신호를 전달할 수 있다.

```
Semaphore(0): 초기 permits=0 → acquire() 즉시 대기
다른 스레드가 release() → 대기 중인 스레드 깨움
```

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Semaphore(3)` — 동시 접근 수 제한, 카운팅 세마포어 기본 |
| `App2` | `Semaphore(1)` 뮤텍스 (Bank 예제) + `Semaphore(0)` 신호 패턴 |
| `App3` | `tryAcquire()`, `acquire(n)/release(n)`, 공정 세마포어 |
| `App4` | 실용 예제 — 데이터베이스 커넥션 풀 |

### App - Semaphore 기본: 동시 접근 수 제한

```java
Semaphore semaphore = new Semaphore(3); // 동시 접근 허용 수: 3

// 10개 스레드가 동시에 진입 시도 → 3개씩 그룹으로 실행
new Thread(() -> {
  semaphore.acquire(); // permits 1 감소. permits=0이면 대기
  try {
    // 임계 구역 (500ms 소요)
  } finally {
    semaphore.release(); // permits 1 증가 → 다음 대기 스레드 깨움
  }
}).start();

// 기대 총 시간: 500ms × ⌈10/3⌉ = 500ms × 4 ≈ 2,000ms
// → 3개씩 묶여 실행됨
```

- `acquire()`와 `release()` 사이를 `try-finally`로 감싸 예외 발생 시에도 permits를 반드시 반납한다.
- `availablePermits()`로 현재 남은 permits 수를 확인할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam05.App
  ```

### App2 - Semaphore(1) 뮤텍스 + Semaphore(0) 신호 패턴

```java
// [파트 1] Semaphore(1) - 뮤텍스, Bank 동기화
private final Semaphore semaphore = new Semaphore(1); // permits=1 → 한 번에 한 스레드만

public void deposit(int amount) {
  semaphore.acquire(); // permits 1→0 (다른 스레드는 acquire에서 대기)
  try {
    int current = balance;
    Thread.yield();
    balance = current + amount;
  } finally {
    semaphore.release(); // permits 0→1 (대기 중인 스레드 깨움)
  }
}
// 해결 1: 갱신 분실 해결 ✓  해결 2: Check-Then-Act 해결 ✓

// [파트 2] Semaphore(0) - 신호 패턴
Semaphore signal = new Semaphore(0); // permits=0 → acquire() 즉시 대기

Thread worker = new Thread(() -> {
  // 작업 수행...
  signal.release(); // 소유권 없음 → worker가 main의 세마포어를 release 가능
});

signal.acquire(); // worker가 release()할 때까지 대기
// → worker 완료 후 여기부터 실행

// [파트 2-2] 실행 순서 제어 (A→B→C 순서 보장)
Semaphore aToB = new Semaphore(0);
Semaphore bToC = new Semaphore(0);

Thread A = new Thread(() -> { /*작업*/ aToB.release(); });         // A완료→B신호
Thread B = new Thread(() -> { aToB.acquire(); /*작업*/ bToC.release(); }); // A대기→작업→C신호
Thread C = new Thread(() -> { bToC.acquire(); /*작업*/ });         // B대기→작업
// C→B→A 순서로 시작해도 A→B→C 순서로 실행 ✓
```

- `Semaphore(1)`은 `synchronized`와 동일한 상호 배제를 제공한다. 단, 소유권이 없어 다른 스레드도 `release()`를 호출할 수 있다.
- `Semaphore(0)` 신호 패턴은 `Thread.join()`보다 유연하다. 작업 완료 시점이 아니라 원하는 중간 지점에서 신호를 보낼 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam05.App2
  ```

### App3 - tryAcquire / acquire(N) / 공정 세마포어

```java
// [1] tryAcquire() - 비차단 획득
boolean got = semaphore.tryAcquire(); // 즉시 시도
if (got) {
  try { /* 임계 구역 */ } finally { semaphore.release(); }
} else {
  // permits=0 → 대기 없이 즉시 반환, 다른 작업 수행
}

// [2] tryAcquire(timeout, unit) - 타임아웃 있는 획득
boolean acquired = semaphore.tryAcquire(2, TimeUnit.SECONDS); // 최대 2초 대기
if (acquired) {
  try { /* 임계 구역 */ } finally { semaphore.release(); }
} else {
  // 2초 내 획득 실패 → 포기
}

// [3] acquire(N) / release(N) - N개 permits 동시 획득·반납
Semaphore sem = new Semaphore(10); // 총 10 단위
sem.acquire(3); // 대용량 작업: 3 단위 점유
sem.release(3); // 3 단위 한꺼번에 반납

// [4] 공정 세마포어 - 대기 순서대로 획득
Semaphore fairSem = new Semaphore(1, true); // fair=true: FIFO 대기 순서 보장
// fair=false (기본): 대기 순서 무관, 임의의 스레드 획득 (성능 우선)
// fair=true        : 먼저 대기한 스레드가 먼저 획득 (공정성 우선)
```

- `tryAcquire()`는 `ReentrantLock.tryLock()`과 동일한 개념이다. 락이 바쁠 때 대안 경로로 분기할 때 사용한다.
- `acquire(N)`은 여러 자원 단위를 소비하는 작업(CPU 코어, 메모리 블록 등)의 가중치를 표현할 때 유용하다.
- 공정 세마포어는 기아(starvation)를 방지하지만, OS 스케줄링 비용 때문에 처리량이 낮아질 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam05.App3
  ```

### App4 - 데이터베이스 커넥션 풀

```java
class ConnectionPool {
  private final Semaphore semaphore;

  public ConnectionPool(int maxConnections) {
    semaphore = new Semaphore(maxConnections, true); // 공정 세마포어
  }

  // 블로킹 연결 획득 - 사용 가능한 연결이 생길 때까지 대기
  public String acquire() throws InterruptedException {
    semaphore.acquire(); // 사용 가능한 permits가 없으면 대기
    return checkOut();
  }

  // 타임아웃 연결 획득 - 지정 시간 내 연결 없으면 null 반환
  public String acquire(long timeoutMs) throws InterruptedException {
    boolean got = semaphore.tryAcquire(timeoutMs, TimeUnit.MILLISECONDS);
    if (!got) return null; // 타임아웃 → 빠른 실패(fail-fast)
    return checkOut();
  }

  // 연결 반납
  public void release(String connection) {
    checkIn(connection);
    semaphore.release(); // permits 증가 → 다음 대기 스레드 깨움
  }
}

// 사용
String conn = pool.acquire();     // 연결 획득 (없으면 대기)
try {
  /* DB 작업 */
} finally {
  pool.release(conn);             // 반드시 반납
}

// 타임아웃 적용
String conn = pool.acquire(500); // 500ms 내 연결 없으면 null
if (conn == null) {
  // 빠른 실패 처리 (에러 응답, 재시도 등)
}
```

- `Semaphore`의 permits 수가 최대 동시 연결 수와 자연스럽게 대응된다.
- 공정 세마포어(`fair=true`)를 사용하면 오래 기다린 스레드가 먼저 연결을 얻어 기아 현상을 방지한다.
- `acquire(long timeoutMs)`로 연결 대기 타임아웃을 설정하면 무한 대기를 방지하고 빠른 실패(fail-fast) 전략을 구현할 수 있다.

| 시나리오 | 동작 |
|---|---|
| 사용 가능한 연결 있음 | `acquire()` 즉시 반환 |
| 모든 연결 사용 중 (블로킹) | `acquire()` 반납될 때까지 대기 |
| 모든 연결 사용 중 (타임아웃) | `acquire(ms)` 지정 시간 내 반납 없으면 null |

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam05.App4
  ```

---

## Exam06 - Executor Framework

### 개념

Executor 프레임워크는 스레드를 직접 만들고 관리하는 일을 대신해 주는 실행 관리 도구다.
개발자는 작업(`Runnable`, `Callable`)을 제출하고, Executor는 내부의 스레드 풀을 사용해 작업을 실행한다.

#### 왜 Executor를 쓰는가?

스레드를 작업마다 직접 생성하면 다음 문제가 생긴다.

- 작업이 많아질수록 스레드 생성/삭제 비용이 커진다.
- 동시에 너무 많은 스레드가 만들어져 시스템 자원을 과하게 사용할 수 있다.
- 작업 제출, 결과 수집, 취소, 종료 처리를 직접 관리해야 한다.

ExecutorService를 사용하면 정해진 수의 스레드를 재사용하면서 작업 큐에 쌓인 일을 순서대로 처리할 수 있다.

```
작업 제출 → 작업 큐 → 스레드 풀의 worker 스레드가 꺼내 실행
```

#### 주요 타입

| 타입 | 설명 |
|---|---|
| `Executor` | `execute(Runnable)`만 가진 가장 기본 실행 인터페이스 |
| `ExecutorService` | 작업 제출, 종료, 결과 관리 기능을 제공 |
| `ScheduledExecutorService` | 지연 실행, 반복 실행 기능을 제공 |
| `Runnable` | 결과 없는 작업 |
| `Callable<V>` | V 타입 결과를 반환하는 작업 |
| `Future<V>` | 아직 끝나지 않은 작업의 결과를 나중에 받는 객체 |

#### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `execute(Runnable)` | 결과 없는 작업 실행 |
| `submit(Runnable)` | 작업 제출 후 `Future<?>` 반환 |
| `submit(Callable<V>)` | 결과를 반환하는 작업 제출 후 `Future<V>` 반환 |
| `invokeAll(tasks)` | 여러 작업을 제출하고 모두 끝날 때까지 대기 |
| `shutdown()` | 새 작업은 받지 않고, 제출된 작업을 마치면 종료 |
| `shutdownNow()` | 실행 중인 작업에 interrupt를 보내고, 대기 중인 작업 반환 |
| `awaitTermination()` | Executor가 종료될 때까지 지정 시간 대기 |

#### Thread 직접 사용 vs ExecutorService

| 방식 | 특징 | 적합한 상황 |
|---|---|---|
| `new Thread(task).start()` | 작업마다 스레드를 직접 생성 | 매우 단순한 일회성 예제 |
| `ExecutorService` | 스레드 풀과 작업 큐로 실행 관리 | 여러 작업 처리, 서버 요청 처리, 배치 작업 |
| `ScheduledExecutorService` | 시간 기반 실행 관리 | 주기적 점검, 타임아웃, 예약 작업 |

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `ExecutorService` 기본 - fixed thread pool, `execute()`, 종료 처리 |
| `App2` | `Callable` + `Future` - 작업 결과 받기 |
| `App3` | `invokeAll()`과 `CompletionService` - 여러 결과 수집 방식 비교 |
| `App4` | `ScheduledExecutorService` - 지연 실행, 반복 실행, 취소 |

### App - ExecutorService 기본

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

for (int i = 0; i < 10; i++) {
  executor.execute(() -> {
    // 결과 없는 작업
  });
}

executor.shutdown();
executor.awaitTermination(5, TimeUnit.SECONDS);
```

- `newFixedThreadPool(3)`은 최대 3개 스레드로 작업을 처리한다.
- 작업이 10개여도 동시에 실행되는 작업은 최대 3개다.
- `shutdown()`을 호출하지 않으면 풀의 worker 스레드가 살아 있어 프로그램이 끝나지 않을 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam06.App
  ```

### App2 - Callable + Future

```java
Callable<Integer> task = () -> {
  return 100;
};

Future<Integer> future = executor.submit(task);
Integer result = future.get(); // 작업 완료까지 대기 후 결과 반환
```

- `Runnable`은 결과를 반환하지 않는다.
- `Callable<V>`는 결과를 반환하고 checked exception도 던질 수 있다.
- `Future.get()`은 결과가 준비될 때까지 현재 스레드를 대기시킨다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam06.App2
  ```

### App3 - invokeAll / CompletionService

```java
List<Future<String>> futures = executor.invokeAll(tasks);

for (Future<String> future : futures) {
  System.out.println(future.get());
}
```

- `invokeAll()`은 모든 작업이 완료될 때까지 기다린다.
- 반환된 `Future` 목록의 순서는 제출한 작업 목록의 순서와 같다.

```java
CompletionService<String> cs = new ExecutorCompletionService<>(executor);

cs.submit(task1);
cs.submit(task2);
cs.submit(task3);

Future<String> completed = cs.take(); // 먼저 끝난 작업부터 반환
```

- `CompletionService`는 완료된 작업부터 결과를 꺼낼 수 있다.
- 작업마다 걸리는 시간이 다를 때 빠른 결과를 먼저 처리하는 데 유용하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam06.App3
  ```

### App4 - ScheduledExecutorService

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

scheduler.schedule(task, 1, TimeUnit.SECONDS);
scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
scheduler.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
```

| 메서드 | 실행 기준 |
|---|---|
| `schedule()` | 지정 시간 뒤 한 번 실행 |
| `scheduleAtFixedRate()` | 작업 시작 시점을 기준으로 일정 주기 실행 |
| `scheduleWithFixedDelay()` | 이전 작업 종료 후 일정 시간 뒤 다음 실행 |

- 반복 작업은 `ScheduledFuture.cancel(false)`로 취소할 수 있다.
- 스케줄러도 `ExecutorService`이므로 사용 후 `shutdown()`을 호출해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam06.App4
  ```

---

## Exam07 - ForkJoinPool

### 개념

`ForkJoinPool`은 큰 작업을 작은 작업으로 나누어 병렬로 처리한 뒤, 결과를 다시 합치는 분할 정복 방식의 스레드 풀이다.
`ExecutorService`의 구현체 중 하나지만, 일반 작업 큐 하나를 여러 스레드가 공유하는 방식보다 재귀적으로 쪼개지는 작업에 더 특화되어 있다.

```
큰 작업
 ├─ 작은 작업 A
 │   ├─ 더 작은 작업 A-1
 │   └─ 더 작은 작업 A-2
 └─ 작은 작업 B
     ├─ 더 작은 작업 B-1
     └─ 더 작은 작업 B-2

fork → 작은 작업을 큐에 넣음
join → 작은 작업의 결과를 기다림
```

#### 핵심 개념

| 개념 | 설명 |
|---|---|
| fork | 현재 작업을 더 작은 작업으로 나누어 비동기 실행 가능하게 만든다 |
| join | fork한 작업이 끝날 때까지 기다린 뒤 결과를 가져온다 |
| threshold | 더 이상 나누지 않고 직접 처리할 작업 크기 |
| work-stealing | 쉬는 worker가 다른 worker의 큐에서 작업을 훔쳐 와 처리하는 방식 |

#### 주요 타입

| 타입 | 설명 |
|---|---|
| `ForkJoinPool` | Fork/Join 작업을 실행하는 스레드 풀 |
| `ForkJoinTask<V>` | Fork/Join 작업의 공통 부모 클래스 |
| `RecursiveTask<V>` | 결과를 반환하는 재귀 작업 |
| `RecursiveAction` | 결과를 반환하지 않는 재귀 작업 |

#### ExecutorService vs ForkJoinPool

| 방식 | 특징 | 적합한 상황 |
|---|---|---|
| `ExecutorService` | 제출된 독립 작업을 스레드 풀이 실행 | 요청 처리, 배치 작업, 일반 비동기 작업 |
| `ForkJoinPool` | 큰 작업을 작은 작업으로 나누고 합침 | 배열 계산, 검색, 정렬, 재귀적 분할 정복 |
| `parallelStream()` | 내부적으로 `ForkJoinPool.commonPool()` 사용 | 간단한 컬렉션 병렬 처리 |

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `RecursiveTask` - 배열 합계 계산 |
| `App2` | `RecursiveAction` - 배열 값을 직접 변경 |
| `App3` | Work-Stealing - `getStealCount()`로 동작 관찰 |
| `App4` | `commonPool`과 `parallelStream()` |

### App - RecursiveTask

`RecursiveTask<V>`는 결과를 반환하는 Fork/Join 작업이다.

```java
class SumTask extends RecursiveTask<Long> {
  @Override
  protected Long compute() {
    if (작업이 충분히 작다) {
      return 직접계산();
    }

    SumTask left = new SumTask(...);
    SumTask right = new SumTask(...);

    left.fork();
    long rightResult = right.compute();
    long leftResult = left.join();

    return leftResult + rightResult;
  }
}
```

- 작업 크기가 `threshold` 이하이면 직접 계산한다.
- 작업이 크면 작은 작업으로 나누고 결과를 합친다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam07.App
  ```

### App2 - RecursiveAction

`RecursiveAction`은 결과를 반환하지 않는 Fork/Join 작업이다.

```java
class DiscountTask extends RecursiveAction {
  @Override
  protected void compute() {
    if (작업이 충분히 작다) {
      직접변경();
      return;
    }

    invokeAll(left, right);
  }
}
```

- 배열을 직접 수정하거나 외부 저장소에 결과를 쓰는 작업에 적합하다.
- `invokeAll(left, right)`는 여러 하위 작업을 실행하고 완료될 때까지 기다린다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam07.App2
  ```

### App3 - Work-Stealing

`ForkJoinPool`의 worker 스레드는 각자 작업 큐를 가진다.
자기 큐에 할 일이 없어지면 다른 worker의 큐에서 작업을 훔쳐 와 처리한다.

```java
ForkJoinPool pool = new ForkJoinPool(4);
int result = pool.invoke(new FibonacciTask(35));

System.out.println(pool.getStealCount());
```

- `getStealCount()`는 worker가 다른 큐에서 훔쳐 온 작업 수의 추정치를 반환한다.
- steal count가 증가하면 worker들이 작업을 나누어 처리했다는 뜻이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam07.App3
  ```

### App4 - commonPool과 parallelStream

JVM에는 공용 ForkJoinPool인 `ForkJoinPool.commonPool()`이 있다.
`parallelStream()`은 기본적으로 이 공용 풀을 사용한다.
이때 `main` 같은 호출 스레드도 일부 작업을 직접 처리할 수 있지만,
`main`이 `commonPool`의 worker 스레드로 소속되는 것은 아니다.

```java
int sum =
    numbers.parallelStream()
        .mapToInt(n -> n * n)
        .sum();
```

- `commonPool`은 애플리케이션 전체에서 공유된다.
- `ForkJoinPool.commonPool().getParallelism()` 값은 공용 풀의 worker 병렬 처리 수준이며, `main` 스레드는 포함하지 않는다.
- `parallelStream()` 출력에 `main`이 보일 수 있다. 이는 호출 스레드가 결과를 기다리는 동안 일부 작업을 직접 처리했기 때문이다.
- 공용 풀이므로 직접 `shutdown()`하지 않는다.
- 오래 걸리는 블로킹 I/O 작업을 `parallelStream()`에 많이 넣으면 다른 병렬 작업도 영향을 받을 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam07.App4
  ```

---

## Exam08 - CompletableFuture

### 개념

`CompletableFuture`는 비동기 작업의 결과를 표현하고, 그 결과를 바탕으로 다음 작업을 이어 붙일 수 있게 해 주는 도구다.
기존 `Future`는 결과를 받으려면 `get()`으로 기다려야 했지만, `CompletableFuture`는 결과 변환, 후속 작업, 여러 작업 조합, 예외 처리를 체인으로 구성할 수 있다.

```
비동기 작업 시작
  → 결과 변환
  → 다른 비동기 작업과 조합
  → 예외 처리
  → 최종 결과 사용
```

#### Future vs CompletableFuture

| 방식 | 특징 |
|---|---|
| `Future` | 결과를 나중에 받을 수 있지만, 후속 작업 연결이 불편하다 |
| `CompletableFuture` | 결과를 변환하고 여러 비동기 작업을 조합할 수 있다 |

#### 주요 생성 메서드

| 메서드 | 설명 |
|---|---|
| `runAsync(Runnable)` | 결과 없는 작업을 비동기로 실행 |
| `supplyAsync(Supplier<T>)` | 결과를 만드는 작업을 비동기로 실행 |
| `completedFuture(value)` | 이미 완료된 `CompletableFuture` 생성 |

별도 `Executor`를 지정하지 않으면 `ForkJoinPool.commonPool()`에서 실행된다.
블로킹 I/O가 많거나 실행 정책을 분리해야 한다면 직접 만든 `ExecutorService`를 넘기는 편이 좋다.

#### 주요 후속 작업 메서드

| 메서드 | 설명 |
|---|---|
| `thenApply()` | 이전 결과를 받아 새 결과로 변환 |
| `thenAccept()` | 이전 결과를 소비하고 결과를 반환하지 않음 |
| `thenRun()` | 이전 결과를 받지 않고 다음 작업 실행 |
| `thenCompose()` | 비동기 작업을 이어 붙여 중첩 future를 평평하게 만듦 |
| `thenCombine()` | 독립적인 두 future 결과를 합침 |
| `allOf()` | 여러 future가 모두 끝날 때까지 대기 |
| `anyOf()` | 여러 future 중 하나라도 끝나면 완료 |

#### 예외 처리 메서드

| 메서드 | 설명 |
|---|---|
| `exceptionally()` | 예외 발생 시 대체 결과 반환 |
| `handle()` | 성공 결과와 예외를 모두 받아 새 결과 반환 |
| `whenComplete()` | 성공 결과나 예외를 관찰. 결과 자체는 바꾸지 않음 |

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `runAsync()`, `supplyAsync()`, `join()` 기본 |
| `App2` | `thenApply()`, `thenAccept()`, `thenRun()` 체이닝 |
| `App3` | `thenCombine()`, `allOf()`, `anyOf()` 작업 조합 |
| `App4` | `exceptionally()`, `handle()`, `whenComplete()` 예외 처리 |

### App - runAsync / supplyAsync

```java
CompletableFuture<Void> f1 =
    CompletableFuture.runAsync(() -> {
      // 결과 없는 비동기 작업
    });

CompletableFuture<Integer> f2 =
    CompletableFuture.supplyAsync(() -> {
      return 100;
    });

f1.join();
Integer result = f2.join();
```

- `runAsync()`는 결과가 없는 작업에 사용한다.
- `supplyAsync()`는 결과를 만드는 작업에 사용한다.
- `join()`은 작업 완료까지 기다리고 결과를 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App
  ```

### App2 - 결과 변환과 체이닝

```java
CompletableFuture<Void> future =
    CompletableFuture.supplyAsync(() -> findMember())
        .thenApply(member -> applyDiscount(member))
        .thenAccept(amount -> System.out.println(amount))
        .thenRun(() -> System.out.println("done"));
```

- `thenApply()`는 이전 결과를 받아 다른 값으로 변환한다.
- `thenAccept()`는 이전 결과를 소비하고 값을 반환하지 않는다.
- `thenRun()`은 이전 결과를 받지 않고 다음 작업만 실행한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App2
  ```

### App3 - 여러 작업 조합

```java
CompletableFuture<Integer> total =
    productPrice.thenCombine(deliveryFee, Integer::sum);
```

- `thenCombine()`은 서로 독립적인 두 작업 결과를 합친다.

```java
CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
all.join();
```

- `allOf()`는 여러 작업이 모두 끝날 때까지 기다린다.
- `allOf()` 자체는 개별 결과를 모아 주지 않으므로 각 future에서 `join()`으로 꺼낸다.

```java
Object fastest = CompletableFuture.anyOf(f1, f2, f3).join();
```

- `anyOf()`는 여러 작업 중 하나라도 먼저 끝나면 완료된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App3
  ```

### App4 - 예외 처리

```java
CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> loadPrice())
        .exceptionally(ex -> 0);
```

- `exceptionally()`는 예외가 발생했을 때 대체 결과를 만든다.

```java
CompletableFuture<String> future =
    CompletableFuture.supplyAsync(() -> loadStock())
        .handle((stock, ex) -> ex != null ? "실패" : "재고: " + stock);
```

- `handle()`은 성공과 실패를 모두 처리하고 새 결과를 만든다.
- `whenComplete()`는 결과나 예외를 기록할 때 사용한다. 결과 자체를 복구하지는 않는다.
- `join()`은 예외를 `CompletionException`으로 감싸서 던진다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App4
  ```

---

## Exam09 - Spring WebFlux 비동기 예외 처리

### 개념

Spring WebFlux는 요청 처리 결과를 `Mono` 또는 `Flux`로 표현한다.
동기 코드에서는 예외가 `throw`로 즉시 전달되지만, WebFlux의 비동기 파이프라인에서는 예외가 `error signal`로 전달된다.

```
요청 수신
  → Mono/Flux 파이프라인 구성
  → WebFlux가 구독
  → 값이 나오면 onNext/onComplete
  → 실패하면 onError
```

따라서 WebFlux에서 비동기 예외를 다룰 때는 `try-catch`보다 Reactor 연산자를 사용한다.

#### 주요 예외 처리 연산자

| 연산자 | 설명 |
|---|---|
| `doOnError()` | 예외를 관찰하고 로그를 남김. 복구하지 않음 |
| `onErrorReturn()` | 예외 발생 시 고정된 대체 값을 반환 |
| `onErrorResume()` | 예외 발생 시 다른 `Mono`/`Flux`로 복구 |
| `onErrorMap()` | 예외를 다른 예외로 변환 |
| `timeout()` | 지정 시간 안에 결과가 없으면 예외 발생 |
| `retryWhen()` | 실패한 작업을 정책에 따라 재시도 |

#### try-catch와 다른 점

```java
Mono<Order> mono = service.findOrder(id);
```

위 코드는 아직 실행된 것이 아니다.
`Mono`는 작업 설명서에 가깝고, 실제 실행은 구독 시점에 일어난다.
그래서 다음 코드는 파이프라인 내부의 비동기 예외를 잡지 못한다.

```java
try {
  return service.findOrder(id);
} catch (Exception e) {
  return Mono.just(fallback);
}
```

대신 파이프라인에 예외 처리 연산자를 붙여야 한다.

```java
return service.findOrder(id)
    .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(fallback));
```

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Mono`에서 `doOnError()`와 `onErrorResume()`으로 복구 |
| `App2` | 도메인 예외를 `ResponseStatusException`으로 매핑 |
| `App3` | `timeout()`, `retryWhen()`, fallback 처리 |
| `App4` | `Flux`에서 항목별 예외 처리 |

### App - Mono 기본 예외 처리

```java
Mono<Order> result =
    findOrder(id)
        .doOnError(ex -> log(ex))
        .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(fallback));
```

- `doOnError()`는 로그처럼 부수 효과를 남길 때 사용한다.
- `doOnError()`는 예외를 복구하지 않는다.
- `onErrorResume()`은 실패한 파이프라인을 다른 `Mono`로 대체한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam09.App
  ```

### App2 - HTTP 예외 매핑

```java
return service.getMember(id)
    .onErrorMap(
        UnauthorizedMemberException.class,
        ex -> new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex));
```

- 서비스 계층의 도메인 예외를 WebFlux가 이해할 수 있는 HTTP 예외로 바꾼다.
- 실제 WebFlux 서버에서는 `ResponseStatusException`이 HTTP 상태 코드로 변환된다.
- `onErrorMap()`은 예외를 변환할 뿐 정상 값으로 복구하지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam09.App2
  ```

### App3 - timeout / retry / fallback

```java
return remoteCall()
    .timeout(Duration.ofMillis(500))
    .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(200)))
    .onErrorResume(ex -> Mono.just(fallback));
```

- `timeout()`은 지정 시간 안에 결과가 없으면 실패로 처리한다.
- `retryWhen()`은 실패한 작업을 다시 구독한다.
- 재시도까지 실패하면 `onErrorResume()`으로 대체 결과를 만들 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam09.App3
  ```

### App4 - Flux 항목별 예외 처리

```java
Flux.fromIterable(orderIds)
    .flatMap(orderId ->
        createDelivery(orderId)
            .onErrorResume(ex -> Mono.just(DeliveryResult.failed(orderId))))
```

- `Flux` 바깥쪽에서만 예외를 처리하면 한 항목의 실패가 전체 스트림 실패로 이어질 수 있다.
- 항목마다 독립적으로 처리해야 한다면 `flatMap()` 내부에서 `onErrorResume()`을 적용한다.
- 이 방식은 여러 요청, 여러 메시지, 여러 DB row를 처리할 때 유용하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam09.App4
  ```

---

## Exam10 - Virtual Thread

### 개념

가상 스레드(Virtual Thread)는 JVM이 관리하는 가벼운 스레드다.
기존 플랫폼 스레드는 OS 스레드와 연결되어 있어 많이 만들기 어렵지만,
가상 스레드는 훨씬 낮은 비용으로 많이 만들 수 있다.

Java 21부터 정식 기능으로 사용할 수 있다.

```
플랫폼 스레드:
  Java Thread ≈ OS Thread

가상 스레드:
  Java Thread → JVM이 carrier thread 위에서 스케줄링
```

가상 스레드는 특히 네트워크 I/O, DB I/O, 파일 I/O, `Thread.sleep()`처럼 대기 시간이 많은 작업에 적합하다.
CPU 계산 자체를 빠르게 만드는 기능은 아니다.

#### 주요 생성 방법

| 방법 | 설명 |
|---|---|
| `Thread.startVirtualThread(task)` | 가상 스레드를 만들고 즉시 시작 |
| `Thread.ofVirtual().start(task)` | 가상 스레드 builder 사용 |
| `Executors.newVirtualThreadPerTaskExecutor()` | 작업마다 새 가상 스레드를 만드는 ExecutorService |

#### 플랫폼 스레드 vs 가상 스레드

| 구분 | 플랫폼 스레드 | 가상 스레드 |
|---|---|---|
| 관리 주체 | OS + JVM | JVM |
| 생성 비용 | 큼 | 작음 |
| 적합한 작업 | 적은 수의 오래 실행되는 작업 | 많은 수의 블로킹 I/O 작업 |
| `Thread` API | 사용 | 사용 |
| `isVirtual()` | `false` | `true` |

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Thread.ofVirtual()`, `Thread.startVirtualThread()` 기본 |
| `App2` | 많은 가상 스레드를 만들어 대기 작업 실행 |
| `App3` | `newVirtualThreadPerTaskExecutor()`와 `Callable/Future` |
| `App4` | 가상 스레드와 외부 자원 제한 주의점 |

### App - Virtual Thread 기본

```java
Thread thread =
    Thread.ofVirtual()
        .name("virtual-worker")
        .start(() -> {
          System.out.println(Thread.currentThread().isVirtual());
        });

thread.join();
```

- 가상 스레드도 `Thread` 객체다.
- `Thread.currentThread().isVirtual()`로 가상 스레드인지 확인할 수 있다.
- `Thread.ofPlatform()`으로 플랫폼 스레드를 만들 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam10.App
  ```

### App2 - 많은 가상 스레드

```java
for (int i = 0; i < 1000; i++) {
  Thread.startVirtualThread(() -> {
    Thread.sleep(200);
  });
}
```

- 가상 스레드는 생성 비용이 작아 많은 블로킹 작업을 단순한 코드로 표현할 수 있다.
- 작업마다 스레드를 하나씩 주는 방식이 다시 현실적인 선택지가 된다.
- CPU를 많이 쓰는 계산 작업은 CPU 코어 수 이상의 병렬성으로 빨라지지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam10.App2
  ```

### App3 - VirtualThreadPerTaskExecutor

```java
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
  Future<String> future = executor.submit(() -> {
    return "result";
  });

  System.out.println(future.get());
}
```

- `newVirtualThreadPerTaskExecutor()`는 작업마다 새 가상 스레드를 만들어 실행한다.
- 기존 `ExecutorService`, `Callable`, `Future` API와 함께 사용할 수 있다.
- `ExecutorService`는 사용 후 닫아야 하므로 `try-with-resources`를 사용하면 좋다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam10.App3
  ```

### App4 - 외부 자원 제한

가상 스레드는 많이 만들 수 있지만, 외부 자원은 무한하지 않다.
DB 커넥션, 원격 API, 파일 핸들, rate limit 같은 자원은 별도로 제한해야 한다.

```java
Semaphore dbConnections = new Semaphore(3);

Thread.startVirtualThread(() -> {
  dbConnections.acquire();
  try {
    // DB 사용
  } finally {
    dbConnections.release();
  }
});
```

- 가상 스레드는 블로킹 대기 비용을 낮춰 준다.
- 제한된 외부 자원의 수를 늘려 주지는 않는다.
- 필요한 경우 `Semaphore`, 커넥션 풀, rate limiter 등으로 동시 접근 수를 제한한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam10.App4
  ```
