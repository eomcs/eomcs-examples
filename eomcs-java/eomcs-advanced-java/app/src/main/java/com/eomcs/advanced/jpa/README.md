# JPA 및 데이터베이스 최적화

## 학습 목표

- JDBC의 기본 실행 흐름을 이해하고, `DriverManager`와 `DataSource` 기반 Connection Pool의 차이를 설명할 수 있다.
- `PreparedStatement`를 사용하여 SQL Injection을 방지하고, Batch 처리로 다중 SQL 실행 성능을 개선할 수 있다.
- 트랜잭션의 `commit`, `rollback`, Savepoint, 격리 수준(Isolation Level)을 이해하고 상황에 맞게 제어할 수 있다.
- `ResultSet` 수동 매핑의 반복 문제를 이해하고, RowMapper 패턴으로 매핑 로직을 분리할 수 있다.
- 템플릿 콜백 패턴을 적용하여 간단한 JDBC Template 구조를 직접 구현할 수 있다.
- JPA의 역할과 `EntityManagerFactory`, `EntityManager`, `EntityTransaction`의 생명주기를 이해하고 활용할 수 있다.
- `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`를 사용하여 기본 엔티티를 매핑할 수 있다.
- 영속성 컨텍스트의 1차 캐시, 동일성 보장, 변경 감지(Dirty Checking), 쓰기 지연, `flush` 동작을 설명할 수 있다.
- `@ManyToOne`, `@OneToMany`, `mappedBy`를 사용하여 단방향/양방향 연관관계를 설계할 수 있다.
- 엔티티의 `persist`, `find`, `merge`, `remove`, `detach`, `flush` 상태 전이를 이해하고 적절히 사용할 수 있다.
- 상속 매핑 전략(`SINGLE_TABLE`, `JOINED`, `TABLE_PER_CLASS`)의 특징과 장단점을 비교할 수 있다.
- `@Embeddable`, `@Embedded`를 사용하여 값 타입을 분리하고 재사용 가능한 도메인 모델을 구성할 수 있다.
- 다대다(`@ManyToMany`) 연관관계의 한계를 이해하고, 중간 테이블 또는 연결 엔티티로 관계를 명확하게 모델링할 수 있다.
- 자기 참조 연관관계를 사용하여 계층 구조를 표현하고, 연관관계 편의 메서드와 Cascade 동작을 다룰 수 있다.
- `@IdClass`와 `@EmbeddedId`를 사용하여 복합 키를 매핑하고, 각 방식의 차이를 구분할 수 있다.
- 엔티티 이벤트와 `@EntityListeners`를 사용하여 생성/수정 시점의 공통 처리와 Auditing을 구현할 수 있다.
- JPQL의 기본 문법, 파라미터 바인딩, 프로젝션을 이해하고 객체 중심 쿼리를 작성할 수 있다.
- `JOIN FETCH`, 서브쿼리, Named Query를 활용하여 JPQL 쿼리를 더 효율적으로 구성할 수 있다.
- Criteria API를 사용하여 타입 안전한 동적 쿼리를 작성할 수 있다.
- Native Query와 `@SqlResultSetMapping`을 사용하여 SQL 기반 조회 결과를 엔티티 또는 DTO로 매핑할 수 있다.
- Spring Data JPA의 `JpaRepository`를 사용하고, 메서드 이름 기반 쿼리를 정의할 수 있다.
- `@Query`, `Pageable`, `Sort`를 활용하여 명시적 쿼리와 페이징/정렬 처리를 구현할 수 있다.
- Querydsl의 `JPAQueryFactory`를 사용하여 타입 안전하고 가독성 있는 동적 쿼리를 작성할 수 있다.
- Specification 패턴을 사용하여 검색 조건을 조합 가능한 형태로 분리할 수 있다.
- Spring Data JPA Auditing을 설정하고, `@CreatedDate`, `@LastModifiedDate` 등 공통 감사 필드를 자동으로 관리할 수 있다.
- N+1 문제의 발생 원인을 이해하고, `JOIN FETCH`와 `@EntityGraph`로 조회 성능을 개선할 수 있다.
- 지연 로딩(Lazy)과 즉시 로딩(Eager)의 차이를 이해하고, `FetchType`과 OSIV 패턴의 영향을 판단할 수 있다.
- 2차 캐시와 쿼리 캐시의 동작 방식을 이해하고, 캐시 적용이 적절한 상황을 판단할 수 있다.
- Bulk 연산과 배치 처리의 특성을 이해하고, 영속성 컨텍스트와의 불일치 문제를 관리할 수 있다.
- 읽기 전용 트랜잭션과 Hibernate Statistics를 활용하여 조회 성능을 측정하고 최적화할 수 있다.

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
  - `@PrePersist`, `@PostUpdate`, Auditing 구현

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
- Port: $DB_PORT
- Service Name: $DB_SERVICE_NAME
- Username: $DB_USERNAME
- Password: $DB_PASSWORD

### 테이블 생성 및 샘플 데이터 입력

- ddl-oracle.sql: Oracle DB 용 테이블 정의서
- sample-data.sql: 샘플 데이터를 입력하는 스크립트

---

## Exam01 - JDBC 기본 복습 & Connection Pool

### 개념

#### JDBC(Java Database Connectivity)

자바 애플리케이션에서 데이터베이스와 통신하기 위한 표준 API다.
드라이버 로딩 → 연결 획득 → SQL 실행 → 결과 처리 → 자원 해제 순서로 동작한다.

#### DriverManager vs DataSource

| 항목 | `DriverManager` | `DataSource` (Connection Pool) |
|---|---|---|
| Connection 생성 | 매 호출마다 새 물리 연결 생성 | 풀에서 이미 만들어진 연결을 재사용 |
| 성능 | 느림 (TCP 핸드셰이크·인증 비용 발생) | 빠름 (물리 연결 재사용) |
| 멀티스레드 | 부적합 | 적합 (풀이 동시 요청을 관리) |
| 자원 해제 | `close()` → 물리 연결 해제 | `close()` → 풀에 반납 (물리 연결 유지) |
| 적합한 용도 | 단순 테스트, 단발성 스크립트 | 실제 서비스, 멀티스레드 서버 |

#### HikariCP

현재 가장 빠른 JDBC Connection Pool 라이브러리로, Spring Boot의 기본 Connection Pool이기도 하다.

##### 주요 설정

| 설정 | 설명 | 기본값 |
|---|---|---|
| `maximumPoolSize` | 풀이 유지할 최대 Connection 수 | 10 |
| `minimumIdle` | 유휴 상태로 유지할 최소 Connection 수 | `maximumPoolSize`와 동일 |
| `connectionTimeout` | Connection 획득 대기 최대 시간 (ms) | 30000 |
| `idleTimeout` | 유휴 Connection 제거 전 대기 시간 (ms) | 600000 |
| `maxLifetime` | Connection 최대 수명 (ms) | 1800000 |

#### Oracle JDBC URL 형식

```text
jdbc:oracle:thin:@//호스트:포트/서비스이름
```

#### 환경 변수로 접속 정보 관리

소스 코드에 계정 정보를 직접 작성하면 Git 등 버전 관리 도구에 유출될 위험이 있다.
OS 환경 변수에 접속 정보를 저장하고 `System.getenv()` 로 읽는 방식이 권장된다.


```java
String host    = System.getenv("DB_HOSTNAME");
String port    = System.getenv("DB_PORT");
String service = System.getenv("DB_SERVICE_NAME");
String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;
String username = System.getenv("DB_USERNAME");
String password = System.getenv("DB_PASSWORD");
```

#### Connection 흐름 비교

```
DriverManager 방식:
  getConnection() → [물리 연결 생성] → 사용 → close() → [물리 연결 해제]

HikariCP 방식:
  Pool 초기화 → 물리 연결 N개 미리 생성
  getConnection() → [풀에서 대여] → 사용 → close() → [풀에 반납]
```

### App - DriverManager로 직접 연결

`DriverManager.getConnection()` 으로 연결을 획득하고, 고객 목록을 조회한다.

```java
String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

// DriverManager.getConnection() 호출 → 새 물리적 연결 생성
try (Connection connection = DriverManager.getConnection(url, username, password)) {
    // 드라이버 정보, DB 버전, AutoCommit 여부 확인
    connection.getMetaData().getDriverName();
    connection.getMetaData().getDatabaseProductVersion();
    connection.getAutoCommit(); // 기본값: true

    // SYSDATE 조회
    try (Statement stmt = connection.createStatement();
         ResultSet rs   = stmt.executeQuery("SELECT SYSDATE FROM DUAL")) {
        if (rs.next()) System.out.println(rs.getString(1));
    }

    // 고객 목록 조회
    try (Statement stmt = connection.createStatement();
         ResultSet rs   = stmt.executeQuery(
             "SELECT id, name, email, city FROM shop_customer ORDER BY id")) {
        while (rs.next()) {
            System.out.printf("[%d] %s / %s%n",
                rs.getLong("id"), rs.getString("name"), rs.getString("city"));
        }
    }
}
// try 블록 종료 → connection.close() 자동 호출 → 물리 연결 해제
```

- `try-with-resources`를 사용하면 예외 발생 여부와 관계없이 `close()`가 자동 호출된다.
- `Connection → Statement → ResultSet` 모두 `AutoCloseable`을 구현하므로 `try-with-resources`로 관리한다.
- `getMetaData()`로 드라이버 이름, DB 버전 등 연결 메타데이터를 확인할 수 있다.
- `AutoCommit`의 기본값은 `true`이다. 트랜잭션 제어가 필요하면 `setAutoCommit(false)`로 변경한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam01.App
  ```

---

### App2 - HikariCP DataSource (Connection Pool)

`HikariCP`를 설정하고, Connection Pool에서 연결을 대여·반납하는 동작을 확인한다.

```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:oracle:thin:@//" + host + ":" + port + "/" + service);
config.setUsername(username);
config.setPassword(password);
config.setMaximumPoolSize(5);        // 최대 Connection 5개
config.setMinimumIdle(2);           // 유휴 Connection 최소 2개
config.setConnectionTimeout(3000);  // 3초 안에 못 얻으면 예외

// HikariDataSource 생성 → Pool 초기화 (내부적으로 물리 연결 생성)
try (HikariDataSource dataSource = new HikariDataSource(config)) {

    // 첫 번째 Connection 획득 (풀에서 대여)
    try (Connection conn1 = dataSource.getConnection()) {
        // conn1 사용 ...
    }
    // conn1.close() → 풀에 반납 (물리 연결은 유지됨)

    // 두 번째 Connection 획득 → 반납된 연결을 재사용
    try (Connection conn2 = dataSource.getConnection()) {
        // conn2 사용 ...
    }
}
// dataSource.close() → 풀의 모든 물리 연결 해제
```

- `HikariDataSource`는 `AutoCloseable`을 구현하므로 `try-with-resources`로 Pool 전체를 관리할 수 있다.
- Pool에서 대여한 `Connection`의 실제 클래스명은 HikariCP 내부 프록시 클래스(`HikariProxyConnection`)이다. `close()` 시 물리 연결을 끊지 않고 풀에 반납한다.
- `connectionTimeout` 초과 시 `SQLTransientConnectionException`이 발생한다.
- 실서비스에서는 `DataSource`를 싱글턴으로 관리하며 애플리케이션 시작 시 한 번만 생성한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam01.App2
  ```

## Exam02 - PreparedStatement & Batch 처리

### 개념

#### Statement vs PreparedStatement

| 항목 | `Statement` | `PreparedStatement` |
|---|---|---|
| SQL 전달 방식 | 완성된 SQL 문자열을 그대로 전송 | SQL 골격(템플릿)을 미리 컴파일한 뒤 파라미터만 바인딩 |
| 파싱·최적화 | 실행할 때마다 반복 수행 | 최초 1회만 수행 (이후 실행 시 재사용) |
| SQL Injection | 취약 (사용자 입력이 SQL 구조 변경 가능) | 안전 (입력값은 항상 데이터로 처리) |
| 파라미터 바인딩 | 문자열 연결 (`"... WHERE id=" + id`) | `?` 플레이스홀더 + `setXxx()` |
| 반복 실행 성능 | 느림 | 빠름 |
| 권장 여부 | 파라미터가 없는 DDL 등 제한적 사용 | 파라미터가 있는 모든 SQL에 사용 |

#### SQL Injection

사용자 입력을 SQL 문자열에 직접 연결할 때 발생하는 보안 취약점이다.
입력값에 SQL 문법 문자(작은따옴표 등)를 포함해 원래 쿼리의 논리를 변조한다.

```
입력값: ' OR '1'='1

Statement 실행 SQL:
  SELECT * FROM shop_customer WHERE name='' OR '1'='1'
  → WHERE 조건이 항상 참이 되어 모든 행이 반환된다.

PreparedStatement 실행:
  SELECT * FROM shop_customer WHERE name = ?  [파라미터: ' OR '1'='1]
  → 입력값 전체가 name 컬럼과 비교하는 리터럴 값으로 처리된다.
```

#### Batch 처리

여러 SQL을 묶어 DB 서버에 한 번에 전송하는 방식이다.

```
개별 실행: SQL 1건 전송 → 응답 → SQL 1건 전송 → 응답 → ... (N번 왕복)
Batch 실행: SQL N건 일괄 전송 → N개 응답 (1번 왕복)
```

네트워크 왕복 횟수를 줄여 대량 INSERT·UPDATE 성능이 크게 향상된다.

##### Batch API

| 메서드 | 설명 |
|---|---|
| `pstmt.addBatch()` | 현재 파라미터 조합을 배치 큐에 추가한다 |
| `pstmt.executeBatch()` | 큐에 쌓인 SQL을 한꺼번에 실행한다. 각 SQL의 영향 행 수를 `int[]`로 반환한다 |
| `pstmt.clearBatch()` | 큐를 비운다 (선택 사항) |

##### 주의 사항

- Batch 실행 중 일부 SQL이 실패하면 `BatchUpdateException`이 발생한다.
- 트랜잭션과 함께 사용해 부분 실패 시 `rollback`을 처리해야 안전하다.

---

### App - SQL Injection vs PreparedStatement

`Statement`의 SQL Injection 취약점과 `PreparedStatement`의 차단 방식을 비교한다.

```java
// [Statement] 사용자 입력을 SQL 문자열에 직접 연결 → 위험
String maliciousInput = "' OR '1'='1";
String sql = "SELECT * FROM shop_customer WHERE name='" + maliciousInput + "'";
// 실행 SQL: SELECT * FROM shop_customer WHERE name='' OR '1'='1'
// → 모든 고객이 반환된다 (의도하지 않은 결과)
try (Statement stmt = conn.createStatement();
     ResultSet rs   = stmt.executeQuery(sql)) { ... }

// [PreparedStatement] ? 플레이스홀더로 파라미터 분리 → 안전
String safeSql = "SELECT id, name, email FROM shop_customer WHERE name = ?";
try (PreparedStatement pstmt = conn.prepareStatement(safeSql)) {
    pstmt.setString(1, maliciousInput); // 인덱스는 1부터 시작
    // 내부 처리: name = ''' OR ''1''=''1'  (이스케이프 처리됨)
    try (ResultSet rs = pstmt.executeQuery()) { ... }
    // → 검색 결과 없음 (SQL Injection 차단됨)
}
```

- `pstmt.setString(1, value)`: 인덱스는 **1**부터 시작한다. `setLong`, `setInt`, `setDouble` 등 타입별 메서드를 사용한다.
- `PreparedStatement`는 파라미터 값 내의 특수문자를 자동으로 이스케이프 처리한다.
- 동일한 `PreparedStatement` 객체는 파라미터를 바꿔 가며 반복 실행할 수 있다. 반복 실행 시 `clearParameters()`로 이전 파라미터를 초기화한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam02.App
  ```

---

### App2 - Batch 처리

개별 `executeUpdate()` 방식과 `addBatch()` / `executeBatch()` 방식을 비교한다.

```java
String sql = "INSERT INTO shop_category (name, parent_id) VALUES (?, ?)";

// [방법 1] 개별 INSERT: 3건 → 3번의 DB 왕복
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, "태블릿");   pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
    pstmt.setString(1, "스마트폰"); pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
    pstmt.setString(1, "액세서리"); pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
}

// [방법 2] Batch INSERT: 5건 → 1번의 DB 왕복
String[] names = {"가전제품", "냉장고", "세탁기", "에어컨", "청소기"};
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    for (String name : names) {
        pstmt.setString(1, name);
        pstmt.setNull(2, Types.NUMERIC);
        pstmt.addBatch(); // 배치 큐에 추가 (아직 실행 안 됨)
    }
    int[] results = pstmt.executeBatch(); // 큐의 SQL 5건 일괄 전송
    // results[i]: i번째 SQL의 영향 행 수 (성공 시 보통 1)
}
```

- `addBatch()` 호출만으로는 SQL이 실행되지 않는다. `executeBatch()`를 호출해야 실제 전송된다.
- `executeBatch()` 반환값 `int[]`의 각 원소는 해당 SQL의 영향 행 수다. `Statement.SUCCESS_NO_INFO(-2)`가 반환되기도 한다 (드라이버 구현 방식에 따라 다름).
- Batch 실행 중 오류 발생 시 `BatchUpdateException`이 던져진다. 트랜잭션 `rollback`과 함께 사용해 데이터 정합성을 유지한다.
- 수만 건 이상의 대량 처리 시 일정 건수마다 `executeBatch()`를 호출하고 큐를 비워 메모리 사용량을 조절한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam02.App2
  ```

## Exam03 - Transaction 관리

### 개념

#### 트랜잭션(Transaction)

하나의 논리적 작업 단위를 구성하는 SQL 묶음이다.
ACID 특성을 보장한다.

| 특성 | 설명 |
|---|---|
| **원자성(Atomicity)** | 묶음 안의 SQL은 모두 성공하거나 모두 취소된다 |
| **일관성(Consistency)** | 트랜잭션 전후 DB는 항상 일관된 상태를 유지한다 |
| **격리성(Isolation)** | 동시 실행 트랜잭션은 서로의 중간 결과를 볼 수 없다 |
| **지속성(Durability)** | commit된 결과는 장애가 발생해도 영구적으로 유지된다 |

#### AutoCommit

JDBC `Connection`의 기본 `AutoCommit` 값은 `true`이다.
`AutoCommit=true`이면 SQL 실행 즉시 자동으로 `commit`된다.
트랜잭션을 직접 관리하려면 `setAutoCommit(false)`로 변경해야 한다.

```
AutoCommit=true  : SQL 실행 → 즉시 commit (트랜잭션 경계 없음)
AutoCommit=false : conn.commit() 또는 conn.rollback()을 명시 호출해야 반영·취소됨
```

#### commit / rollback

| 메서드 | 설명 |
|---|---|
| `conn.setAutoCommit(false)` | 트랜잭션 시작 |
| `conn.commit()` | 트랜잭션 시작 이후 변경 내용을 DB에 영구 반영 |
| `conn.rollback()` | 트랜잭션 시작 이후 변경 내용을 모두 취소 |

#### Savepoint

트랜잭션 내에 중간 저장 지점을 설정한다.
`rollback(savepoint)` 호출 시 해당 지점 이후의 변경만 취소하고, 이전 변경은 유지한다.

```
트랜잭션 시작
  SQL A 실행         ← commit 시 반영됨
  setSavepoint(sp)   ← 지점 설정
  SQL B 실행 (오류)
  rollback(sp)       ← SQL B만 취소, SQL A는 유지
  commit()           ← SQL A만 DB에 반영됨
```

| 메서드 | 설명 |
|---|---|
| `conn.setSavepoint("이름")` | Savepoint 설정, 반환값으로 `Savepoint` 객체를 받는다 |
| `conn.rollback(sp)` | 지정한 Savepoint 이후의 변경만 취소 |
| `conn.releaseSavepoint(sp)` | Savepoint 해제 (선택 사항) |

#### 격리 수준(Isolation Level)

동시에 실행되는 트랜잭션들이 서로를 얼마나 차단할지 결정하는 수준이다.
격리 수준이 낮을수록 성능이 좋고, 높을수록 데이터 정합성이 보장된다.

| 격리 수준 | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| `READ_UNCOMMITTED` (1) | 발생 가능 | 발생 가능 | 발생 가능 |
| `READ_COMMITTED` (2) | 방지 | 발생 가능 | 발생 가능 |
| `REPEATABLE_READ` (4) | 방지 | 방지 | 발생 가능 |
| `SERIALIZABLE` (8) | 방지 | 방지 | 방지 |

##### 이상 현상 설명

| 현상 | 설명 |
|---|---|
| **Dirty Read** | 다른 트랜잭션이 commit하지 않은 변경을 읽는 것 |
| **Non-Repeatable Read** | 같은 행을 두 번 읽었을 때 중간에 다른 트랜잭션이 UPDATE/DELETE 하여 값이 달라지는 것 |
| **Phantom Read** | 같은 조건의 SELECT가 두 번 실행되는 사이 다른 트랜잭션이 INSERT/DELETE하여 행 집합이 달라지는 것 |


##### Non-Repeatable Read 상세

같은 트랜잭션 내에서 **특정 행**을 두 번 읽었을 때, 다른 트랜잭션이 그 행을 UPDATE 또는 DELETE + COMMIT하면 결과가 달라지는 현상이다.

**UPDATE 시나리오**

```
트랜잭션 B                          트랜잭션 A
────────────────────────────        ──────────────────
SELECT price FROM shop_product
WHERE id = 1  →  3000원

                                    UPDATE shop_product
                                    SET price = 5000
                                    WHERE id = 1
                                    COMMIT

SELECT price FROM shop_product
WHERE id = 1  →  5000원  ← 값이 달라짐!
```

**DELETE 시나리오**

```
트랜잭션 B                          트랜잭션 A
────────────────────────────        ──────────────────
SELECT price FROM shop_product
WHERE id = 1  →  3000원 (행 존재)

                                    DELETE FROM shop_product
                                    WHERE id = 1
                                    COMMIT

SELECT price FROM shop_product
WHERE id = 1  →  결과 없음 ← 행 자체가 사라짐!
```

