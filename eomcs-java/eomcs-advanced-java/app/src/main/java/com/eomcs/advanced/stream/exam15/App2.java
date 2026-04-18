package com.eomcs.advanced.stream.exam15;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 수집 고급 - partitioningBy():
//
// Collectors.partitioningBy(Predicate<T>)
//   - 스트림 요소를 조건(Predicate) 기준으로 두 그룹으로 나눈다.
//   - Map<Boolean, List<T>>를 반환한다. (true 그룹, false 그룹)
//   - groupingBy의 특수한 형태: 키가 항상 Boolean(true/false)이다.
//
// Collectors.partitioningBy(Predicate<T>, downstream)
//   - 각 파티션에 downstream Collector를 적용한다.
//
// groupingBy vs partitioningBy:
//   groupingBy:      N개 그룹으로 분류. 키 타입이 다양하다.
//   partitioningBy:  2개 그룹으로 분류. 키는 항상 Boolean.
//                    partitioningBy는 false 파티션이 비어도 키를 보장한다.
//

public class App2 {

  public static void main(String[] args) {

    List<App.Person> people = Arrays.asList(
        new App.Person("Alice",   "서울", 28, "개발"),
        new App.Person("Bob",     "부산", 35, "기획"),
        new App.Person("Charlie", "서울", 22, "개발"),
        new App.Person("Dave",    "대구", 41, "개발"),
        new App.Person("Eve",     "서울", 28, "기획"),
        new App.Person("Frank",   "부산", 31, "개발"),
        new App.Person("Grace",   "대구", 25, "기획")
    );

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. partitioningBy - 기본 (true/false 두 그룹)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] partitioningBy - 짝수/홀수 분류");

    Map<Boolean, List<Integer>> evenOdd = numbers.stream()
        .collect(Collectors.partitioningBy(n -> n % 2 == 0));

    System.out.println("  짝수(true):  " + evenOdd.get(true));  // [2, 4, 6, 8, 10]
    System.out.println("  홀수(false): " + evenOdd.get(false)); // [1, 3, 5, 7, 9]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. partitioningBy - 30세 기준 분류
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] partitioningBy - 30세 이상 / 미만");

    Map<Boolean, List<App.Person>> agePartition = people.stream()
        .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));

    System.out.print("  30세 이상: ");
    System.out.println(agePartition.get(true).stream().map(App.Person::getName).toList());

    System.out.print("  30세 미만: ");
    System.out.println(agePartition.get(false).stream().map(App.Person::getName).toList());

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. partitioningBy + downstream
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] partitioningBy + counting - 파티션별 개수");

    Map<Boolean, Long> countPartition = people.stream()
        .collect(Collectors.partitioningBy(
            p -> p.getDept().equals("개발"),
            Collectors.counting()
        ));
    System.out.println("  개발(true):  " + countPartition.get(true) + "명");
    System.out.println("  기획(false): " + countPartition.get(false) + "명");

    System.out.println();

    System.out.println("[예제 4] partitioningBy + joining - 파티션별 이름 목록");

    Map<Boolean, String> namePartition = people.stream()
        .collect(Collectors.partitioningBy(
            p -> p.getCity().equals("서울"),
            Collectors.mapping(App.Person::getName, Collectors.joining(", "))
        ));
    System.out.println("  서울(true):    " + namePartition.get(true));
    System.out.println("  서울 외(false): " + namePartition.get(false));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. groupingBy vs partitioningBy 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] groupingBy vs partitioningBy 비교");

    // groupingBy: 키가 String (도시)
    Map<String, Long> byCity = people.stream()
        .collect(Collectors.groupingBy(App.Person::getCity, Collectors.counting()));
    System.out.println("  groupingBy(도시): " + byCity);

    // partitioningBy: 키가 항상 Boolean
    Map<Boolean, Long> bySeoul = people.stream()
        .collect(Collectors.partitioningBy(
            p -> p.getCity().equals("서울"), Collectors.counting()));
    System.out.println("  partitioningBy(서울 여부): true=" + bySeoul.get(true)
        + ", false=" + bySeoul.get(false));

    System.out.println();
    System.out.println("→ partitioningBy는 항상 true/false 두 키를 모두 가진다 (비어도 빈 리스트 반환).");
    System.out.println("→ 이진 분류(합격/불합격, 활성/비활성 등)에는 partitioningBy가 더 의도를 명확히 표현한다.");
  }
}
