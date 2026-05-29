# 동시성 방어 원칙 (Concurrency Defense Principles)

> **동시성의 복잡성은 ‘책임 분리’와 ‘데이터 관리 방식’으로 줄일 수 있다**

동시성 문제를 줄이기 위한 핵심 전략:

- SRP (Single Responsibility Principle)
- 데이터 범위 제한
- 데이터 복사 사용
- 스레드 독립성 유지

## 예제 1: Single Responsibility Principle (SRP)

> **동시성 관련 코드는 일반 비즈니스 로직과 분리해야 한다**

- 동시성은 그 자체로 매우 복잡하다
- 비즈니스 로직 + 스레드 관리가 섞이면 이해 불가능해진다
- 동시성 버그는 매우 찾기 어렵다

```java
// Bad
public class OrderService {

    public void process(Order order) {
        new Thread(() -> {
            if (order.isValid()) {
                save(order);
            }
        }).start();
    }

    private void save(Order order) {
        System.out.println("save order");
    }
}
```

- 비즈니스 로직 + 스레드 생성이 섞여 있다
- 테스트하기 어렵다
- 동시성 정책 변경 시 서비스 코드 수정 필요

```java
// Good
public class OrderService {

    public void process(Order order) {
        if (order.isValid()) {
            save(order);
        }
    }

    private void save(Order order) {
        System.out.println("save order");
    }
}
```

```java
public class OrderProcessor {

    private final Executor executor;
    private final OrderService orderService;

    public OrderProcessor(Executor executor, OrderService orderService) {
        this.executor = executor;
        this.orderService = orderService;
    }

    public void process(Order order) {
        executor.execute(() -> orderService.process(order));
    }
}
```

- 비즈니스 로직은 OrderService
- 동시성은 OrderProcessor
- 책임이 명확히 분리됨

## 예제 2: Corollary: Limit the Scope of Data

> **공유 데이터의 범위를 최소화하라**

- 공유 데이터가 많을수록 동기화 필요 증가
- 동기화는 복잡성과 성능 비용 증가
- 버그 가능성 증가

```java
// Bad
public class UserService {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public User find(int index) {
        return users.get(index);
    }
}
```

- users가 여러 스레드에서 공유됨
- 동기화 필요: *`synchronized` 키워드로 보호*
- **공유 자료를 수정하는 위치가 많을수록 버그 가능성 증가**
    - 보호할 임계영역을 빼먹는다. 그래서 공유 자료를 수정하는 모든 코드를 망가뜨린다.
    - 모든 임계영역을 올바로 보호했는지 확인하느라 똑같은 노력과 수고를 반복한다.
    - 그렇지 않아도 찾아내기 어려운 버그가 더욱 찾기 어려워진다.

```java
// Good
public class UserService {

    public void process(List<User> users) {
        List<User> localUsers = new ArrayList<>(users);

        for (User user : localUsers) {
            handle(user);
        }
    }

    private void handle(User user) {
        System.out.println(user.getName());
    }
}
```

- 공유 상태 제거
- **메서드 내부 지역 변수로 제한**
- 동기화 필요 없음

## 예제 3: Corollary: Use Copies of Data

> **공유하지 말고 복사해서 사용하라**

- 공유 대신 복사를 하면 동기화가 필요 없다
- 코드가 단순해진다
- 동시성 버그를 근본적으로 제거

```java
// Bad
public class Statistics {

    private List<Integer> numbers;

   public Statistics(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int sum() {
        return numbers.stream().mapToInt(i -> i).sum();
    }
}
```

- numbers가 다른 스레드에서 변경될 수 있음
- 결과가 불안정

```java
// Good
public class Statistics {

    private final List<Integer> numbers;

    public Statistics(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers); // 복사
    }

    public int sum() {
        return numbers.stream().mapToInt(i -> i).sum();
    }
}
```

- 생성 시 복사
- 내부 상태 보호
- 동기화 필요 없음
- **복사는 메모리를 쓰지만, 동기화보다 훨씬 단순하고 안전하다**

## 예제 4: Corollary: Threads Should Be as Independent as Possible

> **스레드는 가능한 한 서로 영향을 주지 않도록 설계하라**

- 스레드 간 상호작용이 많을수록 복잡성 증가
- 공유 데이터 → 동기화 → 버그 → 성능 저하
- 독립적인 스레드는 훨씬 안전

```java
// Bad
public class TaskProcessor {

    private int sharedCounter = 0;

    public void process() {
        new Thread(() -> sharedCounter++).start();
        new Thread(() -> sharedCounter++).start();
    }
}
```

- 공유 상태 사용
- **경쟁 조건 발생**

```java
// Good
public class TaskProcessor {

    public void process() {
        new Thread(() -> doTask()).start();
        new Thread(() -> doTask()).start();
    }

    private void doTask() {
        int localCounter = 0;
        localCounter++;
    }
}
```

- 각 스레드가 독립적인 상태 사용
- 공유 상태 없음
- 동기화 필요 없음

## 예제 5: 더 현실적인 구조

> **동시성 버그는 재현하기 어렵다**

```java
// Good
public class FileProcessor {

    public void processFiles(List<File> files) {
        for (File file : files) {
            new Thread(() -> processFile(file)).start();
        }
    }

    private void processFile(File file) {
        String content = read(file);
        String result = transform(content);
        save(result);
    }
}
```

- 각 스레드는 하나의 파일만 처리
- **공유 상태 없음**
- 완전히 독립적인 작업 단위

## 동시성 복잡성을 줄이는 4가지 전략

| 원칙        | 의미              | 효과     |
| --------- | --------------- | ------ |
| SRP       | 동시성과 비즈니스 로직 분리 | 이해도 증가 |
| 데이터 범위 제한 | 공유 데이터 최소화      | 동기화 감소 |
| 데이터 복사    | 공유 대신 복사        | 안정성 증가 |
| 스레드 독립성   | 스레드 간 영향 제거     | 버그 감소  |

## 핵심 원칙

> **동시성 문제를 해결하는 가장 강력한 방법은 “공유를 줄이고, 책임을 분리하는 것**

**피해야 할 것:**

- 비즈니스 로직과 스레드 코드를 섞는 것
- 공유 상태를 무분별하게 사용하는 것
- 동기화를 “나중에” 해결하려는 것
- 스레드 간 의존성을 만드는 것

**지켜야 할 것:**

- 동시성 코드를 별도로 분리한다 (SRP)
- 공유 데이터를 최소화한다
- 가능하면 복사해서 사용한다
- 스레드를 최대한 독립적으로 만든다