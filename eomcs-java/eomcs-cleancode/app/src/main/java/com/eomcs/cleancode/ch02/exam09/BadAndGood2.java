package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood2 {

  static class User {
    String name;
  }

  static class Order {
    int id;
  }

  // Bad
  // - save() 만으로는 무엇을 저장하는지 알 수 없다.
  static class BadRepository {
    void save(Object obj) {
      System.out.println("저장: " + obj);
    }
  }

  // Good
  // - saveUser, saveOrder → 대상을 명시해 의도가 명확해진다.
  static class GoodRepository {
    void saveUser(User user) {
      System.out.println("사용자 저장: " + user.name);
    }
    void saveOrder(Order order) {
      System.out.println("주문 저장: " + order.id);
    }
  }
}
