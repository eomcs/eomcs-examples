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

package effectivejava.ch11.item78.exam03;

// [주제] 다른 스레드 멈추기: 잘못한 예
// 1) Thread.stop()?
//    - 사용 자제(deprecated)된 메서드이다. 절대로 사용하지 말라!
//      Thread.stop(Throwable) 메서드는 Java 11에서 제거되었다.
// 2) boolean 필드의 상태를 제어하여 스레드를 멈추도록 구현하라.
//    - 이 방법이 안전하고 올바른 방법이다.
//    - 단, 이 방법을 사용할 때는 동기화를 반드시 신경 써야 한다.
//      동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수 있다.

import java.util.concurrent.TimeUnit;

public class Test {

  private static boolean stopRequested = false;

  public static void main(String[] args) throws Exception {
    Thread backgroundThread =
        new Thread(
            () -> {
              int i = 0;
              while (!stopRequested) {
                i++;
              }
              System.out.println("backgroundThread 종료!");
            });
    backgroundThread.start();

    TimeUnit.SECONDS.sleep(1); // 메인 스레드 1초 대기
    stopRequested = true;
    // main 스레드가 1초 후에 stopRequested 필드를 true로 바꾸면,
    // backgroundThread 스레드가 반복을 멈출까?
    // - 멈추지 않을 수도 있다!

    // [이유]
    // - backgroundThread 스레드가 접근하는 stopRequested 필드는 동기화되지 않았다.
    //   따라서 stopRequested 필드를 사용하는 while 문은 다음과 같이 최적화될 수 있다.
    //   최적화 전:
    //      while (!stopRequested) {
    //        i++;
    //      }
    //   최적화 후:
    //      if (!stopRequested) { // 한 번 CPU 캐시로 읽어온 값을 계속 사용
    //        while (true) {
    //          i++;
    //        }
    //      }
    //    OpenJDK 서버 VM이 실제로 적용하는 끌어올리기(hoisting)라는 최적화 기법이다.
  }
}
