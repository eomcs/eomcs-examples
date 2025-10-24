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

package effectivejava.ch11.item81.exam03;

// [주제] 동기화 장치 - CountDownLatch 사용법
// - 동기화 장치는 스레드가 다른 스레드를 기다릴 수 있게 하여, 서로 작업을 조율할 수 있게 해준다.
// - 동기화 장치 예:
//   - 가장 자주 쓰이는 동기화 장치: CountDownLatch, Semaphore
//   - 덜 쓰이는 동기화 장치: CyclicBarrier, Exchanger
//   - 가장 강력한 동기화 장치: Phaser
// - CountDownLatch
//   - 카운트다움 래치(latch; 걸쇠)는 일회성 장벽으로,
//     하나 이상의 스레드가 또 다른 하나 이상의 스레드 작업이 끝날 때까지 기다리게 한다.
//   - CountDownLatch(int count) 생성자:
//     count: countDown() 메서드를 몇 번 호출해야 대기 중인 스레드들을 깨우는지를 결정한다.

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

  // 여러 스레드가 동시에 작업을 시작하도록 동기화하고,
  // 모든 스레드가 종료될 때까지 기다려 총 소요 시간을 측정한다.
  public static long time(Executor executor, int concurrency, Runnable action)
      throws InterruptedException {
    CountDownLatch ready = new CountDownLatch(concurrency); // 모든 작업자가 준비될 때까지 기다리는 래치
    CountDownLatch start = new CountDownLatch(1); // 시작 신호
    CountDownLatch done = new CountDownLatch(concurrency); // 모든 작업 종료 대기

    for (int i = 0; i < concurrency; i++) {
      executor.execute(
          () -> {
            ready.countDown(); // 준비 완료 알림
            try {
              start.await(); // 시작 신호 대기
              action.run(); // 측정 대상 실행
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt(); // Thread를 다시 interrupted 상태로 설정
            } finally {
              done.countDown(); // 작업 완료 알림
            }
          });
    }

    ready.await(); // 모든 작업자가 준비될 때까지 대기
    long startNanos = System.nanoTime(); // 시작 시간 기록
    start.countDown(); // 모든 작업자에 시작 신호 전달
    done.await(); // 모든 작업자가 끝날 때까지 대기
    return System.nanoTime() - startNanos; // 총 경과 시간(ns) 반환
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    // 1) 실행할 작업 정의
    Runnable action =
        () -> {
          // 측정할 코드 (예: 간단한 연산)
          long sum = 0;
          for (int i = 0; i < 10_000_000; i++) {
            sum += i;
          }
        };

    // 2) time() 메서드로 측정
    long duration = time(executorService, 4, action);

    // 3) 결과 출력
    System.out.printf("4개의 스레드가 작업을 완료하는 데 걸린 시간: %.3f초%n", duration / 1_000_000_000.0);

    executorService.shutdown();

    // [참고] catch 블록에서 InterruptedException 예외 처리 코드 분석
    // InterruptedException 예외:
    //   - 대기 중(sleep(), wait(), join(), await() 등)일 때
    //     다른 스레드가 interrupt() 호출하면 이 예외가 발생한다.
    //     예) ExecutorService의 shutdownNow() 호출
    //   - JVM은 스레드의 인터럽트 플래그를 자동으로 false로 초기화한다.
    // Thread.currentThread().interrupt():
    //   - 현재 스레드의 인터럽트 상태 복원하기 위해 interrupt() 호출한다.
    //     스레드의 인터럽트 플래그를 다시 true로 설정한다.
    //   - 상위 호출자(예: Executor)에게 "이 스레드는 중단되어야 한다"는 신호를 전달하기 위함이다.
    //     이렇게 하지 않으면, Executor는 스레드가 여전히 유효하다고 간주하여,
    //     스레드 풀을 종료하거나 자원 회수가 지연되거나 실패할 수 있다.
    //   - 인터럽트 걸린 스레드가 인터럽트에 반응하는 블로킹 메서드를 호출할 때 예외를 발생시킨다.
    //     예) sleep(), wait(), join(), await(), BlockingQueue.take()/put() 등
    // 결론
    //   - InterruptedException가 발생했을 때 catch 블록에서
    //     Thread.currentThread().interrupt()를 호출하여 인터럽트 상태를 복원하는 이유는,
    //     중단하라는 요청을 받은 스레드를 가지고 블로킹 작업을 수행하지 못하도록 하기 위함이다.
    //   - 곧 종료해야 할 상황에서 블로킹 작업을 수행하다가
    //     무한 대기 상태에 빠지게 되면 스레드가 영원히 종료되지 못해
    //     애플리케이션을 정상 종료 못하는 문제가 발생할 수 있기 때문이다.

    // [참고] System.nanoTime() 메서드:
    //   - System.currentTimeMillis() 보다 더 정확하고 정밀하며
    //     시스템의 실시간 시계의 시간 보정에 영향 받지 않는다.
    //

    // [참고] CountDownLatch 클래스:
    //   - CountDownLatch 3개는 CyclicBarrier나 Phaser 인스턴스 하나로 대체할 수 있다.
    //   - 다만 코드는 더 명료해지지만 이해하기는 더 어려워질 수 있다.

  }
}
