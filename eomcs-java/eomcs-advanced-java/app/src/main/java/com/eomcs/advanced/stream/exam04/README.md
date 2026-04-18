# Exam04 - 필터링과 슬라이싱

## 개념

### 필터링 (Filtering)

스트림에서 **조건을 만족하는 요소만 남기거나 중복을 제거**하는 연산이다.

| 연산 | 설명 |
|---|---|
| `filter(Predicate<T>)` | 조건을 만족하는 요소만 통과시킨다 |
| `distinct()` | `equals()`/`hashCode()` 기준으로 중복 요소를 제거한다. 처음 등장 순서 유지 |

### 슬라이싱 (Slicing)

스트림에서 **원하는 구간만 잘라내는** 연산이다.

| 연산 | 설명 |
|---|---|
| `limit(long n)` | 앞에서 최대 n개만 취한다. 단락 평가(short-circuit) 지원 |
| `skip(long n)` | 앞에서 n개를 버린다. n이 크기보다 크면 빈 스트림 반환 |
| `takeWhile(Predicate<T>)` | 조건을 만족하는 동안 취하다가 처음 false에서 즉시 종료 (Java 9+) |
| `dropWhile(Predicate<T>)` | 조건을 만족하는 동안 버리다가 처음 false에서부터 모두 취함 (Java 9+) |

### Predicate 조합

`filter()`에 전달하는 `Predicate<T>`를 조합해 복잡한 조건을 표현할 수 있다.

| 메서드 | 설명 | 단락 평가 |
|---|---|---|
| `a.and(b)` | a와 b 모두 true여야 true | 앞이 false면 b 실행 안 함 |
| `a.or(b)` | a 또는 b가 true면 true | 앞이 true면 b 실행 안 함 |
| `a.negate()` | a의 결과를 반전 | — |
| `Predicate.not(f)` | 메서드 레퍼런스에 negate 적용 (Java 11+) | — |

```java
// filter 두 번 체인 = and() 결합 (같은 결과)
stream.filter(a).filter(b)  ==  stream.filter(a.and(b))

// or() 조합은 filter 체인으로 표현 불가
stream.filter(a.or(b))      // a 또는 b 를 만족하는 요소
```

### takeWhile vs filter

```
데이터: [1, 3, 5, 7, 9, 11, 13]

filter(n < 10):    [1, 3, 5, 7, 9]  — 전체 7개 검사
takeWhile(n < 10): [1, 3, 5, 7, 9]  — 11에서 중단, 13은 미검사
```

| 항목 | `filter` | `takeWhile` |
|---|---|---|
| 검사 범위 | 모든 요소 | false 첫 등장 시 종료 |
| 비정렬 데이터 | 안전 | 예상치 못한 결과 가능 |
| 적합한 상황 | 일반적인 조건 필터 | 정렬된 데이터의 범위 추출 |

### limit + skip 페이지네이션 패턴

```java
// 페이지 번호(0부터), 페이지 크기
stream
    .skip((long) page * size)  // 이전 페이지 데이터 건너뜀
    .limit(size)               // 현재 페이지 데이터 취함
```

### distinct() 주의사항

커스텀 객체에 `distinct()`를 사용하려면 `equals()`와 `hashCode()`를 반드시 재정의해야 한다.
재정의하지 않으면 객체 참조(주소)로 비교하므로 중복이 제거되지 않는다.

```java
// NG: equals/hashCode 없음 → 참조 비교 → 중복 미제거
new Product("노트북") != new Product("노트북")  // 다른 객체이므로 distinct 안 됨

// OK: equals/hashCode 재정의 → 필드값 비교 → 중복 제거
@Override public boolean equals(Object o) { ... }
@Override public int hashCode() { ... }
```

---

## App - filter()와 Predicate 조합

단일 조건 `filter()`, `Predicate.and()` / `or()` / `negate()`, `Predicate`를 파라미터로 전달하는 패턴을 다룬다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve", "Frank");

