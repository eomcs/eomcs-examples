package com.eomcs.advanced.stream.exam08;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// 매핑 (1:N, Java 16+) - mapMulti() 실전 패턴:
//
// mapMulti()가 flatMap()보다 유리한 경우:
//   1. 결과 요소 수가 적을 때 (0개 또는 1개가 많을 때)
//      → flatMap은 매번 Stream 객체를 생성한다. mapMulti는 생성하지 않는다.
//   2. 재귀/반복 로직으로 요소를 생성할 때
//      → Consumer를 반복 호출하면 되므로 루프 구조가 자연스럽다.
//   3. 중첩 구조(트리, 계층) 평탄화
//      → 재귀적으로 Consumer를 호출해 모든 리프 노드를 하나의 스트림으로 수집한다.
//

public class App2 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 중첩 리스트 평탄화 - flatMap vs mapMulti
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 중첩 리스트 평탄화 - flatMap vs mapMulti");

    List<List<Integer>> nested = Arrays.asList(
        Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5),
        Arrays.asList(6, 7, 8, 9)
    );

    // flatMap 방식
    System.out.print("  flatMap:   ");
    nested.stream()
        .flatMap(List::stream)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // mapMulti 방식
    System.out.print("  mapMulti:  ");
    nested.stream()
        .<Integer>mapMulti((list, downstream) ->
            list.forEach(downstream::accept))
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 조건부 타입 변환 - instanceof 패턴 매칭 + mapMulti
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] instanceof 패턴 매칭 + mapMulti - 특정 타입만 추출");

    // Object 리스트에서 String만 추출
    List<Object> mixed = Arrays.asList(1, "hello", 3.14, "world", true, "stream", 42);

    // flatMap 방식 (캐스팅 필요)
    System.out.println("  flatMap 방식:");
    mixed.stream()
        .flatMap(o -> o instanceof String s ? Stream.of(s) : Stream.empty())
        .map(String::toUpperCase)
        .forEach(s -> System.out.print("    " + s + "\n"));

    // mapMulti 방식 (instanceof 패턴 매칭과 자연스럽게 결합)
    System.out.println("  mapMulti 방식:");
    mixed.stream()
        .<String>mapMulti((o, downstream) -> {
          if (o instanceof String s) {
            downstream.accept(s); // String인 경우만 밀어 넣음
          }
          // String이 아니면 아무것도 밀어 넣지 않음 (필터링 효과)
        })
        .map(String::toUpperCase)
        .forEach(s -> System.out.print("    " + s + "\n"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 계층 구조 평탄화 - 재귀적 mapMulti
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 계층 구조 평탄화 - 카테고리 → 상품");

    List<Category> categories = Arrays.asList(
        new Category("전자기기", Arrays.asList("노트북", "마우스", "키보드")),
        new Category("도서",    Arrays.asList("Java 입문", "알고리즘")),
        new Category("가구",    Arrays.asList("의자", "책상", "책장"))
    );

    // 모든 카테고리의 상품을 하나의 스트림으로
    System.out.println("  모든 상품 목록:");
    categories.stream()
        .<String>mapMulti((category, downstream) ->
            category.getItems().forEach(downstream::accept))
        .sorted()
        .forEach(item -> System.out.println("    " + item));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. mapMulti로 반복 확장
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] mapMulti로 요소 N회 반복 확장");

    List<String> items = Arrays.asList("A", "B", "C");
    int repeat = 3;

    // 각 요소를 repeat번 반복 (A A A B B B C C C)
    System.out.print("  각 요소 " + repeat + "회 반복: ");
    items.stream()
        .<String>mapMulti((item, downstream) -> {
          for (int i = 0; i < repeat; i++) {
            downstream.accept(item);
          }
        })
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    System.out.println();
    System.out.println("→ mapMulti()는 instanceof 패턴 매칭과 결합하면 타입 필터링 + 변환을 간결하게 표현한다.");
    System.out.println("→ 중첩 구조 평탄화, 조건부 확장, 반복 생성 등 flatMap보다 루프 구조가 자연스러울 때 유리하다.");
  }

  static class Category {
    private final String       name;
    private final List<String> items;

    Category(String name, List<String> items) {
      this.name  = name;
      this.items = items;
    }

    String       getName()  { return name; }
    List<String> getItems() { return items; }
  }
}
