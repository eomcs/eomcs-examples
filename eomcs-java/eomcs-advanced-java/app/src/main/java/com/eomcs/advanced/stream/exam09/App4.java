package com.eomcs.advanced.stream.exam09;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// 검색과 매칭 - 복합 예제:
//
// 이 파일은 match/find 연산을 실전 도메인 객체에 적용한 복합 예제이다.
// 주문(Order), 상품(Product), 재고(Inventory) 같은 현실적인 시나리오에서
// 검색과 매칭이 어떻게 조합되는지 보여준다.
//
// 핵심 활용 전략:
//   - "~가 있는가?" → anyMatch
//   - "~를 모두 충족하는가?" → allMatch
//   - "~가 하나도 없는가?" → noneMatch
//   - "~를 만족하는 첫 번째는?" → filter + findFirst
//   - "정렬 후 첫 번째(최솟값/최댓값)?" → sorted + findFirst
//   - "결과가 없어도 괜찮다" → orElse / ifPresent
//   - "결과가 반드시 있어야 한다" → orElseThrow
//

public class App4 {

  public static void main(String[] args) {

    List<Product> products = Arrays.asList(
        new Product(1, "노트북",   "전자기기",  1_200_000, 5),
        new Product(2, "마우스",   "전자기기",    35_000, 0),  // 재고 없음
        new Product(3, "모니터",   "전자기기",   450_000, 3),
        new Product(4, "Java 입문", "도서",      28_000, 12),
        new Product(5, "알고리즘",  "도서",      35_000, 8),
        new Product(6, "의자",     "가구",      250_000, 2),
        new Product(7, "책상",     "가구",      380_000, 0),   // 재고 없음
        new Product(8, "키보드",   "전자기기",    85_000, 6)
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 재고/상태 확인 - anyMatch / allMatch / noneMatch
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 재고/상태 확인");

    // 재고가 있는 상품이 하나라도 있는가?
    boolean hasStock = products.stream()
        .anyMatch(p -> p.getStock() > 0);
    System.out.println("  재고 있는 상품 존재:         " + hasStock); // true

    // 모든 전자기기 상품이 10만원 이하인가?
    boolean allElecCheap = products.stream()
        .filter(p -> "전자기기".equals(p.getCategory()))
        .allMatch(p -> p.getPrice() <= 100_000);
    System.out.println("  전자기기 모두 10만원 이하:   " + allElecCheap); // false (노트북, 모니터)

    // 도서 카테고리에 재고 없는 상품이 없는가?
    boolean noOutOfStockBook = products.stream()
        .filter(p -> "도서".equals(p.getCategory()))
        .noneMatch(p -> p.getStock() == 0);
    System.out.println("  도서 품절 없음:              " + noOutOfStockBook); // true

    // 품절 상품이 있는가?
    boolean hasOutOfStock = products.stream()
        .anyMatch(p -> p.getStock() == 0);
    System.out.println("  품절 상품 존재:              " + hasOutOfStock); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 상품 검색 - filter + findFirst
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 상품 검색 - filter + findFirst");

    // ID로 단일 상품 검색 (보통 Repository.findById 패턴)
    Optional<Product> byId = products.stream()
        .filter(p -> p.getId() == 3)
        .findFirst();
    byId.ifPresent(p ->
        System.out.printf("  ID=3: %s / %,d원%n", p.getName(), p.getPrice())); // 모니터

    // 카테고리에서 재고가 있는 가장 저렴한 상품
    Optional<Product> cheapestElec = products.stream()
        .filter(p -> "전자기기".equals(p.getCategory()))
        .filter(p -> p.getStock() > 0)                          // 재고 있는 것만
        .min(Comparator.comparingInt(Product::getPrice));       // 가격 오름차순 최솟값
    cheapestElec.ifPresent(p ->
        System.out.printf("  전자기기 재고 중 최저가: %s (%,d원)%n",
            p.getName(), p.getPrice())); // 마우스는 재고 없음 → 키보드

    // 이름에 키워드 포함 검색
    String keyword = "Java";
    Optional<Product> byKeyword = products.stream()
        .filter(p -> p.getName().contains(keyword))
        .findFirst();
    byKeyword.ifPresentOrElse(
        p -> System.out.printf("  '%s' 검색 결과: %s%n", keyword, p.getName()),
        ()  -> System.out.printf("  '%s' 검색 결과 없음%n", keyword)
    );

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 가격 범위 검색
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 가격 범위 검색");

    int minPrice = 30_000;
    int maxPrice = 100_000;

    // 해당 가격 범위에 상품이 있는가?
    boolean hasPriceRange = products.stream()
        .anyMatch(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice);
    System.out.printf("  %,d~%,d원 범위 상품 존재: %s%n",
        minPrice, maxPrice, hasPriceRange); // true

    // 해당 가격 범위에서 재고 있는 첫 번째 상품
    Optional<Product> firstInRange = products.stream()
        .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
        .filter(p -> p.getStock() > 0)
        .findFirst();
    firstInRange.ifPresent(p ->
        System.out.printf("  %,d~%,d원 재고 첫 번째: %s%n",
            minPrice, maxPrice, p.getName())); // 알고리즘 (35,000원, 재고 8)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 주문 가능 여부 검증 - allMatch
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 주문 가능 여부 검증");

    // 장바구니 아이템 (상품ID, 주문수량)
    List<CartItem> cart = Arrays.asList(
        new CartItem(1, 2),  // 노트북 2개 (재고 5개 → OK)
        new CartItem(4, 5),  // Java 입문 5개 (재고 12개 → OK)
        new CartItem(6, 1)   // 의자 1개 (재고 2개 → OK)
    );

    // 장바구니의 모든 상품이 주문 가능한가?
    boolean canOrder = cart.stream()
        .allMatch(item -> {
          Optional<Product> product = products.stream()
              .filter(p -> p.getId() == item.getProductId())
              .findFirst();
          // 상품이 존재하고, 재고가 주문 수량 이상이어야 한다.
          return product.map(p -> p.getStock() >= item.getQty()).orElse(false);
        });
    System.out.println("  장바구니 주문 가능:  " + canOrder); // true

    // 재고 부족 상품이 있는 장바구니
    List<CartItem> cart2 = Arrays.asList(
        new CartItem(2, 1),  // 마우스 1개 (재고 0개 → 불가!)
        new CartItem(4, 3)   // Java 입문 3개 (재고 12개 → OK)
    );

    boolean canOrder2 = cart2.stream()
        .allMatch(item -> products.stream()
            .filter(p -> p.getId() == item.getProductId())
            .findFirst()
            .map(p -> p.getStock() >= item.getQty())
            .orElse(false));
    System.out.println("  장바구니2 주문 가능: " + canOrder2); // false (마우스 재고 없음)

    // 재고 부족 상품 목록 출력
    System.out.println("  재고 부족 상품:");
    cart2.stream()
        .filter(item -> products.stream()
            .filter(p -> p.getId() == item.getProductId())
            .findFirst()
            .map(p -> p.getStock() < item.getQty())
            .orElse(true))
        .forEach(item -> {
          String name = products.stream()
              .filter(p -> p.getId() == item.getProductId())
              .findFirst()
              .map(Product::getName)
              .orElse("알 수 없음");
          System.out.printf("    %s (주문 %d개, 재고 %d개)%n",
              name, item.getQty(),
              products.stream()
                  .filter(p -> p.getId() == item.getProductId())
                  .findFirst()
                  .map(Product::getStock)
                  .orElse(0));
        });

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 추천 상품 검색 - 조건 조합
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] 추천 상품 검색 - 조건 조합");

    // 같은 카테고리에서 재고 있는 상품 중 가장 싼 것을 추천
    String targetCategory = "전자기기";

    Optional<Product> recommended = products.stream()
        .filter(p -> targetCategory.equals(p.getCategory()))
        .filter(p -> p.getStock() > 0)
        .min(Comparator.comparingInt(Product::getPrice));

    System.out.print("  " + targetCategory + " 추천 (재고 있는 최저가): ");
    recommended.ifPresentOrElse(
        p -> System.out.printf("%s (%,d원, 재고 %d개)%n",
            p.getName(), p.getPrice(), p.getStock()),
        () -> System.out.println("추천 상품 없음")
    );

    System.out.println();
    System.out.println("→ 존재 여부 확인 → anyMatch, 전체 유효성 → allMatch, 금지 조건 → noneMatch");
    System.out.println("→ 요소 검색 → filter + findFirst (Optional 반환)");
    System.out.println("→ min/max는 Comparator를 받는 최솟값/최댓값 검색 메서드다. Optional로 반환한다.");
  }

  static class Product {
    private final int    id;
    private final String name;
    private final String category;
    private final int    price;
    private final int    stock;

    Product(int id, String name, String category, int price, int stock) {
      this.id       = id;
      this.name     = name;
      this.category = category;
      this.price    = price;
      this.stock    = stock;
    }

    int    getId()       { return id; }
    String getName()     { return name; }
    String getCategory() { return category; }
    int    getPrice()    { return price; }
    int    getStock()    { return stock; }
  }

  static class CartItem {
    private final int productId;
    private final int qty;

    CartItem(int productId, int qty) {
      this.productId = productId;
      this.qty       = qty;
    }

    int getProductId() { return productId; }
    int getQty()       { return qty; }
  }
}
