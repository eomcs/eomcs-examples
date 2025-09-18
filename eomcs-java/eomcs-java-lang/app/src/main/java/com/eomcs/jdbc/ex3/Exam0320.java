// 게시물 관리 - 목록 + PreparedStatement
package com.eomcs.jdbc.ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Exam0320 {

  public static void main(String[] args) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        // 당장 select 할 때 파라미터 값을 넣지 않는다 하더라도,
        // 나중에 넣을 것을 대비해서 그냥 PreparedStatement를 사용하라!
        PreparedStatement stmt = con.prepareStatement(
            "select * from x_board order by board_id desc");
        ResultSet rs = stmt.executeQuery()) {

      System.out.println("번호, 제목, 등록일, 조회수");
      while (rs.next()) {
        System.out.printf("%d, %s, %s, %s, %d\n", //
            rs.getInt("board_id"), //
            rs.getString("title"), //
            rs.getString("contents"), //
            rs.getDate("created_date"), //
            rs.getInt("view_count"));
      }
    }
  }
}


