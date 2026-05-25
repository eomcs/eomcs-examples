package com.eomcs.cleancode.ch05.exam01;

import java.util.ArrayList;
import java.util.List;

// 예제 1: 신문 기사처럼 작성하라 (The Newspaper Metaphor)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Item {
    int getPrice() { return 1000; }
    int getQuantity() { return 1; }
  }

  static class Order {
    List<Item> getItems() {
      List<Item> items = new ArrayList<>();
      items.add(new Item());
      return items;
    }
    String getUserEmail() { return "user@example.com"; }
  }

  static class Database {
    void save(Order o) { System.out.println("DB 저장: " + o); }
  }

  static class EmailService {
    void send(String email, String msg) { System.out.println("이메일 전송 to " + email + ": " + msg); }
  }

  // Bad: 세부 구현이 먼저 등장한다.
  // - saveToDatabase(), calculateTotal()이 파일 상단에 위치한다.
  // - 핵심 함수인 processOrder()가 파일 중간에 숨어 있다.
  // - 읽는 사람이 흐름을 파악하려면 파일을 위아래로 이동해야 한다.
  static class BadOrderService {
    Database database = new Database();
    EmailService emailService = new EmailService();

    private void saveToDatabase(Order order) {
      database.save(order);
    }

    private int calculateTotal(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }

    public void processOrder(Order order) {
      int total = calculateTotal(order);
      saveToDatabase(order);
      sendConfirmationEmail(order, total);
    }

    private void sendConfirmationEmail(Order order, int total) {
      emailService.send(order.getUserEmail(), "Total: " + total);
    }
  }

  // Good: 가장 중요한 public 메서드가 파일 맨 위에 나온다.
  // - processOrder()가 먼저 등장하고 아래로 내려가며 세부 구현이 나온다.
  // - 신문 기사처럼 위에서 아래로 읽으면 전체 흐름을 빠르게 파악할 수 있다.
  static class GoodOrderService {
    Database database = new Database();
    EmailService emailService = new EmailService();

    public void processOrder(Order order) {
      int total = calculateTotal(order);
      saveToDatabase(order);
      sendConfirmationEmail(order, total);
    }

    private int calculateTotal(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }

    private void saveToDatabase(Order order) {
      database.save(order);
    }

    private void sendConfirmationEmail(Order order, int total) {
      emailService.send(order.getUserEmail(), "Total: " + total);
    }
  }
}
