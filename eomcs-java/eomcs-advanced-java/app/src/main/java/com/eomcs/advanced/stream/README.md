# Java Stream과 Optional

## 학습 목표

- 명령형 프로그래밍과 선언형 프로그래밍의 차이를 이해하고, 스트림 파이프라인으로 데이터 처리 흐름을 표현할 수 있다.
- `Optional`을 사용하여 `null` 반환과 `null` 검사를 줄이고, 값이 없을 수 있는 상황을 안전하게 처리할 수 있다.
- 컬렉션, 배열, 값, 숫자 범위, 파일, 문자열 등 다양한 소스에서 스트림을 생성하는 방법을 이해하고 활용할 수 있다.
- `filter()`, `distinct()`, `limit()`, `skip()`, `takeWhile()`, `dropWhile()`를 사용하여 데이터를 조건에 맞게 걸러내고 일부만 선택할 수 있다.
- `map()`, `flatMap()`, `mapMulti()`와 기본형 특화 스트림을 사용하여 데이터를 원하는 형태로 변환하고 평탄화할 수 있다.
- `sorted()`와 `Comparator`를 사용하여 자연 순서와 사용자 정의 기준으로 스트림 요소를 정렬할 수 있다.
- `peek()`를 사용하여 스트림 파이프라인의 중간 상태를 관찰하고, 디버깅 용도로 적절히 활용할 수 있다.
- `anyMatch()`, `allMatch()`, `noneMatch()`, `findFirst()`, `findAny()` 등 검색과 매칭 연산의 단락 평가 동작을 이해할 수 있다.
- `forEach()`, `forEachOrdered()`, `count()`, `sum()`, `min()`, `max()`, `average()` 등 반복과 단순 집계 연산을 사용할 수 있다.
- `reduce()`를 사용하여 스트림 요소를 하나의 값으로 누적하고, identity와 accumulator의 역할을 설명할 수 있다.
- `toArray()`와 생성자 참조를 사용하여 스트림 결과를 배열로 변환할 수 있다.
- `collect()`와 `Collectors`를 사용하여 스트림 결과를 `List`, `Set`, `Map`으로 수집하고, 그룹핑·분할·집계 처리를 수행할 수 있다.
- 병렬 스트림의 동작 방식과 주의사항을 이해하고, 순서·공유 상태·성능 특성을 고려하여 적절히 사용할 수 있다.

---

## Exam01 - 명령형 vs 선언형

### 개념

#### 명령형(Imperative) 프로그래밍

"**어떻게(How)**" 처리할지를 단계별로 기술하는 방식이다.
`for` 루프, `if` 조건, 임시 변수, 누적 변수 등 제어 흐름을 개발자가 직접 작성한다.

```java
// 짝수만 출력 - 명령형
for (int n : numbers) {
  if (n % 2 == 0) {
    System.out.print(n + " ");
  }
}
```

#### 선언형(Declarative) 프로그래밍 - Stream

"**무엇을(What)**" 원하는지를 기술하는 방식이다.
필터링·변환·집계 등의 연산을 메서드 체인으로 연결하며, 제어 흐름은 스트림 내부에서 처리한다.

```java
// 짝수만 출력 - 선언형
numbers.stream()
    .filter(n -> n % 2 == 0)
    .forEach(n -> System.out.print(n + " "));
```

#### Stream 파이프라인 구조

```
소스(Source)  →  중간 연산(Intermediate)  →  최종 연산(Terminal)
List/배열 등      filter, map, sorted ...      forEach, collect, count ...
```

##### 중간 연산 (Intermediate Operation)

| 연산 | 설명 |
|---|---|
| `filter(Predicate<T>)` | 조건을 만족하는 요소만 통과시킨다 |
| `map(Function<T, R>)` | 각 요소를 다른 값으로 변환한다 |
| `mapToInt(ToIntFunction<T>)` | `IntStream`으로 변환한다. `sum()`, `average()` 등을 바로 사용할 수 있다 |
| `sorted()` | 자연 순서(오름차순)로 정렬한다 |
| `sorted(Comparator<T>)` | 지정한 Comparator 기준으로 정렬한다 |

##### 최종 연산 (Terminal Operation)

| 연산 | 설명 |
|---|---|
| `forEach(Consumer<T>)` | 각 요소에 동작을 수행한다. 반환값 없음 |
| `collect(Collector)` | 결과를 `List`, `Set`, `Map` 등으로 수집한다 |
| `reduce(identity, BinaryOperator)` | 요소를 하나의 값으로 누적한다 |
| `count()` | 요소 개수를 `long`으로 반환한다 |
| `sum()` | 합계를 반환한다 (`IntStream` / `LongStream` 전용) |
| `max(Comparator)` | 최댓값을 `Optional<T>`로 반환한다 |
| `min(Comparator)` | 최솟값을 `Optional<T>`로 반환한다 |
| `anyMatch(Predicate)` | 조건을 만족하는 요소가 하나라도 있으면 `true` |
| `allMatch(Predicate)` | 모든 요소가 조건을 만족하면 `true` |
| `noneMatch(Predicate)` | 조건을 만족하는 요소가 하나도 없으면 `true` |
| `findFirst()` | 첫 번째 요소를 `Optional<T>`로 반환한다 |

##### 핵심 특징

| 특징 | 설명 |
|---|---|
| 지연(lazy) 실행 | 중간 연산은 최종 연산이 호출될 때 한꺼번에 처리된다 |
| 원본 불변 | 스트림은 원본 컬렉션을 변경하지 않는다 |
| 일회성 | 스트림은 한 번 소비하면 재사용할 수 없다 |
| 단락 평가(short-circuit) | `anyMatch`, `findFirst` 등은 조건 충족 즉시 나머지 요소를 처리하지 않는다 |

##### 명령형 vs 선언형 비교

| 항목 | 명령형 | 선언형(스트림) |
|---|---|---|
| 초점 | 어떻게(How) 처리할지 | 무엇을(What) 원하는지 |
| 제어 흐름 | 개발자가 직접 작성 | 스트림 내부에서 처리 |
| 복합 조건 | `if` 중첩이 깊어짐 | `filter()` 추가로 해결 |
| 결과 수집 | 새 리스트 생성 + `add()` | `collect(Collectors.toList())` |
| 정렬 | 원본 복사 + `Collections.sort()` | `sorted()` (원본 불변) |
| 검색 | 플래그 변수 + `break` | `anyMatch()`, `findFirst()` |
| null 처리 | `null` 반환 → 호출자가 체크 | `Optional` 반환 → `orElse()` 활용 |

##### Optional\<T\>

`findFirst()`, `max()`, `min()` 등은 결과가 없을 수 있으므로 `Optional<T>`로 반환한다.
`null`을 직접 다루는 대신 `Optional`의 메서드로 안전하게 처리할 수 있다.

| 메서드 | 설명 |
|---|---|
| `isPresent()` | 값이 있으면 `true` |
| `get()` | 값을 꺼낸다. 값이 없으면 `NoSuchElementException` 발생 |
| `orElse(T)` | 값이 없으면 기본값을 반환한다 |
| `orElseThrow()` | 값이 없으면 `NoSuchElementException`을 발생시킨다 |

---

### App - 필터링(filter)과 출력(forEach)

명령형의 `for + if` 패턴과 스트림의 `filter().forEach()`를 비교한다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// [예제 1] 짝수만 출력
// 명령형
for (int n : numbers) {
  if (n % 2 == 0) {
    System.out.print(n + " "); // 2 4 6 8 10
  }
}

// 선언형
numbers.stream()
    .filter(n -> n % 2 == 0)              // 짝수만 통과
    .forEach(n -> System.out.print(n + " ")); // 2 4 6 8 10

// [예제 2] 5보다 큰 수만 출력
numbers.stream()
    .filter(n -> n > 5)
    .forEach(n -> System.out.print(n + " ")); // 6 7 8 9 10

// [예제 3] 짝수이면서 5보다 큰 수만 출력 (복합 조건)
// 명령형: if 중첩이 깊어진다.
for (int n : numbers) {
  if (n % 2 == 0) {
    if (n > 5) {
      System.out.print(n + " "); // 6 8 10
    }
  }
}

// 선언형: filter()를 이어 붙이면 된다.
numbers.stream()
    .filter(n -> n % 2 == 0) // 짝수 필터
    .filter(n -> n > 5)      // 5 초과 필터
    .forEach(n -> System.out.print(n + " ")); // 6 8 10

// [예제 4] 길이가 4 이상인 과일 이름만 출력
List<String> fruits = Arrays.asList("사과", "바나나", "딸기", "수박", "포도", "키위", "블루베리");

fruits.stream()
    .filter(fruit -> fruit.length() >= 4)
    .forEach(fruit -> System.out.print(fruit + " ")); // 바나나 블루베리
```

- 명령형은 `if` 조건이 늘어날수록 중첩이 깊어져 가독성이 떨어진다.
- 선언형은 `filter()`를 추가로 이어 붙이면 되므로 조건이 늘어도 코드 구조가 평탄하게 유지된다.
- `forEach()`는 결과를 반환하지 않는 최종 연산이다. 반환이 필요하면 `collect()`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam01.App
  ```

---

### App2 - 변환(map)과 집계(reduce / sum)

명령형의 누적 변수 패턴과 스트림의 `map()`, `mapToInt()`, `reduce()`, `sum()`을 비교한다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// [예제 1] 각 숫자를 제곱으로 변환하여 출력
// 명령형: 변환과 출력이 한 곳에 섞인다.
for (int n : numbers) {
  System.out.print((n * n) + " "); // 1 4 9 16 25 36 49 64 81 100
}

// 선언형: map()이 변환, forEach()가 출력을 각각 담당한다. 역할이 분리된다.
numbers.stream()
    .map(n -> n * n)
    .forEach(n -> System.out.print(n + " ")); // 1 4 9 16 25 36 49 64 81 100

// [예제 2] 문자열을 대문자로 변환
List<String> names = Arrays.asList("alice", "bob", "charlie", "dave", "eve");

names.stream()
    .map(String::toUpperCase)  // 메서드 레퍼런스로 간결하게 표현
    .forEach(name -> System.out.print(name + " ")); // ALICE BOB CHARLIE DAVE EVE

// [예제 3] 모든 숫자의 합 계산
// 명령형: 누적 변수를 직접 선언하고 관리한다.
int sum = 0;
for (int n : numbers) {
  sum += n;
}
// sum = 55

// 선언형: mapToInt()로 IntStream으로 변환 후 sum()을 바로 호출한다.
int streamSum = numbers.stream()
    .mapToInt(Integer::intValue) // IntStream으로 변환
    .sum();                       // 55

// [예제 4] 짝수만 골라 제곱한 뒤 합계 계산 (필터링 + 변환 + 집계 조합)
// 명령형: 세 단계가 루프 하나 안에 뒤섞인다.
int imperativeResult = 0;
for (int n : numbers) {
  if (n % 2 == 0) {           // 필터
    int squared = n * n;      // 변환
    imperativeResult += squared; // 집계
  }
}
// imperativeResult = 220  (4+16+36+64+100)

