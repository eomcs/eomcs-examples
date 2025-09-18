package com.eomcs.design_pattern.test01.step4;

public class K7Factory implements CarFactory {
  @Override
  public Sedan createCar() {
    Sedan s = new Sedan();
    s.maker = "기아자동차";
    s.model = "K7";
    s.cc = 2500;
    s.auto = true;
    s.sunroof = true;
    return s;
  }
}
