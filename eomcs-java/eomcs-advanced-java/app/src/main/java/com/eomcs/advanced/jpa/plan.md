# JPA 패키지 예제 작성 계획

전체 흐름: **"왜 JPA가 필요한가"를 JDBC 한계에서 출발하여, Hibernate → Spring Data JPA → 성능 최적화** 순서로 자연스럽게 쌓아가는 구성입니다.

---

## Part 1. JDBC 심화 (exam01 ~ exam05)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam01 | JDBC 기본 복습 & Connection Pool | `DriverManager` vs `DataSource`, HikariCP |
| exam02 | PreparedStatement & Batch 처리 | SQL Injection 방지, `addBatch()` / `executeBatch()` |
| exam03 | Transaction 관리 | `commit` / `rollback`, Savepoint, 격리 수준(Isolation Level) |
| exam04 | ResultSet 매핑과 RowMapper 패턴 | 수동 매핑의 반복 문제 → ORM 필요성 인식 |
| exam05 | JDBC Template 패턴 직접 구현 | 템플릿 콜백 패턴, Spring JdbcTemplate 원리 이해 |

---

## Part 2. JPA / Hibernate 기초 (exam06 ~ exam10)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam06 | JPA 소개 & EntityManagerFactory / EntityManager | `persistence.xml`, `EntityTransaction` |
| exam07 | 기본 엔티티 매핑 | `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue` |
| exam08 | 영속성 컨텍스트(Persistence Context) | 1차 캐시, 동일성 보장, 변경 감지(Dirty Checking), 쓰기 지연 |
| exam09 | 연관관계 매핑 기초 | `@ManyToOne`, `@OneToMany`, 단방향 vs 양방향, `mappedBy` |
| exam10 | 엔티티 생명주기 | `persist`, `find`, `merge`, `remove`, `detach`, `flush` |

---

## Part 3. 고급 엔티티 매핑 전략 (exam11 ~ exam16)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam11 | 상속 매핑 전략 | `SINGLE_TABLE`, `JOINED`, `TABLE_PER_CLASS` 비교 |
| exam12 | `@Embeddable` / `@Embedded` | 값 타입(Value Type), 복합 키 매핑 |
| exam13 | 다대다(`@ManyToMany`) & 중간 테이블 | `@JoinTable`, 연결 엔티티로 리팩토링, Product ↔ Category 다대다 도입 |
| exam14 | 자기 참조 연관관계 | Category 계층 구조, `@ManyToOne` self-join, `CascadeType` |
| exam15 | 복합 키 매핑 | `@IdClass`, `@EmbeddedId` |
| exam16 | 엔티티 이벤트 & `@EntityListeners` | `@PrePersist`, `@PostUpdate`, Auditing 구현 |

---

## Part 4. JPQL & Criteria API (exam17 ~ exam20)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam17 | JPQL 기초 | 객체지향 쿼리, 파라미터 바인딩, 프로젝션 |
| exam18 | JPQL 심화 | `JOIN FETCH`, 서브쿼리, Named Query |
| exam19 | Criteria API | 타입 안전 동적 쿼리 |
| exam20 | Native Query & `@SqlResultSetMapping` | 네이티브 SQL 사용, DTO 매핑, 계층 구조 재귀 CTE |

---

## Part 5. Spring Data JPA (exam21 ~ exam25)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam21 | Spring Data JPA 기초 | `JpaRepository`, 메서드 이름 쿼리 자동 생성 |
| exam22 | `@Query` & Paging | `@Query`, `Pageable`, `Sort` |
| exam23 | Querydsl 기초 | `JPAQueryFactory`, 타입 안전 동적 쿼리 |
| exam24 | Specification 패턴 | `JpaSpecificationExecutor`, 검색 조건 조합 |
| exam25 | Auditing & `@EnableJpaAuditing` | `@CreatedDate`, `@LastModifiedBy`, BaseEntity |

---

## Part 6. 성능 최적화 (exam26 ~ exam30)

| exam | 주제 | 핵심 개념 |
|------|------|-----------|
| exam26 | N+1 문제 | 문제 재현 → `JOIN FETCH` / `@EntityGraph`로 해결 |
| exam27 | 지연 로딩(Lazy) vs 즉시 로딩(Eager) | `FetchType` 전략, `OSIV` 패턴 |
| exam28 | 2차 캐시 | `@Cacheable`, Ehcache 연동, 쿼리 캐시 |
| exam29 | 배치 처리 & Bulk 연산 | `@Modifying`, `executeUpdate`, StatelessSession |
| exam30 | 읽기 전용 트랜잭션 & 성능 측정 | `@Transactional(readOnly=true)`, Hibernate Statistics |

---

## 권장 순서 요약

```
JDBC 심화(1~5) → Hibernate 기초(6~10) → 매핑 전략(11~16)
→ JPQL(17~20) → Spring Data JPA(21~25) → 성능 최적화(26~30)
```

JDBC 심화를 앞에 두는 이유: ORM이 내부적으로 무엇을 대신해주는지 체감하게 해서,
Hibernate의 영속성 컨텍스트나 쓰기 지연 같은 개념이 "왜 유용한가"를 자연스럽게 납득할 수 있습니다.
