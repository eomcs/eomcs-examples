package com.eomcs.advanced.stream.exam05;

import java.util.Arrays;
import java.util.List;

// 매핑 - map() 기초:
//
// map(Function<T, R>)
//   - 스트림의 각 요소를 다른 값으로 변환하는 중간 연산이다.
//   - 입력 타입 T와 출력 타입 R이 달라도 된다. (예: String → Integer)
//   - 원소 수는 변하지 않는다. 요소 하나가 변환된 요소 하나로 대응된다.
//   - Function<T, R>: R apply(T t)
//
// map 형태:
//   map(Function<T, R>)      : Stream<T> → Stream<R>
//   mapToInt(ToIntFunction)  : Stream<T> → IntStream
//   mapToLong(ToLongFunction): Stream<T> → LongStream
//   mapToDouble(...)         : Stream<T> → DoubleStream
//
// 람다 vs 메서드 레퍼런스:
//   s -> s.toUpperCase()  ==  String::toUpperCase   (인스턴스 메서드)
//   s -> Integer.parseInt(s)  ==  Integer::parseInt  (정적 메서드)
//   p -> p.getName()  ==  Person::getName             (인스턴스 메서드)
//

public class App {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 기본 타입 변환 - String → Integer
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] String → Integer 변환");

    List<String> strNumbers = Arrays.asList("1", "2", "3", "4", "5");

    // 람다로 변환
    System.out.print("  람다:            ");
    strNumbers.stream()
        .map(s -> Integer.parseInt(s)) // String → Integer
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5
    System.out.println();

    // 정적 메서드 레퍼런스로 더 간결하게 (Integer::parseInt는 static 메서드)
    System.out.print("  메서드 레퍼런스: ");
    strNumbers.stream()
        .map(Integer::parseInt)        // String → Integer
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 문자열 변환 - 대소문자, trim, 포맷
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 문자열 변환");

    List<String> names = Arrays.asList("  alice  ", "BOB", "Charlie", "  dave");

    // 인스턴스 메서드 레퍼런스 (String::toUpperCase)
    System.out.print("  대문자:          ");
    names.stream()
        .map(String::trim)        // 공백 제거
        .map(String::toUpperCase) // 대문자 변환
        .forEach(s -> System.out.print(s + " ")); // ALICE BOB CHARLIE DAVE
    System.out.println();

    // 포맷 변환: "Hello, {name}!"
    System.out.print("  인사말:          ");
    names.stream()
        .map(String::trim)
        .map(s -> "Hello, " + s + "!")
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 객체의 필드 추출 - 객체 → 값
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 객체 필드 추출");

    List<Person> people = Arrays.asList(
        new Person("Alice", 30),
        new Person("Bob",   25),
        new Person("Charlie", 35),
        new Person("Dave",  28)
    );

    // 이름(String) 추출: Person → String
    System.out.print("  이름:  ");
    people.stream()
        .map(Person::getName)      // Person → String (인스턴스 메서드 레퍼런스)
        .forEach(name -> System.out.print(name + " ")); // Alice Bob Charlie Dave
    System.out.println();

    // 나이(int) 추출: Person → Integer
    System.out.print("  나이:  ");
    people.stream()
        .map(Person::getAge)       // Person → Integer
        .forEach(age -> System.out.print(age + " ")); // 30 25 35 28
    System.out.println();

    // 나이 합계: mapToInt → sum (Integer::sum은 int 파라미터를 받아 언박싱 null 경고 발생)
    int totalAge = people.stream()
        .mapToInt(Person::getAge)
        .sum();
    System.out.println("  나이 합계: " + totalAge); // 118

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 객체 → 다른 객체 변환 (DTO 변환)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 객체 → 다른 객체 변환 (Person → PersonDto)");

    // Person 객체를 PersonDto(이름만 포함)로 변환
    people.stream()
        .map(p -> new PersonDto(p.getName().toUpperCase(), p.getAge() >= 30))
        .forEach(dto -> System.out.printf(
            "  이름: %-10s 시니어: %s%n", dto.getName(), dto.isSenior()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. map 체이닝 - 여러 변환을 순서대로 적용
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] map 체이닝 - 여러 변환 연결");

    List<String> raw = Arrays.asList(" apple ", "BANANA", "  Cherry  ", "date");

    // 1. 공백 제거 → 2. 소문자 → 3. 첫 글자 대문자
    System.out.print("  정규화: ");
    raw.stream()
        .map(String::trim)                        // 1. 공백 제거
        .map(String::toLowerCase)                 // 2. 소문자화
        .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1)) // 3. 첫 글자 대문자
        .forEach(s -> System.out.print(s + " ")); // Apple Banana Cherry Date
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. map으로 조건부 변환 - null 안전 처리
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 조건부 변환과 null 안전 처리");

    List<String> mixed = Arrays.asList("hello", null, "world", null, "java");

    // null을 "N/A"로 대체하여 변환
    System.out.print("  null 처리: ");
    mixed.stream()
        .map(s -> s != null ? s.toUpperCase() : "N/A") // null이면 "N/A", 아니면 대문자
        .forEach(s -> System.out.print(s + " ")); // HELLO N/A WORLD N/A JAVA
    System.out.println();

    System.out.println();
    System.out.println("→ map()은 요소 수를 유지하면서 각 요소를 다른 타입이나 값으로 변환한다.");
    System.out.println("→ 람다와 메서드 레퍼런스는 동등하다. 메서드 레퍼런스가 더 간결하다.");
    System.out.println("→ map 체이닝으로 여러 변환을 순서대로 적용할 수 있다.");
  }

  static class Person {
    private final String name;
    private final int    age;

    Person(String name, int age) {
      this.name = name;
      this.age  = age;
    }

    String getName() { return name; }
    int    getAge()  { return age; }
  }

  static class PersonDto {
    private final String  name;
    private final boolean senior; // 30세 이상 여부

    PersonDto(String name, boolean senior) {
      this.name   = name;
      this.senior = senior;
    }

    String  getName()   { return name; }
    boolean isSenior()  { return senior; }
  }
}
