// Executors 태스크 프레임워크 - 스레드풀 종료하기
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam0120 {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.execute( 
        () -> {
          System.out.printf("%s - Hello!\n", Thread.currentThread().getName());

          try { 
            Thread.sleep(10000); // 현재 스레드를 10초 동안 멈춘다.
          } catch (Exception e) {}

          System.out.printf("%s 스레드 종료!\n", Thread.currentThread().getName());
        });

    // 스레드풀에 있는 모든 스레드들이 요청한 작업을 끝내면
    // 종료하도록 지시한다.
    // 모든 스레드가 종료될 때까지 기다리지 않고 바로 리턴한다.
    // shutdown() 호출 이후에는 새 작업 요청은 받지 않는다.
    // 즉 execute()를 호출하면 예외가 발생한다.
    executorService.shutdown();

    System.out.println("main() 종료!");
  }
}

