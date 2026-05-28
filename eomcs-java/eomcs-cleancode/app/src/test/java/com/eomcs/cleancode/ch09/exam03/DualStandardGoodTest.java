package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood2.EnvironmentController;
import com.eomcs.cleancode.ch09.exam03.BadAndGood2.Hardware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 예제 3: 이중 표준 - DualStandard
//
// Bad: hardwareState()에서 StringBuilder + if/else 사용 → 코드가 길고 가독성 낮다.
// Good: 테스트 환경에서는 성능보다 단순함과 표현력이 중요하다.
//       문자열 결합 + 삼항 연산자로 짧고 읽기 쉬운 헬퍼를 작성한다.
//       단, 깨끗함을 포기한 것이 아니다 - 단순하고 명확하게 유지한다.
class DualStandardGoodTest {

  Hardware hardware;
  EnvironmentController controller;

  @BeforeEach
  void setUp() {
    hardware = new Hardware();
    controller = new EnvironmentController(hardware);
  }

  @Test
  void 온도가_너무_낮으면_히터와_송풍기와_저온경보가_켜진다() {
    wayTooCold();

    assertEquals("HBchL", hardwareState());
  }

  @Test
  void 온도가_너무_높으면_냉각기와_송풍기와_고온경보가_켜진다() {
    wayTooHot();

    assertEquals("hBCHl", hardwareState());
  }

  private void wayTooCold() {
    hardware.setTemperature(-10);
    controller.tick();
  }

  private void wayTooHot() {
    hardware.setTemperature(45);
    controller.tick();
  }

  private String hardwareState() {
    String state = "";
    state += hardware.isHeaterOn() ? "H" : "h";
    state += hardware.isBlowerOn() ? "B" : "b";
    state += hardware.isCoolerOn() ? "C" : "c";
    state += hardware.isHighTempAlarmOn() ? "H" : "h";
    state += hardware.isLowTempAlarmOn() ? "L" : "l";
    return state;
  }
}
