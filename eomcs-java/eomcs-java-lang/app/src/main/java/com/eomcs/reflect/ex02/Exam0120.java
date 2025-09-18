// 클래스 정보 추출 - 배열의 이름
package com.eomcs.reflect.ex02;

import java.sql.Date;
import java.util.ArrayList;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    System.out.println(byte.class.getName());  // byte 타입
    System.out.println(byte[].class.getName()); // byte 배열 타입
    System.out.println("---------------------------");

    System.out.println(short.class.getName());
    System.out.println(short[].class.getName());
    System.out.println("---------------------------");

    System.out.println(int.class.getName());
    System.out.println(int[].class.getName());
    System.out.println("---------------------------");

    System.out.println(long.class.getName());
    System.out.println(long[].class.getName());
    System.out.println("---------------------------");

    System.out.println(float.class.getName());
    System.out.println(float[].class.getName());
    System.out.println("---------------------------");

    System.out.println(double.class.getName());
    System.out.println(double[].class.getName());
    System.out.println("---------------------------");

    System.out.println(boolean.class.getName());
    System.out.println(boolean[].class.getName());
    System.out.println("---------------------------");

    System.out.println(char.class.getName());
    System.out.println(char[].class.getName());
    System.out.println("---------------------------");

    System.out.println(String.class.getName());
    System.out.println(String[].class.getName());
    System.out.println("---------------------------");

    System.out.println(Date.class.getName());
    System.out.println(Date[].class.getName());
    System.out.println("---------------------------");

    System.out.println(ArrayList.class.getName());
    System.out.println(ArrayList[].class.getName());
    System.out.println("---------------------------");

  }
}





