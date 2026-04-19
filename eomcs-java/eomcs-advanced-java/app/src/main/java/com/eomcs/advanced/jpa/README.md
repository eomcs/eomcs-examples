# JPA 및 데이터베이스 최적화

## 학습 목표

- JPA와 데이터베이스 최적화 기법을 이해하고 적용할 수 있다.
- 엔티티 매핑 전략과 연관관계 매핑을 이해하고 설계할 수 있다.
- 쿼리 최적화와 성능 분석을 통해 효율적인 데이터 접근을 구현할 수 있다.

---

## 차례

### Part 1. JDBC 심화 (exam01 ~ exam05)
- exam01: JDBC 기본 복습 & Connection Pool 
  - `DriverManager` vs `DataSource`, HikariCP
- exam02: PreparedStatement & Batch 처리
  - SQL Injection 방지, `addBatch()` / `executeBatch()`
- exam03: Transaction 관리
  - `commit` / `rollback`, Savepoint, 격리 수준(Isolation Level)
- exam04: ResultSet 매핑과 RowMapper 패턴
  - 수동 매핑의 반복 문제 → ORM 필요성 인식
- exam05: JDBC Template 패턴 직접 구현
  - 템플릿 콜백 패턴, Spring JdbcTemplate 원리 이해

### Part 2. Hibernate 기초 (exam06 ~ exam10)
- exam06: JPA 소개 & EntityManagerFactory / EntityManager
  - `persistence.xml`, `EntityTransaction`
- exam07: 기본 엔티티 매핑
  - `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`
- exam08: 영속성 컨텍스트(Persistence Context)
  - 1차 캐시, 동일성 보장, 변경 감지(Dirty Checking), 쓰기 지연
- exam09: 연관관계 매핑 기초
  - `@ManyToOne`, `@OneToMany`, 단방향 vs 양방향, `mappedBy`
- exam10: 엔티티 생명주기
  - `persist`, `find`, `merge`, `remove`, `detach`, `flush`

### Part 3. 고급 엔티티 매핑 전략 (exam11 ~ exam16)
- exam11: 상속 매핑 전략
  - `SINGLE_TABLE`, `JOINED`, `TABLE_PER_CLASS` 비교
- exam12: `@Embeddable` / `@Embedded`
  - 값 타입(Value Type), 복합 키 매핑
- exam13: 다대다(`@ManyToMany`) & 중간 테이블
  - `@JoinTable`, 연결 엔티티로 리팩토링, Product ↔ Category 다대다 도입
- exam14: 자기 참조 연관관계
  - Category 계층 구조, `@ManyToOne` self-join, `CascadeType`
- exam15: 복합 키 매핑
  - `@IdClass`, `@EmbeddedId`
- exam16: 엔티티 이벤트 & `@EntityListeners`
  - `@PrePersist`, `@PostUpdate`, Auditing 구현`

### Part 4. JPQL & Criteria API (exam17 ~ exam20)
- exam17: JPQL 기초
  - 객체지향 쿼리, 파라미터 바인딩, 프로젝션
- exam18: JPQL 심화
  - `JOIN FETCH`, 서브쿼리, Named Query
- exam19: Criteria API
  - 타입 안전 동적 쿼리
- exam20: Native Query & `@SqlResultSetMapping`
  - 네이티브 SQL 사용, DTO 매핑, 계층 구조 재귀 CTE

### Part 5. Spring Data JPA (exam21 ~ exam25)
- exam21: Spring Data JPA 기초
  - `JpaRepository`, 메서드 이름 쿼리 자동 생성
- exam22: `@Query` & Paging
  - `@Query`, `Pageable`, `Sort`
- exam23: Querydsl 기초
  - `JPAQueryFactory`, 타입 안전 동적 쿼리
- exam24: Specification 패턴
  - `JpaSpecificationExecutor`, 검색 조건 조합
- exam25: Auditing & `@EnableJpaAuditing`
  - `@CreatedDate`, `@LastModifiedBy`, BaseEntity

### Part 6. 성능 최적화 (exam26 ~ exam30)
- exam26: N+1 문제
  - 문제 재현 → `JOIN FETCH` / `@EntityGraph`로 해결
- exam27: 지연 로딩(Lazy) vs 즉시 로딩(Eager)
  - `FetchType` 전략, `OSIV` 패턴
- exam28: 2차 캐시
  - `@Cacheable`, Ehcache 연동, 쿼리 캐시
- exam29: 배치 처리 & Bulk 연산
  - `@Modifying`, `executeUpdate`, StatelessSession
- exam30: 읽기 전용 트랜잭션 & 성능 측정
  - `@Transactional(readOnly=true)`, Hibernate Statistics

---

## 준비

### 데이터베이스 접속

- Hostname: $DB_HOSTNAME
- Post: $DB_PORT
- Service Name: $DB_SERVICE_NAME
- Username: $DB_USERNAME
- Password: $DB_PASSWORD

### 테이블 생성 및 샘플 데이터 입력

- ddl-oracle.sql: Oracle DB 용 테이블 정의서
- sample-data.sql: 샘플 데이터를 입력하는 스크립트

---

