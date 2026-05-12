# 테스트로 설계 변경을 유도하기

이 단계에서는 테스트를 통해 설계 변경을 유도하는 방법을 다룬다. 테스트가 단순히 코드의 정확성을 검증하는 것을 넘어, 개발자가 더 나은 설계로 나아가도록 압력을 가하는 도구로 활용되는 것이다.

## 개념

### 테스트는 설계 변경의 출발점이다

새로운 요구사항에 대한 테스트를 작성하다 보면 기존 설계의 약점이 드러난다. `testReduceMoneyDifferentCurrency()`는 `2 CHF`를 `1 USD`로 축약해야 한다고 요구한다. 이 테스트는 단순히 환율 계산 결과를 검증하는 것을 넘어, 기존의 `Money.reduce(String to)` 구조만으로는 환율 정보를 자연스럽게 다루기 어렵다는 사실을 드러낸다.

즉 테스트는 코드가 틀렸는지만 알려주는 장치가 아니라, **현재 설계가 다음 요구사항을 받아들일 준비가 되었는지 확인하는 장치**이기도 하다.

### 리팩터링은 동작을 보존하며 설계를 바꾼다

`refactor1`과 `refactor2`는 새 기능을 더하는 단계가 아니라, 이미 통과한 테스트를 유지한 채 내부 구조를 개선하는 단계다.

- `refactor1`: 환율 계산 책임을 `Money`에서 `Bank`로 이동한다.
- `refactor2`: `Bank`의 하드코딩 환율을 해시테이블 기반의 동적 환율 정보로 바꾼다.

테스트가 계속 초록색이면 외부 동작은 유지되고 있다는 뜻이다. 그 상태에서 내부 설계를 조금씩 정리하는 것이 TDD에서 말하는 리팩터링의 핵심이다.

## 실습

> 서로 다른 통화 간의 환율을 적용하여 환전(Conversion)하는 기능을 구현한다. 환율 계산 기능을 추가하기 위해, 덧셈 표현식이 단일 통화로 축약(reduce)될 때 올바른 결과가 나오는지 검증하는 테스트를 작성한다. 

### Red — `testReduceMoneyDifferentCurrency()` 테스트 추가

2 CHF를 1 USD로 환산하는 환율이 있다고 가정할 때, 2 CHF가 1 USD로 축약되는지 검증하는 테스트를 작성한다. 이 테스트는 환율 계산이 제대로 이루어지는지를 검증한다.

**테스트 코드:**
```java
@Test
void testReduceMoneyDifferentCurrency() {
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(Money.franc(2), "USD");
    assertEquals(Money.dollar(1), result);
}
```
- 이 테스트는 아직 Bank 클래스에 addRate() 메서드가 없기 때문에 컴파일되지 않을 것이다. 이를 통과시키기 위해서는 Bank 클래스에 환율을 저장하고 관리하는 기능을 추가해야 한다.

**프로덕션 코드:**
```java
// Bank 클래스:
void addRate(String from, String to, int rate) {}
```
- 일단 컴파일만 되도록 addRate() 메서드를 빈 구현으로 추가한다.

이 단계에서는 addRate() 메서드가 실제로 환율을 저장하지 않고, Money.reduce() 메서드도 환율 계산을 수행하지 않으므로, 이 테스트는 실패할 것이다.


### Green — 가장 추한 방법으로 빠르게 Green 만들기(Fake it)

일단 Green 상태로 만들기 위해 Money.reduce() 안에서 통화 문자열을 직접 비교하는 매우 못생긴 코드를 작성하여 테스트를 통과시킨다. 즉 TDD의 핵심인 "일단 초록 막대를 만들고 나서 리팩토링하자" 원칙을 따른다.

**프로덕션 코드:**
```java
// Money 클래스:
public Money reduce(String to) {
    int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
    return new Money(amount / rate, to);
}
```
- 이 구현은 환율 계산을 하드코딩하여, 2 CHF가 1 USD로 축약되는 경우만 처리한다. 
- 다른 통화나 환율은 고려하지 않으므로, 매우 제한적이고 지저분한 코드이다. 하지만 일단 테스트를 통과시키는 것이 목표이므로, 이 정도로 충분하다.

### Refactor 1 — 환율 관리의 책임 재배치

Money가 환율을 알면 안 된다. 환율 정보는 Bank에만 있어야 하므로, Money.reduce() 메서드에서 환율 계산 로직을 제거하고, Bank.reduce()가 Expression 객체에 계산을 위임하도록 리팩토링한다. 이를 통해 환율 관리의 책임을 Bank로 이동시킨다.

**프로덕션 코드: 1) Bank.reduce() 변경**
```java
// Bank 클래스
Money reduce(Expression source, String to) {
    return source.reduce(this, to);
}
```
- 계산식을 수행할 때 환율 정보를 얻을 수 있도록 reduce() 에 Bank 객체를 파라미터로 전달한다.
- 아직 Expression 인터페이스의 reduce() 메서드가 Bank 파라미터를 받도록 선언되어 있지 않으므로, 다음 단계에서 인터페이스 시그너처를 변경해야 한다.

**프로덕션 코드: 2) Expression.reduce() 시그너처 변경**
```java
// Expression 인터페이스
Money reduce(Bank bank, String to);
```
- Bank 클래스의 reduce() 변경에 맞춰 인터페이스의 reduce() 선언을 변경한다.

