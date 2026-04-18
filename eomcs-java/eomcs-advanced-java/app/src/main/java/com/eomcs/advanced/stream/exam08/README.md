# Exam08 - 매핑 (1:N, Java 16+)

## 개념

### mapMulti(BiConsumer) — 1:N 매핑 (Java 16+)

`mapMulti(BiConsumer<T, Consumer<R>>)`는 각 요소를 **0개 이상의 요소로 변환**하는 중간 연산이다.
`flatMap()`과 같은 목적이지만 방식이 다르다.

```
flatMap:    T  →  Stream<R>   → (합쳐줌) →  Stream<R>
mapMulti:   T  →  Consumer<R>.accept() 반복  →  Stream<R>
```

### flatMap vs mapMulti

| 항목 | `flatMap(Function<T, Stream<R>>)` | `mapMulti(BiConsumer<T, Consumer<R>>)` |
|---|---|---|
| 추가된 버전 | Java 8 | Java 16 |
| 변환 방식 | 요소를 `Stream<R>`으로 변환 | `Consumer<R>`로 직접 밀어 넣음 |
| 중간 Stream 생성 | 매 요소마다 Stream 객체 생성 | Stream 객체 생성 없음 |
| 성능 | 결과가 많을 때 유리 | 결과가 적거나 0개일 때 유리 |
| 가독성 | 단순 변환에 간결 | 반복/재귀 로직에 자연스러움 |

### mapMulti 기본 형태

```java
stream.<R>mapMulti((element, downstream) -> {
    // downstream.accept(value)를 원하는 만큼 호출한다.
    // 호출 안 함  → 0개 (필터링 효과)
    // 1번 호출    → 1개 (map과 동일)
    // N번 호출    → N개 (flatMap과 동일)
});
```

### 기본형 특화

| 메서드 | 결과 타입 | Consumer 타입 |
|---|---|---|
| `mapMultiToInt(BiConsumer<T, IntConsumer>)` | `IntStream` | `IntConsumer` |
| `mapMultiToLong(BiConsumer<T, LongConsumer>)` | `LongStream` | `LongConsumer` |
| `mapMultiToDouble(BiConsumer<T, DoubleConsumer>)` | `DoubleStream` | `DoubleConsumer` |

### mapMulti가 flatMap보다 유리한 경우

```
1. 결과가 0개 또는 1개인 경우가 많을 때 — Stream 객체 생성 오버헤드가 없음
2. 반복(for 루프)으로 요소를 생성할 때 — Consumer 반복 호출이 자연스러움
3. instanceof 패턴 매칭과 결합한 타입 필터링 — 캐스팅 없이 깔끔하게 표현
4. 중첩 구조(트리, 계층) 재귀 평탄화 — 재귀 Consumer 호출 구조가 읽기 쉬움
```

---

## App - mapMulti() 기초

기본 동작, flatMap과의 비교, 요소 수 조절(0/1/N개), `mapMultiToInt`를 다룬다.

```java
List<Integer> numbers = Arrays.asList(-3, -1, 0, 2, 4, 5, 7, -2, 8);

// [예제 1] mapMulti vs flatMap - 양수를 두 배로 확장
// flatMap 방식
numbers.stream()
    .flatMap(n -> n > 0 ? Stream.of(n, n * 10) : Stream.empty())
    .forEach(...); // 2 20 4 40 5 50 7 70 8 80

// mapMulti 방식
numbers.stream()
    .<Integer>mapMulti((n, downstream) -> {
      if (n > 0) {
        downstream.accept(n);       // 원래 값
        downstream.accept(n * 10);  // 10배 값
      }
      // n <= 0: 아무것도 밀어 넣지 않음 → 제거 효과
    })
    .forEach(...); // 2 20 4 40 5 50 7 70 8 80

// [예제 2] 요소 수 조절 - 0개 / 1개 / N개
List<String> words = Arrays.asList("a", "hello", "hi", "b", "stream", "ok");

words.stream()
    .<String>mapMulti((word, downstream) -> {
      if (word.length() == 1) {
        // 0개: 필터링 효과
      } else if (word.length() == 2) {
        downstream.accept(word);               // 1개: 유지
      } else {
        downstream.accept(word);               // 2개: 원본
        downstream.accept(word.toUpperCase()); //      대문자
      }
    })
    .forEach(...);
// hi, hello, HELLO, stream, STREAM, ok

// [예제 3] mapMultiToInt - 단어 길이를 IntStream으로
List<String> sentences = Arrays.asList("hello world", "java stream api", "map multi");

int totalLength = sentences.stream()
    .mapMultiToInt((sentence, downstream) -> {
      for (String word : sentence.split(" ")) {
        downstream.accept(word.length()); // IntConsumer
      }
    })
    .sum(); // 31
```

- `.<Integer>mapMulti(...)` 처럼 제네릭 타입 인수를 명시해야 컴파일러가 타입을 추론할 수 있다.
- `downstream.accept()`를 호출하지 않으면 해당 요소는 결과에 포함되지 않는다 (필터링 효과).
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam08.App
  ```

---

## App2 - mapMulti() 실전 패턴

중첩 리스트 평탄화, instanceof 패턴 매칭, 계층 구조 평탄화, 반복 확장을 다룬다.

```java
// [예제 1] 중첩 리스트 평탄화
List<List<Integer>> nested = [[1,2,3], [4,5], [6,7,8,9]];

// flatMap 방식
nested.stream().flatMap(List::stream).forEach(...);

// mapMulti 방식
nested.stream()
    .<Integer>mapMulti((list, downstream) ->
        list.forEach(downstream::accept))
    .forEach(...);

// [예제 2] instanceof 패턴 매칭 + mapMulti - 특정 타입만 추출
List<Object> mixed = Arrays.asList(1, "hello", 3.14, "world", true, "stream", 42);

// flatMap 방식 (캐스팅 필요)
mixed.stream()
    .flatMap(o -> o instanceof String s ? Stream.of(s) : Stream.empty())
    .map(String::toUpperCase)
    .forEach(...);

// mapMulti 방식 (instanceof 패턴 매칭과 자연스럽게 결합)
mixed.stream()
    .<String>mapMulti((o, downstream) -> {
      if (o instanceof String s) {
        downstream.accept(s); // String인 경우만, 캐스팅 없이
      }
    })
    .map(String::toUpperCase)
    .forEach(...);

// [예제 3] 계층 구조 평탄화 - 카테고리 → 상품
List<Category> categories = ...;

categories.stream()
    .<String>mapMulti((category, downstream) ->
        category.getItems().forEach(downstream::accept))
    .sorted()
    .forEach(...);

// [예제 4] 요소 N회 반복 확장
Arrays.asList("A", "B", "C").stream()
    .<String>mapMulti((item, downstream) -> {
      for (int i = 0; i < 3; i++) {
        downstream.accept(item);
      }
    })
    .forEach(...); // A A A B B B C C C
```

- `instanceof 패턴 매칭 (o instanceof String s)`은 Java 16+에서 지원한다. `mapMulti`와 같은 버전에 추가됐으므로 함께 사용하기에 자연스럽다.
- `mapMulti`는 결과 요소 수가 적을수록(0개, 1개) `flatMap`보다 성능이 유리하다. 결과가 많다면 `flatMap`이 더 적합할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam08.App2
  ```
