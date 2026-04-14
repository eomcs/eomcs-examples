# Exam11 - 스태틱/논스태틱 중첩 클래스, 로컬 클래스, 익명 클래스, 람다, 메서드 레퍼런스

## 개념

`FileFilter` 인터페이스를 구현하는 클래스를 다양한 방식으로 정의하는 방법을 학습한다.

- **톱레벨 클래스**: 독립적인 파일로 정의. 여러 곳에서 재사용할 때 적합하다.
- **스태틱 중첩 클래스**: 바깥 클래스 안에 `static`으로 정의. 바깥 클래스와 관련이 있지만 독립적으로 사용 가능하다.
- **논스태틱 중첩 클래스(이너 클래스)**: 바깥 클래스의 인스턴스에 종속. `인스턴스.new 클래스명()`으로 생성한다.
- **로컬 클래스**: 메서드 블록 안에 정의. 해당 메서드 내에서만 사용 가능하다.
- **익명 클래스**: 이름 없이 인터페이스 구현과 인스턴스 생성을 동시에 처리한다.
- **람다**: `FileFilter`가 함수형 인터페이스이므로 익명 클래스를 람다로 대체할 수 있다.
- **메서드 레퍼런스**: 람다 대신 기존 메서드를 참조하여 전달한다.

## MyFileFilter - 톱레벨 클래스

```java
public class MyFileFilter implements FileFilter {
  @Override
  public boolean accept(File file) {
    return file.isFile();
  }
}
```

- 독립적인 파일로 정의한 `FileFilter` 구현 클래스이다.
- 파일인 경우만 `true`를 반환하여 디렉토리를 걸러낸다.

## App - 톱레벨 클래스 사용

```java
File[] files = dir.listFiles(new MyFileFilter());
```

- 별도 파일에 정의된 `MyFileFilter`를 `new`로 생성하여 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App
  ```

## App2 - 스태틱 중첩 클래스 사용

```java
public class App2 {

  static class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
      return file.isFile();
    }
  }

  public static void main(String[] args) {
    File[] files = dir.listFiles(new MyFileFilter());
  }
}
```

- `MyFileFilter`를 `App2` 안에 `static class`로 정의한다.
- 바깥 클래스 인스턴스 없이 `new MyFileFilter()`로 생성할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App2
  ```

## App3 - 논스태틱 중첩 클래스(이너 클래스) 사용

```java
public class App3 {

  class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
      return file.isFile();
    }
  }

  public static void main(String[] args) {
    App3 app = new App3();
    File[] files = dir.listFiles(app.new MyFileFilter());
  }
}
```

- `static` 없이 중첩 클래스를 정의하면 이너 클래스가 된다.
- 이너 클래스는 바깥 클래스의 인스턴스에 종속되므로 `app.new MyFileFilter()`처럼 인스턴스를 통해 생성해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App3
  ```

## App4 - 로컬 클래스 사용

```java
public static void main(String[] args) {

  class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
      return file.isFile();
    }
  }

  File[] files = dir.listFiles(new MyFileFilter());
}
```

- 메서드 블록 안에 클래스를 정의한다.
- 해당 메서드 내에서만 사용할 수 있으며, `new MyFileFilter()`로 바로 생성 가능하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App4
  ```

## App5 - 익명 클래스를 변수에 저장하여 사용

```java
FileFilter filter = new FileFilter() {
  @Override
  public boolean accept(File file) {
    return file.isFile();
  }
};

File[] files = dir.listFiles(filter);
```

- 익명 클래스를 `FileFilter` 타입 변수에 저장한 후 전달한다.
- 클래스 이름 없이 인터페이스 구현과 인스턴스 생성을 동시에 처리한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App5
  ```

## App6 - 익명 클래스를 인라인으로 사용

```java
File[] files = dir.listFiles(new FileFilter() {
  @Override
  public boolean accept(File file) {
    return file.isFile();
  }
});
```

- 익명 클래스를 변수에 저장하지 않고 `listFiles()`의 인수 자리에 바로 정의한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App6
  ```

## App7 - 람다 사용

```java
File[] files = dir.listFiles(file -> file.isFile());
```

- `FileFilter`는 추상 메서드가 하나인 함수형 인터페이스이므로 람다로 대체할 수 있다.
- `FileFilter` import가 필요 없어지고 코드가 대폭 간결해진다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App7
  ```

## App8 - 스태틱 메서드 레퍼런스 사용

```java
static boolean isFile(File file) {
  return file.isFile();
}

File[] files = dir.listFiles(App8::isFile);
```

- 람다 대신 기존 스태틱 메서드를 `클래스명::메서드명` 형식으로 참조하여 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App8
  ```

## App9 - 인스턴스 메서드 레퍼런스 사용

```java
boolean isFile(File file) {
  return file.isFile();
}

public static void main(String[] args) {
  App9 app = new App9();
  File[] files = dir.listFiles(app::isFile);
}
```

- 인스턴스 메서드를 `인스턴스변수::메서드명` 형식으로 참조하여 전달한다.
- 스태틱 메서드 레퍼런스(`App8::isFile`)와 달리 먼저 인스턴스를 생성해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App9
  ```
