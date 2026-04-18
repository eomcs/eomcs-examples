package com.eomcs.advanced.stream.exam04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 슬라이싱 - takeWhile() / dropWhile() + 실전 복합 파이프라인:
//
// takeWhile(Predicate<T>)  (Java 9+)
//   - 조건을 만족하는 동안 요소를 취하고, 처음으로 false가 되는 순간 스트림을 종료한다.
//   - filter()와 달리 이후 요소를 검사하지 않는다. (단락 평가)
//   - 정렬된 스트림에서 특히 효율적이다.
//   - 비정렬 스트림에서는 예상치 못한 결과가 나올 수 있다.
//
// dropWhile(Predicate<T>)  (Java 9+)
//   - 조건을 만족하는 동안 요소를 버리고, 처음으로 false가 되는 순간부터 모두 취한다.
//   - takeWhile()의 반대이다.
//
// takeWhile vs filter (정렬된 데이터 기준):
//   filter:    모든 요소를 검사 → 조건을 만족하는 요소를 모두 반환
//   takeWhile: 조건이 false인 첫 요소에서 즉시 종료 → 이후 요소는 무시
//
// 복합 파이프라인 실전 패턴:
//   filter → sorted → skip → limit: 조건 필터링 후 정렬, 페이지네이션
//   filter → distinct → sorted → limit: 중복 제거 후 상위 N개 추출
//   takeWhile → map → collect: 정렬된 데이터에서 범위 추출 후 변환
//

