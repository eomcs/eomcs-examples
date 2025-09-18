package com.eomcs.jdbc.ex0.microsoft.typ2;

import com.eomcs.jdbc.ex0.api.Statement;

public class MSType2Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 개발자 이 메서드를 호출하면
    // 로컬에 설치된 C/C++ MS-SQL Vendor API 를 호출한다.
    return 0;
  }

}
