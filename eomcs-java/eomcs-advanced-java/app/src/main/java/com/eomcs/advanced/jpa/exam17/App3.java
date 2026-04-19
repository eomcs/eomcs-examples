package com.eomcs.advanced.jpa.exam17;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam17 - JPQL 기초: 프로젝션 (Projection)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App3
//
public class App3 {

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

      // ── 1. 엔티티 프로젝션 (전체 엔티티 반환) ────────────────────────────────
      System.out.println("=== 1. 엔티티 프로젝션: SELECT c FROM Customer c ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> customers = em.createQuery(
            "SELECT c FROM Customer c ORDER BY c.id",
            Customer.class)
            .getResultList();

        customers.forEach(c -> System.out.println("  " + c));
        // 반환된 Customer 는 영속성 컨텍스트에서 관리(managed 상태)된다.
      }

      // ── 2. 단일 컬럼 프로젝션 ─────────────────────────────────────────────
      System.out.println("\n=== 2. 단일 컬럼 프로젝션: SELECT c.name ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<String> names = em.createQuery(
            "SELECT c.name FROM Customer c ORDER BY c.id",
            String.class)
            .getResultList();

        names.forEach(name -> System.out.println("  이름: " + name));
        // 특정 컬럼 하나만 필요할 때 사용. 결과가 String 타입 List로 반환.
      }

      // ── 3. 다중 컬럼 프로젝션 (Object[]) ────────────────────────────────────
      System.out.println("\n=== 3. 다중 컬럼 프로젝션: SELECT c.name, c.city ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createQuery(
            "SELECT c.id, c.name, c.city FROM Customer c ORDER BY c.id")
            .getResultList();

        for (Object[] row : rows) {
          System.out.printf("  id=%-3s | name=%-6s | city=%s%n",
              row[0], row[1], row[2]);
        }
        // 여러 컬럼 선택 시 Object[] 배열로 반환. 인덱스 접근이라 타입 안전성이 없다.
        // → 생성자 표현식(App3의 다음 섹션)으로 개선 가능
      }

      // ── 4. 생성자 표현식 (Constructor Expression, new DTO(...)) ─────────────
      System.out.println("\n=== 4. 생성자 표현식: new CustomerNameCityDto(c.name, c.city) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<CustomerNameCityDto> dtos = em.createQuery(
            "SELECT new com.eomcs.advanced.jpa.exam17.CustomerNameCityDto(c.name, c.city)"
            + " FROM Customer c ORDER BY c.id",
            CustomerNameCityDto.class)
            .getResultList();

        dtos.forEach(dto -> System.out.println("  " + dto));
        // 장점: Object[] 대신 타입 안전한 DTO로 받을 수 있다.
        // FQCN(완전 클래스명)을 사용해야 하며, 일치하는 생성자가 반드시 존재해야 한다.
        // - 반환된 DTO 객체는 영속 컨텍스트에서 관리되지 않는다 (비관리 상태).
      }

      // ── 5. 집계 + 그룹 프로젝션 ─────────────────────────────────────────────
      System.out.println("\n=== 5. 집계 프로젝션: 도시별 고객 수 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> stats = em.createQuery(
            "SELECT c.city, COUNT(c)"
            + " FROM Customer c"
            + " WHERE c.city IS NOT NULL"
            + " GROUP BY c.city"
            + " ORDER BY COUNT(c) DESC")
            .getResultList();

        stats.forEach(row ->
            System.out.printf("  도시: %-6s | 고객 수: %s명%n", row[0], row[1]));
      }

      // ── 6. 스칼라 + 연산식 프로젝션 ─────────────────────────────────────────
      System.out.println("\n=== 6. 스칼라 프로젝션: 제품명 + 부가세 계산 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createQuery(
            "SELECT p.name, p.price, p.price * 1.1 FROM Product p ORDER BY p.id")
            .getResultList();

        for (Object[] row : rows) {
          System.out.printf("  %-30s 정가=%s원  부가세포함=%.2f원%n",
              row[0], row[1], row[2]);
        }
        // JPQL에서 사칙연산, 함수(UPPER, LOWER, CONCAT, LENGTH 등)를 SELECT 절에 사용 가능
      }

      System.out.println("\n[정리: JPQL 프로젝션]");
      System.out.println("  엔티티 프로젝션   → managed 상태, 변경 감지(Dirty Checking) 적용");
      System.out.println("  단일 컬럼         → 해당 타입의 List 반환");
      System.out.println("  다중 컬럼 Object[]→ 인덱스 접근, 타입 안전성 없음");
      System.out.println("  생성자 표현식     → 타입 안전 DTO 반환, FQCN + 일치 생성자 필요");
    }
  }
}
