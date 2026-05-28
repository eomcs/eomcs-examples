package com.eomcs.cleancode.ch11.exam02;

// 예제 2: 횡단 관심사 - PaymentService / SecurityProxy / TransactionProxy
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Payment {

    private final int amount;

    Payment(int amount) {
      this.amount = amount;
    }

    int amount() { return amount; }
  }

  // 보안 컨텍스트: 현재 사용자 정보를 제공하는 정적 유틸리티
  static class UserPrincipal {

    private final String role;

    UserPrincipal(String role) {
      this.role = role;
    }

    boolean hasRole(String requiredRole) {
      return this.role.equals(requiredRole);
    }
  }

  static class SecurityContext {

    private static UserPrincipal currentUser = new UserPrincipal("PAYMENT");

    static UserPrincipal currentUser() {
      return currentUser;
    }

    // 테스트/시뮬레이션용: 현재 사용자를 교체한다
    static void setCurrentUser(UserPrincipal user) {
      currentUser = user;
    }
  }

  // Bad: 결제 로직보다 보안, 트랜잭션, 성능 측정 코드가 더 많다
  //   - pay()의 핵심 의도가 흐려진다
  //   - 다른 메서드에도 같은 코드가 복사된다
  //   - 보안 정책이 바뀌면 여러 곳을 수정해야 한다
  static class BadPaymentService {

    public void pay(Payment payment) {
      if (!SecurityContext.currentUser().hasRole("PAYMENT")) {
        throw new SecurityException("no permission");
      }

      long start = System.currentTimeMillis();

      try {
        System.out.println("transaction begin");

        System.out.println("pay: " + payment.amount()); // 핵심 로직

        System.out.println("transaction commit");
      } catch (Exception e) {
        System.out.println("transaction rollback");
        throw e;
      } finally {
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("elapsed = " + elapsed);
      }
    }
  }

  // Good: 핵심 비즈니스 로직만 남긴다
  //   - 결제 로직은 결제만 담당한다
  //   - 단독으로 테스트하기 쉽다
  static class PaymentService {

    public void pay(Payment payment) {
      System.out.println("pay: " + payment.amount());
    }
  }

  // Good: 보안 관심사를 별도 객체로 분리한다
  //   - 보안 정책이 바뀌면 이 클래스만 수정한다
  //   - PaymentService는 보안을 모른다
  static class SecurityProxy {

    private final PaymentService paymentService;

    SecurityProxy(PaymentService paymentService) {
      this.paymentService = paymentService;
    }

    public void pay(Payment payment) {
      if (!SecurityContext.currentUser().hasRole("PAYMENT")) {
        throw new SecurityException("no permission");
      }

      paymentService.pay(payment);
    }
  }

  // Good: 트랜잭션 관심사를 별도 객체로 분리한다
  //   - 트랜잭션 정책이 바뀌면 이 클래스만 수정한다
  //   - SecurityProxy는 트랜잭션을 모른다
  static class TransactionProxy {

    private final SecurityProxy paymentService;

    TransactionProxy(SecurityProxy paymentService) {
      this.paymentService = paymentService;
    }

    public void pay(Payment payment) {
      System.out.println("transaction begin");

      try {
        paymentService.pay(payment);
        System.out.println("transaction commit");
      } catch (Exception e) {
        System.out.println("transaction rollback");
        throw e;
      }
    }
  }

  // 조립 예시
  //   PaymentService paymentService = new PaymentService();
  //   SecurityProxy securityProxy = new SecurityProxy(paymentService);
  //   TransactionProxy transactionProxy = new TransactionProxy(securityProxy);
  //   transactionProxy.pay(payment);
}
