// 게시판 관리 - 등록 + PreparedStatement 사용
package com.eomcs.jdbc.ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exam0310 {

  public static void main(String[] args) throws Exception {
    String title = null;
    String contents = null;

    try (Scanner keyScan = new Scanner(System.in)) {

      // 사용자로부터 제목, 내용을 입력 받는다.
      System.out.print("제목? ");
      title = keyScan.nextLine();

      System.out.print("내용? ");
      contents = keyScan.nextLine();

      System.out.print("입력하시겠습니까?(Y/n) ");
      String input = keyScan.nextLine();

      if (!input.equalsIgnoreCase("y") && input.length() != 0) {
        System.out.println("등록을 취소 하였습니다.");
        return;
      }
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");

        // 값이 들어갈 자리에 in-parameter(?)를 지정한다.
        // => 데이터 타입에 상관없이 ?를 넣는다.
        PreparedStatement stmt =
            con.prepareStatement("insert into x_board(title,contents) values(?,?)");) {

      // in-parameter에 값을 설정한다.
      // => 설정하는 순서는 상관없다. 하지만 유지보수를 위해 순서대로 나열하라!
      stmt.setString(1, title);
      stmt.setString(2, contents);

      // 실행할 때는 SQL문을 파라미터로 넘길 필요가 없다.
      int count = stmt.executeUpdate();
      System.out.printf("%d 개 입력 성공!", count);
    }
  }
}


