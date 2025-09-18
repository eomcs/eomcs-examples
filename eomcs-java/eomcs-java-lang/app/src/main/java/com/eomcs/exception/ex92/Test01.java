// 예외 처리하기 - try ~ catch ~ finally 
package com.eomcs.exception.ex92;

import java.util.Scanner;

public class Test01 {
  public static void main(String[] args) {
    
    Scanner keyboard = new Scanner(System.in);
    System.out.print("숫자를 입력하세요: ");
    String input = keyboard.nextLine();
    
    try {
      // 예외가 발생할 수 있는 코드를 두는 블록 
      int value = Integer.parseInt(input);
      System.out.println(value % 2 == 0 ? "짝수입니다." : "홀수입니다.");
      return; // main() 메서드를 나가기 전에 finally 블록이 실행된다.
      
    } catch (Exception e) {
      // 예외가 발생했을 때 실행되는 블록
      System.out.println("입력 값이 유효하지 않습니다.\n정수 값을 입력하세요!");
      return; // main() 메서드를 나가기 전에 finally 블록이 실행된다.
      
    } finally {
      // 정상적으로 실행하든 예외가 발생되든 무조건 try ~ catch 블록을 벗어나기 전에 실행되는 블록
      // => 필수 블록이 아니다. 생략해도 된다.
      System.out.println("종료합니다!");
      keyboard.close();
    }
    
    
  }
}
