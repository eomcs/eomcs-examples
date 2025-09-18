package com.eomcs.design_pattern.decorator.ex01.after1;

public class Printer3 {

  Printer2 origin;
  String footer;

  public Printer3(String header, String footer) {
    origin = new Printer2(header);
    this.footer = footer;
  }

  void print(String content) {
    origin.print(content);
    System.out.printf("========== %s ==========\n", footer);
  }
}
