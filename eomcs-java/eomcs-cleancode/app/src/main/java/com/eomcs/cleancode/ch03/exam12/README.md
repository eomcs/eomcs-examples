# 함수를 어떻게 짜죠? (How Do You Write Functions Like This?)

> **깨끗한 함수는 처음부터 완성된 형태로 작성되지 않는다.**

- 처음에는 함수가 길고 복잡해질 수 있다.  
- 중요한 것은 그 상태로 끝내지 않는 것이다.

Clean Code에서 말하는 함수 작성 과정은 다음과 같다.

1. 먼저 동작하는 코드를 작성한다
2. 테스트를 만든다
3. 중복을 제거한다
4. 이름을 개선한다
5. 함수를 작게 나눈다
6. 추상화 수준을 맞춘다
7. 테스트가 계속 통과하는지 확인한다

## 예제 1

```java
// Bad: 함수 초기 버전
public void register(User user) {
    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }

    if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
    }

    userRepository.save(user);

    Email email = new Email();
    email.setTo(user.getEmail());
    email.setSubject("Welcome");
    email.setBody("Hello " + user.getName());

    emailService.send(email);

    System.out.println("User registered: " + user.getEmail());
}
```

이 함수는 여러 일을 한다.

- 사용자 검증
- 사용자 저장
- 이메일 생성
- 이메일 전송
- 로그 출력

즉, 처음 작성한 초안은 동작하지만 깨끗하지 않다.

## 예제 2: 리팩토링

```java
// Good: 리팩토링 후
public void register(User user) {
    validateUser(user);
    saveUser(user);
    sendWelcomeEmail(user);
    logRegistration(user);
}

private void validateUser(User user) {
    validateEmail(user);
    validateName(user);
}

private void validateEmail(User user) {
    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }
}

private void validateName(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
    }
}

private void saveUser(User user) {
    userRepository.save(user);
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

private void logRegistration(User user) {
    System.out.println("User registered: " + user.getEmail());
}
```

- register() 함수는 이제 전체 흐름만 보여준다.
    ```java
    validateUser(user);
    saveUser(user);
    sendWelcomeEmail(user);
    logRegistration(user);
    ```
- 읽는 사람은 세부 구현을 보지 않아도 흐름을 이해할 수 있다.
