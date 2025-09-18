package com.eomcs.openapi.json.test;

public class Car {
  String name;
  String maker;

  @Override
  public String toString() {
    return "Car [name=" + name + ", maker=" + maker + "]";
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getMaker() {
    return maker;
  }
  public void setMaker(String maker) {
    this.maker = maker;
  }


}
