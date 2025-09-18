// 던지는 예외 받기 - Throwable 변수로 예외를 받지 말라!
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0471 {

  static void m(int i) throws Exception, RuntimeException, SQLException, IOException {
    if (i == 0)
      throw new Exception();
    else if (i == 1)
      throw new RuntimeException();
    else if (i == 2)
      throw new SQLException();
    else if (i == 3)
      throw new IOException();
    else if (i < 0)
      throw new Error(); // 시스템 오류가 발생하다고 가정하자!
  }

  public static void main(String[] args) {
    try {
      // try 블록에서 예외가 발생할 수 있는 메서드를 호출한다.
      m(-1);

    } catch (Throwable e) {
      System.out.println("애플리케이션 예외 발생!");
      // catch 문을 작성할 때
      // 이처럼 무심코 Throwable 변수로 선언하면
      // 시스템 오류인 Error 까지 받기 때문에
      // JVM에서 발생된 오류에 대해서도 예외 처리를 하는 문제가 발생한다.
      // 시스템 오류는 애플리케이션에서 처리할 수 없다.
      // 따라서 실무에서는 예외를 받을 때
      // Throwable 변수를 사용하지 않는다.
    }
  }

}
