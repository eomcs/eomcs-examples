// 던지는 예외 받기 - catch 블록의 순서
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0440 {

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
    try {
      // try 블록에서 예외가 발생할 수 있는 메서드를 호출한다.
      m(1);

    } catch (Exception e) {
      // 여러 개의 예외를 받을 때 수퍼 클래스 변수로 먼저 받지 말라!
      // 그러면 그 클래스의 모든 서브 클래스 객체도 다 받게 된다.
      // 즉 서브 클래스의 변수에서 받을 기회조차 없다.
      // => 예외 객체를 정확하게 받고 싶다면 Exam0430.java 처럼
      //    서브 클래스 예외부터 받아라.
      //
    } catch (IOException e) {

    } catch (SQLException e) {

    } catch (RuntimeException e) {

    }
  }

}
