package com.eomcs.lang.ex06.assignment;

// 너비, 높이를 입력 받아 사각형을 그려라!
public class Test05 {

  public static void main(String[] args) {
    
    int width = Console.inputInt("너비? ");
    int height = Console.inputInt("높이? ");
    
    Graphic.drawRectangle(width, height, '*');
  }
}
