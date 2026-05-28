# 클래스는 작아야 한다! (Classes Should Be Small!)

> **클래스의 크기는 “줄 수”가 아니라 “책임의 수”로 판단한다**

👉 이유:

- 클래스 이름이 모호하면 책임이 많다는 신호다
- Manager, Processor, Super 같은 이름은 여러 책임을 뭉뚱그린 경우가 많다
- 큰 클래스는 변경 이유가 많아진다
- 작은 클래스는 변경 이유가 하나로 좁혀진다
- 책의 예제에서 나온 SuperDashboard처럼 많은 일을 하는 클래스를 “God class”에 가깝게 본다. 
- **클래스는 많은 기능을 담는 상자가 아니라, 하나의 책임을 명확히 가진 단위여야 한다.**

## 예제 1

```java
// Bad
public class UserManager {

    public void register(User user) {
        validate(user);
        save(user);
        sendWelcomeEmail(user);
        writeAuditLog(user);
    }

    private void validate(User user) {
        if (user.email() == null) {
            throw new IllegalArgumentException("email required");
        }
    }

    private void save(User user) {
        System.out.println("save user");
    }

    private void sendWelcomeEmail(User user) {
        System.out.println("send email");
    }

    private void writeAuditLog(User user) {
        System.out.println("write audit log");
    }
}
```

- 사용자 검증, 저장, 이메일 발송, 감사 로그 기록을 모두 담당한다
- 클래스 이름이 UserManager처럼 모호하다
- 변경 이유가 많다
  - 검증 규칙 변경
  - 저장 방식 변경
  - 이메일 정책 변경
  - 로그 정책 변경

```java
// Good
public class UserRegistrationService {

    private final UserValidator validator;
    private final UserRepository repository;
    private final WelcomeEmailSender emailSender;
    private final AuditLogger auditLogger;

    public UserRegistrationService(
            UserValidator validator,
            UserRepository repository,
            WelcomeEmailSender emailSender,
            AuditLogger auditLogger
    ) {
        this.validator = validator;
        this.repository = repository;
        this.emailSender = emailSender;
        this.auditLogger = auditLogger;
    }

    public void register(User user) {
        validator.validate(user);
        repository.save(user);
        emailSender.sendTo(user);
        auditLogger.logRegistration(user);
    }
}
```

```java
public class UserValidator {
    public void validate(User user) {
        if (user.email() == null) {
            throw new IllegalArgumentException("email required");
        }
    }
}

public class UserRepository {
    public void save(User user) {
        System.out.println("save user");
    }
}

public class WelcomeEmailSender {
    public void sendTo(User user) {
        System.out.println("send email");
    }
}

public class AuditLogger {
    public void logRegistration(User user) {
        System.out.println("write audit log");
    }
}
```

- 각 클래스가 하나의 책임을 가진다
- 변경 이유가 분리된다
- UserRegistrationService는 등록 흐름만 조율한다
- 세부 정책은 별도 클래스로 이동한다

## 예제 2: 단일 책임 원칙 (The Single Responsibility Principle)

> **클래스는 변경될 이유가 하나만 있어야 한다**

- “무엇을 하는가?”보다 “왜 변경되는가?”를 봐야 한다
- 변경 이유가 둘 이상이면 클래스를 나누어야 한다
- 동작하는 코드와 깨끗한 코드는 다르다
- 동작하게 만든 뒤, 다시 돌아와 책임을 분리해야 한다

```java
// Bad
public class Invoice {

    private final List<Item> items;

    public Invoice(List<Item> items) {
        this.items = items;
    }

    public int totalPrice() {
        return items.stream()
                .mapToInt(Item::price)
                .sum();
    }

    public String print() {
        return "Total: " + totalPrice();
    }

    public void save() {
        System.out.println("save invoice to database");
    }
}
```

- Invoice가 계산, 출력 형식, 저장을 모두 담당한다
- 출력 형식이 바뀌어도 Invoice가 바뀐다
- DB 저장 방식이 바뀌어도 Invoice가 바뀐다
- 금액 계산 규칙이 바뀌어도 Invoice가 바뀐다

```java
// Good
public class Invoice {

    private final List<Item> items;

    public Invoice(List<Item> items) {
        this.items = items;
    }

    public int totalPrice() {
        return items.stream()
                .mapToInt(Item::price)
                .sum();
    }
}
```

```java
public class InvoicePrinter {

    public String print(Invoice invoice) {
        return "Total: " + invoice.totalPrice();
    }
}
```

```java
public class InvoiceRepository {

    public void save(Invoice invoice) {
        System.out.println("save invoice to database");
    }
}
```

- Invoice는 금액 계산 책임만 가진다
- 출력 변경은 InvoicePrinter만 수정한다
- 저장 변경은 InvoiceRepository만 수정한다
- 변경 이유가 클래스별로 분리된다

## 예제 3: 응집도 (Cohesion)

> **클래스의 메서드들이 같은 인스턴스 변수를 함께 사용할수록 응집도가 높다**

- 응집도가 높으면 클래스가 하나의 논리적 개념으로 묶인다
- 응집도가 낮으면 서로 관련 없는 기능이 한 클래스 안에 섞였다는 신호다
- 일부 메서드만 사용하는 필드가 많아지면 클래스 분리를 의심해야 한다
- **응집도가 높은 클래스는 메서드와 변수가 서로 의존하며 하나의 논리적 전체로 묶인다**
- Stack 예제처럼 대부분의 메서드가 같은 필드를 함께 사용하면 응집도가 높다.

```java
// Bad
public class ReportTool {

    private List<Order> orders;
    private String title;
    private String email;

    public int calculateTotalSales() {
        return orders.stream()
                .mapToInt(Order::amount)
                .sum();
    }

    public String formatReport() {
        return title + ": " + calculateTotalSales();
    }

    public void sendEmail() {
        System.out.println("send to " + email);
    }
}
```

