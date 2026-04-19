# exam26 - N+1 문제

## 개념

### N+1 문제란

1번의 쿼리로 N개의 엔티티를 가져온 뒤, 각 엔티티의 연관 데이터를 로드하기 위해 **N번의 추가 쿼리가 실행**되는 현상이다.

```
findAll()                              → SELECT * FROM shop_customer  (1번)
c.getOrders() × 3명                   → SELECT * FROM shop_orders WHERE customer_id = ?  (3번)
─────────────────────────────────────
합계: 1 + 3 = 4번  (고객 100명이면 101번)
```

### 발생 원인

`@OneToMany(fetch = LAZY)` 상태에서 루프로 연관 데이터에 접근할 때 발생한다.

```java
List<Customer> customers = repo.findAll();   // 쿼리 1번
for (Customer c : customers) {
    c.getOrders().size();  // 여기서 고객마다 쿼리 1번씩 추가 실행 (N번)
}
```

### 해결법 1: JOIN FETCH

JPQL에서 연관 엔티티를 한 번에 로드한다.

```java
@Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllWithOrders();
```

- `JOIN FETCH`: INNER JOIN으로 Customer + orders를 단 1번에 로드
- `DISTINCT`: 조인으로 인한 Customer 중복 제거 (필수)
- 주의: 주문이 없는 고객은 제외됨 → 포함하려면 `LEFT JOIN FETCH` 사용

### 해결법 2: @EntityGraph

어노테이션으로 즉시 로딩할 연관 경로를 선언한다.

```java
@EntityGraph(attributePaths = {"orders"})
@Query("SELECT c FROM Customer c")
List<Customer> findAllWithOrdersGraph();
```

- `attributePaths`: 즉시 로딩할 연관 필드 경로 목록
- 내부적으로 `LEFT OUTER JOIN FETCH` 생성 → 주문 없는 고객도 포함
- JPQL을 직접 수정하지 않아도 됨 → 재사용성↑

### JOIN FETCH vs @EntityGraph 비교

| 비교 | JOIN FETCH | @EntityGraph |
|---|---|---|
| 선언 위치 | JPQL 문자열 내부 | 메서드 어노테이션 |
| 조인 방식 | INNER JOIN (기본) | LEFT OUTER JOIN |
| 주문 없는 고객 | 제외됨 | 포함됨 |
| 재사용성 | JPQL 복사 필요 | 어노테이션 재사용 가능 |
| 복잡한 조건 | 자유롭게 작성 가능 | 단순 경로만 지정 |

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
| N+1 문제 | LAZY 연관관계를 루프에서 접근할 때 발생 |
| 발생 원인 | `findAll()` 후 각 엔티티마다 연관 데이터 개별 조회 |
| JOIN FETCH | JPQL에서 연관 엔티티를 명시적으로 한 번에 로드 |
| DISTINCT | 조인 시 발생하는 부모 엔티티 중복 제거 |
| @EntityGraph | 선언적 즉시 로딩, LEFT OUTER JOIN 자동 생성 |

---

## App - N+1 문제 재현

`findAll()` 후 루프에서 `getOrders()`를 호출해 N+1 쿼리 발생을 콘솔에서 직접 확인한다.

```java
List<Customer> customers = repo.findAll();      // 쿼리 1번
for (Customer c : customers) {
    c.getOrders().size();  // 고객마다 쿼리 추가 발생 → 총 1 + N번
}
```

---

## App2 - N+1 문제 해결

JOIN FETCH와 @EntityGraph 두 가지 해결법을 각각 실행하여 쿼리 횟수를 비교한다.

```java
// 해결법 1: JOIN FETCH - 쿼리 1번
repo.findAllWithOrders();

// 해결법 2: @EntityGraph - 쿼리 1번
repo.findAllWithOrdersGraph();
```

---

## 실행 방법

```bash
# N+1 문제 재현
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App

# N+1 문제 해결 확인
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App2
```
