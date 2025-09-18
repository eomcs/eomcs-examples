package com.eomcs.lang.ex06;

// # 흐름 제어문 - for(:) enhanced for 문 - 배열
//
public class Exam0451 {
  public static void main(String[] args) {
    String[] names = {"홍길동", "임꺽정", "유관순", "윤봉길", "안중근"};

    // for (:) 문을 사용할 경우
    // 변수 선언을 for(:) 문장 바깥에 둘 수 없다.
    String name;
    for (name : names) // 컴파일 오류!
      System.out.println(name);
  }
}
