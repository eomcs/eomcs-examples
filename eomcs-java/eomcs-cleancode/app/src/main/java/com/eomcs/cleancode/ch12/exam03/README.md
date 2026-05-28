# 단순 설계 규칙 2–4: 리팩토링 (Simple Design Rules 2–4: Refactoring)

> **모든 테스트를 통과한 뒤에는 리팩토링을 통해 중복을 제거하고, 의도를 드러내며, 불필요한 구조를 줄인다**

Clean Code: 

> 단순 설계 규칙을 바탕으로, 테스트가 통과하면 그다음 단계는 리팩토링이다. 특히 핵심은 중복 제거, 표현력 향상, 클래스와 메서드 최소화다.

## 예제 1: 중복을 제거하라

```java
// Bad
public class OrderService {

    public int calculateOrderTotal(Order order) {
        int total = 0;

        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }

    public int calculateCartTotal(Cart cart) {
        int total = 0;

        for (Item item : cart.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }
}
```

- item.getPrice() * item.getQuantity()가 반복된다
- 합계 계산 로직도 반복된다
- 가격 계산 규칙이 바뀌면 여러 곳을 수정해야 한다

```java
// Good
public class OrderService {

    public int calculateOrderTotal(Order order) {
        return calculateTotal(order.getItems());
    }

    public int calculateCartTotal(Cart cart) {
        return calculateTotal(cart.getItems());
    }

    private int calculateTotal(List<Item> items) {
        return items.stream()
                .mapToInt(Item::totalPrice)
                .sum();
    }
}
```

```java
public class Item {

    private final int price;
    private final int quantity;

    public Item(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public int totalPrice() {
        return price * quantity;
    }
}
```

- 중복 계산식이 Item.totalPrice()로 이동한다
- 합계 계산은 calculateTotal()로 모인다
- 중복 제거 과정에서 새로운 개념이 드러난다

## 예제 2: 의도를 표현하라

```java
// Bad
public class UserService {

    public boolean canLogin(User user) {
        return user.isActive()
                && !user.isLocked()
                && user.getLoginFailCount() < 5;
    }
}
```

- 조건은 맞지만 의미를 해석해야 한다
- “로그인 가능 조건”이라는 도메인 개념이 코드에 없다

```java
// Good
public class UserService {

    public boolean canLogin(User user) {
        return user.isLoginAllowed();
    }
}
```

```java
public class User {

    private boolean active;
    private boolean locked;
    private int loginFailCount;

    public boolean isLoginAllowed() {
        return isActive()
                && isNotLocked()
                && hasAcceptableLoginFailures();
    }

    private boolean isNotLocked() {
        return !locked;
    }

    private boolean hasAcceptableLoginFailures() {
        return loginFailCount < 5;
    }

    public boolean isActive() {
        return active;
    }
}
```

- 조건식이 도메인 언어로 바뀐다
- isLoginAllowed()가 의도를 드러낸다
- 읽는 사람이 구현보다 의미를 먼저 이해한다

## 예제 3: 클래스와 메서드를 최소화하라

```java
// Bad - 과도한 구조
public interface PricePolicy {
    int calculate(Item item);
}
```

```java
public class DefaultPricePolicy implements PricePolicy {

    @Override
    public int calculate(Item item) {
        return item.getPrice() * item.getQuantity();
    }
}
```

```java
public class PricePolicyFactory {

    public PricePolicy create() {
        return new DefaultPricePolicy();
    }
}
```

- 구현이 하나뿐인데 인터페이스와 팩토리가 생겼다
- 실제 변화 가능성보다 구조가 앞서 있다
- 단순한 계산을 이해하기 위해 파일을 여러 개 봐야 한다

```java
// Good
public class Item {

    private final int price;
    private final int quantity;

    public int totalPrice() {
        return price * quantity;
    }
}
```

- 필요한 구조만 남긴다
- 불필요한 인터페이스, 팩토리, 계층을 만들지 않는다
- 단순한 문제는 단순하게 해결한다

## 예제 4: 리팩토링의 순서

```java
// 1단계: 테스트가 통과하는 코드
public int totalPrice(Order order) {
    int total = 0;

    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }

    return total;
}
```

```java
// 2단계: 중복 또는 숨은 개념 발견
public int totalPrice(Order order) {
    return order.getItems().stream()
            .mapToInt(item -> item.getPrice() * item.getQuantity())
            .sum();
}
```

```java
// 3단계: 의도를 드러내는 메서드 추출
public int totalPrice(Order order) {
    return order.getItems().stream()
            .mapToInt(Item::totalPrice)
            .sum();
}
```

```java
public class Item {

    public int totalPrice() {
        return price * quantity;
    }
}
```

- 테스트 통과 상태를 유지한다
- 중복을 제거한다
- 이름을 통해 의도를 드러낸다
- 필요 없는 구조는 추가하지 않는다

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 코드      | 좋은 코드       |
| ---- | ---------- | ----------- |
| 중복   | 계산식 반복     | 공통 개념 추출    |
| 표현력  | 구현이 직접 노출  | 의도를 이름으로 표현 |
| 구조   | 미리 과도하게 만듦 | 필요한 만큼만 만듦  |
| 리팩토링 | 테스트 없이 위험  | 테스트 기반으로 안전 |
| 설계   | 복잡해짐       | 점진적으로 깨끗해짐  |

## 핵심 원칙

> “모든 테스트를 통과하는 코드”가 좋은 설계의 출발점이며, 그 위에서만 깨끗한 설계가 만들어진다

**피해야 할 것:**

- 테스트가 통과하지 않는데 리팩토링하는 것
- 중복을 그대로 두는 것
- 조건식과 계산식의 의도를 숨기는 것
- 필요하지 않은 인터페이스와 계층을 미리 만드는 것

**지켜야 할 것:**

- 테스트 통과 후 리팩토링한다
- 중복을 제거한다
- 코드가 의도를 표현하게 한다
- 클래스와 메서드는 필요한 만큼만 유지한다
- 설계는 예측이 아니라 반복적인 개선으로 만든다

