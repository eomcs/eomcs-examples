package com.eomcs.advanced.jpa.exam20;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam20 - @SqlResultSetMapping: 네이티브 쿼리 결과를 DTO로 매핑
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App2
//
public class App2 {

  private static final String CUSTOMER_SUMMARY_MAPPING = "CustomerSummaryMapping";

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
        Persistence.createEntityManagerFactory("exam20", props)) {

      // @SqlResultSetMapping 정의 위치: Customer.java
      //
      //   @SqlResultSetMapping(
      //       name    = CUSTOMER_SUMMARY_MAPPING,
      //       classes = @ConstructorResult(
      //           targetClass = CustomerSummary.class,
      //           columns = {
      //               @ColumnResult(name = "id",          type = Long.class),
      //               @ColumnResult(name = "name",         type = String.class),
      //               @ColumnResult(name = "city",         type = String.class),
      //               @ColumnResult(name = "order_count",  type = Long.class)
      //           }
      //       )
      //   )

      // ── 1. @SqlResultSetMapping 으로 DTO 직접 매핑 ──────────────────────────
      System.out.println("=== 1. @SqlResultSetMapping: CustomerSummary DTO 매핑 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<CustomerSummary> summaries = em.createNativeQuery(
            "SELECT c.id, c.name, c.city,"
            + "       (SELECT COUNT(*) FROM shop_orders o WHERE o.customer_id = c.id)"
            + "         AS order_count"
            + " FROM shop_customer c"
            + " ORDER BY c.id",
            CUSTOMER_SUMMARY_MAPPING)   // ← 매핑 이름 지정
            .getResultList();

        summaries.forEach(s -> System.out.println("  " + s));
        // 결과 컬럼명(id, name, city, order_count)이
        // @ColumnResult(name=...) 과 일치해야 매핑된다.
      }

      // ── 2. @NamedNativeQuery 로 명명된 쿼리 사용 ───────────────────────────
      System.out.println("\n=== 2. @NamedNativeQuery: Customer.findSummary ===");
      try (EntityManager em = emf.createEntityManager()) {
        // @NamedNativeQuery 정의: Customer.java
        // query + resultSetMapping 이 함께 정의되어 있어 createNamedQuery 만으로 실행 가능
        List<CustomerSummary> summaries =
            em.createNamedQuery("Customer.findSummary", CustomerSummary.class)
                .getResultList();

        summaries.forEach(s -> System.out.println("  " + s));
      }

      // ── 3. 파라미터 바인딩 + @SqlResultSetMapping ──────────────────────────
      System.out.println("\n=== 3. 파라미터 바인딩: 특정 도시 고객 요약 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<CustomerSummary> summaries = em.createNativeQuery(
            "SELECT c.id, c.name, c.city,"
            + "       (SELECT COUNT(*) FROM shop_orders o WHERE o.customer_id = c.id)"
            + "         AS order_count"
            + " FROM shop_customer c"
            + " WHERE c.city = :city"
            + " ORDER BY c.id",
            CUSTOMER_SUMMARY_MAPPING)
            .setParameter("city", "서울")
            .getResultList();

        summaries.forEach(s -> System.out.println("  " + s));
      }

      // ── 4. Object[] 결과 vs DTO 결과 비교 ────────────────────────────────────
      System.out.println("\n=== 4. Object[] vs DTO 매핑 비교 ===");
      try (EntityManager em = emf.createEntityManager()) {
        String sql =
            "SELECT c.id, c.name, c.city,"
            + "       (SELECT COUNT(*) FROM shop_orders o WHERE o.customer_id = c.id)"
            + "         AS order_count"
            + " FROM shop_customer c ORDER BY c.id";

        // Object[] 방식: 인덱스로 접근, 타입 캐스팅 필요
        @SuppressWarnings("unchecked")
        List<Object[]> rawRows = em.createNativeQuery(sql).getResultList();
        System.out.println("  [Object[] 방식]");
        rawRows.forEach(row ->
            System.out.printf("    id=%s, name=%s, city=%s, orderCount=%s%n",
                row[0], row[1], row[2], row[3]));

        // DTO 방식: 필드명으로 접근, 타입 안전
        @SuppressWarnings("unchecked")
        List<CustomerSummary> dtoRows = em.createNativeQuery(sql, CUSTOMER_SUMMARY_MAPPING)
            .getResultList();
        System.out.println("  [DTO 방식]");
        dtoRows.forEach(s ->
            System.out.printf("    name=%-6s | city=%-6s | 주문=%d건%n",
                s.getName(), s.getCity(), s.getOrderCount()));
      }

      System.out.println("\n[정리: @SqlResultSetMapping]");
      System.out.println("  정의 위치   : 엔티티 클래스 상단 @SqlResultSetMapping 어노테이션");
      System.out.println("  ConstructorResult → DTO 생성자 매핑 (컬럼명·타입 일치 필수)");
      System.out.println("  EntityResult      → 엔티티 필드 매핑");
      System.out.println("  ColumnResult      → 스칼라 단일 컬럼 매핑");
      System.out.println("  @NamedNativeQuery : query + resultSetMapping 을 함께 묶어 재사용");
      System.out.println("  장점            : 엔티티 없이 복잡한 쿼리 결과를 타입 안전하게 수신");
    }
  }
}
