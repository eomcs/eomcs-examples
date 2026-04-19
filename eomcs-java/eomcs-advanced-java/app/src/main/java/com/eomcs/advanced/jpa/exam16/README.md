# exam16 - 엔티티 이벤트 & @EntityListeners

## 개념

### 엔티티 생명주기 콜백 이벤트

JPA는 엔티티의 INSERT·UPDATE·DELETE·SELECT 시점에 **콜백 메서드를 자동 호출**하는 훅(Hook) 메커니즘을 제공한다.

```
INSERT: @PrePersist  →  INSERT SQL  →  @PostPersist
UPDATE: @PreUpdate   →  UPDATE SQL  →  @PostUpdate
DELETE: @PreRemove   →  DELETE SQL  →  @PostRemove
SELECT:                              →  @PostLoad
```

콜백 메서드는 **엔티티 클래스 내부**에 직접 정의하거나, **별도 리스너 클래스**(`@EntityListeners`)에 정의하는 두 가지 방법이 있다.

### @EntityListeners

외부 리스너 클래스를 엔티티에 등록하는 어노테이션이다.

```java
@EntityListeners(AuditListener.class)  // 리스너 등록
public class Customer { ... }
```

- 리스너 클래스의 콜백 메서드는 `Object` 타입으로 엔티티를 받는다
- 여러 엔티티에 동일 리스너를 재사용할 수 있어 **공통 Auditing 로직을 한 곳에서 관리** 가능
- 내부 정의 방식(`@PrePersist void onPrePersist()`)보다 관심사 분리가 명확하다

### Auditing 자동화

`createdAt`·`updatedAt`을 애플리케이션 코드에서 매번 직접 설정하는 대신, 리스너가 이벤트 시점에 자동으로 채워준다.

```
em.persist(entity)  →  @PrePersist 호출  →  createdAt·updatedAt 자동 설정  →  INSERT SQL
tx.commit()         →  @PreUpdate 호출   →  updatedAt 자동 갱신             →  UPDATE SQL
```

> Spring Data JPA의 `@EnableJpaAuditing` / `@CreatedDate` / `@LastModifiedDate`는 이 원리를 추상화한 것이다.

---

## 사용 테이블

```
shop_customer
  created_at  TIMESTAMP  → @PrePersist에서 자동 설정
  updated_at  TIMESTAMP  → @PrePersist / @PreUpdate에서 자동 갱신
```

---

## 이벤트 호출 순서

```
INSERT: @PrePersist  →  INSERT SQL  →  @PostPersist
UPDATE: @PreUpdate   →  UPDATE SQL  →  @PostUpdate
DELETE: @PreRemove   →  DELETE SQL  →  @PostRemove
SELECT:                              →  @PostLoad
```

---

## 핵심 어노테이션

| 어노테이션 | 위치 | 설명 |
|---|---|---|
| `@EntityListeners` | 엔티티 클래스 | 리스너 클래스 등록 |
| `@PrePersist` | 리스너 메서드 | INSERT SQL 실행 전 |
| `@PostPersist` | 리스너 메서드 | INSERT SQL 실행 후 |
| `@PreUpdate` | 리스너 메서드 | UPDATE SQL 실행 전 |
| `@PostUpdate` | 리스너 메서드 | UPDATE SQL 실행 후 |
| `@PreRemove` | 리스너 메서드 | DELETE SQL 실행 전 |
| `@PostRemove` | 리스너 메서드 | DELETE SQL 실행 후 |
| `@PostLoad` | 리스너 메서드 | 엔티티 조회 후 |

---

## 엔티티 클래스 구조

### Auditable (인터페이스)

```java
public interface Auditable {
  void setCreatedAt(LocalDateTime v);
  void setUpdatedAt(LocalDateTime v);
}
```

- `AuditListener`가 `Object` 타입으로 엔티티를 받으므로 `createdAt`/`updatedAt` 필드에 접근하려면 공통 타입이 필요하다
- 리스너 적용 대상 엔티티는 이 인터페이스를 구현해야 한다

---

### AuditListener (엔티티 리스너)

```java
public class AuditListener {

  @PrePersist
  public void prePersist(Object entity) {
    if (entity instanceof Auditable auditable) {
      auditable.setCreatedAt(LocalDateTime.now());
      auditable.setUpdatedAt(LocalDateTime.now());
    }
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    if (entity instanceof Auditable auditable) {
      auditable.setUpdatedAt(LocalDateTime.now());
    }
  }
  // @PostPersist, @PostUpdate, @PreRemove, @PostRemove, @PostLoad 도 구현
}
```

| 어노테이션 | 호출 시점 | 활용 |
|---|---|---|
| `@PrePersist` | `em.persist()` 직후, INSERT SQL 전 | `createdAt`, `updatedAt` 자동 설정 |
| `@PreUpdate` | Dirty Checking 감지 후, UPDATE SQL 전 | `updatedAt` 자동 갱신 |
| `@PostPersist` | INSERT SQL 완료 후 | 로그, 이벤트 발행 |
| `@PostUpdate` | UPDATE SQL 완료 후 | 로그, 이벤트 발행 |
| `@PreRemove` | `em.remove()` 직후, DELETE SQL 전 | 삭제 전처리 |
| `@PostRemove` | DELETE SQL 완료 후 | 로그 |
| `@PostLoad` | `em.find()` 또는 JPQL 조회 후 | 조회 후처리 |

---

### Customer (엔티티)

```java
@Entity
@Table(name = "shop_customer")
@EntityListeners(AuditListener.class)
public class Customer implements Auditable {

  @Column(name = "created_at") private LocalDateTime createdAt;
  @Column(name = "updated_at") private LocalDateTime updatedAt;
}
```

| 어노테이션 | 의미 |
|---|---|
| `@EntityListeners(AuditListener.class)` | 엔티티 생명주기 이벤트 발생 시 `AuditListener` 메서드를 자동 호출 |

- `createdAt`/`updatedAt`을 애플리케이션 코드에서 직접 설정하지 않아도 `AuditListener`가 자동으로 관리
- `implements Auditable`: 리스너가 타입 캐스팅으로 `setCreatedAt()`/`setUpdatedAt()` 호출 가능

---

## App - 이벤트 리스너 동작 전체 흐름

INSERT → SELECT → UPDATE → DELETE 순서로 각 생명주기 이벤트가 자동 호출되는 것을 확인한다.

```java
// 1. INSERT: @PrePersist → INSERT SQL → @PostPersist
Customer customer = new Customer();
customer.setName("이지은");
// createdAt / updatedAt 미설정 → @PrePersist에서 자동 설정
em.persist(customer);  // ← @PrePersist 호출
tx.commit();           // ← @PostPersist 호출
customer.getCreatedAt(); // AuditListener가 설정한 값

// 2. SELECT: @PostLoad
Customer found = em.find(Customer.class, savedId); // ← @PostLoad 호출

// 3. UPDATE: @PreUpdate → UPDATE SQL → @PostUpdate
Customer managed = em.find(Customer.class, savedId);
managed.setCity("부산");
// updatedAt 미설정 → commit() 직전 @PreUpdate에서 자동 갱신
tx.commit(); // ← @PreUpdate → UPDATE SQL → @PostUpdate 순서로 실행

// 4. DELETE: @PreRemove → DELETE SQL → @PostRemove
em.remove(toRemove); // ← @PreRemove 호출
tx.commit();         // ← @PostRemove 호출
```

> Spring Data JPA의 `@EnableJpaAuditing` / `@CreatedDate` / `@LastModifiedDate`는 이 원리(`@EntityListeners`)를 추상화한 것이다.

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam16.App
```
