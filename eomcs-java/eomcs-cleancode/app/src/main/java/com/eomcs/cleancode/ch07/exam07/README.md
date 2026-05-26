# null을 반환하지 마라 (Don’t Return Null)

> **null을 반환하지 마라. 대신 예외를 던지거나, 빈 객체를 반환하라**

👉 이유:

- null은 오류를 숨긴다
- null은 NullPointerException을 유발한다
- 호출자가 항상 null 체크를 강제당한다

👉 Clean Code:

- null은 “값이 없다”가 아니라
- **“처리되지 않은 상태” 또는 “설계 결함”** 일 가능성이 크다
- 따라서 더 명확한 방식으로 표현해야 한다

## 예제 1

```java
// Bad - 함수 정의
public List<Employee> getEmployees() {
    if (repository.isEmpty()) {
        return null;
    }

    return repository.findAll();
}
```

```java
// Bad - 함수 사용
List<Employee> employees = employeeService.getEmployees();

if (employees != null) {
    for (Employee e : employees) {
        System.out.println(e.getName());
    }
}
```

- 호출자가 항상 null 체크를 해야 한다
- null 체크를 깜빡하면 NPE 발생
- “직원이 없음”과 “오류”를 구분할 수 없다
- 코드가 방어 코드로 더러워진다

```java
// Good - 함수 정의
public List<Employee> getEmployees() {
    if (repository.isEmpty()) {
        return Collections.emptyList();
    }

    return repository.findAll();
}
```

```java
// Good - 함수 사용
List<Employee> employees = employeeService.getEmployees();

for (Employee e : employees) {
    System.out.println(e.getName());
}
```

- null 체크가 필요 없다
- 빈 리스트도 정상 흐름으로 처리된다
- 코드가 훨씬 간결해진다
- 안전하다 (NPE 없음)

## 예제 2

```java
// Bad - 함수 정의
public User findUser(Long id) {
    return userRepository.findById(id); // 없으면 null
}
```

```java
// Bad - 함수 사용
User user = userService.findUser(1L);

if (user != null) {
    System.out.println(user.getName());
}
```

- user가 없는 이유를 알 수 없다
- null 체크를 매번 해야 한다
- 실수로 null 체크를 빼면 바로 오류 발생

```java
// Good - 함수 정의
public User findUser(Long id) {
    User user = userRepository.findById(id);

    if (user == null) {
        throw new UserNotFoundException("사용자 없음. id=" + id);
    }

    return user;
}
```

```java
// Good - 함수 사용
try {
    User user = userService.findUser(1L);
    System.out.println(user.getName());
} catch (UserNotFoundException e) {
    System.out.println("사용자 없음");
}
```

- 실패 원인이 명확하다
- null 대신 의미 있는 예외 사용
- 호출자가 의도적으로 처리할 수 있다

## 예제 3

```java
// Bad - 함수 정의
public DiscountPolicy getDiscountPolicy(Customer customer) {
    return discountRepository.find(customer); // 없으면 null
}
```

```java
// Bad - 함수 사용
DiscountPolicy policy = getDiscountPolicy(customer);

if (policy != null) {
    price = policy.apply(price);
}
```

- 할인 정책 없음이라는 상황이 코드에 퍼져 있다
- if문이 계속 증가한다
- 정책 로직이 호출자에 흩어진다

```java
// Good - Special Case Pattern 적용
public DiscountPolicy getDiscountPolicy(Customer customer) {
    DiscountPolicy policy = discountRepository.find(customer);

    if (policy == null) {
        return new NoDiscountPolicy();
    }

    return policy;
}
```

```java
// Special Case
public class NoDiscountPolicy implements DiscountPolicy {
    @Override
    public int apply(int price) {
        return price;
    }
}
```

```java
// Good - 함수 사용
DiscountPolicy policy = getDiscountPolicy(customer);
price = policy.apply(price);
```

- null 대신 “의미 있는 객체” 반환
- 조건문 제거
- 다형성으로 문제 해결
- 코드가 훨씬 자연스럽게 흐름

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드      | 좋은 코드     |
| ----- | ---------- | --------- |
| 반환값   | null       | 빈 객체 / 예외 |
| 안정성   | NPE 위험     | 안전        |
| 호출 코드 | null 체크 필요 | 바로 사용 가능  |
| 가독성   | if문 증가     | 단순 흐름     |
| 의미 표현 | 불명확        | 명확        |

## 핵심 원칙

피해야 할 것:

- null 반환
- null 체크를 호출자에게 강제하는 설계
- null로 여러 의미를 표현하는 것
- “없음”을 null로 표현하는 것

지켜야 할 것:

- 컬렉션은 빈 객체를 반환한다 (emptyList)
- 반드시 존재해야 하는 값은 예외를 던진다
- 특수 상황은 Special Case 객체로 표현한다
- 호출 코드에서 null 체크를 제거한다
- 코드 흐름을 단순하게 유지한다