# Exam02 - 스태틱 필드와 스태틱 메서드

## 개념

클래스에 데이터(필드)와 기능(메서드)을 정의하는 방법을 학습한다.

- **스태틱 필드(static field)**: 클래스에 속하는 변수로, 객체 생성 없이 사용할 수 있다.
- **스태틱 메서드(static method)**: 클래스에 속하는 메서드로, 객체 생성 없이 호출할 수 있다.

## App - main() 메서드에 모든 코드를 작성하는 방식

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

## App2 - 스태틱 필드와 스태틱 메서드를 사용하는 방식

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

## App3 - 표준 입력으로 점수를 입력받는 방식

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

## App4 - 프로그램 아규먼트로 점수를 입력받는 방식

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
