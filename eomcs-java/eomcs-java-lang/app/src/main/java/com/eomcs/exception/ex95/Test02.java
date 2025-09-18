// 예외 정보 출력하기
package com.eomcs.exception.ex95;

import java.util.Scanner;

public class Test02 {
  
  public static void main(String[] args) throws Exception {
    Scanner keyboard = null;
    
    try {
      keyboard = new Scanner(System.in);
      System.out.print("값? ");
      int a = Integer.parseInt(keyboard.nextLine());
      
      int result = sum(a);
      System.out.println(result);
      
    } catch (Exception e) {
      // 예외가 발생한 메서드의 위치 정보를 콘솔에 자세하게 출력한다.
      e.printStackTrace();
      
    } finally {
      keyboard.close();
    }
    
  }
  
  static int sum(int value) { // f(n) = n + f(n-1)
    if (value == 1)
      return 1;
    return value + sum(value - 1);
  }
  
 
}






