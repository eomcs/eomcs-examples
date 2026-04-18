# Exam10 - Virtual Thread

## 개념

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

### 주요 생성 방법

| 방법 | 설명 |
|---|---|
| `Thread.startVirtualThread(task)` | 가상 스레드를 만들고 즉시 시작 |
| `Thread.ofVirtual().start(task)` | 가상 스레드 builder 사용 |
| `Executors.newVirtualThreadPerTaskExecutor()` | 작업마다 새 가상 스레드를 만드는 ExecutorService |

### 플랫폼 스레드 vs 가상 스레드

| 구분 | 플랫폼 스레드 | 가상 스레드 |
|---|---|---|
| 관리 주체 | OS + JVM | JVM |
| 생성 비용 | 큼 | 작음 |
| 적합한 작업 | 적은 수의 오래 실행되는 작업 | 많은 수의 블로킹 I/O 작업 |
| `Thread` API | 사용 | 사용 |
| `isVirtual()` | `false` | `true` |

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Thread.ofVirtual()`, `Thread.startVirtualThread()` 기본 |
| `App2` | 많은 가상 스레드를 만들어 대기 작업 실행 |
| `App3` | `newVirtualThreadPerTaskExecutor()`와 `Callable/Future` |
| `App4` | 가상 스레드와 외부 자원 제한 주의점 |

## App - Virtual Thread 기본

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

## App2 - 많은 가상 스레드

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

## App3 - VirtualThreadPerTaskExecutor

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

## App4 - 외부 자원 제한

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
