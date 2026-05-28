# 변경하기 쉬운 클래스 (Organizing for Change)

> **변경이 생길 때 기존 코드를 고치는 대신, 새 코드를 추가하도록 구조화하라**

- 요구사항은 계속 바뀐다
- 큰 클래스는 변경될 때마다 기존 메서드를 수정하게 만든다
- 기존 코드를 자주 수정하면 이미 동작하던 기능까지 깨질 위험이 커진다
- 좋은 구조는 새 기능을 “수정”이 아니라 “확장”으로 추가하게 만든다

👉 핵심 의도

- 변경 가능성이 있는 부분을 한 클래스 안에 몰아넣지 않는다
- 기능별 클래스로 나눈다
- 공통 개념은 상위 타입이나 인터페이스로 표현한다
- 새 기능은 새 클래스로 추가한다

책의 예제에서도 하나의 Sql 클래스가 create, insert, select, findByKey, preparedInsert 등을 모두 가지면 새 SQL 문법이 추가될 때 기존 클래스를 열어 수정해야 한다고 설명한다. 이를 여러 하위 클래스로 나누면 새 기능은 새 Sql 하위 클래스로 추가할 수 있다.

## 예제 1

```java
// Bad
public class SqlGenerator {

    private final String table;

    public SqlGenerator(String table) {
        this.table = table;
    }

    public String create() {
        return "CREATE TABLE " + table;
    }

    public String insert(String[] columns) {
        return "INSERT INTO " + table + " (" + String.join(", ", columns) + ")";
    }

    public String selectAll() {
        return "SELECT * FROM " + table;
    }

    public String findById(long id) {
        return "SELECT * FROM " + table + " WHERE id = " + id;
    }
}
```

- SQL 생성 책임이 한 클래스에 모두 모여 있다
- update()가 필요해지면 이 클래스를 수정해야 한다
- delete()가 필요해져도 이 클래스를 수정해야 한다
- 기존 insert(), selectAll() 기능까지 영향을 받을 수 있다
- 클래스가 변경에 열려 있다

```java
// Good
public abstract class Sql {

    protected final String table;

    protected Sql(String table) {
        this.table = table;
    }

    public abstract String generate();
}
```

```java
public class CreateSql extends Sql {

    public CreateSql(String table) {
        super(table);
    }

    @Override
    public String generate() {
        return "CREATE TABLE " + table;
    }
}
```

```java
public class InsertSql extends Sql {

    private final String[] columns;

    public InsertSql(String table, String[] columns) {
        super(table);
        this.columns = columns;
    }

    @Override
    public String generate() {
        return "INSERT INTO " + table + " (" + String.join(", ", columns) + ")";
    }
}
```

```java
public class SelectAllSql extends Sql {

    public SelectAllSql(String table) {
        super(table);
    }

    @Override
    public String generate() {
        return "SELECT * FROM " + table;
    }
}
```

```java
public class FindByIdSql extends Sql {

    private final long id;

    public FindByIdSql(String table, long id) {
        super(table);
        this.id = id;
    }

    @Override
    public String generate() {
        return "SELECT * FROM " + table + " WHERE id = " + id;
    }
}
```

```java
// 새 기능 추가
public class UpdateSql extends Sql {

    private final String column;
    private final String value;
    private final long id;

    public UpdateSql(String table, String column, String value, long id) {
        super(table);
        this.column = column;
        this.value = value;
        this.id = id;
    }

    @Override
    public String generate() {
        return "UPDATE " + table
                + " SET " + column + " = '" + value + "'"
                + " WHERE id = " + id;
    }
}
```

- UpdateSql을 추가해도 기존 클래스는 수정하지 않는다
- 기존 기능이 깨질 가능성이 줄어든다
- SRP(Single Responsibility Principle)를 지원한다.
    - 즉, 각 클래스가 하나의 SQL 생성 책임만 가진다
- OCP(Open-Closed Principle)에 가까워진다
    - 즉, 확장에는 열려 있고 수정에는 닫혀 있다

## 예제 2: 변경으로부터 격리하라 (Isolating from Change)

> **구체적인 구현에 직접 의존하지 말고, 추상화에 의존하라**

- 구체 클래스는 구현 세부사항을 가진다
- 구현 세부사항은 자주 바뀐다
- 클라이언트 코드가 구체 클래스에 직접 의존하면, 세부사항 변경에 함께 흔들린다
- 인터페이스를 사이에 두면 변경 영향을 줄일 수 있다

Clean Code:

- 요구사항이 바뀌면 코드도 바뀌며, 
- 구체 클래스에 의존하는 클라이언트는 그 구현 세부사항이 바뀔 때 위험해진다
- 따라서 인터페이스와 추상화를 사용해 변경 가능성이 큰 부분을 격리해야 한다

