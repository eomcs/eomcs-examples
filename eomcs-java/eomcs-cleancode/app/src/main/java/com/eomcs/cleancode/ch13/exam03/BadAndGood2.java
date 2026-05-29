package com.eomcs.cleancode.ch13.exam03;

import java.util.ArrayList;
import java.util.List;

// 예제 2: 데이터 범위 제한 - 공유 데이터의 범위를 최소화하라
public class BadAndGood2 {

  private BadAndGood2() {}

  static class User {

    private final String name;

    User(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }

  // Bad: 공유 필드를 여러 스레드에서 접근한다
  //   - users가 여러 스레드에서 공유됨
  //   - add()와 find()를 모두 synchronized로 보호해야 한다
  //   - 공유 자료를 수정하는 위치가 많을수록 임계영역을 빠뜨릴 위험이 커진다
  //   - 찾기 어려운 버그가 더욱 찾기 어려워진다
  static class BadUserService {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
      users.add(user);
    }

    public User find(int index) {
      return users.get(index);
    }
  }

  // Good: 공유 상태를 제거하고 메서드 내부 지역 변수로 제한한다
  //   - 외부에서 전달받은 리스트를 지역 변수로 복사하여 처리한다
  //   - 공유 필드가 없으므로 동기화가 필요 없다
  //   - 각 스레드가 자신만의 localUsers를 가진다
  static class UserService {

    public void process(List<User> users) {
      List<User> localUsers = new ArrayList<>(users);

      for (User user : localUsers) {
        handle(user);
      }
    }

    private void handle(User user) {
      System.out.println(user.getName());
    }
  }
}
