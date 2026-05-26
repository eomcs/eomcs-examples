# 호출자를 고려해 예외 클래스를 정의하라 (Define Exception Classes in Terms of a Caller’s Needs)

> **예외 클래스는 "던지는 쪽"이 아니라 "사용하는 쪽(호출자)"의 관점에서 정의하라**

👉 즉,

- 내부 구현(DB, API, 파일 등)에 맞춰 예외를 정의하지 말고
- 호출자가 어떤 방식으로 처리하고 싶은지를 기준으로 예외를 설계하라

👉 왜 중요한가?

- 내부 예외를 그대로 노출하면 계층 간 결합도가 증가
- 호출자는 필요 없는 세부사항까지 알아야 함
- 예외 처리 코드가 복잡해짐

## 예제 1

```java
// Bad - 함수 정의
public class UserService {

    public User getUser(Long id) throws SQLException {
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            throw e; // 그대로 던짐
        }
    }
}
```

```java
// Bad - 함수 사용
try {
    User user = userService.getUser(1L);
} catch (SQLException e) {
    if (e.getErrorCode() == 100) {
        System.out.println("사용자 없음");
    } else {
        System.out.println("DB 오류");
    }
}
```

- 호출자가 DB 예외를 알아야 한다
- SQLException의 내부 코드에 의존한다
- 비즈니스 로직이 기술 세부사항(DB)에 묶인다
- 예외 처리 코드가 복잡해진다

```java
// Good - 함수 정의
public class UserService {

    public User getUser(Long id) {
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            throw new UserNotFoundException("사용자 조회 실패. id=" + id, e);
        }
    }
}
```

```java
// Good - 함수 사용
try {
    User user = userService.getUser(1L);
} catch (UserNotFoundException e) {
    System.out.println("사용자를 찾을 수 없음");
}
```

- 호출자는 DB를 몰라도 된다
- 예외가 도메인 의미를 가진다
- 처리 코드가 단순해진다
- 계층 간 결합이 줄어든다

## 예제 2

```java
// Bad - 함수 정의
public void sendEmail(String email) throws IOException, TimeoutException {
    emailClient.send(email);
}
```

```java
// Bad - 함수 사용
try {
    emailService.sendEmail("test@test.com");
} catch (IOException e) {
    System.out.println("네트워크 오류");
} catch (TimeoutException e) {
    System.out.println("타임아웃 발생");
}
```

- 호출자가 너무 많은 예외를 알아야 한다
- 내부 구현(네트워크, 타임아웃)이 노출된다
- 호출 코드가 복잡해진다

```java
// Good - 함수 정의
public void sendEmail(String email) {
    try {
        emailClient.send(email);
    } catch (IOException | TimeoutException e) {
        throw new EmailSendFailedException(
            "이메일 전송 실패. email=" + email,
            e
        );
    }
}
```

```java
// Good - 함수 사용
try {
    emailService.sendEmail("test@test.com");
} catch (EmailSendFailedException e) {
    System.out.println("이메일 전송 실패");
}
```

- 호출자는 하나의 예외만 알면 된다
- 내부 기술 구현이 숨겨진다
- 예외 처리 코드가 단순해진다
- 유지보수가 쉬워진다

## 예제 3

```java
// Bad
public void processPayment(Order order)
        throws NetworkException, BankException, TimeoutException {

    paymentGateway.process(order);
}
```

```java
try {
    processPayment(order);
} catch (NetworkException e) {
    retry();
} catch (BankException e) {
    cancelOrder();
} catch (TimeoutException e) {
    retry();
}
```

- 예외 종류가 많아 처리 코드가 복잡하다
- 같은 처리(retry)를 여러 곳에서 반복한다
- 호출자 관점이 아니라 구현 관점이다

```java
// Good: 호출자 기준으로 그룹화
public void processPayment(Order order) {
    try {
        paymentGateway.process(order);
    } catch (NetworkException | TimeoutException e) {
        throw new RetryablePaymentException(e);
    } catch (BankException e) {
        throw new PaymentFailedException(e);
    }
}
```

```java
try {
    processPayment(order);
} catch (RetryablePaymentException e) {
    retry();
} catch (PaymentFailedException e) {
    cancelOrder();
}
```

- 호출자가 원하는 처리 기준으로 예외가 나뉜다
- 동일한 처리 로직이 하나로 묶인다
- 코드가 훨씬 단순해진다
- 비즈니스 의도가 드러난다

## 예제 4: 외부 라이브러리를 호출하는 경우

```java
// Bad
ACMEPort port = new ACMEPort(12);
try {
    port.open();
} catch (DeviceResponseException e) {
    reportPortError(e);
    logger.log("Device response exception", e);
} catch (ATM1212UnlockedException e) {
    reportPortError(e);
logger.log("Unlock exception", e);
} catch (GMXError e) {
    reportPortError(e);
    logger.log("Device response exception", e);
} finally {
…
}
```

- 외부 라이브러리를 호출하는 경우 외부 라이브러리가 던지는 예외를 모두 잡아내야 한다
- 외부 라이브러리와의 결합도가 높아진다
- 나중에 다른 라이브러리로 갈아타면 예외 처리 코드를 모두 수정해야 한다

```java
// Good: 외부 라이브러리를 감싸는 래퍼 클래스 정의
class LocalPort {

    private final ACMEPort port;

    public LocalPort(int portNumber) {
        this.port = new ACMEPort(portNumber);
    }

    public void open() {
        try {
            port.open();
        } catch (DeviceResponseException | ATM1212UnlockedException | GMXError e) {
            throw new PortDeviceFailure("포트 열기 실패. portNumber=" + port.getPortNumber(), e);
        }
    }
}
```

```java
// Good
LocalPort port = new LocalPort(12);
try {
    port.open();
} catch (PortDeviceFailure e) {
    reportError(e);
    logger.log(e.getMessage(), e);
} finally {
    …
}
```

- 외부 라이브러리의 예외가 노출되지 않는다
- 외부 라이브러리와의 결합도가 낮아진다
- 외부 라이브러리를 갈아타도 예외 처리 코드는 그대로 유지된다
- 감싸기 기법을 사용하면 특정 업체가 API를 설계한 방식에 발목 잡히지 않는다.
- 예외 유형 하나만 처리하면 된다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드                 | 좋은 코드                  |
| ----- | --------------------- | ---------------------- |
| 예외 기준 | 내부 구현 중심              | 호출자 중심                 |
| 예외 종류 | 기술 예외(SQLException 등) | 도메인 예외(UserNotFound 등) |
| 호출 코드 | 복잡한 분기                | 단순한 처리                 |
| 결합도   | 높음 (DB, 네트워크 노출)      | 낮음                     |
| 유지보수  | 어려움                   | 쉬움                     |

## 핵심 원칙

피해야 할 것:

- 내부 기술 예외를 그대로 노출하는 것
- SQLException, IOException 등을 상위로 던지는 것
- 예외를 "발생 원인 기준"으로만 나누는 것
- 호출자가 처리하기 어려운 예외 구조

지켜야 할 것:

- 예외는 호출자의 처리 방식 기준으로 설계한다
- 저수준 예외는 도메인 예외로 변환한다
- 예외 개수를 줄이고 의미를 명확히 한다
- 동일한 처리 로직은 같은 예외로 묶는다
- 계층 간 결합도를 낮춘다