package com.eomcs.cleancode.ch03.exam08;

import java.util.List;

public class BadAndGood3 {

  // Bad
  // - addItem → 리스트에 추가(Command)하면서 포함 여부도 반환(Query)한다.
  // - 반환값 true/false의 의미가 "추가 성공"인지 "포함 여부"인지 애매하다.
  // - add + contains 두 가지 역할을 동시에 수행하여 책임이 불명확하다.
  static class BadListService {
    boolean addItem(List<String> list, String item) {
      list.add(item); // 상태 변경 (Command)
      return list.contains(item); // 조회 (Query)
    }
  }

  // Good: Command와 Query를 분리한다.
  // - addItem → 리스트에 항목을 추가하는 Command만 수행한다. 반환값이 없다.
  // - containsItem → 포함 여부만 반환하는 Query만 수행한다. 상태를 변경하지 않는다.
  // - 각 함수의 이름과 역할이 완전히 일치한다.
  static class GoodListService {
    void addItem(List<String> list, String item) {
      list.add(item);
    }

    boolean containsItem(List<String> list, String item) {
      return list.contains(item);
    }
  }

  static class GoodUsage {
    void demo(GoodListService service, List<String> list, String item) {
      service.addItem(list, item); // Command: 상태 변경

      if (service.containsItem(list, item)) { // Query: 상태 변경 없음
        System.out.println(item + " 항목이 존재합니다.");
      }
    }
  }
}
