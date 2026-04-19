package com.eomcs.advanced.jpa.exam17;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam17 - JPQL 기초: 파라미터 바인딩
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App2
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
        Persistence.createEntityManagerFactory("exam17", props)) {

      // ── 1. 이름 기반 파라미터 (:name) ──────────────────────────────────────
      System.out.println("=== 1. 이름 기반 파라미터 (:name) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.name = :name ORDER BY c.id",
            Customer.class)
            .setParameter("name", "홍길동")
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
        // 콜론(:)으로 시작하는 이름 기반 파라미터는 가독성이 좋고 순서에 무관하다.
      }

      // ── 2. 위치 기반 파라미터 (?1) ─────────────────────────────────────────
      System.out.println("\n=== 2. 위치 기반 파라미터 (?1) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.city = ?1 ORDER BY c.id",
            Customer.class)
            .setParameter(1, "서울")
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
        // 위치 기반 파라미터는 1부터 시작하며 순서가 중요하다.
        // 이름 기반 파라미터보다 가독성이 낮아 잘 사용하지 않는다.
      }

      // ── 3. TypedQuery<T> vs Query ──────────────────────────────────────────
      System.out.println("\n=== 3. TypedQuery<T> vs Query ===");
      try (EntityManager em = emf.createEntityManager()) {
        // TypedQuery<Customer>: 컴파일 타임 타입 안전성 보장 → 권장
        TypedQuery<Customer> typedQuery = em.createQuery(
            "SELECT c FROM Customer c ORDER BY c.id",
            Customer.class);
        List<Customer> typed = typedQuery.getResultList();
        System.out.println("  TypedQuery 결과: " + typed.size() + "건");

        // Query (raw): 반환 타입이 Object → 형변환 필요
        Query rawQuery = em.createQuery("SELECT c FROM Customer c ORDER BY c.id");
        @SuppressWarnings("unchecked")
        List<Customer> raw = rawQuery.getResultList();
        System.out.println("  Query(raw) 결과: " + raw.size() + "건");
        // 가능하면 TypedQuery 를 사용해 타입 캐스팅 오류를 컴파일 시점에 잡는다.
      }

      // ── 4. 다중 이름 기반 파라미터 ─────────────────────────────────────────
      System.out.println("\n=== 4. 다중 파라미터: 가격 범위 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p"
            + " WHERE p.price >= :minPrice AND p.price <= :maxPrice"
            + " ORDER BY p.price",
            Product.class)
            .setParameter("minPrice", new BigDecimal("100000"))
            .setParameter("maxPrice", new BigDecimal("500000"))
            .getResultList();

        result.forEach(p -> System.out.printf("  %-30s %s원%n", p.getName(), p.getPrice()));
      }

      // ── 5. LIKE 파라미터 바인딩 ─────────────────────────────────────────────
      System.out.println("\n=== 5. LIKE + 파라미터 바인딩 ===");
      try (EntityManager em = emf.createEntityManager()) {
        String keyword = "MacBook";
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p WHERE p.name LIKE :keyword ORDER BY p.id",
            Product.class)
            .setParameter("keyword", "%" + keyword + "%")
            .getResultList();

        System.out.printf("  '%s' 포함 제품:%n", keyword);
        result.forEach(p -> System.out.println("    " + p));
        // LIKE의 와일드카드(%)는 파라미터 값에 포함시킨다.
        // 파라미터 자체에 와일드카드를 포함시켜야 SQL 인젝션을 예방할 수 있다.
      }

      // ── 6. setMaxResults / setFirstResult (페이징) ─────────────────────────
      System.out.println("\n=== 6. 페이징: setFirstResult / setMaxResults ===");
      try (EntityManager em = emf.createEntityManager()) {
        int pageSize = 2;
        int pageNo   = 0; // 0-based

        List<Product> page = em.createQuery(
            "SELECT p FROM Product p ORDER BY p.id",
            Product.class)
            .setFirstResult(pageNo * pageSize) // 시작 위치 (0-based)
            .setMaxResults(pageSize)            // 최대 반환 건수
            .getResultList();

        System.out.printf("  페이지 %d (size=%d):%n", pageNo + 1, pageSize);
        page.forEach(p -> System.out.println("    " + p));
        // JPA가 DB 방언(Dialect)에 맞는 페이징 SQL(ROWNUM, LIMIT, FETCH FIRST)로 변환한다.
      }

      System.out.println("\n[정리: JPQL 파라미터 바인딩]");
      System.out.println("  이름 기반 (:name)  → 권장. 순서 무관, 가독성 좋음");
      System.out.println("  위치 기반 (?1)     → 순서 의존. 잘 사용하지 않음");
      System.out.println("  TypedQuery<T>      → 컴파일 타임 타입 안전. Query(raw)보다 권장");
      System.out.println("  setFirstResult/setMaxResults → DB 방언에 맞는 페이징 SQL 자동 생성");
    }
  }
}
