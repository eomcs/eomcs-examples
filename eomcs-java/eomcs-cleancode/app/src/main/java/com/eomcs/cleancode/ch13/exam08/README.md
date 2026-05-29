# 올바른 종료는 구현하기 어렵다 (Writing Correct Shut-Down Code Is Hard)

> **동시성 시스템은 “잘 실행되는 것”만큼이나 “잘 멈추는 것”이 어렵다**

- 계속 실행되는 시스템을 만드는 것과, 일정 시간 실행 후 정상적으로 종료되는 시스템을 만드는 것은 다르다. 
- 정상 종료에서 흔한 문제는 deadlock이며, 어떤 스레드가 오지 않는 신호를 기다리다가 영원히 멈출 수 있다.

## 예제 1: 부모 스레드가 자식 스레드를 영원히 기다리는 경우

```java
// Bad
public class Server {

    private final List<Thread> workers = new ArrayList<>();

    public void start() {
        Thread worker = new Thread(() -> {
            while (true) {
                doWork();
            }
        });

        workers.add(worker);
        worker.start();
    }

    public void shutdown() throws InterruptedException {
        for (Thread worker : workers) {
            worker.join(); // worker가 끝나기를 기다림
        }

        releaseResources();
    }

    private void doWork() {
        System.out.println("working");
    }

    private void releaseResources() {
        System.out.println("release resources");
    }
}
```

- worker는 while (true)로 계속 실행된다
- shutdown()은 join()으로 종료를 기다린다
- 하지만 worker는 끝나지 않는다
- 부모 스레드는 영원히 대기한다

```java
// Good
public class Server {

    private final ExecutorService executor =
            Executors.newFixedThreadPool(2);

    public void start() {
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                doWork();
            }
        });
    }

    public void shutdown() throws InterruptedException {
        executor.shutdownNow();

        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("workers did not stop in time");
        }

        releaseResources();
    }

    private void doWork() {
        System.out.println("working");
    }

    private void releaseResources() {
        System.out.println("release resources");
    }
}
```

- 종료 신호를 interrupt로 보낸다
- worker는 interrupt 상태를 확인한다
- awaitTermination()에 timeout을 둔다
- 영원히 기다리지 않는다

## 예제 2: Producer-Consumer 종료 문제

```java
// Bad
public class LogSystem {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void startConsumer() {
        new Thread(() -> {
            try {
                while (true) {
                    String log = queue.take();
                    save(log);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void stopProducer() {
        // producer만 멈춘다고 가정
    }

    private void save(String log) {
        System.out.println("save: " + log);
    }
}
```

- consumer는 queue.take()에서 대기한다
- producer가 먼저 종료되면 더 이상 메시지가 들어오지 않는다
- consumer는 종료 신호를 받지 못하고 계속 기다릴 수 있다
- 부모 스레드가 consumer 종료를 기다리면 시스템이 멈춘다

```java
// Good - Poison Pill 사용
public class LogSystem {

    private static final String POISON_PILL = "__STOP__";

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private Thread consumer;

    public void startConsumer() {
        consumer = new Thread(() -> {
            try {
                while (true) {
                    String log = queue.take();

                    if (POISON_PILL.equals(log)) {
                        break;
                    }

                    save(log);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
    }

    public void log(String message) throws InterruptedException {
        queue.put(message);
    }

    public void shutdown() throws InterruptedException {
        queue.put(POISON_PILL);
        consumer.join(5000);
    }

    private void save(String log) {
        System.out.println("save: " + log);
    }
}
```

- producer가 종료 전에 특별한 종료 메시지를 넣는다
- consumer는 POISON_PILL을 받으면 루프를 종료한다
- join(5000)으로 무한 대기를 방지한다
- 생산자-소비자 종료 프로토콜이 명확해진다

## 예제 3: interrupt를 무시하는 나쁜 종료 코드

```java
// Bad
public class Worker implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                doWork();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 무시
            }
        }
    }

    private void doWork() {
        System.out.println("work");
    }
}
```

- InterruptedException을 잡고 무시한다
- 종료 요청이 와도 계속 실행된다
- shutdown 코드가 정상적으로 동작하지 않는다

```java
// Good
public class Worker implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                doWork();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        cleanup();
    }

    private void doWork() {
        System.out.println("work");
    }

    private void cleanup() {
        System.out.println("cleanup");
    }
}
```

- interrupt를 종료 신호로 사용한다
- InterruptedException 발생 시 interrupt 상태를 복원한다
- 루프가 종료되고 정리 코드가 실행된다

## 나쁜 코드 vs 좋은 코드

| 구분                | 나쁜 코드                     | 좋은 코드                       |
| ----------------- | ------------------------- | --------------------------- |
| 종료 신호             | 없음 또는 불명확                 | 명확한 종료 신호                   |
| 대기 방식             | 무한 `join()`               | timeout 있는 대기               |
| worker 루프         | `while (true)`            | interrupt 또는 종료 플래그 확인      |
| producer-consumer | consumer가 영원히 `take()` 대기 | poison pill 또는 interrupt 사용 |
| 예외 처리             | `InterruptedException` 무시 | interrupt 상태 복원 후 종료        |


## 핵심 원칙

> **동시성 시스템의 종료는 별도의 기능처럼 설계해야 하며, “언젠가 알아서 끝나겠지”라고 두면 deadlock과 무한 대기가 발생한다.**

**피해야 할 것:**

- worker가 끝날 방법 없이 while (true)로 도는 것
- 부모 스레드가 join()으로 무한 대기하는 것
- InterruptedException을 무시하는 것
- producer만 종료하고 consumer 종료 경로를 만들지 않는 것
- 종료 코드를 마지막에 대충 붙이는 것

**지켜야 할 것:**

- 종료 방식을 초기에 설계한다
- 종료 코드를 일찍 구현하고 테스트한다
- 스레드가 종료 신호를 주기적으로 확인하게 한다
- 대기에는 timeout을 둔다
- producer-consumer 구조에서는 consumer를 깨울 종료 메시지나 interrupt를 준비한다

