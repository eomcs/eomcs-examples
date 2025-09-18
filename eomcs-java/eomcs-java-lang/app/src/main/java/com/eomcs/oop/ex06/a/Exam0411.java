// 다형성 - 다형적 변수의 활용
package com.eomcs.oop.ex06.a;

public class Exam0411 {

  // Sedan과 Truck의 모델명과 cc를 출력하라!

  public static void printSedan(Sedan sedan) {
    System.out.printf("모델명: %s\n", sedan.model);
    System.out.printf("cc: %d\n", sedan.cc);
    System.out.println("-------------------------");
  }

  public static void printTruck(Truck truck) {
    System.out.printf("모델명: %s\n", truck.model);
    System.out.printf("cc: %d\n", truck.cc);
    System.out.println("-------------------------");
  }

  public static void main(String[] args) {
    Sedan sedan = new Sedan();
    sedan.model = "티코";
    sedan.cc = 800;

    Truck truck = new Truck();
    truck.model = "타이탄II";
    truck.cc = 10000;

    // Sedan 객체에서 model 과 cc를 뽑아 출력할 때는 해당 메서드를 호출하고,
    printSedan(sedan);

    // Truck은 다음과 같이 그에 대한 메서드를 호출한다.
    printTruck(truck);

    // Sedan의 모델명과 cc를 출력하는 메서드와
    // Truck의 모델명과 cc를 출력하는 메서드를
    // 모두 만들어야 하는 번거로움이 있다.
    // 해결책?
    // => 두 개의 클래스가 같은 조상을 가질 때는 다형적 변수를 활용하라!
  }

}




