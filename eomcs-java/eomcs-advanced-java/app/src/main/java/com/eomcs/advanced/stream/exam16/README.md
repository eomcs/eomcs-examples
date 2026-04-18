# Exam16 - 병렬 스트림

## 개념

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

### 병렬 스트림이 적합한 경우

| 조건 | 설명 |
|---|---|
| 요소 수가 많다 | 분할/병합 비용을 상쇄할 만큼 작업량이 있어야 한다 |
| 요소별 처리 비용이 크다 | 단순한 덧셈/필터링은 오히려 느릴 수 있다 |
| 작업이 독립적이다 | 각 요소 처리가 서로의 상태에 의존하지 않아야 한다 |
| 순서가 중요하지 않다 | 순서 보장이 필요하면 병렬 처리 이점이 줄어든다 |

### 주의할 점

| 항목 | 설명 |
|---|---|
| 공유 가변 상태 | 외부 `ArrayList`, 배열, 카운터를 직접 변경하면 경쟁 조건이 생긴다 |
| 순서 보장 | `forEach()`는 순서를 보장하지 않는다. 필요하면 `forEachOrdered()` 사용 |
| 공용 풀 사용 | `parallelStream()`은 기본적으로 `ForkJoinPool.commonPool()`을 사용한다 |
| 작은 작업 | 요소 수가 적거나 작업이 가벼우면 병렬화 오버헤드가 더 클 수 있다 |

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `parallelStream()`, `parallel()`, `forEachOrdered()` 기본 |
| `App2` | 병렬 스트림 성능 조건 |
| `App3` | 공유 가변 상태의 위험과 안전한 수집 |
| `App4` | `groupingByConcurrent()`, `unordered()` 병렬 수집 |

---

## App - 병렬 스트림 기본

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

## App2 - 병렬 스트림 성능 조건

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

## App3 - 공유 가변 상태 주의

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

## App4 - 병렬 수집

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

### groupingBy vs groupingByConcurrent 비교

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
