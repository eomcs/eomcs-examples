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

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam21.App2
```
