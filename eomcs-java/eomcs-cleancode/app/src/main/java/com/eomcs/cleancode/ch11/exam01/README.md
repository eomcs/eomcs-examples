# 시스템 제작과 시스템 사용을 분리하라 (Separate Constructing a System from Using It)

> **객체를 만드는 코드와 객체를 사용하는 코드를 섞지 마라**

- 생성은 시스템 시작 단계의 관심사다
- 사용은 런타임 비즈니스 로직의 관심사다
- 둘을 섞으면 클래스가 “생성 + 사용”이라는 두 책임을 가진다
- 테스트하기 어려워지고, 구체 클래스에 강하게 의존한다

Clean Code:

> 애플리케이션 객체를 만들고 의존성을 연결하는 시작 과정과, 시작 이후 동작하는 런타임 로직을 분리해야 한다.

## 예제 1

```java
// Bad
public class OrderService {

    private PaymentGateway paymentGateway;

    public void place(Order order) {
        if (paymentGateway == null) {
            paymentGateway = new KakaoPayGateway();
        }

        paymentGateway.pay(order.totalPrice());
    }
}
```

- OrderService가 KakaoPayGateway 생성까지 담당한다
- 비즈니스 로직과 생성 로직이 섞여 있다
- 다른 결제 수단으로 바꾸기 어렵다
- 테스트에서 가짜 결제 객체를 넣기 어렵다

```java
// Good
public class OrderService {

    private final PaymentGateway paymentGateway;

    public OrderService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void place(Order order) {
        paymentGateway.pay(order.totalPrice());
    }
}
```

```java
public interface PaymentGateway {
    void pay(int amount);
}
```

```java
public class KakaoPayGateway implements PaymentGateway {

    @Override
    public void pay(int amount) {
        System.out.println("Pay with KakaoPay: " + amount);
    }
}
```

- OrderService는 결제 객체를 만들지 않는다
- 이미 준비된 PaymentGateway를 사용만 한다
- 생성 책임이 사라져 비즈니스 로직이 단순해진다
- 테스트하기 쉬워진다

## 예제 2: Main 분리 (Separation of Main)

> **객체 생성과 조립은 main 또는 main이 호출하는 설정 모듈에 모아라**

- main()은 객체를 만든다
- 애플리케이션은 전달받은 객체를 사용한다
- 애플리케이션은 자신이 어떻게 조립되었는지 몰라야 한다

Clean Code:

> 생성의 모든 측면을 main이나 main이 호출하는 모듈로 옮기고, 나머지 시스템은 객체가 이미 적절히 생성되고 연결되었다고 가정하게 만들라.

```java
// Bad
public class Application {

    public void run() {
        OrderRepository repository = new MySqlOrderRepository();
        PaymentGateway paymentGateway = new KakaoPayGateway();
        OrderService orderService = new OrderService(repository, paymentGateway);

        orderService.place(new Order(10_000));
    }
}
```

- Application 실행 로직 안에 생성 코드가 섞여 있다
- 실행 흐름을 읽다가 객체 조립 세부사항까지 봐야 한다
- 저장소나 결제 구현을 바꾸면 실행 코드도 수정된다

```java
// Good
public class Main {

    public static void main(String[] args) {
        OrderRepository repository = new MySqlOrderRepository();
        PaymentGateway paymentGateway = new KakaoPayGateway();

        OrderService orderService =
                new OrderService(repository, paymentGateway);

        Application application = new Application(orderService);
        application.run();
    }
}
```

```java
public class Application {

    private final OrderService orderService;

    public Application(OrderService orderService) {
        this.orderService = orderService;
    }

    public void run() {
        orderService.place(new Order(10_000));
    }
}
```

- Main은 객체 생성과 조립을 담당한다
- Application은 실행만 담당한다
- 애플리케이션은 MySqlOrderRepository, KakaoPayGateway를 모른다
- 생성 방향의 의존성이 애플리케이션 내부로 침투하지 않는다

## 예제 3: 팩토리 (Factories)

> **객체를 “언제 만들지”는 애플리케이션이 알아야 하지만, “어떻게 만들지”는 숨겨야 한다**

- 어떤 객체는 실행 중에 필요할 때마다 생성해야 한다
- 하지만 생성 세부사항까지 애플리케이션이 알면 결합도가 높아진다
- 이때 Factory를 사용하면 생성 시점은 제어하면서 생성 방식은 감출 수 있다

Clean Code:

> 주문 처리 시스템에서 애플리케이션이 LineItem을 언제 만들지는 알아야 하지만, 생성 세부사항은 LineItemFactory 쪽에 숨길 수 있다.

```java
// Bad
public class Order {

    public void addProduct(Product product, int quantity) {
        LineItem item = new LineItem(
                product.id(),
                product.name(),
                product.price(),
                quantity,
                product.price() * quantity
        );

        add(item);
    }

    private void add(LineItem item) {
        System.out.println("add line item");
    }
}
```

- Order가 LineItem 생성 세부사항을 모두 알고 있다
- LineItem 생성 규칙이 바뀌면 Order도 수정된다
- 할인, 세금, 옵션 가격이 추가되면 생성 코드가 더 복잡해진다