| 원인 | 두 번째 읽기 결과 |
|------|-----------------|
| UPDATE + COMMIT | 같은 행, **값이 달라짐** |
| DELETE + COMMIT | **행 자체가 사라짐** |

##### Phantom Read 상세

같은 트랜잭션 내에서 **범위 조건**으로 두 번 조회할 때, 다른 트랜잭션이 INSERT 또는 DELETE + COMMIT하면 행 집합이 달라지는 현상이다.

**INSERT 시나리오**

```
트랜잭션 B                              트랜잭션 A
────────────────────────────────        ──────────────────
SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B]  (2건)

                                        INSERT INTO shop_product
                                        VALUES (3, '상품C', 90000)
                                        COMMIT

SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B], [상품C]  ← 없던 행 등장! (3건)
```

**DELETE 시나리오**

```
트랜잭션 B                              트랜잭션 A
────────────────────────────────        ──────────────────
SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B], [상품C]  (3건)

                                        DELETE FROM shop_product
                                        WHERE id = 3
                                        COMMIT

SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B]  ← 있던 행이 사라짐! (2건)
```

| 원인 | 결과 |
|------|------|
| INSERT + COMMIT | 없던 행이 **새로 나타남** |
| DELETE + COMMIT | 있던 행이 **사라짐** |

> **Non-Repeatable Read vs Phantom Read**: DELETE는 두 현상 모두에 해당할 수 있다. 특정 행(`WHERE id=1`)을 삭제하면 Non-Repeatable Read, 범위 조건 결과를 바꾸면 Phantom Read다.

##### Oracle 특이 사항

- Oracle은 `READ_UNCOMMITTED`를 지원하지 않는다. `READ_COMMITTED`가 최소 수준이다.
- Oracle의 기본 격리 수준은 `READ_COMMITTED`다.
- MVCC(Multi-Version Concurrency Control)로 읽기 잠금 없이 일관된 읽기를 보장한다.

---

### App - commit / rollback

주문 처리를 시나리오로 `commit` 성공 케이스와 `rollback` 실패 케이스를 비교한다.

```java
try (Connection conn = dataSource.getConnection()) {
    conn.setAutoCommit(false); // 트랜잭션 시작

    try {
        // 1단계: 주문 INSERT
        long orderId;
        try (PreparedStatement pstmt = conn.prepareStatement(orderSql, new String[]{"ID"})) {
            pstmt.setLong(1, customerId);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                rs.next();
                orderId = rs.getLong(1); // GENERATED KEY 획득
            }
        }

        // 2단계: 주문 상품 INSERT
        try (PreparedStatement pstmt = conn.prepareStatement(itemSql)) {
            pstmt.setLong(1, orderId);
            pstmt.setLong(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
        }

        conn.commit(); // 두 INSERT 모두 성공 → 영구 반영

    } catch (Exception e) {
        conn.rollback(); // 하나라도 실패 → 두 INSERT 모두 취소
        throw e;
    }
}
```

- Oracle에서 `GENERATED ALWAYS AS IDENTITY` 컬럼의 생성 키를 얻으려면 `prepareStatement(sql, new String[]{"ID"})`와 같이 컬럼명 배열을 지정한다.
- `try-catch` 안에서 예외 발생 시 반드시 `rollback()`을 호출해야 변경 내용이 취소된다. 호출하지 않으면 Connection이 반납될 때까지 잠금이 유지된다.
- `conn.close()` 전에 `commit()`이나 `rollback()`이 호출되지 않으면 드라이버에 따라 자동으로 `rollback`된다 (Oracle JDBC 기본 동작).
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App
  ```

---

### App2 - Savepoint

`Savepoint`로 트랜잭션 내 일부 변경만 취소하는 방법을 다룬다.

```java
conn.setAutoCommit(false);

// 1단계: 첫 번째 고객 INSERT 성공
pstmt.setString(1, "박민준"); ...
pstmt.executeUpdate();

// Savepoint 설정: 이 시점 이전 변경은 rollback해도 보존됨
Savepoint sp = conn.setSavepoint("after_park");

// 2단계: 두 번째 고객 INSERT → 중복 이메일(UK 제약 위반)로 예외 발생
try {
    pstmt.setString(2, "hong@example.com"); // 이미 존재하는 이메일
    pstmt.executeUpdate(); // 예외 발생

} catch (Exception e) {
    // Savepoint 이후(두 번째 INSERT)만 취소, 첫 번째 INSERT는 유지
    conn.rollback(sp);
}

conn.commit(); // 첫 번째 고객만 DB에 반영됨
```

- `setSavepoint(name)`: 이름이 있는 Savepoint는 여러 개 설정할 수 있다. 이름 없이 `setSavepoint()`도 가능하다.
- `rollback(sp)` 이후에도 트랜잭션은 종료되지 않는다. 계속 SQL을 실행하고 최종적으로 `commit()` 또는 `rollback()`을 호출해야 한다.
- `releaseSavepoint(sp)`는 Savepoint 객체를 메모리에서 해제한다. 중첩된 Savepoint가 많을 때 메모리 관리를 위해 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App2
  ```

---

### App3 - 격리 수준(Isolation Level)

JDBC API로 격리 수준을 설정하는 방법과 각 수준의 특성을 확인한다.

```java
// JDBC 격리 수준 상수
Connection.TRANSACTION_NONE             // 0 - 트랜잭션 미지원
Connection.TRANSACTION_READ_UNCOMMITTED // 1 - Oracle 미지원
Connection.TRANSACTION_READ_COMMITTED   // 2 - Oracle 기본값
Connection.TRANSACTION_REPEATABLE_READ  // 4
Connection.TRANSACTION_SERIALIZABLE     // 8

// 격리 수준 설정
conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

// 현재 격리 수준 확인 (정수 반환)
int level = conn.getTransactionIsolation(); // 2

// SERIALIZABLE 설정 예시 (가장 강력한 격리, 성능 저하 주의)
conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
```

- 격리 수준은 `Connection` 단위로 설정된다. 트랜잭션 시작 전에 설정해야 한다.
- Oracle의 기본 격리 수준은 `READ_COMMITTED`(2)이며, MVCC 덕분에 읽기 시 잠금이 발생하지 않는다.
- `Non-Repeatable Read`는 단일 스레드에서 시뮬레이션하기 어렵다. 실제 발생은 두 개의 트랜잭션이 동시에 실행될 때 나타난다.
- 격리 수준이 높아질수록 잠금 경합이 증가해 동시성(Throughput)이 낮아진다. 서비스 특성에 맞게 선택한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App3
  ```

## Exam04 - ResultSet 매핑과 RowMapper 패턴

### 개념

#### ResultSet 수동 매핑

JDBC는 쿼리 결과를 `ResultSet`이라는 커서(cursor) 객체로 반환한다.
개발자가 `rs.getXxx("컬럼명")` 메서드를 직접 호출해 값을 꺼내고, 도메인 객체의 필드에 대입하는 것이 **수동 매핑**이다.

```java
while (rs.next()) {
    Customer c = new Customer();
    c.id        = rs.getLong("id");
    c.name      = rs.getString("name");
    c.email     = rs.getString("email");
    c.city      = rs.getString("city");
    list.add(c);
}
```

##### ResultSet 주요 메서드

| 메서드 | 설명 |
|---|---|
| `rs.next()` | 커서를 다음 행으로 이동한다. 행이 있으면 `true`, 없으면 `false` |
| `rs.getLong("컬럼명")` | `long` 값을 꺼낸다. `NUMBER(19)` 컬럼에 사용 |
| `rs.getInt("컬럼명")` | `int` 값을 꺼낸다 |
| `rs.getString("컬럼명")` | `String` 값을 꺼낸다. `VARCHAR2` 컬럼에 사용 |
| `rs.getDouble("컬럼명")` | `double` 값을 꺼낸다. `NUMBER(12,2)` 컬럼에 사용 |
| `rs.getTimestamp("컬럼명")` | `java.sql.Timestamp` 값을 꺼낸다. `TIMESTAMP` 컬럼에 사용 |
| `rs.wasNull()` | 직전에 읽은 값이 SQL `NULL`이었으면 `true` |

#### 수동 매핑의 반복 문제

테이블(객체)이 늘어날수록 동일한 구조의 코드가 반복된다.

```
findAllCustomers() { 연결 획득 → 쿼리 → ResultSet 순회 → Customer 매핑 → 반환 }
findAllProducts()  { 연결 획득 → 쿼리 → ResultSet 순회 → Product 매핑  → 반환 }
findAllOrders()    { 연결 획득 → 쿼리 → ResultSet 순회 → Order 매핑   → 반환 }
        ↑ 공통 골격은 동일, 변하는 부분은 SQL과 객체 매핑 로직뿐
```

이 반복이 ORM 등장의 배경이며, 단기적으로는 **RowMapper 패턴**으로 개선할 수 있다.

#### RowMapper 패턴

`ResultSet`의 현재 행(row)을 객체로 변환하는 책임을 인터페이스로 분리한다.

```
공통 처리 (query 메서드): 연결 획득 → 쿼리 실행 → ResultSet 순회 → 자원 해제
변하는 부분 (RowMapper): ResultSet 현재 행 → 객체 변환
```

호출 측은 **매핑 로직만** 람다로 제공하면 되며, 나머지 공통 처리는 `query()` 메서드가 담당한다.
Spring의 `JdbcTemplate.query(sql, rowMapper)`가 이 패턴을 라이브러리로 제공한다.

##### RowMapper 패턴 vs 수동 매핑 비교

| 항목 | 수동 매핑 | RowMapper 패턴 |
|---|---|---|
| 연결 획득 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| ResultSet 순회 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| 자원 해제 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| 객체 변환 코드 | 공통 처리와 뒤섞임 | RowMapper 람다로 분리 |
| 새 테이블 추가 | 전체 패턴 반복 작성 | RowMapper 람다만 추가 |

---

### App - ResultSet 수동 매핑

`shop_customer`와 `shop_product`를 수동 매핑으로 조회하고,
두 메서드의 구조가 동일한 **반복 패턴**임을 확인한다.

```java
// 고객 목록 조회: ResultSet → Customer 수동 매핑
static List<Customer> findAllCustomers() throws Exception {
    String sql = "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id";
    List<Customer> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

        // ─ 반복 패턴 시작 ─
        while (rs.next()) {
            Customer c = new Customer();
            c.id        = rs.getLong("id");
            c.name      = rs.getString("name");
            c.email     = rs.getString("email");
            c.city      = rs.getString("city");
            c.createdAt = rs.getTimestamp("created_at");
            list.add(c);
        }
        // ─ 반복 패턴 끝 ─
    }
    return list;
}

// 제품 목록 조회: ResultSet → Product 수동 매핑
// findAllCustomers()와 구조가 완전히 동일하다!
static List<Product> findAllProducts() throws Exception {
    String sql = "SELECT id, dtype, name, price, stock, created_at FROM shop_product ORDER BY id";
    List<Product> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

        // ─ 반복 패턴 시작 ─
        while (rs.next()) {
            Product p = new Product();
            p.id        = rs.getLong("id");
            p.dtype     = rs.getString("dtype");
            p.name      = rs.getString("name");
            p.price     = rs.getDouble("price");
            p.stock     = rs.getInt("stock");
            p.createdAt = rs.getTimestamp("created_at");
            list.add(p);
        }
        // ─ 반복 패턴 끝 ─
    }
    return list;
}
```

- 두 메서드 모두 **연결 획득 → PreparedStatement 생성 → ResultSet 순회 → 객체 대입 → 리스트 반환** 구조가 동일하다.
- 변하는 부분은 SQL 문자열과 `rs.getXxx()` 호출뿐이다.
- 테이블이 10개면 이 패턴이 10번 반복된다. 이것이 ORM(Object-Relational Mapping)이 해결하려는 핵심 문제다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam04.App
  ```

---

### App2 - RowMapper 패턴

`RowMapper` 인터페이스를 도입해 반복 코드를 `query()` 메서드로 통합하고,
매핑 로직만 람다로 제공하는 방식으로 개선한다.

```java
// RowMapper 인터페이스: ResultSet 현재 행 → T 변환 책임
@FunctionalInterface
interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}

// 공통 쿼리 실행 메서드: 연결·ResultSet 처리를 한 곳에서만 담당
static <T> List<T> query(String sql, RowMapper<T> mapper) throws Exception {
    List<T> list = new ArrayList<>();
    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {
        while (rs.next()) {
            list.add(mapper.mapRow(rs)); // 변환 책임을 RowMapper에 위임
        }
    }
    return list;
}

// 호출 측: SQL + 매핑 람다만 작성하면 된다
List<Customer> customers = query(
    "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id",
    rs -> {
        Customer c = new Customer();
        c.id        = rs.getLong("id");
        c.name      = rs.getString("name");
        c.email     = rs.getString("email");
        c.city      = rs.getString("city");
        c.createdAt = rs.getTimestamp("created_at");
        return c;
    });

List<Product> products = query(
    "SELECT id, dtype, name, price, stock FROM shop_product ORDER BY id",
    rs -> {
        Product p = new Product();
        p.id    = rs.getLong("id");
        p.dtype = rs.getString("dtype");
        p.name  = rs.getString("name");
        p.price = rs.getDouble("price");
        p.stock = rs.getInt("stock");
        return p;
    });
```

- `query()` 메서드에서 연결 획득·ResultSet 순회·자원 해제가 단 한 번만 작성된다.
- 호출 측은 **SQL과 매핑 람다**만 제공하면 되므로 새 테이블 추가 시 람다만 작성하면 된다.
- `RowMapper`가 `@FunctionalInterface`이므로 람다 표현식으로 간결하게 전달할 수 있다.
- Spring의 `JdbcTemplate.query(sql, rowMapper)`는 이 패턴에 파라미터 바인딩, 예외 변환 등을 추가한 완성된 구현이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam04.App2
  ```

## Exam05 - JDBC Template 패턴 직접 구현

### 개념

#### 템플릿 콜백(Template-Callback) 패턴

변하지 않는 **공통 로직(템플릿)**과 변하는 부분(**콜백**)을 분리하는 디자인 패턴이다.

```
템플릿 (변하지 않는 것): Connection 획득 → PreparedStatement 생성 → 실행 → 자원 해제
콜백   (변하는 것)     : SQL 문자열, 파라미터 바인딩, 결과 객체 변환
```

템플릿은 한 번만 작성하고, 콜백은 호출할 때마다 람다로 교체한다.
Spring의 `JdbcTemplate`이 이 패턴의 대표적인 구현이다.

#### 이 예제에서 직접 구현하는 클래스

| 클래스/인터페이스 | Spring 대응 | 역할 |
|---|---|---|
| `SimpleJdbcTemplate` | `JdbcTemplate` | 공통 로직(템플릿)을 담당 |
| `StatementSetter` | `PreparedStatementSetter` | PreparedStatement 파라미터 바인딩 콜백 |
| `RowMapper<T>` | `RowMapper<T>` | ResultSet 한 행 → T 변환 콜백 |

#### SimpleJdbcTemplate 메서드 구조

```
query(sql, setter, mapper)
  → 연결 획득 → pstmt 생성 → setter.setValues(pstmt) → 실행 → rs 순회 → mapper.mapRow(rs)

queryForObject(sql, setter, mapper)
  → query() 실행 후 첫 번째 결과 Optional로 반환

update(sql, setter)
  → 연결 획득 → pstmt 생성 → setter.setValues(pstmt) → executeUpdate()
```

#### Spring JdbcTemplate과의 차이

이 예제의 `SimpleJdbcTemplate`은 핵심 원리를 학습하기 위한 단순 구현이다.
Spring의 `JdbcTemplate`은 여기에 다음이 추가된다.

| 기능 | SimpleJdbcTemplate | Spring JdbcTemplate |
|---|---|---|
| 기본 원리 | 동일 | 동일 |
| 예외 처리 | `Exception` 전파 | `DataAccessException` 계층으로 변환 |
| 파라미터 바인딩 | `StatementSetter` 람다 | `Object...` 가변인수 지원 |
| 단건 조회 | `Optional<T>` | `queryForObject()` |
| 네임드 파라미터 | 미지원 | `NamedParameterJdbcTemplate` |
| 트랜잭션 연동 | 미지원 | Spring 트랜잭션 관리와 자동 연동 |

---

### App - SimpleJdbcTemplate 직접 구현

`SimpleJdbcTemplate`을 직접 구현하고, 다건 조회·단건 조회·UPDATE에 활용한다.

```java
// 콜백 인터페이스 정의
@FunctionalInterface
interface StatementSetter {
    void setValues(PreparedStatement pstmt) throws SQLException;
}

@FunctionalInterface
interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}

// 템플릿 클래스: 공통 JDBC 로직 캡슐화
static class SimpleJdbcTemplate {

    private final HikariDataSource dataSource;

    // 다건 조회
    <T> List<T> query(String sql, StatementSetter setter, RowMapper<T> mapper) throws Exception {
        List<T> list = new ArrayList<>();
        try (Connection conn         = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setter.setValues(pstmt);               // 콜백: 파라미터 바인딩
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.mapRow(rs));    // 콜백: 행 변환
                }
            }
        }
        return list;
    }

    // 단건 조회
    <T> Optional<T> queryForObject(String sql, StatementSetter setter, RowMapper<T> mapper)
            throws Exception {
        List<T> result = query(sql, setter, mapper);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    // INSERT / UPDATE / DELETE
    int update(String sql, StatementSetter setter) throws Exception {
        try (Connection conn         = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setter.setValues(pstmt);               // 콜백: 파라미터 바인딩
            return pstmt.executeUpdate();
        }
    }
}
```

#### 사용 예시

```java
SimpleJdbcTemplate jdbc = new SimpleJdbcTemplate(dataSource);

// 1. 전체 고객 조회 (파라미터 없음)
List<Customer> customers = jdbc.query(
    "SELECT id, name, email, city FROM shop_customer ORDER BY id",
    pstmt -> { /* 파라미터 없음 */ },
    rs -> {
        Customer c = new Customer();
        c.id    = rs.getLong("id");
        c.name  = rs.getString("name");
        c.email = rs.getString("email");
        c.city  = rs.getString("city");
        return c;
    });

// 2. id로 단건 조회
Optional<Customer> found = jdbc.queryForObject(
    "SELECT id, name, email, city FROM shop_customer WHERE id = ?",
    pstmt -> pstmt.setLong(1, 1L), // StatementSetter 람다
    CUSTOMER_MAPPER);              // 재사용 가능한 RowMapper 상수
found.ifPresentOrElse(
    c -> System.out.println(c),
    () -> System.out.println("없음"));

// 3. 가격 범위로 제품 조회
List<Product> expensive = jdbc.query(
    "SELECT id, name, price, stock FROM shop_product WHERE price >= ? ORDER BY price DESC",
    pstmt -> pstmt.setDouble(1, 100_000),
    PRODUCT_MAPPER);

// 4. 재고 UPDATE
int affected = jdbc.update(
    "UPDATE shop_product SET stock = stock - ? WHERE id = ?",
    pstmt -> {
        pstmt.setInt(1, 1);    // 차감량
        pstmt.setLong(2, 1L);  // product id
    });
```

- `RowMapper`를 상수(`CUSTOMER_MAPPER`, `PRODUCT_MAPPER`)로 정의해두면 여러 쿼리에서 재사용할 수 있다.
- 파라미터가 없는 쿼리는 `setter` 자리에 `pstmt -> {}` 빈 람다를 전달한다.
- `queryForObject()`는 결과가 없을 때 `Optional.empty()`를 반환하므로 `NullPointerException` 위험 없이 처리할 수 있다.
- `update()`의 반환값은 영향을 받은 행 수(int)이다. 0이면 조건에 맞는 행이 없었음을 의미한다.
- 이 `SimpleJdbcTemplate`에는 트랜잭션 처리가 없다. 트랜잭션이 필요하면 호출 측에서 `Connection`을 직접 관리하거나 Spring의 `@Transactional`을 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam05.App
  ```

## Exam06 - JPA 소개 & EntityManagerFactory / EntityManager

### 개념

#### JPA(Jakarta Persistence API)란?

자바 ORM(Object-Relational Mapping) 표준 인터페이스다.
개발자는 JPA 인터페이스만 사용하고, 실제 SQL 생성과 실행은 **구현체(Hibernate)**가 담당한다.

```
개발자 코드 (JPA API)
      ↓
  Hibernate (JPA 구현체)
      ↓
    SQL 생성 & 실행
      ↓
  Oracle Database
```

| 항목 | JDBC | JPA |
|---|---|---|
| 쿼리 작성 | 개발자가 SQL 직접 작성 | Hibernate가 SQL을 자동 생성 |
| 객체 변환 | ResultSet → 객체 수동 매핑 | 자동 매핑 |
| 트랜잭션 | `Connection.commit()` | `EntityTransaction.commit()` |
| 반복 코드 | 많음 | 없음 |

#### persistence.xml

JPA 설정 파일이다. `src/main/resources/META-INF/persistence.xml`에 위치한다.

```xml
<persistence version="3.1" xmlns="https://jakarta.ee/xml/ns/persistence">
    <persistence-unit name="exam06" transaction-type="RESOURCE_LOCAL">
        <!-- 관리할 엔티티 클래스 목록 -->
        <class>com.eomcs.advanced.jpa.exam06.Customer</class>
        <exclude-unlisted-classes>true</exclude-unlisted-classes>
        <properties>
            <!-- Hibernate 방언: DB 벤더별 SQL 차이를 Hibernate가 처리 -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.OracleDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <!-- none: 스키마 자동 생성 안 함. 테이블은 미리 만들어야 한다 -->
            <property name="hibernate.hbm2ddl.auto" value="none"/>
        </properties>
    </persistence-unit>
</persistence>
```

- `transaction-type="RESOURCE_LOCAL"`: 스프링 없는 독립 실행 환경에서 사용하는 트랜잭션 유형이다.
- `RESOURCE_LOCAL`: `EntityTransaction`으로 트랜잭션을 직접 관리한다.
- `JTA`: JavaEE/Jakarta EE 컨테이너가 트랜잭션을 관리할 때 사용한다.

