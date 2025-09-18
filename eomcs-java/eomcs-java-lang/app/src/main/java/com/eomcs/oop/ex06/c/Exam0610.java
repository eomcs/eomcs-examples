// 오버라이딩(overriding) - 리턴 타입
package com.eomcs.oop.ex06.c;

public class Exam0610 {

  static class Car {}
  static class Sedan extends Car {}
  static class Truck extends Car {}
  static class Tico extends Sedan {}

  static class CarFactory {
    Car create() {
      return new Car();
    }
  }

  static class SedanFactory extends CarFactory {
    // 오버라이딩 메서드의 리턴 타입은
    // 서브 클래스도 가능하다.
    @Override
    Sedan create() {
      return new Sedan();
    }
  }

  static class TicoFactory extends SedanFactory {
    // 오버라이딩 메서드의 리턴 타입은
    // 서브 클래스도 가능하다.
    @Override
    Tico create() {
      return new Tico();
    }
  }


  public static void main(String[] args) {

  }
}








