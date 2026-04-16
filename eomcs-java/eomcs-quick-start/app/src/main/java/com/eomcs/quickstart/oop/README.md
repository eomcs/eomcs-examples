# Object-Oriented Programming (OOP)

## 학습 목표

- 자바 OOP 문법의 특징을 이해한다.
- OOP 문법으로 작성된 자바 코드를 읽고 이해할 수 있다.
- 자바 코드를 작성할 때 OOP 문법을 활용하는 방법을 익힌다.

---

## Exam01 - Entry Point

### 개념

자바 프로그램의 진입점(entry point)은 JVM이 프로그램 실행 시 가장 먼저 호출하는 메서드이다. entry point 메서드의 시그니처는 다음과 같다:

```java
public static void main(String[] args)
```

### 클래스에서의 Entry Point

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

### 인터페이스에서의 Entry Point

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

### 실무

- 일반적으로 entry point 메서드는 클래스에 정의한다. 인터페이스에 entry point 메서드를 정의하는 것은 드물며, 주로 테스트나 예제 코드를 만들 때 가끔 사용된다.
- 클래스에 entry point 메서드를 정의하면 필드나 구현 메서드를 함께 사용할 수 있어 더 유연하게 프로그램을 작성할 수 있다.

---

## Exam02 - 스태틱 필드와 스태틱 메서드

### 개념

클래스에 데이터(필드)와 기능(메서드)을 정의하는 방법을 학습한다.

- **스태틱 필드(static field)**: 클래스에 속하는 변수로, 객체 생성 없이 사용할 수 있다.
- **스태틱 메서드(static method)**: 클래스에 속하는 메서드로, 객체 생성 없이 호출할 수 있다.

### App - main() 메서드에 모든 코드를 작성하는 방식

스태틱 필드와 스태틱 메서드 없이 `main()` 메서드 안에 모든 코드를 작성한다.

```java
public static void main(String[] args) {
  int kor = 100;
  int eng = 90;
  int math = 80;

  int sum = kor + eng + math;
  float aver = (float) sum / 3;

  System.out.printf("총점: %d%n", sum);
  System.out.printf("평균: %.1f%n", aver);
}
```

- 국어, 영어, 수학 점수를 지역변수로 선언하고 값을 직접 초기화한다.
- 총점과 평균을 계산하여 출력한다.

### App2 - 스태틱 필드와 스태틱 메서드를 사용하는 방식

데이터는 스태틱 필드에, 기능은 스태틱 메서드에 분리하여 정의한다.

```java
static int kor = 100;
static int eng = 90;
static int math = 80;

static int sum() {
  return kor + eng + math;
}

static float aver() {
  return (float) sum() / 3;
}
```

- 스태틱 필드에 점수를 저장하면 클래스 내 모든 메서드에서 공유하여 사용할 수 있다.
- `sum()`, `aver()` 스태틱 메서드로 계산 로직을 분리하면 코드 재사용성이 높아진다.

### App3 - 표준 입력으로 점수를 입력받는 방식

`Scanner`를 사용하여 표준 입력 장치(키보드)로부터 점수를 입력받는다.

```java
Scanner sc = new Scanner(System.in);

System.out.print("국어 점수? ");
kor = sc.nextInt();

System.out.print("영어 점수? ");
eng = sc.nextInt();

System.out.print("수학 점수? ");
math = sc.nextInt();

sc.close();
```

- `Scanner(System.in)`으로 표준 입력 스트림과 연결한다.
- `nextInt()`로 정수를 입력받아 스태틱 필드에 저장한다.
- 사용 후 `sc.close()`로 Scanner를 닫는다.
- gradlew로 실행할 때 표준 입력을 사용하려면 `build.gradle`에 다음 설정이 필요하다:
  ```groovy
  tasks.named('run') {
      standardInput = System.in
  }
  ```
- gradlew로 실행할 때 색상과 특수 문자 없이 일반 텍스트로 출력하려면 `gradle.properties`에 다음 설정이 필요하다:
  ```properties
  org.gradle.console=plain
  ```
  - **auto:**	기본값. 터미널 환경을 감지하여 자동으로 선택
  - **plain:**	색상과 특수 문자 없이 일반 텍스트로 출력
  - **rich:**	색상, 진행 표시줄 등 풍부한 콘솔 출력
  - **verbose:**	rich와 동일하나 태스크 결과를 모두 출력
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam02.App3
  ```

### App4 - 프로그램 아규먼트로 점수를 입력받는 방식

`main(String[] args)`의 `args` 배열을 통해 커맨드라인 인수로 점수를 전달받는다.

```java
kor = Integer.parseInt(args[0]);
eng = Integer.parseInt(args[1]);
math = Integer.parseInt(args[2]);
```

- `args[0]`, `args[1]`, `args[2]`에 순서대로 국어, 영어, 수학 점수가 전달된다.
- 커맨드라인 인수는 문자열이므로 `Integer.parseInt()`로 정수로 변환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam02.App4 --args="100 90 80"
  ```

