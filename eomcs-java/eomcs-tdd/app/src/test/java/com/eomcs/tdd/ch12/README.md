# Chapter 12: Addition, Finally

## 학습 목표

- **큰 문제를 단순한 예제로 축소하기:** 혼합 통화 덧셈($5 + 10 CHF)이라는 복잡하고 큰 문제 대신, 같은 통화의 단순한 덧셈($5 + $5 = $10)부터 시작하여 점진적인 진척을 만드는 방법을 익힌다.
- **설계를 위한 메타포(Metaphor) 활용:** 서로 다른 통화를 더하는 것을 '여러 통화가 섞인 지갑'이나 '수학적 수식(Expression)'과 같은 메타포로 치환하여 객체 지향적인 도메인 모델을 설계하는 능력을 익힌다.
- **객체의 책임 분리 (Separation of Responsibilities):** 모든 기능을 하나의 객체에 몰아넣는 대신, 환율 변환과 같은 외부적 요인의 계산은 새로운 객체(Bank)에 위임함으로써 핵심 객체들이 외부 세계를 모른 채 유연성을 유지하도록 설계하는 원칙을 이해한다.
- **원하는 API를 상상하며 테스트 작성:** 아직 존재하지 않는 인터페이스나 클래스라도 먼저 가장 이상적이고 완벽한 형태의 API 호출 구조를 상상하여 테스트 코드로 작성하는 TDD 기법을 체득한다.

## 실습

- $5 + 10 CHF = $10 (rate 2:1)
- **$5 + $5 = $10  ← 이번 장에서 다루는 항목**

## 핵심 설계: Expression 패턴

### 왜 즉시 계산하지 않는가?

`$5 + 10 CHF` 같은 다중 통화 덧셈은 환율 없이 계산할 수 없다.
환율은 Bank가 알고 있다. 따라서:

```
$5.plus(10 CHF)  →  Sum(augend=$5, addend=10CHF)  표현식으로 저장
bank.reduce(sum, "USD")  →  $10   환율을 적용해 최종 계산
```

덧셈 결과를 **Expression(표현식)** 으로 표현하면 복잡한 식도 조합할 수 있다:
`($5 + 10 CHF) × 2`  =  `Sum(Money, Money).times(2)`

### 등장 객체

| 객체 | 역할 |
|---|---|
| `Expression` | 인터페이스: `reduce(Bank, String)` 정의. Money와 Sum이 구현. |
| `Money` | 단일 통화 금액. `plus()`로 Sum 반환. Expression 구현. |
| `Sum` | 두 Expression의 합. augend + addend. Expression 구현. |
| `Bank` | 환율 보유. `reduce(Expression, String)`으로 최종 Money 반환. |

## 단계별 진행

### step01: Red — $5 + $5 테스트 작성

```java
Money five = Money.dollar(5);
Expression sum = five.plus(five);
Bank bank = new Bank();
Money reduced = bank.reduce(sum, "USD");
assertEquals(Money.dollar(10), reduced);
```

- `plus()`, `Bank`, `Expression` 모두 없음 → 컴파일 오류 (Red)

### step02: Green — 가장 단순하게 통과시키기

가장 단순한 구현:
- `Expression` 인터페이스 생성
- `Money.plus()` → `Sum` 반환
- `Bank.reduce()` → `Expression.reduce()` 위임
- `Sum.reduce()` → augend + addend를 target currency로 계산
- `Money.reduce()` → amount 반환 (같은 통화일 때)

### step03: Refactor — Money도 Expression 구현

`bank.reduce(Money.dollar(5), "USD")` 처럼 Money를 직접 reduce할 수 있어야 한다.
Money도 Expression 인터페이스를 구현하도록 한다.
