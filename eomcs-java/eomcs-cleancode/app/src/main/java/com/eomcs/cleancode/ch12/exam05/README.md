# 표현하라 (Expressive)

> **코드는 동작만 맞으면 끝이 아니라, 읽는 사람이 의도를 쉽게 이해할 수 있어야 한다**

- 대부분의 시간은 코드를 쓰는 것보다 읽는 데 쓰인다
- 의도가 숨겨진 코드는 수정하기 어렵다
- 이름, 작은 함수, 표준 용어, 테스트가 표현력을 높인다
- 표현력 있는 코드는 설계 의도를 드러낸다

**Clean Code:**

- 좋은 설계를 위해 테스트를 통과한 뒤 리팩토링하면서 중복을 제거하고, 
- **코드가 의도를 명확히 표현하도록 만들어야 한다.**

## 예제 1

```java
// Bad
if (user.getAge() >= 19 && user.getStatus().equals("ACTIVE")) {
    discount = price * 10 / 100;
}
```

- 조건의 의미를 직접 해석해야 한다
- 19, "ACTIVE"가 무엇을 뜻하는지 드러나지 않는다
- 할인 조건이라는 도메인 의도가 숨겨져 있다

```java
// Good
if (user.isAdultActiveMember()) {
    discount = price * ADULT_MEMBER_DISCOUNT_RATE / 100;
}
```

```java
public class User {

    private static final int ADULT_AGE = 19;

    public boolean isAdultActiveMember() {
        return age >= ADULT_AGE && isActive();
    }

    private boolean isActive() {
        return status.equals("ACTIVE");
    }
}
```

- 조건의 의도가 이름으로 드러난다
- 숫자와 문자열의 의미가 명확해진다
- 읽는 사람이 구현보다 비즈니스 의미를 먼저 이해한다

## 예제 2

```java
// Bad
public void process(Order order) {
    if (order.getItems().size() == 0) {
        throw new IllegalArgumentException();
    }

    int sum = 0;
    for (Item i : order.getItems()) {
        sum += i.getPrice() * i.getQuantity();
    }

    order.setTotal(sum);
    order.setStatus("PAID");
}
```

- process()라는 이름이 모호하다
- 주문 검증, 금액 계산, 상태 변경이 섞여 있다
- sum, i, "PAID"의 의미가 약하다

```java
// Good
public void pay(Order order) {
    validateOrderHasItems(order);

    int totalPrice = calculateTotalPrice(order);

    order.markAsPaid(totalPrice);
}

private void validateOrderHasItems(Order order) {
    if (order.isEmpty()) {
        throw new IllegalArgumentException("order must have items");
    }
}

private int calculateTotalPrice(Order order) {
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

```java
public class Order {

    public void markAsPaid(int totalPrice) {
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PAID;
    }
}
```

- pay()가 의도를 드러낸다
- 각 단계가 이름으로 설명된다
- Item.totalPrice(), Order.markAsPaid()가 도메인 언어처럼 읽힌다

## 예제 3

```java
// Bad
public boolean check(User user) {
    return user.getLoginFailCount() < 5
            && !user.isLocked()
            && user.isEmailVerified();
}
```

- 무엇을 확인하는지 check()만으로 알 수 없다
- 조건의 결과가 어떤 의미인지 호출부에서 추측해야 한다

```java
// Good
public boolean canLogin(User user) {
    return user.hasAcceptableLoginFailures()
            && user.isNotLocked()
            && user.isEmailVerified();
}
```

- canLogin()이 목적을 말한다
- 세부 조건도 이름으로 의도를 표현한다
- 코드가 설명문처럼 읽힌다

## 예제 4

```java
// Bad
@Test
void test1() {
    User user = new User("kim@example.com", 20, true);

    assertTrue(user.getAge() >= 19 && user.isEmailVerified());
}
```

- 테스트 이름이 의미 없다
- 테스트가 무엇을 검증하는지 바로 알기 어렵다
- 구현 조건을 그대로 테스트한다

```java
// Good
@Test
void adultVerifiedUserCanRegister() {
    User user = new AdultVerifiedUserBuilder()
            .withEmail("kim@example.com")
            .build();

    assertTrue(user.canRegister());
}
```

- 테스트 이름이 요구사항을 설명한다
- 테스트 코드도 도메인 언어처럼 읽힌다
- 구현 조건이 아니라 행위를 검증한다

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드                     | 좋은 코드                           |
| --- | ------------------------- | ------------------------------- |
| 이름  | `process`, `check`, `sum` | `pay`, `canLogin`, `totalPrice` |
| 조건식 | 구현 조건 노출                  | 도메인 개념으로 추상화                    |
| 상수  | 매직 넘버, 문자열                | 의미 있는 이름                        |
| 함수  | 여러 일을 한 번에 처리             | 의도별로 작은 함수 분리                   |
| 테스트 | 구현을 확인                    | 행위를 설명                          |

## 핵심 원칙

**피해야 할 것:**

- 모호한 이름을 사용하는 것
- 조건식과 계산식을 그대로 노출하는 것
- 매직 넘버와 문자열을 흩뿌리는 것
- 테스트 이름을 test1, testProcess처럼 짓는 것

**지켜야 할 것:**

- 이름으로 의도를 표현한다
- 작은 함수로 의미 있는 단계를 만든다
- 도메인 용어를 코드에 반영한다
- 표준 명명법과 패턴 이름을 적절히 사용한다
- 테스트도 읽기 쉬운 설명문처럼 작성한다