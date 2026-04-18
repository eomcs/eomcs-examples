package com.eomcs.advanced.concurrency.exam09;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

// timeout / retry / fallback:
//
// WebFlux에서 외부 API나 데이터베이스 호출은 지연되거나 실패할 수 있다.
// 이런 비동기 실패는 timeout(), retryWhen(), onErrorResume()으로 처리한다.
//
// timeout(duration)
//   - 지정 시간 안에 결과가 오지 않으면 TimeoutException을 error signal로 보낸다.
//   - timeout은 retryWhen()보다 앞에 위치해야 각 시도마다 타임아웃이 적용된다.
//
// retryWhen(retrySpec)
//   - error signal이 발생하면 정해진 정책에 따라 소스를 재구독한다.
//   - Retry.fixedDelay(N, duration): N회, 매 시도 사이 duration만큼 대기
//   - Mono.defer()로 소스를 감싸야 재구독 시 작업이 새로 실행된다.
//
// onErrorResume()
//   - 모든 재시도가 소진된 뒤에도 실패하면 fallback 결과를 반환한다.
//
// Mono.defer():
//   - 구독할 때마다 새로운 Mono를 생성한다.
//   - defer 없이 Mono.just()나 Mono.error()를 직접 쓰면 한 번만 생성되어 재시도가 되지 않는다.
//
// 연산자 적용 순서: callPaymentServer() → timeout() → retryWhen() → onErrorResume()
//   실패 시 흐름: error signal → timeout 감지 → retry 재구독 → 모두 실패 시 fallback

public class App3 {

  public static void main(String[] args) {

    System.out.println("[외부 호출] timeout() / retryWhen() / fallback");
    System.out.println("느리거나 실패하는 비동기 작업을 재시도하고, 끝내 실패하면 대체 값을 반환한다.");
    System.out.println();

    // AtomicInteger: 재시도할 때마다 시도 횟수를 스레드 안전하게 증가시킨다.
    AtomicInteger attempts = new AtomicInteger();

    Mono<String> result =
        callPaymentServer(attempts)        // Mono.defer()로 매 구독마다 새로 호출된다.
            // timeout(): 각 시도에서 500ms 이내 결과가 없으면 TimeoutException을 발생시킨다.
            // - retryWhen()보다 앞에 위치해야 각 재시도에 개별 타임아웃이 적용된다.
            .timeout(Duration.ofMillis(500))
            // retryWhen(): error signal 발생 시 최대 2회, 200ms 간격으로 재구독한다.
            // - 1회차(attempt=1): 오류 → retryWhen이 재구독
            // - 2회차(attempt=2): 오류 → retryWhen이 재구독
            // - 3회차(attempt=3): 800ms 지연 → timeout 초과 → 모든 재시도 소진
            .retryWhen(
                Retry.fixedDelay(2, Duration.ofMillis(200))
                    .doBeforeRetry(
                        signal ->
                            System.out.printf(
                                "  재시도 #%d: %s%n",
                                signal.totalRetries() + 1,
                                signal.failure().getMessage())))
            // onErrorResume(): 모든 재시도가 소진된 뒤의 최종 fallback
            .onErrorResume(
                ex -> {
                  // ex: 마지막 실패 예외 (TimeoutException 또는 RetryExhaustedException)
                  System.out.printf("  fallback 실행: %s%n", ex.getClass().getSimpleName());
                  return Mono.just("결제 대기 상태로 저장");
                });

    System.out.println("  최종 결과: " + result.block());
    System.out.println("→ timeout과 retry를 통과하지 못한 예외는 fallback으로 복구할 수 있다.");
  }

  private static Mono<String> callPaymentServer(AtomicInteger attempts) {
    // Mono.defer(): 구독할 때마다 람다를 새로 실행해 새 Mono를 만든다.
    // - defer 없이 Mono.error()를 직접 쓰면 retryWhen이 재구독해도 같은 Mono 인스턴스를 받아
    //   시도 횟수가 증가하지 않는다.
    return Mono.defer(
        () -> {
          int attempt = attempts.incrementAndGet(); // 매 구독마다 카운트 증가
          System.out.printf("  결제 서버 호출 #%d%n", attempt);

          if (attempt < 3) {
            // 1, 2회차: 100ms 뒤에 오류를 발생시킨다. → timeout(500ms) 이내이므로 TimeoutException 아님
            return Mono.delay(Duration.ofMillis(100))
                .then(Mono.error(new IllegalStateException("결제 서버 일시 오류")));
          }

          // 3회차: 800ms 지연 → timeout(500ms) 초과 → TimeoutException → fallback 실행
          return Mono.delay(Duration.ofMillis(800)).thenReturn("결제 승인 완료");
        });
  }
}