---

## Exam03 - 클래스 분리

### 개념

관련된 데이터(필드)와 기능(메서드)을 별도의 클래스로 분리한다.

- 역할에 따라 클래스를 분리하면 코드의 재사용성과 유지보수성이 높아진다.
- `App` 클래스는 프로그램의 흐름(입력/출력)을 담당하고, `Score` 클래스는 데이터와 계산 로직을 담당한다.

### Score - 데이터와 계산 로직을 담당하는 클래스

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

### App - 입출력을 담당하는 클래스

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

---

## Exam04 - 인스턴스와 인스턴스 필드

### 개념

스태틱 필드를 인스턴스 필드로 변경하면 여러 명의 성적을 독립적으로 다룰 수 있다.

- **스태틱 필드**: 클래스에 하나만 존재하므로 여러 학생의 점수를 동시에 저장할 수 없다.
- **인스턴스 필드**: `new` 연산자로 객체를 생성할 때마다 별도의 메모리가 할당되므로 여러 학생의 점수를 독립적으로 저장할 수 있다.
- 스태틱 메서드는 인스턴스 필드에 직접 접근할 수 없으므로, `Score` 인스턴스를 매개변수로 전달받아 접근한다.

### Score - 인스턴스 필드와 스태틱 메서드를 사용하는 클래스

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

### App - 1명의 점수를 입력받아 출력하는 클래스

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam04.App
  ```

### App2 - 3명의 점수를 입력받아 출력하는 클래스

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam04.App2
  ```

---

## Exam05 - 인스턴스 메서드와 this 빌트인 변수

### 개념

인스턴스 메서드는 빌트인 변수 `this`를 통해 자신에게 전달된 인스턴스의 필드에 접근할 수 있다.

- 인스턴스 메서드는 `클래스명.메서드명()` 대신 `인스턴스변수.메서드명()`으로 호출한다.
- 인스턴스는 메서드를 호출할 때 인스턴스 메서드의 빌트인 변수 `this`에 자동으로 전달된다.
- **스태틱 메서드**: `this`가 없으므로 인스턴스를 매개변수로 전달받아야 한다.
- **인스턴스 메서드**: 빌트인 변수 `this`에 대상 인스턴스 레퍼런스가 들어 있으므로 이 레퍼런스를 통해 인스턴스 필드에 직접 접근할 수 있다.

### Score - 인스턴스 필드와 인스턴스 메서드를 사용하는 클래스

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

### App - 1명의 점수를 입력받아 출력하는 클래스

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

### App2 - 3명의 점수를 입력받아 출력하는 클래스

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

---

## Exam06 - 스태틱 메서드의 한계

### 개념

스태틱 메서드는 클래스 이름으로 호출하기 때문에 교체하기 힘들다.

- `Sorter.sort(arr)`처럼 클래스 이름이 코드에 직접 박혀 있으므로 다른 정렬 알고리즘으로 교체하려면 호출하는 코드를 모두 수정해야 한다.
- 반면 인스턴스 메서드로 구현하면 인스턴스 변수만 교체하여 다른 구현체로 쉽게 바꿀 수 있다.

### Sorter - 버블 정렬 클래스

인접한 두 요소를 비교하여 교환하는 버블 정렬(Bubble Sort)을 구현한다.

```java
public class Sorter {

  static void sort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = 0; j < arr.length - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }
}
```

- 시간 복잡도: 평균/최악 O(n²)

### QuickSorter - 퀵 정렬 클래스

피벗을 기준으로 배열을 분할하는 퀵 정렬(Quick Sort)을 재귀로 구현한다.

- 마지막 요소를 피벗으로 선택한다.
- `partition()`은 피벗보다 작은 요소를 왼쪽, 큰 요소를 오른쪽으로 분리하고 피벗의 최종 인덱스를 반환한다.
- 시간 복잡도: 평균 O(n log n), 최악 O(n²)

### App - Sorter(버블 정렬)를 사용하는 클래스

