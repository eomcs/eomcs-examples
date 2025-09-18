// 예외 던지기 - Exception 예외 던지기 II
package com.eomcs.exception.ex94;

public class Test05 {
  
  public static void main(String[] args) {
    // 가능한 main()에서라도 예외를 받아 처리해야 한다.
    // JVM에게 그냥 전달하지 말라!
    // => 예외가 발생하더라도 프로그램을 정상 실행하려면 반드시 
    //    main()에서라도 예외를 처리해야 한다.
    try {
      m3();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    System.out.println("종료!");
  }
  
  static void m3() throws Exception {
    // Exception 예외를 발생시키는 메서드를 호출할 때는 
    // 1) try ~ catch로 예외를 처리하거나
    // 2) throws 절을 사용하여 어떤 예외가 발생하는지 선언(보고)해야 한다.
    m2();
  }
  
  static void m2() throws Exception {
    // Exception 예외를 발생시키는 메서드를 호출할 때는 
    // 1) try ~ catch로 예외를 처리하거나
    // 2) throws 절을 사용하여 어떤 예외가 발생하는지 선언(보고)해야 한다.
    m1();
  }
  
  static void m1() throws Exception {
    // Exception 예외를 발생시키는 메서드는 
    // 반드시 메서드 선언부에 어떤 예외를 던지는지 선언(보고)해야 한다.
    throw new Exception("m1()에서 발생한 예외");
  }
}






