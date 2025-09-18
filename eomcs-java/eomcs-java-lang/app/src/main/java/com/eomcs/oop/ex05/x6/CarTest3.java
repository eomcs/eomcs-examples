package com.eomcs.oop.ex05.x6;

public class CarTest3 {
  public static void main(String[] args) {
    Sedan sedan = new Sedan();
    SnowChain snowChain = new SnowChain(sedan);
    testCar(snowChain);

    System.out.println("-----------------------------");

    Truck truck = new Truck();
    BlackBox blackBox = new BlackBox(truck);
    testCar(blackBox);

    System.out.println("-----------------------------");
    SUV suv = new SUV();
    SnowChain snowChainSuv = new SnowChain(suv);
    BlackBox blackBoxSuv = new BlackBox(snowChainSuv);
    testCar(blackBoxSuv);

  }

  static void testCar(Car car) {
    car.start();
    car.run();
    car.stop();
  }
}
