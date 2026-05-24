package com.eomcs.cleancode.ch03.exam07;

import java.util.ArrayList;
import java.util.List;

public class BadAndGood2 {

  // Bad
  // - addItem이 리스트를 변경(부작용)하면서 크기도 반환한다.
  // - 이름은 단순 추가처럼 보이지만 반환값도 있어 역할이 불명확하다.
  // - 상태 변경과 값 반환을 동시에 하므로 호출자가 동작을 예측하기 어렵다.
  static class BadItemService {
    int addItem(List<String> items, String item) {
      items.add(item); // 부작용: 외부 리스트 변경
      return items.size();
    }
  }

  // Good: 책임을 명확히 분리한다.
  // - addItem → 리스트 변경만 담당. 부작용이 이름과 반환 타입으로 명확히 표현된다.
  // - getItemCountAfterAdd → 새 리스트를 만들어 계산하므로 원본에 부작용이 없다.
  static class GoodItemService {
    void addItem(List<String> items, String item) {
      items.add(item);
    }

    int getItemCountAfterAdd(List<String> items, String item) {
      List<String> newItems = new ArrayList<>(items);
      newItems.add(item);
      return newItems.size();
    }
  }
}