// 선언형: 각 단계가 독립된 연산으로 분리된다.
int streamResult = numbers.stream()
    .filter(n -> n % 2 == 0)  // 단계 1: 짝수 필터
    .mapToInt(n -> n * n)     // 단계 2: 제곱 변환 (IntStream으로 전환)
    .sum();                    // 단계 3: 합계 = 220

// [예제 5] reduce()로 곱셈 누적 (1×2×3×4×5)
List<Integer> small = Arrays.asList(1, 2, 3, 4, 5);

// 명령형
int product = 1;
for (int n : small) {
  product *= n;
}
// product = 120

// 선언형: reduce(초기값, 누적 함수)
int streamProduct = small.stream()
    .reduce(1, (acc, n) -> acc * n); // 120
```

- `map()`은 `T → R` 변환을 담당하고, `forEach()`는 소비를 담당한다. 역할이 분리되어 코드 의도가 명확하다.
- `mapToInt()`를 사용하면 `Integer`를 언박싱해 `IntStream`으로 전환한다. `sum()`, `average()`, `max()`, `min()`을 추가 루프 없이 바로 호출할 수 있다.
- `reduce(identity, BinaryOperator)`는 `sum()`으로 표현할 수 없는 곱셈·최댓값 같은 커스텀 누적 로직을 정의할 때 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam01.App2
  ```

---

### App3 - 수집(collect)과 정렬(sorted)

명령형의 `new ArrayList<>() + add()` 패턴과 스트림의 `collect()`, `sorted()`를 비교한다.

```java
List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2, 7, 4, 6, 10);
List<String> names = Arrays.asList("charlie", "alice", "eve", "bob", "dave");

// [예제 1] 짝수만 새 리스트로 수집
// 명령형: 결과 컨테이너를 직접 생성하고 관리한다.
List<Integer> evenImperative = new ArrayList<>();
for (int n : numbers) {
  if (n % 2 == 0) {
    evenImperative.add(n);
  }
}
// [2, 8, 4, 6, 10]

// 선언형: collect()가 컨테이너 생성과 추가를 대신한다.
List<Integer> evenStream = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList()); // [2, 8, 4, 6, 10]

// [예제 2] 숫자를 오름차순 정렬하여 새 리스트로 수집
// 명령형: 원본을 복사한 뒤 Collections.sort()로 정렬한다 (원본이 수정된다).
List<Integer> sortedImperative = new ArrayList<>(numbers); // 원본 보호를 위한 복사
Collections.sort(sortedImperative);
// [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

// 선언형: sorted()는 원본을 변경하지 않는다.
List<Integer> sortedStream = numbers.stream()
    .sorted()
    .collect(Collectors.toList()); // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
// numbers 원본은 여전히 [5, 3, 8, 1, 9, 2, 7, 4, 6, 10]

// [예제 3] 이름을 알파벳 순으로 정렬
names.stream()
    .sorted()
    .collect(Collectors.toList()); // [alice, bob, charlie, dave, eve]

// [예제 4] 5 이상인 수를 내림차순 정렬 후 제곱하여 수집 (다단계 파이프라인)
// 명령형: 각 단계마다 임시 컬렉션이 필요하다.
List<Integer> step1 = new ArrayList<>();
for (int n : numbers) {
  if (n >= 5) step1.add(n);                       // 1단계: 필터
}
Collections.sort(step1, Collections.reverseOrder()); // 2단계: 내림차순 정렬
List<Integer> imperativeResult = new ArrayList<>();
for (int n : step1) {
  imperativeResult.add(n * n);                     // 3단계: 변환
}
// [100, 81, 64, 49, 36, 25]

// 선언형: 임시 컬렉션 없이 파이프라인 한 흐름으로 처리된다.
List<Integer> streamResult = numbers.stream()
    .filter(n -> n >= 5)                     // 1단계: 필터
    .sorted(Collections.reverseOrder())      // 2단계: 내림차순 정렬
    .map(n -> n * n)                         // 3단계: 제곱 변환
    .collect(Collectors.toList());           // [100, 81, 64, 49, 36, 25]
```

- `collect(Collectors.toList())`는 스트림의 결과 요소를 새 `ArrayList`로 모아 반환한다. 컨테이너 생성과 `add()` 호출을 직접 작성할 필요가 없다.
- `sorted()`는 원본 컬렉션을 수정하지 않고 정렬된 새 스트림을 만든다. 명령형의 `Collections.sort()`는 리스트를 직접 수정하므로 원본 보호를 위해 복사가 필요하다.
- 다단계 파이프라인에서 중간 임시 컬렉션이 필요 없어 코드가 간결해진다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam01.App3
  ```

---

### App4 - 검색(find / match)과 통계(count / max / min)

명령형의 플래그 변수·`break` 패턴과 스트림의 `anyMatch()`, `findFirst()`, `count()`, `max()`, `min()`을 비교한다.

```java
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
List<String> names = Arrays.asList("alice", "bob", "charlie", "dave", "eve");

// [예제 0] noneMatch - 조건을 만족하는 요소가 하나도 없는지 확인
// 명령형
boolean noneFound = true;
for (String name : names) {
  if (name.length() >= 10) {
    noneFound = false;
    break;
  }
}
// noneFound = true

// 선언형
boolean noneFoundStream = names.stream()
    .noneMatch(name -> name.length() >= 10); // true

// [예제 1] anyMatch - 조건을 만족하는 요소가 하나라도 있는지 확인
// 명령형: 플래그 변수를 직접 관리하고 break로 조기 탈출한다.
boolean found = false;
for (int n : numbers) {
  if (n > 10) {
    found = true;
    break;
  }
}
// found = false

// 선언형: anyMatch()가 단락 평가(short-circuit)를 내부적으로 처리한다.
boolean foundStream = numbers.stream()
    .anyMatch(n -> n > 10); // false

// [예제 2] allMatch - 모든 요소가 조건을 만족하는지 확인
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0); // true

// [예제 3] findFirst - 조건을 만족하는 첫 번째 요소 찾기
// 명령형: null 반환 가능 → 호출자가 null 체크 필요
Integer firstFound = null;
for (int n : numbers) {
  if (n > 5) {
    firstFound = n;
    break;
  }
}
// firstFound = 7 (null 체크 필요)

// 선언형: Optional로 반환해 null을 직접 다루지 않아도 된다.
Optional<Integer> firstFoundStream = numbers.stream()
    .filter(n -> n > 5)
    .findFirst();
firstFoundStream.orElse(-1); // 7  (값이 없으면 -1)

// [예제 4] count - 조건을 만족하는 요소의 개수 세기
// 명령형
int count = 0;
for (int n : numbers) {
  if (n > 5) count++;
}
// count = 5

// 선언형
long countStream = numbers.stream()
    .filter(n -> n > 5)
    .count(); // 5 (long 타입)

// [예제 5] max / min - 최댓값·최솟값 찾기
// 명령형: 최대·최솟값을 한 루프에서 동시에 구한다.
int max = numbers.get(0), min = numbers.get(0);
for (int n : numbers) {
  if (n > max) max = n;
  if (n < min) min = n;
}
// max = 10, min = 1

// 선언형: max()/min()은 각각 별도 스트림으로 구한다.
int maxStream = numbers.stream()
    .max(Integer::compareTo) // Optional<Integer>
    .orElseThrow();           // 10

int minStream = numbers.stream()
    .min(Integer::compareTo)
    .orElseThrow();           // 1
```

- `anyMatch()`, `allMatch()`, `noneMatch()`는 조건 충족 즉시 남은 요소를 처리하지 않는 **단락 평가(short-circuit)**를 자동으로 수행한다. 명령형의 `break`를 직접 작성할 필요가 없다.
- `findFirst()`는 `Optional<T>`를 반환한다. `null` 반환 없이 `orElse()`, `orElseThrow()` 등으로 안전하게 결과를 다룰 수 있다.
- `count()`는 `long` 타입을 반환한다. `int`로 사용하려면 명시적으로 캐스팅해야 한다.
- `max()` / `min()`은 `Comparator`를 인자로 받는다. 숫자의 경우 `Integer::compareTo` 또는 `Comparator.naturalOrder()`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam01.App4
  ```

---

## Exam02 - Optional

### 개념

#### Optional\<T\>란?

값이 있을 수도, 없을 수도 있는 **컨테이너 객체**다.
메서드가 `null`을 직접 반환하는 대신 `Optional`을 반환하면, 호출자가 `null` 체크를 강제받지 않고도 빈 값을 안전하게 처리할 수 있다.

```java
// null 반환 방식: 호출자가 null 체크를 해야 한다.
String name = findName(); // null일 수 있음
if (name != null) {
    System.out.println(name.toUpperCase());
}

// Optional 방식: null 체크 없이 체인으로 처리한다.
findNameOptional()
    .map(String::toUpperCase)
    .ifPresent(System.out::println);
```

#### Optional 생성

| 메서드 | 설명 |
|---|---|
| `Optional.of(value)` | `null`이 아닌 값으로 생성한다. `null`을 전달하면 `NullPointerException` 발생 |
| `Optional.ofNullable(value)` | `null`일 수도 있는 값으로 생성한다. `null`이면 빈 `Optional` 반환 |
| `Optional.empty()` | 빈 `Optional`을 생성한다. 값이 없음을 명시적으로 표현할 때 사용 |

#### 값 꺼내기

| 메서드 | 설명 |
|---|---|
| `get()` | 값을 꺼낸다. 값이 없으면 `NoSuchElementException` 발생. **단독 사용은 안티패턴** |
| `orElse(T)` | 값이 있으면 그 값, 없으면 지정한 기본값을 반환한다. **항상 인자를 평가한다** |
| `orElseGet(Supplier<T>)` | 값이 없을 때만 `Supplier`를 호출해 기본값을 생성한다. **지연 평가** |
| `orElseThrow()` | 값이 없으면 `NoSuchElementException`을 발생시킨다 |
| `orElseThrow(Supplier<E>)` | 값이 없으면 지정한 예외를 발생시킨다 |

#### 변환과 필터링

| 메서드 | 설명 |
|---|---|
| `map(Function<T, U>)` | 값에 함수를 적용해 `Optional<U>`로 변환한다. 빈 값이면 아무것도 하지 않는다 |
| `flatMap(Function<T, Optional<U>>)` | 함수가 `Optional<U>`를 반환할 때 사용한다. 중첩 `Optional` 방지 |
| `filter(Predicate<T>)` | 조건을 만족하면 그대로, 아니면 빈 `Optional`을 반환한다 |

#### 소비와 확인

| 메서드 | 설명 |
|---|---|
| `isPresent()` | 값이 있으면 `true` |
| `isEmpty()` | 값이 없으면 `true` (Java 11+) |
| `ifPresent(Consumer<T>)` | 값이 있을 때만 `Consumer`를 실행한다 |
| `ifPresentOrElse(Consumer, Runnable)` | 값이 있으면 `Consumer`, 없으면 `Runnable`을 실행한다 (Java 9+) |

#### Optional과 Stream 연계 (Java 9+)

