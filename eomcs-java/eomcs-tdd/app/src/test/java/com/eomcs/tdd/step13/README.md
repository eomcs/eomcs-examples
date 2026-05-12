# 가짜 구현을 진짜 구현으로 교체하는 TDD 기법 I

이 단계에서는 테스트를 빠르게 통과하기 위해 만든 가짜 구현(Fake It)을 실제 구현(Make It)으로 전환하는 TDD 기법을 체험한다. 

## 개념

### 가짜 구현(Fake It)에서 진짜 구현(Make It)으로

테스트를 빠르게 통과시키기 위해 먼저 작성한 가짜 구현(Fake It)은 반드시 진짜 구현(Make It)으로 교체되어야 한다. 진짜 구현으로 전환하는 시점은 **가짜 구현이 통과시킬 수 없는 새로운 테스트를 추가한 직후**이다. 새 테스트가 가짜 구현의 한계를 드러내는 순간, 진짜 로직을 구현해야 할 압력이 생긴다.

## 실습

> `Money.plus()`의 가짜 구현을 진짜 구현으로 교체

### Red — `testPlusReturnsSum()` 테스트 추가

`testSimpleAddition()` 테스트를 통과하기 위해 `Money.plus()`를 작성했지만, 아직 완벽하게 구현된 상태가 아니다. 가짜 구현의 한계를 드러내는 새 테스트를 추가하여 진짜 구현을 유도한다.

**테스트 코드:**
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
- 기존 설계에서는 Money.plus()가 단순히 Money 객체를 반환했지만, 이 테스트는 덧셈을 표현하는 Expression 구현체를 반환하도록 설계 변경을 강요한다.
- Sum 클래스는 덧셈 표현식을 나타내기 위해 새로 추가한 Expression 구현체다.
- `five.plus(five)`의 결과를 `Sum`으로 캐스팅하여 `augend`와 `addend`가 올바르게 저장되었는지 직접 검증한다.
- 아직 `five.plus(five)`는 `Sum`을 반환하도록 구현되어 있지 않으므로, 이 테스트는 실패할 것이다.


**프로덕션 코드: 컴파일을 통과하기 위해 Sum 클래스 최소 구현**
```java
class Sum {
  Money augend; // 피가수
  Money addend; // 가수
}
```
- Sum 클래스 정의
- augend(피가수)와 addend(가수) 필드 추가

### Green — `testPlusReturnsSum()` 테스트 통과

**프로덕션 코드 - 1) Money.plus() 메서드 변경**
```java
Expression plus(Money addend) {
  return new Sum(this, addend);
}
```
- Money.plus()가 new Sum(this, addend)를 반환하도록 변경

**프로덕션 코드 - 2) Sum 클래스 변경**
```java
class Sum implements Expression {

  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }
}
```
- Sum 클래스가 Expression 인터페이스를 구현하도록 변경
- Sum 생성자에서 augend와 addend 초기화

