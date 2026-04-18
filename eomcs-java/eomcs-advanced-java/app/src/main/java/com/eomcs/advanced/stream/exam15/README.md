# Exam15 - 수집 고급

## 개념

### groupingBy() — 그룹화

스트림 요소를 분류 함수의 결과값으로 그룹화한다.

| 형태 | 반환 타입 |
|---|---|
| `groupingBy(classifier)` | `Map<K, List<T>>` |
| `groupingBy(classifier, downstream)` | `Map<K, D>` |
| `groupingBy(classifier, mapFactory, downstream)` | `Map<K, D>` (구현체 지정) |

### partitioningBy() — 이진 분류

조건(Predicate) 기준으로 두 그룹으로 나눈다. 키는 항상 `Boolean`이다.

| 형태 | 반환 타입 |
|---|---|
| `partitioningBy(predicate)` | `Map<Boolean, List<T>>` |
| `partitioningBy(predicate, downstream)` | `Map<Boolean, D>` |

### groupingBy vs partitioningBy

| 항목 | `groupingBy` | `partitioningBy` |
|---|---|---|
| 그룹 수 | N개 | 2개 (true/false) |
| 키 타입 | 분류 함수의 반환 타입 | `Boolean` |
| 빈 그룹 | 키 없음 | false 키도 항상 존재 |
| 사용 시점 | 카테고리별 분류 | 합격/불합격, 활성/비활성 |

### 주요 downstream Collector

| Collector | 설명 | 반환 타입 |
|---|---|---|
| `counting()` | 그룹 요소 수 | `Long` |
| `summingInt(f)` | 그룹 요소의 int 합계 | `Integer` |
| `averagingInt(f)` | 그룹 요소의 int 평균 | `Double` |
| `minBy(Comparator)` | 그룹 최솟값 | `Optional<T>` |
| `maxBy(Comparator)` | 그룹 최댓값 | `Optional<T>` |
| `mapping(f, dc)` | 변환 후 downstream 적용 | downstream의 결과 |
| `joining(delimiter)` | 문자열 연결 | `String` |
| `filtering(pred, dc)` | 필터 후 downstream 적용 (Java 9+) | downstream의 결과 |
| `toList()` | 그룹 요소를 List로 | `List<T>` |

### teeing() — 두 Collector 동시 처리 (Java 12+)

동일한 스트림을 두 Collector로 동시에 처리한 뒤 결과를 합친다.
스트림을 두 번 순회하지 않고 두 가지 집계를 한 번에 처리한다.

```java
stream.collect(Collectors.teeing(
    collector1,          // 첫 번째 Collector → R1
    collector2,          // 두 번째 Collector → R2
    (r1, r2) -> result  // merger: R1 + R2 → R
));
```

---

## App - groupingBy()

단일 기준 그룹화, `counting`, `mapping + joining`, `averagingInt`, 다단계 그룹화를 다룬다.

```java
// [예제 1] groupingBy - 도시별 그룹화 → Map<String, List<Person>>
Map<String, List<Person>> byCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity));
// {서울=[Alice, Charlie, Eve], 부산=[Bob, Frank], 대구=[Dave, Grace]}

// [예제 2] groupingBy + counting - 도시별 인원 수
Map<String, Long> countByCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));

// [예제 3] groupingBy + mapping + joining - 부서별 이름 목록
Map<String, String> namesByDept = people.stream()
    .collect(Collectors.groupingBy(
        Person::getDept,
        Collectors.mapping(Person::getName, Collectors.joining(", "))
    ));
// {개발=Alice, Charlie, Dave, Frank, 기획=Bob, Eve, Grace}

// [예제 4] groupingBy + averagingInt - 도시별 평균 나이
Map<String, Double> avgAgeByCity = people.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.averagingInt(Person::getAge)
    ));

// [예제 5] 다단계 groupingBy - 도시별 → 부서별
Map<String, Map<String, List<Person>>> byCityAndDept = people.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.groupingBy(Person::getDept)
    ));
```

- `groupingBy(classifier)`는 `Map<K, List<T>>`를 반환한다. 두 번째 인자로 downstream Collector를 지정한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam15.App
  ```

---

## App2 - partitioningBy()

기본 이진 분류, downstream 적용, `groupingBy` vs `partitioningBy` 비교를 다룬다.

```java
// [예제 1] partitioningBy - 짝수/홀수
Map<Boolean, List<Integer>> evenOdd = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
evenOdd.get(true);  // [2, 4, 6, 8, 10]
evenOdd.get(false); // [1, 3, 5, 7, 9]

// [예제 2] partitioningBy - 30세 이상/미만
Map<Boolean, List<Person>> agePartition = people.stream()
    .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));

// [예제 3] partitioningBy + counting
Map<Boolean, Long> countPartition = people.stream()
    .collect(Collectors.partitioningBy(
        p -> p.getDept().equals("개발"),
        Collectors.counting()
    ));

// [예제 4] partitioningBy + mapping + joining
Map<Boolean, String> namePartition = people.stream()
    .collect(Collectors.partitioningBy(
        p -> p.getCity().equals("서울"),
        Collectors.mapping(Person::getName, Collectors.joining(", "))
    ));
```

- `partitioningBy`는 조건을 만족하는 요소가 없어도 `false` 키의 빈 리스트를 보장한다.
- 이진 분류에는 `groupingBy`보다 `partitioningBy`가 의도를 더 명확히 표현한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam15.App2
  ```

---

## App3 - downstream Collector / teeing()

`summingInt`, `averagingInt`, `minBy`, `maxBy`, `teeing()`을 다룬다.

```java
// [예제 1] groupingBy + summingInt / averagingInt
Map<String, Integer> ageSumByCity = people.stream()
    .collect(Collectors.groupingBy(
        Person::getCity, Collectors.summingInt(Person::getAge)));

Map<String, Double> avgAgeByDept = people.stream()
    .collect(Collectors.groupingBy(
        Person::getDept, Collectors.averagingInt(Person::getAge)));

// [예제 2] groupingBy + minBy / maxBy
Map<String, Optional<Person>> youngestByCity = people.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.minBy(Comparator.comparingInt(Person::getAge))
    ));

// [예제 3] teeing() - 최솟값과 최댓값을 동시에 (Java 12+)
int[] minMax = scores.stream()
    .collect(Collectors.teeing(
        Collectors.minBy(Integer::compareTo), // R1: 최솟값
        Collectors.maxBy(Integer::compareTo), // R2: 최댓값
        (min, max) -> new int[]{min.orElse(0), max.orElse(0)}
    ));

// [예제 4] teeing() - 합격/불합격 동시 집계
record Stats(List<Integer> passed, Long failedCount) {}

Stats stats = scores.stream()
    .collect(Collectors.teeing(
        Collectors.filtering(s -> s >= 80, Collectors.toList()),  // 합격자 목록
        Collectors.filtering(s -> s < 80,  Collectors.counting()), // 불합격자 수
        Stats::new
    ));
```

- `counting()`은 `Long`을 반환한다. 레코드/클래스 필드 타입도 `Long`으로 맞춰야 null 타입 안전성 경고가 없다.
- `teeing()`으로 하나의 스트림을 두 번 순회하지 않고 두 가지 집계를 동시에 처리할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam15.App3
  ```
