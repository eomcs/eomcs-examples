package com.eomcs.advanced.jpa.exam04;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// exam04 - RowMapper 패턴: 반복 코드 제거
//
// RowMapper 패턴:
// - ResultSet의 현재 행(row)을 객체로 변환하는 책임을 인터페이스로 분리한다.
// - 쿼리 실행 + ResultSet 순회 로직은 공통 메서드(query)에 한 번만 작성한다.
// - 객체 변환 로직(RowMapper 구현)만 호출 측에서 람다로 제공한다.
// - Spring의 JdbcTemplate.query(sql, rowMapper)가 바로 이 패턴을 사용한다.
//
// App.java(수동 매핑)와 비교:
//   수동 매핑: 쿼리마다 Connection 획득 + ResultSet 순회 + 매핑 코드가 반복
//   RowMapper:  매핑 로직만 람다로 제공, 나머지는 query() 메서드가 처리
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam04.App2
//
public class App2 {

  // RowMapper 인터페이스: ResultSet 현재 행 → 객체 T 변환 책임
  @FunctionalInterface
  interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
  }

  // 도메인 객체: Customer
  static class Customer {
    long      id;
    String    name;
    String    email;
    String    city;
    Timestamp createdAt;

    @Override
    public String toString() {
      return String.format("Customer{id=%d, name='%s', email='%s', city='%s'}", id, name, email, city);
    }
  }

  // 도메인 객체: Product
  static class Product {
    long      id;
    String    dtype;
    String    name;
    double    price;
    int       stock;

    @Override
    public String toString() {
      return String.format("Product{id=%d, dtype='%s', name='%s', price=%,.0f}", id, dtype, name, price);
    }
  }

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
      // RowMapper를 람다로 제공 — 매핑 로직만 작성하면 된다
      List<Customer> customers = query(
          "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id",
          rs -> {
            Customer c = new Customer();
            c.id        = rs.getLong("id");
            c.name      = rs.getString("name");
            c.email     = rs.getString("email");
            c.city      = rs.getString("city");
            c.createdAt = rs.getTimestamp("created_at");
            return c;
          });

      System.out.println("=== 고객 목록 (RowMapper 패턴) ===");
      customers.forEach(c -> System.out.println("  " + c));

      List<Product> products = query(
          "SELECT id, dtype, name, price, stock FROM shop_product ORDER BY id",
          rs -> {
            Product p = new Product();
            p.id    = rs.getLong("id");
            p.dtype = rs.getString("dtype");
            p.name  = rs.getString("name");
            p.price = rs.getDouble("price");
            p.stock = rs.getInt("stock");
            return p;
          });

      System.out.println("\n=== 제품 목록 (RowMapper 패턴) ===");
      products.forEach(p -> System.out.println("  " + p));

      System.out.println("\n[비교]");
      System.out.println("  수동 매핑(App.java)  : 쿼리마다 Connection/ResultSet 처리 코드가 중복");
      System.out.println("  RowMapper(App2.java) : query() 메서드가 공통 처리, 람다로 매핑만 제공");
      System.out.println("  → Spring JdbcTemplate은 이 패턴을 라이브러리로 제공한다.");
    } finally {
      dataSource.close();
    }
  }

  // 공통 쿼리 실행 메서드: Connection 획득 · ResultSet 순회 · 자원 해제를 한 곳에서 처리한다.
  // 호출 측은 RowMapper 람다(매핑 로직)만 제공하면 된다.
  static <T> List<T> query(String sql, RowMapper<T> mapper) throws Exception {
    List<T> list = new ArrayList<>();
    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {
      while (rs.next()) {
        list.add(mapper.mapRow(rs)); // 변환 책임을 RowMapper에 위임
      }
    }
    return list;
  }
}
