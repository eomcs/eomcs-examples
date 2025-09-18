// Object 클래스 - getClass() 와 배열
package com.eomcs.basic.ex01;

public class Exam0162 {

  public static void main(String[] args) {

    // 배열의 클래스 정보
    String[] obj2 = new String[10];
    Class classInfo = obj2.getClass();
    System.out.println(classInfo.getName()); //[Ljava.lang.String;

    String str = new String("Hello");
    Class classInfo2 = str.getClass();
    System.out.println(classInfo2.getName());
    System.out.println(String.class.getName());

    System.out.println(new byte[10].getClass().getName()); //[B
    System.out.println(new short[10].getClass().getName()); //[S
    System.out.println(new char[10].getClass().getName()); //[C
    System.out.println(new int[10].getClass().getName()); //[I
    System.out.println(new long[10].getClass().getName()); //[J
    System.out.println(new float[10].getClass().getName()); //[F
    System.out.println(new double[10].getClass().getName()); //[D
    System.out.println(new boolean[10].getClass().getName()); //[Z

  }
}







