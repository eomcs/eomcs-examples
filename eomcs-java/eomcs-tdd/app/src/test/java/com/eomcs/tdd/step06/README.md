# 일단 동작하게 만들자! Copy-Paste 전략

이 단계에서는 새로운 요구사항을 빠르게 테스트로 표현하고, 빠르게 Green을 달성하기 위한 Copy-Paste 전략을 소개한다. 중복되더라도 빠른 Green을 위해서는 허용할 수 있다. 중요한 것은 **Green을 달성한 뒤, Refactor 단계에서 중복을 제거**하는 것이다.

## 개념

### TDD의 우선 순위

- 1순위: 일단 동작하게 만들어라 (Make it work)
- 2순위: 올바르게 만들어라 (Make it right; Refactor 단계) 
- 3순위: 빠르게 만들어라 (Make it fast)

> "중복은 나쁘다. 하지만 **지금 당장 완벽한 설계를 만들려는 욕심**이 더 나쁘다."

### 죄를 저질러라!

- **복사 & 붙여넣기**를 활용하여 새로운 요구사항을 빠르게 테스트로 표현하고, 빠르게 Green을 달성한다.
- 예: 프랑(Franc) 지원하기
  ```
  즉 다중 통화(multi-currency)를 지원하려면 달러뿐 아니라 스위스 프랑(CHF)도 처리해야 한다.

  Dollar → Franc
    - 클래스명만 Dollar → Franc으로 변경
    - 나머지 로직은 완전히 동일
  ``` 

Java OOP를 먼저 배운 개발자는 본능적으로 **"상속부터 써야지"** 라고 생각할 수 있다. 하지만 Beck의 메시지는:

- 지금 `Franc`과 `Dollar`의 관계가 **상속이 맞는지 아직 모른다**
- 먼저 동작하는 코드를 만들고, **테스트가 보호하는 상태에서** 설계를 개선하라
- 섣부른 추상화보다 **명백한 중복**이 더 낫다

```
섣부른 추상화 → 잘못된 설계를 굳혀버림
명백한 중복  → 올바른 추상화를 찾을 때까지 유연성 유지
```

## 실습

> 스위스 프랑(CHF) 지원하기

### Red 단계

`5 CHF × 2 = 10 CHF`를 검증하는 테스트 코드를 작성하기 위해
`testMultiplication()`을 복사하여 `testFrancMultiplication()`을 만든다. 아직 `Franc` 클래스가 존재하지 않으므로 컴파일 자체가 되지 않는다. 

**테스트 코드:**
```
- testFrancMultiplication() 추가
- testMultiplication()의 이름을 testDollarMultiplication()으로 변경
```

### Green 단계

`Dollar` 코드를 통째로 복사한 뒤 `Dollar` → `Franc`으로 치환하여 `Franc` 클래스를 생성한다. 이렇게 하면 테스트는 통과하지만, `amount`, `times()`, `equals()` 등 `Dollar`와 중복된 코드는 고스란히 남는다. 중복 제거는 다음 단계에서 처리한다.

**프로덕션 코드:**
```
- Dollar 클래스를 복사하여 Franc 클래스를 만든다.
```
