// 예외 처리 연습 II - 호출자에게 전달하기
package com.eomcs.exception.ex97;

public class Test02_3 {

  public static void main(String[] args) throws RuntimeException {
    
    System.out.println(100 / 0);
    // RuntimeException 예외가 발생되는 코드를 작성할 경우 
    // 메서드 선언부에 굳이 throws 를 포함할 필요는 없다.
    // 그런 불편함을 없애기 위해 만든 예외가 RuntimeException이 아닌가!
  }

}
