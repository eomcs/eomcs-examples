package com.eomcs.jdbc.ex0.jre.driver;

import com.eomcs.jdbc.ex0.api.Statement;

public class Type1Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 자바 개발자가 이 메서드를 호출하면 
    // 내부적으로 C/C++ 로 만든 ODBC Driver의 함수를 호출한다.
    return 0;
  }

}
