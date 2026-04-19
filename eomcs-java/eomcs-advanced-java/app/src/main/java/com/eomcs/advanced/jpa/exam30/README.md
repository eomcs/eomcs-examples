# exam30 - 읽기 전용 트랜잭션 & 성능 측정

## 개념

### @Transactional(readOnly = true)

조회 전용 메서드에 `readOnly = true`를 선언하면 Hibernate가 여러 최적화를 적용한다.

```java
@Transactional(readOnly = true)
public List<Customer> findAll() {
    return repo.findAll();
}
```

### readOnly=true의 최적화 효과

| 최적화 항목 | 설명 |
|---|---|
| Dirty Checking 스냅샷 없음 | 엔티티 로드 시 변경 감지용 복사본을 만들지 않음 → 메모리 절약 |
| flush() 자동 실행 안 함 | 트랜잭션 종료 시 DB에 쓰기 시도 없음 |
| JDBC readOnly 힌트 | 일부 드라이버/DB가 읽기 최적화 커넥션 사용 |
| 읽기 DB 라우팅 | Spring `AbstractRoutingDataSource`와 연계 시 읽기 DB로 자동 전환 가능 |

### readOnly=true에서 수정 시도

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

### 일반 트랜잭션: Dirty Checking

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

### Hibernate Statistics

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

### readOnly vs 일반 트랜잭션 비교

| 지표 | `readOnly=true` | 일반 `@Transactional` |
|---|---|---|
| Dirty Check 스냅샷 | 없음 | 있음 |
| `getFlushCount()` | 0 | ≥ 1 |
| `getEntityUpdateCount()` | 0 | 변경 시 > 0 |
| 메모리 사용 | 낮음 | 높음 (스냅샷 = 엔티티 복사본) |
| 적합한 상황 | 조회 전용 | 생성/수정/삭제 |

### 권장 패턴

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

## 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_orders    ← Order 엔티티 (customer_id FK)
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `readOnly = true` | Dirty Check 없음, flush 없음 → 조회 성능 최적화 |
| Dirty Checking | 스냅샷과 현재 상태 비교로 변경 감지 → flush 시 UPDATE |
| `generate_statistics` | 쿼리/flush/로드 횟수 등 성능 지표 수집 |
| `Statistics.clear()` | 측정 구간 시작 전 초기화 |
| 권장 패턴 | 클래스에 `readOnly=true`, 쓰기 메서드에 `@Transactional` 오버라이드 |

---

## App - readOnly vs 일반 트랜잭션 통계 비교

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

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam30.App
```
