# 문제 영역에서 가져온 이름을 사용하라(Use Problem Domain Names)

> **사용자가 다루는 도메인(비즈니스)의 용어를 그대로 사용하라**

- 개발자는 코드로 “문제”를 해결한다
- 따라서 코드에는 **비즈니스 개념(Problem Domain)**이 드러나야 한다

👉 코드가 기술이 아니라 “업무를 설명”해야 한다

✅ 사용해야 하는 경우

- 비즈니스 로직 표현
- 도메인 모델
- 업무 규칙
- 사용자 개념

👉 개발자가 아닌 사람과도 공유 가능한 개념

## 예제 1

```java
// Bad
class DataProcessor {
    void process(Data data) { ... }
}
```

- Data, Processor → 너무 일반적
- 무엇을 처리하는지 알 수 없음
- 비즈니스 의미가 완전히 사라짐

```java
// Good
class OrderProcessor {
    void processOrder(Order order) { ... }
}
```

- Order → 도메인 개념
- processOrder → 업무 의미 명확

## 예제 2

```java
// Bad
class AccountManager {
    void update(Data data) { ... }
}
```

- AccountManager → 역할 모호
- Data → 의미 없음
- update → 어떤 비즈니스 동작인지 불명확

```java
// Good
class AccountService {
    void updateAccountBalance(Account account) { ... }
}
```

- Account → 도메인 객체
- updateAccountBalance → 실제 업무 표현

## 예제 3

```java
// Bad
class Calculator {
    int calculate(int a, int b) { ... }
}
```

- 무엇을 계산하는지 알 수 없음
- 비즈니스 의미 없음

```java
// Good
class InvoiceCalculator {
    int calculateTotalAmount(Invoice invoice) { ... }
}
```

- Invoice → 도메인 개념
- calculateTotalAmount → 업무 의미 명확

## 예제 4

```java
// Bad
class ItemHandler {
    void handle(Item item) { ... }
}
```

- Item, handle → 너무 일반적
- 실제 비즈니스 의미 없음

```java
// Good
class ShoppingCart {
    void addProduct(Product product) { ... }
}
```

- ShoppingCart, Product → 도메인 언어
- 실제 사용자 개념과 일치

## 나쁜 코드 vs 좋은 코드

| 구분 | 나쁜 코드          | 문제     | 좋은 코드             |
| -- | -------------- | ------ | ----------------- |
| 일반 | DataProcessor  | 의미 없음  | OrderProcessor    |
| 모호 | AccountManager | 역할 불명확 | AccountService    |
| 계산 | Calculator     | 대상 불명확 | InvoiceCalculator |
| 처리 | ItemHandler    | 의미 없음  | ShoppingCart      |

### Solution Domain vs Problem Domain:

| 구분              | 의미         | 예                        | 선택 기준           |
| --------------- | ---------- | ------------------------ | ------------------|
| Solution Domain | 기술/구현 영역   | Stack, Cache, Parser     | 알고리즘/자료구조 중심 | 
| Problem Domain  | 비즈니스/업무 영역 | Order, Customer, Invoice | 비즈니스 로직 중심 |

## 핵심 원칙

피해야 할 것:

- Data, Info, Manager 같은 일반적인 이름
- 비즈니스 의미를 숨기는 이름

지켜야 할 것:

- 도메인 전문가가 사용하는 용어 사용
- 코드가 비즈니스를 설명하도록 작성
- 기술보다 의미를 우선