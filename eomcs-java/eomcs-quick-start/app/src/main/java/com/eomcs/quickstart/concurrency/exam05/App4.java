package com.eomcs.quickstart.concurrency.exam05;

import java.util.concurrent.Semaphore;

// Semaphore 실용 예제 - 데이터베이스 커넥션 풀(Connection Pool):
//
// 커넥션 풀은 미리 생성한 제한된 수의 DB 연결을 여러 스레드가 공유한다.
// Semaphore로 동시에 사용 가능한 연결 수를 제한하여 DB 연결 고갈을 방지한다.
//
// 동작 흐름:
//   1. 요청 스레드: semaphore.acquire() → 사용 가능한 연결이 없으면 대기
//   2. 연결 획득 후 DB 작업 수행
//   3. 작업 완료: semaphore.release() → 대기 중인 다음 스레드가 연결 획득
//
// Semaphore가 적합한 이유:
//   - 연결 풀의 "사용 가능한 연결 수"가 permits와 자연스럽게 대응된다
//   - acquire/release로 연결 획득/반납을 직관적으로 표현할 수 있다
//   - tryAcquire(timeout)으로 연결 대기 타임아웃을 설정할 수 있다
//

public class App4 {

  static class ConnectionPool {

    private final String[] connections; // 연결 배열
    private final boolean[] inUse; // 연결 사용 여부
    private final Semaphore semaphore; // 사용 가능한 연결 수 제어
    private final Object poolLock = new Object(); // 연결 배열 접근 동기화

    public ConnectionPool(int maxConnections) {
      connections = new String[maxConnections];
      inUse = new boolean[maxConnections];
      semaphore = new Semaphore(maxConnections, true); // 공정 세마포어

      for (int i = 0; i < maxConnections; i++) {
        connections[i] = "Connection-" + (i + 1);
        inUse[i] = false;
      }
    }

    // 연결 획득 (블로킹: 사용 가능한 연결이 생길 때까지 대기)
    public String acquire() throws InterruptedException {
      semaphore.acquire(); // 사용 가능한 permits가 없으면 대기
      return checkOut();
    }

    // 연결 획득 (타임아웃: 지정 시간 내 사용 가능한 연결이 없으면 null 반환)
    public String acquire(long timeoutMs) throws InterruptedException {
      boolean acquired = semaphore.tryAcquire(
          timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS);
      if (!acquired) return null; // 타임아웃
      return checkOut();
    }

    // 연결 반납
    public void release(String connection) {
      synchronized (poolLock) {
        for (int i = 0; i < connections.length; i++) {
          if (connections[i].equals(connection) && inUse[i]) {
            inUse[i] = false;
            break;
          }
        }
      }
      semaphore.release(); // permits 증가 → 대기 중인 스레드 깨움
    }

    public int availableCount() {
      return semaphore.availablePermits();
    }

    private String checkOut() {
      synchronized (poolLock) {
        for (int i = 0; i < connections.length; i++) {
          if (!inUse[i]) {
            inUse[i] = true;
            return connections[i];
          }
        }
      }
      throw new IllegalStateException("세마포어와 풀 상태 불일치"); // 정상이면 발생 불가
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 데모 1. 연결 풀 기본 사용 - 최대 3개 연결, 8개 스레드 요청
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 1] 커넥션 풀 - 최대 3개 연결, 8개 스레드 요청");
    System.out.println("연결이 부족하면 acquire()에서 반납될 때까지 대기");
    System.out.println();

    ConnectionPool pool = new ConnectionPool(3);
    long start = System.currentTimeMillis();
    Thread[] workers = new Thread[8];

    for (int i = 0; i < 8; i++) {
      final int id = i;
      workers[i] =
          new Thread(
              () -> {
                try {
                  long t = System.currentTimeMillis() - start;
                  System.out.printf(
                      "  Worker-%d: 연결 요청 +%,4dms (사용 가능: %d/3)%n",
                      id, t, pool.availableCount());

                  String conn = pool.acquire(); // 사용 가능한 연결이 없으면 대기

                  t = System.currentTimeMillis() - start;
                  System.out.printf(
                      "  Worker-%d: [%s] 획득 +%,4dms%n", id, conn, t);

                  Thread.sleep(600); // DB 작업 시뮬레이션

                  pool.release(conn); // 연결 반납

                  long elapsed = System.currentTimeMillis() - start;
                  System.out.printf(
                      "  Worker-%d: [%s] 반납 +%,4dms%n", id, conn, elapsed);
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              },
              "Worker-" + id);
    }

    for (Thread w : workers) w.start();
    for (Thread w : workers) w.join();

    long totalTime = System.currentTimeMillis() - start;
    System.out.printf("%n  총 소요 시간: %,dms%n", totalTime);
    System.out.println("→ 3개씩 사용 중, 나머지는 대기 → 연결 고갈 없이 순차 처리 ✓");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 2. 타임아웃 - 지정 시간 내 연결을 못 얻으면 포기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 2] 타임아웃 - 500ms 내 연결을 못 얻으면 포기");

    ConnectionPool pool2 = new ConnectionPool(2);
    long start2 = System.currentTimeMillis();

    // 2개 연결을 모두 점유하는 스레드
    Thread occupier1 =
        new Thread(
            () -> {
              try {
                String conn = pool2.acquire();
                System.out.printf("  Occupier-1: [%s] 획득%n", conn);
                Thread.sleep(2000); // 2초간 점유
                pool2.release(conn);
                System.out.printf("  Occupier-1: [%s] 반납%n", conn);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Occupier-1");

    Thread occupier2 =
        new Thread(
            () -> {
              try {
                String conn = pool2.acquire();
                System.out.printf("  Occupier-2: [%s] 획득%n", conn);
                Thread.sleep(2000);
                pool2.release(conn);
                System.out.printf("  Occupier-2: [%s] 반납%n", conn);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Occupier-2");

    occupier1.start();
    occupier2.start();
    Thread.sleep(200); // Occupier들이 연결을 잡을 시간

    // 500ms 타임아웃으로 연결 요청 → 2초 점유 중이므로 실패 예상
    Thread timeoutWorker =
        new Thread(
            () -> {
              try {
                long t = System.currentTimeMillis() - start2;
                System.out.printf("  TimeoutWorker: 연결 요청(타임아웃 500ms) +%,3dms%n", t);
                String conn = pool2.acquire(500); // 500ms 내 연결 없으면 null

                if (conn != null) {
                  System.out.printf("  TimeoutWorker: [%s] 획득 성공%n", conn);
                  pool2.release(conn);
                } else {
                  long elapsed = System.currentTimeMillis() - start2;
                  System.out.printf(
                      "  TimeoutWorker: 500ms 초과 → 연결 획득 실패, 요청 포기 +%,3dms ✓%n", elapsed);
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "TimeoutWorker");

    timeoutWorker.start();
    timeoutWorker.join();
    occupier1.join();
    occupier2.join();
    System.out.println("→ 타임아웃으로 무한 대기 방지. 빠른 실패(fail-fast) 전략 구현 ✓");
  }
}
