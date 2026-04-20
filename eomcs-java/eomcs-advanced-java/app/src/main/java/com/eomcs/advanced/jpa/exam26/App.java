package com.eomcs.advanced.jpa.exam26;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
// 주의: TransactionTemplate으로 전체 작업을 하나의 트랜잭션으로 감싸야 한다.
//   그렇지 않으면 findAll() 후 Session이 닫혀 LAZY 로딩 시
//   LazyInitializationException이 발생한다.
//
//   Spring Data JPA의 repository 메서드는 각자 독립된 트랜잭션 안에서 실행된다.
//   메서드가 리턴되면 트랜잭션이 종료되고 Hibernate Session이 닫힌다.
//   따라서 findAll()이 반환된 뒤 루프에서 LAZY 컬렉션에 접근하면
//   "could not initialize proxy - no Session" 오류가 발생한다.
//
//   TransactionTemplate으로 findAll()과 루프를 같은 트랜잭션(같은 Session) 안에서
//   실행하면 Session이 열려 있는 동안 LAZY 로딩이 정상 동작하고,
//   N+1 문제를 의도대로 재현할 수 있다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // TransactionTemplate: 트랜잭션 경계를 코드로 직접 제어하는 Spring 유틸리티
      // executeWithoutResult() 안에서 실행되는 모든 작업은 하나의 트랜잭션(Session)을 공유한다.
      PlatformTransactionManager txManager = ctx.getBean(PlatformTransactionManager.class);
      TransactionTemplate txTemplate = new TransactionTemplate(txManager);

      txTemplate.executeWithoutResult(status -> {
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
      });
    }
  }
}
