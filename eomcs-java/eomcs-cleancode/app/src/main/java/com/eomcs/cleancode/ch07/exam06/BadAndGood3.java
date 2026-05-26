package com.eomcs.cleancode.ch07.exam06;

// 예제 3: 정상 흐름을 정의하라 - ShippingFee (Special Case Pattern)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {
    private Long id;
    private int totalPrice;

    Order(Long id, int totalPrice) { this.id = id; this.totalPrice = totalPrice; }

    Long getId() { return id; }
    int getTotalPrice() { return totalPrice; }
  }

  // Bad: 배송비 없음을 예외로 처리한다.
  static class ShippingFeeNotFoundException extends RuntimeException {
    ShippingFeeNotFoundException() { super("배송비 정보가 없습니다."); }
  }

  // Bad: 예외 기반 설계
  // - 배송비가 없는 경우를 예외로 처리한다.
  // - catch 블록에 "무료 배송" 정책이 숨어 있다.
  // - 정상 계산 흐름이 try-catch 때문에 끊긴다.
  static class BadShippingFee {
    private int amount;
    BadShippingFee(int amount) { this.amount = amount; }
    int amount() { return amount; }
  }

  interface BadShippingRepository {
    BadShippingFee findFee(Order order);
  }

  static class BadShippingService {
    private BadShippingRepository shippingRepository;
    BadShippingService(BadShippingRepository repo) { this.shippingRepository = repo; }

    public BadShippingFee getShippingFee(Order order) {
      BadShippingFee fee = shippingRepository.findFee(order);

      if (fee == null) {
        throw new ShippingFeeNotFoundException();
      }

      return fee;
    }
  }

  static class BadOrderClient {
    void calculateTotal(BadShippingService shippingService, Order order) {
      int total = order.getTotalPrice();

      try {
        BadShippingFee fee = shippingService.getShippingFee(order);
        total += fee.amount();
      } catch (ShippingFeeNotFoundException e) {
        total += 0; // 업무 규칙("무료 배송")이 catch 블록에 숨어 있다
      }

      System.out.println("주문 " + order.getId() + " 최종 금액: " + total);
    }
  }

  // -----------------------------------------------------------------------

  // Good: 인터페이스와 특수 사례 객체로 정상 흐름을 표현한다.
  interface ShippingFee {
    int amount();
  }

  // 실제 배송비
  static class DefaultShippingFee implements ShippingFee {
    private final int amount;
    DefaultShippingFee(int amount) { this.amount = amount; }

    @Override
    public int amount() { return amount; }
  }

  // Special Case Object: 배송비가 없을 때 0을 반환한다.
  // - "무료 배송"을 예외가 아니라 정상 객체로 표현한다.
  // - 호출자는 배송비가 있는지 없는지 구분하지 않아도 된다.
  static class FreeShippingFee implements ShippingFee {
    @Override
    public int amount() { return 0; }
  }

  interface GoodShippingRepository {
    DefaultShippingFee findFee(Order order);
  }

  // Good: null이면 FreeShippingFee(Special Case Object)를 반환한다.
  // - 무료 배송이 예외 없이 정상 객체로 처리된다.
  // - 호출 코드는 단순한 계산 흐름만 표현한다.
  // - 특수 상황이 객체 내부로 캡슐화된다.
  static class GoodShippingService {
    private GoodShippingRepository shippingRepository;
    GoodShippingService(GoodShippingRepository repo) { this.shippingRepository = repo; }

    public ShippingFee getShippingFee(Order order) {
      DefaultShippingFee fee = shippingRepository.findFee(order);

      if (fee == null) {
        return new FreeShippingFee();
      }

      return fee;
    }
  }

  static class GoodOrderClient {
    void calculateTotal(GoodShippingService shippingService, Order order) {
      int total = order.getTotalPrice();

      ShippingFee fee = shippingService.getShippingFee(order);
      total += fee.amount(); // 정상 흐름만 보인다

      System.out.println("주문 " + order.getId() + " 최종 금액: " + total);
    }
  }
}
