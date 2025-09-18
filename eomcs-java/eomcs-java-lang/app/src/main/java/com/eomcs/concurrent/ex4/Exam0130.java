// 스레드의 생명주기(lifecycle) - sleep()
package com.eomcs.concurrent.ex4;

public class Exam0130 {
  public static void main(String[] args) throws Exception {
    System.out.println("스레드 실행 전");

    new Thread() {
      @Override
      public void run() {
        System.out.println("Hello!");
      }
    }.start();

    // 3초 동안 not runnable 상태로 만든다.
    // => 즉 3초 동안 CPU가 놀고 있더라도 CPU를 사용하지 않는다.
    // => 3초가 지나면(timeout) 다시 "main" 스레드는 CPU를 받아 실행할 수 있다.
    // => sleep()을 호출하면 그 순간에 실행하는 스레드를 잠들게 한다.
    Thread.sleep(3000); // milliseconds

    System.out.println("스레드 실행 후");
  }

}
