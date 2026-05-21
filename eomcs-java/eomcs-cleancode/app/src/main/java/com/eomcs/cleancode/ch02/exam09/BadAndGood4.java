package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood4 {

  static class Invoice {
    double basePrice;
    double tax;
  }

  static class User {
    String rawData;
  }

  // Bad
  // - do, handle → 의미 없는 접두사. 구체적인 행동이 드러나지 않는다.
  static class BadService {
    double doCalculation(Invoice invoice) {
      return invoice.basePrice + invoice.tax;
    }
    User handleData(String rawData) {
      User user = new User();
      user.rawData = rawData;
      return user;
    }
  }

  // Good
  // - calculateInvoiceTotal, parseUserData → 실제 수행하는 작업을 정확히 표현한다.
  static class GoodService {
    double calculateInvoiceTotal(Invoice invoice) {
      return invoice.basePrice + invoice.tax;
    }
    User parseUserData(String rawData) {
      User user = new User();
      user.rawData = rawData;
      return user;
    }
  }
}