접속 정보(URL, username, password)는 소스 코드에서 환경 변수로 읽어 런타임에 전달한다.
→ `persistence.xml`에 계정 정보를 하드코딩하지 않아도 된다.

```java
Map<String, String> props = new HashMap<>();
props.put("jakarta.persistence.jdbc.url",      url);
props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

EntityManagerFactory emf = Persistence.createEntityManagerFactory("exam06", props);
```

#### EntityManagerFactory (EMF)

| 항목 | 설명 |
|---|---|
| 생성 비용 | 크다 (DB 연결풀·메타데이터 초기화) |
| 인스턴스 수 | 앱 당 1개 |
| Thread-safe | Yes |
| 닫기 | 앱 종료 시 `emf.close()` |

#### EntityManager (EM)

| 항목 | 설명 |
|---|---|
| 생성 비용 | 작다 |
| 인스턴스 수 | 요청·트랜잭션 당 1개 |
| Thread-safe | **No** (스레드 간 공유 금지) |
| 닫기 | 요청 처리 후 반드시 `em.close()` |
| 내부 상태 | 영속성 컨텍스트(1차 캐시) 유지 |

#### EntityTransaction

- `em.getTransaction()`으로 획득한다.
- `begin()` → 작업 → `commit()` 또는 `rollback()` 순서로 사용한다.

```java
EntityTransaction tx = em.getTransaction();
tx.begin();
try {
    // ... 작업
    tx.commit();
} catch (Exception e) {
    tx.rollback();
    throw e;
}
```

#### JPQL(Jakarta Persistence Query Language)

- SQL처럼 보이지만 **엔티티 클래스명과 필드명**을 사용한다.
- Hibernate가 이를 DB 방언에 맞는 SQL로 변환한다.

| 구분 | JPQL | SQL |
|---|---|---|
| 대상 | 엔티티 클래스/필드 | 테이블/컬럼 |
| 예시 | `SELECT c FROM Customer c` | `SELECT * FROM shop_customer` |

---

### App - EntityManagerFactory & EntityManager 기초

`Persistence.createEntityManagerFactory()`로 EMF를 생성하고,
JPQL과 `find()`로 고객 데이터를 조회한다.

```java
Map<String, String> props = new HashMap<>();
props.put("jakarta.persistence.jdbc.url",      url);
props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

// EMF 생성: persistence.xml의 "exam06" 단위 초기화
try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("exam06", props)) {

    // EM 생성: 영속성 컨텍스트 포함
    try (EntityManager em = emf.createEntityManager()) {

        // JPQL: 엔티티 클래스명·필드명 사용
        List<Customer> list = em
            .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
            .getResultList();
        list.forEach(System.out::println);

        // 기본 키로 단건 조회: 없으면 null
        Customer c = em.find(Customer.class, 1L);
        System.out.println(c);
    }
    // em.close() 자동 → 영속성 컨텍스트 해제
}
// emf.close() 자동 → DB 연결풀 해제
```

- `emf.isOpen()` / `em.isOpen()`으로 상태를 확인할 수 있다.
- `find()`는 1차 캐시 우선 조회 → 없으면 DB SELECT.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam06.App
  ```

---

### App2 - EntityTransaction으로 CRUD 처리

`EntityTransaction`으로 트랜잭션을 관리하며 INSERT / SELECT / UPDATE / DELETE를 수행한다.

```java
// INSERT: persist() + commit()
EntityTransaction tx = em.getTransaction();
tx.begin();
try {
    Customer c = new Customer();
    c.setName("테스트고객");
    c.setEmail("test@example.com");
    em.persist(c);   // 영속성 컨텍스트 등록 → IDENTITY 전략은 즉시 INSERT+채번
    tx.commit();     // 나머지 전략은 commit 시 INSERT
} catch (Exception e) {
    tx.rollback();
    throw e;
}

// UPDATE: find() 후 필드 수정 → commit() 시 자동 UPDATE (변경 감지)
tx.begin();
Customer managed = em.find(Customer.class, id);
managed.setCity("수원");    // update() 호출 없음!
tx.commit();               // Dirty Checking → UPDATE SQL 자동 실행

// DELETE: remove() + commit()
tx.begin();
Customer toDelete = em.find(Customer.class, id);
em.remove(toDelete);
tx.commit();               // DELETE SQL 실행
```

- **변경 감지(Dirty Checking)**: `find()`로 조회한 영속 상태 엔티티의 필드를 수정하면, `commit()` 시 Hibernate가 변경을 감지하여 UPDATE SQL을 자동 실행한다.
- `IDENTITY` 전략(`GENERATED ALWAYS AS IDENTITY`)에서는 `persist()` 시점에 즉시 INSERT가 실행되어 id가 채번된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam06.App2
  ```

## Exam07 - 기본 엔티티 매핑

### 개념

#### @Entity

JPA가 관리할 클래스임을 선언한다.

- `public` 또는 `protected` 기본 생성자가 반드시 있어야 한다 (JPA가 리플렉션으로 객체를 생성하기 때문).
- `final` 클래스, `enum`, `interface`는 엔티티가 될 수 없다.
- 필드를 `final`로 선언하면 안 된다.

#### @Table

매핑할 테이블을 지정한다. 생략하면 클래스명을 테이블명으로 사용한다.

```java
@Entity
@Table(name = "shop_customer")  // 매핑 테이블: shop_customer
public class Customer { ... }
```

#### @Id & @GeneratedValue

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

| 전략 | 설명 | Oracle 대응 |
|---|---|---|
| `IDENTITY` | DB의 IDENTITY 기능 사용 | `GENERATED ALWAYS AS IDENTITY` |
| `SEQUENCE` | DB 시퀀스 사용 | `CREATE SEQUENCE` |
| `AUTO` | Hibernate가 DB에 맞게 자동 선택 | DB 방언에 따라 결정 |
| `TABLE` | 별도 키 생성 테이블 사용 | 거의 사용 안 함 |

`IDENTITY` 전략: `persist()` 호출 시 즉시 INSERT → DB가 id를 채번 → 객체에 반영.

#### @Column

| 속성 | 설명 | 기본값 |
|---|---|---|
| `name` | DB 컬럼명 (Java 필드명과 다를 때 명시) | 필드명 |
| `nullable` | NOT NULL 제약 | `true` |
| `length` | VARCHAR 길이 | 255 |
| `precision` | NUMBER 전체 자릿수 | 0 |
| `scale` | NUMBER 소수점 자릿수 | 0 |
| `unique` | UNIQUE 제약 | `false` |
| `insertable` | INSERT 시 포함 여부 | `true` |
| `updatable` | UPDATE 시 포함 여부 | `true` |

```java
@Column(name = "email", nullable = false, length = 200, unique = true)
private String email;

// BigDecimal: 금액처럼 정밀도가 중요한 숫자에 사용 (double은 이진 오차 위험)
@Column(name = "price", nullable = false, precision = 12, scale = 2)
private BigDecimal price;

// Java LocalDateTime ↔ Oracle TIMESTAMP 자동 변환
@Column(name = "created_at")
private LocalDateTime createdAt;
```

#### JPQL 기초

JPQL은 테이블·컬럼명이 아닌 **엔티티 클래스명·필드명**을 사용한다.

```
SQL  : SELECT id, name, city FROM shop_customer WHERE city = '서울' ORDER BY id
JPQL : SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id
```

| 구분 | SQL | JPQL |
|---|---|---|
| 조회 대상 | 테이블 (행/컬럼) | 엔티티 (객체) |
| 클래스/테이블 | 테이블명 | 엔티티 클래스명 |
| 컬럼 | 컬럼명 | Java 필드명 |
| 파라미터 | `?` (위치 기반) | `:이름` (이름 기반) |

---

### App - 기본 CRUD (persist, find, 변경 감지, remove)

엔티티를 저장·조회·수정·삭제하는 전체 흐름을 확인한다.

```java
// INSERT: persist() + commit()
Customer c = new Customer();
c.setName("JPA테스터");
c.setEmail("jpa@test.com");
c.setCity("서울");
em.persist(c);
tx.commit();            // → INSERT INTO shop_customer ...
Long id = c.getId();   // IDENTITY 전략: persist() 직후 id 채번

// SELECT: find(클래스, 기본 키)
Customer found = em.find(Customer.class, id);

// UPDATE: find() 후 필드 수정 → commit() 시 자동 UPDATE (별도 update() 없음)
Customer managed = em.find(Customer.class, id);
managed.setCity("부산");
tx.commit();            // → UPDATE shop_customer SET city=? WHERE id=?

// DELETE: find() → remove() → commit()
Customer toDelete = em.find(Customer.class, id);
em.remove(toDelete);
tx.commit();            // → DELETE FROM shop_customer WHERE id=?
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App
  ```

---

### App2 - JPQL 조건 조회 & 집계

JPQL로 `WHERE`, `LIKE`, `ORDER BY`, `COUNT`, `AVG`를 사용한다.

```java
// 전체 조회: ORDER BY c.id (Java 필드명)
List<Customer> all = em
    .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
    .getResultList();

// 조건 조회: :파라미터명 바인딩
List<Customer> seoulList = em
    .createQuery("SELECT c FROM Customer c WHERE c.city = :city", Customer.class)
    .setParameter("city", "서울")
    .getResultList();

// LIKE 조회
List<Customer> likeList = em
    .createQuery("SELECT c FROM Customer c WHERE c.name LIKE :pattern", Customer.class)
    .setParameter("pattern", "홍%")
    .getResultList();

// 집계: COUNT → Long, AVG → Double
Long count = em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
Double avg  = em.createQuery("SELECT AVG(p.price) FROM Product p", Double.class).getSingleResult();
```

- `createQuery(jpql, 반환타입.class)` → `TypedQuery<T>` (컴파일 시 타입 확인).
- `getResultList()` → `List<T>` (결과 0건이면 빈 리스트, null 아님).
- `getSingleResult()` → `T` (정확히 1건, 0건이거나 2건 이상이면 예외).
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App2
  ```

## Exam08 - 영속성 컨텍스트(Persistence Context)

### 개념

#### 영속성 컨텍스트란?

EntityManager 내부에 존재하는 **엔티티 관리 저장소**다.
영속(managed) 상태의 엔티티를 `Map<@Id, Entity>` 형태로 관리한다.

```
EntityManager
  └── 영속성 컨텍스트
        ├── 1차 캐시 (Map<Id, Entity>)
        ├── 스냅샷 (초기 상태 복사본)
        └── 쓰기 지연 SQL 저장소
```

#### 1차 캐시(First-Level Cache)

같은 EntityManager 범위 안에서 동일한 id로 `find()`를 두 번 호출하면,
두 번째부터는 DB에 접근하지 않고 1차 캐시에서 반환한다.

```java
Customer c1 = em.find(Customer.class, 1L);   // DB SELECT 실행
Customer c2 = em.find(Customer.class, 1L);   // 캐시 반환, SQL 없음
```

```
[실행되는 SQL]
SELECT id, name, ... FROM shop_customer WHERE id = ?   ← 1번만
```

1차 캐시의 범위는 **EntityManager 인스턴스**다. EM이 닫히면 캐시도 소멸한다.

#### 동일성(Identity) 보장

같은 EM 안에서 같은 id로 조회하면 항상 **동일한 인스턴스(==)**를 반환한다.

```java
Customer c1 = em.find(Customer.class, 1L);
Customer c2 = em.find(Customer.class, 1L);

System.out.println(c1 == c2);   // true
```

JDBC 수동 매핑은 `find()`마다 `new Customer()`를 생성하므로 `==`가 `false`다.

#### 변경 감지(Dirty Checking)

영속 상태 엔티티의 필드를 수정하면, `commit()` 시 Hibernate가 자동으로 UPDATE를 실행한다.
별도로 `update()` 메서드를 호출할 필요가 없다.

```
find() 시점: 엔티티 + 스냅샷(복사본) 저장
         ↓
필드 수정 (setter 호출)
         ↓
commit() 직전 flush():
  현재 엔티티 ≠ 스냅샷  →  UPDATE SQL 생성·실행
  현재 엔티티 = 스냅샷  →  UPDATE 미실행 (최적화)
```

```java
EntityTransaction tx = em.getTransaction();
tx.begin();
Customer managed = em.find(Customer.class, 1L); // 스냅샷 저장
managed.setCity("부산");                         // 필드 변경
tx.commit();    // 변경 감지 → UPDATE shop_customer SET city=? WHERE id=?
```

#### 쓰기 지연(Write-Behind / Transactional Write-Behind)

`persist()`, `remove()` 같은 변경 작업은 즉시 SQL을 실행하지 않고,
**쓰기 지연 SQL 저장소**에 모았다가 `flush()` 시 한꺼번에 실행한다.

```
persist(c1) → SQL 저장소에 저장
persist(c2) → SQL 저장소에 저장
persist(c3) → SQL 저장소에 저장
      ↓
commit() → flush() → INSERT × 3 일괄 실행 → DB 반영
```

#### flush()

영속성 컨텍스트의 변경 내용을 DB에 반영하는 작업이다.

| 항목 | flush() | commit() |
|---|---|---|
| SQL 실행 | Yes | Yes (flush 후 커밋) |
| 트랜잭션 종료 | No | Yes |
| 호출 | 수동(`em.flush()`) 또는 자동 | `tx.commit()` |

**FlushModeType:**
- `AUTO` (기본값): JPQL 쿼리 실행 직전 & `commit()` 직전 자동 flush
- `COMMIT`: `commit()` 직전에만 flush

#### IDENTITY 전략과 쓰기 지연

`IDENTITY` 전략(`GENERATED ALWAYS AS IDENTITY`)은 **쓰기 지연이 적용되지 않는다.**
`persist()` 시점에 즉시 INSERT가 실행되어 DB가 id를 채번한다.
(이유: Hibernate가 id를 즉시 알아야 1차 캐시에 저장할 수 있기 때문)

---

### App - 1차 캐시 & 동일성 보장

같은 id로 `find()`를 두 번 호출해 SQL 실행 횟수와 인스턴스 동일성을 확인한다.

```java
try (EntityManager em = emf.createEntityManager()) {
    Customer c1 = em.find(Customer.class, 1L);   // DB SELECT 실행
    Customer c2 = em.find(Customer.class, 1L);   // 캐시 반환, SQL 없음

    System.out.println(c1 == c2);  // true (동일 인스턴스)
}
```

- `show_sql=true`이면 SELECT가 단 한 번만 출력됨을 확인할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App
  ```

---

### App2 - 변경 감지(Dirty Checking)

`find()` 후 필드를 수정하면 `commit()` 시 자동으로 UPDATE가 실행됨을 확인한다.

```java
EntityTransaction tx = em.getTransaction();
tx.begin();
Customer managed = em.find(Customer.class, id); // 스냅샷 저장
managed.setCity("부산");                         // 수정 (UPDATE 호출 없음)
tx.commit();   // → UPDATE shop_customer SET city=?, updated_at=? WHERE id=?
```

- 변경이 없으면 UPDATE SQL이 실행되지 않는다.
- `rollback()` 시 변경 내용은 DB에 반영되지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App2
  ```

---

### App3 - 쓰기 지연 & flush()

`persist()`를 여러 번 호출한 뒤 `commit()` 시 SQL이 일괄 실행됨과,
`em.flush()` 수동 호출로 SQL을 즉시 실행하는 방법을 확인한다.

```java
// persist() 3번 → commit() 시 INSERT 3개 일괄 실행 (SEQUENCE 전략 기준)
tx.begin();
em.persist(c1);
em.persist(c2);
em.persist(c3);
tx.commit();    // → INSERT × 3

// em.flush(): SQL만 실행, 트랜잭션 유지
tx.begin();
managed.setCity("인천");
em.flush();    // UPDATE 실행 (트랜잭션 유지)
tx.commit();   // 확정

// JPQL 실행 전 AUTO flush: 변경이 반영된 상태에서 SELECT
managed.setCity("광주");          // 미flush 상태
count = em.createQuery(...).getSingleResult();  // flush 후 SELECT
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App3
  ```

## Exam09 - 연관관계 매핑 기초

### 개념

#### 연관관계 매핑이란?

객체의 참조 필드(Customer customer)와 테이블의 외래 키(customer_id)를 연결하는 매핑이다.

```
[객체]                        [테이블]
Order.customer (참조)   ↔    shop_orders.customer_id (FK)
```

#### @ManyToOne - 다대일 단방향

여러 주문(N)이 한 고객(1)에 속하는 관계다.

```java
// Order: N 쪽 (연관관계 주인, FK 보유)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;
```

| 어노테이션 | 역할 |
|---|---|
| `@ManyToOne` | 다대일 관계 선언 |
| `fetch = LAZY` | 지연 로딩 (사용 시점에 SELECT) |
| `fetch = EAGER` | 즉시 로딩 (조회 시 항상 JOIN) |
| `@JoinColumn(name)` | FK 컬럼명 지정 |

#### @OneToMany + mappedBy - 일대다 양방향

단방향에 더해 Customer에서도 주문 목록을 탐색할 수 있다.

```java
// Customer: 1 쪽 (비주인, 읽기 전용)
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
private List<Order> orders = new ArrayList<>();
```

#### 연관관계의 주인(Owner)

양방향 매핑에서 **FK를 보유한 쪽(N 쪽)이 주인**이다.

| 구분 | 주인 (Order.customer) | 비주인 (Customer.orders) |
|---|---|---|
| FK | 보유 (customer_id) | 없음 |
| DB 반영 | Yes | **No** (mappedBy 선언으로 읽기 전용) |
| 어노테이션 | `@ManyToOne` + `@JoinColumn` | `@OneToMany(mappedBy="...")` |

비주인 필드(`Customer.orders`)에 값을 넣어도 DB에 반영되지 않는다.
DB에 반영하려면 반드시 주인 필드(`order.setCustomer(customer)`)를 설정해야 한다.

#### 양방향 편의 메서드

양쪽 연관관계를 한 번에 설정하는 메서드를 만들면 실수를 줄일 수 있다.

```java
// Customer 측 편의 메서드
public void addOrder(Order order) {
    orders.add(order);        // 비주인 측 설정 (1차 캐시 일관성)
    order.setCustomer(this);  // 주인 측 설정 (DB 반영)
}
```

#### 지연 로딩(Lazy Loading)

```java
// LAZY: order.getCustomer() 호출 시 SELECT 실행
@ManyToOne(fetch = FetchType.LAZY)
private Customer customer;

Order order = em.find(Order.class, 1L);
// 여기까지 customer SELECT 없음
Customer c = order.getCustomer(); // 이 시점에 SELECT 실행
System.out.println(c.getName());
```

- `@ManyToOne`의 기본값은 `EAGER`이지만 성능 최적화를 위해 `LAZY` 권장.
- `@OneToMany`의 기본값은 `LAZY`.

---

### App - 단방향 @ManyToOne

Order에서 Customer로의 단방향 참조를 통해 연관 데이터를 탐색한다.

```java
// 주문 목록 조회 후 연관 고객 접근 (LAZY 로딩)
List<Order> orders = em
    .createQuery("SELECT o FROM Order o ORDER BY o.id", Order.class)
    .getResultList();

for (Order o : orders) {
    Customer c = o.getCustomer();  // 이 시점에 SELECT customer 실행
    System.out.println(o + " → " + c);
}

// JPQL JOIN: 연관 엔티티를 JOIN 하여 조회
List<Order> joined = em
    .createQuery("SELECT o FROM Order o JOIN o.customer c ORDER BY o.id", Order.class)
    .getResultList();

// 연관 필드 조건 (Hibernate가 자동으로 JOIN SQL 생성)
List<Order> result = em
    .createQuery("SELECT o FROM Order o WHERE o.customer.name = :name", Order.class)
    .setParameter("name", "홍길동")
    .getResultList();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App
  ```

---

### App2 - 양방향 @OneToMany + @ManyToOne

Customer에서 orders 컬렉션으로 역방향 탐색을 추가한다.

```java
// Customer → orders 탐색 (지연 로딩: getOrders() 호출 시 SELECT)
Customer customer = em.find(Customer.class, 1L);
List<Order> orders = customer.getOrders();  // SELECT 실행
System.out.println("주문 수: " + orders.size());

// JPQL: Customer 기준 JOIN
List<Customer> result = em
    .createQuery(
        "SELECT DISTINCT c FROM Customer c JOIN c.orders o ORDER BY c.id",
        Customer.class)
    .getResultList();
```

주의: 모든 고객을 반복하며 `getOrders()`를 호출하면 **N+1 문제**가 발생한다.
- 고객 1건 SELECT + 고객 수 N번 주문 SELECT = N+1 쿼리
- 해결: `JOIN FETCH` (exam18에서 다룸)

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App2
  ```

## Exam10 - 엔티티 생명주기

### 개념

#### 엔티티의 4가지 상태

```
                  persist()
[비영속(New)] ──────────────→ [영속(Managed)]
                                    │  │
                  find()/merge()     │  │  detach()
                  ←────────────────  │  └──────────→ [준영속(Detached)]
                                    │                       │
                                    │  remove()             │  merge()
                                    ↓                       ↓
                             [삭제(Removed)]          [영속(Managed)]
                                    │
                              commit()
                                    ↓
                               DB DELETE
```

#### 1. 비영속(New / Transient)

`new`로 생성했지만 영속성 컨텍스트와 연관이 없는 상태다.

```java
Customer c = new Customer();  // 비영속
c.setName("홍길동");
// DB와 무관. id = null. Hibernate가 관리하지 않음.
```

#### 2. 영속(Managed)

영속성 컨텍스트가 관리하는 상태다.

```java
em.persist(c);              // 비영속 → 영속
Customer c = em.find(Customer.class, 1L);  // DB 조회 → 영속
Customer c = em.merge(detached);           // 준영속 → 영속 (새 인스턴스)

em.contains(c);  // true
```

