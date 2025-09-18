// 스레드의 생명주기(lifecycle) - running 상태 : CPU 쟁탈전(racing)
package com.eomcs.concurrent.ex4;

public class Exam0140 {
  public static void main(String[] args) throws Exception {

    class MyThread extends Thread {
      public MyThread(String name) {
        super(name);
      }
      @Override
      public void run() {
        for (int i = 0; i < 1000; i++) {
          System.out.printf("%s %d\n", this.getName(), i);
        }
      }
    }

    MyThread t1 = new MyThread("홍길동 =====>");
    MyThread t2 = new MyThread("오호라 ------------>");
    MyThread t3 = new MyThread("우헤헤 ##");

    // 스레드를 시작시키는 순간 running 상태로 접어든다.
    // running 상태는 실행하고 있는 상태 뿐만 아니라,
    // CPU를 받을 수 있는 상태이기도 하다.
    // => CPU는 OS의 관리 정책(CPU Scheduling)에 따라 스레드나 프로세스에 배분된다.
    //    물론 OS가 CPU를 배분한 후 임의시간 후에
    //    다시 회수하여 다른 스레드(현재 스레드 포함)나 프로세스에 배분한다.
    //    때에 따라서 같은 스레드가 연속해서 배분 받는 경우도 있을 것이다.
    //
    t1.start();
    t2.start();
    t3.start();

    for (int i = 0; i < 1000; i++) {
      System.out.printf("main 스레드: %d\n", i);
    }
  }

}

// CPU 스케줄링
// => OS가 프로세스나 스레드에 CPU 사용을 배분하는 정책
// 1) Round-Robin 방식
//    - Windows 운영체제에서 사용하는 방식이다.
//    - 우선 순위 보다는 일정 시간 위주로 프로세스나 스레드에게 CPU를 배분하는 방식이다.
// 2) Priority + Aging 방식
//    - Unix나 Linux 운영체제에서 사용하는 방식이다.
//    - 우선 순위가 높은 프로세스나 스레드에게 CPU를 먼저 배분하는 방식이다.
//    - 우선 순위 배분 방식에서는 우선 순위가 낮은 경우 실행에서 소외되는 문제가 발생하기 때문에
//      우선 순위가 높은 프로세스나 스레드 때문에 실행 순서가 밀릴 때 마다
//      원래의 낮은 순위를 높임으로써(aging) 결국에는 모든 프로세스와 스레드의
//      실행을 완료할 수 있게 한다.
//
// "컨텍스트 스위칭(context switching)"
// - 동시에 여러 개의 프로세스나 스레드를 실행할 때
//   CPU 사용권을 뺏어 다른 프로세스나 스레드에게 주기 전에
//   현재까지 실행한 코드의 위치 정보를 저장해야 한다.
//   또한 CPU 사용권을 주기 전에 그 프로세스나 스레드가 이전에 어디까지 실행했었는지
//   이전 실행 위치 정보를 로딩해야 한다.
//   즉 실행 위치에 대한 정보를 저장하고 로딩하는 것을 말한다.










