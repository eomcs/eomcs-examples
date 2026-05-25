# 적절한 행 길이를 유지하라 (Vertical Formatting)

> 세로 형식은 코드가 위에서 아래로 읽힐 때, 개념들이 어떤 순서와 거리로 배치되어야 하는지를 다룬다.

## 예제 1: 신문 기사처럼 작성하라 (The Newspaper Metaphor)

> **소스 파일은 신문 기사처럼 읽혀야 한다**

신문 기사는 보통 다음 순서로 구성된다.

1. 제목
2. 핵심 요약
3. 중요한 내용
4. 세부 정보

코드도 마찬가지다.

- 위쪽에는 고수준 개념과 알고리즘을 설명
- 아래쪽에는 세부 구현
- 읽을수록 점점 구체적인 내용(가장 저차원 함수와 세부 내역)

```java
// Bad
public class OrderService {

    private void saveToDatabase(Order order) {
        database.save(order);
    }

    private int calculateTotal(Order order) {
        int total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void processOrder(Order order) {
        int total = calculateTotal(order);
        saveToDatabase(order);
        sendConfirmationEmail(order, total);
    }

    private void sendConfirmationEmail(Order order, int total) {
        emailService.send(order.getUserEmail(), "Total: " + total);
    }
}
```

- 세부 구현이 먼저 등장한다
- 핵심 함수인 processOrder()가 중간에 숨어 있다
- 읽는 사람이 파일을 위아래로 이동해야 한다

```java
// Good
public class OrderService {

    public void processOrder(Order order) {
        int total = calculateTotal(order);
        saveToDatabase(order);
        sendConfirmationEmail(order, total);
    }

    private int calculateTotal(Order order) {
        int total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    private void saveToDatabase(Order order) {
        database.save(order);
    }

    private void sendConfirmationEmail(Order order, int total) {
        emailService.send(order.getUserEmail(), "Total: " + total);
    }
}
```

- 가장 중요한 public 메서드가 먼저 나온다
- 아래로 내려가며 세부 구현이 등장한다
- 전체 흐름을 신문 기사처럼 빠르게 파악할 수 있다

## 예제 2: 개념은 빈 행으로 분리하라 (Vertical Openness Between Concepts)

> 서로 다른 개념은 빈 줄로 분리하라

빈 줄은 코드에서 “새로운 생각이 시작된다”는 신호다.

```java
// Bad
public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    public void register(User user) {
        validate(user);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user);
    }
    private void validate(User user) {
        if (user.getEmail() == null) {
            throw new IllegalArgumentException();
        }
    }
}
```

- 필드와 메서드가 붙어 있다
- 메서드 간 구분이 어렵다
- 코드가 한 덩어리처럼 보인다

```java
// Good
public class UserService {

    private UserRepository userRepository;
    private EmailService emailService;

    public void register(User user) {
        validate(user);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user);
    }

    private void validate(User user) {
        if (user.getEmail() == null) {
            throw new IllegalArgumentException();
        }
    }
}
```

- 필드와 메서드가 구분된다
- 메서드 단위가 눈에 들어온다
- 코드의 논리적 단락이 명확해진다

## 예제 3: 세로 밀집도 (Vertical Density)

> 서로 밀접한 코드는 가까이 붙여라

관련 있는 코드 사이에 불필요한 빈 줄이나 주석을 넣으면 흐름이 끊긴다.

```java
// Bad
public class ReportConfig {

    // report title
    private String title;

    // report format
    private String format;

    public ReportConfig(String title, String format) {
        this.title = title;
        this.format = format;
    }
}
```

- 필드 사이의 주석이 불필요하다
- 관련 있는 필드들이 멀어져 보인다
- 코드가 실제보다 더 복잡해 보인다

```java
// Good
public class ReportConfig {

    private String title;
    private String format;

    public ReportConfig(String title, String format) {
        this.title = title;
        this.format = format;
    }
}
```

- 관련 있는 필드가 가까이 붙어 있다
- 불필요한 주석이 제거되었다
- 한눈에 구조를 파악할 수 있다

## 예제 4: 수직 거리 (Vertical Distance)

> 서로 밀접한 개념은 세로로 가까이 배치하라

관련 있는 변수, 함수, 호출 관계는 멀리 떨어져 있으면 안 된다.

### 1) 변수는 사용되는 곳 가까이에 선언하라

```java
// Bad
public void printReport(List<User> users) {
    int activeUserCount = 0;
    String title = "Active Users";

    for (User user : users) {
        if (user.isActive()) {
            activeUserCount++;
        }
    }

    System.out.println(title);
    System.out.println(activeUserCount);
}
```

- title은 마지막에 사용되는데 너무 일찍 선언되었다
- 변수의 목적을 기억해야 한다

```java
// Good
public void printReport(List<User> users) {
    int activeUserCount = countActiveUsers(users);

    String title = "Active Users";
    System.out.println(title);
    System.out.println(activeUserCount);
}
```

