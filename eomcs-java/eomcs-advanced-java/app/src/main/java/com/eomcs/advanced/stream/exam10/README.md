# Exam10 - 반복

## 개념

### forEach(Consumer) — 반복 처리

`forEach(Consumer<T>)`는 스트림의 각 요소에 대해 Consumer를 실행하는 **최종 연산**이다.
`void`를 반환하며 스트림을 종료한다.

```java
stream.forEach(element -> System.out.println(element));
stream.forEach(System.out::println); // 메서드 레퍼런스로 단축
```

### forEachOrdered(Consumer) — 순서 보장 반복

`forEachOrdered(Consumer<T>)`는 스트림의 **encounter order(만남 순서)를 보장**하며 각 요소에 Consumer를 실행한다.

| 항목 | `forEach` | `forEachOrdered` |
|---|---|---|
| 순차 스트림 | 소스 순서 처리 | 소스 순서 처리 (동일) |
| 병렬 스트림 | 순서 미보장, 성능 우선 | 소스 순서 보장, 성능 일부 희생 |
| 사용 시점 | 순서가 중요하지 않을 때 | 병렬 스트림에서 순서가 필요할 때 |

### forEach와 부작용

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

## App - forEach() 기초

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

## App2 - forEachOrdered()

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
