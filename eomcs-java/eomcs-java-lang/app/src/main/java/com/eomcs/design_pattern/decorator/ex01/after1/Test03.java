package com.eomcs.design_pattern.decorator.ex01.after1;

public class Test03 {
  public static void main(String[] args) {
    Printer3 printer = new Printer3(
        "인사말",
        "비트캠프");
    printer.print("안녕하세요!");
  }
}