// [예제 1] 단일 조건 filter
numbers.stream().filter(n -> n % 2 == 0)          // 2 4 6 8 10
numbers.stream().filter(n -> n > 5)               // 6 7 8 9 10
names.stream().filter(name -> name.length() >= 4) // Alice Charlie Dave Frank

// [예제 2] Predicate.and() - 두 조건 모두 만족
Predicate<Integer> isEven = n -> n % 2 == 0;
Predicate<Integer> isGt5  = n -> n > 5;

// filter 체인과 and()는 같은 결과
numbers.stream().filter(n -> n % 2 == 0).filter(n -> n > 5);  // 6 8 10
numbers.stream().filter(isEven.and(isGt5));                   // 6 8 10

// [예제 3] Predicate.or() - 두 조건 중 하나
Predicate<Integer> isLt3 = n -> n < 3;
Predicate<Integer> isGt8 = n -> n > 8;

numbers.stream().filter(isLt3.or(isGt8)); // 1 2 9 10

names.stream()
    .filter(startsWithA.or(startsWithE)); // Alice Eve

// [예제 4] Predicate.negate() / Predicate.not()
List<String> sentences = Arrays.asList("Java", "", "  ", "Stream", "  ", "API");

Predicate<String> isBlank = String::isBlank;
sentences.stream().filter(isBlank.negate());              // 'Java' 'Stream' 'API'
sentences.stream().filter(Predicate.not(String::isBlank)); // 'Java' 'Stream' 'API'

// [예제 5] 복합 조합: (짝수 OR 3의 배수) AND 10 미만
Predicate<Integer> isMultipleOf3 = n -> n % 3 == 0;
Predicate<Integer> isLt10       = n -> n < 10;
// isEven.or(isMultipleOf3).and(isLt10) = (isEven OR isMultipleOf3) AND isLt10
Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12).stream()
    .filter(isEven.or(isMultipleOf3).and(isLt10)); // 2 3 4 6 8 9

// [예제 6] Predicate를 파라미터로 전달 - 재사용
static int sumIf(List<Integer> list, Predicate<Integer> condition) {
    return list.stream().filter(condition).mapToInt(Integer::intValue).sum();
}
sumIf(numbers, n -> n % 2 == 0);  // 30 (짝수 합)
sumIf(numbers, n -> n > 5);        // 40 (5 초과 합)
```

- `filter(a).filter(b)`와 `filter(a.and(b))`는 결과가 같다. `Predicate`를 변수로 관리하거나 재사용할 때 `and()`가 유용하다.
- `or()`는 filter 체인으로 표현할 수 없다. 두 조건 중 하나라도 만족하는 요소를 걸러야 할 때 사용한다.
- `Predicate.not()`은 `String::isBlank`처럼 메서드 레퍼런스에 바로 negate를 적용할 수 있어 간결하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam04.App
  ```

---

## App2 - distinct() 중복 제거

`distinct()`의 동작 방식, `equals()`/`hashCode()`와의 관계, `Set`과의 차이를 다룬다.