| 변환 방향 | 방법 | 설명 |
|---|---|---|
| `Optional` → `Stream` | `optional.stream()` | 값이 있으면 1개짜리 `Stream`, 없으면 빈 `Stream` |
| `Stream` → `Optional` | `stream.findFirst()` / `max()` / `min()` / `reduce(op)` | 결과가 없을 수 있으면 `Optional`로 반환 |

#### map vs flatMap

```
map:     Optional<T>  --Function<T, U>----------->  Optional<U>
flatMap: Optional<T>  --Function<T, Optional<U>>-->  Optional<U>  (중첩 Optional 평탄화)
```

- `getEmail()` 반환 타입이 `String`이면 `map` 사용
- `getEmail()` 반환 타입이 `Optional<String>`이면 `flatMap` 사용 → `Optional<Optional<String>>`이 되지 않도록 평탄화

#### orElse vs orElseGet

| 항목 | `orElse(T)` | `orElseGet(Supplier<T>)` |
|---|---|---|
| 인자 평가 시점 | **항상** 평가 (값이 있어도 실행됨) | 값이 **없을 때만** 실행 |
| 적합한 용도 | 리터럴·상수처럼 생성 비용이 없는 기본값 | DB 조회·객체 생성처럼 비용이 큰 기본값 |

#### 안티패턴

| 안티패턴 | 이유 | 대안 |
|---|---|---|
| `isPresent()` + `get()` | `null` 체크와 다를 바 없다 | `map()` / `ifPresent()` / `orElse()` 사용 |
| 필드 타입으로 `Optional` 사용 | 직렬화 불가, 성능 저하 | 메서드 반환 타입으로만 사용 |
| 메서드 파라미터로 `Optional` 사용 | 호출자 코드가 복잡해짐 | 오버로딩 또는 기본값으로 대체 |
| `Optional<Optional<T>>` 중첩 | 처리 불편 | `flatMap()` 으로 평탄화 |

---

### App - Optional 생성과 값 꺼내기

`Optional.of()`, `ofNullable()`, `empty()`로 `Optional`을 생성하고,
`orElse()`, `orElseGet()`, `orElseThrow()`로 값을 꺼내는 방법을 비교한다.

```java
// [예제 1] Optional 생성
Optional<String> opt1 = Optional.of("hello");         // null 불가
Optional<String> opt2 = Optional.ofNullable(null);    // null → 빈 Optional
Optional<String> opt3 = Optional.ofNullable("world"); // null → 값 있는 Optional
Optional<String> opt4 = Optional.empty();             // 명시적 빈 Optional

opt1.isPresent(); // true
opt2.isPresent(); // false
opt4.isEmpty();   // true (Java 11+)

// [예제 2] orElse - 기본값 반환
Optional.of("Alice").orElse("기본값");  // "Alice"
Optional.<String>empty().orElse("기본값"); // "기본값"

// [예제 3] orElseGet vs orElse - Supplier 호출 시점 차이
Optional<String> empty = Optional.empty();

// orElse: 값의 존재 여부와 관계없이 인자 표현식을 항상 평가한다.
empty.orElse(createDefault());       // createDefault() 호출됨

// orElseGet: 값이 없을 때만 Supplier를 호출한다.
empty.orElseGet(() -> createDefault()); // 값 없으므로 호출됨

// 값이 있을 때의 차이
Optional<String> present = Optional.of("존재하는 값");
present.orElse(createDefault());           // createDefault() 여전히 호출됨!
present.orElseGet(() -> createDefault()); // 호출되지 않음 ← 비용이 클 때 유리

// [예제 4] orElseThrow - 값이 없으면 예외 발생
Optional.of("데이터").orElseThrow(() -> new IllegalStateException("없음")); // "데이터"

Optional.<String>empty().orElseThrow(() -> new IllegalStateException("없음")); // 예외 발생
```

- `Optional.of()`는 `null`을 전달하면 즉시 `NullPointerException`이 발생한다. `null`이 올 수 있으면 `ofNullable()`을 사용한다.
- `orElse()`는 값이 있어도 인자 표현식을 항상 평가한다. DB 조회나 객체 생성처럼 비용이 큰 기본값에는 `orElseGet()`을 사용한다.
- `orElseThrow()`는 값이 반드시 있어야 하는 상황에서 `null` 반환 대신 예외로 의도를 명확히 표현한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App
  ```

---

### App2 - map, flatMap, filter, ifPresent

`Optional` 안의 값을 변환·필터링하고 조건부로 실행하는 방법을 다룬다.

```java
// [예제 1] map - Optional 안의 값 변환
// null 체크 방식
String upper = (raw != null) ? raw.toUpperCase() : null;

// Optional.map: null 체크 없이 변환 로직만 작성한다.
Optional.of("alice").map(String::toUpperCase); // Optional["ALICE"]

// 빈 Optional에 map: 함수를 실행하지 않고 빈 Optional을 그대로 반환한다.
Optional.<String>empty().map(String::toUpperCase); // Optional.empty()

// [예제 2] map 체이닝 - 여러 변환을 이어 붙이기
// null 체크 방식: 각 단계마다 null을 확인해야 한다.
if (rawCity != null) {
    String trimmed = rawCity.trim();
    if (!trimmed.isEmpty()) {
        result = trimmed.toUpperCase();
    }
}

// Optional 체이닝: 각 변환이 독립된 단계로 표현된다.
Optional.of("  Seoul  ")
    .map(String::trim)         // "Seoul"
    .filter(s -> !s.isEmpty()) // 비어 있지 않으면 통과
    .map(String::toUpperCase); // "SEOUL"

// [예제 3] flatMap - Optional을 반환하는 메서드와 연결
// getEmail()의 반환 타입: Optional<String>

// map을 쓰면 Optional<Optional<String>>이 된다.
user.map(u -> u.getEmail());     // Optional<Optional<String>>

// flatMap은 자동으로 평탄화해 Optional<String>을 반환한다.
user.flatMap(u -> u.getEmail()); // Optional<String>

// 이메일이 없는 경우
Optional.of(new User("Charlie", null))
    .flatMap(u -> u.getEmail())
    .orElse("이메일 없음"); // "이메일 없음"

// [예제 4] filter - 조건 불만족 시 빈 Optional
Optional.of(85).filter(s -> s >= 60).map(s -> "합격").orElse("불합격"); // "합격"
Optional.of(40).filter(s -> s >= 60).map(s -> "합격").orElse("불합격"); // "불합격"

// [예제 5] ifPresent / ifPresentOrElse
// null 체크 방식
if (raw != null) { System.out.println(raw); }

// ifPresent: 값이 있을 때만 실행. null 체크 if를 대체한다.
Optional.of("안녕").ifPresent(System.out::println); // 실행됨
Optional.<String>empty().ifPresent(System.out::println); // 실행 안 됨

// ifPresentOrElse: 있을 때와 없을 때 각각 동작을 지정한다. (Java 9+)
Optional.of("안녕").ifPresentOrElse(
    m -> System.out.println("값 있음: " + m),
    ()  -> System.out.println("값 없음")
); // "값 있음: 안녕"
```

- `map()`의 함수가 `null`을 반환하면 결과는 `Optional.empty()`가 된다.
- `flatMap()`은 함수의 반환 타입이 `Optional<U>`일 때 사용한다. `map()`을 쓰면 `Optional<Optional<U>>`가 된다.
- `filter()`는 조건을 만족하지 않으면 빈 `Optional`을 반환한다. `if (x != null && condition)` 패턴을 대체한다.
- `ifPresent()`는 `if (x != null)` 패턴을 대체한다. 실행 결과를 반환하지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App2
  ```

---

### App3 - null 방어 코드 제거 패턴

`Optional`을 사용해 실제 코드에서 자주 나타나는 null 처리 패턴을 개선하는 방법을 다룬다.

```java
// [예제 1] 중첩 null 체크 → Optional 체인으로 교체
// null 체크 방식: 단계마다 null을 확인해야 한다. (null 체크 피라미드)
if (order != null) {
    Customer customer = order.getCustomer();
    if (customer != null) {
        Address address = customer.getAddress();
        if (address != null) {
            System.out.println(address.getCity());
            return;
        }
    }
}
System.out.println("알 수 없음");

// Optional 방식: 체인으로 평탄하게 표현된다. 각 단계에서 null이면 빈 Optional이 전파된다.
Optional.ofNullable(order)
    .map(Order::getCustomer)   // null이면 빈 Optional
    .map(Customer::getAddress) // null이면 빈 Optional
    .map(Address::getCity)     // null이면 빈 Optional
    .orElse("알 수 없음");

// [예제 2] 컬렉션에서 조건에 맞는 첫 번째 요소 찾기
// null 반환 방식: 호출자가 null을 확인해야 한다.
static String findFirstStartingWith(List<String> list, String prefix) {
    for (String item : list) {
        if (item.startsWith(prefix)) return item;
    }
    return null; // null 반환 → 호출자 부담
}

// Optional 반환 방식: 없으면 Optional.empty()를 반환해 호출자가 자연스럽게 처리한다.
static Optional<String> findFirstStartingWith(List<String> list, String prefix) {
    return list.stream()
        .filter(item -> item.startsWith(prefix))
        .findFirst();
}

// 호출부 비교
String r1 = findFirstStartingWithNull(items, "블");
if (r1 != null) { System.out.println(r1); } // null 체크 필요

findFirstStartingWith(items, "블").orElse("없음"); // null 체크 불필요

// [예제 3] 설정값 우선순위 폴백 체인
// null 체크 방식
String charset = userSetting != null ? userSetting
    : (envVariable != null ? envVariable : systemDefault);

// Optional 폴백 체인 (Java 9+): or()로 다음 Optional을 지정한다.
String charset = Optional.ofNullable(userSetting)    // 사용자 설정
    .or(() -> Optional.ofNullable(envVariable))       // 없으면 환경 변수
    .orElse(systemDefault);                           // 없으면 시스템 기본값

// [예제 4] 안티패턴 vs 올바른 패턴
// 안티패턴: isPresent() + get() 조합은 null 체크와 다를 바 없다.
if (maybe.isPresent()) {
    System.out.println(maybe.get()); // null 체크 if와 동일
}

// 올바른 패턴: orElse / map / ifPresent 중 하나를 선택한다.
maybe.ifPresent(v -> System.out.println(v));
maybe.orElse("없음");
maybe.map(String::toUpperCase).orElse("없음");
```

- `Optional.ofNullable().map().map()...` 체인을 사용하면 단계별 `null` 체크 피라미드를 선형 코드로 교체할 수 있다.
- 메서드 반환 타입을 `null` 대신 `Optional`로 바꾸면 호출자가 빈 값 처리를 잊지 않도록 API가 강제한다.
- `Optional.or()` (Java 9+)는 폴백 체인을 간결하게 표현한다.
- `isPresent()` + `get()` 조합은 안티패턴이다. `map()`, `ifPresent()`, `orElse()` 중 하나로 대체한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App3
  ```

---

### App4 - Optional과 Stream의 통합

`Optional`을 `Stream`과 연계하고, `Map` 조회·`reduce`·도메인 객체 리스트 처리에 응용한다.

```java
// [예제 1] Optional 리스트에서 값만 추출
List<Optional<String>> optionals = Arrays.asList(
    Optional.of("사과"), Optional.empty(), Optional.of("바나나"),
    Optional.empty(), Optional.of("포도")
);

