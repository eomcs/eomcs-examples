# exam23 - Querydsl 기초

## 개념

### Querydsl이란

JPQL·SQL을 Java 코드로 작성하는 타입 안전(type-safe) 쿼리 라이브러리다.

| 비교 | JPQL | Querydsl |
|---|---|---|
| 작성 방식 | 문자열 | Java 메서드 체인 |
| 오타 발견 | 런타임 | 컴파일 타임 |
| 동적 쿼리 | 문자열 연결 (복잡) | BooleanBuilder (간결) |
| IDE 지원 | 없음 | 자동완성·리팩토링 지원 |

### Q-타입 (Q-Type)

APT(Annotation Processing Tool)가 `@Entity` 클래스를 읽어 자동 생성하는 메타 클래스다.

```java
// APT가 Customer.java → QCustomer.java 자동 생성
public class QCustomer extends EntityPathBase<Customer> {
    public static final QCustomer customer = new QCustomer("customer");
    public final StringPath name  = createString("name");
    public final StringPath city  = createString("city");
    public final NumberPath<Long> id = createNumber("id", Long.class);
    ...
}
```

#### APT 설정 (Gradle)

```groovy
annotationProcessor "com.querydsl:querydsl-apt:${queryDslVersion}:jakarta"
annotationProcessor "jakarta.persistence:jakarta.persistence-api:3.1.0"
// → ./gradlew compileJava 실행 시 Q-타입 자동 생성
```

> 이 예제의 `QCustomer` · `QProduct`는 학습용 수동 작성본이다.

### JPAQueryFactory

Querydsl 쿼리의 진입점이다.

```java
JPAQueryFactory factory = new JPAQueryFactory(entityManager);

// SELECT c FROM Customer c WHERE c.city = '서울' ORDER BY c.name ASC
factory.selectFrom(QCustomer.customer)
       .where(QCustomer.customer.city.eq("서울"))
       .orderBy(QCustomer.customer.name.asc())
       .fetch();
```

### 주요 메서드

| 메서드 | 설명 |
|---|---|
| `eq(v)` | = 조건 |
| `ne(v)` | != 조건 |
| `contains(v)` | LIKE '%v%' |
| `startsWith(v)` | LIKE 'v%' |
| `gt(v)` / `lt(v)` | > / < 조건 |
| `goe(v)` / `loe(v)` | >= / <= 조건 |
| `between(a, b)` | BETWEEN a AND b |
| `and(pred)` / `or(pred)` | 복합 조건 |
| `fetch()` | List\<T\> 반환 |
| `fetchOne()` | 단건 반환 (없으면 null, 둘 이상이면 예외) |
| `fetchFirst()` | 첫 번째 결과만 반환 |

### BooleanBuilder - 동적 쿼리

```java
BooleanBuilder builder = new BooleanBuilder();

if (city != null)    builder.and(c.city.eq(city));
if (keyword != null) builder.and(c.name.contains(keyword));

factory.selectFrom(c).where(builder).fetch();
```

- `null` 파라미터는 그냥 추가하지 않으면 되므로 `if` 분기가 단순해진다
- Criteria API 대비 훨씬 간결한 동적 쿼리 구성

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_product   ← Product 엔티티
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| Q-타입 | APT 자동 생성 메타 클래스, 타입 안전 필드 참조 |
| `JPAQueryFactory` | Querydsl 쿼리 진입점 |
| `selectFrom(Q)` | FROM 절 지정 |
| `where(조건)` | WHERE 절 - 타입 안전 조건 |
| `BooleanBuilder` | 조건 동적 누적 → 동적 쿼리 핵심 |
| `fetch()` | 결과 목록 반환 |

---

## App - JPAQueryFactory 기본 쿼리

`selectFrom`, `where(eq/contains/gt)`, `orderBy`, `limit`, `fetchOne`을 확인한다.

```java
JPAQueryFactory factory = new JPAQueryFactory(em);
QCustomer c = QCustomer.customer;

// 기본 조회
factory.selectFrom(c).fetch();

// 조건
factory.selectFrom(c).where(c.city.eq("서울")).fetch();

// 복합 조건
factory.selectFrom(p)
       .where(p.dtype.eq("DIGITAL").and(p.price.lt(new BigDecimal("100000"))))
       .fetch();
```

- Q-타입(`QCustomer`, `QProduct`)은 `@Entity` 클래스를 기반으로 APT가 자동 생성하는 메타 클래스다. 필드를 문자열 대신 타입 안전한 객체로 참조하므로 오타가 컴파일 타임에 발견된다.
- `selectFrom(Q타입)`은 FROM 절을 지정한다. `select()`와 `from()`을 분리해서도 사용할 수 있다.
- `where()` 안에 `eq`, `contains`, `gt`, `lt`, `between` 등 조건 메서드를 체이닝하면 JPQL의 WHERE 절이 생성된다.
- `.and()`와 `.or()`로 여러 조건을 조합할 수 있다. 이 조합이 메서드 체인으로 표현되므로 JPQL 문자열 연결보다 가독성이 높다.
- `fetchOne()`은 단건을 반환하며, 결과가 없으면 `null`, 결과가 둘 이상이면 예외를 발생시킨다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App
  ```

---

## App2 - BooleanBuilder 동적 쿼리

파라미터 유무에 따라 조건을 동적으로 조합하는 검색 메서드를 확인한다.

```java
BooleanBuilder builder = new BooleanBuilder();
if (city    != null) builder.and(c.city.eq(city));
if (keyword != null) builder.and(c.name.contains(keyword));
factory.selectFrom(c).where(builder).fetch();
```

- `BooleanBuilder`는 조건(`Predicate`)을 `.and()`/`.or()`로 동적으로 누적하는 빌더다. 조건이 하나도 없으면 WHERE 절이 생성되지 않아 전체 조회가 된다.
- `null`인 파라미터는 `if` 분기로 건너뛰기만 하면 되므로, JPA Criteria API의 복잡한 Predicate 배열 처리보다 훨씬 간결하다.
- 같은 `searchCustomers()` 메서드에 city만 전달하거나, 둘 다 전달하거나, 둘 다 `null`로 전달하면 각각 다른 WHERE 절이 만들어진다. 하나의 메서드로 다양한 검색 조건을 처리할 수 있다.
- 가격 범위 조건에서 `goe()`는 `>=`, `loe()`는 `<=`를 의미한다. 최솟값이나 최댓값이 `null`이면 해당 경계 조건이 자동으로 제외된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App2
  ```
