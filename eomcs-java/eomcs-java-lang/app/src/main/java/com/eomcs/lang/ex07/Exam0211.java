package com.eomcs.lang.ex07;

// # 메서드를 잘못 사용한 예
public class Exam0211 {

  static void hello() {
    System.out.println("안녕하세요!");
    System.out.println("이 메서드는 어떤 값도 리턴하지 않습니다.");
  }

  public static void main(String[] args) {

    // hello 메서드는 파라미터 변수가 없기 때문에 호출할 때 값을 넣으면
    //    hello(100);// 컴파일 오류!

    // hello 메서드는 값을 리턴하지 않기 때문에 변수로 값을 받으려 하면
    int i;
    //    i = hello(); // 컴파일 오류!

  }
}
