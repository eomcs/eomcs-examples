package com.eomcs.advanced.concurrency.exam08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 여러 비동기 작업 조합:
//
// thenCombine(other, BiFunction)
//   - 서로 독립적인 두 작업의 결과를 합쳐 새 결과를 만든다.
//   - 두 작업이 모두 완료되면 BiFunction이 실행된다.
//
// allOf(futures...)
//   - 여러 CompletableFuture가 모두 끝날 때까지 기다린다. 반환: CompletableFuture<Void>
//   - allOf() 자체는 개별 결과를 모아 주지 않으므로, 각 future에서 join()으로 꺼낸다.
//   - allOf().join() 이후 각 future.join()은 이미 완료 상태이므로 즉시 반환된다.
//
// anyOf(futures...)
//   - 여러 CompletableFuture 중 하나라도 먼저 끝나면 완료된다. 반환: CompletableFuture<Object>
//   - 반환 타입이 Object이므로 실제 타입으로 캐스팅해야 한다.
//   - 나머지 완료되지 않은 작업은 계속 실행된다 (취소되지 않음).
//
// 사용 지침:
//   thenCombine : 정확히 두 결과를 합칠 때
//   allOf       : N개 결과를 모두 기다린 뒤 처리할 때
//   anyOf       : N개 중 가장 빠른 응답만 필요할 때 (중복 요청 패턴)

public class App3 {

  public static void main(String[] args) {

    System.out.println("[조합] thenCombine() / allOf() / anyOf()");
    System.out.println("독립적인 비동기 작업을 동시에 실행하고 결과를 합친다.");
    System.out.println();

    try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
      demoThenCombine(executor);
      System.out.println();
      demoAllOf(executor);
      System.out.println();
      demoAnyOf(executor);
    }
  }

  private static void demoThenCombine(ExecutorService executor) {
    System.out.println("[데모 1] thenCombine() - 두 결과 합치기");
    System.out.println("상품 가격(500ms)과 배송비(300ms)를 동시에 조회해 합산한다.");

    // 두 supplyAsync는 독립적으로 동시에 실행된다.
    CompletableFuture<Integer> productPrice =
        CompletableFuture.supplyAsync(() -> loadProductPrice("book-1"), executor);

    CompletableFuture<Integer> deliveryFee =
        CompletableFuture.supplyAsync(() -> loadDeliveryFee("Seoul"), executor);

    // thenCombine(): 두 Future가 모두 완료되면 BiFunction을 적용해 새 결과를 만든다.
    // - 총 대기 시간: max(500ms, 300ms) = 500ms (직렬 합산인 800ms보다 빠르다)
    // - Integer::sum은 Integer가 null이면 NullPointerException이 발생할 수 있으므로
    //   IDE에서 null 타입 안전성 경고를 표시한다. 실행상 문제는 없다.
    CompletableFuture<Integer> total =
        productPrice.thenCombine(deliveryFee, Integer::sum);

    System.out.printf("  결제 금액: %,d원%n", total.join());
  }

  private static void demoAllOf(ExecutorService executor) {
    System.out.println("[데모 2] allOf() - 모든 작업 완료 대기");
    System.out.println("쿠폰(300ms)/포인트(500ms)/이벤트(200ms) 할인을 동시에 조회해 합산한다.");

    // 세 작업이 동시에 시작된다.
    CompletableFuture<Integer> coupon =
        CompletableFuture.supplyAsync(() -> loadDiscount("coupon", 300, 2_000), executor);
    CompletableFuture<Integer> point =
        CompletableFuture.supplyAsync(() -> loadDiscount("point", 500, 1_500), executor);
    CompletableFuture<Integer> event =
        CompletableFuture.supplyAsync(() -> loadDiscount("event", 200, 3_000), executor);

    // allOf(): 전달된 모든 Future가 완료될 때까지 대기하는 CompletableFuture<Void>를 반환한다.
    // - 개별 결과를 모아 주지 않으므로, 각 future에서 직접 join()으로 꺼내야 한다.
    CompletableFuture<Void> all = CompletableFuture.allOf(coupon, point, event);

    // 세 작업 중 가장 느린 포인트(500ms)가 끝날 때까지 대기한다.
    all.join();

    // all.join() 이후에는 세 future 모두 완료 상태이므로 join()이 즉시 반환된다.
    int totalDiscount = coupon.join() + point.join() + event.join();
    System.out.printf("  전체 할인 금액: %,d원%n", totalDiscount);
  }

  private static void demoAnyOf(ExecutorService executor) {
    System.out.println("[데모 3] anyOf() - 가장 먼저 끝난 결과 사용");
    System.out.println("A(600ms), B(250ms), C(450ms) 서버에 동시 요청 → 가장 빠른 B를 사용한다.");

    // 세 서버에 동시 요청을 보낸다. (중복 요청/레이스 패턴)
    CompletableFuture<String> serverA =
        CompletableFuture.supplyAsync(() -> requestServer("A", 600), executor);
    CompletableFuture<String> serverB =
        CompletableFuture.supplyAsync(() -> requestServer("B", 250), executor);
    CompletableFuture<String> serverC =
        CompletableFuture.supplyAsync(() -> requestServer("C", 450), executor);

    // anyOf(): 전달된 Future 중 하나라도 완료되면 즉시 그 결과를 가진 CompletableFuture<Object>를 반환한다.
    // - 반환 타입이 Object이므로 실제 타입(String)으로 캐스팅해야 한다.
    // - 나머지 서버(A, C)는 취소되지 않고 계속 실행된다.
    Object fastest = CompletableFuture.anyOf(serverA, serverB, serverC).join();
    System.out.printf("  가장 빠른 응답: %s%n", fastest);
  }

  private static int loadProductPrice(String productId) {
    sleep(500);
    System.out.printf("  상품 가격 조회: %s%n", productId);
    return 25_000;
  }

  private static int loadDeliveryFee(String region) {
    sleep(300);
    System.out.printf("  배송비 조회: %s%n", region);
    return 3_000;
  }

  private static int loadDiscount(String name, int delayMs, int amount) {
    sleep(delayMs);
    System.out.printf("  %s 할인 조회: %,d원%n", name, amount);
    return amount;
  }

  private static String requestServer(String serverName, int delayMs) {
    sleep(delayMs);
    return "Server-" + serverName + " 응답 (" + delayMs + "ms)";
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
