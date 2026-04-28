# 코드 리팩토링 - 가짜 구현을 진짜 구현으로 교체하기

## 학습 목표

- **가짜 구현(Fake It)에서 실제 구현(Make It)으로 전환하는 TDD 리듬**을 체득한다. 테스트를 빠르게 통과시킨 뒤, 그 테스트를 안전망으로 삼아 내부 구현을 점진적으로 올바른 코드로 교체하는 과정을 이해한다.
- **책임 분리(Separation of Responsibilities)** 원칙을 실천한다. `Sum.reduce()`는 두 Expression의 합산을, `Money.reduce()`는 단일 통화의 반환을, `Bank.reduce()`는 위임(delegation)을 각자 담당하도록 구현한다.
- **작은 테스트로 쪼개기:** `Bank.reduce(Sum, ...)` 테스트와 `Bank.reduce(Money, ...)` 테스트를 분리하여 각 클래스의 동작을 독립적으로 검증하는 방법을 익힌다.
- **삼각측량(Triangulation) 없이 명백한 구현으로 전진:** 이미 올바른 구현이 무엇인지 알고 있을 때는 가짜 구현을 거치지 않고 곧바로 올바른 코드를 작성하는 판단력을 기른다.

## 핵심 내용

### 문제: Bank.reduce()의 가짜 구현

Chapter 12에서 테스트를 빠르게 통과시키기 위해 `Bank.reduce()`를 아래와 같이 가짜로 구현했다:

```java
// 가짜 구현 — 무조건 $10을 반환
Money reduce(Expression source, String to) {
    return Money.dollar(10);
}
```

이 구현은 `testSimpleAddition()` 하나는 통과시키지만, 다른 어떤 덧셈 계산도 올바르게 처리하지 못한다. 이를 진짜 구현으로 교체해야 한다.

### 진짜 구현으로 가는 세 가지 테스트

#### 2. `testReduceSum()` — Sum을 직접 reduce하기

```java
Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
Money result = bank.reduce(sum, "USD");
assertEquals(Money.dollar(7), result);
```

- `Bank.reduce()`가 `source.reduce(this, to)`로 위임하면, 실제 계산 책임은 `Sum.reduce()`로 넘어간다.
- `Sum.reduce()`: `augend`와 `addend`를 각각 reduce한 뒤 amount를 합산하여 새 Money를 반환한다.

```java
// Sum.reduce() 실제 구현
public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount
               + addend.reduce(bank, to).amount;
    return new Money(amount, to);
}
```

#### 2. `testReduceMoney()` — Money를 직접 reduce하기

```java
assertEquals(Money.dollar(1), bank.reduce(Money.dollar(1), "USD"));
```

- `bank.reduce(Money, "USD")`처럼 Sum이 아닌 Money를 직접 전달할 수도 있어야 한다.
- `Money.reduce()`: 같은 통화라면 자기 자신의 amount와 to 통화로 새 Money를 반환한다.

```java
// Money.reduce() 실제 구현
public Money reduce(Bank bank, String to) {
    return new Money(amount, to);
}
```

### 최종 구조

```
bank.reduce(sum, "USD")
    └─ source.reduce(bank, "USD")   ← Bank가 위임
           ├─ Sum.reduce()          ← augend + addend 합산
           └─ Money.reduce()        ← 자기 자신 반환
```

| 클래스 | reduce() 역할 |
|---|---|
| `Bank` | `source.reduce(this, to)`로 위임 |
| `Sum` | augend와 addend를 각각 reduce 후 amount 합산 |
| `Money` | `new Money(amount, to)` 반환 |

## 실습

### Green 단계 1 — `testPlusReturnsSum()`

'가짜 구현(Fake It)' 상태에서 역방향으로 코드를 일반화하기 어려울 때, 
확신을 가질 수 있는 '정방향(Working Forward)'으로 실제 코드를 구현해 나가는 첫걸음으로 `testPlusReturnsSum()` 테스트를 작성한다. 

