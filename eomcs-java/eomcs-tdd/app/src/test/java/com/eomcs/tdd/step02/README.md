# 코드의 부작용 문제와 이를 해결하는 방법

이 단계에서는 테스트 코드를 사용하여 **코드의 부작용(side effect) 문제를 드러내고, 이를 해결하기 위한 방법으로 값 객체(Value Object) 패턴을 적용**하는 과정을 다룬다. 또한 TDD 사이클에서 **Fake It과 Obvious Implementation 전략**이 무엇이고 언제 사용할지 판단하는 방법도 설명한다.

## 개념

### 코드의 부작용(side effect)의 예

이전 테스트 코드:
```java
  Dollar five = new Dollar(5);
  five.times(2);
  assertEquals(10, five.amount);
```

부작용을 드러내는 코드 - five 객체로 두 번 곱셈하면 어떻게 될까?
```java
  five.times(2);  → five.amount = 10
  five.times(3);  → five.amount = 30  ← 틀림! 15여야 한다.
```

times()가 객체 내부 상태를 직접 바꾸기 때문에 발생하는 문제다.
이것이 바로 "부작용(side effect)"이다.

### 값 객체(Value Object) 패턴

times()가 현재 객체를 바꾸는 대신, 새로운 Dollar 객체를 반환하도록 만든다. 이렇게 하면 five는 항상 5달러 상태를 유지한다.

### Green 전략 

1. **Fake It (가짜 구현)** :
   - 일단 상수를 반환해서 테스트를 통과시킨다.
   - 그런 다음 단계적으로 상수를 변수로 교체한다.
   - 예)
     ```
     return new Dollar(10);
     →  return new Dollar(amount * 2);
     →  return new Dollar(amount * multiplier);
     ```
2. **Obvious Implementation (명백한 구현)** :
   - 올바른 구현이 명확하다면 바로 타이핑한다.
   - 예) 
     ```
     return new Dollar(amount * multiplier)가 명백하다.
     ```

두 전략을 언제 쓸까?
- 구현이 단순하고 명확하면 → Obvious Implementation
- 어떻게 구현할지 확신이 없으면 → Fake It으로 시작


## 실습

> 곱셈 연산 `times()`을 연속해서 호출하기

### Red 단계

`Dollar` 객체에 부작용이 있음을 드러내는 테스트를 작성한다. 같은 `five` 객체로 `times(2)`, `times(3)`을 연속 호출했을 때 각각 10, 15가 나와야 하는데, `Dollar`는 내부 상태를 직접 변경하므로 두 번째 호출에서 30이 되어 테스트가 실패한다.

### Green 단계

`times()`가 내부 상태를 바꾸는 대신 새로운 `Dollar` 객체를 반환하도록 수정하여 테스트를 통과시킨다.
Fake It 전략으로,
  - `return new Dollar(10)` 
  - → `return new Dollar(amount * 2)` 
  - → `return new Dollar(amount * multiplier)` 

순서로 단계적으로 일반화하는 과정을 보여준다. 중복이 없으므로 Refactor 단계는 생략한다.

## Q&A

### 실무에서는 Fake It 전략과 Obvious Implementation 전략을 어떻게 사용하는가?

- 실무에서는 Fake It 전략과 Obvious Implementation 전략을 번갈아 사용한다.
- 구현이 명백하지 않거나, 어떻게 구현할지 확신이 없을 때 작은 단계로 나눠서 진행하는 Fake It 전략을 사용한다.
- 일이 잘 풀릴 때는 Obvious Implementation 전략을 연속해서 사용하며 속도를 낸다.