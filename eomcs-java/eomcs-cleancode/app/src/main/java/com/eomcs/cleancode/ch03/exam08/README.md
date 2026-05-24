# 명령과 조회를 분리하라! (Command Query Separation)

> **함수는 "명령(Command)"이거나 "조회(Query)" 중 하나만 해야 한다**

- Command: 상태를 변경한다 (return 없음 또는 void)
- Query: 값을 반환한다 (상태 변경 없음)

👉 절대 혼합하면 안 된다

| 유형 | 역할 | 예 |
|------|------|----|
| Command | 상태 변경 | save(), update(), delete() |
| Query | 값 반환 | get(), isActive(), calculate() |

## 예제 1

```java
// Bad: Command + Query 혼합
public boolean setActive(User user) {
    user.setActive(true);  // 상태 변경 (Command)
    return user.isActive(); // 값 반환 (Query)
}
```

```java
// 사용 예
if (setActive(user)) { // "user가 활성화 되어 있는 상태인가?"를 묻는 것으로 보일 수 있음
    // 이게 상태 확인인가?
    // 아니면 설정 결과 확인인가?
}
```

- 두 가지 역할 수행
    - 상태 변경 + 값 반환
- 혼란 발생
    - 👉 읽는 사람이 의도를 해석해야 함

```java
// Good: Command와 Query 분리

// Command: 상태 변경
public void activateUser(User user) {
    user.setActive(true);
}

// Query: 값 반환
public boolean isUserActive(User user) {
    return user.isActive();
}
```

```java
// 사용 예
activateUser(user);

if (isUserActive(user)) {
    // 명확한 의도
}
```

- 👉 각 함수의 역할이 명확함

## 예제 2

```java
// Bad: 숨겨진 상태 변경
public int getTotalPrice(Order order) {
    int total = calculate(order);
    order.setTotal(total); // ❗ 상태 변경
    return total;
}
```

- 함수 이름 → 조회처럼 보임 (get)
- 실제 → 상태 변경까지 수행
- 👉 완전한 규칙 위반

```java
// Good

// Query: 상태 변경 없음
public int calculateTotalPrice(Order order) {
    return calculate(order);
}

// Command: 상태 변경
public void updateOrderTotal(Order order) {
    order.setTotal(calculate(order));
}
```

- 조회와 변경 분리
- 함수 이름과 행동 일치

## 예제 3

```java
// Bad
public boolean addItem(List<String> list, String item) {
    list.add(item);  // 상태 변경
    return list.contains(item); // 조회
}
```

- add + contains → 두 가지 역할
- return 값 의미 애매

```java
// Good
public void addItem(List<String> list, String item) {
    list.add(item);
}

public boolean containsItem(List<String> list, String item) {
    return list.contains(item);
}
```

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 코드 | 좋은 코드 |
| ---- | ----- | ----- |
| 역할   | 혼합    | 분리    |
| 가독성  | 낮음    | 높음    |
| 의도   | 불명확   | 명확    |
| 유지보수 | 어려움   | 쉬움    |

## 핵심 원칙

### 혼합의 문제

❗ 혼합의 문제:

- 코드 읽을 때 해석 필요
- side effect 발생 가능
- 테스트 어려움
- 버그 유발

✅ 분리의 장점:

- 함수 역할 명확
- 예측 가능
- 테스트 쉬움
- 유지보수 쉬움

### 원칙

피해야 할 것: 

- 상태 변경 + 값 반환
- get 함수에서 상태 변경
- set 함수에서 값 반환

지켜야 할 것: 

- Command → 상태 변경만
- Query → 값 반환만
- 이름과 행동 일치