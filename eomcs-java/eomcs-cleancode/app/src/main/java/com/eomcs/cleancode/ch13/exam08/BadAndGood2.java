package com.eomcs.cleancode.ch13.exam08;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 예제 2: Producer-Consumer 종료 문제 - consumer에게 명확한 종료 경로를 만들어라
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: producer가 종료되면 consumer는 영원히 queue.take()에서 대기한다
  //   - consumer는 종료 신호를 받지 못하고 계속 기다릴 수 있다
  //   - 부모 스레드가 consumer 종료를 기다리면 시스템이 멈춘다
  static class BadLogSystem {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void startConsumer() {
      new Thread(() -> {
        try {
          while (true) {
            String log = queue.take();   // producer가 멈추면 여기서 영원히 대기
            save(log);
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }).start();
    }

    public void log(String message) throws InterruptedException {
      queue.put(message);
    }

    public void stopProducer() {
      // producer만 멈춘다 - consumer 종료 경로 없음
    }

    private void save(String log) {
      System.out.println("save: " + log);
    }
  }

  // Good: Poison Pill 패턴으로 consumer에게 명확한 종료 신호를 보낸다
  //   - producer가 종료 전에 특별한 종료 메시지를 큐에 넣는다
  //   - consumer는 POISON_PILL을 받으면 루프를 종료한다
  //   - join(5000)으로 무한 대기를 방지한다
  //   - 생산자-소비자 종료 프로토콜이 명확해진다
  static class LogSystem {

    private static final String POISON_PILL = "__STOP__";

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private Thread consumer;

    public void startConsumer() {
      consumer = new Thread(() -> {
        try {
          while (true) {
            String log = queue.take();

            if (POISON_PILL.equals(log)) {
              break;   // 종료 신호를 받으면 루프 탈출
            }

            save(log);
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });

      consumer.start();
    }

    public void log(String message) throws InterruptedException {
      queue.put(message);
    }

    public void shutdown() throws InterruptedException {
      queue.put(POISON_PILL);   // consumer에게 종료 신호 전달
      consumer.join(5000);      // 최대 5초만 대기
    }

    private void save(String log) {
      System.out.println("save: " + log);
    }
  }
}