// 기존 방식: isPresent()로 필터링 후 get()으로 꺼낸다.
optionals.stream()
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList()); // [사과, 바나나, 포도]

// Optional.stream() 방식 (Java 9+): flatMap과 함께 사용해 빈 값을 자동 제거한다.
optionals.stream()
    .flatMap(Optional::stream)    // 값 있으면 1개짜리 Stream, 없으면 빈 Stream
    .collect(Collectors.toList()); // [사과, 바나나, 포도]

// [예제 2] reduce - 항등원 없는 버전은 Optional 반환
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4);

// reduce(BinaryOperator): 초기값 없이 누적한다.
// 빈 스트림에서 결과가 없을 수 있으므로 Optional<T>를 반환한다.
Optional<Integer> max = numbers.stream()
    .reduce((a, b) -> a >= b ? a : b); // Optional[9]

Optional<Integer> emptyMax = Arrays.<Integer>asList().stream()
    .reduce((a, b) -> a >= b ? a : b); // Optional.empty()

emptyMax.orElse(-1); // -1 (기본값)

// [예제 3] Map 조회에 Optional 적용
Map<String, String> capitals = Map.of("한국", "서울", "일본", "도쿄");

// null 반환 방식: null 체크 필요
String c = capitals.get("독일"); // null
if (c != null) { System.out.println(c); }

// Optional 방식
Optional.ofNullable(capitals.get("독일")).orElse("알 수 없음"); // "알 수 없음"

// Map.getOrDefault()와 비교:
capitals.getOrDefault("독일", "알 수 없음"); // 단순 기본값에 편리
// Optional은 추가 변환(map/filter)이 필요할 때 더 유연하다.

// [예제 4] Optional 필드를 가진 객체 리스트 처리
List<Product> products = Arrays.asList(
    new Product("노트북", Optional.of(1_200_000)),
    new Product("마우스", Optional.of(35_000)),
    new Product("모니터", Optional.empty()),   // 가격 미정
    new Product("키보드", Optional.of(85_000)),
    new Product("웹캠",   Optional.empty())    // 가격 미정
);

// 가격이 있는 상품만 합산
int total = products.stream()
    .flatMap(p -> p.getPrice().stream())  // 가격 없으면 빈 Stream, 있으면 1개짜리 Stream
    .mapToInt(Integer::intValue)
    .sum(); // 1,320,000

// 가격이 없는 상품 이름 목록
List<String> noPriceNames = products.stream()
    .filter(p -> p.getPrice().isEmpty())
    .map(Product::getName)
    .collect(Collectors.toList()); // [모니터, 웹캠]

// 가장 비싼 상품 이름 (가격이 있는 것 중)
Optional<String> mostExpensive = products.stream()
    .filter(p -> p.getPrice().isPresent())
    .max((a, b) -> a.getPrice().get() - b.getPrice().get())
    .map(Product::getName); // Optional["노트북"]
```

- `Optional.stream()` (Java 9+)은 `Optional` 리스트에서 값만 추출할 때 `flatMap`과 조합해 빈 값을 자동으로 제거한다.
- `reduce(BinaryOperator)` (항등원 없는 버전)는 빈 스트림을 안전하게 처리하기 위해 `Optional`을 반환한다.
- `Map.get()`은 `Optional.ofNullable()`로 감싸면 `null` 체크 없이 체인으로 처리할 수 있다.
- 단순 기본값이면 `Map.getOrDefault()`가 간결하고, 추가 변환이 필요하면 `Optional` 체인이 더 유연하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App4
  ```

---

## Exam03 - 스트림 소스 생성

### 개념

스트림(Stream)은 데이터를 처리하는 파이프라인이다. 파이프라인을 시작하려면 **소스(Source)**에서 스트림을 만들어야 한다.
Java는 컬렉션, 배열, 숫자 범위, 파일, 문자열 등 다양한 소스에서 스트림을 생성하는 API를 제공한다.

#### 스트림 소스 분류

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

#### IntStream / LongStream / DoubleStream (기본형 특화 스트림)

| 항목 | `Stream<Integer>` | `IntStream` |
|---|---|---|
| 저장 방식 | Integer 객체 (박싱) | int 기본형 |
| 박싱 오버헤드 | 있음 | **없음** |
| 추가 집계 메서드 | 없음 | `sum()`, `average()`, `min()`, `max()` |
| List로 수집 | `collect(Collectors.toList())` | `boxed().toList()` |

#### 무한 스트림: iterate vs generate

| 항목 | `Stream.iterate(seed, f)` | `Stream.generate(supplier)` |
|---|---|---|
| 다음 값 계산 | 이전 값을 기반으로 함수 적용 | 이전 값과 무관하게 독립 생성 |
| 적합한 용도 | 수열, 피보나치 등 상태 있는 시퀀스 | 난수, 상수, 고유 ID 등 |
| 종료 방법 | `limit()` 또는 `takeWhile()` | `limit()` 또는 `takeWhile()` |

#### takeWhile vs filter (Java 9+)

```
정렬된 데이터: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

filter(n < 5):    모든 요소를 검사 → [1, 2, 3, 4]
takeWhile(n < 5): 5에서 즉시 종료 → [1, 2, 3, 4]  (이후 요소 검사 안 함)
```

비정렬 데이터에서 `takeWhile`은 처음으로 조건이 false가 되는 순간 이후 요소를 버린다.

#### 파일 스트림 주의사항

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

### App - 컬렉션, 배열, 값에서 스트림 생성

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

### App2 - 숫자 범위 스트림

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

### App3 - 무한 스트림 (iterate / generate)

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

### App4 - 파일과 문자열에서 스트림 생성

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

---

## Exam04 - 필터링과 슬라이싱

### 개념

#### 필터링 (Filtering)

스트림에서 **조건을 만족하는 요소만 남기거나 중복을 제거**하는 연산이다.

| 연산 | 설명 |
|---|---|
| `filter(Predicate<T>)` | 조건을 만족하는 요소만 통과시킨다 |
| `distinct()` | `equals()`/`hashCode()` 기준으로 중복 요소를 제거한다. 처음 등장 순서 유지 |

#### 슬라이싱 (Slicing)

스트림에서 **원하는 구간만 잘라내는** 연산이다.

| 연산 | 설명 |
|---|---|
| `limit(long n)` | 앞에서 최대 n개만 취한다. 단락 평가(short-circuit) 지원 |
| `skip(long n)` | 앞에서 n개를 버린다. n이 크기보다 크면 빈 스트림 반환 |
| `takeWhile(Predicate<T>)` | 조건을 만족하는 동안 취하다가 처음 false에서 즉시 종료 (Java 9+) |
| `dropWhile(Predicate<T>)` | 조건을 만족하는 동안 버리다가 처음 false에서부터 모두 취함 (Java 9+) |

#### Predicate 조합

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

#### takeWhile vs filter

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

#### limit + skip 페이지네이션 패턴

```java
// 페이지 번호(0부터), 페이지 크기
stream
    .skip((long) page * size)  // 이전 페이지 데이터 건너뜀
    .limit(size)               // 현재 페이지 데이터 취함
```

#### distinct() 주의사항

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

### App - filter()와 Predicate 조합

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

### App2 - distinct() 중복 제거

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

### App3 - limit()과 skip() 슬라이싱

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

### App4 - takeWhile() / dropWhile() + 복합 파이프라인

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

---

## Exam05 - 매핑

### 개념

#### map() — 1:1 변환

스트림의 각 요소를 **다른 타입이나 값으로 변환**하는 중간 연산이다.
요소 수는 변하지 않는다. 하나의 요소가 변환된 하나의 요소로 대응된다.

```
Stream<T>  --map(T→R)-->  Stream<R>
  [A, B, C]               [f(A), f(B), f(C)]
```

#### flatMap() — 1:N 변환 + 평탄화

각 요소를 `Stream<R>`으로 변환한 뒤, 모든 스트림을 **하나의 스트림으로 합친다**.
요소 하나가 0개 이상의 요소로 변환된다.

```
Stream<T>  --flatMap(T→Stream<R>)-->  Stream<R>
  [[1,2], [3,4], [5]]                 [1, 2, 3, 4, 5]
```

#### map vs flatMap

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

#### 기본형 특화 매핑

| 메서드 | 결과 타입 | 장점 |
|---|---|---|
| `mapToInt(ToIntFunction<T>)` | `IntStream` | 박싱 없음, `sum()`/`average()` 제공 |
| `mapToLong(ToLongFunction<T>)` | `LongStream` | 박싱 없음, 큰 수치 처리 |
| `mapToDouble(ToDoubleFunction<T>)` | `DoubleStream` | 박싱 없음, 실수 계산 |
| `flatMapToInt(f)` | `IntStream` | `flatMap` + `mapToInt` 한 번에 |

#### 기본형 스트림 복귀

| 메서드 | 변환 |
|---|---|
| `intStream.boxed()` | `IntStream` → `Stream<Integer>` |
| `intStream.mapToObj(f)` | `IntStream` → `Stream<R>` |
| `intStream.asLongStream()` | `IntStream` → `LongStream` |
| `intStream.asDoubleStream()` | `IntStream` → `DoubleStream` |

#### summaryStatistics()

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

### App - map() 기초

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

### App2 - flatMap() 중첩 스트림 평탄화

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

### App3 - 기본형 매핑 스트림

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

### App4 - 복합 매핑 실전

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

---

## Exam06 - 정렬

### 개념

#### sorted() — 자연 순서 정렬

`sorted()`는 스트림 요소를 **자연 순서(natural order)** 로 정렬하는 중간 연산이다.
요소 타입이 `Comparable`을 구현해야 한다. `Integer`, `String` 등 JDK 기본 타입은 이미 구현되어 있다.

```java
numbers.stream().sorted(); // 오름차순 (자연 순서)
names.stream().sorted();   // 알파벳 순
```

#### sorted(Comparator) — 지정 순서 정렬

`Comparator`를 인자로 받아 **원하는 순서**로 정렬한다.
커스텀 객체의 특정 필드로 정렬하거나, 역순 정렬할 때 사용한다.

```java
stream.sorted(Comparator.reverseOrder()); // 역순 (내림차순)
```

#### Comparator 생성 메서드

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

#### 정렬의 특성

- **중간 연산**: 최종 연산이 호출될 때 정렬이 수행된다.
- **상태 있는 연산(stateful)**: 전체 요소를 버퍼에 모은 뒤 정렬한다. `filter`, `map`처럼 요소 하나씩 처리하지 않는다.
- **무한 스트림 불가**: `Stream.iterate()` 등 무한 스트림에 `sorted()`를 적용하면 종료되지 않는다.
- **안정 정렬(stable sort)**: 같은 키를 가진 요소의 상대적 순서가 보존된다.

#### 자주 쓰는 정렬 패턴

```
정렬 후 상위 N개:      sorted → limit
정렬 후 페이징:        sorted → skip → limit
정렬 후 수집:          sorted → toList
정렬 후 최솟값/최댓값: min(Comparator) / max(Comparator)
```

