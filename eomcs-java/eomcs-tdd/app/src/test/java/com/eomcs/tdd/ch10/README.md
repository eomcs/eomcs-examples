# Chapter 10: Interesting Times

## 학습 목표

- **컴퓨터에게 직접 질문하기:** 머릿속으로 길게 추론하는 대신, 변경 사항을 적용하고 테스트를 실행하여 컴퓨터가 스스로 15초 만에 답을 주도록 하는 실용적인 접근법을 배운다.
- **안전한 실험과 보수적인 롤백(Back out):** 코드를 수정(실험)했다가 예상치 못한 실패(빨간 막대)를 만나면, 그 상태에서 억지로 계속 수정하는 대신 안전한 초록 막대 상태로 되돌린 후(Back out) 새로운 테스트를 통해 문제를 해결하는 보수적인 대처법을 체득한다.
- **예외적인 테스트 생략 (디버깅 목적):** 디버깅 시 더 나은 오류 메시지를 얻기 위해, 실패 위험이 낮은 toString() 같은 메서드는 예외적으로 테스트 작성 없이 곧바로 추가하는 유연함을 배운다.

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
- Dollar/Franc 중복 제거 (진행 중)
- ~~공통 equals()  (완료 - ch06)~~
- **공통 times()   ← 이번 장에서 검토**
- ~~Franc와 Dollar 비교  (완료 - ch07)~~
- ~~Currency?  (완료 - ch09)~~
- testFrancMultiplication() 삭제?

### 실습 소스 파일 구조

ch10/
├── README.md
├── step01_refactor/  → 전진을 위한 일보 후퇴
├── step02_red/       → 실험을 통한 피드백
├── step03_red/       → 디버깅을 위한 toString() 추가
├── step04_red/       → 실험 취소(Back out)와 방어적 코딩
├── step04_green/     → 실험 취소(Back out)와 방어적 코딩
├── step05_refactor/  → times()에서 Money객체를 반환하기
└── step06_refactor/  → times()를 Money로 올림 (push up)


### ch09 step04_refactor 종료 시점의 Dollar/Franc

```java
class Dollar extends Money {
  Dollar(int amount, String currency) { super(amount, currency); }

  @Override
  Money times(int multiplier) { return Money.dollar(amount * multiplier); }
}

class Franc extends Money {
  Franc(int amount, String currency) { super(amount, currency); }

  @Override
  Money times(int multiplier) { return Money.franc(amount * multiplier); }
}
```

두 times()가 `dollar` vs `franc` 팩토리 메서드 이름만 다를 뿐 구조가 완전히 동일하다.
→ `Money.dollar()`는 `new Money(n, "USD")`, `Money.franc()`은 `new Money(n, "CHF")` 를 반환
→ 결국 `return new Money(amount * multiplier, currency)` 로 통일 가능


### step01_refactor: 전진을 위한 일보 후퇴

Franc과 Dollar 두 하위 클래스에 있는 times() 메서드들의 구현을 완전히 똑같이 만들기 위해, 이전 장에서 갓 도입했던 팩토리 메서드 호출을 원래의 생성자 호출(new Franc(), new Dollar())로 다시 인라인화(inline) 하며 한 걸음 뒤로 물러난다.

```java
// Dollar.times()에서 팩토리 메서드 호출 제거
@Override
Money times(int multiplier) {
  return new Dollar(amount * multiplier, "USD");
}

// Franc.times()에서 팩토리 메서드 호출 제거
@Override
Money times(int multiplier) {
  return new Franc(amount * multiplier, "CHF");
}
```

### step02_red: 실험을 통한 피드백

두 메서드 내부의 하드코딩된 상수를 currency 변수로 바꾼 뒤, Franc이나 Money 중 어느 것을 반환해도 상관없을지 고민해 본다. 

```java
// Dollar.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Dollar(amount * multiplier, currency);
}

// Franc.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Franc(amount * multiplier, currency);
}
```

