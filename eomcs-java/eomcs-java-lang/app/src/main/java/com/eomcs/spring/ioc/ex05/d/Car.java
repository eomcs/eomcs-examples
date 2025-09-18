package com.eomcs.spring.ioc.ex05.d;

import java.util.Properties;

public class Car {
  String model;
  String maker;
  int cc;
  Properties options;

  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + ", options=" + options + "]";
  }

  public Properties getOptions() {
    return options;
  }

  public void setOptions(Properties options) {
    this.options = options;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public int getCc() {
    return cc;
  }

  public void setCc(int cc) {
    this.cc = cc;
  }


}
