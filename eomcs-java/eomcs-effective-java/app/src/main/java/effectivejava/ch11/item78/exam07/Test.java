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

package effectivejava.ch11.item78.exam07;

// [주제] volatile의 한계 극복하기
// - 복합 연산에는 synchronized로 동기화를 처리해야 한다.
//
import java.util.concurrent.TimeUnit;

public class Test {

  private static int nextSerialNumber = 0;

  public static synchronized int generateSerialNumber() {
    // 메서드 안의 코드 전체가 한 단위로 묶여 원자적으로 처리된다.
    return nextSerialNumber++;
  }

  public static void main(String[] args) throws Exception {
    // 처음에는 여러 스레드가 동시에 시리얼 넘버를 1,000개씩 생성한다.
    // 최종 시리얼 넘버의 결과가 3,000이 되어야 하는데 맞는가?
    // 다음으로 10,000개, 100,000개 증가하면서 최종 시리얼 넘버를 확인해보자.
    // 마찬가지로 최종 시리얼 넘버가 기대한 값과 일치하는가?
    //
    final int THREAD_COUNT = 100;

    class CounterThread extends Thread {
      @Override
      public void run() {
        for (int i = 0; i < THREAD_COUNT; i++) {
          generateSerialNumber();
        }
        System.out.printf("%s 스레드 종료!", Thread.currentThread().getName());
      }
    }

    new CounterThread().start();
    new CounterThread().start();
    new CounterThread().start();

    TimeUnit.SECONDS.sleep(5); // 메인 스레드 대기
    System.out.println("최종 시리얼 넘버: " + nextSerialNumber);
  }
}
