# Exam03 - 스트림 소스 생성

## 개념

스트림(Stream)은 데이터를 처리하는 파이프라인이다. 파이프라인을 시작하려면 **소스(Source)**에서 스트림을 만들어야 한다.
Java는 컬렉션, 배열, 숫자 범위, 파일, 문자열 등 다양한 소스에서 스트림을 생성하는 API를 제공한다.

### 스트림 소스 분류

| 소스 | 생성 방법 | 반환 타입 |
|---|---|---|
| List / Set | `collection.stream()` | `Stream<T>` |
| 배열 | `Arrays.stream(array)` | `Stream<T>` / `IntStream` |
| 값 직접 나열 | `Stream.of(v1, v2, ...)` | `Stream<T>` |
| 빈 스트림 | `Stream.empty()` | `Stream<T>` |
| null 안전 단일 값 | `Stream.ofNullable(value)` | `Stream<T>` |
| 정수 범위 | `IntStream.range(s, e)` / `rangeClosed(s, e)` | `IntStream` |
| 난수 | `new Random().ints(n, min, max)` | `IntStream` |
| 무한 순열 | `Stream.iterate(seed, f)` | `Stream<T>` |
| 무한 생성 | `Stream.generate(supplier)` | `Stream<T>` |
| 파일 줄 | `Files.lines(path)` | `Stream<String>` |
| 디렉토리 직속 | `Files.list(path)` | `Stream<Path>` |
| 디렉토리 트리 | `Files.walk(path)` | `Stream<Path>` |
| 문자 분해 | `string.chars()` | `IntStream` |
| 정규식 분할 | `Pattern.compile(regex).splitAsStream(str)` | `Stream<String>` |
| 두 스트림 연결 | `Stream.concat(s1, s2)` | `Stream<T>` |

### IntStream / LongStream / DoubleStream (기본형 특화 스트림)

| 항목 | `Stream<Integer>` | `IntStream` |
|---|---|---|
| 저장 방식 | Integer 객체 (박싱) | int 기본형 |
| 박싱 오버헤드 | 있음 | **없음** |
| 추가 집계 메서드 | 없음 | `sum()`, `average()`, `min()`, `max()` |
| List로 수집 | `collect(Collectors.toList())` | `boxed().toList()` |

### 무한 스트림: iterate vs generate

| 항목 | `Stream.iterate(seed, f)` | `Stream.generate(supplier)` |
|---|---|---|
| 다음 값 계산 | 이전 값을 기반으로 함수 적용 | 이전 값과 무관하게 독립 생성 |
| 적합한 용도 | 수열, 피보나치 등 상태 있는 시퀀스 | 난수, 상수, 고유 ID 등 |
| 종료 방법 | `limit()` 또는 `takeWhile()` | `limit()` 또는 `takeWhile()` |

### takeWhile vs filter (Java 9+)

```
정렬된 데이터: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

filter(n < 5):    모든 요소를 검사 → [1, 2, 3, 4]
takeWhile(n < 5): 5에서 즉시 종료 → [1, 2, 3, 4]  (이후 요소 검사 안 함)
```

비정렬 데이터에서 `takeWhile`은 처음으로 조건이 false가 되는 순간 이후 요소를 버린다.

### 파일 스트림 주의사항

`Files.lines()`, `Files.list()`, `Files.walk()`는 내부적으로 파일 핸들을 열기 때문에
**try-with-resources**로 스트림을 닫아야 한다. 닫지 않으면 파일 핸들이 누수된다.

```java
// 올바른 사용
try (Stream<String> lines = Files.lines(path)) {
    lines.filter(...).forEach(...);
}
// 블록 종료 시 자동으로 lines.close() → 파일 핸들 해제
```

---

## App - 컬렉션, 배열, 값에서 스트림 생성

`Collection.stream()`, `Arrays.stream()`, `Stream.of()`, `Stream.empty()`, `Stream.ofNullable()`을 다룬다.