---

### App - sorted() 기초

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

### App2 - Comparator.comparing() / thenComparing()

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

### App3 - 실전 패턴 (Product 도메인)

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

---

## Exam07 - 엿보기 (peek)

### 개념

#### peek(Consumer) — 중간 엿보기

`peek(Consumer<T>)`는 스트림의 각 요소를 **소비하지 않고 들여다보는** 중간 연산이다.
요소를 변경하거나 제거하지 않으며, 원래 요소를 그대로 다음 연산으로 전달한다.

```
filter → peek → map → peek → collect
           ↑           ↑
      중간 확인     중간 확인  (요소는 변하지 않음)
```

#### peek()의 특성

| 특성 | 설명 |
|---|---|
| 중간 연산 | 스트림을 종료하지 않는다. 이후 연산이 계속 이어진다 |
| 지연 실행 | 최종 연산이 없으면 `Consumer`가 실행되지 않는다 |
| 부작용 전용 | 요소를 변환하거나 필터링하는 데 사용하지 않는다 |
| 원소 유지 | 요소를 변경하지 않고 그대로 다음 연산으로 전달한다 |

#### peek() vs forEach()

| 항목 | `peek(Consumer)` | `forEach(Consumer)` |
|---|---|---|
| 연산 종류 | **중간 연산** | **최종 연산** |
| 스트림 상태 | 이후 연산 가능 | 스트림 종료 |
| 반환 타입 | `Stream<T>` | `void` |
| 주 용도 | 디버깅, 로깅 | 최종 소비, 출력 |

#### 스트림 파이프라인의 실행 순서

스트림은 요소를 **하나씩** 파이프라인 전체를 통과시킨다. 단계별로 전체 요소를 처리하는 것이 아니다.

```
잘못된 이해 (수평 처리):
  filter: [1 2 3 4 5] → [2 4]
  map:    [2 4] → [4 16]

올바른 이해 (수직 처리, depth-first):
  1 → filter(제거)
  2 → filter(통과) → map(4) → forEach
  3 → filter(제거)
  4 → filter(통과) → map(16) → forEach
  5 → filter(제거)
```

`peek()`으로 이 실행 순서를 직접 확인할 수 있다.

#### 주요 활용

```
파이프라인 디버깅:  각 단계 통과 요소 출력
중간 카운팅:        int[] count = {0}; peek(n -> count[0]++)
외부 컬렉션 수집:   List<T> list = new ArrayList<>(); peek(list::add)
로깅:              peek(n -> log.debug("처리: {}", n))
```

---

### App - peek() 기초

`peek()` 기본 동작, 지연 실행, `peek()` vs `forEach()` 비교를 다룬다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// [예제 1] peek() - 중간 단계 확인
numbers.stream()
    .filter(n -> n % 2 == 0)
    .peek(n -> System.out.println("filter 후: " + n))  // 2 4 6 8 10
    .map(n -> n * n)
    .peek(n -> System.out.println("map 후: " + n))     // 4 16 36 64 100
    .toList();

// [예제 2] 지연 실행 - 최종 연산 없으면 Consumer 실행 안 됨
numbers.stream()
    .filter(n -> n > 5)
    .peek(n -> System.out.println("이 줄은 출력되지 않음: " + n));
// 최종 연산이 없으므로 아무것도 출력되지 않는다.

// 최종 연산 추가 → peek() 실행됨
long count = numbers.stream()
    .filter(n -> n > 5)
    .peek(n -> System.out.println("처리됨: " + n)) // 6 7 8 9 10
    .count(); // 5

// [예제 3] peek() vs forEach()
// forEach: 최종 연산 → 스트림 종료
numbers.stream().filter(n -> n <= 3).forEach(System.out::println);

// peek: 중간 연산 → 이후 map, toList 가능
numbers.stream()
    .filter(n -> n <= 3)
    .peek(n -> System.out.print("peek:" + n + " ")) // 중간 확인
    .map(n -> n * 10)
    .toList(); // [10, 20, 30]
```

- `peek()`은 디버깅 목적으로만 사용한다. 프로덕션 코드에서 핵심 로직을 `peek()`에 넣지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam07.App
  ```

---

### App2 - 파이프라인 실행 순서 이해

수직 처리(depth-first), limit()에서의 단락 평가, 중간 카운팅, 외부 수집을 다룬다.

```java
// [예제 1] 파이프라인 실행 순서 추적 - 수직 처리
Arrays.asList(1, 2, 3, 4, 5).stream()
    .peek(n  -> System.out.println("[소스]   " + n))
    .filter(n -> { ... return n % 2 == 0; })
    .peek(n  -> System.out.println("[filter 후] " + n))
    .map(n   -> { ... return n * 10; })
    .peek(n  -> System.out.println("[map 후] " + n))
    .forEach(n -> System.out.println("[forEach] " + n));

// 출력 순서:
//   [소스]   1 → [filter] 1 제거
//   [소스]   2 → [filter] 2 통과 → [map] 20 → [forEach] 20
//   [소스]   3 → [filter] 3 제거
//   [소스]   4 → [filter] 4 통과 → [map] 40 → [forEach] 40
//   [소스]   5 → [filter] 5 제거

// [예제 2] limit()에서 단락 평가
Arrays.asList(1, 2, 3, 4, 5).stream()
    .peek(n -> System.out.println("peek: " + n))
    .filter(n -> n % 2 == 0)
    .limit(1)
    .forEach(n -> System.out.println("forEach: " + n));
// peek에서 1, 2까지만 출력 → limit(1) 충족 후 3, 4, 5는 처리되지 않음

// [예제 3] peek()을 이용한 중간 카운팅
int[] filterCount = {0}, mapCount = {0};

numbers.stream()
    .filter(n -> n % 2 == 0)
    .peek(n -> filterCount[0]++)   // filter 통과 횟수
    .map(n -> n * n)
    .peek(n -> mapCount[0]++)      // map 변환 횟수
    .toList();

// filterCount[0] == 5, mapCount[0] == 5

// [예제 4] peek()으로 외부 컬렉션에 수집
List<Integer> beforeMap = new ArrayList<>();
List<Integer> afterMap  = new ArrayList<>();

Arrays.asList(1, 2, 3, 4, 5).stream()
    .filter(n -> n % 2 != 0)    // 홀수: 1 3 5
    .peek(beforeMap::add)       // map 전 저장
    .map(n -> n * n)            // 제곱
    .peek(afterMap::add)        // map 후 저장
    .toList();

// beforeMap: [1, 3, 5]
// afterMap:  [1, 9, 25]
```

- 스트림은 **요소 하나씩 전체 파이프라인을 통과**한다. 단계별로 전체 요소를 처리하는 게 아니다.
- `limit()` 같은 단락 평가 연산은 조건 충족 시 이후 요소를 처리하지 않는다. `peek()`으로 이를 확인할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam07.App2
  ```

---

## Exam08 - 매핑 (1:N, Java 16+)

### 개념

#### mapMulti(BiConsumer) — 1:N 매핑 (Java 16+)

`mapMulti(BiConsumer<T, Consumer<R>>)`는 각 요소를 **0개 이상의 요소로 변환**하는 중간 연산이다.
`flatMap()`과 같은 목적이지만 방식이 다르다.

```
flatMap:    T  →  Stream<R>   → (합쳐줌) →  Stream<R>
mapMulti:   T  →  Consumer<R>.accept() 반복  →  Stream<R>
```

#### flatMap vs mapMulti

| 항목 | `flatMap(Function<T, Stream<R>>)` | `mapMulti(BiConsumer<T, Consumer<R>>)` |
|---|---|---|
| 추가된 버전 | Java 8 | Java 16 |
| 변환 방식 | 요소를 `Stream<R>`으로 변환 | `Consumer<R>`로 직접 밀어 넣음 |
| 중간 Stream 생성 | 매 요소마다 Stream 객체 생성 | Stream 객체 생성 없음 |
| 성능 | 결과가 많을 때 유리 | 결과가 적거나 0개일 때 유리 |
| 가독성 | 단순 변환에 간결 | 반복/재귀 로직에 자연스러움 |

#### mapMulti 기본 형태

```java
stream.<R>mapMulti((element, downstream) -> {
    // downstream.accept(value)를 원하는 만큼 호출한다.
    // 호출 안 함  → 0개 (필터링 효과)
    // 1번 호출    → 1개 (map과 동일)
    // N번 호출    → N개 (flatMap과 동일)
});
```

#### 기본형 특화

| 메서드 | 결과 타입 | Consumer 타입 |
|---|---|---|
| `mapMultiToInt(BiConsumer<T, IntConsumer>)` | `IntStream` | `IntConsumer` |
| `mapMultiToLong(BiConsumer<T, LongConsumer>)` | `LongStream` | `LongConsumer` |
| `mapMultiToDouble(BiConsumer<T, DoubleConsumer>)` | `DoubleStream` | `DoubleConsumer` |

#### mapMulti가 flatMap보다 유리한 경우

```
1. 결과가 0개 또는 1개인 경우가 많을 때 — Stream 객체 생성 오버헤드가 없음
2. 반복(for 루프)으로 요소를 생성할 때 — Consumer 반복 호출이 자연스러움
3. instanceof 패턴 매칭과 결합한 타입 필터링 — 캐스팅 없이 깔끔하게 표현
4. 중첩 구조(트리, 계층) 재귀 평탄화 — 재귀 Consumer 호출 구조가 읽기 쉬움
```

---

### App - mapMulti() 기초

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

### App2 - mapMulti() 실전 패턴

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

---

## Exam09 - 검색과 매칭

### 개념

#### 매칭 메서드

| 메서드 | 반환 타입 | 설명 | 빈 스트림 |
|---|---|---|---|
| `anyMatch(Predicate<T>)` | `boolean` | 하나라도 조건을 만족하면 `true` | `false` |
| `allMatch(Predicate<T>)` | `boolean` | 모든 요소가 조건을 만족하면 `true` | `true` (공허한 참) |
| `noneMatch(Predicate<T>)` | `boolean` | 조건을 만족하는 요소가 없으면 `true` | `true` |

세 메서드 모두 **단락 평가(short-circuit)**를 수행한다. 결과가 확정되는 순간 이후 요소를 처리하지 않는다.

```
anyMatch:  조건 만족 요소 발견 즉시 true 반환 (이후 요소 무시)
allMatch:  조건 불만족 요소 발견 즉시 false 반환
noneMatch: 조건 만족 요소 발견 즉시 false 반환
```

#### 빈 스트림의 동작

```java
List<Integer> empty = List.of();

