# exam11 - 상속 매핑 전략

## 개념

### JPA 상속 매핑이란

객체 지향의 상속 관계를 관계형 DB 테이블 구조로 표현하는 방법이다.
관계형 DB는 상속 개념이 없으므로 JPA가 이를 3가지 전략 중 하나로 변환한다.

```
[Java]                         [DB]
Product (abstract)
  ├─ PhysicalProduct    →  ?  (전략에 따라 테이블 구조가 달라진다)
  └─ DigitalProduct
```

### 3가지 상속 매핑 전략

| 전략 | 테이블 수 | 조회 | NULL | 추천 상황 |
|---|---|---|---|---|
| `SINGLE_TABLE` | 1 | 빠름 (JOIN 없음) | 많음 | 단순하고 빠른 조회 필요 시 |
| `JOINED` | 1 + 자식 수 | JOIN 발생 | 없음 | **정규화가 중요할 때 (기본 권장)** |
| `TABLE_PER_CLASS` | 자식 수 | UNION | 없음 | 특정 타입만 단독 조회 시 |

**SINGLE_TABLE**: 부모·자식의 모든 컬럼을 하나의 테이블에 저장. `dtype` 컬럼으로 타입 구분. 자식 전용 컬럼은 NULL 허용.

**JOINED** (이 예제): 부모 테이블과 자식별 테이블로 정규화. 조회 시 JOIN 발생. NULL 없이 깔끔한 구조.

**TABLE_PER_CLASS**: 자식 엔티티마다 독립된 테이블(부모 컬럼 포함). 부모 타입으로 다형성 조회 시 UNION으로 느려짐.

### @DiscriminatorColumn

부모 테이블에 저장되는 자식 타입 구분 컬럼이다.

| 전략 | 필요 여부 |
|---|---|
| `SINGLE_TABLE` | **필수** — 구분 컬럼 없이는 타입을 알 수 없음 |
| `JOINED` | **선택적** — 자식 테이블 PK 존재 여부로 판별 가능하나, 명시하면 성능에 도움 |
| `TABLE_PER_CLASS` | **불필요** — 타입별 테이블이 있어 의미 없음 |

---

## 사용 테이블

```
shop_product          (부모)
shop_physical_product (자식 - 실물 제품)
shop_digital_product  (자식 - 디지털 제품)
```

---

## 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@Inheritance(strategy = JOINED)` | 부모 엔티티 | 상속 전략 선택 |
| `@DiscriminatorColumn(name = "dtype")` | 부모 엔티티 | 타입 구분 컬럼 |
| `@DiscriminatorValue("PHYSICAL")` | 자식 엔티티 | 구분 값 지정 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | 자식 엔티티 | 자식 테이블의 PK/FK 컬럼명 |

---

## 3가지 전략 비교

| 전략 | 테이블 수 | 조회 | NULL | 추천 상황 |
|---|---|---|---|---|
| `SINGLE_TABLE` | 1 | 빠름 (JOIN 없음) | 많음 | 단순하고 빠른 조회 필요 시 |
| `JOINED` | 1 + 자식 수 | JOIN 발생 | 없음 | **정규화가 중요할 때 (기본 권장)** |
| `TABLE_PER_CLASS` | 자식 수 | UNION | 없음 | 특정 타입만 단독 조회 시 |

---

## 엔티티 클래스 구조

### Product (부모 엔티티)

```java
@Entity
@Table(name = "shop_product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@Inheritance(strategy = JOINED)` | 자식 엔티티마다 별도 테이블을 만들고 JOIN으로 연결하는 전략 선택 |
| `@DiscriminatorColumn(name = "dtype")` | `shop_product.dtype` 컬럼에 자식 타입 식별자(`'PHYSICAL'`, `'DIGITAL'`)를 저장. JOINED 전략에서는 선택적이나 명시하면 다형성 조회 성능이 향상된다 |

- `abstract`로 선언 → 직접 인스턴스화 불가, 반드시 자식 타입으로만 저장/조회
- `id`는 `IDENTITY` 전략으로 자동 생성되며, 자식 테이블의 PK(`product_id`)로도 사용된다

---

### PhysicalProduct (자식 엔티티 - 실물 제품)

```java
@Entity
@Table(name = "shop_physical_product")
@DiscriminatorValue("PHYSICAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class PhysicalProduct extends Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@DiscriminatorValue("PHYSICAL")` | `shop_product.dtype = 'PHYSICAL'`인 행이 이 클래스로 매핑됨 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | 자식 테이블(`shop_physical_product`)의 PK 컬럼명을 `product_id`로 지정. 이 컬럼이 동시에 부모 테이블 `id`를 참조하는 FK |

```
INSERT 시 SQL 2회 실행:
  INSERT INTO shop_product (dtype, name, price, ...) VALUES ('PHYSICAL', ...)
  INSERT INTO shop_physical_product (product_id, weight, shipping_fee) VALUES (?, ?, ?)

SELECT 시 INNER JOIN 실행:
  SELECT p.*, pp.weight, pp.shipping_fee
  FROM shop_product p
  JOIN shop_physical_product pp ON p.id = pp.product_id
  WHERE p.id = ?
```

