package com.eomcs.advanced.jpa.exam08;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// exam08 - 영속성 컨텍스트: 쓰기 지연(Write-Behind) & flush()
//
// 쓰기 지연(Write-Behind / Transactional Write-Behind):
// - persist(), remove() 등의 변경 작업은 즉시 SQL을 실행하지 않는다.
// - 변경 내용을 "쓰기 지연 SQL 저장소"에 모았다가 flush() 시 한꺼번에 실행한다.
//
// flush():
// - 영속성 컨텍스트의 변경 내용을 DB에 반영(SQL 실행)하는 작업이다.
// - commit() 직전에 자동 호출된다.
// - em.flush()로 수동 호출도 가능하다.
// - flush()는 트랜잭션을 끝내지 않는다. commit()과 다르다.
//
// flush 시점 (FlushModeType):
//   AUTO (기본값): JPQL 쿼리 실행 직전, commit() 직전에 자동 flush
//   COMMIT:        commit() 직전에만 flush (JPQL 실행 전에는 flush 안 함)
//
// IDENTITY 전략 예외:
//   IDENTITY 전략은 persist() 시 즉시 INSERT → id 채번
//   → 쓰기 지연 불가 (id를 즉시 알아야 하기 때문)
//   나머지 전략(SEQUENCE 등)은 쓰기 지연이 적용된다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App3
//
public class App3 {

  public static void main(String[] args) {
    String host = System.getenv("DB_HOSTNAME");
    String port = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url", url);
    props.put("jakarta.persistence.jdbc.user", System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("exam08", props)) {

      // ── 1. persist() 여러 번 → flush 시 일괄 처리 ─────────────────────
      System.out.println("=== [쓰기 지연] persist() 3번 → commit() 시 SQL 3개 일괄 실행 ===");
      System.out.println("  IDENTITY 전략은 persist() 시 즉시 INSERT");
      System.out.println("  (SEQUENCE 전략이면 commit() 시에 모아서 실행됨)");
      System.out.println();

      Long id1, id2, id3;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer c1 = new Customer();
        c1.setName("고객A");
        c1.setEmail("a_" + System.currentTimeMillis() + "@t.com");
        c1.setCity("서울");
        c1.setCreatedAt(LocalDateTime.now());
        System.out.println("  persist(c1) 호출...");
        em.persist(c1);
        // IDENTITY 전략: 여기서 즉시 INSERT → id 채번

        Customer c2 = new Customer();
        c2.setName("고객B");
        c2.setEmail("b_" + System.currentTimeMillis() + "@t.com");
        c2.setCity("부산");
        c2.setCreatedAt(LocalDateTime.now());
        System.out.println("  persist(c2) 호출...");
        em.persist(c2);

        Customer c3 = new Customer();
        c3.setName("고객C");
        c3.setEmail("c_" + System.currentTimeMillis() + "@t.com");
        c3.setCity("대구");
        c3.setCreatedAt(LocalDateTime.now());
        System.out.println("  persist(c3) 호출...");
        em.persist(c3);

        System.out.println("  ↓ commit() 호출 (SEQUENCE 전략이면 여기서 3개 INSERT 일괄 실행)");
        tx.commit();

        id1 = c1.getId();
        id2 = c2.getId();
        id3 = c3.getId();
        System.out.printf("  저장 완료: id1=%d, id2=%d, id3=%d%n", id1, id2, id3);
      }

      // ── 2. em.flush() 수동 호출 ───────────────────────────────────────
      System.out.println("\n=== [flush] 수동 flush() 호출 ===");
      System.out.println("  flush(): SQL을 DB에 반영하지만 트랜잭션은 유지 (commit 아님)");

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, id1);
        managed.setCity("인천");
        System.out.println("  setCity('인천') 후 flush() 수동 호출...");

        em.flush();
        // UPDATE SQL 실행됨 (show_sql로 확인)
        // 트랜잭션은 아직 열려있음 → rollback 가능
        System.out.println("  flush() 완료 → UPDATE 실행됨 (트랜잭션은 유지)");
        System.out.println("  아직 rollback 가능한 상태");

        tx.commit(); // 최종 커밋
        System.out.println("  commit() → 확정");
      }

      // ── 3. JPQL 실행 직전 자동 flush (FlushModeType.AUTO) ────────────
      System.out.println("\n=== [AUTO flush] JPQL 실행 전 자동 flush ===");

      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 영속 상태 엔티티 수정 (아직 flush 안 됨)
        Customer managed = em.find(Customer.class, id2);
        managed.setCity("광주");
        System.out.println("  setCity('광주') 호출 (아직 UPDATE 미실행)");

        // JPQL 실행 전 AUTO 모드에서 자동 flush → UPDATE 먼저 실행 후 SELECT
        System.out.println("  JPQL 실행 직전 → 자동 flush → UPDATE 후 SELECT");
        long count =
            em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
        System.out.println("  전체 고객 수: " + count);

        tx.commit();
      }

      // ── 정리: 테스트 데이터 삭제 ──────────────────────────────────────
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (Long id : new Long[] {id1, id2, id3}) {
          Customer c = em.find(Customer.class, id);
          if (c != null) em.remove(c);
        }
        System.out.println("commit() 호출 → 삭제 SQL 일괄 실행");
        tx.commit();
      }

      System.out.println("\n[정리]");
      System.out.println("  쓰기 지연: 변경 작업을 모았다가 flush() 시 한꺼번에 SQL 실행");
      System.out.println("  flush() ≠ commit(): flush는 SQL 실행, commit은 트랜잭션 확정");
      System.out.println("  AUTO 모드: JPQL 실행 전, commit 전 자동 flush");
      System.out.println("  IDENTITY 전략은 persist() 시 즉시 INSERT (쓰기 지연 불가)");
    }
  }
}
