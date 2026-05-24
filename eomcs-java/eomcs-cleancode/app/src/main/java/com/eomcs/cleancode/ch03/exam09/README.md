# 오류 코드보다 예외를 사용하라! (Prefer Exceptions to Returning Error Codes)

> **오류 코드를 반환하지 말고 예외를 사용하라**

- 오류 코드를 반환하면 호출자가 매번 오류 여부를 확인해야 한다.
- 그 결과 정상 흐름과 오류 처리 흐름이 뒤섞인다.

## 예제 1

```java
// Bad: 오류 코드 반환
public int deletePage(Page page) {
    if (!page.exists()) {
        return ErrorCode.NOT_FOUND;
    }

    if (!page.hasPermission()) {
        return ErrorCode.PERMISSION_DENIED;
    }

    page.delete();
    return ErrorCode.OK;
}
```

```java
// 사용 예
int result = deletePage(page);

if (result == ErrorCode.OK) {
    logger.info("page deleted");
} else if (result == ErrorCode.NOT_FOUND) {
    logger.warn("page not found");
} else if (result == ErrorCode.PERMISSION_DENIED) {
    logger.error("permission denied");
}
```

- 호출자가 오류 코드를 반드시 확인해야 한다
- 정상 로직보다 오류 처리 코드가 더 커진다
- 오류 코드를 무시하기 쉽다
- 함수 호출이 깊어질수록 if 문이 계속 중첩된다

```java
// Good: 예외 사용
public void deletePage(Page page) throws Exception {
    if (!page.exists()) {
        throw new PageNotFoundException();
    }

    if (!page.hasPermission()) {
        throw new PermissionDeniedException();
    }

    page.delete();
}
```

```java
// 사용 예
try {
    deletePage(page);
    logger.info("page deleted");
} catch (PageNotFoundException e) {
    logger.warn("page not found");
} catch (PermissionDeniedException e) {
    logger.error("permission denied");
}
```

- 정상 흐름이 단순해진다
- 오류 처리가 한 곳에 모인다
- 오류를 무시하기 어렵다
- 코드의 의도가 명확해진다

## 예제 2

```java
// Bad: 오류 코드 방식
if (deletePage(page) == ErrorCode.OK) {
    if (registry.deleteReference(page.getName()) == ErrorCode.OK) {
        if (config.deleteKey(page.getKey()) == ErrorCode.OK) {
            logger.info("page deleted");
        } else {
            logger.error("config delete failed");
        }
    } else {
        logger.error("registry delete failed");
    }
} else {
    logger.error("page delete failed");
}
```

```java
// Good: 예외 방식
try {
    deletePage(page);
    registry.deleteReference(page.getName());
    config.deleteKey(page.getKey());

    logger.info("page deleted");
} catch (Exception e) {
    logger.error(e.getMessage());
}
```

- 오류 코드 방식은 흐름이 계속 중첩된다.
- 반면 예외 방식은 정상 흐름이 위에서 아래로 자연스럽게 읽힌다.

## 예제 3: Try/catch 블록 추출

> try/catch 블록은 원래 추하다. 따라서 try/catch 블록은 별도 함수로 추출하는 것이 좋다.

- try/catch는 코드 구조를 복잡하게 만든다.
- 따라서 정상 로직과 오류 처리 로직을 분리하는 것이 좋다.

```java
// Bad: try/catch 블록
public void delete(Page page) {
    try {
        deletePage(page);
        registry.deleteReference(page.getName());
        config.deleteKey(page.getKey());
    } catch (Exception e) {
        logger.error(e.getMessage());
    }
}
```

```java
// Good: try/catch 블록을 별도의 함수로 추출
public void delete(Page page) {
    try {
        deletePageAndAllReferences(page);
    } catch (Exception e) {
        logError(e);
    }
}

private void deletePageAndAllReferences(Page page) throws Exception {
    deletePage(page);
    registry.deleteReference(page.getName());
    config.deleteKey(page.getKey());
}

private void logError(Exception e) {
    logger.error(e.getMessage());
}
```

- delete()는 오류 처리 흐름을 담당한다
- deletePageAndAllReferences()는 실제 삭제 작업을 담당한다
- logError()는 오류 기록만 담당한다
- 즉, 각 함수가 하나의 일만 한다.

