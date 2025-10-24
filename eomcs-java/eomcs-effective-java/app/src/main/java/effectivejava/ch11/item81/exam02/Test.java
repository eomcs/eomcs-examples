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

package effectivejava.ch11.item81.exam02;

// [주제] 동시성 컬렉션 - BlockingQueue 사용법
// - Queue를 확장한 동시성 컬렉션이다.
// - take() 메서드는 큐가 비어 있으면 요소가 추가될 때까지 기다린다.
//   이런 특성 덕분에 생산자-소비자 패턴을 구현할 때 유용하다.
// - ThreadPoolExecutor를 포함한 대부분의 실행자 서비스 구현체에서 이 컬렉션을 내부적으로 사용한다.
//

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

  // 생산자와 소비자 간의 안전한 통신 통로
  private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    // 생산자 (Producer)
    executor.submit(
        () -> {
          try {
            for (int i = 1; i <= 5; i++) {
              String item = "🍎 Apple-" + i;
              System.out.println("[생산자] 생산: " + item);
              queue.put(item); // 큐가 가득 차면 자동으로 대기
              Thread.sleep(500);
            }
            queue.put("END"); // 종료 신호
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        });

    // 소비자 (Consumer)
    executor.submit(
        () -> {
          try {
            while (true) {
              String item = queue.take(); // 큐가 비면 자동으로 대기
              if ("END".equals(item)) break; // 종료 신호 받음
              System.out.println("  [소비자] 소비: " + item);
              Thread.sleep(1000);
            }
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        });

    executor.shutdown();
  }
}