```java
Sorter.sort(arr);
```

- 정렬 알고리즘을 교체하려면 호출 코드(`Sorter.sort` → `QuickSorter.sort`)를 직접 수정해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam06.App
  ```

### App2 - QuickSorter(퀵 정렬)를 사용하는 클래스

```java
QuickSorter.sort(arr);
```

- 스태틱 메서드 방식의 한계: 호출하는 쪽의 코드가 구체적인 클래스에 종속된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam06.App2
  ```

---

## Exam07 - 인스턴스 메서드 호출

### 개념

인스턴스 메서드는 객체를 통해 호출하기 때문에 메서드 호출에 필요한 객체를 파라미터로 전달할 수 있다.

- exam06의 스태틱 메서드는 클래스 이름이 코드에 직접 박혀 있어 교체가 어렵다.
- 인스턴스 메서드로 변경하면 `new Sorter()` 또는 `new QuickSorter()`처럼 생성할 인스턴스만 바꿔 다른 구현체를 손쉽게 전달할 수 있다. (단, 상속이나 인터페이스를 사용한다면)

### App - Sorter를 내부에서 생성하여 사용하는 클래스

```java
static void play(int[] arr) {
  Sorter sorter = new Sorter();
  sorter.sort(arr);
}
```

- `play()` 안에 `Sorter` 클래스 이름이 박혀 있으므로 다른 정렬 알고리즘으로 교체하려면 `play()` 코드를 수정해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam07.App
  ```

### App2 - QuickSorter를 내부에서 생성하여 사용하는 클래스

```java
static void play(int[] arr) {
  QuickSorter sorter = new QuickSorter();
  sorter.sort(arr);
}
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam07.App2
  ```

### App3 - Sorter 인스턴스를 파라미터로 받는 클래스

```java
static void play(int[] arr, Sorter sorter) {
  sorter.sort(arr);
}

public static void main(String[] args) {
  play(numbers, new Sorter());
}
```

- `play()` 내부에서 인스턴스를 생성하지 않고 외부에서 주입받으므로 `main()`에서 전달할 인스턴스만 바꾸면 된다.
- 단, 파라미터 타입이 `Sorter`로 고정되어 있어 `QuickSorter`를 전달하려면 파라미터 타입도 변경해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam07.App3
  ```

### App4 - QuickSorter 인스턴스를 파라미터로 받는 클래스

```java
static void play(int[] arr, QuickSorter sorter) {
  sorter.sort(arr);
}

public static void main(String[] args) {
  play(numbers, new QuickSorter());
}
```

- App3와 App4는 파라미터 타입만 다를 뿐 `play()` 코드가 중복된다. 공통 타입(인터페이스 또는 상위 클래스)이 있다면 하나의 `play()`로 통합할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam07.App4
  ```

---

## Exam08 - 인스턴스 메서드 호출 + 상속 + 다형성

### 개념

상속을 사용하면 두 클래스를 같은 타입으로 묶어서 다룰 수 있다. 즉 자식 클래스는 부모 클래스 타입으로 다룰 수 있기 때문에, 파라미터 타입을 부모 클래스로 선언하면 자식 클래스 인스턴스도 전달할 수 있다.

- exam07의 App3와 App4는 `play(Sorter sorter)`, `play(QuickSorter sorter)`로 파라미터 타입만 다른 중복 코드가 존재했다.
- `QuickSorter extends Sorter`로 선언하면 `QuickSorter`는 `Sorter` 타입으로 다룰 수 있다.
- `play(Sorter sorter)` 하나로 `Sorter`와 `QuickSorter` 인스턴스를 모두 받을 수 있어 코드 중복이 사라진다.
- 메서드 호출 시 실제 인스턴스의 `sort()`가 실행된다. 이를 **다형성(Polymorphism)** 이라 한다.

### App - 부모 클래스 타입 파라미터로 통합한 클래스

```java
static void play(int[] arr, Sorter sorter) {
  sorter.sort(arr);
}

public static void main(String[] args) {
  System.out.println("[버블 정렬]");
  play(numbers.clone(), new Sorter());

  System.out.println("[퀵 정렬]");
  play(numbers.clone(), new QuickSorter());
}
```

- `play()`는 `Sorter` 타입만 알면 되고, 실제로 어떤 정렬 알고리즘이 실행될지는 전달된 인스턴스에 따라 결정된다.
- `numbers.clone()`으로 동일한 배열을 복사하여 두 알고리즘에 같은 데이터를 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam08.App
  ```

