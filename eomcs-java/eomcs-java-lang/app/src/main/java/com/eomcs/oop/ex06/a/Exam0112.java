// 다형성 - 다형적 변수(polymorphic variables)
package com.eomcs.oop.ex06.a;

public class Exam0112 {

  public static void main(String[] args) {

    Vehicle v = new Vehicle();
    Bike b = new Bike();
    Car c = new Car();
    Sedan s = new Sedan();
    Truck t = new Truck();

    Bike b2 = null;

    // 하위 클래스의 레퍼런스로 상위 클래스의 인스턴스를 가리킬 수 없다.
    // => 상위 클래스의 인스턴스에는 하위 클래스의 멤버가 없을 수 있기 때문이다.
    //    b2 = v; // 컴파일 오류!

    // 만약 위의 코드가 가능하다면,
    // 다음과 같이 Bike 레퍼런스로 Bike 인스턴스 변수를 사용하려 할 것이다.
    b2.engine = true;

    // 그러나, b2가 실제 가리키는 것은 Bike의 인스턴스가 아니기 때문에
    // Bike의 engine 변수를 사용할 수 없다.
    //
    // 이렇게 개발자가 레퍼런스를 통해 존재하지 않는 인스턴스 멤버를 사용할까봐,
    // 이것을 미리 방지하려고 컴파일 단계에서 이런 사용을 막는 것이다.

  }

}
