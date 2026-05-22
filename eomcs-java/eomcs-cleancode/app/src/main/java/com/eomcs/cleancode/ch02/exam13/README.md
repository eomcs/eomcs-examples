# 해법 영역에서 가져온 이름을 사용하라(Use Solution Domain Names)

> **컴퓨터 과학(해결 영역)의 용어를 적극적으로 사용하라**

- 개발자는 기술적인 개념을 이해하는 사람들이다
- 따라서 **검증된 기술 용어**를 사용하는 것이 더 명확하다
- **"기술 개념에는 기술 이름이 가장 적합한 선택이다."**

👉 “문제 도메인”이 아니라  
👉 “해결 방법(자료구조, 알고리즘, 패턴)”이 핵심일 때 사용

✅ 사용해야 하는 경우

- 자료구조 (Stack, Queue, Map 등)
- 알고리즘 (Sort, Search 등)
- 디자인 패턴 (Factory, Builder 등)
- 기술 개념 (Cache, Parser 등)

👉 개발자라면 이미 알고 있는 용어

## 예제 1

```java
// Bad
class DataContainer {
    void addItem(String item) { ... }
    String getItem() { ... }
}
```

- DataContainer → 무엇인지 모름
- 내부 구조가 무엇인지 불명확
- Stack인지 Queue인지 알 수 없음

```java
// Good
class Stack {
    void push(String item) { ... }
    String pop() { ... }
}
```

- Stack → 자료구조 명확
- push, pop → 동작 명확
- 이름만 보고 동작 예측 가능

## 예제 2

```java
// Bad
class DataHandler {
    List<User> arrangeUsers(List<User> users) {
        // 정렬 로직
    }
}
```

- arrangeUsers → 정렬인지 모름
- 구현 의도가 숨겨짐

```java
// Good
class UserSorter {
    List<User> sortByName(List<User> users) {
        // 정렬 로직
    }
}
```

- sort → 명확한 알고리즘 의미
- sortByName → 기준까지 명확

## 예제 3

```java
// Bad
class UserCreator {
    User makeUser(String type) { ... }
}
```

- makeUser → 생성 방식 불명확
- 패턴 의도 숨김

```java
// Good
class UserFactory {
    User createUser(String type) { ... }
}
```

- Factory → 생성 패턴 명확
- 개발자가 즉시 이해 가능

## 예제 4

```java
// Bad
class DataHelper {
    String process(String input) { ... }
}
```

- process → 무엇을 하는지 모름
- 역할이 불명확

```java
// Good
class JsonParser {
    Object parse(String json) { ... }
}

class UserCache {
    User get(String userId) { ... }
}
```

- Parser → 파싱 역할 명확
- Cache → 캐시 역할 명확

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 코드         | 문제     | 좋은 코드       |
| ---- | ------------- | ------ | ----------- |
| 자료구조 | DataContainer | 구조 불명확 | Stack       |
| 알고리즘 | arrangeUsers  | 의미 모호  | sortByName  |
| 생성   | makeUser      | 의도 불명확 | UserFactory |
| 처리   | process       | 역할 불명확 | JsonParser  |

## 핵심 원칙

피해야 할 것:

- 모호한 일반 단어 (Data, Helper, Manager)
- 기술적 의미를 숨기는 이름

지켜야 할 것:

- 검증된 CS 용어 사용
- 개발자가 익숙한 개념 활용
- 이름만 보고 구조/동작 예측 가능