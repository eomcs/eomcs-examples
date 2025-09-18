package com.eomcs.lang.ex04;

//# 변수의 종류
public class Exam0710 {

  int a; // 인스턴스 변수

  static int b; // 클래스 변수 == 스태틱 변수


  public static void main(String[] args/*로컬변수=파라미터*/) {

    int c; // 로컬 변수

  }
}

// 인스턴스 변수(instance variable)
// - new 명령을 사용하여 인스턴스를 생성할 때 준비되는 변수
//
// 클래스 변수(class variable = static variable)
// - 클래스가 로딩될 때 준비되는 변수
//
// 로컬 변수(local variable)
// - 블록을 실행할 때 준비되는 변수
//
// 파라미터(parameter)
// - 메서드의 아규먼트를 받는 로컬 변수이다.
//   예) 위의 코드에서 main()의 args 로컬 변수
//