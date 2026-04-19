package com.eomcs.advanced.jpa.exam01;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

// exam01 - Connection Pool: HikariCP DataSource
//
// DataSource (javax.sql.DataSource):
// - Connection 객체를 제공하는 표준 인터페이스다.
// - Connection Pool 구현체(HikariCP, DBCP 등)가 이 인터페이스를 구현한다.
// - getConnection() 호출 시 풀에서 이미 만들어진 Connection을 빌려준다.
// - close() 시 물리적 연결을 닫지 않고 풀에 반납한다.
//
// HikariCP:
// - 현재 가장 빠른 JDBC Connection Pool 라이브러리로 알려져 있다.
// - Spring Boot의 기본 Connection Pool이기도 하다.
// - 주요 설정:
//   maximumPoolSize  : 풀이 유지할 최대 Connection 수 (기본값: 10)
//   minimumIdle      : 유휴 상태로 유지할 최소 Connection 수
//   connectionTimeout: Connection 획득 대기 최대 시간 (ms)
//   idleTimeout      : 유휴 Connection을 풀에서 제거하기 전 대기 시간 (ms)
//
// DriverManager vs DataSource 비교:
// ┌─────────────────┬───────────────────────┬───────────────────────┐
// │                 │   DriverManager        │   DataSource (풀)     │
// ├─────────────────┼───────────────────────┼───────────────────────┤
// │ Connection 생성 │ 매 호출마다 신규 생성  │ 풀에서 재사용         │
// │ 성능            │ 느림 (TCP 핸드셰이크)  │ 빠름                  │
// │ 멀티스레드      │ 부적합                │ 적합                  │
// │ 용도            │ 단순 테스트           │ 실제 서비스           │
// └─────────────────┴───────────────────────┴───────────────────────┘
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam01.App2
//
public class App2 {

  public static void main(String[] args) throws Exception {

    String host = System.getenv("DB_HOSTNAME");
    String port = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");

    // HikariConfig: HikariCP 설정을 담는 객체
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setMaximumPoolSize(5); // 최대 Connection 5개 유지
    config.setMinimumIdle(2); // 유휴 Connection 최소 2개 유지
    config.setConnectionTimeout(3000); // 3초 안에 Connection 못 얻으면 예외

    System.out.println("=== [HikariCP] Connection Pool 예제 ===");

    // HikariDataSource: HikariConfig 설정을 바탕으로 Pool을 초기화한다.
    // try-with-resources: 블록을 벗어나면 Pool 전체가 종료된다.
    try (HikariDataSource dataSource = new HikariDataSource(config)) {

      System.out.println("Pool 생성 완료!");
      System.out.printf("  최대 풀 크기 : %d%n", dataSource.getMaximumPoolSize());

      // 첫 번째 Connection 획득 (풀에서 빌림)
      System.out.println("\n[1번 Connection 획득]");
      try (Connection conn1 = dataSource.getConnection()) {
        System.out.printf("  conn1 클래스: %s(%s)%n", conn1.getClass().getName(), conn1);

        // 제품 목록 조회
        System.out.println("\n--- shop_product 목록 ---");
        try (Statement stmt = conn1.createStatement();
            ResultSet rs =
                stmt.executeQuery("SELECT id, dtype, name, price FROM shop_product ORDER BY id")) {
          while (rs.next()) {
            System.out.printf(
                "  [%d] %-10s %-30s %,10.0f원%n",
                rs.getLong("id"),
                rs.getString("dtype"),
                rs.getString("name"),
                rs.getDouble("price"));
          }
        }
      }
      // conn1.close() → 풀에 반납 (물리적 연결은 유지됨)
      System.out.println("\n[1번 Connection 반납 (풀로 돌아감)]");

      // 두 번째 Connection 획득 → 풀에서 재사용
      System.out.println("[2번 Connection 획득 (재사용)]");
      try (Connection conn2 = dataSource.getConnection()) {
        System.out.printf("  conn2 클래스: %s(%s)%n", conn2.getClass().getName(), conn2);
        System.out.println("  → close()를 호출해도 물리 연결은 끊기지 않고 풀에 반납된다.");
      }
    }
    // dataSource.close() → 풀의 모든 물리적 Connection 해제
    System.out.println("\nPool 종료 완료.");
  }
}
