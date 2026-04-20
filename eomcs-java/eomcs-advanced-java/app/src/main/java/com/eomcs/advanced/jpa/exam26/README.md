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

- `findAll()`은 고객 목록을 1번의 쿼리로 가져오지만, orders 컬렉션은 `LAZY`로 선언되어 있어 이 시점에는 로드되지 않는다.
- 루프에서 `c.getOrders()`를 최초로 접근할 때마다 `SELECT * FROM shop_orders WHERE customer_id = ?`가 실행된다. 고객이 N명이면 쿼리가 N번 추가된다.
- 콘솔에 출력된 Hibernate SQL 로그를 통해 쿼리가 고객 수만큼 반복 실행되는 것을 직접 확인할 수 있다.
- 고객이 100명이면 101번, 1000명이면 1001번 쿼리가 발생해 성능이 데이터 건수에 비례하여 선형으로 저하된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App
  ```

### 왜 TransactionTemplate을 사용하는가

`main()` 메서드에서 `TransactionTemplate`으로 전체 작업을 감싸지 않으면 `LazyInitializationException`이 발생한다.

**원인:**

Spring Data JPA의 repository 메서드는 각자 독립된 트랜잭션 안에서 실행된다. `findAll()`이 리턴되는 순간 트랜잭션이 종료되고 Hibernate Session이 닫힌다. 그 상태에서 루프가 `c.getOrders()`에 접근하면 Session이 없어 LAZY 로딩을 수행할 수 없다.

```
// 오류 상황 (TransactionTemplate 없이 실행)
List<Customer> customers = repo.findAll();  // ← 여기서 Session 종료
for (Customer c : customers) {
    c.getOrders().size();  // ← Session 없음 → LazyInitializationException
}
```

**해결:**

`TransactionTemplate.executeWithoutResult()`로 `findAll()`과 루프를 하나의 트랜잭션(= 하나의 Session) 안에 묶는다. Session이 열려 있는 동안 LAZY 로딩이 정상 동작하고, N+1 쿼리가 의도대로 발생한다.

```java
TransactionTemplate txTemplate = new TransactionTemplate(txManager);
txTemplate.executeWithoutResult(status -> {
    List<Customer> customers = repo.findAll();  // Session 열려있음
    for (Customer c : customers) {
        c.getOrders().size();  // LAZY 로딩 정상 동작 → N번 추가 쿼리 발생
    }
});  // ← 여기서 트랜잭션 종료, Session 닫힘
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

- `JOIN FETCH`는 JPQL에 명시적으로 작성하며, `Customer`와 `orders`를 단 1번의 INNER JOIN 쿼리로 한꺼번에 로드한다. `DISTINCT`를 함께 사용해 조인으로 인한 고객 중복을 제거한다.
- `JOIN FETCH`는 INNER JOIN이므로 주문이 없는 고객이 결과에서 제외된다. 주문 없는 고객도 포함하려면 `LEFT JOIN FETCH`를 사용해야 한다.
- `@EntityGraph(attributePaths = {"orders"})`는 내부적으로 `LEFT OUTER JOIN FETCH`를 생성하므로 주문 없는 고객도 결과에 포함된다.
- `@EntityGraph`는 JPQL 문자열을 수정하지 않고 어노테이션만으로 즉시 로딩 경로를 선언할 수 있어, 동일한 쿼리를 여러 메서드에서 재사용할 때 편리하다.
- 두 해결법 모두 쿼리 횟수가 1번으로 줄어드는 것을 콘솔 SQL 로그로 직접 확인할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam26.App2
  ```
