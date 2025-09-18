// 던지는 예외를 메서드에 선언하기
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0320 {

  // 공통 분모를 사용하여 퉁치는 방법
  // => 메서드에서 발생하는 예외의 공통 수퍼 클래스를 지정하여
  //    여러 개를 나열하지 않을 수 있다.
  // => 그러나 호출자에게 어떤 오류가 발생하는지 정확하게 알려주는 것이
  //    유지보수에 도움이 된다.
  //    따라서 가능한 그 메서드에서 발생하는 예외는 모두 나열하라!
  //
  static void m(int i) throws Exception {
    if (i == 0)
      throw new Exception();
    else if (i == 1)
      throw new RuntimeException();
    else if (i == 2)
      throw new SQLException();
    else
      throw new IOException();
  }

  public static void main(String[] args) {}

}
