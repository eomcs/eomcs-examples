// 던지는 예외 받기 - try ~ catch ~
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0430 {

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

  public static void main(String[] args) {
    // 예외 처리 방법 2:
    // - try ~ catch 를 사용하여 코드 실행 중에 발생된 예외를 중간에 가로챈다.
    //
    try {
      // try 블록에는 예외가 발생할 수 있는 코드를 둔다.
      m(0);
      System.out.println("실행 성공!");
      // try 블록에 있는 코드를 실행하는 중에
      // 예외가 발생하면,
      // 그 예외 객체를 파라미터로 받을 수 있는
      // catch 문을 찾아 실행한다.
      //
    } catch (IOException e) {
      // catch 블록에서 그 예외를 받아서 처리한다.
      // 메서드가 던지는 예외 개수 만큼 catch 블록을 선언하면 된다.
      System.out.println("IOException 발생");

    } catch (SQLException e) {
      System.out.println("SQLException 발생");

    } catch (RuntimeException e) {
      System.out.println("RuntimeException 발생");

    } catch (Exception e) {
      System.out.println("기타 Exception 발생");
    }
  }

}
