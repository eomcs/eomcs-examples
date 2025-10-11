// # 아이템 43. 람다보다는 메서드 참조를 사용하라
// [메서드 레퍼런스]
// - 함수 객체를 람다보다도 더 간결하게 만들 수 있다.
//

package effectivejava.ch07.item43.exam02;

// [주제] 람다 표현식이 메서드 레퍼런스보다 나은 경우 - 인스턴스 메서드 레퍼런스

import java.util.*;

class OrderProcessor {
  private final List<String> orders = new ArrayList<>();

  public void addOrder(String order) {
    orders.add(order);
  }

  public void processOrders() {
    // 람다 버전 (더 명확함)
    // - 람다가 메서드와 같은 클래스에 있을 때는,
    //   '이 코드가 어디를 호출하는지'가 이미 명확하므로 굳이 메서드 참조로 바꾸지 않아도 된다.
    orders.forEach(order -> handleOrder(order));

    // 메서드 참조 버전 (같은 클래스의 인스턴스 메서드)
    // - 같은 클래스의 인스턴스 메서드를 참조하는 것이 더 가독성이 떨어진다고 생각할 수 있다.
    //    orders.forEach(this::handleOrder);
  }

  // 주문 처리 메서드
  private void handleOrder(String order) {
    System.out.printf("%s 주문을 처리했습니다.%n", order);
  }
}

public class Test {
  public static void main(String[] args) {
    OrderProcessor processor = new OrderProcessor();
    processor.addOrder("피자");
    processor.addOrder("치킨");
    processor.addOrder("콜라2병");
    processor.processOrders();

    // [정리]
    // - 다른 클래스의 정적 메서드를 사용할 때: 메서드 레퍼런스가 가독성 좋음
    // - 같은 클래스의 인스턴스 메서드를 사용할 때: 람다가 더 명확함
  }
}
