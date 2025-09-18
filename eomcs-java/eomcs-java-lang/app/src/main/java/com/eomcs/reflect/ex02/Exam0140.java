// 클래스 정보 추출 - 컬렉션의 값 타입
package com.eomcs.reflect.ex02;

import java.util.ArrayList;

public class Exam0140 {

  public static void main(String[] args) throws Exception {
    ArrayList<Object> values = new ArrayList<>();
    values.add(100);
    values.add(100L);
    values.add(3.14f);
    values.add(314.55);
    values.add(true);
    values.add('A');
    values.add("Hello");
    values.add(new int[] {100, 200, 300});
    values.add(new String[] {"aaa", "bbb", "ccc"});

    for (Object value : values) {
      printTypeInfo(value.getClass());
    }
  }

  private static void printTypeInfo(Class<?> type) {
    if (type.getName().startsWith("[")) {
      System.out.printf("=> %s[]\n", type.getComponentType().getName());
    } else {
      System.out.printf("=> %s\n", type.getName());
    }
  }

}





