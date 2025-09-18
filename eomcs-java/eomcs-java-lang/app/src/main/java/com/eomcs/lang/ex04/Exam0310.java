package com.eomcs.lang.ex04;

//# 변수 사용
//- 다른 도구(메서드; 함수라고도 부른다)에 값을 전달 할 수 있다.
//

public class Exam0310 {
  public static void main(String[] args) {
    
    int age = 20;
    
    //println() 메서드(도구)
    //- 콘솔(console)로 값을 출력할 때 사용하는 도구이다.
    //- 즉 JVM의 표준 출력 장치로 값을 출력한다.
    //- 사용법
    //    System.out.println(리터럴 또는 변수, 결과가 생성되는 명령문)
    //
    System.out.println(age); 
    // println()을 실행할 때 age 변수를 넘기는 것이 아니라
    // age 변수에 들어있는 값을 넘기는 것이다!!!!
  }
}
