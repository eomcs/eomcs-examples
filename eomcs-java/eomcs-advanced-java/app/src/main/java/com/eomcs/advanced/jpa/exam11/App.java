package com.eomcs.advanced.jpa.exam11;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam11 - 상속 매핑 전략 (JOINED 전략)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam11.App
//
public class App {

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
        Persistence.createEntityManagerFactory("exam11", props)) {

      // ── 1. PhysicalProduct 저장 ──────────────────────────────────────────
      System.out.println("=== 1. PhysicalProduct INSERT ===");
      Long physicalId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        PhysicalProduct laptop = new PhysicalProduct();
        laptop.setName("LG 그램 17");
        laptop.setPrice(new BigDecimal("1590000"));
        laptop.setStock(8);
        laptop.setWeight(new BigDecimal("1.350"));
        laptop.setShippingFee(new BigDecimal("3000"));
        laptop.setCreatedAt(LocalDateTime.now());
        laptop.setUpdatedAt(LocalDateTime.now());

        em.persist(laptop);
        // Hibernate가 두 번 INSERT 실행:
        //   1) INSERT INTO shop_product (dtype='PHYSICAL', name, price, stock, ...)
        //   2) INSERT INTO shop_physical_product (product_id, weight, shipping_fee)
        tx.commit();
        physicalId = laptop.getId();
        System.out.println("  저장 완료: " + laptop);
      }

      // ── 2. DigitalProduct 저장 ───────────────────────────────────────────
      System.out.println("\n=== 2. DigitalProduct INSERT ===");
      Long digitalId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        DigitalProduct sw = new DigitalProduct();
        sw.setName("한글과컴퓨터 한컴오피스 2024");
        sw.setPrice(new BigDecimal("59000"));
        sw.setStock(999);
        sw.setDownloadUrl("https://www.hancom.com/download");
        sw.setLicenseCount(1);
        sw.setCreatedAt(LocalDateTime.now());
        sw.setUpdatedAt(LocalDateTime.now());

        em.persist(sw);
        tx.commit();
        digitalId = sw.getId();
        System.out.println("  저장 완료: " + sw);
      }

      // ── 3. 특정 타입 조회 (PhysicalProduct) ─────────────────────────────
      System.out.println("\n=== 3. PhysicalProduct 단건 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        PhysicalProduct found = em.find(PhysicalProduct.class, physicalId);
        // Hibernate가 INNER JOIN으로 조회:
        //   SELECT p.*, pp.weight, pp.shipping_fee
        //   FROM shop_product p INNER JOIN shop_physical_product pp ON p.id = pp.product_id
        //   WHERE p.id = ?
        System.out.println("  " + found);
      }

      // ── 4. 부모 타입으로 다형성 조회 ────────────────────────────────────
      System.out.println("\n=== 4. Product 타입으로 다형성 조회 (모든 제품) ===");
      // Product로 find 시 dtype에 따라 PhysicalProduct 또는 DigitalProduct 인스턴스가 반환된다.
      try (EntityManager em = emf.createEntityManager()) {
        Product byParent = em.find(Product.class, digitalId);
        System.out.println("  실제 타입: " + byParent.getClass().getSimpleName());
        System.out.println("  " + byParent);

        if (byParent instanceof DigitalProduct dp) {
          System.out.println("  다운로드 URL: " + dp.getDownloadUrl());
        }
      }

      // ── 5. JPQL로 전체 Product 목록 조회 ────────────────────────────────
      System.out.println("\n=== 5. JPQL: 모든 Product 목록 (다형성 조회) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> all = em.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
            .getResultList();
        // Hibernate는 각 행의 dtype을 보고 적합한 서브클래스 인스턴스로 반환한다.
        for (Product p : all) {
          System.out.printf("  [%-15s] %s%n", p.getClass().getSimpleName(), p.getName());
        }
      }

      // ── 6. JPQL: 특정 타입만 필터링 ─────────────────────────────────────
      System.out.println("\n=== 6. JPQL: PhysicalProduct만 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<PhysicalProduct> physicals = em.createQuery(
            "SELECT p FROM PhysicalProduct p ORDER BY p.id", PhysicalProduct.class)
            .getResultList();
        physicals.forEach(p -> System.out.println("  " + p));
      }

      System.out.println("\n=== 7. JPQL: DigitalProduct만 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<DigitalProduct> digitals = em.createQuery(
            "SELECT p FROM DigitalProduct p ORDER BY p.id", DigitalProduct.class)
            .getResultList();
        digitals.forEach(p -> System.out.println("  " + p));
      }

      // ── 7. 정리 ─────────────────────────────────────────────────────────
      System.out.println("\n[정리: JOINED 전략 특징]");
      System.out.println("  INSERT : 부모/자식 테이블에 각각 한 번씩, 총 2번 INSERT 실행");
      System.out.println("  SELECT : INNER JOIN으로 부모+자식 컬럼을 한 번에 조회");
      System.out.println("  장점   : 정규화, NULL 없음, 각 테이블이 깔끔");
      System.out.println("  단점   : JOIN 비용, INSERT 2번");
      System.out.println();
      System.out.println("  SINGLE_TABLE 전략으로 변경하려면:");
      System.out.println("    Product에 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)");
      System.out.println("    자식 테이블 없이 shop_product 하나에 모든 컬럼 저장");
    }
  }
}
