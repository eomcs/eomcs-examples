// 던지는 예외 받기 - Throwable 변수로 예외를 받지 말라!
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0472 {

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
      m(-1);

    } catch (Exception e) {
      System.out.println("애플리케이션 예외 발생!");
      // 이렇게 Exception 변수를 사용하면
      // 애플리케이션 예외를 처리하고,
      // 시스템 예외는 main() 호출자에게 위임하게 된다.
      // 즉 JVM에게 전달한다.
      // 이렇게 Exception 계열의 애플리케이션 예외만 처리하라.
    }
  }

}
