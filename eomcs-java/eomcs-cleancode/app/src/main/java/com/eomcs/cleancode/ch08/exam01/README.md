# 외부 코드 사용하기 (Using Third-Party Code)

> **외부 라이브러리나 범용 인터페이스를 애플리케이션 전체에 직접 퍼뜨리지 말고, 우리 코드가 필요한 형태로 감싸서 사용하라.**

- Map 같은 경계 인터페이스를 시스템 곳곳에 넘기지 말고, 필요한 클래스 내부나 가까운 클래스 묶음 안에 숨겨라. 
- 특히 public API에서 Map을 반환하거나 인자로 받는 일을 피하라.

## 예제 1

```java
// Bad - 함수 정의
public class SensorService {
    private Map<String, Sensor> sensors = new HashMap<>();

    public Map<String, Sensor> getSensors() {
        return sensors;
    }
}
```

```java
// Bad - 함수 사용
Map<String, Sensor> sensors = sensorService.getSensors();

Sensor sensor = sensors.get("SENSOR-001");
sensors.clear(); // 외부에서 전체 센서 데이터 삭제 가능
sensors.put("TEMP", new Sensor("TEMP"));
```

- Map의 모든 기능이 외부에 노출된다.
- 호출자가 센서 저장 방식이 Map이라는 사실을 알아야 한다.
- 외부 코드가 clear(), put(), remove()로 내부 상태를 망가뜨릴 수 있다.
- 나중에 Map 대신 DB, 캐시, API로 바꾸기 어렵다.

```java
// Good - 함수 정의
public class Sensors {
    private final Map<String, Sensor> sensors = new HashMap<>();

    public Sensor getById(String id) {
        return sensors.get(id);
    }

    public void register(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
    }
}
```

```java
// Good - 함수 사용
Sensor sensor = sensors.getById("SENSOR-001");
```

- Map이 클래스 내부 구현으로 숨겨진다.
- 호출자는 getById()라는 의도 있는 메서드만 사용한다.
- 외부에서 내부 컬렉션을 함부로 변경할 수 없다.
- 저장 방식이 바뀌어도 호출 코드는 거의 영향을 받지 않는다.

## 예제 2

```java
// Bad - 함수 정의
public void sendNotification(Map<String, Object> message) {
    String title = (String) message.get("title");
    String body = (String) message.get("body");
    String receiver = (String) message.get("receiver");

    notificationClient.send(receiver, title, body);
}
```

```java
// Bad - 함수 사용
Map<String, Object> message = new HashMap<>();
message.put("title", "가입 완료");
message.put("body", "회원가입이 완료되었습니다.");
message.put("receiver", "user@test.com");

notificationService.sendNotification(message);
```

- 문자열 키를 잘못 쓰면 런타임에야 오류가 난다.
- Object 타입 때문에 형변환이 필요하다.
- 어떤 값이 필수인지 코드만 보고 알기 어렵다.
- 호출자와 구현자가 Map 구조에 강하게 묶인다.

```java
// Good - 함수 정의
public class NotificationMessage {
    private final String title;
    private final String body;
    private final String receiver;

    public NotificationMessage(String title, String body, String receiver) {
        this.title = title;
        this.body = body;
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getReceiver() {
        return receiver;
    }
}
```

```java
public void sendNotification(NotificationMessage message) {
    notificationClient.send(
        message.getReceiver(),
        message.getTitle(),
        message.getBody()
    );
}
```

```java
// Good - 함수 사용
NotificationMessage message = new NotificationMessage(
    "가입 완료",
    "회원가입이 완료되었습니다.",
    "user@test.com"
);

notificationService.sendNotification(message);
```

- 필요한 데이터 구조가 명확한 타입으로 표현된다.
- 문자열 키 실수를 줄일 수 있다.
- 형변환 코드가 사라진다.
- 호출 코드가 더 읽기 쉬워진다.

## 예제 3

```java
// Bad - 함수 정의
public class OrderService {
    private final ExternalPaymentClient paymentClient;

    public OrderService(ExternalPaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public void pay(Order order) {
        ExternalPaymentRequest request = new ExternalPaymentRequest();
        request.setAmount(order.getTotalPrice());
        request.setCurrency("KRW");
        request.setCustomerId(order.getCustomerId());

        ExternalPaymentResponse response = paymentClient.execute(request);

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getErrorMessage());
        }
    }
}
```

```java
// Bad - 함수 사용
orderService.pay(order);
```

- 서비스 코드가 외부 결제 API의 요청/응답 구조를 직접 안다.
- 외부 API 변경 시 비즈니스 코드가 영향을 받는다.
- 테스트할 때 외부 라이브러리 객체를 계속 다뤄야 한다.
- 결제라는 도메인 의도보다 API 사용법이 더 많이 드러난다.

```java
// Good - 우리 애플리케이션용 인터페이스
public interface PaymentGateway {
    void pay(Order order);
}
```

```java
// Good - 외부 API를 감싸는 어댑터
public class ExternalPaymentGateway implements PaymentGateway {
    private final ExternalPaymentClient paymentClient;

    public ExternalPaymentGateway(ExternalPaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public void pay(Order order) {
        ExternalPaymentRequest request = new ExternalPaymentRequest();
        request.setAmount(order.getTotalPrice());
        request.setCurrency("KRW");
        request.setCustomerId(order.getCustomerId());

        ExternalPaymentResponse response = paymentClient.execute(request);

        if (!response.isSuccess()) {
            throw new PaymentFailedException(response.getErrorMessage());
        }
    }
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
        paymentGateway.pay(order);
        order.markPaid();
    }
}
```

```java
// Good - 함수 사용
orderService.pay(order);
```

- 외부 API 사용법이 어댑터 안에 갇힌다.
- 비즈니스 코드는 PaymentGateway라는 우리 쪽 인터페이스만 안다.
- 외부 라이브러리가 바뀌어도 변경 지점이 제한된다.
- 테스트할 때 가짜 PaymentGateway를 쉽게 넣을 수 있다.

## 나쁜 코드 vs 좋은 코드

| 구분         | 나쁜 코드           | 좋은 코드        |
| ---------- | --------------- | ------------ |
| 외부 코드 사용   | 직접 사용           | 감싸서 사용       |
| public API | `Map`, 외부 타입 노출 | 도메인 타입 노출    |
| 변경 영향      | 시스템 전체로 퍼짐      | 경계 클래스에 제한   |
| 가독성        | 라이브러리 사용법 중심    | 비즈니스 의도 중심   |
| 안정성        | 오용 가능성 큼        | 필요한 기능만 제공   |
| 테스트        | 외부 API에 의존      | 인터페이스로 대체 가능 |

## 핵심 원칙

피해야 할 것:

- Map, List, 외부 API 타입을 public API에 그대로 노출하는 것
- 외부 라이브러리 객체를 애플리케이션 전체에 퍼뜨리는 것
- 호출자가 외부 라이브러리 사용법을 알게 만드는 것
- 범용 인터페이스의 모든 기능을 외부에 열어두는 것

지켜야 할 것:

- 외부 코드는 경계 클래스나 어댑터로 감싼다.
- 우리 애플리케이션에 필요한 기능만 노출한다.
- public API에는 도메인 의미가 있는 타입을 사용한다.
- 외부 라이브러리 변경 영향이 한 곳에 머물게 한다.
- 경계에서는 “외부 코드의 편의”보다 “우리 코드의 의도”를 우선한다.