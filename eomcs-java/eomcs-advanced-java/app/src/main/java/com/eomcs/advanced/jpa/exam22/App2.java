package com.eomcs.advanced.jpa.exam22;

import java.time.LocalDateTime;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam22 - @Query & Paging: @Modifying (UPDATE / DELETE)
//
// 이 예제에서 확인할 내용:
//   1. @Modifying + @Query → JPQL UPDATE / DELETE 벌크 연산
//   2. clearAutomatically = true → 1차 캐시 초기화 (stale 데이터 방지)
//   3. 벌크 연산은 영속성 컨텍스트를 거치지 않으므로 캐시 초기화가 중요하다
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App2
//
public class App2 {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 테스트용 데이터 삽입 ───────────────────────────────────────────
      Customer c1 = new Customer();
      c1.setName("테스트A");
      c1.setEmail("bulk_a_" + System.currentTimeMillis() + "@test.com");
      c1.setCity("대전");
      c1.setCreatedAt(LocalDateTime.now());
      c1.setUpdatedAt(LocalDateTime.now());
      repo.save(c1);

      Customer c2 = new Customer();
      c2.setName("테스트B");
      c2.setEmail("bulk_b_" + System.currentTimeMillis() + "@test.com");
      c2.setCity("대전");
      c2.setCreatedAt(LocalDateTime.now());
      c2.setUpdatedAt(LocalDateTime.now());
      repo.save(c2);

      System.out.println("=== 테스트 데이터 저장 ===");
      repo.findByCity("대전").forEach(c -> System.out.println("  " + c));

      // ── 1. @Modifying UPDATE - 벌크 도시 변경 ─────────────────────────
      System.out.println("\n=== 1. @Modifying UPDATE - 도시 일괄 변경 ===");
      // 반환값: 변경된 행 수
      // clearAutomatically=true → UPDATE 후 1차 캐시 초기화 → 이후 조회 시 DB에서 재조회
      int updated = repo.updateCity("대전", "광주");
      System.out.println("  변경된 행 수: " + updated);
      repo.findByCity("광주").forEach(c -> System.out.println("  변경 후: " + c));

      // ── 2. @Modifying DELETE - 이메일 패턴으로 삭제 ───────────────────
      System.out.println("\n=== 2. @Modifying DELETE - 이메일 패턴 삭제 ===");
      int deleted = repo.deleteByEmailPattern("bulk_%");
      System.out.println("  삭제된 행 수: " + deleted);

      System.out.println("\n[정리]");
      System.out.println("  @Modifying               : SELECT 외 쿼리(UPDATE/DELETE) 실행");
      System.out.println("  clearAutomatically=true  : 실행 후 1차 캐시 초기화");
      System.out.println("  반환값 int               : 영향받은 행 수");
      System.out.println("  벌크 연산                : 영속성 컨텍스트를 거치지 않고 DB 직접 수정");
    }
  }
}
