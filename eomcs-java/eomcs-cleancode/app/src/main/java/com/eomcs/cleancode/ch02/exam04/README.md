# 발음하기 쉬운 이름을 사용하라(Use Pronounceable Names)

> **이름은 말로 표현할 수 있어야 한다**

- 개발자는 코드를 “읽기”만 하지 않는다
- 동료와 **대화하고 설명**해야 한다
- 발음하기 어려운 이름은 협업을 방해한다

발음하기 어려운 이름의 특징:

- 축약어 남용
- 자음만 나열
- 의미 없는 약어 조합

👉 결과:

- 코드 리뷰가 어려움
- 커뮤니케이션 비용 증가
- 이해 속도 저하

## ❌ 나쁜 예

```java
// 이름이 짧아 의미를 알 수 없다.
class DtaRcrd102 {
    private Date genymdhms;  // generation: year, month, day, hour, minute, second
    private Date modymdhms;  // modification: year, month, day, hour, minute, second
    private final String pszqint = "102";
}

// 코드 리뷰에서 어떻게 말하나?
// "gen why emm dee aich emm ess가 내일 날짜로 세팅됐는데..."

String usrNm;
int cnt;
```

말로 설명이 불가능

## ✅ 개선된 예

```java
// 읽기 쉽고 말하기 쉬운 이름을 사용해야 한다.
class Customer {
    private Date generationTimestamp;
    private Date modificationTimestamp;
    private final String recordId = "102";
}

// 코드 리뷰에서 자연스럽게 말할 수 있다:
// "generationTimestamp가 내일 날짜로 세팅됐는데, 어떻게 된 거야?"

String userName;
int count;
```

개선 포인트:

1. 클래스 이름 개선
    - DtaRcrd102 → CustomerRecord
    - 👉 의미가 명확하고 발음 가능

2. 변수 이름 개선
    - genymdhms → generationTimestamp
    - modymdhms → modificationTimestamp
    - 👉 자연스럽게 읽고 말할 수 있음

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 이름      | 좋은 이름               |
| --- | ---------- | ------------------- |
| 클래스 | DtaRcrd102 | CustomerRecord      |
| 변수  | genymdhms  | generationTimestamp |
| 변수  | usrNm      | userName            |
| 변수  | cnt        | count               |

## 핵심 원칙

피해야 할 것:

- 과도한 축약어
- 자음 위주의 이름
- 의미를 유추해야 하는 이름

지켜야 할 것:

- 자연스럽게 읽히는 이름
- 말로 설명 가능한 이름
- 협업을 고려한 이름