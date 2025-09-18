package com.eomcs.design_pattern.decorator.ex01.before;

public class Printer2 extends Printer {
  String header;

  public Printer2(String header) {
    this.header = header;
  }

  @Override
  void print(String content) {
    System.out.printf("[ %s ]--------------\n", header);

    super.print(content);
  }
}