- 1차 캐시에 저장됨.
- 변경 감지(Dirty Checking) 작동.
- `commit()` 시 변경 내용이 DB에 반영됨.

#### 3. 준영속(Detached)

한때 영속이었지만 영속성 컨텍스트에서 분리된 상태다.

```java
em.detach(c);   // 특정 엔티티를 준영속으로
em.clear();     // 영속성 컨텍스트 전체 초기화 → 모든 엔티티 준영속
em.close();     // EM 닫기 → 모든 엔티티 준영속
```

- `em.contains(c)` → `false`
- 변경 감지 작동 안 함 → 필드 수정이 DB에 반영되지 않음.
- `merge()`로 다시 영속 상태로 만들 수 있음.

#### 4. 삭제(Removed)

삭제 예약 상태. `commit()` 시 DELETE SQL이 실행된다.

```java
em.remove(c);   // 삭제 예약 → em.contains(c) = false
tx.commit();    // DELETE SQL 실행
```

#### 주요 메서드 정리

| 메서드 | 역할 | 상태 전환 |
|---|---|---|
| `persist(entity)` | 새 엔티티 저장 | 비영속 → 영속 |
| `find(Class, id)` | 기본 키로 조회 | → 영속 |
| `merge(entity)` | 준영속 엔티티 병합 | 준영속 → 영속 (새 인스턴스) |
| `remove(entity)` | 엔티티 삭제 예약 | 영속 → 삭제 |
| `detach(entity)` | 특정 엔티티 분리 | 영속 → 준영속 |
| `clear()` | 컨텍스트 초기화 | 모든 영속 → 준영속 |
| `flush()` | 변경 내용 DB 전송 | 상태 변경 없음 |
| `contains(entity)` | 영속 여부 확인 | - |

#### merge() 주의사항

`merge()`는 넘겨받은 준영속 엔티티를 그대로 영속으로 바꾸지 않는다.
**새로운 영속 엔티티 인스턴스를 반환**한다.

```java
Customer detached = ...; // 준영속
detached.setCity("부산");

Customer merged = em.merge(detached); // 새 영속 인스턴스 반환
// merged: 영속 상태, em.contains(merged) = true
// detached: 여전히 준영속, em.contains(detached) = false

tx.commit(); // merged의 변경이 DB에 반영됨 (city='부산')
```

---

### App - 엔티티 생명주기 전체 흐름

비영속 → 영속 → 준영속 → 영속(merge) → 삭제 순서로 각 상태를 직접 확인한다.

```java
// 1. 비영속
Customer c = new Customer();   // id = null, 관리 안 됨

// 2. 영속 (persist)
em.persist(c);                 // em.contains(c) = true, id 채번
tx.commit();

// 3. 영속 (find)
Customer managed = em.find(Customer.class, id);  // DB SELECT → 영속

// 4. 준영속 (detach)
em.detach(managed);            // em.contains(managed) = false
managed.setCity("부산");       // 변경 감지 안 됨 → DB 미반영

// 5. 영속 복귀 (merge)
Customer merged = em.merge(c); // 준영속 → 영속 (새 인스턴스)
tx.commit();                   // merged의 변경 DB 반영

// 6. 컨텍스트 초기화 (clear)
em.clear();                    // 모든 엔티티 준영속
                               // 이후 find() → DB 재조회

// 7. 삭제 (remove)
Customer toRemove = em.find(Customer.class, id);
em.remove(toRemove);           // 삭제 예약
tx.commit();                   // DELETE 실행
em.find(Customer.class, id);   // null
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam10.App
  ```

## exam11 - 상속 매핑 전략

### 개념

#### JPA 상속 매핑이란

객체 지향의 상속 관계를 관계형 DB 테이블 구조로 표현하는 방법이다.
관계형 DB는 상속 개념이 없으므로 JPA가 이를 3가지 전략 중 하나로 변환한다.

```
[Java]                         [DB]
Product (abstract)
  ├─ PhysicalProduct    →  ?  (전략에 따라 테이블 구조가 달라진다)
  └─ DigitalProduct
```

#### 3가지 상속 매핑 전략

| 전략 | 테이블 수 | 조회 | NULL | 추천 상황 |
|---|---|---|---|---|
| `SINGLE_TABLE` | 1 | 빠름 (JOIN 없음) | 많음 | 단순하고 빠른 조회 필요 시 |
| `JOINED` | 1 + 자식 수 | JOIN 발생 | 없음 | **정규화가 중요할 때 (기본 권장)** |
| `TABLE_PER_CLASS` | 자식 수 | UNION | 없음 | 특정 타입만 단독 조회 시 |

**SINGLE_TABLE**: 부모·자식의 모든 컬럼을 하나의 테이블에 저장. `dtype` 컬럼으로 타입 구분. 자식 전용 컬럼은 NULL 허용.

**JOINED** (이 예제): 부모 테이블과 자식별 테이블로 정규화. 조회 시 JOIN 발생. NULL 없이 깔끔한 구조.

**TABLE_PER_CLASS**: 자식 엔티티마다 독립된 테이블(부모 컬럼 포함). 부모 타입으로 다형성 조회 시 UNION으로 느려짐.

#### @DiscriminatorColumn

부모 테이블에 저장되는 자식 타입 구분 컬럼이다.

| 전략 | 필요 여부 |
|---|---|
| `SINGLE_TABLE` | **필수** — 구분 컬럼 없이는 타입을 알 수 없음 |
| `JOINED` | **선택적** — 자식 테이블 PK 존재 여부로 판별 가능하나, 명시하면 성능에 도움 |
| `TABLE_PER_CLASS` | **불필요** — 타입별 테이블이 있어 의미 없음 |

---

### 사용 테이블

```
shop_product          (부모)
shop_physical_product (자식 - 실물 제품)
shop_digital_product  (자식 - 디지털 제품)
```

---

### 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@Inheritance(strategy = JOINED)` | 부모 엔티티 | 상속 전략 선택 |
| `@DiscriminatorColumn(name = "dtype")` | 부모 엔티티 | 타입 구분 컬럼 |
| `@DiscriminatorValue("PHYSICAL")` | 자식 엔티티 | 구분 값 지정 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | 자식 엔티티 | 자식 테이블의 PK/FK 컬럼명 |

---

### 3가지 전략 비교

| 전략 | 테이블 수 | 조회 | NULL | 추천 상황 |
|---|---|---|---|---|
| `SINGLE_TABLE` | 1 | 빠름 (JOIN 없음) | 많음 | 단순하고 빠른 조회 필요 시 |
| `JOINED` | 1 + 자식 수 | JOIN 발생 | 없음 | **정규화가 중요할 때 (기본 권장)** |
| `TABLE_PER_CLASS` | 자식 수 | UNION | 없음 | 특정 타입만 단독 조회 시 |

---

### 엔티티 클래스 구조

#### Product (부모 엔티티)

```java
@Entity
@Table(name = "shop_product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@Inheritance(strategy = JOINED)` | 자식 엔티티마다 별도 테이블을 만들고 JOIN으로 연결하는 전략 선택 |
| `@DiscriminatorColumn(name = "dtype")` | `shop_product.dtype` 컬럼에 자식 타입 식별자(`'PHYSICAL'`, `'DIGITAL'`)를 저장. JOINED 전략에서는 선택적이나 명시하면 다형성 조회 성능이 향상된다 |

- `abstract`로 선언 → 직접 인스턴스화 불가, 반드시 자식 타입으로만 저장/조회
- `id`는 `IDENTITY` 전략으로 자동 생성되며, 자식 테이블의 PK(`product_id`)로도 사용된다

---

#### PhysicalProduct (자식 엔티티 - 실물 제품)

```java
@Entity
@Table(name = "shop_physical_product")
@DiscriminatorValue("PHYSICAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class PhysicalProduct extends Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@DiscriminatorValue("PHYSICAL")` | `shop_product.dtype = 'PHYSICAL'`인 행이 이 클래스로 매핑됨 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | 자식 테이블(`shop_physical_product`)의 PK 컬럼명을 `product_id`로 지정. 이 컬럼이 동시에 부모 테이블 `id`를 참조하는 FK |

```
INSERT 시 SQL 2회 실행:
  INSERT INTO shop_product (dtype, name, price, ...) VALUES ('PHYSICAL', ...)
  INSERT INTO shop_physical_product (product_id, weight, shipping_fee) VALUES (?, ?, ?)

SELECT 시 INNER JOIN 실행:
  SELECT p.*, pp.weight, pp.shipping_fee
  FROM shop_product p
  JOIN shop_physical_product pp ON p.id = pp.product_id
  WHERE p.id = ?
```

---

#### DigitalProduct (자식 엔티티 - 디지털 제품)

```java
@Entity
@Table(name = "shop_digital_product")
@DiscriminatorValue("DIGITAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class DigitalProduct extends Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@DiscriminatorValue("DIGITAL")` | `shop_product.dtype = 'DIGITAL'`인 행이 이 클래스로 매핑됨 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | PhysicalProduct와 동일한 방식으로 부모 PK를 자식 PK/FK로 연결 |

```
INSERT 시 SQL 2회 실행:
  INSERT INTO shop_product (dtype, name, price, ...) VALUES ('DIGITAL', ...)
  INSERT INTO shop_digital_product (product_id, download_url, license_count) VALUES (?, ?, ?)
```

---

### `@DiscriminatorColumn` 은 반드시 필요한가?

**전략에 따라 다르다.**

| 전략 | 필요 여부 | 이유 |
|------|-----------|------|
| `SINGLE_TABLE` | **필수** (기본 자동 생성) | 하나의 테이블에 모든 타입이 섞이므로 구분 컬럼이 없으면 타입을 알 수 없음 |
| `JOINED` | **선택적** | 자식 테이블 PK의 존재 여부로 타입 판별 가능. 단, 명시하면 조회 성능에 도움 |
| `TABLE_PER_CLASS` | **불필요/무시** | 타입별로 별도 테이블이 있어 구분 컬럼 자체가 의미 없음 |

#### JOINED 전략에서 명시하는 이유

- `SELECT * FROM shop_product` 처럼 **부모 테이블만 조회할 때** dtype 값으로 어떤 자식 타입인지 즉시 확인 가능
- JPA가 다형성 조회 시 JOIN 대상 자식 테이블을 dtype으로 먼저 판단 → **불필요한 JOIN 감소**

> JOINED 전략에서 `@DiscriminatorColumn` 없이도 JPA는 정상 동작하지만, 있으면 성능과 가독성 측면에서 유리하다.

---

### App - JOINED 전략 INSERT/SELECT 전체 흐름

PhysicalProduct·DigitalProduct 저장 후 단건 조회, 다형성 조회, 타입별 JPQL 조회 순서로 JOINED 전략 동작을 확인한다.

```java
// 1. PhysicalProduct 저장 → 부모/자식 테이블에 각각 INSERT
PhysicalProduct laptop = new PhysicalProduct();
laptop.setName("LG 그램 17");
laptop.setPrice(new BigDecimal("1590000"));
laptop.setWeight(new BigDecimal("1.350"));
laptop.setShippingFee(new BigDecimal("3000"));
em.persist(laptop);
tx.commit();
// INSERT INTO shop_product (dtype='PHYSICAL', name, price, ...)
// INSERT INTO shop_physical_product (product_id, weight, shipping_fee)

// 2. DigitalProduct 저장
DigitalProduct sw = new DigitalProduct();
sw.setName("한컴오피스 2024");
sw.setDownloadUrl("https://www.hancom.com/download");
em.persist(sw);
tx.commit();
// INSERT INTO shop_product (dtype='DIGITAL', ...)
// INSERT INTO shop_digital_product (product_id, download_url, license_count)

// 3. 특정 타입 단건 조회 → INNER JOIN
PhysicalProduct found = em.find(PhysicalProduct.class, physicalId);
// SELECT pp.*, p.* FROM shop_physical_product pp
//   JOIN shop_product p ON pp.product_id = p.id WHERE pp.product_id = ?

// 4. 부모 타입으로 다형성 조회 → dtype 보고 적합한 서브클래스 반환
Product byParent = em.find(Product.class, digitalId);
// dtype = 'DIGITAL' → DigitalProduct 인스턴스로 반환
byParent.getClass().getSimpleName(); // "DigitalProduct"

// 5. JPQL 다형성 조회 → 모든 자식 테이블 LEFT JOIN
List<Product> all = em.createQuery(
    "SELECT p FROM Product p ORDER BY p.id", Product.class).getResultList();
// SELECT p.*, dp.*, pp.* FROM shop_product p
//   LEFT JOIN shop_digital_product dp ON p.id = dp.product_id
//   LEFT JOIN shop_physical_product pp ON p.id = pp.product_id

// 6. JPQL 타입별 조회 → 해당 자식 테이블만 INNER JOIN
List<PhysicalProduct> physicals = em.createQuery(
    "SELECT p FROM PhysicalProduct p ORDER BY p.id", PhysicalProduct.class).getResultList();
// SELECT pp.*, p.* FROM shop_physical_product pp
//   JOIN shop_product p ON pp.product_id = p.id
```

- 실행 명령:
  ```bash
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam11.App
  ```

## exam12 - @Embeddable / @Embedded

### 개념

#### 값 타입(Value Type)이란

JPA에서 데이터를 표현하는 방법은 엔티티 타입과 값 타입 두 가지다.

| 구분 | 엔티티 타입 | 값 타입 |
|---|---|---|
| 식별자(`@Id`) | 있음 | 없음 |
| 생명주기 | 독립적 | 소유 엔티티에 종속 |
| 테이블 | 별도 테이블 | 소유 엔티티 테이블에 합쳐짐 |
| 공유 | 가능 | 불가 (복사해서 사용) |

값 타입은 의미적으로 관련 있는 컬럼들을 하나의 객체로 묶어 **응집도를 높이고** 코드 가독성을 개선한다.

#### @Embeddable / @Embedded

```
[Java]                      [DB - shop_customer 테이블]
Customer                      id
  - id          →             name
  - name        →             email
  - email       →             city       ┐
  - address     →             street     ├─ Address 필드가
      ∟ city                  zipcode    ┘  테이블에 인라인으로 저장
      ∟ street
      ∟ zipcode
```

- `@Embeddable`: 별도 테이블 없이 소유 엔티티 테이블에 컬럼으로 매핑되는 값 타입 클래스 선언
- `@Embedded`: 엔티티에서 값 타입 필드를 사용할 때 선언
- 소유 엔티티가 삭제되면 값 타입 데이터도 함께 사라진다
- `address = null`이면 `city`, `street`, `zipcode` 컬럼 모두 `NULL`로 저장된다

#### @AttributeOverride

같은 `@Embeddable` 타입을 한 엔티티에서 여러 번 사용할 때 컬럼명 충돌을 해소한다.

```java
@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city", column = @Column(name = "home_city"))
})
private Address homeAddress;

@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city", column = @Column(name = "work_city"))
})
private Address workAddress;
```

---

### 사용 테이블

```
shop_customer
  - city, street, zipcode → Address 값 타입으로 그룹화
```

---

### 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@Embeddable` | 값 타입 클래스 | 별도 테이블 없이 소유 엔티티 테이블에 매핑되는 값 타입 선언 |
| `@Embedded` | 엔티티 필드 | 값 타입 필드 선언 |
| `@AttributeOverride` | 엔티티 필드 | 같은 값 타입을 재사용할 때 컬럼명 재정의 |

---

### 엔티티 vs 값 타입

| 구분 | 엔티티 | 값 타입 |
|---|---|---|
| 식별자(`@Id`) | 있음 | 없음 |
| 생명주기 | 독립적 | 소유 엔티티에 종속 |
| 테이블 | 별도 테이블 | 소유 엔티티 테이블에 합쳐짐 |

---

### 엔티티 클래스 구조

#### Address (값 타입)

```java
@Embeddable
public class Address {
  @Column(length = 100) private String city;
  @Column(length = 200) private String street;
  @Column(length = 20)  private String zipcode;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@Embeddable` | 독립적인 생명주기가 없는 값 타입 선언. 자체 `@Id`가 없으며, 소유 엔티티(`Customer`)의 테이블 컬럼으로 매핑된다 |

- 별도 테이블이 생성되지 않는다 → `shop_customer` 테이블의 `city`, `street`, `zipcode` 컬럼으로 저장
- 소유 엔티티가 삭제되면 값 타입 데이터도 함께 삭제된다

---

#### Customer (엔티티)

```java
@Entity
@Table(name = "shop_customer")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private Address address;   // city, street, zipcode → shop_customer 테이블에 저장
}
```

| 어노테이션 | 의미 |
|---|---|
| `@Embedded` | `@Embeddable`로 정의된 값 타입 필드를 소유 엔티티 테이블에 인라인으로 저장 |

- `address`가 `null`이면 `city`, `street`, `zipcode` 컬럼이 모두 `NULL`로 저장된다
- `@AttributeOverride`를 사용하면 같은 `Address` 타입을 여러 필드(예: `homeAddress`, `workAddress`)에 재사용할 때 컬럼명을 재정의할 수 있다

```java
// @AttributeOverride 사용 예 (이 예제에서는 미사용)
@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city",    column = @Column(name = "home_city")),
  @AttributeOverride(name = "street",  column = @Column(name = "home_street")),
  @AttributeOverride(name = "zipcode", column = @Column(name = "home_zipcode"))
})
private Address homeAddress;
```

---

### App - @Embedded 저장/조회/수정/JPQL 전체 흐름

`Address` 값 타입이 포함된 `Customer`를 저장하고, 조회·수정·JPQL 조건 검색 순서로 `@Embedded` 동작을 확인한다.

```java
// 1. Customer + Address 저장
Customer customer = new Customer();
customer.setName("박지수");
customer.setAddress(new Address("인천", "송도대로 77", "21999"));
em.persist(customer);
tx.commit();
// INSERT INTO shop_customer (name, email, city, street, zipcode, ...)
// → Address 필드가 shop_customer 테이블 컬럼으로 그대로 저장된다

// 2. 조회 후 Address 접근
Customer found = em.find(Customer.class, savedId);
found.getAddress().getCity();   // "인천"
found.getAddress().getStreet(); // "송도대로 77"

// 3. Address 수정 → Dirty Checking으로 UPDATE 자동 실행
Customer managed = em.find(Customer.class, savedId);
managed.setAddress(new Address("서울", "강남대로 100", "06000"));
tx.commit();
// UPDATE shop_customer SET city='서울', street='강남대로 100', zipcode='06000' WHERE id=?

// 4. Address = null 저장 → 컬럼 전체 NULL
Customer noAddr = new Customer();
noAddr.setName("주소없는고객");
// address 미설정 → city/street/zipcode 모두 NULL

// 5. JPQL에서 임베디드 필드 접근: '엔티티필드.임베디드필드' 경로 표현식 사용
List<Customer> result = em.createQuery(
    "SELECT c FROM Customer c WHERE c.address.city = :city", Customer.class)
    .setParameter("city", "서울")
    .getResultList();
// → 내부적으로 WHERE city = '서울' 로 변환
```

- 실행 방법:
  ```bash
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam12.App
  ```

## exam13 - 다대다(@ManyToMany) & 중간 테이블

### 개념

#### 다대다(M:N) 관계와 중간 테이블

관계형 DB는 다대다 관계를 직접 표현할 수 없다. 두 테이블 사이에 **중간 테이블**을 두어 1:N + N:1 두 개의 관계로 분해한다.

```
[Java]                           [DB]
Product ─── @ManyToMany ───→  shop_product
                                    │  (product_id FK)
Category ← @ManyToMany ───       shop_product_category  ← 중간 테이블
                                    │  (category_id FK)
                               shop_category
```

JPA는 `@ManyToMany` + `@JoinTable`로 중간 테이블을 자동 관리한다. 단, **중간 테이블에 추가 컬럼(등록일, 수량 등)을 넣을 수 없다**는 한계가 있다.

#### @ManyToMany의 한계와 연결 엔티티

| 방식 | 중간 테이블 관리 | 추가 속성 | JPQL 직접 조회 | 실무 권장 |
|---|---|---|---|---|
| `@ManyToMany` (App1) | JPA 자동 | 불가 | 불가 | 단순 관계 |
| 연결 엔티티 (App2) | 직접 엔티티로 관리 | 가능 | 가능 | **확장 가능성 있을 때** |

추가 속성이 필요하거나 중간 테이블을 직접 조회해야 한다면, `@ManyToMany` 대신 **연결 엔티티**(`ProductCategory`)로 리팩토링해야 한다.

#### @MapsId

연결 엔티티에서 `@EmbeddedId`의 복합 PK 필드와 `@ManyToOne` FK를 하나로 연동하는 어노테이션이다.

```java
@EmbeddedId
private ProductCategoryId id;        // 복합 PK: (productId, categoryId)

@ManyToOne
@MapsId("productId")                 // id.productId ← product.id 로 채워짐
@JoinColumn(name = "product_id")
private Product product;
```

`@MapsId` 없이는 복합 PK 필드와 FK 컬럼을 별도로 관리해야 하고 값을 두 번 설정해야 한다.

---

### 사용 테이블

```
shop_product          (제품)
shop_category         (카테고리)
shop_product_category (중간 테이블 - product_id, category_id 복합 PK)
```

---

### App1 vs App2 비교

| 구분 | App1 (@ManyToMany) | App2 (연결 엔티티) |
|---|---|---|
| 중간 테이블 관리 | JPA 자동 | 직접 엔티티로 관리 |
| 추가 속성 | 불가 | 가능 (createdAt 등) |
| 직접 조회 | 불가 | JPQL로 직접 조회 가능 |
| 복잡도 | 낮음 | 높음 |
| 실무 권장 | 단순 관계 | **확장 가능성 있을 때** |

---

### 핵심 어노테이션

| 어노테이션 | 설명 |
|---|---|
| `@ManyToMany` | 다대다 관계 선언 |
| `@JoinTable` | 중간 테이블 및 FK 컬럼 정의 |
| `@EmbeddedId` | 복합 PK 매핑 (연결 엔티티) |
| `@MapsId` | @EmbeddedId 필드와 @ManyToOne FK 연동 |

