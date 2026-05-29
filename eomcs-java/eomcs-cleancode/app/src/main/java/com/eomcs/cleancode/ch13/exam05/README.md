# 실행 모델을 이해하라 (Know Your Execution Models)

> **동시성 코드는 “스레드를 여러 개 만든다”가 아니라, 어떤 실행 모델로 협력하게 할지 이해해야 한다**

- 동시성 애플리케이션의 동작을 나누는 방식이 여러 가지있다.
- 대표적인 모델로는 Producer-Consumer, Readers-Writers, Dining Philosophers가 있다.

## 기본 용어

### Bound Resource (한정된 자원)

> **개수나 크기가 제한된 자원**

예:

- DB 커넥션 풀
- 고정 크기 큐
- 제한된 버퍼
- 제한된 파일 핸들

```java
BlockingQueue<Job> queue = new ArrayBlockingQueue<>(10);
```

- 큐 크기가 10으로 제한됨
- 생산자가 너무 많이 넣으면 기다려야 함
- 소비자가 꺼내야 다시 넣을 수 있음

### Mutual Exclusion (상호 배제)

> **한 번에 하나의 스레드만 공유 자원에 접근하게 하는 것**

예:

```java
public synchronized void withdraw(int amount) {
    balance -= amount;
}
```

- 여러 스레드가 동시에 balance를 수정하지 못하게 막음
- 데이터 깨짐을 방지

### Starvation (기아)

> **특정 스레드가 너무 오래, 또는 영원히 실행 기회를 얻지 못하는 상태**

예:

```java
// 읽기 작업이 계속 들어오면 쓰기 작업이 계속 밀릴 수 있음
```

- Reader가 너무 많으면 Writer가 대기만 할 수 있음
- 시스템은 돌아가지만 특정 작업은 진행되지 않음

### Deadlock (교착 상태)

> **두 개 이상의 스레드가 서로가 가진 자원을 기다리며 멈추는 상태**

예:

```java
// Thread A: lock1 잡고 lock2 기다림
// Thread B: lock2 잡고 lock1 기다림
```

- 둘 다 기다리기만 함
- 프로그램이 멈춤

### Livelock (활성 교착 상태)

> **스레드들이 계속 움직이지만 실제 진전은 없는 상태**

예:

```java
// A: "네가 먼저 가"
// B: "아니, 네가 먼저 가"
// A와 B가 계속 양보만 반복
```

- CPU는 사용함
- 하지만 작업은 완료되지 않음

## 예제 1: Producer-Consumer (생산자-소비자)

> **생산자는 작업을 만들고, 소비자는 작업을 처리한다**

- Producer-Consumer 모델에서 생산자 스레드는 작업을 큐에 넣고, 
- 소비자 스레드는 큐에서 작업을 꺼내 처리한다. 
- 이때 생산자와 소비자 사이의 큐는 Bound Resource이며, 
- **큐가 가득 차면 생산자는 기다리고 큐가 비어 있으면 소비자는 기다린다.**

```java
public class Job {
    private final String name;

    public Job(String name) {
        this.name = name;
    }

    public void execute() {
        System.out.println("execute: " + name);
    }
}
```

```java
public class Producer implements Runnable {

    private final BlockingQueue<Job> queue;

    public Producer(BlockingQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                queue.put(new Job("job-" + i));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

```java
public class Consumer implements Runnable {

    private final BlockingQueue<Job> queue;

