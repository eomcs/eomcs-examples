# exam19 - Criteria API

## 개념

### Criteria API란

JPQL을 문자열 대신 **Java 코드(객체)**로 작성하는 타입 안전(Type-Safe) 쿼리 API다.

```
[JPQL 문자열]                         [Criteria API]
"SELECT c FROM Customer c          CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
 WHERE c.city = '서울'"         →   Root<Customer> root = cq.from(Customer.class);
                                   cq.select(root).where(cb.equal(root.get("city"), "서울"));
```

- 오타가 있으면 **컴파일 오류** 또는 기동 시 즉시 발견 (JPQL 문자열은 런타임에야 발견)
- 조건을 동적으로 추가·제거하기 쉬워 **동적 쿼리 구성**에 특히 유리하다

### 동적 쿼리 비교

```
[JPQL 문자열 방식 - 취약]
String jpql = "SELECT c FROM Customer c WHERE 1=1";
if (city != null) jpql += " AND c.city = '" + city + "'";  // SQL 인젝션 위험
if (name != null) jpql += " AND c.name LIKE '%" + name + "%'";

[Criteria API - 권장]
List<Predicate> predicates = new ArrayList<>();
if (city != null) predicates.add(cb.equal(root.get("city"), city));
if (name != null) predicates.add(cb.like(root.get("name"), "%" + name + "%"));
cq.where(cb.and(predicates.toArray(new Predicate[0])));
```

### 주요 클래스

| 클래스 | 역할 |
|---|---|
| `CriteriaBuilder` | Predicate·Expression·Order 생성 팩토리 |
| `CriteriaQuery<T>` | SELECT 절 반환 타입을 결정하는 쿼리 객체 |
| `Root<T>` | FROM 절 엔티티 루트. 필드 접근은 `root.get("필드명")` |
| `Predicate` | WHERE 절 조건. `cb.and` / `cb.or`로 조합 |
| `Order` | ORDER BY 절. `cb.asc` / `cb.desc` |

---

## 사용 테이블

```
shop_customer  ← Customer (id, name, email, city)
shop_product   ← Product  (id, dtype, name, price, stock)
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `cb.equal(root.get("city"), "서울")` | `c.city = '서울'` |
| `cb.like(root.get("name"), "%홍%")` | `c.name LIKE '%홍%'` |
| `cb.between(root.get("price"), min, max)` | `p.price BETWEEN min AND max` |
| `cb.and(p1, p2)` | `p1 AND p2` |
| `cb.or(p1, p2)` | `p1 OR p2` |
| `cb.isNotNull(root.get("city"))` | `c.city IS NOT NULL` |
| `cb.count(root)` | `COUNT(c)` |

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
  @Column(name = "dtype", insertable = false, updatable = false) private String dtype;
  private String name;
  private BigDecimal price;
  private int stock;
}
```

---

## App - Criteria API 전체 흐름

기본 쿼리, 단일 WHERE, 동적 쿼리(조건 있음/없음), BETWEEN+OR, COUNT, 복합 ORDER BY, 단일 컬럼 프로젝션을 순서대로 확인한다.

```java
// 1. 기본 쿼리
CriteriaBuilder         cb   = em.getCriteriaBuilder();
CriteriaQuery<Customer> cq   = cb.createQuery(Customer.class);
Root<Customer>          root = cq.from(Customer.class);
cq.select(root).orderBy(cb.asc(root.get("id")));
List<Customer> result = em.createQuery(cq).getResultList();

// 2. 동적 쿼리
List<Predicate> predicates = new ArrayList<>();
if (city != null) predicates.add(cb.equal(root.get("city"), city));
if (name != null) predicates.add(cb.like(root.get("name"), "%" + name + "%"));
if (!predicates.isEmpty()) {
  cq.where(cb.and(predicates.toArray(new Predicate[0])));
}

// 3. COUNT
CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
countCq.select(cb.count(countCq.from(Customer.class)));
Long count = em.createQuery(countCq).getSingleResult();
```

> **QueryDSL**: Criteria API의 장황함을 해소한 오픈소스 라이브러리다. 엔티티를 기반으로 Q클래스를 생성하여 `QCustomer.customer.city.eq("서울")` 처럼 간결하고 타입 안전한 쿼리를 작성할 수 있다.

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam19.App
```
