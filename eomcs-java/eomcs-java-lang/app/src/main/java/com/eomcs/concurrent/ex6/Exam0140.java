// 스레드 재사용 - 4단계) wait()/notify() 사용
package com.eomcs.concurrent.ex6;

import java.util.Scanner;

public class Exam0140 {

  public static void main(String[] args) {

    class MyThread extends Thread {
      int count;

      synchronized public void setCount(int count) {
        this.count = count;

        // 이 객체의 사용을 기다리는 스레드에게 작업을 시작할 것을 알린다.
        //        synchronized (this) {
        this.notify();
        //        }
        // 문법 주의!
        // => notify()도 동기화 영역에서 호출해야 한다.
        // => 안그러면 IllegalMonitorStateException 예외가 발생한다.
      }

      @Override
      public void run() {

        System.out.println("스레드 시작했음!");
        try {
          while (true) {
            System.out.println("스레드 대기중...");

            // wait()
            // - 해당 객체에서 notify()를 통해 알림이 올 때까지 스레드의 실행을 멈추게 한다.
            // - 이 메서드는 동기화 블록
            //   (한 번에 한 스레드만이 진입하도록 설정된 블록)에서만 호출할 수 있다.
            //
            // 문법 주의!
            // => wait()/notify() 는 반드시 동기화 영역 안에서 호출해야 한다.
            //
            // 동기화 영역?
            // => synchronized로 선언된 메서드
            //    예) synchronized void m() {}
            // => synchronized로 묶인 블록
            //    예) synchronized(접근대상) {...}
            //
            synchronized (this) {
              this.wait();
              // valueBox 객체에 대해 사용하라는 신호가 올 때까지 이 스레드에게 기다리라는 명령이다.
              // 즉 wait()를 호출한 스레드는 Not Runnable 상태에 진입한다.
              // => 실행을 멈추고 CPU 사용권을 받지 않는 상태가 된다.
              //
              // 이 스레드를 잠에서 깨우는 방법?
              // => 다른 스레드가 valueBox에 대해 notify()를 호출하면,
              //    wait() 로 신호를 기다리고 있는 스레드가 잠에서 깨어나
              //    실행을 시작한다.
              //
              // 기다림을 끝내는 방법?
              // => 즉 notify()를 통해 기다림이 끝났다는 것을 알림 받아야 한다.
              //
            }

            if (count == -1) {
              return;
            }

            System.out.println("카운트 시작!");
            for (int i = count; i > 0; i--) {
              System.out.println("==> " + i);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    MyThread t = new MyThread();
    t.start(); // 이 스레드는 main 스레드가 실행하라고 신호를 줄 때까지 기다린다

    Scanner keyScan = new Scanner(System.in);

    while (true) {
      System.out.print("카운트? ");
      String str = keyScan.nextLine();

      if (str.equals("quit")) {
        t.setCount(-1); // 스레드를 종료하도록 count를 -1로 설정한다.
        break;
      }

      t.setCount(Integer.parseInt(str));
      // setCount()
      // - 사용자가 입력한 카운트 값을 설정할 때
      // - main 스레드는 이 객체의 사용을 간절히 기다리는 다른 스레드에게
      //   즉시 사용하라고 신호를 보낸다.
      // - setCount() 메서드의 코드를 확인해 보라!
    }

    System.out.println("main 스레드 종료!");
    keyScan.close();
  }
}

