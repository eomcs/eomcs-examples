# exam20 - Native Query & @SqlResultSetMapping

## 개념

### Native Query란

JPQL이 아닌 **DB 고유 SQL**을 직접 실행하는 방법이다.

```java
em.createNativeQuery("SELECT * FROM shop_customer WHERE city = :city", Customer.class)
  .setParameter("city", "서울")
  .getResultList();
```

- DB 고유 함수·힌트·복잡한 SQL을 자유롭게 사용 가능
- DB 종속적이어서 JPQL에 비해 이식성이 낮다
- 컴파일 타임 검증 없음 (오류는 런타임에 발견)

### @SqlResultSetMapping

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

### 재귀 CTE (Common Table Expression)

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

## 사용 테이블

```
shop_customer  ← Customer (@SqlResultSetMapping, @NamedNativeQuery 정의)
shop_orders    ← 주문 수 집계 서브쿼리에 사용
shop_category  ← Category (재귀 CTE 대상)
```

---

## 핵심 포인트

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

## 엔티티 클래스 구조

### Customer (with @SqlResultSetMapping + @NamedNativeQuery)

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

### CustomerSummary (DTO)

```java
public class CustomerSummary {
  public CustomerSummary(Long id, String name, String city, Long orderCount) { ... }
}
```

### Category (재귀 CTE용)

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

## App1 - 네이티브 SQL 사용

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

## App2 - @SqlResultSetMapping

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

## App3 - 재귀 CTE & CONNECT BY

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

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App2
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App3
```