---

## Exam09 - 인터페이스 활용

### 개념

상속(`extends`) 대신 인터페이스(`implements`)를 사용하면 클래스 간의 결합도를 낮추고 더 유연한 구조를 만들 수 있다.

- exam08의 `QuickSorter extends Sorter`는 `Sorter` 클래스의 구현에 종속된다.
- 인터페이스로 분리하면 `BubbleSorter`와 `QuickSorter`가 서로 독립적인 클래스 계층을 가질 수 있다.
- 자바는 단일 상속만 허용하지만, 인터페이스는 여러 개를 구현할 수 있다.
- `play()`의 파라미터 타입을 `Sorter` 인터페이스로 선언하면 이를 구현한 모든 클래스의 인스턴스를 전달할 수 있다.

### App - 인터페이스 타입 파라미터로 통합한 클래스

```java
static void play(int[] arr, Sorter sorter) {
  sorter.sort(arr);
}

public static void main(String[] args) {
  System.out.println("[버블 정렬]");
  play(numbers.clone(), new BubbleSorter());

  System.out.println("[퀵 정렬]");
  play(numbers.clone(), new QuickSorter());
}
```

- `play()`는 `Sorter` 인터페이스만 알면 되고, 어떤 구현체가 전달되는지 알 필요가 없다.
- 새로운 정렬 알고리즘을 추가할 때 `Sorter`를 구현하는 클래스만 만들면 `play()` 코드는 변경할 필요가 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam09.App
  ```

---

## Exam10 - 추상 클래스의 용도

### 개념

추상 클래스를 사용하면 여러 구현 클래스의 공통 필드와 기능을 한 곳에 모아 코드 중복을 줄일 수 있다.

- `BubbleSorter`와 `QuickSorter`는 정렬 대상 배열(`arr`)과 `toString()`이 동일하다.
- 공통 부분을 `AbstractSorter` 추상 클래스에 정의하면 각 구현 클래스는 정렬 로직(`sort()`)만 작성하면 된다.
- `AbstractSorter`는 `Sorter` 인터페이스를 구현하므로 `BubbleSorter`와 `QuickSorter`도 `Sorter` 타입으로 다룰 수 있다.

### App - AbstractSorter 타입 파라미터로 통합한 클래스

```java
static void play(AbstractSorter sorter) {
  System.out.println("정렬 전: " + sorter);
  sorter.sort();
  System.out.println("정렬 후: " + sorter);
}

public static void main(String[] args) {
  System.out.println("[버블 정렬]");
  play(new BubbleSorter(numbers.clone()));

  System.out.println("[퀵 정렬]");
  play(new QuickSorter(numbers.clone()));
}
```

- `"정렬 전: " + sorter`처럼 문자열 연결 시 `toString()`이 자동 호출되어 `[100,200,300]` 형식으로 출력된다.
- `sorter.sort()` 호출 시 실제 인스턴스의 `sort()`가 실행된다. (**다형성**)
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam10.App
  ```

---

## Exam11 - 스태틱/논스태틱 중첩 클래스, 로컬 클래스, 익명 클래스, 람다, 메서드 레퍼런스

### 개념

`FileFilter` 인터페이스를 구현하는 클래스를 다양한 방식으로 정의하는 방법을 학습한다.

- **톱레벨 클래스**: 독립적인 파일로 정의. 여러 곳에서 재사용할 때 적합하다.
- **스태틱 중첩 클래스**: 바깥 클래스 안에 `static`으로 정의. 바깥 클래스와 관련이 있지만 독립적으로 사용 가능하다.
- **논스태틱 중첩 클래스(이너 클래스)**: 바깥 클래스의 인스턴스에 종속. `인스턴스.new 클래스명()`으로 생성한다.
- **로컬 클래스**: 메서드 블록 안에 정의. 해당 메서드 내에서만 사용 가능하다.
- **익명 클래스**: 이름 없이 인터페이스 구현과 인스턴스 생성을 동시에 처리한다.
- **람다**: `FileFilter`가 함수형 인터페이스이므로 익명 클래스를 람다로 대체할 수 있다.
- **메서드 레퍼런스**: 람다 대신 기존 메서드를 참조하여 전달한다.

### App - 톱레벨 클래스 사용

```java
File[] files = dir.listFiles(new MyFileFilter());
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App
  ```

### App2 - 스태틱 중첩 클래스 사용

