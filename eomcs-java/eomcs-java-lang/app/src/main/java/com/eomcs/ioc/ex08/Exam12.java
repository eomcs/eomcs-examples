// IoC 컨테이너 만들기 - 의존 객체의 주입
package com.eomcs.ioc.ex08;

// 의존 객체 주입(dependancies injection; DI)
// => 작업하는 데 필요한 의존객체를 자체적으로 만들지 않고 외부에서 주입해주는 것을 말한다.
// => 이점?
//    다양한 객체를 주입할 수 있다.
//    즉 나중에 Engine의 새로운 서브 클래스가 등장했을 때 그 객체도 주입할 수 있다.
// => Car 클래스는 경우 생성자에서 Engine 객체를 생성하기 때문에 
//    새로운 Engine의 서브 클래스가 만들어지더라도 사용할 수 없다.
// 
public class Exam12 {
  public Exam12() {
    System.out.println("===> Exam12()");
  }

  public static void main(String[] args) throws Exception {
    Engine engine = new Engine();
    Car2 car = new Car2(engine);
    car.move();
  }
}









