# 테스트로 설계 변경을 유도하기 III

이 단계에서는 Sum 클래스의 Expression 구현을 완성한다. 

## 개념

### 명세서로서의 테스트

테스트는 실행 가능한 명세서이자 문서다. 테스트를 작성할 때 "이 코드가 동작하는가" 뿐만 아니라 "이 테스트를 읽을 미래의 사람이 의도를 이해할 수 있는가" 까지 고려해야 한다.

### TDD의 경제성

TDD가 경제적으로 말이 되려면, 같은 프로그래밍 시간에 이전보다 두 배 많은 줄을 쓰거나, 같은 기능을 절반의 줄로 구현할 수 있어야 한다. 그러나 TDD를 하면 오히려 테스트 코드의 줄 수가 모델 코드와 비슷해진다. 이는 경제성 측면에서 TDD가 불리해 보일 수 있다. 하지만 디버깅 시간, 통합 시간, 코드를 남에게 설명하는 시간까지 모두 포함하면 TDD가 오히려 빠를 수 있습니다. 

TDD의 진정한 가치는 코드의 품질과 유지보수성에 있다. TDD를 통해 작성된 코드는 더 명확하고, 버그가 적으며, 변경에 강하다. 따라서 TDD는 단순히 코드의 양이 아니라 코드의 질을 향상시키는 데 중점을 둔다.


## 실습

> Sum 도 plus가 가능하도록 구현한다. 

### Red — `testSumPlusMoney()` 테스트 추가

Sum 객체도 plus() 연산이 가능하도록 구현하기 위해 다음 테스트를 추가한다.

**테스트 코드:**
```java
@Test
public void testSumPlusMoney() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);  // ← Sum을 명시적으로 생성
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(15), result);
}
```
- `testSumPlusMoney()` 테스트는 Sum 객체에 plus() 연산이 가능하도록 구현을 유도한다.
- Sum 객체에 plus()가 null을 리턴하기 때문에, `new Sum(fiveBucks, tenFrancs).plus(fiveBucks)`는  컴파일 에러가 발생한다.
- 이 테스트는 Sum 클래스에 plus() 메서드를 추가하도록 강제한다.

### Green — `testSumPlusMoney()` 테스트 통과시키기

**프로덕션 코드:**
```java
// Sum 클래스:
@Override
public Expression plus(Expression addend) {
  return new Sum(this, addend);
}
```
- Sum 클래스의 plus() 메서드에서 null을 반환하는 대신, 새로운 Sum 객체를 생성하여 반환하도록 구현한다.
- 이렇게 하면 `new Sum(fiveBucks, tenFrancs).plus(fiveBucks)`가 컴파일되고 실행될 수 있다.
- 이 변경으로 `testSumPlusMoney()` 테스트가 통과할 것이다.