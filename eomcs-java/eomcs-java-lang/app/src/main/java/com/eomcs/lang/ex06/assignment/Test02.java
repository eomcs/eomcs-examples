package com.eomcs.lang.ex06.assignment;

public class Test02 {

  public static void main(String[] args) {
    
    int width = Console.inputInt();
    
    int line = 0;
    while (line++ < width) {
      Graphic.drawLine(line);
      System.out.println();
    }
    line--;
    
    while (--line > 0) {
      Graphic.drawLine(line);
      System.out.println();
    }
  }
}
