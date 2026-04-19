package com.eomcs.advanced.jpa.exam09;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam09 - 연관관계 매핑: 양방향 @OneToMany + @ManyToOne
//
// 양방향(Bidirectional) 매핑:
// - Order → Customer (@ManyToOne, FK 주인)
// - Customer → List<Order> (@OneToMany, mappedBy="customer")
//
// 연관관계의 주인(Owner):
// - FK(customer_id)를 보유한 Order.customer 필드가 주인이다.
// - 주인만이 연관관계 변경을 DB에 반영한다.
// - Customer.orders는 mappedBy로 선언 → 조회 전용, DB 반영 안 됨.
//
// 양방향 설정 시 주의사항:
// - 양쪽 모두 연관관계를 설정해야 한다.
//   → order.setCustomer(customer)                    // FK 설정 (주인)
//   → customer.getOrders().add(order)               // 컬렉션 설정 (비주인)
// - 편의 메서드(addOrder)를 사용하면 실수를 줄일 수 있다.
// - 설정 순서가 틀리면 1차 캐시에서 컬렉션 데이터가 불일치할 수 있다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App2
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
        Persistence.createEntityManagerFactory("exam09", props)) {

      // ── 1. Customer → orders 양방향 탐색 ─────────────────────────────────
      System.out.println("=== [양방향] Customer.getOrders()로 주문 탐색 ===");

      try (EntityManager em = emf.createEntityManager()) {
        // 고객 조회 (id=1: 홍길동)
        Customer customer = em.find(Customer.class, 1L);
        System.out.println("고객: " + customer);

        // customer.getOrders() 호출 → 지연 로딩 → SELECT 실행
        List<Order> orders = customer.getOrders();
        System.out.println("주문 수: " + orders.size());
        orders.forEach(o -> System.out.println("  " + o));
      }

      // ── 2. 모든 고객과 주문 수 출력 ─────────────────────────────────────
      System.out.println("\n=== [양방향] 모든 고객과 각 주문 수 ===");

      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> customers = em
            .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
            .getResultList();

        for (Customer c : customers) {
          int orderCount = c.getOrders().size(); // 지연 로딩 → SELECT 실행
          System.out.printf("  %s → 주문 %d건%n", c, orderCount);
        }
        System.out.println();
        System.out.println("  위 코드는 고객 수 N + 주문 조회 N 번 = N+1 쿼리 문제 발생!");
        System.out.println("  해결: JOIN FETCH (exam18에서 다룸)");
      }

      // ── 3. 연관관계 주인(Order)으로 탐색 vs 비주인(Customer)으로 탐색 ─────
      System.out.println("\n=== [주인 vs 비주인] FK 설정 영향 비교 ===");

      try (EntityManager em = emf.createEntityManager()) {
        Customer customer = em.find(Customer.class, 1L);
        Order order1 = em.find(Order.class, 1L);
        Order order2 = em.find(Order.class, 2L);

        System.out.println("order1.getCustomer().getId(): " + order1.getCustomer().getId());
        System.out.println("order2.getCustomer().getId(): " + order2.getCustomer().getId());
        System.out.println("customer.getOrders().size(): " + customer.getOrders().size());
      }

      // ── 4. JPQL: 고객과 주문을 함께 조회 (양방향 JOIN) ─────────────────
      System.out.println("\n=== [JPQL] 고객과 주문 함께 조회 ===");

      try (EntityManager em = emf.createEntityManager()) {
        // Customer → orders 방향으로 JOIN
        List<Customer> customersWithOrders = em
            .createQuery(
                "SELECT DISTINCT c FROM Customer c JOIN c.orders o ORDER BY c.id",
                Customer.class)
            .getResultList();

        System.out.println("주문이 있는 고객:");
        customersWithOrders.forEach(c ->
            System.out.printf("  %s (주문 %d건)%n", c, c.getOrders().size()));
      }

      System.out.println("\n[정리]");
      System.out.println("  양방향 = @ManyToOne(주인, FK) + @OneToMany(mappedBy, 비주인)");
      System.out.println("  주인(Order.customer): DB FK 반영. 이 값만 INSERT/UPDATE에 사용됨");
      System.out.println("  비주인(Customer.orders): mappedBy → 조회 전용, DB 반영 안 됨");
      System.out.println("  양쪽 모두 설정: order.setCustomer(c) + c.getOrders().add(order)");
      System.out.println("  편의 메서드 addOrder()를 사용하면 두 작업을 한 번에 처리");
    }
  }
}
