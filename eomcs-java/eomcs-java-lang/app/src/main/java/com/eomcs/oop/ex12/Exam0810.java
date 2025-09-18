// 인터페이스 구현체를 만드는 다양한 방법
package com.eomcs.oop.ex12;

public class Exam0810 {

  interface Factory {
    Object create();
  }

  static class Car {}

  public static void main(String[] args) {
    // 1) 로컬 클래스로 인터페이스 구현체를 만든다.
    class CarFactory implements Factory {
      @Override
      public Object create() {
        return new Car();
      }
    }
    printCar(new CarFactory());

    // 2) 익명 클래스로 인터페이스 구현체를 만든다.
    printCar(new Factory() {
      @Override
      public Object create() {
        return new Car();
      }
    });

    // 3) 람다로 인터페이스 구현체를 만든다.
    printCar(() -> new Car());
    //    class $1 implements Factory {
    //      @Override
    //      public Object create() {
    //        return new Car();
    //      }
    //    }
    //    printCar(new $1());

    // 4) 기존에 존재하는 메서드를 인터페이스 구현체로 사용한다.
    printCar(Exam0810::createCar);
    //    class $1 implements Factory {
    //      @Override
    //      public Object create() {
    //        return Exam0810.createCar();
    //      }
    //    }
    //    printCar(new $1());

    // 5) 기존 클래스의 생성자를 인터페이스 구현체로 사용한다.
    printCar(Car::new);
    //    class $1 implements Factory {
    //      @Override
    //      public Object create() {
    //        return new Car();
    //      }
    //    }
    //    printCar(new $1());

    System.out.println("완료!");
  }

  public static Car createCar() {
    return new Car();
  }

  public static void printCar(Factory factory) {
    Object obj = factory.create();
    System.out.printf("만든 객체: %s\n", obj);
  }
}


