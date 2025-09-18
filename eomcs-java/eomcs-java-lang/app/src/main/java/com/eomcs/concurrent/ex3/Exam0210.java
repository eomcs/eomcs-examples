// 스레드 만들기 II - Runnable 인터페이스 구현 + Thread
package com.eomcs.concurrent.ex3;

public class Exam0210 {

  public static void main(String[] args) {

    // 2) Runnable 인터페이스를 구현하기
    // => 실무에서 스레드를 만들 때 많이 사용한다.
    // => 인터페이스를 구현하는 것이기 때문에 다른 클래스를 상속 받을 수 있다.
    // => 직접적으로 스레드가 아니기 때문에 실행할 때는 Thread의 도움을 받아야 한다.
    class MyRunnable implements Runnable {
      @Override
      public void run() {
        // 별도로 분리해서 병행으로 실행할 코드를 두는 곳!
        for (int i = 0; i < 1000; i++) {
          System.out.println("===> " + i);
        }
      }
    }

    // 스레드 실행하기
    // => Runnable 구현체를 Thread 객체에 실어서 실행한다.
    // => start()를 호출하여 기존 스레드에서 분리하여 스레드를 실행시킨다.
    Thread t = new Thread(new MyRunnable());
    t.start(); // 실행 흐름을 분리한 후 즉시 리턴한다.

    // "main" 스레드는 Thread와 상관없이 병행하여 실행한다.
    for (int i = 0; i < 1000; i++) {
      System.out.println(">>>> " + i);
    }

  }

}
