package com.eomcs.cleancode.ch13.exam09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eomcs.cleancode.ch13.exam09.BadAndGood3.Message;
import com.eomcs.cleancode.ch13.exam09.BadAndGood3.MessageSender;
import com.eomcs.cleancode.ch13.exam09.BadAndGood3.NotificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;

// 예제 3: 스레드 코드를 교체 가능하게 만들어라
// 테스트에서는 동기 Executor와 FakeMessageSender로 교체하여 검증한다
class BadAndGood3Test {

  // 즉시 실행 Executor: 별도 스레드 없이 호출 스레드에서 바로 실행한다
  private final Executor sameThreadExecutor = Runnable::run;

  @Test
  void sendsMessagesWithoutRealThreads() {
    FakeMessageSender sender = new FakeMessageSender();
    NotificationService service = new NotificationService(sameThreadExecutor, sender);

    service.sendAll(List.of(new Message("A"), new Message("B")));

    assertEquals(2, sender.sentCount());
  }

  @Test
  void recordsSentMessageTexts() {
    FakeMessageSender sender = new FakeMessageSender();
    NotificationService service = new NotificationService(sameThreadExecutor, sender);

    service.sendAll(List.of(new Message("hello"), new Message("world")));

    assertEquals(List.of("hello", "world"), sender.sentTexts());
  }

  // 테스트 더블: 실제 전송 없이 전송 횟수와 내용을 기록한다
  static class FakeMessageSender implements MessageSender {

    private final List<String> texts = new ArrayList<>();

    @Override
    public void send(Message message) {
      texts.add(message.text());
    }

    int sentCount() {
      return texts.size();
    }

    List<String> sentTexts() {
      return texts;
    }
  }
}
