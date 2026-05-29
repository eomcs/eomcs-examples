# 스레드 코드 테스트하기 (Testing Threaded Code)

> **스레드 코드는 테스트로 “완전한 정확성”을 증명하기 어렵다. 대신 문제가 드러날 가능성을 높이는 테스트를 자주, 다양한 조건에서 실행해야 한다**

- 테스트가 정확성을 보장하지는 못하지만 위험을 줄일 수 있다. 
- 특히 두 개 이상의 스레드가 같은 코드와 공유 데이터를 사용하면 복잡도가 크게 증가하므로, 
- 다양한 설정과 부하에서 자주 실행하고 실패를 절대 무시하지 말라.

## 예제 1: Treat Spurious Failures as Candidate Threading Issues (우연한 실패를 동시성 문제 후보로 보라)

> **“가끔 한 번 실패했지만 다시 돌리니 통과했다”를 무시하지 마라**

```java
// Bad
public class Counter {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int value() {
        return count;
    }
}
```

```java
@Test
void incrementsFromManyThreads() throws Exception {
    Counter counter = new Counter();

    List<Thread> threads = new ArrayList<>();

    for (int i = 0; i < 1000; i++) {
        Thread thread = new Thread(counter::increment);
        threads.add(thread);
        thread.start();
    }

    for (Thread thread : threads) {
        thread.join();
    }

    assertEquals(1000, counter.value());
}
```

- 가끔 1000이 나올 수 있다
- 가끔 997, 998처럼 실패할 수 있다
- 다시 실행해서 통과했다고 무시하면 안 된다
- count++는 원자적 연산이 아니므로 race condition 후보다

```java
// Good
public class Counter {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int value() {
        return count.get();
    }
}
```

- 간헐적 실패는 우연이 아니라 동시성 버그의 신호일 수 있다.

## 예제 2: Get Your Nonthreaded Code Working First (스레드 없는 코드를 먼저 동작시켜라)

> **비즈니스 로직 버그와 동시성 버그를 동시에 잡으려 하지 마라**

```java
// Bad
public class AsyncOrderProcessor {

    public void process(Order order) {
        new Thread(() -> {
            if (order.totalPrice() > 0) {
                order.markProcessed();
            }
        }).start();
    }
}
```

- 주문 처리 로직과 스레드 실행이 섞여 있다
- 테스트가 어렵다
- 실패했을 때 비즈니스 버그인지 동시성 버그인지 구분하기 어렵다

```java
// Good
public class OrderProcessor {

    public void process(Order order) {
        if (order.totalPrice() > 0) {
            order.markProcessed();
        }
    }
}
```

```java
public class AsyncOrderProcessor {

    private final Executor executor;
    private final OrderProcessor processor;

    public AsyncOrderProcessor(Executor executor, OrderProcessor processor) {
        this.executor = executor;
        this.processor = processor;
    }

    public void process(Order order) {
        executor.execute(() -> processor.process(order));
    }
}
```

```java
@Test
void processesValidOrderWithoutThreads() {
    Order order = new Order(10_000);
    OrderProcessor processor = new OrderProcessor();

    processor.process(order);

    assertTrue(order.isProcessed());
}
```

- 먼저 순수 비즈니스 로직을 테스트한다
- 그다음 스레드 실행 정책을 따로 테스트한다
- POJO로 분리하면 동시성 없이 검증 가능하다

## 예제 3: Make Your Threaded Code Pluggable (스레드 코드를 교체 가능하게 만들어라)

> **한 스레드, 여러 스레드, 테스트 더블, 느린 작업 등 다양한 구성으로 실행 가능해야 한다**

```java
// Bad
public class NotificationService {

    public void sendAll(List<Message> messages) {
        for (Message message : messages) {
            new Thread(() -> send(message)).start();
        }
    }

    private void send(Message message) {
        System.out.println("send: " + message.text());
    }
}
```

- 항상 새 스레드를 만든다
- 테스트에서 동기 실행으로 바꾸기 어렵다
- 느린/빠른 실행을 조절하기 어렵다

