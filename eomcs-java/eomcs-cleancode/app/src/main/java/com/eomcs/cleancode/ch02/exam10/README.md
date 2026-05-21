# 기발한 이름은 피하라(Don’t Be Cute)

> **재미있는 이름보다 명확한 이름이 더 중요하다**

- 개발자는 코드를 “읽는 사람”을 위해 작성해야 한다
- 유머, 은어, 내부 농담은 코드의 가독성을 떨어뜨린다

👉 코드는 농담이 아니라 **의사소통 도구**

**개발자가 흔히 저지르는 실수:**

- 재미있는 이름 사용
- 문화적/종교적/영화적 레퍼런스 사용
- 팀 외부 사람이 이해 못하는 표현

👉 결과:
- 코드 이해 어려움
- 유지보수 비용 증가
- 팀 의존성 증가

## 예제 1: 기발한 이름

```java
// Bad
void whack();
void eatMyShorts();
void holyHandGrenade();
```

- whack() → 무엇을 하는가?
- eatMyShorts() → 의미 없음 (심슨 reference)
- holyHandGrenade() → 몬티 파이썬 reference

👉 코드 읽는 사람이 반드시 해석하거나 검색해야 함

```java
// Good
void deleteItem();
void rejectRequest();
void removeInactiveUsers();
```

- 동작이 명확하게 드러남
- 누구나 이해 가능
- 추가 설명 불필요

**기발한 이름의 문제:**

- 팀 외부 사람이 이해 못함
- 시간이 지나면 본인도 이해 못함
- 문맥 없이는 의미 전달 불가

👉 클린 코드의 기준

- 코드는 “읽는 순간 이해”되어야 한다

## 예제 2

```java
// Bad
class UserService {
    void ninjaKill(User user) {
        // 사용자 삭제
    }
}
```

- ninjaKill → 개발자 취향 표현
- 실제 동작과 무관
- 유지보수 시 혼란 발생

```java
// Good
class UserService {
    void deleteUser(User user) {
        // 사용자 삭제
    }
}
```

- 이름과 동작이 일치
- 코드만 보고도 이해 가능

## 예제 3

```java
// Bad
void magic();
void boom();
void doTheThing();
```

- 의미 없음
- 코드 분석 필요

```java
// Good
void calculateDiscount();
void sendNotification();
void processOrder();
```

## 나쁜 코드 vs 좋은 코드

| 구분 | 나쁜 이름           | 문제     | 좋은 이름        |
| -- | --------------- | ------ | ------------ |
| 유머 | ninjaKill       | 의미 없음  | deleteUser   |
| 은어 | whack           | 모호함    | removeItem   |
| 참조 | holyHandGrenade | 이해 어려움 | deleteItems  |
| 일반 | doStuff         | 의미 없음  | processOrder |

## 핵심 원칙

피해야 할 것:

- 유머, 농담
- 영화/문화 레퍼런스
- 내부 용어
- 의미 없는 단어

지켜야 할 것:

- 명확하고 직관적인 이름
- 누구나 이해 가능한 표현
- 도메인 중심 이름