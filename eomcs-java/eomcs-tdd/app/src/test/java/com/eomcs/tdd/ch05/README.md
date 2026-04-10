# Chapter 5: Franc-ly Speaking

## 학습 목표

- 새로운 요구사항을 빠르게 테스트로 표현하고, 빠르게 Green을 달성하기 위한 Copy-Paste 전략을 이해한다.
- 빠른 Green을 위해 중복을 허용하는 결정을 내릴 수 있음을 이해한다.

## 핵심 내용

### TDD의 우선 순위

- 1순위: 일단 동작하게 만들어라 (Make it work)
- 2순위: 올바르게 만들어라 (Make it right; Refactor 단계) 
- 3순위: 빠르게 만들어라 (Make it fast)

> "중복은 나쁘다. 하지만 **지금 당장 완벽한 설계를 만들려는 욕심**이 더 나쁘다."

### Green 전략: 죄를 저질러라 (Copy-Paste)

- 이 장에서는 **빠르게 Green을 달성**하는 것을 최우선으로 한다.
- **복사 & 붙여넣기(Copy-Paste)**를 활용하여 새로운 요구사항을 빠르게 테스트로 표현하고, 빠르게 Green을 달성한다.
- 예: 프랑(Franc) 지원하기
  ```
  즉 다중 통화(multi-currency)를 지원하려면 달러뿐 아니라 스위스 프랑(CHF)도 처리해야 한다.

  Dollar → Franc
    - 클래스명만 Dollar → Franc으로 변경
    - 나머지 로직은 완전히 동일
  ```

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
- **5 CHF × 2 = 10 CHF  ← 지금 다루는 항목**
- Dollar/Franc 중복 제거
- 공통 equals()
- 공통 times()

> 이번 장의 목표는 "Make it work" 단계다. 중복은 나중에 제거한다.

이 방식은 TDD에서 허용되는 "죄"다.
중요한 것은 Green을 빠르게 달성한 뒤, **반드시** Refactor 단계에서 중복을 제거한다는 것이다.

### Red 단계

`testFrancMultiplication()` 작성 → Franc 클래스가 없으므로 컴파일 오류 발생

### Green 단계

Dollar를 복사·붙여넣기 하여 Franc 클래스 생성 → 테스트 통과

## Q&A

### 복사/붙여넣기가 정답이라고요?!

Java OOP를 먼저 배운 개발자는 본능적으로 **"상속부터 써야지"** 라고 생각할 수 있다. 하지만 Beck의 메시지는:

- 지금 `Franc`과 `Dollar`의 관계가 **상속이 맞는지 아직 모른다**
- 먼저 동작하는 코드를 만들고, **테스트가 보호하는 상태에서** 설계를 개선하라
- 섣부른 추상화보다 **명백한 중복**이 더 낫다

```
섣부른 추상화 → 잘못된 설계를 굳혀버림
명백한 중복  → 올바른 추상화를 찾을 때까지 유연성 유지
```