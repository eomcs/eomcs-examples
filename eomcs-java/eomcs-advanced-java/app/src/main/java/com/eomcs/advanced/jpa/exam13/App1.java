package com.eomcs.advanced.jpa.exam13;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam13 - @ManyToMany 기본 사용법
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App1
//
public class App1 {

  public static void main(String[] args) {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url",      url);
    props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    try (EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("exam13", props)) {

      // ── 1. 기존 샘플 데이터에서 Product → Category 탐색 ──────────────────
      System.out.println("=== 1. Product → Category 탐색 (@ManyToMany) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> products = em.createQuery(
            "SELECT p FROM Product p ORDER BY p.id", Product.class)
            .getResultList();

        for (Product p : products) {
          // categories 탐색 시점에 SELECT ... FROM shop_product_category JOIN 실행
          System.out.printf("  %-30s → 카테고리: %s%n",
              p.getName(),
              p.getCategories().stream()
                  .map(Category::getName)
                  .toList());
        }
      }

      // ── 2. Category → Product 역방향 탐색 ───────────────────────────────
      System.out.println("\n=== 2. Category → Product 역방향 탐색 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Category> categories = em.createQuery(
            "SELECT c FROM Category c ORDER BY c.id", Category.class)
            .getResultList();

        for (Category c : categories) {
          System.out.printf("  [%s] 제품: %s%n",
              c.getName(),
              c.getProducts().stream()
                  .map(Product::getName)
                  .toList());
        }
      }

      // ── 3. 새 Product + Category 연결 저장 ──────────────────────────────
      System.out.println("\n=== 3. 새 Product + Category 연결 INSERT ===");
      Long newProductId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 기존 카테고리(전자제품=id 1) 로드
        Category elec = em.find(Category.class, 1L);

        Product newProduct = new Product();
        newProduct.setName("삼성 갤럭시북 4");
        newProduct.setPrice(new BigDecimal("1290000"));
        newProduct.setStock(15);
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setUpdatedAt(LocalDateTime.now());

        // 편의 메서드: 양방향 동기화
        newProduct.addCategory(elec);

        em.persist(newProduct);
        // Hibernate 실행 SQL:
        //   1) INSERT INTO shop_product (dtype, name, price, ...) VALUES (...)
        //   2) INSERT INTO shop_product_category (product_id, category_id) VALUES (?, ?)

        tx.commit();
        newProductId = newProduct.getId();
        System.out.println("  저장 완료: " + newProduct);
        System.out.println("  카테고리: " + newProduct.getCategories());
      }

      // ── 4. 연결 삭제 ─────────────────────────────────────────────────────
      System.out.println("\n=== 4. Product-Category 연결 삭제 ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Product managed = em.find(Product.class, newProductId);
        managed.getCategories().clear();
        // @ManyToMany 연결 삭제: DELETE FROM shop_product_category WHERE product_id = ?

        tx.commit();
        System.out.println("  카테고리 연결 삭제 완료");
      }

      System.out.println("\n[정리: @ManyToMany 한계]");
      System.out.println("  - 중간 테이블에 추가 컬럼(등록일, 수량 등) 불가");
      System.out.println("  - 필요 시 App2.java처럼 연결 엔티티(ProductCategory)로 리팩토링");
    }
  }
}
