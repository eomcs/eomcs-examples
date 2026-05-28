package com.eomcs.cleancode.ch09.exam02;

// 예제 2: 깨끗한 테스트 코드 - DiscountService
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Customer {
    private final String email;
    private final String grade;

    Customer(String email, String grade) {
      this.email = email;
      this.grade = grade;
    }

    String getEmail() { return email; }
    String getGrade() { return grade; }
  }

  static class Product {
    private final String name;
    private final int price;

    Product(String name, int price) {
      this.name = name;
      this.price = price;
    }

    String getName() { return name; }
    int getPrice() { return price; }
  }

  static class Order {
    private final Customer customer;
    private final Product product;
    private final int quantity;

    Order(Customer customer, Product product, int quantity) {
      this.customer = customer;
      this.product = product;
      this.quantity = quantity;
    }

    Customer getCustomer() { return customer; }
    Product getProduct() { return product; }
    int getQuantity() { return quantity; }
  }

  // Bad: 여러 등급 조건을 한 테스트에서 검증하던 서비스
  // Good: 각 조건을 별도 테스트로 나누어 검증
  static class DiscountService {
    int discount(Order order) {
      int price = order.getProduct().getPrice() * order.getQuantity();
      if ("VIP".equals(order.getCustomer().getGrade())) {
        return (int) (price * 0.9);
      }
      return price;
    }
  }
}
