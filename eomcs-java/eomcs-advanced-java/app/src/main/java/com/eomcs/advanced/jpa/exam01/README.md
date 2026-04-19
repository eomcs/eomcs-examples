# Exam01 - JDBC 기본 복습 & Connection Pool

## 개념

### JDBC(Java Database Connectivity)

자바 애플리케이션에서 데이터베이스와 통신하기 위한 표준 API다.
드라이버 로딩 → 연결 획득 → SQL 실행 → 결과 처리 → 자원 해제 순서로 동작한다.

### DriverManager vs DataSource

| 항목 | `DriverManager` | `DataSource` (Connection Pool) |
|---|---|---|
| Connection 생성 | 매 호출마다 새 물리 연결 생성 | 풀에서 이미 만들어진 연결을 재사용 |
| 성능 | 느림 (TCP 핸드셰이크·인증 비용 발생) | 빠름 (물리 연결 재사용) |
| 멀티스레드 | 부적합 | 적합 (풀이 동시 요청을 관리) |
| 자원 해제 | `close()` → 물리 연결 해제 | `close()` → 풀에 반납 (물리 연결 유지) |
| 적합한 용도 | 단순 테스트, 단발성 스크립트 | 실제 서비스, 멀티스레드 서버 |

### HikariCP

현재 가장 빠른 JDBC Connection Pool 라이브러리로, Spring Boot의 기본 Connection Pool이기도 하다.

#### 주요 설정

| 설정 | 설명 | 기본값 |
|---|---|---|
| `maximumPoolSize` | 풀이 유지할 최대 Connection 수 | 10 |
| `minimumIdle` | 유휴 상태로 유지할 최소 Connection 수 | `maximumPoolSize`와 동일 |
| `connectionTimeout` | Connection 획득 대기 최대 시간 (ms) | 30000 |
| `idleTimeout` | 유휴 Connection 제거 전 대기 시간 (ms) | 600000 |
| `maxLifetime` | Connection 최대 수명 (ms) | 1800000 |

### Oracle JDBC URL 형식

```
jdbc:oracle:thin:@//호스트:포트/서비스이름
```

### 환경 변수로 접속 정보 관리

소스 코드에 계정 정보를 직접 작성하면 Git 등 버전 관리 도구에 유출될 위험이 있다.
OS 환경 변수에 접속 정보를 저장하고 `System.getenv()`로 읽는 방식이 권장된다.

```java
String host    = System.getenv("DB_HOSTNAME");
String port    = System.getenv("DB_PORT");
String service = System.getenv("DB_SERVICE_NAME");
String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;
String username = System.getenv("DB_USERNAME");
String password = System.getenv("DB_PASSWORD");
```

### Connection 흐름 비교

```
DriverManager 방식:
  getConnection() → [물리 연결 생성] → 사용 → close() → [물리 연결 해제]

HikariCP 방식:
  Pool 초기화 → 물리 연결 N개 미리 생성
  getConnection() → [풀에서 대여] → 사용 → close() → [풀에 반납]
```

---

## App - DriverManager로 직접 연결

`DriverManager.getConnection()`으로 연결을 획득하고, 고객 목록을 조회한다.

```java
String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

// DriverManager.getConnection() 호출 → 새 물리적 연결 생성
try (Connection connection = DriverManager.getConnection(url, username, password)) {
    // 드라이버 정보, DB 버전, AutoCommit 여부 확인
    connection.getMetaData().getDriverName();
    connection.getMetaData().getDatabaseProductVersion();
    connection.getAutoCommit(); // 기본값: true

    // SYSDATE 조회
    try (Statement stmt = connection.createStatement();
         ResultSet rs   = stmt.executeQuery("SELECT SYSDATE FROM DUAL")) {
        if (rs.next()) System.out.println(rs.getString(1));
    }

    // 고객 목록 조회
    try (Statement stmt = connection.createStatement();
         ResultSet rs   = stmt.executeQuery(
             "SELECT id, name, email, city FROM shop_customer ORDER BY id")) {
        while (rs.next()) {
            System.out.printf("[%d] %s / %s%n",
                rs.getLong("id"), rs.getString("name"), rs.getString("city"));
        }
    }
}
// try 블록 종료 → connection.close() 자동 호출 → 물리 연결 해제
```

- `try-with-resources`를 사용하면 예외 발생 여부와 관계없이 `close()`가 자동 호출된다.
- `Connection → Statement → ResultSet` 모두 `AutoCloseable`을 구현하므로 `try-with-resources`로 관리한다.
- `getMetaData()`로 드라이버 이름, DB 버전 등 연결 메타데이터를 확인할 수 있다.
- `AutoCommit`의 기본값은 `true`이다. 트랜잭션 제어가 필요하면 `setAutoCommit(false)`로 변경한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam01.App
  ```

---

## App2 - HikariCP DataSource (Connection Pool)

`HikariCP`를 설정하고, Connection Pool에서 연결을 대여·반납하는 동작을 확인한다.

```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:oracle:thin:@//" + host + ":" + port + "/" + service);
config.setUsername(username);
config.setPassword(password);
config.setMaximumPoolSize(5);        // 최대 Connection 5개
config.setMinimumIdle(2);           // 유휴 Connection 최소 2개
config.setConnectionTimeout(3000);  // 3초 안에 못 얻으면 예외

// HikariDataSource 생성 → Pool 초기화 (내부적으로 물리 연결 생성)
try (HikariDataSource dataSource = new HikariDataSource(config)) {

    // 첫 번째 Connection 획득 (풀에서 대여)
    try (Connection conn1 = dataSource.getConnection()) {
        // conn1 사용 ...
    }
    // conn1.close() → 풀에 반납 (물리 연결은 유지됨)

    // 두 번째 Connection 획득 → 반납된 연결을 재사용
    try (Connection conn2 = dataSource.getConnection()) {
        // conn2 사용 ...
    }
}
// dataSource.close() → 풀의 모든 물리 연결 해제
```

- `HikariDataSource`는 `AutoCloseable`을 구현하므로 `try-with-resources`로 Pool 전체를 관리할 수 있다.
- Pool에서 대여한 `Connection`의 실제 클래스명은 HikariCP 내부 프록시 클래스(`HikariProxyConnection`)이다. `close()` 시 물리 연결을 끊지 않고 풀에 반납한다.
- `connectionTimeout` 초과 시 `SQLTransientConnectionException`이 발생한다.
- 실서비스에서는 `DataSource`를 싱글턴으로 관리하며 애플리케이션 시작 시 한 번만 생성한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam01.App2
  ```
