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

package effectivejava.ch11.item78.exam05;

// [주제] 다른 스레드 멈추기: volatile을 적용한 예

import java.util.concurrent.TimeUnit;

public class Test {

  // volatile 한정자는,
  // - 해당 필드에 대한 모든 쓰기가 즉시 메인 메모리에 반영되도록 하고,
  //   모든 읽기가 메인 메모리에서 최신 값을 읽어 오도록 보장한다.
  private static volatile boolean stopRequested = false;

  public static void main(String[] args) throws Exception {
    Thread backgroundThread =
        new Thread(
            () -> {
              int i = 0;
              while (!stopRequested) { // CPU 캐시가 아니라 메인 메모리에서 읽는다.
                i++;
              }
              System.out.println("backgroundThread 종료!");
            });
    backgroundThread.start();

    TimeUnit.SECONDS.sleep(1); // 메인 스레드 1초 대기
    stopRequested = true; // 즉시 메인 메모리의 값을 바꾼다.
    // main 스레드가 1초 후에 stopRequested 필드를 true로 바꾸면,
    // backgroundThread 스레드는 1초후 멈춘다.

  }
}
