# Exam04 - 인스턴스와 인스턴스 필드

## 개념

스태틱 필드를 인스턴스 필드로 변경하면 여러 명의 성적을 독립적으로 다룰 수 있다.

- **스태틱 필드**: 클래스에 하나만 존재하므로 여러 학생의 점수를 동시에 저장할 수 없다.
- **인스턴스 필드**: `new` 연산자로 객체를 생성할 때마다 별도의 메모리가 할당되므로 여러 학생의 점수를 독립적으로 저장할 수 있다.
- 스태틱 메서드는 인스턴스 필드에 직접 접근할 수 없으므로, `Score` 인스턴스를 매개변수로 전달받아 접근한다.

## Score - 인스턴스 필드와 스태틱 메서드를 사용하는 클래스

```java
public class Score {

  int kor;
  int eng;
  int math;

  static int sum(Score s) {
    return s.kor + s.eng + s.math;
  }

  static float aver(Score s) {
    return (float) sum(s) / 3;
  }
}
```

- `kor`, `eng`, `math`를 인스턴스 필드로 선언하여 객체마다 독립적인 저장공간을 갖는다.
- `sum(Score s)`, `aver(Score s)`는 스태틱 메서드를 유지하되, `Score` 인스턴스를 매개변수로 받아 인스턴스 필드에 접근한다.

## App - 1명의 점수를 입력받아 출력하는 클래스

`new Score()`로 인스턴스를 생성하고 점수를 입력받아 총점과 평균을 출력한다.

```java
Score score = new Score();

System.out.print("국어 점수? ");
score.kor = sc.nextInt();

System.out.print("영어 점수? ");
score.eng = sc.nextInt();

System.out.print("수학 점수? ");
score.math = sc.nextInt();

System.out.printf("총점: %d%n", Score.sum(score));
System.out.printf("평균: %.1f%n", Score.aver(score));
```

- `new Score()`로 인스턴스를 생성한다.
- 입력받은 점수를 인스턴스 필드에 저장한다.
- `Score.sum(score)`, `Score.aver(score)`에 인스턴스를 전달하여 계산한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam04.App
  ```

## App2 - 3명의 점수를 입력받아 출력하는 클래스

`Score` 인스턴스를 3개 생성하여 각 학생의 점수를 독립적으로 저장하고, 모두 입력받은 후 한 번에 출력한다.

```java
Score score1 = new Score();
Score score2 = new Score();
Score score3 = new Score();

// 3명의 점수 입력 ...

System.out.printf("1번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score1), Score.aver(score1));
System.out.printf("2번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score2), Score.aver(score2));
System.out.printf("3번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score3), Score.aver(score3));
```

- 인스턴스마다 별도의 메모리가 할당되므로 3명의 데이터를 동시에 보관할 수 있다.
- 스태틱 필드였다면 마지막으로 입력한 학생의 점수만 남아 3명의 데이터를 동시에 다룰 수 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam04.App2
  ```
