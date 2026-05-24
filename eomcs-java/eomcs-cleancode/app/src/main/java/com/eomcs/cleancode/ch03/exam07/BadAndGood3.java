package com.eomcs.cleancode.ch03.exam07;

public class BadAndGood3 {

  static class Order {
    private int price;
    private int quantity;
    Order(int price, int quantity) { this.price = price; this.quantity = quantity; }
    int getPrice() { return price; }
    int getQuantity() { return quantity; }
  }

  // Bad: 출력 인자 사용
  // - appendFooter(StringBuffer) → report가 입력인지 출력인지 혼란스럽다.
  //   외부 상태를 직접 변경하는 부작용이 있다.
  // - calculateTotal(Order, int[]) → totalHolder[0] 으로 결과를 꺼내는 트릭 같은 코드.
  //   의도를 파악하기 매우 어렵다.
  static class BadReportService {
    void appendFooter(StringBuffer report) {
      report.append("\n--- End of Report ---"); // 출력 인자로 외부 상태 변경
    }
  }

  static class BadOrderService {
    void calculateTotal(Order order, int[] totalHolder) {
      totalHolder[0] = order.getPrice() * order.getQuantity(); // 출력 인자로 결과 전달
    }
  }

  // Good: return 값을 사용한다.
  // - appendFooter → 새 문자열을 반환한다. 출력이 명확하다.
  // - calculateTotal → 결과를 직접 반환한다. 자연스럽게 읽힌다.
  static class GoodReportService {
    String appendFooter(String report) {
      return report + "\n--- End of Report ---";
    }
  }

  static class GoodOrderService {
    int calculateTotal(Order order) {
      return order.getPrice() * order.getQuantity();
    }
  }

  static class GoodUsage {
    void demo(Order order) {
      GoodReportService reportService = new GoodReportService();
      GoodOrderService orderService = new GoodOrderService();

      String reportWithFooter = reportService.appendFooter("주문 내역");
      int total = orderService.calculateTotal(order);
      System.out.println(reportWithFooter + "\n합계: " + total);
    }
  }
}
