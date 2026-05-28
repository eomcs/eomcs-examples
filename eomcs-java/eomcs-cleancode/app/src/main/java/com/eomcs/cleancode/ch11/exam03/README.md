# 자바 프록시 (Java Proxies)

> **프록시는 핵심 객체 앞에 대리 객체를 세워, 로깅·보안·트랜잭션·영속성 같은 부가 관심사를 끼워 넣는 방법이다**

- 도메인 객체는 비즈니스 로직만 가져야 한다
- 영속성, 트랜잭션, 보안 같은 관심사는 여러 객체를 가로지른다
- 프록시는 실제 객체 호출 전후에 공통 처리를 넣을 수 있다
- **하지만 JDK 동적 프록시는 인터페이스에만 적용 가능하고, 코드가 장황해지기 쉽다**

👉 핵심 의도:

- 실제 객체는 POJO로 단순하게 유지한다
- 프록시가 부가 기능을 대신 처리한다
- 클라이언트는 실제 객체인지 프록시인지 몰라도 된다

## 예제 1

```java
// 핵심 인터페이스
public interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
}
```

```java
// 실제 비즈니스 객체
public class BankImpl implements Bank {

    private Collection<Account> accounts = new ArrayList<>();

    @Override
    public Collection<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void setAccounts(Collection<Account> accounts) {
        this.accounts = new ArrayList<>(accounts);
    }
}
```

- BankImpl은 계좌 목록 관리만 담당한다
- DB 저장, 로깅, 트랜잭션 코드는 없다
- 도메인 객체가 단순하다

## 예제 2

```java
// Bad
public class BankImpl implements Bank {

    private Collection<Account> accounts = new ArrayList<>();

    @Override
    public Collection<Account> getAccounts() {

        // DB에서 계좌 목록을 읽어오는 코드라 가정
        System.out.println("load accounts from database");

        return accounts;
    }

    @Override
    public void setAccounts(Collection<Account> accounts) {
        this.accounts = new ArrayList<>(accounts);

        // DB에 계좌 목록을 저장하는 코드라 가정
        System.out.println("save accounts to database");
    }
}
```

- 비즈니스 로직과 영속성 코드가 섞였다
- DB 정책이 바뀌면 BankImpl이 바뀐다
- 테스트하기 어렵다
- 여러 도메인 객체에 같은 코드가 반복될 수 있다

```java
// Good
public class BankProxyHandler implements InvocationHandler {

    private final Bank target;

    public BankProxyHandler(Bank target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        if (method.getName().equals("getAccounts")) {

            // DB에서 계좌 목록을 읽어오는 코드라 가정
            System.out.println("load accounts from database");

            return method.invoke(target, args);
        }

        if (method.getName().equals("setAccounts")) {
            Object result = method.invoke(target, args);

            // DB에 계좌 목록을 저장하는 코드라 가정
            System.out.println("save accounts to database");

            return result;
        }

        return method.invoke(target, args);
    }
}
```

```java
// 프록시 객체 생성
Bank bank = (Bank) Proxy.newProxyInstance(
        Bank.class.getClassLoader(),
        new Class[] { Bank.class },
        new BankProxyHandler(new BankImpl())
);
```

```java
// 프록시 사용
bank.setAccounts(List.of(new Account("A-001")));
Collection<Account> accounts = bank.getAccounts();
```

- BankImpl은 순수 비즈니스 객체로 남는다
- 프록시가 DB 처리 같은 부가 관심사를 담당한다
- 클라이언트는 Bank 인터페이스만 사용한다
- 실제 객체와 프록시의 차이를 몰라도 된다

## 예제 3

```java
// 로깅 프록시
public class LoggingProxyHandler implements InvocationHandler {

    private final Object target;

    public LoggingProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before: " + method.getName());

        Object result = method.invoke(target, args);

        System.out.println("after: " + method.getName());

        return result;
    }
}
```

```java
Bank bank = (Bank) Proxy.newProxyInstance(
        Bank.class.getClassLoader(),
        new Class[] { Bank.class },
        new LoggingProxyHandler(new BankImpl())
);
```

- 모든 메서드 호출 전후에 로깅을 넣을 수 있다
- BankImpl에는 로깅 코드가 없다
- 횡단 관심사를 프록시로 분리했다

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드         | 좋은 코드             |
| ------ | ------------- | ----------------- |
| 도메인 객체 | 영속성, 로깅 코드 포함 | 비즈니스 로직만 포함       |
| 부가 관심사 | 여러 클래스에 흩어짐   | 프록시에서 처리          |
| 의존성    | 구체 구현에 직접 의존  | 인터페이스에 의존         |
| 테스트    | 부가 기능 때문에 복잡  | 핵심 객체만 테스트 가능     |
| 단점     | 단순하지만 오염됨     | **깨끗하지만 프록시 코드가 장황함** |

## 핵심 원칙

**피해야 할 것:**

- 도메인 객체 안에 DB, 로깅, 트랜잭션 코드를 직접 넣는 것
- 여러 클래스에 같은 부가 코드를 반복하는 것
- 프록시 코드가 너무 복잡해져 핵심 의도를 가리는 것

**지켜야 할 것:**

- 핵심 객체는 POJO로 단순하게 유지한다
- 부가 관심사는 프록시로 분리한다
- 클라이언트는 인터페이스에 의존하게 한다
- 프록시가 지나치게 복잡해지면 AOP 프레임워크 사용을 고려한다