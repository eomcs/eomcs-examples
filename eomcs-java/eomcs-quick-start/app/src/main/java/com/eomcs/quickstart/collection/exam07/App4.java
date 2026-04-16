package com.eomcs.quickstart.collection.exam07;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// BlockingQueue:
// - java.util.concurrent 패키지에 소속되어 있다.
// - Queue 인터페이스를 확장하며, 스레드 안전하고 블로킹(대기) 동작을 지원한다.
// - 생산자-소비자(Producer-Consumer) 패턴 구현에 최적화된 자료구조이다.
//
// 주요 메서드와 동작 방식:
// ┌─────────────┬─────────────────────────────────────────────┐
// │ 동작        │ 예외 발생     │ 특수값 반환  │ 블로킹       │ 타임아웃      │
// ├─────────────┼───────────────┼──────────────┼──────────────┼───────────────┤
// │ 삽입        │ add(e)        │ offer(e)     │ put(e)       │ offer(e, t)   │
// │ 제거        │ remove()      │ poll()       │ take()       │ poll(t)       │
// │ 검사(peek)  │ element()     │ peek()       │ -            │ -             │
// └─────────────┴───────────────┴──────────────┴──────────────┴───────────────┘
//   put(e)  : 큐가 가득 차면 공간이 생길 때까지 대기
//   take()  : 큐가 비어 있으면 항목이 생길 때까지 대기
//
// 주요 구현체:
//   LinkedBlockingQueue : 연결 리스트 기반. 최대 용량 지정 가능(미지정 시 Integer.MAX_VALUE)
//   ArrayBlockingQueue  : 배열 기반. 생성 시 최대 용량을 반드시 지정. 공정성(fairness) 옵션 있음
//   PriorityBlockingQueue : 우선순위 기반. 용량 제한 없음
//   SynchronousQueue    : 용량 0. put()과 take()가 직접 만날 때만 전달
//   DelayQueue          : 만료 시간이 지난 항목만 꺼낼 수 있는 큐
//

public class App4 {

  public static void main(String[] args) throws InterruptedException {

    // 1. LinkedBlockingQueue - 기본 사용
    System.out.println("[LinkedBlockingQueue - 기본 사용]");
    BlockingQueue<String> lbq = new LinkedBlockingQueue<>(); // 용량 무제한
    lbq.put("item-1");
    lbq.put("item-2");
    lbq.put("item-3");
    System.out.println("큐: " + lbq);
    System.out.println("take(): " + lbq.take()); // item-1 (FIFO)
    System.out.println("peek(): " + lbq.peek()); // item-2 (제거 안 함)
    System.out.println("poll(): " + lbq.poll()); // item-2 (제거)
    System.out.println("남은 큐: " + lbq);

    // 2. ArrayBlockingQueue - 고정 용량
    System.out.println("\n[ArrayBlockingQueue - 고정 용량]");
    BlockingQueue<Integer> abq = new ArrayBlockingQueue<>(3); // 최대 3개
    abq.put(1);
    abq.put(2);
    abq.put(3);
    System.out.println("큐(용량 3): " + abq);

    // offer() - 가득 찬 경우 즉시 false 반환 (블로킹 없음)
    boolean added = abq.offer(4);
    System.out.println("offer(4) → " + added + " (가득 참)");

    // offer(e, timeout) - 지정 시간 동안 대기
    boolean addedWithTimeout = abq.offer(4, 100, TimeUnit.MILLISECONDS);
    System.out.println("offer(4, 100ms) → " + addedWithTimeout + " (타임아웃)");

    // 3. 생산자-소비자 패턴 - put() / take() 블로킹 활용
    System.out.println("\n[생산자-소비자 패턴 - LinkedBlockingQueue]");
    BlockingQueue<String> queue = new LinkedBlockingQueue<>(5); // 최대 5개

    // 생산자 스레드
    Thread producer = new Thread(() -> {
      try {
        for (int i = 1; i <= 5; i++) {
          String item = "item-" + i;
          queue.put(item); // 큐가 가득 차면 대기
          System.out.println("  [생산자] 생산: " + item + " | 큐 크기: " + queue.size());
          Thread.sleep(50);
        }
        queue.put("DONE"); // 종료 신호
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    // 소비자 스레드
    Thread consumer = new Thread(() -> {
      try {
        while (true) {
          String item = queue.take(); // 큐가 비어 있으면 대기
          if ("DONE".equals(item)) break;
          System.out.println("  [소비자] 소비: " + item + " | 큐 크기: " + queue.size());
          Thread.sleep(100);
        }
        System.out.println("  [소비자] 종료");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    producer.start();
    consumer.start();
    producer.join();
    consumer.join();

    // 4. 다중 생산자 - 단일 소비자
    System.out.println("\n[다중 생산자 - 단일 소비자]");
    BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(10);

    // 생산자 2개
    Thread producer1 = new Thread(() -> {
      try {
        for (int i = 1; i <= 3; i++) {
          sharedQueue.put(i);
          System.out.println("  [생산자1] put: " + i);
        }
      } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    });

    Thread producer2 = new Thread(() -> {
      try {
        for (int i = 11; i <= 13; i++) {
          sharedQueue.put(i);
          System.out.println("  [생산자2] put: " + i);
        }
      } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    });

    // 소비자 1개
    Thread consumer2 = new Thread(() -> {
      try {
        // poll(timeout): 지정 시간 동안 대기 후 없으면 null 반환
        Integer item;
        while ((item = sharedQueue.poll(200, TimeUnit.MILLISECONDS)) != null) {
          System.out.println("  [소비자] take: " + item);
        }
        System.out.println("  [소비자] 큐 소진, 종료");
      } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    });

    producer1.start();
    producer2.start();
    Thread.sleep(50); // 생산자가 먼저 데이터를 넣도록 잠시 대기
    consumer2.start();
    producer1.join();
    producer2.join();
    consumer2.join();

    // 5. ArrayBlockingQueue 공정성(fairness) 옵션
    System.out.println("\n[ArrayBlockingQueue 공정성(fairness=true)]");
    // fairness=true: 대기 중인 스레드에 FIFO 순서로 접근 기회 부여
    // fairness=false(기본): 순서 보장 없음, 성능 우선
    BlockingQueue<String> fairQueue = new ArrayBlockingQueue<>(5, true);
    fairQueue.put("first");
    fairQueue.put("second");
    System.out.println("공정 큐: " + fairQueue);
    System.out.println("take(): " + fairQueue.take()); // first (FIFO 보장)
  }
}
