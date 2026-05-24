# 함수 당 추상화 수준은 하나로! (One Level of Abstraction per Function)

> **하나의 함수는 하나의 추상화 수준(Level of Abstraction)만 가져야 한다**

- 고수준 로직과 저수준 구현을 섞으면 안 된다
- 함수는 **같은 높이(level)**에서 이야기해야 한다

| 수준 | 설명 |
|------|------|
| 고수준 | "무엇을 하는가" |
| 저수준 | "어떻게 하는가" |

```java
processOrder();   // 고수준
calculateTotal(); // 중간 수준
item.getPrice();  // 저수준
```

- 이 세 가지가 한 함수에 섞이면 문제가 된다

## Stepdown Rule (위에서 아래로 읽히는 코드)

> **코드는 위에서 아래로 읽히며 점점 더 구체적으로 내려가야 한다**

- 고수준 → 중간 수준 → 저수준

## 예제 1

```java
// Bad: 추상화 수준 혼합
public void processOrder(Order order) {
    if (order.isValid()) {                         // 고수준
        int total = 0;
        for (Item item : order.getItems()) {      // 저수준
            total += item.getPrice() * item.getQuantity();
        }
        database.save(order);                     // 저수준
        System.out.println("Total: " + total);    // 저수준
    }
}
```

- 추상화 수준 혼합
    - order.isValid() → 비즈니스 로직
    - 반복문, 계산 → 구현 디테일
    - DB, 출력 → 인프라 코드
    - 👉 서로 다른 레벨이 한 함수에 섞임
- 읽기 어려움
    - "무엇을 하는지"와 "어떻게 하는지"가 동시에 등장

```java
// Good: 단일 추상화 수준
public void processOrder(Order order) {
    if (isValidOrder(order)) {
        int total = calculateTotal(order);
        saveOrder(order);
        printTotal(total);
    }
}
```

- 모든 코드가 같은 수준의 추상화
- “무엇을 하는지”만 표현

```java
// Good: 하위 구현(저수준)
private boolean isValidOrder(Order order) {
    return order != null && !order.getItems().isEmpty();
}

private int calculateTotal(Order order) {
    int total = 0;
    for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
    }
    return total;
}

private void saveOrder(Order order) {
    database.save(order);
}

private void printTotal(int total) {
    System.out.println("Total: " + total);
}
```

- 고수준 함수는 흐름만 표현
- 저수준 함수는 구현을 담당

## 예제 2

```java
// Bad: Stepdown Rule 위반
public void processOrder(Order order) {
    saveOrder(order);
    int total = calculateTotal(order);
    printTotal(total);
}

private void calculateTotal(Order order) {
    // 구현
}

private void saveOrder(Order order) {
    // 구현
}
```

- 흐름이 깨짐
- 함수 호출 순서와 정의 순서가 맞지 않음
- 👉 읽는 사람: "이게 왜 먼저 나오지?"

```java
// Good: Stepdown Rule 적용
public void processOrder(Order order) {
    if (isValidOrder(order)) {
        int total = calculateTotal(order);
        saveOrder(order);
        printTotal(total);
    }
}

private boolean isValidOrder(Order order) { ... }

private int calculateTotal(Order order) { ... }

private void saveOrder(Order order) { ... }

private void printTotal(int total) { ... }
```

- 위 → 아래로 읽힘
- 점점 더 상세한 내용 등장
- 자연스러운 흐름 유지

### Stepdown 구조 시각화

```text
processOrder()
 ├─ isValidOrder()
 ├─ calculateTotal()
 │    └─ item.getPrice()
 ├─ saveOrder()
 └─ printTotal()
```

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드 | 좋은 코드 |
| --- | ----- | ----- |
| 추상화 | 혼합    | 동일    |
| 가독성 | 낮음    | 높음    |
| 흐름  | 불규칙   | 위→아래  |
| 이해  | 어려움   | 자연스러움 |

## 핵심 원칙

- 한 함수 = 한 수준
- 고수준 + 저수준 혼합 금지
- 위에서 아래로 읽히게 작성
- 점점 더 상세하게 전개