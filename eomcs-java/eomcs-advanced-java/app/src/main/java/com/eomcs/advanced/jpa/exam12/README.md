# exam12 - @Embeddable / @Embedded

## 개념

### 값 타입(Value Type)이란

JPA에서 데이터를 표현하는 방법은 엔티티 타입과 값 타입 두 가지다.

| 구분 | 엔티티 타입 | 값 타입 |
|---|---|---|
| 식별자(`@Id`) | 있음 | 없음 |
| 생명주기 | 독립적 | 소유 엔티티에 종속 |
| 테이블 | 별도 테이블 | 소유 엔티티 테이블에 합쳐짐 |
| 공유 | 가능 | 불가 (복사해서 사용) |

값 타입은 의미적으로 관련 있는 컬럼들을 하나의 객체로 묶어 **응집도를 높이고** 코드 가독성을 개선한다.

### @Embeddable / @Embedded

```
[Java]                      [DB - shop_customer 테이블]
Customer                      id
  - id          →             name
  - name        →             email
  - email       →             city       ┐
  - address     →             street     ├─ Address 필드가
      ∟ city                  zipcode    ┘  테이블에 인라인으로 저장
      ∟ street
      ∟ zipcode
```

- `@Embeddable`: 별도 테이블 없이 소유 엔티티 테이블에 컬럼으로 매핑되는 값 타입 클래스 선언
- `@Embedded`: 엔티티에서 값 타입 필드를 사용할 때 선언
- 소유 엔티티가 삭제되면 값 타입 데이터도 함께 사라진다
- `address = null`이면 `city`, `street`, `zipcode` 컬럼 모두 `NULL`로 저장된다

### @AttributeOverride

같은 `@Embeddable` 타입을 한 엔티티에서 여러 번 사용할 때 컬럼명 충돌을 해소한다.

```java
@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city", column = @Column(name = "home_city"))
})
private Address homeAddress;

@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city", column = @Column(name = "work_city"))
})
private Address workAddress;
```

---

## 사용 테이블

```
shop_customer
  - city, street, zipcode → Address 값 타입으로 그룹화
```

---

## 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@Embeddable` | 값 타입 클래스 | 별도 테이블 없이 소유 엔티티 테이블에 매핑되는 값 타입 선언 |
| `@Embedded` | 엔티티 필드 | 값 타입 필드 선언 |
| `@AttributeOverride` | 엔티티 필드 | 같은 값 타입을 재사용할 때 컬럼명 재정의 |

---

## 엔티티 vs 값 타입

| 구분 | 엔티티 | 값 타입 |
|---|---|---|
| 식별자(`@Id`) | 있음 | 없음 |
| 생명주기 | 독립적 | 소유 엔티티에 종속 |
| 테이블 | 별도 테이블 | 소유 엔티티 테이블에 합쳐짐 |

---

## 엔티티 클래스 구조

### Address (값 타입)

```java
@Embeddable
public class Address {
  @Column(length = 100) private String city;
  @Column(length = 200) private String street;
  @Column(length = 20)  private String zipcode;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@Embeddable` | 독립적인 생명주기가 없는 값 타입 선언. 자체 `@Id`가 없으며, 소유 엔티티(`Customer`)의 테이블 컬럼으로 매핑된다 |

- 별도 테이블이 생성되지 않는다 → `shop_customer` 테이블의 `city`, `street`, `zipcode` 컬럼으로 저장
- 소유 엔티티가 삭제되면 값 타입 데이터도 함께 삭제된다

---

### Customer (엔티티)

```java
@Entity
@Table(name = "shop_customer")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private Address address;   // city, street, zipcode → shop_customer 테이블에 저장
}
```

| 어노테이션 | 의미 |
|---|---|
| `@Embedded` | `@Embeddable`로 정의된 값 타입 필드를 소유 엔티티 테이블에 인라인으로 저장 |

- `address`가 `null`이면 `city`, `street`, `zipcode` 컬럼이 모두 `NULL`로 저장된다
- `@AttributeOverride`를 사용하면 같은 `Address` 타입을 여러 필드(예: `homeAddress`, `workAddress`)에 재사용할 때 컬럼명을 재정의할 수 있다

```java
// @AttributeOverride 사용 예 (이 예제에서는 미사용)
@Embedded
@AttributeOverrides({
  @AttributeOverride(name = "city",    column = @Column(name = "home_city")),
  @AttributeOverride(name = "street",  column = @Column(name = "home_street")),
  @AttributeOverride(name = "zipcode", column = @Column(name = "home_zipcode"))
})
private Address homeAddress;
```

---

## App - @Embedded 저장/조회/수정/JPQL 전체 흐름

`Address` 값 타입이 포함된 `Customer`를 저장하고, 조회·수정·JPQL 조건 검색 순서로 `@Embedded` 동작을 확인한다.

```java
// 1. Customer + Address 저장
Customer customer = new Customer();
customer.setName("박지수");
customer.setAddress(new Address("인천", "송도대로 77", "21999"));
em.persist(customer);
tx.commit();
// INSERT INTO shop_customer (name, email, city, street, zipcode, ...)
// → Address 필드가 shop_customer 테이블 컬럼으로 그대로 저장된다

// 2. 조회 후 Address 접근
Customer found = em.find(Customer.class, savedId);
found.getAddress().getCity();   // "인천"
found.getAddress().getStreet(); // "송도대로 77"

// 3. Address 수정 → Dirty Checking으로 UPDATE 자동 실행
Customer managed = em.find(Customer.class, savedId);
managed.setAddress(new Address("서울", "강남대로 100", "06000"));
tx.commit();
// UPDATE shop_customer SET city='서울', street='강남대로 100', zipcode='06000' WHERE id=?

// 4. Address = null 저장 → 컬럼 전체 NULL
Customer noAddr = new Customer();
noAddr.setName("주소없는고객");
// address 미설정 → city/street/zipcode 모두 NULL

// 5. JPQL에서 임베디드 필드 접근: '엔티티필드.임베디드필드' 경로 표현식 사용
List<Customer> result = em.createQuery(
    "SELECT c FROM Customer c WHERE c.address.city = :city", Customer.class)
    .setParameter("city", "서울")
    .getResultList();
// → 내부적으로 WHERE city = '서울' 로 변환
```

- `@Embedded` 필드(`address`)는 별도 테이블이 생성되지 않는다. `Address`의 `city`, `street`, `zipcode` 컬럼이 `shop_customer` 테이블에 그대로 저장된다.
- `address = null`로 설정하면 `city`, `street`, `zipcode` 컬럼이 모두 `NULL`로 저장된다. 이 상태에서 `getAddress()`를 호출하면 `null`이 반환되므로 NPE에 주의해야 한다.
- Address 수정은 새 `Address` 객체를 `setAddress()`로 교체하거나 기존 객체의 필드를 수정하면 된다. 어느 방식이든 Dirty Checking이 작동해 `commit()` 시 해당 컬럼들이 UPDATE된다.
- JPQL에서 임베디드 필드는 `c.address.city`처럼 **점 경로 표현식**으로 접근한다. Hibernate가 이를 컬럼명(`city`)으로 변환해 WHERE 절에 적용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam12.App
  ```
