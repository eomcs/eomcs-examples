# 구조적 프로그래밍 (Structured Programming)

> **구조적 프로그래밍은 함수와 블록이 하나의 입구와 하나의 출구를 가져야 한다는 원칙이다.**

즉, 전통적인 구조적 프로그래밍에서는 다음을 권장한다.

- 함수에는 `return`이 하나만 있어야 한다
- 반복문 안에서 `break`, `continue`를 피한다
- `goto`는 사용하지 않는다

## 예제 1

```java
// Bad: 여러 곳에서 return
public boolean isValidUser(User user) {
    if (user == null) {
        return false;
    }

    if (user.getName() == null) {
        return false;
    }

    if (!user.isActive()) {
        return false;
    }

    return true;
}
```

- 구조적 프로그래밍 관점에서는 return이 여러 개이므로 좋지 않다고 볼 수 있다.
- 하지만 이 함수는 작고 단순하다.
- 따라서 여러 return이 오히려 읽기 쉽다.

```java
// Good: 구조적 프로그래밍 방식 (단일 return)
public boolean isValidUser(User user) {
    boolean valid = true;

    if (user == null) {
        valid = false;
    } else if (user.getName() == null) {
        valid = false;
    } else if (!user.isActive()) {
        valid = false;
    }

    return valid;
}
```

- return은 하나만 존재한다.
- 하지만 코드가 더 간접적이다.
    - valid 변수를 계속 추적해야 한다
    - 조건 흐름이 더 복잡해 보인다
    - 작은 함수에서는 오히려 가독성이 떨어질 수 있다

### Clean Code의 관점

Clean Code에서는 구조적 프로그래밍의 원칙을 존중하지만, 함수가 충분히 작다면 여러 `return`, `break`, `continue`가 큰 문제가 되지 않는다고 본다.

즉, 핵심은 이것이다.

> 함수를 작게 유지하면 구조적 프로그래밍 규칙의 엄격함이 덜 중요해진다.

## 예제 2: break

```java
// Bad: 불필요하게 복잡한 단일 출구 구조
public User findActiveUser(List<User> users) {
    User result = null;
    boolean found = false;

    for (User user : users) {
        if (!found && user.isActive()) {
            result = user;
            found = true;
        }
    }

    return result;
}
```

```java
// Good: 작은 함수에서는 break/return 허용
public User findActiveUser(List<User> users) {
    for (User user : users) {
        if (user.isActive()) {
            return user;
        }
    }

    return null;
}
```

- 두 번째 코드가 더 읽기 쉽다.
- 활성 사용자를 찾으면 바로 반환
- 불필요한 상태 변수 없음
- 의도가 명확함

## 예제 3: continue

```java
// Bad: 중첩 증가
public void printActiveUsers(List<User> users) {
    for (User user : users) {
        if (user.isActive()) {
            if (user.hasEmail()) {
                System.out.println(user.getEmail());
            }
        }
    }
}
```

```java
// Good: continue로 흐름 단순화
public void printActiveUsers(List<User> users) {
    for (User user : users) {
        if (!user.isActive()) {
            continue;
        }

        if (!user.hasEmail()) {
            continue;
        }

        System.out.println(user.getEmail());
    }
}
```

- continue를 사용하면 중첩을 줄일 수 있다.
- 함수가 작다면 이런 방식이 더 명확할 수 있다.

## 예제 4: goto

```java
// Bad
outer:
for (Order order : orders) {
    for (Item item : order.getItems()) {
        if (item.isInvalid()) {
            break outer;
        }
    }
}
```

Clean Code에서는 goto는 피해야 한다.

- 흐름을 예측하기 어렵다
- 코드 추적이 어려워진다
- 함수가 커질수록 혼란이 심해진다

현대 Java에는 일반적인 goto가 없지만, 복잡한 라벨 break도 비슷한 문제를 만들 수 있다.

```java
// Good
public boolean hasInvalidItem(List<Order> orders) {
    for (Order order : orders) {
        if (hasInvalidItem(order)) {
            return true;
        }
    }

    return false;
}

private boolean hasInvalidItem(Order order) {
    for (Item item : order.getItems()) {
        if (item.isInvalid()) {
            return true;
        }
    }

    return false;
}
```

## 나쁜 코드 vs 좋은 코드

| 구분       | 엄격한 구조적 프로그래밍 | Clean Code 관점         |
| -------- | ------------- | --------------------- |
| return   | 하나만 사용        | 작은 함수에서는 여러 return 허용 |
| break    | 피하는 편         | 작은 함수에서는 허용 가능        |
| continue | 피하는 편         | 중첩을 줄이면 허용 가능         |
| goto     | 피해야 함         | 피해야 함                 |
| 핵심 기준    | 단일 입구/출구      | 작고 읽기 쉬운 함수           |

## 핵심 원칙

피해야 할 것: 

- 큰 함수에서 여러 흐름이 뒤섞이는 것
- 복잡한 중첩
- 불필요한 상태 변수
- goto 스타일의 흐름 제어

지켜야 할 것: 

- 함수는 작게 유지
- 흐름은 단순하게 유지
- 여러 return이 더 읽기 쉽다면 사용 가능
- goto는 피하기