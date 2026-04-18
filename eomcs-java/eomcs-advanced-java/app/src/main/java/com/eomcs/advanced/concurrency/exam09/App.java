package com.eomcs.advanced.concurrency.exam09;

import java.time.Duration;
import reactor.core.publisher.Mono;

// Spring WebFlux의 비동기 예외 처리 - Mono 기본:
//
// WebFlux는 요청 처리 결과를 Mono(0~1개 결과) / Flux(0~N개 결과)로 표현한다.
// 비동기 작업 도중 발생한 예외는 즉시 throw되는 것이 아니라, 스트림의 error signal로 전달된다.
//
// 주요 연산자:
//   onErrorReturn(value)
//     - 예외가 발생하면 고정된 대체 값을 반환한다. (가장 단순한 복구)
//
//   onErrorResume(type, function)
//     - 특정 예외 타입이 발생하면 다른 Mono로 전환해 복구한다.
//     - function이 반환하는 Mono가 새로운 데이터 소스가 된다.
//
//   onErrorMap(type, function)
//     - 예외를 다른 예외로 변환한다. 복구하지 않고 예외 타입만 바꾼다. (App2 참고)
//
//   doOnError(consumer)
//     - 예외를 관찰하고 로그를 남긴다. 예외를 복구하지는 않는다.
//
// Mono.error(exception) vs throw:
//   Mono.error() : 스트림 파이프라인 안에서 error signal을 발생시킨다.
//   throw        : 구독(subscribe/block) 전에 예외를 즉시 던진다.
//
// block():
//   - 리액티브 스트림을 명령형 코드처럼 블로킹으로 실행한다.
//   - 실제 WebFlux 서버에서는 사용하지 않으며, 테스트나 main에서만 사용한다.

public class App {

  public static void main(String[] args) {

    System.out.println("[기본] Mono 비동기 예외 처리");
    System.out.println("WebFlux에서는 비동기 작업 실패가 error signal로 전달된다.");
    System.out.println();

    Mono<Order> orderMono =
        findOrder("order-404")
            // doOnError(): 예외를 관찰(로그)한다. 예외를 삼키지 않고 그대로 전파한다.
            .doOnError(ex -> System.out.printf("  로그: 주문 조회 실패 - %s%n", ex.getMessage()))
            // onErrorResume(): OrderNotFoundException이 발생하면 대체 Mono로 전환한다.
            // - doOnError() 이후에 위치하므로 doOnError가 먼저 관찰한 뒤 복구한다.
            // - 다른 예외 타입(RuntimeException 등)은 이 연산자를 통과해 그대로 전파된다.
            .onErrorResume(
                OrderNotFoundException.class,
                ex -> {
                  System.out.println("  복구: 기본 주문 객체를 반환");
                  return Mono.just(new Order("unknown", "기본 상품", 0));
                });

    // block(): 스트림을 구독하고 결과가 나올 때까지 현재 스레드를 블로킹한다.
    // - onErrorResume()이 항상 대체 Mono를 반환하므로 null이 아님이 보장된다.
    // - 단, 정적 분석 도구(SonarQube 등)는 block()이 잠재적으로 null을 반환할 수 있다고 경고한다.
    Order order = orderMono.block();

    assert order != null; // onErrorResume이 항상 대체 Order를 제공하므로 null 불가
    System.out.printf("  결과: %s / %s / %,d원%n", order.id(), order.productName(), order.amount());
    System.out.println("→ doOnError()는 관찰만 하고, onErrorResume()이 대체 Mono로 복구한다.");
  }

  private static Mono<Order> findOrder(String orderId) {
    // Mono.delay(): 지정 시간 후에 신호를 발생시킨다. DB/API 지연 시뮬레이션.
    return Mono.delay(Duration.ofMillis(300))
        .flatMap(
            ignored -> {
              // 특정 ID이면 error signal로 전환한다. (throw 대신 Mono.error 사용)
              if (orderId.equals("order-404")) {
                return Mono.error(new OrderNotFoundException(orderId + " 주문 없음"));
              }
              return Mono.just(new Order(orderId, "키보드", 50_000));
            });
  }

  record Order(String id, String productName, int amount) {}

  static class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(String message) {
      super(message);
    }
  }
}
