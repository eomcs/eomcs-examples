# Exam01 - Thread 생성과 제어

## 개념

스레드(Thread)는 프로세스 내에서 독립적으로 실행되는 실행 흐름이다.
Java의 `Thread` 클래스는 `java.lang` 패키지에 소속되어 있어 `import` 없이 사용할 수 있다.
JVM이 시작하면 main 스레드가 자동으로 생성되며, 추가 스레드를 직접 생성하여 병렬 실행을 구현할 수 있다.

### Thread 생성 방법

| 방법 | 특징 | 사용 방식 |
|---|---|---|
| `Thread` 클래스 상속 | `run()` 재정의. 다른 클래스 상속 중이면 사용 불가 | `class MyThread extends Thread` |
| `Runnable` 인터페이스 구현 | 상속 제한 없음. Thread와 실행 로직 분리 | `new Thread(new MyRunnable())` |
| 람다로 `Runnable` 구현 | Java 8+. 가장 간결한 표현 | `new Thread(() -> { ... })` |

### Thread 주요 메서드

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

### Thread 상태 (Thread.State)

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

### interrupt() 동작 방식

| 대상 스레드 상태 | `interrupt()` 효과 |
|---|---|
| `sleep()` / `join()` / `wait()` 대기 중 | `InterruptedException` 발생 (인터럽트 플래그 초기화됨) |
| 루프 등 일반 실행 중 | 인터럽트 플래그만 `true`로 설정. `isInterrupted()`로 확인 필요 |

### 데몬 스레드 vs 일반 스레드

| 구분 | 일반 스레드 | 데몬 스레드 |
|---|---|---|
| 설정 | 기본값 | `setDaemon(true)` — `start()` 전에 호출 |
| JVM 종료 조건 | 모든 일반 스레드가 종료되면 JVM 종료 | 일반 스레드가 모두 종료되면 강제 종료됨 |
| 용도 | 일반 작업 | 백그라운드 보조 작업 (GC, 모니터링 등) |

## App - Thread 생성 방법

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam01.App
  ```

## App2 - Thread 제어 메서드

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam01.App2
  ```

## App3 - Thread 상태

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam01.App3
  ```
