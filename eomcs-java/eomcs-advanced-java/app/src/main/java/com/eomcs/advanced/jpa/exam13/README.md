# exam13 - 다대다(@ManyToMany) & 중간 테이블

## 개념

### 다대다(M:N) 관계와 중간 테이블

관계형 DB는 다대다 관계를 직접 표현할 수 없다. 두 테이블 사이에 **중간 테이블**을 두어 1:N + N:1 두 개의 관계로 분해한다.

```
[Java]                           [DB]
Product ─── @ManyToMany ───→  shop_product
                                    │  (product_id FK)
Category ← @ManyToMany ───       shop_product_category  ← 중간 테이블
                                    │  (category_id FK)
                               shop_category
```

JPA는 `@ManyToMany` + `@JoinTable`로 중간 테이블을 자동 관리한다. 단, **중간 테이블에 추가 컬럼(등록일, 수량 등)을 넣을 수 없다**는 한계가 있다.

### @ManyToMany의 한계와 연결 엔티티

| 방식 | 중간 테이블 관리 | 추가 속성 | JPQL 직접 조회 | 실무 권장 |
|---|---|---|---|---|
| `@ManyToMany` (App1) | JPA 자동 | 불가 | 불가 | 단순 관계 |
| 연결 엔티티 (App2) | 직접 엔티티로 관리 | 가능 | 가능 | **확장 가능성 있을 때** |

추가 속성이 필요하거나 중간 테이블을 직접 조회해야 한다면, `@ManyToMany` 대신 **연결 엔티티**(`ProductCategory`)로 리팩토링해야 한다.

### @MapsId

연결 엔티티에서 `@EmbeddedId`의 복합 PK 필드와 `@ManyToOne` FK를 하나로 연동하는 어노테이션이다.

```java
@EmbeddedId
private ProductCategoryId id;        // 복합 PK: (productId, categoryId)

@ManyToOne
@MapsId("productId")                 // id.productId ← product.id 로 채워짐
@JoinColumn(name = "product_id")
private Product product;
```

`@MapsId` 없이는 복합 PK 필드와 FK 컬럼을 별도로 관리해야 하고 값을 두 번 설정해야 한다.

---

## 사용 테이블

```
shop_product          (제품)
shop_category         (카테고리)
shop_product_category (중간 테이블 - product_id, category_id 복합 PK)
```

---

## App1 vs App2 비교

| 구분 | App1 (@ManyToMany) | App2 (연결 엔티티) |
|---|---|---|
| 중간 테이블 관리 | JPA 자동 | 직접 엔티티로 관리 |
| 추가 속성 | 불가 | 가능 (createdAt 등) |
| 직접 조회 | 불가 | JPQL로 직접 조회 가능 |
| 복잡도 | 낮음 | 높음 |
| 실무 권장 | 단순 관계 | **확장 가능성 있을 때** |

---

## 핵심 어노테이션

| 어노테이션 | 설명 |
|---|---|
| `@ManyToMany` | 다대다 관계 선언 |
| `@JoinTable` | 중간 테이블 및 FK 컬럼 정의 |
| `@EmbeddedId` | 복합 PK 매핑 (연결 엔티티) |
| `@MapsId` | @EmbeddedId 필드와 @ManyToOne FK 연동 |

---

## 엔티티 클래스 구조

### Product (다대다 주인)

```java
@Entity
@Table(name = "shop_product")
public class Product {

  @ManyToMany
  @JoinTable(
      name = "shop_product_category",
      joinColumns        = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToMany` | 다대다 관계 선언. 관계형 DB는 중간 테이블이 필요하므로 JPA가 자동으로 관리 |
| `@JoinTable(name = "shop_product_category")` | 중간 테이블명 지정 |
| `joinColumns` | 현재 엔티티(`Product`)의 FK 컬럼 (`product_id`) |
| `inverseJoinColumns` | 반대편 엔티티(`Category`)의 FK 컬럼 (`category_id`) |

- `@JoinTable`을 소유한 쪽이 연관관계 주인이다
- 중간 테이블에 추가 컬럼(등록일 등)을 넣을 수 없다는 것이 `@ManyToMany`의 한계

