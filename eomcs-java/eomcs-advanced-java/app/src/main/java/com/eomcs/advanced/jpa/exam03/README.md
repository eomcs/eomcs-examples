# Exam03 - Transaction 관리

## 개념

### 트랜잭션(Transaction)

하나의 논리적 작업 단위를 구성하는 SQL 묶음이다.
ACID 특성을 보장한다.

| 특성 | 설명 |
|---|---|
| **원자성(Atomicity)** | 묶음 안의 SQL은 모두 성공하거나 모두 취소된다 |
| **일관성(Consistency)** | 트랜잭션 전후 DB는 항상 일관된 상태를 유지한다 |
| **격리성(Isolation)** | 동시 실행 트랜잭션은 서로의 중간 결과를 볼 수 없다 |
| **지속성(Durability)** | commit된 결과는 장애가 발생해도 영구적으로 유지된다 |

### AutoCommit

JDBC `Connection`의 기본 `AutoCommit` 값은 `true`이다.
`AutoCommit=true`이면 SQL 실행 즉시 자동으로 `commit`된다.
트랜잭션을 직접 관리하려면 `setAutoCommit(false)`로 변경해야 한다.

```
AutoCommit=true  : SQL 실행 → 즉시 commit (트랜잭션 경계 없음)
AutoCommit=false : conn.commit() 또는 conn.rollback()을 명시 호출해야 반영·취소됨
```

### commit / rollback

| 메서드 | 설명 |
|---|---|
| `conn.setAutoCommit(false)` | 트랜잭션 시작 |
| `conn.commit()` | 트랜잭션 시작 이후 변경 내용을 DB에 영구 반영 |
| `conn.rollback()` | 트랜잭션 시작 이후 변경 내용을 모두 취소 |

### Savepoint

트랜잭션 내에 중간 저장 지점을 설정한다.
`rollback(savepoint)` 호출 시 해당 지점 이후의 변경만 취소하고, 이전 변경은 유지한다.

```
트랜잭션 시작
  SQL A 실행         ← commit 시 반영됨
  setSavepoint(sp)   ← 지점 설정
  SQL B 실행 (오류)
  rollback(sp)       ← SQL B만 취소, SQL A는 유지
  commit()           ← SQL A만 DB에 반영됨
```

| 메서드 | 설명 |
|---|---|
| `conn.setSavepoint("이름")` | Savepoint 설정, 반환값으로 `Savepoint` 객체를 받는다 |
| `conn.rollback(sp)` | 지정한 Savepoint 이후의 변경만 취소 |
| `conn.releaseSavepoint(sp)` | Savepoint 해제 (선택 사항) |

### 격리 수준(Isolation Level)

동시에 실행되는 트랜잭션들이 서로를 얼마나 차단할지 결정하는 수준이다.
격리 수준이 낮을수록 성능이 좋고, 높을수록 데이터 정합성이 보장된다.

| 격리 수준 | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| `READ_UNCOMMITTED` (1) | 발생 가능 | 발생 가능 | 발생 가능 |
| `READ_COMMITTED` (2) | 방지 | 발생 가능 | 발생 가능 |
| `REPEATABLE_READ` (4) | 방지 | 방지 | 발생 가능 |
| `SERIALIZABLE` (8) | 방지 | 방지 | 방지 |

#### 이상 현상 설명

| 현상 | 설명 |
|---|---|
| **Dirty Read** | 다른 트랜잭션이 commit하지 않은 변경을 읽는 것 |
| **Non-Repeatable Read** | 같은 행을 두 번 읽었을 때 중간에 다른 트랜잭션이 UPDATE/DELETE 하여 값이 달라지는 것 |
| **Phantom Read** | 같은 조건의 SELECT가 두 번 실행되는 사이 다른 트랜잭션이 INSERT/DELETE하여 행 집합이 달라지는 것 |


#### Non-Repeatable Read 상세

