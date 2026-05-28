# 깨끗한 테스트 코드 유지하기 (Keeping Tests Clean)

> **테스트 코드도 프로덕션 코드만큼 깨끗하게 유지해야 한다.**

- 테스트 코드는 “한 번 작성하고 버리는 코드”가 아니다. 
- 프로덕션 코드가 변경되면 테스트 코드도 함께 변경된다. 
- 그래서 테스트가 지저분하면 테스트를 고치기 어려워지고, 결국 테스트 자체가 부담이 된다. 
- 지저분한 테스트는 테스트가 없는 것과 같거나 더 나쁘며, 
- 테스트 코드도 프로덕션 코드처럼 생각, 설계, 주의를 들여 깨끗하게 유지해야 한다.

> **깨끗한 테스트는 코드의 유연성, 유지보수성, 재사용성을 가능하게 한다.**

- 테스트가 있으면 변경을 두려워하지 않게 된다. 
- 테스트가 없으면 작은 변경도 버그를 만들 수 있다는 두려움 때문에 리팩터링을 멈추게 되고, 결국 프로덕션 코드가 썩는다. 
- **단위 테스트가 코드의 유연성(flexibility), 유지보수성(maintainability), 재사용성(reusability)을 가능하게 한다.**

## 예제 1

```java
// Bad - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void test() {
        Product product = new Product("BOOK", 10000);
        Customer customer = new Customer("kim@test.com");
        Order order = new Order(customer, product, 2);

        OrderService service = new OrderService(
            new OrderRepository(),
            new PaymentGateway(),
            new NotificationSender()
        );

        service.order(order);

        assertEquals("BOOK", order.getProduct().getName());
        assertEquals(2, order.getQuantity());
        assertEquals(20000, order.getTotalPrice());
        assertEquals(OrderStatus.PAID, order.getStatus());
        assertTrue(order.isNotificationSent());
    }
}
```

- 테스트 이름이 무엇을 검증하는지 말하지 않는다.
- 준비 코드가 길어서 핵심 검증이 잘 보이지 않는다.
- 주문 생성, 결제, 알림까지 여러 개념을 한 테스트에서 검증한다.
- 테스트가 깨졌을 때 무엇이 문제인지 빠르게 알기 어렵다.
- 프로덕션 코드가 바뀌면 테스트 수정 비용이 커진다.

```java
// Good - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void 주문하면_총액을_계산하고_결제완료_상태가_된다() {
        Order order = orderWithBook(2);
        OrderService service = orderService();

        service.order(order);

        assertEquals(20000, order.getTotalPrice());
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    private Order orderWithBook(int quantity) {
        Product book = new Product("BOOK", 10000);
        Customer customer = new Customer("kim@test.com");

        return new Order(customer, book, quantity);
    }

    private OrderService orderService() {
        return new OrderService(
            new FakeOrderRepository(),
            new FakePaymentGateway(),
            new FakeNotificationSender()
        );
    }
}
```

- 테스트 이름이 의도를 설명한다.
- 준비 코드가 헬퍼 메서드로 숨겨져 핵심 흐름이 보인다.
- 테스트가 “주문하면 총액 계산 + 결제완료”라는 하나의 시나리오에 집중한다.
- 테스트가 문서처럼 읽힌다.
- 프로덕션 코드 변경 시 테스트 수정 범위가 줄어든다.

## 예제 2

```java
// Bad - 테스트 코드
@Test
void discountTest() {
    Customer c1 = new Customer("kim@test.com", "VIP");
    Product p1 = new Product("NOTEBOOK", 1000000);
    Order o1 = new Order(c1, p1, 1);

    DiscountService s = new DiscountService();

    int r1 = s.discount(o1);

    assertEquals(900000, r1);

    Customer c2 = new Customer("lee@test.com", "NORMAL");
    Product p2 = new Product("NOTEBOOK", 1000000);
    Order o2 = new Order(c2, p2, 1);

    int r2 = s.discount(o2);

    assertEquals(1000000, r2);
}
```

- VIP 할인과 일반 고객 가격을 한 테스트에서 함께 검증한다.
- 변수명 c1, p1, o1, r1이 의미를 드러내지 않는다.
- 테스트가 실패했을 때 어느 고객 조건이 실패했는지 알기 어렵다.
- 중복 코드가 많아 테스트 유지보수가 어렵다.

```java
// Good - 테스트 코드
@Test
void VIP_고객은_10퍼센트_할인을_받는다() {
    Order order = orderOf("VIP", 1_000_000);
    DiscountService service = new DiscountService();

    int discountedPrice = service.discount(order);

    assertEquals(900_000, discountedPrice);
}

@Test
void 일반_고객은_할인을_받지_않는다() {
    Order order = orderOf("NORMAL", 1_000_000);
    DiscountService service = new DiscountService();

    int discountedPrice = service.discount(order);

    assertEquals(1_000_000, discountedPrice);
}

private Order orderOf(String grade, int price) {
    Customer customer = new Customer("user@test.com", grade);
    Product product = new Product("NOTEBOOK", price);

    return new Order(customer, product, 1);
}
```

- 테스트가 하나의 개념만 검증한다.
- 테스트 이름이 요구사항을 문장처럼 표현한다.
- 의미 있는 변수명으로 읽기 쉽다.
- 중복 생성 코드는 헬퍼 메서드로 제거했다.
- 실패 시 어떤 규칙이 깨졌는지 바로 알 수 있다.

## 예제 3: Tests Enable the -ilities

