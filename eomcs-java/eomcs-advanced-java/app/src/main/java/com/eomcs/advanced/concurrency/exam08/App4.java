package com.eomcs.advanced.concurrency.exam08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 예외 처리:
//
// exceptionally(Function<Throwable, T>)
//   - 앞 단계에서 예외가 발생하면 대체 결과를 반환한다. 성공 시에는 실행되지 않는다.
//   - 반환: CompletableFuture<T> (예외를 정상 결과로 복구한다)
//
// handle(BiFunction<T, Throwable, U>)
//   - 성공(T)과 실패(Throwable) 모두를 받아 새 결과(U)를 만든다.
//   - 성공이면 ex=null, 실패이면 result=null이다.
//   - exceptionally()와 달리 성공 결과도 변환할 수 있다.
//
// whenComplete(BiConsumer<T, Throwable>)
//   - 결과나 예외를 관찰(로그/통계)한다. 결과 자체를 바꾸지는 않는다.
//   - 예외가 발생한 경우 whenComplete() 이후 단계로 예외가 그대로 전파된다.
//
// 세 메서드 비교:
//   exceptionally : 실패만 처리. 대체 값으로 복구.
//   handle        : 성공/실패 모두 처리. 결과 변환 가능.
//   whenComplete  : 성공/실패 모두 관찰. 결과 변환 불가.
//
// join() vs get() 예외 처리:
//   join() : CompletionException(unchecked) → getCause()로 원인 확인
//   get()  : ExecutionException(checked)    → getCause()로 원인 확인

public class App4 {

  public static void main(String[] args) {

    System.out.println("[예외 처리] exceptionally() / handle() / whenComplete()");
    System.out.println("비동기 작업 중 예외가 발생했을 때 대체 결과를 만들거나 기록한다.");
    System.out.println();

    try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
      demoExceptionally(executor);
      System.out.println();
      demoHandle(executor);
      System.out.println();
      demoWhenComplete(executor);
    }
  }

  private static void demoExceptionally(ExecutorService executor) {
    System.out.println("[데모 1] exceptionally() - 실패 시 대체 결과");
    System.out.println("loadPrice(fail=true)가 예외를 던지면 exceptionally()가 0을 반환한다.");

    Integer price =
        CompletableFuture.supplyAsync(() -> loadPrice(true), executor) // 예외 발생
            .exceptionally(
                ex -> {
                  // ex: supplyAsync에서 발생한 예외 (CompletionException으로 감싸진 상태)
                  // - getClass().getSimpleName()은 CompletionException이 아닌 원인 예외를 확인하려면
                  //   ex.getCause().getClass().getSimpleName()을 사용해야 한다.
                  System.out.printf("  가격 조회 실패: %s%n", ex.getClass().getSimpleName());
                  return 0; // 대체 결과: 예외 대신 0을 정상 결과처럼 반환한다.
                })
            .join(); // exceptionally()가 예외를 복구했으므로 join()은 0을 반환한다.

    System.out.printf("  대체 가격: %,d원%n", price);
  }

  private static void demoHandle(ExecutorService executor) {
    System.out.println("[데모 2] handle() - 성공/실패 모두 처리");
    System.out.println("loadStock(fail=false)는 성공하므로 stock에 값이 들어오고 ex=null이다.");

    String message =
        CompletableFuture.supplyAsync(() -> loadStock(false), executor) // 정상 실행
            .handle(
                (stock, ex) -> {
                  // handle(): 성공이면 (결과, null), 실패이면 (null, 예외)가 전달된다.
                  // - exceptionally()와 달리 성공 결과도 이 자리에서 변환할 수 있다.
                  if (ex != null) {
                    return "재고 조회 실패"; // 실패 시 대체 문자열
                  }
                  return "재고 수량: " + stock + "개"; // 성공 시 결과 변환
                })
            .join();

    System.out.println("  " + message);
  }

  private static void demoWhenComplete(ExecutorService executor) {
    System.out.println("[데모 3] whenComplete() - 결과/예외 관찰");
    System.out.println("loadPrice(fail=true)가 예외를 던지면 whenComplete()는 로그만 남기고 예외를 그대로 전파한다.");

    CompletableFuture<Integer> future =
        CompletableFuture.supplyAsync(() -> loadPrice(true), executor) // 예외 발생
            .whenComplete(
                (price, ex) -> {
                  // whenComplete(): 성공/실패를 관찰만 한다. 결과를 바꾸지 않는다.
                  // - 예외가 발생했어도 이후 단계로 예외가 그대로 전파된다.
                  // - handle()과 달리 반환값이 없으므로 결과를 변환할 수 없다.
                  if (ex != null) {
                    System.out.println("  로그: 가격 조회 중 예외 발생"); // 로그만 남기고 예외 전파
                  } else {
                    System.out.printf("  로그: 가격 조회 성공 %,d원%n", price);
                  }
                });

    try {
      // whenComplete()는 예외를 복구하지 않으므로 join()에서 CompletionException이 발생한다.
      future.join();
    } catch (CompletionException e) {
      // getCause(): CompletionException에 감싸진 원인 예외(IllegalStateException)를 꺼낸다.
      System.out.printf("  Main: 예외 수신 = %s%n", e.getCause().getMessage());
    }
  }

  private static int loadPrice(boolean fail) {
    sleep(300);
    if (fail) {
      throw new IllegalStateException("가격 서버 응답 없음");
    }
    return 25_000;
  }

  private static int loadStock(boolean fail) {
    sleep(300);
    if (fail) {
      throw new IllegalStateException("재고 서버 응답 없음");
    }
    return 12;
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
