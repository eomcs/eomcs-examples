package com.eomcs.cleancode.ch13.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 예제 1: 부모 스레드가 자식 스레드를 영원히 기다리는 경우
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: worker가 끝날 방법이 없어 shutdown()이 영원히 대기한다
  //   - worker는 while (true)로 계속 실행된다
  //   - shutdown()은 join()으로 종료를 기다린다
  //   - 하지만 worker는 끝나지 않는다
  //   - 부모 스레드는 영원히 대기한다
  static class BadServer {

    private final List<Thread> workers = new ArrayList<>();

    public void start() {
      Thread worker = new Thread(() -> {
        while (true) {   // 종료 조건이 없다
          doWork();
        }
      });

      workers.add(worker);
      worker.start();
    }

    public void shutdown() throws InterruptedException {
      for (Thread worker : workers) {
        worker.join();   // worker가 끝나기를 기다리지만 영원히 끝나지 않는다
      }

      releaseResources();
    }

    private void doWork() {
      System.out.println("working");
    }

    private void releaseResources() {
      System.out.println("release resources");
    }
  }

  // Good: 종료 신호를 interrupt로 보내고 timeout을 둬 무한 대기를 방지한다
  //   - 종료 신호를 interrupt로 보낸다
  //   - worker는 interrupt 상태를 확인하여 루프를 빠져나온다
  //   - awaitTermination()에 timeout을 둬 영원히 기다리지 않는다
  static class Server {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void start() {
      executor.submit(() -> {
        while (!Thread.currentThread().isInterrupted()) {   // 종료 신호 확인
          doWork();
        }
      });
    }

    public void shutdown() throws InterruptedException {
      executor.shutdownNow();   // 모든 worker에 interrupt 신호 전송

      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        System.out.println("workers did not stop in time");
      }

      releaseResources();
    }

    private void doWork() {
      System.out.println("working");
    }

    private void releaseResources() {
      System.out.println("release resources");
    }
  }
}
