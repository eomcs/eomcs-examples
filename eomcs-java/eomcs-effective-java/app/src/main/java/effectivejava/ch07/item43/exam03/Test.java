// # 아이템 43. 람다보다는 메서드 참조를 사용하라
// [메서드 레퍼런스]
// - 함수 객체를 람다보다도 더 간결하게 만들 수 있다.
//

package effectivejava.ch07.item43.exam03;

// [주제] 람다 표현식이 메서드 레퍼런스보다 나은 경우 II - 스태틱 메서드 레퍼런스

import java.util.*;
import java.util.function.Function;

class Order {
  private final String item;
  private final int quantity;
  private final int unitPrice;

  public Order(String item, int quantity, int unitPrice) {
    this.item = item;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public String getItem() {
    return item;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getUnitPrice() {
    return unitPrice;
  }
}

class OrderProcessor {
  private final List<Order> orders = new ArrayList<>();

  public void addOrder(Order order) {
    orders.add(order);
  }

  public static int totalPrice(Order order) {
    return order.getQuantity() * order.getUnitPrice();
  }

  public void processOrders() {
    // 람다 버전 (더 명확함)
    // - 람다와 참조 대상 메서드가 같은 클래스에 있을 때는,
    //   메서드 참조(class::method)보다 람다(arg -> method(arg))를 쓰는 편이
    //   코드의 의도를 더 명확히 드러내므로 낫다
    //
    for (Order order : orders) {
      handleOrder(order, o -> totalPrice(o));
    }

    // 메서드 참조 버전 (같은 클래스의 인스턴스 메서드)
    // - 축약되었지만 코드의 의도가 덜 드러난다.
    //   totalPrice로 무슨 일을 하는지 알기 위해서는 totalPrice 메서드의 구현을 봐야 한다.
    //
    //    for (Order order : orders) {
    //      handleOrder(order, OrderProcessor::totalPrice);
    //    }
  }

  // 주문 처리 메서드
  private void handleOrder(Order order, Function<Order, Integer> orderPrice) {
    System.out.printf("%s(%d 원) 주문을 처리했습니다.%n", order.getItem(), orderPrice.apply(order));
  }
}

public class Test {
  public static void main(String[] args) {
    OrderProcessor processor = new OrderProcessor();
    processor.addOrder(new Order("피자", 2, 16000));
    processor.addOrder(new Order("치킨", 1, 18000));
    processor.addOrder(new Order("콜라2병", 3, 2000));
    processor.processOrders();

    // [정리]
    // - 다른 클래스의 정적 메서드를 사용할 때: 메서드 레퍼런스가 가독성 좋음
    // - 같은 클래스의 인스턴스 메서드를 사용할 때: 람다가 더 명확함
  }
}
