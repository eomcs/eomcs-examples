package com.eomcs.oop.ex06.a;

public class Bike extends Vehicle {
  boolean engine;

  public Bike() {}

  public Bike(String model, int capacity, boolean engine) {
    super(model, capacity);
    this.engine = engine;
  }
}






