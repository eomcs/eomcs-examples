package com.eomcs.advanced.jpa.exam01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// exam01 - JDBC 기본 복습: DriverManager로 직접 연결
//
// DriverManager:
// - java.sql 패키지에 포함된 JDBC 관리 클래스다.
// - getConnection()을 호출할 때마다 새 Connection 객체를 생성한다.
// - 연결 생성 비용(TCP 핸드셰이크, 인증 등)이 매 호출마다 발생한다.
// - 단순 테스트나 단발성 스크립트에는 적합하지만, 멀티스레드 서버 환경에서는 부적합하다.
//
// JDBC URL 형식 (Oracle Thin):
//   jdbc:oracle:thin:@//호스트:포트/서비스이름
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam01.App
//
public class App {

  public static void main(String[] args) throws Exception {

    // OS 환경 변수에서 접속 정보를 읽는다.
    // → 소스 코드에 계정 정보를 하드코딩하지 않아 보안상 안전하다.
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url      = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");

    System.out.println("=== [DriverManager] JDBC 직접 연결 ===");
    System.out.println("URL: " + url);

    // DriverManager.getConnection() 호출 → 새 물리적 연결 생성
    // try-with-resources: 블록을 벗어나면 connection.close()가 자동 호출된다.
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      System.out.println("연결 성공!");
      System.out.printf("  드라이버 : %s%n", connection.getMetaData().getDriverName());
      System.out.printf("  DB 버전  : %s%n", connection.getMetaData().getDatabaseProductVersion());
      System.out.printf("  자동커밋 : %s%n", connection.getAutoCommit());

      // 간단한 쿼리 실행: 현재 시각 조회
      try (Statement stmt = connection.createStatement();
           ResultSet rs   = stmt.executeQuery("SELECT SYSDATE FROM DUAL")) {
        if (rs.next()) {
          System.out.println("DB 현재 시각: " + rs.getString(1));
        }
      }

      // 고객 목록 조회
      System.out.println("\n--- shop_customer 목록 ---");
      try (Statement stmt = connection.createStatement();
           ResultSet rs   = stmt.executeQuery(
               "SELECT id, name, email, city FROM shop_customer ORDER BY id")) {
        while (rs.next()) {
          System.out.printf("  [%d] %s (%s) / %s%n",
              rs.getLong("id"),
              rs.getString("name"),
              rs.getString("email"),
              rs.getString("city"));
        }
      }
    }
    // try 블록을 벗어나면 Connection.close()가 호출되어 물리적 연결이 해제된다.
    System.out.println("\n연결 종료 (Connection.close() 호출됨)");
  }
}