같은 트랜잭션 내에서 **특정 행**을 두 번 읽었을 때, 다른 트랜잭션이 그 행을 UPDATE 또는 DELETE + COMMIT하면 결과가 달라지는 현상이다.

**UPDATE 시나리오**

```
트랜잭션 B                          트랜잭션 A
────────────────────────────        ──────────────────
SELECT price FROM shop_product
WHERE id = 1  →  3000원

                                    UPDATE shop_product
                                    SET price = 5000
                                    WHERE id = 1
                                    COMMIT

SELECT price FROM shop_product
WHERE id = 1  →  5000원  ← 값이 달라짐!
```

**DELETE 시나리오**

```
트랜잭션 B                          트랜잭션 A
────────────────────────────        ──────────────────
SELECT price FROM shop_product
WHERE id = 1  →  3000원 (행 존재)

                                    DELETE FROM shop_product
                                    WHERE id = 1
                                    COMMIT

SELECT price FROM shop_product
WHERE id = 1  →  결과 없음 ← 행 자체가 사라짐!
```

| 원인 | 두 번째 읽기 결과 |
|------|-----------------|
| UPDATE + COMMIT | 같은 행, **값이 달라짐** |
| DELETE + COMMIT | **행 자체가 사라짐** |

#### Phantom Read 상세

같은 트랜잭션 내에서 **범위 조건**으로 두 번 조회할 때, 다른 트랜잭션이 INSERT 또는 DELETE + COMMIT하면 행 집합이 달라지는 현상이다.

**INSERT 시나리오**

```
트랜잭션 B                              트랜잭션 A
────────────────────────────────        ──────────────────
SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B]  (2건)

                                        INSERT INTO shop_product
                                        VALUES (3, '상품C', 90000)
                                        COMMIT

SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B], [상품C]  ← 없던 행 등장! (3건)
```

**DELETE 시나리오**

```
트랜잭션 B                              트랜잭션 A
────────────────────────────────        ──────────────────
SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B], [상품C]  (3건)

                                        DELETE FROM shop_product
                                        WHERE id = 3
                                        COMMIT

SELECT * FROM shop_product
WHERE price < 100000
→ [상품A], [상품B]  ← 있던 행이 사라짐! (2건)
```

| 원인 | 결과 |
|------|------|
| INSERT + COMMIT | 없던 행이 **새로 나타남** |
| DELETE + COMMIT | 있던 행이 **사라짐** |

> **Non-Repeatable Read vs Phantom Read**: DELETE는 두 현상 모두에 해당할 수 있다. 특정 행(`WHERE id=1`)을 삭제하면 Non-Repeatable Read, 범위 조건 결과를 바꾸면 Phantom Read다.

#### Oracle 특이 사항

- Oracle은 `READ_UNCOMMITTED`를 지원하지 않는다. `READ_COMMITTED`가 최소 수준이다.
- Oracle의 기본 격리 수준은 `READ_COMMITTED`다.
- MVCC(Multi-Version Concurrency Control)로 읽기 잠금 없이 일관된 읽기를 보장한다.

---

## App - commit / rollback

주문 처리를 시나리오로 `commit` 성공 케이스와 `rollback` 실패 케이스를 비교한다.

```java
try (Connection conn = dataSource.getConnection()) {
    conn.setAutoCommit(false); // 트랜잭션 시작

    try {
        // 1단계: 주문 INSERT
        long orderId;
        try (PreparedStatement pstmt = conn.prepareStatement(orderSql, new String[]{"ID"})) {
            pstmt.setLong(1, customerId);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                rs.next();
                orderId = rs.getLong(1); // GENERATED KEY 획득
            }
        }

        // 2단계: 주문 상품 INSERT
        try (PreparedStatement pstmt = conn.prepareStatement(itemSql)) {
            pstmt.setLong(1, orderId);
            pstmt.setLong(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
        }

        conn.commit(); // 두 INSERT 모두 성공 → 영구 반영

    } catch (Exception e) {
        conn.rollback(); // 하나라도 실패 → 두 INSERT 모두 취소
        throw e;
    }
}
```

