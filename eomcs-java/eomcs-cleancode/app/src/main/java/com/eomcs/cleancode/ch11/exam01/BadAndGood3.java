package com.eomcs.cleancode.ch11.exam01;

// 예제 3: 팩토리 - 언제 만들지는 애플리케이션이, 어떻게 만들지는 팩토리가
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Product {

    private final String id;
    private final String name;
    private final int price;

    Product(String id, String name, int price) {
      this.id = id;
      this.name = name;
      this.price = price;
    }

    String id() { return id; }
    String name() { return name; }
    int price() { return price; }
  }

  static class LineItem {

    private final String productId;
    private final String productName;
    private final int unitPrice;
    private final int quantity;
    private final int totalPrice;

    LineItem(String productId, String productName, int unitPrice, int quantity, int totalPrice) {
      this.productId = productId;
      this.productName = productName;
      this.unitPrice = unitPrice;
      this.quantity = quantity;
      this.totalPrice = totalPrice;
    }

    String productId() { return productId; }
    String productName() { return productName; }
    int unitPrice() { return unitPrice; }
    int quantity() { return quantity; }
    int totalPrice() { return totalPrice; }
  }

  // Bad: Order가 LineItem 생성 세부사항을 모두 알고 있다
  //   - LineItem 생성 규칙이 바뀌면 Order도 수정된다
  //   - 할인, 세금, 옵션 가격이 추가되면 생성 코드가 더 복잡해진다
  static class BadOrder {

    public void addProduct(Product product, int quantity) {
      LineItem item = new LineItem( // 생성 세부사항이 Order 안에 노출됨
          product.id(),
          product.name(),
          product.price(),
          quantity,
          product.price() * quantity
      );
      add(item);
    }

    private void add(LineItem item) {
      System.out.println("add line item: " + item.totalPrice());
    }
  }

  // Good: 생성 세부사항을 팩토리 안으로 숨긴다
  //   - Order는 LineItem을 언제 만들지 결정한다
  //   - LineItemFactory는 LineItem을 어떻게 만들지 결정한다
  //   - 생성 규칙 변경이 Order로 전파되지 않는다
  interface LineItemFactory {
    LineItem create(Product product, int quantity);
  }

  static class DefaultLineItemFactory implements LineItemFactory {

    @Override
    public LineItem create(Product product, int quantity) {
      int totalPrice = product.price() * quantity;

      return new LineItem(
          product.id(),
          product.name(),
          product.price(),
          quantity,
          totalPrice
      );
    }
  }

  static class Order {

    private final LineItemFactory lineItemFactory;

    Order(LineItemFactory lineItemFactory) {
      this.lineItemFactory = lineItemFactory;
    }

    public void addProduct(Product product, int quantity) {
      LineItem item = lineItemFactory.create(product, quantity); // 어떻게 만들지는 모른다
      add(item);
    }

    private void add(LineItem item) {
      System.out.println("add line item: " + item.totalPrice());
    }
  }
}