- **역방향 추론의 한계와 정방향 작업 선택:** 
이전 장(12장)에서 저자는 동일 통화 덧셈 테스트($5 + $5 = $10)를 빠르게 통과하기 위해, reduce() 메서드가 무조건 결과값(10달러)을 반환하도록 상수를 하드코딩하는 '가짜 구현'을 사용했다. 보통은 이렇게 가짜 구현을 만든 후 상수를 변수로 치환하며 역방향으로 진짜 코드를 구현해 나가지만, 역방향으로 추론하는 방법이 명확하게 떠오르지 않을 때는 약간 투기적(speculative)이더라도 정방향으로 작업을 진행한다.
- **진짜 수식(Sum) 객체 반환 강제:** 
정방향 작업의 첫 단계로, Money.plus() 연산이 단순한 Money 객체를 반환하는 것이 아니라, 연산 자체를 나타내는 진짜 Expression(구체적으로는 Sum 객체)을 반환하도록 만든다. 이를 강제하고 검증하기 위해 두 화폐 객체를 더했을 때 올바른 Sum 객체가 반환되는지 확인하는 testPlusReturnsSum()을 작성한다. 
- **목표 달성을 위한 징검다리 (내부 구현 검증):** 이 테스트가 시스템의 외부에서 관찰 가능한 행동을 검증하기보다는 내부 구현에 너무 깊이 관여하고 있기 때문에 오래 유지될 테스트는 아니다. 하지만 이 테스트를 먼저 통과시키고 나면, 최종 목표(진짜 다중 통화 덧셈과 환전)를 달성하는 데 한 걸음 더 가까워질 수 있기에 설계의 징검다리 삼아 이 메서드를 등장시킨 것이다.

테스트 코드:
```java
@Test
void testPlusReturnsSum() {
    Money five = Money.dollar(5);
    Expression result = five.plus(five);
    Sum sum = (Sum) result;
    assertEquals(five, sum.augend);
    assertEquals(five, sum.addend);
}
```
- `five.plus(five)`의 결과를 `Sum`으로 캐스팅하여 `augend`와 `addend`가 올바르게 저장되었는지 직접 검증한다.
- 이 테스트는 `plus()`의 반환 타입을 `Expression`에서 덧셈을 표현식으로 구체화한 `Sum`을 유도한다.
- `Sum.augend`와 `Sum.addend` 필드가 package-private(기본 접근 제어)으로 선언되어 있어야 테스트에서 직접 접근할 수 있다.

모델 코드:
- `Sum` 클래스 정의
    - `Sum` 클래스는 `Expression` 인터페이스를 구현
    - `augend(피가수)`와 `addend(가수)` 필드 추가
    - `Sum` 생성자에서 `augend`와 `addend` 초기화
- `Money.plus()` 메서드 수정
    - `Money.plus()`가 `new Sum(this, addend)`를 반환하도록 변경


### Green 단계 2 — `testReduceSum()`

Bank.reduce() 메서드에 남아있던 '가짜 구현(Fake It)'을 깨뜨리고, 
전달받은 Sum 객체의 금액을 실제로 합산하는 진짜 로직(Make It)을 구현하도록 강제하기 위해 `testReduceSum()` 테스트를 작성한다.

- **하드코딩된 결과값 깨뜨리기:** 
이전 과정에서 5달러와 5달러의 덧셈을 빠르게 통과하기 위해, Bank.reduce() 메서드는 무조건 10달러(Money.dollar(10))를 반환하도록 하드코딩 되어 있다. 이 기존 테스트를 고의로 실패하게 만들고자 합이 7이 되는 새로운 테스트를 작성한다.
- **실제 덧셈 연산 강제:** 10달러를 고정으로 반환하던 기존 코드는 이 새로운 테스트(3 + 4 = 7)를 통과할 수 없다. 따라서 테스트를 통과시키기 위해서는 전달받은 수식(Sum) 객체에서 피가산수(augend)와 가산수(addend)의 금액을 실제로 더하도록 구현을 변경해야만 한다. 따라서 테스트를 통과시키기 위해서는 전달받은 수식(Sum) 객체에서 피가산수(augend)와 가산수(addend)의 금액을 실제로 더하도록 구현을 변경해야만 한다.
- **단일 통화 축약(reduce) 검증:** 이 테스트는 수식 내의 화폐 단위가 동일하고 축약하려는 목표 통화 역시 같을 때, 합산된 금액과 목표 통화 단위를 가진 새로운 Money 객체가 올바르게 반환되는지를 확실하게 검증한다

결과적으로 이 테스트를 추가하고 통과시킴으로써, 상수를 반환하던 지저분한 코드는 진짜로 피가산수와 가산수의 값을 합산하여 Money 객체를 반환하는 실질적인 축약 로직으로 발전한다.

테스트 코드:
```java
@Test
void testReduceSum() {
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(7), result);
}
```
- `Bank.reduce()`가 `source.reduce(this, to)`로 위임하면, 실제 계산 책임은 `Sum.reduce()`로 넘어간다.
- `Sum.reduce()`: `augend`와 `addend`를 각각 reduce한 뒤 amount를 합산하여 새 Money를 반환한다.


모델 코드:
- `Bank.reduce()` 메서드 수정
    - `Bank.reduce()`가 `source.reduce(this, to)`로 위임하도록 변경
