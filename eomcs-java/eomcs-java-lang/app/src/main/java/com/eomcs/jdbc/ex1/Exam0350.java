// JDBC 프로그래밍 - DBMS에 SQL문 보내기 : update
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0350 {

  public static void main(String[] args) throws Exception {
    try (
        java.sql.Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        java.sql.Statement stmt = con.createStatement();
        ) {

      // executeUpdate()
      // => DBMS 서버에 update 문을 보낸다.
      // => 리턴 값: 변경된 레코드의 개수이다.
      int count = stmt.executeUpdate(
          "update x_board set"
              + " view_count = view_count + 1"
              + " where board_id = 4");
      System.out.printf("%d 개 변경 성공!", count);
    }
  }
}


