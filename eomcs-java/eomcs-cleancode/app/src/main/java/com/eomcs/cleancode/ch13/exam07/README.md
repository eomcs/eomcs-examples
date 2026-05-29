# 동기화하는 부분을 작게 만들어라 (Keep Synchronized Sections Small)

> **락은 꼭 필요한 코드에만 걸고, 락을 잡은 상태에서 오래 걸리는 일을 하지 마라**

- synchronized가 락을 만들며, 같은 락으로 보호되는 코드는 한 번에 하나의 스레드만 실행할 수 있다. 
- 락은 지연과 오버헤드를 만들기 때문에 남발하면 안 되지만, 동시에 보호가 필요한 임계 구역은 반드시 보호해야 한다. 
- 핵심 권장 사항은 **동기화 구간을 가능한 작게 유지하라** 는 것이다.

**임계 구역(Critical Section)**

> **여러 스레드가 동시에 실행하면 안 되는 코드 구간**

예를 들어 공유 변수 수정은 임계 구역이다.

```java
balance -= amount;
```

이 코드는 여러 스레드가 동시에 실행하면 잔액이 깨질 수 있다.

## 예제 1

```java
// Bad
public class Account {

    private int balance;

    public synchronized void withdraw(int amount) {
        validate(amount);
        writeAuditLog(amount);
        sendNotification(amount);

        balance -= amount;
    }

    private void validate(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    private void writeAuditLog(int amount) {
        System.out.println("audit log");
    }

    private void sendNotification(int amount) {
        System.out.println("send notification");
    }
}
```

- balance -= amount만 실제 공유 상태 변경이다
- 그런데 검증, 로그, 알림까지 모두 락 안에서 실행된다
- 락을 오래 잡는다
- 다른 스레드가 불필요하게 오래 기다린다
- 경쟁(contention)이 증가한다

```java
// Good
public class Account {

    private int balance;

    public void withdraw(int amount) {
        validate(amount);

        synchronized (this) {
            balance -= amount;
        }

        writeAuditLog(amount);
        sendNotification(amount);
    }

    private void validate(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    private void writeAuditLog(int amount) {
        System.out.println("audit log");
    }

    private void sendNotification(int amount) {
        System.out.println("send notification");
    }
}
```

- 공유 상태를 변경하는 부분만 동기화한다
- 로그와 알림은 락 밖에서 실행한다
- 락 보유 시간이 짧아진다
- 처리량이 좋아진다

## 예제 2

```java
// Bad
public class ProductCache {

    private final Map<Long, Product> cache = new HashMap<>();

    public synchronized Product find(long id) {
        Product product = cache.get(id);

        if (product == null) {
            product = loadFromDatabase(id);
            cache.put(id, product);
        }

        return product;
    }

    private Product loadFromDatabase(long id) {
        return new Product(id);
    }
}
```

- DB 조회까지 synchronized 안에서 실행된다
- DB 조회는 오래 걸릴 수 있다
- 한 스레드가 DB를 기다리는 동안 다른 스레드는 캐시 조회조차 못 한다

```java
/// Better
public class ProductCache {

    private final Map<Long, Product> cache = new HashMap<>();

    public Product find(long id) {
        synchronized (this) {
            Product cached = cache.get(id);
            if (cached != null) {
                return cached;
            }
        }

        Product loaded = loadFromDatabase(id);

        synchronized (this) {
            cache.put(id, loaded);
            return loaded;
        }
    }

    private Product loadFromDatabase(long id) {
        return new Product(id);
    }
}
```

- 캐시 조회와 저장만 락으로 보호한다
- 오래 걸리는 DB 조회는 락 밖에서 수행한다
- 다른 스레드가 불필요하게 막히지 않는다

단, 이 코드는 같은 상품을 여러 스레드가 동시에 DB에서 읽어올 수 있다. 이 문제가 중요하다면 ConcurrentHashMap.computeIfAbsent() 같은 더 적절한 도구를 고려한다.

```java
// Good
public class ProductCache {

    private final ConcurrentMap<Long, Product> cache =
            new ConcurrentHashMap<>();

    public Product find(long id) {
        return cache.computeIfAbsent(id, this::loadFromDatabase);
    }

    private Product loadFromDatabase(long id) {
        return new Product(id);
    }
}
```

- 직접 락 범위를 고민하는 코드가 줄어든다
- 표준 동시성 컬렉션이 복합 연산을 처리한다
- 코드 의도가 더 명확하다

## 예제 3

```java
// Bad
public class ReportService {

    private final List<Report> reports = new ArrayList<>();

    public synchronized void generateAndAddReport(User user) {
        Report report = generateReport(user);
        reports.add(report);
    }

    private Report generateReport(User user) {
        // 오래 걸리는 계산이라고 가정
        return new Report(user.id());
    }
}
```

- 리포트 생성은 오래 걸릴 수 있다
- 하지만 generateReport()까지 락 안에서 실행된다
- 실제 공유 자원 접근은 reports.add(report)뿐이다

```java
// Good
public class ReportService {

    private final List<Report> reports = new ArrayList<>();

    public void generateAndAddReport(User user) {
        Report report = generateReport(user);

        synchronized (this) {
            reports.add(report);
        }
    }

    private Report generateReport(User user) {
        return new Report(user.id());
    }
}
```

- 리포트 생성은 락 밖에서 수행한다
- 공유 리스트에 추가하는 순간만 락을 건다
- 동기화 구간이 최소화된다

## 예제 4

```java
// Bad
public class OrderProcessor {

    private final List<Order> processedOrders = new ArrayList<>();

    public synchronized void process(Order order) {
        validate(order);
        charge(order);
        ship(order);

        processedOrders.add(order);
    }
}
```

- 결제, 배송 같은 외부 작업이 락 안에 있다
- 외부 API 호출이 느리면 전체 처리가 막힌다
- 다른 주문도 대기한다

```java
// Good
public class OrderProcessor {

    private final List<Order> processedOrders = new ArrayList<>();

    public void process(Order order) {
        validate(order);
        charge(order);
        ship(order);

        synchronized (this) {
            processedOrders.add(order);
        }
    }
}
```

- 주문 처리 대부분은 락 없이 실행한다
- 공유 컬렉션 갱신만 락으로 보호한다
- 락의 범위가 명확해진다

## 나쁜 코드 vs 좋은 코드

| 구분        | 나쁜 코드         | 좋은 코드        |
| --------- | ------------- | ------------ |
| 락 범위      | 메서드 전체        | 공유 상태 접근 부분만 |
| 오래 걸리는 작업 | 락 안에서 실행      | 락 밖에서 실행     |
| 성능        | 경쟁 증가         | 대기 시간 감소     |
| 의도        | 무엇을 보호하는지 불명확 | 보호 대상이 명확    |
| 위험        | 처리량 저하        | 임계 구역 최소화    |

## 핵심 원칙

**피해야 할 것:**

- 메서드 전체에 무심코 synchronized를 붙이는 것
- DB 호출, 네트워크 호출, 파일 I/O를 락 안에서 실행하는 것
- 공유 상태와 무관한 계산까지 락으로 묶는 것
- 락을 크게 잡아 “안전해 보이게” 만드는 것

**지켜야 할 것:**

- 임계 구역을 먼저 식별한다
- 공유 상태를 읽거나 쓰는 부분만 보호한다
- 락을 잡은 상태에서 오래 걸리는 작업을 하지 않는다
- 가능하면 ConcurrentHashMap, BlockingQueue 같은 표준 동시성 도구를 사용한다
- 동기화 구간은 가능한 작게 유지한다

