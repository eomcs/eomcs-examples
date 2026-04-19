# Exam04 - ResultSet 매핑과 RowMapper 패턴

## 개념

### ResultSet 수동 매핑

JDBC는 쿼리 결과를 `ResultSet`이라는 커서(cursor) 객체로 반환한다.
개발자가 `rs.getXxx("컬럼명")` 메서드를 직접 호출해 값을 꺼내고, 도메인 객체의 필드에 대입하는 것이 **수동 매핑**이다.

```java
while (rs.next()) {
    Customer c = new Customer();
    c.id        = rs.getLong("id");
    c.name      = rs.getString("name");
    c.email     = rs.getString("email");
    c.city      = rs.getString("city");
    list.add(c);
}
```

#### ResultSet 주요 메서드

| 메서드 | 설명 |
|---|---|
| `rs.next()` | 커서를 다음 행으로 이동한다. 행이 있으면 `true`, 없으면 `false` |
| `rs.getLong("컬럼명")` | `long` 값을 꺼낸다. `NUMBER(19)` 컬럼에 사용 |
| `rs.getInt("컬럼명")` | `int` 값을 꺼낸다 |
| `rs.getString("컬럼명")` | `String` 값을 꺼낸다. `VARCHAR2` 컬럼에 사용 |
| `rs.getDouble("컬럼명")` | `double` 값을 꺼낸다. `NUMBER(12,2)` 컬럼에 사용 |
| `rs.getTimestamp("컬럼명")` | `java.sql.Timestamp` 값을 꺼낸다. `TIMESTAMP` 컬럼에 사용 |
| `rs.wasNull()` | 직전에 읽은 값이 SQL `NULL`이었으면 `true` |

### 수동 매핑의 반복 문제

테이블(객체)이 늘어날수록 동일한 구조의 코드가 반복된다.

```
findAllCustomers() { 연결 획득 → 쿼리 → ResultSet 순회 → Customer 매핑 → 반환 }
findAllProducts()  { 연결 획득 → 쿼리 → ResultSet 순회 → Product 매핑  → 반환 }
findAllOrders()    { 연결 획득 → 쿼리 → ResultSet 순회 → Order 매핑   → 반환 }
        ↑ 공통 골격은 동일, 변하는 부분은 SQL과 객체 매핑 로직뿐
```

이 반복이 ORM 등장의 배경이며, 단기적으로는 **RowMapper 패턴**으로 개선할 수 있다.

### RowMapper 패턴

`ResultSet`의 현재 행(row)을 객체로 변환하는 책임을 인터페이스로 분리한다.

```
공통 처리 (query 메서드): 연결 획득 → 쿼리 실행 → ResultSet 순회 → 자원 해제
변하는 부분 (RowMapper): ResultSet 현재 행 → 객체 변환
```

호출 측은 **매핑 로직만** 람다로 제공하면 되며, 나머지 공통 처리는 `query()` 메서드가 담당한다.
Spring의 `JdbcTemplate.query(sql, rowMapper)`가 이 패턴을 라이브러리로 제공한다.

#### RowMapper 패턴 vs 수동 매핑 비교

| 항목 | 수동 매핑 | RowMapper 패턴 |
|---|---|---|
| 연결 획득 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| ResultSet 순회 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| 자원 해제 코드 | 쿼리마다 반복 | `query()` 메서드 한 곳에만 작성 |
| 객체 변환 코드 | 공통 처리와 뒤섞임 | RowMapper 람다로 분리 |
| 새 테이블 추가 | 전체 패턴 반복 작성 | RowMapper 람다만 추가 |

---

## App - ResultSet 수동 매핑

`shop_customer`와 `shop_product`를 수동 매핑으로 조회하고,
두 메서드의 구조가 동일한 **반복 패턴**임을 확인한다.

