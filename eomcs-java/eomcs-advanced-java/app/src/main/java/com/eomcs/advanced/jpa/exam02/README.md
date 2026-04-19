# Exam02 - PreparedStatement & Batch 처리

## 개념

### Statement vs PreparedStatement

| 항목 | `Statement` | `PreparedStatement` |
|---|---|---|
| SQL 전달 방식 | 완성된 SQL 문자열을 그대로 전송 | SQL 골격(템플릿)을 미리 컴파일한 뒤 파라미터만 바인딩 |
| 파싱·최적화 | 실행할 때마다 반복 수행 | 최초 1회만 수행 (이후 실행 시 재사용) |
| SQL Injection | 취약 (사용자 입력이 SQL 구조 변경 가능) | 안전 (입력값은 항상 데이터로 처리) |
| 파라미터 바인딩 | 문자열 연결 (`"... WHERE id=" + id`) | `?` 플레이스홀더 + `setXxx()` |
| 반복 실행 성능 | 느림 | 빠름 |
| 권장 여부 | 파라미터가 없는 DDL 등 제한적 사용 | 파라미터가 있는 모든 SQL에 사용 |

### SQL Injection

사용자 입력을 SQL 문자열에 직접 연결할 때 발생하는 보안 취약점이다.
입력값에 SQL 문법 문자(작은따옴표 등)를 포함해 원래 쿼리의 논리를 변조한다.

```
입력값: ' OR '1'='1

Statement 실행 SQL:
  SELECT * FROM shop_customer WHERE name='' OR '1'='1'
  → WHERE 조건이 항상 참이 되어 모든 행이 반환된다.

PreparedStatement 실행:
  SELECT * FROM shop_customer WHERE name = ?  [파라미터: ' OR '1'='1]
  → 입력값 전체가 name 컬럼과 비교하는 리터럴 값으로 처리된다.
```

### Batch 처리

여러 SQL을 묶어 DB 서버에 한 번에 전송하는 방식이다.

```
개별 실행: SQL 1건 전송 → 응답 → SQL 1건 전송 → 응답 → ... (N번 왕복)
Batch 실행: SQL N건 일괄 전송 → N개 응답 (1번 왕복)
```

네트워크 왕복 횟수를 줄여 대량 INSERT·UPDATE 성능이 크게 향상된다.

#### Batch API

| 메서드 | 설명 |
|---|---|
| `pstmt.addBatch()` | 현재 파라미터 조합을 배치 큐에 추가한다 |
| `pstmt.executeBatch()` | 큐에 쌓인 SQL을 한꺼번에 실행한다. 각 SQL의 영향 행 수를 `int[]`로 반환한다 |
| `pstmt.clearBatch()` | 큐를 비운다 (선택 사항) |

#### 주의 사항

- Batch 실행 중 일부 SQL이 실패하면 `BatchUpdateException`이 발생한다.
- 트랜잭션과 함께 사용해 부분 실패 시 `rollback`을 처리해야 안전하다.

---

## App - SQL Injection vs PreparedStatement

`Statement`의 SQL Injection 취약점과 `PreparedStatement`의 차단 방식을 비교한다.

```java
// [Statement] 사용자 입력을 SQL 문자열에 직접 연결 → 위험
String maliciousInput = "' OR '1'='1";
String sql = "SELECT * FROM shop_customer WHERE name='" + maliciousInput + "'";
// 실행 SQL: SELECT * FROM shop_customer WHERE name='' OR '1'='1'
// → 모든 고객이 반환된다 (의도하지 않은 결과)
try (Statement stmt = conn.createStatement();
     ResultSet rs   = stmt.executeQuery(sql)) { ... }

// [PreparedStatement] ? 플레이스홀더로 파라미터 분리 → 안전
String safeSql = "SELECT id, name, email FROM shop_customer WHERE name = ?";
try (PreparedStatement pstmt = conn.prepareStatement(safeSql)) {
    pstmt.setString(1, maliciousInput); // 인덱스는 1부터 시작
    // 내부 처리: name = ''' OR ''1''=''1'  (이스케이프 처리됨)
    try (ResultSet rs = pstmt.executeQuery()) { ... }
    // → 검색 결과 없음 (SQL Injection 차단됨)
}
```

- `pstmt.setString(1, value)`: 인덱스는 **1**부터 시작한다. `setLong`, `setInt`, `setDouble` 등 타입별 메서드를 사용한다.
- `PreparedStatement`는 파라미터 값 내의 특수문자를 자동으로 이스케이프 처리한다.
- 동일한 `PreparedStatement` 객체는 파라미터를 바꿔 가며 반복 실행할 수 있다. 반복 실행 시 `clearParameters()`로 이전 파라미터를 초기화한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam02.App
  ```

---

## App2 - Batch 처리

개별 `executeUpdate()` 방식과 `addBatch()` / `executeBatch()` 방식을 비교한다.

```java
String sql = "INSERT INTO shop_category (name, parent_id) VALUES (?, ?)";

// [방법 1] 개별 INSERT: 3건 → 3번의 DB 왕복
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, "태블릿");   pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
    pstmt.setString(1, "스마트폰"); pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
    pstmt.setString(1, "액세서리"); pstmt.setNull(2, Types.NUMERIC); pstmt.executeUpdate();
}

// [방법 2] Batch INSERT: 5건 → 1번의 DB 왕복
String[] names = {"가전제품", "냉장고", "세탁기", "에어컨", "청소기"};
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    for (String name : names) {
        pstmt.setString(1, name);
        pstmt.setNull(2, Types.NUMERIC);
        pstmt.addBatch(); // 배치 큐에 추가 (아직 실행 안 됨)
    }
    int[] results = pstmt.executeBatch(); // 큐의 SQL 5건 일괄 전송
    // results[i]: i번째 SQL의 영향 행 수 (성공 시 보통 1)
}
```

- `addBatch()` 호출만으로는 SQL이 실행되지 않는다. `executeBatch()`를 호출해야 실제 전송된다.
- `executeBatch()` 반환값 `int[]`의 각 원소는 해당 SQL의 영향 행 수다. `Statement.SUCCESS_NO_INFO(-2)`가 반환되기도 한다 (드라이버 구현 방식에 따라 다름).
- Batch 실행 중 오류 발생 시 `BatchUpdateException`이 던져진다. 트랜잭션 `rollback`과 함께 사용해 데이터 정합성을 유지한다.
- 수만 건 이상의 대량 처리 시 일정 건수마다 `executeBatch()`를 호출하고 큐를 비워 메모리 사용량을 조절한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam02.App2
  ```
