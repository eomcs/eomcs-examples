// 다형성 - 다형적 변수(polymorphic variables)
package com.eomcs.oop.ex06.a;

public class Exam0110 {

  public static void main(String[] args) {
    Vehicle vehicle = new Vehicle();
    Bike bike = new Bike();
    Car car = new Car();
    Sedan sedan = new Sedan();
    Truck truck = new Truck();

    // 레퍼런스는 같은 타입의 객체를 가리킬 수 있을 뿐만아니라
    // 그 클래스의 서브클래스 객체까지 가리킬 수 있다.
    // 왜? 
    // - 서브 클래스는 항상 상위 클래스의 모든 것을 사용할 수 있기 때문이다.
    // - 서브 클래스의 인스턴스를 만들 때 상위 클래스의 인스턴스 변수도 만든다.
    // - 따라서 상위 클래스의 레퍼런스를 사용하여 그 클래스의 인스턴스 변수를 100% 사용할 수 있다.
    // 
    // 이런 규칙에 따라, 다음 vehicle2 변수는 Vehicle 객체 뿐만아니라
    // Bike, Car, Sedan, Truck 객체까지 다양한 서브클래스의 객체를 가리킬 수 있다.
    // 그래서 vehicle2를 "다형적 변수"의 기능을 갖고 있다 말한다.
    Vehicle vehicle2 = null;
    vehicle2 = bike; // OK
    vehicle2 = car; // OK
    vehicle2 = sedan; // OK
    vehicle2 = truck; // OK

    Car car2 = null;
    car2 = sedan; // OK
    car2 = truck; // OK
    //    car2 = bike; // Error!

    Bike bike2 = null;
    //    bike2 = car; // Error!
    //    bike2 = sedan; // Error!
    //    bike2 = truck; // Error!
  }

}









