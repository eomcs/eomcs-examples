package com.eomcs.advanced.jpa.exam08;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// exam08 - 영속성 컨텍스트: 변경 감지(Dirty Checking)
//
// 변경 감지(Dirty Checking):
// - 영속 상태 엔티티의 필드를 수정하면 commit() 시 Hibernate가 변경을 감지하여
//   자동으로 UPDATE SQL을 생성·실행한다.
// - 개발자가 별도로 update() 메서드를 호출할 필요가 없다.
//
// 작동 원리:
//   1. find() 시 엔티티의 '스냅샷(초기 상태)'을 영속성 컨텍스트에 저장한다.
//   2. commit() 직전 flush() 시, 현재 엔티티 상태와 스냅샷을 비교한다.
//   3. 변경된 필드가 있으면 UPDATE SQL을 생성하여 실행한다.
//   4. 변경이 없으면 UPDATE를 실행하지 않는다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App2
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
        Persistence.createEntityManagerFactory("exam08", props)) {

      // ── 테스트용 고객 저장 ─────────────────────────────────────────────
      Long id;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer c = new Customer();
        c.setName("변경감지테스트");
        c.setEmail("dirty_" + System.currentTimeMillis() + "@test.com");
        c.setCity("서울");
        c.setCreatedAt(LocalDateTime.now());
        c.setUpdatedAt(LocalDateTime.now());
        em.persist(c);
        tx.commit();
        id = c.getId();
        System.out.println("테스트 고객 저장: " + c);
      }

      // ── 1. 변경 감지: UPDATE 자동 실행 ────────────────────────────────
      System.out.println("\n=== [변경 감지] find() → 필드 수정 → commit() ===");
      System.out.println("  (UPDATE SQL 실행 여부를 show_sql 로그로 확인하세요)");

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, id);
        // find() 시점: Hibernate가 스냅샷 저장
        System.out.println("  find() 직후 city: " + managed.getCity());

        managed.setCity("부산");               // 필드 수정 (UPDATE 호출 없음)
        managed.setUpdatedAt(LocalDateTime.now());
        System.out.println("  setCity('부산') 호출");

        tx.commit();
        // commit() 시: 스냅샷과 비교 → city 변경 감지 → UPDATE SQL 자동 실행
        System.out.println("  commit() 완료 → UPDATE가 자동으로 실행되었습니다");
      }

      // 변경 결과 확인
      try (EntityManager em = emf.createEntityManager()) {
        Customer result = em.find(Customer.class, id);
        System.out.println("  확인: " + result);
      }

      // ── 2. 변경 없으면 UPDATE 미실행 ──────────────────────────────────
      System.out.println("\n=== [최적화] 변경 없을 때 UPDATE 미실행 ===");

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, id);
        System.out.println("  find() 후 아무 것도 변경하지 않음");
        // 필드를 수정하지 않음 → 스냅샷과 동일 → UPDATE 실행 안 함

        tx.commit();
        System.out.println("  commit() 완료 → UPDATE가 실행되지 않았습니다 (로그 확인)");
      }

      // ── 3. 롤백 시 변경 무효 ──────────────────────────────────────────
      System.out.println("\n=== [rollback] 롤백 시 변경 무효 ===");

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, id);
        System.out.println("  rollback 전 city: " + managed.getCity());
        managed.setCity("대구");               // 변경 시도
        System.out.println("  setCity('대구') 호출 후 rollback");

        tx.rollback();                         // 변경 취소 → DB에 반영 안 됨
      }

      // rollback 후 확인 → city는 '부산'으로 유지
      try (EntityManager em = emf.createEntityManager()) {
        Customer after = em.find(Customer.class, id);
        System.out.println("  rollback 후 city: " + after.getCity()); // 부산 유지
      }

      // ── 정리: 테스트 데이터 삭제 ──────────────────────────────────────
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Customer.class, id));
        tx.commit();
      }

      System.out.println("\n[정리]");
      System.out.println("  변경 감지: find() 반환 엔티티(영속 상태)의 필드 수정 → commit() 시 UPDATE 자동");
      System.out.println("  스냅샷 비교: 변경 없으면 UPDATE SQL 미실행 (성능 최적화)");
      System.out.println("  rollback: 영속성 컨텍스트 초기화 → DB에 아무 것도 반영 안 됨");
    }
  }
}
