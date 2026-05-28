package com.eomcs.cleancode.ch09.exam03;

// 예제 1: 깔끔한 테스트 - OrderService (VIP 할인 적용)
public class BadAndGood1 {

  private BadAndGood1() {}

  enum OrderStatus { CREATED, PAID }

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
    private int totalPrice;
    private OrderStatus status = OrderStatus.CREATED;

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
  }

  interface OrderRepositoryPort {
    void save(Order order);
  }

  interface PaymentGatewayPort {
    void pay(Order order);
  }

  static class FakeOrderRepository implements OrderRepositoryPort {
    private int count = 0;

    @Override
    public void save(Order order) {
      count++;
    }

    int count() { return count; }
  }

  static class FakePaymentGateway implements PaymentGatewayPort {
    private boolean paid = false;

    @Override
    public void pay(Order order) {
      paid = true;
      order.setStatus(OrderStatus.PAID);
    }

    boolean wasPaid() { return paid; }
  }

  static class OrderService {
    private final OrderRepositoryPort repository;
    private final PaymentGatewayPort paymentGateway;

    OrderService(OrderRepositoryPort repository, PaymentGatewayPort paymentGateway) {
      this.repository = repository;
      this.paymentGateway = paymentGateway;
    }

    void order(Order order) {
      int price = order.getProduct().getPrice() * order.getQuantity();
      if ("VIP".equals(order.getCustomer().getGrade())) {
        price = (int) (price * 0.9);
      }
      order.setTotalPrice(price);
      paymentGateway.pay(order);
      repository.save(order);
    }
  }
}
