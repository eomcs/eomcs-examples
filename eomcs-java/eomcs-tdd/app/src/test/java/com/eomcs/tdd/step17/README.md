# 테스트로 설계 변경을 유도하기 II

이 단계에서는 서로 다른 통화 간의 계산을 지원하기 위해 테스트로 설계 변경을 유도하는 방법을 다룬다.

## 개념

### 리팩터링의 방향

step17의 핵심은 혼합 통화 덧셈 테스트를 추가하여 기존 구현의 한계를 드러내고, 그 한계를 더 일반적인 Expression 구조로 밀어 올리는 것이다.

- `green`: `Sum.reduce()`가 각 항을 목표 통화로 reduce한 뒤 더하도록 변경한다.
- `refactor1`: `Sum`의 필드와 생성자 파라미터를 Money에서 Expression으로 일반화한다.
- `refactor2`: 테스트 코드도 Money 대신 Expression을 사용하도록 바꾸고, Expression 인터페이스에 plus()를 추가한다.

이 과정은 앞 단계에서 반복해 온 TDD 원칙과 같다. 먼저 구체적인 실패 테스트로 문제를 드러내고, 최소한의 구현으로 Green을 만든 뒤, 테스트가 통과하는 상태에서 더 넓은 설계로 일반화한다.

## 실습

> 서로 다른 통화 간의 계산을 지원한다.

### Red — `testMixedAddition()` 테스트 추가

$5 + 10 CHF 계산을 검증하는 테스트를 작성한다. 이 테스트는 서로 다른 통화 간의 덧셈이 올바르게 처리되는지 검증하고 그렇게 되도록 강제하는 역할을 한다.

**테스트 코드:**
```java
@Test
void testMixedAddition() {
    Money fiveBucks = Money.dollar(5);
    Money tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertEquals(Money.dollar(10), result);
}
```
- Money.dollar(5)와 Money.franc(10)을 각각 Expression으로 선언하여, 서로 다른 통화 간의 덧셈을 표현한다.

`Money.plus()`가 리턴하는 Sum 객체는 **동일 통화를 가정하고 덧셈을 수행**하기 때문에 이 테스트는 실패할 것이다. 


### Green — `testMixedAddition()` 테스트 통과

`Sum.reduce()` 메서드에 환율을 적용하여 서로 다른 통화 간의 덧셈이 올바르게 계산되도록 구현한다.

**프로덕션 코드:**
```java
// Sum 클래스:
@Override
public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
  return new Money(amount, to);
}
```
- 동일 통화로 환산한 후 덧셈을 수행한다.

이렇게 하면 `addend(10 CHF)`가 먼저 5 USD로 변환된 뒤 5 USD + 5 USD = 10 USD가 되어 테스트를 통과합니다.

### Refactor 1 - `Expression`을 사용하여 일반화하기

`Sum` 클래스는 현재 `Money` 객체만을 다루도록 설계되어 있지만, `Expression` 타입을 다루도록 변경하여 더 일반적인 덧셈 표현식으로 확장한다. 또한 `Money` 클래스도 곱셈과 덧셈을 다루는 메서드의 리턴 타입과 파라미터 타입을 `Expression`으로 변경한다.

**프로덕션 코드:**
```java
// Sum 클래스:
Expression augend; // 피가수
Expression addend; // 가수
```
- augend와 addend를 Expression 타입으로 변경하여, 덧셈 표현식이 Money뿐만 아니라 다른 Expression도 포함할 수 있도록 한다.

```java
// Sum 클래스:
Sum(Expression augend, Expression addend) {
  this.augend = augend;
  this.addend = addend;
}
```
- Sum 생성자의 파라미터 타입을 Expression 으로 변경한다.

```java
// Money 클래스:
Expression plus(Expression addend) {
return new Sum(this, addend);
}
```
- Money.plus() 메서드의 리턴 타입 및 파라미터 타입을 Expression으로 변경하여, 덧셈 표현식이 Money뿐만 아니라 다른 Expression도 포함할 수 있도록 한다.

```java
// Money 클래스:
Expression times(int multiplier) {
return new Money(amount * multiplier, currency);
}
```
- Money.times() 메서드의 리턴 타입도 Expression으로 변경한다.


### Refactor 2 - 테스트 코드 리팩토링

`Sum`과 `Money`가 Expression을 다루도록 변경했으므로, `testPlusReturnsSum()` 테스트도 Expression을 사용하도록 리팩토링한다.

**테스트 코드:**
```java
@Test
void testMixedAddition() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertEquals(Money.dollar(10), result);
}
```
- `testMixedAddition()` 테스트를 수행할 때 Money 대신 Expression 타입을 사용하도록 변경한다.

> `testMixedAddition()` 테스트 코드를 변경에 맞춰서 프로덕션 코드를 변경한다.

**프로덕션 코드:**
```java
// Expression 인터페이스:
Expression plus(Expression addend);
```
- Expression 인터페이스에 plus() 메서드를 추가하여, 모든 Expression이 덧셈 표현식을 생성할 수 있도록 한다.

```java
// Money 클래스:
@Override
public Expression plus(Expression addend) {
  return new Sum(this, addend);
}
```
- 이제 Money 클래스의 plus() 메서드는 Expression 인터페이스의 메서드를 구현한 것이므로, @Override 어노테이션을 추가하여 명시적으로 구현을 나타낸다. 또한 public 접근 제어자를 추가하여 인터페이스 메서드의 요구사항을 충족시킨다. 

```java
// Sum 클래스:
@Override
public Expression plus(Expression addend) {
  return null;
}
```
- Expression 인터페이스에 plus()를 추가했기 때문에 Sum 클래eh plus() 메서드를 구현해야 한다.
- 아직 구현이 필요하지 않으므로, 일단 null을 반환한다.

