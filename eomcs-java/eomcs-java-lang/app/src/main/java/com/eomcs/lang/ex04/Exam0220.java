package com.eomcs.lang.ex04;

//# 변수 선언 오류
//
public class Exam0220 {
  
  public static void main(String[] args) {
    
    //변수 선언 보다 변수 사용이 먼저 올 수 없다.
    count = 50; // 문법 오류!
    
    int count;
    
    //항상 변수가 선언된 다음에 사용해야 한다.
    count = 50; // 옳다!
  }
}
