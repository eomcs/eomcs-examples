package com.eomcs.advanced.jpa.exam10;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// exam10 - 엔티티 생명주기
//
// 엔티티의 4가지 상태:
//
//   비영속(New/Transient):
//     new로 생성만 되고, 영속성 컨텍스트와 무관한 상태.
//     DB에 없고, Hibernate가 관리하지 않는다.
//
//   영속(Managed):
//     영속성 컨텍스트가 관리하는 상태.
//     persist(), find(), merge()로 영속 상태가 된다.
//     변경 감지(Dirty Checking), 1차 캐시가 작동한다.
//
//   준영속(Detached):
//     한때 영속이었지만 영속성 컨텍스트에서 분리된 상태.
//     detach(), em.close(), em.clear()로 준영속이 된다.
//     변경 감지가 작동하지 않는다 (필드 수정이 DB에 반영 안 됨).
//     merge()로 다시 영속 상태로 만들 수 있다.
//
//   삭제(Removed):
//     remove()로 삭제 예약된 상태.
//     commit() 시 DELETE SQL 실행.
//
// 상태 전환 다이어그램:
//
//   [비영속] → persist() → [영속]
//   [영속]   → detach()  → [준영속]
//   [영속]   → remove()  → [삭제]  → commit() → DB 삭제
//   [준영속] → merge()   → [영속]  (새 영속 인스턴스 반환)
//   em.close() / em.clear() → 모든 영속 → 준영속
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam10.App
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
        Persistence.createEntityManagerFactory("exam10", props)) {

      // ── 1. 비영속(New) 상태 ────────────────────────────────────────────
      System.out.println("=== 1. 비영속(New) 상태 ===");
      Customer newCustomer = new Customer();
      newCustomer.setName("생명주기테스트");
      newCustomer.setEmail("lc_" + System.currentTimeMillis() + "@test.com");
      newCustomer.setCity("서울");
      newCustomer.setCreatedAt(LocalDateTime.now());
      System.out.println("  new Customer() 직후 id: " + newCustomer.getId()); // null
      System.out.println("  DB와 무관, Hibernate가 관리하지 않음");

      // ── 2. persist() → 영속(Managed) 상태 ─────────────────────────────
      System.out.println("\n=== 2. persist() → 영속(Managed) ===");
      Long savedId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(newCustomer);
        // IDENTITY 전략: 즉시 INSERT → id 채번
        savedId = newCustomer.getId();
        System.out.println("  persist() 후 id: " + savedId);
        System.out.println("  em.contains(): " + em.contains(newCustomer)); // true (영속)

        tx.commit();
        System.out.println("  commit() 완료 → DB에 저장됨");
      }
      // em.close() → newCustomer는 준영속 상태가 됨

      // ── 3. find() → 영속(Managed) 상태 ────────────────────────────────
      System.out.println("\n=== 3. find() → 영속(Managed) ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer managed = em.find(Customer.class, savedId);
        System.out.println("  find() 결과: " + managed);
        System.out.println("  em.contains(): " + em.contains(managed)); // true
      }

      // ── 4. detach() → 준영속(Detached) 상태 ───────────────────────────
      System.out.println("\n=== 4. detach() → 준영속(Detached) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, savedId); // 영속 상태
        System.out.println("  detach() 전 em.contains(): " + em.contains(managed));

        em.detach(managed);                    // 영속성 컨텍스트에서 분리
        System.out.println("  detach() 후 em.contains(): " + em.contains(managed)); // false

        managed.setCity("부산");               // 필드 수정 (준영속이므로 DB 반영 안 됨)
        System.out.println("  setCity('부산') 후 commit()");

        tx.commit();
        // 변경 감지 안 됨 → UPDATE 미실행
        System.out.println("  → 준영속 엔티티 변경은 DB에 반영되지 않음");
      }

      // 확인: city가 변경 안 됨
      try (EntityManager em = emf.createEntityManager()) {
        Customer check = em.find(Customer.class, savedId);
        System.out.println("  DB 확인: " + check); // city는 '서울' 유지
      }

      // ── 5. merge() → 준영속 → 영속(Managed) 복귀 ─────────────────────
      System.out.println("\n=== 5. merge() → 준영속 엔티티를 다시 영속으로 ===");

      // newCustomer는 em.close() 이후 준영속 상태
      newCustomer.setCity("대전");             // 준영속 상태에서 변경

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // merge(): 준영속 엔티티의 값을 DB에서 조회한 영속 엔티티에 복사
        // → 반환값이 새 영속 엔티티다. 파라미터로 넘긴 객체는 여전히 준영속.
        Customer merged = em.merge(newCustomer);
        System.out.println("  merge() 반환값 em.contains(): " + em.contains(merged)); // true
        System.out.println("  원본 em.contains(): " + em.contains(newCustomer));      // false

        tx.commit();
        System.out.println("  commit() → UPDATE 실행 (city='대전' 반영)");
      }

      // 확인: city가 대전으로 변경됨
      try (EntityManager em = emf.createEntityManager()) {
        Customer check = em.find(Customer.class, savedId);
        System.out.println("  DB 확인: " + check); // city='대전'
      }

      // ── 6. em.clear() → 모든 엔티티를 준영속으로 ─────────────────────
      System.out.println("\n=== 6. em.clear() → 1차 캐시 초기화 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer c1 = em.find(Customer.class, savedId);    // DB SELECT
        System.out.println("  clear() 전 em.contains(): " + em.contains(c1));

        em.clear();                            // 영속성 컨텍스트 전체 초기화
        System.out.println("  clear() 후 em.contains(): " + em.contains(c1)); // false

        Customer c2 = em.find(Customer.class, savedId);    // 캐시 소멸 → DB SELECT 재실행
        System.out.println("  clear() 후 find() → DB 재조회: " + c2);
        System.out.println("  c1 == c2 : " + (c1 == c2));  // false (다른 인스턴스)
      }

      // ── 7. remove() → 삭제(Removed) 상태 ─────────────────────────────
      System.out.println("\n=== 7. remove() → 삭제(Removed) → commit() 시 DELETE ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer toRemove = em.find(Customer.class, savedId);
        System.out.println("  remove() 전 em.contains(): " + em.contains(toRemove));
        em.remove(toRemove);                   // 삭제 예약 (아직 DELETE 안 됨)
        System.out.println("  remove() 후 em.contains(): " + em.contains(toRemove)); // false

        tx.commit();                           // DELETE SQL 실행
        System.out.println("  commit() → DELETE 실행 완료");
      }

      // 삭제 확인
      try (EntityManager em = emf.createEntityManager()) {
        Customer gone = em.find(Customer.class, savedId);
        System.out.println("  삭제 후 find(): " + gone);  // null
      }

      System.out.println("\n[정리: 엔티티 생명주기]");
      System.out.println("  비영속(New)    : new Entity()  → Hibernate 관리 외");
      System.out.println("  영속(Managed)  : persist/find/merge → 변경 감지, 1차 캐시 작동");
      System.out.println("  준영속(Detached): detach/close/clear → 변경 감지 중단");
      System.out.println("  삭제(Removed)  : remove() + commit() → DELETE SQL 실행");
      System.out.println();
      System.out.println("  flush()  : 변경 내용을 DB로 전송 (트랜잭션 유지)");
      System.out.println("  clear()  : 영속성 컨텍스트 초기화 → 모든 엔티티 준영속");
      System.out.println("  merge()  : 준영속 → 영속 복귀 (새 영속 인스턴스 반환)");
    }
  }
}
