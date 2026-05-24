# 부수 효과를 일으키지 마라! (Have No Side Effects)

> **함수는 이름이 암시하는 일만 해야 한다. 숨겨진 동작(부작용)을 가지면 안 된다**

- 부작용(side effect)이란:
  - 함수 이름에 드러나지 않은 상태 변경
  - 외부 상태 변경 (전역 변수, DB, 세션 등)

👉 문제:
- 코드를 이해하기 어려움
- 예측 불가능한 동작
- 버그 발생

## 예제 1

```java
// Bad: 숨겨진 부작용
public boolean checkPassword(String userName, String password) {
    User user = userRepository.findByName(userName);

    if (user != null && user.getPassword().equals(password)) {
        Session.initialize();  // ❗ 숨겨진 부작용
        return true;
    }
    return false;
}
```

```java
// 사용 예
if (checkPassword(user, password)) {
    // 이미 세션이 생성된 상태 (예상 못함)
}
```

- 함수 이름과 실제 동작 불일치
    - checkPassword → 비밀번호 확인만 할 것 같음
    - 실제 → 세션 초기화까지 수행
- 예상 불가능한 동작
    - 👉 호출자는 이 사실을 모름

```java
// Good: 부작용 분리
public boolean isPasswordValid(String userName, String password) {
    User user = userRepository.findByName(userName);
    return user != null && user.getPassword().equals(password);
}

public void initializeSession(User user) {
    Session.initialize();
}
```

```java
// 사용 예
if (isPasswordValid(userName, password)) {
    initializeSession(user);
}
```

- 검증과 상태 변경 분리
- 함수 이름과 동작 일치
- 👉 모든 동작이 명확하게 드러남

## 예제 2

```java
// Bad: 상태 변경 숨김
public int addItem(List<String> items, String item) {
    items.add(item);  // ❗ 리스트 변경 (부작용)
    return items.size();
}
```

- 함수 이름 → 단순 계산처럼 보임
- 실제 → 리스트를 변경함

```java
// Good
public void addItem(List<String> items, String item) {
    items.add(item);
}
```

또는

```java
public int getItemCountAfterAdd(List<String> items, String item) {
    List<String> newItems = new ArrayList<>(items);
    newItems.add(item);
    return newItems.size();
}
```

- 부작용 있는 함수 → 명확히 표현
- 또는 완전히 제거

## 예제 3: 출력 인자 (Output Arguments)

> 출력 인자를 사용하지 말고, 반환값(return value)을 사용하라

```java
// Bad: 출력 인자 사용
public void appendFooter(StringBuffer report) {
    report.append("\n--- End of Report ---");
}
```

- report가 입력인지 출력인지 혼란
- 함수가 외부 상태를 변경 (부작용)

```java
// Bad
public void calculateTotal(Order order, int[] totalHolder) {
    totalHolder[0] = order.getPrice() * order.getQuantity();
}
```

- totalHolder → 트릭 같은 코드
- 읽기 매우 어려움

```java
// Good: return 사용
public String appendFooter(String report) {
    return report + "\n--- End of Report ---";
}
```

```java
// Good
public int calculateTotal(Order order) {
    return order.getPrice() * order.getQuantity();
}
```

- 출력이 명확
- 함수 사용이 자연스러움

```java
// 사용 예
String reportWithFooter = appendFooter(report);

int total = calculateTotal(order);
```

## 예제 4: 허용되는 경우

```java
// Good: 허용되는 경우(객체 내부 변경)
class Report {
    void appendFooter() {
        this.content += "\n--- End ---";
    }
}
```

- 객체 자신을 수정하는 것은 자연스러움
- 하지만 외부 객체를 수정하는 것은 문제

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드           | 좋은 코드  |
| --- | --------------- | ------ |
| 부작용 | 숨겨짐             | 명확     |
| 출력  | output argument | return |
| 가독성 | 낮음              | 높음     |
| 예측  | 어려움             | 쉬움     |

## 핵심 원칙

### 부작용 문제

❗ 부작용이 문제인 이유:

- 함수 이름이 거짓말함
- 코드 이해 어려움
- 디버깅 어려움
- 테스트 어려움

✅ 좋은 함수:

- 입력 → 출력만 존재
- 상태 변경 없음 (또는 명확히 드러남)
- 예측 가능

### 원칙

피해야 할 것: 

- 숨겨진 상태 변경
- output arguments
- 함수 이름과 다른 동작

지켜야 할 것: 

- 함수는 하나의 역할만 수행
- 상태 변경은 명확히 표현
- return 값 사용