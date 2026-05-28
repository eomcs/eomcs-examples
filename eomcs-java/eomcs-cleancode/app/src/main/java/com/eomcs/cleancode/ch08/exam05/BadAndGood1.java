package com.eomcs.cleancode.ch08.exam05;

// 예제 1: 아직 존재하지 않는 코드를 사용하기 - NotificationService
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {
    private String name;
    private String email;
    User(String name, String email) { this.name = name; this.email = email; }
    String getName() { return name; }
    String getEmail() { return email; }
  }

  // Bad: 외부 알림 API가 나올 때까지 구현을 미룬다.
  // - 외부 API가 완성될 때까지 우리 개발이 막힌다.
  // - 서비스 코드의 의도가 TODO 주석으로만 표현된다.
  // - 테스트를 작성할 수 없다.
  // - 나중에 외부 API 형태에 우리 코드가 끌려갈 가능성이 크다.
  static class BadNotificationService {
    public void notifyUser(User user, String message) {
      // TODO: ExternalNotificationClient가 완성되면 구현
    }
  }

  static class BadNotificationClient {
    void run(BadNotificationService notificationService, User user) {
      notificationService.notifyUser(user, "가입을 환영합니다.");
      // 실제로 아무 일도 일어나지 않는다
    }
  }

  // -----------------------------------------------------------------------

  // Good: 우리가 원하는 인터페이스를 먼저 정의한다.
  // - 외부 API가 없어도 우리 코드를 개발하고 테스트할 수 있다.
  // - 서비스 코드는 우리가 원하는 말로 표현된다.
  // - 나중에 실제 API가 나오면 어댑터만 만들면 된다.
  interface NotificationSender {
    void send(String receiver, String message);
  }

  // Good: NotificationSender 인터페이스를 주입받아 사용한다.
  static class GoodNotificationService {
    private final NotificationSender notificationSender;
    GoodNotificationService(NotificationSender sender) { this.notificationSender = sender; }

    public void notifyUser(User user, String message) {
      notificationSender.send(user.getEmail(), message);
    }
  }

  // Good: 테스트용 가짜 구현 - 외부 API 없이도 동작을 검증할 수 있다.
  static class FakeNotificationSender implements NotificationSender {
    private String receiver;
    private String message;

    @Override
    public void send(String receiver, String message) {
      this.receiver = receiver;
      this.message = message;
    }

    String getReceiver() { return receiver; }
    String getMessage() { return message; }
  }

  static class GoodNotificationClient {
    void run(GoodNotificationService notificationService, User user) {
      notificationService.notifyUser(user, "가입을 환영합니다.");
    }
  }
}
