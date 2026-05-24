package com.eomcs.cleancode.ch03.exam05;

public class BadAndGood2 {

  // Bad
  // - calc → 무엇을 계산하는지 전혀 알 수 없다.
  // - a, b → 파라미터의 의미를 추측해야 한다.
  // - 0.9 → 할인율인지 세율인지 알 수 없다.
  static class BadPriceService {
    double calc(double a, double b) {
      return a * b * 0.9;
    }
  }

  // Good
  // - calculateDiscountedPrice → 계산 대상(Price)과 목적(Discounted)이 이름에 드러난다.
  // - price, quantity → 파라미터의 의미가 명확해 추측이 필요 없다.
  static class GoodPriceService {
    double calculateDiscountedPrice(double price, int quantity) {
      return price * quantity * 0.9;
    }
  }
}