---

### 엔티티 클래스 구조

#### Product (다대다 주인)

```java
@Entity
@Table(name = "shop_product")
public class Product {

  @ManyToMany
  @JoinTable(
      name = "shop_product_category",
      joinColumns        = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToMany` | 다대다 관계 선언. 관계형 DB는 중간 테이블이 필요하므로 JPA가 자동으로 관리 |
| `@JoinTable(name = "shop_product_category")` | 중간 테이블명 지정 |
| `joinColumns` | 현재 엔티티(`Product`)의 FK 컬럼 (`product_id`) |
| `inverseJoinColumns` | 반대편 엔티티(`Category`)의 FK 컬럼 (`category_id`) |

- `@JoinTable`을 소유한 쪽이 연관관계 주인이다
- 중간 테이블에 추가 컬럼(등록일 등)을 넣을 수 없다는 것이 `@ManyToMany`의 한계

---

#### Category (다대다 비주인)

```java
@Entity
@Table(name = "shop_category")
public class Category {

  @ManyToMany(mappedBy = "categories")
  private List<Product> products = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToMany(mappedBy = "categories")` | `Product.categories`가 주인임을 지정. 비주인은 읽기 전용 참조만 가진다 |

---

#### ProductCategoryId (복합 PK 클래스)

```java
@Embeddable
public class ProductCategoryId implements Serializable {
  @Column(name = "product_id") private Long productId;
  @Column(name = "category_id") private Long categoryId;
  // equals() / hashCode() 필수 구현
}
```

| 요건 | 이유 |
|---|---|
| `@Embeddable` | `@EmbeddedId`로 사용하기 위한 선언 |
| `Serializable` 구현 | JPA 스펙: PK 클래스는 직렬화 가능해야 함 |
| `equals()` / `hashCode()` | 1차 캐시 조회 및 엔티티 동일성 보장에 사용 |

---

#### ProductCategory (연결 엔티티 - App2 방식)

```java
@Entity
@Table(name = "shop_product_category")
public class ProductCategory {

  @EmbeddedId
  private ProductCategoryId id = new ProductCategoryId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "created_at")
  private LocalDateTime createdAt;  // @ManyToMany로는 불가능한 추가 속성
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EmbeddedId` | `@Embeddable` 복합 PK 클래스를 단일 필드로 선언 |
| `@MapsId("productId")` | `@EmbeddedId`의 `productId` 필드와 `@ManyToOne` FK를 연동. `product.id`가 복합 PK의 `productId`로 사용됨 |
| `@MapsId("categoryId")` | 동일하게 `category.id`를 복합 PK의 `categoryId`로 연동 |

- `@ManyToMany` 대비 장점: 중간 테이블에 `createdAt` 등 추가 속성을 붙일 수 있고 연결 자체를 JPQL로 직접 조회 가능

---

### App1 - @ManyToMany 기본 사용법

`Product ↔ Category` 다대다 관계를 `@ManyToMany`로 관리하는 방식을 확인한다.

```java
// 1. Product → Category 탐색 (지연 로딩)
List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
product.getCategories(); // ← 이 시점에 SELECT FROM shop_product_category JOIN 실행

// 2. Category → Product 역방향 탐색 (비주인)
category.getProducts(); // 읽기만 가능, INSERT/DELETE 영향 없음

// 3. 새 Product + Category 연결 저장
Product newProduct = new Product();
newProduct.addCategory(elec);   // 양방향 동기화 편의 메서드
em.persist(newProduct);
// INSERT INTO shop_product (...)
// INSERT INTO shop_product_category (product_id, category_id)

// 4. 연결 삭제
managed.getCategories().clear();
// DELETE FROM shop_product_category WHERE product_id = ?
```

---

### App2 - 연결 엔티티(ProductCategory)로 리팩토링

`@ManyToMany` 대신 `ProductCategory` 엔티티를 직접 사용하여 추가 속성 관리와 직접 조회를 가능하게 한다.

```java
// 1. 연결 엔티티 생성 (createdAt 자동 설정됨)
ProductCategory pc = new ProductCategory(product, elec);
em.persist(pc);
// INSERT INTO shop_product_category (product_id, category_id, created_at)

// 2. JPQL로 연결 엔티티 직접 조회 → 추가 속성(createdAt) 함께 조회 가능
List<ProductCategory> list = em.createQuery(
    "SELECT pc FROM ProductCategory pc ORDER BY pc.id.productId",
    ProductCategory.class).getResultList();
pc.getCreatedAt(); // @ManyToMany로는 접근 불가능한 필드

// 3. 복합 PK로 단건 조회
ProductCategoryId pk = new ProductCategoryId(productId, categoryId);
ProductCategory found = em.find(ProductCategory.class, pk);

// 4. 연결 삭제
em.remove(toRemove);
// DELETE FROM shop_product_category WHERE product_id=? AND category_id=?
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App2
```

## exam14 - 자기 참조 연관관계

### 개념

#### 자기 참조(Self-Join)란

같은 테이블의 다른 행을 FK로 참조하는 구조다. 카테고리·조직도·댓글 계층처럼 **재귀적 트리 구조**를 관계형 DB에 표현할 때 사용한다.

```
[DB - shop_category]           [Java - Category]
id | name       | parent_id
 1 | 전자제품   | NULL       →  parent = null          (루트)
 2 | 노트북     | 1          →  parent = 전자제품
 3 | 게이밍노트북| 2          →  parent = 노트북

[트리 구조]
전자제품 (루트, parent = null)
  └─ 노트북
       └─ 게이밍노트북
```

#### JPA 자기 참조 매핑

엔티티가 자기 자신의 타입을 연관관계 필드로 갖는다.

```java
@ManyToOne  private Category parent;    // 부모 참조 (FK = parent_id)
@OneToMany  private List<Category> children;  // 자식 목록 (mappedBy = "parent")
```

- `parent` 필드가 연관관계 **주인** (FK `parent_id` 관리)
- `children` 필드는 **비주인** (읽기 전용, mappedBy)
- 루트 카테고리는 `parent = null`

#### CascadeType.PERSIST

연관된 엔티티를 함께 저장하는 전파(Cascade) 옵션이다.

```
em.persist(루트);  →  루트, 자식, 손자 모두 INSERT
```

- `CascadeType.REMOVE`를 추가하면 부모 삭제 시 자식도 함께 삭제 — **의도치 않은 대량 삭제에 주의**
- 실무에서는 `PERSIST`만 단독으로 사용하는 경우가 많다

---

### 사용 테이블

```
shop_category
  id        NUMBER PK
  name      VARCHAR2
  parent_id NUMBER FK → shop_category.id  (자기 참조)
```

---

### 계층 구조 예시

```
전자제품 (루트, parent_id = NULL)
  └─ 노트북
       └─ 게이밍노트북

의류 (루트, parent_id = NULL)
  └─ 남성의류
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@ManyToOne` parent | 부모 카테고리 참조. 루트는 `null` |
| `@OneToMany` children | 자식 카테고리 목록 (지연 로딩) |
| `CascadeType.PERSIST` | 부모 persist 시 자식도 자동 persist |
| 루트 조회 JPQL | `WHERE c.parent IS NULL` |
| 자식 조회 JPQL | `WHERE c.parent.id = :id` |

---

### 엔티티 클래스 구조

#### Category (자기 참조 엔티티)

```java
@Entity
@Table(name = "shop_category")
public class Category {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Category> children = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToOne` parent | 부모 카테고리 참조. 같은 `shop_category` 테이블을 자기 참조(Self-Join). 루트는 `null` |
| `@JoinColumn(name = "parent_id")` | FK 컬럼명 지정. 연관관계 주인 |
| `@OneToMany(mappedBy = "parent")` | 자식 카테고리 목록. 비주인(읽기 전용) |
| `CascadeType.PERSIST` | 부모를 `persist`할 때 `children` 목록도 함께 자동 저장 |

- `isRoot()` 메서드: `parent == null`이면 루트 카테고리
- `addChild()` 편의 메서드: 자식 추가 시 `child.parent`도 함께 설정하여 양방향 동기화

```
테이블 구조:
shop_category
  id        PK
  name
  parent_id FK → shop_category.id  (자기 참조)
```

---

### App - 자기 참조 저장/탐색/JPQL 전체 흐름

계층 구조 저장, 트리 탐색, 부모 체인 조회, JPQL 직계 자식 조회 순서로 자기 참조 동작을 확인한다.

```java
// 1. 루트 카테고리 조회 (parent IS NULL)
List<Category> roots = em.createQuery(
    "SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.id",
    Category.class).getResultList();

// 2. 부모 체인 탐색 (지연 로딩으로 조상까지 순회)
Category gaming = em.find(Category.class, 3L);  // 게이밍노트북
gaming.getParent().getName();                    // "노트북"
gaming.getParent().getParent().getName();         // "전자제품"

// 3. CascadeType.PERSIST: 루트만 persist해도 자식/손자까지 함께 저장
Category sports  = new Category("스포츠");
Category outdoor = new Category("아웃도어");
Category hiking  = new Category("등산");

sports.addChild(outdoor);   // outdoor.parent = sports
outdoor.addChild(hiking);   // hiking.parent = outdoor

em.persist(sports);  // sports, outdoor, hiking 모두 INSERT
tx.commit();

// 4. JPQL: 특정 부모의 직계 자식만 조회
List<Category> children = em.createQuery(
    "SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.id",
    Category.class)
    .setParameter("parentId", 1L)
    .getResultList();
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam14.App
```

## exam15 - 복합 키 매핑

### 개념

#### 복합 기본 키(Composite PK)란

둘 이상의 컬럼이 함께 기본 키를 구성하는 구조다. 주문 상세(`order_id + product_id`)처럼 두 FK가 합쳐서 유일성을 보장할 때 사용한다.

```
shop_order_item
  PK = (order_id, product_id)  ← 두 컬럼의 조합이 기본 키
```

JPA는 복합 PK를 반드시 **별도 클래스**로 정의해야 한다. 방법은 두 가지다.

#### @IdClass vs @EmbeddedId

| 구분 | `@IdClass` (App1) | `@EmbeddedId` (App2) |
|---|---|---|
| PK 클래스 | 일반 클래스 (`Serializable` 구현) | `@Embeddable` 클래스 |
| 엔티티 선언 | `@Id`를 여러 개 선언 | `@EmbeddedId` 단일 필드 |
| JPQL 접근 | `oi.orderId` (필드 직접 접근) | `oi.id.orderId` (객체 경유) |
| find() | `new OrderItemId(1L, 1L)` | `new OrderItemPK(1L, 1L)` |
| 특징 | 단순, JPQL 편리 | PK 객체 자체를 다루기 편리, 재사용 가능 |

#### PK 클래스 필수 요건 (JPA 스펙)

두 방식 모두 PK 클래스가 다음 요건을 만족해야 한다.

1. `public` 클래스
2. 기본 생성자 필수
3. `Serializable` 구현 — 1차 캐시 직렬화 등에 사용
4. `equals()` / `hashCode()` 구현 — 동일 PK 엔티티 식별에 사용

---

### 사용 테이블

```
shop_order_item
  order_id   NUMBER  PK(1/2)  FK → shop_orders
  product_id NUMBER  PK(2/2)  FK → shop_product
  quantity   NUMBER
  price      NUMBER
```

---

### 엔티티 클래스 구조

#### App1 방식 - @IdClass

##### OrderItemId (PK 클래스)

```java
public class OrderItemId implements Serializable {
  private Long orderId;
  private Long productId;
  // equals() / hashCode() 필수
}
```

- 일반 클래스. `@Embeddable` 불필요
- 필드명이 엔티티의 `@Id` 필드명과 반드시 일치해야 함

##### OrderItem (엔티티)

```java
@Entity
@Table(name = "shop_order_item")
@IdClass(OrderItemId.class)
public class OrderItem {

  @Id @Column(name = "order_id")   private Long orderId;
  @Id @Column(name = "product_id") private Long productId;

  private int quantity;
  private BigDecimal price;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@IdClass(OrderItemId.class)` | 복합 PK 클래스 지정. PK 필드가 엔티티에 직접 노출됨 |
| `@Id` (복수) | 복합 PK를 구성하는 각 필드에 개별 선언 |

- JPQL 접근: `oi.orderId` (PK 필드를 일반 필드처럼 직접 사용)
- `find()` 호출: `em.find(OrderItem.class, new OrderItemId(1L, 1L))`

---

#### App2 방식 - @EmbeddedId

##### OrderItemPK (PK 클래스)

```java
@Embeddable
public class OrderItemPK implements Serializable {
  @Column(name = "order_id")   private Long orderId;
  @Column(name = "product_id") private Long productId;
  // equals() / hashCode() 필수
}
```

- `@Embeddable` 필수. 컬럼 매핑을 PK 클래스 내부에서 선언

##### OrderItemV2 (엔티티)

```java
@Entity
@Table(name = "shop_order_item")
public class OrderItemV2 {

  @EmbeddedId
  private OrderItemPK id;

  private int quantity;
  private BigDecimal price;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EmbeddedId` | `@Embeddable` PK 클래스를 단일 필드로 선언. PK가 하나의 객체로 묶임 |

- JPQL 접근: `oi.id.orderId` (PK 객체를 거쳐 접근)
- `find()` 호출: `em.find(OrderItemV2.class, new OrderItemPK(1L, 1L))`

---

### App1 - @IdClass 방식 INSERT/SELECT/UPDATE/DELETE

```java
// 1. 전체 조회 (JPQL: 필드 직접 접근)
em.createQuery(
    "SELECT oi FROM OrderItem oi ORDER BY oi.orderId, oi.productId", OrderItem.class)
    .getResultList();

// 2. 복합 PK로 단건 조회
OrderItemId pk = new OrderItemId(1L, 1L);
OrderItem found = em.find(OrderItem.class, pk);

// 3. INSERT
OrderItem newItem = new OrderItem();
newItem.setOrderId(3L);
newItem.setProductId(1L);
newItem.setQuantity(1);
newItem.setPrice(new BigDecimal("2990000"));
em.persist(newItem);

// 4. JPQL 조건 조회 (@IdClass: PK 필드 직접 접근)
em.createQuery(
    "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", OrderItem.class)
    .setParameter("orderId", 1L).getResultList();

// 5. 수량 변경 (Dirty Checking)
OrderItem managed = em.find(OrderItem.class, new OrderItemId(3L, 1L));
managed.setQuantity(2);
tx.commit(); // UPDATE 자동 실행

// 6. 삭제
em.remove(em.find(OrderItem.class, new OrderItemId(3L, 1L)));
```

---

### App2 - @EmbeddedId 방식 INSERT/SELECT/DELETE

```java
// 1. 전체 조회 (JPQL: PK 객체 경유)
em.createQuery(
    "SELECT oi FROM OrderItemV2 oi ORDER BY oi.id.orderId, oi.id.productId",
    OrderItemV2.class).getResultList();

// 2. 복합 PK로 단건 조회
OrderItemPK pk = new OrderItemPK(1L, 1L);
OrderItemV2 found = em.find(OrderItemV2.class, pk);

// 3. INSERT
OrderItemV2 newItem = new OrderItemV2();
newItem.setId(new OrderItemPK(3L, 5L));
newItem.setQuantity(1);
newItem.setPrice(new BigDecimal("189000"));
em.persist(newItem);

// 4. JPQL 조건 조회 (@EmbeddedId: PK 객체 경유)
em.createQuery(
    "SELECT oi FROM OrderItemV2 oi WHERE oi.id.orderId = :orderId",
    OrderItemV2.class).setParameter("orderId", 1L).getResultList();

// 5. 삭제
em.remove(em.find(OrderItemV2.class, new OrderItemPK(3L, 5L)));
```

---

### 실행 방법

```bash
# @IdClass 방식
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App1

# @EmbeddedId 방식
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App2
```

## exam16 - 엔티티 이벤트 & @EntityListeners

### 개념

#### 엔티티 생명주기 콜백 이벤트

JPA는 엔티티의 INSERT·UPDATE·DELETE·SELECT 시점에 **콜백 메서드를 자동 호출**하는 훅(Hook) 메커니즘을 제공한다.

```
INSERT: @PrePersist  →  INSERT SQL  →  @PostPersist
UPDATE: @PreUpdate   →  UPDATE SQL  →  @PostUpdate
DELETE: @PreRemove   →  DELETE SQL  →  @PostRemove
SELECT:                              →  @PostLoad
```

콜백 메서드는 **엔티티 클래스 내부**에 직접 정의하거나, **별도 리스너 클래스**(`@EntityListeners`)에 정의하는 두 가지 방법이 있다.

#### @EntityListeners

외부 리스너 클래스를 엔티티에 등록하는 어노테이션이다.

```java
@EntityListeners(AuditListener.class)  // 리스너 등록
public class Customer { ... }
```

- 리스너 클래스의 콜백 메서드는 `Object` 타입으로 엔티티를 받는다
- 여러 엔티티에 동일 리스너를 재사용할 수 있어 **공통 Auditing 로직을 한 곳에서 관리** 가능
- 내부 정의 방식(`@PrePersist void onPrePersist()`)보다 관심사 분리가 명확하다

#### Auditing 자동화

`createdAt`·`updatedAt`을 애플리케이션 코드에서 매번 직접 설정하는 대신, 리스너가 이벤트 시점에 자동으로 채워준다.

```
em.persist(entity)  →  @PrePersist 호출  →  createdAt·updatedAt 자동 설정  →  INSERT SQL
tx.commit()         →  @PreUpdate 호출   →  updatedAt 자동 갱신             →  UPDATE SQL
```

> Spring Data JPA의 `@EnableJpaAuditing` / `@CreatedDate` / `@LastModifiedDate`는 이 원리를 추상화한 것이다.

---

### 사용 테이블

```
shop_customer
  created_at  TIMESTAMP  → @PrePersist에서 자동 설정
  updated_at  TIMESTAMP  → @PrePersist / @PreUpdate에서 자동 갱신
```

---

### 이벤트 호출 순서

```
INSERT: @PrePersist  →  INSERT SQL  →  @PostPersist
UPDATE: @PreUpdate   →  UPDATE SQL  →  @PostUpdate
DELETE: @PreRemove   →  DELETE SQL  →  @PostRemove
SELECT:                              →  @PostLoad
```

---

### 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@EntityListeners` | 엔티티 클래스 | 리스너 클래스 등록 |
| `@PrePersist` | 리스너 메서드 | INSERT SQL 실행 전 |
| `@PostPersist` | 리스너 메서드 | INSERT SQL 실행 후 |
| `@PreUpdate` | 리스너 메서드 | UPDATE SQL 실행 전 |
| `@PostUpdate` | 리스너 메서드 | UPDATE SQL 실행 후 |
| `@PreRemove` | 리스너 메서드 | DELETE SQL 실행 전 |
| `@PostRemove` | 리스너 메서드 | DELETE SQL 실행 후 |
| `@PostLoad` | 리스너 메서드 | 엔티티 조회 후 |

---

### 엔티티 클래스 구조

#### Auditable (인터페이스)

```java
public interface Auditable {
  void setCreatedAt(LocalDateTime v);
  void setUpdatedAt(LocalDateTime v);
}
```

- `AuditListener`가 `Object` 타입으로 엔티티를 받으므로 `createdAt`/`updatedAt` 필드에 접근하려면 공통 타입이 필요하다
- 리스너 적용 대상 엔티티는 이 인터페이스를 구현해야 한다

---

#### AuditListener (엔티티 리스너)

```java
public class AuditListener {

  @PrePersist
  public void prePersist(Object entity) {
    if (entity instanceof Auditable auditable) {
      auditable.setCreatedAt(LocalDateTime.now());
      auditable.setUpdatedAt(LocalDateTime.now());
    }
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    if (entity instanceof Auditable auditable) {
      auditable.setUpdatedAt(LocalDateTime.now());
    }
  }
  // @PostPersist, @PostUpdate, @PreRemove, @PostRemove, @PostLoad 도 구현
}
```

| 어노테이션 | 호출 시점 | 활용 |
|---|---|---|
| `@PrePersist` | `em.persist()` 직후, INSERT SQL 전 | `createdAt`, `updatedAt` 자동 설정 |
| `@PreUpdate` | Dirty Checking 감지 후, UPDATE SQL 전 | `updatedAt` 자동 갱신 |
| `@PostPersist` | INSERT SQL 완료 후 | 로그, 이벤트 발행 |
| `@PostUpdate` | UPDATE SQL 완료 후 | 로그, 이벤트 발행 |
| `@PreRemove` | `em.remove()` 직후, DELETE SQL 전 | 삭제 전처리 |
| `@PostRemove` | DELETE SQL 완료 후 | 로그 |
| `@PostLoad` | `em.find()` 또는 JPQL 조회 후 | 조회 후처리 |

---

#### Customer (엔티티)

```java
@Entity
@Table(name = "shop_customer")
@EntityListeners(AuditListener.class)
public class Customer implements Auditable {

  @Column(name = "created_at") private LocalDateTime createdAt;
  @Column(name = "updated_at") private LocalDateTime updatedAt;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EntityListeners(AuditListener.class)` | 엔티티 생명주기 이벤트 발생 시 `AuditListener` 메서드를 자동 호출 |

- `createdAt`/`updatedAt`을 애플리케이션 코드에서 직접 설정하지 않아도 `AuditListener`가 자동으로 관리
- `implements Auditable`: 리스너가 타입 캐스팅으로 `setCreatedAt()`/`setUpdatedAt()` 호출 가능

---

### App - 이벤트 리스너 동작 전체 흐름

INSERT → SELECT → UPDATE → DELETE 순서로 각 생명주기 이벤트가 자동 호출되는 것을 확인한다.

