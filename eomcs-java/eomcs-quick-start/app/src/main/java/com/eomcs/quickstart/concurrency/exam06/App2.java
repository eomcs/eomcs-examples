package com.eomcs.quickstart.concurrency.exam06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// Callable / Future:
//
// Runnable은 결과를 반환할 수 없고 checked exception을 직접 던질 수 없다.
// Callable<V>는 V 타입 결과를 반환하고, 예외도 던질 수 있다.
//
//   Future<V> future = executor.submit(callable);
//   V result = future.get(); // 작업 완료까지 대기 후 결과 반환
//
// Future 주요 메서드:
//   get()                 : 결과가 나올 때까지 대기
//   get(timeout, unit)    : 지정 시간까지만 대기
//   isDone()              : 완료 여부 확인
//   cancel(true)          : 작업 취소 시도. true면 실행 중인 작업에 interrupt 전달
//
// Runnable vs Callable 비교:
//   Runnable : void run()           → 반환값 없음, checked exception 불가
//   Callable : V    call() throws E → V 타입 반환, checked exception 가능
//
// Future.get() 예외:
//   InterruptedException  : get() 대기 중 현재 스레드에 인터럽트가 걸릴 때
//   ExecutionException    : 작업(call()) 내부에서 예외가 발생했을 때 (getCause()로 원인 확인)

public class App2 {

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    System.out.println("[결과 받기] Callable + Future");
    System.out.println("주문 금액 계산 작업을 스레드 풀에 맡기고, Future로 결과를 받는다.");
    System.out.println();

    ExecutorService executor = Executors.newFixedThreadPool(3); // 동시 처리 스레드 3개
    List<Future<Integer>> futures = new ArrayList<>();

    int[] orderAmounts = {15_000, 27_000, 8_000, 31_000, 19_000};

    for (int i = 0; i < orderAmounts.length; i++) {
      final int orderNo = i + 1;
      final int amount = orderAmounts[i];

      // Callable<Integer>: Integer를 반환하는 작업 정의
      // - Runnable과 달리 return 문으로 결과를 돌려줄 수 있다.
      // - Thread.sleep()처럼 checked exception을 별도 try-catch 없이 던질 수 있다.
      Callable<Integer> task =
          () -> {
            String threadName = Thread.currentThread().getName();
            System.out.printf("  주문-%d 계산 시작 (%s)%n", orderNo, threadName);
            Thread.sleep(1000); // 배송비 계산 등 시간이 걸리는 작업 시뮬레이션

            int deliveryFee = amount >= 20_000 ? 0 : 3_000; // 2만원 이상 무료 배송
            int total = amount + deliveryFee;

            System.out.printf("  주문-%d 계산 완료: %,d원%n", orderNo, total);
            return total; // Callable의 반환값 → Future.get()으로 꺼낸다
          };

      // submit(Callable): 작업을 풀에 제출하고 Future<Integer>를 즉시 반환한다.
      // - 반환된 Future는 "나중에 결과를 꺼낼 수 있는 손잡이"다.
      // - 이 시점에 작업이 완료되었는지와 무관하게 Future가 즉시 반환된다.
      futures.add(executor.submit(task));
    }

    int grandTotal = 0;
    for (int i = 0; i < futures.size(); i++) {
      Future<Integer> future = futures.get(i);
      // get(): 해당 작업이 완료될 때까지 현재 스레드를 블로킹한다.
      // - 작업이 이미 끝났으면 즉시 반환
      // - 작업 중 예외가 발생했으면 ExecutionException으로 감싸 던진다
      // - 작업 목록 순서대로 get()하므로, 앞 작업이 느려도 뒷 작업 결과를 먼저 받지 못한다.
      //   (완료 순서대로 결과를 받으려면 CompletionService 사용 → App3 참고)
      Integer result = future.get();
      grandTotal += result;
      System.out.printf("  Main: 주문-%d 결과 수신 = %,d원%n", i + 1, result);
    }

    executor.shutdown(); // 이미 제출된 작업을 마저 실행하고 풀을 닫는다
    executor.awaitTermination(3, TimeUnit.SECONDS); // 최대 3초 대기 후 정상 종료 확인

    System.out.printf("%n  전체 결제 금액: %,d원%n", grandTotal);
    System.out.println("→ Callable은 결과를 만들고, Future는 그 결과를 나중에 꺼내는 손잡이다.");
  }
}
