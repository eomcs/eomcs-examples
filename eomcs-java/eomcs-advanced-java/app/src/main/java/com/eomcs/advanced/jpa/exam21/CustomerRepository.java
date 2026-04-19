package com.eomcs.advanced.jpa.exam21;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// exam21 - Spring Data JPA 기초: JpaRepository + 파생 쿼리(Derived Query)
//
// JpaRepository<엔티티, PK타입>을 상속하면 Spring Data JPA가 런타임에
// 구현체를 자동 생성한다. 별도의 SQL이나 JPQL을 작성할 필요가 없다.
//
// ── 기본 제공 메서드 (JpaRepository / CrudRepository 상속) ──
//   save(entity)        : INSERT 또는 UPDATE (id 유무로 판단)
//   findById(id)        : SELECT WHERE id = ? → Optional<T>
//   findAll()           : SELECT * → List<T>
//   findAll(Sort)       : SELECT * ORDER BY → List<T>
//   count()             : SELECT COUNT(*) → long
//   deleteById(id)      : DELETE WHERE id = ?
//   existsById(id)      : SELECT COUNT(*) > 0 → boolean
//
// ── 파생 쿼리 (메서드 이름 → JPQL 자동 생성) ──
//   findBy{필드}(값)         : WHERE 필드 = ?
//   findBy{필드}OrderBy{필드}: WHERE + ORDER BY
//   countBy{필드}(값)        : SELECT COUNT(*) WHERE ...
//   existsBy{필드}(값)       : SELECT COUNT(*) > 0 WHERE ...
//   findBy{필드}Containing   : WHERE 필드 LIKE '%값%'
//   findBy{필드}Between      : WHERE 필드 BETWEEN ? AND ?
//
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // WHERE city = :city
  List<Customer> findByCity(String city);

  // WHERE city = :city ORDER BY name ASC
  List<Customer> findByCityOrderByNameAsc(String city);

  // WHERE email = :email → 이메일은 유일하므로 Optional 반환
  Optional<Customer> findByEmail(String email);

  // WHERE name LIKE '%keyword%'
  List<Customer> findByNameContaining(String keyword);

  // SELECT COUNT(*) WHERE city = :city
  long countByCity(String city);

  // SELECT COUNT(*) > 0 WHERE email = :email
  boolean existsByEmail(String email);
}
