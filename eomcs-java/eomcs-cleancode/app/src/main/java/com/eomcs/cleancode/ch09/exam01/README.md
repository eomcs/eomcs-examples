# TDD 법칙 세 가지 (The Three Laws of TDD)

> **The Three Laws of TDD는 테스트 주도 개발을 매우 짧은 주기로 수행하기 위한 세 가지 규칙이다.**

1. 실패하는 단위 테스트를 작성하기 전에는 프로덕션 코드를 작성하지 않는다.
2. 실패하기에 충분한 정도를 넘어서 단위 테스트를 작성하지 않는다. 컴파일 실패도 실패다.
3. 현재 실패하는 테스트를 통과시키는 정도를 넘어서 프로덕션 코드를 작성하지 않는다.

즉, TDD는 다음과 같은 짧은 반복이다.

```text
실패하는 테스트 작성
→ 테스트를 통과할 만큼만 프로덕션 코드 작성
→ 리팩터링
→ 다음 실패 테스트 작성
```

## 예제 1

```java
// Bad - 테스트 없이 먼저 만든 프로덕션 코드
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }
}
```

```java
// Bad - 함수 사용
Calculator calculator = new Calculator();

int result = calculator.add(2, 3);
System.out.println(result);
```

- 테스트가 없어서 코드가 올바른지 자동으로 검증할 수 없다.
- 요구사항보다 많은 기능을 미리 구현했다.
- subtract(), multiply()가 당장 필요한지 알 수 없다.
- 코드 변경 후 기존 기능이 깨졌는지 확인하기 어렵다.

### 1단계: 실패하는 테스트를 먼저 작성한다
```java
// Good - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void 두_수를_더한다() {
        Calculator calculator = new Calculator();

        int result = calculator.add(2, 3);

        assertThat(result).isEqualTo(5);
    }
}
```

- 이 시점에는 Calculator가 없으므로 컴파일이 실패한다.
- 두 번째 법칙에 따르면 컴파일 실패도 실패하는 테스트다.

### 2단계: 테스트를 통과할 만큼만 프로덕션 코드를 작성한다

```java
// Good - 프로덕션 코드
public class Calculator {

    public int add(int a, int b) {
        return 5;
    }
}
```

- 아직 일반화하지 않는다.
- 현재 테스트를 통과하는 최소 코드만 작성한다.
- subtract(), multiply() 같은 미래 기능을 만들지 않는다.

### 3단계: 다음 실패 테스트를 추가한다

```java
@Test
void 다른_두_수도_더한다() {
    Calculator calculator = new Calculator();

    int result = calculator.add(10, 20);

    assertEquals(30, result);
}
```

### 4단계: 테스트를 통과하도록 일반화한다

```java
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }
}
```

- 테스트가 프로덕션 코드보다 몇 초 앞서간다.
- 필요한 만큼만 구현한다.
- 테스트가 코드의 요구사항을 설명한다.
- 과잉 구현을 막을 수 있다.

## 예제 2

```java
// Bad - 한 번에 많이 구현한 프로덕션 코드
public class PasswordValidator {

    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        return true;
    }
}
```

```java
// Bad - 함수 사용
PasswordValidator validator = new PasswordValidator();

boolean valid = validator.isValid("Abc12345");
System.out.println(valid);
```

- 테스트 없이 여러 규칙을 한꺼번에 구현했다.
- 어느 규칙이 실제 요구사항인지 테스트로 드러나지 않는다.
- 실패 시 어떤 조건 때문에 실패했는지 추적하기 어렵다.
- 구현이 테스트보다 앞서간다.

### 1단계: 첫 번째 실패 테스트
```java
class PasswordValidatorTest {

    @Test
    void 비밀번호는_8자_이상이어야_한다() {
        PasswordValidator validator = new PasswordValidator();

        boolean valid = validator.isValid("abc");

        assertFalse(valid);
    }
}
```

### 2단계: 통과할 만큼만 구현

```java
public class PasswordValidator {

    public boolean isValid(String password) {
        return password.length() >= 8;
    }
}
```

### 3단계: 다음 실패 테스트

