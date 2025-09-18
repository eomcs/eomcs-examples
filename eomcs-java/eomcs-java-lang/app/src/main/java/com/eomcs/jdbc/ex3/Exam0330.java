// 게시물 관리 - 조회 + PreparedStatement 적용
package com.eomcs.jdbc.ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Exam0330 {

  public static void main(String[] args) throws Exception {
    String no = null;
    try (Scanner keyScan = new Scanner(System.in)) {
      System.out.print("번호? ");
      no = keyScan.nextLine();
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from x_board where board_id = ?")) {

      stmt.setString(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          System.out.printf("제목: %s\n", rs.getString("title"));
          System.out.printf("내용: %s\n", rs.getString("contents"));
          System.out.printf("등록일: %s\n", rs.getInt("board_id"));
          System.out.printf("조회수: %d\n", rs.getInt("view_count"));
        } else {
          System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
        }
      }
    }
  }
}


