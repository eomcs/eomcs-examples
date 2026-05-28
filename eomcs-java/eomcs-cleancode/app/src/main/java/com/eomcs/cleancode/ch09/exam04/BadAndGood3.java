package com.eomcs.cleancode.ch09.exam04;

// 예제 3: 여러 assert가 허용되는 경우 - Point
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    int getX() { return x; }
    int getY() { return y; }

    Point move(int dx, int dy) {
      return new Point(x + dx, y + dy);
    }
  }
}
