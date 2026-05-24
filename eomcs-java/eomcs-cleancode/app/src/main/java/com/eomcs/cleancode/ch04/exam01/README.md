# 주석은 나쁜 코드를 보완하지 못한다 (Comments Do Not Make Up for Bad Code)

> **나쁜 코드를 주석으로 덮지 마라. 코드를 고쳐라.**

- 주석은 문제를 해결하지 않는다
- 나쁜 코드는 그대로 남는다
- 결국 코드 + 주석 둘 다 이해해야 한다

👉 진짜 해결책은 **코드 개선**

## 예제 1

```java
// Bad: 주석으로 설명

// Check if user is eligible for discount
if (user.getAge() > 18 &&
    user.getStatus() == 1 &&
    user.getBalance() > 1000) {

    applyDiscount(user);
}
```

- 코드 자체는 이해하기 어려움
- 주석 없으면 의미 파악 불가능

👉 문제는 주석이 아니라 코드 자체

```java
// Good: 주석 제거
if (isEligibleForDiscount(user)) {
    applyDiscount(user);
}

// 내부 구현
private boolean isEligibleForDiscount(User user) {
    return user.getAge() > 18 &&
           user.isActive() &&
           user.getBalance() > 1000;
}
```

- 주석 제거 가능
- 코드 자체가 의미를 설명

## 예제 2

```java
// Bad: 주석으로 덮기

// initialize variables
int x = 0;
int y = 0;

// calculate total
for (Item item : items) {
    x += item.price * item.quantity;
}

// apply tax
y = (int)(x * 1.1);
```

- 변수 이름이 의미 없음 (x, y)
- 주석 없으면 이해 불가
- 주석이 코드 의미를 대신함

```java
// Good
int totalPrice = calculateTotalPrice(items);
int totalPriceWithTax = applyTax(totalPrice);
```

- 변수 이름으로 의미 표현
- 함수로 로직 분리
- 주석 필요 없음

## 예제 3

```java
// Bad: 주석이 문제를 숨기는 경우

// This method handles user processing
public void process(User user) {
    if (user != null) {
        if (user.getStatus() == 1) {
            database.save(user);
        }
    }
}
```

- process → 의미 없음
- 조건 로직이 불명확
- 주석이 없으면 이해 불가

👉 주석은 단지 문제를 가림

```java
// Good
public void saveActiveUser(User user) {
    if (isActiveUser(user)) {
        saveUser(user);
    }
}

// 내부 구현
private boolean isActiveUser(User user) {
    return user != null && user.isActive();
}
```

- 함수 이름이 역할 설명
- 조건을 의미 있는 이름으로 추출

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드   | 좋은 코드          |
| --- | ------- | -------------- |
| 설명  | 주석에 의존  | 코드로 표현         |
| 변수  | x, y    | totalPrice     |
| 함수  | process | saveActiveUser |
| 가독성 | 낮음      | 높음             |

## 핵심 원칙

피해야 할 것:

- 나쁜 코드를 주석으로 설명
- 의미 없는 변수/함수 이름
- 주석에 의존하는 구조

지켜야 할 것:

- 코드 자체로 의미 표현
- 이름을 개선
- 함수로 분리
- 주석 없이도 이해 가능하게 작성