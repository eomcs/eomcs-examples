# exam17 - JPQL 기초

## 개념

### JPQL(Java Persistence Query Language)이란

SQL은 테이블·컬럼명을 사용하지만, JPQL은 **엔티티 클래스명·필드명**을 사용하는 객체지향 쿼리 언어다.

```
[SQL]                              [JPQL]
SELECT * FROM shop_customer     →  SELECT c FROM Customer c
WHERE city = '서울'              →  WHERE c.city = '서울'
ORDER BY id                     →  ORDER BY c.id
```

- Hibernate가 JPQL을 DB 방언(Dialect)에 맞는 SQL로 변환한다
- 특정 DB에 종속되지 않아 이식성이 높다

### 파라미터 바인딩

| 방식 | 문법 | 특징 |
|---|---|---|
| 이름 기반 | `:name` | 순서 무관, 가독성 좋음 → **권장** |
| 위치 기반 | `?1` | 1부터 시작, 순서 의존 |

### 프로젝션(Projection)

SELECT 절에 무엇을 반환할지 지정하는 것이다.

| 종류 | 예시 | 반환 타입 |
|---|---|---|
| 엔티티 | `SELECT c FROM Customer c` | `List<Customer>` (managed) |
| 단일 컬럼 | `SELECT c.name FROM Customer c` | `List<String>` |
| 다중 컬럼 | `SELECT c.name, c.city FROM Customer c` | `List<Object[]>` |
| 생성자 표현식 | `SELECT new DTO(c.name, c.city) FROM Customer c` | `List<DTO>` |

---

## 사용 테이블

```
shop_customer
  id         NUMBER PK
  name       VARCHAR2
  email      VARCHAR2
  city       VARCHAR2
  created_at TIMESTAMP
  updated_at TIMESTAMP

shop_product
  id         NUMBER PK
  dtype      VARCHAR2  (읽기 전용: PHYSICAL/DIGITAL)
  name       VARCHAR2
  price      NUMBER
  stock      NUMBER
  created_at TIMESTAMP
  updated_at TIMESTAMP
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| 엔티티명 사용 | `Customer` (테이블명 `shop_customer` 아님) |
| 필드명 사용 | `c.city` (컬럼명 `city` 와 동일하지만 Java 필드 기준) |
| 집계 함수 | `COUNT`, `SUM`, `AVG`, `MIN`, `MAX` |
| 페이징 | `setFirstResult(시작위치)` / `setMaxResults(건수)` |
| 타입 안전 | `createQuery(jpql, 클래스)` → `TypedQuery<T>` 반환 |

---

## 엔티티 클래스 구조

### Customer

```java
@Entity
@Table(name = "shop_customer")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String city;
}
```

### Product

```java
@Entity
@Table(name = "shop_product")
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // dtype: 기존 DB 컬럼 읽기 전용 매핑
  @Column(name = "dtype", insertable = false, updatable = false)
  private String dtype;

  private String name;
  private BigDecimal price;
  private int stock;
}
```

### CustomerNameCityDto (생성자 표현식용 DTO)

```java
public class CustomerNameCityDto {
  private final String name;
  private final String city;

  public CustomerNameCityDto(String name, String city) { ... }
}
```

---

## App1 - 기본 JPQL 쿼리 문법

SELECT, WHERE, LIKE, BETWEEN, IN, IS NULL, 집계 함수, GROUP BY, ORDER BY 를 순서대로 확인한다.

```java
// 전체 조회
em.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class).getResultList();

// WHERE + 조건
em.createQuery("SELECT c FROM Customer c WHERE c.city = '서울'", Customer.class);

// BETWEEN: 가격 범위
em.createQuery("SELECT p FROM Product p WHERE p.price BETWEEN :low AND :high ORDER BY p.price",
    Product.class)
    .setParameter("low", new BigDecimal("100000"))
    .setParameter("high", new BigDecimal("2000000"));

// 집계 함수
Object[] stats = (Object[]) em.createQuery(
    "SELECT COUNT(p), AVG(p.price), MIN(p.price), MAX(p.price) FROM Product p")
    .getSingleResult();

