package com.eomcs.cleancode.ch06.exam02;

// 예제 2: 객체 방식 (Object Approach)
// - 데이터는 숨기고, 동작은 각 객체 내부에 구현한다.
// - 새로운 자료 타입 추가 → 쉬움 (새 클래스만 추가)
// - 새로운 동작 추가 → 어려움 (모든 기존 구현 클래스 수정 필요)
public class BadAndGood2 {

  private BadAndGood2() {}

  // 동작을 인터페이스로 정의한다.
  interface Shape {
    double area();
  }

  // 각 도형이 데이터와 동작을 함께 캡슐화한다.
  static class Square implements Shape {
    private final double side;
    Square(double side) { this.side = side; }

    @Override public double area() { return side * side; }
  }

  static class Rectangle implements Shape {
    private final double width;
    private final double height;
    Rectangle(double width, double height) { this.width = width; this.height = height; }

    @Override public double area() { return width * height; }
  }

  static class Circle implements Shape {
    private final double radius;
    Circle(double radius) { this.radius = radius; }

    @Override public double area() { return Math.PI * radius * radius; }
  }

  // 사용 예: 호출 코드는 Shape 인터페이스만 알면 된다.
  static class AreaCalculator {
    double calculateArea(Shape shape) {
      return shape.area();
    }
  }

  // -----------------------------------------------------------------------
  // Pro: 새로운 자료 타입 추가가 쉽다.
  // - Triangle 클래스만 새로 만들면 된다.
  // - 기존 Square, Rectangle, Circle, AreaCalculator는 전혀 수정하지 않는다.
  static class Triangle implements Shape {
    private final double base;
    private final double height;
    Triangle(double base, double height) { this.base = base; this.height = height; }

    @Override public double area() { return base * height / 2; }
  }

  // -----------------------------------------------------------------------
  // Con: 새로운 동작 추가가 어렵다.
  // - Shape 인터페이스에 perimeter()를 추가하면
  //   모든 구현 클래스(Square, Rectangle, Circle, Triangle)가 perimeter()를 구현해야 한다.
  interface ShapeWithPerimeter {
    double area();
    double perimeter(); // 새 동작 추가
  }

  static class SquareWithPerimeter implements ShapeWithPerimeter {
    private final double side;
    SquareWithPerimeter(double side) { this.side = side; }

    @Override public double area() { return side * side; }
    @Override public double perimeter() { return 4 * side; } // 모든 클래스에 추가 필요
  }

  static class RectangleWithPerimeter implements ShapeWithPerimeter {
    private final double width;
    private final double height;
    RectangleWithPerimeter(double width, double height) { this.width = width; this.height = height; }

    @Override public double area() { return width * height; }
    @Override public double perimeter() { return 2 * (width + height); } // 모든 클래스에 추가 필요
  }

  static class CircleWithPerimeter implements ShapeWithPerimeter {
    private final double radius;
    CircleWithPerimeter(double radius) { this.radius = radius; }

    @Override public double area() { return Math.PI * radius * radius; }
    @Override public double perimeter() { return 2 * Math.PI * radius; } // 모든 클래스에 추가 필요
  }

  static class TriangleWithPerimeter implements ShapeWithPerimeter {
    private final double base;
    private final double height;
    private final double sideA;
    private final double sideB;
    private final double sideC;

    TriangleWithPerimeter(double base, double height,
        double sideA, double sideB, double sideC) {
      this.base = base;
      this.height = height;
      this.sideA = sideA;
      this.sideB = sideB;
      this.sideC = sideC;
    }

    @Override public double area() { return base * height / 2; }
    @Override public double perimeter() { return sideA + sideB + sideC; } // 모든 클래스에 추가 필요
  }
}
