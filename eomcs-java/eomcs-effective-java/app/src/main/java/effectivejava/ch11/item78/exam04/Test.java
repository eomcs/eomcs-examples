// # 아이템 78. 공유 중인 가변 데이터는 동기화해 사용하라
// - 여러 스레드가 가변 데이터를 공유한다면 그 데이터를 읽고 쓰는 동작은 반드시 동기화해야 한다.
//   동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수 있다.
// - 공유되는 가변 데이터를 동기화하는데 실패하면,
//   응답 불가 상태에 빠지거나 안전 실패로 이어질 수 있다.
//   이는 디버깅 난이도가 가장 높은 문제에 속한다.
//   간헐적이거나 특정 타이밍에만 발생할 수 있고, VM에 따라 현상이 달라지기도 한다.
// - 베타적 실행은 필요 없고 스레드끼리의 통신만 필요하다면,
//   volatile 한정자만으로 동기화할 수 있다. 다만 올바로 사용하기가 까다롭다.
//
//

package effectivejava.ch11.item78.exam04;

// [주제] 다른 스레드 멈추기: 동기화를 적용한 예

import java.util.concurrent.TimeUnit;

public class Test {

  private static boolean stopRequested = false;

  private static synchronized void requestStop() {
    stopRequested = true;
    // synchronized 블록에서 나갈 때,
    // - CPU 캐시 값을 메인 메모리로 복사한다.
  }

  private static synchronized boolean stopRequested() {
    // synchronized 블록에 진입할 때,
    // - 메인 메모리에서 최신 값을 읽어 CPU 캐시로 복사한다.
    return stopRequested;
  }

  public static void main(String[] args) throws Exception {
    Thread backgroundThread =
        new Thread(
            () -> {
              int i = 0;
              while (!stopRequested()) {
                i++;
              }
              System.out.println("backgroundThread 종료!");
            });
    backgroundThread.start();

    TimeUnit.SECONDS.sleep(1); // 메인 스레드 1초 대기
    requestStop();
    // main 스레드가 1초 후에 stopRequested 필드를 true로 바꾸면,
    // backgroundThread 스레드가 반복을 멈출까?
    // - 멈춘다!

    // [이유]
    // - main 스레드가 requestStop() 메서드를 호출하여 stopRequested 필드를 true로 바꾸면,
    //   synchronized 블록을 나갈 때 그 값이 메인 메모리에 반영된다.
    // - backgroundThread 스레드가 stopRequested() 메서드를 호출할 때,
    //   synchronized 블록에 진입하면서 메인 메모리에서 최신 값을 읽어 온다.
    //   따라서 backgroundThread 스레드는 최신 값(true)을 읽어 반복을 멈춘다.
    //

    // [정리]
    // - Java 메모리 모델은 "happens-before 관계"로 가시성을 정의한다.
    //   즉, 어떤 스레드가 변경한 값은 synchronized 블록을 나가기 전에 메인 메모리에 반영되고,
    //   다른 스레드가 synchronized 블록에 진입할 때 메인 메모리의 변경 값을 볼 수 있음을 보장한다.
    // - 쓰기와 읽기 모두가 동기화되지 않으면 동작을 보장하지 않는다.
  }
}
