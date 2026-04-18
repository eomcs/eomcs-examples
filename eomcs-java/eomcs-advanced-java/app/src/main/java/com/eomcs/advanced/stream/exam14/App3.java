package com.eomcs.advanced.stream.exam14;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 수집 기초 - joining():
//
// Collectors.joining()
//   - 스트림의 문자열 요소를 하나의 String으로 연결한다.
//   - 세 가지 형태가 있다.
//
//   joining()                              → 단순 연결 (구분자 없음)
//   joining(CharSequence delimiter)        → 구분자 삽입
//   joining(delimiter, prefix, suffix)     → 구분자 + 접두사 + 접미사
//
// 주로 쓰이는 패턴:
//   CSV: joining(", ")
//   JSON 배열: joining(", ", "[", "]")
//   SQL IN 절: joining(", ", "(", ")")
//

public class App3 {

  public static void main(String[] args) {

    List<String> names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");
    List<String> cities  = Arrays.asList("서울", "부산", "대구", "인천");
    List<Integer> ids    = Arrays.asList(1, 2, 3, 4, 5);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. joining() - 단순 연결
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] joining() - 단순 연결");

    String simple = names.stream().collect(Collectors.joining());
    System.out.println("  joining(): " + simple); // AliceBobCharlieDaveEve

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. joining(delimiter) - 구분자
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] joining(delimiter) - 구분자 삽입");

    String csv   = names.stream().collect(Collectors.joining(", "));
    String pipe  = names.stream().collect(Collectors.joining(" | "));
    String slash = cities.stream().collect(Collectors.joining("/"));

    System.out.println("  CSV:   " + csv);   // Alice, Bob, Charlie, Dave, Eve
    System.out.println("  Pipe:  " + pipe);  // Alice | Bob | Charlie | Dave | Eve
    System.out.println("  Slash: " + slash); // 서울/부산/대구/인천

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. joining(delimiter, prefix, suffix) - 접두사 + 접미사
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] joining(delimiter, prefix, suffix)");

    // JSON 배열 형태
    String jsonArray = names.stream()
        .map(name -> "\"" + name + "\"")
        .collect(Collectors.joining(", ", "[", "]"));
    System.out.println("  JSON 배열: " + jsonArray);
    // ["Alice", "Bob", "Charlie", "Dave", "Eve"]

    // SQL IN 절 형태
    String sqlIn = ids.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(", ", "(", ")"));
    System.out.println("  SQL IN:    " + sqlIn); // (1, 2, 3, 4, 5)

    // 괄호로 감싼 목록
    String bracketed = cities.stream()
        .collect(Collectors.joining(", ", "도시: [", "]"));
    System.out.println("  도시 목록: " + bracketed); // 도시: [서울, 부산, 대구, 인천]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. map + joining - 객체 필드를 추출해 연결
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] map + joining - 객체 필드 추출 후 연결");

    List<App2.Person> people = Arrays.asList(
        new App2.Person(1, "Alice",   28),
        new App2.Person(2, "Bob",     35),
        new App2.Person(3, "Charlie", 22)
    );

    // 이름만 추출하여 연결
    String nameList = people.stream()
        .map(App2.Person::getName)
        .collect(Collectors.joining(", "));
    System.out.println("  이름 목록: " + nameList); // Alice, Bob, Charlie

    // 이름과 나이를 함께 포맷
    String formatted = people.stream()
        .map(p -> p.getName() + "(" + p.getAge() + ")")
        .collect(Collectors.joining(", ", "[", "]"));
    System.out.println("  포맷 목록: " + formatted); // [Alice(28), Bob(35), Charlie(22)]

    System.out.println();
    System.out.println("→ joining()은 Stream<String>에만 적용된다. 숫자는 map(String::valueOf)로 변환 후 사용한다.");
    System.out.println("→ joining(delimiter, prefix, suffix)으로 JSON 배열, SQL IN 절 등을 간결하게 생성한다.");
  }
}
