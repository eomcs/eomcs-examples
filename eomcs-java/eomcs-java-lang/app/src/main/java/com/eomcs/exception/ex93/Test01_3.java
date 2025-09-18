// 애플리케이션 예외의 종류: main() 과 RuntimeException
package com.eomcs.exception.ex93;

import java.util.Scanner;

public class Test01_3 {

  public static void main(String[] args) {

    // RuntimeException을 발생시키는 메서드를 호출할 때 
    // 예외 처리 코드를 작성하지 않더라도 
    // 컴파일러는 에러를 띄우지 않는다.
    // 
    // 하지만 RuntimeException 예외를 처리하지 않으면 
    // 최종적으로 JVM에게 전달되고, 즉시 프로그램을 종료하기 때문에
    // 프로그램을 종료하기 싫으면 RuntimeException 에 대해서도 
    // 예외 처리를 하라!
    //
    
    Scanner keyScan = new Scanner(System.in);
    
    while (true) {
      try {
        // 아래의 코드에서 예외가 발생한다면 RuntimeException 예외이다.
        // 따라서 try ~ catch ~ 블록을 사용하지 않아도 컴파일러가 에러를 띄우지는 않는다.
        // 그러나 예외는 예외이기 때문에 발생하는 순간 호출자에게 전달한다.
        // main()에서 발생된 예외는 JVM에게 바로 전달되기 때문에 
        // 즉시 실행을 멈출 것이다. 
        // 그래서 예외처리가 강제 사항이 아니라 할지라도 
        // 이렇게 try ~ catch ~로 처리한 것이다.
        
        System.out.print("값1? ");
        int a = Integer.parseInt(keyScan.nextLine());
        
        System.out.print("값2? ");
        int b = Integer.parseInt(keyScan.nextLine());
        
        System.out.println(divide(a, b));
      } catch (RuntimeException e) {
        System.out.println("입력 또는 계산 중에 오류 발생!");
      }
    }
    
  }
  
  static int divide(int a, int b) throws RuntimeException {
    if (b == 0)
      throw new RuntimeException("0으로 나눌 수 없습니다.");
    return a / b;
  }

}









