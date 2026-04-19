package com.eomcs.advanced.jpa.exam04;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// exam04 - ResultSet 수동 매핑: 반복 문제 확인
//
// JDBC ResultSet 수동 매핑:
// - rs.getString("컬럼명"), rs.getLong("컬럼명") 등으로 컬럼 값을 꺼낸다.
// - 꺼낸 값을 직접 객체 필드에 대입한다.
// - 컬럼이 많아질수록 매핑 코드가 비례해서 늘어난다.
// - 쿼리마다 동일한 패턴이 반복된다 → 이것이 ORM 등장 배경이다.
//
// 이 예제에서 확인할 내용:
//   1. 고객(Customer) 목록을 ResultSet에서 수동으로 객체 변환
//   2. 제품(Product) 목록을 ResultSet에서 수동으로 객체 변환
//   3. 두 매핑 코드의 구조가 완전히 동일한 "반복" 패턴임을 확인
//   → exam04/App2.java에서 RowMapper 패턴으로 개선한다.
//
// 실행 방법:
//   ./gradlew run -PmainClass=com.eomcs.advanced.jpa.exam04.App
//
public class App {

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
    Timestamp createdAt;

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
      List<Customer> customers = findAllCustomers();
      System.out.println("=== 고객 목록 (수동 매핑) ===");
      customers.forEach(c -> System.out.println("  " + c));

      List<Product> products = findAllProducts();
      System.out.println("\n=== 제품 목록 (수동 매핑) ===");
      products.forEach(p -> System.out.println("  " + p));

      System.out.println("\n[관찰] 두 메서드(findAllCustomers, findAllProducts)의 구조가 동일하다.");
      System.out.println("  1. Connection 획득");
      System.out.println("  2. PreparedStatement 생성 & 실행");
      System.out.println("  3. ResultSet 순회하며 rs.getXxx()로 꺼내 객체에 대입");
      System.out.println("  4. 리스트에 담아 반환");
      System.out.println("  → 이 반복 패턴을 제거하는 것이 RowMapper 패턴의 목적이다.");
    } finally {
      dataSource.close();
    }
  }

  // 고객 목록 조회: ResultSet → Customer 수동 매핑
  static List<Customer> findAllCustomers() throws Exception {
    String sql = "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id";
    List<Customer> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

      while (rs.next()) {
        // ─ 반복 패턴 시작 ─────────────────────────────
        Customer c = new Customer();
        c.id        = rs.getLong("id");
        c.name      = rs.getString("name");
        c.email     = rs.getString("email");
        c.city      = rs.getString("city");
        c.createdAt = rs.getTimestamp("created_at");
        list.add(c);
        // ─ 반복 패턴 끝 ───────────────────────────────
      }
    }
    return list;
  }

  // 제품 목록 조회: ResultSet → Product 수동 매핑
  // findAllCustomers()와 구조가 완전히 동일함에 주목!
  static List<Product> findAllProducts() throws Exception {
    String sql = "SELECT id, dtype, name, price, stock, created_at FROM shop_product ORDER BY id";
    List<Product> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

      while (rs.next()) {
        // ─ 반복 패턴 시작 ─────────────────────────────
        Product p = new Product();
        p.id        = rs.getLong("id");
        p.dtype     = rs.getString("dtype");
        p.name      = rs.getString("name");
        p.price     = rs.getDouble("price");
        p.stock     = rs.getInt("stock");
        p.createdAt = rs.getTimestamp("created_at");
        list.add(p);
        // ─ 반복 패턴 끝 ───────────────────────────────
      }
    }
    return list;
  }
}
