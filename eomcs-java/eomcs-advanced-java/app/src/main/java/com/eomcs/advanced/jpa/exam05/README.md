# Exam05 - JDBC Template 패턴 직접 구현

## 개념

### 템플릿 콜백(Template-Callback) 패턴

변하지 않는 **공통 로직(템플릿)**과 변하는 부분(**콜백**)을 분리하는 디자인 패턴이다.

```
템플릿 (변하지 않는 것): Connection 획득 → PreparedStatement 생성 → 실행 → 자원 해제
콜백   (변하는 것)     : SQL 문자열, 파라미터 바인딩, 결과 객체 변환
```

템플릿은 한 번만 작성하고, 콜백은 호출할 때마다 람다로 교체한다.
Spring의 `JdbcTemplate`이 이 패턴의 대표적인 구현이다.

### 이 예제에서 직접 구현하는 클래스

| 클래스/인터페이스 | Spring 대응 | 역할 |
|---|---|---|
| `SimpleJdbcTemplate` | `JdbcTemplate` | 공통 로직(템플릿)을 담당 |
| `StatementSetter` | `PreparedStatementSetter` | PreparedStatement 파라미터 바인딩 콜백 |
| `RowMapper<T>` | `RowMapper<T>` | ResultSet 한 행 → T 변환 콜백 |

### SimpleJdbcTemplate 메서드 구조

```
query(sql, setter, mapper)
  → 연결 획득 → pstmt 생성 → setter.setValues(pstmt) → 실행 → rs 순회 → mapper.mapRow(rs)

queryForObject(sql, setter, mapper)
  → query() 실행 후 첫 번째 결과 Optional로 반환

update(sql, setter)
  → 연결 획득 → pstmt 생성 → setter.setValues(pstmt) → executeUpdate()
```

### Spring JdbcTemplate과의 차이

이 예제의 `SimpleJdbcTemplate`은 핵심 원리를 학습하기 위한 단순 구현이다.
Spring의 `JdbcTemplate`은 여기에 다음이 추가된다.

| 기능 | SimpleJdbcTemplate | Spring JdbcTemplate |
|---|---|---|
| 기본 원리 | 동일 | 동일 |
| 예외 처리 | `Exception` 전파 | `DataAccessException` 계층으로 변환 |
| 파라미터 바인딩 | `StatementSetter` 람다 | `Object...` 가변인수 지원 |
| 단건 조회 | `Optional<T>` | `queryForObject()` |
| 네임드 파라미터 | 미지원 | `NamedParameterJdbcTemplate` |
| 트랜잭션 연동 | 미지원 | Spring 트랜잭션 관리와 자동 연동 |

---

## App - SimpleJdbcTemplate 직접 구현

`SimpleJdbcTemplate`을 직접 구현하고, 다건 조회·단건 조회·UPDATE에 활용한다.

```java
// 콜백 인터페이스 정의
@FunctionalInterface
interface StatementSetter {
    void setValues(PreparedStatement pstmt) throws SQLException;
}

@FunctionalInterface
interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}

// 템플릿 클래스: 공통 JDBC 로직 캡슐화
static class SimpleJdbcTemplate {

    private final HikariDataSource dataSource;

    // 다건 조회
    <T> List<T> query(String sql, StatementSetter setter, RowMapper<T> mapper) throws Exception {
        List<T> list = new ArrayList<>();
        try (Connection conn         = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setter.setValues(pstmt);               // 콜백: 파라미터 바인딩
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.mapRow(rs));    // 콜백: 행 변환
                }
            }
        }
        return list;
    }

    // 단건 조회
    <T> Optional<T> queryForObject(String sql, StatementSetter setter, RowMapper<T> mapper)
            throws Exception {
        List<T> result = query(sql, setter, mapper);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    // INSERT / UPDATE / DELETE
    int update(String sql, StatementSetter setter) throws Exception {
        try (Connection conn         = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setter.setValues(pstmt);               // 콜백: 파라미터 바인딩
            return pstmt.executeUpdate();
        }
    }
}
```

### 사용 예시

```java
SimpleJdbcTemplate jdbc = new SimpleJdbcTemplate(dataSource);

// 1. 전체 고객 조회 (파라미터 없음)
List<Customer> customers = jdbc.query(
    "SELECT id, name, email, city FROM shop_customer ORDER BY id",
    pstmt -> { /* 파라미터 없음 */ },
    rs -> {
        Customer c = new Customer();
        c.id    = rs.getLong("id");
        c.name  = rs.getString("name");
        c.email = rs.getString("email");
        c.city  = rs.getString("city");
        return c;
    });

// 2. id로 단건 조회
Optional<Customer> found = jdbc.queryForObject(
    "SELECT id, name, email, city FROM shop_customer WHERE id = ?",
    pstmt -> pstmt.setLong(1, 1L), // StatementSetter 람다
    CUSTOMER_MAPPER);              // 재사용 가능한 RowMapper 상수
found.ifPresentOrElse(
    c -> System.out.println(c),
    () -> System.out.println("없음"));

// 3. 가격 범위로 제품 조회
List<Product> expensive = jdbc.query(
    "SELECT id, name, price, stock FROM shop_product WHERE price >= ? ORDER BY price DESC",
    pstmt -> pstmt.setDouble(1, 100_000),
    PRODUCT_MAPPER);

// 4. 재고 UPDATE
int affected = jdbc.update(
    "UPDATE shop_product SET stock = stock - ? WHERE id = ?",
    pstmt -> {
        pstmt.setInt(1, 1);    // 차감량
        pstmt.setLong(2, 1L);  // product id
    });
```

- `RowMapper`를 상수(`CUSTOMER_MAPPER`, `PRODUCT_MAPPER`)로 정의해두면 여러 쿼리에서 재사용할 수 있다.
- 파라미터가 없는 쿼리는 `setter` 자리에 `pstmt -> {}` 빈 람다를 전달한다.
- `queryForObject()`는 결과가 없을 때 `Optional.empty()`를 반환하므로 `NullPointerException` 위험 없이 처리할 수 있다.
- `update()`의 반환값은 영향을 받은 행 수(int)이다. 0이면 조건에 맞는 행이 없었음을 의미한다.
- 이 `SimpleJdbcTemplate`에는 트랜잭션 처리가 없다. 트랜잭션이 필요하면 호출 측에서 `Connection`을 직접 관리하거나 Spring의 `@Transactional`을 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam05.App
  ```
