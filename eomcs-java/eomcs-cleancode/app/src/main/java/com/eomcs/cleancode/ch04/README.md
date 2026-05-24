# Chapter 4. Comments (주석)

이 장은 주석을 잘 다는 법을 소개한다.

## 주석은 왜 필요한가?

> **코드는 의도를 표현하기 어렵기 때문에 주석이 필요하다**

```java
// check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) &&
    (employee.age > 65))
```

- 주석이 없으면 의도를 해석해야 한다.

```java
// 개선한 예
if (employee.isEligibleForFullBenefits())
```

- 주석을 쓰는 대신 코드 자체를 개선하는 것이 더 낫다.

## 좋은 코드는 주석을 대체한다.

> 코드를 개선하면 주석이 필요 없어지는 경우가 대부분이다

```java
// Bad: 주석을 단 경우

// calculate total price
int total = 0;
for (Item item : items) {
    total += item.price * item.quantity;
}
```

```java
// Good: 함수 이름이 주석 역할을 대신한다.
int total = calculateTotalPrice(items);
```

## 주석의 근본적인 문제

- 주석은 거짓말할 수 있다
    - 코드는 변경되지만 주석은 그대로 남아 있을 수 있다.
    - 코드가 바뀌면 주석은 틀린 정보가 된다.
- 주석은 유지보수 대상이다
    - 코드 + 주석 둘 다 관리해야 한다
    - 주석이 많을수록 유지 비용 증가

## 그래도 주석은 필요하다!

> 좋은 주석은 필요하다, 하지만 대부분의 주석은 나쁘다

법적 주석: 

```java
// Copyright (C) 2024 Company
```

의도 설명:

```java
// We are temporarily using this workaround due to bug #1234
```

경고:

```java
// Do not change this logic unless you understand the concurrency issue
```

## 정리

1. 주석은 필요하다
2. 하지만 대부분의 주석은 나쁜 코드의 결과다
3. 좋은 코드는 주석 없이도 읽혀야 한다
4. 주석은 쉽게 틀린 정보가 된다

## 예제

- [주석은 나쁜 코드를 보완하지 못한다 (Comments Do Not Make Up for Bad Code)](exam01/README.md)
- [코드로 의도를 표현하라 (Explain Yourself in Code)](exam02/README.md)
- [좋은 주석 (Good Comments)](exam03/README.md)
    - 법적인 주석 (Legal Comments)
    - 정보를 제공하는 주석 (Informative Comments)
    - 의도를 설명하는 주석 (Explanation of Intent)
    - 의미를 명료하게 밝히는 주석 (Clarification)
    - 결과를 경고하는 주석 (Warning of Consequences)
    - TODO 주석 (TODO Comments)
    - 중요성을 강조하는 주석 (Amplification)
    - 공개 API의 Javadocs (Javadocs in Public APIs)
- [나쁜 주석 (Bad Comments)](exam04/README.md)
    - 주절거리는 주석 (Mumbling)
    - 같은 이야기를 중복하는 주석 (Redundant Comments)
    - 오해할 여지가 있는 주석 (Misleading Comments)
    - 의무적으로 다는 주석 (Mandated Comments)
    - 이력을 기록하는 주석 (Journal Comments)
    - 있으나 마나 한 주석 (Noise Comments)
    - 무서운 잡음 (Scary Noise)
    - 함수나 변수로 표현할 수 있다면 주석을 달지 마라 (Don’t Use a Comment When You Can Use a Function or a Variable)
    - 위치를 표시하는 주석 (Position Markers)
    - 닫는 괄호를 다는 주석 (Closing Brace Comments)
    - 공로를 돌리거나 저자를 표시하는 주석 (Attributions and Bylines)
    - 주석 처리한 코드 (Commented-Out Code)
    - HTML 주석 (HTML Comments)
    - 전역 정보 (Nonlocal Information)
    - 너무 많은 정보 (Too Much Information)
    - 모호한 관계 (Inobvious Connection)
    - 함수 헤더 (Function Headers)
    - 비공개 코드에서 Javadocs (Javadocs in Nonpublic Code)
    - 예제 (Example)