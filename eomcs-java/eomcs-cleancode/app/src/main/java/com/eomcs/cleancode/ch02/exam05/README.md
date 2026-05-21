# 검색하기 쉬운 이름을 사용하라(Use Searchable Names)

> **이름은 코드에서 쉽게 찾을 수 있어야 한다**

- 개발자는 코드를 읽는 것뿐만 아니라 **검색하며 이해한다**
- 이름이 검색되지 않으면 유지보수 비용이 증가한다

검색이 어려운 이름의 특징:

- 너무 짧은 이름 (`e`, `x`, `i`)
- 숫자 상수 (`7`, `1`, `100`)
- 의미 없는 축약어

👉 결과:

- IDE에서 검색이 어려움
- 코드 이해 속도 저하
- 버그 추적 어려움

## ❌ 나쁜 예

문자 하나를 사용하는 이름과 상수는 텍스트 코드에서 쉽게 눈에 띄지 않는다.

```java
for (int j = 0; j < 34; j++) {
    s += (t[j] * 4) / 5;
}
```

- j → 무엇을 의미하는지 모름
- 34 → 무엇을 의미하는지 모름
- t → 어떤 데이터인지 불명확
- 4, 5 → 의미 없는 매직 넘버

👉 이 코드는 검색도 이해도 어렵다

```java
int e = 7;
```

- e는 너무 짧아서 검색 불가능
- 7은 코드 전체에 너무 많이 존재

## ✅ 개선된 예

```java
// 상수명으로 의미와 검색 가능성 모두 확보
int WORK_DAYS_PER_WEEK = 5;
int WORK_HOURS_PER_DAY = 4;
int totalWorkDays = 34;

for (int dayIndex = 0; dayIndex < totalWorkDays; dayIndex++) {
    sum += (taskHours[dayIndex] * WORK_HOURS_PER_DAY) / WORK_DAYS_PER_WEEK;
}
```

개선 포인트:

1. 매직 넘버 제거
    - 34 → totalWorkDays
    - 4, 5 → 상수로 분리
    - 👉 의미를 이름으로 표현
2. 변수 이름 개선
    - j → dayIndex
    - t → taskHours
    - 👉 역할과 의미를 명확히 표현
3. 검색 가능성 증가
    - WORK_DAYS_PER_WEEK → 프로젝트 전체에서 검색 가능
    - dayIndex → 반복문 역할 파악 가능

```java
int maxRetryCount = 7;
```

- maxRetryCount로 쉽게 검색 가능
- 의미 기반으로 코드 이해 가능

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드 | 문제     | 좋은 코드         |
| ----- | ----- | ------ | ------------- |
| 변수    | e     | 검색 불가  | maxRetryCount |
| 반복 변수 | j     | 의미 없음  | dayIndex      |
| 상수    | 34    | 의미 없음  | totalWorkDays |
| 배열    | t     | 역할 불명확 | taskHours     |

## 핵심 원칙

피해야 할 것:

- 한 글자 변수 (단, i, j 같은 짧은 루프는 제한적으로 허용)
- 매직 넘버
- 검색 불가능한 이름

지켜야 할 것:

- 의미 있는 긴 이름 사용
- 상수는 이름으로 추출
- 검색 가능한 단어 선택