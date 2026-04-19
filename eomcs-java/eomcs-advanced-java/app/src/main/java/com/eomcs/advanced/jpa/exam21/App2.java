package com.eomcs.advanced.jpa.exam21;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;

// exam21 - Spring Data JPA 기초: 파생 쿼리(Derived Query) & 정렬
//
// Spring Data JPA는 메서드 이름을 파싱해 JPQL을 자동 생성한다.
//
//   findByCity("서울")
//   → SELECT c FROM Customer c WHERE c.city = '서울'
//
//   findByCityOrderByNameAsc("서울")
//   → SELECT c FROM Customer c WHERE c.city = '서울' ORDER BY c.name ASC
//
//   findByNameContaining("길")
//   → SELECT c FROM Customer c WHERE c.name LIKE '%길%'
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App2
//
public class App2 {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 1. 도시별 조회 ─────────────────────────────────────────────────
      System.out.println("=== 1. findByCity (WHERE city = ?) ===");
      List<Customer> seoulCustomers = repo.findByCity("서울");
      seoulCustomers.forEach(c -> System.out.println("  " + c));

      // ── 2. 도시별 이름 오름차순 ────────────────────────────────────────
      System.out.println("\n=== 2. findByCityOrderByNameAsc (WHERE city = ? ORDER BY name ASC) ===");
      List<Customer> sorted = repo.findByCityOrderByNameAsc("서울");
      sorted.forEach(c -> System.out.println("  " + c));

      // ── 3. 이메일로 단건 조회 ──────────────────────────────────────────
      System.out.println("\n=== 3. findByEmail (WHERE email = ?) → Optional ===");
      Optional<Customer> byEmail = repo.findByEmail("hong@example.com");
      byEmail.ifPresentOrElse(
          c -> System.out.println("  찾음: " + c),
          ()  -> System.out.println("  없음"));

      // ── 4. 이름 포함 검색 ──────────────────────────────────────────────
      System.out.println("\n=== 4. findByNameContaining (WHERE name LIKE '%?%') ===");
      List<Customer> containing = repo.findByNameContaining("길");
      containing.forEach(c -> System.out.println("  " + c));

      // ── 5. 도시별 개수 ─────────────────────────────────────────────────
      System.out.println("\n=== 5. countByCity (SELECT COUNT(*) WHERE city = ?) ===");
      System.out.println("  서울 고객 수: " + repo.countByCity("서울"));

      // ── 6. 이메일 존재 여부 ────────────────────────────────────────────
      System.out.println("\n=== 6. existsByEmail (SELECT COUNT(*) > 0 WHERE email = ?) ===");
      System.out.println("  hong@example.com 존재: " + repo.existsByEmail("hong@example.com"));
      System.out.println("  없는@이메일.com 존재: " + repo.existsByEmail("없는@이메일.com"));

      // ── 7. Sort 객체로 동적 정렬 ───────────────────────────────────────
      System.out.println("\n=== 7. findAll(Sort) - 동적 정렬 ===");
      // Sort.by("필드명"): 오름차순 / .descending(): 내림차순
      List<Customer> byName   = repo.findAll(Sort.by("name").ascending());
      List<Customer> byCity   = repo.findAll(Sort.by("city").descending().and(Sort.by("name")));
      System.out.println("  이름 오름차순:");
      byName.forEach(c -> System.out.println("    " + c));
      System.out.println("  도시 내림차순 → 이름 오름차순:");
      byCity.forEach(c -> System.out.println("    " + c));

      System.out.println("\n[정리]");
      System.out.println("  findBy{필드}        → WHERE 필드 = ?");
      System.out.println("  findBy{필드}Containing → WHERE 필드 LIKE '%?%'");
      System.out.println("  countBy{필드}       → SELECT COUNT(*) WHERE ...");
      System.out.println("  existsBy{필드}      → SELECT COUNT(*) > 0 WHERE ...");
      System.out.println("  findAll(Sort.by())  → ORDER BY 동적 지정");
    }
  }
}
