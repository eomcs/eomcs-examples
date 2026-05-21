# 자신의 기억력을 자랑하지 마라(Avoid Mental Mapping)

> **코드를 이해하기 위해 머릿속에서 변환(매핑)하지 않게 하라**

- 좋은 코드는 “읽는 즉시 이해”되어야 한다
- 나쁜 코드는 “해석”이 필요하다

👉 개발자가 코드를 읽을 때 **기억하거나 추측하게 만들면 안 된다**

똑똑한 프로그래머와 전문가 프로그래머 사이의 차이점:

- 똑똑한 프로그래머:
    - 자신의 정신적 능력을 과시하고 싶어한다.
- 전문가 프로그래머:
    - 명료함의 최고라는 사실을 이해한다.
    - 자신의 능력을 좋은 방향으로 사용해 남들이 이해하는 코드를 내놓는다.
    
## 예제 1

```java
// Bad
int r = 10;
int c = 20;

for (int i = 0; i < r; i++) {
    for (int j = 0; j < c; j++) {
        processCell(i, j);
    }
}
```

- r → row?
- c → column?
- i, j → 무엇을 의미하는가?

👉 읽는 사람이 다음과 같이 해석해야 한다:

- r = row
- c = column
- i = row index
- j = column index

👉 즉, 머릿속에서 매핑 작업이 필요함

```java
// Good
int rowCount = 10;
int columnCount = 20;

for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
        processCell(rowIndex, columnIndex);
    }
}
```

1. 변수 이름 명확화
    - r → rowCount
    - c → columnCount
2. 반복 변수 의미 부여
    - i → rowIndex
    - j → columnIndex

👉 이제 코드를 읽을 때:

- 해석이 필요 없음
- 즉시 이해 가능

## 예제 2

```java
// Bad
List<int[]> l = getData();

for (int[] x : l) {
    if (x[0] == 1) {
        process(x);
    }
}
```

- l → list?
- x → 무엇인가?
- x[0] → 어떤 의미?

👉 계속 추측해야 한다

```java
// Good
List<User> users = getUsers();

for (User user : users) {
    if (user.isActive()) {
        process(user);
    }
}
```

- l → users
- x → user
- x[0] == 1 → user.isActive()

👉 의미를 기억하지 않아도 코드가 설명됨

## 예제 3

```java
// Bad
int d; // days?
int m; // months?
int y; // years?
```

👉 이런 경우는 절대 금지

```java
// Good
for (int i = 0; i < n; i++) {
    // 매우 짧고 단순한 루프
}
```

약어를 써도 되는 경우, 

- 범위가 매우 작을 때
- 의미가 명확할 때
- 짧은 컨텍스트 안에서만 사용

## 예제 4

```java
// Bad
// - 단일 문자 변수: 독자가 'r'이 무엇인지 외워야 한다
String r = url.toLowerCase().replaceAll("https?://", "").split("/")[0];
// r은 '스키마와 호스트를 제거한 소문자 URL'이라는 걸 기억해야 한다.

// 루프 변수 남용
for (int i = 0; i < 10; i++) {
    for (int j = 0; j < 10; j++) {
        // 수십 줄이 지난 후, i와 j가 각각 무엇을 뜻하는지 기억하는가?
        process(data[i][j]);
    }
}
```

```java
// Good
// - 의미가 드러나는 이름
String hostWithoutScheme = url.toLowerCase().replaceAll("https?://", "").split("/")[0];

// 루프 변수도 의미를 담는다
for (int row = 0; row < BOARD_ROWS; row++) {
    for (int col = 0; col < BOARD_COLS; col++) {
        process(data[row][col]);
    }
}
```

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드 | 문제       | 좋은 코드                 |
| --- | ----- | -------- | --------------------- |
| 변수  | r, c  | 의미 추측 필요 | rowCount, columnCount |
| 반복  | i, j  | 해석 필요    | rowIndex, columnIndex |
| 컬렉션 | l     | 의미 없음    | users                 |
| 요소  | x     | 역할 불명확   | user                  |

## 핵심 원칙

피해야 할 것:

- 한 글자 변수 남용
- 약어로 의미 축약
- 해석이 필요한 이름

지켜야 할 것:

- 이름만으로 의미 전달
- 읽는 즉시 이해 가능
- 기억에 의존하지 않기