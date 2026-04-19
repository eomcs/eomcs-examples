package com.eomcs.advanced.jpa.exam26;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // 해결법 1: JOIN FETCH
  // Customer와 orders를 한 번의 쿼리로 조회
  // DISTINCT: 조인으로 인한 중복 Customer 제거
  @Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders")
  List<Customer> findAllWithOrders();

  // 해결법 2: @EntityGraph
  // attributePaths에 즉시 로딩할 연관 경로를 선언
  // 내부적으로 LEFT OUTER JOIN FETCH와 동일한 쿼리 생성
  @EntityGraph(attributePaths = {"orders"})
  @Query("SELECT c FROM Customer c")
  List<Customer> findAllWithOrdersGraph();
}
