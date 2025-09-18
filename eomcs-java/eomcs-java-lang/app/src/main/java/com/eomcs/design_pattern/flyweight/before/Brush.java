package com.eomcs.design_pattern.flyweight.before;

public class Brush {
  String pattern;

  public Brush(String pattern) {
    this.pattern = pattern;
  }

  public void draw(int length) {
    for (int i = 0; i < length; i++) {
      System.out.print(pattern);
    }
    System.out.println();
  }
}