- Oracle에서 `GENERATED ALWAYS AS IDENTITY` 컬럼의 생성 키를 얻으려면 `prepareStatement(sql, new String[]{"ID"})`와 같이 컬럼명 배열을 지정한다.
- `try-catch` 안에서 예외 발생 시 반드시 `rollback()`을 호출해야 변경 내용이 취소된다. 호출하지 않으면 Connection이 반납될 때까지 잠금이 유지된다.
- `conn.close()` 전에 `commit()`이나 `rollback()`이 호출되지 않으면 드라이버에 따라 자동으로 `rollback`된다 (Oracle JDBC 기본 동작).
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App
  ```

---

## App2 - Savepoint

`Savepoint`로 트랜잭션 내 일부 변경만 취소하는 방법을 다룬다.

```java
conn.setAutoCommit(false);

// 1단계: 첫 번째 고객 INSERT 성공
pstmt.setString(1, "박민준"); ...
pstmt.executeUpdate();

// Savepoint 설정: 이 시점 이전 변경은 rollback해도 보존됨
Savepoint sp = conn.setSavepoint("after_park");

// 2단계: 두 번째 고객 INSERT → 중복 이메일(UK 제약 위반)로 예외 발생
try {
    pstmt.setString(2, "hong@example.com"); // 이미 존재하는 이메일
    pstmt.executeUpdate(); // 예외 발생

} catch (Exception e) {
    // Savepoint 이후(두 번째 INSERT)만 취소, 첫 번째 INSERT는 유지
    conn.rollback(sp);
}

conn.commit(); // 첫 번째 고객만 DB에 반영됨
```

- `setSavepoint(name)`: 이름이 있는 Savepoint는 여러 개 설정할 수 있다. 이름 없이 `setSavepoint()`도 가능하다.
- `rollback(sp)` 이후에도 트랜잭션은 종료되지 않는다. 계속 SQL을 실행하고 최종적으로 `commit()` 또는 `rollback()`을 호출해야 한다.
- `releaseSavepoint(sp)`는 Savepoint 객체를 메모리에서 해제한다. 중첩된 Savepoint가 많을 때 메모리 관리를 위해 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App2
  ```

---

## App3 - 격리 수준(Isolation Level)

JDBC API로 격리 수준을 설정하는 방법과 각 수준의 특성을 확인한다.

```java
// JDBC 격리 수준 상수
Connection.TRANSACTION_NONE             // 0 - 트랜잭션 미지원
Connection.TRANSACTION_READ_UNCOMMITTED // 1 - Oracle 미지원
Connection.TRANSACTION_READ_COMMITTED   // 2 - Oracle 기본값
Connection.TRANSACTION_REPEATABLE_READ  // 4
Connection.TRANSACTION_SERIALIZABLE     // 8

// 격리 수준 설정
conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

// 현재 격리 수준 확인 (정수 반환)
int level = conn.getTransactionIsolation(); // 2

// SERIALIZABLE 설정 예시 (가장 강력한 격리, 성능 저하 주의)
conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
```

- 격리 수준은 `Connection` 단위로 설정된다. 트랜잭션 시작 전에 설정해야 한다.
- Oracle의 기본 격리 수준은 `READ_COMMITTED`(2)이며, MVCC 덕분에 읽기 시 잠금이 발생하지 않는다.
- `Non-Repeatable Read`는 단일 스레드에서 시뮬레이션하기 어렵다. 실제 발생은 두 개의 트랜잭션이 동시에 실행될 때 나타난다.
- 격리 수준이 높아질수록 잠금 경합이 증가해 동시성(Throughput)이 낮아진다. 서비스 특성에 맞게 선택한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam03.App3
  ```
