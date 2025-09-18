package com.eomcs.lang.ex06;

import java.util.Iterator;

// # 흐름 제어문 - for(:) enhanced for 문
//
public class Exam0481 {
  public static void main(String[] args) {

    class User implements Iterable {
      String name;
      String email;
      String tel;
      boolean working;
      int age;

      @Override
      public Iterator iterator() {
        return new Iterator() {
          int count = 0;
          @Override
          public boolean hasNext() {
            return count < 5;
          }

          @Override
          public Object next() {
            switch (count++) {
              case 0: return name;
              case 1: return email;
              case 2: return tel;
              case 3: return working;
              case 4: return age;
            }
            return null;
          }
        };
      }
    }

    User user = new User();
    user.name = "홍길동";
    user.email = "hong@test.com";
    user.tel = "010-1111-2222";
    user.working = false;
    user.age = 20;

    // User 클래스는 Iterable 구현체가 아니다!!
    for (Object value : user) {
      System.out.println(value);
    }

    // 위의 반복문은 다음 같은 문장으로 컴파일 된다.
    //    Iterator iterator = user.iterator();
    //    while (iterator.hasNext()) {
    //      Object value = iterator.next();
    //      System.out.println(value);
    //    }
  }
}




















