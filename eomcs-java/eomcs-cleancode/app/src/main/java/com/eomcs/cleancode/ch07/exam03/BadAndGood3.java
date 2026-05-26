package com.eomcs.cleancode.ch07.exam03;

import java.sql.SQLException;

// 예제 3: 예외 전파 오류 — 저수준 예외를 도메인 예외로 변환하라
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 저수준 예외(SQLException)가 상위 레이어까지 그대로 전파된다.
  // - Service, Controller가 모두 SQLException에 의존한다.
  // - 계층 간 결합도가 높아진다.
  // - DB를 다른 기술로 교체하면 모든 레이어를 수정해야 한다.
  static class BadRepository {
    public void save() throws SQLException {
      throw new SQLException("DB 오류");
    }
  }

  static class BadService {
    private BadRepository repository = new BadRepository();

    public void execute() throws SQLException {
      repository.save(); // SQLException을 그대로 위로 전파한다
    }
  }

  static class BadController {
    private BadService service = new BadService();

    public void handle() {
      try {
        service.execute();
      } catch (SQLException e) {
        // 최상위까지 전파된 저수준 예외를 처리한다
        e.printStackTrace();
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 저수준 예외를 도메인 수준의 Unchecked Exception으로 변환한다.
  static class DataAccessException extends RuntimeException {
    DataAccessException(String message, Throwable cause) { super(message, cause); }
  }

  // Good: Repository 계층에서 SQLException을 잡아 DataAccessException으로 변환한다.
  // - 상위 레이어는 SQLException을 전혀 알 필요가 없다.
  // - 계층 간 결합도가 낮아진다.
  // - DB 기술이 바뀌어도 Service, Controller는 변경되지 않는다.
  static class GoodRepository {
    public void save() {
      try {
        throw new SQLException("DB 오류");
      } catch (SQLException e) {
        throw new DataAccessException("DB 처리 실패", e); // 저수준 예외를 도메인 예외로 변환
      }
    }
  }

  static class GoodService {
    private GoodRepository repository = new GoodRepository();

    public void execute() {
      repository.save(); // throws 선언 없이 깔끔하다
    }
  }

  static class GoodController {
    private GoodService service = new GoodService();

    public void handle() {
      try {
        service.execute();
      } catch (DataAccessException e) {
        // 도메인 수준 예외만 알면 된다
        System.out.println("서비스 오류 처리: " + e.getMessage());
      }
    }
  }
}
