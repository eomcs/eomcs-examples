package com.eomcs.advanced.stream.exam15;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 수집 고급 - downstream Collector / teeing():
//
// downstream Collector:
//   - groupingBy / partitioningBy의 두 번째 인자로 전달되는 Collector이다.
//   - 각 그룹의 요소에 추가 집계를 수행한다.
//   - 주요 downstream Collectors:
//       counting()          그룹 요소 수
//       summingInt(f)       그룹 요소의 int 합계
//       averagingInt(f)     그룹 요소의 int 평균
//       minBy(Comparator)   그룹 최솟값 Optional<T>
//       maxBy(Comparator)   그룹 최댓값 Optional<T>
//       mapping(f, dc)      그룹 요소를 변환 후 downstream Collector 적용
//       toList()            그룹 요소를 List로 수집
//       joining(delimiter)  그룹 요소(String)를 연결
//
// Collectors.teeing(downstream1, downstream2, merger) (Java 12+)
//   - 동일한 스트림을 두 Collector로 동시에 처리한 뒤 결과를 합친다.
//   - merger: (R1, R2) → R (두 결과를 합치는 함수)
//   - 스트림을 두 번 순회하지 않고 두 가지 집계를 한 번에 처리한다.
//

public class App3 {

  public static void main(String[] args) {

    List<App.Person> people =
        Arrays.asList(
            new App.Person("Alice", "서울", 28, "개발"),
            new App.Person("Bob", "부산", 35, "기획"),
            new App.Person("Charlie", "서울", 22, "개발"),
            new App.Person("Dave", "대구", 41, "개발"),
            new App.Person("Eve", "서울", 28, "기획"),
            new App.Person("Frank", "부산", 31, "개발"),
            new App.Person("Grace", "대구", 25, "기획"));

    List<Integer> scores = Arrays.asList(85, 92, 78, 95, 88, 70, 99, 83, 91, 76);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. downstream - summingInt / averagingInt
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] groupingBy + summingInt / averagingInt");

    // 도시별 나이 합계
    Map<String, Integer> ageSumByCity =
        people.stream()
            .collect(
                Collectors.groupingBy(
                    App.Person::getCity, Collectors.summingInt(App.Person::getAge)));
    System.out.println("  도시별 나이 합계: " + ageSumByCity);

    // 부서별 평균 나이
    Map<String, Double> avgAgeByDept =
        people.stream()
            .collect(
                Collectors.groupingBy(
                    App.Person::getDept, Collectors.averagingInt(App.Person::getAge)));
    avgAgeByDept.forEach((dept, avg) -> System.out.printf("  %-4s 평균 나이: %.1f세%n", dept, avg));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. downstream - minBy / maxBy
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] groupingBy + minBy / maxBy - 그룹별 최솟값/최댓값");

    // 도시별 가장 나이 어린 사람
    Map<String, java.util.Optional<App.Person>> youngestByCity =
        people.stream()
            .collect(
                Collectors.groupingBy(
                    App.Person::getCity,
                    Collectors.minBy(java.util.Comparator.comparingInt(App.Person::getAge))));
    youngestByCity.forEach(
        (city, person) ->
            person.ifPresent(
                p -> System.out.printf("  %-6s 최연소: %s (%d세)%n", city, p.getName(), p.getAge())));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. teeing() - 두 Collector 동시 처리 (Java 12+)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] teeing() - 두 Collector 동시 처리 (Java 12+)");

    // 스트림 한 번 순회로 최솟값과 최댓값을 동시에 구함
    int[] minMax =
        scores.stream()
            .collect(
                Collectors.teeing(
                    Collectors.minBy(Integer::compareTo), // Collector 1: 최솟값
                    Collectors.maxBy(Integer::compareTo), // Collector 2: 최댓값
                    (min, max) ->
                        new int[] { // merger: 두 결과를 합침
                          min.orElse(0), max.orElse(0)
                        }));
    System.out.println("  최솟값: " + minMax[0]); // 70
    System.out.println("  최댓값: " + minMax[1]); // 99

    System.out.println();

    // 평균과 합계를 동시에
    double[] sumAndAvg =
        scores.stream()
            .mapToInt(Integer::intValue) // Stream<Integer> → IntStream  (교육용 설명을 위함)
            .boxed() // IntStream → Stream<Integer> (교육용 설명을 위함) (Collectors.teeing은 객체 스트림 필요)
            .collect(
                Collectors.teeing(
                    Collectors.summingInt(Integer::intValue), // Collector 1: 합계
                    Collectors.averagingInt(Integer::intValue), // Collector 2: 평균
                    (sum, avg) -> new double[] {sum, avg}));
    System.out.printf("  합계: %.0f, 평균: %.2f%n", sumAndAvg[0], sumAndAvg[1]);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. teeing() - 합격/불합격 통계 동시 처리
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] teeing() - 합격(80점 이상)/불합격 동시 집계");

    record Stats(List<Integer> passed, Long failedCount) {}

    Stats stats =
        scores.stream()
            .collect(
                Collectors.teeing(
                    Collectors.filtering(s -> s >= 80, Collectors.toList()), // 합격자 목록
                    Collectors.filtering(s -> s < 80, Collectors.counting()), // 불합격자 수
                    Stats::new));
    System.out.println("  합격자 점수: " + stats.passed());
    System.out.println("  불합격자 수: " + stats.failedCount() + "명");

    System.out.println();
    System.out.println(
        "→ downstream Collector로 각 그룹에 counting/summing/averaging/minBy/maxBy를 적용한다.");
    System.out.println("→ teeing()은 하나의 스트림을 두 Collector로 동시 처리해 두 결과를 합친다 (Java 12+).");
  }
}
