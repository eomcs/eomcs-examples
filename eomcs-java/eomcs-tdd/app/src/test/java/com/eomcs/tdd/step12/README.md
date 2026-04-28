# 테스트를 통해 새 기능을 수행할 API 설계하기 - 혼합 통화 덧셈

## 학습 목표

- 새로운 기능을 추가할 때, 철저하게 테스트를 먼저 작성하고 이를 기준으로 설계를 확장해 나가는 TDD 핵심 원칙을 이해한다. 
- 복잡한 큰 문제를 단순화하여 **작은 테스트로 시작**하고, 점진적으로 진척을 만들어 나가는 방법을 익힌다.
- 현실 세계의 개념이나 모델을 '수학적 수식(Expression)'과 같은 **메타포로 치환**하여 객체 지향적인 도메인 모델을 설계하는 방법을 체험한다.
- 모든 기능을 하나의 객체에 몰아넣는 대신, **객체의 책임 분리 (Separation of Responsibilities):**를 통해 핵심 도메인 객체가 자신의 본질적인 역할에만 집중하도록 설계하여 유지보수성과 확장성을 높이고 테스트를 쉽게 만드는 객체 지향 설계의 원칙을 이해한다.
- **원하는 API를 상상하며 테스트 작성:** 아직 존재하지 않는 인터페이스나 클래스라도 먼저 가장 이상적이고 완벽한 형태의 API 호출 구조를 상상하여 테스트 코드로 작성하는 TDD 기법을 체득한다.

## 핵심 내용

### 새 기능 추가 시 TDD 핵심 과정

1. **큰 문제를 작은 테스트로 축소하여 시작:** 최종 목표인 혼합 통화 덧셈($5 + 10 CHF = $10)이라는 복잡한 기능을 당장 구현하는 대신, 해결하기 쉬운 동일 통화의 단순한 덧셈 테스트($5 + $5 = $10)부터 작성하며 점진적인 진척을 만들어 낸다.
2. **이상적인 설계를 테스트 코드로 먼저 표현:** 덧셈 기능을 시스템에 어떻게 녹여낼지 고민한 끝에, 화폐의 합을 '수식(Expression)'으로 다루고 이를 축약(reduce)하는 책임을 '은행(Bank)' 객체에 위임하는 메타포를 구상한다. 실제 클래스를 만들기 전에 자신이 사용하고 싶은 이상적인 형태의 API 호출 구조를 테스트 코드로 가장 먼저 작성한다.
3. **컴파일 에러 해결 및 가짜 구현(Fake It):** 상상하여 작성한 테스트 코드가 컴파일되도록 Expression 인터페이스와 Bank 클래스의 껍데기(stub)를 최소한으로 만듭니다. 그 후 완벽한 덧셈 및 환율 변환 로직을 짜는 대신, 무조건 Money.dollar(10)을 반환하도록 가짜로 구현하여 우선 테스트를 통과(초록 막대)시키는 데 집중한다.

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

### Green 단계 1 - 가장 단순한 형태의 덧셈 API 설계

혼합 통화의 덧셈($5 + 10 CHF)을 테스트하는 대신 가장 단순한 형태의 같은 통화의 덧셈($5 + $5 = $10)을 검증하는 테스트를 작성하며 시작한다.

### Green 단계 2 - 원하는 API를 상상하여 테스트 작성한 후 빠르게 구현하기

다중 통화 연산을 깔끔하게 처리하기 위해 두 화폐의 합을 나타내는 **수식(Expression)**과, 이 수식에 환율을 적용해 단일 통화로 변환해 주는 **은행(Bank)**이라는 메타포 도입을 상상한다. 이 아이디어를 바탕으로 코드를 직접 짜기 전, '자신이 사용하고 싶은 이상적인 API'를 역방향으로 상상하며 테스트 코드를 수정한다.

- 기대하는 결과값 상상: 가장 마지막에 확인해야 할 결과물(reduced)을 먼저 정의한다.
- Bank 객체의 역할 상상: 현실 세계에서 환율을 적용해 주는 주체인 은행(Bank) 객체가 reduce() 연산을 수행하여 수식(sum)을 달러("USD")로 축약해 주길 기대한다.

#### **1단계: 가장 마지막에 확인할 결과물 정의하기:** 
먼저 연산이 모두 끝난 후 도출되어야 할 최종 결과(reduced)를 단언(assert)하는 코드를 가장 먼저 작성한다

```java
public void testSimpleAddition() {
    ...
    assertEquals(Money.dollar(10), reduced);
}
```

#### **2단계: 환율을 적용해 결과를 만들어내는 '은행(Bank)' 메타포 적용하기:** 
reduced라는 결과값이 어떻게 만들어질지 상상해 본다. '수식(Expression)'에 환율을 적용하는 현실 세계의 주체로 '은행(Bank)'을 도출한다. 은행에 수식(sum)을 달러("USD")로 축약(reduce)하여 결과를 반환하는 코드를 추가한다.

```java
public void testSimpleAddition() {
    ...
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

#### **3단계: Bank 객체 생성하기:** 
bank.reduce()를 호출하기 위해서는 Bank 객체가 필요하므로 이를 생성하는 코드를 그 위에 추가한다

```java
public void testSimpleAddition() {
    ...
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

#### **4단계: 수식(Expression) 메타포 적용하기** 
은행에 전달할 sum이라는 변수가 무엇인지 정의합니다. 두 화폐의 합은 단순히 계산된 화폐 값이 아니라 연산 자체를 나타내는 Expression(수식) 객체가 되어야 하므로 아래와 같이 작성합니다

```java
public void testSimpleAddition() {
    ...
    Expression sum= five.plus(five);
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

#### **5단계: 기초 화폐(Money) 객체 생성 및 최종 완성** 
마지막으로 수식을 만들기 위해 우리가 확실히 알고 있는 5달러 객체(five)를 생성하는 코드를 맨 위에 배치한다. 이를 통해 거꾸로 역추적하며 작성하던 코드가 완벽하게 동작하는 하나의 테스트 시나리오로 완성된다

```java
public void testSimpleAddition() {
    Money five= Money.dollar(5);
    Expression sum= five.plus(five);
    Bank bank= new Bank();
    Money reduced= bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```

#### 테스트에 통과할 수 있는 코드(Fake It)를 빠르게 작성하기

가장 단순한 구현:
1. `Expression` 인터페이스 정의
2. `Money.plus()` → 리턴 타입을 `Expression`으로 변경
3. `Money` → `Expression` 구현
4. `Bank` 클래스 정의
5. `Bank.reduce()` → 무조건 `Money.dollar(10)` 반환하여 테스트 통과시키기


## Q&A

### reduce 라는 이름의 의미는?

reduce는 복잡하게 얽혀 있는 다중 통화 연산(수식)을 사용자가 이해할 수 있는 하나의 깔끔한 화폐 값으로 단순화해 내는 핵심적인 책임을 표현하는 단어



