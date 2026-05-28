# 순수 자바 AOP 프레임워크 (Pure Java AOP Frameworks)

> **직접 InvocationHandler를 작성하지 않고, Spring AOP의 @Aspect로 메서드 호출 전후 로깅을 분리한다**

- Spring AOP는 런타임 프록시 기반으로 동작하며, 
- @AspectJ 스타일을 사용하려면 @EnableAspectJAutoProxy와 aspectjweaver가 필요하다. 
- 또한 Spring AOP는 기본적으로 메서드 실행 조인 포인트를 대상으로 한다.

## 예제 1

### Before - 직접 만든 프록시

```java
public class LoggingProxyHandler implements InvocationHandler {

    private final Object target;

    public LoggingProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        System.out.println("before: " + method.getName());

        Object result = method.invoke(target, args);

        System.out.println("after: " + method.getName());

        return result;
    }
}
```

- 프록시 생성 코드를 직접 작성해야 한다
- Proxy.newProxyInstance(...)가 필요하다
- 로깅 대상이 늘어날수록 설정 코드가 번거롭다

### After - Spring AOP

```java
public interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
}
```

```java
@Service
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

```java
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.eomcs.cleancode.ch11.exam04.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        System.out.println("before: " + methodName);

        Object result = joinPoint.proceed();

        System.out.println("after: " + methodName);

        return result;
    }
}
```

```java
@Configuration
@ComponentScan("com.eomcs.cleancode.ch11.exam04")
@EnableAspectJAutoProxy
public class AppConfig {
}
```

```java
// 직접 실행하여 로그 출력 확인
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Bank bank = context.getBean(Bank.class);

        bank.setAccounts(List.of(new Account("A-001")));
        bank.getAccounts();

        context.close();
    }
}
```

```java
// 테스트로 로그 출력 확인
  @Test
  void setAccounts_호출_전후에_로그가_출력된다() {
    bank.setAccounts(List.of(new Account("A-001")));

    String log = out.toString();
    assertTrue(log.contains("before: setAccounts"));
    assertTrue(log.contains("after: setAccounts"));
  }

  @Test
  void getAccounts_호출_전후에_로그가_출력된다() {
    bank.getAccounts();

    String log = out.toString();
    assertTrue(log.contains("before: getAccounts"));
    assertTrue(log.contains("after: getAccounts"));
  }

  @Test
  void setAccounts로_저장한_계좌를_getAccounts로_조회할_수_있다() {
    bank.setAccounts(List.of(new Account("A-001"), new Account("A-002")));

    Collection<Account> accounts = bank.getAccounts();

    assertEquals(2, accounts.size());
  }
```

## 나쁜 코드 vs 좋은 코드

| 구분       | 직접 프록시                           | Spring AOP                           |
| -------- | -------------------------------- | ------------------------------------ |
| 프록시 생성   | 직접 `Proxy.newProxyInstance()` 호출 | **Spring이 자동 생성**                        |
| 부가 기능 위치 | `InvocationHandler`              | `@Aspect`                            |
| 대상 지정    | 직접 인터페이스 지정                      | pointcut 표현식                         |
| 코드량      | 많음                               | 적음                                   |
| 한계       | JDK 동적 프록시 중심                    | **Spring도 프록시 기반, 내부 호출은 기본적으로 가로채지 못함** |


## 핵심 원칙

- BankImpl은 계좌 관리만 담당한다
- 로깅은 LoggingAspect가 담당한다
- 클라이언트는 Bank를 사용할 뿐 프록시 존재를 모른다
- 횡단 관심사인 로깅이 도메인 객체에서 분리된다
