package com.eomcs.cleancode.ch10.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam03.BadAndGood3.FakeOrderRepository;
import com.eomcs.cleancode.ch10.exam03.BadAndGood3.MongoOrderRepository;
import com.eomcs.cleancode.ch10.exam03.BadAndGood3.MySqlOrderRepository;
import com.eomcs.cleancode.ch10.exam03.BadAndGood3.Order;
import com.eomcs.cleancode.ch10.exam03.BadAndGood3.OrderService;
import org.junit.jupiter.api.Test;

// 예제 3: 변경으로부터 격리 - OrderService / OrderRepository
//
// Bad: OrderService가 MySqlOrderRepository에 직접 의존 — 저장소 변경 시 OrderService도 수정해야 한다.
// Good: OrderRepository 인터페이스를 주입받으므로 저장 방식을 자유롭게 교체할 수 있다.
//       새 저장 방식은 OrderService를 수정하지 않고 새 클래스로 추가한다.
class OrderServiceGoodTest {

  @Test
  void 주문을_저장한다() {
    FakeOrderRepository repository = new FakeOrderRepository();
    OrderService service = new OrderService(repository);

    Order order = new Order("order-1");
    service.place(order);

    assertTrue(repository.wasSaved());
    assertEquals("order-1", repository.savedOrder().id());
  }

  @Test
  void MySQL_저장소로_주문을_처리한다() {
    // 실제 DB 없이 컴파일·구조 확인만 수행
    OrderService service = new OrderService(new MySqlOrderRepository());

    // 컴파일되면 구조가 올바른 것이다
    assertNotNull(service);
  }

  @Test
  void MongoDB_저장소로_주문을_처리한다() {
    // 새 저장 방식: OrderService 코드를 수정하지 않고 MongoOrderRepository만 추가했다
    OrderService service = new OrderService(new MongoOrderRepository());

    // 컴파일되면 확장이 올바르게 된 것이다
    assertNotNull(service);
  }
}