더 좋게 개선한 예:

```java
// Good
public void printReport(List<User> users) {
    System.out.println("Active Users");
    System.out.println(countActiveUsers(users));
}
```

### 2) 호출하는 함수와 호출되는 함수는 가까이 둔다

```java
// Bad
public void register(User user) {
    validate(user);
    save(user);
    sendWelcomeEmail(user);
}

private void sendWelcomeEmail(User user) {
    emailService.send(user.getEmail(), "Welcome");
}

private void save(User user) {
    userRepository.save(user);
}

private void validate(User user) {
    if (user.getEmail() == null) {
        throw new IllegalArgumentException();
    }
}
```

- register()에서 호출한 순서와 함수 정의 순서가 다르다
- 읽는 사람이 위아래로 이동해야 한다

```java
// Good
public void register(User user) {
    validate(user);
    save(user);
    sendWelcomeEmail(user);
}

private void validate(User user) {
    if (user.getEmail() == null) {
        throw new IllegalArgumentException();
    }
}

private void save(User user) {
    userRepository.save(user);
}

private void sendWelcomeEmail(User user) {
    emailService.send(user.getEmail(), "Welcome");
}
```

- 호출 순서와 정의 순서가 일치한다
- 관련 함수들이 가까이에 있다
- 읽는 흐름이 자연스럽다

### 3) 서로 관련된 개념은 물리적으로 가까이 배치한다

- 단순히 "호출 관계"만 가까이 두는 것이 아니다
- **의미적으로 연관된 코드도 함께 배치하는 것이 좋다**
- **비슷한 동작을 수행하는 일군의 함수도 함께 배치하는 것이 좋다**

```java
// Bad
public class UserService {

    public void register(User user) {
        validate(user);
        save(user);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private void save(User user) {
        userRepository.save(user);
    }

    private void validate(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
```

- validate()와 isValidEmail()은 강하게 관련됨
- 하지만 코드상으로 떨어져 있음
- 읽는 순서: validate() → 아래로 이동 → 다시 위로 이동 → isValidEmail()
    - 인지 비용 증가

```java
// Good
public class UserService {

    public void register(User user) {
        validate(user);
        save(user);
    }

    private void validate(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private void save(User user) {
        userRepository.save(user);
    }
}
```

- validate()와 isValidEmail()을 붙여서 배치
- 관련 개념을 하나의 "덩어리"로 만듦
- 읽는 순서: validate() → isValidEmail() → 이해 완료

```java
// Good
public class Assert {

    static public void assertTrue(String message, boolean condition) {
        if (!condition)
            fail(message);
    }

    static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    static public void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }
...
```

- assertTrue()와 assertFalse()가 함께 배치되어 있다.
- 명명법이 똑같고 기본 기능이 유사하고 간단하다.
- **이런 경우 종속적인 관계가 없더라도 가까이 배치하는 것이 좋다.**

## 예제 5: 세로 순서 (Vertical Ordering)

> 중요한 개념은 위에, 세부 구현은 아래에 배치하라

코드는 위에서 아래로 읽히며 점점 구체화되어야 한다.

```java
// Bad
public class PaymentService {

    private void writeLog(Payment payment) {
        logger.info("paid: " + payment.getId());
    }

    private void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void pay(Payment payment) {
        validatePayment(payment);
        savePayment(payment);
        writeLog(payment);
    }

    private void validatePayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
```

- 세부 함수가 먼저 나온다
- 핵심 public 함수가 중간에 있다
- 위에서 아래로 흐름을 읽기 어렵다

```java
// Good
public class PaymentService {

    public void pay(Payment payment) {
        validatePayment(payment);
        savePayment(payment);
        writeLog(payment);
    }

    private void validatePayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    private void writeLog(Payment payment) {
        logger.info("paid: " + payment.getId());
    }
}
```

- 가장 중요한 pay()가 먼저 나온다
- 아래로 내려가며 세부 함수가 나온다
- Stepdown Rule과도 연결된다

## 나쁜 코드 vs 좋은 코드

| 주제                 | 나쁜 코드           | 좋은 코드         |
| ------------------ | --------------- | ------------- |
| Newspaper Metaphor | 세부 구현이 먼저 나옴    | 고수준 개념이 먼저 나옴 |
| Vertical Openness  | 개념들이 붙어 있음      | 빈 줄로 개념 분리    |
| Vertical Density   | 관련 코드 사이에 잡음 있음 | 관련 코드를 가까이 둠  |
| Vertical Distance  | 관련 요소가 멀리 있음    | 관련 요소를 가까이 둠  |
| Vertical Ordering  | 순서가 뒤섞임         | 위에서 아래로 구체화   |


## 핵심 원칙

> 세로 형식은 코드가 위에서 아래로 자연스럽게 읽히도록 개념의 거리와 순서를 조정하는 기술이다.