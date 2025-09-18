package com.eomcs.design_pattern.decorator.ex01.before;

public class Printer6 extends Printer5 {

  String header;

  public Printer6(String sign, String header) {
    super(sign);
    this.header = header;
  }

  @Override
  void print(String content) {
    System.out.printf("[ %s ]--------------\n", header);
    super.print(content);
  }
}
