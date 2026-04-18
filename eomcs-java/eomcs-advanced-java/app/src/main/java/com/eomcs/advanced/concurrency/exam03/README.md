# Exam03 - volatile

## 개념

멀티스레드 환경에서 `synchronized` 없이 공유 변수를 읽고 쓰면 **가시성(Visibility) 문제**가 발생할 수 있다.
Java는 `volatile` 키워드로 이 문제를 해결하지만, 원자성(Atomicity)은 보장하지 않는다.

### 가시성(Visibility) 문제

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

### volatile 키워드

`volatile`은 변수 접근을 항상 메인 메모리를 통하도록 강제하여 두 가지 문제를 모두 해결한다.

| 동작 | 설명 |
|---|---|
| JIT 최적화 방지 | 레지스터 hoisting 금지 — 루프마다 메모리에서 재읽기 강제 |
| 쓰기 | 메모리 배리어 삽입 — CPU 캐시를 즉시 메인 메모리에 플러시 |
| 읽기 | 메모리 배리어 삽입 — 다른 코어의 캐시를 무효화하고 메인 메모리에서 최신 값 로드 |
| 재정렬 방지 | volatile 접근을 기준으로 앞뒤 명령어가 재정렬되지 않음 |

### volatile의 한계 — 원자성(Atomicity) 미보장

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

### volatile이 적합한 상황

```java
// O 적합: 한 스레드만 쓰고, 나머지는 읽기만 하는 플래그
volatile boolean stopFlag = false;

// X 부적합: 여러 스레드가 동시에 읽고 쓰는 카운터
volatile int counter = 0;
counter++; // 원자적이지 않다 → 갱신 분실 발생
```

### volatile vs synchronized vs AtomicInteger

| 방식 | 가시성 | 원자성 | 적합한 상황 |
|---|---|---|---|
| 없음 | X | X | (멀티스레드 공유 자원에 부적합) |
| `volatile` | O | X | 단순 플래그 (한 스레드 쓰기, 여러 스레드 읽기) |
| `synchronized` | O | O | 복합 연산, 여러 변수 동시 보호 |
| `AtomicInteger` | O | O | 단일 숫자 카운터, 락 없어 성능 우수 |

### App / App2 / App3 비교

| 클래스 | 변수 | 핵심 주제 |
|---|---|---|
| `App` | `static boolean running` | volatile 없음 — JIT 레지스터 최적화로 가시성 문제 재현 |
| `App2` | `static volatile boolean running` | volatile로 가시성 문제 해결 |
| `App3` | `static volatile int counter` | volatile의 한계 — counter++ 원자성 미보장, synchronized·AtomicInteger로 해결 |

## App - volatile 없음: 가시성 문제 재현

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

## App2 - volatile: 가시성 문제 해결

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

## App3 - volatile의 한계: 원자성 미보장

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
