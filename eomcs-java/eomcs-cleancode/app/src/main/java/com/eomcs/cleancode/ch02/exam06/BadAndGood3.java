package com.eomcs.cleancode.ch02.exam06;

// 인터페이스 접두어
public class BadAndGood3 {

  // Bad: 인터페이스에 I 접두어
  void bad() {
    interface IShape {
      double area();
    }
  }

  void bad2() {
    interface IShapeFactory {}

    // 또 다른 나쁜 예
    interface CShapeFactory {} // 극도로 나쁜 형태

    class ShapeFactory implements IShapeFactory {}
  }

  // Good: 인터페이스는 장식하지 않는다. 다만 구현체의 이름에서 인코딩할 수 있다.
  void good() {
    interface Shape {
      double area();
    }

    class Circle implements Shape {
      double radius;

      public double area() {
        return Math.PI * radius * radius;
      }
    }
  }

  void good2() {
    interface ShapeFactory {}

    class ShapeFactoryImpl implements ShapeFactory {}

    // 또는
    class DefaultShapeFactory implements ShapeFactory {}
  }
}
