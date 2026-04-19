# Exam08 - 영속성 컨텍스트(Persistence Context)

## 개념

### 영속성 컨텍스트란?

EntityManager 내부에 존재하는 **엔티티 관리 저장소**다.
영속(managed) 상태의 엔티티를 `Map<@Id, Entity>` 형태로 관리한다.

```
EntityManager
  └── 영속성 컨텍스트
        ├── 1차 캐시 (Map<Id, Entity>)
        ├── 스냅샷 (초기 상태 복사본)
        └── 쓰기 지연 SQL 저장소
```

### 1차 캐시(First-Level Cache)

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

### 동일성(Identity) 보장

같은 EM 안에서 같은 id로 조회하면 항상 **동일한 인스턴스(==)**를 반환한다.

```java
Customer c1 = em.find(Customer.class, 1L);
Customer c2 = em.find(Customer.class, 1L);

System.out.println(c1 == c2);   // true
```

JDBC 수동 매핑은 `find()`마다 `new Customer()`를 생성하므로 `==`가 `false`다.

### 변경 감지(Dirty Checking)

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

### 쓰기 지연(Write-Behind / Transactional Write-Behind)

`persist()`, `remove()` 같은 변경 작업은 즉시 SQL을 실행하지 않고,
**쓰기 지연 SQL 저장소**에 모았다가 `flush()` 시 한꺼번에 실행한다.

```
persist(c1) → SQL 저장소에 저장
persist(c2) → SQL 저장소에 저장
persist(c3) → SQL 저장소에 저장
      ↓
commit() → flush() → INSERT × 3 일괄 실행 → DB 반영
```

### flush()

영속성 컨텍스트의 변경 내용을 DB에 반영하는 작업이다.

| 항목 | flush() | commit() |
|---|---|---|
| SQL 실행 | Yes | Yes (flush 후 커밋) |
| 트랜잭션 종료 | No | Yes |
| 호출 | 수동(`em.flush()`) 또는 자동 | `tx.commit()` |

**FlushModeType:**
- `AUTO` (기본값): JPQL 쿼리 실행 직전 & `commit()` 직전 자동 flush
- `COMMIT`: `commit()` 직전에만 flush

### IDENTITY 전략과 쓰기 지연

`IDENTITY` 전략(`GENERATED ALWAYS AS IDENTITY`)은 **쓰기 지연이 적용되지 않는다.**
`persist()` 시점에 즉시 INSERT가 실행되어 DB가 id를 채번한다.
(이유: Hibernate가 id를 즉시 알아야 1차 캐시에 저장할 수 있기 때문)

---

## App - 1차 캐시 & 동일성 보장

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

## App2 - 변경 감지(Dirty Checking)

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

## App3 - 쓰기 지연 & flush()

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
