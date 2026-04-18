# Exam13 - 배열 변환 (toArray)

## 개념

### toArray() — 배열로 수집

스트림의 요소를 배열로 수집하는 최종 연산이다.

| 형태 | 시그니처 | 반환 타입 | 특징 |
|---|---|---|---|
| 1 | `toArray()` | `Object[]` | 타입 정보 없음, 캐스팅 필요 |
| 2 | `toArray(T[]::new)` | `T[]` | 정확한 타입 배열, 캐스팅 불필요 |

### 배열 생성자 레퍼런스

`toArray(IntFunction<A[]>)`의 인자는 배열 크기를 받아 배열을 만드는 함수이다.

```java
// 배열 생성자 레퍼런스 = (int n) -> new String[n]
String[] arr = stream.toArray(String[]::new);
```

### 기본형 스트림 toArray

`IntStream`, `LongStream`, `DoubleStream`은 `toArray()`가 바로 기본형 배열을 반환한다.

```java
int[]    arr = intStream.toArray();    // int[]
long[]   arr = longStream.toArray();   // long[]
double[] arr = doubleStream.toArray(); // double[]
```

### toArray vs toList

| 항목 | `toArray(T[]::new)` | `toList()` (Java 16+) |
|---|---|---|
| 반환 타입 | `T[]` | `List<T>` (불변) |
| 요소 변경 | 가능 (`arr[i] = value`) | 불가 (`UnsupportedOperationException`) |
| 크기 변경 | 불가 (배열 크기 고정) | 불가 (불변 리스트) |
| 사용 시점 | 배열 API와 연동할 때 | 일반적인 컬렉션이 필요할 때 |

---

## App - toArray() 기초

`toArray()` vs `toArray(T[]::new)`, 중간 연산 후 배열 수집, `toArray` vs `toList` 비교를 다룬다.

```java
List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);

// [예제 1] toArray() → Object[]
Object[] objArray = names.stream().toArray();
// 타입: Object[], 길이: 5

// [예제 2] toArray(IntFunction) → 타입 배열 (권장)
String[] strArray = names.stream().toArray(String[]::new);
// 타입: String[], 길이: 5
// Arrays.toString(strArray) → [Charlie, Alice, Eve, Bob, Dave]

// [예제 3] filter + sorted + toArray
// IntStream.toArray() → int[]
int[] evenSorted = numbers.stream()
    .mapToInt(Integer::intValue)
    .filter(n -> n % 2 == 0)
    .sorted()
    .toArray(); // [2, 4, 6]

// String[] — 이름 길이순 정렬 후 배열
String[] sortedByLength = names.stream()
    .sorted(Comparator.comparingInt(String::length))
    .toArray(String[]::new);

// [예제 4] toArray vs toList
String[] array = names.stream().toArray(String[]::new);
List<String> list = names.stream().toList(); // Java 16+ (불변)

array[0] = "Modified"; // 가능 — 배열은 변경 가능
list.set(0, "Modified"); // UnsupportedOperationException — toList()는 불변
```

- `toArray(T[]::new)`를 사용하면 타입 안전한 배열을 얻을 수 있다. `toArray()`의 `Object[]`는 캐스팅이 필요하다.
- `IntStream.toArray()`는 별도의 인자 없이 `int[]`를 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam13.App
  ```
