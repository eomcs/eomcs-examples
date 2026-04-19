# Exam09 - 연관관계 매핑 기초

## 개념

### 연관관계 매핑이란?

객체의 참조 필드(Customer customer)와 테이블의 외래 키(customer_id)를 연결하는 매핑이다.

```
[객체]                        [테이블]
Order.customer (참조)   ↔    shop_orders.customer_id (FK)
```

### @ManyToOne - 다대일 단방향

여러 주문(N)이 한 고객(1)에 속하는 관계다.

```java
// Order: N 쪽 (연관관계 주인, FK 보유)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;
```

| 어노테이션 | 역할 |
|---|---|
| `@ManyToOne` | 다대일 관계 선언 |
| `fetch = LAZY` | 지연 로딩 (사용 시점에 SELECT) |
| `fetch = EAGER` | 즉시 로딩 (조회 시 항상 JOIN) |
| `@JoinColumn(name)` | FK 컬럼명 지정 |

### @OneToMany + mappedBy - 일대다 양방향

단방향에 더해 Customer에서도 주문 목록을 탐색할 수 있다.

```java
// Customer: 1 쪽 (비주인, 읽기 전용)
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
private List<Order> orders = new ArrayList<>();
```

### 연관관계의 주인(Owner)

양방향 매핑에서 **FK를 보유한 쪽(N 쪽)이 주인**이다.

| 구분 | 주인 (Order.customer) | 비주인 (Customer.orders) |
|---|---|---|
| FK | 보유 (customer_id) | 없음 |
| DB 반영 | Yes | **No** (mappedBy 선언으로 읽기 전용) |
| 어노테이션 | `@ManyToOne` + `@JoinColumn` | `@OneToMany(mappedBy="...")` |

비주인 필드(`Customer.orders`)에 값을 넣어도 DB에 반영되지 않는다.
DB에 반영하려면 반드시 주인 필드(`order.setCustomer(customer)`)를 설정해야 한다.

### 양방향 편의 메서드

양쪽 연관관계를 한 번에 설정하는 메서드를 만들면 실수를 줄일 수 있다.

```java
// Customer 측 편의 메서드
public void addOrder(Order order) {
    orders.add(order);        // 비주인 측 설정 (1차 캐시 일관성)
    order.setCustomer(this);  // 주인 측 설정 (DB 반영)
}
```

### 지연 로딩(Lazy Loading)

```java
// LAZY: order.getCustomer() 호출 시 SELECT 실행
@ManyToOne(fetch = FetchType.LAZY)
private Customer customer;

Order order = em.find(Order.class, 1L);
// 여기까지 customer SELECT 없음
Customer c = order.getCustomer(); // 이 시점에 SELECT 실행
System.out.println(c.getName());
```

- `@ManyToOne`의 기본값은 `EAGER`이지만 성능 최적화를 위해 `LAZY` 권장.
- `@OneToMany`의 기본값은 `LAZY`.

---

## App - 단방향 @ManyToOne

Order에서 Customer로의 단방향 참조를 통해 연관 데이터를 탐색한다.

```java
// 주문 목록 조회 후 연관 고객 접근 (LAZY 로딩)
List<Order> orders = em
    .createQuery("SELECT o FROM Order o ORDER BY o.id", Order.class)
    .getResultList();

for (Order o : orders) {
    Customer c = o.getCustomer();  // 이 시점에 SELECT customer 실행
    System.out.println(o + " → " + c);
}

// JPQL JOIN: 연관 엔티티를 JOIN 하여 조회
List<Order> joined = em
    .createQuery("SELECT o FROM Order o JOIN o.customer c ORDER BY o.id", Order.class)
    .getResultList();

// 연관 필드 조건 (Hibernate가 자동으로 JOIN SQL 생성)
List<Order> result = em
    .createQuery("SELECT o FROM Order o WHERE o.customer.name = :name", Order.class)
    .setParameter("name", "홍길동")
    .getResultList();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App
  ```

---

## App2 - 양방향 @OneToMany + @ManyToOne

Customer에서 orders 컬렉션으로 역방향 탐색을 추가한다.

```java
// Customer → orders 탐색 (지연 로딩: getOrders() 호출 시 SELECT)
Customer customer = em.find(Customer.class, 1L);
List<Order> orders = customer.getOrders();  // SELECT 실행
System.out.println("주문 수: " + orders.size());

// JPQL: Customer 기준 JOIN
List<Customer> result = em
    .createQuery(
        "SELECT DISTINCT c FROM Customer c JOIN c.orders o ORDER BY c.id",
        Customer.class)
    .getResultList();
```

주의: 모든 고객을 반복하며 `getOrders()`를 호출하면 **N+1 문제**가 발생한다.
- 고객 1건 SELECT + 고객 수 N번 주문 SELECT = N+1 쿼리
- 해결: `JOIN FETCH` (exam18에서 다룸)

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App2
  ```
