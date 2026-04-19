package com.eomcs.advanced.jpa.exam22;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

// exam22 - @Query & Paging: Page / Slice / Sort
//
// 이 예제에서 확인할 내용:
//   1. @Query JPQL 직접 실행
//   2. Page<T> - totalElements, totalPages 포함 (COUNT 쿼리 자동 실행)
//   3. Slice<T> - 다음 페이지 여부만 (COUNT 없음, 무한 스크롤)
//   4. PageRequest.of(page, size, Sort) - 페이지 + 정렬 조합
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 1. @Query (JPQL 직접 지정) ────────────────────────────────────
      System.out.println("=== 1. @Query - JPQL 직접 지정 ===");
      List<Customer> byCity = repo.findByCityJpql("서울");
      byCity.forEach(c -> System.out.println("  " + c));

      // ── 2. @Query - 집계 (Object[] 반환) ──────────────────────────────
      System.out.println("\n=== 2. @Query - 집계 (GROUP BY) ===");
      List<Object[]> grouped = repo.countGroupByCity();
      grouped.forEach(row ->
          System.out.printf("  도시: %-6s 고객 수: %s%n", row[0], row[1]));

      // ── 3. Page<T> - 페이징 ───────────────────────────────────────────
      System.out.println("\n=== 3. Page<T> - 페이징 (Page 0, Size 2) ===");
      // PageRequest.of(pageNum, pageSize): 0-based 페이지 번호
      Pageable firstPage = PageRequest.of(0, 2);
      Page<Customer> page0 = repo.findByCity("서울", firstPage);

      System.out.println("  현재 페이지: " + page0.getNumber());
      System.out.println("  페이지 크기: " + page0.getSize());
      System.out.println("  전체 건수:  " + page0.getTotalElements());  // COUNT 쿼리 실행
      System.out.println("  전체 페이지: " + page0.getTotalPages());
      System.out.println("  다음 있음:  " + page0.hasNext());
      page0.getContent().forEach(c -> System.out.println("  " + c));

      // 다음 페이지
      if (page0.hasNext()) {
        Page<Customer> page1 = repo.findByCity("서울", page0.nextPageable());
        System.out.println("\n  Page 1:");
        page1.getContent().forEach(c -> System.out.println("  " + c));
      }

      // ── 4. Slice<T> - COUNT 없는 페이징 ──────────────────────────────
      System.out.println("\n=== 4. Slice<T> - COUNT 없는 페이징 ===");
      // Slice는 totalElements/totalPages 없음 → COUNT 쿼리 미실행 (성능 유리)
      Slice<Customer> slice = repo.findByNameContaining("홍", PageRequest.of(0, 2));
      System.out.println("  다음 있음: " + slice.hasNext());
      slice.getContent().forEach(c -> System.out.println("  " + c));

      // ── 5. @Query + Pageable + Sort ────────────────────────────────────
      System.out.println("\n=== 5. @Query + Pageable + Sort 조합 ===");
      Pageable withSort = PageRequest.of(0, 5, Sort.by("name").ascending());
      Page<Customer> sorted = repo.searchByCity("서울", withSort);
      System.out.println("  서울 고객 (이름순):");
      sorted.getContent().forEach(c -> System.out.println("  " + c));

      System.out.println("\n[정리]");
      System.out.println("  Page<T>  : COUNT 쿼리 추가 실행 → totalElements / totalPages 제공");
      System.out.println("  Slice<T> : COUNT 없음 → hasNext()만 제공 (무한 스크롤에 적합)");
      System.out.println("  PageRequest.of(page, size, Sort): 페이지 + 정렬 한 번에 지정");
    }
  }
}