**프로덕션 코드: 3) Sum.reduce() 변경**
```java
// Sum 클래스
public Money reduce(Bank bank, String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
}
```
- Expression 인터페이스의 reduce() 시그너처 변경에 맞춰 Sum 클래스의 reduce() 메서드도 Bank 파라미터를 받도록 변경한다. 이 단계에서는 아직 환율 계산을 적용하지 않고 단순히 덧셈 결과를 반환하도록 기존 코드를 유지한다.

**프로덕션 코드: 4) Money.reduce() 변경**
```java
// Money 클래스
public Money reduce(Bank bank, String to) {
    int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
    return new Money(amount / rate, to);
}
```
- Expression 인터페이스의 reduce() 시그너처 변경에 맞춰 Money 클래스의 reduce() 메서드도 Bank 파라미터를 받도록 변경한다. 이 단계에서는 아직 환율 계산을 적용하지 않고, 기존의 하드코딩된 환율 계산 로직을 그대로 유지한다.

**프로덕션 코드: 5) Money.reduce()에 환율 적용**
```java
// Money 클래스
public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
}
```
- 계산식을 수행할 때 Bank에서 환율 정보를 얻어 계산을 수행하도록 Money.reduce() 메서드를 변경한다. 이제 환율 계산이 Bank의 rate() 메서드에서 제공하는 값을 사용하여 이루어지므로, 환율 관리의 책임이 Bank로 완전히 이동한다.

**프로덕션 코드: 6) Bank.rate() 추가**
```java
// Bank 클래스
public int rate(String from, String to) {
    if (from.equals("CHF") && to.equals("USD")) return 2;
    return 1;
}
```
- 계산식을 수행할 때 환율 정보를 얻을 수 있도록 rate() 메서드를 추가한다. 일단은 하드코딩된 환율을 반환하도록 구현한다.


### Refactor 2 — 하드 코딩 환율 정보를 동적 환율 정보로 변경

Bank 클래스의 rate() 메서드가 하드코딩된 환율을 반환하는 대신, addRate() 메서드를 통해 동적으로 환율 정보를 저장하고 반환하도록 리팩토링한다. 이를 통해 다양한 통화쌍에 대한 환율을 유연하게 관리할 수 있다.

**프로덕션 코드: 1) Pair 클래스 추가**
```java
// Pair 클래스
private class Pair {
    private String from;
    private String to;

    Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public boolean equals(Object object) {
      Pair pair = (Pair) object;
      return from.equals(pair.from) && to.equals(pair.to);
    }

    @Override
    public int hashCode() {
      return 0;
    }
}
```
- Bank 클래스에서 통화쌍 정보를 다룰 때 사용할 Pair 클래스를 중첩 클래스로 추가한다.
- hashCode() 메서드는 간단히 0을 반환하도록 구현한다. 실제로는 더 복잡한 해시 로직이 필요하지만, 일단은 테스트를 통과시키는 것이 목표이므로, 최적화는 나중으로 미룬다.

**프로덕션 코드: 2) Bank에 환율 정보를 저장할 Hashtable 필드 추가**
```java
// Bank 클래스
private Hashtable<Pair, Integer> rates = new Hashtable<>();
```
- Bank 클래스에 Pair 객체를 키로 하고 환율을 값으로 하는 Hashtable을 추가하여, 다양한 통화쌍에 대한 환율 정보를 저장할 수 있도록 한다.

**프로덕션 코드: 3) Bank.addRate() 완성**
```java
// Bank 클래스
public void addRate(String from, String to, int rate) {
    rates.put(new Pair(from, to), rate);
}
```
- 현재 Bank 클래스에는 addRate() 메서드가 빈 구현으로 존재한다. 이 메서드를 완성하여 환율 정보를 저장할 수 있게 한다.

**프로덕션 코드: 4) Bank.rate() 변경**
```java
// Bank 클래스
public int rate(String from, String to) {
    Integer rate = rates.get(new Pair(from, to));
    return rate.intValue();
}
```
- 환율 정보를 Hashtable에서 조회하도록 Bank 클래스의 rate() 메서드를 변경한다. 

>테스트를 실행하면 `testReduceMoney()`에서 오류가 발생할 것이다. 이는 `Bank.rate()`에서 동일 통화에 대한 환율을 고려하지 않았기 때문이다.

**프로덕션 코드: 5) Bank.rate() 변경**
```java
// Bank 클래스
public int rate(String from, String to) {
    if (from.equals(to)) return 1;
    Integer rate = rates.get(new Pair(from, to));
    return rate.intValue();
}
```
- 동일 통화에 대한 환율은 항상 1이 되도록 Bank.rate() 메서드를 변경한다. 이제 환율 계산이 다양한 통화쌍에 대해 유연하게 이루어질 수 있다.

**테스트 코드:**
```java
@Test
void testIdentityRate() {
    assertEquals(1, new Bank().rate("USD", "USD"));
    assertEquals(1, new Bank().rate("CHF", "CHF"));
}
```
- 동일 통화에 대한 환율이 항상 1로 반환되는지 검증하는 테스트를 추가한다. 이 테스트는 Bank.rate() 메서드의 변경 사항을 검증하는 역할을 한다.