package com.eomcs.advanced.stream.exam04;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// 필터링 - distinct() 중복 제거:
//
// distinct()
//   - 스트림에서 중복 요소를 제거하는 중간 연산이다.
//   - 내부적으로 equals()와 hashCode()를 사용해 동등성을 판단한다.
//   - 원본의 상대적 순서를 유지한다. (처음 등장한 요소를 남긴다)
//   - 무한 스트림이나 정렬되지 않은 병렬 스트림에서는 상태를 유지해야 하므로 비용이 클 수 있다.
//
// distinct() vs Set:
//   - Set은 삽입 순서를 보장하지 않는다. (LinkedHashSet은 보장)
//   - distinct()는 스트림 파이프라인 안에서 순서를 유지하며 중복을 제거한다.
//   - Set은 중복 제거가 목적이라면 더 직관적이지만, 파이프라인 중간에 사용하기 불편하다.
//
// 주의:
//   - 커스텀 객체에 distinct()를 사용하려면 equals()와 hashCode()를 반드시 재정의해야 한다.
//   - 재정의하지 않으면 객체 참조(주소)로 비교하므로 중복 제거가 제대로 되지 않는다.
//

public class App2 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 기본 distinct() - 기본형 래퍼 타입
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 기본 distinct()");

    List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);

    // 중복 제거
    System.out.print("  원본:      ");
    numbers.forEach(n -> System.out.print(n + " ")); // 3 1 4 1 5 9 2 6 5 3 5
    System.out.println();

    System.out.print("  distinct:  ");
    numbers.stream()
        .distinct()
        .forEach(n -> System.out.print(n + " ")); // 3 1 4 5 9 2 6  (처음 등장 순서 유지)
    System.out.println();

    // distinct + sorted
    System.out.print("  distinct + sorted: ");
    numbers.stream()
        .distinct()
        .sorted()
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5 6 9
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. distinct() vs Set - 순서 보장 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] distinct() vs Set - 순서 보장 차이");

    List<String> fruits = Arrays.asList("바나나", "사과", "바나나", "포도", "사과", "딸기");

    // Set: 순서 보장 없음 (HashSet)
    System.out.println("  HashSet:      " + java.util.Set.copyOf(fruits)); // 순서 불확정

    // distinct(): 처음 등장한 순서 유지
    System.out.print("  distinct:     ");
    fruits.stream()
        .distinct()
        .forEach(f -> System.out.print(f + " ")); // 바나나 사과 포도 딸기
    System.out.println();

    // LinkedHashSet: 삽입 순서 유지 (distinct와 동일한 결과이지만 스트림 밖에서 처리)
    System.out.println("  LinkedHashSet: "
        + fruits.stream().collect(Collectors.toCollection(java.util.LinkedHashSet::new)));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. filter + distinct - 필터링 후 중복 제거
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] filter + distinct 조합");

    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 4, 6, 8, 10);

    // 짝수만 골라 중복 제거 후 정렬
    System.out.print("  짝수 unique: ");
    data.stream()
        .filter(n -> n % 2 == 0) // 짝수만
        .distinct()               // 중복 제거
        .sorted()                 // 정렬
        .forEach(n -> System.out.print(n + " ")); // 2 4 6 8 10
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 커스텀 객체 distinct() - equals/hashCode 재정의 필요
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 커스텀 객체 distinct()");

    List<Product> products = Arrays.asList(
        new Product("노트북",   "전자기기"),
        new Product("마우스",   "전자기기"),
        new Product("노트북",   "전자기기"), // 중복
        new Product("책상",     "가구"),
        new Product("의자",     "가구"),
        new Product("마우스",   "전자기기")  // 중복
    );

    System.out.println("  [equals/hashCode 재정의한 Product]");
    System.out.print("  distinct: ");
    products.stream()
        .distinct()               // equals()/hashCode() 기준으로 중복 제거
        .forEach(p -> System.out.print(p.getName() + " ")); // 노트북 마우스 책상 의자
    System.out.println();

    // equals/hashCode가 없으면 참조 비교 → 중복 제거 안 됨
    List<ProductNoEquals> rawProducts = Arrays.asList(
        new ProductNoEquals("노트북"),
        new ProductNoEquals("마우스"),
        new ProductNoEquals("노트북")  // 중복인데 제거 안 됨
    );

    System.out.println("  [equals/hashCode 미재정의]");
    System.out.print("  distinct: ");
    rawProducts.stream()
        .distinct()               // 참조(주소)로 비교 → 중복 미제거
        .forEach(p -> System.out.print(p.getName() + " ")); // 노트북 마우스 노트북 (3개 모두 남음)
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 특정 필드 기준으로 중복 제거 (map + distinct + map)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] 특정 필드 기준 중복 제거");

    // 카테고리 기준 중복 제거 (어떤 카테고리가 있는지 파악)
    System.out.print("  카테고리 목록: ");
    products.stream()
        .map(Product::getCategory)  // 카테고리 필드만 추출
        .distinct()                 // 중복 제거
        .sorted()
        .forEach(c -> System.out.print(c + " ")); // 가구 전자기기
    System.out.println();

    // null 요소가 있는 경우: filter(Objects::nonNull) 후 distinct
    List<String> withNull = Arrays.asList("A", null, "B", null, "A", "C");
    System.out.print("  null 포함 distinct: ");
    withNull.stream()
        .filter(Objects::nonNull) // null 먼저 제거 (distinct는 null도 하나만 남김)
        .distinct()
        .sorted()
        .forEach(s -> System.out.print(s + " ")); // A B C
    System.out.println();

    System.out.println();
    System.out.println("→ distinct()는 equals()/hashCode() 기준으로 중복을 제거하고 처음 등장 순서를 유지한다.");
    System.out.println("→ 커스텀 객체에 distinct()를 사용하려면 equals()/hashCode()를 반드시 재정의해야 한다.");
    System.out.println("→ 특정 필드 기준 중복 제거: map()으로 해당 필드를 추출한 뒤 distinct()를 적용한다.");
  }

  // equals/hashCode 재정의한 클래스
  static class Product {
    private final String name;
    private final String category;

    Product(String name, String category) {
      this.name     = name;
      this.category = category;
    }

    String getName()     { return name; }
    String getCategory() { return category; }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Product p)) return false;
      return Objects.equals(name, p.name) && Objects.equals(category, p.category);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, category);
    }
  }

  // equals/hashCode 재정의하지 않은 클래스 (참조 비교)
  static class ProductNoEquals {
    private final String name;
    ProductNoEquals(String name) { this.name = name; }
    String getName() { return name; }
    // equals/hashCode 없음 → Object 기본 구현(참조 비교) 사용
  }
}