```java
// 고객 목록 조회: ResultSet → Customer 수동 매핑
static List<Customer> findAllCustomers() throws Exception {
    String sql = "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id";
    List<Customer> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

        // ─ 반복 패턴 시작 ─
        while (rs.next()) {
            Customer c = new Customer();
            c.id        = rs.getLong("id");
            c.name      = rs.getString("name");
            c.email     = rs.getString("email");
            c.city      = rs.getString("city");
            c.createdAt = rs.getTimestamp("created_at");
            list.add(c);
        }
        // ─ 반복 패턴 끝 ─
    }
    return list;
}

// 제품 목록 조회: ResultSet → Product 수동 매핑
// findAllCustomers()와 구조가 완전히 동일하다!
static List<Product> findAllProducts() throws Exception {
    String sql = "SELECT id, dtype, name, price, stock, created_at FROM shop_product ORDER BY id";
    List<Product> list = new ArrayList<>();

    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {

        // ─ 반복 패턴 시작 ─
        while (rs.next()) {
            Product p = new Product();
            p.id        = rs.getLong("id");
            p.dtype     = rs.getString("dtype");
            p.name      = rs.getString("name");
            p.price     = rs.getDouble("price");
            p.stock     = rs.getInt("stock");
            p.createdAt = rs.getTimestamp("created_at");
            list.add(p);
        }
        // ─ 반복 패턴 끝 ─
    }
    return list;
}
```

- 두 메서드 모두 **연결 획득 → PreparedStatement 생성 → ResultSet 순회 → 객체 대입 → 리스트 반환** 구조가 동일하다.
- 변하는 부분은 SQL 문자열과 `rs.getXxx()` 호출뿐이다.
- 테이블이 10개면 이 패턴이 10번 반복된다. 이것이 ORM(Object-Relational Mapping)이 해결하려는 핵심 문제다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam04.App
  ```

---

## App2 - RowMapper 패턴

`RowMapper` 인터페이스를 도입해 반복 코드를 `query()` 메서드로 통합하고,
매핑 로직만 람다로 제공하는 방식으로 개선한다.

```java
// RowMapper 인터페이스: ResultSet 현재 행 → T 변환 책임
@FunctionalInterface
interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}

// 공통 쿼리 실행 메서드: 연결·ResultSet 처리를 한 곳에서만 담당
static <T> List<T> query(String sql, RowMapper<T> mapper) throws Exception {
    List<T> list = new ArrayList<>();
    try (Connection conn         = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs            = pstmt.executeQuery()) {
        while (rs.next()) {
            list.add(mapper.mapRow(rs)); // 변환 책임을 RowMapper에 위임
        }
    }
    return list;
}

// 호출 측: SQL + 매핑 람다만 작성하면 된다
List<Customer> customers = query(
    "SELECT id, name, email, city, created_at FROM shop_customer ORDER BY id",
    rs -> {
        Customer c = new Customer();
        c.id        = rs.getLong("id");
        c.name      = rs.getString("name");
        c.email     = rs.getString("email");
        c.city      = rs.getString("city");
        c.createdAt = rs.getTimestamp("created_at");
        return c;
    });

List<Product> products = query(
    "SELECT id, dtype, name, price, stock FROM shop_product ORDER BY id",
    rs -> {
        Product p = new Product();
        p.id    = rs.getLong("id");
        p.dtype = rs.getString("dtype");
        p.name  = rs.getString("name");
        p.price = rs.getDouble("price");
        p.stock = rs.getInt("stock");
        return p;
    });
```

- `query()` 메서드에서 연결 획득·ResultSet 순회·자원 해제가 단 한 번만 작성된다.
- 호출 측은 **SQL과 매핑 람다**만 제공하면 되므로 새 테이블 추가 시 람다만 작성하면 된다.
- `RowMapper`가 `@FunctionalInterface`이므로 람다 표현식으로 간결하게 전달할 수 있다.
- Spring의 `JdbcTemplate.query(sql, rowMapper)`는 이 패턴에 파라미터 바인딩, 예외 변환 등을 추가한 완성된 구현이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam04.App2
  ```