empty.stream().anyMatch(n -> n > 0);  // false — "하나라도"가 없으므로
empty.stream().allMatch(n -> n > 0);  // true  — 반례(조건 불만족)가 없으므로 (공허한 참)
empty.stream().noneMatch(n -> n > 0); // true  — 조건을 만족하는 요소가 없으므로
```

`allMatch`가 `true`를 반환하는 이유: **공허한 참(vacuous truth)** — "모든 요소가 조건을 만족하지 않는 반례가 없다"는 명제가 참이다.

#### 논리적 관계

```
noneMatch(p) == !anyMatch(p)
allMatch(p)  == !anyMatch(!p)
```

#### 검색 메서드

| 메서드 | 반환 타입 | 설명 |
|---|---|---|
| `findFirst()` | `Optional<T>` | 스트림의 첫 번째 요소 반환. 병렬에서도 순서 보장 |
| `findAny()` | `Optional<T>` | 임의의 요소 반환. 병렬 스트림에서 더 빠를 수 있음 |

두 메서드 모두 **단락 평가**를 수행하며 `Optional<T>`를 반환해 null 없이 안전하게 결과를 처리할 수 있다.

#### findFirst vs findAny

| 항목 | `findFirst()` | `findAny()` |
|---|---|---|
| 순차 스트림 | 항상 첫 번째 요소 반환 | 보통 첫 번째 요소 반환 |
| 병렬 스트림 | 순서 보장 (오버헤드 있음) | 순서 미보장 (더 빠름) |
| 사용 시점 | 순서가 중요할 때 | 성능이 중요할 때 |

#### Optional 처리 패턴

| 메서드 | 설명 |
|---|---|
| `orElse(T)` | 값이 없으면 기본값 반환 |
| `orElseGet(Supplier<T>)` | 값이 없을 때만 Supplier 실행 (지연 평가) |
| `orElseThrow()` | 값이 없으면 예외 발생 |
| `ifPresent(Consumer<T>)` | 값이 있을 때만 실행 |
| `map(Function<T,R>)` | 값이 있으면 변환 |
| `ifPresentOrElse(Consumer, Runnable)` | 있을 때/없을 때 각각 처리 (Java 9+) |

#### anyMatch vs filter + findFirst

```
anyMatch:          boolean이 필요할 때 — 존재 여부만 확인
filter + findFirst: Optional이 필요할 때 — 실제 요소가 필요할 때
```

**나쁜 패턴**: `anyMatch`로 확인 후 `findFirst`로 다시 조회 → 스트림을 두 번 순회

```java
// 나쁜 패턴: 스트림 두 번 순회
boolean exists = list.stream().anyMatch(predicate);
if (exists) {
  list.stream().filter(predicate).findFirst().ifPresent(...);
}

// 좋은 패턴: findFirst 하나로 존재 확인과 결과 조회를 동시에
list.stream().filter(predicate).findFirst().ifPresent(...);
```

#### 활용 전략 요약

| 질문 | 사용 메서드 |
|---|---|
| "~가 있는가?" | `anyMatch` |
| "~를 모두 충족하는가?" | `allMatch` |
| "~가 하나도 없는가?" | `noneMatch` |
| "~를 만족하는 첫 번째는?" | `filter + findFirst` |
| "정렬 후 최솟값/최댓값?" | `sorted + findFirst` 또는 `min` / `max` |
| "결과가 없어도 괜찮다" | `orElse` / `ifPresent` |
| "결과가 반드시 있어야 한다" | `orElseThrow` |

---

### App - anyMatch / allMatch / noneMatch

단락 평가, 빈 스트림 동작, 세 메서드의 논리 관계를 다룬다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

// [예제 1] anyMatch - 하나라도 조건을 만족하는가?
boolean anyGt5  = numbers.stream().anyMatch(n -> n > 5);   // true
boolean anyGt10 = numbers.stream().anyMatch(n -> n > 10);  // false
boolean anyLong = names.stream().anyMatch(name -> name.length() > 5); // true (Charlie)

// [예제 2] allMatch - 모든 요소가 조건을 만족하는가?
boolean allPositive = numbers.stream().allMatch(n -> n > 0);        // true
boolean allEven     = numbers.stream().allMatch(n -> n % 2 == 0);   // false
boolean allShort    = names.stream().allMatch(name -> name.length() <= 8); // true

// [예제 3] noneMatch - 조건을 만족하는 요소가 하나도 없는가?
boolean noneNeg  = numbers.stream().noneMatch(n -> n < 0);   // true
boolean noneGt10 = numbers.stream().noneMatch(n -> n > 10);  // true
boolean noneGt5  = numbers.stream().noneMatch(n -> n > 5);   // false

// [예제 4] 논리 관계
// noneMatch(p) == !anyMatch(p)
// allMatch(p)  == !anyMatch(!p)

// [예제 5] 빈 스트림
List<Integer> empty = List.of();
empty.stream().anyMatch(n -> n > 0);  // false
empty.stream().allMatch(n -> n > 0);  // true  (공허한 참)
empty.stream().noneMatch(n -> n > 0); // true

// [예제 6] 단락 평가 - 처리 횟수 추적
int[] anyCount = {0};
numbers.stream().anyMatch(n -> {
  anyCount[0]++;
  return n > 5;
});
// anyCount[0] == 6 (6번째 요소 '6'에서 종료)
```

- `int[] count = {0}` 패턴: 람다 안에서 외부 카운터를 변경하기 위해 배열을 사용한다. 람다는 effectively final 변수만 캡처할 수 있으므로 `int count`는 불가능하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App
  ```

---

### App2 - findFirst / findAny

`findFirst`, `findAny`, Optional 처리 패턴, 병렬 스트림 비교를 다룬다.

```java
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

// [예제 1] findFirst - 조건을 만족하는 첫 번째 요소
Optional<Integer> firstGt5   = numbers.stream().filter(n -> n > 5).findFirst(); // Optional[7]
Optional<Integer> firstGt100 = numbers.stream().filter(n -> n > 100).findFirst(); // Optional.empty

firstGt5.orElse(-1);   // 7
firstGt100.orElse(-1); // -1 (기본값)

// [예제 2] sorted + findFirst - 최솟값 패턴
Optional<Integer> minEven = numbers.stream()
    .filter(n -> n % 2 == 0) // 2 4 6 8 10
    .sorted()                 // 오름차순
    .findFirst();             // Optional[2]

Optional<String> firstName = names.stream()
    .sorted()      // Alice Bob Charlie Dave Eve
    .findFirst();  // Optional[Alice]

// [예제 3] findAny - 순차 스트림 (findFirst와 결과 유사)
Optional<Integer> anyGt5 = numbers.stream().filter(n -> n > 5).findAny(); // 보통 7

// 병렬 스트림에서는 순서 미보장
Optional<Integer> anyGt5Parallel = numbers.parallelStream()
    .filter(n -> n > 5).findAny(); // 7,9,6,8,10 중 하나 (비결정적)

// [예제 4] findFirst vs findAny - 병렬 스트림
List<Integer> ordered = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

ordered.parallelStream().filter(n -> n % 2 == 0).findFirst(); // 항상 2 (첫 번째 짝수)
ordered.parallelStream().filter(n -> n % 2 == 0).findAny();  // 비결정적 (더 빠름)

// [예제 5] Optional 처리 패턴
Optional<String> found = names.stream().filter(name -> name.startsWith("C")).findFirst();

found.orElse("없음");                          // Charlie
found.orElseGet(() -> "기본 이름");            // Charlie (지연 실행)
found.orElseThrow();                           // Charlie (없으면 예외)
found.ifPresent(name -> System.out.println(name)); // Charlie
found.map(String::length).orElse(0);          // 7
found.ifPresentOrElse(
    name -> System.out.println("있음: " + name),
    ()   -> System.out.println("없음")
);

// [예제 6] 빈 스트림
Optional<Integer> emptyFirst = List.<Integer>of().stream().findFirst(); // Optional.empty
emptyFirst.isPresent(); // false
emptyFirst.isEmpty();   // true (Java 11+)
```

- `findFirst()`는 순서를 보장한다. 병렬 스트림에서도 첫 번째 요소를 반환하지만 오버헤드가 있다.
- `findAny()`는 병렬 스트림에서 순서를 신경 쓰지 않아 더 빠를 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App2
  ```

---

### App3 - 실전 패턴 (User 도메인)

존재 여부 확인, 유효성 검사, 사용자 검색, anyMatch vs findFirst 역할 분리를 다룬다.

```java
List<User> users = Arrays.asList(
    new User(1, "alice",   "alice@example.com",  true,  28),
    new User(2, "bob",     "bob@example.com",    false, 35),
    new User(3, "charlie", "charlie@example.com", true, 22),
    new User(4, "dave",    "dave@example.com",   true,  41),
    new User(5, "eve",     "",                   false, 19) // 이메일 없음
);

// [예제 1] anyMatch - 존재 여부 확인
boolean hasActiveUser  = users.stream().anyMatch(User::isActive);        // true
boolean hasAdult30Plus = users.stream().anyMatch(u -> u.getAge() >= 30); // true
boolean hasNoEmail     = users.stream().anyMatch(u -> u.getEmail().isBlank()); // true

// [예제 2] allMatch - 유효성 전체 검사
boolean allHaveNames  = users.stream()
    .allMatch(u -> u.getName() != null && !u.getName().isBlank()); // true
boolean allHaveEmails = users.stream()
    .allMatch(u -> !u.getEmail().isBlank()); // false (eve 이메일 없음)
boolean allAdults     = users.stream()
    .allMatch(u -> u.getAge() >= 18); // true

// [예제 3] noneMatch - 금지 조건 검사
boolean noMinor       = users.stream().noneMatch(u -> u.getAge() < 18); // true
boolean noNullName    = users.stream().noneMatch(u -> u.getName() == null); // true

// [예제 4] filter + findFirst - 특정 사용자 검색
Optional<User> byId = users.stream().filter(u -> u.getId() == 3).findFirst();
byId.ifPresent(u -> System.out.printf("%s (%d세)", u.getName(), u.getAge())); // charlie (22세)

// 이름으로 검색 (대소문자 무시)
users.stream()
    .filter(u -> "ALICE".equalsIgnoreCase(u.getName()))
    .findFirst()
    .map(User::getEmail)
    .ifPresent(System.out::println);

// 활성 + 30세 이상인 첫 번째 사용자
users.stream()
    .filter(User::isActive)
    .filter(u -> u.getAge() >= 30)
    .findFirst()
    .ifPresent(u -> System.out.printf("%s (%d세)", u.getName(), u.getAge())); // dave (41세)

// [예제 5] anyMatch vs findFirst 역할 분리
// 나쁜 패턴: 스트림 두 번 순회
boolean exists = users.stream().anyMatch(u -> u.getAge() > 40);
if (exists) {
  users.stream().filter(u -> u.getAge() > 40).findFirst()
      .ifPresent(u -> System.out.println(u.getName()));
}

// 좋은 패턴: findFirst 하나로
users.stream().filter(u -> u.getAge() > 40).findFirst()
    .ifPresent(u -> System.out.println(u.getName())); // dave

// boolean만 필요하면 anyMatch
boolean hasOldUser = users.stream().anyMatch(u -> u.getAge() > 40); // true

// [예제 6] 활성 사용자 이메일 검증
List<String> activeEmails = users.stream()
    .filter(User::isActive)
    .map(User::getEmail)
    .filter(email -> !email.isBlank())
    .toList();

boolean allExampleDomain = activeEmails.stream()
    .allMatch(email -> email.endsWith("@example.com")); // true

String newEmail = "alice@example.com";
boolean isDuplicate = users.stream()
    .anyMatch(u -> newEmail.equals(u.getEmail())); // true
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App3
  ```

