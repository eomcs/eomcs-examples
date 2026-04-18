package com.eomcs.advanced.stream.exam05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 매핑 - 복합 매핑 실전:
//
// 실전에서 map/flatMap은 단독으로 쓰이기보다
// filter, sorted, collect 등과 조합되어 데이터 변환 파이프라인을 구성한다.
//
// 주요 실전 패턴:
//   1. 객체 → DTO 변환:  entities.stream().map(Entity::toDto).toList()
//   2. 중첩 데이터 평탄화 + 집계:  outer.stream().flatMap(o -> o.getInner().stream()).collect(...)
//   3. 조건 필터 + 변환 + 정렬:  .filter().map().sorted().toList()
//   4. 그룹별 집계 후 재변환:  .collect(groupingBy(...)).entrySet().stream().map(...)
//   5. 인덱스 포함 변환:  IntStream.range(0, list.size()).mapToObj(i -> ...)
//

public class App4 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. Entity → DTO 변환 파이프라인
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Entity → DTO 변환");

    List<OrderEntity> orders = Arrays.asList(
        new OrderEntity(1, "Alice",   15_000, "완료"),
        new OrderEntity(2, "Bob",     32_000, "취소"),
        new OrderEntity(3, "Charlie", 8_500,  "완료"),
        new OrderEntity(4, "Dave",    47_000, "완료"),
        new OrderEntity(5, "Eve",     21_000, "대기")
    );

    // "완료" 상태 주문만 DTO로 변환하여 금액 내림차순 정렬
    List<OrderDto> completedOrders = orders.stream()
        .filter(o -> "완료".equals(o.getStatus()))          // 상태 필터
        .map(o -> new OrderDto(o.getId(), o.getCustomer(),  // Entity → DTO
            String.format("%,d원", o.getAmount())))
        .sorted(Comparator.comparingInt(OrderDto::getId).reversed()) // ID 내림차순
        .toList();

    System.out.println("  완료 주문 (ID 내림차순):");
    completedOrders.forEach(dto ->
        System.out.printf("    [%d] %s - %s%n",
            dto.getId(), dto.getCustomer(), dto.getFormattedAmount()));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 중첩 데이터 평탄화 + 집계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 중첩 데이터 평탄화 + 집계");

    List<Category> categories = Arrays.asList(
        new Category("전자기기", Arrays.asList(
            new Product("노트북",  1_200_000),
            new Product("마우스",    35_000),
            new Product("모니터",   450_000)
        )),
        new Category("도서", Arrays.asList(
            new Product("Java 입문",   28_000),
            new Product("알고리즘",    35_000)
        )),
        new Category("가구", Arrays.asList(
            new Product("의자",    250_000),
            new Product("책상",    380_000),
            new Product("선반",    120_000)
        ))
    );

    // 모든 카테고리의 상품을 평탄화하여 가격 합계
    int totalPrice = categories.stream()
        .flatMap(cat -> cat.getProducts().stream()) // Category → Stream<Product>
        .mapToInt(Product::getPrice)
        .sum();
    System.out.printf("  전체 상품 합계: %,d원%n", totalPrice);

    // 30만원 이상 상품을 카테고리명과 함께 출력
    System.out.println("  30만원 이상 상품:");
    categories.stream()
        .flatMap(cat -> cat.getProducts().stream()
            .filter(p -> p.getPrice() >= 300_000)
            .map(p -> String.format("    [%s] %s: %,d원",
                cat.getName(), p.getName(), p.getPrice())))
        .forEach(System.out::println);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 그룹 집계 후 재변환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 그룹 집계 후 재변환 - 카테고리별 평균 가격");

    categories.stream()
        .collect(Collectors.toMap(               // Map<카테고리명, 평균가격>
            Category::getName,
            cat -> cat.getProducts().stream()
                .mapToInt(Product::getPrice)
                .average()
                .orElse(0)
        ))
        .entrySet().stream()
        .sorted(java.util.Map.Entry.<String, Double>comparingByValue().reversed())
        .map(e -> String.format("  %-8s 평균: %,.0f원", e.getKey(), e.getValue()))
        .forEach(System.out::println);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 인덱스 포함 변환 - IntStream.range + mapToObj
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 인덱스 포함 변환");

    List<String> items = Arrays.asList("사과", "바나나", "포도", "딸기", "블루베리");

    // Stream에는 인덱스가 없다. IntStream.range를 활용해 인덱스를 함께 처리한다.
    System.out.println("  번호 매기기:");
    java.util.stream.IntStream.range(0, items.size())
        .mapToObj(i -> String.format("    %d. %s", i + 1, items.get(i)))
        .forEach(System.out::println);

    // 짝수 인덱스 요소만 추출 (0, 2, 4번째)
    System.out.print("  짝수 인덱스: ");
    java.util.stream.IntStream.range(0, items.size())
        .filter(i -> i % 2 == 0)           // 짝수 인덱스 필터
        .mapToObj(items::get)              // 인덱스 → 요소값
        .forEach(item -> System.out.print(item + " ")); // 사과 포도 블루베리
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 문자열 처리 파이프라인 - CSV 파싱
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] CSV 파싱 - flatMap + map 조합");

    List<String> csvLines = Arrays.asList(
        "Alice,30,서울",
        "Bob,25,부산",
        "Charlie,35,서울",
        "Dave,28,대전",
        "Eve,32,서울"
    );

    // CSV 파싱 → Person 객체로 변환
    List<PersonRecord> people = csvLines.stream()
        .map(line -> line.split(","))                          // String → String[]
        .map(parts -> new PersonRecord(
            parts[0].trim(),
            Integer.parseInt(parts[1].trim()),
            parts[2].trim()))
        .toList();

    // 서울 거주자 이름과 나이
    System.out.println("  서울 거주자:");
    people.stream()
        .filter(p -> "서울".equals(p.city()))
        .sorted(Comparator.comparing(PersonRecord::name))
        .map(p -> String.format("    %s (%d세)", p.name(), p.age()))
        .forEach(System.out::println);

    // 도시별 평균 나이
    System.out.println("  도시별 평균 나이:");
    people.stream()
        .collect(Collectors.groupingBy(PersonRecord::city,
            Collectors.averagingInt(PersonRecord::age)))
        .entrySet().stream()
        .sorted(java.util.Map.Entry.comparingByKey())
        .map(e -> String.format("    %s: %.1f세", e.getKey(), e.getValue()))
        .forEach(System.out::println);

    System.out.println();
    System.out.println("→ 실전에서는 map/flatMap을 filter/sorted/collect와 조합해 데이터 변환 파이프라인을 구성한다.");
    System.out.println("→ 인덱스가 필요하면 IntStream.range()와 mapToObj()를 조합한다.");
    System.out.println("→ flatMap은 중첩 구조(카테고리→상품)를 평탄화하고 집계 연산과 연결할 때 유용하다.");
  }

  // 주문 엔티티
  static class OrderEntity {
    private final int    id;
    private final String customer;
    private final int    amount;
    private final String status;

    OrderEntity(int id, String customer, int amount, String status) {
      this.id       = id;
      this.customer = customer;
      this.amount   = amount;
      this.status   = status;
    }

    int    getId()       { return id; }
    String getCustomer() { return customer; }
    int    getAmount()   { return amount; }
    String getStatus()   { return status; }
  }

  // 주문 DTO
  static class OrderDto {
    private final int    id;
    private final String customer;
    private final String formattedAmount;

    OrderDto(int id, String customer, String formattedAmount) {
      this.id              = id;
      this.customer        = customer;
      this.formattedAmount = formattedAmount;
    }

    int    getId()              { return id; }
    String getCustomer()        { return customer; }
    String getFormattedAmount() { return formattedAmount; }
  }

  // 카테고리
  static class Category {
    private final String        name;
    private final List<Product> products;

    Category(String name, List<Product> products) {
      this.name     = name;
      this.products = products;
    }

    String        getName()     { return name; }
    List<Product> getProducts() { return products; }
  }

  // 상품
  static class Product {
    private final String name;
    private final int    price;

    Product(String name, int price) {
      this.name  = name;
      this.price = price;
    }

    String getName()  { return name; }
    int    getPrice() { return price; }
  }

  // 간단한 Person 레코드 (Java 16+ record 사용)
  record PersonRecord(String name, int age, String city) {}
}
