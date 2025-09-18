package com.eomcs.lang.ex06.assignment;

public class Graphic {
  
  static void drawLine(int length) {
    int x = 0;
    while (x++ < length) {
      System.out.print("*");
    }
  }
  
  static void drawLine(int length, char ch) {
    int x = 0;
    while (x++ < length) {
      System.out.print(ch);
    }
  }
  
  static void drawRectangle(int width, int height, char ch) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.out.print(ch);
      }
      System.out.println();
    }
  }
}






