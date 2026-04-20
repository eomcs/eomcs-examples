# exam25 - Auditing & @EnableJpaAuditing

## 개념

### JPA Auditing이란

엔티티 생성/수정 시 **타임스탬프와 사용자 정보를 자동으로 채워주는** 기능이다.  
개발자가 `setCreatedAt(LocalDateTime.now())` 같은 반복 코드를 작성할 필요가 없다.

### 활성화 방법

```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig { ... }
```

### 어노테이션

| 어노테이션 | 적용 대상 | 자동 설정 시점 |
|---|---|---|
| `@CreatedDate` | `LocalDateTime` 필드 | `persist` 시 현재 시각 |
| `@LastModifiedDate` | `LocalDateTime` 필드 | `persist` / `merge` 시 현재 시각 |
| `@CreatedBy` | `String` 등 필드 | `persist` 시 `AuditorAware.getCurrentAuditor()` 반환값 |
| `@LastModifiedBy` | `String` 등 필드 | `persist` / `merge` 시 `AuditorAware.getCurrentAuditor()` 반환값 |

### BaseEntity (@MappedSuperclass)

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)  // INSERT 시만 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
```

- `@MappedSuperclass`: 테이블 미생성, 자식 엔티티에 컬럼 매핑 상속
- `@EntityListeners(AuditingEntityListener.class)`: JPA 이벤트를 Auditing 리스너에 연결
- `updatable = false`: `@CreatedDate` / `@CreatedBy`는 최초 삽입 후 변경 불가

### AuditorAware

`@CreatedBy` / `@LastModifiedBy`에 채울 "현재 사용자"를 반환한다.

```java
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 웹 환경: SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of("system-user");
    }
}
```

### 동작 흐름

```
save(entity)
  → @PrePersist 이벤트 → AuditingEntityListener
    → @CreatedDate      = LocalDateTime.now()
    → @CreatedBy        = AuditorAware.getCurrentAuditor()
    → @LastModifiedDate = LocalDateTime.now()
    → @LastModifiedBy   = AuditorAware.getCurrentAuditor()

save(entity) [UPDATE]
  → @PreUpdate 이벤트 → AuditingEntityListener
    → @LastModifiedDate = LocalDateTime.now()   (갱신)
    → @LastModifiedBy   = AuditorAware.getCurrentAuditor() (갱신)
    → @CreatedDate      = 변경 없음 (updatable=false)
    → @CreatedBy        = 변경 없음 (updatable=false)
```

---

## 사용 테이블

```
exam25_customer  ← Customer 엔티티 (hbm2ddl.auto=create-drop 으로 자동 생성/삭제)
```

> `shop_customer` 테이블에는 `created_by` / `updated_by` 컬럼이 없어  
> exam25에서는 별도 테이블(`exam25_customer`)을 자동 생성해 사용한다.

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@EnableJpaAuditing` | Auditing 기능 활성화, `auditorAwareRef`로 AuditorAware 빈 지정 |
| `@MappedSuperclass` | DB 테이블 없이 자식 엔티티에 매핑 정보 상속 |
| `@EntityListeners` | JPA 이벤트를 Auditing 리스너에 연결 |
| `@CreatedDate` | INSERT 시 현재 시각 자동 설정 |
| `@LastModifiedDate` | INSERT/UPDATE 시 현재 시각 자동 갱신 |
| `@CreatedBy` | INSERT 시 사용자 자동 설정 (`updatable=false`) |
| `@LastModifiedBy` | INSERT/UPDATE 시 사용자 자동 갱신 |
| `AuditorAware<T>` | 현재 사용자 반환 인터페이스 (Spring Security 연동 가능) |

---

## App - Auditing 자동 설정 데모

INSERT 시 4개 필드 자동 설정, UPDATE 시 `@LastModifiedDate`/`@LastModifiedBy` 자동 갱신을 확인한다.

```java
// INSERT - createdAt, updatedAt, createdBy, updatedBy 자동 설정
Customer c = new Customer();
c.setName("감사테스터");
c.setEmail("audit@test.com");
Customer saved = repo.save(c);  // setCreatedAt() 없이도 자동 채워짐

System.out.println(saved.getCreatedAt());   // 자동 설정됨
System.out.println(saved.getCreatedBy());   // "system-user" (AuditorAware 반환값)

// UPDATE - updatedAt, updatedBy만 갱신
Customer toUpdate = repo.findById(id).orElseThrow();
toUpdate.setCity("부산");
Customer updated = repo.save(toUpdate);

// createdAt == 이전 그대로, updatedAt == 갱신됨
```

- `setCreatedAt()`을 호출하지 않아도 `save()` 시 `@CreatedDate`가 현재 시각을 자동으로 채운다. `@CreatedBy`도 마찬가지로 `AuditorAware.getCurrentAuditor()` 반환값이 자동으로 설정된다.
- UPDATE 시에는 `@LastModifiedDate`와 `@LastModifiedBy`만 갱신되고, `@CreatedDate`와 `@CreatedBy`는 `updatable = false`로 선언되어 있어 변경되지 않는다.
- `AuditorAware`의 `getCurrentAuditor()`는 웹 환경에서 `SecurityContextHolder`로 로그인 사용자를 반환하도록 연동한다. 이 예제에서는 단순히 `"system-user"` 문자열을 반환한다.
- `@EntityListeners(AuditingEntityListener.class)`가 `BaseEntity`에 선언되어 있어, 이를 상속한 모든 엔티티에 Auditing이 자동으로 적용된다.
- 이 예제는 `shop_customer` 대신 `exam25_customer` 테이블을 `hbm2ddl.auto=create-drop`으로 자동 생성해 사용한다. `shop_customer`에 `created_by` / `updated_by` 컬럼이 없기 때문이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam25.App
  ```
