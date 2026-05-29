package com.eomcs.cleancode.ch13.exam01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 예제 3: 오해 3 - "동시성 버그는 드물다"
// 동시성 버그는 발견하기 어렵고 특정 타이밍에서만 발생하지만, 치명적이다
public class BadAndGood5 {

  private BadAndGood5() {}

  // Bad: check-then-act 패턴을 동기화 없이 사용한다
  //   - 두 스레드가 동시에 실행하면 중복 추가 가능
  //   - contains()와 add() 사이에 다른 스레드가 끼어들 수 있다
  //   - 재현이 어렵고 특정 타이밍에서만 발생하는 버그
  static class BadUniqueList {

    private final List<String> list = new ArrayList<>();

    public void addIfAbsent(String item) {
      if (!list.contains(item)) { // 검사와 추가 사이에 다른 스레드 개입 가능
        list.add(item);
      }
    }

    public List<String> getList() {
      return Collections.unmodifiableList(list);
    }
  }

  // Good: 임계 영역 전체를 동기화하여 check-then-act를 원자적으로 만든다
  //   - contains()와 add()가 하나의 동기화 블록 안에서 실행된다
  //   - 다른 스레드가 중간에 끼어들 수 없다
  static class UniqueList {

    private final List<String> list = new ArrayList<>();

    public synchronized void addIfAbsent(String item) {
      if (!list.contains(item)) {
        list.add(item);
      }
    }

    public synchronized List<String> getList() {
      return Collections.unmodifiableList(new ArrayList<>(list));
    }
  }
}
