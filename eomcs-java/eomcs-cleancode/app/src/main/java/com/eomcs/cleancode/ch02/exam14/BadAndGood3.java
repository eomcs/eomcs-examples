package com.eomcs.cleancode.ch02.exam14;

public class BadAndGood3 {

  static class Invoice {
    double baseAmount;
    double taxRate;
  }

  // Bad
  // - Calculator → 무엇을 계산하는지 알 수 없다.
  // - calculate(int a, int b) → 파라미터 이름에서도 비즈니스 의미가 없다.
  static class BadCalculator {
    int calculate(int a, int b) {
      return a + b;
    }
  }

  // Good
  // - InvoiceCalculator → 도메인 개념(Invoice)이 이름에 드러난다.
  // - calculateTotalAmount → 업무 의미(청구서 합계 계산)가 명확하다.
  static class GoodInvoiceCalculator {
    int calculateTotalAmount(Invoice invoice) {
      return (int) (invoice.baseAmount * (1 + invoice.taxRate));
    }
  }
}
