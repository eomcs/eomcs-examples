// 트랜잭션 다루기 - commit & rollback
package com.eomcs.jdbc.ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Exam0220 {

  public static void main(String[] args) {
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

      // 여러 개의 데이터 변경 작업을 한 단위로 묶어 수행해야 한다면,
      // commit 할 때까지 실제 테이블에 적용하지 않도록
      // auto commit을 취소하고 수동 커밋 상태로 만든다.
      //
      // 트랜잭션(transaction)?
      // => 여러 개의 데이터 변경 작업을 한 단위로 묶은 것.
      // => 한 단위로 묶인 모든 작업이 성공 했을 때 그 작업 결과를 저장한다.
      // => 묶인 작업 중에 한 개라도 실패하면 이전에 성공한 모든 작업을 취소한다.
      //
      // 여러 작업을 트랜잭션으로 묶는 방법?
      // => autocommit을 수동으로 전환한다.
      // => 모든 작업이 성공했을 때 저장한다. - commit
      // => 한 작업이라도 실패하면 기존에 작업한 결과를 취소한다. - rollback
      //
      // 1) 트랜잭션 시작 - 커넥션 객체의 오토커밋을 false로 지정한다.
      // 이후부터 이 커넥션으로 실행하는 모든 SQL은
      // commit을 요청하기 전에는 테이블에 그 결과를 적용하지 않고
      // 임시 데이터베이스에 따로 보관한다.
      con.setAutoCommit(false);

      // 게시글 입력
      boardStmt.setString(1, title);
      boardStmt.setString(2, contents);
      int count = boardStmt.executeUpdate();
      System.out.printf("%d 개 게시글 입력 성공!\n", count);

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

      // 자식 테이블의 데이터까지 정상적으로 입력했다면,
      // DBMS에게 작업 결과를 실제 테이블에 적용하라고 요청한다.
      con.commit();
      // 데이터를 변경하는 모든 작업은(insert|update|delete)
      // 클라이언트의 요청을 처리하는 스레드가 임시 데이터베이스에 따로 보관한다.
      // 클라이언트와의 연결이 끊어지면 스레드는 임시 데이터베이스에 보관된 데이터를 버린다.
      // 따라서 연결을 끊기 전에 작업한 내용을 적용하고 싶다면,
      // 반드시 commit을 요청해야 한다.
      // 클라이언트로부터 commit 요청이 들어오면
      // 그 클라이언트와 연결된 스레드는 임시 데이터베이스에 보관된 데이터 변경 결과를
      // 실제 테이블에 적용한다.

    } catch (Exception e) {
      System.out.println("게시글 입력 중 오류 발생!");

      // 만약에 입력 도중에 실패했다면,
      // 현재까지 작업한 결과를 모두 취소하라고 DBMS에게 통보한다.
      // => commit()을 호출하지 않고 커넥션 객체를 닫으면,
      //    DBMS는 그 커넥션을 통해 수행했던 모든 작업 결과를 취소한다.
      // => 따라서 따로 커넥션 객체에 대해 rollback() 을 호출할 필요는 없다.
      //
      // => 만약 커넥션을 공유하는 상황이라면,
      //    명시적으로 작업 취소(rollback)를 명령해야 한다.
      // => 왜냐하면 같은 커넥션으로 다른 작업을 처리하는 경우에
      //    이전에 수행한 작업이 취소되지 않고 남아 있어서
      //    새 작업에 영향을 주기 때문이다.
      // 결론,
      // => 예외가 발생하면 rollback()을 명시적으로 호출하라!!!!!!
      //con.rollback();
    }
  }
}


