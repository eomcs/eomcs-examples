package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood2 {

  static class User {
    String name;
    String email;
    boolean active;
    User(String name, String email, boolean active) {
      this.name = name; this.email = email; this.active = active;
    }
  }

  static class UserDto {
    String name;
    String email;
  }

  // Good: 단항 형식은 가장 이해하기 쉬운 형태다.
  // 크게 세 가지 패턴이 있으며, 각각 자연스럽게 읽힌다.
  static class UserService {
    // 질문 형식 (boolean 반환): 단일 객체의 상태를 묻는다.
    boolean isActive(User user) {
      return user.active;
    }

    // 변환 형식: 단일 객체를 다른 타입으로 변환한다.
    UserDto toDto(User user) {
      UserDto dto = new UserDto();
      dto.name = user.name;
      dto.email = user.email;
      return dto;
    }

    // 명령 형식: 단일 객체에 대해 동작을 수행한다.
    void save(User user) {
      System.out.println("사용자 저장: " + user.name);
    }
  }
}
