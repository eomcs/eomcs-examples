package com.eomcs.advanced.stream.exam15;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 수집 고급 - groupingBy():
//
// Collectors.groupingBy(classifier)
//   - 요소를 분류 함수(classifier)의 결과값으로 그룹화한다.
//   - Map<K, List<T>>를 반환한다.
//   - classifier: T → K 함수. 그룹 키를 추출한다.
//
// Collectors.groupingBy(classifier, downstream)
//   - 각 그룹에 downstream Collector를 적용한다.
//   - Map<K, D>를 반환한다. D는 downstream의 결과 타입.
//
// Collectors.groupingBy(classifier, mapFactory, downstream)
//   - 결과 Map의 구현체를 지정한다.
//

public class App {

  public static void main(String[] args) {

    List<Person> people = Arrays.asList(
        new Person("Alice",   "서울", 28, "개발"),
        new Person("Bob",     "부산", 35, "기획"),
        new Person("Charlie", "서울", 22, "개발"),
        new Person("Dave",    "대구", 41, "개발"),
        new Person("Eve",     "서울", 28, "기획"),
        new Person("Frank",   "부산", 31, "개발"),
        new Person("Grace",   "대구", 25, "기획")
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. groupingBy - 단일 기준 그룹화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] groupingBy - 도시별 그룹화 → Map<String, List<Person>>");

    Map<String, List<Person>> byCity = people.stream()
        .collect(Collectors.groupingBy(Person::getCity));

    byCity.forEach((city, persons) -> {
      System.out.print("  " + city + ": ");
      System.out.println(persons.stream().map(Person::getName).toList());
    });

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. groupingBy + counting - 그룹별 개수
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] groupingBy + counting - 도시별 인원 수");

    Map<String, Long> countByCity = people.stream()
        .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));

    countByCity.forEach((city, count) ->
        System.out.printf("  %-6s: %d명%n", city, count));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. groupingBy + mapping - 그룹별 이름 목록
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] groupingBy + mapping + joining - 부서별 이름 목록");

    Map<String, String> namesByDept = people.stream()
        .collect(Collectors.groupingBy(
            Person::getDept,
            Collectors.mapping(Person::getName, Collectors.joining(", "))
        ));

    namesByDept.forEach((dept, names) ->
        System.out.printf("  %-4s: %s%n", dept, names));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. groupingBy + averagingInt - 그룹별 평균 나이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] groupingBy + averagingInt - 도시별 평균 나이");

    Map<String, Double> avgAgeByCity = people.stream()
        .collect(Collectors.groupingBy(
            Person::getCity,
            Collectors.averagingInt(Person::getAge)
        ));

    avgAgeByCity.forEach((city, avg) ->
        System.out.printf("  %-6s: %.1f세%n", city, avg));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. groupingBy 다단계 - 도시 → 부서별 그룹화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] groupingBy 다단계 - 도시별 → 부서별");

    Map<String, Map<String, List<Person>>> byCityAndDept = people.stream()
        .collect(Collectors.groupingBy(
            Person::getCity,
            Collectors.groupingBy(Person::getDept)
        ));

    byCityAndDept.forEach((city, deptMap) -> {
      System.out.println("  " + city + ":");
      deptMap.forEach((dept, persons) ->
          System.out.println("    " + dept + ": " + persons.stream().map(Person::getName).toList()));
    });

    System.out.println();
    System.out.println("→ groupingBy(classifier)는 Map<K, List<T>>를 반환한다.");
    System.out.println("→ groupingBy(classifier, downstream)으로 각 그룹에 집계(counting, averaging 등)를 적용한다.");
    System.out.println("→ groupingBy를 중첩하면 다단계 그룹화가 가능하다.");
  }

  static class Person {
    private final String name;
    private final String city;
    private final int    age;
    private final String dept;

    Person(String name, String city, int age, String dept) {
      this.name = name;
      this.city = city;
      this.age  = age;
      this.dept = dept;
    }

    String getName() { return name; }
    String getCity() { return city; }
    int    getAge()  { return age; }
    String getDept() { return dept; }
  }
}
