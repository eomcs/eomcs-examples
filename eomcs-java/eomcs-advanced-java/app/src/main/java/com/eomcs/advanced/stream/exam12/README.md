# Exam12 - 범용 집계 (reduce)

## 개념

### reduce() — 범용 집계

`reduce()`는 스트림의 모든 요소를 **하나의 값으로 합치는** 최종 연산이다.
`count()`, `sum()`, `min()`, `max()`가 `reduce()`의 특수한 형태이다.

### reduce()의 세 가지 형태

| 형태 | 시그니처 | 반환 타입 | 빈 스트림 |
|---|---|---|---|
| 1 | `reduce(BinaryOperator<T>)` | `Optional<T>` | `Optional.empty()` |
| 2 | `reduce(T identity, BinaryOperator<T>)` | `T` | `identity` |
| 3 | `reduce(U identity, BiFunction<U,T,U>, BinaryOperator<U>)` | `U` | `identity` |

### identity (항등원)

연산 `f`에서 `f(identity, x) == x`를 만족하는 값이다.

| 연산 | identity |
|---|---|
| 덧셈 | `0` |
| 곱셈 | `1` |
| 최댓값 | `Integer.MIN_VALUE` |
| 최솟값 | `Integer.MAX_VALUE` |
| 문자열 연결 | `""` |

### IntStream.reduce() 사용 권장

`Stream<Integer>.reduce()`에서 `Integer::sum` 같은 기본형 파라미터 메서드를 사용하면
null 타입 안전성 경고가 발생한다.
`mapToInt()`로 `IntStream`으로 변환한 뒤 `reduce()`를 사용하면 경고 없이 처리할 수 있다.

```java
// 경고 발생 가능
numbers.stream().reduce(0, Integer::sum);

// 경고 없음 — IntBinaryOperator는 (int, int) → int
numbers.stream().mapToInt(Integer::intValue).reduce(0, (a, b) -> a + b);
```

---

## App - reduce() 기초

형태 1 (Optional), 형태 2 (identity), reduce로 count/sum/min/max 직접 구현을 다룬다.

```java
List<String>  words   = Arrays.asList("Java", "Stream", "reduce", "example");
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// [예제 1] reduce(BinaryOperator) - 초기값 없음 → Optional
words.stream().reduce((a, b) -> a + " " + b);          // Optional[Java Stream reduce example]
words.stream().reduce((a, b) -> a.length() >= b.length() ? a : b); // Optional[example]
List.<String>of().stream().reduce((a, b) -> a + b);    // Optional.empty

// [예제 2] IntStream.reduce(IntBinaryOperator) - OptionalInt
numbers.stream().mapToInt(Integer::intValue)
    .reduce((a, b) -> a + b);  // OptionalInt[15]
numbers.stream().mapToInt(Integer::intValue)
    .reduce((a, b) -> a * b);  // OptionalInt[120]

// [예제 3] IntStream.reduce(identity, IntBinaryOperator) - int
numbers.stream().mapToInt(Integer::intValue)
    .reduce(0, (a, b) -> a + b);                        // 15 (합계)
numbers.stream().mapToInt(Integer::intValue)
    .reduce(1, (a, b) -> a * b);                        // 120 (곱)
numbers.stream().mapToInt(Integer::intValue)
    .reduce(Integer.MIN_VALUE, (a, b) -> a >= b ? a : b); // 5 (최댓값)

List.<Integer>of().stream().mapToInt(Integer::intValue)
    .reduce(0, (a, b) -> a + b);                        // 0 (빈 스트림 → identity)

// [예제 4] reduce로 count / sum / min / max 구현
numbers.stream().mapToInt(Integer::intValue).reduce(0, (acc, n) -> acc + 1); // 5 (count)
numbers.stream().mapToInt(Integer::intValue).reduce(0, (a, b) -> a + b);     // 15 (sum)
numbers.stream().mapToInt(Integer::intValue).reduce((a, b) -> a <= b ? a : b); // OptionalInt[1] (min)
numbers.stream().mapToInt(Integer::intValue).reduce((a, b) -> a >= b ? a : b); // OptionalInt[5] (max)
```

- `reduce(BinaryOperator)`는 빈 스트림 시 `Optional.empty()`를 반환한다.
- `reduce(identity, BinaryOperator)`는 빈 스트림 시 `identity`를 반환한다. `Optional` 불필요.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam12.App
  ```

---

## App2 - reduce() 3인자 / 실전 패턴

결과 타입이 다른 경우(3인자 형태), 문자열 연결, 실행 과정 시각화를 다룬다.

```java
// [예제 1] reduce(identity, accumulator, combiner) - T ≠ U
// 단어 리스트에서 총 문자 수 (String → int)
int totalLength = words.stream()
    .reduce(
        0,                                  // identity (int)
        (acc, word) -> acc + word.length(), // accumulator: (int, String) → int
        (a, b) -> a + b                     // combiner: (int, int) → int (병렬용)
    ); // 31

// [예제 2] 문자열 연결 패턴 비교
// reduce로 구현
names.stream()
    .reduce("", (acc, name) -> acc.isEmpty() ? name : acc + ", " + name);
// Alice, Bob, Charlie, Dave

// 실전에서는 Collectors.joining()이 더 간결
names.stream().collect(Collectors.joining(", "));
// Alice, Bob, Charlie, Dave

// [예제 3] 실행 과정 시각화
// reduce(0, (acc, n) -> acc + n):
//   acc=0,  n=1 → 1
//   acc=1,  n=2 → 3
//   acc=3,  n=3 → 6
//   acc=6,  n=4 → 10
//   acc=10, n=5 → 15
```

- 3인자 `reduce`의 `combiner`는 병렬 스트림에서 부분 결과를 합칠 때만 호출된다. 순차 스트림에서는 호출되지 않는다.
- 가변 컨테이너(List, Map)로 수집할 때는 `reduce`보다 `collect()`가 더 적합하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam12.App2
  ```
