package com.eomcs.cleancode.ch05.exam02;

// 예제 3: 들여쓰기 (Indentation)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {
    private boolean active;
    User(boolean active) { this.active = active; }
    boolean isActive() { return active; }
  }

  static class UserRepository {
    void save(User u) { System.out.println("저장: " + u); }
  }

  static class EmailService {
    void sendEmail(User u) { System.out.println("이메일 전송: " + u); }
  }

  // Bad: 들여쓰기가 없어 블록 구조가 사라진다.
  // - 어떤 코드가 어떤 조건 안에 속하는지 파악하기 어렵다.
  // - 계층 구조가 눈에 들어오지 않는다.
  static class BadProcessor {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    public void process(User user) {
if (user != null) {
if (user.isActive()) {
userRepository.save(user);
emailService.sendEmail(user);
}
}
    }
  }

  // Good: 블록 깊이마다 한 단계씩 들여쓴다.
  // - 클래스 내부 → 한 단계 들여쓰기
  // - 메서드 내부 → 한 단계 들여쓰기
  // - 조건문 내부 → 한 단계 더 들여쓰기
  static class GoodProcessor {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    public void process(User user) {
      if (user != null) {
        if (user.isActive()) {
          userRepository.save(user);
          emailService.sendEmail(user);
        }
      }
    }
  }

  // Good: 들여쓰기가 깊어지면 메서드 분리를 고려한다.
  // - 중첩 조건을 isActiveUser()로 추출해 깊이를 줄인다.
  // - 코드가 평탄해지고 의도가 명확해진다.
  static class BetterProcessor {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    public void process(User user) {
      if (isActiveUser(user)) {
        userRepository.save(user);
        emailService.sendEmail(user);
      }
    }

    private boolean isActiveUser(User user) {
      return user != null && user.isActive();
    }
  }

  // -----------------------------------------------------------------------
  // 들여쓰기 무시 금지: 짧다는 이유로 한 줄에 몰아 쓰지 마라.

  static class ParentWidget {
    ParentWidget(ParentWidget parent, String text) {}
  }

  // Bad: 짧은 생성자와 메서드를 한 줄로 압축했다.
  // - 블록 경계가 보이지 않아 구조를 파악하기 어렵다.
  // - 일관성이 깨져 코드 전체의 리듬이 흐트러진다.
  static class BadCommentWidget extends ParentWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public BadCommentWidget(ParentWidget parent, String text){super(parent, text);}
    public String render() throws Exception {return ""; }
  }

  // Good: 짧더라도 블록마다 일관되게 들여쓴다.
  // - 생성자와 메서드 본문이 각자 독립된 줄에 있다.
  // - 나중에 코드가 추가될 때도 구조를 유지하기 쉽다.
  static class GoodCommentWidget extends ParentWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public GoodCommentWidget(ParentWidget parent, String text) {
      super(parent, text);
    }

    public String render() throws Exception {
      return "";
    }
  }
}
