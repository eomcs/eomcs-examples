# Chapter 7: Apples and Oranges

## 학습 목표

- 의문을 테스트로 변환하기: 머리속을 맴도는 설계 상의 의문이나 불쾌한 느낌을 무시하지 않고, 이를 구체적인 자동화 테스트로 변환하여 시스템 결함을 확인할 수 있다.
- 합리적인 타협과 전진: 완벽한 도메인 설계가 아니라도, 현재 직면한 테스트를 통과시킬 수 있는 합리적인 방법을 사용하여 일단 Green 상태로 만드는 실용적인 접근법을 이해한다.
- 설계 결정의 지연: 더 나은 설계가 필요하다는 것을 인지하더라도, 이를 도입할 명확한 동기(테스트)가 아직 없다면 섣부른 추가를 미루는 원칙을 이해한다.

## 핵심 내용

### 문제: 교차 통화 비교 (Apples and Oranges)

- "사과와 오렌지를 비교한다"는 말처럼, Dollar와 Franc을 비교하는 것은 의미가 없다.
- 같은 amount를 가져도 통화가 다르면 같은 값이 아니다.

현재 Money.equals()가 클래스 검사 없이 단순히 amount만 비교한다면 어떻게 될까?

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

- $5 + 10 CHF = $10 if rate is 2:1
- ~~$5 × 2 = $10  (완료 - ch01)~~
- ~~amount를 private으로 만들기  (완료 - ch04)~~
- ~~Dollar 부작용(side effect)?  (완료 - ch02)~~
- Money 반올림 처리?
- ~~equals() 구현  (완료 - ch03)~~
- hashCode() 구현
- Equal null
- Equal object
- ~~5 CHF × 2 = 10 CHF  (완료 - ch05)~~
- Dollar/Franc 중복 제거
- ~~공통 equals()  (완료 - ch06)~~
- 공통 times()
- **Franc와 Dollar 비교  ← 지금 다루는 항목**


### Red: 버그를 드러내는 테스트

```java
assertFalse(new Dollar(5).equals(new Franc(5))); // 실패!
```

동일 객체인지를 검사하지 않기 때문에 이 테스트는 실패한다.

### Green: getClass()로 클래스 검사 추가

```java
@Override
public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
}
```

→ Dollar와 Franc은 서로 다른 클래스이므로 `getClass()` 비교에서 false 반환
→ `assertFalse(new Dollar(5).equals(new Franc(5)))` 통과

## Q&A

### 테스트의 역할?

- 테스트는 **기존 코드가 올바르게 동작하는지 확인**하는 것뿐 아니라,
**놓치고 있던 엣지 케이스를 발견**하는 역할도 한다.
  
- 예) 교차 통화 비교 테스트는 equals()의 숨겨진 결함을 드러냈다.

### 엣지 케이스(Edge Case)란?

- 일반적인 상황에서는 드러나지 않지만, 특정 조건에서 발생하는 예외적인 경우.