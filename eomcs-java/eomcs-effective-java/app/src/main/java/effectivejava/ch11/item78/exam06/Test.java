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

package effectivejava.ch11.item78.exam06;

// [주제] volatile의 한계 확인하기

import java.util.concurrent.TimeUnit;

public class Test {

  // volatile 한정자가 붙은 변수
  private static volatile int nextSerialNumber = 0;

  public static int generateSerialNumber() {
    return nextSerialNumber++; // 이 한 줄이 원자적이지 않다!
    // ++ 연산자는 코드상으로는 한 줄이지만, 실제로는 세 단계로 이루어진 복합 연산이다.
    //   1. 현재 값 읽기
    //   2. 값 증가
    //   3. 증가한 값 쓰기
    // 이 세 단계를 수행하는 중에 다른 스레드가 중간에 끼어들 수 있다.
    // 이때 일관성이 깨진다.
  }

  public static void main(String[] args) throws Exception {
    // 처음에는 여러 스레드가 동시에 시리얼 넘버를 100개씩 생성한다.
    // 최종 시리얼 넘버의 결과가 300이 되어야 하는데 맞는가?
    // 다음으로 1,000개, 10,000개, 100,000개 증가시키면서 최종 시리얼 넘버를 확인해보자.
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

    // [정리]
    // - volatile 한정자는 변수에 대한 읽기/쓰기 동작을 원자적으로 만들어주지만,
    //   복합 연산을 원자적으로 만들어주지는 않는다.
    // - 복합 연산을 원자적으로 만들어야 한다면 synchronized로 동기화를 처리해야 한다.
  }
}
