package com.eomcs.advanced.jpa.exam03;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// exam03 - Transaction 관리: commit / rollback
//
// 트랜잭션(Transaction):
// - 하나의 논리적 작업 단위를 구성하는 SQL 묶음이다.
// - 묶음 안의 SQL은 모두 성공하거나 모두 취소되어야 한다 (원자성, Atomicity).
//
// JDBC 기본 동작:
// - Connection의 AutoCommit 기본값은 true이다.
// - AutoCommit=true이면 SQL 실행 즉시 자동으로 commit된다.
// - 트랜잭션 관리를 위해 AutoCommit을 false로 설정한 뒤
//   명시적으로 commit() 또는 rollback()을 호출해야 한다.
//
// 트랜잭션 흐름:
//   conn.setAutoCommit(false)  → 트랜잭션 시작
//   SQL 실행 ...
//   conn.commit()              → 변경 내용을 DB에 영구 반영
//   conn.rollback()            → 트랜잭션 시작 이후 변경 내용을 모두 취소
//
// 예제 시나리오:
//   주문 처리: shop_orders INSERT + shop_order_item INSERT
//   → 두 INSERT 모두 성공하면 commit, 하나라도 실패하면 rollback
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam03.App
//
public class App {

  static HikariDataSource dataSource;

  static {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
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
      successCase();
      failureCase();
    } finally {
      dataSource.close();
    }
  }

  // 성공 케이스: 주문 + 주문상품 모두 INSERT 성공 → commit
  static void successCase() throws Exception {
    System.out.println("=== [성공 케이스] 주문 처리 → commit ===");
    printOrderCount("처리 전");

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false); // 트랜잭션 시작

      try {
        // 1단계: 주문 INSERT (GENERATED ALWAYS AS IDENTITY이므로 id 제외)
        long orderId;
        String orderSql = "INSERT INTO shop_orders (customer_id, order_status, order_date, created_at, updated_at) "
            + "VALUES (?, 'CONFIRMED', SYSTIMESTAMP, SYSTIMESTAMP, SYSTIMESTAMP)";
        try (PreparedStatement pstmt = conn.prepareStatement(orderSql,
            new String[]{"ID"})) {
          pstmt.setLong(1, 1L); // 홍길동
          pstmt.executeUpdate();
          // GENERATED KEY로 생성된 id 획득
          try (ResultSet rs = pstmt.getGeneratedKeys()) {
            rs.next();
            orderId = rs.getLong(1);
          }
        }
        System.out.println("  주문 INSERT 성공 (orderId=" + orderId + ")");

        // 2단계: 주문 상품 INSERT
        String itemSql = "INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(itemSql)) {
          pstmt.setLong(1, orderId);
          pstmt.setLong(2, 1L);      // MacBook Pro
          pstmt.setInt(3, 1);
          pstmt.setDouble(4, 2990000.00);
          pstmt.executeUpdate();
        }
        System.out.println("  주문상품 INSERT 성공");

        conn.commit(); // 두 INSERT 모두 성공 → DB에 영구 반영
        System.out.println("  → commit 완료");

      } catch (Exception e) {
        conn.rollback();
        System.out.println("  → rollback (예외: " + e.getMessage() + ")");
        throw e;
      }
    }
    printOrderCount("처리 후");
  }

  // 실패 케이스: 첫 INSERT 성공 후 두 번째에서 예외 → rollback
  static void failureCase() throws Exception {
    System.out.println("\n=== [실패 케이스] 중간 오류 발생 → rollback ===");
    printOrderCount("처리 전");

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);

      try {
        // 1단계: 주문 INSERT 성공
        long orderId;
        String orderSql = "INSERT INTO shop_orders (customer_id, order_status, order_date, created_at, updated_at) "
            + "VALUES (?, 'PENDING', SYSTIMESTAMP, SYSTIMESTAMP, SYSTIMESTAMP)";
        try (PreparedStatement pstmt = conn.prepareStatement(orderSql,
            new String[]{"ID"})) {
          pstmt.setLong(1, 2L); // 김영희
          pstmt.executeUpdate();
          try (ResultSet rs = pstmt.getGeneratedKeys()) {
            rs.next();
            orderId = rs.getLong(1);
          }
        }
        System.out.println("  주문 INSERT 성공 (orderId=" + orderId + ") ← 아직 commit 안 됨");

        // 2단계: 존재하지 않는 product_id(9999) → FK 제약 위반으로 예외 발생
        String itemSql = "INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(itemSql)) {
          pstmt.setLong(1, orderId);
          pstmt.setLong(2, 9999L); // 존재하지 않는 product_id
          pstmt.setInt(3, 1);
          pstmt.setDouble(4, 100.0);
          pstmt.executeUpdate(); // FK 제약 위반 → 예외 발생
        }

        conn.commit();

      } catch (Exception e) {
        conn.rollback(); // 1단계 INSERT도 함께 취소됨
        System.out.println("  → rollback (예외: " + e.getMessage() + ")");
      }
    }
    printOrderCount("처리 후 (rollback으로 건수 변화 없음)");
  }

  static void printOrderCount(String label) throws Exception {
    try (Connection conn = dataSource.getConnection();
         Statement  stmt = conn.createStatement();
         ResultSet  rs   = stmt.executeQuery("SELECT COUNT(*) FROM shop_orders")) {
      rs.next();
      System.out.printf("  [%s] shop_orders 총 %d건%n", label, rs.getLong(1));
    }
  }
}
