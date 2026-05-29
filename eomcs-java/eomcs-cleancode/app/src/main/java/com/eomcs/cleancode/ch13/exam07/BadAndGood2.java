package com.eomcs.cleancode.ch13.exam07;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 예제 2: 캐시 조회 - DB 조회처럼 오래 걸리는 작업은 락 밖에서 수행하라
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Product {

    private final long id;

    Product(long id) {
      this.id = id;
    }

    long id() {
      return id;
    }
  }

  // Bad: DB 조회까지 synchronized 안에서 실행된다
  //   - DB 조회는 오래 걸릴 수 있다
  //   - 한 스레드가 DB를 기다리는 동안 다른 스레드는 캐시 조회조차 못 한다
  static class BadProductCache {

    private final Map<Long, Product> cache = new HashMap<>();

    public synchronized Product find(long id) {
      Product product = cache.get(id);

      if (product == null) {
        product = loadFromDatabase(id);  // 오래 걸리는 작업이 락 안에 있다
        cache.put(id, product);
      }

      return product;
    }

    private Product loadFromDatabase(long id) {
      return new Product(id);
    }
  }

  // Better: 캐시 조회와 저장만 락으로 보호하고, DB 조회는 락 밖에서 수행한다
  //   - 다른 스레드가 캐시 조회 시 불필요하게 막히지 않는다
  //   - 단, 같은 상품을 여러 스레드가 동시에 DB에서 중복으로 읽어올 수 있다
  static class BetterProductCache {

    private final Map<Long, Product> cache = new HashMap<>();

    public Product find(long id) {
      synchronized (this) {
        Product cached = cache.get(id);
        if (cached != null) {
          return cached;
        }
      }

      Product loaded = loadFromDatabase(id);  // 락 밖에서 DB 조회

      synchronized (this) {
        cache.put(id, loaded);
        return loaded;
      }
    }

    private Product loadFromDatabase(long id) {
      return new Product(id);
    }
  }

  // Good: ConcurrentHashMap.computeIfAbsent()로 직접 락 범위 고민을 없앤다
  //   - 표준 동시성 컬렉션이 복합 연산을 처리한다
  //   - 직접 락 범위를 고민하는 코드가 줄어든다
  //   - 코드 의도가 더 명확하다
  static class ProductCache {

    private final ConcurrentMap<Long, Product> cache = new ConcurrentHashMap<>();

    public Product find(long id) {
      return cache.computeIfAbsent(id, this::loadFromDatabase);
    }

    private Product loadFromDatabase(Long id) {
      return new Product(id);
    }
  }
}
