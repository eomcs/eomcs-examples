package com.eomcs.design_pattern.decorator.ex01.after2;

public class HeaderPrinter extends PrinterDecorator {

  String header;

  public HeaderPrinter(Printer printer, String header) {
    super(printer);
    this.header = header;
  }

  @Override
  public void print(String content) {
    System.out.printf("[ %s ]--------------\n", header);
    origin.print(content);
  }
}
