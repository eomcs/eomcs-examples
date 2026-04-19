package com.eomcs.advanced.jpa.exam24;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.domain.Specification;

// exam24 - Specification 패턴: JpaSpecificationExecutor
//
// Specification 패턴: WHERE 조건을 객체로 표현하고 조합(and/or/not)하는 패턴
//
// 이 예제에서 확인할 내용:
//   1. 단일 Specification 적용
//   2. and()로 조건 조합
//   3. or()로 조건 조합
//   4. not()으로 조건 부정
//   5. null 파라미터 처리 (Specification.where(null) → 전체 조회)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam24.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 1. 단일 조건 ────────────────────────────────────────────────────
      System.out.println("=== 1. 단일 Specification - hasCity('서울') ===");
      List<Customer> seoul = repo.findAll(CustomerSpecs.hasCity("서울"));
      seoul.forEach(c -> System.out.println("  " + c));

      // ── 2. and() 조합 ───────────────────────────────────────────────────
      System.out.println("\n=== 2. and() 조합 - 서울 AND 이름에 '홍' 포함 ===");
      Specification<Customer> seoulAndHong =
          CustomerSpecs.hasCity("서울").and(CustomerSpecs.nameContains("홍"));
      repo.findAll(seoulAndHong).forEach(c -> System.out.println("  " + c));

      // ── 3. or() 조합 ────────────────────────────────────────────────────
      System.out.println("\n=== 3. or() 조합 - 서울 OR 부산 ===");
      Specification<Customer> seoulOrBusan =
          CustomerSpecs.hasCity("서울").or(CustomerSpecs.hasCity("부산"));
      repo.findAll(seoulOrBusan).forEach(c -> System.out.println("  " + c));

      // ── 4. not() 부정 ───────────────────────────────────────────────────
      System.out.println("\n=== 4. not() 부정 - 서울이 아닌 고객 ===");
      Specification<Customer> notSeoul = CustomerSpecs.cityNot("서울");
      repo.findAll(notSeoul).forEach(c -> System.out.println("  " + c));

      // ── 5. 세 조건 조합 ─────────────────────────────────────────────────
      System.out.println("\n=== 5. 세 조건 조합 - (서울 OR 부산) AND 이메일 example.com ===");
      Specification<Customer> complex = seoulOrBusan
          .and(CustomerSpecs.emailDomain("example.com"));
      repo.findAll(complex).forEach(c -> System.out.println("  " + c));

      // ── 6. null 처리 - 전체 조회 ────────────────────────────────────────
      System.out.println("\n=== 6. Specification.where(null) - 전체 조회 ===");
      // hasCity(null) → Predicate null 반환 → 조건 없음
      List<Customer> all = repo.findAll(
          Specification.where(CustomerSpecs.hasCity(null)));
      System.out.println("  전체 고객 수: " + all.size());

      // ── 7. count() ──────────────────────────────────────────────────────
      System.out.println("\n=== 7. count(Specification) ===");
      long seoulCount = repo.count(CustomerSpecs.hasCity("서울"));
      System.out.println("  서울 고객 수: " + seoulCount);

      System.out.println("\n[정리]");
      System.out.println("  JpaSpecificationExecutor : findAll(Spec) / findOne(Spec) / count(Spec)");
      System.out.println("  spec.and(spec2)          : AND 조합");
      System.out.println("  spec.or(spec2)           : OR 조합");
      System.out.println("  Specification.where(null): 조건 없음 (전체 조회)");
      System.out.println("  조건 반환 null           : 해당 조건 무시 (동적 쿼리 핵심)");
    }
  }
}
