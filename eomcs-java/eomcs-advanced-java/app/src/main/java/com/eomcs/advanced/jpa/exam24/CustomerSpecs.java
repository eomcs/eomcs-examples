package com.eomcs.advanced.jpa.exam24;

import org.springframework.data.jpa.domain.Specification;

// exam24 - Specification 패턴: 검색 조건 팩토리
//
// Specification<T>: 함수형 인터페이스
//   (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) → Predicate
//
// 조합:
//   spec1.and(spec2)   → AND
//   spec1.or(spec2)    → OR
//   Specification.not(spec) → NOT
//   Specification.where(null) → 조건 없음 (전체 조회)
//
// 장점: 각 조건을 독립적인 메서드로 분리 → 재사용·조합이 쉽다
//
public class CustomerSpecs {

  // 특정 도시 조건
  public static Specification<Customer> hasCity(String city) {
    return (root, query, cb) -> {
      if (city == null || city.isBlank()) return null;   // null 반환 → 조건 무시
      return cb.equal(root.get("city"), city);
    };
  }

  // 이름에 키워드 포함 조건 (LIKE '%keyword%')
  public static Specification<Customer> nameContains(String keyword) {
    return (root, query, cb) -> {
      if (keyword == null || keyword.isBlank()) return null;
      return cb.like(root.get("name"), "%" + keyword + "%");
    };
  }

  // 이메일 도메인 조건 (LIKE '%@domain')
  public static Specification<Customer> emailDomain(String domain) {
    return (root, query, cb) -> {
      if (domain == null || domain.isBlank()) return null;
      return cb.like(root.get("email"), "%@" + domain);
    };
  }

  // 특정 도시가 아닌 조건 (NOT EQUAL)
  public static Specification<Customer> cityNot(String city) {
    return (root, query, cb) -> {
      if (city == null || city.isBlank()) return null;
      return cb.notEqual(root.get("city"), city);
    };
  }
}
