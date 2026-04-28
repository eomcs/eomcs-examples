# Refactor 단계를 통해 반드시 중복 코드 제거하라!

이 단계에서는 이전 단계에서 발생한 심각한 코드 중복을 해결하기 위해 공통 상위 클래스를 도입하고, 코드를 점진적으로 상위 클래스로 올리는(pull up) 리팩토링을 수행한다. 

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

> `Dollar`와 `Franc` 클래스이 중복 코드를 제거하기 위해 `Money`라는 공통 부모 클래스를 도입한다.

### Refactor 단계 1 - Money 생성, amount를 Money로 올림

`Money` 공통 부모 클래스를 생성하고 `protected int amount` 필드만 올린다.
`Dollar extends Money`로 변경하여 자신의 `amount` 필드를 제거하고 `Money`에서 상속받는다.
이 단계에서 `equals()`는 아직 `Dollar`에 그대로 유지하며, `Franc`은 아직 변경하지 않는다.
기존 테스트는 모두 그대로 통과한다.

**프로덕션 코드:**
```
- Money 클래스 생성
- Dollar의 amount 필드를 Money로 이동
- `Dollar extends Money` 로 변경
```

### Refactor 단계 2 - equals()를 Money로 올림

`Dollar`의 `equals()`를 `Money`로 이동(pull up method)하여 `Dollar`에서는 `equals()`를 제거한다. 
`Money.equals()`는 `Money other = (Money) obj`로 캐스팅하여 `amount`를 비교한다.
기존 테스트는 모두 그대로 통과한다.

**프로덕션 코드:**
```
- Dollar의 equals()를 Money로 이동
- Money의 equals()에서 `Money other = (Money) obj;`로 타입 캐스팅 변경
```

### Refactor 단계 3 - Franc도 Money를 상속

`Franc extends Money`로 변경하여 `amount` 필드와 `equals()`를 모두 제거하고 `Money`에서 상속받는다.
이로써 `Dollar`와 `Franc`의 `amount` 필드 중복과 `equals()` 중복이 완전히 제거된다.
`times()`는 반환 타입이 각각 `Dollar`, `Franc`으로 달라 아직 `Money`로 올릴 수 없다.

**프로덕션 코드:**
```
- `Franc extends Money`로 변경
- Franc의 amount 제거
- Franc의 equals() 제거
```

### times() 중복은 왜 지금 제거하지 않는가?

- `Dollar.times()`와 `Franc.times()`는 반환 타입이 각각 `Dollar`, `Franc`으로 다르다.
- 반환 타입이 다르면 바로 Money로 올릴 수 없다.
- 두 클래스에 있는 times() 코드를 일치시킨 후에 Money로 올려야 한다.


