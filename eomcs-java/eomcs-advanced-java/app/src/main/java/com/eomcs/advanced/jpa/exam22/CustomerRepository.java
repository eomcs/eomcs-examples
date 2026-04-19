package com.eomcs.advanced.jpa.exam22;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

// exam22 - @Query & Paging
//
// @Query: 메서드 이름 대신 JPQL(또는 nativeQuery=true로 SQL)을 직접 지정한다.
//   - 복잡한 조건·집계 쿼리에 사용
//   - JPQL이므로 테이블명·컬럼명 대신 엔티티명·필드명을 사용한다
//
// Pageable: 페이지 번호(0-based), 크기, 정렬을 하나의 객체로 전달한다.
//   PageRequest.of(page, size)           → 정렬 없이
//   PageRequest.of(page, size, Sort)     → 정렬 포함
//
// Page<T>  : 전체 건수(totalElements), 전체 페이지 수(totalPages) 포함 → COUNT 쿼리 추가 실행
// Slice<T> : 다음 페이지 존재 여부(hasNext)만 → COUNT 없음, 무한 스크롤에 적합
//
// @Modifying: SELECT가 아닌 UPDATE·DELETE 쿼리에 필수
//   - clearAutomatically = true 옵션: 실행 후 영속성 컨텍스트 1차 캐시 자동 초기화
//
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // ── @Query (JPQL) ───────────────────────────────────────────────────────────

  // 단순 조건 JPQL - 이름 파라미터 바인딩
  @Query("SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.name")
  List<Customer> findByCityJpql(@Param("city") String city);

  // 집계 쿼리
  @Query("SELECT c.city, COUNT(c) FROM Customer c GROUP BY c.city ORDER BY COUNT(c) DESC")
  List<Object[]> countGroupByCity();

  // ── Pageable (페이징) ───────────────────────────────────────────────────────

  // 파생 쿼리 + Pageable: Page<T> 반환 (totalCount 포함)
  Page<Customer> findByCity(String city, Pageable pageable);

  // 파생 쿼리 + Pageable: Slice<T> 반환 (totalCount 없음, 다음 페이지 여부만)
  Slice<Customer> findByNameContaining(String keyword, Pageable pageable);

  // @Query + Pageable: COUNT 쿼리를 countQuery로 분리해 최적화
  @Query(
      value      = "SELECT c FROM Customer c WHERE c.city = :city",
      countQuery = "SELECT COUNT(c) FROM Customer c WHERE c.city = :city")
  Page<Customer> searchByCity(@Param("city") String city, Pageable pageable);

  // ── @Modifying (UPDATE / DELETE) ────────────────────────────────────────────

  // clearAutomatically = true: UPDATE 후 1차 캐시를 비워 stale 데이터 방지
  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("UPDATE Customer c SET c.city = :newCity WHERE c.city = :oldCity")
  int updateCity(@Param("oldCity") String oldCity, @Param("newCity") String newCity);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("DELETE FROM Customer c WHERE c.email LIKE :pattern")
  int deleteByEmailPattern(@Param("pattern") String pattern);
}
