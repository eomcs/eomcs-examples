package com.eomcs.lang.ex04;

//# 변수 사용 오류 II
//- 변수에 값을 저장하지 않은 상태에서 변수를 사용할 수 없다.
//

public class Exam0340 {
  public static void main(String[] args) {
    
    int age1;
    
    //값이 저장되지 않은 변수를 사용하면 문법 오류이다!
    int age2 = age1;
  }
}