---

### DigitalProduct (자식 엔티티 - 디지털 제품)

```java
@Entity
@Table(name = "shop_digital_product")
@DiscriminatorValue("DIGITAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class DigitalProduct extends Product { ... }
```

| 어노테이션 | 의미 |
|---|---|
| `@DiscriminatorValue("DIGITAL")` | `shop_product.dtype = 'DIGITAL'`인 행이 이 클래스로 매핑됨 |
| `@PrimaryKeyJoinColumn(name = "product_id")` | PhysicalProduct와 동일한 방식으로 부모 PK를 자식 PK/FK로 연결 |

```
INSERT 시 SQL 2회 실행:
  INSERT INTO shop_product (dtype, name, price, ...) VALUES ('DIGITAL', ...)
  INSERT INTO shop_digital_product (product_id, download_url, license_count) VALUES (?, ?, ?)
```

---

## `@DiscriminatorColumn` 은 반드시 필요한가?

**전략에 따라 다르다.**

| 전략 | 필요 여부 | 이유 |
|------|-----------|------|
| `SINGLE_TABLE` | **필수** (기본 자동 생성) | 하나의 테이블에 모든 타입이 섞이므로 구분 컬럼이 없으면 타입을 알 수 없음 |
| `JOINED` | **선택적** | 자식 테이블 PK의 존재 여부로 타입 판별 가능. 단, 명시하면 조회 성능에 도움 |
| `TABLE_PER_CLASS` | **불필요/무시** | 타입별로 별도 테이블이 있어 구분 컬럼 자체가 의미 없음 |

### JOINED 전략에서 명시하는 이유

- `SELECT * FROM shop_product` 처럼 **부모 테이블만 조회할 때** dtype 값으로 어떤 자식 타입인지 즉시 확인 가능
- JPA가 다형성 조회 시 JOIN 대상 자식 테이블을 dtype으로 먼저 판단 → **불필요한 JOIN 감소**

> JOINED 전략에서 `@DiscriminatorColumn` 없이도 JPA는 정상 동작하지만, 있으면 성능과 가독성 측면에서 유리하다.

---

## App - JOINED 전략 INSERT/SELECT 전체 흐름

PhysicalProduct·DigitalProduct 저장 후 단건 조회, 다형성 조회, 타입별 JPQL 조회 순서로 JOINED 전략 동작을 확인한다.

```java
// 1. PhysicalProduct 저장 → 부모/자식 테이블에 각각 INSERT
PhysicalProduct laptop = new PhysicalProduct();
laptop.setName("LG 그램 17");
laptop.setPrice(new BigDecimal("1590000"));
laptop.setWeight(new BigDecimal("1.350"));
laptop.setShippingFee(new BigDecimal("3000"));
em.persist(laptop);
tx.commit();
// INSERT INTO shop_product (dtype='PHYSICAL', name, price, ...)
// INSERT INTO shop_physical_product (product_id, weight, shipping_fee)

// 2. DigitalProduct 저장
DigitalProduct sw = new DigitalProduct();
sw.setName("한컴오피스 2024");
sw.setDownloadUrl("https://www.hancom.com/download");
em.persist(sw);
tx.commit();
// INSERT INTO shop_product (dtype='DIGITAL', ...)
// INSERT INTO shop_digital_product (product_id, download_url, license_count)

// 3. 특정 타입 단건 조회 → INNER JOIN
PhysicalProduct found = em.find(PhysicalProduct.class, physicalId);
// SELECT pp.*, p.* FROM shop_physical_product pp
//   JOIN shop_product p ON pp.product_id = p.id WHERE pp.product_id = ?

// 4. 부모 타입으로 다형성 조회 → dtype 보고 적합한 서브클래스 반환
Product byParent = em.find(Product.class, digitalId);
// dtype = 'DIGITAL' → DigitalProduct 인스턴스로 반환
byParent.getClass().getSimpleName(); // "DigitalProduct"

// 5. JPQL 다형성 조회 → 모든 자식 테이블 LEFT JOIN
List<Product> all = em.createQuery(
    "SELECT p FROM Product p ORDER BY p.id", Product.class).getResultList();
// SELECT p.*, dp.*, pp.* FROM shop_product p
//   LEFT JOIN shop_digital_product dp ON p.id = dp.product_id
//   LEFT JOIN shop_physical_product pp ON p.id = pp.product_id

// 6. JPQL 타입별 조회 → 해당 자식 테이블만 INNER JOIN
List<PhysicalProduct> physicals = em.createQuery(
    "SELECT p FROM PhysicalProduct p ORDER BY p.id", PhysicalProduct.class).getResultList();
// SELECT pp.*, p.* FROM shop_physical_product pp
//   JOIN shop_product p ON pp.product_id = p.id
```

- 실행 명령:
```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam11.App
```
