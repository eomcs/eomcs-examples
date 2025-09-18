// 파라미터 - 파라미터 정보 알아내기
package com.eomcs.reflect.ex05;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Exam0110 {

  public void m1(String name, int age) {}

  public void m2() {}

  public void m3(File file, String name) {}

  public static void main(String[] ok) {
    Class<?> clazz = Exam0110.class;

    // 해당 클래스에 정의된 메서드를 모두 가져온다.
    Method[] methods = clazz.getDeclaredMethods();
    for (Method m : methods) {
      System.out.printf("%s:\n", m.getName());

      // 메서드의 파라미터 정보를 가져온다.
      Parameter[] parameters = m.getParameters();
      for (Parameter p : parameters) {
        System.out.printf(" %s : %s\n", p.getName(), p.getType().getName());
        // 파라미터의 이름은 argx 행태로 되어 있다.
        // .class 파일에는 분명히 파라미터 이름이 보관되어 있지만,
        // Reflection API에서는 보관된 값을 꺼낼 수 없다.
        // 꺼내려면 직접 .class 파일을 읽어서 파라미터 정보를 알아내야 한다.
        //
        // 컴파일할 때 파라미터 이름을 Reflection API에서 꺼낼 수 있도록
        // 설정해준다면, 그렇다면 원래의 파라미터 이름을 알아낼 수 있다.
        // 즉 -parameters 옵션을 추가하여 컴파일하면 Reflection API로 파라미터 이름을
        // 꺼낼 수 있다.
        // > javac -d bin/main -encoding UTF-8 -parameters src/com/eomcs/reflect/ex05/Exam01.java
        // 그런데 일반적으로 이 옵션을 붙여 컴파일 하지 않는다.
        // 그럼에도불구하고 Spring Framework나 eclipse IDE에서는
        // 메서드의 파라미터 이름을 정확하게 추출한다.
        // 그것은 Spring 프레임워크나 eclipse IDE가
        // 직접 .class 파일을 읽고 분석해서 해당 정보를 추출하기 때문이다.
        //
      }
    }
  }

}


