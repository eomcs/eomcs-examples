package com.eomcs.cleancode.ch02.exam08;

public class BadAndGood2 {

  static class User {
    int id;
    String name;
    String email;
  }

  // Bad
  // - UserManager 가 저장, 삭제, 이메일 발송까지 담당 → 너무 많은 책임.
  // - 'Manager'는 의미가 없고, 이 클래스가 무엇을 하는지 역할이 불명확하다.
  static class UserManager {
    void save(User user) {
      System.out.println("DB에 사용자 저장: " + user.name);
    }
    void delete(User user) {
      System.out.println("DB에서 사용자 삭제: " + user.name);
    }
    void sendEmail(User user) {
      System.out.println("이메일 발송: " + user.email);
    }
  }

  // Good
  // - UserRepository → 저장소 역할(저장, 삭제)만 담당. 이름이 책임을 설명한다.
  // - EmailService → 이메일 발송만 담당. 역할별로 분리되어 명확하다.
  static class UserRepository {
    void save(User user) {
      System.out.println("DB에 사용자 저장: " + user.name);
    }
    void delete(User user) {
      System.out.println("DB에서 사용자 삭제: " + user.name);
    }
  }

  static class EmailService {
    void sendEmail(User user) {
      System.out.println("이메일 발송: " + user.email);
    }
  }
}
