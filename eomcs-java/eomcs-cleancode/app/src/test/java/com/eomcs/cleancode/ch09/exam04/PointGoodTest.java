package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood3.Point;
import org.junit.jupiter.api.Test;

// 예제 3: 여러 assert가 허용되는 경우 - Point
//
// Bad: 값을 문자열로 억지로 합쳐 assert 하나로 만들었다.
// Good: x, y 두 assert가 있지만 "좌표 이동"이라는 하나의 개념을 검증한다.
//       실패 지점이 더 명확하고, 테스트가 자연스럽게 읽힌다.
//       중요한 것은 assert 개수보다 테스트 개념의 단일성이다.
class PointGoodTest {

  @Test
  void 좌표를_이동한다() {
    Point point = new Point(1, 2);

    Point moved = point.move(3, 4);

    assertEquals(4, moved.getX());
    assertEquals(6, moved.getY());
  }
}
