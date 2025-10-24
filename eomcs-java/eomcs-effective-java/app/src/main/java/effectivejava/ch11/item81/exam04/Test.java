// # 아이템 81. wait와 notify보다는 동시성 유틸리티를 애용하라
// - wait와 notify는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용하라.
//   wait와 notify를 직접 사용하는 것을 동시성 '어셈블리 언어'로 프로그래밍하는 것에 비유할 수 있다.
//   반면, java.util.concurrent 패키지는 고수준 언어에 비유할 수 있다.
// - 코드를 새로 작성한다면 wait와 notify를 쓸 이유가 거의 없다.
//   이들을 사용하는 레거시 코드를 유지보수해야 한다면,
//   wait는 항상 표준 관용구에 따라 while문 안에서 호출하도록 하라.
//   일반적으로 notify보다는 notifyAll을 사용해야 한다.
//   혹시라도 notify를 사용한다면 응답 불가 상태에 빠지지 않도록 각별히 주의하라.
//
// [java.util.concurrent 패키지의 동시성 유틸리티들]
// 1) 실행자 프레임워크
// 2) 동시성 컬렉션(concurrent collection)
// 3) 동기화 장치(synchronizer)

package effectivejava.ch11.item81.exam04;

// [주제] 동기화 장치 - CyclicBarrier 사용법

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class Test {

  // CyclicBarrier를 사용한 동시 시작/종료 동기화 및 시간 측정
  public static long time(Executor executor, int concurrency, Runnable action)
      throws InterruptedException {
    final AtomicLong startNanos = new AtomicLong(0L);

    // 모든 스레드(작업자 n + 메인 1)가 모이면 시작 시각 기록
    final CyclicBarrier startBarrier =
        new CyclicBarrier(concurrency + 1, () -> startNanos.set(System.nanoTime()));

    // 모든 스레드(작업자 n + 메인 1)가 모이면 종료 시점(동시 완료) 신호
    final CyclicBarrier endBarrier = new CyclicBarrier(concurrency + 1);

    for (int i = 0; i < concurrency; i++) {
      executor.execute(
          () -> {
            try {
              // 모두 준비되면 동시에 출발
              startBarrier.await();
              try {
                action.run();
              } finally {
                // 작업 완료 후 모두가 모일 때까지 대기
                endBarrier.await();
              }
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt(); // 인터럽트 복원
            } catch (BrokenBarrierException e) {
              // 바리어가 깨진 경우: 조용히 종료(데모 목적)
            }
          });
    }

    try {
      // 메인 스레드도 파티로 참여: 출발 동기화
      startBarrier.await();
      // 메인 스레드도 파티로 참여: 종료 동기화
      endBarrier.await();
    } catch (BrokenBarrierException e) {
      throw new RuntimeException(e);
    }

    return System.nanoTime() - startNanos.get();
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Runnable action =
        () -> {
          long sum = 0;
          for (int i = 0; i < 10_000_000; i++) {
            sum += i;
          }
        };

    long duration = time(executorService, 3, action);
    System.out.printf("3개의 스레드가 작업을 완료하는 데 걸린 시간: %.3f초%n", duration / 1_000_000_000.0);

    executorService.shutdown();
  }
}
