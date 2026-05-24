package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood5 {

  // Bad
  // - draw(x, y, radius) → 인자 3개의 순서와 의미를 기억해야 한다.
  // - x, y 가 중심 좌표인지, 모서리 좌표인지 이름만으로 알 수 없다.
  static class BadRenderer {
    void draw(int x, int y, int radius) {
      System.out.println("원 그리기: (" + x + ", " + y + ") 반지름=" + radius);
    }
  }

  // Good
  // - 관련 인자(x, y, radius)를 Circle 객체로 묶어 인자를 하나로 줄인다.
  // - draw(Circle circle) → 인자 의미가 명확하고 순서를 기억할 필요가 없다.
  static class Circle {
    int x;
    int y;
    int radius;
    Circle(int x, int y, int radius) { this.x = x; this.y = y; this.radius = radius; }
  }

  static class GoodRenderer {
    void draw(Circle circle) {
      System.out.println("원 그리기: (" + circle.x + ", " + circle.y + ") 반지름=" + circle.radius);
    }
  }
}
