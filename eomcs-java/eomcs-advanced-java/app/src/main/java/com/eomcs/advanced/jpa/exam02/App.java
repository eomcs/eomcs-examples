package com.eomcs.advanced.jpa.exam02;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// exam02 - PreparedStatement: SQL Injection 방지
//
// Statement vs PreparedStatement:
//
// Statement:
// - SQL 문자열을 그대로 DB 서버에 전송한다.
// - 매 실행마다 SQL 파싱 + 최적화 + 실행 계획 수립을 반복한다.
// - 사용자 입력을 문자열로 직접 연결하면 SQL Injection 공격에 취약하다.
//
// PreparedStatement:
// - SQL 골격(템플릿)을 미리 컴파일해 두고, 실행 시 파라미터(?)만 바인딩한다.
// - 파라미터 값은 항상 데이터로 처리되므로 SQL Injection을 원천 차단한다.
// - 동일 SQL을 반복 실행할 때 파싱 비용이 없어 성능이 더 좋다.
//
// SQL Injection 예시:
//   입력: name = "' OR '1'='1"
//   Statement 결과: SELECT * FROM shop_customer WHERE name='' OR '1'='1'
//     → 모든 행이 반환된다 (보안 취약점)
//   PreparedStatement 결과: name 컬럼 값이 ' OR '1'='1' 인 행만 반환된다
//     → 입력 전체가 문자열 리터럴로 처리된다
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam02.App
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
      demonstrateSqlInjection();
      demonstratePreparedStatement();
    } finally {
      dataSource.close();
    }
  }

  // Statement로 이름 검색 → SQL Injection에 취약
  static void demonstrateSqlInjection() throws Exception {
    System.out.println("=== [Statement] SQL Injection 취약점 시연 ===");

    // 정상 입력
    String safeInput = "홍길동";
    // 악의적인 입력: 작은따옴표를 포함해 SQL 논리를 변조한다
    String maliciousInput = "' OR '1'='1";

    String[] inputs = {safeInput, maliciousInput};

    for (String input : inputs) {
      System.out.printf("%n입력값: [%s]%n", input);
      // 사용자 입력을 SQL 문자열에 직접 연결 → 위험!
      String sql = "SELECT id, name, email FROM shop_customer WHERE name='" + input + "'";
      System.out.println("실행 SQL: " + sql);

      try (Connection conn = dataSource.getConnection();
           Statement stmt  = conn.createStatement();
           ResultSet rs    = stmt.executeQuery(sql)) {
        int count = 0;
        while (rs.next()) {
          System.out.printf("  → [%d] %s (%s)%n",
              rs.getLong("id"), rs.getString("name"), rs.getString("email"));
          count++;
        }
        if (count == 0) System.out.println("  → 검색 결과 없음");
      }
    }
  }

  // PreparedStatement로 이름 검색 → SQL Injection 차단
  static void demonstratePreparedStatement() throws Exception {
    System.out.println("\n=== [PreparedStatement] SQL Injection 차단 시연 ===");

    String safeInput     = "홍길동";
    String maliciousInput = "' OR '1'='1";

    // ? 플레이스홀더를 사용한 SQL 골격 (미리 컴파일됨)
    String sql = "SELECT id, name, email FROM shop_customer WHERE name = ?";

    String[] inputs = {safeInput, maliciousInput};

    for (String input : inputs) {
      System.out.printf("%n입력값: [%s]%n", input);

      try (Connection conn         = dataSource.getConnection();
           // PreparedStatement는 SQL 골격만 전송하고 파라미터는 별도로 바인딩한다.
           PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, input); // 인덱스는 1부터 시작
        System.out.println("실행 SQL: " + sql + "  [파라미터: " + input + "]");

        try (ResultSet rs = pstmt.executeQuery()) {
          int count = 0;
          while (rs.next()) {
            System.out.printf("  → [%d] %s (%s)%n",
                rs.getLong("id"), rs.getString("name"), rs.getString("email"));
            count++;
          }
          if (count == 0) System.out.println("  → 검색 결과 없음 (SQL Injection 차단됨)");
        }
      }
    }
  }
}
