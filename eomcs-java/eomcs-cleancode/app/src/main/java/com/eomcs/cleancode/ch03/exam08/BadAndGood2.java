package com.eomcs.cleancode.ch03.exam08;

public class BadAndGood2 {

  static class Order {
    private int price;
    private int quantity;
    private int total;

    Order(int price, int quantity) { this.price = price; this.quantity = quantity; }
    int getPrice() { return price; }
    int getQuantity() { return quantity; }
    void setTotal(int total) { this.total = total; }
    int getTotal() { return total; }
  }

  // Bad
  // - getTotalPrice → 이름은 조회(Query)처럼 보이지만 order.setTotal()로 상태를 변경(Command)한다.
  // - 'get' 접두어가 숨겨진 상태 변경을 완전히 가린다.
  // - CQS 원칙을 완전히 위반한 경우다.
  static class BadOrderService {
    int getTotalPrice(Order order) {
      int total = calculate(order);
      order.setTotal(total); // 숨겨진 상태 변경
      return total;
    }

    private int calculate(Order order) {
      return order.getPrice() * order.getQuantity();
    }
  }

  // Good: 조회와 변경을 분리한다.
  // - calculateTotalPrice → 상태 변경 없이 계산 결과만 반환하는 순수 Query다.
  // - updateOrderTotal → 상태만 변경하고 반환값이 없는 순수 Command다.
  // - 함수 이름과 실제 행동이 완전히 일치한다.
  static class GoodOrderService {
    int calculateTotalPrice(Order order) {
      return calculate(order);
    }

    void updateOrderTotal(Order order) {
      order.setTotal(calculate(order));
    }

    private int calculate(Order order) {
      return order.getPrice() * order.getQuantity();
    }
  }

  static class GoodUsage {
    void demo(GoodOrderService service, Order order) {
      int total = service.calculateTotalPrice(order); // Query: 상태 변경 없음
      System.out.println("계산된 합계: " + total);

      service.updateOrderTotal(order); // Command: 상태 변경
      System.out.println("저장된 합계: " + order.getTotal());
    }
  }
}
