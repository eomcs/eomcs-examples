package com.eomcs.cleancode.ch07.exam02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

// 예제 3: 구조를 먼저 만든 후 구현
// "Write Your Try-Catch-Finally Statement First"의 핵심:
// 틀(구조)을 먼저 잡고, 그 안에 구현을 채워 넣는다.
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 기능만 먼저 작성하고 나중에 예외 처리를 붙인다.
  // - 오류 처리를 억지로 끼워 넣으면 구조가 깨진다.
  // - 리소스 해제가 보장되지 않는다.
  // - 정상 흐름과 오류 흐름이 뒤섞인다.
  static class BadProcessor {
    public void process(Connection conn) throws SQLException, IOException {
      // 기능부터 작성
      conn.setAutoCommit(false);
      doSomething();
      conn.commit();
      // 나중에 오류 처리를 끼워 넣으면 구조가 무너진다
    }

    private void doSomething() throws IOException {
      System.out.println("비즈니스 로직 실행");
    }
  }

  // Good: try-catch-finally 구조를 먼저 설계하고 그 안에 구현을 채운다.
  // - 정상 흐름 / 오류 처리 / 정리 작업이 처음부터 분리된다.
  // - 각 역할이 별도 메서드로 분리되어 단일 책임을 갖는다.
  // - 코드가 역할별로 완전히 분리되어 테스트가 쉬워진다.
  static class GoodProcessor {
    public void process() {
      try {
        doSomething();
      } catch (Exception e) {
        handleError(e);
      } finally {
        cleanup();
      }
    }

    private void doSomething() throws IOException {
      System.out.println("비즈니스 로직 실행");
    }

    private void handleError(Exception e) {
      System.out.println("오류 처리: " + e.getMessage());
    }

    private void cleanup() {
      System.out.println("리소스 정리");
    }
  }

  // 실무 예: DB 트랜잭션에 구조를 먼저 적용한다.
  // - try: 트랜잭션 시작 + 비즈니스 로직
  // - catch: 롤백
  // - finally: 커넥션 반환
  static class GoodTransactionProcessor {
    public void process(Connection conn) {
      try {
        beginTransaction(conn);
        doBusinessLogic(conn);
        commit(conn);
      } catch (Exception e) {
        rollback(conn, e);
      } finally {
        close(conn);
      }
    }

    private void beginTransaction(Connection conn) throws SQLException {
      conn.setAutoCommit(false);
    }

    private void doBusinessLogic(Connection conn) throws SQLException {
      System.out.println("비즈니스 로직 실행 (conn=" + conn + ")");
    }

    private void commit(Connection conn) throws SQLException {
      conn.commit();
    }

    private void rollback(Connection conn, Exception e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        System.out.println("롤백 실패: " + ex.getMessage());
      }
      System.out.println("오류 처리: " + e.getMessage());
    }

    private void close(Connection conn) {
      try {
        conn.close();
      } catch (SQLException e) {
        System.out.println("커넥션 반환 실패: " + e.getMessage());
      }
    }
  }
}
