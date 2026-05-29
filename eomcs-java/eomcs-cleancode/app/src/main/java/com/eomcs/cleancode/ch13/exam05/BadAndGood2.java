package com.eomcs.cleancode.ch13.exam05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 예제 2: Readers-Writers - 많은 스레드는 읽고, 일부 스레드는 쓴다
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Product {

    private final long id;
    private final String name;

    Product(long id, String name) {
      this.id = id;
      this.name = name;
    }

    long id() {
      return id;
    }

    String name() {
      return name;
    }
  }

  // Bad: HashMap은 thread-safe하지 않다
  //   - 여러 스레드가 동시에 읽고 쓴다
  //   - 읽는 도중 쓰기가 발생하면 문제가 생길 수 있다
  static class BadProductCatalog {

    private final Map<Long, Product> products = new HashMap<>();

    public Product findById(long id) {
      return products.get(id);
    }

    public void update(Product product) {
      products.put(product.id(), product);
    }
  }

  // Bad - 읽기 처리량 낮음: synchronized로 읽기와 쓰기를 모두 직렬화한다
  //   - 읽기끼리도 서로 막는다
  //   - 읽기 요청이 많은 시스템에서는 처리량이 낮아질 수 있다
  static class SynchronizedProductCatalog {

    private final Map<Long, Product> products = new HashMap<>();

    public synchronized Product findById(long id) {
      return products.get(id);
    }

    public synchronized void update(Product product) {
      products.put(product.id(), product);
    }
  }

  // Good: ReadWriteLock으로 읽기는 동시에, 쓰기는 단독으로 처리한다
  //   - readLock → readLock: 동시에 읽기 가능
  //   - readLock → writeLock: 모든 readLock 해제 후 writeLock 획득
  //   - writeLock → readLock: writeLock 해제 후 readLock 획득
  //   - writeLock → writeLock: 직렬화
  //   - 읽기 처리량을 높이면서 쓰기 중 데이터 깨짐을 막는다
  static class ProductCatalog {

    private final Map<Long, Product> products = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Product findById(long id) {
      lock.readLock().lock();
      try {
        return products.get(id);
      } finally {
        lock.readLock().unlock();
      }
    }

    public void update(Product product) {
      lock.writeLock().lock();
      try {
        products.put(product.id(), product);
      } finally {
        lock.writeLock().unlock();
      }
    }
  }
}
