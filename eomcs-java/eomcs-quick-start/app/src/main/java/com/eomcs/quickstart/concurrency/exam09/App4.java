package com.eomcs.quickstart.concurrency.exam09;

import java.time.Duration;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Flux에서 항목별 예외 처리:
//
// Flux 전체에 onErrorResume()을 붙이면 한 항목의 실패가 전체 스트림 실패로 이어진 뒤 fallback으로 전환된다.
// 여러 항목을 독립적으로 처리해야 한다면 flatMap() 내부에서 각 항목마다 onErrorResume()을 적용한다.
//
// 이 방식은 WebFlux에서 여러 요청, 여러 메시지, 여러 DB row를 처리할 때 유용하다.
//
// Flux 전체 오류 처리 vs 항목별 오류 처리:
//
//   [전체] Flux.fromIterable(ids)
//             .flatMap(id -> createDelivery(id))  // 실패하면 Flux 전체가 종료된다
//             .onErrorResume(...)                 // 나머지 항목은 처리되지 않는다
//
//   [항목별] Flux.fromIterable(ids)
//               .flatMap(id -> createDelivery(id)
//                   .onErrorResume(...))          // 해당 항목만 복구, Flux는 계속된다
//
// flatMap(): 각 항목을 Mono/Flux로 변환하고, 결과를 하나의 Flux로 병합한다.
//   - concatMap()은 순서를 보장하지만, flatMap()은 완료 순서대로 병합한다.
//
// collectList(): Flux의 모든 결과를 List로 모아 Mono<List<T>>로 반환한다.

public class App4 {

  public static void main(String[] args) {

    System.out.println("[Flux] 항목별 비동기 예외 처리");
    System.out.println("한 항목이 실패해도 나머지 항목 처리는 계속 진행한다.");
    System.out.println();

    List<String> orderIds = List.of("order-1", "bad-order", "order-2", "order-3");

    // Flux.fromIterable(): 컬렉션의 각 항목을 순서대로 발행하는 Flux를 생성한다.
    List<DeliveryResult> results =
        Flux.fromIterable(orderIds)
            // flatMap(): 각 orderId를 createDelivery()로 변환하고 결과를 병합한다.
            // - 여기서 onErrorResume()을 flatMap 내부에 적용해야 항목별로 독립적으로 처리된다.
            // - Flux 외부에 onErrorResume()을 붙이면 첫 실패 시 스트림 전체가 종료된다.
            .flatMap(
                orderId ->
                    createDelivery(orderId)
                        // 항목별 onErrorResume(): 이 항목만 실패 결과로 복구하고, Flux는 계속된다.
                        .onErrorResume(
                            ex -> {
                              System.out.printf("  %s 처리 실패: %s%n", orderId, ex.getMessage());
                              return Mono.just(DeliveryResult.failed(orderId));
                            }))
            // collectList(): Flux의 모든 DeliveryResult를 List로 모아 Mono<List<DeliveryResult>>를 만든다.
            .collectList()
            // block(): collectList() 결과를 동기적으로 받는다.
            // - collectList()는 Flux가 완료될 때 항상 List를 발행하므로 null이 반환되지 않는다.
            // - 단, 정적 분석 도구(SonarQube 등)는 block()이 잠재적으로 null을 반환할 수 있다고 경고한다.
            .block();

    assert results != null; // collectList()는 빈 Flux여도 빈 List를 발행하므로 null 불가
    results.forEach(
        result ->
            System.out.printf("  결과: %s → %s%n", result.orderId(), result.status()));

    System.out.println("→ 항목 내부에서 복구하면 Flux 전체가 중단되지 않는다.");
  }

  private static Mono<DeliveryResult> createDelivery(String orderId) {
    // 200ms 지연 후 배송 생성을 시도한다. (외부 배송 API 호출 시뮬레이션)
    return Mono.delay(Duration.ofMillis(200))
        .flatMap(
            ignored -> {
              // "bad-order"는 주소 정보가 없어 error signal을 발생시킨다.
              // - 이 Mono를 받는 flatMap 내부의 onErrorResume()이 이 오류를 항목별로 처리한다.
              if (orderId.equals("bad-order")) {
                return Mono.error(new IllegalArgumentException("주소 정보 없음"));
              }
              return Mono.just(DeliveryResult.success(orderId));
            });
  }

  record DeliveryResult(String orderId, String status) {
    static DeliveryResult success(String orderId) {
      return new DeliveryResult(orderId, "배송 접수");
    }

    static DeliveryResult failed(String orderId) {
      return new DeliveryResult(orderId, "배송 보류");
    }
  }
}
