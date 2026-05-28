# AspectJ 관점 (AspectJ Aspects)

> **Spring AOP는 Spring Bean에 프록시를 붙이는 방식이고, AspectJ는 클래스 바이트코드 자체에 Aspect를 엮는 방식이다.**

- Spring AOP는 런타임 프록시 기반으로 동작하며, 
- @AspectJ 스타일을 사용하려면 @EnableAspectJAutoProxy와 aspectjweaver가 필요하다. 
- 또한 Spring AOP는 기본적으로 메서드 실행 조인 포인트를 대상으로 한다.

## 예제 1

### 핵심 비즈니스 코드

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

### AspectJ Aspect 코드

```java
public aspect LoggingAspect {

    pointcut bankMethods():
        execution(* com.example.bank..*(..));

    Object around(): bankMethods() {
        String methodName = thisJoinPointStaticPart
                .getSignature()
                .getName();

        System.out.println("before: " + methodName);

        Object result = proceed();

        System.out.println("after: " + methodName);

        return result;
    }
}
```

- pointcut은 가로챌 지점을 정의한다
- execution(* com.example.bank..*(..))는 com.example.bank 하위 패키지의 모든 메서드 실행을 의미한다
- around()는 대상 메서드 실행 전후에 코드를 삽입한다
- proceed()가 실제 메서드 호출이다

### 사용 코드

```java
public class Main {

    public static void main(String[] args) {
        Bank bank = new BankImpl();

        bank.setAccounts(List.of(new Account("A-001")));
        bank.getAccounts();
    }
}
```

- Spring AOP와 달리 Proxy.newProxyInstance()도, Spring Bean 조회도 필요 없다. 
- AspectJ가 컴파일 시점 또는 로드 시점에 바이트코드에 로깅 코드를 엮는다. 
- Spring은 AspectJ의 load-time weaving 설정도 지원한다.

### Weaving 범위 제한

**resources/META-INF/aop.xml:**

```xml
<!DOCTYPE aspectj PUBLIC "-//AspectJ//DTD//EN" "https://www.eclipse.org/aspectj/dtd/aspectj.dtd">
<aspectj>
    <!--
        위빙 대상을 exam05 패키지로 한정한다.
        - BankImpl: 로깅 어드바이스가 삽입될 대상
        - LoggingAspect: 위버가 aspectOf() 등 인프라 메서드를 주입해야 하는 Aspect 클래스
        이 설정이 없으면 클래스패스의 모든 클래스를 위빙하려 해서 성능이 저하된다.
    -->
    <weaver>
        <include within="com.eomcs.cleancode.ch11.exam05.*"/>
    </weaver>

    <!-- 적용할 Aspect 클래스를 등록한다 -->
    <aspects>
        <aspect name="com.eomcs.cleancode.ch11.exam05.LoggingAspect"/>
    </aspects>
</aspectj>
```

## 나쁜 코드 vs 좋은 코드

| 구분     | Spring AOP            | AspectJ                    |
| ------ | --------------------- | -------------------------- |
| 방식     | 프록시 기반                | 바이트코드 위빙                   |
| 적용 대상  | 주로 Spring Bean        | 일반 Java 객체도 가능             |
| 조인 포인트 | 주로 메서드 실행             | 메서드 호출/실행, 생성자, 필드 접근 등 다양 |
| 내부 호출  | 기본적으로 가로채기 어려움        | 가능                         |
| 설정 난이도 | 비교적 쉬움                | 상대적으로 복잡                   |
| 적합한 경우 | 트랜잭션, 로깅, 보안 같은 일반 업무 | 더 정교한 AOP가 필요할 때           |

## 핵심 원칙

> AspectJ는 메서드 실행뿐 아니라 생성자 실행, 필드 get/set, 예외 핸들러 등 더 다양한 조인 포인트를 지원한다.
