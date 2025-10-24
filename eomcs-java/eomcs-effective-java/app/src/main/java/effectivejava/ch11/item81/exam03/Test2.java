package effectivejava.ch11.item81.exam03;

import java.util.List;
import java.util.concurrent.*;

public class Test2 {

  private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

  public static void main(String[] args) throws Exception {
    // 인터럽트 예외가 발생했을 때, 인터럽트를 복원하는 경우와 복원하지 않는 경우를 비교해보기!
    //
    ExecutorService pool = Executors.newFixedThreadPool(1);

    Future<?> f = pool.submit(new MyTask());

    // 잠깐 일 시킴
    Thread.sleep(500);

    List<Runnable> dropped = pool.shutdownNow(); // 대기 중 작업 취소 + 실행 중 스레드 interrupt()

    boolean terminated = pool.awaitTermination(2, TimeUnit.SECONDS);
    System.out.println("[GOOD] Future isDone: " + f.isDone() + ", isCancelled: " + f.isCancelled());
  }

  static class MyTask implements Runnable {
    @Override
    public void run() {
      try {
        while (true) {
          System.out.println("  [MyTask] 작업 중...");
          Thread.sleep(200); // 블로킹 포인트(인터럽트 발생 시 InterruptedException 던짐)
        }
      } catch (InterruptedException e) {
        // 다음 두 줄의 코드를 주석으로 막아서 인터럽트를 복원하지 않는 경우를 시험해보자!
        System.out.println("  [MyTask] InterruptedException 발생 — 인터럽트 상태 복원 후 종료");
        Thread.currentThread().interrupt(); // 인터럽트 상태 복원

        try {
          queue.take(); // 또 다른 블로킹 메서드
        } catch (InterruptedException ex) {
          // 인터럽트가 걸린 스레드에서 블로킹 메서드를 호출하면 다시 InterruptedException 발생한다.
          System.out.println("  [MyTask] InterruptedException 발생 during take() — 종료");
        }
      }
    }
  }
}
