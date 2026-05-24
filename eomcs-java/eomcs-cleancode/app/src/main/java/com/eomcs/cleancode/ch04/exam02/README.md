# 코드로 의도를 표현하라 (Explain Yourself in Code)

> **주석으로 설명하지 말고, 코드 자체로 의도를 드러내라**

- 좋은 코드는 읽는 사람이 “해석”하지 않아도 이해된다
- 이름과 구조로 의미를 표현해야 한다

👉 목표:

> **코드 = 설명**

## 예제 1

```java
// Bad: 주석으로 설명

// check if the user is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) &&
    (employee.age > 65)) {

    giveBenefits(employee);
}
```

- flags & HOURLY_FLAG → 의미 해석 필요
- 조건이 복잡함
- 주석 없으면 이해 어려움

👉 코드가 아니라 주석이 설명 역할

```java
// Good: 코드로 설명
if (employee.isEligibleForFullBenefits()) {
    giveBenefits(employee);
}

// 내부 구현
boolean isEligibleForFullBenefits() {
    return isHourlyEmployee() && isSeniorEmployee();
}
```

- 조건을 함수로 추출
- 의미를 이름으로 표현
- 주석 제거 가능

## 예제 2

```java
// Bad: 주석으로 설명

// calculate total price
int total = 0;
for (Item item : items) {
    total += item.price * item.quantity;
}
```

- 주석이 없으면 의도 파악 어려움
- 계산 로직이 직접 노출됨

```java
// Good: 코드로 설명
int totalPrice = calculateTotalPrice(items);
```

- 함수 이름이 설명 역할
- 코드가 자연어처럼 읽힘

## 예제 3

```java
// Bad: 복잡한 조건을 주석으로 설명

// if user is active and has enough balance
if (user.getStatus() == 1 && user.getBalance() > 1000) {
    applyDiscount(user);
}
```

- status == 1 → 의미 불명확
- 주석 없으면 이해 어려움

```java
// Good: 복잡한 조건을 코드로 설명

if (isEligibleForDiscount(user)) {
    applyDiscount(user);
}

// 내부 구현
private boolean isEligibleForDiscount(User user) {
    return user.isActive() && user.hasSufficientBalance();
}
```

## 예제 4: 주석 대신 변수 이름으로 설명

```java
// Bad
int d;
d = (p * q) * 0.9;
```

- 변수 의미 없음
- 계산 목적 불명확

```java
// Good
double discountedPrice = calculateDiscountedPrice(price, quantity);
```

## 예제 5: 주석 대신 함수 분리로 설명

```java
// Bad

// validate user
if (user != null &&
    user.getEmail() != null &&
    user.getEmail().contains("@")) {

    save(user);
}
```

- 조건이 길고 복잡
- 주석 없으면 의미 파악 어려움

```java
// Good
if (isValidUser(user)) {
    save(user);
}

// 내부 구현
private boolean isValidUser(User user) {
    return user != null && isValidEmail(user.getEmail());
}
```

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드  | 좋은 코드    |
| --- | ------ | -------- |
| 설명  | 주석     | 코드       |
| 조건  | 복잡한 표현 | 의미 있는 함수 |
| 변수  | 의미 없음  | 명확한 이름   |
| 가독성 | 낮음     | 높음       |

## 핵심 원칙

피해야 할 것:

- 주석으로 의도 설명
- 의미 없는 변수/함수 이름
- 복잡한 조건 직접 작성

지켜야 할 것:

- 함수 이름으로 설명
- 변수 이름으로 의미 표현
- 조건은 함수로 추출