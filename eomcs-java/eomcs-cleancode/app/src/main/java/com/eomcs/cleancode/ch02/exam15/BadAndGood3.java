package com.eomcs.cleancode.ch02.exam15;

public class BadAndGood3 {

  // Bad
  // - x, y, w, h → 좌표인지 크기인지 의미를 추측해야 한다.
  // - 변수 간의 관계와 맥락이 전혀 드러나지 않는다.
  static class BadExample {
    void draw() {
      int x = 10;
      int y = 20;
      int w = 100;
      int h = 50;
      render(x, y, w, h);
    }
    void render(int x, int y, int w, int h) {
      System.out.println("x=" + x + " y=" + y + " w=" + w + " h=" + h);
    }
  }

  // Good
  // - Rectangle이라는 맥락을 제공한다.
  // - x, y는 좌표, width, height는 크기임이 자연스럽게 연결된다.
  static class Rectangle {
    int x;
    int y;
    int width;
    int height;
  }

  static class GoodExample {
    void draw() {
      Rectangle rect = new Rectangle();
      rect.x = 10;
      rect.y = 20;
      rect.width = 100;
      rect.height = 50;
      render(rect);
    }
    void render(Rectangle rect) {
      System.out.println("x=" + rect.x + " y=" + rect.y + " w=" + rect.width + " h=" + rect.height);
    }
  }
}
