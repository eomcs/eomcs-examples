# exam15 - 복합 키 매핑

## 개념

### 복합 기본 키(Composite PK)란

둘 이상의 컬럼이 함께 기본 키를 구성하는 구조다. 주문 상세(`order_id + product_id`)처럼 두 FK가 합쳐서 유일성을 보장할 때 사용한다.

```
shop_order_item
  PK = (order_id, product_id)  ← 두 컬럼의 조합이 기본 키
```

JPA는 복합 PK를 반드시 **별도 클래스**로 정의해야 한다. 방법은 두 가지다.

### @IdClass vs @EmbeddedId

| 구분 | `@IdClass` (App1) | `@EmbeddedId` (App2) |
|---|---|---|
| PK 클래스 | 일반 클래스 (`Serializable` 구현) | `@Embeddable` 클래스 |
| 엔티티 선언 | `@Id`를 여러 개 선언 | `@EmbeddedId` 단일 필드 |
| JPQL 접근 | `oi.orderId` (필드 직접 접근) | `oi.id.orderId` (객체 경유) |
| find() | `new OrderItemId(1L, 1L)` | `new OrderItemPK(1L, 1L)` |
| 특징 | 단순, JPQL 편리 | PK 객체 자체를 다루기 편리, 재사용 가능 |

### PK 클래스 필수 요건 (JPA 스펙)

두 방식 모두 PK 클래스가 다음 요건을 만족해야 한다.

1. `public` 클래스
2. 기본 생성자 필수
3. `Serializable` 구현 — 1차 캐시 직렬화 등에 사용
4. `equals()` / `hashCode()` 구현 — 동일 PK 엔티티 식별에 사용

---

## 사용 테이블

```
shop_order_item
  order_id   NUMBER  PK(1/2)  FK → shop_orders
  product_id NUMBER  PK(2/2)  FK → shop_product
  quantity   NUMBER
  price      NUMBER
```

---

## 엔티티 클래스 구조

### App1 방식 - @IdClass

#### OrderItemId (PK 클래스)

```java
public class OrderItemId implements Serializable {
  private Long orderId;
  private Long productId;
  // equals() / hashCode() 필수
}
```

- 일반 클래스. `@Embeddable` 불필요
- 필드명이 엔티티의 `@Id` 필드명과 반드시 일치해야 함

#### OrderItem (엔티티)

```java
@Entity
@Table(name = "shop_order_item")
@IdClass(OrderItemId.class)
public class OrderItem {

  @Id @Column(name = "order_id")   private Long orderId;
  @Id @Column(name = "product_id") private Long productId;

  private int quantity;
  private BigDecimal price;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@IdClass(OrderItemId.class)` | 복합 PK 클래스 지정. PK 필드가 엔티티에 직접 노출됨 |
| `@Id` (복수) | 복합 PK를 구성하는 각 필드에 개별 선언 |

- JPQL 접근: `oi.orderId` (PK 필드를 일반 필드처럼 직접 사용)
- `find()` 호출: `em.find(OrderItem.class, new OrderItemId(1L, 1L))`

---

### App2 방식 - @EmbeddedId

#### OrderItemPK (PK 클래스)

```java
@Embeddable
public class OrderItemPK implements Serializable {
  @Column(name = "order_id")   private Long orderId;
  @Column(name = "product_id") private Long productId;
  // equals() / hashCode() 필수
}
```

- `@Embeddable` 필수. 컬럼 매핑을 PK 클래스 내부에서 선언

#### OrderItemV2 (엔티티)

```java
@Entity
@Table(name = "shop_order_item")
public class OrderItemV2 {

  @EmbeddedId
  private OrderItemPK id;

  private int quantity;
  private BigDecimal price;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EmbeddedId` | `@Embeddable` PK 클래스를 단일 필드로 선언. PK가 하나의 객체로 묶임 |

- JPQL 접근: `oi.id.orderId` (PK 객체를 거쳐 접근)
- `find()` 호출: `em.find(OrderItemV2.class, new OrderItemPK(1L, 1L))`

---

## App1 - @IdClass 방식 INSERT/SELECT/UPDATE/DELETE

