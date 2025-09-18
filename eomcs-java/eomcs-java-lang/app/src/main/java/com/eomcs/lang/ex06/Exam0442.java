package com.eomcs.lang.ex06;

// # 흐름 제어문 - for(;;) 와 배열
//
public class Exam0442 {
  public static void main(String[] args) {
    String[] names = {"홍길동", "임꺽정", "유관순", "윤봉길", "안중근"};

    // 증가치 조정
    for (int i = 0; i < names.length; i += 2) {
      System.out.println(names[i]);
    }

  }
}
