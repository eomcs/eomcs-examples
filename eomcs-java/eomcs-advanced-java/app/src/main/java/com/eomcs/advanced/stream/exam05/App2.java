package com.eomcs.advanced.stream.exam05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 매핑 - flatMap() 중첩 스트림 평탄화:
//
// flatMap(Function<T, Stream<R>>)
//   - 각 요소를 Stream<R>으로 변환한 뒤, 모든 스트림을 하나의 Stream<R>로 합친다.
//   - "변환 + 평탄화(flatten)"를 한 번에 수행한다.
//   - map()은 Stream<Stream<R>>이 되지만, flatMap()은 Stream<R>이 된다.
//
// map vs flatMap:
//   map:     T  → R           : 요소 하나가 변환된 요소 하나로 대응 (1:1)
//   flatMap: T  → Stream<R>   : 요소 하나가 0개 이상의 요소로 변환 (1:N)
//
// 주요 사용 패턴:
//   - 중첩 리스트(List<List<T>>)를 단일 스트림으로 평탄화
//   - 문장(String)을 단어(Stream<String>)로 분해
//   - Optional을 포함한 리스트에서 값만 추출 (exam02 참고)
//   - 계층 구조(부서-직원)의 하위 컬렉션을 모두 합치기
//
// flatMapToInt / flatMapToLong / flatMapToDouble
//   - flatMap + 기본형 스트림 변환을 한 번에 처리한다.
//   - flatMap(f).mapToInt(...)보다 간결하다.
//

public class App2 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. map vs flatMap - 중첩 스트림 차이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] map vs flatMap - 중첩 스트림 차이");

    List<List<Integer>> nested = Arrays.asList(
        Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5),
        Arrays.asList(6, 7, 8, 9)
    );

    // map: 각 리스트를 Stream으로 변환 → Stream<Stream<Integer>> (중첩!)
    System.out.println("  map 결과 타입: Stream<Stream<Integer>>");
    Stream<Stream<Integer>> mapped = nested.stream()
        .map(List::stream); // List<Integer> → Stream<Integer>
    System.out.println("  map count (스트림 개수): " + mapped.count()); // 3 (스트림 3개)

    // flatMap: 각 리스트를 Stream으로 변환 후 평탄화 → Stream<Integer>
    System.out.print("  flatMap 결과: ");
    nested.stream()
        .flatMap(List::stream) // List<Integer> → Stream<Integer>, 자동 평탄화
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5 6 7 8 9
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 문장 → 단어 분해
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 문장 → 단어 분해");

    List<String> sentences = Arrays.asList(
        "Java Stream API is powerful",
        "flatMap flattens nested streams",
        "map and flatMap are different"
    );

    // 각 문장을 단어 배열로 split → Stream<String[]> (배열이므로 그대로 출력 불가)
    // flatMap(Arrays::stream)으로 String[] → Stream<String>으로 변환 후 평탄화
    System.out.println("  전체 단어 목록:");
    sentences.stream()
        .flatMap(sentence -> Arrays.stream(sentence.split(" "))) // 문장 → 단어 배열 → Stream<String>
        .distinct()             // 중복 제거
        .sorted()               // 알파벳 순 정렬
        .forEach(word -> System.out.print("    " + word + "\n"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 단어 빈도 분석 - flatMap + groupingBy
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 단어 빈도 분석");

    List<String> texts = Arrays.asList(
        "stream map filter",
        "map flatMap stream",
        "filter distinct stream"
    );

    // 모든 단어를 평탄화한 뒤 빈도 집계
    texts.stream()
        .flatMap(line -> Arrays.stream(line.split(" ")))
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
        .entrySet().stream()
        .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(e -> System.out.printf("  %-10s: %d회%n", e.getKey(), e.getValue()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 중첩 객체 구조 평탄화 - 부서 → 직원
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 중첩 객체 구조 평탄화 - 부서 → 직원");

    List<Department> departments = Arrays.asList(
        new Department("개발팀", Arrays.asList("Alice", "Bob", "Charlie")),
        new Department("디자인팀", Arrays.asList("Dave", "Eve")),
        new Department("마케팅팀", Arrays.asList("Frank", "Grace", "Hank"))
    );

    // 모든 부서의 직원을 하나의 스트림으로 평탄화
    System.out.print("  전체 직원: ");
    departments.stream()
        .flatMap(dept -> dept.getMembers().stream()) // Department → Stream<String>
        .forEach(name -> System.out.print(name + " "));
    System.out.println();

    // 특정 조건(이름이 5글자 이상)인 직원만
    System.out.print("  이름 5자 이상: ");
    departments.stream()
        .flatMap(dept -> dept.getMembers().stream())
        .filter(name -> name.length() >= 5)
        .sorted()
        .forEach(name -> System.out.print(name + " ")); // Alice Charlie Frank Grace
    System.out.println();

    // 부서별 직원 수 (flatMap 없이 map 사용)
    System.out.println("  부서별 직원 수:");
    departments.stream()
        .map(dept -> dept.getName() + ": " + dept.getMembers().size() + "명")
        .forEach(s -> System.out.println("    " + s));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. flatMapToInt - 중첩 숫자 리스트 합계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] flatMapToInt - 중첩 숫자 리스트 집계");

    List<List<Integer>> scoreGroups = Arrays.asList(
        Arrays.asList(85, 90, 78),
        Arrays.asList(92, 88),
        Arrays.asList(70, 95, 83, 77)
    );

    // flatMap + mapToInt 두 단계
    int sum1 = scoreGroups.stream()
        .flatMap(List::stream)      // Stream<Stream<Integer>> → Stream<Integer>
        .mapToInt(Integer::intValue) // Stream<Integer> → IntStream
        .sum();

    // flatMapToInt: 위 두 단계를 한 번에 처리
    int sum2 = scoreGroups.stream()
        .flatMapToInt(group ->          // List<Integer> → IntStream
            group.stream().mapToInt(Integer::intValue))
        .sum();

    System.out.println("  flatMap + mapToInt: " + sum1);   // 758
    System.out.println("  flatMapToInt:       " + sum2);   // 758

    // 전체 평균
    double avg = scoreGroups.stream()
        .flatMapToInt(group -> group.stream().mapToInt(Integer::intValue))
        .average()
        .orElse(0);
    System.out.printf("  전체 평균:          %.2f%n", avg);

    System.out.println();
    System.out.println("→ flatMap()은 '변환 + 평탄화'를 한 번에 처리한다. 중첩 컬렉션을 단일 스트림으로 만든다.");
    System.out.println("→ map()은 요소가 1:1로 변환되고, flatMap()은 1:N으로 변환된다.");
    System.out.println("→ 문장→단어, 부서→직원처럼 컬렉션 안의 컬렉션을 펼칠 때 flatMap을 사용한다.");
  }

  static class Department {
    private final String       name;
    private final List<String> members;

    Department(String name, List<String> members) {
      this.name    = name;
      this.members = members;
    }

    String       getName()    { return name; }
    List<String> getMembers() { return members; }
  }
}
