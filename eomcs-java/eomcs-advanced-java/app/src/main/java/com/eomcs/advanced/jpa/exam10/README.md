# Exam10 - 엔티티 생명주기

## 개념

### 엔티티의 4가지 상태

```
                  persist()
[비영속(New)] ──────────────→ [영속(Managed)]
                                    │  │
                  find()/merge()     │  │  detach()
                  ←────────────────  │  └──────────→ [준영속(Detached)]
                                    │                       │
                                    │  remove()             │  merge()
                                    ↓                       ↓
                             [삭제(Removed)]          [영속(Managed)]
                                    │
                              commit()
                                    ↓
                               DB DELETE
```

### 1. 비영속(New / Transient)

`new`로 생성했지만 영속성 컨텍스트와 연관이 없는 상태다.

```java
Customer c = new Customer();  // 비영속
c.setName("홍길동");
// DB와 무관. id = null. Hibernate가 관리하지 않음.
```

### 2. 영속(Managed)

영속성 컨텍스트가 관리하는 상태다.

```java
em.persist(c);              // 비영속 → 영속
Customer c = em.find(Customer.class, 1L);  // DB 조회 → 영속
Customer c = em.merge(detached);           // 준영속 → 영속 (새 인스턴스)

em.contains(c);  // true
```

- 1차 캐시에 저장됨.
- 변경 감지(Dirty Checking) 작동.
- `commit()` 시 변경 내용이 DB에 반영됨.

### 3. 준영속(Detached)

한때 영속이었지만 영속성 컨텍스트에서 분리된 상태다.

```java
em.detach(c);   // 특정 엔티티를 준영속으로
em.clear();     // 영속성 컨텍스트 전체 초기화 → 모든 엔티티 준영속
em.close();     // EM 닫기 → 모든 엔티티 준영속
```

- `em.contains(c)` → `false`
- 변경 감지 작동 안 함 → 필드 수정이 DB에 반영되지 않음.
- `merge()`로 다시 영속 상태로 만들 수 있음.

### 4. 삭제(Removed)

삭제 예약 상태. `commit()` 시 DELETE SQL이 실행된다.

```java
em.remove(c);   // 삭제 예약 → em.contains(c) = false
tx.commit();    // DELETE SQL 실행
```

### 주요 메서드 정리

| 메서드 | 역할 | 상태 전환 |
|---|---|---|
| `persist(entity)` | 새 엔티티 저장 | 비영속 → 영속 |
| `find(Class, id)` | 기본 키로 조회 | → 영속 |
| `merge(entity)` | 준영속 엔티티 병합 | 준영속 → 영속 (새 인스턴스) |
| `remove(entity)` | 엔티티 삭제 예약 | 영속 → 삭제 |
| `detach(entity)` | 특정 엔티티 분리 | 영속 → 준영속 |
| `clear()` | 컨텍스트 초기화 | 모든 영속 → 준영속 |
| `flush()` | 변경 내용 DB 전송 | 상태 변경 없음 |
| `contains(entity)` | 영속 여부 확인 | - |

### merge() 주의사항

`merge()`는 넘겨받은 준영속 엔티티를 그대로 영속으로 바꾸지 않는다.
**새로운 영속 엔티티 인스턴스를 반환**한다.

```java
Customer detached = ...; // 준영속
detached.setCity("부산");

Customer merged = em.merge(detached); // 새 영속 인스턴스 반환
// merged: 영속 상태, em.contains(merged) = true
// detached: 여전히 준영속, em.contains(detached) = false

tx.commit(); // merged의 변경이 DB에 반영됨 (city='부산')
```

---

## App - 엔티티 생명주기 전체 흐름

비영속 → 영속 → 준영속 → 영속(merge) → 삭제 순서로 각 상태를 직접 확인한다.

```java
// 1. 비영속
Customer c = new Customer();   // id = null, 관리 안 됨

// 2. 영속 (persist)
em.persist(c);                 // em.contains(c) = true, id 채번
tx.commit();

// 3. 영속 (find)
Customer managed = em.find(Customer.class, id);  // DB SELECT → 영속

// 4. 준영속 (detach)
em.detach(managed);            // em.contains(managed) = false
managed.setCity("부산");       // 변경 감지 안 됨 → DB 미반영

// 5. 영속 복귀 (merge)
Customer merged = em.merge(c); // 준영속 → 영속 (새 인스턴스)
tx.commit();                   // merged의 변경 DB 반영

// 6. 컨텍스트 초기화 (clear)
em.clear();                    // 모든 엔티티 준영속
                               // 이후 find() → DB 재조회

// 7. 삭제 (remove)
Customer toRemove = em.find(Customer.class, id);
em.remove(toRemove);           // 삭제 예약
tx.commit();                   // DELETE 실행
em.find(Customer.class, id);   // null
```

- `em.contains(c)`로 해당 엔티티가 현재 영속성 컨텍스트에서 관리되는지 확인할 수 있다. 영속 상태이면 `true`, 비영속·준영속·삭제 상태이면 `false`를 반환한다.
- `detach()` 후 필드를 수정해도 Dirty Checking이 작동하지 않아 DB에 반영되지 않는다. 변경 내용을 DB에 반영하려면 반드시 `merge()`로 영속성 컨텍스트에 다시 등록해야 한다.
- `merge(detached)`는 넘겨받은 준영속 인스턴스를 그대로 영속으로 바꾸지 않고, DB에서 조회하거나 1차 캐시에서 꺼낸 **새로운 영속 인스턴스**를 반환한다. 반환된 인스턴스를 사용해야 한다.
- `clear()`는 영속성 컨텍스트 전체를 초기화한다. 이후 `find()`를 호출하면 1차 캐시가 비어 있으므로 DB SELECT가 다시 실행된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam10.App
  ```
