package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood3.Point;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - Point
//
// 문제점:
// - assert는 하나지만 x, y를 문자열로 합쳐 표현이 부자연스럽다.
// - 실패했을 때 x가 문제인지 y가 문제인지 알기 어렵다.
// - "One Assert" 규칙을 기계적으로 적용해 가독성이 오히려 나빠졌다.
class PointBadTest {

  @Test
  void 좌표를_이동한다() {
    Point point = new Point(1, 2);

    Point moved = point.move(3, 4);

    assertEquals("4,6", moved.getX() + "," + moved.getY());
  }
}
