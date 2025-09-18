package com.eomcs.lang.ex04;

//# 변수 사용 II
//- 다른 변수에 값을 저장할 때 사용할 수 있다.
//

public class Exam0320 {
  public static void main(String[] args) {
    
    int age1 = 20;
    
    //r-value 자리에 변수를 놓을 수 있다.
    int age2 = age1;
    
    //의미
    //- age1에 저장된 값을 age2에 복사하라!
    
    //age1과 age2는 같은 메모리가 아니다.
    //증명
    //- age1의 값을 변경한다.
    age1 = 30;
    
    System.out.println(age1);
    System.out.println(age2);
  }
}
