# Exam05 - 매핑

## 개념

### map() — 1:1 변환

스트림의 각 요소를 **다른 타입이나 값으로 변환**하는 중간 연산이다.
요소 수는 변하지 않는다. 하나의 요소가 변환된 하나의 요소로 대응된다.

```
Stream<T>  --map(T→R)-->  Stream<R>
  [A, B, C]               [f(A), f(B), f(C)]
```

### flatMap() — 1:N 변환 + 평탄화

각 요소를 `Stream<R>`으로 변환한 뒤, 모든 스트림을 **하나의 스트림으로 합친다**.
요소 하나가 0개 이상의 요소로 변환된다.

```
Stream<T>  --flatMap(T→Stream<R>)-->  Stream<R>
  [[1,2], [3,4], [5]]                 [1, 2, 3, 4, 5]
```

### map vs flatMap

| 항목 | `map` | `flatMap` |
|---|---|---|
| 변환 비율 | 1:1 | 1:N |
| 함수 반환 타입 | `R` | `Stream<R>` |
| 결과 타입 | `Stream<R>` | `Stream<R>` (평탄화) |
| 잘못 쓴 경우 | — | `Stream<Stream<R>>` (중첩) |

```java
// map 잘못 쓴 예 (중첩 스트림)
list.stream().map(inner -> inner.stream()); // Stream<Stream<Integer>>

// flatMap으로 올바르게
list.stream().flatMap(Collection::stream); // Stream<Integer>
```

### 기본형 특화 매핑

| 메서드 | 결과 타입 | 장점 |
|---|---|---|
| `mapToInt(ToIntFunction<T>)` | `IntStream` | 박싱 없음, `sum()`/`average()` 제공 |
| `mapToLong(ToLongFunction<T>)` | `LongStream` | 박싱 없음, 큰 수치 처리 |
| `mapToDouble(ToDoubleFunction<T>)` | `DoubleStream` | 박싱 없음, 실수 계산 |
| `flatMapToInt(f)` | `IntStream` | `flatMap` + `mapToInt` 한 번에 |

### 기본형 스트림 복귀

| 메서드 | 변환 |
|---|---|
| `intStream.boxed()` | `IntStream` → `Stream<Integer>` |
| `intStream.mapToObj(f)` | `IntStream` → `Stream<R>` |
| `intStream.asLongStream()` | `IntStream` → `LongStream` |
| `intStream.asDoubleStream()` | `IntStream` → `DoubleStream` |

### summaryStatistics()

`sum()`, `average()`, `min()`, `max()`를 각각 별도 스트림으로 계산하면 N번 순회한다.
`summaryStatistics()`는 **단 1번 순회로 모든 통계를 계산**한다.

```java
IntSummaryStatistics stats = stream.mapToInt(f).summaryStatistics();
stats.getCount();   // 개수
stats.getSum();     // 합계
stats.getAverage(); // 평균
stats.getMax();     // 최대
stats.getMin();     // 최소
```

---

## App - map() 기초

타입 변환, 필드 추출, 메서드 레퍼런스, map 체이닝, 조건부 변환을 다룬다.

```java
// [예제 1] String → Integer 변환
List<String> strs = Arrays.asList("1", "2", "3", "4", "5");

strs.stream().map(s -> Integer.parseInt(s));  // 람다
strs.stream().map(Integer::parseInt);          // 정적 메서드 레퍼런스 (더 간결)

// [예제 2] 문자열 변환
Arrays.asList("  alice  ", "BOB", "Charlie", "  dave").stream()
    .map(String::trim)         // 공백 제거
    .map(String::toUpperCase); // 대문자
// → ALICE BOB CHARLIE DAVE

// [예제 3] 객체 필드 추출 (Person → String / int)
List<Person> people = ...;

people.stream().map(Person::getName);  // Person → String (인스턴스 메서드 레퍼런스)
people.stream().map(Person::getAge);   // Person → Integer
people.stream().mapToInt(Person::getAge).sum(); // 나이 합계 (박싱 없음)

// [예제 4] Entity → DTO 변환
people.stream()
    .map(p -> new PersonDto(p.getName().toUpperCase(), p.getAge() >= 30))
    .forEach(dto -> System.out.printf("이름: %s 시니어: %s%n",
        dto.getName(), dto.isSenior()));

// [예제 5] map 체이닝 - 여러 변환 순서대로 적용
Arrays.asList(" apple ", "BANANA", "  Cherry  ").stream()
    .map(String::trim)                                             // 공백 제거
    .map(String::toLowerCase)                                      // 소문자
    .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1)); // 첫 글자 대문자
// → Apple Banana Cherry

// [예제 6] 조건부 변환 - null 안전 처리
Arrays.asList("hello", null, "world").stream()
    .map(s -> s != null ? s.toUpperCase() : "N/A");
// → HELLO N/A WORLD
```