```java
// [예제 1] Collection.stream()
List<String> list = Arrays.asList("사과", "바나나", "포도", "딸기");
list.stream().forEach(s -> System.out.print(s + " ")); // 사과 바나나 포도 딸기

Set<Integer> set = Set.of(10, 20, 30, 40);
set.stream().sorted().forEach(n -> System.out.print(n + " ")); // 10 20 30 40

// [예제 2] Map → entrySet / keySet / values
// Map 자체는 stream()이 없다. entrySet() / keySet() / values()를 경유한다.
Map<String, Integer> scores = Map.of("Alice", 90, "Bob", 75, "Charlie", 85);

scores.keySet().stream().sorted()
    .forEach(k -> System.out.print(k + " ")); // Alice Bob Charlie

scores.entrySet().stream()
    .sorted(Map.Entry.comparingByKey())
    .forEach(e -> System.out.print(e.getKey() + "=" + e.getValue() + " "));

// [예제 3] Arrays.stream()
String[] strArr = {"Java", "Python", "Go", "Rust"};
int[]    intArr = {5, 3, 8, 1, 9, 2};

// 객체 배열 → Stream<T>
Arrays.stream(strArr).forEach(s -> System.out.print(s + " "));

// int[] → IntStream (기본형 배열은 IntStream으로 변환된다)
Arrays.stream(intArr).sorted().forEach(n -> System.out.print(n + " ")); // 1 2 3 5 8 9

// 범위 지정: [fromIndex, toIndex)
Arrays.stream(intArr, 1, 4).forEach(n -> System.out.print(n + " ")); // 3 8 1

// [예제 4] Stream.of() - 가변 인자로 즉석 스트림 생성
Stream.of("Java", "Kotlin", "Scala").forEach(s -> System.out.print(s + " "));
Stream.of("하나").findFirst().orElse("없음"); // "하나"

// [예제 5] Stream.empty() - 빈 스트림 (null 대신 사용)
Stream.empty().count(); // 0

// 조건에 따라 스트림을 반환하는 메서드에서 null 대신 empty() 반환
Stream<String> result = keyword.isEmpty()
    ? Stream.empty()
    : list.stream().filter(s -> s.contains(keyword));

// [예제 6] Stream.ofNullable() - null이면 빈 스트림 (Java 9+)
Stream.ofNullable("데이터").count(); // 1
Stream.ofNullable(null).count();     // 0

// null이 섞인 리스트에서 null 제거 - flatMap과 조합
List<String> nullable = Arrays.asList("A", null, "B", null, "C");
nullable.stream()
    .flatMap(Stream::ofNullable) // null → 빈 스트림 → 자동 제거
    .forEach(s -> System.out.print(s + " ")); // A B C

// [예제 7] Collection<T>로 다형적 처리
static int sumCollection(Collection<Integer> col) {
    return col.stream().mapToInt(Integer::intValue).sum();
}
sumCollection(List.of(3, 1, 4, 1, 5, 9)); // 23
sumCollection(Set.of(3, 1, 4, 5, 9));      // 22
```

- `Arrays.stream(int[])`는 `IntStream`을 반환한다. `Stream<Integer>`가 아니므로 `sum()`, `average()` 등을 바로 사용할 수 있다.
- `Stream.ofNullable()`과 `flatMap`을 조합하면 null 요소를 NullPointerException 없이 안전하게 제거할 수 있다.
- `Stream.empty()`는 결과가 없을 때 `null` 반환을 대신한다. 호출자가 null 체크를 하지 않아도 된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam03.App
  ```

---

## App2 - 숫자 범위 스트림

`IntStream.range()`, `rangeClosed()`, `LongStream`, `Random.ints()`, `boxed()`를 다룬다.

```java
// [예제 1] IntStream.range() - for 루프를 선언형으로 대체
// 명령형
for (int i = 0; i < 5; i++) { System.out.print(i + " "); } // 0 1 2 3 4

// 선언형: range(start, end) - end 미포함 [0, 5)
IntStream.range(0, 5).forEach(i -> System.out.print(i + " ")); // 0 1 2 3 4

// rangeClosed(start, end) - end 포함 [1, 5]
IntStream.rangeClosed(1, 5).forEach(i -> System.out.print(i + " ")); // 1 2 3 4 5

// [예제 2] 범위 스트림으로 수치 계산
IntStream.rangeClosed(1, 100).sum();          // 5050  (1~100 합)
IntStream.rangeClosed(1, 10).average();       // OptionalDouble[5.5]
IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum(); // 30  (짝수 합)
IntStream.rangeClosed(1, 5).reduce(1, (acc, n) -> acc * n); // 120 (5!)

// [예제 3] LongStream.range() - 큰 범위 순회
LongStream.rangeClosed(1, 1_000_000).sum(); // 500000500000

// [예제 4] IntStream.of() / DoubleStream.of()
IntStream.of(10, 20, 30, 40, 50).forEach(n -> System.out.print(n + " "));
DoubleStream.of(1.5, 2.5, 3.5).map(d -> d * 2)
    .forEach(d -> System.out.print(d + " ")); // 3.0 5.0 7.0

// [예제 5] Random.ints() - 난수 스트림
// 무한 스트림이므로 limit()으로 제한
new Random(42).ints().limit(5)
    .forEach(n -> System.out.print(n + " "));

// ints(streamSize, origin, bound): [origin, bound) 범위의 난수 streamSize개
new Random(42).ints(5, 1, 10)
    .forEach(n -> System.out.print(n + " "));

