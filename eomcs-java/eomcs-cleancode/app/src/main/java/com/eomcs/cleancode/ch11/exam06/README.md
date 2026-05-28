# 기타 

- *예제 파일 없음*

## 1. 테스트 주도 시스템 아키텍처 구축 (Test Drive the System Architecture)

> **아키텍처도 코드처럼 테스트 가능해야 하며, 테스트를 통해 점진적으로 설계해야 한다**

- 초기 아키텍처를 과하게 설계하면 실제 요구사항과 어긋날 가능성이 크다
- 테스트 가능한 구조는 자연스럽게 느슨한 결합 + 높은 응집도를 만든다
- 테스트가 어려운 구조는 이미 잘못된 아키텍처일 가능성이 높다

**핵심 의도:**

- 아키텍처를 “한 번에 완성”하려 하지 않는다
- 테스트 가능한 구조로 작게 시작한다
- 필요할 때 점진적으로 확장한다

```java
// Bad
public class UserService {

    private final MySqlUserRepository repository = new MySqlUserRepository();

    public User find(long id) {
        return repository.findById(id);
    }
}
```

- DB에 강하게 결합됨
- 테스트 시 실제 DB 필요
- 아키텍처가 이미 고정됨

```java
// Good
public interface UserRepository {
    User findById(long id);
}
```

```java
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User find(long id) {
        return repository.findById(id);
    }
}
```

```java
// 테스트
class FakeUserRepository implements UserRepository {
    public User findById(long id) {
        return new User(id, "test");
    }
}
```

- 테스트가 쉬워진다
- 아키텍처가 유연해진다
- DB → API → 캐시 변경 가능

## 2. 의사결정을 최적화하라 (Optimize Decision Making)

> **결정을 가능한 한 늦춰라. 지금 당장 필요한 것만 결정하라**

- 너무 이른 결정은 잘못될 가능성이 높다
- 변경 비용이 커진다
- 시스템이 불필요하게 복잡해진다

**핵심 의도:**

- “지금 필요한 최소한”만 결정한다
- 나중에 바꿀 수 있도록 구조를 유지한다

```java
// Bad - 너무 이른 결정
public class PaymentService {

    private final StripePaymentGateway gateway = new StripePaymentGateway();

    public void pay(int amount) {
        gateway.pay(amount);
    }
}
```

- 결제 수단이 Stripe로 고정됨
- 요구사항 변경 시 수정 필요

```java
// Good
public interface PaymentGateway {
    void pay(int amount);
}
```

```java
public class PaymentService {

    private final PaymentGateway gateway;

    public PaymentService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void pay(int amount) {
        gateway.pay(amount);
    }
}
```

- 결제 방식 변경 가능
- 나중에 결정 가능
- 시스템 유연성 증가

## 3. 명백한 가치가 있을 때 표준을 현명하게 사용하라 (Use Standards Wisely, When They Add DemonstrableValue)

> **표준을 “좋아 보이기 때문에” 쓰지 말고, 실제로 도움이 될 때만 써라**

- 표준은 복잡성을 증가시킨다
- 잘못된 표준 선택은 큰 비용을 만든다
- 필요 없는 추상화는 오히려 설계를 망친다

**핵심 의도:**

- 표준은 “문제 해결”에 도움이 될 때만 사용
- 도입 비용 vs 이익을 항상 비교

```java
// Bad - 과도한 표준 적용
public interface UserService {
    User find(long id);
}
```

```java
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User find(long id) {
        return repository.findById(id);
    }
}
```

- 인터페이스가 실제로 필요하지 않음
- 구현이 하나뿐인데도 추상화
- 불필요한 복잡성 증가

```java
// Good
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User find(long id) {
        return repository.findById(id);
    }
}
```

- 단순함 유지
- 필요할 때만 인터페이스 도입

## 4. 시스템은 도메인 특화 언어가 필요하다 (Systems Need Domain-Specific Languages)

> **코드는 도메인을 표현하는 언어처럼 읽혀야 한다**

- 좋은 시스템은 “무엇을 하는지”가 코드에서 드러난다
- 기술 용어보다 도메인 용어가 중요하다
- DSL은 의도를 명확하게 표현한다

**핵심 의도:**

- 코드가 도메인 언어처럼 읽히게 만든다
- 추상화 수준을 높인다
- 비즈니스 규칙을 명확하게 표현한다

```java
// Bad
order.setStatus(1);
order.setType(2);
order.process();
```

- 숫자 의미를 알 수 없음
- 도메인 의미가 사라짐

```java
// Good
order.markAsPaid();
order.markAsExpress();
order.process();
```

- 코드 자체가 의미를 전달한다
- 읽기 쉬움
- 도메인 의도가 명확

```java
// Bad
if (user.getRole().equals("ADMIN") && user.getAge() > 18) {
    // ...
}
```

```java
// Good
if (user.isAdultAdmin()) {
    // ...
}
```

- 조건이 도메인 개념으로 추상화됨
- 읽는 사람이 의도를 바로 이해

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드  | 좋은 코드         |
| ----- | ------ | ------------- |
| 아키텍처  | 고정된 구조 | 테스트 가능한 유연 구조 |
| 의사결정  | 초기 확정  | 지연 결정         |
| 표준 사용 | 무조건 적용 | 필요할 때만 적용     |
| 코드 표현 | 기술 중심  | 도메인 중심        |

## 핵심 원칙

**피해야 할 것:**

- 처음부터 완벽한 아키텍처 설계
- 너무 이른 기술 선택
- 표준을 무조건 사용하는 것
- 도메인 의미가 없는 코드 작성

**지켜야 할 것:**

- 테스트 가능한 구조로 시작한다
- 의사결정을 가능한 한 늦춘다
- 표준은 가치가 있을 때만 사용한다
- 코드가 도메인 언어처럼 읽히게 만든다
