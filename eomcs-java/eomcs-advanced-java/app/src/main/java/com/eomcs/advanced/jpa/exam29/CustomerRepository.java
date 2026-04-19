package com.eomcs.advanced.jpa.exam29;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // Bulk UPDATE: 단 1번의 쿼리로 여러 행을 한꺼번에 변경
  // @Modifying: UPDATE/DELETE JPQL 실행에 필수
  // clearAutomatically: 벌크 연산 후 1차 캐시를 초기화하여 stale 데이터 방지
  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("UPDATE Customer c SET c.city = :newCity WHERE c.city = :oldCity")
  int bulkUpdateCity(@Param("oldCity") String oldCity, @Param("newCity") String newCity);

  // Bulk DELETE: 조건에 맞는 데이터를 한 번에 삭제
  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("DELETE FROM Customer c WHERE c.email LIKE :pattern")
  int bulkDeleteByEmailPattern(@Param("pattern") String pattern);

  // 전체 건수 조회
  @Query("SELECT COUNT(c) FROM Customer c WHERE c.email LIKE :pattern")
  long countByEmailPattern(@Param("pattern") String pattern);
}
