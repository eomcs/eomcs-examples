package com.eomcs.advanced.jpa.exam21;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam21 - Spring Data JPA 기초: 기본 CRUD (save / findById / findAll / deleteById)
//
// Spring Data JPA 동작 흐름:
//   1. AnnotationConfigApplicationContext → JpaConfig 읽어 빈 생성
//   2. @EnableJpaRepositories → CustomerRepository 인터페이스의 구현체 자동 생성
//   3. 리포지토리 메서드 호출 → Spring Data가 JPQL 생성 → Hibernate 실행 → SQL
//
// 이 예제에서 확인할 내용:
//   1. save()     → INSERT / UPDATE
//   2. findById() → SELECT WHERE id = ?   (Optional 반환)
//   3. findAll()  → SELECT * (전체 조회)
//   4. deleteById()→ DELETE WHERE id = ?
//   5. count()    → SELECT COUNT(*)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App
//
public class App {

  public static void main(String[] args) {

    // AnnotationConfigApplicationContext: Java @Configuration 클래스를 읽어 Spring 컨테이너 생성
    // try-with-resources 사용 → close() 시 HikariCP 커넥션 풀까지 정리
    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 1. 저장 (INSERT) ──────────────────────────────────────────────
      System.out.println("=== 1. 고객 저장 (save → INSERT) ===");
      Customer c = new Customer();
      c.setName("Spring테스터");
      c.setEmail("spring_" + System.currentTimeMillis() + "@test.com");
      c.setCity("서울");
      c.setStreet("테헤란로 100");
      c.setZipcode("06000");
      c.setCreatedAt(LocalDateTime.now());
      c.setUpdatedAt(LocalDateTime.now());

      // save(): id == null → INSERT, id != null이고 DB에 존재 → UPDATE
      Customer saved = repo.save(c);
      System.out.println("  저장 완료: " + saved);
      Long savedId = saved.getId();

      // ── 2. 기본 키 조회 ───────────────────────────────────────────────
      System.out.println("\n=== 2. 기본 키로 조회 (findById → SELECT) ===");
      Optional<Customer> found = repo.findById(savedId);
      found.ifPresent(fc -> System.out.println("  조회: " + fc));
      // Optional이므로 없을 때 NPE 없이 처리 가능
      System.out.println("  존재 여부(existsById): " + repo.existsById(savedId));

      // ── 3. 전체 조회 ──────────────────────────────────────────────────
      System.out.println("\n=== 3. 전체 조회 (findAll → SELECT *) ===");
      List<Customer> all = repo.findAll();
      System.out.println("  전체 고객 수: " + all.size());
      all.forEach(cu -> System.out.println("  " + cu));

      // ── 4. 수정 (UPDATE) ──────────────────────────────────────────────
      System.out.println("\n=== 4. 수정 (save → UPDATE) ===");
      // findById로 조회 후 값을 변경하고 save() 재호출 → UPDATE
      Customer toUpdate = repo.findById(savedId).orElseThrow();
      toUpdate.setCity("부산");
      toUpdate.setUpdatedAt(LocalDateTime.now());
      repo.save(toUpdate);
      System.out.println("  수정 후 city: " + repo.findById(savedId).orElseThrow().getCity());

      // ── 5. 개수 조회 ──────────────────────────────────────────────────
      System.out.println("\n=== 5. 개수 조회 (count) ===");
      System.out.println("  전체 고객 수: " + repo.count());

      // ── 6. 삭제 ───────────────────────────────────────────────────────
      System.out.println("\n=== 6. 삭제 (deleteById → DELETE) ===");
      repo.deleteById(savedId);
      System.out.println("  삭제 후 존재 여부: " + repo.existsById(savedId));

      System.out.println("\n[정리]");
      System.out.println("  JpaRepository.save()       → id 없으면 INSERT, 있으면 UPDATE");
      System.out.println("  JpaRepository.findById()   → Optional<T> 반환 (NPE 방지)");
      System.out.println("  JpaRepository.findAll()    → List<T> 전체 조회");
      System.out.println("  JpaRepository.deleteById() → DELETE WHERE id = ?");
      System.out.println("  JpaRepository.count()      → SELECT COUNT(*)");
    }
  }
}
