# 서술적인 이름을 사용하라! (Use Descriptive Names)

> **함수 이름은 길어도 괜찮다. 중요한 것은 “무엇을 하는지” 명확히 드러내는 것이다**

- 짧은 이름보다 **설명적인 이름**이 더 중요하다
- 함수 이름은 코드의 의도를 설명하는 문장이어야 한다

👉 함수 이름 = 주석을 대체

> "코드를 읽으면서 짐작했던 기능을 각 루틴이 그대로 수행한다면 깨끗한 코드라 불러도 되겠다" - 워드 커닝햄(Ward Cunningham)

## 예제 1

```java
// Bad: 짧지만 모호함
public void process(User user) {
    if (user.getStatus() == 1) {
        doStuff(user);
    }
}
```

- process → 무엇을 하는지 모름
- doStuff → 완전히 의미 없음
- status == 1 → 무엇을 의미하는지 모름
- 👉 코드 전체를 읽어야 이해 가능

```java
// Good: 설명적인 이름
public void activateUserIfPending(User user) {
    if (user.isPendingActivation()) {
        sendActivationEmail(user);
    }
}
```

- activateUserIfPending → 조건 + 행동 포함
- isPendingActivation → 상태 의미 명확
- sendActivationEmail → 수행 작업 명확
- 👉 코드가 자연어처럼 읽힘

## 예제 2

```java
// Bad: 의미가 모호한 짧은 이름
public void calc(int a, int b) {
    return a * b * 0.9;
}
```

- calc → 무엇을 계산하는지 모름

```java
// Good: 설명적인 이름
public double calculateDiscountedPrice(double price, int quantity) {
    return price * quantity * 0.9;
}
```

- 계산 대상과 목적이 명확
- 이름만 보고 이해 가능

## 예제 3

```java
// Bad
boolean flag;
```

- flag → 무엇을 의미하는지 모름

```java
// Good
boolean isUserEligibleForDiscount;
```

- 조건의 의미를 이름에 포함
- 별도의 설명 필요 없음

## 예제 4

```java
// Bad
if (user.getAge() > 18 && user.getStatus() == 1 && user.getBalance() > 1000) {
    applyDiscount(user);
}
```

- 조건이 복잡함
- 의미 파악 어려움

```java
// Good
if (isEligibleForPremiumDiscount(user)) {
    applyDiscount(user);
}
```

```java
// 내부 구현
private boolean isEligibleForPremiumDiscount(User user) {
    return user.getAge() > 18 && user.getStatus() == 1 && user.getBalance() > 1000;
}
```

- 조건 → 함수 이름으로 추상화
- 가독성 극대화

## 예제 5

```java
// Bad
// 사용자가 활성 상태이고 잔액이 충분하면 할인 적용
if (user.isActive() && user.getBalance() > 1000) {
    applyDiscount(user);
}
```

- 조건이 복잡함
- 의미 파악 어려움

```java
// Good
if (isEligibleForDiscount(user)) {
    applyDiscount(user);
}
```

- 주석 없이도 이해 가능

## 나쁜 코드 vs 좋은 코드

| 구분 | 나쁜 코드   | 좋은 코드                     |
| -- | ------- | ------------------------- |
| 함수 | process | activateUserIfPending     |
| 함수 | calc    | calculateDiscountedPrice  |
| 변수 | flag    | isUserEligibleForDiscount |
| 조건 | 복잡한 if  | 의미 있는 함수                  |

## 핵심 원칙

❗ 피해야 할 것:

- 짧지만 의미 없는 이름
- process, doStuff 같은 일반 단어
- 주석에 의존하는 코드

✅ 지켜야 할 것:

- 이름으로 의도를 설명
- 길어도 명확하게 작성
- 조건을 함수로 추출