# Exam01 - Entry Point

## 개념

자바 프로그램의 진입점(entry point)은 JVM이 프로그램 실행 시 가장 먼저 호출하는 메서드이다. entry point 메서드의 시그니처는 다음과 같다:

```java
public static void main(String[] args)
```

## 클래스에서의 Entry Point

`App` 클래스는 일반적인 방식으로 entry point 메서드를 정의한다.

```java
public class App {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}
```

- `public`: JVM이 외부에서 호출할 수 있도록 공개 접근 제어자를 사용한다.
- `static`: 객체 생성 없이 JVM이 직접 호출할 수 있도록 정적 메서드로 선언한다.
- `void`: 반환값이 없다.
- `String[] args`: 커맨드라인 인수를 문자열 배열로 전달받는다.

## 인터페이스에서의 Entry Point

`App2` 인터페이스는 Java 8부터 지원하는 인터페이스의 정적 메서드(`static method`) 기능을 활용하여 entry point 메서드를 정의한다.

```java
public interface App2 {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}
```

- Java 8부터 인터페이스에 `static` 메서드를 정의할 수 있다.
- `main` 메서드의 시그니처가 동일하므로 인터페이스에 정의된 `static main` 메서드도 JVM의 entry point로 사용할 수 있다.
- 클래스와 달리 인터페이스의 정적 메서드는 상속되지 않는다.

## 실무

- 일반적으로 entry point 메서드는 클래스에 정의한다. 인터페이스에 entry point 메서드를 정의하는 것은 드물며, 주로 테스트나 예제 코드를 만들 때 가끔 사용된다. 
- 클래스에 entry point 메서드를 정의하면 필드나 구현 메서드를 함께 사용할 수 있어 더 유연하게 프로그램을 작성할 수 있다.