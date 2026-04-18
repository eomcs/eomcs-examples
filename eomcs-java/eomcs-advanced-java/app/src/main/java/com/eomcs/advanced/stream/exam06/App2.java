package com.eomcs.advanced.stream.exam06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 정렬 - Comparator.comparing() / thenComparing():
//
// Comparator.comparing(keyExtractor)
//   - 특정 필드(키)를 기준으로 오름차순 Comparator를 만든다.
//   - keyExtractor: Function<T, Comparable> — 정렬 기준 값을 추출하는 함수
//
// Comparator.comparingInt / comparingLong / comparingDouble
//   - 기본형 키를 추출할 때 사용한다. 박싱 없이 int/long/double로 직접 비교한다.
//
// thenComparing(keyExtractor)
//   - 1차 정렬 기준이 같을 때 2차 기준으로 정렬한다. 다중 정렬 기준 연결에 사용한다.
//
// reversed()
//   - 기존 Comparator의 순서를 반대로 뒤집는다.
//   - Comparator.comparing(...).reversed() 처럼 체이닝할 수 있다.
//

public class App2 {

  public static void main(String[] args) {

    List<Person> people = Arrays.asList(
        new Person("Charlie", 35, "서울"),
        new Person("Alice",   28, "부산"),
        new Person("Dave",    28, "서울"),
        new Person("Bob",     35, "부산"),
        new Person("Eve",     22, "서울")
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. Comparator.comparing() - 단일 필드 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Comparator.comparing() - 단일 필드 정렬");

    // 이름 알파벳 순
    System.out.println("  이름 오름차순:");
    people.stream()
        .sorted(Comparator.comparing(Person::getName))
        .forEach(p -> System.out.printf("    %s (%d세, %s)%n",
            p.getName(), p.getAge(), p.getCity()));
    // Alice Dave Bob Charlie Eve... 아니고 알파벳 순: Alice Bob Charlie Dave Eve

    System.out.println();

    // 나이 오름차순
    System.out.println("  나이 오름차순:");
    people.stream()
        .sorted(Comparator.comparingInt(Person::getAge))
        .forEach(p -> System.out.printf("    %s (%d세)%n", p.getName(), p.getAge()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. reversed() - 내림차순
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] reversed() - 내림차순");

    // 나이 내림차순
    System.out.println("  나이 내림차순:");
    people.stream()
        .sorted(Comparator.comparingInt(Person::getAge).reversed())
        .forEach(p -> System.out.printf("    %s (%d세)%n", p.getName(), p.getAge()));

    System.out.println();

    // 이름 역순
    System.out.println("  이름 역순:");
    people.stream()
        .sorted(Comparator.comparing(Person::getName).reversed())
        .forEach(p -> System.out.printf("    %s%n", p.getName()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. thenComparing() - 다중 정렬 기준
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] thenComparing() - 다중 정렬 기준");

    // 나이 오름차순 → 나이가 같으면 이름 오름차순
    System.out.println("  나이 오름차순 → 이름 오름차순:");
    people.stream()
        .sorted(Comparator.comparingInt(Person::getAge)
            .thenComparing(Person::getName))
        .forEach(p -> System.out.printf("    %s (%d세, %s)%n",
            p.getName(), p.getAge(), p.getCity()));

    System.out.println();

    // 도시 오름차순 → 나이 내림차순 → 이름 오름차순
    System.out.println("  도시 오름차순 → 나이 내림차순 → 이름 오름차순:");
    people.stream()
        .sorted(Comparator.comparing(Person::getCity)
            .thenComparing(Comparator.comparingInt(Person::getAge).reversed())
            .thenComparing(Person::getName))
        .forEach(p -> System.out.printf("    %s (%d세, %s)%n",
            p.getName(), p.getAge(), p.getCity()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 정렬 후 최솟값/최댓값 - min/max 활용
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] min() / max() - 정렬 기준으로 최솟값/최댓값 검색");

    // 가장 나이 어린 사람
    people.stream()
        .min(Comparator.comparingInt(Person::getAge))
        .ifPresent(p -> System.out.printf("  가장 어린: %s (%d세)%n",
            p.getName(), p.getAge())); // Eve (22세)

    // 이름 알파벳 마지막
    people.stream()
        .max(Comparator.comparing(Person::getName))
        .ifPresent(p -> System.out.printf("  이름 알파벳 마지막: %s%n", p.getName())); // Eve

    System.out.println();
    System.out.println("→ Comparator.comparing()으로 특정 필드 기준 정렬 Comparator를 생성한다.");
    System.out.println("→ thenComparing()으로 동률 시 2차, 3차 정렬 기준을 추가한다.");
    System.out.println("→ reversed()로 오름차순/내림차순을 전환한다.");
  }

  static class Person {
    private final String name;
    private final int    age;
    private final String city;

    Person(String name, int age, String city) {
      this.name = name;
      this.age  = age;
      this.city = city;
    }

    String getName() { return name; }
    int    getAge()  { return age; }
    String getCity() { return city; }
  }
}
