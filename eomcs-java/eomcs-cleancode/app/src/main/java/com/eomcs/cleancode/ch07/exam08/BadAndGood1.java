package com.eomcs.cleancode.ch07.exam08;

// 예제 1: null을 전달하지 마라 - registerItem (묵살 vs 예외)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Item {
    private String name;
    Item(String name) { this.name = name; }
    String getName() { return name; }
  }

  interface ItemRepository {
    void save(Item item);
  }

  // Bad: null을 조용히 무시한다.
  // - 함수 내부에 불필요한 null 체크가 생긴다.
  // - 호출자가 null을 전달해도 아무 일도 일어나지 않는다.
  // - 버그가 숨겨진다.
  static class BadItemService {
    private ItemRepository repository;
    BadItemService(ItemRepository repo) { this.repository = repo; }

    public void registerItem(Item item) {
      if (item != null) { // null을 묵살한다
        repository.save(item);
      }
    }
  }

  static class BadItemClient {
    void run(BadItemService itemService) {
      itemService.registerItem(null); // null을 전달해도 오류가 드러나지 않는다
    }
  }

  // -----------------------------------------------------------------------

  // Good: null이 전달되면 즉시 예외를 던진다.
  // - 잘못된 사용이 즉시 드러난다.
  // - 함수 계약(contract)이 명확해진다.
  // - 버그를 초기에 발견할 수 있다.
  static class GoodItemService {
    private ItemRepository repository;
    GoodItemService(ItemRepository repo) { this.repository = repo; }

    public void registerItem(Item item) {
      if (item == null) {
        throw new IllegalArgumentException("item must not be null");
      }

      repository.save(item);
    }
  }

  static class GoodItemClient {
    void run(GoodItemService itemService, Item item) {
      itemService.registerItem(item); // null을 전달하지 않는다
    }
  }
}
