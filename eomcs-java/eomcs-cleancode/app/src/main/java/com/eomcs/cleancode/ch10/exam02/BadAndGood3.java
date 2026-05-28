package com.eomcs.cleancode.ch10.exam02;

import java.util.List;

// 예제 3: 응집도 (Cohesion) - ReportTool vs SalesReport + EmailSender
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {
    private final int amount;

    Order(int amount) {
      this.amount = amount;
    }

    int amount() { return amount; }
  }

  // Bad: orders·title·email 필드가 하나의 목적을 위해 함께 사용되지 않음 - 응집도 낮음
  static class BadReportTool {

    private final List<Order> orders;
    private final String title;
    private final String email;

    BadReportTool(List<Order> orders, String title, String email) {
      this.orders = orders;
      this.title = title;
      this.email = email;
    }

    int calculateTotalSales() {
      return orders.stream()          // orders만 사용
          .mapToInt(Order::amount)
          .sum();
    }

    String formatReport() {
      return title + ": " + calculateTotalSales();  // title + orders 사용
    }

    void sendEmail() {
      System.out.println("send to " + email);       // email만 사용
    }
  }

  // Good: 보고서 계산·포맷 책임 - orders와 title이 함께 사용되어 응집도 높음
  static class SalesReport {

    private final List<Order> orders;
    private final String title;

    SalesReport(List<Order> orders, String title) {
      this.orders = orders;
      this.title = title;
    }

    int totalSales() {
      return orders.stream()
          .mapToInt(Order::amount)
          .sum();
    }

    String format() {
      return title + ": " + totalSales();
    }
  }

  // Good: 이메일 발송 책임만 가짐 - email 필드만 사용
  static class EmailSender {

    private final String email;

    EmailSender(String email) {
      this.email = email;
    }

    void send(String message) {
      System.out.println("send to " + email);
    }
  }
}
