package com.eomcs.oop.ex06.a;

public class Truck extends Car{
  float ton;
  boolean dump;

  public Truck() {}

  public Truck(String model, int capacity, int cc, int valve, float ton, boolean dump) {
    super(model, capacity, cc, valve);
    this.ton = ton;
    this.dump = dump;
  }
}
