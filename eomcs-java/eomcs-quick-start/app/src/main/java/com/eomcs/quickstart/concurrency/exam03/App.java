package com.eomcs.quickstart.concurrency.exam03;

// volatile 없을 때 가시성(Visibility) 문제:
//
// CPU 캐시와 가시성 문제:
//   - 현대 CPU는 성능을 위해 변수를 메인 메모리 대신 레지스터나 CPU 캐시에 저장한다.
//   - JIT 컴파일러는 루프 내에서 값이 바뀌지 않는다고 판단하면 변수를
//     레지스터에 고정(캐시)하여 메인 메모리 접근을 생략할 수 있다.
//   - 결과적으로 한 스레드가 변수를 변경해도 다른 스레드는 캐시된 이전 값을
//     계속 읽는다 → 가시성(Visibility) 문제
//
// 재현 조건:
//   - 워커 스레드가 tight loop(메서드 호출 없는 단순 루프)를 충분히 반복한다.
//   - JIT이 최적화(약 10,000회 반복 후)하면 running이 레지스터에 고정된다.
//   - 이후 main 스레드가 running = false를 써도 워커는 알아채지 못한다.
//
// 주의:
//   - JVM 구현·버전·실행 옵션에 따라 재현 여부가 달라질 수 있다.
//   - -server 플래그나 충분한 warm-up 시간이 있을 때 더 잘 재현된다.
//   - 프로그램이 무한 루프에 빠지지 않도록 최대 대기 시간(3초)을 설정했다.
//

public class App {

  // volatile 없음: JIT이 이 값을 레지스터에 캐싱할 수 있다
  static boolean running = true;

  public static void main(String[] args) throws InterruptedException {

    System.out.println("[문제] volatile 없음 - 가시성 문제");
    System.out.println("Main이 running=false를 써도 Worker가 인식하지 못할 수 있다.");

    Thread worker =
        new Thread(
            () -> {
              long count = 0;
              // JIT 최적화 후 running이 레지스터에 고정되면
              // main이 running=false를 써도 이 조건이 갱신되지 않는다
              while (running) {
                count++; // 아무 메서드도 호출하지 않는 tight loop → JIT 최적화 대상
              }
              System.out.println("  Worker: 종료 감지. count=" + String.format("%,d", count));
            },
            "Worker");

    worker.start();
    Thread.sleep(500); // JIT이 최적화를 수행할 시간을 충분히 준다

    System.out.println("  Main: running = false 설정");
    running = false; // Worker가 이 변경을 보지 못할 수 있다

    worker.join(3000); // 최대 3초 대기

    if (worker.isAlive()) {
      System.out.println("  Worker가 여전히 실행 중! → 가시성 문제 재현 성공");
      System.out.println("  (main의 running=false가 Worker의 캐시에 반영되지 않음)");
      worker.interrupt(); // 프로그램 종료를 위해 강제 인터럽트
    } else {
      System.out.println("  Worker가 정상 종료됨 (이 JVM에서는 재현 안 됨)");
      System.out.println("  → JVM·OS·하드웨어에 따라 재현 여부가 다를 수 있다.");
    }

    System.out.println("→ volatile 없으면 스레드 간 변수 변경이 전달되지 않을 수 있다.");
    System.out.println("  이 문제는 App2에서 volatile 키워드로 해결한다.");
  }
}
