# null을 전달하지 마라 (Don’t Pass Null)

> **null을 반환하지 말 뿐 아니라, 애초에 null을 전달하지도 마라**

👉 이유:

- null을 넘기는 순간 함수 내부에 방어 코드가 필요
- null은 숨겨진 조건 분기(if)를 만든다
- 함수의 책임이 흐려진다

👉 핵심 의도

- null을 허용하는 순간:
    - “이 값이 없어도 된다”는 계약이 생김
- 이는 코드 전체에 불필요한 복잡성 전파

## 예제 1

```java
// Bad - 함수 정의
public void registerItem(Item item) {
    if (item != null) {
        repository.save(item);
    }
}
```

```java
// Bad - 함수 사용
registerItem(null);
```

- 함수 내부에 불필요한 null 체크 발생
- 함수가 아무것도 안 하는 “묵살(silent ignore)” 동작
- 버그를 숨긴다
- 호출자가 잘못 사용해도 문제가 드러나지 않는다

```java
// Good - 함수 정의
public void registerItem(Item item) {
    if (item == null) {
        throw new IllegalArgumentException("item must not be null");
    }

    repository.save(item);
}
```

```java
// Good - 함수 사용
registerItem(item); // null 전달 금지
```

- 잘못된 사용이 즉시 드러난다
- 함수 계약(contract)이 명확해진다
- 버그를 초기에 발견할 수 있다
- 방어 코드 대신 명확한 실패 처리

## 예제 2

```java
// Bad - 함수 정의
public void sendEmail(String email) {
    if (email == null) {
        return;
    }

    emailClient.send(email);
}
```

```java
// Bad - 함수 사용
sendEmail(null);
```

- 이메일이 없는 상황이 “조용히 무시”된다
- 실제로는 중요한 오류일 수 있음
- 시스템 상태가 조용히 깨질 수 있음

```java
// Good - 함수 정의
public void sendEmail(String email) {
    if (email == null) {
        throw new IllegalArgumentException("email must not be null");
    }

    emailClient.send(email);
}
```

```java
// Good - 함수 사용
sendEmail(user.getEmail());
```

- 잘못된 입력이 즉시 드러난다
- 오류를 조기에 발견할 수 있다
- 의도하지 않은 동작 방지

## 예제 3

```java
// Bad
public void process(User user) {
    if (user != null) {
        System.out.println(user.getName());
    }
}
```

```java
process(null);
```

- null이 정상적인 입력처럼 취급된다
- 함수가 두 가지 책임을 가진다 (검증 + 처리)

```java
// Good: 방법 1 - 예외 던지기
public void process(User user) {
    if (user == null) {
        throw new IllegalArgumentException("user must not be null");
    }

    System.out.println(user.getName());
}
```

```java
// Good: 방법 2 - 호출부에서 null 제거하기
if (user != null) {
    process(user);
}
```

```java
// Good: 방법 3 - Optional 사용하기
public void process(Optional<User> userOpt) {
    userOpt.ifPresent(user ->
        System.out.println(user.getName())
    );
}
```

- null 자체를 모델에서 제거
- 코드 의미가 명확해짐
- 의도를 타입으로 표현

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드   | 좋은 코드   |
| ----- | ------- | ------- |
| 입력값   | null 허용 | null 금지 |
| 함수 책임 | 처리 + 방어 | 처리만     |
| 오류 처리 | 조용히 무시  | 명확한 예외  |
| 버그 발견 | 늦음      | 빠름      |
| 가독성   | 조건문 증가  | 단순 흐름   |

## 핵심 원칙

피해야 할 것:

- null을 인자로 전달하는 것
- 함수 내부에서 null을 묵살하는 것
- null을 “정상적인 값”처럼 사용하는 것
- null 체크를 모든 함수에 퍼뜨리는 것

지켜야 할 것:

- null을 전달하지 않는다
- 반드시 필요한 값이면 예외를 던진다
- 호출부에서 null을 제거한다
- Optional 등으로 의도를 명확히 표현한다
- 함수는 하나의 책임만 갖게 한다