package com.eomcs.lang.ex06.assignment;

public class Test01 {

  public static void main(String[] args) {
    
    int width = Console.inputInt();
    int line = 0; 
    while (line++ < width) {
      Graphic.drawLine(line);
      System.out.println();
    }
  }
}