---

### App4 - 복합 예제 (Product/CartItem 도메인)

재고 확인, 가격 범위 검색, 장바구니 주문 검증, 추천 상품 검색을 다룬다.

```java
List<Product> products = Arrays.asList(
    new Product(1, "노트북",    "전자기기", 1_200_000, 5),
    new Product(2, "마우스",    "전자기기",    35_000, 0), // 재고 없음
    new Product(3, "모니터",    "전자기기",   450_000, 3),
    new Product(4, "Java 입문", "도서",       28_000, 12),
    ...
);

// [예제 1] 재고/상태 확인
boolean hasStock       = products.stream().anyMatch(p -> p.getStock() > 0); // true
boolean allElecCheap   = products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .allMatch(p -> p.getPrice() <= 100_000); // false (노트북, 모니터)
boolean noOutOfStockBook = products.stream()
    .filter(p -> "도서".equals(p.getCategory()))
    .noneMatch(p -> p.getStock() == 0);      // true
boolean hasOutOfStock  = products.stream().anyMatch(p -> p.getStock() == 0); // true

// [예제 2] filter + findFirst - 상품 검색
// ID로 단일 상품 검색 (Repository.findById 패턴)
products.stream().filter(p -> p.getId() == 3).findFirst()
    .ifPresent(p -> System.out.printf("%s / %,d원", p.getName(), p.getPrice())); // 모니터

// 카테고리에서 재고 있는 최저가 (min 활용)
products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .filter(p -> p.getStock() > 0)
    .min(Comparator.comparingInt(Product::getPrice)); // 키보드 (마우스는 재고 없음)

// 키워드 포함 검색
products.stream()
    .filter(p -> p.getName().contains("Java"))
    .findFirst()
    .ifPresentOrElse(
        p  -> System.out.printf("'%s' 검색 결과: %s", "Java", p.getName()),
        () -> System.out.printf("'%s' 검색 결과 없음", "Java")
    );

// [예제 3] 가격 범위 검색
int minPrice = 30_000, maxPrice = 100_000;

boolean hasPriceRange = products.stream()
    .anyMatch(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice); // true

products.stream()
    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
    .filter(p -> p.getStock() > 0)
    .findFirst(); // 알고리즘 (35,000원, 재고 8)

// [예제 4] allMatch - 장바구니 주문 가능 여부 검증
List<CartItem> cart = Arrays.asList(
    new CartItem(1, 2), // 노트북 2개 (재고 5개)
    new CartItem(4, 5), // Java 입문 5개 (재고 12개)
    new CartItem(6, 1)  // 의자 1개 (재고 2개)
);

boolean canOrder = cart.stream()
    .allMatch(item -> products.stream()
        .filter(p -> p.getId() == item.getProductId())
        .findFirst()
        .map(p -> p.getStock() >= item.getQty())
        .orElse(false)); // true

// [예제 5] 추천 상품 - 재고 있는 최저가
products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .filter(p -> p.getStock() > 0)
    .min(Comparator.comparingInt(Product::getPrice))
    .ifPresentOrElse(
        p  -> System.out.printf("%s (%,d원, 재고 %d개)", p.getName(), p.getPrice(), p.getStock()),
        () -> System.out.println("추천 상품 없음")
    );
```

- `min(Comparator)`은 Comparator를 받아 최솟값을 `Optional<T>`로 반환한다. `max(Comparator)`은 최댓값을 반환한다.
- `allMatch` 내부에서 중첩 스트림으로 `findFirst`를 호출하는 패턴은 실전 장바구니 검증 코드에서 자주 사용된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App4
  ```

---

## Exam10 - 반복

### 개념

#### forEach(Consumer) — 반복 처리

`forEach(Consumer<T>)`는 스트림의 각 요소에 대해 Consumer를 실행하는 **최종 연산**이다.
`void`를 반환하며 스트림을 종료한다.

```java
stream.forEach(element -> System.out.println(element));
stream.forEach(System.out::println); // 메서드 레퍼런스로 단축
```

#### forEachOrdered(Consumer) — 순서 보장 반복

`forEachOrdered(Consumer<T>)`는 스트림의 **encounter order(만남 순서)를 보장**하며 각 요소에 Consumer를 실행한다.

| 항목 | `forEach` | `forEachOrdered` |
|---|---|---|
| 순차 스트림 | 소스 순서 처리 | 소스 순서 처리 (동일) |
| 병렬 스트림 | 순서 미보장, 성능 우선 | 소스 순서 보장, 성능 일부 희생 |
| 사용 시점 | 순서가 중요하지 않을 때 | 병렬 스트림에서 순서가 필요할 때 |

#### forEach와 부작용

`forEach` 내부에서 외부 상태를 변경하는 것은 **부작용(side effect)** 이다.
병렬 스트림에서 스레드 안전 문제를 일으킬 수 있으므로, 결과를 수집할 때는 `collect()`를 사용한다.

```java
// 안티패턴: forEach로 외부 리스트에 수집
List<Integer> result = new ArrayList<>();
stream.filter(...).forEach(result::add); // 병렬에서 위험

// 올바른 패턴: collect로 수집
List<Integer> result = stream.filter(...).toList();
```

---

### App - forEach() 기초

명령형 vs 선언형 비교, 중간 연산과의 조합, 부작용 안티패턴, `List.forEach()`를 다룬다.

```java
List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// [예제 1] forEach 기본
// 명령형
for (String name : names) { System.out.println(name); }

// 선언형 - 람다
names.stream().forEach(name -> System.out.println(name));

// 선언형 - 메서드 레퍼런스 (가장 간결)
names.stream().forEach(System.out::println);

// [예제 2] forEach + filter + map
numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .forEach(System.out::println); // 4 16

// [예제 3] 부작용 안티패턴 vs 올바른 패턴
// 안티패턴
List<Integer> result = new ArrayList<>();
numbers.stream().filter(n -> n % 2 == 0).forEach(result::add);

// 올바른 패턴
List<Integer> collected = numbers.stream().filter(n -> n % 2 == 0).toList();

// [예제 4] List.forEach() - 스트림 없이 바로 호출
names.forEach(System.out::println); // 스트림 생성 불필요
```

- `forEach()`는 최종 연산이므로 이후 스트림 연산을 추가할 수 없다.
- `List.forEach()`는 스트림을 거치지 않으므로 단순 반복에 더 가볍다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam10.App
  ```

---

### App2 - forEachOrdered()

순차/병렬 스트림에서의 차이, 정렬 순서 유지, 사용 시점을 다룬다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// [예제 1] 순차 스트림 - forEach와 forEachOrdered 결과 동일
numbers.stream().filter(n -> n % 2 == 0).forEach(...);        // 2 4 6 8 10
numbers.stream().filter(n -> n % 2 == 0).forEachOrdered(...); // 2 4 6 8 10

// [예제 2] 병렬 스트림 - 순서 차이
numbers.parallelStream().filter(n -> n % 2 == 0)
    .forEach(...);         // 순서 미보장 (실행마다 다를 수 있음)
numbers.parallelStream().filter(n -> n % 2 == 0)
    .forEachOrdered(...);  // 항상 2 4 6 8 10

// [예제 3] sorted() + forEachOrdered - 정렬 순서 유지
names.parallelStream()
    .sorted()
    .forEachOrdered(System.out::println); // Alice Bob Charlie Dave Eve (항상)

// [예제 4] 순번 있는 목록 출력 (병렬에서도 순서 보장)
int[] index = {1};
names.parallelStream()
    .sorted()
    .forEachOrdered(name -> System.out.println(index[0]++ + ". " + name));
```

- 순차 스트림에서는 `forEach`와 `forEachOrdered` 결과가 동일하다.
- 병렬 스트림에서 순서가 필요한 경우에만 `forEachOrdered`를 사용한다. 불필요하게 쓰면 병렬 처리의 이점이 줄어든다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam10.App2
  ```

---

## Exam11 - 단순 집계

### 개념

#### count() — 요소 수

스트림의 요소 수를 `long`으로 반환한다.

```java
long count = stream.count();
long filteredCount = stream.filter(predicate).count();
```

#### min(Comparator) / max(Comparator) — 최솟값 / 최댓값

Comparator 기준으로 최솟값/최댓값을 `Optional<T>`로 반환한다.
스트림이 비어 있으면 `Optional.empty()`를 반환한다.

```java
Optional<T> min = stream.min(Comparator.comparingInt(T::getField));
Optional<T> max = stream.max(Comparator.comparingInt(T::getField));
```

#### 기본형 스트림 집계

`IntStream`, `LongStream`, `DoubleStream`은 박싱 없이 집계 메서드를 직접 제공한다.

| 메서드 | 반환 타입 | 빈 스트림 |
|---|---|---|
| `sum()` | `int` / `long` / `double` | `0` |
| `average()` | `OptionalDouble` | `OptionalDouble.empty()` |
| `min()` | `OptionalInt` / `OptionalLong` / `OptionalDouble` | `empty()` |
| `max()` | `OptionalInt` / `OptionalLong` / `OptionalDouble` | `empty()` |
| `summaryStatistics()` | `IntSummaryStatistics` 등 | count=0, sum=0 |

#### summaryStatistics() — 단 1번 순회로 모든 통계

`sum()`, `average()`, `min()`, `max()`를 각각 호출하면 스트림을 4번 순회한다.
`summaryStatistics()`는 **단 1번 순회**로 count/sum/average/min/max를 모두 계산한다.

```java
IntSummaryStatistics stats = stream.mapToInt(f).summaryStatistics();
stats.getCount();    stats.getSum();    stats.getAverage();
stats.getMin();      stats.getMax();
```

---

### App - count() / min() / max()

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

### App2 - 기본형 스트림 집계

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

---

## Exam12 - 범용 집계 (reduce)

### 개념

#### reduce() — 범용 집계

`reduce()`는 스트림의 모든 요소를 **하나의 값으로 합치는** 최종 연산이다.
`count()`, `sum()`, `min()`, `max()`가 `reduce()`의 특수한 형태이다.

#### reduce()의 세 가지 형태

| 형태 | 시그니처 | 반환 타입 | 빈 스트림 |
|---|---|---|---|
| 1 | `reduce(BinaryOperator<T>)` | `Optional<T>` | `Optional.empty()` |
| 2 | `reduce(T identity, BinaryOperator<T>)` | `T` | `identity` |
| 3 | `reduce(U identity, BiFunction<U,T,U>, BinaryOperator<U>)` | `U` | `identity` |

#### identity (항등원)

연산 `f`에서 `f(identity, x) == x`를 만족하는 값이다.

| 연산 | identity |
|---|---|
| 덧셈 | `0` |
| 곱셈 | `1` |
| 최댓값 | `Integer.MIN_VALUE` |
| 최솟값 | `Integer.MAX_VALUE` |
| 문자열 연결 | `""` |

#### IntStream.reduce() 사용 권장

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

### App - reduce() 기초

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

### App2 - reduce() 3인자 / 실전 패턴

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

---

