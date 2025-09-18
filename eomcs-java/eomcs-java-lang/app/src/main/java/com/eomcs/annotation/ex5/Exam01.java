// 애노테이션 프로퍼티 값 추출
package com.eomcs.annotation.ex5;

public class Exam01 {

  public static void main(String[] args) {
    Class<?> clazz = MyClass.class;
    MyAnnotation obj = clazz.getAnnotation(MyAnnotation.class);

    System.out.println(obj.v1());
    System.out.println(obj.v2());
    System.out.println(obj.v3());

  }
}


