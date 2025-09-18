package com.eomcs.design_pattern.decorator.ex01.after2;

public class Test03 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    HeaderPrinter printer1 = new HeaderPrinter(printer0, "인사말");
    FooterPrinter printer = new FooterPrinter(printer1, "비트캠프");
    printer.print("안녕하세요!");
  }
}
