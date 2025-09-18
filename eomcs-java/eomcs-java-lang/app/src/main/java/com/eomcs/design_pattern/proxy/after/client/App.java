// 3단계 - 프록시 패턴을 적용하여 C/S(Client/Server) 계산기 애플리케이션 사용하기
// => 서버 개발자가 제공해준 프록시 객체를 사용하여 계산을 수행한다.
//    프록시 객체는 마치 자신이 실제 일을 하는 것처럼 행동한다.
//
package com.eomcs.design_pattern.proxy.after.client;

import java.util.Scanner;
import com.eomcs.design_pattern.proxy.after.server.Calculator;
import com.eomcs.design_pattern.proxy.after.server.CalculatorStub;

public class App {
  public static void main(String[] args) throws Exception {
    
    Scanner keyboard = new Scanner(System.in);
    
    // 클라이언트 개발자가 원격에 있는 객체를 사용하기 위해서
    // 원격 서버와 통신하는 코드를 프로토콜(데이터를 주고 받는 규칙)에 맞춰 직접 작성하였다.
    // 
    Calculator calc = new CalculatorStub();
    
    while (true) {
      System.out.print("계산식>(예: 100 + 200) ");
      String input = keyboard.nextLine();
      if (input.equalsIgnoreCase("quit"))
        break;
      
      String[] values = input.split(" ");
      try {
        int a = Integer.parseInt(values[0]);
        int b = Integer.parseInt(values[2]);
        String op = values[1];
        
        switch (op) {
          // 이렇게 Calculator를 로컬에서 사용하는 것처럼 
          // CalculatorStub 이라는 프록시를 통해 작업을 수행할 수 있다.
          // Calculator를 사용해야 하는 클라이언트 개발자는 
          // Calculator를 사용하기 위해 서버와 통신하는 코드를 작성할 필요가 없다.
          // 서버 개발자가 프록시 객체를 만들어 제공해 줄 것이다.
          // 
          case "+": System.out.println(calc.plus(a,b)); break;
          case "-": System.out.println(calc.minus(a,b)); break;
          default:
            System.out.println("해당 연산자를 지원하지 않습니다.");
        }

      } catch (Exception e) {
        System.out.println("식 또는 계산 오류: " + e.getMessage());
      }
    }
    keyboard.close();
  }
}





