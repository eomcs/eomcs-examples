package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood1 {

  static class Order {}
  static class User {}

  // Bad
  // - process, handle, doStuff → 무엇을 처리하는지 내부 코드를 보지 않으면 알 수 없다.
  static class BadService {
    void process() {}
    void handle() {}
    void doStuff() {}
  }

  // Good
  // - processOrder, handleUserLogin, calculateTotalPrice → 대상 + 행동을 함께 표현한다.
  // - 메서드 이름만으로 역할을 이해할 수 있다.
  static class GoodService {
    void processOrder(Order order) {}
    void handleUserLogin(User user) {}
    double calculateTotalPrice(Order order) { return 0; }
  }
}
