package com.eomcs.lang.ex04;

//# 논리값 변수 - 변수의 메모리 크기
//
public class Exam0441 {
  public static void main(String[] args) {

    // 논리 값을 담을 변수는 JVM 내부에서 4바이트 크기의 int 타입 메모리를 사용한다.  
    boolean b1, b2;
    
    b1 = true; // 실제 메모리에는 1을 저장한다.
    b2 = false; // 실제 메모리에는 0을 저장한다.

    System.out.println(b1);
    System.out.println(b2);

    // 주의!
    // - JVM 내부에서 true, false를 정수 값으로 다룬다고 해서 boolean 변수에 직접 1과 0을 저장할 수 없다.
    //b1 = 1; // 컴파일 오류!
    //b2 = 0; // 컴파일 오류!
  }
}
