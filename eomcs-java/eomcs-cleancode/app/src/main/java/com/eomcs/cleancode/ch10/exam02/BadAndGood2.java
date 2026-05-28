package com.eomcs.cleancode.ch10.exam02;

import java.util.List;

// 예제 2: 단일 책임 원칙 (SRP) - Invoice
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Item {
    private final int price;

    Item(int price) {
      this.price = price;
    }

    int price() { return price; }
  }

  // Bad: 계산·출력·저장을 모두 담당 - 변경 이유가 셋
  static class BadInvoice {

    private final List<Item> items;

    BadInvoice(List<Item> items) {
      this.items = items;
    }

    int totalPrice() {
      return items.stream()
          .mapToInt(Item::price)
          .sum();
    }

    String print() {
      return "Total: " + totalPrice();
    }

    void save() {
      System.out.println("save invoice to database");
    }
  }

  // Good: 계산 책임만 가진 Invoice
  static class Invoice {

    private final List<Item> items;

    Invoice(List<Item> items) {
      this.items = items;
    }

    int totalPrice() {
      return items.stream()
          .mapToInt(Item::price)
          .sum();
    }
  }

  // Good: 출력 책임만 가진 InvoicePrinter
  static class InvoicePrinter {

    String print(Invoice invoice) {
      return "Total: " + invoice.totalPrice();
    }
  }

  // Good: 저장 책임만 가진 InvoiceRepository
  interface InvoiceRepository {
    void save(Invoice invoice);
  }

  static class FakeInvoiceRepository implements InvoiceRepository {
    private boolean saved = false;

    @Override
    public void save(Invoice invoice) { saved = true; }

    boolean wasSaved() { return saved; }
  }
}
