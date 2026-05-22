package com.eomcs.cleancode.ch02.exam13;

import java.util.ArrayDeque;
import java.util.Deque;

public class BadAndGood1 {

  // Bad
  // - DataContainer → 내부 구조가 무엇인지 전혀 알 수 없다.
  // - addItem/getItem → Stack인지 Queue인지 동작 방식을 예측할 수 없다.
  static class BadDataContainer {
    private Deque<String> storage = new ArrayDeque<>();

    void addItem(String item) {
      storage.push(item);
    }
    String getItem() {
      return storage.pop();
    }
  }

  // Good
  // - Stack → 자료구조가 명확하다.
  // - push/pop → CS 표준 용어로 동작을 즉시 예측할 수 있다.
  static class GoodStack {
    private Deque<String> storage = new ArrayDeque<>();

    void push(String item) {
      storage.push(item);
    }
    String pop() {
      return storage.pop();
    }
  }
}
