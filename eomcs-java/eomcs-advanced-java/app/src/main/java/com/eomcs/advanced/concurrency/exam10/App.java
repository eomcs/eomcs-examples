package com.eomcs.advanced.concurrency.exam10;

// Virtual Thread 기본:
//
// 가상 스레드(Virtual Thread)는 JVM이 관리하는 가벼운 스레드다.
// 기존 플랫폼 스레드(OS 스레드)보다 생성 비용이 낮아, 요청이나 작업마다 스레드를 하나씩
// 만들어도 부담이 훨씬 작다. Java 21부터 정식 기능으로 사용할 수 있다.
//
// 플랫폼 스레드 vs 가상 스레드:
//   플랫폼 스레드 : OS 스레드와 1:1 매핑. 생성 비용 높음. 수천 개가 한계.
//   가상 스레드   : 캐리어 스레드(OS 스레드)에 N:M으로 스케줄링. 수백만 개도 가능.
//
// 캐리어 스레드(Carrier Thread):
//   - 가상 스레드가 실제 실행될 때 올라타는 플랫폼 스레드
//   - 가상 스레드가 I/O로 블로킹되면 캐리어 스레드를 자동으로 해제(unmount)해 다른 가상 스레드가 사용할 수 있게 한다.
//
// 생성 방법:
//   Thread.startVirtualThread(task)           - 이름 없이 즉시 시작 (가장 간단)
//   Thread.ofVirtual().name("name").start(task) - 이름 지정 후 시작
//   Thread.ofVirtual().unstarted(task)        - 나중에 start() 호출
//
// isVirtual():
//   - 가상 스레드이면 true, 플랫폼 스레드이면 false를 반환한다.

public class App {

  public static void main(String[] args) throws InterruptedException {

    System.out.println("[기본] Virtual Thread 생성");
    System.out.println("가상 스레드와 플랫폼 스레드의 이름, 종류를 비교한다.");
    System.out.println();

    // Thread.ofVirtual(): 가상 스레드 빌더를 반환한다.
    // - name(): 스레드 이름 지정
    // - start(): 작업을 설정하고 즉시 시작한다. Thread 객체를 반환한다.
    Thread virtualThread =
        Thread.ofVirtual()
            .name("virtual-worker")
            .start(
                () -> {
                  Thread current = Thread.currentThread();
                  System.out.printf("  가상 스레드 이름: %s%n", current.getName());
                  System.out.printf("  isVirtual(): %s%n", current.isVirtual()); // true
                });

    virtualThread.join(); // 가상 스레드 완료 대기 (플랫폼 스레드와 동일한 join() API)

    // Thread.ofPlatform(): 플랫폼 스레드(OS 스레드) 빌더를 반환한다.
    // - 기존 new Thread(task)와 동일하지만 빌더 스타일로 생성한다.
    Thread platformThread =
        Thread.ofPlatform()
            .name("platform-worker")
            .start(
                () -> {
                  Thread current = Thread.currentThread();
                  System.out.printf("  플랫폼 스레드 이름: %s%n", current.getName());
                  System.out.printf("  isVirtual(): %s%n", current.isVirtual()); // false
                });

    platformThread.join();

    // Thread.startVirtualThread(): 이름 없는 가상 스레드를 가장 간단하게 시작하는 방법
    // - Thread.ofVirtual().start(task)의 축약형이다.
    // - 이름이 지정되지 않으므로 getName()은 빈 문자열("")을 반환한다.
    Thread quick =
        Thread.startVirtualThread(
            () -> {
              String name = Thread.currentThread().getName();
              if (name.isBlank()) {
                name = "(이름 없음)"; // startVirtualThread()로 생성한 스레드는 이름이 없다.
              }
              System.out.printf("  startVirtualThread() 실행: %s%n", name);
            });

    quick.join();

    System.out.println("→ 가상 스레드는 Thread 객체이지만, OS 스레드와 1:1로 묶이지 않는 가벼운 실행 단위다.");
  }
}
