// 생성자 : 파라미터를 가지는 생성자 알아내기 
package com.eomcs.reflect.ex04;

import java.lang.reflect.Constructor;

public class Exam02 {

  public Exam02() {}

  public Exam02(int i) {}

  public Exam02(String s, int i) {}

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Exam02.class;

    // 파라미터가 없는 기본 생성자 가져오기
    Constructor<?> c1 = clazz.getConstructor();
    System.out.printf("%s(%d)\n", c1.getName(), c1.getParameterCount());

    // int 값을 받는 생성자 가져오기
    Constructor<?> c2 = clazz.getConstructor(int.class);
    System.out.printf("%s(%d)\n", c2.getName(), c2.getParameterCount());

    // String과 int 값을 순서대로 받는 생성자 가져오기
    Constructor<?> c3 = clazz.getConstructor(String.class, int.class);
    System.out.printf("%s(%d)\n", c3.getName(), c3.getParameterCount());

    // 해당 타입의 값을 받는 생성자가 없을 때?
    // => 예외 발생!
    Constructor<?> c4 = clazz.getConstructor(String.class);
    System.out.printf("%s(%d)\n", c4.getName(), c4.getParameterCount());
  }

}
