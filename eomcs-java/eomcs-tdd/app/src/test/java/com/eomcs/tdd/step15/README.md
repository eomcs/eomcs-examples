# 가짜 구현을 진짜 구현으로 교체하는 TDD 기법 III

이 단계에서는 테스트를 빠르게 통과하기 위해 만든 가짜 구현(Fake It)을 실제 구현(Make It)으로 전환하는 TDD 기법을 체험한다. 

## 개념

### 타입 캐스팅의 위험성

진짜 구현을 작성했더라도 코드에 지저분한 타입 캐스팅이 남아 있을 수 있다. `Bank.reduce()`가 `Sum sum = (Sum) source`처럼 구체 타입으로 캐스팅하는 방식은 `Sum`이 아닌 다른 `Expression` 구현체가 전달될 경우 런타임 오류를 유발한다. 이는 `Bank`가 `Expression`의 구체 타입을 직접 알고 있어야 한다는 강한 결합을 의미하며, 새로운 `Expression` 구현체를 추가할 때마다 `Bank`도 함께 수정해야 하는 취약한 설계가 된다.

### 책임 위임을 통한 다형성 (Delegation via Polymorphism)

타입 캐스팅 문제를 해결하는 방법은 **계산 책임을 `Expression` 구현체에 위임**하는 것이다. `Expression` 인터페이스에 `reduce()` 메서드를 선언하고, `Sum`과 `Money` 각각이 자신의 방식으로 구현하게 한다. 이렇게 하면 `Bank.reduce()`는 `source`의 구체 타입을 알 필요 없이 `source.reduce(to)`를 호출하기만 하면 된다.

```
타입 캐스팅 방식:
  Bank.reduce() → (Sum) source 캐스팅 → sum.augend, sum.addend 직접 접근하여 계산

책임 위임 방식:
  Bank.reduce() → source.reduce(to) 호출 → Sum 또는 Money가 각자 계산
```

이 구조는 새로운 `Expression` 구현체가 추가되어도 `Bank.reduce()`를 수정하지 않아도 되는 **개방-폐쇄 원칙(OCP)** 을 자연스럽게 따르게 된다.

## 실습

> `Bank.reduce()`의 문제점 드러내기

### Red — `testReduceMoney()` 테스트 추가

`Bank.reduce()`에 Sum이 아니라 다른 Expression 객체를 전달할 때 제대로 동작하지 않는 문제점을 드러내기 위해, `testReduceMoney()` 테스트를 추가한다. 이 테스트에서는 `Money` 객체를 `Bank.reduce()`에 전달하여 축약하는 시나리오를 검증한다.

**테스트 코드:**
```java
@Test
void testReduceMoney() {
  Bank bank = new Bank();
  Money result = bank.reduce(Money.dollar(1), "USD");
  assertEquals(Money.dollar(1), result);
}
```
- 현재 `Bank.reduce()`는 Expression 객체를 무조건 Sum으로 캐스팅하여 처리하고 있으므로, Money 객체를 전달하면 이 테스트는 실패할 것이다. 이를 통과시키기 위해서는 `Bank.reduce()`가 전달받은 표현식의 타입을 구분하여 처리하도록 구현을 변경해야 한다.


### Green — `testReduceMoney()` 테스트 통과

`Bank.reduce()`가 `testReduceMoney()` 테스트를 통과하도록 구현을 변경한다. 

**프로덕션 코드:**
```java
// Bank 클래스:
Money reduce(Expression source, String to) {
  // Expression이 Money인지 Sum인지 구분하여 처리한다.
  if (source instanceof Money) {
    return (Money) source;
  }

  Sum sum = (Sum) source;
  int amount = sum.augend.amount + sum.addend.amount;
  return new Money(amount, to);
}
```
- 전달받은 Expression 객체를 타입 검사하여 Money인 경우에는 그대로 반환하고, Sum인 경우에는 augend와 addend의 금액을 합산하여 새로운 Money 객체로 반환한다.


이제 `testReduceMoney()` 테스트를 통과할 것이다.

### Refactor — `Expression.reduce()` 메서드로 위임하기

현재 `Bank.reduce()`는 Expression의 타입에 따라 계산을 직접 수행한다. 이렇게 하면 Sum이나 Money가 아닌 다른 Expression이 들어왔을 때 Bank.reduce()가 제대로 동작하지 않게 된다. 이를 해결하기 위해 Bank.reduce()가 표현식을 직접 계산하지 않고 Expression 객체에 위임하는 방식으로 리팩토링한다.

**프로덕션 코드:**
```java
class Bank {
  Money reduce(Expression source, String to) {
    return source.reduce(to);
  }
}
```
- Bank.reduce()가 직접 계산하지 않고, source.reduce(this, to)로 위임한다.

```java
interface Expression {
  Money reduce(String to);
}
```
- Bank에서 계산을 위임할 때 호출할 reduce() 메서드를 Expression 인터페이스에 추가한다.

```java
// Sum 클래스:
@Override
public Money reduce(String to) {
  int amount = augend.amount + addend.amount;
  return new Money(amount, to);
}
```
- Expression 인터페이스에 reduce() 메서드가 추가됨에 따라, Sum 클래스에서 reduce() 메서드를 구현한다. 
- 이 메서드에서 augend와 addend의 금액을 실제로 더하여 새로운 Money 객체를 반환한다.

```java
// Money 클래스:
@Override
public Money reduce(String to) {
  return this;
}
```
- Expression 인터페이스에 reduce() 메서드가 추가됨에 따라, Money 클래스도 reduce() 메서드를 구현해야 한다.
- Money는 이미 축약된 상태이기 때문에 현재 Money 객체를 그대로 반환한다.