```java
static class MyFileFilter implements FileFilter {
  @Override
  public boolean accept(File file) { return file.isFile(); }
}
File[] files = dir.listFiles(new MyFileFilter());
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App2
  ```

### App3 - 논스태틱 중첩 클래스(이너 클래스) 사용

```java
class MyFileFilter implements FileFilter {
  @Override
  public boolean accept(File file) { return file.isFile(); }
}
App3 app = new App3();
File[] files = dir.listFiles(app.new MyFileFilter());
```

- 이너 클래스는 바깥 클래스의 인스턴스에 종속되므로 `app.new MyFileFilter()`처럼 인스턴스를 통해 생성해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App3
  ```

### App4 - 로컬 클래스 사용

```java
class MyFileFilter implements FileFilter {
  @Override
  public boolean accept(File file) { return file.isFile(); }
}
File[] files = dir.listFiles(new MyFileFilter());
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App4
  ```

### App5 - 익명 클래스를 변수에 저장하여 사용

```java
FileFilter filter = new FileFilter() {
  @Override
  public boolean accept(File file) { return file.isFile(); }
};
File[] files = dir.listFiles(filter);
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App5
  ```

### App6 - 익명 클래스를 인라인으로 사용

```java
File[] files = dir.listFiles(new FileFilter() {
  @Override
  public boolean accept(File file) { return file.isFile(); }
});
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App6
  ```

### App7 - 람다 사용

```java
File[] files = dir.listFiles(file -> file.isFile());
```

- `FileFilter`는 추상 메서드가 하나인 함수형 인터페이스이므로 람다로 대체할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App7
  ```

### App8 - 스태틱 메서드 레퍼런스 사용

```java
static boolean isFile(File file) { return file.isFile(); }
File[] files = dir.listFiles(App8::isFile);
```

- 람다 대신 기존 스태틱 메서드를 `클래스명::메서드명` 형식으로 참조하여 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App8
  ```

### App9 - 인스턴스 메서드 레퍼런스 사용

```java
boolean isFile(File file) { return file.isFile(); }
App9 app = new App9();
File[] files = dir.listFiles(app::isFile);
```

- 인스턴스 메서드를 `인스턴스변수::메서드명` 형식으로 참조하여 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam11.App9
  ```

---

## Exam12 - 스태틱/논스태틱 중첩 클래스, 로컬 클래스, 익명 클래스, 람다

### 개념

`Sorter` 인터페이스를 구현하는 클래스를 다양한 방식으로 정의하는 방법을 학습한다.

- **톱레벨 클래스**: 독립적인 파일로 정의. 여러 곳에서 재사용할 때 적합하다.
- **스태틱 중첩 클래스**: 바깥 클래스 안에 `static`으로 정의. 바깥 클래스와 관련이 있지만 독립적으로 사용 가능하다.
- **논스태틱 중첩 클래스(이너 클래스)**: 컴파일러가 바깥 클래스의 인스턴스 주소를 전달하는 코드를 자동 생성한다. 따라서 바깥 클래스의 필드에 직접 접근할 수 있다.
- **로컬 클래스**: 컴파일러가 바깥 클래스의 인스턴스 주소나 로컬 변수의 값을 전달하는 코드를 자동 생성한다.
- **익명 클래스**: 로컬 클래스와 마찬가지로 동작한다.
- **람다**: 로컬 클래스와 마찬가지로 동작한다.

### App - 톱레벨 클래스 사용

```java
BubbleSorter sorter = new BubbleSorter(numbers);
sorter.sort();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App
  ```

### App2 - 스태틱 중첩 클래스 사용

```java
static class BubbleSorter implements Sorter { ... }
BubbleSorter sorter = new BubbleSorter(numbers);
sorter.sort();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App2
  ```

### App3 - 논스태틱 중첩 클래스(이너 클래스) 사용

```java
class BubbleSorter implements Sorter {
  @Override
  public void sort() {
    for (int i = 0; i < App3.this.numbers.length - 1; i++) { ... }
  }
}
BubbleSorter sorter = new BubbleSorter();
sorter.sort();
```

