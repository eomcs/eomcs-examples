package com.eomcs.oop.ex06.a;

public class Car extends Vehicle {
  int cc;
  int valve;

  public Car() {}

  public Car(String model, int capacity, int cc, int valve) {
    super(model, capacity);
    this.cc = cc;
    this.valve = valve;
  }
}