```java
// Good
public interface LineItemFactory {
    LineItem create(Product product, int quantity);
}
```

```java
public class DefaultLineItemFactory implements LineItemFactory {

    @Override
    public LineItem create(Product product, int quantity) {
        int totalPrice = product.price() * quantity;

        return new LineItem(
                product.id(),
                product.name(),
                product.price(),
                quantity,
                totalPrice
        );
    }
}
```

```java
public class Order {

    private final LineItemFactory lineItemFactory;

    public Order(LineItemFactory lineItemFactory) {
        this.lineItemFactory = lineItemFactory;
    }

    public void addProduct(Product product, int quantity) {
        LineItem item = lineItemFactory.create(product, quantity);
        add(item);
    }

    private void add(LineItem item) {
        System.out.println("add line item");
    }
}
```

- Order는 LineItem을 언제 만들지 결정한다
- LineItemFactory는 LineItem을 어떻게 만들지 결정한다
- 생성 규칙 변경이 Order로 전파되지 않는다
- 생성 세부사항이 팩토리 안으로 분리된다

## 예제 4: 의존성 주입 (Dependency Injection)

> **클래스가 의존 객체를 직접 찾거나 만들지 말고, 외부에서 주입받게 하라**

- 객체는 자신의 의존성을 직접 생성하지 않는다
- 생성 책임을 main, 설정 모듈, DI 컨테이너에 넘긴다
- 클래스는 수동적이 되고, 필요한 객체를 생성자나 setter로 받는다

Clean Code:

> DI는 “생성과 사용을 분리하는 강력한 메커니즘”이며, 객체가 의존성을 직접 인스턴스화하지 않고 외부 메커니즘에 맡겨야 한다.

```java
// Bad
public class EmailNotificationService {

    private final EmailClient emailClient = new SmtpEmailClient();

    public void sendWelcomeEmail(User user) {
        emailClient.send(user.email(), "Welcome!");
    }
}
```

- EmailNotificationService가 SmtpEmailClient를 직접 생성한다
- SMTP 구현에 강하게 묶인다
- 테스트에서 실제 메일 발송을 막기 어렵다
- 다른 메일 서비스로 교체하기 어렵다

```java
// Good
public interface EmailClient {
    void send(String email, String message);
}
```

```java
public class SmtpEmailClient implements EmailClient {

    @Override
    public void send(String email, String message) {
        System.out.println("SMTP send: " + email);
    }
}
```

```java
public class EmailNotificationService {

    private final EmailClient emailClient;

    public EmailNotificationService(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    public void sendWelcomeEmail(User user) {
        emailClient.send(user.email(), "Welcome!");
    }
}
```

```java
// main 또는 설정 모듈
public class Main {

    public static void main(String[] args) {
        EmailClient emailClient = new SmtpEmailClient();
        EmailNotificationService notificationService =
                new EmailNotificationService(emailClient);

        notificationService.sendWelcomeEmail(new User("kim@example.com"));
    }
}
```

```java
// 테스트
class FakeEmailClient implements EmailClient {

    boolean sent = false;

    @Override
    public void send(String email, String message) {
        sent = true;
    }
}
```

```java
@Test
void sendsWelcomeEmail() {
    FakeEmailClient emailClient = new FakeEmailClient();
    EmailNotificationService service =
            new EmailNotificationService(emailClient);

    service.sendWelcomeEmail(new User("kim@example.com"));

    assertTrue(emailClient.sent);
}
```

- 서비스는 EmailClient를 사용만 한다
- 실제 구현은 외부에서 주입한다
- 테스트에서는 가짜 구현을 주입한다
- 생성과 사용이 분리된다
- 클래스의 책임이 명확해진다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드               | 좋은 코드                         |
| ----- | ------------------- | ----------------------------- |
| 객체 생성 | 사용하는 클래스 내부에서 `new` | `main`, Factory, DI 컨테이너에서 생성 |
| 책임    | 생성 + 사용             | 사용만                           |
| 의존성   | 구체 클래스에 직접 의존       | 인터페이스에 의존                     |
| 테스트   | 실제 구현에 묶임           | 테스트 더블 주입 가능                  |
| 변경    | 구현 변경이 비즈니스 코드에 전파  | 조립 코드만 변경                     |


## 핵심 원칙

피해야 할 것:

- 비즈니스 로직 안에서 new로 구체 클래스를 직접 생성하는 것
- Lazy Initialization을 여러 곳에 흩뿌리는 것
- 객체 생성 조건과 실제 업무 로직을 한 메서드에 섞는 것
- 클래스가 전체 시스템의 조립 방식을 알게 만드는 것

지켜야 할 것:

- 생성과 사용을 분리한다
- main은 객체를 만들고 연결한다
- 애플리케이션 코드는 이미 준비된 객체를 사용한다
- 실행 중 생성이 필요하면 Factory로 생성 세부사항을 숨긴다
- 의존 객체는 생성자나 setter로 주입받는다