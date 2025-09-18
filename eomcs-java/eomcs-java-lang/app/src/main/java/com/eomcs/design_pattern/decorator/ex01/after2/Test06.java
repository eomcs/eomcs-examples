package com.eomcs.design_pattern.decorator.ex01.after2;

public class Test06 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    SignPrinter printer1 = new SignPrinter(printer0, "홍길동");
    HeaderPrinter printer = new HeaderPrinter(printer1, "편지");
    printer.print("안녕하세요!");
  }
}
