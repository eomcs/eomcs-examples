// # 아이템 80. 스레드보다는 실행자, 태스크, 스트림을 애용하라
// [실행자 프레임워크]
// - 인터페이스 기반의 유연한 태스크 실행 기능을 담고 있다.
// - 주요 기능:
//   - 특정 태스크가 완료되기를 기다린다.
//   - 태스크 모음 중 아무것 하나(invokeAny 메서드)
//     혹은 모든 태스크(invokeAll 메서드)가 완료되기를 기다린다.
//   - 실행자 서비스가 종료하기를 기다린다(awaitTermination 메서드).
//   - 완료된 태스크들의 결과를 차례로 받는다(ExecutorCompletionService 이용).
//   - 태스크를 특정 시간에 혹은 주기적으로 실행하게 한다(ScheduledThreadPoolExecutor 이용).
//

package effectivejava.ch11.item80.exam02;

// [주제] ExecutorService vs ForkJoinPool

import java.util.*;
import java.util.concurrent.*;

public class Test {

  // ------- 설정 -------
  static final int N = 80_000_000; // 배열 길이 (환경에 따라 조정)
  static final int WARMUP = 1; // 워밍업 횟수
  static final int ROUNDS = 3; // 측정 횟수
  static final int THRESHOLD = 50_000; // ForkJoin 분할 기준

  // --------------------

  public static void main(String[] args) throws Exception {
    long[] data = new long[N];
    Arrays.fill(data, 1L); // 합계는 N이어야 함(검증용)

    System.out.println("availableProcessors = " + Runtime.getRuntime().availableProcessors());

    // 워밍업
    for (int i = 0; i < WARMUP; i++) {
      long a = executorOneTaskSum(data); // 분할 없음
      long b = forkJoinSum(data); // 분할 있음
      if (a != b) throw new AssertionError("Warmup sums differ");
    }

    // 측정
    List<Long> tExec = new ArrayList<>();
    List<Long> tFj = new ArrayList<>();

    for (int i = 0; i < ROUNDS; i++) {
      tExec.add(time(() -> check(executorOneTaskSum(data))));
      tFj.add(time(() -> check(forkJoinSum(data))));
    }

    System.out.printf(
        "Executor 1-thread no-split (ms): %s | min=%.1f avg=%.1f%n",
        toMsList(tExec), nsToMs(min(tExec)), nsToMs(avg(tExec)));
    System.out.printf(
        "ForkJoin split (ms): %s | min=%.1f avg=%.1f%n",
        toMsList(tFj), nsToMs(min(tFj)), nsToMs(avg(tFj)));
  }

  // ----------- 구현부 ------------

  // ExecutorService: 단일 스레드로 전체 작업 1개를 실행 (작업 분할 없음)
  static long executorOneTaskSum(long[] a) {
    ExecutorService exec = Executors.newSingleThreadExecutor(); // 1 스레드, no split
    try {
      Future<Long> f =
          exec.submit(
              () -> {
                long s = 0;
                for (int i = 0; i < a.length; i++) s += a[i];
                return s;
              });
      return f.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    } finally {
      exec.shutdown();
    }
  }

  // ForkJoinPool + RecursiveTask: 작업 분할 있음
  static long forkJoinSum(long[] a) {
    ForkJoinPool pool = ForkJoinPool.commonPool(); // 공용 풀 사용
    return pool.invoke(new SumTask(a, 0, a.length));
  }

  static class SumTask extends RecursiveTask<Long> {
    final long[] arr;
    final int lo, hi; // [lo, hi)

    SumTask(long[] arr, int lo, int hi) {
      this.arr = arr;
      this.lo = lo;
      this.hi = hi;
    }

    @Override
    protected Long compute() {
      int len = hi - lo;
      if (len <= THRESHOLD) {
        long s = 0;
        for (int i = lo; i < hi; i++) s += arr[i];
        return s;
      }
      int mid = lo + (len >>> 1);
      SumTask left = new SumTask(arr, lo, mid);
      SumTask right = new SumTask(arr, mid, hi);
      left.fork(); // 왼쪽 비동기
      long r = right.compute(); // 오른쪽 현재 스레드에서
      long l = left.join(); // 왼쪽 결과 대기
      return l + r;
    }
  }

  // ----------- 유틸 ------------

  static long time(Runnable r) {
    long t0 = System.nanoTime();
    r.run();
    return System.nanoTime() - t0;
  }

  static void check(long sum) {
    if (sum != N) throw new AssertionError("wrong sum: " + sum);
  }

  static double nsToMs(long ns) {
    return ns / 1_000_000.0;
  }

  static double nsToMs(double ns) {
    return ns / 1_000_000.0;
  }

  static long min(List<Long> xs) {
    return xs.stream().mapToLong(Long::longValue).min().orElse(0L);
  }

  static double avg(List<Long> xs) {
    return xs.stream().mapToLong(Long::longValue).average().orElse(0.0);
  }

  static String toMsList(List<Long> ns) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < ns.size(); i++) {
      if (i > 0) sb.append(", ");
      sb.append(String.format("%.1f", nsToMs(ns.get(i))));
    }
    sb.append("]");
    return sb.toString();
  }
}
