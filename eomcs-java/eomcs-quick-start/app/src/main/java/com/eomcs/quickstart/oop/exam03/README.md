# Exam03 - 클래스 분리

## 개념

관련된 데이터(필드)와 기능(메서드)을 별도의 클래스로 분리한다.

- 역할에 따라 클래스를 분리하면 코드의 재사용성과 유지보수성이 높아진다.
- `App` 클래스는 프로그램의 흐름(입력/출력)을 담당하고, `Score` 클래스는 데이터와 계산 로직을 담당한다.

## Score - 데이터와 계산 로직을 담당하는 클래스

국어, 영어, 수학 점수를 스태틱 필드로 저장하고, 총점과 평균을 계산하는 스태틱 메서드를 제공한다.

```java
public class Score {

  static int kor;
  static int eng;
  static int math;

  static int sum() {
    return kor + eng + math;
  }

  static float aver() {
    return (float) sum() / 3;
  }
}
```

- 점수 데이터와 계산 로직을 한 클래스에 모아 응집도를 높인다.
- `App` 클래스에서 `Score.kor`, `Score.eng`, `Score.math`로 필드에 접근한다.
- `Score.sum()`, `Score.aver()`로 메서드를 호출한다.

## App - 입출력을 담당하는 클래스

`Scanner`로 점수를 입력받아 `Score` 클래스에 저장하고 결과를 출력한다.

```java
Scanner sc = new Scanner(System.in);

System.out.print("국어 점수? ");
Score.kor = sc.nextInt();

System.out.print("영어 점수? ");
Score.eng = sc.nextInt();

System.out.print("수학 점수? ");
Score.math = sc.nextInt();

sc.close();

System.out.printf("총점: %d%n", Score.sum());
System.out.printf("평균: %.1f%n", Score.aver());
```

- 입력받은 값을 `Score` 클래스의 스태틱 필드에 직접 저장한다.
- 계산은 `Score` 클래스의 메서드에 위임한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam03.App
  ```
