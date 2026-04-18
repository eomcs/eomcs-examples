package com.eomcs.advanced.stream.exam06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 정렬 - 실전 패턴:
//
// 실전에서 자주 쓰이는 정렬 패턴:
//   1. 기본형 필드 정렬: comparingInt / comparingLong / comparingDouble
//   2. null 안전 정렬:   Comparator.nullsFirst / nullsLast
//   3. 대소문자 무시 정렬: String.CASE_INSENSITIVE_ORDER
//   4. 정렬 + 수집:      sorted → toList (정렬된 결과 리스트)
//   5. 정렬 + 페이징:    sorted → skip → limit
//

public class App3 {

  public static void main(String[] args) {

    List<Product> products =
        Arrays.asList(
            new Product(1, "노트북", 1_200_000, 4.5),
            new Product(2, "마우스", 35_000, 4.8),
            new Product(3, "모니터", 450_000, 4.2),
            new Product(4, "키보드", 85_000, 4.7),
            new Product(5, "헤드셋", 120_000, 4.8),
            new Product(6, "웹캠", 65_000, 4.0),
            new Product(7, "USB 허브", 28_000, 4.3),
            new Product(8, "외장 SSD", 180_000, 4.6));

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 가격 오름차순 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 가격 오름차순 정렬");

    products.stream()
        .sorted(Comparator.comparingInt(Product::getPrice))
        .forEach(p -> System.out.printf("  %-12s %,7d원%n", p.getName(), p.getPrice()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 평점 내림차순 → 같으면 가격 오름차순
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 평점 내림차순 → 가격 오름차순 (2차 정렬)");

    products.stream()
        .sorted(
            Comparator.comparingDouble(Product::getRating)
                .reversed()
                .thenComparingInt(Product::getPrice))
        .forEach(
            p ->
                System.out.printf(
                    "  %-12s 평점 %.1f  %,7d원%n", p.getName(), p.getRating(), p.getPrice()));
    // 마우스(4.8, 35000)와 헤드셋(4.8, 120000) → 평점 같으면 가격 오름차순

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 정렬 + 페이징 (sorted → skip → limit)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 정렬 + 페이징 - 가격 오름차순 2페이지(페이지당 3개)");

    int pageSize = 3;
    int page = 1; // 0-based → 1페이지

    products.stream()
        .sorted(Comparator.comparingInt(Product::getPrice))
        .skip((long) page * pageSize) // 3개 건너뜀
        .limit(pageSize) // 3개 가져옴
        .forEach(p -> System.out.printf("  %-12s %,7d원%n", p.getName(), p.getPrice()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 정렬 + 수집 (sorted → toList)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 정렬 후 List로 수집 - 10만원 미만 상품을 가격 오름차순");

    List<Product> affordable =
        products.stream()
            .filter(p -> p.getPrice() < 100_000)
            .sorted(Comparator.comparingInt(Product::getPrice))
            .toList();

    affordable.forEach(
        p ->
            System.out.printf(
                "  %-12s %,7d원 (평점 %.1f)%n", p.getName(), p.getPrice(), p.getRating()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. null 안전 정렬 - nullsFirst / nullsLast
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] nullsFirst / nullsLast - null 안전 정렬");

    List<String> withNulls = Arrays.asList("Banana", null, "Apple", null, "Cherry");

    System.out.print("  nullsFirst:  ");
    withNulls.stream()
        .sorted(Comparator.nullsFirst(Comparator.naturalOrder())) // null → 맨 앞
        .forEach(s -> System.out.print((s == null ? "null" : s) + " "));
    System.out.println();

    System.out.print("  nullsLast:   ");
    withNulls.stream()
        .sorted(Comparator.nullsLast(Comparator.naturalOrder())) // null → 맨 뒤
        .forEach(s -> System.out.print((s == null ? "null" : s) + " "));
    System.out.println();

    System.out.println();
    System.out.println(
        "→ sorted(Comparator.comparingInt(...).reversed().thenComparingInt(...))으로 다중 정렬을 표현한다.");
    System.out.println("→ sorted → skip → limit 패턴으로 정렬된 페이징을 구현한다.");
    System.out.println("→ null이 포함된 경우 nullsFirst / nullsLast로 NullPointerException을 방지한다.");
  }

  static class Product {
    private final int id;
    private final String name;
    private final int price;
    private final double rating;

    Product(int id, String name, int price, double rating) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.rating = rating;
    }

    int getId() {
      return id;
    }

    String getName() {
      return name;
    }

    int getPrice() {
      return price;
    }

    double getRating() {
      return rating;
    }
  }
}