```java
// Good
public class NotificationService {

    private final Executor executor;
    private final MessageSender sender;

    public NotificationService(Executor executor, MessageSender sender) {
        this.executor = executor;
        this.sender = sender;
    }

    public void sendAll(List<Message> messages) {
        for (Message message : messages) {
            executor.execute(() -> sender.send(message));
        }
    }
}
```

```java
// 테스트에서는 동기 실행
Executor sameThreadExecutor = Runnable::run;

@Test
void sendsMessagesWithoutRealThreads() {
    FakeMessageSender sender = new FakeMessageSender();
    NotificationService service =
            new NotificationService(sameThreadExecutor, sender);

    service.sendAll(List.of(new Message("A"), new Message("B")));

    assertEquals(2, sender.sentCount());
}
```

- 운영에서는 thread pool 사용
- 테스트에서는 즉시 실행 executor 사용
- 동시성 정책을 쉽게 바꿀 수 있다

## 예제 4: Make Your Threaded Code Tunable (스레드 코드를 조정 가능하게 만들어라)

> **스레드 수, 큐 크기, 반복 횟수 등을 쉽게 바꿀 수 있어야 한다**

```java
// Bad
public class ImageResizeService {

    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public void resizeAll(List<Image> images) {
        for (Image image : images) {
            executor.submit(() -> resize(image));
        }
    }
}
```

- 스레드 수가 코드에 박혀 있다
- 환경에 따라 조정하기 어렵다
- 테스트에서 부하를 바꾸기 어렵다

```java
// Good
public class ImageResizeService {

    private final ExecutorService executor;

    public ImageResizeService(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void resizeAll(List<Image> images) {
        for (Image image : images) {
            executor.submit(() -> resize(image));
        }
    }

    private void resize(Image image) {
        System.out.println("resize");
    }
}
```

```java
ImageResizeService service = new ImageResizeService(16);
```

- 로컬에서는 2개
- 테스트에서는 32개
- 운영에서는 설정값 기반으로 조정 가능하다

## 예제 5: Run with More Threads Than Processors (프로세서 수보다 많은 스레드로 실행하라)

> **컨텍스트 스위칭이 자주 일어나야 숨은 동시성 문제가 드러난다**

```java
@Test
void stressTestCounter() throws Exception {
    Counter counter = new Counter();

    int threadCount = Runtime.getRuntime().availableProcessors() * 4;
    int incrementsPerThread = 10_000;

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch done = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
        executor.submit(() -> {
            for (int j = 0; j < incrementsPerThread; j++) {
                counter.increment();
            }
            done.countDown();
        });
    }

    done.await();

    assertEquals(threadCount * incrementsPerThread, counter.value());

    executor.shutdown();
}
```

- CPU 코어보다 많은 스레드를 만든다
- 스레드 전환이 자주 발생한다
- 임계 구역 누락, race condition, deadlock이 드러날 가능성이 커진다

## 예제 6: Run on Different Platforms (다른 플랫폼에서 실행하라)

> **운영체제, JVM, CPU 구조에 따라 스레드 스케줄링이 다르게 동작할 수 있다**

예를 들어 아래 테스트가 내 개발 PC에서는 통과해도 CI 서버, Linux, macOS, Windows에서 다르게 실패할 수 있다.

```java
@Test
void runManyTimes() throws Exception {
    for (int i = 0; i < 10_000; i++) {
        stressTestCounter();
    }
}
```

- 동시성 버그는 타이밍 의존적이다
- 플랫폼마다 스케줄링 타이밍이 다르다
- 가능하면 CI에서 여러 OS/JDK 버전으로 실행한다
- **내 컴퓨터에서 통과했다고 안전한 것이 아니다.**

## 예제 7: Instrument Your Code to Try and Force Failures (실패가 드러나도록 코드를 계측하라)

> **yield, sleep, wait 등을 의도적으로 끼워 넣어 스레드 전환 지점을 늘려라**

### Hand-Coded

```java
// Bad - 실제 버그가 숨어 있을 수 있음
public class UnsafeCounter {

    private int count = 0;

    public void increment() {
        int current = count;
        count = current + 1;
    }

    public int value() {
        return count;
    }
}
```

