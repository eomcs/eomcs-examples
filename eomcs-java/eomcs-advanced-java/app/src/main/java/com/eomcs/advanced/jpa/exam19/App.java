package com.eomcs.advanced.jpa.exam19;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam19 - Criteria API: 타입 안전 동적 쿼리
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam19.App
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
        Persistence.createEntityManagerFactory("exam19", props)) {

      // ── 1. 기본 Criteria 쿼리 ───────────────────────────────────────────────
      System.out.println("=== 1. 기본 Criteria 쿼리: 전체 고객 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder   cb   = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq   = cb.createQuery(Customer.class);
        Root<Customer>    root = cq.from(Customer.class);

        cq.select(root)
          .orderBy(cb.asc(root.get("id")));

        List<Customer> result = em.createQuery(cq).getResultList();
        result.forEach(c -> System.out.println("  " + c));
        // Criteria API: JPQL 문자열 없이 Java 코드로 쿼리를 구성
        // → 컴파일 타임에 타입 오류 탐지 가능
      }

      // ── 2. WHERE 조건 (단일 Predicate) ──────────────────────────────────────
      System.out.println("\n=== 2. WHERE: 서울 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder   cb   = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq   = cb.createQuery(Customer.class);
        Root<Customer>    root = cq.from(Customer.class);

        cq.select(root)
          .where(cb.equal(root.get("city"), "서울"))
          .orderBy(cb.asc(root.get("id")));

        em.createQuery(cq).getResultList()
          .forEach(c -> System.out.println("  " + c));
      }

      // ── 3. 동적 쿼리 (조건부 Predicate 조합) ────────────────────────────────
      System.out.println("\n=== 3. 동적 쿼리: 조건 조합 (city=서울, name에 '홍' 포함) ===");
      try (EntityManager em = emf.createEntityManager()) {
        findCustomersDynamic(em, "서울", "홍")
            .forEach(c -> System.out.println("  " + c));
        // 핵심 장점: 조건이 없으면 Predicate 를 추가하지 않으면 되므로
        // JPQL 문자열 조작 없이 안전하게 동적 WHERE 절 구성 가능
      }

      // ── 4. 동적 쿼리: 조건 없음 → 전체 조회 ──────────────────────────────────
      // 섹션 3과 동일한 메서드를 null 값으로 호출하면 WHERE 절 없이 전체 반환
      System.out.println("\n=== 4. 동적 쿼리: 조건 없음 → 전체 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // city=null, name=null → Predicate 를 하나도 추가하지 않음 → 전체 조회
        findCustomersDynamic(em, null, null)
            .forEach(c -> System.out.println("  " + c));
      }

      // ── 5. BETWEEN + OR 조건 ─────────────────────────────────────────────────
      System.out.println("\n=== 5. BETWEEN + OR: 가격 범위 또는 재고 없는 제품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder  cb   = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq   = cb.createQuery(Product.class);
        Root<Product>    root = cq.from(Product.class);

        Predicate inPriceRange = cb.between(
            root.<BigDecimal>get("price"),
            new BigDecimal("100000"),
            new BigDecimal("500000"));
        Predicate noStock = cb.equal(root.get("stock"), 0);

        cq.select(root)
          .where(cb.or(inPriceRange, noStock))
          .orderBy(cb.asc(root.get("price")));

        em.createQuery(cq).getResultList()
          .forEach(p -> System.out.println("  " + p));
      }

      // ── 6. 집계 쿼리 (COUNT) ─────────────────────────────────────────────────
      System.out.println("\n=== 6. 집계: 전체 고객 수 ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder   cb   = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq  = cb.createQuery(Long.class);
        Root<Customer>    root = cq.from(Customer.class);

        cq.select(cb.count(root));

        Long count = em.createQuery(cq).getSingleResult();
        System.out.println("  전체 고객 수: " + count + "명");
      }

      // ── 7. 다중 정렬 (복합 ORDER BY) ────────────────────────────────────────
      System.out.println("\n=== 7. 복합 ORDER BY: 도시 ASC, 이름 DESC ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder   cb   = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq   = cb.createQuery(Customer.class);
        Root<Customer>    root = cq.from(Customer.class);

        List<Order> orderBy = List.of(
            cb.asc(root.get("city")),
            cb.desc(root.get("name")));

        cq.select(root)
          .where(cb.isNotNull(root.get("city")))
          .orderBy(orderBy);

        em.createQuery(cq).getResultList()
          .forEach(c -> System.out.printf("  [%-4s] %s%n", c.getCity(), c.getName()));
      }

      // ── 8. 단일 컬럼 프로젝션 ─────────────────────────────────────────────
      System.out.println("\n=== 8. 단일 컬럼 프로젝션: 제품명만 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        CriteriaBuilder   cb   = em.getCriteriaBuilder();
        CriteriaQuery<String> cq   = cb.createQuery(String.class);
        Root<Product>     root = cq.from(Product.class);

        cq.select(root.get("name"))
          .orderBy(cb.asc(root.get("id")));

        em.createQuery(cq).getResultList()
          .forEach(name -> System.out.println("  제품명: " + name));
      }

      System.out.println("\n[정리: Criteria API]");

      System.out.println("  CriteriaBuilder  : Predicate·Expression·Order 등을 생성하는 팩토리");
      System.out.println("  CriteriaQuery<T> : SELECT 절 타입을 결정하는 쿼리 객체");
      System.out.println("  Root<T>          : FROM 절 엔티티. 필드 접근 시 root.get(\"필드명\")");
      System.out.println("  Predicate        : WHERE 절 조건. cb.and/or 로 조합 가능");
      System.out.println("  동적 쿼리        : Predicate 를 조건에 따라 추가 → JPQL 문자열 조작 불필요");
      System.out.println("  단점             : 코드가 장황해짐 → 복잡한 쿼리는 QueryDSL 사용 권장");
    }
  }

  // 동적 Criteria 쿼리: city/name 이 null 또는 빈 문자열이면 해당 조건 미적용
  private static List<Customer> findCustomersDynamic(
      EntityManager em, String city, String name) {

    CriteriaBuilder         cb         = em.getCriteriaBuilder();
    CriteriaQuery<Customer> cq         = cb.createQuery(Customer.class);
    Root<Customer>          root       = cq.from(Customer.class);
    List<Predicate>         predicates = new ArrayList<>();

    if (city != null && !city.isBlank()) {
      predicates.add(cb.equal(root.get("city"), city));
    }
    if (name != null && !name.isBlank()) {
      predicates.add(cb.like(root.get("name"), "%" + name + "%"));
    }

    if (!predicates.isEmpty()) {
      cq.where(cb.and(predicates.toArray(new Predicate[0])));
    }
    cq.orderBy(cb.asc(root.get("id")));

    return em.createQuery(cq).getResultList();
  }
}
