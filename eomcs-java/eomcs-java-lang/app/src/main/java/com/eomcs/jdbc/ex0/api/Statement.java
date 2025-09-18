package com.eomcs.jdbc.ex0.api;

// JDBC API
// - 개발자가 DBMS에 SQL을 보내기 위해 호출하는 메서드의 시그너처를 정의한 것. 
// 
public interface Statement {
  int executeUpdate(String sql) throws Exception;
}
