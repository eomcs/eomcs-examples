# 복잡한 문제를 다루는 API를 설계하는 방법 II

이 단계에서는 TDD의 핵심 원칙인 "테스트를 먼저 작성하고 이를 기준으로 설계를 확장해 나가는" 방법을 다룬다. 복잡한 문제를 작은 테스트로 축소하여 시작하는 방법과, 현실 세계의 개념을 메타포로 치환하여 객체 지향적인 도메인 모델을 설계하는 방법을 체험한다.

## 개념

### 이상적인 형태의 API를 먼저 작성하라!

설계를 할 때 실제 클래스부터 만들지 말고, 현실 세계의 개념이나 모델을 '수학적 수식(Expression)'과 같은 **메타포로 치환**하여 테스트 코드를 작성하여 설계 의도를 구체화 한다. 예를 들어, 화폐의 합을 '수식(Expression)'으로 다루고 이를 축약(reduce)하는 책임을 '은행(Bank)' 객체에 위임하는 메타포를 구상한다. 이렇게 생각한 비유를 기반으로 테스트를 재작성하고, 컴파일 오류를 하나씩 해결해 나가다 보면 새로운 구조의 애플리케이션을 뼈대부터 안전하게 구현해 나갈 수 있다.

아직 존재하지 않는 인터페이스나 클래스라도 먼저 가장 이상적이고 완벽한 형태의 API 호출 구조를 상상하여 테스트 코드로 작성하는 것이 중요하다. **이상적인 API란, 객체 지향 설계 원칙에 따라 유지보수성과 확장성을 높이고 테스트를 쉽게 만드는 구조로 설계된 API 이다.** 예를 들어, 모든 기능을 하나의 객체에 몰아넣는 대신  **객체의 책임 분리(Separation of Responsibilities)** 를 통해 핵심 도메인 객체가 자신의 본질적인 역할에만 집중하도록 설계한다. 

### 잊지말자! 가짜 구현(Fake It)

API를 상상하여 테스트를 코드를 작성했으면 컴파일되도록 Expression 인터페이스와 Bank 클래스의 껍데기(stub)를 최소한으로 만든다. 완벽한 덧셈 및 환율 변환 로직을 짜는 대신, 무조건 Money.dollar(10)을 반환하도록 **가짜로 구현하여 우선 테스트를 통과시키는 데 집중** 한다. **진짜로 구현하는 것은 테스트가 통과한 후에 리팩토링 단계에서 점진적** 으로 해 나가면 된다. 이렇게 하면 복잡한 문제를 작은 단계로 나누어 해결할 수 있고, 설계의 방향성을 잃지 않으면서도 빠르게 피드백을 받을 수 있다.

### Expression 패턴

#### 왜 즉시 계산하지 않는가?

`$5 + 10 CHF` 같은 다중 통화 덧셈은 환율 없이 계산할 수 없다.
환율은 Bank가 알고 있다. 따라서:

```
$5.plus(10 CHF)  →  Sum(augend=$5, addend=10CHF)  표현식으로 저장
bank.reduce(sum, "USD")  →  $10   환율을 적용해 최종 계산
```

덧셈 결과를 **Expression(표현식)** 으로 표현하면 복잡한 식도 조합할 수 있다:
`($5 + 10 CHF) × 2`  =  `Sum(Money, Money).times(2)`

#### 등장 객체

| 객체 | 역할 |
|---|---|
| `Expression` | 인터페이스: `reduce(Bank, String)` 정의. Money와 Sum이 구현. |
| `Money` | 단일 통화 금액. `plus()`로 Sum 반환. Expression 구현. |
| `Sum` | 두 Expression의 합. augend + addend. Expression 구현. |
| `Bank` | 환율 보유. `reduce(Expression, String)`으로 최종 Money 반환. |


## 실습

> $5 + 10 CHF = $10 이라는 다중 통화 덧셈을 다루는 API를 설계한다.

### Red - 다중 통화의 덧셈 API를 설계

다중 통화 연산을 깔끔하게 처리하기 위해 두 화폐의 합을 나타내는 **수식(Expression)**과, 이 수식에 환율을 적용해 단일 통화로 변환해 주는 **은행(Bank)**이라는 메타포 도입을 상상한다. 이 아이디어를 바탕으로 코드를 직접 짜기 전, '자신이 사용하고 싶은 이상적인 API'를 역방향으로 상상하며 테스트 코드를 수정한다.

**테스트 코드 - 1) 가장 마지막에 확인할 결과물 정의하기:** 

> 먼저 연산이 모두 끝난 후 도출되어야 할 최종 결과(reduced)를 단언(assert)하는 코드를 가장 먼저 작성한다

```java
public void testSimpleAddition() {
    ...
    assertEquals(Money.dollar(10), reduced);
}
```

> **'reduce'** 는 복잡하게 얽혀 있는 다중 통화 연산(수식)을 사용자가 이해할 수 있는 하나의 깔끔한 화폐 값으로 단순화해 내는 핵심적인 책임을 표현하는 단어이다.

**테스트 코드 - 2) 환율을 적용해 결과를 만들어내는 '은행(Bank)' 메타포 적용하기:**

> reduced라는 결과값이 어떻게 만들어질지 상상해 본다. '수식(Expression)'에 환율을 적용하는 현실 세계의 주체로 '은행(Bank)'을 도출한다. 은행에 수식(sum)을 달러("USD")로 축약(reduce)하여 결과를 반환하는 코드를 추가한다.

```java
public void testSimpleAddition() {
    ...
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

**테스트 코드 - 3) Bank 객체 생성하기:** 

> bank.reduce()를 호출하기 위해서는 Bank 객체가 필요하므로 이를 생성하는 코드를 그 위에 추가한다

```java
public void testSimpleAddition() {
    ...
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

**테스트 코드 - 4) 수식(Expression) 메타포 적용하기** 

> 은행에 전달할 sum이라는 변수가 무엇인지 정의합니다. 두 화폐의 합은 단순히 계산된 화폐 값이 아니라 연산 자체를 나타내는 Expression(수식) 객체가 되어야 하므로 아래와 같이 작성합니다

```java
public void testSimpleAddition() {
    ...
    Expression sum= five.plus(five);
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

**테스트 코드 - 5) 기초 화폐(Money) 객체 생성 및 최종 완성** 

> 마지막으로 수식을 만들기 위해 우리가 확실히 알고 있는 5달러 객체(five)를 생성하는 코드를 맨 위에 배치한다. 이를 통해 거꾸로 역추적하며 작성하던 코드가 완벽하게 동작하는 하나의 테스트 시나리오로 완성된다

```java
public void testSimpleAddition() {
    Money five= Money.dollar(5);
    Expression sum= five.plus(five);
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

**프로덕션 코드 - 컴파일 통과를 위한 최소 코드 작성**
```java
// Expression 인터페이스 정의
interface Expression {}

// Money.plus() → 리턴 타입을 Expression으로 변경
Expression plus(Money addend) {
  return new Money(amount + addend.amount, currency);
}

// Money → Expression 구현
class Money implements Expression {
  ...
}

// Bank 클래스 정의
class Bank {}

// Bank.reduce() 추가
Money reduce(Expression source, String to) {
  return null; // 컴파일 통과시키기
}
```

### Green - 다중 통화 덧셈 API 구현(Fake It)

테스트에 통과할 수 있는 코드를 빠르게 작성한다.

**프로덕션 코드:**
```java
// Bank 클래스:
Money reduce(Expression source, String to) {
  return Money.dollar(10);
}
```
- 일단 테스트 통과를 위해 reduce()가 무조건 Money.dollar(10)을 반환하도록 구현한다.