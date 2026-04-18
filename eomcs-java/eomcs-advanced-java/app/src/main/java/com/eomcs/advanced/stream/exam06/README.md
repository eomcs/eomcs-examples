# Exam06 - 정렬

## 개념

### sorted() — 자연 순서 정렬

`sorted()`는 스트림 요소를 **자연 순서(natural order)** 로 정렬하는 중간 연산이다.
요소 타입이 `Comparable`을 구현해야 한다. `Integer`, `String` 등 JDK 기본 타입은 이미 구현되어 있다.

```java
numbers.stream().sorted(); // 오름차순 (자연 순서)
names.stream().sorted();   // 알파벳 순
```

### sorted(Comparator) — 지정 순서 정렬

`Comparator`를 인자로 받아 **원하는 순서**로 정렬한다.
커스텀 객체의 특정 필드로 정렬하거나, 역순 정렬할 때 사용한다.

```java
stream.sorted(Comparator.reverseOrder()); // 역순 (내림차순)
```

### Comparator 생성 메서드

| 메서드 | 설명 |
|---|---|
| `Comparator.naturalOrder()` | 자연 순서 (오름차순) |
| `Comparator.reverseOrder()` | 역순 (내림차순) |
| `Comparator.comparing(keyExtractor)` | 특정 필드 기준 오름차순 |
| `Comparator.comparingInt(keyExtractor)` | int 필드 기준 (박싱 없음) |
| `Comparator.comparingLong(keyExtractor)` | long 필드 기준 |
| `Comparator.comparingDouble(keyExtractor)` | double 필드 기준 |
| `comparator.reversed()` | 기존 Comparator 역순 |
| `comparator.thenComparing(keyExtractor)` | 1차 동률 시 2차 정렬 기준 추가 |
| `Comparator.nullsFirst(comparator)` | null을 맨 앞으로 (NullPointerException 방지) |
| `Comparator.nullsLast(comparator)` | null을 맨 뒤로 |

### 정렬의 특성

- **중간 연산**: 최종 연산이 호출될 때 정렬이 수행된다.
- **상태 있는 연산(stateful)**: 전체 요소를 버퍼에 모은 뒤 정렬한다. `filter`, `map`처럼 요소 하나씩 처리하지 않는다.
- **무한 스트림 불가**: `Stream.iterate()` 등 무한 스트림에 `sorted()`를 적용하면 종료되지 않는다.
- **안정 정렬(stable sort)**: 같은 키를 가진 요소의 상대적 순서가 보존된다.

### 자주 쓰는 정렬 패턴

```
정렬 후 상위 N개:      sorted → limit
정렬 후 페이징:        sorted → skip → limit
정렬 후 수집:          sorted → toList
정렬 후 최솟값/최댓값: min(Comparator) / max(Comparator)
```

---

## App - sorted() 기초

자연 순서 정렬, 역순 정렬, 정렬 + 슬라이싱, distinct + sorted를 다룬다.

```java
List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2, 7, 4, 6, 10);
List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

// [예제 1] sorted() - 자연 순서
numbers.stream().sorted()
    .forEach(...); // 1 2 3 4 5 6 7 8 9 10

names.stream().sorted()
    .forEach(...); // Alice Bob Charlie Dave Eve

// [예제 2] sorted(Comparator) - 역순
numbers.stream().sorted(Comparator.reverseOrder())
    .forEach(...); // 10 9 8 7 6 5 4 3 2 1

names.stream().sorted(Comparator.reverseOrder())
    .forEach(...); // Eve Dave Charlie Bob Alice

// [예제 3] sorted() + filter + limit - 정렬 후 상위 N개
numbers.stream()
    .filter(n -> n > 5)  // 8 9 7 6 10
    .sorted()            // 6 7 8 9 10
    .limit(3)            // 6 7 8
    .forEach(...);

// [예제 4] distinct() + sorted() - 중복 제거 후 정렬
Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5).stream()
    .distinct() // 3 1 4 5 9 2 6
    .sorted()   // 1 2 3 4 5 6 9
    .forEach(...);
```

