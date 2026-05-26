# 미확인 예외를 사용하라 (Use Unchecked Exceptions)

> **Checked Exception을 남용하지 말고, Unchecked Exception(RuntimeException)을 사용하라**

👉 이유:

- Checked Exception은 모든 호출자에게 강제로 전파됨
- 코드가 점점 오염되고 복잡해짐
- 실제로는 대부분 복구가 불가능한 경우가 많음

👉 따라서:

- 복구 불가능한 오류 → Unchecked Exception
- 호출자가 처리 가능한 경우 → Checked Exception (제한적으로)

## 예제 1: Checked Exception 남용

```java
// Bad - 함수 정의
public class DeviceController {

    public void sendShutDown() throws DeviceHandleException {
        DeviceHandle handle = getHandle();

        if (handle == null) {
            throw new DeviceHandleException("장치를 찾을 수 없음");
        }

        handle.shutdown();
    }
}
```

```java
// Bad - 함수 사용
try {
    deviceController.sendShutDown();
} catch (DeviceHandleException e) {
    e.printStackTrace();
}
```

- 모든 호출자가 예외를 처리해야 한다
- 실제로 처리할 수 없는 예외까지 강제 처리된다
- 단순히 printStackTrace() 같은 의미 없는 코드가 늘어난다
- 코드가 예외 처리로 오염된다

```java
// Good - Unchecked Exception 사용
public class DeviceController {

    public void sendShutDown() {
        DeviceHandle handle = getHandle();

        if (handle == null) {
            throw new DeviceNotFoundException("장치를 찾을 수 없음");
        }

        handle.shutdown();
    }
}
```

```java
// 함수 사용 예
deviceController.sendShutDown();
```

- 불필요한 try-catch가 사라진다
- 코드가 간결해진다
- 진짜 필요한 곳에서만 예외를 처리할 수 있다
- 정상 흐름이 명확해진다

## 예제 2

```java
// Bad - 함수 정의
public void processOrder(Order order) throws Exception {
    if (order == null) {
        throw new Exception("잘못된 주문");
    }

    if (!order.isPaid()) {
        throw new Exception("결제되지 않음");
    }
}
```

```java
// 함수 사용
try {
    processOrder(order);
} catch (Exception e) {
    System.out.println("오류 발생");
}
```

- 너무 일반적인 Exception 사용
- 어떤 오류인지 구분 불가능
- 호출자가 의미 있는 처리를 할 수 없음
- Checked Exception으로 강제됨

```java
// Good - 함수 정의
public void processOrder(Order order) {
    validateOrder(order);
    validatePayment(order);
}

private void validateOrder(Order order) {
    if (order == null) {
        throw new InvalidOrderException("잘못된 주문");
    }
}

private void validatePayment(Order order) {
    if (!order.isPaid()) {
        throw new PaymentRequiredException("결제되지 않음");
    }
}
```

```java
// Good - 함수 사용
processOrder(order);
```

- 예외 타입으로 의미가 명확하다
- 불필요한 try-catch 제거
- 코드가 간결해진다
- 필요한 경우에만 상위에서 처리 가능

## 예제 3: 예외 전파

```java
// Bad
public void service() throws SQLException {
    repository();
}

public void repository() throws SQLException {
    throw new SQLException("DB 오류");
}
```

```java
// 호출 코드
public void controller() {
    try {
        service();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

- SQLException이 상위 레이어까지 전파됨
- 계층 간 결합도 증가
- 모든 레이어가 예외에 의존

```java
// Good
public void service() {
    repository();
}

public void repository() {
    try {
        throw new SQLException("DB 오류");
    } catch (SQLException e) {
        throw new DataAccessException("DB 처리 실패", e);
    }
}
```

```java
// 호출 코드
controller();

public void controller() {
    try {
        service();
    } catch (DataAccessException e) {
        System.out.println("서비스 오류 처리");
    }
}
```

- 저수준 예외를 감춘다
- 도메인 수준 예외로 변환한다
- 계층 간 결합도 감소
- 예외 의미가 명확해진다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드 (Checked Exception 남용) | 좋은 코드 (Unchecked Exception) |
| ----- | ---------------------------- | --------------------------- |
| 예외 처리 | 모든 곳에서 강제                    | 필요한 곳에서만                    |
| 가독성   | try-catch 난무                 | 간결한 흐름                      |
| 의미    | 일반 Exception                 | 명확한 예외 타입                   |
| 결합도   | 계층 간 강한 결합                   | 느슨한 결합                      |
| 유지보수  | 수정 영향 큼                      | 영향 최소                       |


## 핵심 원칙

피해야 할 것:

- Checked Exception을 무분별하게 사용하는 것
- throws Exception 같은 포괄적인 선언
- 처리하지 못하는 예외까지 강제하는 구조
- 단순히 로그만 찍는 catch 블록

지켜야 할 것:

- 기본적으로 Unchecked Exception을 사용한다
- 복구 가능한 경우에만 Checked Exception 사용
- 예외는 의미 있는 타입으로 정의한다
- 저수준 예외는 상위 계층에 맞게 변환한다
- 예외 처리는 "필요한 위치"에서만 한다