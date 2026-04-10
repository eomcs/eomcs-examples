# Chapter 1: Multi-Currency Money

## 학습 목표

- TDD(Test-Driven Development) 사이클(Red → Green → Refactor)을 이해한다.
- TDD의 핵심 5단계 주기에 대해 설명할 수 있다.
- 테스트와 코드 사이의 중복이 왜 문제인지 이해한다.
- 중복을 제거하여 테스트와 코드 사이의 의존성을 없애는 방법을 연습한다.

## 핵심 내용

### TDD 작업 방식

1. 작은 테스트를 하나 추가한다.
2. 모든 테스트를 실행하고 새로 추가한 테스트가 실패하는 것을 확인한다.
3. 코드를 조금 변경한다.
4. 모든 테스트를 실행하고 전부 통과하는 것을 확인한다.
5. 리팩토링을 통해 중복을 제거한다.

### TDD red-green-refactor 사이클

- Red: 작동하지 않는 작은 테스트를 먼저 작성한다.
- Green: 하드 코딩을 해서라도 가장 빠르게 테스트가 통과하도록 만든다.
- Refactor: 중복을 제거하고 코드를 개선한다.

## 실습

- $5 + 10 CHF = $10 if rate is 2:1
- **$5 × 2 = $10  <-- 지금 다루는 항목**
- amount를 private으로 만들기
- Dollar 부작용(side effect)?
- Money 반올림 처리?





