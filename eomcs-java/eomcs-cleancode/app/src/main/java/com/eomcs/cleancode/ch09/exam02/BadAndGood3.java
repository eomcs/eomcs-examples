package com.eomcs.cleancode.ch09.exam02;

// 예제 3: Tests Enable the -ilities - PriceCalculator
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Customer {
    private final String email;
    private final String grade;

    Customer(String email, String grade) {
      this.email = email;
      this.grade = grade;
    }

    String getEmail() { return email; }
    String getGrade() { return grade; }
    boolean isVip() { return "VIP".equals(grade); }
  }

  static class Product {
    private final int price;

    Product(int price) {
      this.price = price;
    }

    int getPrice() { return price; }
  }

  static class Coupon {
    private final int amount;

    Coupon(int amount) {
      this.amount = amount;
    }

    int getAmount() { return amount; }
  }

  static class Order {
    private final Customer customer;
    private final Product product;
    private final int quantity;
    private final Coupon coupon;

    Order(Customer customer, Product product, int quantity, Coupon coupon) {
      this.customer = customer;
      this.product = product;
      this.quantity = quantity;
      this.coupon = coupon;
    }

    Customer getCustomer() { return customer; }
    Product getProduct() { return product; }
    int getQuantity() { return quantity; }
    Coupon getCoupon() { return coupon; }
    boolean hasCoupon() { return coupon != null; }
  }

  // Bad: 테스트가 부족해 리팩터링하기 어려운 프로덕션 코드.
  // - 할인, 쿠폰, 음수 방지 로직이 한 메서드에 모두 인라인되어 있다.
  // - 테스트 없이 이 로직을 변경하면 어디서 버그가 생겼는지 알기 어렵다.
  static class BadPriceCalculator {
    int calculate(Order order) {
      int price = order.getProduct().getPrice() * order.getQuantity();

      if (order.getCustomer().getGrade().equals("VIP")) {
        price = (int) (price * 0.9);
      }

      if (order.getCoupon() != null) {
        price -= order.getCoupon().getAmount();
      }

      if (price < 0) {
        price = 0;
      }

      return price;
    }
  }

  // Good: 테스트가 안전망이 되어 리팩터링한 프로덕션 코드.
  // - 각 계산 규칙이 별도 메서드로 분리되어 읽기 쉽다.
  // - 테스트가 있으므로 각 메서드를 안전하게 변경할 수 있다.
  static class GoodPriceCalculator {
    int calculate(Order order) {
      int price = basePrice(order);
      price = applyGradeDiscount(order, price);
      price = applyCoupon(order, price);
      return minimumZero(price);
    }

    private int basePrice(Order order) {
      return order.getProduct().getPrice() * order.getQuantity();
    }

    private int applyGradeDiscount(Order order, int price) {
      if (order.getCustomer().isVip()) {
        return (int) (price * 0.9);
      }
      return price;
    }

    private int applyCoupon(Order order, int price) {
      if (!order.hasCoupon()) {
        return price;
      }
      return price - order.getCoupon().getAmount();
    }

    private int minimumZero(int price) {
      return Math.max(price, 0);
    }
  }
}
