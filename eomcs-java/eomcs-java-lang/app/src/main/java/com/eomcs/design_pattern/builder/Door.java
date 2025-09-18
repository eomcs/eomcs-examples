package com.eomcs.design_pattern.builder;

public class Door {
  String model;

  public Door(String model) {
    this.model = model;
  }
  
  @Override
  public String toString() {
    return "Door [model=" + model + "]";
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
  
  
}
