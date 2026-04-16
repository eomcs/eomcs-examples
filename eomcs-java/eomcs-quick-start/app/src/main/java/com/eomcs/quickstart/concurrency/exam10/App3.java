package com.eomcs.quickstart.concurrency.exam10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// newVirtualThreadPerTaskExecutor():
//
// ExecutorService 방식으로 가상 스레드를 사용할 수 있다.
// 제출한 작업마다 새 가상 스레드를 만들어 실행한다.
//
// newFixedThreadPool vs newVirtualThreadPerTaskExecutor:
//   newFixedThreadPool(N)           : N개 플랫폼 스레드 풀. N을 초과하는 작업은 큐에서 대기.
//   newVirtualThreadPerTaskExecutor : 작업마다 새 가상 스레드 생성. 풀 크기 제한 없음.
//
// try-with-resources (Java 19+):
//   ExecutorService는 AutoCloseable을 구현하므로 try 블록을 벗어나면 close()가 자동 호출된다.
//   close()는 shutdown()을 호출하고 모든 제출된 작업이 끝날 때까지 대기한다.
//   → shutdown() + awaitTermination() 없이 깔끔하게 종료할 수 있다.
//
// 기존 코드 마이그레이션:
//   newFixedThreadPool(200)을 newVirtualThreadPerTaskExecutor()로 교체하면
//   추가 코드 변경 없이 가상 스레드의 이점을 누릴 수 있다. (API 호환)

public class App3 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    System.out.println("[Executor] newVirtualThreadPerTaskExecutor()");
    System.out.println("Callable 작업마다 가상 스레드를 하나씩 만들어 결과를 받는다.");
    System.out.println();

    List<Future<String>> futures = new ArrayList<>();

    // try-with-resources: try 블록이 끝날 때 executor.close()가 자동 호출된다.
    // - close()는 제출된 5개 작업이 모두 완료될 때까지 대기한 뒤 executor를 닫는다.
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 1; i <= 5; i++) {
        final int orderNo = i;
        // Callable을 submit()하면 각 작업마다 새 가상 스레드가 하나씩 생성된다.
        Callable<String> task =
            () -> {
              sleep(300); // 가상 스레드가 sleep()으로 블로킹되면 캐리어 스레드를 자동으로 해제한다.
              return "주문-" + orderNo + " 처리 완료 (" + Thread.currentThread() + ")";
            };

        // submit(): Future<String>을 즉시 반환한다. 작업은 새 가상 스레드에서 비동기로 실행된다.
        futures.add(executor.submit(task));
      }

      // future.get(): 각 작업이 완료될 때까지 대기하고 결과를 반환한다.
      // - 5개가 동시에 실행되므로 총 대기 시간은 약 300ms (직렬 1500ms가 아님)
      for (Future<String> future : futures) {
        System.out.println("  " + future.get());
      }
    } // close() 호출: 모든 작업 완료 대기 후 executor 종료

    System.out.println("→ 기존 ExecutorService API와 같은 방식으로 가상 스레드를 사용할 수 있다.");
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
