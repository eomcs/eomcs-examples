package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood2 {

  // Bad: 같은 이야기를 중복하는 주석 (Redundant Comments)
  // - "increments count by one"은 count++가 이미 말하는 내용이다.
  // - 코드보다 더 많은 정보를 제공하지 못하는 주석은 잡음이다.
  // - 읽는 사람이 주석과 코드를 모두 읽어야 하므로 오히려 피로가 증가한다.
  static class BadCounter {
    int count = 0;

    void increment() {
      // increments count by one
      count++;
    }
  }

  // Good: 코드 자체가 이미 충분히 설명한다.
  // - count++는 읽는 사람이 바로 이해하는 코드다.
  // - 주석이 없어도 의미가 명확할 때는 주석을 달지 않는다.
  static class GoodCounter {
    int count = 0;

    void increment() {
      count++;
    }
  }
}