```java
// 1. 전체 조회 (JPQL: 필드 직접 접근)
em.createQuery(
    "SELECT oi FROM OrderItem oi ORDER BY oi.orderId, oi.productId", OrderItem.class)
    .getResultList();

// 2. 복합 PK로 단건 조회
OrderItemId pk = new OrderItemId(1L, 1L);
OrderItem found = em.find(OrderItem.class, pk);

// 3. INSERT
OrderItem newItem = new OrderItem();
newItem.setOrderId(3L);
newItem.setProductId(1L);
newItem.setQuantity(1);
newItem.setPrice(new BigDecimal("2990000"));
em.persist(newItem);

// 4. JPQL 조건 조회 (@IdClass: PK 필드 직접 접근)
em.createQuery(
    "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", OrderItem.class)
    .setParameter("orderId", 1L).getResultList();

// 5. 수량 변경 (Dirty Checking)
OrderItem managed = em.find(OrderItem.class, new OrderItemId(3L, 1L));
managed.setQuantity(2);
tx.commit(); // UPDATE 자동 실행

// 6. 삭제
em.remove(em.find(OrderItem.class, new OrderItemId(3L, 1L)));
```

- `@IdClass` 방식은 PK 필드(`orderId`, `productId`)가 엔티티에 직접 노출된다. JPQL에서 `oi.orderId`처럼 일반 필드처럼 바로 접근할 수 있어 쿼리 작성이 간결하다.
- 복합 PK로 단건 조회 시 `new OrderItemId(orderId, productId)` PK 객체를 만들어 `em.find()`에 전달한다.
- 수량 변경처럼 PK가 아닌 일반 필드 수정은 Dirty Checking이 정상적으로 작동해 `commit()` 시 UPDATE가 자동 실행된다.
- `@IdClass`에서 PK 클래스(`OrderItemId`)는 엔티티의 `@Id` 필드명과 반드시 일치하는 필드를 가져야 한다. `equals()`와 `hashCode()` 구현도 필수다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App1
  ```

---

## App2 - @EmbeddedId 방식 INSERT/SELECT/DELETE

```java
// 1. 전체 조회 (JPQL: PK 객체 경유)
em.createQuery(
    "SELECT oi FROM OrderItemV2 oi ORDER BY oi.id.orderId, oi.id.productId",
    OrderItemV2.class).getResultList();

// 2. 복합 PK로 단건 조회
OrderItemPK pk = new OrderItemPK(1L, 1L);
OrderItemV2 found = em.find(OrderItemV2.class, pk);

// 3. INSERT
OrderItemV2 newItem = new OrderItemV2();
newItem.setId(new OrderItemPK(3L, 5L));
newItem.setQuantity(1);
newItem.setPrice(new BigDecimal("189000"));
em.persist(newItem);

// 4. JPQL 조건 조회 (@EmbeddedId: PK 객체 경유)
em.createQuery(
    "SELECT oi FROM OrderItemV2 oi WHERE oi.id.orderId = :orderId",
    OrderItemV2.class).setParameter("orderId", 1L).getResultList();

// 5. 삭제
em.remove(em.find(OrderItemV2.class, new OrderItemPK(3L, 5L)));
```

- `@EmbeddedId` 방식은 PK가 하나의 객체(`OrderItemPK`)로 묶인다. JPQL에서는 `oi.id.orderId`처럼 PK 객체를 거쳐 필드에 접근한다.
- INSERT 시 `newItem.setId(new OrderItemPK(orderId, productId))`로 복합 PK 전체를 한 번에 설정한다. `@IdClass`처럼 각 필드를 따로 설정하는 것과 대비된다.
- PK 클래스(`OrderItemPK`)에 `@Embeddable`이 선언되어 있으며, 컬럼 매핑(`@Column`)이 PK 클래스 내부에서 이루어진다.
- `@IdClass`와 달리 PK 객체를 별도 변수로 다루거나 다른 엔티티에서 재사용하기 편리하다. 복잡한 복합 PK를 여러 엔티티에서 공유하는 경우에 유리하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App2
  ```
