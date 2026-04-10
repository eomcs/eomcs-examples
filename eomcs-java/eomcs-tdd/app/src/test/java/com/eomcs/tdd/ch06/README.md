# Chapter 6: Equality for All, Redux

## 학습 목표

- 두 클래스 간에 발생하는 심각한 코드 중복을 해결하기 위해 공통 상위 클래스를 도입하고, 코드를 점진적으로 상위 클래스로 올리는(pull up) 리팩토링을 수행할 수 있다.
- 서로 다른 두 구현 코드를 완전히 동일한 형태로 맞춘(reconcile) 후, 중복되는 코드를 삭제하는 안전하고 보수적인 리팩토링 전략을 이해한다.


## 핵심 내용

### 중복 제거 전략: Pull Up (위로 올리기)

Dollar와 Franc은 아래 코드가 완전히 동일하다:

```
Dollar          │ Franc
────────────────┼────────────────
int amount      │ int amount         ← 동일
equals()        │ equals()           ← 완전히 동일
times()         │ times()            ← 반환 타입만 다름 (다음 챕터)
```

해결책: 공통 상위 클래스 `Money`를 도입하여 중복 코드를 위로 올린다.

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
- **공통 equals() ← 지금 다루는 항목**
- 공통 times()
- Franc와 Dollar 비교


### Refactor 01: Money 생성, amount를 Money로 올림

- `Money` 클래스 생성: `protected int amount` 필드만 가짐
- `Dollar extends Money`: 자신의 `amount` 필드 제거 (Money에서 상속)

### Refactor 02: equals()를 Money로 올림

- Dollar의 `equals()`를 Money로 이동
- `Dollar`: `equals()` 제거 (Money에서 상속)
- `Franc`: 아직 Money를 상속하지 않음 → 자신의 `equals()` 그대로 유지

### Refactor 03: Franc도 Money를 상속

- `Franc extends Money`: `amount` 필드와 `equals()` 제거
- 새 테스트 추가: `assertFalse(new Franc(5).equals(new Dollar(5)))`
  → Dollar와 Franc은 같은 amount라도 다른 통화이므로 동등하지 않아야 한다.
  → `getClass()` 비교로 이미 처리됨을 확인한다.

## Q&A

### times() 중복은 왜 지금 제거하지 않는가?

- `Dollar.times()`와 `Franc.times()`는 반환 타입이 각각 `Dollar`, `Franc`으로 다르다.
- 반환 타입이 다르면 바로 Money로 올릴 수 없다.
- 이 문제도 이후 챕터에서 해결한다.

### Dollar와 Franc을 비교한다면?

- `getClass()` 비교 방식은 동작하지만, 추후 통화(currency) 개념을 도입하면 더 나은 설계로 바꿀 수 있다.
- equals()에서 `getClass()` 대신 통화 코드("USD", "CHF")를 비교하는 방식이 더 명확하다.
- 이 문제는 이후 챕터에서 다룬다.


