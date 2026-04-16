package com.eomcs.quickstart.concurrency.exam08;

import java.util.concurrent.CompletableFuture;

// CompletableFuture 기본:
//
// CompletableFuture는 비동기 작업의 결과를 표현하는 객체다.
// Future와 달리 결과가 준비된 뒤 실행할 후속 작업을 체인으로 연결할 수 있다.
//
// 주요 생성 메서드:
//   runAsync(Runnable)
//     - 결과 없는 작업을 비동기로 실행한다. 반환 타입: CompletableFuture<Void>
//
//   supplyAsync(Supplier<T>)
//     - 결과를 만드는 작업을 비동기로 실행한다. 반환 타입: CompletableFuture<T>
//
// 기본 실행 위치:
//   - 별도 Executor를 지정하지 않으면 ForkJoinPool.commonPool()을 사용한다.
//   - 특정 풀에서 실행하려면 두 번째 인자로 Executor를 전달한다.
//
// join() vs get():
//   join() : 완료 시 결과 반환. 예외는 unchecked인 CompletionException으로 감싼다.
//   get()  : 완료 시 결과 반환. 예외는 checked인 ExecutionException으로 감싼다.
//   → CompletableFuture 체이닝에서는 join()을 주로 사용한다.
//
// Future(exam06/App2) vs CompletableFuture:
//   Future            : get()으로 결과만 꺼낸다. 후속 작업 연결 불가.
//   CompletableFuture : thenApply/thenAccept 등으로 후속 작업을 체인으로 연결할 수 있다.

public class App {

  public static void main(String[] args) {

    System.out.println("[기본] runAsync() / supplyAsync()");
    System.out.println("작업을 비동기로 실행하고, main 스레드는 결과를 나중에 받는다.");
    System.out.println();

    // runAsync(): 결과 없는 Runnable을 비동기로 제출한다.
    // - 이 시점에 작업이 즉시 백그라운드에서 시작된다.
    // - main 스레드는 블로킹 없이 바로 다음 줄로 진행한다.
    CompletableFuture<Void> logFuture =
        CompletableFuture.runAsync(
            () -> {
              sleep(300); // 로그 기록 시뮬레이션
              System.out.printf("  로그 저장 완료 (%s)%n", Thread.currentThread().getName());
            });

    // supplyAsync(): T 타입 결과를 만드는 Supplier를 비동기로 제출한다.
    // - logFuture와 priceFuture는 동시에 진행된다. (독립적인 두 비동기 작업)
    CompletableFuture<Integer> priceFuture =
        CompletableFuture.supplyAsync(
            () -> {
              sleep(500); // 가격 조회 시뮬레이션
              System.out.printf("  상품 가격 조회 완료 (%s)%n", Thread.currentThread().getName());
              return 15_000;
            });

    // main 스레드는 백그라운드 작업과 독립적으로 자신의 작업을 계속 진행한다.
    System.out.println("  Main: 다른 작업 수행");

    // join(): 해당 작업이 완료될 때까지 현재 스레드(main)를 블로킹한다.
    // - logFuture는 300ms, priceFuture는 500ms이므로 순서대로 join해도 총 500ms만 기다린다.
    logFuture.join();            // CompletableFuture<Void>: 반환값 없음
    int price = priceFuture.join(); // CompletableFuture<Integer>: Integer 반환

    System.out.printf("  Main: 상품 가격 = %,d원%n", price);
    System.out.println("→ join()은 작업이 끝날 때까지 기다린 뒤 결과를 반환한다.");
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