```java
// 1. INSERT: @PrePersist → INSERT SQL → @PostPersist
Customer customer = new Customer();
customer.setName("이지은");
// createdAt / updatedAt 미설정 → @PrePersist에서 자동 설정
em.persist(customer);  // ← @PrePersist 호출
tx.commit();           // ← @PostPersist 호출
customer.getCreatedAt(); // AuditListener가 설정한 값

// 2. SELECT: @PostLoad
Customer found = em.find(Customer.class, savedId); // ← @PostLoad 호출

// 3. UPDATE: @PreUpdate → UPDATE SQL → @PostUpdate
Customer managed = em.find(Customer.class, savedId);
managed.setCity("부산");
// updatedAt 미설정 → commit() 직전 @PreUpdate에서 자동 갱신
tx.commit(); // ← @PreUpdate → UPDATE SQL → @PostUpdate 순서로 실행

// 4. DELETE: @PreRemove → DELETE SQL → @PostRemove
em.remove(toRemove); // ← @PreRemove 호출
tx.commit();         // ← @PostRemove 호출
```

> Spring Data JPA의 `@EnableJpaAuditing` / `@CreatedDate` / `@LastModifiedDate`는 이 원리(`@EntityListeners`)를 추상화한 것이다.

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam16.App
```

## exam17 - JPQL 기초

### 개념

#### JPQL(Java Persistence Query Language)이란

SQL은 테이블·컬럼명을 사용하지만, JPQL은 **엔티티 클래스명·필드명**을 사용하는 객체지향 쿼리 언어다.

```
[SQL]                              [JPQL]
SELECT * FROM shop_customer     →  SELECT c FROM Customer c
WHERE city = '서울'              →  WHERE c.city = '서울'
ORDER BY id                     →  ORDER BY c.id
```

- Hibernate가 JPQL을 DB 방언(Dialect)에 맞는 SQL로 변환한다
- 특정 DB에 종속되지 않아 이식성이 높다

#### 파라미터 바인딩

| 방식 | 문법 | 특징 |
|---|---|---|
| 이름 기반 | `:name` | 순서 무관, 가독성 좋음 → **권장** |
| 위치 기반 | `?1` | 1부터 시작, 순서 의존 |

#### 프로젝션(Projection)

SELECT 절에 무엇을 반환할지 지정하는 것이다.

| 종류 | 예시 | 반환 타입 |
|---|---|---|
| 엔티티 | `SELECT c FROM Customer c` | `List<Customer>` (managed) |
| 단일 컬럼 | `SELECT c.name FROM Customer c` | `List<String>` |
| 다중 컬럼 | `SELECT c.name, c.city FROM Customer c` | `List<Object[]>` |
| 생성자 표현식 | `SELECT new DTO(c.name, c.city) FROM Customer c` | `List<DTO>` |

---

### 사용 테이블

```
shop_customer
  id         NUMBER PK
  name       VARCHAR2
  email      VARCHAR2
  city       VARCHAR2
  created_at TIMESTAMP
  updated_at TIMESTAMP

shop_product
  id         NUMBER PK
  dtype      VARCHAR2  (읽기 전용: PHYSICAL/DIGITAL)
  name       VARCHAR2
  price      NUMBER
  stock      NUMBER
  created_at TIMESTAMP
  updated_at TIMESTAMP
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| 엔티티명 사용 | `Customer` (테이블명 `shop_customer` 아님) |
| 필드명 사용 | `c.city` (컬럼명 `city` 와 동일하지만 Java 필드 기준) |
| 집계 함수 | `COUNT`, `SUM`, `AVG`, `MIN`, `MAX` |
| 페이징 | `setFirstResult(시작위치)` / `setMaxResults(건수)` |
| 타입 안전 | `createQuery(jpql, 클래스)` → `TypedQuery<T>` 반환 |

---

### 엔티티 클래스 구조

#### Customer

```java
@Entity
@Table(name = "shop_customer")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String city;
}
```

#### Product

```java
@Entity
@Table(name = "shop_product")
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // dtype: 기존 DB 컬럼 읽기 전용 매핑
  @Column(name = "dtype", insertable = false, updatable = false)
  private String dtype;

  private String name;
  private BigDecimal price;
  private int stock;
}
```

#### CustomerNameCityDto (생성자 표현식용 DTO)

```java
public class CustomerNameCityDto {
  private final String name;
  private final String city;

  public CustomerNameCityDto(String name, String city) { ... }
}
```

---

### App1 - 기본 JPQL 쿼리 문법

SELECT, WHERE, LIKE, BETWEEN, IN, IS NULL, 집계 함수, GROUP BY, ORDER BY 를 순서대로 확인한다.

```java
// 전체 조회
em.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class).getResultList();

// WHERE + 조건
em.createQuery("SELECT c FROM Customer c WHERE c.city = '서울'", Customer.class);

// BETWEEN: 가격 범위
em.createQuery("SELECT p FROM Product p WHERE p.price BETWEEN :low AND :high ORDER BY p.price",
    Product.class)
    .setParameter("low", new BigDecimal("100000"))
    .setParameter("high", new BigDecimal("2000000"));

// 집계 함수
Object[] stats = (Object[]) em.createQuery(
    "SELECT COUNT(p), AVG(p.price), MIN(p.price), MAX(p.price) FROM Product p")
    .getSingleResult();

// GROUP BY + HAVING
em.createQuery("SELECT c.city, COUNT(c) FROM Customer c WHERE c.city IS NOT NULL"
    + " GROUP BY c.city HAVING COUNT(c) >= 1");
```

---

### App2 - 파라미터 바인딩

이름 기반·위치 기반 파라미터, TypedQuery vs Query, 다중 파라미터, 페이징을 확인한다.

```java
// 이름 기반 (:name) - 권장
em.createQuery("SELECT c FROM Customer c WHERE c.name = :name", Customer.class)
    .setParameter("name", "홍길동");

// 위치 기반 (?1)
em.createQuery("SELECT c FROM Customer c WHERE c.city = ?1", Customer.class)
    .setParameter(1, "서울");

// 페이징
em.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
    .setFirstResult(0)    // 시작 위치 (0-based)
    .setMaxResults(2);    // 최대 건수
```

---

### App3 - 프로젝션

엔티티·단일 컬럼·Object[]·생성자 표현식·집계 프로젝션을 순서대로 확인한다.

```java
// 단일 컬럼
List<String> names = em.createQuery("SELECT c.name FROM Customer c", String.class).getResultList();

// 다중 컬럼 Object[]
List<Object[]> rows = em.createQuery("SELECT c.id, c.name, c.city FROM Customer c")
    .getResultList();

// 생성자 표현식 → DTO
List<CustomerNameCityDto> dtos = em.createQuery(
    "SELECT new com.eomcs.advanced.jpa.exam17.CustomerNameCityDto(c.name, c.city)"
    + " FROM Customer c",
    CustomerNameCityDto.class)
    .getResultList();
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App2
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App3
```

## exam18 - JPQL 심화

### 개념

#### N+1 문제와 JOIN FETCH

지연 로딩(LAZY) 설정 시 연관 엔티티를 실제로 접근할 때 추가 SELECT가 발생한다.

```
[N+1 문제]
SELECT * FROM shop_orders                   ← 1번 (3건 조회)
SELECT * FROM shop_customer WHERE id = 1    ← N번 (각 주문마다)
SELECT * FROM shop_customer WHERE id = 2
...
총 1 + N 번 SELECT 실행

[JOIN FETCH 해결]
SELECT o.*, c.*
  FROM shop_orders o
  JOIN shop_customer c ON c.id = o.customer_id
                                             ← 1번으로 해결
```

#### 서브쿼리

JPQL의 WHERE/HAVING 절에서 서브쿼리를 사용할 수 있다.

| 구문 | 설명 |
|---|---|
| `EXISTS (서브쿼리)` | 서브쿼리에 결과가 하나 이상 있으면 true |
| `NOT EXISTS (서브쿼리)` | 서브쿼리에 결과가 없으면 true |
| `IN (서브쿼리)` | 서브쿼리 결과 집합에 포함되면 true |
| `ALL (서브쿼리)` | 서브쿼리 모든 값과 비교가 true |
| `스칼라 서브쿼리` | WHERE 절에서 단일 값 반환 서브쿼리 사용 |

#### Named Query

엔티티 클래스에 `@NamedQuery`로 JPQL을 미리 정의해 두는 방식이다.

```
[정의]                              [사용]
@NamedQuery(                        em.createNamedQuery(
  name  = "Customer.findAll",    →      "Customer.findAll",
  query = "SELECT c FROM ..."         Customer.class)
)                                       .getResultList();
```

- EntityManagerFactory 기동 시 JPQL 파싱·검증 → 오타를 런타임 전에 발견
- 파싱 결과를 캐시하여 반복 호출 시 `createQuery`보다 빠름

---

### 사용 테이블

```
shop_customer  ← Customer
shop_orders    ← Order     (customer_id FK → shop_customer)
shop_order_item← OrderItem (복합 PK: order_id + product_id)
shop_product   ← Product
```

---

### 연관관계 구조

```
Customer ── Order (ManyToOne, LAZY)
             └── OrderItem (OneToMany, LAZY, mappedBy="order")
                     └── Product (ManyToOne, LAZY)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JOIN FETCH` | 연관 엔티티를 즉시 한 번의 SQL로 로드 |
| `DISTINCT` | 컬렉션 JOIN FETCH 시 중복 제거 필수 |
| `EXISTS` | 서브쿼리 결과 존재 여부 조건 |
| `NOT EXISTS` | 서브쿼리 결과 없음 조건 |
| `@NamedQuery` | 엔티티에 정의, 기동 시 검증·캐시 |

---

### 엔티티 클래스 구조

#### Customer (with @NamedQuery)

```java
@Entity
@Table(name = "shop_customer")
@NamedQuery(name = "Customer.findAll",
    query = "SELECT c FROM Customer c ORDER BY c.id")
@NamedQuery(name = "Customer.findByCity",
    query = "SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id")
public class Customer {
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();
}
```

#### Order

```java
@Entity
@Table(name = "shop_orders")
public class Order {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> orderItems = new ArrayList<>();
}
```

#### OrderItem (@IdClass 복합 키)

```java
@Entity
@Table(name = "shop_order_item")
@IdClass(OrderItemId.class)
public class OrderItem {
  @Id @Column(name = "order_id")   private Long orderId;
  @Id @Column(name = "product_id") private Long productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;
}
```

#### Product (with @NamedQuery)

```java
@Entity
@Table(name = "shop_product")
@NamedQuery(name = "Product.findByPriceRange",
    query = "SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max ORDER BY p.price")
@NamedQuery(name = "Product.findExpensiveThanAvg",
    query = "SELECT p FROM Product p WHERE p.price > (SELECT AVG(p2.price) FROM Product p2) ORDER BY p.price DESC")
public class Product { ... }
```

---

### App1 - JOIN FETCH (N+1 문제 해결)

N+1 문제 발생 확인, JOIN FETCH 해결, 컬렉션 JOIN FETCH, 다중 JOIN FETCH 순서로 확인한다.

```java
// N+1 문제: 주문 조회 후 각 고객 지연 로딩
List<Order> orders = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
for (Order o : orders) {
  o.getCustomer().getName(); // 각 주문마다 추가 SELECT 발생
}

// JOIN FETCH 해결: 한 번에 로드
List<Order> orders = em.createQuery(
    "SELECT o FROM Order o JOIN FETCH o.customer ORDER BY o.id",
    Order.class).getResultList();

// 컬렉션 JOIN FETCH: DISTINCT 로 중복 제거
List<Order> orders = em.createQuery(
    "SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems ORDER BY o.id",
    Order.class).getResultList();
```

---

### App2 - 서브쿼리

EXISTS, NOT EXISTS, IN + 서브쿼리, 스칼라 서브쿼리, ALL 서브쿼리를 확인한다.

```java
// EXISTS: 주문이 있는 고객
em.createQuery("SELECT c FROM Customer c"
    + " WHERE EXISTS (SELECT o FROM Order o WHERE o.customer = c)", Customer.class);

// 스칼라 서브쿼리: 평균 가격 초과 제품
em.createQuery("SELECT p FROM Product p"
    + " WHERE p.price > (SELECT AVG(p2.price) FROM Product p2)"
    + " ORDER BY p.price DESC", Product.class);
```

---

### App3 - Named Query

`@NamedQuery`로 정의된 쿼리를 `createNamedQuery`로 호출하고, 성능 차이를 비교한다.

```java
// 파라미터 없는 Named Query
em.createNamedQuery("Customer.findAll", Customer.class).getResultList();

// 파라미터 있는 Named Query
em.createNamedQuery("Customer.findByCity", Customer.class)
    .setParameter("city", "서울")
    .getResultList();
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App2
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App3
```

## exam19 - Criteria API

### 개념

#### Criteria API란

JPQL을 문자열 대신 **Java 코드(객체)**로 작성하는 타입 안전(Type-Safe) 쿼리 API다.

```
[JPQL 문자열]                         [Criteria API]
"SELECT c FROM Customer c          CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
 WHERE c.city = '서울'"         →   Root<Customer> root = cq.from(Customer.class);
                                   cq.select(root).where(cb.equal(root.get("city"), "서울"));
```

- 오타가 있으면 **컴파일 오류** 또는 기동 시 즉시 발견 (JPQL 문자열은 런타임에야 발견)
- 조건을 동적으로 추가·제거하기 쉬워 **동적 쿼리 구성**에 특히 유리하다

#### 동적 쿼리 비교

```
[JPQL 문자열 방식 - 취약]
String jpql = "SELECT c FROM Customer c WHERE 1=1";
if (city != null) jpql += " AND c.city = '" + city + "'";  // SQL 인젝션 위험
if (name != null) jpql += " AND c.name LIKE '%" + name + "%'";

[Criteria API - 권장]
List<Predicate> predicates = new ArrayList<>();
if (city != null) predicates.add(cb.equal(root.get("city"), city));
if (name != null) predicates.add(cb.like(root.get("name"), "%" + name + "%"));
cq.where(cb.and(predicates.toArray(new Predicate[0])));
```

#### 주요 클래스

| 클래스 | 역할 |
|---|---|
| `CriteriaBuilder` | Predicate·Expression·Order 생성 팩토리 |
| `CriteriaQuery<T>` | SELECT 절 반환 타입을 결정하는 쿼리 객체 |
| `Root<T>` | FROM 절 엔티티 루트. 필드 접근은 `root.get("필드명")` |
| `Predicate` | WHERE 절 조건. `cb.and` / `cb.or`로 조합 |
| `Order` | ORDER BY 절. `cb.asc` / `cb.desc` |

---

### 사용 테이블

```
shop_customer  ← Customer (id, name, email, city)
shop_product   ← Product  (id, dtype, name, price, stock)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `cb.equal(root.get("city"), "서울")` | `c.city = '서울'` |
| `cb.like(root.get("name"), "%홍%")` | `c.name LIKE '%홍%'` |
| `cb.between(root.get("price"), min, max)` | `p.price BETWEEN min AND max` |
| `cb.and(p1, p2)` | `p1 AND p2` |
| `cb.or(p1, p2)` | `p1 OR p2` |
| `cb.isNotNull(root.get("city"))` | `c.city IS NOT NULL` |
| `cb.count(root)` | `COUNT(c)` |

---

### 엔티티 클래스 구조

#### Customer

```java
@Entity
@Table(name = "shop_customer")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String city;
}
```

#### Product

```java
@Entity
@Table(name = "shop_product")
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "dtype", insertable = false, updatable = false) private String dtype;
  private String name;
  private BigDecimal price;
  private int stock;
}
```

---

### App - Criteria API 전체 흐름

기본 쿼리, 단일 WHERE, 동적 쿼리(조건 있음/없음), BETWEEN+OR, COUNT, 복합 ORDER BY, 단일 컬럼 프로젝션을 순서대로 확인한다.

```java
// 1. 기본 쿼리
CriteriaBuilder         cb   = em.getCriteriaBuilder();
CriteriaQuery<Customer> cq   = cb.createQuery(Customer.class);
Root<Customer>          root = cq.from(Customer.class);
cq.select(root).orderBy(cb.asc(root.get("id")));
List<Customer> result = em.createQuery(cq).getResultList();

// 2. 동적 쿼리
List<Predicate> predicates = new ArrayList<>();
if (city != null) predicates.add(cb.equal(root.get("city"), city));
if (name != null) predicates.add(cb.like(root.get("name"), "%" + name + "%"));
if (!predicates.isEmpty()) {
  cq.where(cb.and(predicates.toArray(new Predicate[0])));
}

// 3. COUNT
CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
countCq.select(cb.count(countCq.from(Customer.class)));
Long count = em.createQuery(countCq).getSingleResult();
```

> **QueryDSL**: Criteria API의 장황함을 해소한 오픈소스 라이브러리다. 엔티티를 기반으로 Q클래스를 생성하여 `QCustomer.customer.city.eq("서울")` 처럼 간결하고 타입 안전한 쿼리를 작성할 수 있다.

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam19.App
```

## exam20 - Native Query & @SqlResultSetMapping

### 개념

#### Native Query란

JPQL이 아닌 **DB 고유 SQL**을 직접 실행하는 방법이다.

```java
em.createNativeQuery("SELECT * FROM shop_customer WHERE city = :city", Customer.class)
  .setParameter("city", "서울")
  .getResultList();
```

- DB 고유 함수·힌트·복잡한 SQL을 자유롭게 사용 가능
- DB 종속적이어서 JPQL에 비해 이식성이 낮다
- 컴파일 타임 검증 없음 (오류는 런타임에 발견)

#### @SqlResultSetMapping

네이티브 쿼리 결과를 **엔티티 또는 DTO로 매핑**하는 어노테이션이다.

```
[네이티브 쿼리 결과 컬럼]             [@ConstructorResult 매핑]
id, name, city, order_count   →   new CustomerSummary(id, name, city, orderCount)
```

| 매핑 타입 | 어노테이션 | 설명 |
|---|---|---|
| 엔티티 매핑 | `@EntityResult` | 쿼리 결과를 엔티티 필드로 매핑 |
| DTO 매핑 | `@ConstructorResult` | 쿼리 결과를 DTO 생성자 인수로 매핑 |
| 스칼라 매핑 | `@ColumnResult` | 단일 컬럼 값 매핑 |

#### 재귀 CTE (Common Table Expression)

SQL WITH 절로 자기 자신을 참조하는 재귀 쿼리를 작성할 수 있다.

```sql
WITH category_tree (id, name, parent_id, lvl) AS (
  SELECT id, name, parent_id, 1            -- 앵커(루트)
    FROM shop_category WHERE parent_id IS NULL
  UNION ALL
  SELECT c.id, c.name, c.parent_id, ct.lvl + 1   -- 재귀(자식 반복)
    FROM shop_category c JOIN category_tree ct ON c.parent_id = ct.id
)
SELECT * FROM category_tree ORDER BY lvl, id
```

- JPQL로는 표현 불가 → Native Query 필수
- Oracle 12c R2+ 지원 (표준 SQL)

---

### 사용 테이블

```
shop_customer  ← Customer (@SqlResultSetMapping, @NamedNativeQuery 정의)
shop_orders    ← 주문 수 집계 서브쿼리에 사용
shop_category  ← Category (재귀 CTE 대상)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `createNativeQuery(sql, 엔티티.class)` | 결과를 엔티티로 자동 매핑 |
| `createNativeQuery(sql)` | `Object[]` 배열 반환 |
| `createNativeQuery(sql, "매핑이름")` | `@SqlResultSetMapping` 적용 |
| `@ConstructorResult` | DTO 생성자 인수로 컬럼 매핑 |
| `@NamedNativeQuery` | 재사용 가능한 명명된 네이티브 쿼리 |
| 재귀 CTE | `WITH ... UNION ALL` 계층 트리 조회 |
| `CONNECT BY` | Oracle 전통 계층 쿼리 (Oracle 전용) |

---

### 엔티티 클래스 구조

#### Customer (with @SqlResultSetMapping + @NamedNativeQuery)

```java
@Entity
@Table(name = "shop_customer")
@SqlResultSetMapping(
    name    = "CustomerSummaryMapping",
    classes = @ConstructorResult(
        targetClass = CustomerSummary.class,
        columns = {
            @ColumnResult(name = "id",          type = Long.class),
            @ColumnResult(name = "name",         type = String.class),
            @ColumnResult(name = "city",         type = String.class),
            @ColumnResult(name = "order_count",  type = Long.class)
        }
    )
)
@NamedNativeQuery(
    name            = "Customer.findSummary",
    query           = "SELECT c.id, c.name, c.city, ... FROM shop_customer c ...",
    resultSetMapping = "CustomerSummaryMapping"
)
public class Customer { ... }
```

#### CustomerSummary (DTO)

```java
public class CustomerSummary {
  public CustomerSummary(Long id, String name, String city, Long orderCount) { ... }
}
```

#### Category (재귀 CTE용)

```java
@Entity
@Table(name = "shop_category")
public class Category {
  @Id private Long id;
  private String name;
  @Column(name = "parent_id") private Long parentId;
}
```

---

### App1 - 네이티브 SQL 사용

기본 네이티브 쿼리, 이름/위치 기반 파라미터, Object[] 집계, JOIN, Oracle 전용 함수를 확인한다.

```java
// 엔티티 반환
List<Customer> customers = em.createNativeQuery(
    "SELECT * FROM shop_customer ORDER BY id", Customer.class).getResultList();

// Object[] 반환 (집계)
List<Object[]> rows = em.createNativeQuery(
    "SELECT city, COUNT(*) AS cnt FROM shop_customer GROUP BY city ORDER BY cnt DESC")
    .getResultList();
```

---

### App2 - @SqlResultSetMapping

`CustomerSummaryMapping`으로 DTO 매핑, `@NamedNativeQuery` 호출, Object[] 방식과 비교한다.

```java
// @SqlResultSetMapping 적용
List<CustomerSummary> summaries = em.createNativeQuery(
    "SELECT c.id, c.name, c.city, (SELECT COUNT(*) ...) AS order_count ...",
    "CustomerSummaryMapping")  // ← 매핑 이름
    .getResultList();

