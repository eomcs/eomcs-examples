package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood2.EnvironmentController;
import com.eomcs.cleancode.ch09.exam03.BadAndGood2.Hardware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Bad 테스트 헬퍼 코드 - 이중 표준 (Dual Standard)
//
// 문제점:
// - hardwareState()가 StringBuilder와 if/else를 사용해 프로덕션 코드처럼 작성됐다.
// - 코드가 길고 산만해서 헬퍼임에도 읽는 비용이 크다.
// - 테스트의 목적보다 구현 세부사항이 더 많이 보인다.
// - 프로덕션 코드 기준을 기계적으로 적용해 테스트 가독성이 떨어졌다.
class DualStandardBadTest {

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
    StringBuilder state = new StringBuilder(5);

    if (hardware.isHeaterOn()) {
      state.append("H");
    } else {
      state.append("h");
    }

    if (hardware.isBlowerOn()) {
      state.append("B");
    } else {
      state.append("b");
    }

    if (hardware.isCoolerOn()) {
      state.append("C");
    } else {
      state.append("c");
    }

    if (hardware.isHighTempAlarmOn()) {
      state.append("H");
    } else {
      state.append("h");
    }

    if (hardware.isLowTempAlarmOn()) {
      state.append("L");
    } else {
      state.append("l");
    }

    return state.toString();
  }
}
