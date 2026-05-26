package com.eomcs.cleancode.ch07.exam07;

// 예제 3: null을 반환하지 마라 - getDiscountPolicy (Special Case Pattern)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Customer {
    private String name;
    Customer(String name) { this.name = name; }
    String getName() { return name; }
  }

  interface DiscountPolicy {
    int apply(int price);
  }

  static class RateDiscountPolicy implements DiscountPolicy {
    private final double rate;
    RateDiscountPolicy(double rate) { this.rate = rate; }

    @Override
    public int apply(int price) { return (int) (price * (1 - rate)); }
  }

  interface DiscountRepository {
    DiscountPolicy find(Customer customer); // 없으면 null
  }

  // Bad: 할인 정책이 없을 때 null을 반환한다.
  // - 호출자가 null 체크를 해야 한다.
  // - if문이 호출하는 곳마다 늘어난다.
  // - 할인 정책 없음이라는 상황이 호출 코드 전반에 퍼진다.
  static class BadDiscountService {
    private DiscountRepository discountRepository;
    BadDiscountService(DiscountRepository repo) { this.discountRepository = repo; }

    public DiscountPolicy getDiscountPolicy(Customer customer) {
      return discountRepository.find(customer); // 없으면 null 반환
    }
  }

  static class BadPricingClient {
    void calculatePrice(BadDiscountService service, Customer customer, int price) {
      DiscountPolicy policy = service.getDiscountPolicy(customer);

      // null 체크를 빠뜨리면 NPE 발생
      if (policy != null) {
        price = policy.apply(price);
      }

      System.out.println(customer.getName() + " 최종 가격: " + price);
    }
  }

  // -----------------------------------------------------------------------

  // Good: Special Case Object로 "할인 없음"을 표현한다.
  // - null 대신 의미 있는 객체를 반환한다.
  // - 호출자에서 null 체크와 조건문이 사라진다.
  // - 다형성으로 정책 없음을 자연스럽게 표현한다.
  static class NoDiscountPolicy implements DiscountPolicy {
    @Override
    public int apply(int price) { return price; } // 할인 없이 원가 그대로 반환
  }

  // Good: null 대신 NoDiscountPolicy(Special Case Object)를 반환한다.
  // - 호출자는 null 체크 없이 apply()를 바로 호출할 수 있다.
  // - "할인 정책 없음" 로직이 객체 내부에 캡슐화된다.
  static class GoodDiscountService {
    private DiscountRepository discountRepository;
    GoodDiscountService(DiscountRepository repo) { this.discountRepository = repo; }

    public DiscountPolicy getDiscountPolicy(Customer customer) {
      DiscountPolicy policy = discountRepository.find(customer);

      if (policy == null) {
        return new NoDiscountPolicy(); // null 대신 특수 사례 객체를 반환한다
      }

      return policy;
    }
  }

  static class GoodPricingClient {
    void calculatePrice(GoodDiscountService service, Customer customer, int price) {
      DiscountPolicy policy = service.getDiscountPolicy(customer);
      price = policy.apply(price); // null 체크 없이 바로 사용한다

      System.out.println(customer.getName() + " 최종 가격: " + price);
    }
  }
}
