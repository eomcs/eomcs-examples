# exam18 - JPQL 심화

## 개념

### N+1 문제와 JOIN FETCH

지연 로딩(LAZY) 설정 시 연관 엔티티를 실제로 접근할 때 추가 SELECT가 발생한다.

```
[N+1 문제]
SELECT * FROM shop_orders                   ← 1번 (3건 조회)
SELECT * FROM shop_customer WHERE id = 1    ← N번 (각 주문마다)
SELECT * FROM shop_customer WHERE id = 2
...
총 1 + N 번 SELECT 실행

[JOIN FETCH 해결]
SELECT o.*, c.*
  FROM shop_orders o
  JOIN shop_customer c ON c.id = o.customer_id
                                             ← 1번으로 해결
```

### 서브쿼리

JPQL의 WHERE/HAVING 절에서 서브쿼리를 사용할 수 있다.

| 구문 | 설명 |
|---|---|
| `EXISTS (서브쿼리)` | 서브쿼리에 결과가 하나 이상 있으면 true |
| `NOT EXISTS (서브쿼리)` | 서브쿼리에 결과가 없으면 true |
| `IN (서브쿼리)` | 서브쿼리 결과 집합에 포함되면 true |
| `ALL (서브쿼리)` | 서브쿼리 모든 값과 비교가 true |
| `스칼라 서브쿼리` | WHERE 절에서 단일 값 반환 서브쿼리 사용 |

### Named Query

엔티티 클래스에 `@NamedQuery`로 JPQL을 미리 정의해 두는 방식이다.

```
[정의]                              [사용]
@NamedQuery(                        em.createNamedQuery(
  name  = "Customer.findAll",    →      "Customer.findAll",
  query = "SELECT c FROM ..."         Customer.class)
)                                       .getResultList();
```

- EntityManagerFactory 기동 시 JPQL 파싱·검증 → 오타를 런타임 전에 발견
- 파싱 결과를 캐시하여 반복 호출 시 `createQuery`보다 빠름

---

## 사용 테이블

```
shop_customer  ← Customer
shop_orders    ← Order     (customer_id FK → shop_customer)
shop_order_item← OrderItem (복합 PK: order_id + product_id)
shop_product   ← Product
```

---

## 연관관계 구조

```
Customer ── Order (ManyToOne, LAZY)
             └── OrderItem (OneToMany, LAZY, mappedBy="order")
                     └── Product (ManyToOne, LAZY)
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JOIN FETCH` | 연관 엔티티를 즉시 한 번의 SQL로 로드 |
| `DISTINCT` | 컬렉션 JOIN FETCH 시 중복 제거 필수 |
| `EXISTS` | 서브쿼리 결과 존재 여부 조건 |
| `NOT EXISTS` | 서브쿼리 결과 없음 조건 |
| `@NamedQuery` | 엔티티에 정의, 기동 시 검증·캐시 |

---

## 엔티티 클래스 구조

### Customer (with @NamedQuery)

```java
@Entity
@Table(name = "shop_customer")
@NamedQuery(name = "Customer.findAll",
    query = "SELECT c FROM Customer c ORDER BY c.id")
@NamedQuery(name = "Customer.findByCity",
    query = "SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id")
public class Customer {
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();
}
```

### Order

```java
@Entity
@Table(name = "shop_orders")
public class Order {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> orderItems = new ArrayList<>();
}
```

### OrderItem (@IdClass 복합 키)

```java
@Entity
@Table(name = "shop_order_item")
@IdClass(OrderItemId.class)
public class OrderItem {
  @Id @Column(name = "order_id")   private Long orderId;
  @Id @Column(name = "product_id") private Long productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;
}
```

### Product (with @NamedQuery)

```java
@Entity
@Table(name = "shop_product")
@NamedQuery(name = "Product.findByPriceRange",
    query = "SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max ORDER BY p.price")
@NamedQuery(name = "Product.findExpensiveThanAvg",
    query = "SELECT p FROM Product p WHERE p.price > (SELECT AVG(p2.price) FROM Product p2) ORDER BY p.price DESC")
public class Product { ... }
```

---

## App1 - JOIN FETCH (N+1 문제 해결)

N+1 문제 발생 확인, JOIN FETCH 해결, 컬렉션 JOIN FETCH, 다중 JOIN FETCH 순서로 확인한다.

```java
// N+1 문제: 주문 조회 후 각 고객 지연 로딩
List<Order> orders = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
for (Order o : orders) {
  o.getCustomer().getName(); // 각 주문마다 추가 SELECT 발생
}

// JOIN FETCH 해결: 한 번에 로드
List<Order> orders = em.createQuery(
    "SELECT o FROM Order o JOIN FETCH o.customer ORDER BY o.id",
    Order.class).getResultList();

// 컬렉션 JOIN FETCH: DISTINCT 로 중복 제거
List<Order> orders = em.createQuery(
    "SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems ORDER BY o.id",
    Order.class).getResultList();
```

---

## App2 - 서브쿼리

EXISTS, NOT EXISTS, IN + 서브쿼리, 스칼라 서브쿼리, ALL 서브쿼리를 확인한다.

```java
// EXISTS: 주문이 있는 고객
em.createQuery("SELECT c FROM Customer c"
    + " WHERE EXISTS (SELECT o FROM Order o WHERE o.customer = c)", Customer.class);

// 스칼라 서브쿼리: 평균 가격 초과 제품
em.createQuery("SELECT p FROM Product p"
    + " WHERE p.price > (SELECT AVG(p2.price) FROM Product p2)"
    + " ORDER BY p.price DESC", Product.class);
```

---

## App3 - Named Query

`@NamedQuery`로 정의된 쿼리를 `createNamedQuery`로 호출하고, 성능 차이를 비교한다.

```java
// 파라미터 없는 Named Query
em.createNamedQuery("Customer.findAll", Customer.class).getResultList();

// 파라미터 있는 Named Query
em.createNamedQuery("Customer.findByCity", Customer.class)
    .setParameter("city", "서울")
    .getResultList();
```

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App2
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App3
```
