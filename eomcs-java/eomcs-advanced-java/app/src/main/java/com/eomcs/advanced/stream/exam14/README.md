# Exam14 - 수집 기초

## 개념

### collect(Collector) — 수집

`collect(Collector<T,A,R>)`는 스트림의 요소를 **컬렉션이나 다른 자료구조로 수집**하는 최종 연산이다.
`Collectors` 클래스가 다양한 Collector 팩토리 메서드를 제공한다.

### 리스트/셋 수집

| 메서드 | 반환 타입 | 가변 여부 | 비고 |
|---|---|---|---|
| `toList()` (Java 16+) | `List<T>` | 불변 | 가장 간결 |
| `Collectors.toList()` | `List<T>` | 가변 | null 허용 |
| `Collectors.toUnmodifiableList()` | `List<T>` | 불변 | null 불허 (Java 10+) |
| `Collectors.toSet()` | `Set<T>` | 가변 | 중복 제거, 순서 미보장 |
| `Collectors.toUnmodifiableSet()` | `Set<T>` | 불변 | Java 10+ |
| `Collectors.toCollection(Supplier)` | `C<T>` | 구현체에 따름 | LinkedList, TreeSet 등 |

### Map 수집

| 메서드 | 키 중복 | 비고 |
|---|---|---|
| `toMap(key, value)` | `IllegalStateException` | 키 중복 불허 |
| `toMap(key, value, merge)` | merge 함수로 처리 | 키 중복 허용 |
| `toMap(key, value, merge, mapFactory)` | merge 함수로 처리 | 구현체 지정 가능 |
| `toUnmodifiableMap(key, value)` | `IllegalStateException` | 불변 Map (Java 10+) |

### 문자열 수집

| 메서드 | 결과 |
|---|---|
| `joining()` | `"AliceBobCharlie"` |
| `joining(", ")` | `"Alice, Bob, Charlie"` |
| `joining(", ", "[", "]")` | `"[Alice, Bob, Charlie]"` |

---

## App - toList / toSet / toCollection

`toList()` vs `Collectors.toList()`, `toUnmodifiableList()`, `toSet()`, `toCollection()`을 다룬다.

```java
List<String> names = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Alice", "Dave");

// [예제 1] toList() vs Collectors.toList()
List<String> immutableList = names.stream().filter(...).toList();   // 불변
List<String> mutableList   = names.stream().filter(...).collect(Collectors.toList()); // 가변
mutableList.add("Frank"); // 가능

// [예제 2] Collectors.toUnmodifiableList()
List<Integer> unmodifiable = numbers.stream()
    .filter(n -> n > 5).sorted()
    .collect(Collectors.toUnmodifiableList()); // [6, 7, 8, 9, 10]

// [예제 3] Collectors.toSet() - 중복 제거
Set<String> nameSet = names.stream().collect(Collectors.toSet()); // Alice 중복 제거

// [예제 4] Collectors.toCollection() - 특정 구현체
LinkedList<String> linkedList = names.stream()
    .collect(Collectors.toCollection(LinkedList::new));

TreeSet<String> treeSet = names.stream()
    .collect(Collectors.toCollection(TreeSet::new)); // 정렬 + 중복 제거
```

- `toList()`는 Java 16+에서 불변 리스트를 반환한다. 가변이 필요하면 `Collectors.toList()`를 사용한다.
- `TreeSet`으로 수집하면 정렬과 중복 제거를 동시에 처리할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam14.App
  ```

---

## App2 - toMap()

`toMap` 기본, 요소 자체를 값으로, 키 중복 처리, `LinkedHashMap`, `toUnmodifiableMap`을 다룬다.

```java
// [예제 1] toMap(key, value) - id → name
Map<Integer, String> idToName = people.stream()
    .collect(Collectors.toMap(Person::getId, Person::getName));

// [예제 2] 요소 자체를 값으로
Map<Integer, Person> idToPerson = people.stream()
    .collect(Collectors.toMap(Person::getId, p -> p));

// [예제 3] 키 중복 처리 - mergeFunction
// 키 중복 시 기존 값 유지
Map<Integer, String> ageToName = people.stream()
    .collect(Collectors.toMap(
        Person::getAge, Person::getName,
        (existing, replacement) -> existing  // 기존 유지
    ));

// 키 중복 시 이름 연결
Map<Integer, String> ageToNames = people.stream()
    .collect(Collectors.toMap(
        Person::getAge, Person::getName,
        (a, b) -> a + ", " + b              // 연결
    ));

// [예제 4] LinkedHashMap - 삽입 순서 유지
Map<Integer, String> orderedMap = people.stream()
    .collect(Collectors.toMap(
        Person::getId, Person::getName,
        (a, b) -> a,
        LinkedHashMap::new
    ));

// [예제 5] toUnmodifiableMap - 불변 Map
Map<Integer, String> unmodifiableMap = people.stream()
    .collect(Collectors.toUnmodifiableMap(Person::getId, Person::getName));
```

- `toMap(key, value)`는 키 중복 시 `IllegalStateException`을 발생시킨다. 키 중복 가능성이 있으면 mergeFunction을 반드시 지정한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam14.App2
  ```

---

## App3 - joining()

단순 연결, 구분자, 접두사/접미사, map + joining 조합을 다룬다.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

// [예제 1] joining() - 단순 연결
names.stream().collect(Collectors.joining()); // AliceBobCharlieDaveEve

// [예제 2] joining(delimiter) - 구분자
names.stream().collect(Collectors.joining(", "));  // Alice, Bob, Charlie, Dave, Eve
names.stream().collect(Collectors.joining(" | ")); // Alice | Bob | Charlie | Dave | Eve

// [예제 3] joining(delimiter, prefix, suffix)
// JSON 배열
names.stream()
    .map(name -> "\"" + name + "\"")
    .collect(Collectors.joining(", ", "[", "]")); // ["Alice", "Bob", "Charlie", "Dave", "Eve"]

// SQL IN 절
ids.stream()
    .map(String::valueOf)
    .collect(Collectors.joining(", ", "(", ")")); // (1, 2, 3, 4, 5)

// [예제 4] map + joining - 객체 필드 추출 후 연결
people.stream().map(Person::getName).collect(Collectors.joining(", ")); // Alice, Bob, Charlie
people.stream()
    .map(p -> p.getName() + "(" + p.getAge() + ")")
    .collect(Collectors.joining(", ", "[", "]")); // [Alice(28), Bob(35), Charlie(22)]
```

- `joining()`은 `Stream<String>`에만 적용된다. 숫자는 `.map(String::valueOf)`로 변환 후 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam14.App3
  ```
