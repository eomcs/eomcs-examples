package com.eomcs.oop.ex01.assignment;

import java.util.Scanner;

public class Test01 {
  public static void main(String[] args) {

    class Score {
      String name;
      int kor;
      int eng;
      int math;
    }

    Scanner keyScan = new Scanner(System.in);

    Score[] arr = new Score[3];

    for (int i = 0; i < arr.length; i++) {
      System.out.print("입력: ");
      Score s = new Score();

      s.name = keyScan.next();
      s.kor = keyScan.nextInt();
      s.eng = keyScan.nextInt();
      s.math = keyScan.nextInt();

      arr[i] = s;
    }

    keyScan.close();

    System.out.println("-------------------");
    
    for (int i = 0; i < arr.length; i++) {
      Score s = arr[i];
      int sum = s.kor + s.eng + s.math;
      float aver = sum / 3f;
      System.out.printf("%s %d %d %d %d %.1f\n", 
          s.name, s.kor, s.eng, s.math, sum, aver);
    }
  }
}