- `App3.this.numbers`로 바깥 클래스의 필드에 직접 접근할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App3
  ```

### App4 - 로컬 클래스 사용

```java
class BubbleSorter implements Sorter {
  @Override
  public void sort() {
    for (int i = 0; i < App4.this.numbers.length - 1; i++) { ... }
  }
}
BubbleSorter sorter = new BubbleSorter();
sorter.sort();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App4
  ```

### App5 - 익명 클래스 사용

```java
new Sorter() {
  @Override
  public void sort() {
    for (int i = 0; i < App5.this.numbers.length - 1; i++) { ... }
  }
}.sort();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App5
  ```

### App6 - 람다 사용

```java
Sorter sorter = () -> {
  for (int i = 0; i < App6.this.numbers.length - 1; i++) { ... }
};
sorter.sort();
```

- `Sorter`는 추상 메서드가 `sort()` 하나뿐인 함수형 인터페이스이므로 람다로 대체할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam12.App6
  ```

---

## Exam13 - 제네릭

### 개념

타입별로 별도의 클래스를 만들지 않고 다양한 타입을 처리하는 방법을 학습한다.

- **타입별 클래스**: 타입마다 별도의 클래스를 정의한다. 코드가 중복되고 타입이 늘어날수록 클래스도 늘어난다.
- **Object 타입 사용**: 필드를 `Object` 타입으로 선언하면 모든 타입을 저장할 수 있다. 단, 꺼낼 때 형변환이 필요하며 잘못된 형변환은 런타임 오류를 발생시킨다.
- **제네릭(Generic)**: 타입 파라미터(`<T>`)를 사용하여 클래스 정의 시 타입을 지정하지 않고, 인스턴스 생성 시 타입을 지정한다. 형변환이 필요 없고 타입 오류를 컴파일 시점에 잡을 수 있다.

### App - 타입별 클래스 사용

```java
StringBox strBox = new StringBox(); strBox.set("Hello"); String strValue = strBox.get();
IntBox intBox = new IntBox(); intBox.set(100); int intValue = intBox.get();
FloatBox floatBox = new FloatBox(); floatBox.set(3.14f); float floatValue = floatBox.get();
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam13.App
  ```

### App2 - Object 타입 사용

```java
ObjectBox strBox = new ObjectBox();
strBox.set("Hello");
String strValue = (String) strBox.get(); // 형변환 필요
```

- 잘못된 형변환은 컴파일 시점이 아닌 **런타임에 `ClassCastException`** 을 발생시킨다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam13.App2
  ```

### App3 - 제네릭 사용

```java
GenericBox<String> strBox = new GenericBox<>();
strBox.set("Hello");
String strValue = strBox.get(); // 형변환 불필요
```

- 잘못된 타입을 넣으면 **컴파일 시점에 오류**가 발생한다.
- 제네릭의 타입 파라미터에는 기본 타입(`int`, `float`)을 직접 지정할 수 없으며, 래퍼 클래스(`Integer`, `Float`)를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam13.App3
  ```

---

## Exam14 - 자주 사용하는 함수형 인터페이스(functional interface)와 람다(lambda)

### 개념

`java.util.function` 패키지는 자주 사용하는 함수형 인터페이스를 제공한다.
함수형 인터페이스는 추상 메서드가 하나뿐이므로 람다로 대체할 수 있다.

| 인터페이스 | 메서드 | 설명 |
|---|---|---|
| `Supplier<T>` | `T get()` | 인수 없이 값을 **공급** |
| `Consumer<T>` | `void accept(T)` | 값을 받아 **소비** (리턴 없음) |
| `Function<T,R>` | `R apply(T)` | 값을 받아 **변환**하여 리턴 |
| `Predicate<T>` | `boolean test(T)` | 값을 받아 **조건 판별** |

### App - Supplier\<T\> 사용

```java
static <T> void print(Supplier<T> supplier) {
  System.out.println(supplier.get());
}
print(() -> "Hello, World!");
print(() -> random.nextInt(100) + 1);
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App
  ```

### App2 - Consumer\<T\> 사용

```java
static <T> void processUsers(T[] arr, Consumer<T> consumer) {
  for (T obj : arr) { consumer.accept(obj); }
}
processUsers(users, user -> System.out.println(user.name));
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App2
  ```

### App3 - Function\<T,R\> 사용

```java
static void printScore(
    String[] scores, Function<String, Integer> sumFunc, Function<String, String> nameFunc) {
  for (String score : scores) {
    int sum = sumFunc.apply(score);
    System.out.printf("%s: %d %.1f%n", nameFunc.apply(score), sum, sum / 3f);
  }
}
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App3
  ```

### App4 - Predicate\<T\> 사용

```java
static void printMembers(Member[] members, Predicate<Member> filter) {
  for (Member member : members) {
    if (filter.test(member)) { System.out.println(member.name); }
  }
}
printMembers(members, member -> member.type == MemberType.STUDENT);
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App4
  ```
