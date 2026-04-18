# Exam11 - 단순 집계

## 개념

### count() — 요소 수

스트림의 요소 수를 `long`으로 반환한다.

```java
long count = stream.count();
long filteredCount = stream.filter(predicate).count();
```

### min(Comparator) / max(Comparator) — 최솟값 / 최댓값

Comparator 기준으로 최솟값/최댓값을 `Optional<T>`로 반환한다.
스트림이 비어 있으면 `Optional.empty()`를 반환한다.

```java
Optional<T> min = stream.min(Comparator.comparingInt(T::getField));
Optional<T> max = stream.max(Comparator.comparingInt(T::getField));
```

### 기본형 스트림 집계

`IntStream`, `LongStream`, `DoubleStream`은 박싱 없이 집계 메서드를 직접 제공한다.

| 메서드 | 반환 타입 | 빈 스트림 |
|---|---|---|
| `sum()` | `int` / `long` / `double` | `0` |
| `average()` | `OptionalDouble` | `OptionalDouble.empty()` |
| `min()` | `OptionalInt` / `OptionalLong` / `OptionalDouble` | `empty()` |
| `max()` | `OptionalInt` / `OptionalLong` / `OptionalDouble` | `empty()` |
| `summaryStatistics()` | `IntSummaryStatistics` 등 | count=0, sum=0 |

### summaryStatistics() — 단 1번 순회로 모든 통계

`sum()`, `average()`, `min()`, `max()`를 각각 호출하면 스트림을 4번 순회한다.
`summaryStatistics()`는 **단 1번 순회**로 count/sum/average/min/max를 모두 계산한다.

```java
IntSummaryStatistics stats = stream.mapToInt(f).summaryStatistics();
stats.getCount();    stats.getSum();    stats.getAverage();
stats.getMin();      stats.getMax();
```

---

## App - count() / min() / max()

요소 수 카운팅, 최솟값/최댓값, 빈 스트림 처리를 다룬다.

```java
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

// [예제 1] count()
numbers.stream().count();                             // 10
numbers.stream().filter(n -> n % 2 == 0).count();    // 5 (짝수 개수)
names.stream().filter(n -> n.length() > 3).count();  // 3

// [예제 2] min() / max()
numbers.stream().min(Integer::compareTo);  // Optional[1]
numbers.stream().max(Integer::compareTo);  // Optional[10]

names.stream().min(Comparator.comparingInt(String::length)); // Optional[Bob] (or Eve)
names.stream().max(Comparator.comparingInt(String::length)); // Optional[Charlie]

// [예제 3] 빈 스트림에서 min / max
numbers.stream()
    .filter(n -> n > 100)
    .min(Integer::compareTo); // Optional.empty
```

- `count()`는 `long`을 반환한다. `filter()`와 조합하면 조건 만족 개수를 센다.
- `min()` / `max()`는 `Optional<T>`를 반환한다. 빈 스트림이면 `Optional.empty()`이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam11.App
  ```

---

## App2 - 기본형 스트림 집계

`sum()`, `average()`, 기본형 `min()` / `max()`, `summaryStatistics()`를 다룬다.

```java
List<Integer> scores = Arrays.asList(85, 92, 78, 95, 88, 70, 99, 83, 91, 76);

// [예제 1] sum()
scores.stream().mapToInt(Integer::intValue).sum();                        // 857
scores.stream().mapToInt(Integer::intValue).filter(n -> n > 100).sum();   // 0 (빈 스트림)

// [예제 2] average()
scores.stream().mapToInt(Integer::intValue).average();                    // OptionalDouble[85.7]
scores.stream().mapToInt(Integer::intValue).filter(n -> n > 100).average(); // OptionalDouble.empty

// [예제 3] min() / max() - 기본형 스트림 (Comparator 불필요)
scores.stream().mapToInt(Integer::intValue).min(); // OptionalInt[70]
scores.stream().mapToInt(Integer::intValue).max(); // OptionalInt[99]

// [예제 4] summaryStatistics() - 1번 순회로 모든 통계
IntSummaryStatistics stats = scores.stream()
    .mapToInt(Integer::intValue)
    .summaryStatistics();

stats.getCount();    // 10
stats.getSum();      // 857
stats.getAverage();  // 85.7
stats.getMin();      // 70
stats.getMax();      // 99
```

- `sum()`은 빈 스트림에서 `0`을 반환한다. `average()`는 빈 스트림에서 `OptionalDouble.empty()`를 반환한다.
- 기본형 스트림의 `min()` / `max()`는 `Comparator` 없이 `OptionalInt` 등을 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam11.App2
  ```