```java
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);

// [예제 1] 기본 distinct()
numbers.stream().distinct()
    .forEach(n -> ...); // 3 1 4 5 9 2 6  (처음 등장 순서 유지)

numbers.stream().distinct().sorted()
    .forEach(n -> ...); // 1 2 3 4 5 6 9

// [예제 2] distinct() vs Set - 순서 보장
List<String> fruits = Arrays.asList("바나나", "사과", "바나나", "포도", "사과", "딸기");

Set.copyOf(fruits);                  // 순서 불확정 (HashSet)
fruits.stream().distinct().toList(); // [바나나, 사과, 포도, 딸기] (처음 등장 순서 유지)

// [예제 3] filter + distinct + sorted
Arrays.asList(1,2,3,...,2,4,6,8,10).stream()
    .filter(n -> n % 2 == 0)
    .distinct()
    .sorted();  // [2, 4, 6, 8, 10]

// [예제 4] 커스텀 객체 distinct() - equals/hashCode 필요
// equals/hashCode 재정의한 경우: 필드값 기준 중복 제거
products.stream().distinct(); // 노트북 마우스 책상 의자 (4개)

// equals/hashCode 미재정의: 참조 비교 → 중복 미제거
rawProducts.stream().distinct(); // 노트북 마우스 노트북 (3개 모두 남음!)

// [예제 5] 특정 필드 기준 중복 제거 (map + distinct)
products.stream()
    .map(Product::getCategory) // 카테고리 필드 추출
    .distinct()                // 중복 제거
    .sorted();                 // 가구 전자기기

// null 포함 시: filter(Objects::nonNull) 먼저
Arrays.asList("A", null, "B", null, "A", "C").stream()
    .filter(Objects::nonNull)
    .distinct()
    .sorted(); // A B C
```

- `distinct()`는 `equals()`/`hashCode()`로 동등성을 판단한다. 커스텀 객체는 반드시 두 메서드를 재정의해야 한다.
- `distinct()`는 처음 등장한 요소를 남기고 순서를 유지한다. `Set`은 순서를 보장하지 않는다.
- 특정 필드 기준 중복 제거: `map()`으로 해당 필드를 추출한 뒤 `distinct()`를 적용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam04.App2
  ```

---

## App3 - limit()과 skip() 슬라이싱

`limit(n)`, `skip(n)`, 두 연산의 조합으로 페이지네이션을 구현하는 방법을 다룬다.

```java
List<Integer> numbers = IntStream.rangeClosed(1, 20).boxed().toList(); // [1..20]

// [예제 1] limit(n) - 앞에서 n개만
numbers.stream().limit(5);    // [1, 2, 3, 4, 5]

numbers.stream()
    .filter(n -> n % 2 == 0)  // [2, 4, 6, 8, ..., 20]
    .limit(3);                 // [2, 4, 6]  (짝수 중 앞 3개)

// [예제 2] skip(n) - 앞에서 n개 건너뛰기
numbers.stream().skip(15);    // [16, 17, 18, 19, 20]

numbers.stream()
    .filter(n -> n % 2 == 0)  // [2, 4, 6, 8, ..., 20]
    .skip(3);                  // [8, 10, 12, ..., 20]  (앞 3개 skip)

numbers.stream().skip(100).count(); // 0 (빈 스트림)

// [예제 3] limit + skip 페이지네이션
int pageSize = 5;
for (int page = 0; page < 4; page++) {
    numbers.stream()
        .skip((long) page * pageSize) // 페이지 오프셋
        .limit(pageSize)              // 페이지 크기
        .toList();
}
// 페이지 0: [1, 2, 3, 4, 5]
// 페이지 1: [6, 7, 8, 9, 10]
// 페이지 2: [11, 12, 13, 14, 15]
// 페이지 3: [16, 17, 18, 19, 20]

// [예제 4] filter + limit - 조건을 만족하는 앞 N개
// 'A'로 시작하는 이름 중 처음 2개
names.stream().filter(name -> name.startsWith("A")).limit(2); // Alice Amy

// 1개 skip 후 2개
names.stream().filter(name -> name.startsWith("A")).skip(1).limit(2); // Amy Anna

// [예제 5] 순서에 따른 결과 차이
List.of(1,2,3,4,5,6,7,8,9,10).stream()
    .limit(5).skip(2).toList();  // [3, 4, 5]   (앞 5개 → 앞 2개 버림)
List.of(1,2,3,4,5,6,7,8,9,10).stream()
    .skip(2).limit(5).toList();  // [3, 4, 5, 6, 7]   (앞 2개 버림 → 5개 취함)

// [예제 6] 무한 스트림 + limit
Stream.iterate(new int[]{0,1}, f -> new int[]{f[1], f[0]+f[1]})
    .limit(8).map(f -> f[0]).toList(); // [0, 1, 1, 2, 3, 5, 8, 13]