```java
// Bad - 테스트가 없거나 지저분해서 리팩터링하기 어려운 코드
public class PriceCalculator {

    public int calculate(Order order) {
        int price = order.getProduct().getPrice() * order.getQuantity();

        if (order.getCustomer().getGrade().equals("VIP")) {
            price = (int) (price * 0.9);
        }

        if (order.getCoupon() != null) {
            price -= order.getCoupon().getAmount();
        }

        if (price < 0) {
            price = 0;
        }

        return price;
    }
}
```

```java
// Bad - 테스트 코드
@Test
void testPrice() {
    PriceCalculator calculator = new PriceCalculator();

    Order order = new Order(
        new Customer("kim@test.com", "VIP"),
        new Product("BOOK", 10000),
        2,
        new Coupon(3000)
    );

    assertEquals(15000, calculator.calculate(order));
}
```

- 테스트가 하나뿐이라 리팩터링 시 다른 조건이 깨졌는지 알 수 없다.
- VIP 할인, 쿠폰, 음수 방지 정책이 한 테스트에 섞여 있다.
- 테스트가 부족하면 가격 계산 로직을 분리하거나 개선하기 두렵다.
- 결국 프로덕션 코드를 계속 덧붙이는 방식으로 유지하게 된다.

```java
// Good - 테스트 코드
class PriceCalculatorTest {

    private final PriceCalculator calculator = new PriceCalculator();

    @Test
    void 기본_가격은_상품가격과_수량을_곱한다() {
        Order order = order("NORMAL", 10_000, 2, null);

        int price = calculator.calculate(order);

        assertEquals(20_000, price);
    }

    @Test
    void VIP_고객은_10퍼센트_할인을_받는다() {
        Order order = order("VIP", 10_000, 2, null);

        int price = calculator.calculate(order);

        assertEquals(18_000, price);
    }

    @Test
    void 쿠폰_금액만큼_가격을_차감한다() {
        Order order = order("NORMAL", 10_000, 2, new Coupon(3_000));

        int price = calculator.calculate(order);

        assertEquals(17_000, price);
    }

    @Test
    void 최종_가격은_0원보다_작을_수_없다() {
        Order order = order("NORMAL", 10_000, 1, new Coupon(20_000));

        int price = calculator.calculate(order);

        assertEquals(0, price);
    }

    private Order order(String grade, int productPrice, int quantity, Coupon coupon) {
        return new Order(
            new Customer("user@test.com", grade),
            new Product("BOOK", productPrice),
            quantity,
            coupon
        );
    }
}
```

- 이제 테스트가 있기 때문에 다음처럼 프로덕션 코드를 더 안전하게 리팩터링할 수 있다.

```java
// Good - 리팩터링한 프로덕션 코드
public class PriceCalculator {

    public int calculate(Order order) {
        int price = basePrice(order);
        price = applyGradeDiscount(order, price);
        price = applyCoupon(order, price);

        return minimumZero(price);
    }

    private int basePrice(Order order) {
        return order.getProduct().getPrice() * order.getQuantity();
    }

    private int applyGradeDiscount(Order order, int price) {
        if (order.getCustomer().isVip()) {
            return (int) (price * 0.9);
        }

        return price;
    }

    private int applyCoupon(Order order, int price) {
        if (!order.hasCoupon()) {
            return price;
        }

        return price - order.getCoupon().getAmount();
    }

    private int minimumZero(int price) {
        return Math.max(price, 0);
    }
}
```

- 테스트가 변경에 대한 안전망이 된다.
- 가격 계산 로직을 작은 메서드로 분리할 수 있다.
- 리팩터링 후 기존 기능이 유지되는지 바로 확인할 수 있다.
- 테스트가 있으므로 설계를 개선하는 데 두려움이 줄어든다.
- 이것이 테스트가 -ilities, 즉 유지보수성·유연성·재사용성을 가능하게 한다는 의미다.

## 나쁜 코드 vs 좋은 코드

| 구분         | 나쁜 테스트      | 좋은 테스트        |
| ---------- | ----------- | ------------- |
| 테스트 이름     | `test()`    | 요구사항을 문장처럼 표현 |
| 준비 코드      | 길고 반복됨      | 헬퍼 메서드로 정리    |
| 검증 범위      | 여러 개념 혼합    | 하나의 개념에 집중    |
| 실패 원인      | 파악 어려움      | 즉시 파악 가능      |
| 변경 대응      | 테스트 수정 비용 큼 | 변경에 강함        |
| 프로덕션 코드 영향 | 리팩터링을 두렵게 함 | 리팩터링을 가능하게 함  |

## 핵심 원칙

피해야 할 것:

- 테스트 코드를 “대충 작성해도 되는 코드”로 취급하는 것
- 테스트 이름을 test1, testOrder처럼 모호하게 짓는 것
- 한 테스트에서 너무 많은 개념을 검증하는 것
- 중복된 준비 코드를 테스트마다 반복하는 것
- 깨지기 쉬운 테스트를 방치하는 것

지켜야 할 것:

- 테스트 코드도 프로덕션 코드처럼 깨끗하게 유지한다.
- 테스트 이름은 요구사항을 설명하게 짓는다.
- 테스트는 읽기 쉬워야 한다.
- 중복 설정은 헬퍼 메서드나 테스트 픽스처로 정리한다.
- 깨끗한 테스트를 통해 프로덕션 코드의 변경 가능성을 지킨다.
- 테스트를 리팩터링의 안전망으로 사용한다.