package com.eomcs.lang.ex06;

import java.util.ArrayList;

// # 흐름 제어문 - for(:) enhanced for 문 - 컬렉션
//
public class Exam0471 {
  public static void main(String[] args) {
    // 제네릭 적용
    ArrayList<String> list = new ArrayList<>();
    list.add("홍길동");
    list.add("임꺽정");
    list.add("유관순");
    list.add("안중근");
    list.add("윤봉길");
    list.add("김원봉");
    list.add("김구");

    // 제네릭을 사용하면 for(:) 문장에서
    // 항목의 타입을 정확하게 지정하여 값을 받을 수 있다.
    for (String name : list) {
      System.out.print(name + " ");
    }
  }
}


