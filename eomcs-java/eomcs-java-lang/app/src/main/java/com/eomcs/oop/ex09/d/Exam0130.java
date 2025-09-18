// 인터페이스와 추상 클래스 : 추상 클래스의 도움 받기
package com.eomcs.oop.ex09.d;

// 1) 인터페이스 정의
interface CarSpec {
  // 인터페이스는 사용규칙을 정의한 것이다.
  // 그래서 인터페이스에 선언하는 모든 메서드는 추상 메서드이어야 한다.
  void run(); // 자동차를 몰 때 호출할 메서드
  void on(); // 자동차 시동을 켤 때 호출할 메서드
  void off(); // 자동차 시동을 끌 때 호출할 메서드
}

// 2) 추상 클래스로 인터페이스의 일부 규칙 구현하기
abstract class AbstractCar implements CarSpec {

  // 추상 클래스의 목적은 서브 클래스에게 공통 필드와 공통 메서드를 상속해주는 것이다.
  String model;
  int cc;

  // => 그 중에서 인터페이스에 선언된 메서드의 일부를 미리 구현해 주면
  //    서브 클래스를 만들기가 매우 편리하다.
  @Override
  public void on() {
    System.out.println("시동을 켠다.");
  }

  @Override
  public void off() {
    System.out.println("시동을 끈다.");
  }

  // => 즉 인터페이스의 메서드 중에서 서브 클래스가 구현해야만 의미가 있는 메서드의 경우
  //    추상 클래스에서 미리 구현할 필요가 없다.
  // => 서브 클래스가 구현하도록 강제하기 위해 추상 메서드로 내비 둔다.
  //    인터페이스의 메서드를 구현하지 않는다면 다음과 같이 추상 메서드인채로 남겨진다.
  //  @Override
  //  public abstract void run();
}

// 3) 인터페이스 직접 구현하기
// - 인터페이스에 따라 클래스를 작성하면
//   사용하는 쪽에서 일관된 방식으로 메서드를 사용할 수 있다.
class Tico implements CarSpec {

  // 인터페이스를 구현한다는 것은 인터페이스에 선언된 모든 메서드를 정의함을 의미한다.
  @Override
  public void run() {
    System.out.println("달린다!");
  }

  @Override
  public void on() {
    System.out.println("시동 켠다!");
  }

  @Override
  public void off() {
    System.out.println("시동 끈다.");
  }
}

// 4) 인터페이스 간접 구현
// - 인터페이스를 구현한 클래스를 상속 받는다면
//   결국 인터페이스를 구현한 것이 된다.
// - 자동차의 기능을 갖추려면 CarSpec을 만족시켜야 하는데,
//   직접 CarSpec 인터페이스를 구현하는 대신에
//   CarSpec을 미리 구현한 AbstractCar를 상속 받는 것이
//   클래스를 만들기가 편하다.
class Sonata extends AbstractCar {

  // CarSpec에 선언된 on(), off() 메서드는 수퍼 클래스에서 미리 구현했기 때문에
  // 서브 클래스에서 다시 구현할 필요가 없어 편하다!
  //
  // 서브 클래스는 수퍼 클래스가 구현하지 않은 나머지 메서드만 구현하면 된다.
  @Override
  public void run() {
    System.out.println("씽씽~~ 달린다!");
  }
}

class Truck extends AbstractCar {
  @Override
  public void run() {
    System.out.println("덜컹 덜컹 달린다!");
  }
}


// 5) 인터페이스 규칙에 따라 구현체 사용하기
public class Exam0130 {

  void test() {
    play(new Tico());
    System.out.println("----------------------");

    play(new Sonata());
    System.out.println("----------------------");

    play(new Truck());
    System.out.println("----------------------");
  }

  void play(CarSpec car) {
    car.on();
    car.run();
    car.off();
  }

  public static void main(String[] args) {
    new Exam0130().test();
  }
}






