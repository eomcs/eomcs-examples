package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood2.EnvironmentController;
import com.eomcs.cleancode.ch09.exam03.BadAndGood2.Hardware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 예제 2: 도메인 특화 테스트 언어 - EnvironmentController
//
// Bad: assertTrue, assertFalse 나열 → 기대 상태를 한눈에 파악하기 어렵다.
// Good: wayTooCold(), wayTooHot() 같은 상황 메서드와 hardwareState()로
//       테스트가 "기계 조작 코드"가 아니라 "요구사항 문장"처럼 읽힌다.
class EnvironmentControllerGoodTest {

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
