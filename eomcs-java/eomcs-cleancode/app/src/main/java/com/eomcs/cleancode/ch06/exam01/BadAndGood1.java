package com.eomcs.cleancode.ch06.exam01;

// 예제 1: 자료 추상화 - Point
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad1: 구현을 그대로 노출한다.
  // - x, y 좌표를 public 필드로 직접 노출한다.
  // - 직교 좌표계라는 구현 방식이 외부에 드러난다.
  // - 나중에 극좌표계로 바꾸기 어렵다.
  static class BadPoint1 {
    public double x;
    public double y;
  }

  // Bad2: getter/setter를 붙인다고 추상화가 되는 것은 아니다.
  // - 필드는 private이지만 x, y라는 내부 구현을 그대로 공개하고 있다.
  // - 변수 사이에 함수라는 계층을 넣어도 구현이 저절로 감춰지지 않는다.
  static class BadPoint2 {
    private double x;
    private double y;

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
  }

  // Good: 추상 인터페이스로 자료의 본질을 표현한다.
  // - 사용자는 직교 좌표(x, y)와 극좌표(r, theta) 두 방식으로 점을 다룰 수 있다.
  // - 내부 구현이 x/y인지 r/theta인지 전혀 알 수 없다.
  // - 내부 표현을 바꿔도 인터페이스는 그대로 유지된다.
  interface GoodPoint {
    double getX();
    double getY();

    double getR();
    double getTheta();

    void setCartesian(double x, double y);
    void setPolar(double r, double theta);
  }

  // 내부는 직교 좌표로 저장하지만, 극좌표 조회도 지원한다.
  static class CartesianPoint implements GoodPoint {
    private double x;
    private double y;

    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    @Override public double getR() { return Math.sqrt(x * x + y * y); }
    @Override public double getTheta() { return Math.atan2(y, x); }

    @Override
    public void setCartesian(double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public void setPolar(double r, double theta) {
      this.x = r * Math.cos(theta);
      this.y = r * Math.sin(theta);
    }
  }
}
