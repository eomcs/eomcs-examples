package com.eomcs.cleancode.ch04.exam02;

public class BadAndGood4 {

  // Bad
  // - d, p, q는 아무 의미도 없는 단일 문자 변수다.
  // - 계산식 (p * q) * 0.9가 무엇을 구하는지 코드만으로 알 수 없다.
  // - 주석 없이는 의도를 전혀 파악할 수 없다.
  static class BadPricing {
    double calculate(int p, int q) {
      int d;
      d = (int) ((p * q) * 0.9);
      return d;
    }
  }

  // Good: 의미 있는 변수 이름과 함수 이름으로 주석 없이 의도를 표현한다.
  // - price, quantity, discountedPrice라는 이름이 역할을 즉시 설명한다.
  // - calculateDiscountedPrice()라는 함수 이름이 계산 목적을 드러낸다.
  // - 읽는 사람이 해석하지 않아도 코드를 이해할 수 있다.
  static class GoodPricing {
    double calculateDiscountedPrice(int price, int quantity) {
      double discountedPrice = (price * quantity) * 0.9;
      return discountedPrice;
    }
  }
}
