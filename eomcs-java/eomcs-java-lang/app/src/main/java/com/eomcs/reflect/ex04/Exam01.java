// 생성자 : 생성자 정보 가져오기
package com.eomcs.reflect.ex04;

import java.lang.reflect.Constructor;

public class Exam01 {

  public Exam01() {}

  public Exam01(int i) {}

  public Exam01(String s, int i) {}

  public static void main(String[] args) {
    Class<?> clazz = Exam01.class;

    // 생성자 목록 가져오기
    Constructor<?>[] list = clazz.getConstructors();
    for (Constructor<?> c : list) {
      System.out.printf("%s(%d)\n", c.getName(), c.getParameterCount());
    }

  }

}
