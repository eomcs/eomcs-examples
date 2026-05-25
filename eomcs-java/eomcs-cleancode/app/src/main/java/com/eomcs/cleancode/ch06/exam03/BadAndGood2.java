package com.eomcs.cleancode.ch06.exam03;

import java.util.List;

// 예제 2: 잡종 구조 (Hybrids)
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Customer {
    private String name;
    Customer(String name) { this.name = name; }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Customer other)) return false;
      return name.equals(other.name);
    }

    @Override public int hashCode() { return name.hashCode(); }
  }

  static class Item {
    private int price;
    private int quantity;

    Item(int price, int quantity) { this.price = price; this.quantity = quantity; }

    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // 객체 방식: 계산 책임을 Item이 직접 갖는다
    public int subtotal() { return price * quantity; }
  }

  // Bad: 객체처럼 행동도 있고, 자료 구조처럼 내부 데이터도 공개한다.
  // - 의미 있는 메서드: calculateTotalPrice(), applyDiscount()
  // - 동시에 내부 데이터 공개: getCustomer(), getItems(), getTotalPrice()
  // - 결과: 외부 코드가 Order의 내부 자료를 직접 꺼내 계산하는 혼란이 생긴다.
  static class BadOrder {
    private Customer customer;
    private List<Item> items;
    private int totalPrice;

    BadOrder(Customer customer, List<Item> items) {
      this.customer = customer;
      this.items = items;
    }

    public Customer getCustomer() { return customer; }
    public List<Item> getItems() { return items; }
    public int getTotalPrice() { return totalPrice; }

    public void calculateTotalPrice() {
      totalPrice = 0;
      for (Item item : items) {
        totalPrice += item.getPrice() * item.getQuantity();
      }
    }

    public void applyDiscount(int discountAmount) {
      totalPrice -= discountAmount;
    }
  }

  static class BadOrderClient {
    void process(BadOrder order) {
      // 외부 코드가 Order의 내부 자료 구조를 직접 꺼내 계산한다
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      // 이렇게 외부에서 계산한 total을 applyDiscount()는 사용하지 못한다
      order.applyDiscount(1000);
      System.out.println("total = " + total);
    }
  }

  // Good: 데이터는 숨기고, 의미 있는 동작만 외부에 제공한다.
  // - 외부는 items를 직접 꺼내지 않는다.
  // - Order에게 일을 시키기만 한다.
  static class GoodOrder {
    private final Customer customer;
    private final List<Item> items;
    private int totalPrice;

    GoodOrder(Customer customer, List<Item> items) {
      this.customer = customer;
      this.items = items;
    }

    public void calculateTotalPrice() {
      totalPrice = calculateItemsTotal();
    }

    public void applyDiscount(int discountAmount) {
      totalPrice -= discountAmount;
    }

    public int getTotalPrice() { return totalPrice; }

    public boolean isOwnedBy(Customer customer) {
      return this.customer.equals(customer);
    }

    private int calculateItemsTotal() {
      int total = 0;
      for (Item item : items) {
        total += item.subtotal();
      }
      return total;
    }
  }

  static class GoodOrderClient {
    void process(GoodOrder order) {
      // 객체에게 일을 시킨다 — 내부 데이터를 꺼내지 않는다
      order.calculateTotalPrice();
      order.applyDiscount(1000);
      System.out.println("total = " + order.getTotalPrice());
    }
  }
}
