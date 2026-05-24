package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood6 {

  static class Circle {
    int x;
    int y;
    int radius;
    Circle(int x, int y, int radius) { this.x = x; this.y = y; this.radius = radius; }
  }

  // Bad
  // - makeCircle(x, y, radius) → x, y 가 중심 좌표라는 것을 호출부에서 추측해야 한다.
  // - 인자 3개의 순서를 기억해야 한다.
  static class BadCircleFactory {
    Circle makeCircle(int x, int y, int radius) {
      return new Circle(x, y, radius);
    }
  }

  // Good
  // - 관련 데이터(x, y)를 Point 객체로 묶어 의미를 명확히 한다.
  // - makeCircle(Point center, int radius) → 중심점과 반지름의 관계가 이름으로 드러난다.
  static class Point {
    int x;
    int y;
    Point(int x, int y) { this.x = x; this.y = y; }
  }

  static class GoodCircleFactory {
    Circle makeCircle(Point center, int radius) {
      return new Circle(center.x, center.y, radius);
    }
  }
}
