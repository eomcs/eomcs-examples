package com.eomcs.design_pattern.decorator.ex01.before;

public class Printer5 extends Printer {

  String sign;

  public Printer5(String sign) {
    this.sign = sign;
  }

  @Override
  void print(String content) {
    super.print(content);
    System.out.println();
    System.out.printf("from %s\n", sign);
    System.out.println();
  }
}
