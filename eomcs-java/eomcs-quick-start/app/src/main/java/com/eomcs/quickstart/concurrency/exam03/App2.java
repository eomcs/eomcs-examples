package com.eomcs.quickstart.concurrency.exam03;

// volatile 키워드로 가시성(Visibility) 문제 해결:
//
// volatile의 역할:
//   1. 가시성 보장: volatile 변수에 대한 읽기/쓰기는 항상 메인 메모리를 통한다.
//      - 쓰기: CPU 캐시→레지스터를 거치지 않고 메인 메모리에 즉시 반영
//      - 읽기: 캐시 대신 메인 메모리에서 항상 최신 값을 읽는다
//   2. 명령어 재정렬 방지: volatile 변수 접근을 기준으로 앞뒤 명령어가 재정렬되지 않는다.
//
// volatile vs synchronized:
//   - volatile: 가시성만 보장. 단순 읽기·쓰기에 적합. 락 없어 오버헤드 낮음
//   - synchronized: 가시성 + 원자성 보장. 복합 연산(read-modify-write)에 필요
//
// volatile이 적합한 상황:
//   - 하나의 스레드만 쓰고, 다른 스레드는 읽기만 하는 플래그 변수
//   - 단순 boolean 상태 신호 (stop/pause 플래그 등)
//
// volatile이 적합하지 않은 상황:
//   - count++ 같은 복합 연산 (읽기→증가→쓰기가 원자적이지 않음)
//   - → App3에서 이 한계를 확인한다
//

public class App2 {

  // volatile: 항상 메인 메모리에서 읽고 쓴다 → JIT 캐싱 불가
  static volatile boolean running = true;

  public static void main(String[] args) throws InterruptedException {

    System.out.println("[해결] volatile - 가시성 문제 해결");
    System.out.println("volatile 덕분에 Worker가 running=false를 즉시 인식한다.");

    Thread worker =
        new Thread(
            () -> {
              long count = 0;
              // volatile → 루프마다 메인 메모리에서 running을 읽는다
              // JIT이 최적화해도 캐싱하지 않는다
              while (running) {
                count++;
              }
              System.out.println("  Worker: 종료 감지. count=" + String.format("%,d", count));
            },
            "Worker");

    worker.start();
    Thread.sleep(500); // App과 동일한 조건

    System.out.println("  Main: running = false 설정");
    running = false; // volatile → 메인 메모리에 즉시 반영 → Worker가 즉시 인식

    worker.join(3000);

    if (worker.isAlive()) {
      System.out.println("  Worker가 여전히 실행 중! (예상치 못한 문제)");
      worker.interrupt();
    } else {
      System.out.println("  Worker가 정상 종료됨 ✓ (volatile 덕분에 변경 즉시 인식)");
    }

    System.out.println("→ volatile은 단순 플래그 변수의 가시성 문제를 해결한다.");
    System.out.println("  단, 복합 연산(count++ 등)의 원자성은 보장하지 않는다.");
    System.out.println("  → App3에서 이 한계를 확인한다.");
  }
}
