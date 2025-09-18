// 애노테이션 프로퍼티 값 추출 - 배열 값 추출
package com.eomcs.annotation.ex5;

public class Exam04 {

  public static void main(String[] args) {
    Class<?> clazz = MyClass4.class;
    MyAnnotation3 obj = clazz.getAnnotation(MyAnnotation3.class);

    printValues(obj.v1());
    System.out.println("----------------------");

    printValues(obj.v2());
    System.out.println("----------------------");

    printValues(obj.v3());
  }

  static void printValues(String[] values) {
    for (String value : values) {
      System.out.print(value + ",");
    }
    System.out.println();
  }

  static void printValues(int[] values) {
    for (int value : values) {
      System.out.print(value + ",");
    }
    System.out.println();
  }

  static void printValues(float[] values) {
    for (float value : values) {
      System.out.print(value + ",");
    }
    System.out.println();
  }
}