- `Sum.reduce()` 메서드 구현
    - `Sum.reduce(String to)` 메서드에서 `augend.amount`와 `addend.amount` 이 두 금액을 합산하여 `new Money(amount, to)`를 반환하도록 구현

### Green 단계 3 — `testReduceMoney()`

Bank.reduce() 메서드가 수식(Sum) 객체뿐만 아니라 단일 Money 객체를 인자로 받았을 때도 
스스로를 올바르게 반환(축약)하는지 검증하기 위해 `testReduceMoney()` 테스트를 작성한다.

- **단일 화폐의 축약 상황 고려:**
이전 테스트(testReduceSum())를 통해 Bank.reduce()가 피가산수와 가산수를 가진 Sum 객체를 처리하도록 만들었다. 만약 인자가 Sum이 아니라 단순한 Money일 때는 Bank.reduce()를 어떻게 테스트할 것인가?
- **초록 막대 상태에서의 전진:** 코드가 테스트를 모두 통과하여 초록 막대(Green Bar)인 상태였고, 기존 코드 구조에서 뚜렷하게 수정할 부분이 당장 보이지 않는다. 따라서 할 일 목록에 있던 Bank.reduce(Money) 기능을 구현하기 위해 새로운 테스트를 작성하며 다음 단계로 나아간 것이다.
- **다형성(Polymorphism)을 이끌어내는 발판:** 이 테스트를 억지로 통과시키기 위해 처음에 Bank.reduce() 내부에 if (source instanceof Money)와 같은 명시적 타입 검사와 캐스팅 코드를 추가하게 된다.
- **지저분한 코드(Ugly)의 발견과 리팩토링:** 이러한 명시적 클래스 검사는 "매우 추하다(Ugly, ugly, ugly)"고 표현할 수 있다. 이 문제를 해결하기 위해 Expression 인터페이스에 reduce(String to) 메서드를 선언하고, Money와 Sum 클래스가 각자의 방식대로 reduce()를 구현하도록 다형성을 도입한다.

결과적으로 testReduceMoney()는 단순한 Money 객체 처리 기능을 추가하는 것을 넘어, 타입 검사로 인해 지저분해진 코드를 다형성을 활용한 깔끔한 객체 지향 설계로 리팩토링하게 만드는 결정적인 역할을 수행한다.

테스트 코드:
```java
@Test
void testReduceMoney() {
    Bank bank = new Bank();
    Money result = bank.reduce(Money.dollar(1), "USD");
    assertEquals(Money.dollar(1), result);
}
```

모델 코드:
- `Bank.reduce(Expression source, String to)` 메서드 변경
    - `source`가 Money 타입일 때, Money의 `reduce()` 메서드를 호출하도록 구현
- `Money` 클래스에 `reduce(String to)` 메서드 추가
    - `Money.reduce()`에서 현재 Money 객체를 반환하도록 구현


### Refactor 단계 — Bank.reduce()에서 타입 검사 및 캐스팅 코드 제거하기

- **명시적 타입 검사 제거:** 
Money와 Sum에 있는 reduce() 메서드의 규칙을 Expression 인터페이스에 선언하면, 
Bank.reduce() 메서드에서 `instanceof`를 이용한 명시적 타입 검사나 타입 캐스팅과 같은 지저분한(ugly) 코드를 제거할 수 있다.

모델 코드:
- `Expression` 인터페이스에 `reduce(String to)` 메서드 선언
- `Money` 클래스에 `reduce(String to)` 메서드 구현
- `Sum` 클래스에 `reduce(String to)` 메서드 구현
- `Bank.reduce(Expression source, String to)` 메서드에서 `source.reduce(this, to)`로 위임하도록 변경


## Q&A

### 왜 Bank.reduce()가 직접 계산하지 않고 위임(delegation)하는가?

`Bank`가 `Sum`인지 `Money`인지를 직접 판별하면 `instanceof` 분기가 생기고, Expression이 늘어날 때마다 Bank를 수정해야 한다. 위임 방식은 새로운 Expression 구현체가 추가되어도 Bank를 전혀 건드리지 않아도 된다. 이것이 **Open-Closed Principle(개방-폐쇄 원칙)** 을 따르는 설계다.

### Money.reduce()에서 to 통화를 그대로 쓰는 이유는?

현재 장에서는 같은 통화끼리만 reduce한다고 가정한다. 다른 통화 간 변환(예: CHF → USD)은 Chapter 14에서 환율(rate)을 Bank에 추가하면서 다룬다. 지금은 `amount`만 가져오고 통화는 `to`로 대체하는 단순한 구현으로 충분하다.