## Exam13 - 배열 변환 (toArray)

### 개념

#### toArray() — 배열로 수집

스트림의 요소를 배열로 수집하는 최종 연산이다.

| 형태 | 시그니처 | 반환 타입 | 특징 |
|---|---|---|---|
| 1 | `toArray()` | `Object[]` | 타입 정보 없음, 캐스팅 필요 |
| 2 | `toArray(T[]::new)` | `T[]` | 정확한 타입 배열, 캐스팅 불필요 |

#### 배열 생성자 레퍼런스

`toArray(IntFunction<A[]>)`의 인자는 배열 크기를 받아 배열을 만드는 함수이다.

```java
// 배열 생성자 레퍼런스 = (int n) -> new String[n]
String[] arr = stream.toArray(String[]::new);
```

#### 기본형 스트림 toArray

`IntStream`, `LongStream`, `DoubleStream`은 `toArray()`가 바로 기본형 배열을 반환한다.

```java
int[]    arr = intStream.toArray();    // int[]
long[]   arr = longStream.toArray();   // long[]
double[] arr = doubleStream.toArray(); // double[]
```

#### toArray vs toList

| 항목 | `toArray(T[]::new)` | `toList()` (Java 16+) |
|---|---|---|
| 반환 타입 | `T[]` | `List<T>` (불변) |
| 요소 변경 | 가능 (`arr[i] = value`) | 불가 (`UnsupportedOperationException`) |
| 크기 변경 | 불가 (배열 크기 고정) | 불가 (불변 리스트) |
| 사용 시점 | 배열 API와 연동할 때 | 일반적인 컬렉션이 필요할 때 |

---

### App - toArray() 기초

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

---

## Exam14 - 수집 기초

### 개념

#### collect(Collector) — 수집

`collect(Collector<T,A,R>)`는 스트림의 요소를 **컬렉션이나 다른 자료구조로 수집**하는 최종 연산이다.
`Collectors` 클래스가 다양한 Collector 팩토리 메서드를 제공한다.

#### 리스트/셋 수집

| 메서드 | 반환 타입 | 가변 여부 | 비고 |
|---|---|---|---|
| `toList()` (Java 16+) | `List<T>` | 불변 | 가장 간결 |
| `Collectors.toList()` | `List<T>` | 가변 | null 허용 |
| `Collectors.toUnmodifiableList()` | `List<T>` | 불변 | null 불허 (Java 10+) |
| `Collectors.toSet()` | `Set<T>` | 가변 | 중복 제거, 순서 미보장 |
| `Collectors.toUnmodifiableSet()` | `Set<T>` | 불변 | Java 10+ |
| `Collectors.toCollection(Supplier)` | `C<T>` | 구현체에 따름 | LinkedList, TreeSet 등 |

#### Map 수집

| 메서드 | 키 중복 | 비고 |
|---|---|---|
| `toMap(key, value)` | `IllegalStateException` | 키 중복 불허 |
| `toMap(key, value, merge)` | merge 함수로 처리 | 키 중복 허용 |
| `toMap(key, value, merge, mapFactory)` | merge 함수로 처리 | 구현체 지정 가능 |
| `toUnmodifiableMap(key, value)` | `IllegalStateException` | 불변 Map (Java 10+) |

#### 문자열 수집

| 메서드 | 결과 |
|---|---|
| `joining()` | `"AliceBobCharlie"` |
| `joining(", ")` | `"Alice, Bob, Charlie"` |
| `joining(", ", "[", "]")` | `"[Alice, Bob, Charlie]"` |

---

### App - toList / toSet / toCollection

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

### App2 - toMap()

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

### App3 - joining()

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

---

## Exam15 - 수집 고급

### 개념

#### groupingBy() — 그룹화

스트림 요소를 분류 함수의 결과값으로 그룹화한다.

| 형태 | 반환 타입 |
|---|---|
| `groupingBy(classifier)` | `Map<K, List<T>>` |
| `groupingBy(classifier, downstream)` | `Map<K, D>` |
| `groupingBy(classifier, mapFactory, downstream)` | `Map<K, D>` (구현체 지정) |

#### partitioningBy() — 이진 분류

조건(Predicate) 기준으로 두 그룹으로 나눈다. 키는 항상 `Boolean`이다.

| 형태 | 반환 타입 |
|---|---|
| `partitioningBy(predicate)` | `Map<Boolean, List<T>>` |
| `partitioningBy(predicate, downstream)` | `Map<Boolean, D>` |

#### groupingBy vs partitioningBy

| 항목 | `groupingBy` | `partitioningBy` |
|---|---|---|
| 그룹 수 | N개 | 2개 (true/false) |
| 키 타입 | 분류 함수의 반환 타입 | `Boolean` |
| 빈 그룹 | 키 없음 | false 키도 항상 존재 |
| 사용 시점 | 카테고리별 분류 | 합격/불합격, 활성/비활성 |

#### 주요 downstream Collector

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

#### teeing() — 두 Collector 동시 처리 (Java 12+)

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

### App - groupingBy()

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

### App2 - partitioningBy()

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

### App3 - downstream Collector / teeing()

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

---

## Exam16 - 병렬 스트림

### 개념

병렬 스트림(parallel stream)은 스트림 요소를 여러 작업으로 나누어 여러 스레드에서 동시에 처리하는 기능이다.
컬렉션의 `parallelStream()`을 호출하거나, 기존 스트림에 `parallel()`을 호출해 만들 수 있다.

```java
list.parallelStream()
    .filter(...)
    .map(...)
    .toList();

list.stream()
    .parallel()
    .filter(...)
    .toList();
```

병렬 스트림은 내부적으로 `ForkJoinPool.commonPool()`을 사용한다.
따라서 애플리케이션의 다른 병렬 작업과 같은 공용 풀을 공유한다.

#### 병렬 스트림이 적합한 경우

| 조건 | 설명 |
|---|---|
| 요소 수가 많다 | 분할/병합 비용을 상쇄할 만큼 작업량이 있어야 한다 |
| 요소별 처리 비용이 크다 | 단순한 덧셈/필터링은 오히려 느릴 수 있다 |
| 작업이 독립적이다 | 각 요소 처리가 서로의 상태에 의존하지 않아야 한다 |
| 순서가 중요하지 않다 | 순서 보장이 필요하면 병렬 처리 이점이 줄어든다 |

#### 주의할 점

| 항목 | 설명 |
|---|---|
| 공유 가변 상태 | 외부 `ArrayList`, 배열, 카운터를 직접 변경하면 경쟁 조건이 생긴다 |
| 순서 보장 | `forEach()`는 순서를 보장하지 않는다. 필요하면 `forEachOrdered()` 사용 |
| 공용 풀 사용 | `parallelStream()`은 기본적으로 `ForkJoinPool.commonPool()`을 사용한다 |
| 작은 작업 | 요소 수가 적거나 작업이 가벼우면 병렬화 오버헤드가 더 클 수 있다 |

#### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `parallelStream()`, `parallel()`, `forEachOrdered()` 기본 |
| `App2` | 병렬 스트림 성능 조건 |
| `App3` | 공유 가변 상태의 위험과 안전한 수집 |
| `App4` | `groupingByConcurrent()`, `unordered()` 병렬 수집 |

---

### App - 병렬 스트림 기본

순차 스트림과 병렬 스트림의 실행 스레드, `forEach()`와 `forEachOrdered()`의 순서 차이를 확인한다.

```java
numbers.parallelStream()
    .map(n -> trace(n))
    .forEach(System.out::println);

numbers.parallelStream()
    .forEachOrdered(System.out::println);
```

- `parallelStream()`은 병렬 스트림을 생성한다.
- `stream().parallel()`은 순차 스트림을 병렬 스트림으로 전환한다.
- `forEach()`는 병렬 스트림에서 출력 순서를 보장하지 않는다.
- `forEachOrdered()`는 원본 순서를 보장하지만 병렬 처리 이점이 줄어들 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam16.App
  ```

---

### App2 - 병렬 스트림 성능 조건

작은 작업과 무거운 작업을 각각 순차/병렬로 실행해 병렬화 비용을 비교한다.

```java
long sum = LongStream.rangeClosed(1, count)
    .parallel()
    .map(App2::heavyWork)
    .sum();
```

- 병렬 스트림은 항상 빠르지 않다.
- 요소별 작업이 가볍다면 분할, 스케줄링, 병합 비용 때문에 순차 스트림이 더 빠를 수 있다.
- 요소 수가 많고 각 요소 처리 비용이 충분히 클 때 병렬 처리 효과가 커진다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam16.App2
  ```

---

### App3 - 공유 가변 상태 주의

병렬 스트림에서 외부 `ArrayList`를 직접 변경하는 방식과 스트림 수집 연산을 사용하는 방식을 비교한다.

```java
// 나쁜 예
List<Integer> result = new ArrayList<>();
IntStream.rangeClosed(1, 10000)
    .parallel()
    .forEach(result::add);

// 권장
List<Integer> result = IntStream.rangeClosed(1, 10000)
    .parallel()
    .boxed()
    .toList();
```

- `ArrayList`는 스레드 안전하지 않다.
- `Collections.synchronizedList()`로 보호할 수 있지만 락 경합이 생길 수 있다.
- 병렬 스트림에서는 외부 상태 변경보다 `collect()`, `toList()`, `reduce()` 같은 연산을 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam16.App3
  ```

---

### App4 - 병렬 수집

`groupingBy()`와 `groupingByConcurrent()`를 비교하고, 순서가 필요 없는 병렬 처리에서 `unordered()`를 사용한다.

```java
ConcurrentMap<String, Long> countByCategory =
    orders.parallelStream()
        .collect(Collectors.groupingByConcurrent(
            Order::category,
            Collectors.counting()));
```

- `groupingBy()`는 병렬 스트림에서도 동작하지만 부분 결과를 병합하는 비용이 발생한다.
- `groupingByConcurrent()`는 `ConcurrentMap`을 사용해 병렬 수집에 적합하다.
- `unordered()`는 원본 순서가 필요 없다는 의도를 스트림에 알려준다.

#### groupingBy vs groupingByConcurrent 비교

**`groupingBy` (countByCategory)**

- 각 스레드가 부분 결과(Map)를 따로 만든 뒤 나중에 병합(merge)
- 병합 비용이 발생
- 반환 타입: `HashMap`
- 그룹 내 순서 보장

**`groupingByConcurrent` (concurrentCount)**

- 여러 스레드가 단 하나의 `ConcurrentMap`을 동시에 직접 갱신
- 병합 비용 없음 → 병렬 환경에서 더 효율적
- 반환 타입: `ConcurrentHashMap`
- 그룹 내 순서 보장 안 됨

| 항목 | `groupingBy` | `groupingByConcurrent` |
|---|---|---|
| 수집 전략 | 부분 Map 생성 후 병합 | 공유 Map에 직접 삽입 |
| 병합 비용 | 있음 | 없음 |
| 반환 타입 | `HashMap` | `ConcurrentHashMap` |
| 순서 보장 | O | X |
| 적합한 상황 | 순서가 중요할 때 | 순서 불필요, 성능 우선 |

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam16.App4
  ```