이때 몇 분간 추론하는 대신, 직접 반환 타입을 Money로 변경해보고 테스트를 실행하여 컴퓨터에게 답을 묻는 실험을 진행한다.

```java
// Dollar.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Money(amount * multiplier, currency);
}

// Franc.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Money(amount * multiplier, currency);
}

// Money 클래스를 추상 클래스에서 concrete 클래스로 변경해야 한다.
abstract class Money {
  // ...

  // times()를 추상 메서드에서 구현 메서드로 변경해야 한다.
  Money times(int multiplier) { return null; }
}
```

다음 테스트 코드에서 오류가 발생한다.

```java
@Test
void testDollarMultiplication() {
  Money five = Money.dollar(5);
  assertEquals(Money.dollar(10), five.times(2));
  assertEquals(Money.dollar(15), five.times(3));
}
```

오류 메시지에서 객체 상태가 "Money@1a2b3c" 처럼 나와서 읽기 어렵다. 이때 toString() 메서드를 추가하여 객체의 상태를 명확히 출력하도록 한다.

### step03_red: 디버깅을 위한 toString() 추가

실험 결과 컴파일 에러와 함께 빨간 막대가 나타나지만 에러 메시지(객체 참조값)가 불명확할 수 있다. 이때 결과를 명확히 보기 위해 테스트 없이 toString() 메서드를 추가해서 객체의 상태를 출력한다. 빨간 막대 상태에서는 새 테스트를 작성하지 않는 것이 원칙이며, 출력용 메서드라 실패 위험이 낮다.

```java
// Money에 추가
@Override
public String toString() {
  return amount + " " + currency;
}
```

### step04_red: 실험 취소(Back out)와 방어적 코딩

toString() 덕분에 에러의 원인이 equals() 메서드가 화폐 단위(currency) 대신 여전히 클래스 자체(getClass())를 비교하고 있기 때문임을 알아낸다. 빨간 막대 상태에서 곧바로 모델 코드를 고치지 않고, 안전한 초록 막대 상태로 돌아가기 위해 실험(변경 사항)을 과감히 취소(Back out) 한다.

```java
// Money.equals()
@Override
public boolean equals(Object object) {
  Money money = (Money) object;
  return amount == money.amount
      && getClass() == money.getClass();  // 클래스 타입 비교
}
```

### step04_green: 실험 취소(Back out)와 방어적 코딩

equals() 메서드가 클래스 타입 대신 화폐 단위(currency)를 비교하도록 변경한다. 

```java
// Money.equals()
@Override
public boolean equals(Object object) {
  Money money = (Money) object;
  return amount == money.amount
      && currency.equals(money.currency);  // 화폐 단위 비교
}
```

### step05_refactor: times()에서 Money객체를 반환하기

equals()에서 타입이 아니라 currency를 비교하도록 변경했으므로, 이제 Franc과 Dollar의 times()는 모두 Money 객체를 반환한다.

```java
// Dollar.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Money(amount * multiplier, currency);
}
// Franc.times()에서 반환 타입을 Money로 변경
@Override
Money times(int multiplier) {
  return new Money(amount * multiplier, currency);
}
```

### step06_refactor: times()를 Money로 올림 (push up)

Dollar.times()와 Franc.times()가 이제 완전히 동일한 구조이므로, 이 메서드를 Money로 올린다.

```java
// Money의 times()를 변경한다.
Money times(int multiplier) {
  return new Money(amount * multiplier, currency);
}

// Dollar/Franc에서 times()를 제거한다.
```

## 핵심 교훈

> "중복된 테스트를 없애는 것은 중복된 코드를 없애는 것만큼 중요하다."

times()가 Money로 올라간 후 Franc/Dollar는 생성자만 남는다.
이 두 클래스를 완전히 없애는 것이 다음 챕터(ch11)의 과제다.
