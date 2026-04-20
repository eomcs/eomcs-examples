# exam21 - Spring Data JPA 기초

## 개념

### Spring Data JPA란

JPA(EntityManager)를 직접 다루는 반복적인 코드를 제거하고, **인터페이스 선언만으로 리포지토리를 자동 구현**하는 Spring 하위 프로젝트다.

```
개발자가 선언:  interface CustomerRepository extends JpaRepository<Customer, Long>
Spring이 생성:  런타임에 프록시 구현체 자동 생성 → save / findById / findAll 등 제공
```

### JpaRepository 상속 계층

```
Repository
 └── CrudRepository<T, ID>   : save / findById / findAll / delete / count / existsById
      └── PagingAndSortingRepository<T, ID> : findAll(Sort) / findAll(Pageable)
           └── JpaRepository<T, ID>          : flush / saveAllAndFlush / deleteInBatch 등 JPA 전용 추가
```

### Spring Data JPA 설정 (Spring Boot 없이)

| 필수 빈 | 클래스 | 역할 |
|---|---|---|
| `DataSource` | `HikariDataSource` | DB 연결 풀 |
| `EntityManagerFactory` | `LocalContainerEntityManagerFactoryBean` | JPA 영속성 단위 |
| `TransactionManager` | `JpaTransactionManager` | 트랜잭션 관리 |

`@EnableJpaRepositories(basePackages = "...")` : 지정 패키지에서 Repository 인터페이스를 탐색해 구현체 생성  
`@EnableTransactionManagement` : `@Transactional` AOP 활성화

### 파생 쿼리(Derived Query Method)

메서드 이름을 파싱해 자동으로 JPQL을 생성한다.

| 메서드 이름 | 생성되는 JPQL |
|---|---|
| `findByCity(city)` | `WHERE c.city = :city` |
| `findByCityOrderByNameAsc(city)` | `WHERE c.city = :city ORDER BY c.name ASC` |
| `findByEmail(email)` | `WHERE c.email = :email` |
| `findByNameContaining(kw)` | `WHERE c.name LIKE '%:kw%'` |
| `countByCity(city)` | `SELECT COUNT(c) WHERE c.city = :city` |
| `existsByEmail(email)` | `SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END WHERE c.email = :email` |

### save() 동작 원리

```
save(entity) {
  if (entity.id == null) → em.persist(entity)   // INSERT
  else                   → em.merge(entity)      // UPDATE
}
```

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `JpaRepository<T, ID>` | CRUD + 페이징 + 정렬 기본 제공 |
| 파생 쿼리 | 메서드 이름 → JPQL 자동 생성 (SQL 작성 불필요) |
| `Optional<T>` 반환 | `findById` 등 단건 조회는 Optional 반환 → NPE 방지 |
| `Sort.by()` | `findAll(Sort)` 로 동적 정렬 지정 |
| Spring 컨텍스트 | `AnnotationConfigApplicationContext` → Boot 없이 사용 가능 |

---

## App - 기본 CRUD

`save()` / `findById()` / `findAll()` / `deleteById()` / `count()` / `existsById()` 동작을 확인한다.

```java
CustomerRepository repo = ctx.getBean(CustomerRepository.class);

// INSERT
Customer saved = repo.save(customer);

// SELECT WHERE id = ?
Optional<Customer> found = repo.findById(saved.getId());

// SELECT *
List<Customer> all = repo.findAll();

// UPDATE: 조회 후 필드 변경 → save() 재호출
Customer toUpdate = repo.findById(id).orElseThrow();
toUpdate.setCity("부산");
repo.save(toUpdate);

// DELETE WHERE id = ?
repo.deleteById(id);
```

- `save()`는 id가 `null`이면 `em.persist()` (INSERT), id가 이미 있으면 `em.merge()` (UPDATE)를 내부적으로 호출한다.
- `findById()`는 `Optional<T>`를 반환하므로 결과가 없을 때도 NPE 없이 `.ifPresent()` / `.orElseThrow()` 등으로 처리할 수 있다.
- 수정(UPDATE)을 위한 별도 메서드는 없다. **조회 → 필드 변경 → `save()` 재호출** 패턴으로 UPDATE가 수행된다.
- `existsById()`는 `SELECT COUNT(*) > 0` 쿼리를 실행해 해당 id의 레코드 존재 여부를 `boolean`으로 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App
  ```

---

## App2 - 파생 쿼리 & Sort

파생 쿼리 메서드와 `Sort` 객체로 동적 정렬을 확인한다.

```java
// WHERE city = '서울'
repo.findByCity("서울");

// WHERE city = '서울' ORDER BY name ASC
repo.findByCityOrderByNameAsc("서울");

// WHERE name LIKE '%길%'
repo.findByNameContaining("길");

// SELECT COUNT(*) WHERE city = '서울'
repo.countByCity("서울");

// ORDER BY name ASC
repo.findAll(Sort.by("name").ascending());

// ORDER BY city DESC, name ASC
repo.findAll(Sort.by("city").descending().and(Sort.by("name")));
```

- 파생 쿼리는 메서드 이름 규칙(`findBy{필드}`, `countBy{필드}`, `existsBy{필드}` 등)에 따라 JPQL이 자동 생성되므로 SQL을 직접 작성하지 않아도 된다.
- `findByEmail()`처럼 결과가 0 또는 1건인 조회는 `Optional<T>`로 반환해 값이 없는 경우를 안전하게 처리한다.
- `Containing` 키워드는 `LIKE '%?%'` 조건을 생성한다. `StartingWith`는 `LIKE '?%'`, `EndingWith`는 `LIKE '%?'`에 해당한다.
- `findAll(Sort.by(...))`는 런타임에 정렬 기준을 동적으로 지정할 수 있어, 메서드 이름에 `OrderBy`를 고정하는 것보다 유연하다.
- `.and(Sort.by(...))`를 체이닝하면 복합 정렬(다중 컬럼)을 간결하게 표현할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App2
  ```
