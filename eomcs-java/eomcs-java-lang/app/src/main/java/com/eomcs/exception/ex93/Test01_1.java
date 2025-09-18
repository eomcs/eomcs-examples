// 애플리케이션 예외의 종류: Exception 계열의 예외와 RuntimeException 계열의 예외
package com.eomcs.exception.ex93;

public class Test01_1 {

  public static void main(String[] args) {
    // 1) Exception 계열의 예외 
    // => try ~ catch 를 강요하는 예외 
    // => 예외처리를 하지 않으면 컴파일 오류가 발생한다.
    // => 단 Exception의 자식 클래스인 RuntimeException은 제외한다.
    // 
    
    // Exception 예외가 발생할 수 있는 메서드 호출하기
    //
    //divide(100, 2); // 컴파일 오류!
    
    // 이유?
    // divide() 메서드는 특정 상황(두 번째 값이 0일 때)에서 Exception 예외를 발생시킨다.
    // 메서드 시그너처에 그렇게 정의되어 있다.
    // Exception 계열의 예외가 발생할 수 있는 그런 메서드를 호출할 때는 
    // 반드시 try ~ catch ~로 예외처리를 해야 한다.

    // 다음과 같이 try ~ catch ~ 블록에서 호출하라!
    try {
      divide(100, 2);
    } catch (Exception e) {
      System.out.println("나누기 실행 중 오류 발생!");
    }
    
    System.out.println("실행 종료!");
  }
  
  static int divide(int a, int b) throws Exception {
    if (b == 0)
      throw new Exception("0으로 나눌 수 없습니다.");
    return a / b;
  }

}








