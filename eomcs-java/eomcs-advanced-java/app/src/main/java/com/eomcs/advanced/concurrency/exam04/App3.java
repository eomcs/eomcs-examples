package com.eomcs.advanced.concurrency.exam04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// ReentrantReadWriteLock - 읽기/쓰기 락 분리:
//
// 일반 ReentrantLock은 읽기 스레드끼리도 서로 배제한다.
// 읽기가 많고 쓰기가 드문 상황에서는 읽기 스레드끼리 동시 접근을 허용하면 성능이 높아진다.
//
// ReadWriteLock 규칙:
//   - 읽기 락: 여러 스레드가 동시에 획득 가능 (쓰기 락이 없을 때)
//   - 쓰기 락: 한 번에 한 스레드만 획득 가능 (모든 읽기·쓰기 락 배제)
//
//   상태표:
//   현재 락 상태       읽기 락 추가 획득  쓰기 락 추가 획득
//   ─────────────────────────────────────────────────────
//   아무 락 없음       O                 O
//   읽기 락 보유 중    O (동시 허용)      X (쓰기는 대기)
//   쓰기 락 보유 중    X (읽기도 대기)    X (쓰기도 대기)
//
// 사용 패턴:
//   ReadWriteLock rwLock = new ReentrantReadWriteLock();
//   Lock readLock  = rwLock.readLock();
//   Lock writeLock = rwLock.writeLock();
//
//   // 읽기
//   readLock.lock();
//   try { return data; } finally { readLock.unlock(); }
//
//   // 쓰기
//   writeLock.lock();
//   try { data = newValue; } finally { writeLock.unlock(); }
//
// 적합한 상황:
//   - 읽기(90%)가 쓰기(10%)보다 훨씬 많은 경우
//   - 예: 캐시, 설정 정보, 공유 사전 등
//

public class App3 {

  // 읽기/쓰기 분리 락으로 보호하는 공유 캐시
  static class SharedCache {

    private final Map<String, String> store = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void put(String key, String value) {
      writeLock.lock(); // 쓰기 락: 모든 읽기·쓰기 스레드 배제
      try {
        Thread.sleep(200); // 쓰기 작업 시뮬레이션
        store.put(key, value);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } finally {
        writeLock.unlock();
      }
    }

    public String get(String key) {
      readLock.lock(); // 읽기 락: 다른 읽기 스레드와 동시 진입 허용
      try {
        Thread.sleep(300); // 읽기 작업 시뮬레이션 (시간이 걸리는 연산)
        return store.get(key);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return null;
      } finally {
        readLock.unlock();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {

    SharedCache cache = new SharedCache();
    cache.put("lang", "Java"); // 초기 데이터 삽입

    // ─────────────────────────────────────────────────────────────
    // 데모 1. ReadWriteLock: 읽기 스레드 5개 동시 실행
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 1] ReadWriteLock - 읽기 스레드 5개 동시 실행");
    System.out.println("각 읽기 작업은 300ms 소요. 5개가 동시에 실행되면 총 시간 ≈ 300ms");

    long start = System.currentTimeMillis();
    Thread[] readers = new Thread[5];

    for (int i = 0; i < 5; i++) {
      final int id = i;
      readers[i] =
          new Thread(
              () -> {
                long t = System.currentTimeMillis() - start;
                String val = cache.get("lang");
                long elapsed = System.currentTimeMillis() - start;
                System.out.printf(
                    "  Reader-%d: 시작 +%,3dms, 완료 +%,3dms, 값=%s%n", id, t, elapsed, val);
              },
              "Reader-" + i);
    }

    for (Thread r : readers) r.start();
    for (Thread r : readers) r.join();

    long totalTime = System.currentTimeMillis() - start;
    System.out.printf("  총 소요 시간: %,dms (읽기 스레드 동시 실행 → 300ms에 가까울수록 정상)%n", totalTime);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 2. ReadWriteLock: 읽기 스레드 실행 중 쓰기 스레드 대기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 2] 읽기 스레드 실행 중 쓰기 스레드는 대기");
    System.out.println("읽기 3개(300ms) 시작 → 쓰기가 100ms 후 시도 → 읽기 완료 후 쓰기 실행");

    long start2 = System.currentTimeMillis();
    Thread[] readers2 = new Thread[3];

    for (int i = 0; i < 3; i++) {
      final int id = i;
      readers2[i] =
          new Thread(
              () -> {
                long t = System.currentTimeMillis() - start2;
                String val = cache.get("lang");
                long elapsed = System.currentTimeMillis() - start2;
                System.out.printf(
                    "  Reader-%d: 시작 +%,3dms, 완료 +%,3dms, 값=%s%n", id, t, elapsed, val);
              },
              "Reader2-" + i);
    }

    Thread writer =
        new Thread(
            () -> {
              try {
                Thread.sleep(100); // 읽기 스레드들이 진행 중일 때 쓰기 시도
                long t = System.currentTimeMillis() - start2;
                System.out.printf(
                    "  Writer:   시작 +%,3dms (읽기 락 해제 대기 중...)%n", t);
                cache.put("lang", "Kotlin");
                long elapsed = System.currentTimeMillis() - start2;
                System.out.printf("  Writer:   완료 +%,3dms → 값 변경 완료%n", elapsed);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Writer");

    for (Thread r : readers2) r.start();
    writer.start();
    for (Thread r : readers2) r.join();
    writer.join();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 3. 비교: 일반 ReentrantLock은 읽기 스레드도 순차 실행
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 3] 비교 - 일반 ReentrantLock은 읽기 스레드도 순차 실행");
    System.out.println("각 읽기 작업 300ms, 5개 순차 실행 → 총 시간 ≈ 1,500ms");

    Lock regularLock = new java.util.concurrent.locks.ReentrantLock();
    long start3 = System.currentTimeMillis();
    Thread[] readers3 = new Thread[5];

    for (int i = 0; i < 5; i++) {
      final int id = i;
      readers3[i] =
          new Thread(
              () -> {
                regularLock.lock();
                try {
                  long t = System.currentTimeMillis() - start3;
                  Thread.sleep(300); // 읽기 작업 시뮬레이션
                  long elapsed = System.currentTimeMillis() - start3;
                  System.out.printf(
                      "  Reader-%d: 시작 +%,4dms, 완료 +%,4dms%n", id, t, elapsed);
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } finally {
                  regularLock.unlock();
                }
              },
              "RegReader-" + id);
    }

    for (Thread r : readers3) r.start();
    for (Thread r : readers3) r.join();

    long totalTime3 = System.currentTimeMillis() - start3;
    System.out.printf("  총 소요 시간: %,dms (순차 실행 → 1,500ms에 가까울수록 정상)%n", totalTime3);
    System.out.println();
    System.out.println("정리:");
    System.out.println("  ReadWriteLock: 읽기 스레드 동시 실행 → ≈ 300ms");
    System.out.println("  ReentrantLock: 읽기 스레드도 순차 실행 → ≈ 1,500ms");
    System.out.println("  읽기가 많고 쓰기가 드문 경우 ReadWriteLock이 처리량을 크게 높인다.");
  }
}
