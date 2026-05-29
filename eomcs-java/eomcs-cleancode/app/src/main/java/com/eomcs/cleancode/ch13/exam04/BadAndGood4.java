package com.eomcs.cleancode.ch13.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// 예제 4: 공유 List - ArrayList는 thread-safe하지 않다
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: ArrayList는 thread-safe하지 않다
  //   - 여러 스레드가 동시에 순회/수정하면 ConcurrentModificationException이 발생할 수 있다
  //   - 내부 리스트를 그대로 반환하면 외부에서 마음대로 수정할 수 있다
  static class BadRecentSearches {

    private final List<String> keywords = new ArrayList<>();

    public void add(String keyword) {
      keywords.add(keyword);
    }

    public List<String> all() {
      return keywords; // 내부 상태 노출
    }
  }

  // Good: CopyOnWriteArrayList는 읽기가 많고 쓰기가 적은 경우 유용하다
  //   - 쓰기 시 내부 배열을 복사하므로 순회 중 수정 문제가 없다
  //   - 반환할 때도 복사본을 주어 내부 상태를 보호한다
  static class RecentSearches {

    private final List<String> keywords = new CopyOnWriteArrayList<>();

    public void add(String keyword) {
      keywords.add(keyword);
    }

    public List<String> all() {
      return List.copyOf(keywords);
    }
  }
}
