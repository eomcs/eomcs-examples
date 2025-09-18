// Executors 태스크 프레임워크 - 스레드풀 만들고 사용하기
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam0110 {

  public static void main(String[] args) {
    // 스레드풀을 생성한다.
    // - 최대 3개의 스레드를 생성한다.
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    // 스레드풀에 작업 수행을 요청한다.
    // - 작업은 Runnable 구현체로 작성하여 넘겨준다.
    // - 스레드풀은 스레드를 생성하여 작업을 수행시킨다.
    executorService.execute(() -> System.out.printf("%s - Hello!\n", Thread.currentThread().getName()));

    System.out.println("main() 종료!");
    // JVM은 main 스레드를 종료하더라도 나머지 스레드가 종료할 때까지 기다린다.
    // 스레드풀에서 생성한 스레드가 요청한 작업을 마치더라도
    // 다음 작업을 수행하기 위해 계속 실행된 채로 대기하고 있기 때문에
    // JVM은 종료하지 않는다.
  }
}

