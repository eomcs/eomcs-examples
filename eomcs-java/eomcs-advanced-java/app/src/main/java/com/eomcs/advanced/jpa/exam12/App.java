package com.eomcs.advanced.jpa.exam12;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam12 - @Embeddable / @Embedded (값 타입 매핑)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam12.App
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
        Persistence.createEntityManagerFactory("exam12", props)) {

      // ── 1. @Embedded Address를 포함한 Customer 저장 ──────────────────────
      System.out.println("=== 1. Customer + Address(Embedded) 저장 ===");
      Long savedId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setName("박지수");
        customer.setEmail("jisu_" + System.currentTimeMillis() + "@test.com");
        customer.setAddress(new Address("인천", "송도대로 77", "21999"));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        em.persist(customer);
        // INSERT INTO shop_customer (name, email, city, street, zipcode, ...)
        // → Address의 city/street/zipcode가 Customer 테이블에 그대로 저장된다.
        tx.commit();
        savedId = customer.getId();
        System.out.println("  저장 완료: " + customer);
      }

      // ── 2. Address 조회 ──────────────────────────────────────────────────
      System.out.println("\n=== 2. 조회 후 Address 접근 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer found = em.find(Customer.class, savedId);
        System.out.println("  고객: " + found);
        // address 필드가 null이 아닌 Address 객체로 복원된다.
        System.out.println("  주소: " + found.getAddress());
        System.out.println("  도시: " + found.getAddress().getCity());
        System.out.println("  도로명: " + found.getAddress().getStreet());
      }

      // ── 3. Address 수정 (Dirty Checking) ────────────────────────────────
      System.out.println("\n=== 3. Address 수정 (변경 감지) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, savedId);
        // Address 객체 전체를 새로 교체하거나 필드를 직접 수정해도 된다.
        managed.setAddress(new Address("서울", "강남대로 100", "06000"));
        managed.setUpdatedAt(LocalDateTime.now());
        System.out.println("  주소 변경: 인천 → 서울");

        tx.commit();
        // Dirty Checking: Address 내부 필드 변경도 감지 → UPDATE SQL 실행
        System.out.println("  commit() → UPDATE 실행 완료");
      }

      // ── 4. 변경 확인 ─────────────────────────────────────────────────────
      System.out.println("\n=== 4. 수정 후 확인 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer check = em.find(Customer.class, savedId);
        System.out.println("  " + check);
      }

      // ── 5. JPQL에서 임베디드 필드 접근 ──────────────────────────────────
      System.out.println("\n=== 5. JPQL: address.city 조건으로 검색 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // JPQL에서 임베디드 타입 필드는 '엔티티필드.임베디드필드' 형태로 접근한다.
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.address.city = :city", Customer.class)
            .setParameter("city", "서울")
            .getResultList();
        System.out.println("  서울 거주 고객 수: " + result.size());
        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 6. Address가 null인 경우 ─────────────────────────────────────────
      System.out.println("\n=== 6. Address = null 저장 (컬럼 전체 NULL) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer noAddr = new Customer();
        noAddr.setName("주소없는고객");
        noAddr.setEmail("noaddr_" + System.currentTimeMillis() + "@test.com");
        // address를 설정하지 않으면 city/street/zipcode 모두 NULL로 저장된다.
        noAddr.setCreatedAt(LocalDateTime.now());
        noAddr.setUpdatedAt(LocalDateTime.now());

        em.persist(noAddr);
        tx.commit();
        System.out.println("  address=null 저장: " + noAddr);
      }

      System.out.println("\n[정리: @Embeddable / @Embedded]");
      System.out.println("  @Embeddable : 값 타입 클래스 선언 (자체 ID 없음)");
      System.out.println("  @Embedded   : 엔티티 내에서 값 타입 필드 선언");
      System.out.println("  특징        : 소유 엔티티 테이블에 컬럼으로 합쳐짐 (별도 테이블 없음)");
      System.out.println("  JPQL       : c.address.city처럼 경로 표현식으로 접근");
    }
  }
}
