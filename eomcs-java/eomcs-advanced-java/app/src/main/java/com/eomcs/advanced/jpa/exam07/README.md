# Exam07 - 기본 엔티티 매핑

## 개념

### @Entity

JPA가 관리할 클래스임을 선언한다.

- `public` 또는 `protected` 기본 생성자가 반드시 있어야 한다 (JPA가 리플렉션으로 객체를 생성하기 때문).
- `final` 클래스, `enum`, `interface`는 엔티티가 될 수 없다.
- 필드를 `final`로 선언하면 안 된다.

### @Table

매핑할 테이블을 지정한다. 생략하면 클래스명을 테이블명으로 사용한다.

```java
@Entity
@Table(name = "shop_customer")  // 매핑 테이블: shop_customer
public class Customer { ... }
```

### @Id & @GeneratedValue

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

| 전략 | 설명 | Oracle 대응 |
|---|---|---|
| `IDENTITY` | DB의 IDENTITY 기능 사용 | `GENERATED ALWAYS AS IDENTITY` |
| `SEQUENCE` | DB 시퀀스 사용 | `CREATE SEQUENCE` |
| `AUTO` | Hibernate가 DB에 맞게 자동 선택 | DB 방언에 따라 결정 |
| `TABLE` | 별도 키 생성 테이블 사용 | 거의 사용 안 함 |

`IDENTITY` 전략: `persist()` 호출 시 즉시 INSERT → DB가 id를 채번 → 객체에 반영.

### @Column

| 속성 | 설명 | 기본값 |
|---|---|---|
| `name` | DB 컬럼명 (Java 필드명과 다를 때 명시) | 필드명 |
| `nullable` | NOT NULL 제약 | `true` |
| `length` | VARCHAR 길이 | 255 |
| `precision` | NUMBER 전체 자릿수 | 0 |
| `scale` | NUMBER 소수점 자릿수 | 0 |
| `unique` | UNIQUE 제약 | `false` |
| `insertable` | INSERT 시 포함 여부 | `true` |
| `updatable` | UPDATE 시 포함 여부 | `true` |

```java
@Column(name = "email", nullable = false, length = 200, unique = true)
private String email;

// BigDecimal: 금액처럼 정밀도가 중요한 숫자에 사용 (double은 이진 오차 위험)
@Column(name = "price", nullable = false, precision = 12, scale = 2)
private BigDecimal price;

// Java LocalDateTime ↔ Oracle TIMESTAMP 자동 변환
@Column(name = "created_at")
private LocalDateTime createdAt;
```

### JPQL 기초

JPQL은 테이블·컬럼명이 아닌 **엔티티 클래스명·필드명**을 사용한다.

```
SQL  : SELECT id, name, city FROM shop_customer WHERE city = '서울' ORDER BY id
JPQL : SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id
```

| 구분 | SQL | JPQL |
|---|---|---|
| 조회 대상 | 테이블 (행/컬럼) | 엔티티 (객체) |
| 클래스/테이블 | 테이블명 | 엔티티 클래스명 |
| 컬럼 | 컬럼명 | Java 필드명 |
| 파라미터 | `?` (위치 기반) | `:이름` (이름 기반) |

---

## App - 기본 CRUD (persist, find, 변경 감지, remove)

엔티티를 저장·조회·수정·삭제하는 전체 흐름을 확인한다.

```java
// INSERT: persist() + commit()
Customer c = new Customer();
c.setName("JPA테스터");
c.setEmail("jpa@test.com");
c.setCity("서울");
em.persist(c);
tx.commit();            // → INSERT INTO shop_customer ...
Long id = c.getId();   // IDENTITY 전략: persist() 직후 id 채번

// SELECT: find(클래스, 기본 키)
Customer found = em.find(Customer.class, id);

// UPDATE: find() 후 필드 수정 → commit() 시 자동 UPDATE (별도 update() 없음)
Customer managed = em.find(Customer.class, id);
managed.setCity("부산");
tx.commit();            // → UPDATE shop_customer SET city=? WHERE id=?

// DELETE: find() → remove() → commit()
Customer toDelete = em.find(Customer.class, id);
em.remove(toDelete);
tx.commit();            // → DELETE FROM shop_customer WHERE id=?
```

- `IDENTITY` 전략(`GENERATED ALWAYS AS IDENTITY`)을 사용하면 `persist()` 호출 즉시 INSERT가 실행되고 DB가 id를 채번하여 엔티티 필드에 반영한다. 따라서 `persist()` 직후 `c.getId()`로 생성된 id를 바로 확인할 수 있다.
- UPDATE는 별도의 `update()` 메서드가 없다. `find()`로 영속 상태가 된 엔티티의 필드를 수정하면 `commit()` 시 변경 감지(Dirty Checking)가 작동해 UPDATE SQL이 자동으로 실행된다.
- DELETE는 `find()`로 영속 상태의 엔티티를 얻은 뒤 `remove()`로 삭제 예약을 하고, `commit()` 시 DELETE SQL이 실행된다. 준영속(detached) 상태의 엔티티는 `remove()`에 전달할 수 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App
  ```

---

## App2 - JPQL 조건 조회 & 집계

JPQL로 `WHERE`, `LIKE`, `ORDER BY`, `COUNT`, `AVG`를 사용한다.

```java
// 전체 조회: ORDER BY c.id (Java 필드명)
List<Customer> all = em
    .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
    .getResultList();

// 조건 조회: :파라미터명 바인딩
List<Customer> seoulList = em
    .createQuery("SELECT c FROM Customer c WHERE c.city = :city", Customer.class)
    .setParameter("city", "서울")
    .getResultList();

// LIKE 조회
List<Customer> likeList = em
    .createQuery("SELECT c FROM Customer c WHERE c.name LIKE :pattern", Customer.class)
    .setParameter("pattern", "홍%")
    .getResultList();

// 집계: COUNT → Long, AVG → Double
Long count = em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
Double avg  = em.createQuery("SELECT AVG(p.price) FROM Product p", Double.class).getSingleResult();
```

- `createQuery(jpql, 반환타입.class)` → `TypedQuery<T>` (컴파일 시 타입 확인).
- `getResultList()` → `List<T>` (결과 0건이면 빈 리스트, null 아님).
- `getSingleResult()` → `T` (정확히 1건, 0건이거나 2건 이상이면 예외).
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App2
  ```
