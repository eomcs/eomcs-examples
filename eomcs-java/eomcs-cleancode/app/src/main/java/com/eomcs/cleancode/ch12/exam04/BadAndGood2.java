package com.eomcs.cleancode.ch12.exam04;

import java.util.ArrayList;
import java.util.List;

// 예제 2: 상태 중복을 제거하라 - Cart
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Item {
    private final String name;
    Item(String name) { this.name = name; }
    String name() { return name; }
  }

  // Bad: items.size()와 empty가 같은 정보를 중복해서 관리한다
  //   - empty 갱신을 잊으면 size()와 isEmpty() 결과가 달라진다
  //   - 상태 중복은 버그를 만든다
  static class BadCart {

    private final List<Item> items = new ArrayList<>();
    private boolean empty = true;

    public int size() {
      return items.size();
    }

    public boolean isEmpty() {
      return empty;
    }

    public void add(Item item) {
      items.add(item);
      empty = false;
    }

    public void remove(Item item) {
      items.remove(item);

      if (items.size() == 0) {
        empty = true;
      }
    }
  }

  // Good: 비어 있는지 여부는 size()에서 계산한다
  //   - 같은 상태를 두 곳에서 관리하지 않는다
  //   - 중복 상태가 사라져 버그 가능성이 줄어든다
  static class Cart {

    private final List<Item> items = new ArrayList<>();

    public int size() {
      return items.size();
    }

    public boolean isEmpty() {
      return size() == 0;
    }

    public void add(Item item) {
      items.add(item);
    }

    public void remove(Item item) {
      items.remove(item);
    }
  }
}
