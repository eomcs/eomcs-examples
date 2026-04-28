# 의심되는 부분이 있다면 테스트로 확인하라! 

이 단계에서는 의심되는 부분을 테스트로 확인해보는 과정을 다룬다. 

## 개념

### TDD의 실용적 실천 원칙

- **의문을 테스트로 변환하기:** 설계 상의 의문이나 불쾌한 느낌이 들 때, 이를 무시하지 않고 구체적인 자동화 테스트로 변환하여 시스템의 결함을 확인한다. 
- **합리적인 타협과 전진:** 완벽한 도메인 설계가 아니라도 현재 직면한 테스트를 통과시킬 수 있는 합리적인 방법을 사용하여 일단 Green 상태로 만든다.
- **설계 결정의 지연:** 더 나은 설계가 필요하다는 것을 인지하더라도 이를 도입할 명확한 동기(테스트)가 아직 없다면 섣부른 추가를 미룬다.

### 테스트의 역할

테스트는 **기존 코드가 올바르게 동작하는지 확인**하는 것뿐 아니라, **놓치고 있던 엣지 케이스를 발견**하는 역할도 한다. 

> 예) 교차 통화 비교 테스트는 equals()의 숨겨진 결함을 드러냈다.

#### 엣지 케이스(Edge Case)란?

일반적인 상황에서는 드러나지 않지만, 특정 조건에서 발생하는 예외적인 경우.


### 문제: 교차 통화 비교 (Apples and Oranges)

- "사과와 오렌지를 비교한다"는 말처럼, Dollar와 Franc을 비교하는 것은 의미가 없다.
- 같은 amount를 가져도 통화가 다르면 같은 값이 아니다.

현재 Money.equals()가 클래스 검사 없이 단순히 amount만 비교하고 있다.

```java
// 클래스 검사가 없는 naive한 구현:
@Override
public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount; // amount만 비교!
}
```
→ `new Dollar(5).equals(new Franc(5))` 는 **true** 를 반환한다.
→ 5달러와 5프랑은 **다른 값**인데 같다고 판단하는 버그다.


## 실습

> 교차 통화 비교 테스트를 추가한다.

### Red 단계 - 버그를 드러내는 테스트

교차 통화 비교 테스트 `assertFalse(new Dollar(5).equals(new Franc(5)))`를 추가한다. `Money.equals()`가 클래스 검사 없이 `amount`만 비교하므로 `5 == 5`가 되어 `true`를 반환하고 테스트가 실패한다.

**테스트 코드:**
```
- testEquality()에 교차 통화 비교 테스트 추가
```

### Green 단계 - getClass()로 클래스 검사 추가

`Money.equals()`에 `getClass() != obj.getClass()` 검사를 추가하여 버그를 수정한다.
`Dollar.class`와 `Franc.class`는 서로 다른 클래스이므로 `false`를 반환하여 테스트가 통과한다.

**프로덕션 코드:**

```
- Money.equals()에 getClass() 검사 추가
```

**instanceof vs getClass():**

- `instanceof` 는 하위 클래스도 `true`로 처리 → 이 경우 원하지 않는 동작 가능
- `getClass()` 는 정확히 같은 클래스일 때만 `true` → 통화 비교에 더 적합



