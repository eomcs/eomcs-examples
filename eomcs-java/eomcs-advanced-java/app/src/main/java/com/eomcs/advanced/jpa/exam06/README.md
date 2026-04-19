# Exam06 - JPA 소개 & EntityManagerFactory / EntityManager

## 개념

### JPA(Jakarta Persistence API)란?

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

### persistence.xml

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

### EntityManagerFactory (EMF)

| 항목 | 설명 |
|---|---|
| 생성 비용 | 크다 (DB 연결풀·메타데이터 초기화) |
| 인스턴스 수 | 앱 당 1개 |
| Thread-safe | Yes |
| 닫기 | 앱 종료 시 `emf.close()` |

### EntityManager (EM)

| 항목 | 설명 |
|---|---|
| 생성 비용 | 작다 |
| 인스턴스 수 | 요청·트랜잭션 당 1개 |
| Thread-safe | **No** (스레드 간 공유 금지) |
| 닫기 | 요청 처리 후 반드시 `em.close()` |
| 내부 상태 | 영속성 컨텍스트(1차 캐시) 유지 |

### EntityTransaction

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

### JPQL(Jakarta Persistence Query Language)

- SQL처럼 보이지만 **엔티티 클래스명과 필드명**을 사용한다.
- Hibernate가 이를 DB 방언에 맞는 SQL로 변환한다.

| 구분 | JPQL | SQL |
|---|---|---|
| 대상 | 엔티티 클래스/필드 | 테이블/컬럼 |
| 예시 | `SELECT c FROM Customer c` | `SELECT * FROM shop_customer` |

---

## App - EntityManagerFactory & EntityManager 기초

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

## App2 - EntityTransaction으로 CRUD 처리

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
