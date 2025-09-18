package com.eomcs.design_pattern.test01.step6;

public class Sedan extends Car {
  boolean sunroof;
  boolean auto;

  @Override
  public String toString() {
    return "Sedan [sunroof=" + sunroof + ", auto=" + auto + ", maker=" + maker + ", model=" + model
        + ", cc=" + cc + "]";
  }

  protected Sedan() {}

  @Override
  protected void start() {
    System.out.printf("%s 시동건다!!\n", this.model);
  }

  @Override
  protected void run() {
    System.out.printf("%s '%s' 하고 달린다!\n", this.model, this.sunroof ? "썬루프 열고" : "썬루프 닫고");
  }

  @Override
  protected void stop() {
    System.out.printf("%s 시동끈다!!\n", this.model);
  }
}
