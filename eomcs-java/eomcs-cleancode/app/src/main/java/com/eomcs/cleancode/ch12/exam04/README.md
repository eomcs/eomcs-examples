# 중복을 없애라 (No Duplication)

> **중복은 좋은 설계의 가장 큰 적이다**

- 같은 코드가 여러 곳에 있으면 수정도 여러 번 해야 한다
- 한 곳만 고치면 버그가 생긴다
- 중복은 불필요한 복잡성을 만든다
- 중복을 제거하면 숨겨져 있던 개념이 드러난다

**Clean Code:**

- 중복은 잘 설계된 시스템의 주된 적이다. 
- 중복은 추가 작업, 추가 위험, 불필요한 복잡성을 만든다.

## 예제 1

```java
// Bad
public class ImageEditor {

    private RenderedOp image;

    public void scale(float factor) {
        RenderedOp newImage =
                ImageUtilities.getScaledImage(image, factor, factor);

        image.dispose();
        System.gc();
        image = newImage;
    }

    public void rotate(int degrees) {
        RenderedOp newImage =
                ImageUtilities.getRotatedImage(image, degrees);

        image.dispose();
        System.gc();
        image = newImage;
    }
}
```

- 다음 세 줄이 반복된다.
    - image.dispose()
    - System.gc()
    - image = newImage

```java
// Good
public class ImageEditor {

    private RenderedOp image;

    public void scale(float factor) {
        replaceImage(
                ImageUtilities.getScaledImage(image, factor, factor)
        );
    }

    public void rotate(int degrees) {
        replaceImage(
                ImageUtilities.getRotatedImage(image, degrees)
        );
    }

    private void replaceImage(RenderedOp newImage) {
        image.dispose();
        System.gc();
        image = newImage;
    }
}
```

- 이미지 교체 절차가 replaceImage()로 모였다
- 이미지 교체 방식이 바뀌면 한 곳만 수정하면 된다
- scale()과 rotate()는 자신의 핵심 의도만 표현한다

## 예제 2

```java
// Bad
public class Cart {

    private final List<Item> items = new ArrayList<>();
    private boolean empty = true;

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void add(Item item) {
        items.add(item);
        empty = false;
    }

    public void remove(Item item) {
        items.remove(item);

        if (items.size() == 0) {
            empty = true;
        }
    }
}
```

- items.size()와 empty가 같은 정보를 중복해서 관리한다
- empty 갱신을 잊으면 size()와 isEmpty() 결과가 달라질 수 있다
- 상태 중복은 버그를 만든다

```java
// Good
public class Cart {

    private final List<Item> items = new ArrayList<>();

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void add(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }
}
```

- 비어 있는지 여부는 size()에서 계산한다
- 같은 상태를 두 곳에서 관리하지 않는다
- 중복 상태가 사라져 버그 가능성이 줄어든다

## 예제 3

```java
// Bad
public class OrderService {

    public int calculateOrderTotal(Order order) {
        return order.getItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public int calculateCartTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
```

- item.getPrice() * item.getQuantity()가 반복된다
- 합계 계산 방식도 반복된다
- 가격 계산 규칙이 바뀌면 여러 곳을 수정해야 한다

```java
// Good
public class OrderService {

    public int calculateOrderTotal(Order order) {
        return calculateTotal(order.getItems());
    }

    public int calculateCartTotal(Cart cart) {
        return calculateTotal(cart.getItems());
    }

    private int calculateTotal(List<Item> items) {
        return items.stream()
                .mapToInt(Item::totalPrice)
                .sum();
    }
}
```

```java
public class Item {

    private final int price;
    private final int quantity;

    public int totalPrice() {
        return price * quantity;
    }
}
```

- Item.totalPrice()라는 개념이 드러난다
- 중복 제거가 곧 설계 개선으로 이어진다
- 계산 규칙 변경 지점이 하나로 줄어든다

## 예제 4

```java
// Bad
public class UserValidator {

    public boolean isValidForSignup(User user) {
        return user.email() != null
                && user.email().contains("@")
                && user.password() != null
                && user.password().length() >= 8;
    }

    public boolean isValidForPasswordReset(User user) {
        return user.email() != null
                && user.email().contains("@");
    }
}
```

- 이메일 검증 로직이 중복된다
- 이메일 정책이 바뀌면 두 곳 모두 수정해야 한다
- 한 곳을 빠뜨리면 검증 기준이 달라진다

```java
// Good
public class UserValidator {

    public boolean isValidForSignup(User user) {
        return hasValidEmail(user)
                && hasValidPassword(user);
    }

    public boolean isValidForPasswordReset(User user) {
        return hasValidEmail(user);
    }

    private boolean hasValidEmail(User user) {
        return user.email() != null
                && user.email().contains("@");
    }

    private boolean hasValidPassword(User user) {
        return user.password() != null
                && user.password().length() >= 8;
    }
}
```

- 이메일 검증 규칙이 한 곳에 모인다
- 메서드 이름이 의도를 드러낸다
- 중복 제거와 표현력 향상이 함께 일어난다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드                | 좋은 코드       |
| ----- | -------------------- | ----------- |
| 중복 형태 | 같은 코드 반복             | 공통 메서드 추출   |
| 상태 관리 | 같은 의미의 상태를 여러 곳에서 관리 | 하나의 원천에서 계산 |
| 변경 비용 | 여러 곳 수정              | 한 곳 수정      |
| 버그 위험 | 수정 누락 가능             | 수정 지점 명확    |
| 설계 효과 | 복잡도 증가               | 숨은 개념 발견    |

## 핵심 원칙

> “모든 테스트를 통과하는 코드”가 좋은 설계의 출발점이며, 그 위에서만 깨끗한 설계가 만들어진다

**피해야 할 것:**

- 같은 계산식을 여러 곳에 반복하는 것
- 같은 상태를 두 변수로 관리하는 것
- 비슷한 코드를 “조금 다르다”는 이유로 방치하는 것
- 중복된 조건식을 여러 메서드에 흩뿌리는 것

**지켜야 할 것:**

- 중복을 발견하면 공통 개념을 찾는다
- 중복 코드는 메서드나 클래스로 추출한다
- 같은 정보는 하나의 원천에서 관리한다
- 작은 중복도 적극적으로 제거한다
- 중복 제거 후 모든 테스트를 다시 실행한다
