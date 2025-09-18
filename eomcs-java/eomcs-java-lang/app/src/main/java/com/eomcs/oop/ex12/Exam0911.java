// List 와 forEach() - forEach() 사용 전 II
package com.eomcs.oop.ex12;

import java.util.ArrayList;

public class Exam0911 {

  public static void main(String[] args) {
    ArrayList<String> names = new ArrayList<>();
    names.add("홍길동");
    names.add("임꺽정");
    names.add("유관순");
    names.add("김구");
    names.add("안중근");

    for (String name : names) {
      System.out.println(name);
    }
  }
}