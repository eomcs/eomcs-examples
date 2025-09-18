// auto commit 의 문제점
package com.eomcs.jdbc.ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    String title = null;
    String contents = null;
    ArrayList<String> files = new ArrayList<>();

    try (Scanner keyScan = new Scanner(System.in)) {

      System.out.print("제목? ");
      title = keyScan.nextLine();

      System.out.print("내용? ");
      contents = keyScan.nextLine();

      while (true) {
        System.out.print("첨부파일:(완료는 그냥 엔터!) ");
        String filename = keyScan.nextLine();
        if (filename.length() == 0) {
          break;
        }
        files.add(filename);
      }
    }

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");

        // => 게시글을 입력할 때 자동 생성된 PK 값을 받겠다고 설정한다.
        PreparedStatement boardStmt = con.prepareStatement(
            "insert into x_board(title,contents) values(?,?)",
            Statement.RETURN_GENERATED_KEYS);

        PreparedStatement fileStmt = con.prepareStatement(
            "insert into x_board_file(file_path,board_id) values(?,?)")) {

      // 게시글 입력
      boardStmt.setString(1, title);
      boardStmt.setString(2, contents);
      int count = boardStmt.executeUpdate();
      System.out.printf("%d 개 게시글 입력 성공!\n", count);

      // DriverManager가 리턴한 커넥션은
      // Auto Commit의 기본 상태가 true로 설정되어 있다.
      // Auto Commit?
      // => insert, update, delete을 실행한 후
      //    그 결과를 즉시 실제 테이블에 적용하는 것.

      // Auto commit의 문제점
      // - insert/update/delete 할 때 바로 실제 테이블에 적용하기 때문에
      //   다음에 수행하는 insert/update/delete 작업이 실패하더라도
      //   이전에 수행한 작업은 그대로 유효한 것이 문제다!
      // 왜?
      // - 여러 개의 insert/update/delete 작업이 한 작업 단위로
      //   묶이는 경우에는 이런 방식이 옳지 않다.
      // - 예를 들면, 제품을 주문하는 경우를 생각해보자.
      //   주문 정보를 주문 테이블에 입력한 후
      //   결제 정보를 결제 테이블에 입력할 때 실패한다면,
      //   이전에 입력한 주문 정보는 무효하게 된다.
      // - 이런 경우에는 결제 정보까지 완전하게 입력되어야만
      //   주문 정보도 유효한 것이 바람직하다.
      // - 물론 이어지는 작업에 상관없이 먼저 수행한 작업을
      //   실제 테이블에 적용해도 되는 경우에는
      //   무조건 실제 테이블에 적용하는 auto commit 이 맞다.
      //
      // 연속적으로 수행하는 여러 개 insert/update/delete 작업을
      // 한 단위의 작업으로 묶는 방법?
      // - 작업을 수행하기 전에 auto commit 을 false로 설정한다.
      // - 모든 작업이 끝났을 때 commit()을 요청한다.
      // - 중간에 한 작업이라도 실패한다면,
      //   rollback()을 요청하여 작업 전 상태(마지막 commit 상태)로 되돌린다.
      // - 여러 개의 작업을 한 단위로 묶은 것을
      //   "트랜잭션(transaction)" 이라 부른다.
      //
      // "트랜잭션"을 다룰 때 auto commit 을 수동 상태로 만든다.
      // => 여러 개의 작업을 한 단위로 다룰 때 auto commit 을 수동 상태로 만든다.

      ResultSet keyRS = boardStmt.getGeneratedKeys();
      keyRS.next(); // PK가 들어있는 레코드를 한 개 가져온다.
      int boardId = keyRS.getInt(1); // 레코드에서 컬럼 값을 꺼낸다.

      // 첨부파일 입력
      int fileCount = 0;
      for (String filename : files) {
        fileStmt.setString(1, filename);

        // 위에서 게시글 입력 후에 자동 생성된 PK 값을 사용한다.
        fileStmt.setInt(2, boardId);

        fileStmt.executeUpdate();
        fileCount++;
      }
      System.out.printf("%d 개 첨부파일 입력 성공!\n", fileCount);
    }
  }
}


