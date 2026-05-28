package com.eomcs.cleancode.ch08.exam02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 학습 테스트 예제 2: 외부 결제 API 동작 방식을 운영 코드가 아닌 테스트로 먼저 탐색한다.
// - 요청 필드 중 무엇이 필수인지 테스트로 확인한다.
// - 응답 형태를 서비스 코드에 섞지 않고 테스트에서 먼저 검증한다.
// - 라이브러리 변경 시 이 테스트가 경고 역할을 한다.
class PaymentClientLearningTest {

  // 외부 결제 API 시뮬레이션 (서드파티 라이브러리 역할)
  static class PaymentRequest {
    private int amount;
    private String customerId;

    void setAmount(int amount) { this.amount = amount; }
    void setCustomerId(String customerId) { this.customerId = customerId; }
    int getAmount() { return amount; }
    String getCustomerId() { return customerId; }
  }

  static class PaymentResponse {
    private final boolean success;
    private final String message;

    PaymentResponse(boolean success, String message) {
      this.success = success;
      this.message = message;
    }

    boolean isSuccess() { return success; }
    String getMessage() { return message; }
  }

  static class ExternalPaymentClient {
    public PaymentResponse execute(PaymentRequest request) {
      if (request.getCustomerId() == null || request.getCustomerId().isEmpty()) {
        return new PaymentResponse(false, "고객 ID가 없습니다.");
      }
      if (request.getAmount() <= 0) {
        return new PaymentResponse(false, "결제 금액이 올바르지 않습니다.");
      }
      return new PaymentResponse(true, "결제 성공");
    }
  }

  @Test
  void 결제_승인_요청은_금액과_고객ID가_필요하다() {
    ExternalPaymentClient client = new ExternalPaymentClient();

    PaymentRequest request = new PaymentRequest();
    request.setAmount(10_000);
    request.setCustomerId("C-100");

    PaymentResponse response = client.execute(request);

    assertTrue(response.isSuccess());
  }

  @Test
  void 고객ID가_없으면_결제_승인이_실패한다() {
    ExternalPaymentClient client = new ExternalPaymentClient();

    PaymentRequest request = new PaymentRequest();
    request.setAmount(10_000);
    // customerId 미설정

    PaymentResponse response = client.execute(request);

    assertFalse(response.isSuccess());
  }

  @Test
  void 금액이_0이하이면_결제_승인이_실패한다() {
    ExternalPaymentClient client = new ExternalPaymentClient();

    PaymentRequest request = new PaymentRequest();
    request.setAmount(0);
    request.setCustomerId("C-100");

    PaymentResponse response = client.execute(request);

    assertFalse(response.isSuccess());
  }
}
