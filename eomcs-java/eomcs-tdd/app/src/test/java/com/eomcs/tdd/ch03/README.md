# Chapter 3: Equality for All

## 학습 목표

- 값 객체 패턴을 사용할 때 발생할 수 있는 동치성 문제를 이해하고 해결할 수 있다. 
- 동치성 검사를 위해 equals() 메서드를 활용 할 수 있다.
- 삼각측량 (Triangulation) 테스트 기법을 이해하고 활용할 수 있다.

## 핵심 내용

### 값 객체의 특성
- 1장, 2장을 거치며 만든 Dollar 클래스는 "값 객체"의 전형적인 예이다.
- 단순한 '값'으로 사용할 때 적용하는 패턴이다.
- 값 객체는 한 번 생성되면 그 안의 인스턴스 변수 값이 변하지 않아야 한다. (불변 객체)
- 연산이 수행될 때는 항상 새로운 객체를 만들어 반환해야 한다. (예: times() 메서드) 

### 동치성의 필요성
- 값 객체는 내부 값이 같으면 동일한 객체로 취급해야 한다.
- 즉 모든 5달러 객체는 서로 동등해야 한다.

### equals() 재정의
- ch02에서 Dollar를 값 객체(Value Object)로 만들었다.
- 예를 들어 5달러짜리 지폐 두 장은 서로 다른 물체이지만, "값"으로서는 동일하다.
- 그러나 자바의 기본 equals()는 객체 참조(메모리 주소)를 비교하므로,
  내용이 같아도 다른 인스턴스면 false를 반환한다.
- 따라서 값 객체에는 반드시 내용을 비교하도록 equals()를 재정의 해야 한다.

### Green 전략 - 삼각측량(Triangulation)

Triangulation: 두 개 이상의 예시로부터 올바른 일반화를 이끌어내는 전략

- 테스트가 하나뿐이면 Fake It(상수 반환)으로도 통과할 수 있다.
  ```
  assertTrue(new Dollar(5).equals(new Dollar(5)))  →  return true; 
  ```  
- 두 번째 테스트를 추가하면 상수로는 통과할 수 없게 된다.
  ```
  assertFalse(new Dollar(5).equals(new Dollar(6)))  →  return true;
  ```

이 시점에서 일반적인 해결책을 구현해야만 한다. 즉, 두 테스트가 "삼각측량" 하듯 교차하여 올바른 구현으로 수렴시킨다.

## 실습

- $5 + 10 CHF = $10 if rate is 2:1
- ~~$5 × 2 = $10  (완료 - ch01)~~
- amount를 private으로 만들기
- ~~Dollar 부작용(side effect)?  (완료 - ch02)~~
- Money 반올림 처리?
- **equals() 구현  <-- 지금 다루는 항목**
- hashCode() 구현
- Equal null
- Equal object

## Q&A

### 언제 삼각측량(Triangulation) 기법을 쓸까?

- 올바른 추상화가 무엇인지 확신이 없을 때 사용한다.
- Fake It이나 Obvious Implementation보다 느리지만,
  설계 방향이 명확하지 않을 때 안전하게 일반화를 유도할 수 있다.