---

### Category (다대다 비주인)

```java
@Entity
@Table(name = "shop_category")
public class Category {

  @ManyToMany(mappedBy = "categories")
  private List<Product> products = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToMany(mappedBy = "categories")` | `Product.categories`가 주인임을 지정. 비주인은 읽기 전용 참조만 가진다 |

---

### ProductCategoryId (복합 PK 클래스)

```java
@Embeddable
public class ProductCategoryId implements Serializable {
  @Column(name = "product_id") private Long productId;
  @Column(name = "category_id") private Long categoryId;
  // equals() / hashCode() 필수 구현
}
```

| 요건 | 이유 |
|---|---|
| `@Embeddable` | `@EmbeddedId`로 사용하기 위한 선언 |
| `Serializable` 구현 | JPA 스펙: PK 클래스는 직렬화 가능해야 함 |
| `equals()` / `hashCode()` | 1차 캐시 조회 및 엔티티 동일성 보장에 사용 |

---

### ProductCategory (연결 엔티티 - App2 방식)

```java
@Entity
@Table(name = "shop_product_category")
public class ProductCategory {

  @EmbeddedId
  private ProductCategoryId id = new ProductCategoryId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "created_at")
  private LocalDateTime createdAt;  // @ManyToMany로는 불가능한 추가 속성
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EmbeddedId` | `@Embeddable` 복합 PK 클래스를 단일 필드로 선언 |
| `@MapsId("productId")` | `@EmbeddedId`의 `productId` 필드와 `@ManyToOne` FK를 연동. `product.id`가 복합 PK의 `productId`로 사용됨 |
| `@MapsId("categoryId")` | 동일하게 `category.id`를 복합 PK의 `categoryId`로 연동 |

- `@ManyToMany` 대비 장점: 중간 테이블에 `createdAt` 등 추가 속성을 붙일 수 있고 연결 자체를 JPQL로 직접 조회 가능

---

## App1 - @ManyToMany 기본 사용법

`Product ↔ Category` 다대다 관계를 `@ManyToMany`로 관리하는 방식을 확인한다.

```java
// 1. Product → Category 탐색 (지연 로딩)
List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
product.getCategories(); // ← 이 시점에 SELECT FROM shop_product_category JOIN 실행

// 2. Category → Product 역방향 탐색 (비주인)
category.getProducts(); // 읽기만 가능, INSERT/DELETE 영향 없음

// 3. 새 Product + Category 연결 저장
Product newProduct = new Product();
newProduct.addCategory(elec);   // 양방향 동기화 편의 메서드
em.persist(newProduct);
// INSERT INTO shop_product (...)
// INSERT INTO shop_product_category (product_id, category_id)

// 4. 연결 삭제
managed.getCategories().clear();
// DELETE FROM shop_product_category WHERE product_id = ?
```

---

## App2 - 연결 엔티티(ProductCategory)로 리팩토링

`@ManyToMany` 대신 `ProductCategory` 엔티티를 직접 사용하여 추가 속성 관리와 직접 조회를 가능하게 한다.

```java
// 1. 연결 엔티티 생성 (createdAt 자동 설정됨)
ProductCategory pc = new ProductCategory(product, elec);
em.persist(pc);
// INSERT INTO shop_product_category (product_id, category_id, created_at)

// 2. JPQL로 연결 엔티티 직접 조회 → 추가 속성(createdAt) 함께 조회 가능
List<ProductCategory> list = em.createQuery(
    "SELECT pc FROM ProductCategory pc ORDER BY pc.id.productId",
    ProductCategory.class).getResultList();
pc.getCreatedAt(); // @ManyToMany로는 접근 불가능한 필드

// 3. 복합 PK로 단건 조회
ProductCategoryId pk = new ProductCategoryId(productId, categoryId);
ProductCategory found = em.find(ProductCategory.class, pk);

// 4. 연결 삭제
em.remove(toRemove);
// DELETE FROM shop_product_category WHERE product_id=? AND category_id=?
```

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App1
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam13.App2
```