- `map()`은 요소 수를 유지한다. `filter()`는 요소를 제거하지만 `map()`은 변환만 한다.
- 메서드 레퍼런스(`String::toUpperCase`)는 람다(`s -> s.toUpperCase()`)와 동등하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam05.App
  ```

---

## App2 - flatMap() 중첩 스트림 평탄화

중첩 리스트 평탄화, 문장→단어 분해, 계층 구조 평탄화, `flatMapToInt`를 다룬다.

```java
// [예제 1] map vs flatMap 차이
List<List<Integer>> nested = [[1,2,3], [4,5], [6,7,8,9]];

nested.stream().map(List::stream);     // Stream<Stream<Integer>> (중첩!)
nested.stream().flatMap(List::stream); // Stream<Integer> → 1 2 3 4 5 6 7 8 9

// [예제 2] 문장 → 단어 분해
List.of("Java Stream API is powerful", "flatMap flattens nested streams").stream()
    .flatMap(sentence -> Arrays.stream(sentence.split(" "))) // 문장 → 단어 스트림
    .distinct()
    .sorted()
    .forEach(System.out::println);

// [예제 3] 단어 빈도 분석
texts.stream()
    .flatMap(line -> Arrays.stream(line.split(" ")))
    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

// [예제 4] 중첩 객체 평탄화 - 부서 → 직원
List<Department> depts = ...;

depts.stream()
    .flatMap(dept -> dept.getMembers().stream()) // Department → Stream<String>
    .filter(name -> name.length() >= 5)
    .sorted()
    .forEach(System.out::println);

// [예제 5] flatMapToInt - 중첩 숫자 리스트 집계
List<List<Integer>> scoreGroups = [[85,90,78], [92,88], [70,95,83,77]];

// 두 단계: flatMap + mapToInt
scoreGroups.stream()
    .flatMap(List::stream)
    .mapToInt(Integer::intValue)
    .sum(); // 758

// 한 단계: flatMapToInt
scoreGroups.stream()
    .flatMapToInt(group -> group.stream().mapToInt(Integer::intValue))
    .sum(); // 758 (더 간결)
```

- `flatMap()`은 "변환 + 평탄화"를 한 번에 처리한다. 중첩 컬렉션을 단일 스트림으로 만든다.
- `map()`은 요소가 1:1 변환, `flatMap()`은 1:N 변환이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam05.App2
  ```

---

## App3 - 기본형 매핑 스트림

`mapToInt`, `mapToLong`, `mapToDouble`, `summaryStatistics()`, 기본형 스트림 복귀를 다룬다.

```java
// [예제 1] mapToInt - 객체 → IntStream
List<String> words = Arrays.asList("Java", "Stream", "API", "is", "powerful");

words.stream().mapToInt(String::length)
    .forEach(...); // 4 6 3 2 8

words.stream().mapToInt(String::length).sum();               // 23
words.stream().mapToInt(String::length).average().orElse(0); // 4.6
words.stream().mapToInt(String::length).max().orElse(0);     // 8

// [예제 2] Stream<Integer> vs IntStream - 박싱 오버헤드
scores.stream()
    .map(Integer::parseInt)      // String → Integer (박싱 발생)
    .reduce(0, (a, b) -> a + b); // Integer → int (언박싱 발생)

scores.stream()
    .mapToInt(Integer::parseInt) // String → int (박싱 없음)
    .sum();                       // int 기본형으로 바로 계산

// [예제 3] summaryStatistics - 1번 순회로 모든 통계
IntSummaryStatistics stats = numbers.stream()
    .mapToInt(Integer::intValue)
    .summaryStatistics();

stats.getCount();   stats.getSum();   stats.getAverage();
stats.getMax();     stats.getMin();

// [예제 4] mapToLong - 큰 수치 (int 범위 초과)
fileSizes.stream()
    .mapToLong(Long::parseLong)
    .sum(); // long 타입으로 합계

// [예제 5] mapToDouble - 실수 계산
prices.stream()
    .mapToDouble(Double::parseDouble)
    .map(p -> p * 0.9)   // DoubleStream.map: double → double
    .sum();

// [예제 6] 기본형 스트림 → 객체 스트림
IntStream.rangeClosed(1, 5).boxed().toList();               // [1,2,3,4,5]
IntStream.rangeClosed(1, 5).mapToObj(i -> "item-" + i).toList(); // [item-1, ...]
IntStream.rangeClosed(1, 10).asLongStream().sum();           // 55 (long)
IntStream.rangeClosed(1, 10).asDoubleStream().average();     // OptionalDouble[5.5]
```

