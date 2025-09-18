// insert 한 후 auto increment PK 값 리턴 받기
package com.eomcs.jdbc.ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Exam0111 {

  public static void main(String[] args) throws Exception {
    String title = null;
    String contents = null;

    try (Scanner keyScan = new Scanner(System.in)) {

      // 사용자로부터 제목, 내용을 입력 받는다.
      System.out.print("제목? ");
      title = keyScan.nextLine();

      System.out.print("내용? ");
      contents = keyScan.nextLine();
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");

        // 입력 후 PK 값을 리턴 받고 싶다면,
        // PreparedStatement 객체를 얻을 때 다음과 같은 옵션을 지정하라!
        // => prepareStatement(sql, 자동생성된 PK 값 리턴 여부)
        //
        PreparedStatement stmt = con.prepareStatement(
            "insert into x_board(title,contents) values(?,?)",
            Statement.RETURN_GENERATED_KEYS);) {

      stmt.setString(1, title);
      stmt.setString(2, contents);
      int count = stmt.executeUpdate();
      System.out.printf("%d 개 입력 성공!\n", count);

      // insert 수행 후 자동 생성된 PK 값은 따로 요구해야 한다.
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        // insert를 한 개만 했기 때문에 PK도 한 개만 생성되었다.
        // 따라서 ResultSet에 대해 여러 번 반복을 할 필요가 없다.
        rs.next();

        // 자동 생성된 PK 값을 꺼낼 때는 컬럼 이름이나 PK 컬럼의 인덱스로 꺼낸다.
        //        int no = rs.getInt("board_id"); // MariaDB와 MySQL JDBC Driver에서는 오류 발생!
        int no = rs.getInt(1);
        System.out.printf("입력된 게시글 번호: %d\n", no);
      }
    }
  }
}


