package com.eomcs.lang.ex04;

//# 로컬 변수와 블록 III
//
public class Exam0822 {
  public static void main(String[] args) {

    int a = 100;

    switch (a) {
      case 1:
        //switch 바깥 블록에 같은 이름의 변수가 있다.
        //        int a; //컴파일 오류! 

        // case 문에서 선언한 변수는 switch 문에 소속된다.
        int b;
        {
          // 다음과 명확하게 블록 안에 선언하면
          // 이 블록의 변수가 된다.
          int c; 
        }
        break;
      case 2:
        // case는 다른 블록으로 취급되지 않는다.
        // 위쪽 case에서 선언한 변수는 switch 문에 소속된다.
        // 따라서 다음과 같이 아래쪽 case에서 
        // 같은 이름의 변수를 선언할 수 없다.
        //        int b; // 컴파일 오류!
        int c; // OK!

        break;

      case 3:
        for (int i = 0; i < 100; i++) {
          // for의 바깥 블럭인 switch 문에 이미 b 변수가 있다. 
          //          int b; // 컴파일 오류!
        }
        break;
      default:
    }
  }
}

