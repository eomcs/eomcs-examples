# Exam05 - Semaphore

## 개념

`Semaphore`는 동시에 실행할 수 있는 스레드의 수를 N개로 제한하는 동기화 도구다.
`synchronized` / `Lock`이 한 번에 한 스레드만 허용하는 뮤텍스라면,
`Semaphore(N)`은 한 번에 최대 N개의 스레드를 허용하는 카운팅 세마포어다.

### 핵심 개념 — permits(허가)

```
Semaphore(3) 초기 상태: permits = 3

Thread-A acquire() → permits = 2
Thread-B acquire() → permits = 1
Thread-C acquire() → permits = 0
Thread-D acquire() → permits = 0이므로 WAITING 대기...

Thread-A release() → permits = 1 → Thread-D 깨어남
Thread-D acquire() 완료 → permits = 0
```

### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `acquire()` | permits 1 감소. 0이면 양수가 될 때까지 블로킹 대기 |
| `release()` | permits 1 증가. 대기 중인 스레드 하나를 깨운다 |
| `acquire(int n)` | permits n개 감소 (한 번에 n개 획득) |
| `release(int n)` | permits n개 증가 (한 번에 n개 반납) |
| `tryAcquire()` | 즉시 획득 시도. 실패 시 false 반환 (대기 없음) |
| `tryAcquire(timeout, unit)` | 지정 시간 동안 획득 시도. 초과 시 false 반환 |
| `availablePermits()` | 현재 사용 가능한 permits 수 반환 |

### synchronized / Lock vs Semaphore

| 방식 | 동시 허용 수 | 소유권 | 적합한 상황 |
|---|---|---|---|
| `synchronized` / `Lock` | 1개 (뮤텍스) | 있음 (획득한 스레드만 해제) | 임계 구역 보호 |
| `Semaphore(1)` | 1개 (뮤텍스) | **없음** (어느 스레드든 release 가능) | 신호 패턴, 단순 뮤텍스 |
| `Semaphore(N)` | N개 | 없음 | 동시 접근 수 제한, 자원 풀 |

### 소유권 없음 — 신호(Signal) 패턴

`Semaphore`는 소유권이 없으므로 **acquire한 스레드가 아닌 다른 스레드도 release를 호출**할 수 있다.
이 특성을 이용해 스레드 간 이벤트 신호를 전달할 수 있다.

```
Semaphore(0): 초기 permits=0 → acquire() 즉시 대기
다른 스레드가 release() → 대기 중인 스레드 깨움
```

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Semaphore(3)` — 동시 접근 수 제한, 카운팅 세마포어 기본 |
| `App2` | `Semaphore(1)` 뮤텍스 (Bank 예제) + `Semaphore(0)` 신호 패턴 |
| `App3` | `tryAcquire()`, `acquire(n)/release(n)`, 공정 세마포어 |
| `App4` | 실용 예제 — 데이터베이스 커넥션 풀 |

## App - Semaphore 기본: 동시 접근 수 제한

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

## App2 - Semaphore(1) 뮤텍스 + Semaphore(0) 신호 패턴

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

## App3 - tryAcquire / acquire(N) / 공정 세마포어

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

## App4 - 데이터베이스 커넥션 풀

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
