package com.eomcs.cleancode.ch10.exam01;

// 예제 1: 클래스 체계 - OrderService
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Order {
    private int discountedAmount;

    Order(int amount) {
      this.discountedAmount = amount;
    }

    int totalAmount() { return discountedAmount; }

    void applyDiscount(int discount) {
      discountedAmount = Math.max(0, discountedAmount - discount);
    }
  }

  interface OrderRepository {
    void save(Order order);
  }

  interface DiscountPolicy {
    int calculate(Order order);
  }

  static class FakeOrderRepository implements OrderRepository {
    private boolean saved = false;

    @Override
    public void save(Order order) {
      saved = true;
    }

    boolean wasSaved() { return saved; }
  }

  static class FakeDiscountPolicy implements DiscountPolicy {
    private final int discount;

    FakeDiscountPolicy(int discount) {
      this.discount = discount;
    }

    @Override
    public int calculate(Order order) { return discount; }
  }

  // Bad: 상수·필드·메서드 순서가 뒤섞이고, 필드가 public으로 노출됨
  static class BadOrderService {

    public OrderRepository repository;         // public 필드 노출
    public DiscountPolicy discountPolicy;      // public 필드 노출

    private static final int FREE_SHIPPING_THRESHOLD = 50_000;

    public void applyDiscount(Order order) {   // 내부 로직이 public으로 노출
      int discount = discountPolicy.calculate(order);
      order.applyDiscount(discount);
    }

    public static final int MAX_ORDER_AMOUNT = 1_000_000; // 상수 위치가 잘못됨

    public void save(Order order) {
      repository.save(order);
    }
  }

  // Good: 상수 → 변수 → public 메서드 → private 보조 메서드 순서로 배치
  static class OrderService {

    public static final int MAX_ORDER_AMOUNT = 1_000_000;

    private static final int FREE_SHIPPING_THRESHOLD = 50_000;

    private final OrderRepository repository;
    private final DiscountPolicy discountPolicy;

    OrderService(OrderRepository repository, DiscountPolicy discountPolicy) {
      this.repository = repository;
      this.discountPolicy = discountPolicy;
    }

    void save(Order order) {
      validate(order);
      applyDiscount(order);
      repository.save(order);
    }

    private void validate(Order order) {
      if (order.totalAmount() > MAX_ORDER_AMOUNT) {
        throw new IllegalArgumentException("주문 금액이 한도를 초과했습니다");
      }
    }

    private void applyDiscount(Order order) {
      int discount = discountPolicy.calculate(order);
      order.applyDiscount(discount);
    }
  }
}
