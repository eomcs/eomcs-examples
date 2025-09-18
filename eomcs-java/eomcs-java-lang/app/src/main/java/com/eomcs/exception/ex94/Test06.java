// 예외 던지기 - RuntimeException 예외 던지기
package com.eomcs.exception.ex94;

public class Test06 {
  
  public static void main(String[] args) {
    // main()에서 RuntimeException 예외를 처리하지 않으면 
    // 당연히 그 상위 호출자에게 전달된다. 즉 JVM에게 전달된다.
    // 
    // RuntimeException은 Exception에 비해 다루기가 편하다.
    // 각 메서드마다 throws 절을 작성할 필요가 없다.
    //
    m3();
    System.out.println("종료!");
  }
  
  static void m3() {
    // RuntimeException 예외를 발생시키는 메서드는 
    // 메서드 선언부에 어떤 예외를 던지는지 선언(보고)하지 않아도 된다.
    // try ~ catch로 처리하지 않아도 된다.
    m2();
  }
  
  static void m2() {
    // RuntimeException 예외를 발생시키는 메서드는 
    // 메서드 선언부에 어떤 예외를 던지는지 선언(보고)하지 않아도 된다.
    // try ~ catch로 처리하지 않아도 된다.
    m1();
  }
  
  static void m1() {
    // RuntimeException 예외를 발생시키는 메서드는 
    // 메서드 선언부에 어떤 예외를 던지는지 선언(보고)하지 않아도 된다.
    throw new RuntimeException("m1()에서 발생한 예외");
  }
}






