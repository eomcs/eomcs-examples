// 예외 처리 후 마무리 작업 - finally 블록
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0520 {

  static void m(int i) throws Exception, RuntimeException, SQLException, IOException {
    if (i == 0)
      throw new Exception();
    else if (i == 1)
      throw new RuntimeException();
    else if (i == 2)
      throw new SQLException();
    else if (i == 3)
      throw new IOException();
  }

  public static void main(String[] args) throws Exception {
    try {
      System.out.println("try");
      m(0);
      System.out.println("try 블록 실행 완료!");
      // m()에서 발생된 예외는
      // try 블록에서 받지 않는다.
      // 따라서 main() 호출자에게 위임한다.
      // => 물론 main() 메서드 선언부에 위임할 예외의 종류를 표시해야 한다.
      //
    } finally {
      // try 블록을 나가기 전에 무조건 실행해야 할 작업이 있다면
      // catch 블록이 없어도 finally 블록만 사용할 수 있다.
      System.out.println("마무리 작업 실행!");
    }

    // 이렇게 catch 블록이 없는 try ~ finally ~ 블록을 작성하는 상황:
    // - 예외가 발생하면 그 처리는 호출자에게 맡긴다.
    // - 그러나 이 메서드를 호출하는 동안 사용한 자원은 이 메서드를 종료하기 전에 해제시킨다.
    //
  }

}
