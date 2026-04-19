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

// exam13 - @ManyToMany → 연결 엔티티(ProductCategory)로 리팩토링
//
// @ManyToMany 대신 ProductCategory 엔티티를 직접 사용하면:
//   - 중간 테이블에 추가 속성(createdAt 등)을 붙일 수 있다.
//   - 연결 자체를 엔티티로 조회/수정/삭제할 수 있다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App2
//
public class App2 {

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

      // ── 1. ProductCategory 연결 엔티티로 새 제품-카테고리 연결 저장 ────────
      System.out.println("=== 1. ProductCategory 연결 엔티티 INSERT ===");
      Long newProductId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 새 제품 저장
        Product product = new Product();
        product.setName("Apple iPad Pro M4");
        product.setPrice(new BigDecimal("1590000"));
        product.setStock(12);
        product.setDtype("PHYSICAL");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        em.persist(product);

        // 기존 카테고리(전자제품=1) 로드
        Category elec = em.find(Category.class, 1L);

        // 연결 엔티티 생성 (추가 속성인 createdAt도 함께 설정됨)
        ProductCategory pc = new ProductCategory(product, elec);
        em.persist(pc);
        // INSERT INTO shop_product_category (product_id, category_id) + createdAt

        tx.commit();
        newProductId = product.getId();
        System.out.println("  제품 저장: " + product);
        System.out.println("  연결 저장: " + pc);
      }

      // ── 2. ProductCategory JPQL 조회 ─────────────────────────────────────
      System.out.println("\n=== 2. ProductCategory JPQL 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // 연결 엔티티로 직접 조회 → 추가 속성(createdAt)도 함께 조회 가능
        List<ProductCategory> list = em.createQuery(
            "SELECT pc FROM ProductCategory pc ORDER BY pc.id.productId", ProductCategory.class)
            .getResultList();

        for (ProductCategory pc : list) {
          System.out.printf("  제품: %-30s | 카테고리: %-15s | 등록일: %s%n",
              pc.getProduct().getName(),
              pc.getCategory().getName(),
              pc.getCreatedAt() != null ? pc.getCreatedAt().toLocalDate() : "N/A");
        }
      }

      // ── 3. 복합 PK로 특정 연결 조회 ──────────────────────────────────────
      System.out.println("\n=== 3. 복합 PK(ProductCategoryId)로 단건 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        ProductCategoryId pk = new ProductCategoryId(newProductId, 1L);
        ProductCategory found = em.find(ProductCategory.class, pk);
        if (found != null) {
          System.out.println("  조회 성공: " + found);
        }
      }

      // ── 4. 연결 삭제 (remove) ────────────────────────────────────────────
      System.out.println("\n=== 4. 연결 엔티티 삭제 ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        ProductCategoryId pk = new ProductCategoryId(newProductId, 1L);
        ProductCategory toRemove = em.find(ProductCategory.class, pk);
        if (toRemove != null) {
          em.remove(toRemove);
          System.out.println("  삭제 완료: " + toRemove);
        }

        tx.commit();
      }

      System.out.println("\n[정리: @ManyToMany vs 연결 엔티티]");
      System.out.println("  @ManyToMany    : 단순, 중간 테이블 추가 속성 불가");
      System.out.println("  연결 엔티티     : 추가 속성 가능, 복합 PK(@EmbeddedId) 사용");
      System.out.println("  실무 권장       : 처음부터 연결 엔티티로 설계 (확장성)");
    }
  }
}