- calculateTotalSales()는 orders만 사용한다
- formatReport()는 title, orders를 사용한다
- sendEmail()은 email만 사용한다
- 필드들이 하나의 목적을 위해 함께 사용되지 않는다
- 응집도가 낮다

```java
// Good
public class SalesReport {

    private final List<Order> orders;
    private final String title;

    public SalesReport(List<Order> orders, String title) {
        this.orders = orders;
        this.title = title;
    }

    public int totalSales() {
        return orders.stream()
                .mapToInt(Order::amount)
                .sum();
    }

    public String format() {
        return title + ": " + totalSales();
    }
}
```

```java
public class EmailSender {

    private final String email;

    public EmailSender(String email) {
        this.email = email;
    }

    public void send(String message) {
        System.out.println("send to " + email);
    }
}
```

- 보고서 계산과 포맷은 SalesReport로 묶인다
- 이메일 발송은 EmailSender로 분리된다
- 각 클래스의 필드와 메서드가 더 강하게 연결된다
- 응집도가 높아진다

## 예제 4: 응집도를 유지하면 작은 클래스가 많아진다

> **작은 함수와 짧은 인자 목록을 유지하다 보면, 자연스럽게 관련 필드와 메서드가 모여 여러 작은 클래스로 분리된다**

- 큰 클래스 하나에 모든 메서드를 넣지 않는다
- 특정 필드 집합을 사용하는 메서드들이 보이면 새 클래스로 뽑아낸다
- 작은 클래스가 많아지는 것은 나쁜 것이 아니다
- 오히려 복잡성을 이름 붙은 작은 단위로 정리하는 것이다

책에서 소수 생성기 예제도 하나의 큰 흐름을 PrimePrinter, RowColumnPagePrinter, PrimeGenerator 같은 작은 클래스로 나누어 출력 책임과 소수 생성 책임을 분리하고 있다.

```java
// Bad
public class OrderProcessor {

    private Order order;
    private int discountRate;
    private int shippingFee;
    private String receiverEmail;

    public int calculateDiscount() {
        return order.totalPrice() * discountRate / 100;
    }

    public int calculateShippingFee() {
        if (order.totalPrice() >= 50_000) {
            return 0;
        }
        return shippingFee;
    }

    public int finalPrice() {
        return order.totalPrice()
                - calculateDiscount()
                + calculateShippingFee();
    }

    public void sendReceipt() {
        System.out.println("send receipt to " + receiverEmail);
    }
}
```

- 할인 계산, 배송비 계산, 최종 금액 계산, 영수증 발송이 섞여 있다
- receiverEmail은 결제 금액 계산과 관계없다
- shippingFee는 할인 계산과 관계없다
- 응집도가 낮고 책임이 많다

```java
// Good
public class OrderPriceCalculator {

    private final Order order;
    private final DiscountPolicy discountPolicy;
    private final ShippingPolicy shippingPolicy;

    public OrderPriceCalculator(
            Order order,
            DiscountPolicy discountPolicy,
            ShippingPolicy shippingPolicy
    ) {
        this.order = order;
        this.discountPolicy = discountPolicy;
        this.shippingPolicy = shippingPolicy;
    }

    public int finalPrice() {
        return order.totalPrice()
                - discountPolicy.discountFor(order)
                + shippingPolicy.shippingFeeFor(order);
    }
}
```

```java
public class DiscountPolicy {

    private final int discountRate;

    public DiscountPolicy(int discountRate) {
        this.discountRate = discountRate;
    }

    public int discountFor(Order order) {
        return order.totalPrice() * discountRate / 100;
    }
}
```

```java
public class ShippingPolicy {

    private final int defaultShippingFee;

    public ShippingPolicy(int defaultShippingFee) {
        this.defaultShippingFee = defaultShippingFee;
    }

    public int shippingFeeFor(Order order) {
        if (order.totalPrice() >= 50_000) {
            return 0;
        }

        return defaultShippingFee;
    }
}
```

```java
public class ReceiptSender {

    private final String receiverEmail;

    public ReceiptSender(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public void send(Order order, int finalPrice) {
        System.out.println("send receipt to " + receiverEmail);
    }
}
```

- 가격 계산, 할인 정책, 배송 정책, 영수증 발송이 분리된다
- 각 클래스는 자신에게 필요한 필드만 가진다
- 응집도가 높아진다
- 변경 영향 범위가 작아진다

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드                           | 좋은 코드             |
| ------ | ------------------------------- | ----------------- |
| 클래스 크기 | 많은 책임을 가진 큰 클래스                 | 하나의 책임을 가진 작은 클래스 |
| 변경 이유  | 여러 개                            | 하나                |
| 이름     | `Manager`, `Processor`, `Super` | 구체적인 책임 이름        |
| 응집도    | 일부 메서드만 일부 필드 사용                | 메서드와 필드가 함께 움직임   |
| 구조     | 기능을 한 클래스에 모음                   | 책임별로 클래스를 나눔      |

## 핵심 원칙

피해야 할 것:

- God Class 만들기
- Manager, Processor 같은 모호한 이름으로 여러 책임을 숨기기
- “메서드 수가 적으니 작은 클래스다”라고 판단하기
- 서로 관련 없는 필드를 한 클래스에 넣기
- 동작만 확인하고 책임 분리를 미루기

지켜야 할 것:

- 클래스는 작게 만든다
- 작다는 기준은 줄 수가 아니라 책임의 수다
- 클래스는 변경될 이유가 하나만 있어야 한다
- 응집도가 낮아지면 클래스를 나눈다
- 많은 작은 클래스가 큰 클래스 몇 개보다 이해하기 쉽다