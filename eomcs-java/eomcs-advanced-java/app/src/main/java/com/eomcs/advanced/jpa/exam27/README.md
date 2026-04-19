# exam27 - 지연 로딩(Lazy) vs 즉시 로딩(Eager)

## 개념

### FetchType.LAZY (지연 로딩)

연관 데이터를 실제로 접근하는 순간까지 로드를 미룬다.

```java
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)  // @OneToMany 기본값
private List<Order> orders;

@ManyToOne(fetch = FetchType.LAZY)  // 기본값은 EAGER이지만 성능상 LAZY 권장
private Customer customer;
```

- 프록시 객체가 자리를 차지하다가 최초 접근 시 SELECT 실행
- **트랜잭션 안에서만 정상 동작**
- 트랜잭션 밖에서 접근 → `LazyInitializationException`

### FetchType.EAGER (즉시 로딩)

부모 엔티티 로드 시 연관 데이터를 항상 JOIN하여 함께 가져온다.

```java
@ManyToOne(fetch = FetchType.EAGER)  // @ManyToOne, @OneToOne 기본값
private Customer customer;
```

- 조회할 때마다 연관 데이터를 항상 로드 → 사용하지 않아도 쿼리 실행
- **`@OneToMany` EAGER는 N+1 문제를 심화시킬 수 있어 비권장**

### 기본값 정리

| 어노테이션 | 기본 FetchType | 권장 FetchType |
|---|---|---|
| `@OneToMany` | `LAZY` | `LAZY` (기본값 유지) |
| `@ManyToMany` | `LAZY` | `LAZY` (기본값 유지) |
| `@ManyToOne` | `EAGER` | **`LAZY`** (변경 권장) |
| `@OneToOne` | `EAGER` | **`LAZY`** (변경 권장) |

### LazyInitializationException

LAZY 로딩 프록시가 Hibernate 세션(트랜잭션) 밖에서 초기화되려 할 때 발생한다.

```java
// 트랜잭션 없는 메서드에서 반환된 엔티티
Customer detached = svc.findByIdDetached(1L);

detached.getOrders().size();  // 세션 닫힘 → LazyInitializationException!
```

**해결법:**
1. 트랜잭션 안에서 미리 초기화: `c.getOrders().size()`
2. JOIN FETCH / `@EntityGraph`로 즉시 로딩 (exam26 참고)
3. DTO로 필요한 데이터만 조회

### OSIV (Open Session In View)

HTTP 요청 시작부터 종료(View 렌더링 완료)까지 Hibernate 세션을 열어 두는 패턴이다.

```
HTTP 요청 시작
  │ 세션 오픈 (OSIV)
  ├─ Controller
  ├─ Service  (@Transactional 범위)
  ├─ Repository
  ├─ View 렌더링  ← LAZY 로딩 허용 (세션 열려 있음)
  │ 세션 종료
HTTP 응답 완료
```

| 구분 | OSIV ON | OSIV OFF |
|---|---|---|
| LAZY 접근 가능 범위 | 요청 전체 | `@Transactional` 범위만 |
| 커넥션 점유 | 요청 전체 동안 | 트랜잭션 동안만 |
| 성능 | 커넥션 오래 점유 | 커넥션 빨리 반환 |
| Spring Boot 기본값 | `true` (ON) | - |

> 비웹 환경(이 예제): 세션 범위 = `@Transactional` 범위

---

## 사용 테이블

```
shop_customer  ← Customer 엔티티
shop_orders    ← Order 엔티티 (customer_id FK)
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `LAZY` | 실제 접근 시 SELECT, 트랜잭션 안에서만 동작 |
| `EAGER` | 항상 JOIN 로드, `@ManyToOne` 기본값 |
| `LazyInitializationException` | 세션 밖에서 LAZY 필드 접근 시 발생 |
| OSIV | 세션을 요청 전체 동안 유지하는 패턴 |
| 권장 | 모든 연관관계를 `LAZY`로 설정, 필요 시 JOIN FETCH |

---

## App - LAZY 로딩 동작 및 LazyInitializationException 데모

트랜잭션 안/밖에서의 LAZY 로딩 동작 차이를 확인한다.

```java
// 1. 트랜잭션 안 → 정상 동작
svc.printOrdersInsideTransaction();

// 2. 트랜잭션 안에서 미리 초기화 → 트랜잭션 밖에서도 사용 가능
List<Customer> list = svc.findAllInitialized();

// 3. 트랜잭션 밖 → LazyInitializationException
Customer c = svc.findByIdDetached(1L);
c.getOrders().size();  // 예외 발생!
```

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam27.App
```