    public Consumer(BlockingQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Job job = queue.take();
                job.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

```java
BlockingQueue<Job> queue = new ArrayBlockingQueue<>(10);

new Thread(new Producer(queue)).start();
new Thread(new Consumer(queue)).start();
```

- Producer는 작업을 만든다
- Consumer는 작업을 처리한다
- BlockingQueue가 둘 사이를 조정한다
- 큐가 가득 차면 put()은 기다린다
- 큐가 비면 take()는 기다린다

### Bad

```java
public class BadJobQueue {

    private final Queue<Job> queue = new LinkedList<>();

    public void add(Job job) {
        queue.add(job);
    }

    public Job take() {
        return queue.poll();
    }
}
```

- LinkedList는 thread-safe하지 않다
- 큐가 비었을 때 기다리지 않는다
- 직접 wait() / notify()를 작성해야 할 수 있다

### Good

```java
BlockingQueue<Job> queue = new ArrayBlockingQueue<>(10);
```

- Java 라이브러리가 생산자-소비자 조정을 제공한다
- 직접 동기화 코드를 만들 필요가 줄어든다

## 예제 2: Readers-Writers (읽기-쓰기)

> **많은 스레드는 읽고, 일부 스레드는 쓴다**

- Readers-Writers 모델은 “공유 자원이 주로 읽기용 정보원이고, 가끔 writer가 갱신하는 경우”이다. 
- 처리량을 높이려다 보면 writer가 굶주릴 수 있고, update를 허용하면 reader 처리량이 떨어질 수 있다.

### Bad

```java
public class ProductCatalog {

    private final Map<Long, Product> products = new HashMap<>();

    public Product findById(long id) {
        return products.get(id);
    }

    public void update(Product product) {
        products.put(product.id(), product);
    }
}
```

- 여러 스레드가 동시에 읽고 쓴다
- HashMap은 안전하지 않다
- 읽는 도중 쓰기가 발생하면 문제가 생길 수 있다

### Good

```java
public class ProductCatalog {

    private final Map<Long, Product> products = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Product findById(long id) {
        lock.readLock().lock();
        try {
            return products.get(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void update(Product product) {
        lock.writeLock().lock();
        try {
            products.put(product.id(), product);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

- 여러 reader는 동시에 읽을 수 있다
- writer는 단독으로 쓴다
- 읽기 처리량을 높이면서 쓰기 중 데이터 깨짐을 막는다

**readLock() → readLock():**

- A 스레드: readLock() 획득
- B 스레드: readLock() 획득
- A와 B는 동시에 읽기 가능

**readLock() → writeLock():**

- A 스레드: readLock() 획득
- B 스레드: writeLock() 획득 시도 → A가 readLock을 놓을 때까지 대기
- readLock이 모두 해제되어야 writeLock이 획득 가능

**writeLock() → readLock():**

- A 스레드: writeLock() 획득
- B 스레드: readLock() 획득 시도 → A가 writeLock을 놓을 때까지 대기
- writeLock이 해제되어야 readLock이 획득 가능

**writeLock() → writeLock():**

- A 스레드: writeLock() 획득
- B 스레드: writeLock() 획득 시도 → A가 writeLock을 놓을 때까지 대기
- writeLock이 해제되어야 다음 writeLock이 획득 가능

### synchronized 사용?

```java
public synchronized Product findById(long id) {
    return products.get(id);
}

public synchronized void update(Product product) {
    products.put(product.id(), product);
}
```

- 읽기끼리도 서로 막는다
- 읽기 요청이 많은 시스템에서는 처리량이 낮아질 수 있다
- Readers-Writers 모델은 “읽기는 동시에, 쓰기는 단독으로”라는 균형을 잡는다

## 예제 3: Dining Philosophers (식사하는 철학자들)

> **여러 스레드가 여러 자원을 동시에 필요로 할 때 생기는 교착 문제를 설명하는 모델**

- 철학자들이 원탁에 앉아 있고, 각 철학자는 양쪽 젓가락을 모두 집어야 식사할 수 있다.
- 각 철학자가 왼쪽 젓가락을 먼저 들고 오른쪽 젓가락을 기다리면 모두가 서로 기다리는 Deadlock이 생긴다.

### Bad

```java
public class Philosopher implements Runnable {

    private final Object leftChopstick;
    private final Object rightChopstick;

    public Philosopher(Object leftChopstick, Object rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        synchronized (leftChopstick) {
            synchronized (rightChopstick) {
                eat();
            }
        }
    }

    private void eat() {
        System.out.println("eat");
    }
}
```

```java
Object c1 = new Object();
Object c2 = new Object();
Object c3 = new Object();

new Thread(new Philosopher(c1, c2)).start();
new Thread(new Philosopher(c2, c3)).start();
new Thread(new Philosopher(c3, c1)).start();
```

- 각 스레드가 하나의 자원을 잡고 다른 자원을 기다릴 수 있다
- 서로 기다리면 deadlock 발생 가능
- 자원 획득 순서가 일관되지 않다

### Good: 자원 획득 순서를 통일

```java
public class Chopstick {

    private final int id;

    public Chopstick(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
```

```java
public class SafePhilosopher implements Runnable {

    private final Chopstick first;
    private final Chopstick second;

    public SafePhilosopher(Chopstick left, Chopstick right) {
        if (left.id() < right.id()) {
            this.first = left;
            this.second = right;
        } else {
            this.first = right;
            this.second = left;
        }
    }

    @Override
    public void run() {
        synchronized (first) {
            synchronized (second) {
                eat();
            }
        }
    }

    private void eat() {
        System.out.println("eat");
    }
}
```

- 모든 스레드가 작은 id의 젓가락부터 잡는다
- 순환 대기가 깨진다
- deadlock 가능성이 줄어든다

## 세 모델 비교

| 실행 모델               | 핵심 문제                  | 대표 도구           |
| ------------------- | ---------------------- | --------------- |
| Producer-Consumer   | 생산 속도와 소비 속도 조정        | `BlockingQueue` |
| Readers-Writers     | 읽기 처리량과 쓰기 안전성 균형      | `ReadWriteLock` |
| Dining Philosophers | 여러 자원 획득 시 deadlock 방지 | 락 순서 통일         |



## 핵심 원칙

**피해야 할 것:**

- 실행 모델 없이 스레드만 늘리는 것
- 공유 큐를 직접 구현하는 것
- 읽기와 쓰기를 같은 방식으로 무조건 막는 것
- 여러 락을 아무 순서 없이 획득하는 것

**지켜야 할 것:**

- 작업 흐름이 Producer-Consumer인지 구분한다
- 읽기 많고 쓰기 적은 구조라면 Readers-Writers 모델을 고려한다
- 여러 자원을 동시에 잡아야 한다면 Dining Philosophers 문제를 의식한다
- 표준 라이브러리의 동시성 도구를 먼저 사용한다
- 락 순서와 자원 범위를 명확히 한다

