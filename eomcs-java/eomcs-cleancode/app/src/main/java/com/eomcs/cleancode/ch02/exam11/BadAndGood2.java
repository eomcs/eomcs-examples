package com.eomcs.cleancode.ch02.exam11;

public class BadAndGood2 {

  static class Item {
    String id;
  }

  // Bad
  // - deleteUser, removeOrder, eraseItem → 모두 "삭제"라는 같은 개념인데 단어가 다르다.
  // - 이름이 다르면 읽는 사람은 세 동작 사이에 의미 차이가 있는지 의심하게 된다.
  static class BadUserService {
    void deleteUser(String id) {
      System.out.println("사용자 삭제: " + id);
    }
  }

  static class BadOrderService {
    void removeOrder(String id) {
      System.out.println("주문 삭제: " + id);
    }
  }

  static class BadCartService {
    void eraseItem(String id) {
      System.out.println("장바구니 항목 삭제: " + id);
    }
  }

  // Good
  // - delete 로 통일 → "이 시스템에서 삭제는 delete"임을 이름만으로 바로 알 수 있다.
  static class GoodUserService {
    void deleteUser(String id) {
      System.out.println("사용자 삭제: " + id);
    }
  }

  static class GoodOrderService {
    void deleteOrder(String id) {
      System.out.println("주문 삭제: " + id);
    }
  }

  static class GoodCartService {
    void deleteItem(String id) {
      System.out.println("장바구니 항목 삭제: " + id);
    }

    // addItem(컬렉션에 항목 추가)과 sum(숫자 계산)은 서로 다른 개념이므로
    // 단어가 달라도 된다. 의미가 다를 때만 다른 단어를 쓴다.
    void addItem(Item item) {
      System.out.println("장바구니에 추가: " + item.id);
    }
  }

  static class NumberCalculator {
    int sum(int a, int b) {
      return a + b;
    }
  }
}
