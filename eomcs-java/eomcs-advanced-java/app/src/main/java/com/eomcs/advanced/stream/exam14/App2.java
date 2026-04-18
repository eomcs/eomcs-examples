package com.eomcs.advanced.stream.exam14;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 수집 기초 - toMap():
//
// Collectors.toMap(keyMapper, valueMapper)
//   - 스트림 요소를 Map으로 수집한다.
//   - keyMapper: 요소 → 키 추출 함수
//   - valueMapper: 요소 → 값 추출 함수
//   - 키 중복 시 IllegalStateException 발생
//
// Collectors.toMap(keyMapper, valueMapper, mergeFunction)
//   - mergeFunction: 키 중복 시 기존 값과 새 값을 합치는 방법을 지정한다.
//
// Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapFactory)
//   - mapFactory: 생성할 Map 구현체를 지정한다. (LinkedHashMap 등)
//
// Collectors.toUnmodifiableMap(keyMapper, valueMapper)
//   - 불변 Map으로 수집한다.
//

public class App2 {

  public static void main(String[] args) {

    List<Person> people = Arrays.asList(
        new Person(1, "Alice",   28),
        new Person(2, "Bob",     35),
        new Person(3, "Charlie", 22),
        new Person(4, "Dave",    41),
        new Person(5, "Eve",     28)  // Alice와 나이 같음
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. toMap(keyMapper, valueMapper) - 기본
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] toMap(keyMapper, valueMapper) - id → name");

    Map<Integer, String> idToName = people.stream()
        .collect(Collectors.toMap(Person::getId, Person::getName));
    System.out.println("  idToName: " + idToName);
    System.out.println("  ID 3의 이름: " + idToName.get(3)); // Charlie

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. toMap - 요소 자체를 값으로 (Function.identity())
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] toMap - 요소 자체를 값으로 (id → Person)");

    Map<Integer, Person> idToPerson = people.stream()
        .collect(Collectors.toMap(Person::getId, p -> p));
    idToPerson.forEach((id, p) ->
        System.out.printf("  %d → %s (%d세)%n", id, p.getName(), p.getAge()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. toMap - 키 중복 시 mergeFunction
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] toMap(keyMapper, valueMapper, mergeFunction) - 키 중복 처리");

    // 나이(age) → 이름 Map (Alice와 Eve의 나이가 28로 같음 → 충돌)
    // 키 중복 시 IllegalStateException 방지: mergeFunction으로 기존 값 유지
    Map<Integer, String> ageToName = people.stream()
        .collect(Collectors.toMap(
            Person::getAge,                     // keyMapper
            Person::getName,                    // valueMapper
            (existing, replacement) -> existing // mergeFunction: 기존 값 유지
        ));
    System.out.println("  age → name (중복 시 기존 유지): " + ageToName);

    // 키 중복 시 이름 연결
    Map<Integer, String> ageToNames = people.stream()
        .collect(Collectors.toMap(
            Person::getAge,
            Person::getName,
            (existing, replacement) -> existing + ", " + replacement // 이름 연결
        ));
    System.out.println("  age → names (중복 시 연결):     " + ageToNames);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. toMap - LinkedHashMap으로 삽입 순서 유지
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] toMap - LinkedHashMap으로 삽입 순서 유지");

    Map<Integer, String> orderedMap = people.stream()
        .collect(Collectors.toMap(
            Person::getId,
            Person::getName,
            (a, b) -> a,                                // 중복 없으므로 임의 mergeFunction
            java.util.LinkedHashMap::new               // mapFactory: LinkedHashMap
        ));
    System.out.println("  삽입 순서 유지: " + orderedMap);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. toUnmodifiableMap - 불변 Map
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] toUnmodifiableMap - 불변 Map");

    Map<Integer, String> unmodifiableMap = people.stream()
        .collect(Collectors.toUnmodifiableMap(Person::getId, Person::getName));
    System.out.println("  결과: " + unmodifiableMap);
    try {
      unmodifiableMap.put(99, "New");
    } catch (UnsupportedOperationException e) {
      System.out.println("  불변 Map: 추가 불가");
    }

    System.out.println();
    System.out.println("→ toMap(key, value)은 키 중복 시 IllegalStateException이 발생한다.");
    System.out.println("→ 키 중복 가능성이 있으면 세 번째 인자 mergeFunction을 반드시 지정한다.");
    System.out.println("→ 삽입 순서를 보장하려면 네 번째 인자로 LinkedHashMap::new를 지정한다.");
  }

  static class Person {
    private final int    id;
    private final String name;
    private final int    age;

    Person(int id, String name, int age) {
      this.id   = id;
      this.name = name;
      this.age  = age;
    }

    int    getId()   { return id; }
    String getName() { return name; }
    int    getAge()  { return age; }
  }
}
