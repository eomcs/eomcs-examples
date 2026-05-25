# 자료/객체 비대칭 (Data/Object Anti-Symmetry)

> **객체(Object)와 자료 구조(Data Structure)는 서로 반대되는 특성을 가진다.**

> 객체는 동작을 공개하고 데이터를 숨긴다.  
> 자료 구조는 데이터를 공개하고 의미 있는 동작은 거의 갖지 않는다.

그 결과, 객체 방식은 **새로운 자료 타입 추가**에 유리하고, 자료 구조 방식은 **새로운 동작 추가**에 유리하다.


## 예제 1: 자료 구조 방식

```java
// 도형 데이터
public class Square {
    public Point topLeft;
    public double side;
}

public class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}

public class Circle {
    public Point center;
    public double radius;
}
```

```java
// 면적 계산 함수
public class Geometry {
    public final double PI = 3.141592653589793;

    public double area(Object shape) throws NoSuchShapeException
    {
        if (shape instanceof Square) {
            Square s = (Square)shape;
            return s.side * s.side;
        }
        else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle)shape;
            return r.height * r.width;
        }
        else if (shape instanceof Circle) {
            Circle c = (Circle)shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }
}
```

이 방식에서는 데이터가 공개되어 있고, 동작은 별도 함수에 있다.

```java
geometry.area(circle);
```

### 자료 구조 방식의 장점

```java
// 둘레를 계산하는 기능 추가
public double perimeter(Object shape) {
    if (shape instanceof Square square) {
        return 4 * square.side;
    }

    if (shape instanceof Rectangle rectangle) {
        return 2 * (rectangle.width + rectangle.height);
    }

    if (shape instanceof Circle circle) {
        return 2 * Math.PI * circle.radius;
    }

    throw new IllegalArgumentException("Unknown shape");
}
```

- 새로운 동작 추가가 쉽다.
    - Geometry에 새 함수 `perimeter()`만 추가하면 된다.
- 기존 데이터 클래스는 수정하지 않아도 된다.
    - `Square`
    - `Rectangle`
    - `Circle`

### 자료 구조 방식의 단점

```java
public class Triangle {
    public double base;
    public double height;
    public double sideA;
    public double sideB;
    public double sideC;
}
```

- 새로운 자료 타입 추가가 어렵다.
- 기존의 모든 함수(`area()`와 `perimeter()`)를 수정해야 한다.
- 즉 함수마다 `Triangle` 분기를 추가해야 한다.
    ```java
    // 예)
    if (shape instanceof Triangle triangle) {
        return triangle.base * triangle.height / 2;
    }
    ```

## 예제 2: 객체 방식

```java
// 동작 정의
public interface Shape {
    double area();
}
```

```java
// 도형 데이터와 동작 구현: 정사각형
public class Square implements Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    public double area() {
        return side * side;
    }
}
```

```java
// 도형 데이터와 동작 구현: 직사각형
public class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double area() {
        return width * height;
    }
}
```

```java
// 도형 데이터와 동작 구현: 원
public class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }
}
```

```java
// 사용 예
public double calculateArea(Shape shape) {
    return shape.area();
}
```

### 객체 방식의 장점

```java
public class Triangle implements Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    public double area() {
        return base * height / 2;
    }
}
```

- 새로운 자료 타입 추가가 쉽다.
    - `Triangle` 클래스만 새로 만들면 된다.
- 기존 코드는 수정하지 않는다.
    - 예) `calculateArea(shape);`
- 즉, 객체 방식은 새로운 타입 추가에 강하다.

### 객체 방식의 단점

```java
// 새 동작 추가
public interface Shape {
    double area();
    double perimeter(); // 새 동작
}
```

- 새로운 동작 추가가 어렵다.
- 기존의 모든 자료 타입(`Square`, `Rectangle`, `Circle`, `Triangle`)이 새 동작을 구현해야 한다.
    - 예) `perimeter()` 메서드 추가
- 객체 방식은 새로운 동작을 추가할 때 변경 범위가 넓어진다.

## 나쁜 코드 vs 좋은 코드

| 구분      | 자료 구조 방식  | 객체 방식     |
| ------- | --------- | --------- |
| 데이터     | 공개        | 숨김        |
| 동작      | 외부 함수에 있음 | 객체 내부에 있음 |
| 새 동작 추가 | 쉬움        | 어려움       |
| 새 타입 추가 | 어려움       | 쉬움        |
| 대표 방식   | 절차적 코드    | 객체지향 코드   |

## Anti-Symmetry

객체와 자료 구조는 서로 반대되는 특성을 가진다.

자료구조 방식:

- 새 타입 추가 어려움
- 새 함수 추가 쉬움

객체 방식:

- 새 타입 추가 쉬움
- 새 함수 추가 어려움

그래서 이를 Data/Object Anti-Symmetry, 즉 **자료와 객체의 반대 대칭성**이라고 한다.

## 핵심 원칙

> 분별있는 프로그래머라면 **모든 것이 객체라는 생각이 미신임**을 잘 안다. **때로는 단순한 자료구조와 절차적인 코드가 가장 적합한 상황도 있다.**

### 객체 방식이 적합한 경우 

새로운 타입이 자주 추가될 때,

```text
PaymentMethod
 ├─ CreditCardPayment
 ├─ BankTransferPayment
 └─ KakaoPayPayment
```

결제 방식이 늘어나더라도 사용법은 똑같다.

```java
payment.pay();
```

### 자료 구조 방식이 적합한 경우 

데이터 타입은 안정적이고 새로운 연산이 자주 추가될 때,

```java
calculateTotal(orderData);
validate(orderData);
print(orderData);
export(orderData); // 새 함수 추가
```

- 새 함수가 추가되더라도 `orderData`는 안정적이다.
