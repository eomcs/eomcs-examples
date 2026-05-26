# Try-Catch-Finally 문부터 작성하라 (Write Your Try-Catch-Finally Statement First)

> **"예외 처리가 필요한 코드라면, try-catch-finally 구조를 먼저 설계하라"**

👉 즉,

- 먼저 오류 상황을 어떻게 처리할지 정의
- 그 다음에 정상 로직을 채운다

👉 왜 중요한가?

- 오류 처리를 나중에 붙이면 구조가 깨진다
- try-catch는 단순 문법이 아니라 프로그램 구조의 일부다
- 처음부터 작성하면 책임이 분리된 깔끔한 코드가 된다

## 예제 1

```java
// Bad - 함수 정의
public void readFile(String path) {
    FileInputStream input = new FileInputStream(path);

    byte[] data = new byte[input.available()];
    input.read(data);

    System.out.println(new String(data));

    input.close();
}
```

- 예외 처리가 전혀 없다
- 파일이 없으면 프로그램이 깨진다
- 리소스가 close되지 않을 수 있다
- 정상 흐름만 있고 실패 흐름이 고려되지 않았다

```java
// Good - 함수 정의 (구조 먼저 작성)
public void readFile(String path) {
    try {
        FileInputStream input = new FileInputStream(path);
        byte[] data = new byte[input.available()];
        input.read(data);
        System.out.println(new String(data));
        input.close();
    } catch (FileNotFoundException e) {
        System.out.println("파일을 찾을 수 없음");
    } catch (IOException e) {
        System.out.println("파일 읽기 오류");
    }
}
```

- 오류 처리 구조가 명확하다
- 실패 상황이 코드에 드러난다
- 예외 상황별로 대응 가능하다
- 프로그램이 안정적으로 동작한다

## 예제 2: 단계 별 코드 작성 예

```java
// Bad - 함수 정의
public void copyFile(String src, String dest) {
    FileInputStream input = new FileInputStream(src);
    FileOutputStream output = new FileOutputStream(dest);

    byte[] buffer = new byte[1024];
    int n;

    while ((n = input.read(buffer)) != -1) {
        output.write(buffer, 0, n);
    }

    input.close();
    output.close();
}
```

- try-catch가 없어서 예외 발생 시 중간에 깨진다
- 리소스 누수 발생 가능
- 코드가 "무조건 성공"을 가정하고 있다

### 1단계: 먼저 try-catch-finally 틀을 만든다

```java
public void copyFile(String src, String dest) {
    try {
        // 정상 처리
    } catch (IOException e) {
        // 오류 처리
    } finally {
        // 정리 작업
    }
}
```

- 이 단계에서는 아직 복사 로직이 없다.
- 먼저 성공 흐름 / 실패 흐름 / 정리 흐름을 나눈다.

### 2단계: 리소스 변수를 finally에서 접근 가능하게 둔다

```java
public void copyFile(String src, String dest) {
    FileInputStream input = null;
    FileOutputStream output = null;

    try {
        // 정상 처리
    } catch (IOException e) {
        // 오류 처리
    } finally {
        // 정리 작업
    }
}
```

- input, output을 try 안에서만 선언하면 finally에서 닫을 수 없다.
- 그래서 리소스 생명주기를 먼저 고려한다.
- **try with resources 문법이 없는 시절에는 이런 패턴이 일반적이었다.**

### 3단계: try 안에 정상 흐름을 넣는다

```java
public void copyFile(String src, String dest) {
    FileInputStream input = null;
    FileOutputStream output = null;

    try {
        input = new FileInputStream(src);
        output = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int n;

        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    } catch (IOException e) {
        // 오류 처리
    } finally {
        // 정리 작업
    }
}
```

- 이제야 실제 파일 복사 로직을 넣는다.

### 4단계: catch에 오류 처리를 넣는다

