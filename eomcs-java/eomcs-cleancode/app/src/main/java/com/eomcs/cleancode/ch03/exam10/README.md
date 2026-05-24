# 오류 코드보다 예외를 사용하라! (Prefer Exceptions to Returning Error Codes)

> **중복은 소프트웨어에서 모든 악의 근원이다**

- 같은 코드가 여러 곳에 반복되면 변경 비용이 커진다.
- 하나를 수정하면 다른 곳도 함께 수정해야 하고, 하나라도 빠뜨리면 버그가 된다.

## 예제 1

```java
// Bad: 검증 로직이 중복됨
public void createUser(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
    }

    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }

    userRepository.save(user);
}

public void updateUser(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
    }

    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }

    userRepository.update(user);
}
```

위 코드에서는 다음과 같이 사용자 검증 로직이 두 번 반복된다.

```java
// 중복된 부분
if (user.getName() == null || user.getName().isBlank()) {
    throw new IllegalArgumentException("Invalid name");
}

if (user.getEmail() == null || !user.getEmail().contains("@")) {
    throw new IllegalArgumentException("Invalid email");
}
```

- 검증 규칙이 바뀌면 여러 곳을 수정해야 한다
- 한 곳만 수정하고 다른 곳을 놓치기 쉽다
- 코드가 길어지고 핵심 로직이 흐려진다

```java
// Good: 중복 제거
public void createUser(User user) {
    validateUser(user);
    userRepository.save(user);
}

public void updateUser(User user) {
    validateUser(user);
    userRepository.update(user);
}

private void validateUser(User user) {
    validateName(user);
    validateEmail(user);
}

private void validateName(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
    }
}

private void validateEmail(User user) {
    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }
}
```

- 중복된 검증 로직을 validateUser()로 추출했다.
- 검증 규칙 변경 시 한 곳만 수정하면 된다
- createUser()와 updateUser()의 핵심 흐름이 명확하다
- 테스트하기 쉬워진다
- 코드 재사용성이 높아진다

## 예제 2

```java
// Bad: 이메일 생성 코드 중복됨
public void sendWelcomeEmail(User user) {
    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject("Welcome");
    email.setBody("Hello " + user.getName());

    emailService.send(email);
}

public void sendPasswordResetEmail(User user) {
    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject("Password Reset");
    email.setBody("Reset your password, " + user.getName());

    emailService.send(email);
}
```
이메일 객체를 만드는 코드가 반복된다.

```java
// 중복된 부분
Email email = new Email();
email.setTo(user.getEmail());
email.setSubject(...);
email.setBody(...);

emailService.send(email);
```

- 이메일 생성 방식이 바뀌면 여러 메서드를 수정해야 한다
- from, template, trackingId 같은 필드가 추가되면 중복 수정 발생
- 이메일 전송 흐름이 여러 곳에 흩어진다

```java
// Good: 공통 생성 로직 추출
public void sendWelcomeEmail(User user) {
    sendEmail(user, "Welcome", "Hello " + user.getName());
}

public void sendPasswordResetEmail(User user) {
    sendEmail(user, "Password Reset", "Reset your password, " + user.getName());
}

private void sendEmail(User user, String subject, String body) {
    Email email = createEmail(user, subject, body);
    emailService.send(email);
}

private Email createEmail(User user, String subject, String body) {
    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject(subject);
    email.setBody(body);
    return email;
}
```

- 중복된 이메일 생성 및 전송 흐름을 분리했다.
- 이메일 생성 규칙이 한 곳에 모인다
- 전송 방식 변경 시 sendEmail()만 수정하면 된다
- 각 public 메서드는 자신의 의도만 드러낸다

## 예제 3

```java
// Bad: 가격 계산 로직이 중복됨
public int calculateRegularPrice(Order order) {
    int total = 0;

    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }

    return total;
}

public int calculateDiscountedPrice(Order order) {
    int total = 0;

    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }

    return (int) (total * 0.9);
}
```

두 함수는 거의 같은 계산 로직을 가지고 있다.

```java
// 중복된 부분
int total = 0;

for (Item item : order.getItems()) {
    total += item.getPrice() * item.getQuantity();
}
```

- 가격 계산 방식이 바뀌면 두 곳 모두 수정해야 한다
- 할인 로직과 기본 합계 계산 로직이 섞여 있다
- 중복이 작아 보여도 시간이 지나면 계속 커진다

```java
// Good: 공통 계산 로직 추출
public int calculateRegularPrice(Order order) {
    return calculateTotalPrice(order);
}

public int calculateDiscountedPrice(Order order) {
    return applyDiscount(calculateTotalPrice(order));
}

private int calculateTotalPrice(Order order) {
    int total = 0;

    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }

    return total;
}

private int applyDiscount(int totalPrice) {
    return (int) (totalPrice * 0.9);
}
```

- 공통 계산 로직을 calculateTotalPrice()로 추출했다.
- 기본 가격 계산은 한 곳에서 관리
- 할인 정책은 applyDiscount()로 분리
- 각 함수가 하나의 역할만 담당

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드      | 좋은 코드                       |
| ------ | ---------- | --------------------------- |
| 검증     | 여러 메서드에 반복 | `validateUser()`로 추출        |
| 이메일 생성 | 각 메서드에서 반복 | `createEmail()`로 추출         |
| 가격 계산  | 계산식 반복     | `calculateTotalPrice()`로 추출 |
| 변경 비용  | 높음         | 낮음                          |
| 버그 가능성 | 높음         | 낮음                          |

## 핵심 원칙

피해야 할 것: 

- 복사해서 붙여넣은 코드
- 여러 곳에 흩어진 같은 규칙
- 비슷하지만 살짝 다른 반복 코드
- 반복되는 조건문과 계산식

지켜야 할 것: 

- 중복된 코드는 함수로 추출
- 중복된 데이터는 상수로 추출
- 중복된 개념은 클래스로 추출
- 하나의 정책은 한 곳에서 관리

### DRY 원칙의 핵심

DRY는 단순히 “코드를 복사하지 말라”는 뜻만은 아니다.

> 하나의 지식은 시스템 안에서 단 하나의 표현만 가져야 한다

즉, 중복 코드뿐 아니라 다음도 중복이다.

- 같은 검증 규칙의 반복
- 같은 비즈니스 정책의 반복
- 같은 계산식의 반복
- 같은 문자열/상수의 반복
- 같은 조건문의 반복
