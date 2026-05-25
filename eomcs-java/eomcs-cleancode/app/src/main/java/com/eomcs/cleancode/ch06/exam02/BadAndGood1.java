package com.eomcs.cleancode.ch06.exam02;

// 예제 1: 자료 구조 방식 (Data Structure Approach)
// - 데이터는 공개하고, 동작은 외부 함수(Geometry)에 모은다.
// - 새로운 동작 추가 → 쉬움 (Geometry에 메서드만 추가)
// - 새로운 자료 타입 추가 → 어려움 (모든 기존 함수에 분기 추가 필요)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Point {
    double x;
    double y;
    Point(double x, double y) { this.x = x; this.y = y; }
  }

  static class NoSuchShapeException extends RuntimeException {
    NoSuchShapeException() { super("Unknown shape"); }
  }

  // 자료 구조: 데이터만 담고 동작은 없다.
  static class Square {
    public Point topLeft;
    public double side;
  }

  static class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
  }

  static class Circle {
    public Point center;
    public double radius;
  }

  // 동작: 외부 함수 클래스에 모아서 구현한다.
  static class Geometry {
    public final double PI = 3.141592653589793;

    public double area(Object shape) {
      if (shape instanceof Square s)    return s.side * s.side;
      if (shape instanceof Rectangle r) return r.height * r.width;
      if (shape instanceof Circle c)    return PI * c.radius * c.radius;
      throw new NoSuchShapeException();
    }
  }

  // -----------------------------------------------------------------------
  // Pro: 새로운 동작 추가가 쉽다.
  // - Square, Rectangle, Circle은 전혀 수정하지 않는다.
  // - Geometry에 perimeter() 메서드만 추가하면 된다.
  static class GeometryWithPerimeter {
    public final double PI = 3.141592653589793;

    public double area(Object shape) {
      if (shape instanceof Square s)    return s.side * s.side;
      if (shape instanceof Rectangle r) return r.height * r.width;
      if (shape instanceof Circle c)    return PI * c.radius * c.radius;
      throw new NoSuchShapeException();
    }

    public double perimeter(Object shape) {
      if (shape instanceof Square square)          return 4 * square.side;
      if (shape instanceof Rectangle rectangle)    return 2 * (rectangle.width + rectangle.height);
      if (shape instanceof Circle circle)          return 2 * Math.PI * circle.radius;
      throw new NoSuchShapeException();
    }
  }

  // -----------------------------------------------------------------------
  // Con: 새로운 자료 타입 추가가 어렵다.
  // - Triangle을 추가하면 기존의 모든 함수(area, perimeter)에
  //   Triangle 분기를 하나씩 추가해야 한다.
  static class Triangle {
    public double base;
    public double height;
    public double sideA;
    public double sideB;
    public double sideC;
  }

  static class GeometryWithTriangle {
    public final double PI = 3.141592653589793;

    public double area(Object shape) {
      if (shape instanceof Square s)      return s.side * s.side;
      if (shape instanceof Rectangle r)   return r.height * r.width;
      if (shape instanceof Circle c)      return PI * c.radius * c.radius;
      if (shape instanceof Triangle t)    return t.base * t.height / 2; // 분기 추가
      throw new NoSuchShapeException();
    }

    public double perimeter(Object shape) {
      if (shape instanceof Square square)       return 4 * square.side;
      if (shape instanceof Rectangle rectangle) return 2 * (rectangle.width + rectangle.height);
      if (shape instanceof Circle circle)       return 2 * Math.PI * circle.radius;
      if (shape instanceof Triangle t)          return t.sideA + t.sideB + t.sideC; // 분기 추가
      throw new NoSuchShapeException();
    }
  }
}
