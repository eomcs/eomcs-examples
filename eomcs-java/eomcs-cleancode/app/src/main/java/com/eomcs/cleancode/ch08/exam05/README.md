# 아직 존재하지 않는 코드를 사용하기 (Using Code That Does Not Yet Exist)

> **아직 존재하지 않거나 아직 확정되지 않은 외부 코드가 있다면, 기다리지 말고 우리가 원하는 인터페이스를 먼저 정의하라.**

- 우리가 원하는 인터페이스를 정의한다.
- 테스트를 위해 인터페이스의 Fake 구현체를 만든다.
- 이후 실제 API가 나오면 Adapter를 만들어 연결한다.

## 예제 1

```java
// Bad - 함수 정의
public class NotificationService {

    public void notifyUser(User user, String message) {
        // 아직 외부 알림 API가 나오지 않아서 구현 불가
        // TODO: ExternalNotificationClient가 완성되면 구현
    }
}
```

```java
// Bad - 함수 사용
notificationService.notifyUser(user, "가입을 환영합니다.");
```

- 외부 API가 완성될 때까지 우리 개발이 막힌다.
- 서비스 코드의 의도가 불명확하다.
- 테스트할 수 없다.
- 나중에 외부 API 형태에 우리 코드가 끌려갈 가능성이 크다.

```java
// Good - 우리가 원하는 인터페이스를 먼저 정의
public interface NotificationSender {
    void send(String receiver, String message);
}
```

```java
// Good - 함수 정의
public class NotificationService {
    private final NotificationSender notificationSender;

    public NotificationService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void notifyUser(User user, String message) {
        notificationSender.send(user.getEmail(), message);
    }
}
```

```java
// Good - 테스트용 가짜 구현
public class FakeNotificationSender implements NotificationSender {
    private String receiver;
    private String message;

    @Override
    public void send(String receiver, String message) {
        this.receiver = receiver;
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
```

```java
// Good - 함수 사용
NotificationSender sender = new FakeNotificationSender();
NotificationService notificationService = new NotificationService(sender);

notificationService.notifyUser(user, "가입을 환영합니다.");
```

- 외부 API가 없어도 우리 코드를 개발할 수 있다.
- 서비스 코드는 우리가 원하는 말로 표현된다.
- 테스트용 가짜 객체를 쉽게 만들 수 있다.
- 나중에 실제 API가 나오면 어댑터만 만들면 된다.

## 예제 2

```java
// Bad - 아직 확정되지 않은 결제 API에 맞춰 코드를 기다림
public class OrderService {

    public void pay(Order order) {
        // TODO: 결제팀 API 스펙 확정 후 구현
        // ExternalPaymentClient? PaymentApi? 아직 모름
    }
}
```

```java
// Bad - 함수 사용
orderService.pay(order);
```

- 결제 API가 확정될 때까지 주문 서비스 개발이 지연된다.
- 주문 서비스 테스트를 작성하기 어렵다.
- 외부 API 이름과 구조가 비즈니스 코드에 직접 들어올 가능성이 높다.

```java
// Good - 주문 서비스가 원하는 결제 경계 정의
public interface PaymentGateway {
    void pay(String orderId, int amount);
}
```

```java
// Good - 함수 정의
public class OrderService {
    private final PaymentGateway paymentGateway;

    public OrderService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void pay(Order order) {
        paymentGateway.pay(order.getId(), order.getTotalPrice());
        order.markPaid();
    }
}
```

```java
// Good - 테스트용 구현
public class FakePaymentGateway implements PaymentGateway {
    private String paidOrderId;
    private int paidAmount;

    @Override
    public void pay(String orderId, int amount) {
        this.paidOrderId = orderId;
        this.paidAmount = amount;
    }

    public String getPaidOrderId() {
        return paidOrderId;
    }

    public int getPaidAmount() {
        return paidAmount;
    }
}
```

```java
// Good - 함수 사용
PaymentGateway paymentGateway = new FakePaymentGateway();
OrderService orderService = new OrderService(paymentGateway);

orderService.pay(order);
```

- 주문 서비스는 외부 결제 API를 기다리지 않아도 된다.
- pay(orderId, amount)라는 우리 쪽 요구사항이 먼저 드러난다.
- 테스트 가능한 구조가 된다.
- 실제 결제 API가 나오면 어댑터에서만 변환하면 된다.

## 예제 3: 실제 API가 나온 뒤 Adapter 연결

```java
// 외부 결제 API가 나중에 이렇게 확정되었다고 가정
public class ExternalPaymentClient {
    public ExternalPaymentResponse execute(ExternalPaymentRequest request) {
        // 외부 결제 API 호출
        return new ExternalPaymentResponse(true);
    }
}
```

```java
// Good - Adapter
public class ExternalPaymentGateway implements PaymentGateway {
    private final ExternalPaymentClient client;

    public ExternalPaymentGateway(ExternalPaymentClient client) {
        this.client = client;
    }

    @Override
    public void pay(String orderId, int amount) {
        ExternalPaymentRequest request = new ExternalPaymentRequest();
        request.setMerchantOrderNo(orderId);
        request.setPaymentAmount(amount);
        request.setCurrency("KRW");

        ExternalPaymentResponse response = client.execute(request);

        if (!response.isSuccess()) {
            throw new PaymentFailedException("결제 실패. orderId=" + orderId);
        }
    }
}
```

```java
// Good - 실제 함수 사용
PaymentGateway paymentGateway =
    new ExternalPaymentGateway(new ExternalPaymentClient());

OrderService orderService = new OrderService(paymentGateway);

orderService.pay(order);
```

- 외부 API의 복잡한 요청/응답 구조가 Adapter 안에 갇힌다.
- OrderService는 변경되지 않는다.
- 외부 API가 바뀌어도 수정 지점은 ExternalPaymentGateway로 제한된다.
- 경계가 명확해진다.

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드          | 좋은 코드            |
| ----- | -------------- | ---------------- |
| 개발 방식 | 외부 API를 기다림    | 우리 인터페이스를 먼저 정의  |
| 의존 방향 | 외부 코드에 끌려감     | 우리 코드가 원하는 형태 유지 |
| 테스트   | 외부 API 없으면 어려움 | Fake 구현으로 가능     |
| 변경 영향 | 비즈니스 코드까지 영향   | Adapter만 수정      |
| 코드 표현 | TODO, 임시 코드 증가 | 도메인 의도 중심        |

## 핵심 원칙

피해야 할 것:

- 아직 없는 외부 API를 기다리며 개발을 멈추는 것
- 외부 API 스펙이 정해지기 전까지 테스트를 미루는 것
- 외부 API 형태에 비즈니스 코드를 맞추는 것
- 임시 TODO 코드로 경계를 흐리는 것

지켜야 할 것:

- 우리가 원하는 인터페이스를 먼저 정의한다.
- 외부 시스템은 경계 밖에 둔다.
- 테스트용 Fake 구현으로 우리 코드를 먼저 검증한다.
- 실제 API가 나오면 Adapter로 연결한다.
- 외부 API 변경 영향이 Adapter 안에 머물게 한다.