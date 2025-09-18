package com.eomcs.lang.ex06;

import java.util.ArrayList;

// # 흐름 제어문 - for(:) enhanced for 문 - 컬렉션
//
public class Exam0470 {
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

    // String 이 아닌 값은 넣을 수 없다.
    // list.add(3.14f); // 컴파일 오류
    // list.add(true); // 컴파일 오류
    // list.add(365); // 컴파일 오류

    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }
}


