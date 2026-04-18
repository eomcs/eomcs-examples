# Exam01 - 명령형 vs 선언형

## 개념

### 명령형(Imperative) 프로그래밍

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

### 선언형(Declarative) 프로그래밍 - Stream

"**무엇을(What)**" 원하는지를 기술하는 방식이다.
필터링·변환·집계 등의 연산을 메서드 체인으로 연결하며, 제어 흐름은 스트림 내부에서 처리한다.

```java
// 짝수만 출력 - 선언형
numbers.stream()
    .filter(n -> n % 2 == 0)
    .forEach(n -> System.out.print(n + " "));
```

### Stream 파이프라인 구조

```
소스(Source)  →  중간 연산(Intermediate)  →  최종 연산(Terminal)
List/배열 등      filter, map, sorted ...      forEach, collect, count ...
```

#### 중간 연산 (Intermediate Operation)

| 연산 | 설명 |
|---|---|
| `filter(Predicate<T>)` | 조건을 만족하는 요소만 통과시킨다 |
| `map(Function<T, R>)` | 각 요소를 다른 값으로 변환한다 |
| `mapToInt(ToIntFunction<T>)` | `IntStream`으로 변환한다. `sum()`, `average()` 등을 바로 사용할 수 있다 |
| `sorted()` | 자연 순서(오름차순)로 정렬한다 |
| `sorted(Comparator<T>)` | 지정한 Comparator 기준으로 정렬한다 |

#### 최종 연산 (Terminal Operation)

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

#### 핵심 특징

| 특징 | 설명 |
|---|---|
| 지연(lazy) 실행 | 중간 연산은 최종 연산이 호출될 때 한꺼번에 처리된다 |
| 원본 불변 | 스트림은 원본 컬렉션을 변경하지 않는다 |
| 일회성 | 스트림은 한 번 소비하면 재사용할 수 없다 |
| 단락 평가(short-circuit) | `anyMatch`, `findFirst` 등은 조건 충족 즉시 나머지 요소를 처리하지 않는다 |

#### 명령형 vs 선언형 비교

| 항목 | 명령형 | 선언형(스트림) |
|---|---|---|
| 초점 | 어떻게(How) 처리할지 | 무엇을(What) 원하는지 |
| 제어 흐름 | 개발자가 직접 작성 | 스트림 내부에서 처리 |
| 복합 조건 | `if` 중첩이 깊어짐 | `filter()` 추가로 해결 |
| 결과 수집 | 새 리스트 생성 + `add()` | `collect(Collectors.toList())` |
| 정렬 | 원본 복사 + `Collections.sort()` | `sorted()` (원본 불변) |
| 검색 | 플래그 변수 + `break` | `anyMatch()`, `findFirst()` |
| null 처리 | `null` 반환 → 호출자가 체크 | `Optional` 반환 → `orElse()` 활용 |

#### Optional\<T\>

`findFirst()`, `max()`, `min()` 등은 결과가 없을 수 있으므로 `Optional<T>`로 반환한다.
`null`을 직접 다루는 대신 `Optional`의 메서드로 안전하게 처리할 수 있다.

| 메서드 | 설명 |
|---|---|
| `isPresent()` | 값이 있으면 `true` |
| `get()` | 값을 꺼낸다. 값이 없으면 `NoSuchElementException` 발생 |
| `orElse(T)` | 값이 없으면 기본값을 반환한다 |
| `orElseThrow()` | 값이 없으면 `NoSuchElementException`을 발생시킨다 |

---

## App - 필터링(filter)과 출력(forEach)

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

## App2 - 변환(map)과 집계(reduce / sum)

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

## App3 - 수집(collect)과 정렬(sorted)

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

## App4 - 검색(find / match)과 통계(count / max / min)

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
