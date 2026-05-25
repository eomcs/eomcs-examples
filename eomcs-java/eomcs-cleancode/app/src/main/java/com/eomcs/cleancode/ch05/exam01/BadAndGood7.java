package com.eomcs.cleancode.ch05.exam01;

// 예제 5: 세로 순서 (Vertical Ordering)
public class BadAndGood7 {

  private BadAndGood7() {}

  static class Payment {
    long getId() { return 1L; }
    int getAmount() { return 10000; }
  }

  static class PaymentRepository {
    void save(Payment p) { System.out.println("결제 저장: " + p.getId()); }
  }

  static class Logger {
    void info(String msg) { System.out.println("[INFO] " + msg); }
  }

  // Bad: 세부 함수가 파일 상단에 먼저 나온다.
  // - writeLog(), savePayment()가 핵심 함수인 pay()보다 앞에 정의되어 있다.
  // - 핵심 public 함수인 pay()가 중간에 숨어 있다.
  // - 위에서 아래로 흐름을 읽기 어렵다.
  static class BadPaymentService {
    PaymentRepository paymentRepository = new PaymentRepository();
    Logger logger = new Logger();

    private void writeLog(Payment payment) {
      logger.info("paid: " + payment.getId());
    }

    private void savePayment(Payment payment) {
      paymentRepository.save(payment);
    }

    public void pay(Payment payment) {
      validatePayment(payment);
      savePayment(payment);
      writeLog(payment);
    }

    private void validatePayment(Payment payment) {
      if (payment.getAmount() <= 0) {
        throw new IllegalArgumentException();
      }
    }
  }

  // Good: 가장 중요한 pay()가 파일 맨 위에 나온다.
  // - 아래로 내려가며 세부 함수가 차례로 등장한다.
  // - 중요도 순서에 따라 위에서 아래로 자연스럽게 읽힌다.
  // - Stepdown Rule과 Newspaper Metaphor가 함께 적용된 예다.
  static class GoodPaymentService {
    PaymentRepository paymentRepository = new PaymentRepository();
    Logger logger = new Logger();

    public void pay(Payment payment) {
      validatePayment(payment);
      savePayment(payment);
      writeLog(payment);
    }

    private void validatePayment(Payment payment) {
      if (payment.getAmount() <= 0) {
        throw new IllegalArgumentException();
      }
    }

    private void savePayment(Payment payment) {
      paymentRepository.save(payment);
    }

    private void writeLog(Payment payment) {
      logger.info("paid: " + payment.getId());
    }
  }
}