// [예제 6] boxed() - IntStream → Stream<Integer>
// IntStream은 collect(Collectors.toList()) 직접 사용 불가 → boxed() 필요
List<Integer> intList = IntStream.rangeClosed(1, 5)
    .boxed()    // IntStream → Stream<Integer>
    .toList();  // [1, 2, 3, 4, 5]

// mapToObj()로 변환하면서 가공 가능
List<String> strList = IntStream.rangeClosed(1, 5)
    .mapToObj(i -> "item-" + i)
    .toList(); // [item-1, item-2, item-3, item-4, item-5]
```

- `IntStream.range(s, e)`는 `[s, e)`, `rangeClosed(s, e)`는 `[s, e]`이다.
- `IntStream`은 `Stream<Integer>`보다 박싱 오버헤드가 없어 성능상 유리하다.
- `IntStream`을 `List<Integer>`로 수집하려면 `boxed()`로 `Stream<Integer>`로 변환해야 한다.
- `Random.ints()`는 무한 스트림이므로 반드시 `limit()` 또는 `takeWhile()`로 종료 조건을 지정한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam03.App2
  ```

---

## App3 - 무한 스트림 (iterate / generate)

`Stream.iterate()`, `Stream.generate()`, `takeWhile()`, `dropWhile()`을 다룬다.

```java
// [예제 1] Stream.iterate() - 등차 수열
// 0, 2, 4, 6, 8, 10 (초기값 0, 공차 2)
Stream.iterate(0, n -> n + 2).limit(6)
    .forEach(n -> System.out.print(n + " ")); // 0 2 4 6 8 10

// [예제 2] Stream.iterate() - 등비 수열
// 1, 2, 4, 8, 16, 32, 64, 128 (초기값 1, 공비 2)
Stream.iterate(1, n -> n * 2).limit(8)
    .forEach(n -> System.out.print(n + " "));

// [예제 3] Stream.iterate(seed, Predicate, UnaryOperator) - Java 9+
// for (int i = 0; i < 10; i += 3) 패턴과 동일
Stream.iterate(0, n -> n < 10, n -> n + 3)
    .forEach(n -> System.out.print(n + " ")); // 0 3 6 9

// 2의 거듭제곱이 1000 미만인 동안 생성
Stream.iterate(1, n -> n < 1000, n -> n * 2)
    .forEach(n -> System.out.print(n + " ")); // 1 2 4 8 ... 512

// [예제 4] Stream.generate() - 독립적인 값 생성
// 상수 스트림
Stream.generate(() -> "Hello").limit(4)
    .forEach(s -> System.out.print(s + " ")); // Hello Hello Hello Hello

// 난수 스트림 (각 호출이 독립적)
Stream.generate(Math::random).limit(5)
    .map(d -> String.format("%.3f", d))
    .forEach(s -> System.out.print(s + " "));

// [예제 5] takeWhile() vs filter() - Java 9+
List<Integer> sorted = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

sorted.stream().filter(n -> n < 5).toList();    // [1, 2, 3, 4] (모든 요소 검사)
sorted.stream().takeWhile(n -> n < 5).toList(); // [1, 2, 3, 4] (5에서 즉시 종료)

// 비정렬 데이터에서의 차이
List.of(1, 3, 2, 5, 4).stream().filter(n -> n < 4).toList();    // [1, 3, 2]
List.of(1, 3, 2, 5, 4).stream().takeWhile(n -> n < 4).toList(); // [1, 3, 2] ← 5에서 종료

// [예제 6] dropWhile() - Java 9+
// n < 5인 동안 버리고, 5 이상이 되는 순간부터 모두 취한다.
List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
    .dropWhile(n -> n < 5)
    .toList(); // [5, 6, 7, 8, 9, 10]

// [예제 7] 피보나치 수열 - int[] 로 두 값을 동시 추적
// [0,1] → [1,1] → [1,2] → [2,3] → [3,5] → ...
Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
    .limit(10)
    .map(f -> f[0])
    .forEach(n -> System.out.print(n + " ")); // 0 1 1 2 3 5 8 13 21 34
```

- `Stream.iterate()`는 이전 값을 기반으로 다음 값을 계산한다. 수열처럼 **상태 있는 시퀀스**에 적합하다.
- `Stream.generate()`는 이전 값에 의존하지 않는 **독립적인 값 생성**에 적합하다. (난수, 상수 등)
- Java 9+의 `iterate(seed, Predicate, f)` 형태는 `for (T x = seed; pred(x); x = f(x))` 패턴을 선언형으로 표현한다.
- `takeWhile()`은 정렬된 데이터에서 조건이 false인 첫 요소에서 즉시 종료하므로 `filter()`보다 효율적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam03.App3
  ```

---

## App4 - 파일과 문자열에서 스트림 생성

`String.chars()`, `Pattern.splitAsStream()`, `Stream.concat()`, `Files.lines()`, `Files.list()`, `Files.walk()`를 다룬다.

```java
// [예제 1] String.chars() - 문자 스트림
// chars()는 각 char를 int(유니코드 코드 포인트)로 반환하는 IntStream이다.
"Hello, World!".chars()
    .mapToObj(c -> String.valueOf((char) c)) // int → char → String
    .forEach(c -> System.out.print(c + " "));

