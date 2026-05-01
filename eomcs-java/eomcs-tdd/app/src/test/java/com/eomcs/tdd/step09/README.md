# 리팩토링의 유연성을 강화하는 방법 - 테스트 코드의 프로덕션 코드에 대한 의존성 제거

이 단계에서는 테스트 코드가 프로덕션 코드에 의존하는 문제를 해결하기 위해 팩토리 메서드 패턴을 도입하는 방법을 학습한다.

## 개념

### 테스트 코드의 프로덕션 코드에 대한 의존성

테스트 코드가 실제 프로덕션 코드(모델 코드)에 강하게 의존할 경우, 한쪽을 변경할 때 다른 한쪽도 반드시 변경해야만 하는 종속적인 문제가 발생한다. 

의존성이 코드에 미치는 영향:
- **코드 변경의 어려움과 유지보수 비용 증가:** 스티브 프리먼(Steve Freeman)은 "테스트와 코드 사이의 가장 근본적인 문제는 중복 그 자체보다 '의존성'에 있다"고 지적했다. 테스트 코드가 프로덕션 코드의 내부 구현 세부 사항에 깊이 의존하고 있다면, 프로덕션 코드의 구조를 조금만 개선하려 해도 관련된 수많은 테스트가 모두 깨지게 된다. 결국 코드를 수정할 때마다 테스트 코드까지 함께 수정해야 하는 막대한 부담이 생긴다.

의존성 제거로 얻는 이점:
- **리팩토링의 자유 확보 (결합도를 끊어냈을 때의 효과):** 예를 들어, 테스트 코드 내부에서 프로덕션 클래스를 직접 생성(new Dollar(5))하던 의존성을 팩토리 메서드(Money.dollar(5))로 대체하여 결합도를 낮출 수 있다. 이렇게 테스트가 프로덕션 코드의 구체적인 하위 클래스 존재를 알지 못하게 분리(Decoupling)하면, 어떠한 모델 코드에도 악영향을 주지 않고 상속 구조를 마음껏 바꾸거나 불필요한 클래스를 완전히 제거할 수 있는 유연성과 자유를 얻는다.

중복 제거와 의존성: 
- **중복 제거를 통한 의존성 제거:** 프로그래밍에서 논리의 중복을 제거하면 자연스럽게 의존성도 함께 제거된다. TDD에서 다음 테스트로 넘어가기 전에 반드시 중복을 제거하라고 강조하는 이유도, 의존성을 미리 낮춰두어야 다음 테스트를 작성할 때 단 하나의 코드 변경만으로도 프로그램이 올바르게 작동할 가능성이 극대화되기 때문이다.


### 문제: 테스트 코드와 구체 클래스의 결합

테스트 코드가 `new Dollar(5)`, `new Franc(5)`처럼 구체 클래스의 생성자를 직접 호출하는 방식은 테스트와 구체 클래스 간의 강한 결합을 초래한다.

```java
// 테스트 코드 예시:
@Test
void testDollarMultiplication() {
  Dollar five = new Dollar(5);
  assertEquals(new Dollar(10), five.times(2));
  assertEquals(new Dollar(15), five.times(3));
}
```
### 해결책: 팩토리 메서드 도입

`Money` 클래스에 `static Money dollar(int amount)`와 `static Money franc(int amount)` 팩토리 메서드를 추가하여, 테스트 코드가 구체 클래스 대신 `Money`의 팩토리 메서드를 통해 객체를 생성하도록 변경한다.
이렇게 하면 테스트 코드가 `Money` 클래스에만 의존하게 되어 구체 클래스에 대한 결합이 끊어진다.

```java
// 테스트 코드 예시:
@Test
void testDollarMultiplication() {
  Money five = Money.dollar(5); 
  assertEquals(Money.dollar(10), five.times(2)); 
  assertEquals(Money.dollar(15), five.times(3));
}
```

### 팩토리 메서드의 이점?

1. **결합도 감소**: 클라이언트(테스트)가 구체 클래스(Dollar, Franc)를 알 필요가 없다.
2. **유연성 확보**: 나중에 Dollar/Franc 대신 Money의 다른 하위 클래스를 반환해도 테스트는 변경할 필요가 없다.

## 실습

> 프로덕션 하위 클래스에 대한 테스트 코드의 의존성을 제거하기 위해 팩토리 메서드를 도입한다. 

### Red 단계 - 팩토리 메서드 도입 준비

테스트가 `new Dollar(5)`, `new Franc(5)`처럼 구체 클래스 생성자를 직접 호출하는 결합을 제거하기 위해
`Money.dollar(5)`, `Money.franc(5)` 팩토리 메서드로 교체한다.
그러나 `Money` 클래스에 `times()` 메서드가 없으므로 컴파일 에러가 발생한다.

**테스트 코드:**

```
testDollarMultiplication() 변경
  - `new Dollar(5)` → `Money.dollar(5)`

testFrancMultiplication() 변경
  - `new Franc(5)` → `Money.franc(5)`

testEquality() 변경
  - `new Dollar(5)` → `Money.dollar(5)`
  - `new Franc(5)` → `Money.franc(5)`
```

### Green 단계 - 팩토리 메서드 적용

`Money` 클래스에 `static Money dollar(int amount)`와 `static Money franc(int amount)` 팩토리 메서드를 추가한다.
`Money` 클래스에 `times()` 메서드를 추가하여 컴파일을 통과한다.
`Dollar`와 `Franc`은 `Money`의 내부 구현 세부 사항이 되며, 테스트는 `Money`만 알면 된다.
즉 테스트와 `Money`의 하위 클래스 간의 결합이 팩토리 메서드를 통해 완전히 끊어진다.

**프로덕션 코드:**
```
Money 클래스 변경
  - `static Money dollar(int amount)` 추가
  - `static Money franc(int amount)` 추가
  - times() 추가

Dollar 클래스 변경
  - times()에 오버라이드 애노테이션 추가

Franc 클래스 변경
  - times()에 오버라이드 애노테이션 추가
```
### Refactor 단계 - times() 추상 메서드로, Money를 추상 클래스로 변경

`times()` 메서드는 하위 클래스에서 구현되어야 하므로 `Money` 클래스에 `abstract Money times(int multiplier)` 추상 메서드로 선언한다.
`Money` 클래스가 추상 메서드를 갖기 때문에 `abstract class` 추상 클래스로 변경한다.

**프로덕션 코드:**
```
Money 클래스 변경
  - times()를 추상 메서드로 변경
  - 추상 클래스로 선언
```

