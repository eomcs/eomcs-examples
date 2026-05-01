# 구현 중심 사고에서 테스트 중심 사고로의 전환

이 단계에서는 설계 변경이라는 예를 통해 **구현 중심 사고에서 테스트 중심 사고로 전환**하는 TDD의 핵심 철학을 체험한다.  

## 개념

### 구현 중심 사고 → 테스트 중심 사고

TDD의 핵심 사고 방식은 '어떻게 구현할 것인가?'를 고민하기 전에 '어떻게 테스트할 것인가?'를 먼저 묻고 생각하는 것이다. 예를 들어, `Dollar`과 `Franc`이라는 두 하위 클래스를 제거하는 큰 설계 문제에 직면했을 때, 단번에 모든 문제를 해결하려고 하기보다는, '통화'라는 개념을 도입하는 **작은 테스트부터 시작**하여 점진적으로 문제를 풀어나가는 접근법을 사용한다.

### TDD의 보폭(Step Size)

TDD는 정해진 보폭만 고집하는 것이 아니라 지속적인 '조향(steering)' 과정이다. 확신이 부족할 때는 아주 작은 단계로 기어를 낮추고, 구조가 명확할 때는 한 번에 큰 단계(One swell foop)로 도약하는 유연함을 발휘해야 한다.

## 실습

> 불필요한 하위 클래스 Dollar와 Franc 클래스를 제거한다.

### Red 단계 - 통화(Currency) 개념과 테스트 도입

큰 설계 문제(예: Dollar/Franc 하위 클래스 제거)를 당장 해결하기 막막할 때, 눈에 띄는 더 작고 구체적인 작업(예: '통화' 개념 도입)부터 시작하여 점진적으로 실마리를 풀어가는 접근법을 사용한다. 즉 Dollar와 Franc의 차이를 데이터(통화 코드 문자열)로 표현하려면 currency()를 반환하는 메서드가 필요하다. 이를 검증하는 testCurrency() 테스트를 먼저 작성한다. 

**테스트 코드:**

```
`testCurrency()` 테스트 추가
  - 
  - `Money.dollar(1).currency()`가 "USD"를 반환하는지 검증
  - `Money.franc(1).currency()`가 "CHF"를 반환하는지 검증
```
아직 Money 클래스에 `currency()` 메서드가 없으므로 컴파일 오류가 발생한다.

### Green 단계 - currency() 메서드 추가

테스트를 통과하는 currency()를 추가한다. 각 하위 클래스가 반환할 값을 하드코딩하는 단순한 구현이다.

**프로덕션 코드:**
```
Money 클래스:
  - `abstract String currency()`를 추가
  
Dollar 클래스: 
  - currency() 메서드 재정의 및 "USD" 리턴
  
Franc 클래스: 
  - currency() 메서드 재정의 및 "CHF" 리턴
```

### Refactor 단계 1 - Dollar/Franc에 currency 필드 추가

**작은 보폭을 통한 점진적 리팩토링:** Franc 클래스에 먼저 currency 인스턴스 변수를 추가하고, 생성자에서 currency 변수를 "CHF"로 초기화한다. currency() 메서드가 이 변수를 반환하도록 구현하여 testCurrency() 테스트를 통과시킨다. 이 작은 단계는 Franc 클래스의 구조를 변경하면서도 Dollar 클래스에는 영향을 주지 않아, 변경의 범위를 최소화한다. 

이어서 Dollar 클래스에도 동일한 방식으로 currency 필드를 추가하고 초기화하여 testCurrency() 테스트를 통과시킨다. 이렇게 하면 두 클래스 모두 currency 정보를 필드로 관리하게 되어, 향후 통화 코드 변경 시 더 유연하게 대응할 수 있다.

**프로덕션 코드:**
```
Franc 클래스 변경:
  - `private String currency` 필드를 추가하고 생성자에서 초기화한다.
  - `currency()` 메서드가 `currency` 필드를 반환하도록 변경한다.

Dollar 클래스 변경:
  - `private String currency` 필드를 추가하고 생성자에서 초기화한다.
  - `currency()` 메서드가 `currency` 필드를 반환하도록 변경한다.
```
이 단계에서 각 하위 클래스는 여전히 독립적으로 `currency` 필드를 보유한다.

### Refactor 단계 2 - currency 필드 및 currency() 메서드 pull up

**상위 클래스로 필드와 메서드 밀어 올리기(Push up):** Dollar과 Franc에서 공통적으로 사용하는 currency 필드와 currency() 메서드를 Money로 pull up하여 중복을 제거한다.

**프로덕션 코드:**
```
Dollar와 Franc 클래스:
  - currency 필드와 currency() 메서드를 Money로 pull up

Money 클래스:
  - currency 필드와 currency() 메서드를 추가
```
Dollar와 Franc의 currency 필드와 currency() 메서드의 중복이 제거되고 Money에서 통합 관리된다.


