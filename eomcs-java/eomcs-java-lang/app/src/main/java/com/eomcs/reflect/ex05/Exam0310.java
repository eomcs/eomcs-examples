// 메서드의 modifier
package com.eomcs.reflect.ex05;

//어떤 클래스나 인터페이스의 스태틱 멤버를 import 할 수 있다.
import static java.lang.reflect.Modifier.FINAL;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.lang.reflect.Modifier.PROTECTED;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Exam0310 {

  public static void main(String[] ok) {
    Class<?> clazz = String.class;

    // 클래스에 정의된 메서드를 모두 가져온다.
    Method[] methods = clazz.getDeclaredMethods();
    for (Method m : methods) {
      System.out.printf("%s() => ", m.getName());

      int modifiers = m.getModifiers();
      System.out.println(String.format("%32s", Integer.toBinaryString(modifiers)).replaceAll(" ", "0"));

      if (Modifier.isPublic(modifiers)) { //(modifiers & Modifier.PUBLIC) == Modifier.PUBLIC)
        System.out.print(" public");
      } else if ((modifiers & PROTECTED) != 0) {
        System.out.print(" protected");
      } else if ((modifiers & PRIVATE) != 0) {
        System.out.print(" private");
      }


      //if ((modifiers & STATIC) != 0)
      if (Modifier.isStatic(modifiers)) {
        System.out.print(" static");
      }

      if ((modifiers & FINAL) != 0) {
        System.out.print(" final");
      }

      System.out.println();
    }
  }

}


