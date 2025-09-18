package com.eomcs.design_pattern.decorator.ex01.after2;

public abstract class PrinterDecorator implements Printer {

  protected Printer origin;

  public PrinterDecorator(Printer printer) {
    this.origin = printer;
  }
}
