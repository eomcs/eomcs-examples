# Exam07 - 엿보기 (peek)

## 개념

### peek(Consumer) — 중간 엿보기

`peek(Consumer<T>)`는 스트림의 각 요소를 **소비하지 않고 들여다보는** 중간 연산이다.
요소를 변경하거나 제거하지 않으며, 원래 요소를 그대로 다음 연산으로 전달한다.

```
filter → peek → map → peek → collect
           ↑           ↑
      중간 확인     중간 확인  (요소는 변하지 않음)
```

### peek()의 특성

| 특성 | 설명 |
|---|---|
| 중간 연산 | 스트림을 종료하지 않는다. 이후 연산이 계속 이어진다 |
| 지연 실행 | 최종 연산이 없으면 `Consumer`가 실행되지 않는다 |
| 부작용 전용 | 요소를 변환하거나 필터링하는 데 사용하지 않는다 |
| 원소 유지 | 요소를 변경하지 않고 그대로 다음 연산으로 전달한다 |

### peek() vs forEach()

| 항목 | `peek(Consumer)` | `forEach(Consumer)` |
|---|---|---|
| 연산 종류 | **중간 연산** | **최종 연산** |
| 스트림 상태 | 이후 연산 가능 | 스트림 종료 |
| 반환 타입 | `Stream<T>` | `void` |
| 주 용도 | 디버깅, 로깅 | 최종 소비, 출력 |

### 스트림 파이프라인의 실행 순서

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

### 주요 활용

```
파이프라인 디버깅:  각 단계 통과 요소 출력
중간 카운팅:        int[] count = {0}; peek(n -> count[0]++)
외부 컬렉션 수집:   List<T> list = new ArrayList<>(); peek(list::add)
로깅:              peek(n -> log.debug("처리: {}", n))
```

---

## App - peek() 기초

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

## App2 - 파이프라인 실행 순서 이해

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
