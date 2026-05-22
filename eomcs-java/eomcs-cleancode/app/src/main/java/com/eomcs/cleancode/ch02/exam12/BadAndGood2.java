package com.eomcs.cleancode.ch02.exam12;

public class BadAndGood2 {

  static class Account {
    String id;
    String name;
  }

  // Bad
  // - update라는 동일한 단어를 서로 다른 의미로 사용한다.
  //   - BadAccountService.update(): DB에서 계정 정보 수정
  //   - BadCacheService.update(): 캐시 항목 덮어쓰기
  // - 두 동작은 다른데 이름이 같아 사용자가 의미 차이를 추측해야 한다.
  static class BadAccountService {
    void update(Account account) {
      System.out.println("계정 정보 수정: " + account.name);
    }
  }

  static class BadCacheService {
    void update(String key, Object value) {
      System.out.println("캐시 덮어쓰기: " + key + " = " + value);
    }
  }

  // Good
  // - updateAccount → 수정 대상(Account)을 명시해 의미가 명확하다.
  // - put → 캐시에 값을 저장하는 표준 표현(Map.put과 동일)으로 기존 의미를 존중한다.
  static class GoodAccountService {
    void updateAccount(Account account) {
      System.out.println("계정 정보 수정: " + account.name);
    }
  }

  static class GoodCacheService {
    void put(String key, Object value) {
      System.out.println("캐시 저장: " + key + " = " + value);
    }
  }
}
