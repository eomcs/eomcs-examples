// DB 커넥션 객체를 관리하는 역할의 규칙 정의
// => 빌려주는 역할을 수행한다.
package com.eomcs.jdbc.ex5;

import java.sql.Connection;

public interface DataSource {
  Connection getConnection() throws Exception;
  void returnConnection(Connection con);
}




