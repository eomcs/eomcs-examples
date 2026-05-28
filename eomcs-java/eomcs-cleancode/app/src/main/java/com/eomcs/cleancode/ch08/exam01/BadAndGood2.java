package com.eomcs.cleancode.ch08.exam01;

import java.util.HashMap;
import java.util.Map;

// 예제 2: 외부 코드 사용하기 - Map 인자 대신 도메인 클래스를 사용하라
public class BadAndGood2 {

  private BadAndGood2() {}

  interface NotificationClient {
    void send(String receiver, String title, String body);
  }

  // Bad: Map을 인자로 받는다.
  // - 문자열 키를 잘못 쓰면 런타임에야 오류가 난다.
  // - Object 타입 때문에 형변환이 필요하다.
  // - 어떤 키가 필수인지 코드만 보고 알기 어렵다.
  // - 호출자와 구현자가 Map 구조에 강하게 묶인다.
  static class BadNotificationService {
    private NotificationClient notificationClient;
    BadNotificationService(NotificationClient client) { this.notificationClient = client; }

    public void sendNotification(Map<String, Object> message) {
      String title = (String) message.get("title");     // 형변환 필요
      String body = (String) message.get("body");
      String receiver = (String) message.get("receiver");

      notificationClient.send(receiver, title, body);
    }
  }

  static class BadNotificationClient {
    void run(BadNotificationService notificationService) {
      Map<String, Object> message = new HashMap<>();
      message.put("title", "가입 완료");
      message.put("body", "회원가입이 완료되었습니다.");
      message.put("receiver", "user@test.com");
      // "reciver"처럼 오타가 있어도 컴파일 시점에 잡히지 않는다

      notificationService.sendNotification(message);
    }
  }

  // -----------------------------------------------------------------------

  // Good: 도메인 의미가 있는 클래스를 인자로 받는다.
  // - 필요한 데이터 구조가 명확한 타입으로 표현된다.
  // - 문자열 키 실수를 컴파일 시점에 잡을 수 있다.
  // - 형변환 코드가 사라진다.
  // - 어떤 값이 필수인지 생성자를 보면 바로 알 수 있다.
  static class NotificationMessage {
    private final String title;
    private final String body;
    private final String receiver;

    NotificationMessage(String title, String body, String receiver) {
      this.title = title;
      this.body = body;
      this.receiver = receiver;
    }

    String getTitle() { return title; }
    String getBody() { return body; }
    String getReceiver() { return receiver; }
  }

  static class GoodNotificationService {
    private NotificationClient notificationClient;
    GoodNotificationService(NotificationClient client) { this.notificationClient = client; }

    public void sendNotification(NotificationMessage message) {
      notificationClient.send(
          message.getReceiver(),
          message.getTitle(),
          message.getBody()
      );
    }
  }

  static class GoodNotificationClient {
    void run(GoodNotificationService notificationService) {
      NotificationMessage message = new NotificationMessage(
          "가입 완료",
          "회원가입이 완료되었습니다.",
          "user@test.com"
      );

      notificationService.sendNotification(message);
    }
  }
}