### Refactor 단계 3 - Dollar/Franc의 생성자에서 하드코딩 제거

**하드코딩 제거:** Dollar과 Franc의 생성자에서 currency 필드 값을 하드코딩("USD"/"CHF")하는 대신, 생성자의 파라미터로 통화 코드를 받아 초기화하도록 변경한다. 

**프로덕션 코드:**
```
Dollar와 Franc 클래스:
  - 생성자에 currency 파라미터 추가
  - currency 필드를 생성자의 currency 파라미터 값으로 초기화
  - times() 메서드에서 객체를 생성할 때 currency 파라미터의 값을 전달

Money 클래스:
  - 팩토리 메서드에서 Dollar과 Franc 객체를 생성할 때 currency 값을 전달
```
Dollar/Franc 생성자에서 하드 코딩을 제거하고, 두 클래스의 생성자를 동일하게 맞춘다.

### Refactor 단계 4 - Dollar/Franc 의 생성자 코드가 중복되므로 상위 클래스의 생성자로 pull up

**하위 클래스 생성자에 중복된 코드 제거:** Dollar과 Franc의 생성자에서 amount와 currency를 초기화하는 코드가 중복되므로, 이 부분을 Money 클래스의 생성자로 pull up하여 중복을 제거한다.

**프로덕션 코드:**
```
Dollar와 Franc 클래스:
  - 생성자의 코드를 상위 클래스 Money의 생성자로 pull up하여 중복 제거
  - 생성자에서 super(amount, currency) 호출

Money 클래스:
  - 하위 클래스의 생성자에서 중복된 코드를 제거하기 위해 amount와 currency를 초기화하는 생성자 추가
```

### Refactor 단계 5 - Dollar/Franc의 times() 메서드에서 Money 객체 생성하여 리턴

금액과 통화 정보가 모두 Money 클래스에서 관리되므로, Dollar과 Franc의 times() 메서드에서 객체를 생성할 때 Money 객체를 생성하여 반환하도록 변경한다.
이렇게 하면 Dollar와 Franc의 times() 메서드가 동일해지기 때문에 times() 메서드를 상위 클래스 Money로 pull up하여 중복을 제거할 수 있다.

**프로덕션 코드:**
```
Dollar와 Franc 클래스:
  - times() 메서드에서 Money 객체를 생성하여 반환
  - 리턴 타입을 Money로 변경

Money 클래스:
  - Money 클래스의 객체를 생성할 수 있도록 추상 클래스에서 concrete 클래스로 변경
  - times() 메서드를 임시로 구현
  - testDollarMultiplication()에서 팩토리의 리턴 값은 Dollar이고 times()의 리턴 값은 Money 이므로 값 비교 오류가 발생한다.
    이를 해결하기 위해 Money 클래스의 equals() 메서드를 오버라이딩하여 값을 비교할 때 객체의 타입이 아닌 currency를 비교하도록 변경한다.
```

### Refactor 단계 6 - Dollar/Franc의 times()를 상위 클래스 Money로 pull up하여 중복 제거

Dollar과 Franc의 times() 메서드가 동일해졌으므로, 이 메서드를 상위 클래스 Money로 pull up하여 중복을 제거한다.

**프로덕션 코드:**
```
Dollar와 Franc 클래스:
  - times() 메서드를 상위 클래스 Money로 pull up하여 중복 제거

Money 클래스:
  - Dollar와 Franc의 times() 메서드가 중복되므로, 이 부분을 Money 클래스의 메서드로 pull up
```

### Refactor 단계 7 - 팩토리 메서드의 리턴 타입을 Money로 변경

금액과 통화 정보가 모두 Money 클래스에서 관리되므로, 팩토리 메서드의 리턴 타입을 Dollar/Franc에서 Money로 변경한다.
이제 Dollar과 Franc 클래스는 더 이상 고유한 행위를 가지지 않으므로, 이 두 클래스를 제거한다.

**프로덕션 코드:**
```
Money 클래스:
  - 팩토리 메서드에서 Money객체를 생성하여 리턴하도록 변경

Dollar와 Franc 클래스:
  - Money 클래스로 금액과 통화를 다룰 수 있으므로 Dollar과 Franc 클래스 제거
```

**테스트 코드:**
```
testDollarMultiplication()과 testFrancMultiplication()를 testMultiplication()으로 통합
  - 금액과 통화는 모두 Money 클래스에서 관리되므로, 두 테스트를 하나로 통합

testEquality()에서 잉여 테스트 제거
  - 달러와 프랑의 동등성 검증을 하나로 합치고, 다른 통화 간의 동등성 검증은 유지
```