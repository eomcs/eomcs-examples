package com.eomcs.quickstart.concurrency.exam06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// 여러 작업의 결과 수집:
//
// 1. invokeAll(tasks)
//    - 모든 작업이 끝날 때까지 현재 스레드를 블로킹한다.
//    - 반환되는 Future 목록의 순서는 제출한 작업 목록의 순서와 같다.
//    - 모두 완료된 시점에 Future를 반환하므로, future.get()은 즉시 결과를 돌려준다.
//    - 단점: 가장 느린 작업이 끝날 때까지 기다려야 첫 번째 결과도 얻을 수 있다.
//
// 2. CompletionService (ExecutorCompletionService)
//    - 내부적으로 완료된 Future를 블로킹 큐(BlockingQueue)에 넣는다.
//    - take() : 먼저 완료된 Future를 큐에서 꺼낸다 (없으면 대기)
//    - poll() : 완료된 Future가 없으면 즉시 null 반환 (비차단)
//    - 작업 시간이 제각각이면 빠른 결과를 먼저 처리할 때 유용하다.
//
// invokeAll vs CompletionService:
//   invokeAll        : 모두 끝날 때까지 기다렸다가 제출 순서대로 결과 수집
//   CompletionService: 끝나는 즉시 결과를 꺼낼 수 있어 반응성이 높음

public class App3 {

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    ExecutorService executor = Executors.newFixedThreadPool(3);

    try {
      demoInvokeAll(executor);
      System.out.println();
      demoCompletionService(executor);
    } finally {
      executor.shutdown();
      executor.awaitTermination(3, TimeUnit.SECONDS);
    }
  }

  private static void demoInvokeAll(ExecutorService executor)
      throws InterruptedException, ExecutionException {

    System.out.println("[데모 1] invokeAll() - 모든 작업 완료 후 결과 수집");
    System.out.println("상품(700ms), 리뷰(300ms), 재고(500ms) 세 작업을 동시에 제출");
    System.out.println("가장 느린 상품(700ms)이 끝나야 invokeAll()이 반환된다.");

    List<Callable<String>> tasks = new ArrayList<>();
    tasks.add(createSearchTask("상품", 700)); // 가장 느린 작업
    tasks.add(createSearchTask("리뷰", 300)); // 가장 빠른 작업
    tasks.add(createSearchTask("재고", 500));

    long start = System.currentTimeMillis();

    // invokeAll(): 세 작업을 모두 제출하고 모두 완료될 때까지 현재 스레드를 블로킹한다.
    // - 반환 시점에 모든 Future가 이미 완료 상태이므로, 이후 future.get()은 즉시 반환된다.
    // - Future 목록의 순서는 tasks 목록의 순서와 동일하다 (완료 순서와 무관).
    List<Future<String>> futures = executor.invokeAll(tasks);

    for (Future<String> future : futures) {
      // invokeAll() 이후이므로 get()은 블로킹 없이 즉시 결과를 반환한다.
      System.out.printf("  결과: %s%n", future.get());
    }

    long elapsed = System.currentTimeMillis() - start;
    System.out.printf("  완료: %,dms (가장 느린 상품 조회 700ms 기준)%n", elapsed);
    System.out.println("→ 결과 출력 순서는 작업 완료 순서가 아니라 제출 순서다.");
  }

  private static void demoCompletionService(ExecutorService executor)
      throws InterruptedException, ExecutionException {

    System.out.println("[데모 2] CompletionService - 완료된 작업부터 결과 수집");
    System.out.println("invokeAll과 동일한 작업(상품 700ms, 리뷰 300ms, 재고 500ms)을 제출");
    System.out.println("취소 순서: 리뷰(300ms) → 재고(500ms) → 상품(700ms) 순으로 결과 도착 예상");

    // ExecutorCompletionService: 기존 ExecutorService를 감싸는 래퍼(wrapper)
    // - submit()한 작업이 완료되면 내부 BlockingQueue에 Future를 자동으로 추가한다.
    // - take()/poll()로 완료된 Future를 꺼낸다.
    CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

    // submit(): executor.submit()과 동일하게 작업을 풀에 제출한다.
    // - 완료된 순서대로 내부 큐에 쌓인다.
    completionService.submit(createSearchTask("상품", 700));
    completionService.submit(createSearchTask("리뷰", 300));
    completionService.submit(createSearchTask("재고", 500));

    long start = System.currentTimeMillis();

    for (int i = 0; i < 3; i++) {
      // take(): 완료된 Future가 있으면 즉시 꺼내고, 없으면 완료될 때까지 대기한다.
      // - invokeAll()과 달리 가장 빠른 작업의 결과부터 처리할 수 있다.
      // - 결과를 먼저 처리하고 싶다면 invokeAll보다 CompletionService가 적합하다.
      Future<String> completed = completionService.take();
      long elapsed = System.currentTimeMillis() - start;
      // take()가 반환한 Future는 이미 완료 상태이므로 get()은 블로킹 없이 즉시 반환된다.
      System.out.printf("  +%,4dms 결과: %s%n", elapsed, completed.get());
    }

    System.out.println("→ 작업 시간이 다른 경우, 빠른 결과를 먼저 처리할 수 있다.");
  }

  private static Callable<String> createSearchTask(String name, int delayMs) {
    return () -> {
      Thread.sleep(delayMs);
      return name + " 조회 완료 (" + delayMs + "ms)";
    };
  }
}
