package com.eomcs.advanced.concurrency.exam07;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// RecursiveAction:
//
// RecursiveTask<V>가 결과를 반환하는 작업이라면,
// RecursiveAction은 결과를 반환하지 않는 Fork/Join 작업이다.
//
// 배열을 직접 수정하거나, 파일 처리처럼 결과를 별도 저장소에 기록하는 작업에 적합하다.
//
// RecursiveTask vs RecursiveAction:
//   RecursiveTask<V>  : compute()가 V를 반환한다. pool.invoke()로 결과를 받는다.
//   RecursiveAction   : compute()가 void. 결과 없이 공유 배열/자원을 직접 변경한다.
//
// invokeAll(left, right) vs fork()/join():
//   invokeAll()  : 두 작업을 모두 fork하고 모두 join할 때까지 대기한다. (편의 메서드)
//   fork()/join(): 한 쪽은 compute()로 직접 실행하여 현재 worker를 놀리지 않는다. (최적화)

public class App2 {

  static class DiscountTask extends RecursiveAction {

    private static final int THRESHOLD = 5;

    private final int[] prices;
    private final int start;
    private final int end;
    private final int percent;

    DiscountTask(int[] prices, int start, int end, int percent) {
      this.prices = prices;
      this.start = start;
      this.end = end;
      this.percent = percent;
    }

    @Override
    protected void compute() {
      int length = end - start;

      // 범위가 THRESHOLD(5개) 이하이면 직접 할인 적용 (순차 처리로 전환)
      if (length <= THRESHOLD) {
        applyDiscount();
        return;
      }

      int mid = start + length / 2;

      DiscountTask left = new DiscountTask(prices, start, mid, percent);
      DiscountTask right = new DiscountTask(prices, mid, end, percent);

      // invokeAll(): 두 작업을 동시에 fork하고 모두 완료될 때까지 기다린다.
      // - 내부적으로 left.fork(), right.fork()한 뒤 두 작업을 join()한다.
      // - RecursiveAction은 반환값이 없으므로 join 결과를 사용하지 않아도 된다.
      invokeAll(left, right);
    }

    private void applyDiscount() {
      String threadName = Thread.currentThread().getName();
      System.out.printf("  %s: [%d, %d) 할인 적용%n", threadName, start, end);

      for (int i = start; i < end; i++) {
        prices[i] = prices[i] * (100 - percent) / 100;
      }
    }
  }

  public static void main(String[] args) {

    System.out.println("[결과 없는 작업] RecursiveAction - 배열 값 변경");
    System.out.println("상품 가격 배열을 여러 구간으로 나누어 20% 할인한다.");
    System.out.println();

    int[] prices = {
      10_000, 12_000, 15_000, 18_000, 20_000,
      22_000, 25_000, 28_000, 30_000, 35_000,
      40_000, 45_000, 50_000, 55_000, 60_000
    };

    System.out.println("  할인 전: " + Arrays.toString(prices));

    // ForkJoinPool(3): 스레드 3개짜리 풀 생성 (병렬도 명시)
    // - invoke()로 RecursiveAction을 제출하면 반환값 없이 완료될 때까지 대기한다.
    // - prices 배열은 참조로 공유되므로 DiscountTask가 직접 수정한다.
    try (ForkJoinPool pool = new ForkJoinPool(3)) {
      pool.invoke(new DiscountTask(prices, 0, prices.length, 20));
    }

    System.out.println("  할인 후: " + Arrays.toString(prices));
    System.out.println("→ RecursiveAction은 결과를 반환하지 않고 공유 데이터나 외부 자원을 변경한다.");
  }
}
