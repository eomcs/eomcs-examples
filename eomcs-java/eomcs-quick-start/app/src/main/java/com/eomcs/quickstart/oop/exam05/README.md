# Exam05 - 인스턴스 메서드와 this 빌트인 변수

## 개념

인스턴스 메서드는 빌트인 변수 `this`를 통해 자신에게 전달된 인스턴스의 필드에 접근할 수 있다.

- 인스턴스 메서드는 `클래스명.메서드명()` 대신 `인스턴스변수.메서드명()`으로 호출한다.
- 인스턴스는 메서드를 호출할 때 인스턴스 메서드의 빌트인 변수 `this`에 자동으로 전달된다.
- **스태틱 메서드**: `this`가 없으므로 인스턴스를 매개변수로 전달받아야 한다.
- **인스턴스 메서드**: 빌트인 변수 `this`에 대상 인스턴스 레퍼런스가 들어 있으므로 이 레퍼런스를 통해 인스턴스 필드에 직접 접근할 수 있다.

## Score - 인스턴스 필드와 인스턴스 메서드를 사용하는 클래스

```java
public class Score {

  int kor;
  int eng;
  int math;

  int sum() {
    return this.kor + this.eng + this.math;
  }

  float aver() {
    return (float) this.sum() / 3;
  }
}
```

- `sum()`, `aver()`를 인스턴스 메서드로 선언하여 매개변수 없이 빌트인 변수 `this`를 통해 인스턴스 필드에 직접 접근한다.
- exam04의 `Score.sum(score)` 호출 방식에서 `score.sum()` 호출 방식으로 단순해진다.

## App - 1명의 점수를 입력받아 출력하는 클래스

```java
Score score = new Score();

System.out.print("국어 점수? ");
score.kor = sc.nextInt();

System.out.print("영어 점수? ");
score.eng = sc.nextInt();

System.out.print("수학 점수? ");
score.math = sc.nextInt();

System.out.printf("총점: %d%n", score.sum());
System.out.printf("평균: %.1f%n", score.aver());
```

- `score.sum()`, `score.aver()`처럼 인스턴스를 통해 메서드를 호출한다.
- 인스턴스를 매개변수로 전달할 필요가 없다. 대신 인스턴스로 메서드를 호출하면 그 인스턴스가 `this`에 자동으로 전달된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam05.App
  ```

## App2 - 3명의 점수를 입력받아 출력하는 클래스

```java
Score score1 = new Score();
Score score2 = new Score();
Score score3 = new Score();

// 3명의 점수 입력 ...

System.out.printf("1번 학생 => 총점: %d, 평균: %.1f%n", score1.sum(), score1.aver());
System.out.printf("2번 학생 => 총점: %d, 평균: %.1f%n", score2.sum(), score2.aver());
System.out.printf("3번 학생 => 총점: %d, 평균: %.1f%n", score3.sum(), score3.aver());
```

- sum()과 aver()를 호출할 때 이 메서드가 사용할 인스턴스를 지정할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam05.App2
  ```