```java
public void copyFile(String src, String dest) {
    FileInputStream input = null;
    FileOutputStream output = null;

    try {
        input = new FileInputStream(src);
        output = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int n;

        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    } catch (IOException e) {
        System.out.println("파일 처리 중 오류가 발생했습니다.");
    } finally {
        // 정리 작업
    }
}
```

- 오류 처리를 나중에 억지로 붙이는 것이 아니라, 처음부터 이 위치를 확보해둔다.

### 5단계: finally에 정리 작업을 넣는다

```java
public void copyFile(String src, String dest) {
    FileInputStream input = null;
    FileOutputStream output = null;

    try {
        input = new FileInputStream(src);
        output = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int n;

        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    } catch (IOException e) {
        System.out.println("파일 처리 중 오류가 발생했습니다.");
    } finally {
        try {
            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            System.out.println("리소스 해제 중 오류가 발생했습니다.");
        }
    }
}
```

- 이제 파일 복사 중 예외가 발생해도 리소스 해제를 시도한다.

### 6단계: Clean Code식으로 역할을 분리한다

```java
public void copyFile(String src, String dest) {
    FileInputStream input = null;
    FileOutputStream output = null;

    try {
        input = openInput(src);
        output = openOutput(dest);

        copy(input, output);
    } catch (IOException e) {
        handleCopyError(e);
    } finally {
        close(input);
        close(output);
    }
}

private FileInputStream openInput(String src) throws FileNotFoundException {
    return new FileInputStream(src);
}

private FileOutputStream openOutput(String dest) throws FileNotFoundException {
    return new FileOutputStream(dest);
}

private void copy(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[1024];
    int n;

    while ((n = input.read(buffer)) != -1) {
        output.write(buffer, 0, n);
    }
}

private void handleCopyError(IOException e) {
    System.out.println("파일 처리 중 오류가 발생했습니다.");
}

private void close(Closeable resource) {
    try {
        if (resource != null) {
            resource.close();
        }
    } catch (IOException e) {
        System.out.println("리소스 해제 중 오류가 발생했습니다.");
    }
}
```

## 예제 3: 구조를 먼저 만든 후 구현

> 다음과 같이 틀을 먼저 잡고, 그 안에 구현을 채워 넣는 것이 **“Write Your Try-Catch-Finally Statement First”**의 의미다.

```java
// Good - 구조 먼저 작성
public void process() {
    try {
        doSomething();
    } catch (Exception e) {
        handleError(e);
    } finally {
        cleanup();
    }
}

private void doSomething() {
    // 실제 비즈니스 로직
}

private void handleError(Exception e) {
    // 오류 처리
}

private void cleanup() {
    // 리소스 정리
}
```

- 코드가 역할별로 완전히 분리된다
- 테스트가 쉬워진다
- 각 메서드가 단일 책임을 가진다
- 구조가 먼저 잡혀 있어 확장이 쉽다

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드          | 좋은 코드         |
| ------ | -------------- | ------------- |
| 설계 순서  | 기능 → 나중에 예외 처리 | 예외 구조 → 기능 구현 |
| 안정성    | 예외 발생 시 깨짐     | 예외 처리 보장      |
| 리소스 관리 | 누수 가능          | finally로 보장   |
| 가독성    | 흐름이 불명확        | 구조가 명확        |
| 유지보수   | 수정 시 위험        | 역할 분리로 안정     |

## 핵심 원칙

피해야 할 것:

- try-catch를 나중에 붙이는 방식
- 예외 처리를 고려하지 않고 기능만 먼저 작성하는 것
- 리소스 해제를 보장하지 않는 코드
- 모든 로직을 하나의 try 블록에 몰아넣는 것

지켜야 할 것:

- try-catch-finally 구조를 먼저 설계한다
- 정상 흐름 / 오류 처리 / 정리 작업을 분리한다
- finally로 리소스 해제를 보장한다
- 각 역할을 별도의 메서드로 분리한다
- 예외 처리는 “구조”로 다룬다 (단순 문법이 아님)