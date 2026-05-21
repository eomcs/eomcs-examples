# 테스트로 설계 변경을 유도하기 IV

이 단계에서는 Sum 표현식에 곱셈 연산을 연결할 수 있도록 설계 변경을 유도한다.

## 개념

### "한 곳을 바꾸면 컴파일러가 다음 할 일을 알려준다."

테스트로 설계 변경을 유도할 때, 한 곳을 바꾸면 컴파일러가 다음에 무엇을 해야 하는지 알려준다. 예를 들어 테스트에서 Sum.times() 메서드를 사용하면, 컴파일러는 Sum 클래스에 times() 메서드를 추가해야 한다고 알려준다. Sum.times()에서 augend.times()와 addend.times()를 호출하면, 컴파일러는 Expression 인터페이스에 times() 메서드를 추가해야 한다고 알려준다. 이런 식으로 한 곳을 바꾸면 컴파일러가 다음에 무엇을 해야 하는지 알려주는 과정을 반복하면서 설계 변경을 유도할 수 있다. 이렇게 컴파일러의 도움을 받아 설계 변경을 유도하는 과정은 테스트 주도 개발(TDD)의 매우 중요한 기법이다.

## 실습

> Sum 에 곱셈 연산을 연결한다.

### Red — `testSumTimes()` 테스트 추가

Sum 표현식에 곱셈 연산을 연결하기 위해 다음 테스트를 추가한다.

**테스트 코드:**
```java
@Test
void testSumTimes() {
  Money fiveBucks = Money.dollar(5);
  Money tenFrancs = Money.franc(10);
  Bank bank = new Bank();
  bank.addRate("CHF", "USD", 2);
  Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
  Money result = bank.reduce(sum, "USD");
  assertEquals(Money.dollar(20), result);
}
```
- Sum 객체에 times() 메서드를 호출하여 곱셈 연산을 연결
- Bank.reduce() 메서드를 사용하여 Sum 표현식을 USD로 환산
- 결과가 20달러인지 검증

**프로덕션 코드:**
```java
// Sum 클래스:
Expression times(int multiplier) {
  return null;
}
```
- 컴파일 오류를 해결하기 위해 Sum 클래스에 times() 메서드 추가
- times() 메서드는 단순히 null을 반환하기 때문에, 이 테스트는 실패할 것이다.

### Green - `testSumTimes()` 테스트 통과시키기

**프로덕션 코드:**
```java
// Sum 클래스:
public Expression times(int multiplier) {
  return new Sum(augend.times(multiplier), addend.times(multiplier));
}
```
- Sum.times() 메서드 구현

```java
// Expression 인터페이스:
Expression times(int multiplier);
```
- Expression 인터페이스에 times() 메서드 선언 추가

```java
// Money 클래스:
public Expression times(int multiplier) {...}

// Sum 클래스:
public Expression times(int multiplier) {...}
```
- Money 클래스와 Sum 클래스에서 times() 메서드의 접근 제어를 public으로 변경하여 컴파일 오류 해결



