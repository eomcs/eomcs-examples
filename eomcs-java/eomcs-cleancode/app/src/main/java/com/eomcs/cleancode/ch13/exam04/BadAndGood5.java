package com.eomcs.cleancode.ch13.exam04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 예제 5: 직접 동기화 vs 라이브러리 사용
public class BadAndGood5 {

  private BadAndGood5() {}

  // Bad - 가능하지만 장황함: 모든 접근을 하나의 락으로 막는다
  //   - synchronized 메서드가 늘어날수록 반복적이고 장황해진다
  //   - 모든 스레드가 하나의 락을 두고 경쟁하여 병목이 생긴다
  //   - 동기화 의도가 코드에서 명확하게 드러나지 않는다
  static class BadSafeStore {

    private final Map<String, String> values = new HashMap<>();

    public synchronized void put(String key, String value) {
      values.put(key, value);
    }

    public synchronized String get(String key) {
      return values.get(key);
    }
  }

  // Good: ConcurrentHashMap은 동시성에 맞게 설계된 컬렉션이다
  //   - 세그먼트 단위 락으로 병목을 줄인다
  //   - synchronized 없이도 thread-safe하다
  //   - 의도가 더 분명하다
  static class SafeStore {

    private final ConcurrentMap<String, String> values = new ConcurrentHashMap<>();

    public void put(String key, String value) {
      values.put(key, value);
    }

    public String get(String key) {
      return values.get(key);
    }
  }
}
