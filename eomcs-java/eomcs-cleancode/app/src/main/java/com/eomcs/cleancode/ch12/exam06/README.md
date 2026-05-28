# 클래스와 메서드 수를 최소로 줄여라 (Minimal Classes and Methods)

> **설계를 깨끗하게 만들기 위해 클래스와 메서드를 만들되, 불필요하게 많이 만들지는 마라**

- 중복 제거와 표현력 향상을 하다 보면 클래스와 메서드가 늘어난다
- 하지만 과도한 분리는 오히려 이해를 어렵게 만든다
- “작게 나누기”도 목적이 아니라 수단이다
- 최종 목표는 단순하고 읽기 쉬운 설계다

**Clean Code:**

- 단순 설계 규칙의 마지막으로 클래스와 메서드 수를 최소화하라. 
- **단, 이 규칙은 테스트 통과, 중복 제거, 표현력 향상보다 우선하지 않는다.**

## 예제 1

```java
// Bad - 과도한 인터페이스
public interface UserNameValidator {
    boolean validate(String name);
}
```

```java
public class DefaultUserNameValidator implements UserNameValidator {

    @Override
    public boolean validate(String name) {
        return name != null && name.length() >= 2;
    }
}
```

```java
public class UserNameValidatorFactory {

    public UserNameValidator create() {
        return new DefaultUserNameValidator();
    }
}
```

```java
public class UserService {

    private final UserNameValidator validator;

    public UserService(UserNameValidatorFactory factory) {
        this.validator = factory.create();
    }

    public void register(String name) {
        if (!validator.validate(name)) {
            throw new IllegalArgumentException("invalid name");
        }
    }
}
```

- 구현이 하나뿐인데 인터페이스가 있다
- 팩토리까지 추가되어 구조가 불필요하게 커졌다
- 단순한 이름 검증을 이해하려고 여러 클래스를 봐야 한다
- “확장 가능성”을 미리 예측해서 너무 복잡하게 만든다

```java
// Good
public class UserService {

    public void register(String name) {
        validateUserName(name);
    }

    private void validateUserName(String name) {
        if (name == null || name.length() < 2) {
            throw new IllegalArgumentException("invalid name");
        }
    }
}
```

- 지금 필요한 구조만 남긴다
- 클래스 수가 줄어든다
- 읽는 흐름이 단순해진다
- 나중에 검증 정책이 복잡해질 때 분리해도 늦지 않다

## 예제 2

```java
// Bad - 너무 잘게 쪼갠 메서드
public class PriceCalculator {

    public int calculate(Item item) {
        int price = getPrice(item);
        int quantity = getQuantity(item);
        return multiply(price, quantity);
    }

    private int getPrice(Item item) {
        return item.getPrice();
    }

    private int getQuantity(Item item) {
        return item.getQuantity();
    }

    private int multiply(int price, int quantity) {
        return price * quantity;
    }
}
```

- 메서드는 많지만 의미 있는 추상화가 아니다
- getPrice(), getQuantity(), multiply()는 코드 자체보다 더 설명적이지 않다
- 읽는 사람이 메서드를 계속 따라가야 한다

```java
// Good
public class PriceCalculator {

    public int calculate(Item item) {
        return item.getPrice() * item.getQuantity();
    }
}
```

또는 더 도메인 중심으로는:

```java
public class Item {

    private final int price;
    private final int quantity;

    public int totalPrice() {
        return price * quantity;
    }
}
```

- 의미 없는 메서드 분리를 제거한다
- totalPrice()처럼 도메인 의미가 있을 때만 메서드로 뽑는다
- 단순한 코드는 단순하게 둔다

## 예제 3

```java
// Bad - 불필요한 계층 구조
public interface OrderProcessor {
    void process(Order order);
}
```

```java
public abstract class AbstractOrderProcessor implements OrderProcessor {

    @Override
    public void process(Order order) {
        validate(order);
        doProcess(order);
    }

    protected abstract void validate(Order order);
    protected abstract void doProcess(Order order);
}
```

```java
public class DefaultOrderProcessor extends AbstractOrderProcessor {

    @Override
    protected void validate(Order order) {
        if (order.isEmpty()) {
            throw new IllegalArgumentException("empty order");
        }
    }

    @Override
    protected void doProcess(Order order) {
        order.markAsProcessed();
    }
}
```

- 구현이 하나뿐인데 인터페이스, 추상 클래스, 구현 클래스가 있다
- 실제 요구사항보다 구조가 앞서 있다
- 단순한 주문 처리가 프레임워크처럼 변했다

```java
// Good
public class OrderProcessor {

    public void process(Order order) {
        validate(order);
        order.markAsProcessed();
    }

    private void validate(Order order) {
        if (order.isEmpty()) {
            throw new IllegalArgumentException("empty order");
        }
    }
}
```

- 필요한 클래스는 하나로 충분하다
- 흐름이 직접적이다
- 추상화가 실제 필요할 때까지 기다린다

## 예제 4

```java
// Bad - 패턴을 위한 패턴
public interface DiscountStrategy {
    int discount(int price);
}
```

```java
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public int discount(int price) {
        return 0;
    }
}
```

```java
public class DiscountContext {

    private final DiscountStrategy strategy;

    public DiscountContext(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public int apply(int price) {
        return price - strategy.discount(price);
    }
}
```

- 할인 정책이 하나뿐인데 Strategy 패턴을 적용했다
- 구조는 유연해 보이지만 실제 가치는 없다
- 단순한 요구사항에 비해 클래스가 많다

```java
// Good
public class Price {

    private final int amount;

    public int discountedAmount() {
        return amount;
    }
}
```

나중에 할인 정책이 여러 개 생기면 그때 분리한다.

```java
// 필요가 생긴 뒤 도입
public interface DiscountPolicy {
    int discount(int price);
}
```

- 지금은 단순하게 유지한다
- 변화가 실제로 나타날 때 추상화를 도입한다
- 예측 기반 설계를 줄인다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드                 | 좋은 코드          |
| ----- | --------------------- | -------------- |
| 클래스 수 | 미래를 예측해 미리 많음         | 현재 필요한 만큼만     |
| 메서드 수 | 의미 없는 분리              | 의도가 있을 때만 분리   |
| 추상화   | 인터페이스, 팩토리, 추상 클래스 남용 | 실제 변화가 있을 때 도입 |
| 읽기 흐름 | 여러 파일을 따라가야 함         | 한곳에서 이해 가능     |
| 설계    | 복잡해 보임                | 단순하고 직접적       |


## 핵심 원칙

> **좋은 설계는 많은 클래스와 메서드가 아니라, 필요한 개념만 남긴 단순한 구조다.**

**피해야 할 것:**

- 구현이 하나뿐인데 인터페이스를 만드는 것
- 단순한 로직을 여러 메서드로 기계적으로 쪼개는 것
- 패턴을 쓰기 위해 패턴을 적용하는 것
- 미래 변경을 과도하게 예측해 구조를 미리 만드는 것

**지켜야 할 것:**

- 테스트를 통과시킨다
- 중복을 제거한다
- 의도를 표현한다
- 그다음 클래스와 메서드 수를 최소화한다
- 단순한 문제는 단순하게 해결한다