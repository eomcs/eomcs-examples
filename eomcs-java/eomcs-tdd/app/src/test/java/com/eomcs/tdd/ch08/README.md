# Chapter 8: Makin' Objects

## 학습 목표

- **팩토리 메서드(Factory Method) 도입:** 구체적인 하위 클래스(Subclass)에 대한 의존성을 끊어내고, 테스트 코드가 구체 클래스의 존재를 알지 못하도록 분리(Decouple)하는 방법으로 팩토리 메서드 패턴을 활용할 수 있다.
- **점진적인 하위 클래스 제거 준비:** 불필요한 하위 클래스들을 제거하기 위해 단계적으로 안전하게 접근하는 방법을 수행할 수 있다.
- **컴파일러 피드백의 활용:** 설계 변경 과정에서 발생하는 컴파일 에러를 길잡이 삼아, 필요한 추상 메서드를 상위 클래스에 추가하며 설계를 유기적으로 수정하는 방법을 이해한다.

## 핵심 내용

### 문제: times() 중복

ch07 종료 시점의 Dollar와 Franc:

```
Dollar.times()   │  Franc.times()
─────────────────┼─────────────────
Dollar times() { │  Franc times() {
  return new     │    return new
    Dollar(      │      Franc(
      amount *   │        amount *
      multiplier │        multiplier
    );           │      );
}                │  }
```

구조가 완전히 동일한데 반환 타입(Dollar vs Franc)과 생성자 호출만 다르다.
times()를 Money로 올리려면 먼저 반환 타입을 `Money`로 통일해야 한다.


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
- **Dollar/Franc 중복 제거 ← 지금 다루는 항목**
- ~~공통 equals()  (완료 - ch06)~~
- 공통 times() 
- ~~Franc와 Dollar 비교  (완료 - ch07)~~
- Corrency?

### 실습 소스 파일 구조

ch08/
├── README.md
├── step01/     → times() 반환 타입만 Money로 변경, 테스트 코드 그대로
│   ├── Money.java
│   ├── Dollar.java   Money times() { return new Dollar(...); }
│   ├── Franc.java    Money times() { return new Franc(...); }
│   └── MoneyTest.java
├── step02/     → 팩토리 메서드 도입, 테스트가 구체 클래스에서 분리
│   ├── Money.java    static Money dollar() / franc() 추가
│   ├── Dollar.java
│   ├── Franc.java
│   └── MoneyTest.java
└── step03/     → Money에 times() 추상 메서드 추가
    ├── Money.java    abstract class Money { abstract Money times(int multiplier); }
    ├── Dollar.java
    ├── Franc.java
    └── MoneyTest.java


### step01: times() 반환 타입을 Money로 변경

**메서드 시그니처 일치시키기:** Franc과 Dollar의 times()가 반환하는 타입을 공통 상위 클래스인 Money로 변경하여 두 메서드를 비슷한 형태로 만든다

```java
// Before:
Dollar times(int multiplier) { return new Dollar(amount * multiplier); }
Franc  times(int multiplier) { return new Franc(amount * multiplier); }

// After:
Money times(int multiplier) { return new Dollar(amount * multiplier); }
Money times(int multiplier) { return new Franc(amount * multiplier); }
```

반환 타입만 바꾸는 것이므로 기존 테스트는 그대로 통과한다. (Green 유지)

### step02: 팩토리 메서드 도입

테스트가 `new Dollar(5)`, `new Franc(5)` 처럼 구체 클래스 생성자를 직접 호출하면, 하위 클래스가 바뀔 때마다 테스트도 수정해야 한다. (강한 결합)

**팩토리 메서드(Factory Method) 추출:** 하위 클래스를 제거하려면 먼저 클라이언트(테스트 코드)가 하위 클래스를 직접 참조하지 않아야 한다. 이를 위해 Money.dollar(5)와 같이 객체를 생성해 반환하는 정적 팩토리 메서드를 Money 클래스에 도입한다

```java
// Money 클래스에 추가:
static Money dollar(int amount) { return new Dollar(amount); }
static Money franc(int amount)  { return new Franc(amount);  }
```

**테스트 코드의 의존성 제거(Decoupling):** 테스트 코드 내에 있던 new Dollar(5) 등의 구체적인 객체 생성 코드를 모두 팩토리 메서드(Money.dollar(5))로 교체한다. 이로써 테스트 코드는 더 이상 Dollar나 Franc이라는 하위 클래스의 존재를 알 필요가 없게 되며(Decoupling), 향후 상속 구조를 마음대로 변경할 수 있다

```java
// Before:
Dollar five = new Dollar(5);
assertEquals(new Dollar(10), five.times(2));

// After:
Money five = Money.dollar(5);
assertEquals(Money.dollar(10), five.times(2));
```

이제 테스트는 Dollar, Franc이라는 구체 클래스를 전혀 알지 못해도 된다.
Dollar와 Franc 클래스는 `Money` 내부 구현의 세부 사항이 된다.

단, Money 클래스에 times() 메서드가 없기 때문에 컴파일 에러가 발생한다. (Red 상태)

### step03: Money에 times() 추상 메서드 추가

팩토리 메서드를 적용하여 변수 타입을 Money로 변경하면, Money 클래스에는 아직 times() 메서드가 없기 때문에 컴파일 에러가 발생한다. 이를 해결하기 위해 Money 클래스에 times() 메서드를 추가한다. 

**추상 클래스와 추상 메서드 선언:** times() 메서드는 하위 클래스마다 구현이 다르므로, Money 클래스의 times()는 추상 메서드로 선언한다. Money에 times() 추상 메서드를 추가하면 Money 클래스는 추상 클래스(abstract class)로 선언해야 한다. 

```java
// Money 클래스 수정:
abstract class Money {
  // ...
  abstract Money times(int multiplier);
}
```

이렇게 하면 Money 클래스는 더 이상 인스턴스화할 수 없는 추상 클래스가 되고, times() 메서드는 하위 클래스에서 반드시 구현해야 하는 추상 메서드가 된다. 이제 Money.times() 메서드를 호출할 수 있다. (step02 red → step03 green)


## Q&A

### 팩토리 메서드의 이점?

1. **결합도 감소**: 클라이언트(테스트)가 구체 클래스(Dollar, Franc)를 알 필요가 없다.
2. **유연성 확보**: 나중에 Dollar/Franc 대신 단일 Money 클래스를 반환하도록 바꿔도 테스트는 변경 불필요.
