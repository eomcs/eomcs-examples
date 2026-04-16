# Exam06 - Executor Framework

## 개념

Executor 프레임워크는 스레드를 직접 만들고 관리하는 일을 대신해 주는 실행 관리 도구다.
개발자는 작업(`Runnable`, `Callable`)을 제출하고, Executor는 내부의 스레드 풀을 사용해 작업을 실행한다.

### 왜 Executor를 쓰는가?

스레드를 작업마다 직접 생성하면 다음 문제가 생긴다.

- 작업이 많아질수록 스레드 생성/삭제 비용이 커진다.
- 동시에 너무 많은 스레드가 만들어져 시스템 자원을 과하게 사용할 수 있다.
- 작업 제출, 결과 수집, 취소, 종료 처리를 직접 관리해야 한다.

ExecutorService를 사용하면 정해진 수의 스레드를 재사용하면서 작업 큐에 쌓인 일을 순서대로 처리할 수 있다.

```
작업 제출 → 작업 큐 → 스레드 풀의 worker 스레드가 꺼내 실행
```

### 주요 타입

| 타입 | 설명 |
|---|---|
| `Executor` | `execute(Runnable)`만 가진 가장 기본 실행 인터페이스 |
| `ExecutorService` | 작업 제출, 종료, 결과 관리 기능을 제공 |
| `ScheduledExecutorService` | 지연 실행, 반복 실행 기능을 제공 |
| `Runnable` | 결과 없는 작업 |
| `Callable<V>` | V 타입 결과를 반환하는 작업 |
| `Future<V>` | 아직 끝나지 않은 작업의 결과를 나중에 받는 객체 |

### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `execute(Runnable)` | 결과 없는 작업 실행 |
| `submit(Runnable)` | 작업 제출 후 `Future<?>` 반환 |
| `submit(Callable<V>)` | 결과를 반환하는 작업 제출 후 `Future<V>` 반환 |
| `invokeAll(tasks)` | 여러 작업을 제출하고 모두 끝날 때까지 대기 |
| `shutdown()` | 새 작업은 받지 않고, 제출된 작업을 마치면 종료 |
| `shutdownNow()` | 실행 중인 작업에 interrupt를 보내고, 대기 중인 작업 반환 |
| `awaitTermination()` | Executor가 종료될 때까지 지정 시간 대기 |

### Thread 직접 사용 vs ExecutorService

| 방식 | 특징 | 적합한 상황 |
|---|---|---|
| `new Thread(task).start()` | 작업마다 스레드를 직접 생성 | 매우 단순한 일회성 예제 |
| `ExecutorService` | 스레드 풀과 작업 큐로 실행 관리 | 여러 작업 처리, 서버 요청 처리, 배치 작업 |
| `ScheduledExecutorService` | 시간 기반 실행 관리 | 주기적 점검, 타임아웃, 예약 작업 |

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `ExecutorService` 기본 - fixed thread pool, `execute()`, 종료 처리 |
| `App2` | `Callable` + `Future` - 작업 결과 받기 |
| `App3` | `invokeAll()`과 `CompletionService` - 여러 결과 수집 방식 비교 |
| `App4` | `ScheduledExecutorService` - 지연 실행, 반복 실행, 취소 |

## App - ExecutorService 기본

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam06.App
  ```

## App2 - Callable + Future

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam06.App2
  ```

## App3 - invokeAll / CompletionService

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam06.App3
  ```

## App4 - ScheduledExecutorService

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam06.App4
  ```
