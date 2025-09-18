package com.eomcs.oop.ex05.x6;

public class CarTest1 {
  public static void main(String[] args) {
    Sedan sedan = new Sedan();
    testSedan(sedan);

    System.out.println("-----------------------");

    Truck truck = new Truck();
    testTruck(truck);
  }

  static void testSedan(Sedan car) {
    car.start();
    car.run();
    car.stop();
  }

  static void testTruck(Truck car) {
    car.launch();
    car.go();
    car.stopping();
  }
}
