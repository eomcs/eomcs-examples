// 클래스 정보 추출 - 배열 항목의 타입
package com.eomcs.reflect.ex02;

import java.sql.Date;

public class Exam0130 {

  public static void main(String[] args) throws Exception {
    // getComponentType()
    // - 배열의 항목에 대한 타입 정보를 리턴한다.
    // - 배열이 아니라면, 항목이 없기 때문에 리턴 값은 null이다.
    // 
    System.out.println(byte.class.getComponentType());
    System.out.println("---------------------------");

    System.out.println(byte.class.getName());
    System.out.println(byte[].class.getName());
    System.out.println(byte[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(short.class.getName());
    System.out.println(short[].class.getName());
    System.out.println(short[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(int.class.getName());
    System.out.println(int[].class.getName());
    System.out.println(int[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(long.class.getName());
    System.out.println(long[].class.getName());
    System.out.println(long[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(float.class.getName());
    System.out.println(float[].class.getName());
    System.out.println(float[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(double.class.getName());
    System.out.println(double[].class.getName());
    System.out.println(double[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(boolean.class.getName());
    System.out.println(boolean[].class.getName());
    System.out.println(boolean[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(char.class.getName());
    System.out.println(char[].class.getName());
    System.out.println(char[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(String.class.getName());
    System.out.println(String[].class.getName());
    System.out.println(String[].class.getComponentType().getName());
    System.out.println("---------------------------");

    System.out.println(Date.class.getName());
    System.out.println(Date[].class.getName());
    System.out.println(Date[].class.getComponentType().getName());
    System.out.println("---------------------------");
  }

}





