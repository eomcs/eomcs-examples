package com.eomcs.advanced.concurrency.exam08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 결과 변환과 후속 작업 연결:
//
// thenApply(Function<T,U>)
//   - 이전 결과(T)를 받아 새 결과(U)로 변환한다. 반환: CompletableFuture<U>
//
// thenAccept(Consumer<T>)
//   - 이전 결과(T)를 받아 소비한다. 반환: CompletableFuture<Void>
//
// thenRun(Runnable)
//   - 이전 결과를 받지 않고 다음 작업만 실행한다. 반환: CompletableFuture<Void>
//
// thenApply vs thenApplyAsync:
//   thenApply()      : 이전 단계와 같은 스레드(또는 main 스레드)에서 실행될 수 있다.
//   thenApplyAsync() : 반드시 별도 스레드(Executor)에서 실행한다.
//   → Executor를 지정하지 않으면 commonPool을 사용한다.
//
// 체이닝 규칙:
//   thenApply()는 CompletableFuture<U>를 반환하므로, 다시 thenApply/thenAccept/thenRun을 붙일 수 있다.
//   thenAccept()는 CompletableFuture<Void>를 반환하므로, 결과가 필요 없는 thenRun만 자연스럽다.

public class App2 {

  public static void main(String[] args) {

    System.out.println("[체이닝] thenApply() / thenAccept() / thenRun()");
    System.out.println("회원 조회 → 등급 할인 적용 → 결제 금액 출력 순서로 이어 붙인다.");
    System.out.println();

    try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
      CompletableFuture<Void> future =
          // 1단계: 회원 조회 (executor 스레드에서 비동기 실행)
          CompletableFuture.supplyAsync(() -> findMember("user-100"), executor)
              // 2단계: 조회된 Member를 받아 할인 금액(Integer)으로 변환한다.
              // - 이전 단계(supplyAsync)가 완료되면 자동으로 실행된다.
              // - thenApply()이므로 동일 스레드(executor)에서 실행될 수 있다.
              .thenApply(App2::applyGradeDiscount)
              // 3단계: 할인 금액을 받아 출력한다. 반환값 없음(Void).
              .thenAccept(
                  amount ->
                      System.out.printf(
                          "  최종 결제 금액: %,d원 (%s)%n",
                          amount, Thread.currentThread().getName()))
              // 4단계: 이전 결과와 무관하게 종료 메시지만 출력한다.
              // - thenRun()은 이전 단계 결과를 받지 않는다.
              .thenRun(() -> System.out.println("  주문 처리 흐름 종료"));

      // 체이닝의 마지막 CompletableFuture<Void>가 완료될 때까지 대기한다.
      future.join();
    }

    System.out.println("→ thenApply()는 결과를 변환하고, thenAccept()는 결과를 소비한다.");
  }

  private static Member findMember(String memberId) {
    sleep(300);
    System.out.printf("  회원 조회: %s (%s)%n", memberId, Thread.currentThread().getName());
    return new Member(memberId, "GOLD", 30_000);
  }

  private static int applyGradeDiscount(Member member) {
    sleep(300);
    int discountRate = member.grade().equals("GOLD") ? 20 : 0;
    int amount = member.orderAmount() * (100 - discountRate) / 100;
    System.out.printf("  %s 등급 %d%% 할인 적용%n", member.grade(), discountRate);
    return amount;
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  record Member(String id, String grade, int orderAmount) {}
}
