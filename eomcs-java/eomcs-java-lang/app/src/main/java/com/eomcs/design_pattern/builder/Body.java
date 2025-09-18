package com.eomcs.design_pattern.builder;

public class Body {
  String model;
  String type;
  
  public Body(String model, String type) {
    this.model = model;
    this.type = type;
  }
  
  @Override
  public String toString() {
    return "Body [model=" + model + ", type=" + type + "]";
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  
  
}
