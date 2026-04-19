package com.eomcs.advanced.jpa.exam24;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// exam24 - Specification 패턴
//
// JpaSpecificationExecutor<T>를 추가 상속하면
//   findAll(Specification<T>)
//   findOne(Specification<T>)
//   count(Specification<T>)
// 메서드가 자동 제공된다.
//
// Specification<T>: WHERE 절 조건 하나를 표현하는 함수형 인터페이스
//   toPredicate(Root<T>, CriteriaQuery<?>, CriteriaBuilder) → Predicate
//
// 여러 Specification을 and() / or() / not()으로 조합해
// 복잡한 동적 쿼리를 선언적으로 구성할 수 있다.
//
public interface CustomerRepository
    extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
