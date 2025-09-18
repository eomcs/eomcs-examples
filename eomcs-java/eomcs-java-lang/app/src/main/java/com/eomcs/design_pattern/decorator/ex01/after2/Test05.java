package com.eomcs.design_pattern.decorator.ex01.after2;

public class Test05 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    SignPrinter printer = new SignPrinter(printer0, "홍길동");
    printer.print("안녕하세요!");
  }
}
