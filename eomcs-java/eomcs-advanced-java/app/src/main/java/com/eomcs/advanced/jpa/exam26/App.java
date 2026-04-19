package com.eomcs.advanced.jpa.exam26;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam26 - N+1 문제 재현
//
// N+1 문제 발생 순서:
//   1. findAll() → SELECT * FROM shop_customer  (1번 쿼리)
//   2. c.getOrders() 최초 접근 시마다
//      → SELECT * FROM shop_orders WHERE customer_id = ?  (고객 수만큼 N번)
//   3. 결과: 총 1 + N번 쿼리 실행
//
// 콘솔 출력에서 Hibernate가 실행한 SELECT 쿼리 횟수를 직접 확인한다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      System.out.println("=== N+1 문제 재현 ===");
      System.out.println(">> [쿼리 1] findAll() 실행 - 고객 목록 조회");

      // 1번 쿼리: SELECT * FROM shop_customer
      List<Customer> customers = repo.findAll();
      System.out.println("   고객 " + customers.size() + "명 조회 완료\n");

      System.out.println(">> [쿼리 N] 각 고객의 주문 수 출력 - 고객마다 추가 쿼리 발생");
      for (Customer c : customers) {
        // getOrders().size() 호출 시 LAZY 로딩으로 추가 SELECT 실행
        // → SELECT * FROM shop_orders WHERE customer_id = ?
        int orderCount = c.getOrders().size();
        System.out.printf("   %s → 주문 %d건%n", c.getName(), orderCount);
      }

      System.out.println();
      System.out.println("[결론] 총 " + (1 + customers.size()) + "번 쿼리 실행 (1 + " + customers.size() + ")");
      System.out.println("  고객이 100명이면 → 101번, 1000명이면 → 1001번 쿼리 발생");
      System.out.println("  → 성능이 데이터 건수에 비례하여 선형 저하");
      System.out.println();
      System.out.println("[해결법]");
      System.out.println("  JOIN FETCH  : App2 참고 - findAllWithOrders()");
      System.out.println("  @EntityGraph: App2 참고 - findAllWithOrdersGraph()");
    }
  }
}
