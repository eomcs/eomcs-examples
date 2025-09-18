// 메서드가 실제 정의된 클래스 알아내기
package com.eomcs.reflect.ex05;

import java.io.BufferedReader;
import java.lang.reflect.Method;

public class Exam0410 {

  public static void main(String[] ok) {
    Class<?> clazz = BufferedReader.class;

    // 수퍼 클래스의 메서드를 포함하여 모든 public 메서드를 알아낸다.
    Method[] methods = clazz.getMethods();

    for (Method m : methods) {
      // 메서드가 실제 정의된 클래스의 이름 출력하기
      System.out.printf("%s.%s()\n", 
          m.getDeclaringClass().getSimpleName(),
          m.getName());
    }
  }

}


