package com.eomcs.design_pattern.decorator.ex01.after2;

public class ContentPrinter implements Printer {
  @Override
  public void print(String content) {
    System.out.println(content);
  }
}