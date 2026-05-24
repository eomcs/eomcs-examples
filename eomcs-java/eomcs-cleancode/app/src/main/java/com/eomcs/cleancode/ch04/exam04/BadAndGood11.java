package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood11 {

  // Bad: 공로를 돌리거나 저자를 표시하는 주석 (Attributions and Bylines)
  // - 작성자 정보는 버전 관리 시스템이 더 정확히 관리한다.
  // - 코드가 오랫동안 수정되면 주석이 현실과 달라져 부정확한 정보가 된다.
  // - "git blame"으로 언제, 누가 추가했는지 정확히 확인할 수 있다.
  static class BadOrderService {
    // Added by Bernard
    void calculate() {
      System.out.println("계산");
    }
  }

  // Good: 작성자 정보는 코드에서 제거하고 VCS에 맡긴다.
  // - 코드는 현재의 동작만 담고, 역사는 git이 관리한다.
  static class GoodOrderService {
    void calculate() {
      System.out.println("계산");
    }
  }
}
