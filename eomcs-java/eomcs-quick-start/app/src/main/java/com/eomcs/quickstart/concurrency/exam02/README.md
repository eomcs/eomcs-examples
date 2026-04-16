# Exam02 - 동기화(synchronized)

## 개념

멀티스레드 환경에서 여러 스레드가 공유 자원에 동시에 접근하면 데이터가 손상되거나 일관성이 깨진다.
Java는 `synchronized` 키워드로 임계 구역(Critical Section)을 보호하여 이 문제를 해결한다.

### 동기화 없을 때 발생하는 문제

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

### synchronized 방식 비교

| 방식 | 문법 | 락 객체 | 특징 |
|---|---|---|---|
| synchronized 메서드 | `public synchronized void f()` | `this` | 메서드 전체가 임계 구역 |
| synchronized 블록 | `synchronized(obj) { ... }` | 지정한 객체 | 블록 내부만 임계 구역. 락 범위 최소화 가능 |

`synchronized` 메서드는 `synchronized(this) { 메서드 전체 }` 와 동일하다.

### 동기화 동작 원리 (모니터 락)

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

### App / App2 / App3 / App4 비교

| 클래스 | Bank.deposit() | Bank.withdraw() | 핵심 주제 |
|---|---|---|---|
| `App` | 동기화 없음 | 동기화 없음 | 갱신 분실, 잔액 음수, 잔액 불일치 문제 재현 |
| `App2` | `synchronized` 메서드 | `synchronized` 메서드 | 메서드 전체를 임계 구역으로 보호 |
| `App3` | `synchronized` 블록 (`lock` 객체) | `synchronized` 블록 (`lock` 객체) | 블록으로 임계 구역 선택적 보호 |
| `App4` | — | — | 락 범위 최소화 — 공유 자원 접근만 보호 |

## App - 동기화 없는 Bank (문제 재현)

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam02.App
  ```

## App2 - synchronized 메서드로 해결

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam02.App2
  ```

## App3 - synchronized 블록으로 해결

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam02.App3
  ```

## App4 - synchronized 블록 락 범위 최소화

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam02.App4
  ```
