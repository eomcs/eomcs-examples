# 디미터 법칙 (The Law of Demeter)

Law of Demeter는 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙이다.

> **객체는 친구에게만 말해야 한다. 낯선 객체의 내부까지 파고들면 안 된다.**

즉, 객체는 자신이 직접 알고 있는 객체에게만 메시지를 보내야 한다.

## 예제 1: 기차 충돌 (Train Wrecks)

> **메서드 호출이 기차처럼 줄줄이 이어지는 코드를 피하라.**

```java
// Bad
final String outputDir =
    ctxt.getOptions().getScratchDir().getAbsolutePath();
```

- 여러 객체를 연쇄적으로 탐색하기 때문에 "기차 충돌(Train Wreck)"이라고 부른다.
- ctxt → Options → ScratchDir → absolutePath까지 호출자가 너무 많은 내부 구조를 알고 있다.

```java
// Bad
String city =
    order.getCustomer()
         .getAddress()
         .getCity();
```

호출자가 다음 구조를 모두 알고 있다.

```text
Order
 └─ Customer
     └─ Address
         └─ City
```

- 즉, 호출자가 Order의 내부 구조를 지나치게 많이 안다.

```java
// Good
String city = order.getShippingCity();
```

또는

```java
// Good
String city = order.shippingCity();
```

- 호출자는 더 이상 Customer, Address 구조를 알 필요가 없다.
- Order에게 필요한 정보를 요청한다.

## 예제 2: 잡종 구조 (Hybrids)

> **객체와 자료 구조를 섞은 하이브리드 구조를 피하라.**

하이브리드는 다음 특징을 가진다.

- 의미 있는 메서드도 있다
- 그런데 getter/setter로 내부 데이터도 거의 다 공개한다

이런 구조는 새 함수 추가도 어렵고, 새 자료 구조 추가도 어렵다.

```java
// Bad
public class Order {
    private Customer customer;
    private List<Item> items;
    private int totalPrice;

    public Customer getCustomer() {
        return customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void calculateTotalPrice() {
        totalPrice = 0;

        for (Item item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
    }

    public void applyDiscount(int discountAmount) {
        totalPrice -= discountAmount;
    }
}
```

- 이 클래스는 객체처럼 행동도 가진다.
    - calculateTotalPrice() 메서드로 totalPrice를 계산한다.
    - applyDiscount() 메서드로 totalPrice에 할인도 적용한다.
- 하지만 동시에 내부 데이터도 공개한다.
    - getCustomer() 메서드로 customer를 공개한다.
    - getItems() 메서드로 items를 공개한다.
    - getTotalPrice() 메서드로 totalPrice를 공개한다.
    
```java
// 사용 예
int total = 0;

for (Item item : order.getItems()) {
    total += item.getPrice() * item.getQuantity();
}

order.applyDiscount(1000);
```

- 외부 코드가 Order의 내부 자료 구조를 직접 사용할 수 있다.
- 문제는 이렇게 외부에서 계산된 total을 applyDiscount() 메서드가 사용하지 못한다.
- 이것이 하이브리드 구조의 문제점이다.

```java
// Good: 객체로 설계
public class Order {
    private final Customer customer;
    private final List<Item> items;
    private int totalPrice;

    public void calculateTotalPrice() {
        totalPrice = calculateItemsTotal();
    }

    public void applyDiscount(int discountAmount) {
        totalPrice -= discountAmount;
    }

    public boolean isOwnedBy(Customer customer) {
        return this.customer.equals(customer);
    }

    private int calculateItemsTotal() {
        int total = 0;

        for (Item item : items) {
            total += item.subtotal();
        }

        return total;
    }
}
```

- 외부는 items를 직접 가져가지 않는다.
- 대신 Order에게 일을 시킨다.

```java
// 사용 예
order.calculateTotalPrice();
order.applyDiscount(1000);
```

- 객체는 내부 데이터를 숨기고, 의미 있는 동작을 제공한다.

## 예제 3: 구조체 감추기 (Hiding Structure)

> **객체의 내부 구조를 묻지 말고, 객체에게 일을 시켜라.**

```java
// Bad
String path =
    context.getOptions()
           .getScratchDirectory()
           .getAbsolutePath();

File file = new File(path, fileName);
file.createNewFile();
```

호출자가 너무 많은 것을 알고 있다.

- context가 options를 가진다
- options가 scratch directory를 가진다
- scratch directory가 absolute path를 가진다
- 그 경로로 파일을 만들어야 한다

이것은 객체에게 묻는 코드다.
    
```java
// Good: 애매한 개선
String path = context.getScratchDirectoryAbsolutePath();
File file = new File(path, fileName);
file.createNewFile();
```

- 호출 체인은 줄었지만, context가 점점 많은 getter성 메서드를 가지게 될 수 있다.
- 예: 
    ```java
    getScratchDirectoryAbsolutePath()
    getTempDirectoryAbsolutePath()
    getLogDirectoryAbsolutePath()
    ```
    - 이 방식은 메서드 폭발로 이어질 수 있다.

```java
// Good: 일을 시키기
context.createScratchFile(fileName);
```

```java
// 내부 구현 
public class Context {
    private Options options;

    public File createScratchFile(String fileName) throws IOException {
        File scratchDirectory = options.getScratchDirectory();
        File file = new File(scratchDirectory, fileName);
        file.createNewFile();
        return file;
    }
}
```

- 호출자는 더 이상 scratch directory의 구조를 알 필요가 없다.
    - 객체에게 묻지 않고, 객체에게 일을 시킨다.
    - 예) `context.createScratchFile("result.txt");`

## 나쁜 코드 vs 좋은 코드

| 주제               | 나쁜 코드                    | 좋은 코드             |
| ---------------- | ------------------------ | ----------------- |
| Train Wrecks     | `a.getB().getC().getD()` | `a.doSomething()` |
| Hybrids          | 데이터 공개 + 동작 보유           | 데이터 숨김 + 동작 제공    |
| Hiding Structure | 내부 구조를 탐색                | 객체에게 일을 시킴        |

## 핵심 원칙

> 객체에게 내부를 묻지 말고, 객체에게 일을 시켜라.

피해야 할 것:

```java
order.getCustomer().getAddress().getCity();
context.getOptions().getScratchDir().getAbsolutePath();
order.getItems().forEach(...);
```

지켜야 할 것:

```java
order.getShippingCity();
context.createScratchFile(fileName);
order.calculateTotalPrice();
```