```java
@Test
void 비밀번호가_8자_이상이면_유효하다() {
    PasswordValidator validator = new PasswordValidator();

    boolean valid = validator.isValid("abcdefgh");

    assertTrue(valid);
}
```

### 4단계: 다음 요구사항을 테스트로 추가

```java
@Test
void 비밀번호에는_숫자가_포함되어야_한다() {
    PasswordValidator validator = new PasswordValidator();

    boolean valid = validator.isValid("abcdefgh");

    assertFalse(valid);
}
```

### 5단계: 현재 테스트를 통과할 만큼만 수정

```java
public class PasswordValidator {

    public boolean isValid(String password) {
        return password.length() >= 8
                && password.matches(".*[0-9].*");
    }
}
```

- 요구사항이 테스트로 하나씩 드러난다.
- 한 번에 하나의 실패만 다룬다.
- 프로덕션 코드는 테스트가 요구한 만큼만 증가한다.
- 테스트와 프로덕션 코드가 함께 성장한다.

## 예제 3

```java
// Bad - 테스트를 너무 많이 먼저 작성
class OrderTest {

    @Test
    void 주문을_생성한다() {
        Order order = new Order("BOOK", 2);

        assertEquals("BOOK", order.getProductName());
        assertEquals(2, order.getQuantity());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(20000, order.getTotalPrice());
    }
}
```

```java
// Bad - 아직 없는 기능까지 한꺼번에 요구
Order order = new Order("BOOK", 2);
```

- 하나의 테스트가 너무 많은 기능을 요구한다.
- 아직 필요하지 않은 status, totalPrice까지 강제한다.
- 실패 원인이 많아진다.
- TDD의 두 번째 법칙을 어긴다.

### 1단계: 실패할 만큼만 테스트한다
```java
class OrderTest {

    @Test
    void 주문은_상품명과_수량으로_생성된다() {
        Order order = new Order("BOOK", 2);

        assertEquals("BOOK", order.getProductName());
        assertEquals(2, order.getQuantity());
    }
}
```

### 2단계: 통과할 만큼만 구현한다

```java
public class Order {
    private final String productName;
    private final int quantity;

    public Order(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
```

### 3단계: 다음 테스트를 추가한다

```java
@Test
void 주문의_초기_상태는_CREATED이다() {
    Order order = new Order("BOOK", 2);

    assertEquals(OrderStatus.CREATED, order.getStatus());
}
```

### 4단계: 필요한 만큼만 구현한다

```java
public class Order {
    private final String productName;
    private final int quantity;
    private final OrderStatus status = OrderStatus.CREATED;

    public Order(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
```

- 테스트가 한 번에 하나의 요구사항만 말한다.
- 실패 이유가 명확하다.
- 구현이 불필요하게 커지지 않는다.
- 작은 주기로 안정적으로 전진한다.

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드         | 좋은 코드      |
| ------ | ------------- | ---------- |
| 시작점    | 프로덕션 코드 먼저    | 실패 테스트 먼저  |
| 테스트 크기 | 한 번에 많은 요구사항  | 실패할 만큼만    |
| 구현 크기  | 예상 기능까지 미리 구현 | 통과할 만큼만    |
| 피드백    | 늦음            | 빠름         |
| 설계     | 구현 중심         | 테스트가 설계 압박 |
| 변경 안정성 | 낮음            | 높음         |


## 핵심 원칙

피해야 할 것:

- 테스트 없이 프로덕션 코드를 먼저 작성하는 것
- 실패하기에 충분한 범위를 넘어서 테스트를 많이 작성하는 것
- 현재 실패 테스트와 무관한 프로덕션 코드를 미리 구현하는 것
- 여러 요구사항을 한 테스트에 몰아넣는 것
- “나중에 필요할 것 같아서” 기능을 추가하는 것

지켜야 할 것:

- 실패하는 테스트를 먼저 작성한다.
- 컴파일 실패도 실패 테스트로 본다.
- 실패를 보여줄 만큼만 테스트를 작성한다.
- 현재 실패 테스트를 통과할 만큼만 프로덕션 코드를 작성한다.
- 작은 주기로 테스트와 프로덕션 코드를 함께 성장시킨다.