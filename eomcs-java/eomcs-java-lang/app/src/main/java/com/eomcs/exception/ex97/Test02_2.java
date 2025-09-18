// 예외 처리 연습 II - try ~ catch ~ 예외 처리
package com.eomcs.exception.ex97;

public class Test02_2 {

  public static void main(String[] args) {
    
    try {
      System.out.println(100 / 0);
    
    } catch (RuntimeException e) {
      // RuntimeException을 처리하고 싶다면 
      // Exception 예외와 마찬가지로 try ~ catch ~를 사용하라!
      System.out.println("나누기 오류입니다.");
      
      // 이런 RuntimeException 예외를 개발자가 조심해야한다.
      // 컴파일러가 알려주지 않기 때문에
      // 개발자가 스스로 판단하여 예외를 try ~ catch ~ 로 처리할 것인지 
      // 아니면 호출자에게 전달할 것인지 결정해야 한다.
    }
    
    System.out.println("종료!");
  }

}