```

- `limit(n)`은 단락 평가(short-circuit)를 지원한다. n개를 채우면 이후 요소를 처리하지 않는다.
- `skip(page * size).limit(size)` 패턴으로 페이지네이션을 구현한다.
- `limit(5).skip(2)`과 `skip(2).limit(5)`은 다른 결과를 낸다. 순서에 주의한다.
- `filter()` 뒤에 오는 `skip`/`limit`은 **필터를 통과한 요소 기준**으로 동작한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam04.App3
  ```

---

## App4 - takeWhile() / dropWhile() + 복합 파이프라인

`takeWhile()`, `dropWhile()`, 실전 필터링+정렬+페이지네이션 복합 파이프라인을 다룬다.

```java
// [예제 1] takeWhile() - 정렬된 데이터에서 범위 추출
List.of(1, 3, 5, 7, 9, 11, 13, 15).stream()
    .filter(n -> n < 10);    // [1, 3, 5, 7, 9]  (7개 모두 검사)
    .takeWhile(n -> n < 10); // [1, 3, 5, 7, 9]  (11에서 종료, 13은 미검사)

// [예제 2] 비정렬 데이터에서 takeWhile vs filter
List.of(2, 4, 1, 6, 3, 8, 5).stream()
    .filter(n -> n < 5);    // [2, 4, 1, 3]  (모든 요소 검사)
    .takeWhile(n -> n < 5); // [2, 4, 1]     (6에서 즉시 종료 → 3, 8, 5 미검사)

// [예제 3] dropWhile() + 중간 구간 추출
List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
    .dropWhile(n -> n < 5); // [5, 6, 7, 8, 9, 10]

// dropWhile + takeWhile 조합으로 중간 구간 추출
.dropWhile(n -> n < 5)    // 1,2,3,4 버림
.takeWhile(n -> n <= 9);  // [5, 6, 7, 8, 9]

// [예제 4] 실전 - 필터링 + 정렬 + 페이지네이션
students.stream()
    .filter(s -> s.getScore() >= 60)               // 합격 필터
    .sorted(Comparator.comparingInt(Student::getScore).reversed()) // 점수 내림차순
    .skip((long) page * size)                      // 페이지 오프셋
    .limit(size)                                   // 페이지 크기
    .toList();

// [예제 5] 실전 - 중복 집계 + 빈도 내림차순 + 상위 N개
List<String> tags = Arrays.asList("Java","Stream","Java","Lambda","Stream",...);

tags.stream()
    .collect(Collectors.groupingBy(t -> t, Collectors.counting())) // 빈도 집계
    .entrySet().stream()
    .sorted(Map.Entry.<String,Long>comparingByValue().reversed())  // 빈도 내림차순
    .limit(3)                                                      // 상위 3개
    .map(Map.Entry::getKey)
    .toList(); // [Java, Stream, Lambda]

// [예제 6] 실전 - 시간 순 로그에서 특정 시간대 추출
logs.stream()
    .dropWhile(log -> log.getTime().compareTo("09:00") < 0) // 09:00 이전 버림
    .takeWhile(log -> log.getTime().compareTo("10:00") < 0) // 10:00 이상에서 종료
    .forEach(...);
```

- `takeWhile()`은 정렬된 스트림에서 조건이 false가 되는 첫 요소 이후를 검사하지 않으므로 `filter()`보다 효율적이다.
- 비정렬 데이터에서 `takeWhile()`은 `filter()`와 결과가 다를 수 있다. 정렬된 데이터에서만 사용한다.
- `dropWhile()` + `takeWhile()` 조합으로 시작·끝 조건으로 중간 구간을 추출할 수 있다.
- `filter → sorted → skip → limit` 패턴은 검색 결과 페이지네이션의 기본 형태이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam04.App4
  ```
