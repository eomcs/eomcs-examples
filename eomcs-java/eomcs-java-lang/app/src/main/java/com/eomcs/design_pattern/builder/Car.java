package com.eomcs.design_pattern.builder;

import java.util.ArrayList;

public class Car {
  Body body;
  Engine engine;
  ArrayList<Door> doors = new ArrayList<>();
  
  public Car(Body body, Engine engine) {
    this.body = body;
    this.engine = engine;
  }
  
  public void addDoor(Door door) {
    doors.add(door);
  }

  @Override
  public String toString() {
    return "Car [body=" + body + ", engine=" + engine + ", doors=" + doors + "]";
  }
  
  
}