public class App4 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. takeWhile() - 정렬된 데이터에서 범위 추출
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] takeWhile() - 정렬된 데이터에서 범위 추출");

    List<Integer> sorted = List.of(1, 3, 5, 7, 9, 11, 13, 15);

    // filter: 모든 요소를 검사하며 조건을 만족하는 것만 통과
    List<Integer> filtered = sorted.stream()
        .filter(n -> n < 10)
        .toList();
    System.out.println("  filter(n<10):    " + filtered); // [1, 3, 5, 7, 9]

    // takeWhile: 10 이상이 나오는 순간 종료 (11 이후는 검사하지 않음)
    List<Integer> taken = sorted.stream()
        .takeWhile(n -> n < 10)
        .toList();
    System.out.println("  takeWhile(n<10): " + taken);   // [1, 3, 5, 7, 9]

    // 정렬된 데이터에서는 결과가 같지만, takeWhile은 불필요한 검사를 하지 않는다.
    System.out.println("  → 정렬된 데이터에서는 결과 동일. takeWhile이 더 효율적.");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. takeWhile() - 비정렬 데이터에서의 차이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] takeWhile() vs filter() - 비정렬 데이터");

    List<Integer> unsorted = List.of(2, 4, 1, 6, 3, 8, 5);

    System.out.print("  filter(n<5):    ");
    unsorted.stream().filter(n -> n < 5).forEach(n -> System.out.print(n + " "));
    System.out.println(); // 2 4 1 3 (조건을 만족하는 모든 요소)

    System.out.print("  takeWhile(n<5): ");
    unsorted.stream().takeWhile(n -> n < 5).forEach(n -> System.out.print(n + " "));
    System.out.println(); // 2 4 1  (6에서 즉시 종료 → 3, 8, 5는 검사 안 함)
    // 2(<5) 통과, 4(<5) 통과, 1(<5) 통과, 6(≥5) → 즉시 종료

    System.out.println("  → 비정렬 데이터에서는 takeWhile 결과가 filter와 다를 수 있다.");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. dropWhile() - 조건을 만족하는 동안 버리기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] dropWhile() - 조건이 false가 되는 순간부터 취하기");

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // 5 미만인 동안 버리고, 5 이상이 되는 순간부터 모두 취한다.
    List<Integer> dropped = numbers.stream()
        .dropWhile(n -> n < 5)
        .toList();
    System.out.println("  dropWhile(n<5): " + dropped); // [5, 6, 7, 8, 9, 10]

    // takeWhile + dropWhile: 중간 구간 추출
    // 5 이상 9 이하인 구간 → dropWhile(n<5) 후 takeWhile(n<=9)
    List<Integer> middle = numbers.stream()
        .dropWhile(n -> n < 5)   // 1,2,3,4 버림 → [5, 6, 7, 8, 9, 10]
        .takeWhile(n -> n <= 9)  // 10에서 종료  → [5, 6, 7, 8, 9]
        .toList();
    System.out.println("  5 이상 9 이하:  " + middle); // [5, 6, 7, 8, 9]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 실전 - 점수 기반 등급 필터링 + 페이지네이션
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 실전 - 점수 기반 등급 필터링 + 페이지네이션");

    List<Student> students = Arrays.asList(
        new Student("Alice",   92),
        new Student("Bob",     68),
        new Student("Charlie", 85),
        new Student("Dave",    45),
        new Student("Eve",     77),
        new Student("Frank",   91),
        new Student("Grace",   58),
        new Student("Hank",    88),
        new Student("Ivy",     73),
        new Student("Jack",    95)
    );

    // 60점 이상인 학생을 점수 내림차순으로 정렬 후, 1페이지(0번, size=3) 조회
    int page = 0, size = 3;
    List<Student> result = students.stream()
        .filter(s -> s.getScore() >= 60)                      // 합격 기준 필터
        .sorted(Comparator.comparingInt(Student::getScore)    // 점수 내림차순 정렬
            .reversed())
        .skip((long) page * size)                             // 페이지 오프셋
        .limit(size)                                          // 페이지 크기
        .toList();

    System.out.println("  [합격자 점수 내림차순 - 1페이지]");
    result.forEach(s ->
        System.out.printf("    %s: %d점%n", s.getName(), s.getScore()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 실전 - 중복 제거 + 정렬 + 상위 N개 추출
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] 실전 - 중복 제거 + 정렬 + 상위 N개");

    List<String> tags = Arrays.asList(
        "Java", "Stream", "Java", "Lambda", "Stream", "Optional",
        "Java", "Functional", "Lambda", "Stream"
    );

    // 등장 횟수 기준 상위 3개 태그 추출
    // 1단계: 태그별 등장 횟수 집계
    // 2단계: 빈도 내림차순 정렬
    // 3단계: 상위 3개만 취하기
    List<String> topTags = tags.stream()
        .collect(Collectors.groupingBy(t -> t, Collectors.counting())) // 빈도 집계
        .entrySet().stream()
        .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed()) // 빈도 내림차순
        .limit(3)                                                                // 상위 3개
        .map(java.util.Map.Entry::getKey)
        .toList();

    System.out.println("  상위 3 태그: " + topTags); // [Java, Stream, Lambda]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 실전 - 정렬된 로그에서 오류만 takeWhile로 추출
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 실전 - 정렬된 시간 순 로그에서 특정 시간대 추출");

    List<LogEntry> logs = Arrays.asList(
        new LogEntry("08:01", "INFO",  "서버 시작"),
        new LogEntry("08:15", "INFO",  "요청 처리"),
        new LogEntry("09:02", "WARN",  "메모리 부족"),
        new LogEntry("09:45", "ERROR", "연결 실패"),
        new LogEntry("10:10", "INFO",  "복구 완료"),
        new LogEntry("10:30", "INFO",  "정상 운영"),
        new LogEntry("11:00", "INFO",  "정상 운영")
    );

    // 09:00 ~ 10:00 사이의 로그만 추출 (시간순 정렬되어 있음)
    System.out.println("  [09:00 ~ 10:00 로그]");
    logs.stream()
        .dropWhile(log -> log.getTime().compareTo("09:00") < 0) // 09:00 이전 버림
        .takeWhile(log -> log.getTime().compareTo("10:00") < 0) // 10:00 이상에서 종료
        .forEach(log -> System.out.printf(
            "    [%s] %-5s %s%n", log.getTime(), log.getLevel(), log.getMessage()));

    // ERROR 로그만 필터링
    System.out.println("  [ERROR 로그]");
    logs.stream()
        .filter(log -> "ERROR".equals(log.getLevel()))
        .forEach(log -> System.out.printf(
            "    [%s] %s%n", log.getTime(), log.getMessage()));

    System.out.println();
    System.out.println("→ takeWhile()은 정렬된 데이터에서 범위 추출 시 filter()보다 효율적이다.");
    System.out.println("→ dropWhile() + takeWhile() 조합으로 시작·끝 조건으로 중간 구간을 추출할 수 있다.");
    System.out.println("→ filter → sorted → skip → limit 패턴은 검색 + 정렬 + 페이지네이션의 기본 형태이다.");
  }

  static class Student {
    private final String name;
    private final int    score;

    Student(String name, int score) {
      this.name  = name;
      this.score = score;
    }

    String getName()  { return name; }
    int    getScore() { return score; }
  }

  static class LogEntry {
    private final String time;
    private final String level;
    private final String message;

    LogEntry(String time, String level, String message) {
      this.time    = time;
      this.level   = level;
      this.message = message;
    }

    String getTime()    { return time; }
    String getLevel()   { return level; }
    String getMessage() { return message; }
  }
}
