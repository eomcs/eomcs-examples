# 창발적 설계로 깔끔한 코드를 구현하자 (Getting Clean via Emergent Design)

> **좋은 설계는 처음부터 완성하는 것이 아니라, 단순한 규칙을 지키며 리팩토링할 때 자연스럽게 드러난다**

- 모든 테스트를 실행한다
- 중복을 없앤다
- 프로그래머 의도를 표현한다
- 클래스와 메서드 수를 최소로 줄인다

**Clean Code:**

> 단순 설계 규칙이 SRP, DIP 같은 원칙을 적용하기 쉽게 만들고, 좋은 설계가 자연스럽게 나타나게 한다.

## 예제 1

```java
// Bad
public class OrderService {

    public int calculateTotalPrice(Order order) {
        int total = 0;

        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        if (order.getCoupon() != null) {
            total -= total * order.getCoupon().getDiscountRate() / 100;
        }

        return total;
    }
}
```

- 계산 로직이 한 메서드에 몰려 있다
- 상품 금액 계산과 쿠폰 할인 계산이 섞여 있다
- 테스트는 가능하지만 설계 의도가 잘 드러나지 않는다

```java
// Good
public class OrderService {

    public int calculateTotalPrice(Order order) {
        int itemTotal = calculateItemTotal(order);
        return applyCouponDiscount(itemTotal, order.getCoupon());
    }

    private int calculateItemTotal(Order order) {
        return order.getItems().stream()
                .mapToInt(this::calculateItemPrice)
                .sum();
    }

    private int calculateItemPrice(Item item) {
        return item.getPrice() * item.getQuantity();
    }

    private int applyCouponDiscount(int total, Coupon coupon) {
        if (coupon == null) {
            return total;
        }

        return total - total * coupon.getDiscountRate() / 100;
    }
}
```

- 테스트 통과 상태를 유지하면서 리팩토링한다
- 계산 단계가 이름으로 드러난다
- 중복 가능성이 있는 계산 로직이 분리된다
- 설계가 점진적으로 깨끗해진다

## 예제 2

```java
// Bad
public class UserService {

    public boolean canLogin(User user) {
        if (user.isActive() && !user.isLocked() && user.getLoginFailCount() < 5) {
            return true;
        }

        return false;
    }
}
```

- 조건의 의도를 읽는 사람이 해석해야 한다
- 로그인 가능 조건이 도메인 개념으로 표현되지 않는다

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

- 조건문이 도메인 언어로 바뀐다
- canLogin()의 의도가 명확해진다
- 테스트를 유지한 채 리팩토링하면 이런 구조가 자연스럽게 나온다

## 예제 3

```java
// Bad
public class PriceCalculator {

    public int calculateOrderPrice(Order order) {
        return order.getItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public int calculateCartPrice(Cart cart) {
        return cart.getItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
```

- item.getPrice() * item.getQuantity() 중복
- 주문과 장바구니 계산 방식이 같은데 따로 구현되어 있다
- 계산 규칙이 바뀌면 여러 곳을 수정해야 한다

```java
// Good
public class PriceCalculator {

    public int calculateOrderPrice(Order order) {
        return calculateItemsPrice(order.getItems());
    }

    public int calculateCartPrice(Cart cart) {
        return calculateItemsPrice(cart.getItems());
    }

    private int calculateItemsPrice(List<Item> items) {
        return items.stream()
                .mapToInt(Item::totalPrice)
                .sum();
    }
}
```

```java
public class Item {

    private int price;
    private int quantity;

    public int totalPrice() {
        return price * quantity;
    }
}
```

- 중복을 제거하면서 Item.totalPrice()라는 개념이 드러난다
- 설계가 더 객체지향적으로 이동한다
- 이것이 “창발적 설계”의 핵심 흐름이다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드            | 좋은 코드            |
| ----- | ---------------- | ---------------- |
| 설계 방식 | 처음부터 구조를 예측해서 만듦 | 테스트 후 리팩토링으로 드러냄 |
| 중복    | 계산식, 조건식 반복      | 공통 개념 추출         |
| 표현력   | 구현 중심            | 도메인 개념 중심        |
| 안정성   | 수정 시 위험          | 테스트로 보호          |
| 구조    | 우연히 커짐           | 필요할 때만 나뉨        |

## 핵심 원칙

피해야 할 것:

- 테스트 없이 구조부터 바꾸는 것
- 중복을 방치하는 것
- 조건식과 계산식을 그대로 흩뿌리는 것
- 의미 없는 클래스와 인터페이스를 미리 만드는 것

지켜야 할 것:

- 먼저 테스트를 통과시킨다
- 테스트가 보호하는 상태에서 리팩토링한다
- 중복을 제거하며 숨은 개념을 찾는다
- 코드가 의도를 말하게 만든다
- 필요한 만큼만 클래스와 메서드를 만든다

