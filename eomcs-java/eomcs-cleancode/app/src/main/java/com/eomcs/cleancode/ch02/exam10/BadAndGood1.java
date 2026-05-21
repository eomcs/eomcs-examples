package com.eomcs.cleancode.ch02.exam10;

public class BadAndGood1 {

  static class Item {
    int id;
  }

  static class Request {
    String type;
  }

  // Bad
  // - whack, eatMyShorts, holyHandGrenade → 유머나 문화 참조를 이름으로 사용했다.
  // - 팀 외부 사람은 물론 시간이 지나면 본인도 의미를 알 수 없다.
  // - 코드를 읽는 사람이 반드시 해석하거나 검색해야만 이해할 수 있다.
  static class BadItemService {
    void whack(Item item) {
      System.out.println("아이템 삭제: " + item.id);
    }
    void eatMyShorts(Request request) {
      System.out.println("요청 거절: " + request.type);
    }
    void holyHandGrenade() {
      System.out.println("비활성 사용자 일괄 제거");
    }
  }

  // Good
  // - deleteItem, rejectRequest, removeInactiveUsers → 동작이 명확하게 드러난다.
  // - 누구나 이해 가능하고 추가 설명이 불필요하다.
  static class GoodItemService {
    void deleteItem(Item item) {
      System.out.println("아이템 삭제: " + item.id);
    }
    void rejectRequest(Request request) {
      System.out.println("요청 거절: " + request.type);
    }
    void removeInactiveUsers() {
      System.out.println("비활성 사용자 일괄 제거");
    }
  }
}
