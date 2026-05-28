# 확장 (Scaling Up)

> **처음부터 거대한 구조를 만들지 말고, 관심사를 분리하면서 점진적으로 확장하라**

- 시스템은 처음부터 완성된 형태로 커지지 않는다
- 단순한 구조에서 시작해 점진적으로 확장해야 한다
- 하지만 관심사가 섞이면 확장할수록 코드가 복잡해진다
- 특히 트랜잭션, 보안, 로깅, 영속성 같은 기능은 여러 객체에 반복적으로 퍼진다

Clean Code:

> 시스템을 확장하려면 관심사를 적절히 분리해야 하며, 특히 영속성·보안·트랜잭션 같은 기능은 도메인 객체의 자연스러운 경계를 가로지르는 횡단 관심사 이다.

- 비즈니스 로직은 비즈니스 로직대로 둔다
- 공통 기술 관심사는 별도로 분리한다
- 여러 클래스에 반복되는 코드를 흩뿌리지 않는다
- 시스템이 커질수록 “기능 추가”보다 “관심사 분리”가 더 중요해진다

## 예제 1

```java
// Bad
public class OrderService {

    private final OrderRepository repository;
    private final Logger logger;

    public OrderService(OrderRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    public void place(Order order) {
        logger.info("start place order");

        Transaction transaction = TransactionManager.begin();

        try {
            repository.save(order);
            transaction.commit();

            logger.info("order placed");
        } catch (Exception e) {
            transaction.rollback();
            logger.error("order failed", e);
            throw e;
        }
    }
}
```

- 주문 처리 로직에 로깅, 트랜잭션 처리가 섞여 있다
- 다른 서비스에도 같은 코드가 반복된다
- 트랜잭션 정책이 바뀌면 여러 서비스 클래스를 수정해야 한다
- 핵심 비즈니스 로직인 repository.save(order)가 부가 코드에 묻힌다

```java
// Good
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void place(Order order) {
        repository.save(order);
    }
}
```

```java
public class TransactionalOrderService {

    private final OrderService orderService;
    private final TransactionManager transactionManager;
    private final Logger logger;

    public TransactionalOrderService(
            OrderService orderService,
            TransactionManager transactionManager,
            Logger logger
    ) {
        this.orderService = orderService;
        this.transactionManager = transactionManager;
        this.logger = logger;
    }

    public void place(Order order) {
        logger.info("start place order");

        Transaction transaction = transactionManager.begin();

        try {
            orderService.place(order);
            transaction.commit();

            logger.info("order placed");
        } catch (Exception e) {
            transaction.rollback();
            logger.error("order failed", e);
            throw e;
        }
    }
}
```

- OrderService는 주문 처리만 한다
- 트랜잭션과 로깅은 별도 객체가 감싼다
- 비즈니스 로직과 기술 관심사가 분리된다
- 시스템이 커져도 핵심 로직은 단순하게 유지된다

## 예제 2: 횡단 관심사 (Cross-Cutting Concerns)

> **여러 객체를 가로질러 반복되는 기술적 관심사는 한곳에서 다뤄라**

대표적인 횡단 관심사:

- 트랜잭션
- 보안
- 로깅
- 캐싱
- 영속성
- 권한 검사

문제점:

- 이런 관심사는 하나의 도메인 클래스에만 속하지 않는다
- 여러 서비스, 여러 메서드에 반복적으로 등장한다
- 반복되면 누락, 불일치, 중복이 생긴다
- 도메인 로직이 기술 코드에 오염된다

Clean Code:

> EJB2가 트랜잭션, 보안, 일부 영속성 설정을 소스 코드와 분리하려 했다는 점에서는 관심사 분리에 가까웠지만, 실제로는 컨테이너와 강하게 결합되어 테스트와 재사용이 어려웠다.

```java
// Bad
public class PaymentService {

    public void pay(Payment payment) {
        if (!SecurityContext.currentUser().hasRole("PAYMENT")) {
            throw new SecurityException("no permission");
        }

        long start = System.currentTimeMillis();

        try {
            System.out.println("transaction begin");

            System.out.println("pay: " + payment.amount());

            System.out.println("transaction commit");
        } catch (Exception e) {
            System.out.println("transaction rollback");
            throw e;
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("elapsed = " + elapsed);
        }
    }
}
```

- 결제 로직보다 보안, 트랜잭션, 성능 측정 코드가 더 많다
- pay()의 핵심 의도가 흐려진다
- 다른 메서드에도 같은 코드가 복사된다
- 보안 정책이 바뀌면 여러 곳을 수정해야 한다

