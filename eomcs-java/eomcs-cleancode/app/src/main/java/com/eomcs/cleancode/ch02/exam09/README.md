# 메서드 이름(Method Names)

> **메서드 이름은 “무엇을 하는가(What it does)”를 나타내는 동사여야 한다**

- 메서드는 행동(Action)을 표현해야 한다
- 이름만 보고도 **무슨 일을 하는지 알 수 있어야 한다**

    
## 예제 1: 모호한 메서드 이름

```java
// Bad
void process();
void handle();
void doStuff();
```

- 무엇을 처리하는지 알 수 없음
- 내부 코드를 보지 않으면 이해 불가

```java
// Good
void processOrder();
void handleUserLogin();
void calculateTotalPrice();
```

- 대상 + 행동을 함께 표현
- 메서드 이름만으로 역할 이해 가능

## 예제 2: 구체적인 동작 표현

```java
// Bad
void save();
```

- 무엇을 저장하는지 모름

```java
// Good
void saveUser(User user);
void saveOrder(Order order);
```

- 대상 명시
- 의도가 명확해짐

## 예제 3: Boolean 메서드 이름

```java
// Bad
boolean active();
boolean permission();
```

- true/false 의미 불명확

```java
// Good
boolean isActive();
boolean hasPermission();
boolean canExecute();
boolean shouldRetry();
```

- is / has / can / should 를 사용한다
- 읽을 때 자연스럽게 해석됨
- → "isActive?" → 활성 상태인가?

## 예제 4: 의미 없는 단어 사용

```java
// Bad
void doCalculation();
void handleData();
```

- do, handle → 의미 없음
- 구체적인 행동이 드러나지 않음

```java
// Good
void calculateInvoiceTotal();
void parseUserData();
```

- 실제 수행하는 작업을 정확히 표현

## 예제 5: 동일 개념에 다른 단어 사용

```java
// Bad
User fetchUser();
User getUser();
User retrieveUser();
```

- 같은 의미인데 다른 단어 사용
- API 사용자가 혼란

```java
// Good
User getUser();
User getActiveUser();
User getUserById(String id);
```

- 하나의 개념 → 하나의 단어 사용
- 일관성 유지

## 예제 6: 부작용 없는 메서드 이름

```java
// Bad
User getUser(); // 실제로는 DB 업데이트도 수행
```

- 이름은 조회인데 실제는 변경 발생
- 예측 불가능한 코드

```java
// Good
User findUser();
void updateUserLastLogin(User user);
```

- 행동을 정확히 반영
- 부작용을 숨기지 않음

## 예제 7: 메서드 이름과 의도

```java
// Bad
void check();
```

- 무엇을 체크하는가?

```java
// Good
void validateUserCredentials();
```

- 체크 대상과 목적을 명확히 표현

## 예제 8: 종합

```java
// Bad
class UserService {
    void process(User u) {
        if (u.flag == 1) {
            doStuff(u);
        }
    }
}
```

- process → 의미 불명확
- doStuff → 무엇인지 모름
- flag → 의미 불명확

```java
// Good
class UserService {
    void activateUser(User user) {
        if (user.isPendingActivation()) {
            sendActivationEmail(user);
        }
    }
}
```

- activateUser → 명확한 행동
- isPendingActivation → 조건 의미 명확
- sendActivationEmail → 수행 작업 명확

## 예제 9: 생성자 vs 정적 팩토리 메서드

```java
// Bad
User admin = new User("ADMIN");
User guest = new User("GUEST");
```

- 문자열 "ADMIN" → 의미 불명확
- 잘못된 값 입력 가능
- 코드 읽기 어려움

```java
// Good
User admin = User.createAdminUser();
User guest = User.createGuestUser();
```

- 타입 안정성 증가
- 가독성 향상
- 의도 명확

👉 메서드 이름이 생성 목적을 설명

## 예제 10: 생성 로직 캡슐화

```java
// Bad
User user = new User();
user.setRole("ADMIN");
user.setActive(true);
```

- 객체 생성 후 상태를 따로 설정
- 생성 과정이 분산됨
- 실수 가능성 증가

```java
// Good
User admin = User.createAdminUser();
```

- 생성 로직을 한 곳에 집중
- 객체의 일관성 유지

## 핵심 원칙

피해야 할 것:

- process, handle, doSomething 같은 모호한 이름
- 의미 없는 동사 사용
- 동일 개념에 여러 단어 사용
- 부작용을 숨기는 이름

지켜야 할 것:

- 동사 기반 이름 사용
- 대상 + 행동 명확히 표현
- Boolean은 is/has/can 사용
- 일관성 유지