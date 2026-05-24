package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood5 {

  // Bad: 이력을 기록하는 주석 (Journal Comments)
  // - 변경 이력은 Git 같은 버전 관리 시스템이 더 정확히 관리한다.
  // - 코드에 남겨두면 시간이 지날수록 부정확해지고 지저분해진다.
  // - 소스 파일 상단에 변경 이력을 기록하는 관례는 VCS가 없던 시절의 유물이다.
  // 2024-01-01 Bernard created this class
  // 2024-02-10 Jinyoung fixed login bug
  // 2024-03-05 Updated validation
  static class BadLoginService {
    void login(String username, String password) {
      System.out.println("로그인: " + username);
    }
  }

  // Good: 변경 이력은 코드 밖 VCS에서 관리한다.
  // - git log로 언제, 누가, 왜 바꿨는지 정확히 확인할 수 있다.
  // - 코드는 현재 상태만 담고, 역사는 VCS에 맡긴다.
  static class GoodLoginService {
    void login(String username, String password) {
      System.out.println("로그인: " + username);
    }
  }
}
