package com.eomcs.basic.ex06.assignment;

import java.util.Scanner;

public class Test03 {

  public static void main(String[] args) {
    int width = inputInt();
    
    if (width % 2 == 0)
      width--;
    
    int spaceSize = width >> 1;
    int line = 0; 
    while (line++ < width) {
      if (line % 2 == 0) {
        continue;
      }
      
      int count = 0;
      while (count++ < spaceSize) {
        System.out.print(" ");
      }
      spaceSize--;
      
      drawLine(line);
      System.out.println();
    }
  }
  
  static int inputInt() {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("밑변 길이? ");
    int width = keyScan.nextInt();
    keyScan.close();
    return width;
  }
  
  static void drawLine(int length) {
    int x = 0;
    while (x++ < length) {
      System.out.print("*");
    }
  }
  
  

}
