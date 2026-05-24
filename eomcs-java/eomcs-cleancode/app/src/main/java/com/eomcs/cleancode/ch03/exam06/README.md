# 함수 인수 (Function Arguments)

> **함수 인자는 적을수록 좋다 (이상적: 0개, 최대: 2개 이하)**

- 인자가 많을수록 이해하기 어려움
- 테스트하기 어려움
- 사용하기 어려움

👉 함수는 가능한 한 단순해야 한다

## 예제 1

```java
// Bad
public void createUser(String name, int age, String role, boolean active) {
    ...
}
```

- 인자가 많음 (4개)
- 순서 기억 필요
- 의미 파악 어려움

```java
// Good
public void createUser(User user) {
    ...
}
```

- 객체로 묶어서 전달
- 의미 명확
- 확장 용이

## 예제 2: 많이 쓰는 단항 형식 (Common Monadic Forms)

> 하나의 인자는 가장 이해하기 쉬운 형태

```java
// Good: 질문 (boolean 반환)
boolean isActive(User user) { ... }
```

```java
// Good: 변환
UserDto toDto(User user) { ... }
```

```java
// Good: 명령
void save(User user) { ... }
```

- 단일 인자는 의미가 명확하고 자연스럽다

## 예제 3: 플래그 인자 (Flag Arguments)

```java
// Bad
public void processOrder(Order order, boolean isUrgent) {
    if (isUrgent) {
        // 긴급 처리
    } else {
        // 일반 처리
    }
}
```

- 하나의 함수가 두 가지 일을 함
- true/false 의미 파악 필요

```java
// Good
public void processUrgentOrder(Order order) { ... }

public void processNormalOrder(Order order) { ... }
```

- 함수 분리
- 의도 명확

## 예제 4: 이항 함수 (Dyadic Functions)

```java
// Bad
void compare(int a, int b);
```

- 어떤 비교인지 불명확

```java
// Good
boolean isGreaterThan(int value, int threshold) { ... }
```

- 2개 인자는 허용되지만
- 관계가 명확해야 함

## 예제 5: 삼항 함수 (Triads)

```java
// Bad
void draw(int x, int y, int radius) { ... }
```

- 순서 기억 필요
- 의미 해석 필요

```java
// Good
class Circle {
    int x;
    int y;
    int radius;
}

void draw(Circle circle) { ... }
```

- 객체로 묶기
- 의미 명확

## 예제 6: 인자 객체 (Argument Objects)

```java
// Bad
Circle makeCircle(int x, int y, int radius) { ... }
```

- 순서 기억 필요
- 의미 해석 필요

```java
// Good
Circle makeCircle(Point center, int radius) { ... }
```

- 관련 데이터 묶기
- 확장성 증가
- 가독성 향상

## 예제 7: 인자 목록 (Argument Lists)

```java
// Bad
void addItems(Item item1, Item item2, Item item3) { ... }
```

- 순서 기억 필요
- 의미 해석 필요

```java
// Good
void addItems(List<Item> items) { ... }
```
또는

```java
void addItems(Item... items) { ... }
```

- 유연성 증가
- 코드 간결

## 예제 8: 동사와 키워드 (Verbs and Keywords)

```java
// Bad
void user(User user) { ... }
```

```java
// Good
void saveUser(User user) { ... }
```

## 예제 9: 키워드 인자 (Keyword Argument 느낌)

```java
// Bad
assertEquals(expected, actual);
```

- 순서 헷갈림

```java
// Good
assertExpectedEqualsActual(expected, actual);
```

- 함수 이름에 인자 이름을 넣기
- 인자의 순서를 기억할 필요 없음

또는
```java
// Good
assertEquals(expectedValue, actualValue);
```

- 인자의 의미를 이름으로 표현
- 순서 의존성 감소

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드      | 좋은 코드 |
| ----- | ---------- | ----- |
| 인자 수  | 많음         | 적음    |
| flag  | boolean 사용 | 함수 분리 |
| triad | 3개 인자      | 객체 사용 |
| 의미    | 불명확        | 명확    |

## 핵심 원칙

❗ 피해야 할 것:

- 인자 3개 이상
- boolean flag
- 순서 의존
- 의미 없는 인자

✅ 지켜야 할 것:

- 0~1개 인자 권장
- 많으면 객체로 묶기
- 이름으로 의미 표현
- 함수 분리