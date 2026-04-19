package com.eomcs.advanced.jpa.exam30;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// exam30 - 읽기 전용 트랜잭션 & 성능 측정: CustomerService
//
// @Transactional(readOnly = true):
//   1. Hibernate가 엔티티 스냅샷(Dirty Checking용 복사본)을 만들지 않음
//      → 엔티티 개수 × 필드 수만큼 메모리 절약
//   2. flush() 자동 실행 안 함 → DB 쓰기 시도 없음
//   3. 일부 DB/드라이버: readOnly 힌트로 읽기 전용 커넥션 사용
//   4. Spring: DataSource 레벨에서 읽기 전용 커넥션으로 라우팅 가능 (읽기 DB 분리)
//
// readOnly=true는 보장이 아닌 힌트다 → JPA 구현체/드라이버마다 동작 차이 있음
//
@Service
public class CustomerService {

  private final CustomerRepository repo;

  public CustomerService(CustomerRepository repo) {
    this.repo = repo;
  }

  // 읽기 전용 트랜잭션: Dirty Checking 스냅샷 없음 → 메모리/성능 최적화
  @Transactional(readOnly = true)
  public List<Customer> findAll() {
    return repo.findAll();
  }

  @Transactional(readOnly = true)
  public List<Customer> findByCity(String city) {
    return repo.findByCity(city);
  }

  // 읽기 전용에서 수정 시도 → flush 안 함, DB 변경 없음 (Hibernate 보장)
  @Transactional(readOnly = true)
  public Customer findAndTryModify(Long id) {
    Customer c = repo.findById(id).orElseThrow();
    c.setCity("변경시도"); // Dirty Checking 스냅샷 없음 → flush 시 감지 안 됨
    // flush()가 호출되지 않으므로 DB에 반영되지 않음
    return c;
  }

  // 일반 트랜잭션: Dirty Checking 스냅샷 생성 → 변경 감지 → 자동 UPDATE
  @Transactional
  public Customer findAndModify(Long id, String newCity) {
    Customer c = repo.findById(id).orElseThrow();
    c.setCity(newCity); // 스냅샷과 비교 → 변경 감지 → 트랜잭션 종료 시 UPDATE 실행
    return c;
  }
}