```java
// Bad
public class Portfolio {

    private final TokyoStockExchange exchange;

    public Portfolio() {
        this.exchange = new TokyoStockExchange();
    }

    public int valueOf(String stockName, int shares) {
        int price = exchange.currentPrice(stockName);
        return price * shares;
    }
}
```

```java
public class TokyoStockExchange {

    public int currentPrice(String stockName) {
        // 실제 외부 API 호출
        return 1000;
    }
}
```

- Portfolio가 TokyoStockExchange라는 구체 클래스에 직접 의존한다
- 외부 API가 바뀌면 Portfolio도 영향을 받는다
- 테스트하기 어렵다
- 테스트 중에도 실제 증권 거래소 API를 호출할 위험이 있다

```java
// Good
public interface StockExchange {
    int currentPrice(String stockName);
}
```

```java
public class TokyoStockExchange implements StockExchange {

    @Override
    public int currentPrice(String stockName) {
        // 실제 외부 API 호출
        return 1000;
    }
}
```

```java
public class Portfolio {

    private final StockExchange exchange;

    public Portfolio(StockExchange exchange) {
        this.exchange = exchange;
    }

    public int valueOf(String stockName, int shares) {
        int price = exchange.currentPrice(stockName);
        return price * shares;
    }
}
```

```java
// 테스트용 구현
public class FixedStockExchange implements StockExchange {

    @Override
    public int currentPrice(String stockName) {
        return 1000;
    }
}
```

```java
@Test
void calculatesPortfolioValue() {
    StockExchange exchange = new FixedStockExchange();
    Portfolio portfolio = new Portfolio(exchange);

    int value = portfolio.valueOf("Samsung", 10);

    assertEquals(10000, value);
}
```

- Portfolio는 실제 거래소 구현을 모른다
- 테스트에서는 가짜 거래소를 넣을 수 있다
- 외부 API 변경이 Portfolio로 전파되지 않는다
- 변경 가능성이 큰 부분이 StockExchange 구현체 안으로 격리된다
- DIP(Dependency Inversion Principle)를 지원한다
    - 즉, 고수준 모듈이 저수준 모듈에 의존하는 것이 아니라, 둘 다 추상화에 의존한다

## 예제 3

```java
// Bad
public class OrderService {

    private final MySqlOrderRepository repository = new MySqlOrderRepository();

    public void place(Order order) {
        repository.insert(order);
    }
}
```

```java
public class MySqlOrderRepository {

    public void insert(Order order) {
        System.out.println("insert into mysql");
    }
}
```

- OrderService가 MySQL 구현에 직접 의존한다
- 저장소가 PostgreSQL, MongoDB, 외부 API로 바뀌면 OrderService도 수정해야 한다
- 비즈니스 로직과 저장 기술이 강하게 결합된다

```java
// Good
public interface OrderRepository {
    void save(Order order);
}
```

```java
public class MySqlOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        System.out.println("insert into mysql");
    }
}
```

```java
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void place(Order order) {
        repository.save(order);
    }
}
```

```java
// 새 저장 방식 추가
public class MongoOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        System.out.println("insert into mongodb");
    }
}
```

- OrderService는 저장 방식 변경에 닫혀 있다
- 새 저장 방식은 새 클래스로 추가한다
- 비즈니스 로직은 저장 기술로부터 격리된다
- 테스트도 쉬워진다

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드     | 좋은 코드            |
| ----- | --------- | ---------------- |
| 변경 방식 | 기존 클래스 수정 | 새 클래스 추가         |
| 의존 대상 | 구체 클래스    | 인터페이스, 추상 클래스    |
| 변경 영향 | 여러 기능에 전파 | 변경 지점에 격리        |
| 테스트   | 실제 구현에 묶임 | 가짜 구현 주입 가능      |
| 설계 원칙 | 수정에 열려 있음 | 확장에 열려 있고 수정에 닫힘 |

## 핵심 원칙

피해야 할 것:

- 모든 기능을 하나의 큰 클래스에 넣는 것
- 새 요구사항이 생길 때마다 기존 클래스를 계속 수정하는 것
- 비즈니스 로직이 DB, 외부 API, 파일 시스템 같은 구체 구현에 직접 의존하는 것
- 테스트하기 어려운 구체 객체를 클래스 내부에서 직접 생성하는 것

지켜야 할 것:

- 변경 가능성이 큰 부분을 인터페이스 뒤로 숨긴다
- 새 기능은 기존 코드 수정이 아니라 새 클래스로 추가한다
- 클라이언트는 구체 구현보다 추상화에 의존하게 한다
- 변경이 자주 일어나는 구현 세부사항을 비즈니스 로직과 분리한다
- 시스템은 “수정하기 쉬운 구조”보다 “수정할 필요가 적은 구조”를 지향한다