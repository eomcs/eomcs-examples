// 스레드와 프로그램 종료
package com.eomcs.concurrent.ex3;

import java.util.Scanner;

public class Exam0310 {

  static class MyThread extends Thread {
    @Override
    public void run() {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("입력하시오> ");
      String input = keyboard.nextLine();
      System.out.println("입력한 문자열 => " + input);
      keyboard.close();
    }
  }

  public static void main(String[] args) {

    // main 스레드에서 새 스레드 객체 생성하기
    // => 어떤 스레드에서 만든 스레드를 그 스레드의 자식 스레드라 부른다.
    // => 즉 다음 스레드는 main 스레드의 자식 스레드이다.
    // => 자식 스레드는 부모 스레드와 같은 우선 순위를 갖는다.
    MyThread t = new MyThread(); // 우선순위 5
    t.start();

    // 모든 스레드가 완료할 때까지 JVM은 종료되지 않는다.
    System.out.println("프로그램 종료?");
  }

}


