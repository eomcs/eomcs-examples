package com.eomcs.cleancode.ch09.exam04;

// 예제 2: 테스트당 하나의 개념 - OrderService
public class BadAndGood2 {

  private BadAndGood2() {}

  enum OrderStatus { CREATED, PAID }

  static class Order {
    private final String productName;
    private final int quantity;
    private final int unitPrice;
    private int totalPrice;
    private OrderStatus status = OrderStatus.CREATED;
    private boolean saved;
    private boolean notificationSent;

    Order(String productName, int quantity, int unitPrice) {
      this.productName = productName;
      this.quantity = quantity;
      this.unitPrice = unitPrice;
    }

    String getProductName() { return productName; }
    int getQuantity() { return quantity; }
    int getUnitPrice() { return unitPrice; }
    int getTotalPrice() { return totalPrice; }
    void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    OrderStatus getStatus() { return status; }
    void setStatus(OrderStatus status) { this.status = status; }
    boolean isSaved() { return saved; }
    void setSaved(boolean saved) { this.saved = saved; }
    boolean isNotificationSent() { return notificationSent; }
    void setNotificationSent(boolean notificationSent) { this.notificationSent = notificationSent; }
  }

  interface NotificationSenderPort {
    void send(Order order);
  }

  static class FakeNotificationSender implements NotificationSenderPort {
    private boolean sent = false;

    @Override
    public void send(Order order) {
      sent = true;
    }

    boolean wasSent() { return sent; }
  }

  static class OrderService {
    private final NotificationSenderPort notificationSender;

    OrderService() {
      this.notificationSender = order -> order.setNotificationSent(true);
    }

    OrderService(NotificationSenderPort notificationSender) {
      this.notificationSender = notificationSender;
    }

    void place(Order order) {
      order.setTotalPrice(order.getUnitPrice() * order.getQuantity());
      order.setStatus(OrderStatus.PAID);
      order.setSaved(true);
      notificationSender.send(order);
    }
  }
}
