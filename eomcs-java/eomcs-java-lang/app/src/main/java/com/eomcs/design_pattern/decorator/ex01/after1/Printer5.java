package com.eomcs.design_pattern.decorator.ex01.after1;

public class Printer5 {

  Printer origin = new Printer();
  String sign;

  public Printer5(String sign) {
    this.sign = sign;
  }

  void print(String content) {
    origin.print(content);
    System.out.println();
    System.out.printf("from %s\n", sign);
    System.out.println();
  }
}
