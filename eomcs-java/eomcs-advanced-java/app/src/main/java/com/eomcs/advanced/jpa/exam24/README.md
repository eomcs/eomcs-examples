# exam24 - Specification 패턴

## 개념

### Specification이란

WHERE 절 조건 하나를 객체로 표현하는 함수형 인터페이스다.

```java
@FunctionalInterface
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
```

### JpaSpecificationExecutor

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

### Specification 조합

```java
spec1.and(spec2)               // AND
spec1.or(spec2)                // OR
Specification.not(spec)        // NOT
Specification.where(null)      // 조건 없음 (전체 조회)
```

### 조건 팩토리 패턴

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

### Querydsl vs Specification

| 비교 | Specification | Querydsl BooleanBuilder |
|---|---|---|
| 타입 안전 | 아님 (문자열로 필드 참조) | 타입 안전 (Q-타입) |
| 설정 복잡도 | 없음 (Spring Data 내장) | APT 설정 필요 |
| 복잡한 쿼리 | 제한적 | 자유로움 |
| 적합한 상황 | 간단한 동적 검색 | 복잡한 동적 쿼리 |

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JpaSpecificationExecutor<T>` | `findAll(Spec)` / `count(Spec)` 제공 |
| `Specification<T>` | WHERE 조건 하나를 표현하는 함수형 인터페이스 |
| `.and()` / `.or()` | 조건 조합 |
| `null` 반환 | 조건 무시 → 동적 쿼리 구현 핵심 |
| `Specification.where(null)` | 전체 조회 (조건 없음) |

---

## App - Specification 조합 데모

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

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam24.App
```
