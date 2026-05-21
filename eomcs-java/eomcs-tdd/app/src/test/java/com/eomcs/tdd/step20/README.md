# 일회용 도구(spike)로서의 테스트

이 단계에서는 일회용 도구로서의 테스트를 체험해본다.

## 개념

### 실험은 실패해도 괜찮다!

모든 테스트가 영구적일 필요는 없다. 어떤 테스트는 "이 설계 방향이 좋은가?" 를 탐색하는 일회용 도구(spike)이고, 답이 "아니오"라면 미련 없이 버리는 것이 옳다. 실패한 실험은 실패가 아니라, "이 길은 막혔다" 는 정보를 얻은 성공이다.

**테스트는 목적을 위한 도구이며, 목적이 사라지면 도구도 정리하는 것이 옳다.**

## 실습

> 같은 통화끼리 더할 때는 Sum이 아니라 Money 객체를 반환하도록 설계 변경을 시도해 보자.

### Red — `testPlusSameCurrencyReturnsMoney()` 테스트 추가

$5 + $5처럼 같은 통화끼리 더할 때는 Sum이 아니라 Money를 바로 반환하면 어떨까" 라는 최적화를 실험해 본다.

**테스트 코드:**
```java
@Test
public void testPlusSameCurrencyReturnsMoney() {
    Expression sum = Money.dollar(1).plus(Money.dollar(1));
    assertTrue(sum instanceof Money);
}
```
- Money.dollar(1).plus(Money.dollar(1))의 결과가 Sum이 아니라 Money 객체인지 검증하는 테스트 추가

이 테스트는 현재 설계에서는 실패할 것이다. 왜냐하면 Money.plus()가 항상 Sum 객체를 반환하도록 구현되어 있기 때문이다. 

### Green - `testPlusSameCurrencyReturnsMoney()` 테스트 제거하기

`testPlusSameCurrencyReturnsMoney()` 테스트 **외부 동작이 아니라 구현의 내부를 검사**하므로 못생긴 코드다. 답이 "아니오"라고 판단되면, 미련 없이 테스트를 버리는 것이 옳다. 실패한 실험은 실패가 아니라, "이 길은 막혔다" 는 정보를 얻은 성공이라 생각하자.