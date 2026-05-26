package com.eomcs.cleancode.ch07.exam05;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 예제 2: 호출자를 고려해 예외 클래스를 정의하라 - sendEmail
public class BadAndGood2 {

  private BadAndGood2() {}

  interface EmailClient {
    void send(String email) throws IOException, TimeoutException;
  }

  // Bad: 내부 기술 예외(IOException, TimeoutException)를 여러 개 노출한다.
  // - 호출자가 네트워크, 타임아웃 같은 내부 기술 세부사항을 알아야 한다.
  // - 호출 코드에 catch 블록이 많아져 복잡해진다.
  // - 이메일 라이브러리를 교체하면 호출자 코드도 바꿔야 한다.
  static class BadEmailService {
    private EmailClient emailClient;
    BadEmailService(EmailClient client) { this.emailClient = client; }

    public void sendEmail(String email) throws IOException, TimeoutException {
      emailClient.send(email);
    }
  }

  static class BadEmailClient {
    void run(BadEmailService emailService) {
      try {
        emailService.sendEmail("test@test.com");
      } catch (IOException e) {
        System.out.println("네트워크 오류");
      } catch (TimeoutException e) {
        System.out.println("타임아웃 발생");
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 여러 기술 예외를 하나의 도메인 예외로 통합한다.
  static class EmailSendFailedException extends RuntimeException {
    EmailSendFailedException(String message, Throwable cause) { super(message, cause); }
  }

  // Good: IOException과 TimeoutException을 하나의 EmailSendFailedException으로 묶는다.
  // - 호출자는 하나의 예외만 알면 된다.
  // - 내부 기술 구현이 숨겨진다.
  // - 이메일 라이브러리를 바꿔도 호출자 코드는 변경되지 않는다.
  static class GoodEmailService {
    private EmailClient emailClient;
    GoodEmailService(EmailClient client) { this.emailClient = client; }

    public void sendEmail(String email) {
      try {
        emailClient.send(email);
      } catch (IOException | TimeoutException e) {
        throw new EmailSendFailedException(
            "이메일 전송 실패. email=" + email,
            e
        );
      }
    }
  }

  static class GoodEmailClient {
    void run(GoodEmailService emailService) {
      try {
        emailService.sendEmail("test@test.com");
      } catch (EmailSendFailedException e) {
        System.out.println("이메일 전송 실패");
      }
    }
  }
}
