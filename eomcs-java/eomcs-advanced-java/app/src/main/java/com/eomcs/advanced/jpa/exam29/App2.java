package com.eomcs.advanced.jpa.exam29;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam29 - StatelessSession 배치 처리
//
// StatelessSession (경량 세션):
//   - 1차 캐시(영속성 컨텍스트) 없음 → 메모리 사용량 최소
//   - Dirty Checking 없음 → 명시적 update() 호출 필요
//   - @PrePersist, @PostLoad 등 엔티티 이벤트 발생 안 함
//   - 2차 캐시 사용 안 함
//   - 대용량 데이터 INSERT / 일괄 처리에 최적
//
// 일반 EntityManager vs StatelessSession 비교:
//   EntityManager    : 1차 캐시 관리 필요, flush+clear 반복, 이벤트 발생
//   StatelessSession : 캐시 없음, 단순 JDBC에 가까운 동작, 대량 처리에 유리
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App2
//
public class App2 {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository   repo = ctx.getBean(CustomerRepository.class);
      EntityManagerFactory emf  = ctx.getBean(EntityManagerFactory.class);
      SessionFactory       sf   = emf.unwrap(SessionFactory.class);

      // ── StatelessSession으로 대량 INSERT ──────────────────────────────
      System.out.println("=== StatelessSession 배치 INSERT ===");
      System.out.println(">> 50건 INSERT - 1차 캐시 없이 직접 DB 전송\n");

      try (StatelessSession ss = sf.openStatelessSession()) {
        Transaction tx = ss.beginTransaction();
        try {
          for (int i = 1; i <= 50; i++) {
            Customer c = new Customer(
                "무상태" + i,
                "stateless_" + System.currentTimeMillis() + "_" + i + "@test.com",
                i % 2 == 0 ? "서울" : "대전");
            // StatelessSession.insert(): 1차 캐시 없이 즉시 DB INSERT
            ss.insert(c);

            if (i % 10 == 0) {
              System.out.println("   [진행] " + i + "건 처리");
            }
          }
          tx.commit();
          System.out.println("   INSERT 완료: 50건");
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── StatelessSession으로 대량 UPDATE ──────────────────────────────
      System.out.println("\n=== StatelessSession 배치 UPDATE ===");

      try (StatelessSession ss = sf.openStatelessSession()) {
        Transaction tx = ss.beginTransaction();
        try {
          // StatelessSession에서는 JPQL executeUpdate() 사용 가능
          int updated = ss.createMutationQuery(
              "UPDATE Customer c SET c.city = '인천' WHERE c.city = '대전' AND c.email LIKE 'stateless_%'")
              .executeUpdate();
          System.out.println("   대전 → 인천 변경: " + updated + "건");
          tx.commit();
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 정리: 삽입된 데이터 삭제 ──────────────────────────────────────
      int deleted = repo.bulkDeleteByEmailPattern("stateless_%");
      System.out.println("\n   정리 완료: " + deleted + "건 삭제");

      System.out.println("\n[정리]");
      System.out.println("  StatelessSession.insert()  : 1차 캐시 없이 즉시 INSERT");
      System.out.println("  StatelessSession.update()  : Dirty Checking 없음, 명시적 UPDATE");
      System.out.println("  StatelessSession.delete()  : 즉시 DELETE");
      System.out.println("  적합한 경우: 대량 데이터 마이그레이션, ETL, 배치 잡");
    }
  }
}
