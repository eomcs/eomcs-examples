# Chapter 9: Times We're Livin' In

## 학습 목표

- **설계의 교착 상태 돌파하기:** 큰 설계 문제(하위 클래스 제거)를 당장 해결하기 막막할 때, 눈에 띄는 더 작고 구체적인 작업('통화' 개념 도입)부터 시작하여 점진적으로 실마리를 풀어가는 접근법을 익힌다.
- **변이를 호출자로 이동시켜 생성자 일치시키기:** 두 하위 클래스에 하드코딩된 차이점(통화 문자열)을 호출자(정적 팩토리 메서드)의 매개변수로 이동시켜 생성자의 형태를 동일하게 맞추고, 이를 상위 클래스로 끌어올리는(Push up) 리팩토링 기법을 배운다.
- **TDD의 보폭(Step Size) 조절 체득:** TDD는 정해진 보폭만 고집하는 것이 아니라 지속적인 '조향(steering)' 과정임을 이해한다. 확신이 부족할 때는 아주 작은 단계로 기어를 낮추고, 구조가 명확할 때는 한 번에 큰 단계(One swell foop)로 도약하는 유연함을 기른다.

## 핵심 내용

### 통화(Currency) 개념과 테스트 도입

불필요해진 잉여 하위 클래스(Dollar, Franc)를 제거하기 위한 단서로 '통화(Currency)' 개념을 도입한다. 이를 위해 Money.dollar(1).currency()가 "USD"를 반환하는지 확인하는 testCurrency() 테스트를 우선 작성한다.

### 작은 보폭을 통한 점진적 리팩토링

Franc 클래스에 먼저 currency 인스턴스 변수를 추가하고, 정적 팩토리 메서드(Money.franc())에서 "CHF"라는 문자열을 생성자의 매개변수로 전달하도록 수정한다. 이 과정에서 잠시 리팩토링을 멈추고 times() 메서드가 팩토리 메서드를 호출하도록 곁가지 작업을 처리하는 등, 실수를 방지하기 위해 아주 세밀한 단계를 밟는다.

### 큰 보폭을 활용한 단번의 수정

Franc을 수정하며 변경 구조를 확실히 파악했다면, Dollar 클래스에 동일한 구조를 적용할 때 작은 단계를 생략하고 한 번에(in one swell foop) 여러 수정을 가하여 코드를 변경한다.  이는 TDD가 상황에 따라 보폭을 자유자재로 조절할 수 있음을 보여주는 사례다.

### 상위 클래스로 생성자 밀어 올리기(Push up)

호출자가 통화 문자열("CHF", "USD")을 전달하게 만들면서 두 하위 클래스의 생성자는 (int amount, String currency) 형태로 완전히 동일해졌다. 이 동일한 생성자를 공통 상위 클래스인 Money로 끌어올림으로써 중복을 제거하고, 하위 클래스들의 역할을 최소화하여 제거 준비를 마친다.


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
- Dollar/Franc 중복 제거 (진행 중 - ch08에서 일부 수행)
- ~~공통 equals()  (완료 - ch06)~~
- 공통 times()  
- ~~Franc와 Dollar 비교  (완료 - ch07)~~
- **Currency?  ← 지금 다루는 항목**
- testFrancMultiplication() 삭제?

### 실습 소스 파일 구조

ch09/
├── README.md
├── step01_red/         → currency() 테스트 추가 (메서드 없어서 컴파일 오류)
├── step01_green/       → 각 클래스에 currency() 메서드 추가
├── step01_refactor/    → Dollar/Franc에 각자 currency 필드 추가
├── step02_refactor/    → currency를 Money로 올리기
├── step03_refactor/    → times()를 동일하게 만들기
└── step04_refactor/    → 생성자에 currency 값을 전달하기

### step01: Red - testCurrency() 테스트 작성

```java
assertEquals("USD", Money.dollar(1).currency());
assertEquals("CHF", Money.franc(1).currency());
```

currency() 메서드가 없으므로 컴파일 오류 → Red 상태

### step01: Green - currency() 메서드 추가

각 클래스에 currency() 메서드를 추가하여 테스트 통과 → Green 상태
```java
String currency() { return "USD"; }  // Dollar
String currency() { return "CHF"; }  // Franc
abstract String currency();  // Money
```

### step01: Refactor - Dollar/Franc에 currency 필드 추가

각 하위 클래스에 currency 필드를 추가하여 테스트 통과

### step02: Refactor - currency를 Money로 올리기

- currency 필드와 생성자를 Money로 이동
- Dollar, Franc은 super(amount, "USD") / super(amount, "CHF") 호출

### step03: Refactor - times()를 동일하게 만들기

- times()에서 Money의 팩토리 메서드를 사용하여 객체를 생성하게 만든다.

```java
// Before (ch08):
Money times(int multiplier) { return new Dollar(amount * multiplier); }  // Dollar
Money times(int multiplier) { return new Franc(amount * multiplier); }   // Franc

// After:
Money times(int multiplier) { return Money.dollar(amount * multiplier); } // Dollar
Money times(int multiplier) { return Money.franc(amount * multiplier); }  // Franc
```

이제 두 times()는 `dollar` vs `franc` 이름만 다를 뿐 구조가 완전히 동일하다.
이 차이를 데이터(currency 문자열)로 표현하면 하나로 합칠 수 있다.

### step04: Refactor - 생성자에 currency 값을 전달하기

- Dollar, Franc의 생성자는 (int amount, String currency) 형태로 변경
