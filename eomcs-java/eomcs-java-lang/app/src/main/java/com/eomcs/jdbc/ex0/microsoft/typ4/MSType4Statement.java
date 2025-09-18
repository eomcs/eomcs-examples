package com.eomcs.jdbc.ex0.microsoft.typ4;

import com.eomcs.jdbc.ex0.api.Statement;

public class MSType4Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 개발작 이 메서드를 호출하면 
    // 원격의 MS-SQL 서버에 SQL을 보낸다.
    return 0;
  }

}
