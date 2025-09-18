package com.eomcs.jdbc.ex0.oracle.type2driver;

import com.eomcs.jdbc.ex0.api.Statement;

public class OracleType2Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 자바 개발자가 이 메서드를 호출하면
    // 로컬에 설치된 C/C++ Oracle Vendor API의 함수를 호출한다.
    return 0;
  }

}
