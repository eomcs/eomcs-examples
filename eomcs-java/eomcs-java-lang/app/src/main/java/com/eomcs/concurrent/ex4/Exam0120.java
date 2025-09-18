// 스레드의 생명주기(lifecycle) - join()
package com.eomcs.concurrent.ex4;

public class Exam0120 {
  public static void main(String[] args) throws Exception {
    System.out.println("스레드 실행 전");

    Thread t = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        System.out.println("===> " + i);
      }
    });

    t.start(); // 스레드를 생성하고 시작시킨다.

    t.join(); // t 스레드가 종료될 때까지 "main" 스레드는 기다린다.

    // 즉 t 스레드가 종료된 후 다음 코드를 실행한다.
    System.out.println("스레드 종료 후");

    // 스레드 종료 후 다시 시작시킨다면?
    // => IllegalThreadStateException 발생!
    // => 즉 종료된 스레드는 다시 running 할 수 없다.
    //    t.start();

  }

}
