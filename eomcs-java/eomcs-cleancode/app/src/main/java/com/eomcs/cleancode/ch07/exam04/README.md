# 예외에 의미를 제공하라 (Provide Context with Exceptions)

> **예외를 던질 때 단순히 “실패했다”만 알리지 말고, 무엇을 하다가, 어떤 대상에서, 왜 실패했는지를 함께 담아야 한다.**

- 스택 트레이스가 오류 위치는 알려주지만, 실패한 작업의 의도까지는 알려주지 못한다.
- 예외 메시지에 실패한 작업과 실패 유형을 포함해야 한다.

## 예제 1

```java
// Bad - 함수 정의
public User findUser(Long userId) {
    User user = userRepository.findById(userId);

    if (user == null) {
        throw new RuntimeException("Not found");
    }

    return user;
}
```

```java
// Bad - 함수 사용
try {
    User user = userService.findUser(10L);
    System.out.println(user.getName());
} catch (RuntimeException e) {
    logger.error(e.getMessage(), e);
}
```

- "Not found"만 보고는 무엇을 찾지 못했는지 알기 어렵다.
- 어떤 userId에서 실패했는지 알 수 없다.
- 로그만 보고 원인을 추적하기 어렵다.

```java
// Good - 함수 정의
public User findUser(Long userId) {
    User user = userRepository.findById(userId);

    if (user == null) {
        throw new UserNotFoundException(
            "사용자를 찾을 수 없습니다. userId=" + userId
        );
    }

    return user;
}
```

```java
// Good - 함수 사용
try {
    User user = userService.findUser(10L);
    System.out.println(user.getName());
} catch (UserNotFoundException e) {
    logger.error(e.getMessage(), e);
}
```

- 실패한 작업이 “사용자 조회”임을 알 수 있다.
- 실패한 대상 userId=10이 로그에 남는다.
- 예외 타입만 봐도 원인이 명확하다.
- 디버깅할 때 호출자에게 다시 물어보지 않아도 된다.

## 예제 2

```java
// Bad - 함수 정의
public void pay(Order order) {
    if (!paymentGateway.approve(order.getTotalPrice())) {
        throw new RuntimeException("Payment failed");
    }

    order.markPaid();
}
```

```java
// Bad - 함수 사용
try {
    orderService.pay(order);
} catch (RuntimeException e) {
    logger.error(e.getMessage(), e);
}
```

- 결제 실패 원인이 불명확하다.
- 어떤 주문에서 실패했는지 알 수 없다.
- 금액, 주문 번호 같은 핵심 정보가 없다.

```java
// Good - 함수 정의
public void pay(Order order) {
    boolean approved = paymentGateway.approve(order.getTotalPrice());

    if (!approved) {
        throw new PaymentFailedException(
            "결제 승인 실패. orderId=" + order.getId()
                + ", amount=" + order.getTotalPrice()
        );
    }

    order.markPaid();
}
```

```java
// Good - 함수 사용
try {
    orderService.pay(order);
} catch (PaymentFailedException e) {
    logger.error(e.getMessage(), e);
}
```

- 어떤 주문의 결제가 실패했는지 알 수 있다.
- 실패한 금액이 함께 기록된다.
- 로그만으로 장애 분석이 쉬워진다.
- 예외 메시지가 운영 환경에서 바로 쓸 수 있는 정보가 된다.

## 예제 3

```java
// Bad - 함수 정의
public Product loadProduct(String productCode) {
    try {
        return externalProductApi.fetch(productCode);
    } catch (IOException e) {
        throw new RuntimeException("API error");
    }
}
```

```java
// Bad - 함수 사용
try {
    Product product = productService.loadProduct("P-100");
    System.out.println(product.getName());
} catch (RuntimeException e) {
    logger.error(e.getMessage(), e);
}
```

- 외부 API 호출 중 어떤 상품에서 실패했는지 알기 어렵다.
- 원래 발생한 IOException의 의미가 약해진다.
- "API error"는 너무 일반적이다.

```java
// Good - 함수 정의
public Product loadProduct(String productCode) {
    try {
        return externalProductApi.fetch(productCode);
    } catch (IOException e) {
        throw new ProductLoadException(
            "상품 정보 조회 실패. productCode=" + productCode,
            e
        );
    }
}
```

```java
// Good - 함수 사용
try {
    Product product = productService.loadProduct("P-100");
    System.out.println(product.getName());
} catch (ProductLoadException e) {
    logger.error(e.getMessage(), e);
}
```

- 실패한 작업이 “상품 정보 조회”임을 알 수 있다.
- 실패한 대상 productCode=P-100이 남는다.
- 원인 예외 IOException을 함께 보존한다.
- 상위 계층에서는 도메인 의미가 있는 예외로 처리할 수 있다.

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드                 | 좋은 코드                              |
| ------ | --------------------- | ---------------------------------- |
| 예외 메시지 | `"Error"`, `"Failed"` | `"상품 정보 조회 실패. productCode=P-100"` |
| 예외 타입  | `RuntimeException`    | `ProductLoadException`             |
| 실패 대상  | 알 수 없음                | ID, 코드, 금액 등 포함                    |
| 원인 예외  | 사라짐                   | `cause`로 보존                        |
| 로그 분석  | 추가 추적 필요              | 로그만으로 상황 파악 가능                     |



## 핵심 원칙

피해야 할 것:

- "Error", "Failed", "Not found"처럼 모호한 메시지
- 무조건 RuntimeException으로 감싸는 것
- 원인 예외를 버리는 것
- 실패한 대상 정보를 남기지 않는 것
- 로그를 봐도 어떤 작업이 실패했는지 알 수 없는 코드

지켜야 할 것:

- 예외 메시지에 실패한 작업을 적는다.
- 예외 메시지에 실패한 대상을 적는다.
- 가능한 경우 원인 예외를 함께 전달한다.
- 예외 타입 자체가 의미를 갖게 만든다.
- catch에서 바로 로그로 남길 수 있을 만큼 충분한 정보를 제공한다.