// @NamedNativeQuery 호출
em.createNamedQuery("Customer.findSummary", CustomerSummary.class).getResultList();
```

---

### App3 - 재귀 CTE & CONNECT BY

재귀 CTE로 전체 카테고리 트리 조회, 특정 루트 하위 트리 조회, 조상 체인 역방향 조회, Oracle CONNECT BY를 확인한다.

```java
// 재귀 CTE (표준 SQL)
em.createNativeQuery("""
    WITH category_tree (id, name, parent_id, lvl, path) AS (
      SELECT id, name, parent_id, 1, CAST(name AS VARCHAR2(4000))
        FROM shop_category WHERE parent_id IS NULL
      UNION ALL
      SELECT c.id, c.name, c.parent_id, ct.lvl + 1, ct.path || ' > ' || c.name
        FROM shop_category c JOIN category_tree ct ON c.parent_id = ct.id
    )
    SELECT id, name, lvl, path FROM category_tree ORDER BY path
    """)

// Oracle CONNECT BY (Oracle 전용)
em.createNativeQuery(
    "SELECT id, name, LEVEL AS lvl, SYS_CONNECT_BY_PATH(name, ' > ') AS path"
    + " FROM shop_category"
    + " START WITH parent_id IS NULL"
    + " CONNECT BY PRIOR id = parent_id")
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App2
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App3
```

## exam21 - Spring Data JPA 기초

### 개념

#### Spring Data JPA란

JPA(EntityManager)를 직접 다루는 반복적인 코드를 제거하고, **인터페이스 선언만으로 리포지토리를 자동 구현**하는 Spring 하위 프로젝트다.

```
개발자가 선언:  interface CustomerRepository extends JpaRepository<Customer, Long>
Spring이 생성:  런타임에 프록시 구현체 자동 생성 → save / findById / findAll 등 제공
```

#### JpaRepository 상속 계층

```
Repository
 └── CrudRepository<T, ID>   : save / findById / findAll / delete / count / existsById
      └── PagingAndSortingRepository<T, ID> : findAll(Sort) / findAll(Pageable)
           └── JpaRepository<T, ID>          : flush / saveAllAndFlush / deleteInBatch 등 JPA 전용 추가
```

#### Spring Data JPA 설정 (Spring Boot 없이)

| 필수 빈 | 클래스 | 역할 |
|---|---|---|
| `DataSource` | `HikariDataSource` | DB 연결 풀 |
| `EntityManagerFactory` | `LocalContainerEntityManagerFactoryBean` | JPA 영속성 단위 |
| `TransactionManager` | `JpaTransactionManager` | 트랜잭션 관리 |

`@EnableJpaRepositories(basePackages = "...")` : 지정 패키지에서 Repository 인터페이스를 탐색해 구현체 생성  
`@EnableTransactionManagement` : `@Transactional` AOP 활성화

#### 파생 쿼리(Derived Query Method)

메서드 이름을 파싱해 자동으로 JPQL을 생성한다.

| 메서드 이름 | 생성되는 JPQL |
|---|---|
| `findByCity(city)` | `WHERE c.city = :city` |
| `findByCityOrderByNameAsc(city)` | `WHERE c.city = :city ORDER BY c.name ASC` |
| `findByEmail(email)` | `WHERE c.email = :email` |
| `findByNameContaining(kw)` | `WHERE c.name LIKE '%:kw%'` |
| `countByCity(city)` | `SELECT COUNT(c) WHERE c.city = :city` |
| `existsByEmail(email)` | `SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END WHERE c.email = :email` |

#### save() 동작 원리

```
save(entity) {
  if (entity.id == null) → em.persist(entity)   // INSERT
  else                   → em.merge(entity)      // UPDATE
}
```

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JpaRepository<T, ID>` | CRUD + 페이징 + 정렬 기본 제공 |
| 파생 쿼리 | 메서드 이름 → JPQL 자동 생성 (SQL 작성 불필요) |
| `Optional<T>` 반환 | `findById` 등 단건 조회는 Optional 반환 → NPE 방지 |
| `Sort.by()` | `findAll(Sort)` 로 동적 정렬 지정 |
| Spring 컨텍스트 | `AnnotationConfigApplicationContext` → Boot 없이 사용 가능 |

---

### App - 기본 CRUD

`save()` / `findById()` / `findAll()` / `deleteById()` / `count()` / `existsById()` 동작을 확인한다.

```java
CustomerRepository repo = ctx.getBean(CustomerRepository.class);

// INSERT
Customer saved = repo.save(customer);

// SELECT WHERE id = ?
Optional<Customer> found = repo.findById(saved.getId());

// SELECT *
List<Customer> all = repo.findAll();

// UPDATE: 조회 후 필드 변경 → save() 재호출
Customer toUpdate = repo.findById(id).orElseThrow();
toUpdate.setCity("부산");
repo.save(toUpdate);

// DELETE WHERE id = ?
repo.deleteById(id);
```

---

### App2 - 파생 쿼리 & Sort

파생 쿼리 메서드와 `Sort` 객체로 동적 정렬을 확인한다.

```java
// WHERE city = '서울'
repo.findByCity("서울");

// WHERE city = '서울' ORDER BY name ASC
repo.findByCityOrderByNameAsc("서울");

// WHERE name LIKE '%길%'
repo.findByNameContaining("길");

// SELECT COUNT(*) WHERE city = '서울'
repo.countByCity("서울");

// ORDER BY name ASC
repo.findAll(Sort.by("name").ascending());

// ORDER BY city DESC, name ASC
repo.findAll(Sort.by("city").descending().and(Sort.by("name")));
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App2
```

## exam22 - @Query & Paging

### 개념

#### @Query

메서드 이름 대신 JPQL(또는 Native SQL)을 직접 작성한다.

```java
@Query("SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.name")
List<Customer> findByCityJpql(@Param("city") String city);
```

| 속성 | 설명 | 기본값 |
|---|---|---|
| `value` | JPQL 문자열 | 필수 |
| `nativeQuery` | `true`이면 SQL, `false`이면 JPQL | `false` |
| `countQuery` | `Page<T>` 반환 시 사용할 COUNT 쿼리 분리 | 자동 생성 |

#### Pageable / Page / Slice

```
PageRequest.of(page, size)         → 0-based 페이지 번호, 페이지 크기
PageRequest.of(page, size, Sort)   → 정렬 포함

Page<T>  : 전체 건수(totalElements), 전체 페이지 수(totalPages) 포함
           → COUNT 쿼리가 추가 실행됨
Slice<T> : hasNext() 여부만 제공
           → COUNT 쿼리 없음 (무한 스크롤에 적합)
```

| 메서드 | 설명 |
|---|---|
| `getContent()` | 현재 페이지 데이터 목록 |
| `getNumber()` | 현재 페이지 번호 (0-based) |
| `getSize()` | 페이지 크기 |
| `getTotalElements()` | 전체 데이터 건수 (Page만) |
| `getTotalPages()` | 전체 페이지 수 (Page만) |
| `hasNext()` | 다음 페이지 존재 여부 |
| `nextPageable()` | 다음 페이지용 Pageable |

#### @Modifying

`UPDATE` · `DELETE` JPQL을 실행할 때 필수다.

```java
@Modifying(clearAutomatically = true)
@Transactional
@Query("UPDATE Customer c SET c.city = :newCity WHERE c.city = :oldCity")
int updateCity(@Param("oldCity") String oldCity, @Param("newCity") String newCity);
```

- `clearAutomatically = true`: 실행 후 1차 캐시를 자동 초기화 → stale 데이터 방지
- 반환값 `int`: 영향받은 행 수
- `@Transactional` 필수: 벌크 연산에는 트랜잭션이 필요하다

#### countQuery 분리

기본 COUNT 쿼리는 `value`의 SELECT절을 `COUNT(*)`로 변환한다.  
JOIN이 복잡하면 COUNT 쿼리가 비효율적일 수 있어 별도로 작성한다.

```java
@Query(
    value      = "SELECT c FROM Customer c WHERE c.city = :city",
    countQuery = "SELECT COUNT(c) FROM Customer c WHERE c.city = :city")
Page<Customer> searchByCity(@Param("city") String city, Pageable pageable);
```

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Query` | 복잡한 조건·집계 쿼리를 JPQL로 직접 작성 |
| `@Param` | JPQL의 `:파라미터명`에 바인딩 |
| `Page<T>` | COUNT 포함 → 전체 페이지 정보 필요한 경우 |
| `Slice<T>` | COUNT 없음 → 무한 스크롤, 다음 페이지 여부만 필요 |
| `@Modifying` | UPDATE / DELETE 쿼리에 필수 |
| `clearAutomatically` | 벌크 연산 후 1차 캐시 초기화 |

---

### App - Page / Slice / Sort

`@Query` JPQL 직접 실행, `Page<T>` 페이징, `Slice<T>` 무한 스크롤, `Sort` 동적 정렬을 확인한다.

```java
// @Query JPQL
repo.findByCityJpql("서울");

// Page (0번째 페이지, 크기 2)
Page<Customer> page = repo.findByCity("서울", PageRequest.of(0, 2));
page.getTotalElements();   // 전체 건수
page.getTotalPages();      // 전체 페이지 수

// Slice (COUNT 없음)
Slice<Customer> slice = repo.findByNameContaining("홍", PageRequest.of(0, 2));
slice.hasNext();           // 다음 페이지 여부만

// 정렬 포함
repo.searchByCity("서울", PageRequest.of(0, 5, Sort.by("name").ascending()));
```

---

### App2 - @Modifying (UPDATE / DELETE)

JPQL 벌크 UPDATE / DELETE 실행 결과를 확인한다.

```java
// UPDATE
int updated = repo.updateCity("대전", "광주");   // 변경된 행 수 반환

// DELETE
int deleted = repo.deleteByEmailPattern("bulk_%");
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App2
```

## exam23 - Querydsl 기초

### 개념

#### Querydsl이란

JPQL·SQL을 Java 코드로 작성하는 타입 안전(type-safe) 쿼리 라이브러리다.

| 비교 | JPQL | Querydsl |
|---|---|---|
| 작성 방식 | 문자열 | Java 메서드 체인 |
| 오타 발견 | 런타임 | 컴파일 타임 |
| 동적 쿼리 | 문자열 연결 (복잡) | BooleanBuilder (간결) |
| IDE 지원 | 없음 | 자동완성·리팩토링 지원 |

#### Q-타입 (Q-Type)

APT(Annotation Processing Tool)가 `@Entity` 클래스를 읽어 자동 생성하는 메타 클래스다.

```java
// APT가 Customer.java → QCustomer.java 자동 생성
public class QCustomer extends EntityPathBase<Customer> {
    public static final QCustomer customer = new QCustomer("customer");
    public final StringPath name  = createString("name");
    public final StringPath city  = createString("city");
    public final NumberPath<Long> id = createNumber("id", Long.class);
    ...
}
```

##### APT 설정 (Gradle)

```groovy
annotationProcessor "com.querydsl:querydsl-apt:${queryDslVersion}:jakarta"
annotationProcessor "jakarta.persistence:jakarta.persistence-api:3.1.0"
// → ./gradlew compileJava 실행 시 Q-타입 자동 생성
```

> 이 예제의 `QCustomer` · `QProduct`는 학습용 수동 작성본이다.

#### JPAQueryFactory

Querydsl 쿼리의 진입점이다.

```java
JPAQueryFactory factory = new JPAQueryFactory(entityManager);

// SELECT c FROM Customer c WHERE c.city = '서울' ORDER BY c.name ASC
factory.selectFrom(QCustomer.customer)
       .where(QCustomer.customer.city.eq("서울"))
       .orderBy(QCustomer.customer.name.asc())
       .fetch();
```

#### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `eq(v)` | = 조건 |
| `ne(v)` | != 조건 |
| `contains(v)` | LIKE '%v%' |
| `startsWith(v)` | LIKE 'v%' |
| `gt(v)` / `lt(v)` | > / < 조건 |
| `goe(v)` / `loe(v)` | >= / <= 조건 |
| `between(a, b)` | BETWEEN a AND b |
| `and(pred)` / `or(pred)` | 복합 조건 |
| `fetch()` | List\<T\> 반환 |
| `fetchOne()` | 단건 반환 (없으면 null, 둘 이상이면 예외) |
| `fetchFirst()` | 첫 번째 결과만 반환 |

#### BooleanBuilder - 동적 쿼리

```java
BooleanBuilder builder = new BooleanBuilder();

if (city != null)    builder.and(c.city.eq(city));
if (keyword != null) builder.and(c.name.contains(keyword));

factory.selectFrom(c).where(builder).fetch();
```

- `null` 파라미터는 그냥 추가하지 않으면 되므로 `if` 분기가 단순해진다
- Criteria API 대비 훨씬 간결한 동적 쿼리 구성

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_product   ← Product 엔티티
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| Q-타입 | APT 자동 생성 메타 클래스, 타입 안전 필드 참조 |
| `JPAQueryFactory` | Querydsl 쿼리 진입점 |
| `selectFrom(Q)` | FROM 절 지정 |
| `where(조건)` | WHERE 절 - 타입 안전 조건 |
| `BooleanBuilder` | 조건 동적 누적 → 동적 쿼리 핵심 |
| `fetch()` | 결과 목록 반환 |

---

### App - JPAQueryFactory 기본 쿼리

`selectFrom`, `where(eq/contains/gt)`, `orderBy`, `limit`, `fetchOne`을 확인한다.

```java
JPAQueryFactory factory = new JPAQueryFactory(em);
QCustomer c = QCustomer.customer;

// 기본 조회
factory.selectFrom(c).fetch();

// 조건
factory.selectFrom(c).where(c.city.eq("서울")).fetch();

// 복합 조건
factory.selectFrom(p)
       .where(p.dtype.eq("DIGITAL").and(p.price.lt(new BigDecimal("100000"))))
       .fetch();
```

---

### App2 - BooleanBuilder 동적 쿼리

파라미터 유무에 따라 조건을 동적으로 조합하는 검색 메서드를 확인한다.

```java
BooleanBuilder builder = new BooleanBuilder();
if (city    != null) builder.and(c.city.eq(city));
if (keyword != null) builder.and(c.name.contains(keyword));
factory.selectFrom(c).where(builder).fetch();
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App2
```

## exam24 - Specification 패턴

### 개념

#### Specification이란

WHERE 절 조건 하나를 객체로 표현하는 함수형 인터페이스다.

```java
@FunctionalInterface
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
```

#### JpaSpecificationExecutor

`JpaRepository`에 추가 상속하면 아래 메서드가 자동 제공된다.

```java
interface CustomerRepository
    extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

| 메서드 | 설명 |
|---|---|
| `findAll(Specification<T>)` | 조건에 맞는 전체 목록 |
| `findAll(Specification<T>, Pageable)` | 조건 + 페이징 |
| `findAll(Specification<T>, Sort)` | 조건 + 정렬 |
| `findOne(Specification<T>)` | 단건 조회 → `Optional<T>` |
| `count(Specification<T>)` | 조건에 맞는 건수 |

#### Specification 조합

```java
spec1.and(spec2)               // AND
spec1.or(spec2)                // OR
Specification.not(spec)        // NOT
Specification.where(null)      // 조건 없음 (전체 조회)
```

#### 조건 팩토리 패턴

```java
public class CustomerSpecs {

    public static Specification<Customer> hasCity(String city) {
        return (root, query, cb) -> {
            if (city == null) return null;   // null → 조건 무시
            return cb.equal(root.get("city"), city);
        };
    }

    public static Specification<Customer> nameContains(String keyword) {
        return (root, query, cb) ->
            keyword == null ? null : cb.like(root.get("name"), "%" + keyword + "%");
    }
}
```

- 각 조건을 독립 메서드로 분리 → 재사용 가능
- `null` 반환 시 해당 조건이 무시됨 → 동적 쿼리 핵심

#### Querydsl vs Specification

| 비교 | Specification | Querydsl BooleanBuilder |
|---|---|---|
| 타입 안전 | 아님 (문자열로 필드 참조) | 타입 안전 (Q-타입) |
| 설정 복잡도 | 없음 (Spring Data 내장) | APT 설정 필요 |
| 복잡한 쿼리 | 제한적 | 자유로움 |
| 적합한 상황 | 간단한 동적 검색 | 복잡한 동적 쿼리 |

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JpaSpecificationExecutor<T>` | `findAll(Spec)` / `count(Spec)` 제공 |
| `Specification<T>` | WHERE 조건 하나를 표현하는 함수형 인터페이스 |
| `.and()` / `.or()` | 조건 조합 |
| `null` 반환 | 조건 무시 → 동적 쿼리 구현 핵심 |
| `Specification.where(null)` | 전체 조회 (조건 없음) |

---

### App - Specification 조합 데모

단일 조건, and/or 조합, not 부정, count를 확인한다.

```java
// 단일 조건
repo.findAll(CustomerSpecs.hasCity("서울"));

// AND 조합
repo.findAll(
    CustomerSpecs.hasCity("서울")
    .and(CustomerSpecs.nameContains("홍")));

// OR 조합
repo.findAll(
    CustomerSpecs.hasCity("서울")
    .or(CustomerSpecs.hasCity("부산")));

// count
repo.count(CustomerSpecs.hasCity("서울"));
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam24.App
```

## exam25 - Auditing & @EnableJpaAuditing

### 개념

#### JPA Auditing이란

엔티티 생성/수정 시 **타임스탬프와 사용자 정보를 자동으로 채워주는** 기능이다.  
개발자가 `setCreatedAt(LocalDateTime.now())` 같은 반복 코드를 작성할 필요가 없다.

#### 활성화 방법

```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig { ... }
```

#### 어노테이션

| 어노테이션 | 적용 대상 | 자동 설정 시점 |
|---|---|---|
| `@CreatedDate` | `LocalDateTime` 필드 | `persist` 시 현재 시각 |
| `@LastModifiedDate` | `LocalDateTime` 필드 | `persist` / `merge` 시 현재 시각 |
| `@CreatedBy` | `String` 등 필드 | `persist` 시 `AuditorAware.getCurrentAuditor()` 반환값 |
| `@LastModifiedBy` | `String` 등 필드 | `persist` / `merge` 시 `AuditorAware.getCurrentAuditor()` 반환값 |

#### BaseEntity (@MappedSuperclass)

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)  // INSERT 시만 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
```

- `@MappedSuperclass`: 테이블 미생성, 자식 엔티티에 컬럼 매핑 상속
- `@EntityListeners(AuditingEntityListener.class)`: JPA 이벤트를 Auditing 리스너에 연결
- `updatable = false`: `@CreatedDate` / `@CreatedBy`는 최초 삽입 후 변경 불가

#### AuditorAware

`@CreatedBy` / `@LastModifiedBy`에 채울 "현재 사용자"를 반환한다.

```java
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 웹 환경: SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of("system-user");
    }
}
```

#### 동작 흐름

```
save(entity)
  → @PrePersist 이벤트 → AuditingEntityListener
    → @CreatedDate      = LocalDateTime.now()
    → @CreatedBy        = AuditorAware.getCurrentAuditor()
    → @LastModifiedDate = LocalDateTime.now()
    → @LastModifiedBy   = AuditorAware.getCurrentAuditor()

save(entity) [UPDATE]
  → @PreUpdate 이벤트 → AuditingEntityListener
    → @LastModifiedDate = LocalDateTime.now()   (갱신)
    → @LastModifiedBy   = AuditorAware.getCurrentAuditor() (갱신)
    → @CreatedDate      = 변경 없음 (updatable=false)
    → @CreatedBy        = 변경 없음 (updatable=false)
```

---

### 사용 테이블

```
exam25_customer  ← Customer 엔티티 (hbm2ddl.auto=create-drop 으로 자동 생성/삭제)
```

> `shop_customer` 테이블에는 `created_by` / `updated_by` 컬럼이 없어  
> exam25에서는 별도 테이블(`exam25_customer`)을 자동 생성해 사용한다.

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@EnableJpaAuditing` | Auditing 기능 활성화, `auditorAwareRef`로 AuditorAware 빈 지정 |
| `@MappedSuperclass` | DB 테이블 없이 자식 엔티티에 매핑 정보 상속 |
| `@EntityListeners` | JPA 이벤트를 Auditing 리스너에 연결 |
| `@CreatedDate` | INSERT 시 현재 시각 자동 설정 |
| `@LastModifiedDate` | INSERT/UPDATE 시 현재 시각 자동 갱신 |
| `@CreatedBy` | INSERT 시 사용자 자동 설정 (`updatable=false`) |
| `@LastModifiedBy` | INSERT/UPDATE 시 사용자 자동 갱신 |
| `AuditorAware<T>` | 현재 사용자 반환 인터페이스 (Spring Security 연동 가능) |

---

### App - Auditing 자동 설정 데모

INSERT 시 4개 필드 자동 설정, UPDATE 시 `@LastModifiedDate`/`@LastModifiedBy` 자동 갱신을 확인한다.

```java
// INSERT - createdAt, updatedAt, createdBy, updatedBy 자동 설정
Customer c = new Customer();
c.setName("감사테스터");
c.setEmail("audit@test.com");
Customer saved = repo.save(c);  // setCreatedAt() 없이도 자동 채워짐

System.out.println(saved.getCreatedAt());   // 자동 설정됨
System.out.println(saved.getCreatedBy());   // "system-user" (AuditorAware 반환값)

// UPDATE - updatedAt, updatedBy만 갱신
Customer toUpdate = repo.findById(id).orElseThrow();
toUpdate.setCity("부산");
Customer updated = repo.save(toUpdate);

