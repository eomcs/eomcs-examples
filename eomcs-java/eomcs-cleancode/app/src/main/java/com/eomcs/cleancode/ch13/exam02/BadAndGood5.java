package com.eomcs.cleancode.ch13.exam02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 예제 5: 테스트의 어려움 - 동시성 버그는 재현하기 어렵다
public class BadAndGood5 {

  private BadAndGood5() {}

  // Bad: check-then-act가 동기화되지 않아 중복 추가 가능
  //   - 두 스레드가 동시에 contains() 검사 → 둘 다 false
  //   - 둘 다 add() 실행 → 중복 추가
  //   - 단일 스레드 테스트에서는 재현되지 않는다
  //   - 특정 타이밍에서만 운영 환경에서 발생한다
  static class BadItemRepository {

    private final List<String> items = new ArrayList<>();

    public void addIfNotExists(String item) {
      if (!items.contains(item)) {
        items.add(item);
      }
    }

    public List<String> getItems() {
      return Collections.unmodifiableList(items);
    }
  }

  // Good: 메서드 전체를 동기화하여 check-then-act를 원자적으로 만든다
  //   - 테스트에서는 잘 보이지 않으므로 설계 단계에서 동시성을 고려해야 한다
  static class ItemRepository {

    private final List<String> items = new ArrayList<>();

    public synchronized void addIfNotExists(String item) {
      if (!items.contains(item)) {
        items.add(item);
      }
    }

    public synchronized List<String> getItems() {
      return Collections.unmodifiableList(new ArrayList<>(items));
    }
  }
}
