# 작게 만들어라! (Small!)

> **함수는 가능한 한 작게 만들어라**

- 함수는 짧을수록 이해하기 쉽다
- 하나의 함수는 **하나의 일만 해야 한다**
- 길이가 길어질수록 책임이 섞인다

> **if, while 등의 블록은 한 줄이어야 하며, 그 줄은 함수 호출이어야 한다**

- 블록 내부가 길어질수록 가독성이 떨어진다
- 들여쓰기(depth)가 깊어질수록 이해하기 어려워진다

👉 목표:
- 중첩 최소화
- 들여쓰기 최소화
- 블록 안에는 **하나의 함수 호출만 존재**

## 예제 1

```java
// Bad: 큰 함수
public void processUser(User user) {
    // 1. 유효성 검사
    if (user.getName() == null || user.getName().isEmpty()) {
        throw new IllegalArgumentException("Invalid name");
    }

    // 2. 사용자 저장
    database.save(user);

    // 3. 이메일 전송
    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject("Welcome");
    email.setBody("Hello " + user.getName());
    emailService.send(email);

    // 4. 로그 기록
    System.out.println("User processed: " + user.getName());
}
```

- 하나의 함수에 여러 책임 존재: *검증, 저장, 이메일 전송, 로깅*
- 함수가 너무 많은 일을 함

❗ 결과:

- 읽기 어려움
- 재사용 어려움
- 테스트 어려움
- 변경 시 영향 범위 증가

```java
// Good: 작은 함수로 분리
public void processUser(User user) {
    validateUser(user);
    saveUser(user);
    sendWelcomeEmail(user);
    logUserProcessing(user);
}

private void validateUser(User user) {
    if (user.getName() == null || user.getName().isEmpty()) {
        throw new IllegalArgumentException("Invalid name");
    }
}

private void saveUser(User user) {
    database.save(user);
}

private void sendWelcomeEmail(User user) {
    Email email = createWelcomeEmail(user);
    emailService.send(email);
}

private Email createWelcomeEmail(User user) {
    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject("Welcome");
    email.setBody("Hello " + user.getName());
    return email;
}

private void logUserProcessing(User user) {
    System.out.println("User processed: " + user.getName());
}
```

- 함수 크기 감소
    - 각 함수는 짧고 단순함
- 한 가지 일만 수행
    - validateUser → 검증
    - saveUser → 저장
    - sendWelcomeEmail → 이메일 전송
- 읽기 쉬움
    - processUser() → 내부 구현을 몰라도 흐름 이해 가능

## 예제 2

```java
// Bad: 중첩 + 긴 함수 + 깊은 들여쓰기
public void processUsers(List<User> users) {
    for (User user : users) {
        if (user != null) {
            if (user.isActive()) {
                if (user.getBalance() > 0) {
                    System.out.println(user.getName() + " : " + user.getBalance());
                }
            }
        }
    }
}
```

- 들여쓰기(depth)가 깊음
    - for → if → if → if → 출력
    - 읽기 매우 어려움
- 블록 내부 로직이 많음
    - 조건 + 출력 로직이 한 곳에 섞임
- 의도가 드러나지 않음
    - 이 코드는 무엇을 하는가? "활성 사용자 중 잔액 있는 사용자 출력"
    - 하지만 코드만 보면 바로 이해하기 어려움

```java
// Good: 블록 축소
public void printEligibleUsers(List<User> users) {
    for (User user : users) {
        if (isEligibleUser(user)) {
            printUserBalance(user);
        }
    }
}
```

- 블록 내부는 한 줄
    - if 안에는 함수 호출 하나
- 들여쓰기 단순화
    - 깊이가 줄어듦
- 의도 명확
    - 함수 이름만 읽어도 의미 파악 가능

```java
// Good: 조건 로직 분리
private boolean isEligibleUser(User user) {
    return user != null &&
           user.isActive() &&
           user.getBalance() > 0;
}
```

- 복잡한 조건을 한 곳에 모음
- 이름으로 의미 표현

```java
// Good: 동작 로직 분리
private void printUserBalance(User user) {
    System.out.println(user.getName() + " : " + user.getBalance());
}
```

## 예제 3

```java
// Bad: while + 내부 로직
while (scanner.hasNext()) {
    String line = scanner.nextLine();
    if (line != null) {
        if (!line.isEmpty()) {
            processLine(line);
        }
    }
}
```

- 중첩된 조건
- 불필요한 블록 증가

```java
// Good: 블록 축소
while (scanner.hasNext()) {
    processIfValidLine(scanner.nextLine());
}

private void processIfValidLine(String line) {
    if (isValidLine(line)) {
        processLine(line);
    }
}

private boolean isValidLine(String line) {
    return line != null && !line.isEmpty();
}
```

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드 | 좋은 코드 |
| --- | ----- | ----- |
| 크기  | 길고 복잡 | 짧고 단순 |
| 책임  | 여러 개  | 하나    |
| 가독성 | 낮음    | 높음    |
| 테스트 | 어려움   | 쉬움    |
| 들여쓰기 | 깊음    | 얕음     |
| 블록   | 여러 줄  | 한 줄    |
| 조건   | 인라인   | 함수로 분리 |

## 핵심 원칙

피해야 할 것:

- 긴 함수
- 여러 책임을 가진 함수
- 깊은 중첩 구조
- 블록 안에 많은 코드
- 조건 + 동작 혼합

지켜야 할 것:

- 함수는 작게
- 한 가지 일만 수행
- 의미 있는 이름으로 분리
- 블록은 한 줄로 유지
- 함수 호출로 추상화
- 들여쓰기 최소화