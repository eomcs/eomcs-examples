package com.eomcs.cleancode.ch11.exam01;

// 예제 2: Main 분리 - 객체 생성과 조립은 main에 모아라
public class BadAndGood2 {

  private BadAndGood2() {}

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

  static class MySqlOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
      System.out.println("save order to mysql");
    }
  }

  interface PaymentGateway {
    void pay(int amount);
  }

  static class KakaoPayGateway implements PaymentGateway {

    @Override
    public void pay(int amount) {
      System.out.println("Pay with KakaoPay: " + amount);
    }
  }

  static class OrderService {

    private final OrderRepository repository;
    private final PaymentGateway paymentGateway;

    OrderService(OrderRepository repository, PaymentGateway paymentGateway) {
      this.repository = repository;
      this.paymentGateway = paymentGateway;
    }

    public void place(Order order) {
      paymentGateway.pay(order.totalPrice());
      repository.save(order);
    }
  }

  // Bad: Application 실행 로직 안에 생성 코드가 섞여 있다
  //   - 실행 흐름을 읽다가 객체 조립 세부사항까지 봐야 한다
  //   - 저장소나 결제 구현을 바꾸면 실행 코드도 수정된다
  static class BadApplication {

    public void run() {
      OrderRepository repository = new MySqlOrderRepository(); // 생성이 실행 코드 안에 섞임
      PaymentGateway paymentGateway = new KakaoPayGateway();
      OrderService orderService = new OrderService(repository, paymentGateway);

      orderService.place(new Order(10_000));
    }
  }

  // Good: Application은 실행만 담당한다
  //   - MySqlOrderRepository, KakaoPayGateway를 모른다
  //   - 생성 방향의 의존성이 애플리케이션 내부로 침투하지 않는다
  static class Application {

    private final OrderService orderService;

    Application(OrderService orderService) {
      this.orderService = orderService;
    }

    public void run() {
      orderService.place(new Order(10_000));
    }
  }

  // Good: AppMain은 객체 생성과 조립을 담당한다
  //   - 생성 세부사항이 한 곳에 모인다
  //   - Application은 이미 준비된 객체를 받아 실행만 한다
  static class AppMain {

    static void start() {
      OrderRepository repository = new MySqlOrderRepository();
      PaymentGateway paymentGateway = new KakaoPayGateway();
      OrderService orderService = new OrderService(repository, paymentGateway);

      Application application = new Application(orderService);
      application.run();
    }
  }
}
