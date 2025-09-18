// 애플리케이션 예외의 종류: Exception 계열의 예외 처리 II
package com.eomcs.exception.ex93;

public class Test03_1 {

  public static void main(String[] args) throws Exception { // main()의 호출자? JVM이다.
    // Exception 계열의 예외 처리 방법
    // 1) try ~ catch로 예외 받기
    //    try {
    //      예외 발생 코드
    //    } catch (예외 파라미터) {
    //      예외처리 코드
    //    }
    // 2) 호출자에게 예외 처리를 떠넘기기
    //    void 메서드() throws 예외클래스명, 예외클래스명, ... {
    //      예외가 발생할 수 있는 코드 
    //    }
    // 
    
    // '방법2' 적용
    // => 메서드 선언부에 throws 절 추가하기
    // => 이 방식을 사용해야 하는 경우? 
    //    이런 예외를 한 곳에서 처리하고 싶을 때 이 방식을 사용한다.
    // => "어이~~ 여기에 예외 처리하지 마! 호출자에게 전달해." 라는 의미! 
    
    int result = divide(100, 0);
    System.out.println(result);
    
  }
  
  static int divide(int a, int b) throws Exception {
    if (b == 0)
      throw new Exception("0으로 나눌 수 없습니다.");
    return a / b;
  }

}
