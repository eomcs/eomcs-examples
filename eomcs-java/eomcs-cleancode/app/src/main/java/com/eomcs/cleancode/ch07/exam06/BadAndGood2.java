package com.eomcs.cleancode.ch07.exam06;

// 예제 2: 정상 흐름을 정의하라 - DiscountPolicy (Special Case Pattern)
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Customer {
    private String name;
    Customer(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: 할인 정책 없음을 예외로 처리한다.
  static class DiscountPolicyNotFoundException extends RuntimeException {
    DiscountPolicyNotFoundException() { super("할인 정책이 없습니다."); }
  }

  // Bad: 예외 기반 설계
  // - 할인 정책이 없는 것은 오류라기보다 정상적인 경우일 수 있다.
  // - catch 블록에 "할인 없음"이라는 업무 규칙이 들어간다.
  // - 호출 코드가 불필요하게 복잡해진다.
  interface BadDiscountRepository {
    Integer findRateByCustomer(Customer customer); // null이면 정책 없음
  }

  static class BadDiscountService {
    private BadDiscountRepository discountRepository;
    BadDiscountService(BadDiscountRepository repo) { this.discountRepository = repo; }

    public int findDiscountPolicy(Customer customer) {
      Integer rate = discountRepository.findRateByCustomer(customer);

      if (rate == null) {
        throw new DiscountPolicyNotFoundException();
      }

      return rate;
    }
  }

  static class BadPricingClient {
    void calculatePrice(BadDiscountService service, Customer customer, int price) {
      int finalPrice;

      try {
        int rate = service.findDiscountPolicy(customer);
        finalPrice = (int) (price * (1 - rate / 100.0));
      } catch (DiscountPolicyNotFoundException e) {
        finalPrice = price; // 업무 규칙이 catch 블록에 숨어 있다
      }

      System.out.println(customer.getName() + " 최종 가격: " + finalPrice);
    }
  }

  // -----------------------------------------------------------------------

  // Good: 인터페이스와 특수 사례 객체로 정상 흐름을 표현한다.
  interface DiscountPolicy {
    int apply(int price);
  }

  // 실제 할인 정책
  static class RateDiscountPolicy implements DiscountPolicy {
    private final double rate;
    RateDiscountPolicy(double rate) { this.rate = rate; }

    @Override
    public int apply(int price) { return (int) (price * (1 - rate)); }
  }

  // Special Case Object: 할인 정책이 없을 때 원가를 그대로 반환한다.
  // - "할인 없음"이 하나의 정상 정책으로 표현된다.
  // - 호출자는 예외 처리 없이 동일하게 apply()를 호출할 수 있다.
  static class NoDiscountPolicy implements DiscountPolicy {
    @Override
    public int apply(int price) { return price; }
  }

  interface GoodDiscountRepository {
    DiscountPolicy findByCustomer(Customer customer); // null이면 정책 없음
  }

  // Good: null이면 NoDiscountPolicy(Special Case Object)를 반환한다.
  // - 호출자는 try-catch 없이 정상 흐름만 표현하면 된다.
  // - 다형성 덕분에 호출 코드가 단순해진다.
  static class GoodDiscountService {
    private GoodDiscountRepository discountRepository;
    GoodDiscountService(GoodDiscountRepository repo) { this.discountRepository = repo; }

    public DiscountPolicy findDiscountPolicy(Customer customer) {
      DiscountPolicy policy = discountRepository.findByCustomer(customer);

      if (policy == null) {
        return new NoDiscountPolicy();
      }

      return policy;
    }
  }

  static class GoodPricingClient {
    void calculatePrice(GoodDiscountService service, Customer customer, int price) {
      DiscountPolicy policy = service.findDiscountPolicy(customer);
      int finalPrice = policy.apply(price); // 정상 흐름만 보인다

      System.out.println(customer.getName() + " 최종 가격: " + finalPrice);
    }
  }
}
