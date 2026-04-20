package com.eomcs.advanced.jpa.exam29;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam29 - 배치 처리: Bulk UPDATE / DELETE & JDBC 배치 INSERT
//
// 1. Bulk UPDATE (@Modifying JPQL)
//    UPDATE Customer c SET c.city = :new WHERE c.city = :old
//    → 1차 캐시 거치지 않고 DB에 직접 실행 → 매우 빠름
//    → 영향받은 행 수(int) 반환
//
// 2. JDBC 배치 INSERT (hibernate.jdbc.batch_size)
//    save()를 반복 호출해도 JDBC 레벨에서 addBatch() → executeBatch()로 묶어서 전송
//    → N번의 네트워크 왕복 → N/batch_size번으로 감소
//    → flush()와 clear()를 주기적으로 호출해 1차 캐시 메모리 관리 필수
//
// ★ 주의 사항
//
// [1] GenerationType.IDENTITY → JDBC 배치 INSERT 비활성화
//     Hibernate는 IDENTITY 전략에서 INSERT 직후 DB가 생성한 ID를 즉시 받아야 한다.
//     → addBatch() 불가, 건마다 즉시 executeUpdate() 호출 → batch_size 무시됨
//     배치 INSERT를 실제로 활성화하려면 GenerationType.SEQUENCE 사용 필수.
//     (이 예제의 Customer는 IDENTITY를 사용하므로 배치가 실제로 동작하지 않는다)
//
// [2] flush() + clear() 미호출 → OutOfMemoryError 위험
//     batch_size마다 em.flush() + em.clear()를 수동 호출하지 않으면
//     1차 캐시에 엔티티가 계속 쌓여 대량 처리 시 OOM이 발생한다.
//
// [3] order_inserts/updates 미설정 → 배치 중단
//     엔티티 타입이 섞이면(A→B→A) 배치가 중간에 끊긴다.
//     order_inserts=true로 같은 테이블끼리 정렬해야 배치가 연속으로 묶인다.
//
// [4] clearAutomatically 미설정 → stale 데이터
//     Bulk UPDATE 후 1차 캐시가 초기화되지 않으면 DB 변경이 캐시에 반영되지 않아
//     findById() 등에서 변경 전 값이 반환된다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository   repo  = ctx.getBean(CustomerRepository.class);
      EntityManagerFactory emf   = ctx.getBean(EntityManagerFactory.class);
      SessionFactory       sf    = emf.unwrap(SessionFactory.class);
      Statistics           stats = sf.getStatistics();

      // ── 1. Bulk UPDATE (@Modifying) ────────────────────────────────────
      System.out.println("=== 1. Bulk UPDATE (@Modifying) ===");
      System.out.println(">> 서울 → 수원 일괄 변경");
      int updated = repo.bulkUpdateCity("서울", "수원");
      System.out.println("   변경된 행 수: " + updated);

      // 변경 확인 (clearAutomatically=true로 1차 캐시 초기화됨)
      List<Customer> suwon = repo.findAll().stream()
          .filter(c -> "수원".equals(c.getCity())).toList();
      System.out.println("   수원 고객 수: " + suwon.size());

      // 원복
      repo.bulkUpdateCity("수원", "서울");
      System.out.println("   원복 완료 (수원 → 서울)");

      // ── 2. JDBC 배치 INSERT ────────────────────────────────────────────
      System.out.println("\n=== 2. JDBC 배치 INSERT (batch_size=50) ===");
      System.out.println(">> 100건 INSERT - JDBC 배치로 묶어서 전송");

      stats.clear();
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
      try {
        int batchSize = 50;
        for (int i = 1; i <= 100; i++) {
          Customer c = new Customer(
              "배치고객" + i,
              "batch_" + System.currentTimeMillis() + "_" + i + "@test.com",
              i % 2 == 0 ? "서울" : "부산");
          em.persist(c);

          // batch_size마다 flush + clear: 1차 캐시 메모리 관리
          if (i % batchSize == 0) {
            em.flush();
            em.clear();
            System.out.println("   [flush+clear] " + i + "건 처리");
          }
        }
        em.getTransaction().commit();
      } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;
      } finally {
        em.close();
      }

      System.out.println("   삽입 완료: JDBC batch_size=50으로 묶어 전송");
      System.out.println("   Entity insert count: " + stats.getEntityInsertCount());

      // ── 3. Bulk DELETE ─────────────────────────────────────────────────
      System.out.println("\n=== 3. Bulk DELETE ===");
      long beforeCount = repo.countByEmailPattern("batch\\_%");
      System.out.println(">> 삭제 전 배치 고객 수: " + beforeCount);
      int deleted = repo.bulkDeleteByEmailPattern("batch_%");
      System.out.println("   삭제된 행 수: " + deleted);

      System.out.println("\n[정리]");
      System.out.println("  Bulk UPDATE/DELETE : JPQL로 1번 쿼리, 1차 캐시 무시 → clearAutomatically 필수");
      System.out.println("  JDBC 배치 INSERT   : batch_size마다 묶어서 전송, flush+clear로 메모리 관리");
      System.out.println("  StatelessSession   : App2 참고 - 1차 캐시 없는 경량 세션");
    }
  }
}
