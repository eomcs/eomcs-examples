package com.eomcs.oop.ex06.a;

public class Sedan extends Car {
  boolean sunroof;
  boolean auto;

  public Sedan() {}

  public Sedan(String model, int capacity, int cc, int valve, boolean sunroof, boolean auto) {
    super(model, capacity, cc, valve);
    this.sunroof = sunroof;
    this.auto = auto;
  }
}






