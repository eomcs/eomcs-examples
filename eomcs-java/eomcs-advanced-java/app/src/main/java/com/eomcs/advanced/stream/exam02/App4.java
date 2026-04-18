package com.eomcs.advanced.stream.exam02;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// Optional과 Stream의 통합:
//
// Optional → Stream 변환 (Java 9+):
//   optional.stream()
//     - 값이 있으면 1개짜리 Stream<T>를 반환한다.
//     - 값이 없으면 빈 Stream을 반환한다.
//     - Optional 리스트에서 빈 값을 제거하고 값만 추출할 때 flatMap과 함께 사용한다.
//
// Stream → Optional 변환:
//   stream.findFirst()  - 첫 번째 요소를 Optional로 반환
//   stream.findAny()    - 임의 요소를 Optional로 반환 (병렬 스트림에서 유용)
//   stream.max(comp)    - 최댓값을 Optional로 반환
//   stream.min(comp)    - 최솟값을 Optional로 반환
//   stream.reduce(op)   - 항등원 없는 reduce는 Optional로 반환
//
// Optional을 사용한 Map 조회:
//   Map.get()은 키가 없으면 null을 반환한다.
//   Optional.ofNullable(map.get(key))로 감싸면 null 체크 없이 처리할 수 있다.
//

public class App4 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. Optional 리스트에서 빈 값 제거 후 값만 추출
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Optional 리스트에서 값만 추출");

    List<Optional<String>> optionals = Arrays.asList(
        Optional.of("사과"),
        Optional.empty(),
        Optional.of("바나나"),
        Optional.empty(),
        Optional.of("포도")
    );

    // 기존 방식: isPresent()로 필터링한 뒤 get()으로 꺼낸다.
    List<String> oldWay = optionals.stream()
        .filter(Optional::isPresent)  // 빈 Optional 제거
        .map(Optional::get)           // 값 꺼내기
        .collect(Collectors.toList());
    System.out.println("  기존 방식: " + oldWay);

    // Optional.stream() 방식 (Java 9+):
    //   값이 있으면 1개짜리 Stream, 없으면 빈 Stream으로 변환한 뒤 flatMap으로 평탄화한다.
    List<String> newWay = optionals.stream()
        .flatMap(Optional::stream)    // Optional<String> → Stream<String> (없으면 빈 스트림)
        .collect(Collectors.toList());
    System.out.println("  stream() 방식: " + newWay);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Stream → Optional: reduce (항등원 없는 버전)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] reduce - 항등원 없는 버전은 Optional 반환");

    List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4);
    List<Integer> empty   = Arrays.asList();

    // reduce(BinaryOperator): 초기값 없이 누적한다.
    //   - 스트림이 비어 있으면 결과가 없을 수 있으므로 Optional<T>를 반환한다.
    //   - Integer::max는 int 파라미터를 받으므로 언박싱 null 경고가 발생한다.
    //     람다 (a, b) -> a >= b ? a : b 로 대신 표현한다.
    Optional<Integer> max = numbers.stream().reduce((a, b) -> a >= b ? a : b); // 최댓값 누적
    System.out.println("  숫자 리스트 max: " + max.orElse(-1));   // 9

    Optional<Integer> emptyMax = empty.stream().reduce((a, b) -> a >= b ? a : b); // 빈 스트림
    System.out.println("  빈 리스트 max:   " + emptyMax.orElse(-1)); // -1 (기본값)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. Map 조회에 Optional 적용
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Map 조회 - Optional로 null 반환 처리");

    Map<String, String> capitals = Map.of(
        "한국", "서울",
        "일본", "도쿄",
        "미국", "워싱턴 D.C."
    );

    // null 반환 방식: get()이 null을 반환할 수 있어 null 체크가 필요하다.
    String capital = capitals.get("한국");
    System.out.println("  null 방식 (한국): " + (capital != null ? capital : "알 수 없음"));

    String missing = capitals.get("독일");
    System.out.println("  null 방식 (독일): " + (missing != null ? missing : "알 수 없음"));

    // Optional 방식: ofNullable로 감싸면 null 체크 없이 체인으로 처리한다.
    String opt1 = Optional.ofNullable(capitals.get("일본")).orElse("알 수 없음");
    System.out.println("  Optional (일본):  " + opt1);

    String opt2 = Optional.ofNullable(capitals.get("독일")).orElse("알 수 없음");
    System.out.println("  Optional (독일):  " + opt2);

    // Map.getOrDefault()와의 비교:
    //   getOrDefault는 단순 기본값 반환에 편리하다.
    //   Optional은 map/filter 등 추가 변환이 필요할 때 더 유연하다.
    String withGetOrDefault = capitals.getOrDefault("독일", "알 수 없음");
    System.out.println("  getOrDefault:     " + withGetOrDefault);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Optional을 포함한 객체 리스트에서 조건 집계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Optional 필드를 가진 객체 리스트 처리");

    List<Product> products = Arrays.asList(
        new Product("노트북",   Optional.of(1_200_000)),
        new Product("마우스",   Optional.of(35_000)),
        new Product("모니터",   Optional.empty()),         // 가격 미정
        new Product("키보드",   Optional.of(85_000)),
        new Product("웹캠",     Optional.empty())          // 가격 미정
    );

    // 가격이 있는 상품만 모아 총합 계산
    int total = products.stream()
        .flatMap(p -> p.getPrice().stream())   // Optional<Integer> → 값이 있는 것만 Stream으로
        .mapToInt(Integer::intValue)
        .sum();
    System.out.println("  가격 있는 상품 총합: " + String.format("%,d", total) + "원");

    // 가격이 없는 상품 이름 목록
    List<String> noPriceNames = products.stream()
        .filter(p -> p.getPrice().isEmpty())   // 가격 없는 상품 필터
        .map(Product::getName)
        .collect(Collectors.toList());
    System.out.println("  가격 미정 상품: " + noPriceNames);

    // 가장 비싼 상품 이름 (가격이 있는 것 중)
    Optional<String> mostExpensive = products.stream()
        .filter(p -> p.getPrice().isPresent())
        .max((a, b) -> a.getPrice().get() - b.getPrice().get())
        .map(Product::getName);
    System.out.println("  가장 비싼 상품: " + mostExpensive.orElse("없음"));

    System.out.println();
    System.out.println("→ Optional.stream()으로 Optional 리스트를 flatMap과 연결해 빈 값을 자동 제거할 수 있다.");
    System.out.println("→ reduce(BinaryOperator)는 빈 스트림을 안전하게 처리하기 위해 Optional을 반환한다.");
    System.out.println("→ Map.get()은 Optional.ofNullable()로 감싸면 null 체크 없이 체인으로 처리할 수 있다.");
  }

  static class Product {
    private final String name;
    private final Optional<Integer> price;

    Product(String name, Optional<Integer> price) {
      this.name  = name;
      this.price = price;
    }

    String getName() { return name; }
    Optional<Integer> getPrice() { return price; }
  }
}
