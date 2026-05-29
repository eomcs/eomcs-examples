package com.eomcs.cleancode.ch13.exam04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 예제 2: 복합 연산 - get() 후 put()은 원자적 연산이 아니다
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: get() 후 put()은 하나의 원자적 연산이 아니다
  //   - 두 스레드가 동시에 같은 postId의 값을 읽으면 증가가 누락될 수 있다
  //   - HashMap 자체도 thread-safe하지 않다
  @SuppressWarnings("java:S3824")
  static class BadViewCounter {

    private final Map<Long, Integer> views = new HashMap<>();

    public void increase(long postId) {
      Integer current = views.get(postId);

      if (current == null) {
        views.put(postId, 1);
        return;
      }

      views.put(postId, current + 1);
    }
  }

  // Good: merge()는 복합 갱신을 원자적으로 안전하게 처리한다
  //   - 조회 후 갱신 사이의 경쟁 조건을 줄인다
  //   - 직접 락을 잡지 않아도 된다
  static class ViewCounter {

    private final ConcurrentMap<Long, Integer> views = new ConcurrentHashMap<>();

    public void increase(long postId) {
      views.merge(postId, 1, (a, b) -> a + b);
    }

    public int countOf(long postId) {
      return views.getOrDefault(postId, 0);
    }
  }
}
