package com.eomcs.lang.ex06;

import java.util.ArrayList;

// # 흐름 제어문 - for(:) enhanced for 문 - 컬렉션
//
public class Exam0460 {
  public static void main(String[] args) {
    // 배열은 같은 타입의 값만 보관할 수 있지만
    // 컬렉션은 다른 타입의 값도 보관할 수 있다.
    //
    // 배열은 크기가 고정되지만,
    // 컬렉션은 크기가 가변적이다.
    ArrayList list = new ArrayList();
    list.add("홍길동");
    list.add(3.14f);
    list.add(true);
    list.add(365);

    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }
}


