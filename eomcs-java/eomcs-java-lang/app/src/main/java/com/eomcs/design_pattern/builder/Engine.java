package com.eomcs.design_pattern.builder;

public class Engine {
  String model;
  String cc;
  int cylinder;
  int valve;
  
  public Engine(String model, String cc, int cylinder, int valve) {
    this.model = model;
    this.cc = cc;
    this.cylinder = cylinder;
    this.valve = valve;
  }
  
  @Override
  public String toString() {
    return "Engine [model=" + model + ", cc=" + cc + ", cylinder=" + cylinder + ", valve=" + valve
        + "]";
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getCc() {
    return cc;
  }
  public void setCc(String cc) {
    this.cc = cc;
  }
  public int getCylinder() {
    return cylinder;
  }
  public void setCylinder(int cylinder) {
    this.cylinder = cylinder;
  }
  public int getValve() {
    return valve;
  }
  public void setValve(int valve) {
    this.valve = valve;
  }
  
  
}
