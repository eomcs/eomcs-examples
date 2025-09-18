package com.eomcs.jdbc.ex0.oracle.type4driver;

import com.eomcs.jdbc.ex0.api.Statement;

public class OracleType4Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 개발자가 이 메서드를 호출하면 
    // 네트워크를 통해 오라클 DBMS에 직접 SQL을 보낸다.
    return 0;
  }

}