// createdAt == 이전 그대로, updatedAt == 갱신됨
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam25.App
```

## exam26 - N+1 문제

### 개념

#### N+1 문제란

1번의 쿼리로 N개의 엔티티를 가져온 뒤, 각 엔티티의 연관 데이터를 로드하기 위해 **N번의 추가 쿼리가 실행**되는 현상이다.

```
findAll()                              → SELECT * FROM shop_customer  (1번)
c.getOrders() × 3명                   → SELECT * FROM shop_orders WHERE customer_id = ?  (3번)
─────────────────────────────────────
합계: 1 + 3 = 4번  (고객 100명이면 101번)
```

#### 발생 원인

`@OneToMany(fetch = LAZY)` 상태에서 루프로 연관 데이터에 접근할 때 발생한다.

```java
List<Customer> customers = repo.findAll();   // 쿼리 1번
for (Customer c : customers) {
    c.getOrders().size();  // 여기서 고객마다 쿼리 1번씩 추가 실행 (N번)
}
```

#### 해결법 1: JOIN FETCH

JPQL에서 연관 엔티티를 한 번에 로드한다.

```java
@Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllWithOrders();
```

- `JOIN FETCH`: INNER JOIN으로 Customer + orders를 단 1번에 로드
- `DISTINCT`: 조인으로 인한 Customer 중복 제거 (필수)
- 주의: 주문이 없는 고객은 제외됨 → 포함하려면 `LEFT JOIN FETCH` 사용

#### 해결법 2: @EntityGraph

어노테이션으로 즉시 로딩할 연관 경로를 선언한다.

```java
@EntityGraph(attributePaths = {"orders"})
@Query("SELECT c FROM Customer c")
List<Customer> findAllWithOrdersGraph();
```

- `attributePaths`: 즉시 로딩할 연관 필드 경로 목록
- 내부적으로 `LEFT OUTER JOIN FETCH` 생성 → 주문 없는 고객도 포함
- JPQL을 직접 수정하지 않아도 됨 → 재사용성↑

#### JOIN FETCH vs @EntityGraph 비교

| 비교 | JOIN FETCH | @EntityGraph |
|---|---|---|
| 선언 위치 | JPQL 문자열 내부 | 메서드 어노테이션 |
| 조인 방식 | INNER JOIN (기본) | LEFT OUTER JOIN |
| 주문 없는 고객 | 제외됨 | 포함됨 |
| 재사용성 | JPQL 복사 필요 | 어노테이션 재사용 가능 |
| 복잡한 조건 | 자유롭게 작성 가능 | 단순 경로만 지정 |

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_orders    ← Order 엔티티 (customer_id FK)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| N+1 문제 | LAZY 연관관계를 루프에서 접근할 때 발생 |
| 발생 원인 | `findAll()` 후 각 엔티티마다 연관 데이터 개별 조회 |
| JOIN FETCH | JPQL에서 연관 엔티티를 명시적으로 한 번에 로드 |
| DISTINCT | 조인 시 발생하는 부모 엔티티 중복 제거 |
| @EntityGraph | 선언적 즉시 로딩, LEFT OUTER JOIN 자동 생성 |

---

### App - N+1 문제 재현

`findAll()` 후 루프에서 `getOrders()`를 호출해 N+1 쿼리 발생을 콘솔에서 직접 확인한다.

```java
List<Customer> customers = repo.findAll();      // 쿼리 1번
for (Customer c : customers) {
    c.getOrders().size();  // 고객마다 쿼리 추가 발생 → 총 1 + N번
}
```

---

### App2 - N+1 문제 해결

JOIN FETCH와 @EntityGraph 두 가지 해결법을 각각 실행하여 쿼리 횟수를 비교한다.

```java
// 해결법 1: JOIN FETCH - 쿼리 1번
repo.findAllWithOrders();

// 해결법 2: @EntityGraph - 쿼리 1번
repo.findAllWithOrdersGraph();
```

---

### 실행 방법

```bash
# N+1 문제 재현
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App

# N+1 문제 해결 확인
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App2
```

## exam27 - 지연 로딩(Lazy) vs 즉시 로딩(Eager)

### 개념

#### FetchType.LAZY (지연 로딩)

연관 데이터를 실제로 접근하는 순간까지 로드를 미룬다.

```java
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)  // @OneToMany 기본값
private List<Order> orders;

@ManyToOne(fetch = FetchType.LAZY)  // 기본값은 EAGER이지만 성능상 LAZY 권장
private Customer customer;
```

- 프록시 객체가 자리를 차지하다가 최초 접근 시 SELECT 실행
- **트랜잭션 안에서만 정상 동작**
- 트랜잭션 밖에서 접근 → `LazyInitializationException`

#### FetchType.EAGER (즉시 로딩)

부모 엔티티 로드 시 연관 데이터를 항상 JOIN하여 함께 가져온다.

```java
@ManyToOne(fetch = FetchType.EAGER)  // @ManyToOne, @OneToOne 기본값
private Customer customer;
```

- 조회할 때마다 연관 데이터를 항상 로드 → 사용하지 않아도 쿼리 실행
- **`@OneToMany` EAGER는 N+1 문제를 심화시킬 수 있어 비권장**

#### 기본값 정리

| 어노테이션 | 기본 FetchType | 권장 FetchType |
|---|---|---|
| `@OneToMany` | `LAZY` | `LAZY` (기본값 유지) |
| `@ManyToMany` | `LAZY` | `LAZY` (기본값 유지) |
| `@ManyToOne` | `EAGER` | **`LAZY`** (변경 권장) |
| `@OneToOne` | `EAGER` | **`LAZY`** (변경 권장) |

#### LazyInitializationException

LAZY 로딩 프록시가 Hibernate 세션(트랜잭션) 밖에서 초기화되려 할 때 발생한다.

```java
// 트랜잭션 없는 메서드에서 반환된 엔티티
Customer detached = svc.findByIdDetached(1L);

detached.getOrders().size();  // 세션 닫힘 → LazyInitializationException!
```

**해결법:**
1. 트랜잭션 안에서 미리 초기화: `c.getOrders().size()`
2. JOIN FETCH / `@EntityGraph`로 즉시 로딩 (exam26 참고)
3. DTO로 필요한 데이터만 조회

#### OSIV (Open Session In View)

HTTP 요청 시작부터 종료(View 렌더링 완료)까지 Hibernate 세션을 열어 두는 패턴이다.

```
HTTP 요청 시작
  │ 세션 오픈 (OSIV)
  ├─ Controller
  ├─ Service  (@Transactional 범위)
  ├─ Repository
  ├─ View 렌더링  ← LAZY 로딩 허용 (세션 열려 있음)
  │ 세션 종료
HTTP 응답 완료
```

| 구분 | OSIV ON | OSIV OFF |
|---|---|---|
| LAZY 접근 가능 범위 | 요청 전체 | `@Transactional` 범위만 |
| 커넥션 점유 | 요청 전체 동안 | 트랜잭션 동안만 |
| 성능 | 커넥션 오래 점유 | 커넥션 빨리 반환 |
| Spring Boot 기본값 | `true` (ON) | - |

> 비웹 환경(이 예제): 세션 범위 = `@Transactional` 범위

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_orders    ← Order 엔티티 (customer_id FK)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `LAZY` | 실제 접근 시 SELECT, 트랜잭션 안에서만 동작 |
| `EAGER` | 항상 JOIN 로드, `@ManyToOne` 기본값 |
| `LazyInitializationException` | 세션 밖에서 LAZY 필드 접근 시 발생 |
| OSIV | 세션을 요청 전체 동안 유지하는 패턴 |
| 권장 | 모든 연관관계를 `LAZY`로 설정, 필요 시 JOIN FETCH |

---

### App - LAZY 로딩 동작 및 LazyInitializationException 데모

트랜잭션 안/밖에서의 LAZY 로딩 동작 차이를 확인한다.

```java
// 1. 트랜잭션 안 → 정상 동작
svc.printOrdersInsideTransaction();

// 2. 트랜잭션 안에서 미리 초기화 → 트랜잭션 밖에서도 사용 가능
List<Customer> list = svc.findAllInitialized();

// 3. 트랜잭션 밖 → LazyInitializationException
Customer c = svc.findByIdDetached(1L);
c.getOrders().size();  // 예외 발생!
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam27.App
```

## exam28 - 2차 캐시

### 개념

#### 1차 캐시 vs 2차 캐시

| 구분 | 1차 캐시 (L1C) | 2차 캐시 (L2C) |
|---|---|---|
| 범위 | `EntityManager` 단위 | `EntityManagerFactory`(SessionFactory) 단위 |
| 공유 | 트랜잭션 내부만 | 애플리케이션 전체 |
| 소멸 | 트랜잭션 종료 시 | 애플리케이션 종료 시 (또는 TTL 만료) |
| 설정 | 자동 제공 | 명시적 설정 필요 |

#### 2차 캐시 동작 흐름

```
findById(1L) 첫 번째
  → L2C 미스 → DB SELECT → 결과를 L2C에 PUT

findById(1L) 두 번째 (다른 트랜잭션/EntityManager)
  → L2C 히트 → DB 쿼리 없이 반환  ← 성능 향상
```

#### 설정

**JpaConfig.java:**

```java
props.setProperty("hibernate.cache.use_second_level_cache", "true");
props.setProperty("hibernate.cache.use_query_cache",        "true");
props.setProperty("hibernate.cache.region.factory_class",   "jcache");
props.setProperty("hibernate.javax.cache.provider",
    "org.ehcache.jsr107.EhcacheCachingProvider");
props.setProperty("hibernate.javax.cache.uri", "ehcache.xml");
props.setProperty("hibernate.generate_statistics", "true");
```

**build.gradle (의존성 추가):**

```groovy
implementation "org.hibernate.orm:hibernate-jcache:${libs.versions.hibernate.get()}"
implementation("org.ehcache:ehcache:${libs.versions.ehcache.get()}") { artifact { classifier = "jakarta" } }
```

#### @Cache 어노테이션

```java
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer { ... }
```

`@Cache`가 없으면 L2C에 저장되지 않는다.

#### CacheConcurrencyStrategy 전략

| 전략 | 설명 | 적합한 데이터 |
|---|---|---|
| `READ_ONLY` | 읽기만, 변경 불가 | 코드 테이블, 마스터 데이터 |
| `READ_WRITE` | 읽기/쓰기, 낙관적 잠금 | 일반 엔티티 (권장) |
| `NONSTRICT_READ_WRITE` | 짧은 불일치 허용, 성능↑ | 갱신 드문 데이터 |
| `TRANSACTIONAL` | JTA 트랜잭션 기반, 완전 일관성 | 정확성 최우선 |

#### 쿼리 캐시

```java
@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Query("SELECT c FROM Customer c WHERE c.city = :city")
List<Customer> findByCityWithCache(String city);
```

- 동일 파라미터로 재호출 시 DB 쿼리 없이 캐시 결과 반환
- `hibernate.cache.use_query_cache=true` 설정 필수
- 연관 테이블이 변경되면 쿼리 캐시 자동 무효화

#### ehcache.xml 캐시 리전

```xml
<!-- 엔티티 캐시: 패키지.클래스명 -->
<cache alias="com.eomcs.advanced.jpa.exam28.Customer">
  <expiry><ttl unit="minutes">10</ttl></expiry>
  <resources><heap unit="entries">1000</heap></resources>
</cache>

<!-- 쿼리 캐시 결과 -->
<cache alias="org.hibernate.cache.spi.QueryResultsRegion"> ... </cache>

<!-- 쿼리 캐시 타임스탬프 (무효화 추적용) -->
<cache alias="org.hibernate.cache.spi.TimestampsRegion"> ... </cache>
```

#### Hibernate Statistics

```java
SessionFactory sf    = emf.unwrap(SessionFactory.class);
Statistics     stats = sf.getStatistics();

stats.getSecondLevelCacheHitCount()  // L2C 히트 횟수
stats.getSecondLevelCacheMissCount() // L2C 미스 횟수
stats.getSecondLevelCachePutCount()  // L2C 저장 횟수
stats.getQueryExecutionCount()       // 실행된 쿼리 횟수
```

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티 (@Cache 적용)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Cache` | 엔티티를 L2C 대상으로 표시 (없으면 캐시 안 됨) |
| `READ_WRITE` | 일반 엔티티에 권장하는 캐시 전략 |
| `use_second_level_cache` | 엔티티 캐시 활성화 설정 |
| `use_query_cache` | 쿼리 결과 캐시 활성화 설정 |
| `@QueryHints(cacheable)` | 특정 쿼리를 캐시 대상으로 지정 |
| `Statistics` | 캐시 히트/미스 횟수로 캐시 효과 측정 |

---

### App - 2차 캐시 히트/미스 데모

`findById()` 반복 호출 시 L2C 히트/미스 횟수와 DB 쿼리 실행 여부를 통계로 확인한다.

```java
// 1차 조회: L2C 미스 → DB SELECT → L2C PUT
repo.findById(1L);
// → L2C 히트: 0, 미스: 1, PUT: 1

// 2차 조회: L2C 히트 → DB 쿼리 없음
repo.findById(1L);
// → L2C 히트: 1, 미스: 1, PUT: 1

// 쿼리 캐시
repo.findByCityWithCache("서울");  // DB 실행, 결과 캐시
repo.findByCityWithCache("서울");  // 캐시 히트, DB 미실행
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam28.App
```

## exam29 - 배치 처리 & Bulk 연산

### 개념

#### Bulk 연산 (@Modifying JPQL)

1차 캐시를 거치지 않고 DB에 직접 실행하는 대량 UPDATE / DELETE다.

```java
@Modifying(clearAutomatically = true)
@Transactional
@Query("UPDATE Customer c SET c.city = :newCity WHERE c.city = :oldCity")
int bulkUpdateCity(@Param("oldCity") String oldCity, @Param("newCity") String newCity);
```

| 속성 | 설명 |
|---|---|
| `@Modifying` | UPDATE / DELETE JPQL 실행에 필수 |
| `clearAutomatically = true` | 실행 후 1차 캐시 자동 초기화 → stale 데이터 방지 |
| 반환값 `int` | 영향받은 행 수 |
| `@Transactional` | 벌크 연산에 트랜잭션 필수 |

**왜 `clearAutomatically`가 필요한가?**

```
bulkUpdateCity("서울", "수원") → DB에서 서울 → 수원 변경
repo.findById(1L)              → 1차 캐시에서 반환 (city = "서울" 여전히!)
                                  ↑ stale 데이터! clearAutomatically=true로 방지
```

#### JDBC 배치 처리

`hibernate.jdbc.batch_size`를 설정하면 `em.persist()` 반복 호출 시 JDBC 레벨에서 묶어서 전송한다.

```java
// JpaConfig 설정
props.setProperty("hibernate.jdbc.batch_size", "50");
props.setProperty("hibernate.order_inserts",   "true");
props.setProperty("hibernate.order_updates",   "true");
```

```java
// 대량 INSERT 패턴
for (int i = 1; i <= 1000; i++) {
    em.persist(new Customer(...));
    if (i % 50 == 0) {
        em.flush();   // 배치 전송
        em.clear();   // 1차 캐시 초기화 (메모리 관리)
    }
}
```

- `flush()`: 1차 캐시 → DB 동기화 (JDBC executeBatch 실행)
- `clear()`: 1차 캐시 비움 → 대량 처리 시 OutOfMemoryError 방지
- `order_inserts/updates`: 같은 테이블 SQL을 묶어서 배치 효율 향상

#### StatelessSession

1차 캐시(영속성 컨텍스트) 없이 DB에 직접 접근하는 경량 세션이다.

```java
SessionFactory sf = emf.unwrap(SessionFactory.class);
try (StatelessSession ss = sf.openStatelessSession()) {
    Transaction tx = ss.beginTransaction();
    ss.insert(entity);   // 즉시 INSERT, 캐시 없음
    tx.commit();
}
```

| 구분 | EntityManager | StatelessSession |
|---|---|---|
| 1차 캐시 | 있음 | **없음** |
| Dirty Checking | 자동 | 없음 (명시적 `update()` 필요) |
| 엔티티 이벤트 | 발생 (`@PrePersist` 등) | 발생 안 함 |
| 2차 캐시 | 사용 | 사용 안 함 |
| 적합한 상황 | 일반 CRUD | **대량 배치, ETL, 마이그레이션** |

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Modifying` | UPDATE / DELETE JPQL에 필수 |
| `clearAutomatically` | Bulk 연산 후 1차 캐시 초기화, stale 데이터 방지 |
| `batch_size` | JDBC 배치 묶음 크기, 네트워크 왕복 횟수 감소 |
| `flush + clear` | 배치 INSERT 중 주기적으로 호출, 메모리 관리 |
| `StatelessSession` | 1차 캐시 없는 경량 세션, 대량 처리에 최적 |

---

### App - Bulk UPDATE / DELETE & JDBC 배치 INSERT

```java
// Bulk UPDATE (1번 쿼리로 여러 행 변경)
int updated = repo.bulkUpdateCity("서울", "수원");

// JDBC 배치 INSERT (batch_size=50으로 묶어 전송)
for (int i = 1; i <= 100; i++) {
    em.persist(new Customer(...));
    if (i % 50 == 0) { em.flush(); em.clear(); }
}

// Bulk DELETE
int deleted = repo.bulkDeleteByEmailPattern("batch_%");
```

---

### App2 - StatelessSession 배치 처리

```java
try (StatelessSession ss = sf.openStatelessSession()) {
    Transaction tx = ss.beginTransaction();
    for (int i = 1; i <= 50; i++) {
        ss.insert(new Customer(...));  // 즉시 INSERT, 캐시 없음
    }
    tx.commit();
}
```

---

### 실행 방법

```bash
# Bulk 연산 & JDBC 배치
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App

# StatelessSession 배치
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App2
```

## exam30 - 읽기 전용 트랜잭션 & 성능 측정

### 개념

#### @Transactional(readOnly = true)

조회 전용 메서드에 `readOnly = true`를 선언하면 Hibernate가 여러 최적화를 적용한다.

```java
@Transactional(readOnly = true)
public List<Customer> findAll() {
    return repo.findAll();
}
```

#### readOnly=true의 최적화 효과

| 최적화 항목 | 설명 |
|---|---|
| Dirty Checking 스냅샷 없음 | 엔티티 로드 시 변경 감지용 복사본을 만들지 않음 → 메모리 절약 |
| flush() 자동 실행 안 함 | 트랜잭션 종료 시 DB에 쓰기 시도 없음 |
| JDBC readOnly 힌트 | 일부 드라이버/DB가 읽기 최적화 커넥션 사용 |
| 읽기 DB 라우팅 | Spring `AbstractRoutingDataSource`와 연계 시 읽기 DB로 자동 전환 가능 |

#### readOnly=true에서 수정 시도

```java
@Transactional(readOnly = true)
public Customer findAndTryModify(Long id) {
    Customer c = repo.findById(id).orElseThrow();
    c.setCity("변경시도");  // 메모리에서만 변경
    // flush() 실행 안 함 → DB에 반영되지 않음
    return c;
}
```

> `readOnly=true`는 DB 쓰기를 막는 것이 아니라 **Hibernate가 flush하지 않는** 것이다.  
> 직접 `em.flush()`를 호출하거나 `@Modifying` JPQL을 실행하면 예외가 발생할 수 있다.

#### 일반 트랜잭션: Dirty Checking

```java
@Transactional
public Customer findAndModify(Long id, String newCity) {
    Customer c = repo.findById(id).orElseThrow();
    // 로드 시 스냅샷 생성 (c의 복사본 보관)
    c.setCity(newCity);
    // 트랜잭션 종료 → flush() → 스냅샷과 비교 → city 변경 감지 → UPDATE 실행
    return c;
}
```

#### Hibernate Statistics

```java
SessionFactory sf    = emf.unwrap(SessionFactory.class);
Statistics     stats = sf.getStatistics();
stats.clear();  // 측정 시작 전 초기화

// ... 코드 실행 ...

stats.getQueryExecutionCount()   // 실행된 SQL 쿼리 수
stats.getEntityLoadCount()       // 로드된 엔티티 수
stats.getEntityUpdateCount()     // UPDATE된 엔티티 수
stats.getFlushCount()            // flush() 실행 횟수
stats.getConnectCount()          // DB 커넥션 획득 횟수
```

`hibernate.generate_statistics=true` 설정이 있어야 수집된다.

#### readOnly vs 일반 트랜잭션 비교

| 지표 | `readOnly=true` | 일반 `@Transactional` |
|---|---|---|
| Dirty Check 스냅샷 | 없음 | 있음 |
| `getFlushCount()` | 0 | ≥ 1 |
| `getEntityUpdateCount()` | 0 | 변경 시 > 0 |
| 메모리 사용 | 낮음 | 높음 (스냅샷 = 엔티티 복사본) |
| 적합한 상황 | 조회 전용 | 생성/수정/삭제 |

#### 권장 패턴

```java
@Service
@Transactional(readOnly = true)   // 클래스 기본값: 조회 전용
public class CustomerService {

    public List<Customer> findAll() { ... }  // readOnly 상속

    @Transactional  // 오버라이드: 쓰기 허용
    public Customer update(Long id, String city) { ... }
}
```

---

### 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_orders    ← Order 엔티티 (customer_id FK)
```

---

### 핵심 포인트

| 구분 | 설명 |
|---|---|
| `readOnly = true` | Dirty Check 없음, flush 없음 → 조회 성능 최적화 |
| Dirty Checking | 스냅샷과 현재 상태 비교로 변경 감지 → flush 시 UPDATE |
| `generate_statistics` | 쿼리/flush/로드 횟수 등 성능 지표 수집 |
| `Statistics.clear()` | 측정 구간 시작 전 초기화 |
| 권장 패턴 | 클래스에 `readOnly=true`, 쓰기 메서드에 `@Transactional` 오버라이드 |

---

### App - readOnly vs 일반 트랜잭션 통계 비교

Statistics로 flush 횟수와 엔티티 수정 횟수를 측정하여 두 방식의 차이를 수치로 확인한다.

```java
// 1. readOnly=true
svc.findAll();
stats.getFlushCount();        // → 0
stats.getEntityUpdateCount(); // → 0

// 2. readOnly=true에서 수정 시도 → DB 반영 안 됨
svc.findAndTryModify(1L);
stats.getFlushCount();        // → 0 (flush 없음)

// 3. 일반 @Transactional: Dirty Checking → 자동 UPDATE
svc.findAndModify(1L, "임시도시");
stats.getFlushCount();        // → 1 (flush 실행)
stats.getEntityUpdateCount(); // → 1 (UPDATE 감지)
```

---

### 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam30.App
```
