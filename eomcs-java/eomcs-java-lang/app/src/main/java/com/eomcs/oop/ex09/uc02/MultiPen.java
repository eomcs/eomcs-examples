package com.eomcs.oop.ex09.uc02;

public class MultiPen extends Zebra implements Pen {
  @Override
  public void write(String text) {
    if (!this.use)
      return;
    System.out.println("제브라>> " + text);
  }
}
