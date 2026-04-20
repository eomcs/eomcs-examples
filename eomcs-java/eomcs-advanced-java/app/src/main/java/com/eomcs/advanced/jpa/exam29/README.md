# exam29 - 배치 처리 & Bulk 연산

## 개념

### Bulk 연산 (@Modifying JPQL)

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

### JDBC 배치 처리

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

### StatelessSession

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

## 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Modifying` | UPDATE / DELETE JPQL에 필수 |
| `clearAutomatically` | Bulk 연산 후 1차 캐시 초기화, stale 데이터 방지 |
| `batch_size` | JDBC 배치 묶음 크기, 네트워크 왕복 횟수 감소 |
| `flush + clear` | 배치 INSERT 중 주기적으로 호출, 메모리 관리 |
| `StatelessSession` | 1차 캐시 없는 경량 세션, 대량 처리에 최적 |

---

## App - Bulk UPDATE / DELETE & JDBC 배치 INSERT

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

- Bulk UPDATE는 JPQL 1번으로 조건에 맞는 모든 행을 변경한다. 개별 엔티티를 조회한 뒤 루프로 변경하는 방식보다 훨씬 빠르다. `bulkUpdateCity("서울", "수원")` 실행 후 `bulkUpdateCity("수원", "서울")`로 원복하는 패턴으로 테스트 데이터를 관리한다.
- JDBC 배치 INSERT에서 `em.persist()`를 반복 호출하면 `hibernate.jdbc.batch_size` 설정값만큼 SQL을 모아 한 번에 DB로 전송한다. 100건을 batch_size=50으로 처리하면 2번의 `executeBatch()`만 실행된다.
- `em.flush()`는 1차 캐시의 변경 사항을 DB에 동기화하고, `em.clear()`는 1차 캐시를 비운다. 대량 처리 시 `clear()`를 주기적으로 호출하지 않으면 1차 캐시가 계속 커져 `OutOfMemoryError`가 발생할 수 있다.
- `Bulk DELETE`는 이메일 패턴(`LIKE 'batch_%'`)으로 조건을 지정해 테스트 데이터를 일괄 삭제한다. 배치 INSERT로 삽입한 100건이 1번의 DELETE 쿼리로 제거된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App
  ```

---

## App2 - StatelessSession 배치 처리

```java
try (StatelessSession ss = sf.openStatelessSession()) {
    Transaction tx = ss.beginTransaction();
    for (int i = 1; i <= 50; i++) {
        ss.insert(new Customer(...));  // 즉시 INSERT, 캐시 없음
    }
    tx.commit();
}
```

- `StatelessSession`은 영속성 컨텍스트(1차 캐시)가 없어 `ss.insert()` 호출 즉시 DB에 INSERT가 실행된다. `flush()`나 `clear()` 없이 대량 처리가 가능하다.
- `EntityManager` 방식과 달리 Dirty Checking이 없으므로, 엔티티 필드를 변경해도 자동 UPDATE가 발생하지 않는다. 수정이 필요하면 `ss.update(entity)`를 명시적으로 호출해야 한다.
- `@PrePersist` 같은 엔티티 라이프사이클 이벤트도 발생하지 않는다. Auditing(`@CreatedDate` 등)이 동작하지 않으므로, Auditing이 필요한 경우에는 `StatelessSession`을 사용하면 안 된다.
- `StatelessSession`에서도 JPQL `createMutationQuery().executeUpdate()`로 벌크 UPDATE·DELETE를 실행할 수 있다. 1차 캐시가 없으므로 `clearAutomatically`는 고려할 필요가 없다.
- 대용량 데이터 마이그레이션, ETL 작업, 배치 잡처럼 Auditing이나 이벤트 없이 순수하게 빠른 INSERT·UPDATE가 필요한 상황에 적합하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam29.App2
  ```