```java
// 테스트용 계측 버전
public class InstrumentedCounter {

    private int count = 0;

    public void increment() {
        int current = count;

        Thread.yield(); // 일부러 스레드 전환 유도

        count = current + 1;
    }

    public int value() {
        return count;
    }
}
```

- Thread.yield()로 다른 스레드가 끼어들 기회를 만든다
- race condition이 더 잘 드러난다
- 단, 직접 넣은 계측 코드는 테스트용이어야 한다

```java
// 해결
public class SafeCounter {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int value() {
        return count.get();
    }
}
```

### Automated

> **수동으로 yield()를 넣기보다, 계측 도구나 테스트 훅으로 자동 삽입할 수 있게 만든다**

```java
public interface ThreadJiggle {
    void jiggle();
}
```

```java
public class NoJiggle implements ThreadJiggle {
    @Override
    public void jiggle() {
    }
}
```

```java
public class YieldJiggle implements ThreadJiggle {
    @Override
    public void jiggle() {
        Thread.yield();
    }
}
```

```java
public class SleepJiggle implements ThreadJiggle {
    @Override
    public void jiggle() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

```java
public class TestableCounter {

    private int count = 0;
    private final ThreadJiggle jiggle;

    public TestableCounter(ThreadJiggle jiggle) {
        this.jiggle = jiggle;
    }

    public void increment() {
        int current = count;
        jiggle.jiggle();
        count = current + 1;
    }

    public int value() {
        return count;
    }
}
```

```java
@Test
void exposesRaceConditionWithJiggle() throws Exception {
    TestableCounter counter = new TestableCounter(new YieldJiggle());

    int threadCount = 20;
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch done = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
        executor.submit(() -> {
            counter.increment();
            done.countDown();
        });
    }

    done.await();

    assertEquals(threadCount, counter.value());

    executor.shutdown();
}
```

- 테스트에서만 스레드 전환 지점을 강제로 만든다
- NoJiggle, YieldJiggle, SleepJiggle을 바꿔가며 실행한다
- 실패 확률을 높인다
- 운영 코드에 무작위 sleep()을 남기지 않는다

## 나쁜 코드 vs 좋은 코드

| 주제     | 나쁜 접근             | 좋은 접근                      |
| ------ | ----------------- | -------------------------- |
| 간헐적 실패 | 무시                | 동시성 버그 후보로 추적              |
| 기본 로직  | 스레드 안에서만 테스트      | POJO로 먼저 테스트               |
| 실행 방식  | `new Thread()` 고정 | `Executor` 주입              |
| 튜닝     | 스레드 수 하드코딩        | 설정 가능                      |
| 부하 테스트 | 작은 스레드 수          | 코어 수보다 많은 스레드              |
| 플랫폼    | 한 환경만 실행          | 여러 OS/JDK에서 실행             |
| 실패 유도  | 운에 맡김             | `yield`, `sleep`, 계측 도구 활용 |



## 핵심 원칙

> **스레드 코드는 “한 번 통과”가 아니라, 다양한 설정·부하·플랫폼·타이밍에서 반복적으로 흔들어 보아야 한다.**

**피해야 할 것:**

- “다시 돌리니 통과했다”라고 실패를 무시하는 것
- 비즈니스 버그와 동시성 버그를 동시에 디버깅하는 것
- 코드 안에 new Thread()를 고정하는 것
- 스레드 수를 하드코딩하는 것
- 한 플랫폼에서만 테스트하는 것
- 동시성 실패가 자연히 발생하기만 기다리는 것

**지켜야 할 것:**

- 간헐적 실패를 반드시 추적한다
- 스레드 없는 코드를 먼저 완성한다
- 스레드 정책을 교체 가능하게 만든다
- 스레드 수와 부하를 조정 가능하게 만든다
- 코어 수보다 많은 스레드로 스트레스 테스트한다
- 여러 플랫폼에서 실행한다
- 계측으로 실패 가능성을 높인다

