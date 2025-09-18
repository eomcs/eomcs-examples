// 스레드 재사용 - 1단계) 스레드를 재 사용하려 시도
package com.eomcs.concurrent.ex6;

import java.util.Scanner;

public class Exam0111 {

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

    // 카운트를 수행할 스레드를 미리 만든다.
    MyThread t = new MyThread();

    Scanner keyScan = new Scanner(System.in);

    while (true) {
      System.out.print("카운트? ");
      String str = keyScan.nextLine();
      if (str.equals("quit")) {
        break;
      }

      // 사용자가 카운트 값을 입력하면, 
      int count = Integer.parseInt(str);

      // 기존에 생성한 스레드에 카운트 값을 설정한 후 실행을 시작시킨다.
      t.setCount(count);
      t.start();
      // 문제점?
      // - 한 번 실행이 완료된 Dead 상태의 스레드는 다시 시작시킬 수 없다.
      // - 다시 시작하려고 하면 예외가 발생한다.
      // - 즉 run() 메서드 호출이 끝나, Dead 상태가 된 스레드는 다시 start() 할 수 없다!
    }

    System.out.println("main 스레드 종료!");
    keyScan.close();
  }
}

