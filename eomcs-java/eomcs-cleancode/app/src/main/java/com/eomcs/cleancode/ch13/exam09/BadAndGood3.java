package com.eomcs.cleancode.ch13.exam09;

import java.util.List;
import java.util.concurrent.Executor;

// 예제 3: 스레드 코드를 교체 가능하게 만들어라
// 한 스레드, 여러 스레드, 테스트 더블, 느린 작업 등 다양한 구성으로 실행 가능해야 한다
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Message {

    private final String text;

    Message(String text) {
      this.text = text;
    }

    String text() {
      return text;
    }
  }

  interface MessageSender {
    void send(Message message);
  }

  // Bad: 항상 새 스레드를 만들어 실행 방식이 고정된다
  //   - 테스트에서 동기 실행으로 바꾸기 어렵다
  //   - 느린/빠른 실행을 조절하기 어렵다
  //   - 테스트에서 메시지 전송 여부를 검증하기 어렵다
  static class BadNotificationService {

    public void sendAll(List<Message> messages) {
      for (Message message : messages) {
        new Thread(() -> send(message)).start();
      }
    }

    private void send(Message message) {
      System.out.println("send: " + message.text());
    }
  }

  // Good: Executor와 MessageSender를 주입받아 실행 방식을 교체할 수 있다
  //   - 운영에서는 thread pool Executor + 실제 MessageSender 사용
  //   - 테스트에서는 동기 Executor(Runnable::run) + FakeMessageSender 사용
  //   - 동시성 정책을 쉽게 바꿀 수 있다
  static class NotificationService {

    private final Executor executor;
    private final MessageSender sender;

    NotificationService(Executor executor, MessageSender sender) {
      this.executor = executor;
      this.sender = sender;
    }

    public void sendAll(List<Message> messages) {
      for (Message message : messages) {
        executor.execute(() -> sender.send(message));
      }
    }
  }
}
