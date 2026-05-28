package com.eomcs.cleancode.ch12.exam06;

// 예제 4: 패턴을 위한 패턴을 적용하지 마라 - Price / DiscountStrategy
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: 할인 정책이 하나뿐인데 Strategy 패턴을 적용했다
  //   - 구조는 유연해 보이지만 실제 가치는 없다
  //   - 단순한 요구사항에 비해 클래스가 많다
  interface BadDiscountStrategy {
    int discount(int price);
  }

  static class NoDiscountStrategy implements BadDiscountStrategy {

    @Override
    public int discount(int price) {
      return 0;
    }
  }

  static class DiscountContext {

    private final BadDiscountStrategy strategy;

    DiscountContext(BadDiscountStrategy strategy) {
      this.strategy = strategy;
    }

    public int apply(int price) {
      return price - strategy.discount(price);
    }
  }

  // Good: 지금은 단순하게 유지한다
  //   - 변화가 실제로 나타날 때 추상화를 도입한다
  //   - 예측 기반 설계를 줄인다
  static class Price {

    private final int amount;

    Price(int amount) {
      this.amount = amount;
    }

    public int discountedAmount() {
      return amount; // 할인 정책이 없는 현재는 원가 그대로
    }
  }

  // 나중에 할인 정책이 여러 개 생기면 그때 도입한다
  interface DiscountPolicy {
    int discount(int price);
  }
}
