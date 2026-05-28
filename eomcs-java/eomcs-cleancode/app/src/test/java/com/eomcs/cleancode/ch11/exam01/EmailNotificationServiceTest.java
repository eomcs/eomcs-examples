package com.eomcs.cleancode.ch11.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch11.exam01.BadAndGood4.EmailNotificationService;
import com.eomcs.cleancode.ch11.exam01.BadAndGood4.FakeEmailClient;
import com.eomcs.cleancode.ch11.exam01.BadAndGood4.User;
import org.junit.jupiter.api.Test;

// 예제 4: 의존성 주입 - EmailNotificationService
//
// Bad: SmtpEmailClient를 직접 생성 — 테스트에서 실제 메일이 발송된다.
// Good: EmailClient를 생성자로 주입받으므로 테스트에서 가짜 구현을 주입할 수 있다.
//       생성과 사용이 분리되어 클래스의 책임이 명확해진다.
class EmailNotificationServiceTest {

  @Test
  void sendsWelcomeEmail() {
    FakeEmailClient emailClient = new FakeEmailClient();
    EmailNotificationService service =
        new EmailNotificationService(emailClient);

    service.sendWelcomeEmail(new User("kim@example.com"));

    assertTrue(emailClient.sent);
  }
}
