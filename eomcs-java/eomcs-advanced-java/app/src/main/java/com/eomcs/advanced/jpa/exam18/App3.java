package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam18 - JPQL 심화: Named Query (@NamedQuery)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App3
//
public class App3 {

  public static void main(String[] args) {
    String host = System.getenv("DB_HOSTNAME");
    String port = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url", url);
    props.put("jakarta.persistence.jdbc.user", System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("exam18", props)) {

      // @NamedQuery 정의 위치:
      //   Customer.java → "Customer.findAll", "Customer.findByCity"
      //   Product.java  → "Product.findByPriceRange", "Product.findExpensiveThanAvg"

      // ── 1. Customer.findAll ─────────────────────────────────────────────────
      System.out.println("=== 1. Named Query: Customer.findAll ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> all =
            em.createNamedQuery("Customer.findAll", Customer.class).getResultList();

        all.forEach(c -> System.out.println("  " + c));
        // createNamedQuery("이름", 반환타입): EntityManagerFactory 기동 시 파싱·캐시됨
        // 오타가 있으면 애플리케이션 시작 시점에 바로 예외 발생 (런타임 전 오류 발견)
      }

      // ── 2. Customer.findByCity (파라미터 바인딩) ────────────────────────────
      System.out.println("\n=== 2. Named Query: Customer.findByCity (city=서울) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> seoulCustomers =
            em.createNamedQuery("Customer.findByCity", Customer.class)
                .setParameter("city", "서울")
                .getResultList();

        seoulCustomers.forEach(c -> System.out.println("  " + c));
      }

      // ── 3. Product.findByPriceRange ─────────────────────────────────────────
      System.out.println("\n=== 3. Named Query: Product.findByPriceRange (50,000 ~ 200,000원) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> midRange =
            em.createNamedQuery("Product.findByPriceRange", Product.class)
                .setParameter("min", new BigDecimal("50000"))
                .setParameter("max", new BigDecimal("200000"))
                .getResultList();

        midRange.forEach(p -> System.out.printf("  %-30s %s원%n", p.getName(), p.getPrice()));
      }

      // ── 4. Product.findExpensiveThanAvg (서브쿼리 Named Query) ──────────────
      System.out.println("\n=== 4. Named Query: Product.findExpensiveThanAvg ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> expensive =
            em.createNamedQuery("Product.findExpensiveThanAvg", Product.class).getResultList();

        System.out.println("  평균 가격 초과 제품:");
        expensive.forEach(p -> System.out.printf("  %-30s %s원%n", p.getName(), p.getPrice()));
      }

      // ── 5. Named Query vs createQuery 비교 ─────────────────────────────────
      System.out.println("\n=== 5. Named Query vs createQuery 비교 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // createQuery: 매번 파싱
        long t1 = System.nanoTime();
        em.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class).getResultList();
        long createQueryMs = System.nanoTime() - t1;

        // createNamedQuery: 이미 파싱·캐시됨
        long t2 = System.nanoTime();
        em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
        long namedQueryMs = System.nanoTime() - t2;

        System.out.printf("  createQuery    : %,d ns%n", createQueryMs);
        System.out.printf("  createNamedQuery: %,d ns%n", namedQueryMs);
        System.out.println("  (첫 실행이라 차이가 미미할 수 있으나 반복 호출 시 Named Query가 유리)");
      }

      System.out.println("\n[정리: @NamedQuery]");
      System.out.println("  정의 위치   : 엔티티 클래스 상단 @NamedQuery 어노테이션");
      System.out.println("  검증 시점   : EntityManagerFactory 기동 시 (런타임 전 오류 조기 발견)");
      System.out.println("  사용 방법   : em.createNamedQuery(\"이름\", 반환타입)");
      System.out.println("  파라미터    : 일반 JPQL과 동일하게 :name 으로 바인딩");
      System.out.println("  성능        : 기동 시 파싱·캐시 → 반복 호출 시 createQuery보다 빠름");
      System.out.println("  Spring Boot : @Query(name=\"...\") 또는 Repository 메서드로 연결 가능");
    }
  }
}