## 예제 4: Error Handling Is One Thing

> 오류 처리는 하나의 일이다

- 함수가 오류 처리를 한다면, 그 함수는 오류 처리만 해야 한다.
- **try 문으로 시작**해 **catch/finally 문으로 끝**나야 한다.

```java
// Bad: 오류 처리와 정상 로직이 혼합
public void saveUser(User user) {
    validate(user);

    try {
        userRepository.save(user);
    } catch (DatabaseException e) {
        logger.error(e.getMessage());
    }

    sendWelcomeEmail(user);
}
```

이 함수는 여러 일을 한다.

- 사용자 검증
- 저장
- 오류 처리
- 이메일 전송

특히 try/catch 중간에 정상 로직이 섞여 있어 흐름이 불명확하다.

```java
// Good
public void registerUser(User user) {
    try {
        saveValidUser(user);
        sendWelcomeEmail(user);
    } catch (DatabaseException e) {
        logDatabaseError(e);
    }
}

private void saveValidUser(User user) {
    validate(user);
    userRepository.save(user);
}

private void logDatabaseError(DatabaseException e) {
    logger.error(e.getMessage());
}
```

- registerUser()는 전체 흐름을 표현한다
- saveValidUser()는 검증 후 저장만 담당한다
- logDatabaseError()는 오류 처리만 담당한다

## 예제 5: The "Error.java" Dependency Magnet

> 오류 코드를 모아둔 Error 클래스는 의존성 자석이 된다

- 오류 코드를 상수나 enum으로 한 곳에 모아두면, 많은 클래스가 그 파일에 의존하게 된다.

```java
// Bad: 오류 코드를 모아둔 Error 클래스
public enum ErrorCode {
    OK,
    INVALID_USER,
    USER_NOT_FOUND,
    DATABASE_ERROR,
    PERMISSION_DENIED
}
```

```java
// 사용 예
public ErrorCode saveUser(User user) {
    if (user == null) {
        return ErrorCode.INVALID_USER;
    }

    try {
        userRepository.save(user);
        return ErrorCode.OK;
    } catch (Exception e) {
        return ErrorCode.DATABASE_ERROR;
    }
}
```

- 모든 코드가 ErrorCode에 의존한다
- 새로운 오류가 생길 때마다 ErrorCode를 수정해야 한다
- ErrorCode 수정이 여러 모듈에 영향을 준다
- 오류 코드 재사용으로 의미가 흐려질 수 있다

```java
// Good: 예외 클래스 분리
class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("Invalid user");
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}

class UserSaveException extends RuntimeException {
    public UserSaveException(Throwable cause) {
        super("Failed to save user", cause);
    }
}
```

```java
// 사용 예
public void saveUser(User user) {
    if (user == null) {
        throw new InvalidUserException();
    }

    try {
        userRepository.save(user);
    } catch (Exception e) {
        throw new UserSaveException(e);
    }
}
```

- 오류 의미가 예외 타입으로 드러난다
- 새로운 오류는 새 예외 클래스로 추가 가능하다
- 하나의 거대한 ErrorCode에 의존하지 않는다
- 오류 처리가 더 객체지향적이다

## 나쁜 코드 vs 좋은 코드

| 구분    | 오류 코드 방식      | 예외 방식      |
| ----- | ------------- | ---------- |
| 정상 흐름 | if문에 묻힘       | 명확함        |
| 오류 처리 | 호출자마다 반복      | catch로 분리  |
| 의존성   | ErrorCode에 집중 | 예외 클래스로 분산 |
| 확장성   | 낮음            | 높음         |
| 가독성   | 낮음            | 높음         |

## 핵심 원칙

피해야 할 것: 

- 오류 코드를 반환하는 함수
- ErrorCode.OK 검사 반복
- 거대한 Error.java, ErrorCode.java
- 정상 흐름과 오류 흐름 혼합

지켜야 할 것: 

- 오류 상황은 예외로 표현
- try/catch 블록은 함수로 추출
- 오류 처리는 하나의 책임으로 분리
- 오류 의미는 예외 타입으로 표현