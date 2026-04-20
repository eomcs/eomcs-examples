# exam28 - 2차 캐시

## 개념

### 1차 캐시 vs 2차 캐시

| 구분 | 1차 캐시 (L1C) | 2차 캐시 (L2C) |
|---|---|---|
| 범위 | `EntityManager` 단위 | `EntityManagerFactory`(SessionFactory) 단위 |
| 공유 | 트랜잭션 내부만 | 애플리케이션 전체 |
| 소멸 | 트랜잭션 종료 시 | 애플리케이션 종료 시 (또는 TTL 만료) |
| 설정 | 자동 제공 | 명시적 설정 필요 |

### 2차 캐시 동작 흐름

```
findById(1L) 첫 번째
  → L2C 미스 → DB SELECT → 결과를 L2C에 PUT

findById(1L) 두 번째 (다른 트랜잭션/EntityManager)
  → L2C 히트 → DB 쿼리 없이 반환  ← 성능 향상
```

### 설정

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

### @Cache 어노테이션

```java
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer { ... }
```

`@Cache`가 없으면 L2C에 저장되지 않는다.

### CacheConcurrencyStrategy 전략

| 전략 | 설명 | 적합한 데이터 |
|---|---|---|
| `READ_ONLY` | 읽기만, 변경 불가 | 코드 테이블, 마스터 데이터 |
| `READ_WRITE` | 읽기/쓰기, 낙관적 잠금 | 일반 엔티티 (권장) |
| `NONSTRICT_READ_WRITE` | 짧은 불일치 허용, 성능↑ | 갱신 드문 데이터 |
| `TRANSACTIONAL` | JTA 트랜잭션 기반, 완전 일관성 | 정확성 최우선 |

### 쿼리 캐시

```java
@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Query("SELECT c FROM Customer c WHERE c.city = :city")
List<Customer> findByCityWithCache(String city);
```

- 동일 파라미터로 재호출 시 DB 쿼리 없이 캐시 결과 반환
- `hibernate.cache.use_query_cache=true` 설정 필수
- 연관 테이블이 변경되면 쿼리 캐시 자동 무효화

### ehcache.xml 캐시 리전

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

### Hibernate Statistics

```java
SessionFactory sf    = emf.unwrap(SessionFactory.class);
Statistics     stats = sf.getStatistics();

stats.getSecondLevelCacheHitCount()  // L2C 히트 횟수
stats.getSecondLevelCacheMissCount() // L2C 미스 횟수
stats.getSecondLevelCachePutCount()  // L2C 저장 횟수
stats.getQueryExecutionCount()       // 실행된 쿼리 횟수
```

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티 (@Cache 적용)
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Cache` | 엔티티를 L2C 대상으로 표시 (없으면 캐시 안 됨) |
| `READ_WRITE` | 일반 엔티티에 권장하는 캐시 전략 |
| `use_second_level_cache` | 엔티티 캐시 활성화 설정 |
| `use_query_cache` | 쿼리 결과 캐시 활성화 설정 |
| `@QueryHints(cacheable)` | 특정 쿼리를 캐시 대상으로 지정 |
| `Statistics` | 캐시 히트/미스 횟수로 캐시 효과 측정 |

---

## App - 2차 캐시 히트/미스 데모

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

- 첫 번째 `findById(1L)` 호출에서 L2C 미스가 발생하고 DB SELECT가 실행된 뒤 결과가 L2C에 저장(PUT)된다. 두 번째 호출에서는 L2C 히트가 발생해 DB 쿼리 없이 반환된다.
- 서로 다른 트랜잭션에서도 동일한 id를 조회하면 L2C에서 데이터를 꺼내 반환한다. 1차 캐시는 트랜잭션 단위이지만, 2차 캐시는 `SessionFactory` 단위로 애플리케이션 전체에 공유된다.
- `Hibernate Statistics`는 `sf.getStatistics()`로 얻으며, `getSecondLevelCacheHitCount()`와 `getSecondLevelCacheMissCount()`로 캐시 효과를 수치로 측정할 수 있다. `stats.clear()`로 초기화하면 구간별 통계를 비교할 수 있다.
- 쿼리 캐시는 동일한 파라미터로 같은 메서드를 재호출할 때 DB 쿼리를 실행하지 않는다. `@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))`로 특정 쿼리를 캐시 대상으로 지정한다.
- 자주 변경되는 데이터에 캐시를 적용하면 무효화가 빈번하게 발생해 오히려 성능이 저하될 수 있다. 읽기 빈도가 높고 변경이 드문 데이터에 적합하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam28.App
  ```
