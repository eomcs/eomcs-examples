package com.eomcs.cleancode.ch10.exam03;

// 예제 3: 변경으로부터 격리하라 - OrderService / OrderRepository
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {

    private final String id;

    Order(String id) {
      this.id = id;
    }

    String id() { return id; }
  }

  // Bad: OrderService가 MySQL 구현에 직접 의존한다
  //   - 저장소가 PostgreSQL, MongoDB, 외부 API로 바뀌면 OrderService도 수정해야 한다
  //   - 비즈니스 로직과 저장 기술이 강하게 결합된다
  static class BadMySqlOrderRepository {

    public void insert(Order order) {
      System.out.println("insert into mysql: " + order.id());
    }
  }

  static class BadOrderService {

    private final BadMySqlOrderRepository repository = new BadMySqlOrderRepository(); // 구체 클래스를 직접 생성

    public void place(Order order) {
      repository.insert(order);
    }
  }

  // Good: 인터페이스를 사이에 두어 저장 기술 변경을 격리한다
  //   - OrderService는 저장 방식 변경에 닫혀 있다
  //   - 새 저장 방식은 새 클래스로 추가한다
  //   - 비즈니스 로직은 저장 기술로부터 격리된다
  //   - 테스트도 쉬워진다
  interface OrderRepository {
    void save(Order order);
  }

  static class MySqlOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
      System.out.println("insert into mysql: " + order.id());
    }
  }

  static class OrderService {

    private final OrderRepository repository;

    OrderService(OrderRepository repository) { // 인터페이스로 의존성 주입
      this.repository = repository;
    }

    public void place(Order order) {
      repository.save(order);
    }
  }

  // 새 기능 추가: 기존 OrderService를 수정하지 않고 새 저장 방식을 추가한다
  static class MongoOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
      System.out.println("insert into mongodb: " + order.id());
    }
  }

  // 테스트용 구현: 실제 DB 없이 동작 여부만 확인
  static class FakeOrderRepository implements OrderRepository {

    private boolean saved = false;
    private Order savedOrder = null;

    @Override
    public void save(Order order) {
      saved = true;
      savedOrder = order;
    }

    boolean wasSaved() { return saved; }
    Order savedOrder() { return savedOrder; }
  }
}
