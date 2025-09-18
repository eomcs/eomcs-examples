// 스레드 재사용 - 1단계) 스레드 재 사용전 - 매번 스레드 생성
package com.eomcs.concurrent.ex6;

import java.util.Scanner;

public class Exam0110 {

  public static void main(String[] args) {

    class MyThread extends Thread {
      int count;

      public void setCount(int count) {
        this.count = count;
      }

      @Override
      public void run() {
        for (int i = count; i > 0; i--) {
          System.out.println("==> " + i);
        }
      }
    }

    Scanner keyScan = new Scanner(System.in);

    while (true) {
      System.out.print("카운트? ");
      String str = keyScan.nextLine();
      if (str.equals("quit")) {
        break;
      }

      int count = Integer.parseInt(str);

      MyThread t = new MyThread();
      t.setCount(count);
      t.start();
      // 카운트 할 때 마다 매번 스레드를 생성한다.
      // => 실행 완료된 스레드는 가비지가 된다.
      // => 가비지 컬렉터가 가비지가 된 스레드를 수집하여 해제시키기 전까지는
      //    그 스레드를 위해 할당된 메모리를 사용할 수 없다.
      // => 즉 스레드를 매번 생성하는 방식은
      //    과다한 가비지를 생성하기 때문에 메모리 낭비를 일으킨다.
      // => 스레드는 실제 OS가 생성한다.
      //    즉 스레드를 생성하는데 시간이 소요된다는 것이다.
      //    스레드를 자주 생성하면 결국 스레드 생성하는데 시간을 많이 소요하게 된다.
    }

    System.out.println("main 스레드 종료!");
    keyScan.close();
  }
}

