package com.eomcs.advanced.jpa.exam27;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// exam27 - 지연/즉시 로딩: CustomerService
//
// @Transactional 범위 안에서는 LAZY 로딩이 정상 동작한다.
// 트랜잭션이 종료된 후 LAZY 필드에 접근하면 LazyInitializationException이 발생한다.
//
@Service
public class CustomerService {

  private final CustomerRepository repo;

  public CustomerService(CustomerRepository repo) {
    this.repo = repo;
  }

  // 트랜잭션 안에서 LAZY 로딩 → 정상 동작
  @Transactional(readOnly = true)
  public void printOrdersInsideTransaction() {
    System.out.println(">> [트랜잭션 안] LAZY 로딩 정상 동작");
    List<Customer> customers = repo.findAll();
    for (Customer c : customers) {
      // 트랜잭션 안이므로 Hibernate 세션이 열려 있음 → LAZY 로딩 성공
      int count = c.getOrders().size();
      System.out.printf("   %s → 주문 %d건 (정상)%n", c.getName(), count);
    }
  }

  // LAZY 로딩된 컬렉션을 트랜잭션 안에서 초기화하여 반환
  @Transactional(readOnly = true)
  public List<Customer> findAllInitialized() {
    List<Customer> customers = repo.findAll();
    // 트랜잭션 안에서 미리 접근(초기화)해 두면 트랜잭션 종료 후에도 사용 가능
    customers.forEach(c -> c.getOrders().size());
    return customers;
  }

  // 트랜잭션 없이 조회 (detached 상태 반환)
  public Customer findByIdDetached(Long id) {
    // 이 메서드는 @Transactional이 없으므로 반환 후 LAZY 접근 시 예외 발생
    return repo.findById(id).orElseThrow();
  }
}
