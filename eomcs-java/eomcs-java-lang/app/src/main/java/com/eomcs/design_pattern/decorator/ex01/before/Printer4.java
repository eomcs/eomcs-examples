package com.eomcs.design_pattern.decorator.ex01.before;

public class Printer4 extends Printer {

  String footer;

  public Printer4(String footer) {
    this.footer = footer;
  }

  @Override
  void print(String content) {
    super.print(content);
    System.out.printf("========== %s ==========\n", footer);
  }
}