```java
// Good - 핵심 비즈니스 로직만 남김
public class PaymentService {

    public void pay(Payment payment) {
        System.out.println("pay: " + payment.amount());
    }
}
```

```java
// Good - 보안 관심사 분리
public class SecurityProxy {

    private final PaymentService paymentService;

    public SecurityProxy(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void pay(Payment payment) {
        if (!SecurityContext.currentUser().hasRole("PAYMENT")) {
            throw new SecurityException("no permission");
        }

        paymentService.pay(payment);
    }
}
```

```java
// Good - 트랜잭션 관심사 분리
public class TransactionProxy {

    private final SecurityProxy paymentService;

    public TransactionProxy(SecurityProxy paymentService) {
        this.paymentService = paymentService;
    }

    public void pay(Payment payment) {
        System.out.println("transaction begin");

        try {
            paymentService.pay(payment);
            System.out.println("transaction commit");
        } catch (Exception e) {
            System.out.println("transaction rollback");
            throw e;
        }
    }
}
```

```java
// Good - 사용
PaymentService paymentService = new PaymentService();
SecurityProxy securityProxy = new SecurityProxy(paymentService);
TransactionProxy transactionProxy = new TransactionProxy(securityProxy);

transactionProxy.pay(payment);
```

- 결제 로직은 결제만 담당한다
- 보안은 보안 프록시가 담당한다
- 트랜잭션은 트랜잭션 프록시가 담당한다
- 관심사가 분리되어 확장하기 쉬워진다

## 예제 3

```java
// Bad
public class UserService {

    private final UserRepository repository;
    private final Cache cache;

    public User findById(long id) {
        String key = "user:" + id;

        User cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        User user = repository.findById(id);
        cache.put(key, user);

        return user;
    }
}
```

- 사용자 조회 로직과 캐싱 로직이 섞여 있다
- 다른 조회 메서드에도 캐싱 코드가 반복된다
- 캐시 키 정책이 바뀌면 여러 서비스가 영향을 받는다

```java
// Good
public interface UserReader {
    User findById(long id);
}
```

```java
public class DatabaseUserReader implements UserReader {

    private final UserRepository repository;

    public DatabaseUserReader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(long id) {
        return repository.findById(id);
    }
}
```

```java
public class CachedUserReader implements UserReader {

    private final UserReader userReader;
    private final Cache cache;

    public CachedUserReader(UserReader userReader, Cache cache) {
        this.userReader = userReader;
        this.cache = cache;
    }

    @Override
    public User findById(long id) {
        String key = "user:" + id;

        User cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        User user = userReader.findById(id);
        cache.put(key, user);

        return user;
    }
}
```

```java
UserReader userReader =
        new CachedUserReader(
                new DatabaseUserReader(repository),
                cache
        );

User user = userReader.findById(1L);
```

- DB 조회와 캐싱이 분리된다
- 캐싱을 제거하거나 추가하기 쉽다
- 핵심 조회 로직은 캐시 정책을 모른다
- 횡단 관심사를 별도 객체로 감쌌다

## 나쁜 코드 vs 좋은 코드

| 구분      | 나쁜 코드          | 좋은 코드             |
| ------- | -------------- | ----------------- |
| 비즈니스 로직 | 기술 코드와 섞임      | 핵심 로직만 남김         |
| 트랜잭션    | 각 메서드에 반복      | 프록시, AOP, 설정으로 분리 |
| 보안      | 서비스 내부에서 직접 검사 | 별도 보안 계층에서 처리     |
| 캐싱      | 조회 코드에 섞임      | 데코레이터/프록시로 분리     |
| 확장성     | 기능이 늘수록 코드 오염  | 관심사별로 독립 확장       |

## 핵심 원칙

피해야 할 것:

- 모든 서비스 메서드에 트랜잭션 코드를 직접 넣는 것
- 보안, 로깅, 캐싱 코드를 비즈니스 로직 안에 반복하는 것
- 프레임워크 코드가 도메인 객체를 침범하게 만드는 것
- 기술 관심사 때문에 테스트가 어려워지는 구조

지켜야 할 것:

- 시스템은 점진적으로 확장한다
- 확장하려면 관심사를 분리해야 한다
- 횡단 관심사는 한곳에서 일관되게 처리한다
- 도메인 로직은 도메인 문제만 표현하게 한다
- 프록시, 데코레이터, AOP 같은 방식으로 부가 관심사를 분리한다