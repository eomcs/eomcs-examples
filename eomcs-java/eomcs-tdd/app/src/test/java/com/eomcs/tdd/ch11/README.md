# Chapter 11: The Root of All Evil

## 학습 목표

- **불필요한 하위 클래스 제거:** 단지 생성자만 남아있고 고유한 행위가 없는 하위 클래스들을 상위 클래스로 안전하게 대체하고 완전히 삭제하는 리팩토링 단계를 익힌다.
- **구조 변경에 따른 잉여 테스트 정리:** 과거의 코드 구조(다중 클래스)에서는 의미가 있었지만, 새로운 구조(단일 클래스)에서는 더 이상 시스템 검증에 도움을 주지 않고 오히려 부담(burden)이 되는 기존 테스트를 과감히 삭제하는 기준을 배운다.

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
- **Dollar/Franc 중복 제거  ← 이번 장에서 검토**
- ~~공통 equals()  (완료 - ch06)~~
- ~~공통 times()  (완료 - ch10)~~
- ~~Franc와 Dollar 비교  (완료 - ch07)~~
- ~~Currency?  (완료 - ch09)~~
- **testFrancMultiplication() 삭제?  ← 이번 장에서 검토**

### 실습 소스 파일 구조

ch11/
├── README.md
├── step01_refactor/
│   ├── Money.java   dollar() → new Money() 직접 반환, Dollar.java 삭제
│   ├── Franc.java   아직 존재
│   └── MoneyTest.java
├── step02_refactor/
│   ├── Money.java   franc() → new Money() 직접 반환, Franc.java도 삭제
│   └── MoneyTest.java  (testDifferentClassEquality 제거)
└── step03_refactor/
    ├── Money.java   최종 결과 (Dollar/Franc 없음)
    └── MoneyTest.java  (testFrancMultiplication 제거, testMultiplication으로 통합)


### ch10 step06_refactor 종료 시점의 Dollar/Franc

```java
class Dollar extends Money {
  Dollar(int amount, String currency) { super(amount, currency); }
  // 아무 메서드도 없다 → 완전한 빈 껍데기
}

class Franc extends Money {
  Franc(int amount, String currency) { super(amount, currency); }
  // 아무 메서드도 없다 → 완전한 빈 껍데기
}
```

두 클래스가 하는 일이 하나도 없다. 존재 자체가 중복이다.
"All Evil(모든 악)의 근원"은 바로 이 불필요한 하위 클래스들이다.

### step01: Refactor - Dollar 클래스 제거

**하위 클래스의 무용성 확인 및 대체:** 이전 장들에서 리팩토링을 진행한 결과, Dollar와 Franc 클래스에는 오직 생성자만 남게 되었다. 생성자만 존재하는 것은 하위 클래스를 유지할 충분한 이유가 되지 못한다. 따라서 정적 팩토리 메서드(Money.franc(), Money.dollar())가 기존 하위 클래스 대신 상위 클래스인 Money 객체를 직접 생성하여 반환하도록 수정한다.

팩토리 메서드에서 `new Dollar()` → `new Money()` 로 변경하면 Dollar 클래스를 제거할 수 있다.

```java
// Before:
static Money dollar(int amount) { return new Dollar(amount, "USD"); }

// After:
static Money dollar(int amount) { return new Money(amount, "USD"); }
```

**Dollar 클래스 삭제:** 참조가 모두 Money로 변경되어 코드 내에서 Dollar 클래스에 대한 참조가 완전히 사라지게 되었고, 이를 안전하게 삭제한다. 테스트는 그대로 통과한다.

### step02: Refactor - Franc 클래스 제거

팩토리 메서드에서 `new Franc()` → `new Money()` 로 변경하면 Franc 클래스를 제거할 수 있다.

```java
// Before:
static Money franc(int amount) { return new Franc(amount, "CHF"); }

// After:
static Money franc(int amount) { return new Money(amount, "CHF"); }
```

**잉여 테스트 제거와 Franc 클래스 삭제:** Franc 클래스는 testDifferentClassEquality() (프랑과 달러의 클래스가 다를 때 동치성 비교) 테스트에 여전히 참조되어 있어 바로 삭제할 수 없었다. 하지만 시스템 구조가 단일 Money 클래스로 통합되었기 때문에 **Franc 클래스가 존재하는 상황을 가정한 테스트는 더 이상 도움이 되지 않는 짐(burden)"** 이기 때문에 이 테스트와 함께 Franc 클래스를 완전히 삭제한다.

```java
// Dollar/Franc이 없으므로 이 테스트는 의미가 없어졌다 → 제거
void testDifferentClassEquality() {
  assertTrue(new Money(10, "CHF").equals(new Franc(10, "CHF")));
}
```

### step03: Refactor - 잉여 테스트 정리

**중복 테스트 제거 마무리:** 통화(Currency) 개념의 도입으로 달러와 프랑의 곱셈 로직 간의 차이가 완전히 사라졌기 때문에, testDollarMultiplication()과 testFrancMultiplication()을 통합한다.

