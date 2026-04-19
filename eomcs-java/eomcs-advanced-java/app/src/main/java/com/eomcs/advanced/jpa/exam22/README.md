# exam22 - @Query & Paging

## 개념

### @Query

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

### Pageable / Page / Slice

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

### @Modifying

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

### countQuery 분리

기본 COUNT 쿼리는 `value`의 SELECT절을 `COUNT(*)`로 변환한다.  
JOIN이 복잡하면 COUNT 쿼리가 비효율적일 수 있어 별도로 작성한다.

```java
@Query(
    value      = "SELECT c FROM Customer c WHERE c.city = :city",
    countQuery = "SELECT COUNT(c) FROM Customer c WHERE c.city = :city")
Page<Customer> searchByCity(@Param("city") String city, Pageable pageable);
```

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@Query` | 복잡한 조건·집계 쿼리를 JPQL로 직접 작성 |
| `@Param` | JPQL의 `:파라미터명`에 바인딩 |
| `Page<T>` | COUNT 포함 → 전체 페이지 정보 필요한 경우 |
| `Slice<T>` | COUNT 없음 → 무한 스크롤, 다음 페이지 여부만 필요 |
| `@Modifying` | UPDATE / DELETE 쿼리에 필수 |
| `clearAutomatically` | 벌크 연산 후 1차 캐시 초기화 |

---

## App - Page / Slice / Sort

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

## App2 - @Modifying (UPDATE / DELETE)

JPQL 벌크 UPDATE / DELETE 실행 결과를 확인한다.

```java
// UPDATE
int updated = repo.updateCity("대전", "광주");   // 변경된 행 수 반환

// DELETE
int deleted = repo.deleteByEmailPattern("bulk_%");
```

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam22.App2
```