- `sorted()`는 전체 요소를 버퍼링하는 **상태 있는(stateful) 연산**이다. 무한 스트림에는 사용할 수 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam06.App
  ```

---

## App2 - Comparator.comparing() / thenComparing()

커스텀 객체의 필드 정렬, 다중 정렬 기준, reversed, min/max를 다룬다.

```java
// [예제 1] Comparator.comparing() - 단일 필드
people.stream()
    .sorted(Comparator.comparing(Person::getName))  // 이름 오름차순
    .forEach(...);

people.stream()
    .sorted(Comparator.comparingInt(Person::getAge)) // 나이 오름차순 (박싱 없음)
    .forEach(...);

// [예제 2] reversed() - 내림차순
people.stream()
    .sorted(Comparator.comparingInt(Person::getAge).reversed()) // 나이 내림차순
    .forEach(...);

// [예제 3] thenComparing() - 다중 정렬 기준
// 나이 오름차순 → 나이가 같으면 이름 오름차순
people.stream()
    .sorted(Comparator.comparingInt(Person::getAge)
        .thenComparing(Person::getName))
    .forEach(...);

// 도시 오름차순 → 나이 내림차순 → 이름 오름차순
people.stream()
    .sorted(Comparator.comparing(Person::getCity)
        .thenComparing(Comparator.comparingInt(Person::getAge).reversed())
        .thenComparing(Person::getName))
    .forEach(...);

// [예제 4] min() / max() - Comparator 기반 최솟값/최댓값
people.stream()
    .min(Comparator.comparingInt(Person::getAge))
    .ifPresent(p -> System.out.println(p.getName())); // 가장 나이 어린 사람

people.stream()
    .max(Comparator.comparing(Person::getName))
    .ifPresent(p -> System.out.println(p.getName())); // 이름 알파벳 마지막
```

- `thenComparing()`으로 동률 시 2차, 3차 정렬 기준을 체이닝 방식으로 추가할 수 있다.
- `min(Comparator)` / `max(Comparator)`은 내부적으로 정렬 기준을 사용해 최솟값/최댓값을 `Optional<T>`로 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam06.App2
  ```

---

## App3 - 실전 패턴 (Product 도메인)

가격/평점 정렬, 다중 정렬, 정렬+페이징, 정렬+수집, null 안전 정렬을 다룬다.

```java
// [예제 1] 가격 오름차순
products.stream()
    .sorted(Comparator.comparingInt(Product::getPrice))
    .forEach(...);

// [예제 2] 평점 내림차순 → 같으면 가격 오름차순
products.stream()
    .sorted(Comparator.comparingDouble(Product::getRating).reversed()
        .thenComparingInt(Product::getPrice))
    .forEach(...);
// 마우스(4.8, 35,000)와 헤드셋(4.8, 120,000) → 평점 같으면 가격 오름차순

// [예제 3] 정렬 + 페이징 (sorted → skip → limit)
int pageSize = 3, page = 1;
products.stream()
    .sorted(Comparator.comparingInt(Product::getPrice))
    .skip((long) page * pageSize)
    .limit(pageSize)
    .forEach(...);

// [예제 4] 정렬 후 List 수집 (sorted → toList)
List<Product> affordable = products.stream()
    .filter(p -> p.getPrice() < 100_000)
    .sorted(Comparator.comparingInt(Product::getPrice))
    .toList();

// [예제 5] null 안전 정렬
List<String> withNulls = Arrays.asList("Banana", null, "Apple", null, "Cherry");

withNulls.stream()
    .sorted(Comparator.nullsFirst(Comparator.naturalOrder()))
    .forEach(...); // null null Apple Banana Cherry

withNulls.stream()
    .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
    .forEach(...); // Apple Banana Cherry null null
```

- `nullsFirst` / `nullsLast`는 정렬 대상에 `null`이 포함될 수 있을 때 `NullPointerException`을 방지한다.
- 정렬 후 페이징: `sorted → skip((long)page * size) → limit(size)` 패턴을 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam06.App3
  ```