// GROUP BY + HAVING
em.createQuery("SELECT c.city, COUNT(c) FROM Customer c WHERE c.city IS NOT NULL"
    + " GROUP BY c.city HAVING COUNT(c) >= 1");
```

- JPQL에서 테이블명 대신 **엔티티 클래스명**, 컬럼명 대신 **Java 필드명**을 사용한다. `shop_customer` 대신 `Customer`, `created_at` 대신 `createdAt`을 쓴다.
- 집계 함수(`COUNT`, `AVG`, `MIN`, `MAX`)의 반환 타입은 각각 `Long`, `Double`, `Object` 타입이다. 여러 집계를 한 쿼리에서 조회하면 `Object[]`로 반환된다.
- `BETWEEN :low AND :high`처럼 이름 기반 파라미터를 사용하면 순서에 관계없이 바인딩할 수 있어 가독성이 높다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App1
  ```

---

## App2 - 파라미터 바인딩

이름 기반·위치 기반 파라미터, TypedQuery vs Query, 다중 파라미터, 페이징을 확인한다.

```java
// 이름 기반 (:name) - 권장
em.createQuery("SELECT c FROM Customer c WHERE c.name = :name", Customer.class)
    .setParameter("name", "홍길동");

// 위치 기반 (?1)
em.createQuery("SELECT c FROM Customer c WHERE c.city = ?1", Customer.class)
    .setParameter(1, "서울");

// 페이징
em.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
    .setFirstResult(0)    // 시작 위치 (0-based)
    .setMaxResults(2);    // 최대 건수
```

- 이름 기반 파라미터(`:name`)는 파라미터명을 문자열로 지정하므로 순서가 달라도 문제없다. 가독성과 유지보수 측면에서 위치 기반(`?1`)보다 권장된다.
- 위치 기반 파라미터 인덱스는 JPQL에서 **1부터 시작**한다. JDBC의 `PreparedStatement`와 동일하다.
- `setFirstResult(0)`은 0번째 행부터 시작(0-based)하고, `setMaxResults(2)`는 최대 2건을 가져온다. Hibernate가 DB 방언에 맞는 페이징 SQL(`FETCH FIRST`, `ROWNUM`, `LIMIT` 등)을 자동 생성한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App2
  ```

---

## App3 - 프로젝션

엔티티·단일 컬럼·Object[]·생성자 표현식·집계 프로젝션을 순서대로 확인한다.

```java
// 단일 컬럼
List<String> names = em.createQuery("SELECT c.name FROM Customer c", String.class).getResultList();

// 다중 컬럼 Object[]
List<Object[]> rows = em.createQuery("SELECT c.id, c.name, c.city FROM Customer c")
    .getResultList();

// 생성자 표현식 → DTO
List<CustomerNameCityDto> dtos = em.createQuery(
    "SELECT new com.eomcs.advanced.jpa.exam17.CustomerNameCityDto(c.name, c.city)"
    + " FROM Customer c",
    CustomerNameCityDto.class)
    .getResultList();
```

- 단일 컬럼 프로젝션(`SELECT c.name`)은 `List<String>`처럼 해당 컬럼 타입의 리스트로 바로 반환된다.
- 다중 컬럼 프로젝션(`SELECT c.id, c.name, c.city`)은 `List<Object[]>`로 반환된다. `row[0]`, `row[1]`처럼 인덱스로 접근하므로 타입 캐스팅이 필요하고 실수하기 쉽다.
- 생성자 표현식(`SELECT new 패키지.DTO(...)`)을 사용하면 결과가 DTO 인스턴스의 리스트로 반환되어 타입 안전하게 사용할 수 있다. DTO 클래스에 해당 시그니처의 생성자가 반드시 있어야 한다.
- 엔티티 프로젝션으로 조회된 엔티티는 영속 상태(managed)가 되어 Dirty Checking이 작동한다. 단일/다중 컬럼·DTO 프로젝션은 영속 상태가 아니다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App3
  ```
