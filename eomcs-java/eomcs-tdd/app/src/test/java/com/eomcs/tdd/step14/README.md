# 가짜 구현을 진짜 구현으로 교체하는 TDD 기법 II

이 단계에서는 테스트를 빠르게 통과하기 위해 만든 가짜 구현(Fake It)을 실제 구현(Make It)으로 전환하는 TDD 기법을 체험한다. 

## 개념

### 삼각측량(Triangulation)

삼각측량은 하나의 테스트로만은 유도할 수 없는 진짜 구현을 이끌어내기 위해, **다른 각도에서 같은 동작을 검증하는 두 번째 테스트를 추가**하는 기법이다. 두 테스트를 동시에 만족시킬 수 없는 가짜 구현은 결국 진짜 구현으로 교체될 수밖에 없다.

예를 들어:
- `testSimpleAddition()`만으로는 `Bank.reduce()`가 `Money.dollar(10)`을 하드코딩해도 통과된다.
- `testReduceSum()`을 추가해 `Money.dollar(3) + Money.dollar(4) = Money.dollar(7)`을 검증하면, 하드코딩된 구현이 두 테스트를 동시에 만족할 수 없으므로 실제로 합산하는 진짜 로직을 작성해야 한다.


## 실습

> `Bank.reduce()`의 가짜 구현을 진짜 구현으로 교체

### Red — `testReduceSum()` 테스트 추가

`Bank.reduce()` 코드가 진짜로 동작하도록 만들어 보자. 삼각측량 기법을 활용하여 Bank.reduce()의 '가짜 구현(Fake It)'을 '진짜 구현(Make It)'으로 유도하는 `testReduceSum()` 테스트를 추가한다.

**테스트 코드:**
```java
@Test
void testReduceSum() {
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(7), result);
}
```
- 현재 `Bank.reduce()`는 `Money.dollar(10)`을 무조건 반환하는 가짜 구현이므로, 이 테스트는 실패할 것이다. 이를 통과시키기 위해서는 `Bank.reduce()`가 전달받은 표현식(Sum)을 실제로 계산하여 그 결과를 반환하도록 구현을 변경해야 한다.

**프로덕션 코드:**
```java
@Override
public String toString() {
  return amount + " " + currency;
}
```
- Money의 테스트 결과를 디버깅할 때, 객체의 내용을 쉽게 확인할 수 있도록 toString() 메서드를 구현한다.


### Green — `testReduceSum()` 테스트 통과

`Bank.reduce()`가 `testReduceSum()` 테스트를 통과하도록 구현을 변경한다. 현재 `Bank.reduce()`는 Sum 객체를 실제로 계산하지 않고, 단순히 `Money.dollar(10)`을 반환하는 가짜 구현이므로, 이 부분을 수정하여 Sum 객체의 augend와 addend를 합산하여 그 결과를 반환하도록 구현한다.

**프로덕션 코드:**
```java
// Bank 클래스:
Money reduce(Expression source, String to) {
  Sum sum = (Sum) source; 
  int amount = sum.augend.amount + sum.addend.amount; 
  return new Money(amount, to); 
}
```
- 전달받은 Expression 객체를 Sum으로 캐스팅한다.
- Sum의 augend와 addend의 금액을 합산한다.
- 그 결과를 새로운 Money 객체로 반환한다.

이제 `testReduceSum()` 테스트를 통과할 것이다.