// 대문자 개수
"Hello, World!".chars().filter(Character::isUpperCase).count(); // 2

// 알파벳만 소문자로 추출
"Hello, World!".chars()
    .filter(Character::isLetter)
    .map(Character::toLowerCase)
    .mapToObj(c -> String.valueOf((char) c))
    .forEach(c -> System.out.print(c + " ")); // h e l l o w o r l d

// [예제 2] 문자 빈도 분석 - groupingBy
"programming".chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
    .entrySet().stream()
    .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
    .forEach(e -> System.out.println("'" + e.getKey() + "': " + e.getValue()));

// [예제 3] Pattern.splitAsStream() - 정규식 분할
// String.split()은 배열 반환 → Arrays.stream() 필요
// Pattern.splitAsStream()은 스트림 바로 반환 → 파이프라인에 직접 연결

Pattern.compile(",").splitAsStream("사과,바나나,,포도, 딸기 ,블루베리")
    .map(String::trim)
    .filter(s -> !s.isEmpty())
    .sorted()
    .toList(); // [딸기, 바나나, 블루베리, 사과, 포도]

Pattern.compile("\\s+").splitAsStream("Java   is   a   powerful   language")
    .forEach(w -> System.out.print(w + " ")); // Java is a powerful language

// [예제 4] Stream.concat() - 두 스트림 이어 붙이기
Stream.concat(Stream.of("사과", "바나나"), Stream.of("딸기", "포도"))
    .sorted().toList(); // [딸기, 바나나, 사과, 포도]

// 여러 스트림 합치기: Stream.of(s1, s2, s3).flatMap(s -> s)
Stream.of(Stream.of("A1","A2"), Stream.of("B1","B2"), Stream.of("C1","C2"))
    .flatMap(s -> s)
    .toList(); // [A1, A2, B1, B2, C1, C2]

// [예제 5] Files.lines() - 파일 줄 스트림
// try-with-resources 필수: 파일 핸들 누수 방지
try (Stream<String> lines = Files.lines(path)) {
    lines.filter(line -> !line.startsWith("#")) // 주석 제거
        .map(String::toUpperCase)
        .forEach(System.out::println);
}

// [예제 6] Files.list() - 디렉토리 직속 항목 스트림 (재귀 없음)
try (Stream<Path> entries = Files.list(dir)) {
    entries.map(p -> p.getFileName().toString()).sorted().toList();
    // [alpha.txt, beta.java, gamma.txt, subdir]
}

// .txt 파일만 필터링
try (Stream<Path> entries = Files.list(dir)) {
    entries.filter(p -> p.toString().endsWith(".txt"))
        .map(p -> p.getFileName().toString()).sorted().toList();
    // [alpha.txt, gamma.txt]
}

// [예제 7] Files.walk() - 디렉토리 트리 재귀 탐색
try (Stream<Path> tree = Files.walk(dir)) {
    tree.filter(Files::isRegularFile)           // 파일만
        .map(p -> dir.relativize(p).toString()) // 상대 경로
        .sorted()
        .forEach(System.out::println);
    // alpha.txt, beta.java, gamma.txt, subdir/delta.txt, subdir/epsilon.java
}

// 깊이 제한: walk(path, maxDepth)
try (Stream<Path> shallow = Files.walk(dir, 1)) { // 직속만 (subdir 내부 미포함)
    shallow.filter(Files::isRegularFile).count(); // 3
}
```

- `String.chars()`는 `IntStream`을 반환한다. 문자로 사용하려면 `(char) c` 또는 `Character.xxx()` 메서드를 사용한다.
- `Pattern.splitAsStream()`은 `String.split()`과 달리 배열 없이 스트림을 바로 반환해 파이프라인에 직접 연결할 수 있다.
- `Files.lines()`, `Files.list()`, `Files.walk()`는 파일 핸들을 열기 때문에 **try-with-resources**로 반드시 닫아야 한다.
- `Files.list()`는 직속 항목만, `Files.walk()`는 재귀적으로 모든 하위 항목을 스트림으로 제공한다.
- `Files.walk(path, maxDepth)`로 탐색 깊이를 제한할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam03.App4
  ```
