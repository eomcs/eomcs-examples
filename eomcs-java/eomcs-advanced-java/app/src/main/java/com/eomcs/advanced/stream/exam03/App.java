package com.eomcs.advanced.stream.exam03;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

// 스트림 소스 생성 (1) - 컬렉션, 배열, 값:
//
// Collection.stream()
//   - List, Set, Queue 등 모든 Collection 구현체에서 순차 스트림을 생성한다.
//
// Collection.parallelStream()
//   - 병렬 처리를 위한 병렬 스트림을 생성한다.
//   - 내부적으로 ForkJoinPool.commonPool()을 사용한다.
//   - CPU 집약적 작업에 효과적이며, I/O나 단순 작업에는 오버헤드가 더 클 수 있다.
//
// Arrays.stream(array)
//   - 배열로부터 스트림을 생성한다.
//   - Arrays.stream(array, from, to) 로 범위를 지정할 수 있다.
//
// Stream.of(values...)
//   - 가변 인자(varargs)로 직접 스트림을 생성한다.
//   - 소수의 요소를 즉석에서 스트림으로 만들 때 사용한다.
//
// Stream.empty()
//   - 요소가 0개인 빈 스트림을 생성한다.
//   - 메서드가 스트림을 반환해야 하지만 결과가 없을 때 null 대신 사용한다.
//
// Stream.ofNullable(value)  (Java 9+)
//   - 값이 null이면 빈 스트림, null이 아니면 1개짜리 스트림을 반환한다.
//   - flatMap과 함께 null일 수 있는 값을 스트림 파이프라인에 안전하게 연결한다.
//

public class App {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. List / Set에서 스트림 생성
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Collection.stream()");

    List<String> list = Arrays.asList("사과", "바나나", "포도", "딸기");
    Set<Integer> set = Set.of(10, 20, 30, 40);

    // List → 스트림: 삽입 순서 유지
    System.out.print("  List:  ");
    list.stream().forEach(s -> System.out.print(s + " "));
    System.out.println();

    // Set → 스트림: 순서 보장 없음 (Set 특성)
    System.out.print("  Set:   ");
    set.stream().sorted().forEach(n -> System.out.print(n + " ")); // 정렬 후 출력
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Map에서 스트림 생성
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] Map → entrySet / keySet / values 스트림");

    Map<String, Integer> scores = Map.of("Bob", 75, "Alice", 90, "Charlie", 85);

    // Map 자체는 stream()이 없다. entrySet() / keySet() / values()를 경유한다.
    System.out.print("  키:    ");
    scores.keySet().stream().sorted().forEach(k -> System.out.print(k + " "));
    System.out.println();

    System.out.print("  값:    ");
    scores.values().stream().sorted().forEach(v -> System.out.print(v + " "));
    System.out.println();

    System.out.print("  항목:  ");
    scores.entrySet().stream()
        .sorted(Map.Entry.comparingByKey()) // 키 기준 정렬
        .forEach(e -> System.out.print(e.getKey() + "=" + e.getValue() + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 배열에서 스트림 생성
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Arrays.stream()");

    String[] strArr = {"Java", "Python", "Go", "Rust"};
    int[] intArr = {5, 3, 8, 1, 9, 2};
    double[] dblArr = {1.1, 2.2, 3.3};

    // 객체 배열 → Stream<T>
    System.out.print("  String[]: ");
    Arrays.stream(strArr).forEach(s -> System.out.print(s + " "));
    System.out.println();

    // int[] → IntStream (기본형 배열은 IntStream / LongStream / DoubleStream으로 변환된다)
    System.out.print("  int[]:    ");
    Arrays.stream(intArr).sorted().forEach(n -> System.out.print(n + " "));
    System.out.println();

    // 범위 지정: Arrays.stream(array, fromIndex, toIndex)
    // fromIndex 포함, toIndex 미포함
    System.out.print("  int[] [1,4): ");
    Arrays.stream(intArr, 1, 4).forEach(n -> System.out.print(n + " ")); // 3 8 1
    System.out.println();

    System.out.print("  double[]: ");
    Arrays.stream(dblArr).forEach(d -> System.out.print(d + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Stream.of() - 값을 직접 나열
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Stream.of()");

    // 가변 인자로 즉석에서 스트림 생성
    Stream<String> languages = Stream.of("Java", "Kotlin", "Scala");
    System.out.print("  Stream.of: ");
    languages.forEach(s -> System.out.print(s + " "));
    System.out.println();

    // 단일 요소 스트림
    Stream<String> single = Stream.of("하나");
    System.out.println("  단일 요소: " + single.findFirst().orElse("없음"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. Stream.empty() - 빈 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] Stream.empty()");

    // 메서드가 스트림을 반환해야 하지만 결과가 없을 때 null 대신 empty()를 반환한다.
    Stream<String> empty = Stream.empty();
    System.out.println("  빈 스트림 count: " + empty.count()); // 0

    // 실용 예: 조건에 따라 스트림을 반환하는 메서드
    String keyword = ""; // 검색어가 없는 경우
    Stream<String> result =
        keyword.isEmpty()
            ? Stream.empty() // 빈 스트림 반환
            : list.stream().filter(s -> s.contains(keyword)); // 검색 결과 반환
    System.out.println("  검색 결과 count: " + result.count()); // 0

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. Stream.ofNullable() - null 안전 단일 요소 스트림 (Java 9+)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] Stream.ofNullable()");

    String value1 = "데이터";
    String value2 = null;

    // ofNullable: null이면 빈 스트림, 아니면 1개짜리 스트림
    System.out.println("  value1 count: " + Stream.ofNullable(value1).count()); // 1
    System.out.println("  value2 count: " + Stream.ofNullable(value2).count()); // 0

    // 실용 예: null이 섞인 데이터를 flatMap으로 안전하게 펼친다.
    List<String> nullable = Arrays.asList("A", null, "B", null, "C");
    System.out.print("  null 제거: ");
    nullable.stream()
        .flatMap(Stream::ofNullable) // null이면 빈 스트림으로 변환 → 자동 제거
        .forEach(s -> System.out.print(s + " ")); // A B C
    System.out.println();

    // Collection.stream()에는 null 요소가 있어도 스트림 생성은 가능하지만,
    // forEach 등 연산에서 NullPointerException이 발생할 수 있다.
    // Stream.ofNullable()을 flatMap과 사용하면 null 요소를 안전하게 제거할 수 있다.

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 7. Collection 타입 파라미터 활용 - 상위 타입으로 받기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 7] Collection<T>로 다형적 스트림 처리");

    // List, Set, Queue 등 어떤 Collection이든 stream()을 사용할 수 있다.
    List<Integer> listInts = Arrays.asList(3, 1, 4, 1, 5, 9);
    Set<Integer> setInts = Set.of(3, 1, 4, 5, 9);

    System.out.println("  List 합계: " + sumCollection(listInts));
    System.out.println("  Set  합계: " + sumCollection(setInts));

    System.out.println();
    System.out.println("→ Collection.stream()은 List / Set / Queue 등 모든 컬렉션에서 사용할 수 있다.");
    System.out.println(
        "→ 기본형 배열(int[], double[])은 Arrays.stream()으로 IntStream / DoubleStream을 얻는다.");
    System.out.println("→ Stream.ofNullable()과 flatMap을 조합하면 null 요소를 안전하게 제거할 수 있다.");
  }

  // Collection<Integer>를 받아 합계를 구한다. List든 Set이든 동일하게 처리된다.
  static int sumCollection(Collection<Integer> col) {
    return col.stream().mapToInt(Integer::intValue).sum();
  }
}
