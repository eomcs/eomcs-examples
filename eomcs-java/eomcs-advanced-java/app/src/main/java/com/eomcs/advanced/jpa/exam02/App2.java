package com.eomcs.advanced.jpa.exam02;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// exam02 - Batch 처리: addBatch() / executeBatch()
//
// Batch 처리란?
// - 여러 개의 SQL을 하나로 묶어 DB 서버에 한 번에 전송하는 방식이다.
// - 개별 실행 방식: SQL 1건 → 네트워크 왕복 1회 (N건이면 N번 왕복)
// - Batch 실행 방식: SQL N건 → 네트워크 왕복 1회
//   → 네트워크 왕복 횟수를 줄여 대량 데이터 처리 성능이 크게 향상된다.
//
// PreparedStatement Batch API:
//   pstmt.setXxx(...)  → 파라미터 세팅
//   pstmt.addBatch()   → 현재 파라미터 조합을 배치 큐에 추가
//   pstmt.executeBatch() → 큐에 쌓인 SQL을 한꺼번에 실행, 각 SQL의 영향 행 수를 배열로 반환
//   pstmt.clearBatch() → 큐를 비운다 (선택적)
//
// 주의사항:
// - Batch 실행 중 일부 SQL이 실패하면 BatchUpdateException이 발생한다.
// - 트랜잭션과 함께 사용해 부분 실패 시 rollback 처리해야 안전하다.
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam02.App2
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
      insertOneByOne();
      insertWithBatch();
      printProductCount();
    } finally {
      dataSource.close();
    }
  }

  // 방법 1: 개별 INSERT (네트워크 왕복 N회)
  static void insertOneByOne() throws Exception {
    System.out.println("=== [개별 INSERT] 카테고리 3개 추가 ===");

    String sql = "INSERT INTO shop_category (name, parent_id) VALUES (?, ?)";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        long start = System.currentTimeMillis();

        // 3건을 각각 executeUpdate() 호출 → 3번의 DB 왕복
        pstmt.setString(1, "태블릿");   pstmt.setNull(2, java.sql.Types.NUMERIC); pstmt.executeUpdate();
        pstmt.setString(1, "스마트폰"); pstmt.setNull(2, java.sql.Types.NUMERIC); pstmt.executeUpdate();
        pstmt.setString(1, "액세서리"); pstmt.setNull(2, java.sql.Types.NUMERIC); pstmt.executeUpdate();

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("  → 3건 개별 INSERT 완료 (소요: %dms)%n", elapsed);

        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw e;
      }
    }
  }

  // 방법 2: Batch INSERT (네트워크 왕복 1회)
  static void insertWithBatch() throws Exception {
    System.out.println("\n=== [Batch INSERT] 카테고리 5개 추가 ===");

    String sql = "INSERT INTO shop_category (name, parent_id) VALUES (?, ?)";

    String[] names = {"가전제품", "냉장고", "세탁기", "에어컨", "청소기"};

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        long start = System.currentTimeMillis();

        for (String name : names) {
          pstmt.setString(1, name);
          pstmt.setNull(2, java.sql.Types.NUMERIC);
          pstmt.addBatch(); // 배치 큐에 추가 (아직 실행 안 됨)
        }

        // 큐에 쌓인 SQL 5건을 한 번에 DB로 전송
        int[] results = pstmt.executeBatch();

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("  → %d건 Batch INSERT 완료 (소요: %dms)%n", results.length, elapsed);
        System.out.print("  각 SQL 영향 행 수: ");
        for (int r : results) System.out.print(r + " ");
        System.out.println();

        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw e;
      }
    }
  }

  // 삽입 후 카테고리 전체 건수 확인
  static void printProductCount() throws Exception {
    System.out.println("\n=== 현재 shop_category 건수 ===");
    try (Connection conn   = dataSource.getConnection();
         Statement  stmt   = conn.createStatement();
         ResultSet  rs     = stmt.executeQuery("SELECT COUNT(*) FROM shop_category")) {
      if (rs.next()) {
        System.out.printf("  총 %d건%n", rs.getLong(1));
      }
    }
  }
}
