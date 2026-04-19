package com.eomcs.advanced.jpa.exam03;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// exam03 - 격리 수준(Isolation Level)
//
// 격리 수준이란?
// - 동시에 실행 중인 트랜잭션들이 서로를 얼마나 차단할지 결정하는 수준이다.
// - 격리 수준이 낮을수록 성능이 좋고, 높을수록 데이터 정합성이 보장된다.
//
// ANSI SQL 표준 격리 수준 (낮음 → 높음):
// ┌───────────────────────┬────────────┬──────────────┬──────────────────┐
// │ 격리 수준             │ Dirty Read │ Non-Repeatable│ Phantom Read    │
// ├───────────────────────┼────────────┼──────────────┼──────────────────┤
// │ READ_UNCOMMITTED (1)  │ 발생 가능  │ 발생 가능    │ 발생 가능        │
// │ READ_COMMITTED   (2)  │ 방지       │ 발생 가능    │ 발생 가능        │
// │ REPEATABLE_READ  (4)  │ 방지       │ 방지         │ 발생 가능        │
// │ SERIALIZABLE     (8)  │ 방지       │ 방지         │ 방지             │
// └───────────────────────┴────────────┴──────────────┴──────────────────┘
//
// 이상 현상:
//   Dirty Read        : 다른 트랜잭션이 commit하지 않은 변경을 읽는 것
//   Non-Repeatable Read: 같은 쿼리를 두 번 실행했을 때 결과가 달라지는 것
//   Phantom Read      : 같은 조건의 SELECT가 서로 다른 행 집합을 반환하는 것
//
// Oracle 특이 사항:
//   - Oracle은 READ_UNCOMMITTED를 지원하지 않는다 (READ_COMMITTED가 최소 수준).
//   - Oracle의 기본 격리 수준은 READ_COMMITTED이다.
//   - MVCC(Multi-Version Concurrency Control)로 읽기 잠금 없이 일관성을 보장한다.
//
// JDBC API:
//   conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//   conn.getTransactionIsolation(); → 현재 격리 수준 반환 (정수)
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam03.App3
//
public class App3 {

  static HikariDataSource dataSource;

  static {
    String host = System.getenv("DB_HOSTNAME");
    String port = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:oracle:thin:@//" + host + ":" + port + "/" + service);
    config.setUsername(System.getenv("DB_USERNAME"));
    config.setPassword(System.getenv("DB_PASSWORD"));
    config.setMaximumPoolSize(3);
    dataSource = new HikariDataSource(config);
  }

  public static void main(String[] args) throws Exception {
    try {
      showIsolationLevels();
      demonstrateReadCommitted();
    } finally {
      dataSource.close();
    }
  }

  // 각 격리 수준 상수값 출력
  static void showIsolationLevels() {
    System.out.println("=== JDBC 격리 수준 상수 ===");
    System.out.printf("  TRANSACTION_NONE             = %d%n", Connection.TRANSACTION_NONE);
    System.out.printf(
        "  TRANSACTION_READ_UNCOMMITTED = %d%n", Connection.TRANSACTION_READ_UNCOMMITTED);
    System.out.printf(
        "  TRANSACTION_READ_COMMITTED   = %d%n", Connection.TRANSACTION_READ_COMMITTED);
    System.out.printf(
        "  TRANSACTION_REPEATABLE_READ  = %d%n", Connection.TRANSACTION_REPEATABLE_READ);
    System.out.printf("  TRANSACTION_SERIALIZABLE     = %d%n", Connection.TRANSACTION_SERIALIZABLE);
  }

  // READ_COMMITTED: Non-Repeatable Read 시연
  // → 트랜잭션 A가 같은 행을 두 번 읽는 사이 트랜잭션 B가 변경·commit하면 다른 값이 읽힌다.
  // (단일 스레드에서는 재현 불가 — 개념 설명 및 격리 수준 설정 방법에 집중)
  static void demonstrateReadCommitted() throws Exception {
    System.out.println("\n=== READ_COMMITTED 격리 수준 설정 및 현재 수준 확인 ===");

    try (Connection conn = dataSource.getConnection()) {
      System.out.println("  기본 격리 수준: " + isolationName(conn.getTransactionIsolation()));

      // READ_COMMITTED로 명시 설정 (Oracle 기본값이므로 사실상 동일)
      conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      conn.setAutoCommit(false);

      System.out.println("  설정 후 격리 수준: " + isolationName(conn.getTransactionIsolation()));

      // 첫 번째 읽기
      long price1 = readProductPrice(conn, 1L);
      System.out.printf("  [1차 읽기] product#1 price = %,d원%n", price1);

      // 다른 세션에서 가격을 변경했다고 가정 (시뮬레이션)
      simulatePriceUpdate(1L, 2800000.00);

      // 두 번째 읽기 — READ_COMMITTED이면 commit된 새 값을 읽는다 (Non-Repeatable Read 발생)
      long price2 = readProductPrice(conn, 1L);
      System.out.printf("  [2차 읽기] product#1 price = %,d원%n", price2);

      if (price1 != price2) {
        System.out.println("  → Non-Repeatable Read 발생: 같은 트랜잭션 내 값이 달라짐");
      } else {
        System.out.println("  → 두 읽기 결과 동일 (단일 스레드 환경)");
      }

      conn.rollback();

      // 가격 원복
      simulatePriceUpdate(1L, 2990000.00);
    }

    // SERIALIZABLE 설정 예시
    System.out.println("\n=== SERIALIZABLE 격리 수준 설정 예시 ===");
    try (Connection conn = dataSource.getConnection()) {
      conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
      System.out.println("  격리 수준: " + isolationName(conn.getTransactionIsolation()));
      System.out.println("  → 가장 강력한 격리. 성능 저하 주의.");
    }
  }

  static long readProductPrice(Connection conn, long productId) throws Exception {
    String sql = "SELECT price FROM shop_product WHERE id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, productId);
      try (ResultSet rs = pstmt.executeQuery()) {
        rs.next();
        return rs.getLong("price");
      }
    }
  }

  static void simulatePriceUpdate(long productId, double newPrice) throws Exception {
    String sql = "UPDATE shop_product SET price = ? WHERE id = ?";
    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setDouble(1, newPrice);
        pstmt.setLong(2, productId);
        pstmt.executeUpdate();
        conn.commit();
      }
    }
  }

  static String isolationName(int level) {
    return switch (level) {
      case Connection.TRANSACTION_NONE -> "NONE";
      case Connection.TRANSACTION_READ_UNCOMMITTED -> "READ_UNCOMMITTED";
      case Connection.TRANSACTION_READ_COMMITTED -> "READ_COMMITTED";
      case Connection.TRANSACTION_REPEATABLE_READ -> "REPEATABLE_READ";
      case Connection.TRANSACTION_SERIALIZABLE -> "SERIALIZABLE";
      default -> "UNKNOWN(" + level + ")";
    };
  }
}
