# 단순 설계 규칙 1: 모든 테스트를 실행하라 (Simple Design Rule 1: Runs All the Tests)

> **좋은 설계의 첫 번째 조건은 “모든 테스트를 통과하는 것”이다**

- 테스트를 통과하지 못하는 코드는 “좋은 설계” 이전에 “잘못된 코드”
- 설계는 동작 위에 존재한다
- 테스트는 코드가 올바르게 동작한다는 안전망(safety net) 역할
- 이 안전망이 있어야 리팩토링이 가능하다

**핵심 의도:**

- 설계보다 먼저 정확한 동작
- 테스트가 깨지지 않는 범위에서만 구조 개선
- 테스트가 없으면 설계 개선도 위험한 도박

## 예제 1

```java
// Bad - 테스트 없이 구현
public class Calculator {

    public int add(int a, int b) {
        return a - b; // 버그!
    }
}
```

- 코드만 보면 문제를 발견하기 어렵다
- 설계를 아무리 잘해도 동작이 틀리면 의미 없음

```java
// Good - 테스트 먼저
@Test
void addsTwoNumbers() {
    Calculator calculator = new Calculator();

    assertEquals(5, calculator.add(2, 3));
}
```

```java
// 구현
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }
}
```

- 테스트가 동작을 명확히 정의한다
- 잘못된 구현이 바로 드러난다
- 설계 개선의 출발점이 된다

## 예제 2

```java
// Bad
public class OrderService {

    public int totalPrice(Order order) {
        int total = 0;

        for (Item item : order.getItems()) {
            total += item.getPrice(); // 수량 고려 안함 (버그)
        }

        return total;
    }
}
```

- 테스트가 없으면 이 버그는 쉽게 놓친다
- 이후 리팩토링하면 더 위험해진다

```java
// Good - 테스트
@Test
void calculatesTotalPriceWithQuantity() {
    Order order = new Order();
    order.addItem(new Item(1000, 2)); // 2000
    order.addItem(new Item(500, 3));  // 1500

    OrderService service = new OrderService();

    assertEquals(3500, service.totalPrice(order));
}
```

```java
// 구현
public class OrderService {

    public int totalPrice(Order order) {
        return order.getItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
```

- 테스트가 “올바른 계산 규칙”을 정의한다
- 이후 구조를 바꿔도 테스트가 보호해준다

## 예제 3: 리팩토링과의 관계

```java
// 테스트
@Test
void calculatesTotalPrice() {
    Order order = new Order();
    order.addItem(new Item(1000, 2));
    order.addItem(new Item(500, 1));

    OrderService service = new OrderService();

    assertEquals(2500, service.totalPrice(order));
}
```

```java
// 초기 구현 (동작 OK)
public class Item {

    private int price;
    private int quantity;
    
    public int getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
}

public class OrderService {

    public int totalPrice(Order order) {
        int total = 0;

        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }
}
```

- 이제 리팩토링 가능

```java
// 리팩토링 후
public class Item {

    private int price;
    private int quantity;

    public int totalPrice() {
        return price * quantity;
    }
}

public class OrderService {

    public int totalPrice(Order order) {
        return order.getItems().stream()
                .mapToInt(Item::totalPrice)
                .sum();
    }
}
```

- 테스트가 깨지지 않으면 리팩토링 성공
- 구조는 바뀌었지만 동작은 동일
- 이것이 Emergent Design의 핵심

## 왜 “모든 테스트 통과”가 중요한가?

**1. 설계 개선의 전제 조건**

- 테스트 실패 상태에서 리팩토링하면 위험
- 먼저 “정확하게 동작”해야 한다

**2. 변경에 대한 안전망**

- 구조를 바꿔도 동작이 유지되는지 확인 가능
- 리팩토링을 두려워하지 않게 된다

**3. 설계를 이끌어줌**

테스트 작성 과정에서:

- 인터페이스가 자연스럽게 설계됨
- 결합도가 낮아짐
- 의존성 주입 구조가 만들어짐

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드 | 좋은 코드 |
| ----- | ----- | ----- |
| 테스트   | 없음    | 존재    |
| 안정성   | 불확실   | 검증됨   |
| 리팩토링  | 위험    | 안전    |
| 설계 개선 | 어려움   | 가능    |
| 버그 발견 | 늦음    | 빠름    |

## 핵심 원칙

> “모든 테스트를 통과하는 코드”가 좋은 설계의 출발점이며, 그 위에서만 깨끗한 설계가 만들어진다

**피해야 할 것:**

- 테스트 없이 코드 작성
- 테스트가 깨진 상태에서 리팩토링
- 테스트를 “나중에” 작성하는 것
- 중요한 로직을 테스트하지 않는 것

**지켜야 할 것:**

- 모든 테스트를 통과해야 한다
- 테스트가 설계의 출발점이다
- 테스트가 리팩토링을 가능하게 만든다
- 테스트는 코드의 “행동”을 정의한다

