# Exam14 - 자주 사용하는 함수형 인터페이스(functional interface)와 람다(lambda)

## 개념

`java.util.function` 패키지는 자주 사용하는 함수형 인터페이스를 제공한다.
함수형 인터페이스는 추상 메서드가 하나뿐이므로 람다로 대체할 수 있다.

| 인터페이스 | 메서드 | 설명 |
|---|---|---|
| `Supplier<T>` | `T get()` | 인수 없이 값을 **공급** |
| `Consumer<T>` | `void accept(T)` | 값을 받아 **소비** (리턴 없음) |
| `Function<T,R>` | `R apply(T)` | 값을 받아 **변환**하여 리턴 |
| `Predicate<T>` | `boolean test(T)` | 값을 받아 **조건 판별** |

## App - Supplier\<T\> 사용

```java
static <T> void print(Supplier<T> supplier) {
  System.out.println(supplier.get());
}

public static void main(String[] args) {
  print(() -> "Hello, World!");

  Random random = new Random();
  print(() -> random.nextInt(100) + 1);
  print(() -> random.nextInt(100) + 1);
}
```

- `Supplier<T>`는 인수 없이 값을 공급하는 함수형 인터페이스이다.
- `get()`을 호출하면 람다가 실행되어 값을 리턴한다.
- `print()` 메서드를 제네릭 메서드(`<T>`)로 정의하여 `Supplier<String>`, `Supplier<Integer>` 등 어떤 타입의 `Supplier`도 받을 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App
  ```

## App2 - Consumer\<T\> 사용

```java
static <T> void processUsers(T[] arr, Consumer<T> consumer) {
  for (T obj : arr) {
    consumer.accept(obj);
  }
}

public static void main(String[] args) {
  User[] users = {new User("홍길동", 20), new User("임꺽정", 30), new User("유관순", 17)};
  processUsers(users, user -> System.out.println(user.name));
}
```

- `Consumer<T>`는 값을 받아 소비하는 함수형 인터페이스이다. 리턴값이 없다.
- `accept(T)`를 호출하면 람다가 실행된다.
- `processUsers()` 메서드는 배열의 각 요소에 `Consumer`를 적용한다.
- 호출 시 람다를 바꾸는 것만으로 다양한 동작을 처리할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App2
  ```

## App3 - Function\<T,R\> 사용

```java
static void printScore(
    String[] scores, Function<String, Integer> sumFunc, Function<String, String> nameFunc) {
  for (String score : scores) {
    int sum = sumFunc.apply(score);
    System.out.printf("%s: %d %.1f%n", nameFunc.apply(score), sum, sum / 3f);
  }
}

public static void main(String[] args) {
  String[] scores = {"홍길동 100 100 100", "임꺽정 90 90 90", "유관순 80 80 80"};
  printScore(
      scores,
      score -> {
        String[] values = score.split(" ");
        int sum = 0;
        for (int i = 1; i <= 3; i++) {
          sum += Integer.parseInt(values[i]);
        }
        return sum;
      },
      score -> score.split(" ")[0]);
}
```

- `Function<T,R>`은 `T` 타입 값을 받아 `R` 타입 값으로 변환하는 함수형 인터페이스이다.
- `apply(T)`를 호출하면 람다가 실행되어 변환된 값을 리턴한다.
- `printScore()` 메서드는 점수 문자열에서 합계를 구하는 함수와 이름을 추출하는 함수를 파라미터로 받는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App3
  ```

## App4 - Predicate\<T\> 사용

```java
static void printMembers(Member[] members, Predicate<Member> filter) {
  for (Member member : members) {
    if (filter.test(member)) {
      System.out.println(member.name);
    }
  }
}

public static void main(String[] args) {
  printMembers(members, member -> member.type == MemberType.STUDENT);
  printMembers(members, member -> member.type == MemberType.TEACHER);
  printMembers(members, member -> member.type == MemberType.MANAGER);
}
```

- `Predicate<T>`는 값을 받아 `boolean`을 리턴하는 함수형 인터페이스이다.
- `test(T)`를 호출하면 람다가 실행되어 조건 판별 결과를 리턴한다.
- `printMembers()` 메서드는 `Predicate`를 파라미터로 받아 조건을 만족하는 멤버만 출력한다.
- 호출 시 람다를 바꾸는 것만으로 학생, 교사, 관리자 등 다양한 조건으로 필터링할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam14.App4
  ```
