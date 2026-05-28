# 클래스 체계 (Class Organization)

> **클래스는 위에서 아래로 읽히는 이야기처럼 구성하고, 내부 구현은 가능한 한 숨겨라**

👉 이유:

- 클래스의 읽는 순서가 일정해야 이해하기 쉽다
- 변수와 공개 메서드, 내부 유틸리티 메서드의 위치가 예측 가능해야 한다
- 필드와 보조 메서드는 기본적으로 private이어야 한다
- 테스트 때문에 접근 범위를 넓힐 수는 있지만, 그것은 최후의 수단이다

👉 핵심 의도

- 클래스는 보통 다음 순서로 배치한다.
    - public static final 상수
    - private static 변수
    - private 인스턴스 변수
    - public 메서드
    - 해당 public 메서드가 호출하는 private 보조 메서드
- 클래스 내부 구현은 외부에 노출하지 않는다.
- 테스트를 위해 protected나 package-private으로 열기 전에, 먼저 private을 유지할 방법을 찾는다.

## 예제 1

```java
// Bad
public class OrderService {

    public OrderRepository repository;
    public DiscountPolicy discountPolicy;

    private static final int FREE_SHIPPING_THRESHOLD = 50000;

    public void applyDiscount(Order order) {
        int discount = discountPolicy.calculate(order);
        order.applyDiscount(discount);
    }

    public static final int MAX_ORDER_AMOUNT = 1_000_000;

    public void save(Order order) {
        repository.save(order);
    }
}
```

- 상수, 필드, 메서드의 위치가 뒤섞여 있다
- repository, discountPolicy가 public으로 노출되어 외부에서 마음대로 바꿀 수 있다
- 클래스의 구조를 한눈에 파악하기 어렵다
- 내부 구현이 외부 코드에 새어 나간다

```java
// Good
public class OrderService {

    public static final int MAX_ORDER_AMOUNT = 1_000_000;

    private static final int FREE_SHIPPING_THRESHOLD = 50_000;

    private final OrderRepository repository;
    private final DiscountPolicy discountPolicy;

    public OrderService(OrderRepository repository, DiscountPolicy discountPolicy) {
        this.repository = repository;
        this.discountPolicy = discountPolicy;
    }

    public void save(Order order) {
        validate(order);
        applyDiscount(order);
        repository.save(order);
    }

    private void validate(Order order) {
        if (order.totalAmount() > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("order amount is too large");
        }
    }

    private void applyDiscount(Order order) {
        int discount = discountPolicy.calculate(order);
        order.applyDiscount(discount);
    }
}
```

- 클래스 구성 순서가 예측 가능하다
- 외부에는 save()라는 public 기능만 보인다
- validate(), applyDiscount()는 내부 처리이므로 private으로 숨긴다
- public 메서드 바로 아래에 관련 private 메서드를 두어 위에서 아래로 읽힌다

## 예제 2

```java
// Bad
public class PaymentProcessor {

    public PaymentGateway gateway;

    public boolean testMode = false;

    public void pay(Order order) {
        if (testMode) {
            System.out.println("test payment");
            return;
        }

        gateway.request(order.totalAmount());
    }
}
```

```java
// Bad - 외부 코드
processor.gateway = new FakePaymentGateway();
processor.testMode = true;
processor.pay(order);
```

- 외부 코드가 객체 내부 상태를 직접 변경한다
- PaymentProcessor의 동작이 어디서 바뀌는지 추적하기 어렵다
- 캡슐화가 깨져 테스트 코드와 운영 코드가 내부 구현에 의존한다

```java
// Good
public class PaymentProcessor {

    private final PaymentGateway gateway;

    public PaymentProcessor(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void pay(Order order) {
        gateway.request(order.totalAmount());
    }
}
```

```java
// Good - 테스트 코드
PaymentGateway fakeGateway = new FakePaymentGateway();
PaymentProcessor processor = new PaymentProcessor(fakeGateway);

processor.pay(order);
```

- 필드는 private으로 숨긴다
- 테스트는 필드를 직접 건드리지 않고 생성자 주입으로 대체 객체를 넣는다
- 캡슐화를 깨지 않고도 테스트 가능하다
- 객체의 상태 변경 경로가 명확해진다

## 예제 3

```java
// Bad
public class UserValidator {

    public boolean hasValidEmail(User user) {
        return user.email() != null && user.email().contains("@");
    }

    protected boolean isBlockedDomain(String email) {
        return email.endsWith("@spam.com");
    }

    public boolean validate(User user) {
        return hasValidEmail(user) && !isBlockedDomain(user.email());
    }
}
```

- isBlockedDomain()이 테스트 때문에 protected로 열려 있다
- 외부 하위 클래스가 내부 판단 로직에 의존할 수 있다
- 단순 보조 메서드가 클래스의 확장 포인트처럼 보인다

```java
// Good
public class UserValidator {

    public boolean validate(User user) {
        return hasValidEmail(user) && !isBlockedDomain(user.email());
    }

    private boolean hasValidEmail(User user) {
        return user.email() != null && user.email().contains("@");
    }

    private boolean isBlockedDomain(String email) {
        return email.endsWith("@spam.com");
    }
}
```

```java
// Good - 테스트는 public 동작을 검증
@Test
void rejectsBlockedDomain() {
    UserValidator validator = new UserValidator();

    boolean valid = validator.validate(new User("kim@spam.com"));

    assertFalse(valid);
}
```

- private 메서드를 직접 테스트하지 않는다
- public 메서드의 결과로 내부 동작을 검증한다
- 캡슐화를 유지한다
- 테스트가 구현 세부사항이 아니라 클래스의 행위에 집중한다

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드        | 좋은 코드                                 |
| ------ | ------------ | ------------------------------------- |
| 필드     | public 노출    | private 숨김                            |
| 클래스 순서 | 뒤섞임          | 상수 → 변수 → public 메서드 → private 보조 메서드 |
| 테스트    | 내부 메서드 직접 호출 | public 행위 검증                          |
| 캡슐화    | 쉽게 깨짐        | 최후의 수단으로만 완화                          |
| 읽기 흐름  | 여기저기 이동      | 위에서 아래로 읽힘                            |


## 핵심 원칙

피해야 할 것:

- public 인스턴스 변수를 두는 것
- 테스트를 이유로 무조건 protected로 여는 것
- private 보조 메서드를 클래스 아래쪽에 무질서하게 흩어 놓는 것
- 클래스 내부 구현을 외부 코드가 알게 만드는 것

지켜야 할 것:

- 변수와 유틸리티 메서드는 기본적으로 private으로 둔다
- public 메서드 뒤에 관련 private 메서드를 배치한다
- 클래스가 신문 기사처럼 위에서 아래로 읽히게 한다
- 테스트는 내부 구현보다 public 행위를 검증한다
- 캡슐화를 완화하는 것은 마지막 선택으로 둔다