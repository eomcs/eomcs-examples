package com.eomcs.advanced.jpa.exam05;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// exam05 - JDBC Template 패턴 직접 구현
//
// 템플릿 콜백(Template-Callback) 패턴:
// - 변하지 않는 공통 로직(템플릿)과 변하는 부분(콜백)을 분리한다.
// - 공통 로직: Connection 획득 → PreparedStatement 생성 → 실행 → 자원 해제
// - 변하는 부분: SQL, 파라미터 바인딩(StatementSetter), 결과 변환(RowMapper)
// - Spring의 JdbcTemplate이 정확히 이 구조로 구현되어 있다.
//
// 이 예제에서 구현하는 클래스:
//   SimpleJdbcTemplate  → 공통 로직(템플릿)을 담당
//   StatementSetter     → PreparedStatement에 파라미터를 바인딩하는 콜백
//   RowMapper<T>        → ResultSet 한 행을 T로 변환하는 콜백
//
// Spring JdbcTemplate 대응:
//   SimpleJdbcTemplate.query()  → JdbcTemplate.query()
//   SimpleJdbcTemplate.update() → JdbcTemplate.update()
//   StatementSetter             → PreparedStatementSetter
//   RowMapper<T>                → RowMapper<T>
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam05.App
//
public class App {

  // ── 콜백 인터페이스 ────────────────────────────────────────────────────────

  // PreparedStatement에 파라미터를 바인딩하는 콜백
  @FunctionalInterface
  interface StatementSetter {
    void setValues(PreparedStatement pstmt) throws SQLException;
  }

  // ResultSet 현재 행을 T로 변환하는 콜백
  @FunctionalInterface
  interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
  }

  // ── 템플릿 클래스 ──────────────────────────────────────────────────────────

  // SimpleJdbcTemplate: JDBC 공통 로직을 캡슐화한 템플릿
  static class SimpleJdbcTemplate {

    private final HikariDataSource dataSource;

    SimpleJdbcTemplate(HikariDataSource dataSource) {
      this.dataSource = dataSource;
    }

    // SELECT → 다건 조회
    <T> List<T> query(String sql, StatementSetter setter, RowMapper<T> mapper) throws Exception {
      List<T> list = new ArrayList<>();
      try (Connection conn         = dataSource.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
        setter.setValues(pstmt);                 // 콜백: 파라미터 바인딩
        try (ResultSet rs = pstmt.executeQuery()) {
          while (rs.next()) {
            list.add(mapper.mapRow(rs));          // 콜백: 행 변환
          }
        }
      }
      return list;
    }

    // SELECT → 단건 조회 (결과가 없으면 Optional.empty())
    <T> Optional<T> queryForObject(String sql, StatementSetter setter, RowMapper<T> mapper) throws Exception {
      List<T> result = query(sql, setter, mapper);
      return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    // INSERT / UPDATE / DELETE
    int update(String sql, StatementSetter setter) throws Exception {
      try (Connection conn         = dataSource.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
        setter.setValues(pstmt);                 // 콜백: 파라미터 바인딩
        return pstmt.executeUpdate();
      }
    }
  }

  // ── 도메인 객체 ────────────────────────────────────────────────────────────

  static class Customer {
    long   id;
    String name;
    String email;
    String city;

    @Override
    public String toString() {
      return String.format("Customer{id=%d, name='%s', email='%s', city='%s'}", id, name, email, city);
    }
  }

  static class Product {
    long   id;
    String name;
    double price;
    int    stock;

    @Override
    public String toString() {
      return String.format("Product{id=%d, name='%s', price=%,.0f, stock=%d}", id, name, price, stock);
    }
  }

  // ── RowMapper 상수 (재사용) ────────────────────────────────────────────────

  static final RowMapper<Customer> CUSTOMER_MAPPER = rs -> {
    Customer c = new Customer();
    c.id    = rs.getLong("id");
    c.name  = rs.getString("name");
    c.email = rs.getString("email");
    c.city  = rs.getString("city");
    return c;
  };

  static final RowMapper<Product> PRODUCT_MAPPER = rs -> {
    Product p = new Product();
    p.id    = rs.getLong("id");
    p.name  = rs.getString("name");
    p.price = rs.getDouble("price");
    p.stock = rs.getInt("stock");
    return p;
  };

  // ── main ──────────────────────────────────────────────────────────────────

  public static void main(String[] args) throws Exception {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:oracle:thin:@//" + host + ":" + port + "/" + service);
    config.setUsername(System.getenv("DB_USERNAME"));
    config.setPassword(System.getenv("DB_PASSWORD"));
    config.setMaximumPoolSize(3);

    try (HikariDataSource ds = new HikariDataSource(config)) {
      SimpleJdbcTemplate jdbc = new SimpleJdbcTemplate(ds);

      // 1. 전체 고객 조회
      System.out.println("=== 전체 고객 ===");
      List<Customer> allCustomers = jdbc.query(
          "SELECT id, name, email, city FROM shop_customer ORDER BY id",
          pstmt -> { /* 파라미터 없음 */ },
          CUSTOMER_MAPPER);
      allCustomers.forEach(c -> System.out.println("  " + c));

      // 2. 특정 id로 고객 단건 조회
      System.out.println("\n=== id=1 고객 조회 ===");
      Optional<Customer> found = jdbc.queryForObject(
          "SELECT id, name, email, city FROM shop_customer WHERE id = ?",
          pstmt -> pstmt.setLong(1, 1L),
          CUSTOMER_MAPPER);
      found.ifPresentOrElse(
          c -> System.out.println("  " + c),
          () -> System.out.println("  없음"));

      // 3. 가격 범위로 제품 조회
      System.out.println("\n=== 가격 100,000원 이상 제품 ===");
      List<Product> expensiveProducts = jdbc.query(
          "SELECT id, name, price, stock FROM shop_product WHERE price >= ? ORDER BY price DESC",
          pstmt -> pstmt.setDouble(1, 100_000),
          PRODUCT_MAPPER);
      expensiveProducts.forEach(p -> System.out.println("  " + p));

      // 4. 재고 UPDATE
      System.out.println("\n=== 재고 차감 (product#1, -1) ===");
      int affected = jdbc.update(
          "UPDATE shop_product SET stock = stock - ? WHERE id = ?",
          pstmt -> {
            pstmt.setInt(1, 1);
            pstmt.setLong(2, 1L);
          });
      System.out.println("  영향 행 수: " + affected);

      Optional<Product> updated = jdbc.queryForObject(
          "SELECT id, name, price, stock FROM shop_product WHERE id = ?",
          pstmt -> pstmt.setLong(1, 1L),
          PRODUCT_MAPPER);
      updated.ifPresent(p -> System.out.println("  업데이트 후: " + p));

      System.out.println("\n[정리]");
      System.out.println("  SimpleJdbcTemplate.query()  ≈ Spring JdbcTemplate.query()");
      System.out.println("  SimpleJdbcTemplate.update() ≈ Spring JdbcTemplate.update()");
      System.out.println("  → 공통 로직(템플릿)은 변하지 않고, SQL·바인딩·매핑(콜백)만 교체된다.");
    }
  }
}
