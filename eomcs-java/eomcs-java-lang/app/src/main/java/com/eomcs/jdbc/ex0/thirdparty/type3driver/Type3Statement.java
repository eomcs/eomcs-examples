package com.eomcs.jdbc.ex0.thirdparty.type3driver;

import com.eomcs.jdbc.ex0.api.Statement;

public class Type3Statement implements Statement {

  @Override
  public int executeUpdate(String sql) throws Exception {
    // 자바 개발자가 이 메서드를 호출하면 
    // 원격의 중계서버에 SQL을 보내는 일을 한다.
    return 0;
  }

}
