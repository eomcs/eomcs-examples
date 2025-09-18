// JDBC 프로그래밍 - DBMS에 SQL문 보내기 : select
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0341 {

  public static void main(String[] args) throws Exception {
    try (
        java.sql.Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        java.sql.Statement stmt = con.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery("select * from x_board order by board_id desc");

        ) {

      while (rs.next()) {
        // 컬럼 타입에 상관없이 getString()으로 값을 꺼낼 수 있다.
        System.out.printf("%s, %s, %s, %s, %s\n", //
            rs.getString("board_id"), //
            rs.getString("title"), //
            rs.getString("contents"), //
            rs.getString("created_date"), // 시간까지 꺼낸다.
            rs.getString("view_count")); //
      }
    }
  }
}


