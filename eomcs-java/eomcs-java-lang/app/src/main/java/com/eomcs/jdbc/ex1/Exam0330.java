// JDBC 프로그래밍 - DBMS에 SQL문 보내기 : select
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0330 {

  public static void main(String[] args) throws Exception {
    try (
        java.sql.Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        java.sql.Statement stmt = con.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(//
            "select * from x_board order by board_id desc");
        ) {

      boolean isReceived = rs.next();

      if (isReceived) {
        System.out.printf("%d, %s, %s, %s, %d\n",
            rs.getInt("board_id"),
            rs.getString("title"),
            rs.getString("contents"),
            rs.getDate("created_date"),
            rs.getInt("view_count"));

        // getXxx(컬럼명):
        // => 컬럼의 번호를 지정하는 방식은 소스 코드를 읽을 때 매우 불편하다.
        // => 해당 번호가 어떤 컬럼을 가리키는지 알려면
        //    select 문을 살펴봐야 하는 번거로움이 있다.
        // => 그래서 실무에서는 가능한 번호 대신 컬럼의 이름을 사용한다.

      } else {
        System.out.println("서버에서 한 개의 레코드를 가져오지 못했다!");
      }
    }
  }
}


