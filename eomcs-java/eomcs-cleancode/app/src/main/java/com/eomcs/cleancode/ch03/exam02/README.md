# 한 가지만 해라! (Do One Thing)

> **함수는 오직 한 가지 일만 해야 한다**

- 함수는 하나의 책임만 가져야 한다
- 함수 이름 아래에서 한 단계(level)의 추상화만 존재해야 한다
- 즉, 함수 안의 모든 코드는 **같은 수준의 추상화**에 있어야 한다

> **함수 안에서 “섹션(단계)”이 보인다면, 이미 여러 일을 하고 있는 것이다**

- 주석으로 구분된 코드 블록은 함수 분리 대상이다

## 예제 1

```java
// Bad: 여러 일을 하는 함수
public void processOrder(Order order) {
    // 1. 유효성 검사
    if (order == null || order.getItems().isEmpty()) {
        throw new IllegalArgumentException("Invalid order");
    }

    // 2. 총 금액 계산
    int total = 0;
    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }

    // 3. DB 저장
    database.save(order);

    // 4. 로그 출력
    System.out.println("Order processed: " + total);
}
```

- 하나의 함수가 여러 일을 함: *검증, 계산, 저장, 로깅*
- 이건 "processOrder"가 아니라 "everythingOrder"에 가까움

```java
// Good: 한 가지 일만 수행
public void processOrder(Order order) {
    validateOrder(order);
    int total = calculateTotal(order);
    saveOrder(order);
    logOrder(total);
}

private void validateOrder(Order order) { ... }
private int calculateTotal(Order order) { ... }
private void saveOrder(Order order) { ... }
private void logOrder(int total) { ... }
```

- 함수는 "흐름"만 표현
- 실제 작업은 하위 함수로 분리
- 이제 각 함수는 정확히 하나의 일만 수행

## 예제 2

```java
// Bad: 추상화 레벨 혼합
public void processUser(User user) {
    if (user.isActive()) {                  // 고수준
        database.connect();                // 저수준
        database.save(user);               // 저수준
        System.out.println(user.getName());// 저수준
    }
}
```

- 추상화 레벨이 섞임
    - isActive() → 비즈니스 로직
    - database.connect() → 구현 디테일
    - database.save(user) → 구현 디테일
    - System.out.println(user.getName()) → 구현 디테일
- 한 함수에서 서로 다른 레벨이 섞이면 이해가 어려움

```java
// Good: 같은 수준으로 추상화 유지
public void processActiveUser(User user) {
    if (user.isActive()) {
        saveUser(user);
        logUser(user);
    }
}

private void saveUser(User user) {
    database.connect();
    database.save(user);
}

private void logUser(User user) {
    System.out.println(user.getName());
}
```

- 각각의 함수는 같은 수준의 추상화 유지

## 예제 3

```java
// Bad: 함수 내 섹션 존재
public void generateReport(List<User> users) {

    // 1. 필터링
    List<User> activeUsers = new ArrayList<>();
    for (User user : users) {
        if (user.isActive()) {
            activeUsers.add(user);
        }
    }

    // 2. 정렬
    activeUsers.sort((a, b) -> a.getName().compareTo(b.getName()));

    // 3. 출력
    for (User user : activeUsers) {
        System.out.println(user.getName());
    }
}
```

- 주석으로 단계 구분됨
- 하나의 함수 안에 3개의 책임 존재
- 👉 주석은 힌트: **"이 코드는 분리해야 한다"**

```java
// Good: 섹션 제거
public void generateReport(List<User> users) {
    List<User> activeUsers = filterActiveUsers(users);
    List<User> sortedUsers = sortUsersByName(activeUsers);
    printUsers(sortedUsers);
}

// 각 단계 함수로 분리
private List<User> filterActiveUsers(List<User> users) { ... }
private List<User> sortUsersByName(List<User> users) { ... }
private void printUsers(List<User> users) { ... }
```

- 주석 제거 가능
    - 함수 이름이 주석 역할을 대신함
- 읽기 쉬운 흐름
    - `generateReport(users);` 코드는 자연어처럼 읽힘

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드     | 좋은 코드 |
| --- | --------- | ----- |
| 책임  | 여러 개      | 하나    |
| 구조  | 섹션(주석) 존재 | 함수 분리 |
| 추상화 | 혼합됨       | 동일 수준 |
| 가독성 | 낮음        | 매우 높음 |

## 핵심 원칙

지켜야 할 것:

- 함수는 하나의 책임만 가져야 한다
- 한 단계의 추상화만 사용
- 주석으로 구분된 블록은 분리 대상
- 각 단계는 별도 함수로 추출