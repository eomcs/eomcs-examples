# Chapter 4: Privacy

## 학습 목표

- 테스트 코드에서 객체의 내부 필드에 직접 접근할 때 발생하는 문제점을 이해한다.
- equals() 메서드를 활용하여 테스트 코드와 실제 코드 간의 결합도를 낮추고, 테스트 코드의 의도를 명확히 표현할 수 있다.
- 인스턴스 변수를 안전하게 은닉하기 위해 private 을 활용할 수 있다.

## 핵심 내용

### 문제 인식

기존의 `testMultiplication()`은 `times()`의 결과를 다음처럼 검증한다:

```java
Dollar product = five.times(2);
assertEquals(10, product.amount);  // amount 필드에 직접 접근!
```
- 테스트가 객체 내부 구현에 결합(coupling)되어 있다.

### 테스트 리팩토링

ch03에서 구현한 `equals()`를 활용하면 Dollar 객체끼리 직접 비교할 수 있다:

```java
assertEquals(new Dollar(10), five.times(2));  // 내부 필드 접근 불필요
```

- 테스트가 더 이상 `amount`를 직접 참조하지 않으므로 테스트 코드와 실제 코드의 결합도를 낮춘다.


## 실습

- $5 + 10 CHF = $10 if rate is 2:1
- ~~$5 × 2 = $10  (완료 - ch01)~~
- **amount를 private으로 만들기  ← 지금 다루는 항목**
- ~~Dollar 부작용(side effect)?  (완료 - ch02)~~
- Money 반올림 처리?
- ~~equals() 구현  (완료 - ch03)~~
- hashCode() 구현
- Equal null
- Equal object


## Q&A

### 테스트 코드가 객체 내부를 들여다보는 것이 왜 문제인가?

- 테스트 코드가 객체 내부를 들여다볼수록 캡슐화가 약해진다. 
- 즉 실제 코드의 변경과 유지보수가 어려워진다.

### equals() 메서드를 활용하면 어떤 이점이 있나?

- equals()처럼 공개된 인터페이스를 통해 검증하면 내부 구현을 자유롭게 숨길 수 있다.