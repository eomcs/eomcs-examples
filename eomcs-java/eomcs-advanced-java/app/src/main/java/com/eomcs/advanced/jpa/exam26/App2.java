package com.eomcs.advanced.jpa.exam26;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam26 - N+1 문제 해결: JOIN FETCH / @EntityGraph
//
// 해결법 1. JOIN FETCH
//   JPQL: SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders
//   → Customer와 orders를 단 1번의 쿼리(INNER JOIN)로 한꺼번에 로드
//   → DISTINCT: 조인으로 인한 Customer 중복 제거
//   주의: 주문이 없는 고객은 결과에서 제외됨 (INNER JOIN)
//         주문이 없는 고객도 포함하려면 LEFT JOIN FETCH 사용
//
// 해결법 2. @EntityGraph
//   @EntityGraph(attributePaths = {"orders"})
//   → LEFT OUTER JOIN FETCH로 쿼리 생성
//   → 주문이 없는 고객도 포함 (LEFT JOIN)
//   → 어노테이션으로 선언 가능 → 재사용성↑
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App2
//
public class App2 {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 해결법 1: JOIN FETCH ───────────────────────────────────────────
      System.out.println("=== 해결법 1: JOIN FETCH ===");
      System.out.println(">> findAllWithOrders() 실행 - 쿼리 1번으로 완료\n");

      List<Customer> withFetch = repo.findAllWithOrders();
      for (Customer c : withFetch) {
        // 이미 orders가 메모리에 로드되어 있음 → 추가 쿼리 없음
        System.out.printf("   %s → 주문 %d건%n", c.getName(), c.getOrders().size());
      }
      System.out.println("   [쿼리 1번] SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders");
      System.out.println("   주의: 주문이 없는 고객은 결과에서 제외 (INNER JOIN)");

      // ── 해결법 2: @EntityGraph ─────────────────────────────────────────
      System.out.println("\n=== 해결법 2: @EntityGraph ===");
      System.out.println(">> findAllWithOrdersGraph() 실행 - 쿼리 1번으로 완료\n");

      List<Customer> withGraph = repo.findAllWithOrdersGraph();
      for (Customer c : withGraph) {
        System.out.printf("   %s → 주문 %d건%n", c.getName(), c.getOrders().size());
      }
      System.out.println("   [쿼리 1번] LEFT OUTER JOIN FETCH (주문 없는 고객도 포함)");

      System.out.println("\n[정리]");
      System.out.println("  JOIN FETCH        : JPQL 직접 작성, INNER JOIN (주문 없는 고객 제외)");
      System.out.println("  LEFT JOIN FETCH   : JPQL 직접 작성, 주문 없는 고객도 포함");
      System.out.println("  @EntityGraph      : 어노테이션 선언, LEFT OUTER JOIN 자동 생성");
      System.out.println("  공통점            : 단 1번의 쿼리로 N+1 해결");
    }
  }
}
