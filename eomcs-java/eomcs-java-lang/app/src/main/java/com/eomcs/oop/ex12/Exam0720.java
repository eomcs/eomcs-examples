// 메서드 레퍼런스 - 생성자 레퍼런스 구현 원리
package com.eomcs.oop.ex12;

import java.util.ArrayList;
import java.util.List;

public class Exam0720 {

  static interface ListFactory {
    List create();
  }

  public static void main(String[] args) {

    // 생성자 레퍼런스를 지정하는 것은
    // 다음과 같이 익명 클래스를 만드는 것과 같다.
    // => ListFactory f1 = ArrayList::new;
    // => ListFactory f1 = () -> new ArrayList();

    ListFactory f1 = new ListFactory() {
      @Override
      public List create() {
        return new ArrayList();
      }
    };

    List list = f1.create();

    System.out.println(list instanceof ArrayList);
    System.out.println(list.getClass().getName());
  }
}


