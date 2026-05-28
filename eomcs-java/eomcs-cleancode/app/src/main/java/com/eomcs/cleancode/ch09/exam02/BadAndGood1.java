package com.eomcs.cleancode.ch09.exam02;

// 예제 1: 깨끗한 테스트 코드 - OrderService
public class BadAndGood1 {

  private BadAndGood1() {}

  enum OrderStatus { CREATED, PAID }

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

  static class Customer {
    private final String email;

    Customer(String email) {
      this.email = email;
    }

    String getEmail() { return email; }
  }

  static class Order {
    private final Customer customer;
    private final Product product;
    private final int quantity;
    private int totalPrice;
    private OrderStatus status = OrderStatus.CREATED;
    private boolean notificationSent;

    Order(Customer customer, Product product, int quantity) {
      this.customer = customer;
      this.product = product;
      this.quantity = quantity;
    }

    Customer getCustomer() { return customer; }
    Product getProduct() { return product; }
    int getQuantity() { return quantity; }
    int getTotalPrice() { return totalPrice; }
    void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    OrderStatus getStatus() { return status; }
    void setStatus(OrderStatus status) { this.status = status; }
    boolean isNotificationSent() { return notificationSent; }
    void setNotificationSent(boolean notificationSent) { this.notificationSent = notificationSent; }
  }

  interface OrderRepositoryPort {
    void save(Order order);
  }

  interface PaymentGatewayPort {
    void pay(Order order);
  }

  interface NotificationSenderPort {
    void send(Order order);
  }

  // Bad 테스트에서 사용하는 실제 구현체 - 외부 시스템에 의존하여 테스트하기 어렵다.
  static class OrderRepository implements OrderRepositoryPort {
    @Override
    public void save(Order order) {}
  }

  static class PaymentGateway implements PaymentGatewayPort {
    @Override
    public void pay(Order order) {
      order.setStatus(OrderStatus.PAID);
    }
  }

  static class NotificationSender implements NotificationSenderPort {
    @Override
    public void send(Order order) {
      order.setNotificationSent(true);
    }
  }

  // Good 테스트에서 사용하는 가짜 구현체 - 빠르고 독립적으로 테스트할 수 있다.
  static class FakeOrderRepository implements OrderRepositoryPort {
    @Override
    public void save(Order order) {}
  }

  static class FakePaymentGateway implements PaymentGatewayPort {
    @Override
    public void pay(Order order) {
      order.setStatus(OrderStatus.PAID);
    }
  }

  static class FakeNotificationSender implements NotificationSenderPort {
    @Override
    public void send(Order order) {
      order.setNotificationSent(true);
    }
  }

  static class OrderService {
    private final OrderRepositoryPort repository;
    private final PaymentGatewayPort paymentGateway;
    private final NotificationSenderPort notificationSender;

    OrderService(OrderRepositoryPort repository,
                 PaymentGatewayPort paymentGateway,
                 NotificationSenderPort notificationSender) {
      this.repository = repository;
      this.paymentGateway = paymentGateway;
      this.notificationSender = notificationSender;
    }

    void order(Order order) {
      order.setTotalPrice(order.getProduct().getPrice() * order.getQuantity());
      paymentGateway.pay(order);
      repository.save(order);
      notificationSender.send(order);
    }
  }
}
