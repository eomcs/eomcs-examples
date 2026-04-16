package com.eomcs.quickstart.concurrency.exam02;

// synchronized 블록 락 범위 최소화:
//
// 락 범위를 최소화하면 동기화 구간을 줄여 스레드 대기 시간을 낮추고
// 처리량(throughput)을 높일 수 있다.
//
// 핵심 원칙:
//   - 공유 자원에 접근하는 구간만 synchronized 블록으로 감싼다.
//   - 락이 필요 없는 연산(준비 작업, 계산, 로깅 등)은 블록 바깥에서 실행한다.
//   - 락 보유 중에 I/O, 네트워크 호출, 장시간 계산을 하지 않도록 주의한다.
//
// 비교:
//   // 락 범위 과도 (불필요한 대기 발생)
//   synchronized (lock) {
//     int value = i * 2;       // 공유 자원과 무관한 계산 → 락 불필요
//     count[0] += value;       // 공유 자원 접근
//   }
//
//   // 락 범위 최소화 (권장)
//   int value = i * 2;         // 락 바깥: 병렬 실행 허용
//   synchronized (lock) {
//     count[0] += value;       // 공유 자원 접근만 보호
//   }
//

public class App4 {

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // synchronized 블록 락 범위 최소화 예시
    // ─────────────────────────────────────────────────────────────
    // 두 스레드가 각각 0+2+4+...+1998 = 999,000을 count에 더한다.
    // 기대 결과: 999,000 × 2 = 1,998,000
    //
    // 락 바깥(value 계산)은 두 스레드가 동시에 실행한다.
    // 락 안(count 갱신)은 한 번에 한 스레드만 실행한다.
    //
    System.out.println("[참고] synchronized 블록 - 락 범위 최소화");
    System.out.println("두 스레드가 각각 0+2+4+...+1998 을 count에 더한다.");
    System.out.println("기대 결과: 999,000 × 2 = 1,998,000");

    Object lock = new Object();
    int[] count = {0};

    Thread t1 =
        new Thread(
            () -> {
              for (int i = 0; i < 1000; i++) {
                int value = i * 2; // 락 바깥: 준비 작업 (동시 실행 허용)
                synchronized (lock) {
                  count[0] += value; // 공유 자원 접근만 보호
                } // 락 해제 → 다른 스레드 즉시 진입 가능
              }
            },
            "T1");

    Thread t2 =
        new Thread(
            () -> {
              for (int i = 0; i < 1000; i++) {
                int value = i * 2; // 락 바깥: 준비 작업 (동시 실행 허용)
                synchronized (lock) {
                  count[0] += value; // 공유 자원 접근만 보호
                }
              }
            },
            "T2");

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    // 0+2+4+...+1998 = 999*1000 = 999,000, 두 스레드 합산 = 1,998,000
    System.out.printf(
        "  락 범위 최소화 결과: %,d (기대: %,d) [%s]%n",
        count[0], 1_998_000, count[0] == 1_998_000 ? "정상 ✓" : "오류!");
  }
}
