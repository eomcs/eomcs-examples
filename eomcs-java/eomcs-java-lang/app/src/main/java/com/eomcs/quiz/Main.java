package com.eomcs.quiz;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner keyIn = new Scanner(System.in);
    int a = keyIn.nextInt();
    int b = keyIn.nextInt();
    keyIn.close();
    System.out.println(a + b);
    System.out.println(a - b);
    System.out.println(a * b);
    System.out.println(a / b);
    System.out.println(a % b);
  }

}
