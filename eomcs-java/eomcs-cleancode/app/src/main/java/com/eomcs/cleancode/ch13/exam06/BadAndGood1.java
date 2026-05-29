package com.eomcs.cleancode.ch13.exam06;

import java.util.List;

// 예제 1: synchronized 메서드 각각은 안전해도, 여러 메서드를 조합하면 안전하지 않다
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: hasNext()와 next()가 각각 synchronized이지만, 두 메서드 사이는 보호되지 않는다
  //   - Thread A: hasNext() → true
  //   - Thread B: hasNext() → true
  //   - Thread B: next() → 마지막 값 반환
  //   - Thread A: next() → 더 이상 값 없음, 예외 발생 가능
  //   - 각 메서드는 안전하지만, 두 메서드 사이의 관계는 안전하지 않다
  static class BadNumberIterator {

    private final List<Integer> numbers = List.of(1, 2, 3);
    private int index = 0;

    public synchronized boolean hasNext() {
      return index < numbers.size();
    }

    public synchronized int next() {
      return numbers.get(index++);
    }
  }

  // Bad 클라이언트: hasNext()와 next()를 분리해서 호출한다
  //   - hasNext()의 락이 해제된 뒤 next() 호출 전에 다른 스레드가 끼어들 수 있다
  static class BadClient {

    public void iterate(BadNumberIterator iterator) {
      while (iterator.hasNext()) {         // lock 획득 → 해제
        int number = iterator.next();      // lock 획득 → 해제 (중간에 다른 스레드 개입 가능)
        System.out.println(number);
      }
    }
  }

  // 해결 1 - Client-Based Locking: 클라이언트가 공유 객체 전체 사용 구간을 직접 잠근다
  //   - hasNext()와 next()가 하나의 락 범위에 들어간다
  //   - 하지만 모든 클라이언트가 이 규칙을 알고 지켜야 한다
  //   - 한 곳이라도 synchronized(iterator)를 빠뜨리면 다시 버그가 생긴다
  static class ClientBasedLockingClient {

    public void iterate(BadNumberIterator iterator) {
      while (true) {
        int number;

        synchronized (iterator) {          // hasNext()와 next()를 하나의 락으로 묶는다
          if (!iterator.hasNext()) {
            break;
          }
          number = iterator.next();
        }

        System.out.println(number);
      }
    }
  }

  // 해결 2 - Server-Based Locking: 확인과 가져오기를 하나의 synchronized 메서드로 묶는다
  //   - 동시성 규칙이 서버 객체 내부에 있다
  //   - 클라이언트는 여러 메서드를 조합하지 않는다
  //   - 클라이언트 실수 가능성이 줄어든다
  static class NumberIterator {

    private final List<Integer> numbers = List.of(1, 2, 3);
    private int index = 0;

    public synchronized Integer nextOrNull() {
      if (index >= numbers.size()) {
        return null;
      }
      return numbers.get(index++);
    }
  }

  // 해결 2 클라이언트: 메서드 하나만 호출한다
  static class ServerBasedLockingClient {

    public void iterate(NumberIterator iterator) {
      while (true) {
        Integer number = iterator.nextOrNull();

        if (number == null) {
          break;
        }

        System.out.println(number);
      }
    }
  }

  // 해결 3 - Adapted Server: 기존 클래스를 바꿀 수 없을 때 어댑터가 잠금 책임을 맡는다
  //   - 원래 BadNumberIterator를 수정하지 않아도 된다
  //   - 잠금 규칙을 어댑터에 모은다
  //   - 클라이언트는 안전한 메서드 하나만 호출한다
  static class SafeNumberIterator {

    private final BadNumberIterator iterator;

    SafeNumberIterator(BadNumberIterator iterator) {
      this.iterator = iterator;
    }

    public Integer nextOrNull() {
      synchronized (iterator) {
        if (!iterator.hasNext()) {
          return null;
        }
        return iterator.next();
      }
    }
  }

  // 해결 3 클라이언트: 어댑터만 사용하므로 락 규칙을 알 필요가 없다
  static class AdaptedServerClient {

    public void iterate(SafeNumberIterator iterator) {
      while (true) {
        Integer number = iterator.nextOrNull();

        if (number == null) {
          break;
        }

        System.out.println(number);
      }
    }
  }
}
