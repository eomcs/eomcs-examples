package com.eomcs.design_pattern.decorator.ex01.after1;

public class Printer2 {

  Printer origin = new Printer();
  String header;

  public Printer2(String header) {
    this.header = header;
  }

  void print(String content) {
    System.out.printf("[ %s ]--------------\n", header);
    origin.print(content);
  }
}
