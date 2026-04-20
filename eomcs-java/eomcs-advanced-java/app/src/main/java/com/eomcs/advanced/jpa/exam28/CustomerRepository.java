package com.eomcs.advanced.jpa.exam28;

import jakarta.persistence.QueryHint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // 쿼리 캐시 활성화: 동일 파라미터로 재호출 시 DB 조회 없이 캐시에서 반환
  // hibernate.cache.use_query_cache=true 설정이 있어야 동작한다.
  @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
  @Query("SELECT c FROM Customer c WHERE c.city = :city")
  List<Customer> findByCityWithCache(@Param("city") String city);
}
