package com.eomcs.advanced.jpa.exam03;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Statement;

// exam03 - Savepoint: 트랜잭션 부분 취소
//
// Savepoint:
// - 트랜잭션 내에 중간 저장 지점을 설정한다.
// - rollback(Savepoint) 호출 시 해당 지점 이후의 변경만 취소하고
//   이전 변경은 유지한다.
// - 모두-또는-없음(all-or-nothing)이 아닌 부분 취소가 필요할 때 사용한다.
//
// API:
//   Savepoint sp = conn.setSavepoint("이름");  → Savepoint 설정
//   conn.rollback(sp);                         → sp 이후 변경만 취소
//   conn.releaseSavepoint(sp);                 → Savepoint 해제 (선택)
//
// 예제 시나리오:
//   고객 A INSERT → Savepoint 설정 → 고객 B INSERT (오류) → Savepoint로 rollback
//   → 고객 A는 유지, 고객 B는 취소된 채 commit
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam03.App2
//
public class App2 {

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
      demonstrateSavepoint();
    } finally {
      dataSource.close();
    }
  }

  static void demonstrateSavepoint() throws Exception {
    System.out.println("=== Savepoint 예제 ===");

    String insertSql = "INSERT INTO shop_customer (name, email, city, street, zipcode, created_at, updated_at) "
        + "VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";

    printCustomerCount("시작 전");

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);

      try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

        // [1단계] 첫 번째 고객 INSERT 성공
        pstmt.setString(1, "박민준");
        pstmt.setString(2, "park@example.com");
        pstmt.setString(3, "인천");
        pstmt.setString(4, "송도대로 10");
        pstmt.setString(5, "22000");
        pstmt.executeUpdate();
        System.out.println("  [1] 박민준 INSERT 성공");

        // Savepoint 설정: 이 시점 이전 변경(박민준)은 rollback해도 보존된다.
        Savepoint sp = conn.setSavepoint("after_park");
        System.out.println("  Savepoint 설정 완료 (after_park)");

        // [2단계] 두 번째 고객 INSERT — 중복 이메일로 예외 유발
        try {
          pstmt.setString(1, "최수연");
          pstmt.setString(2, "hong@example.com"); // 이미 존재하는 email → UK 위반
          pstmt.setString(3, "광주");
          pstmt.setString(4, "충장로 55");
          pstmt.setString(5, "61000");
          pstmt.executeUpdate();
          System.out.println("  [2] 최수연 INSERT 성공");
        } catch (Exception e) {
          System.out.println("  [2] 최수연 INSERT 실패: " + e.getMessage());
          // Savepoint 이후(최수연 INSERT)만 취소 → 박민준 INSERT는 유지
          conn.rollback(sp);
          System.out.println("  → Savepoint(after_park)로 부분 rollback");
        }

        conn.commit(); // 박민준만 commit됨
        System.out.println("  → commit 완료");
      }
    }

    printCustomerCount("완료 후 (박민준만 추가됨)");
  }

  static void printCustomerCount(String label) throws Exception {
    try (Connection conn = dataSource.getConnection();
         Statement  stmt = conn.createStatement();
         ResultSet  rs   = stmt.executeQuery("SELECT COUNT(*) FROM shop_customer")) {
      rs.next();
      System.out.printf("  [%s] shop_customer 총 %d건%n", label, rs.getLong(1));
    }
  }
}