- `mapToInt()`는 박싱 없이 `int`를 다루므로 대량 수치 처리에서 `Stream<Integer>`보다 성능이 유리하다.
- `summaryStatistics()`는 count/sum/average/max/min을 단 1번 순회로 계산한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam05.App3
  ```

---

## App4 - 복합 매핑 실전

Entity→DTO 변환, 중첩 데이터 집계, 그룹 집계 후 재변환, 인덱스 포함 변환, CSV 파싱을 다룬다.

```java
// [예제 1] Entity → DTO 변환 파이프라인
orders.stream()
    .filter(o -> "완료".equals(o.getStatus()))
    .map(o -> new OrderDto(o.getId(), o.getCustomer(),
        String.format("%,d원", o.getAmount())))     // Entity → DTO
    .sorted(Comparator.comparingInt(OrderDto::getId).reversed())
    .toList();

// [예제 2] 중첩 데이터 평탄화 + 집계
// 모든 카테고리의 상품 합계
categories.stream()
    .flatMap(cat -> cat.getProducts().stream())   // Category → Stream<Product>
    .mapToInt(Product::getPrice)
    .sum();

// 30만원 이상 상품을 카테고리명과 함께
categories.stream()
    .flatMap(cat -> cat.getProducts().stream()
        .filter(p -> p.getPrice() >= 300_000)
        .map(p -> "[" + cat.getName() + "] " + p.getName()))
    .forEach(System.out::println);

// [예제 3] 그룹 집계 후 재변환
categories.stream()
    .collect(Collectors.toMap(
        Category::getName,
        cat -> cat.getProducts().stream().mapToInt(Product::getPrice).average().orElse(0)
    ))
    .entrySet().stream()
    .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
    .map(e -> e.getKey() + " 평균: " + e.getValue())
    .forEach(System.out::println);

// [예제 4] 인덱스 포함 변환 - IntStream.range + mapToObj
// Stream에는 인덱스가 없으므로 IntStream.range를 활용한다.
IntStream.range(0, items.size())
    .mapToObj(i -> (i + 1) + ". " + items.get(i))
    .forEach(System.out::println);

IntStream.range(0, items.size())
    .filter(i -> i % 2 == 0)    // 짝수 인덱스 필터
    .mapToObj(items::get)       // 인덱스 → 요소값
    .forEach(System.out::println);

// [예제 5] CSV 파싱 파이프라인
List<String> csvLines = Arrays.asList("Alice,30,서울", "Bob,25,부산", ...);

List<PersonRecord> people = csvLines.stream()
    .map(line -> line.split(","))                    // String → String[]
    .map(parts -> new PersonRecord(
        parts[0].trim(), Integer.parseInt(parts[1].trim()), parts[2].trim()))
    .toList();

people.stream()
    .filter(p -> "서울".equals(p.city()))
    .sorted(Comparator.comparing(PersonRecord::name))
    .forEach(p -> System.out.println(p.name() + " (" + p.age() + "세)"));
```

- `filter → map → sorted → toList` 패턴은 데이터 변환 파이프라인의 기본 형태이다.
- 중첩 구조는 `flatMap`으로 평탄화한 뒤 `filter`/`map`/`collect`를 적용한다.
- 인덱스가 필요하면 `IntStream.range(0, list.size()).mapToObj(i -> ...)`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam05.App4
  ```
