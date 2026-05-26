# 오류 코드보다 예외를 사용하라 (Use Exceptions Rather Than Return Codes)

> **오류가 발생했을 때 true/false, -1, E_OK, ERROR_CODE 같은 반환 코드로 실패를 알리지 말고 예외를 던져라.**

반환 코드를 사용하면, 

- 호출자가 오류를 즉시 처리하게 만들어 중첩 구조를 유발한다.
- 즉 호출자가 즉시 오류를 검사해야 하므로 정상 흐름과 오류 처리 흐름이 뒤섞인다. 

예외를 사용하면,

- “happy path”와 오류 처리 코드를 분리할 수 있다.
- 즉, 정상 흐름은 그대로 읽히고, 
- 오류 처리는 catch 블록으로 분리된다. 
- 👉 코드가 “이야기처럼 읽히게” 된다

## 예제 1

```java
// Bad - 함수 정의
public int deletePage(Page page) {
    if (page == null) {
        return E_NOT_FOUND;
    }

    if (!page.delete()) {
        return E_DELETE_FAILED;
    }

    return E_OK;
}
```

```java
// Bad - 함수 사용
int result = pageService.deletePage(page);

if (result == E_OK) {
    System.out.println("삭제 성공");
} else if (result == E_NOT_FOUND) {
    System.out.println("페이지 없음");
} else if (result == E_DELETE_FAILED) {
    System.out.println("삭제 실패");
}
```

- 정상 흐름보다 오류 처리 코드가 더 많다
- if/else 분기가 계속 늘어난다
- 호출자가 내부 구현(E_NOT_FOUND 등)을 알아야 한다
- 결과를 체크하지 않으면 오류가 무시된다

```java
// Good - 함수 정의
public void deletePage(Page page) {
    if (page == null) {
        throw new PageNotFoundException();
    }

    if (!page.delete()) {
        throw new PageDeleteFailedException();
    }
}
```

```java
// Good - 함수 사용
try {
    pageService.deletePage(page);
    System.out.println("삭제 성공");
} catch (PageNotFoundException e) {
    System.out.println("페이지 없음");
} catch (PageDeleteFailedException e) {
    System.out.println("삭제 실패");
}
```

- 정상 흐름이 먼저 읽힌다
- 오류 처리 로직이 분리되어 있다
- 호출 코드가 비즈니스 흐름 중심으로 표현된다
- 예외 타입으로 실패 원인이 명확하다

## 예제 2

```java
// Bad - 함수 정의
public boolean withdraw(Account account, int amount) {
    if (account.getBalance() < amount) {
        return false;
    }

    account.decreaseBalance(amount);
    return true;
}
```

```java
// Bad - 함수 사용
if (withdraw(account, 10000)) {
    receipt.print();
} else {
    System.out.println("실패");
}
```

- 실패 원인이 전혀 드러나지 않는다
- 단순 성공/실패로만 표현된다
- 다양한 오류 상황을 확장하기 어렵다
- 호출 코드에서 의미 해석이 필요하다

```java
// Good - 함수 정의
public void withdraw(Account account, int amount) {
    if (account.getBalance() < amount) {
        throw new InsufficientBalanceException();
    }

    account.decreaseBalance(amount);
}
```

```java
// Good - 함수 사용
try {
    withdraw(account, 10000);
    receipt.print();
} catch (InsufficientBalanceException e) {
    System.out.println("잔액 부족");
}
```

- 실패 원인이 명확하게 표현된다
- 다양한 예외로 확장 가능하다
- 호출 코드가 단순하고 읽기 쉽다
- 정상 흐름이 중심이 된다

## 예제 3

```java
// Bad - 함수 정의
public ErrorCode register(User user) {
    if (user == null) {
        return ErrorCode.INVALID_USER;
    }

    if (userRepository.existsByEmail(user.getEmail())) {
        return ErrorCode.DUPLICATED_EMAIL;
    }

    return ErrorCode.OK;
}
```

```java
// Bad - 함수 사용
ErrorCode result = userService.register(user);

if (result == ErrorCode.OK) {
    System.out.println("회원가입 성공");
} else if (result == ErrorCode.INVALID_USER) {
    System.out.println("잘못된 사용자");
} else if (result == ErrorCode.DUPLICATED_EMAIL) {
    System.out.println("중복 이메일");
}
```

- ErrorCode에 강하게 의존한다
- 조건 분기가 계속 증가한다
- 호출 코드가 점점 비대해진다
- 내부 정책이 외부로 노출된다

```java
// Good
// Good - 함수 정의
public void register(User user) {
    validateUser(user);
    validateEmail(user);
}

private void validateUser(User user) {
    if (user == null) {
        throw new InvalidUserException();
    }
}

private void validateEmail(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
        throw new DuplicatedEmailException();
    }
}
```

```java
// Good - 함수 사용
try {
    userService.register(user);
    System.out.println("회원가입 성공");
} catch (InvalidUserException e) {
    System.out.println("잘못된 사용자");
} catch (DuplicatedEmailException e) {
    System.out.println("중복 이메일");
}
```

- 오류 처리 책임이 명확히 분리된다
- 예외 단위로 확장 가능하다
- 호출 코드가 간결해진다
- 내부 로직이 외부에 노출되지 않는다

## 나쁜 코드 vs 좋은 코드

| 구분       | 나쁜 코드       | 좋은 코드         |
| -------- | ----------- | ------------- |
| 함수 사용 방식 | 결과값을 해석해야 함 | 예외로 흐름 제어     |
| 호출 코드    | if/else 난무  | try-catch로 분리 |
| 가독성      | 흐름이 끊김      | 이야기처럼 읽힘      |
| 오류 표현    | 숫자/enum     | 의미 있는 예외      |
| 유지보수     | 수정 시 영향 큼   | 변경 영향 최소      |


## 핵심 원칙

피해야 할 것:

- 반환 코드 기반 오류 처리
- boolean으로 성공/실패 표현
- 호출자에게 오류 해석 책임을 넘기는 구조
- 정상 흐름과 오류 흐름이 섞인 코드

지켜야 할 것:

- 오류는 예외로 표현한다
- 정상 흐름을 중심으로 코드 작성
- 오류 처리 로직은 분리한다
- 예외 이름으로 의미를 전달한다
- 호출 코드는 “비즈니스 흐름”만 드러나게 만든다
