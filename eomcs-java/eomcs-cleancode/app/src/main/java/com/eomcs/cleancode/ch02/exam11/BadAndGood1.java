package com.eomcs.cleancode.ch02.exam11;

public class BadAndGood1 {

  static class User { String id; }
  static class Order { String id; }
  static class Product { String id; }

  // Bad
  // - getUser, fetchOrder, retrieveProduct → 모두 "ID로 객체를 가져온다"는 같은 개념이다.
  // - 메서드 이름이 제각각이어서 읽는 사람은 세 단어의 차이가 있는지 고민하게 된다.
  static class BadUserRepository {
    User getUser(String id) { return null; }
  }

  static class BadOrderRepository {
    Order fetchOrder(String id) { return null; }
  }

  static class BadProductRepository {
    Product retrieveProduct(String id) { return null; }
  }

  // Good
  // - findById 로 통일 → 같은 개념에 같은 단어를 사용한다.
  // - API 사용자가 예측 가능하고 IDE 자동완성에서도 일관성이 향상된다.
  static class GoodUserRepository {
    User findById(String id) { return null; }
  }

  static class GoodOrderRepository {
    Order findById(String id) { return null; }
  }

  static class GoodProductRepository {
    Product findById(String id) { return null; }
  }
}
