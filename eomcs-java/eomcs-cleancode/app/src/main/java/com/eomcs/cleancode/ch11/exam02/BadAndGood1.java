package com.eomcs.cleancode.ch11.exam02;

// 예제 1: 비즈니스 로직과 기술 관심사를 분리하라 - OrderService
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Order {

    private final int totalPrice;

    Order(int totalPrice) {
      this.totalPrice = totalPrice;
    }

    int totalPrice() { return totalPrice; }
  }

  interface OrderRepository {
    void save(Order order);
  }

  interface Logger {
    void info(String message);
    void error(String message, Exception e);
  }

  interface Transaction {
    void commit();
    void rollback();
  }

  interface TransactionManager {
    Transaction begin();
  }

  // Bad: 주문 처리 로직에 로깅, 트랜잭션 처리가 섞여 있다
  //   - 다른 서비스에도 같은 코드가 반복된다
  //   - 트랜잭션 정책이 바뀌면 여러 서비스 클래스를 수정해야 한다
  //   - 핵심 비즈니스 로직인 repository.save(order)가 부가 코드에 묻힌다
  static class BadOrderService {

    private final OrderRepository repository;
    private final Logger logger;

    BadOrderService(OrderRepository repository, Logger logger) {
      this.repository = repository;
      this.logger = logger;
    }

    public void place(Order order) {
      logger.info("start place order");

      // 트랜잭션 관리를 내부에서 직접 처리 — 생성과 비즈니스 로직이 섞임
      Transaction transaction = beginTransaction();

      try {
        repository.save(order);
        transaction.commit();

        logger.info("order placed");
      } catch (Exception e) {
        transaction.rollback();
        logger.error("order failed", e);
        throw e;
      }
    }

    // 트랜잭션 시작을 직접 책임진다 — TransactionManager.begin() 역할을 내부에서 수행
    private Transaction beginTransaction() {
      System.out.println("transaction begin");
      return new Transaction() {
        @Override
        public void commit() { System.out.println("transaction commit"); }

        @Override
        public void rollback() { System.out.println("transaction rollback"); }
      };
    }
  }

  // Good: OrderService는 주문 처리만 한다
  //   - 비즈니스 로직이 단순하게 유지된다
  //   - 트랜잭션이나 로깅 정책이 바뀌어도 이 클래스는 수정하지 않는다
  static class OrderService {

    private final OrderRepository repository;

    OrderService(OrderRepository repository) {
      this.repository = repository;
    }

    public void place(Order order) {
      repository.save(order);
    }
  }

  // Good: 트랜잭션과 로깅은 별도 객체가 감싼다
  //   - 비즈니스 로직과 기술 관심사가 분리된다
  //   - 시스템이 커져도 핵심 로직은 단순하게 유지된다
  //   - 트랜잭션 정책 변경은 이 클래스 하나만 수정한다
  static class TransactionalOrderService {

    private final OrderService orderService;
    private final TransactionManager transactionManager;
    private final Logger logger;

    TransactionalOrderService(
        OrderService orderService,
        TransactionManager transactionManager,
        Logger logger) {
      this.orderService = orderService;
      this.transactionManager = transactionManager;
      this.logger = logger;
    }

    public void place(Order order) {
      logger.info("start place order");

      Transaction transaction = transactionManager.begin();

      try {
        orderService.place(order);
        transaction.commit();

        logger.info("order placed");
      } catch (Exception e) {
        transaction.rollback();
        logger.error("order failed", e);
        throw e;
      }
    }
  }
}
