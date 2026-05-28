package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood2.EnvironmentController;
import com.eomcs.cleancode.ch09.exam03.BadAndGood2.Hardware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - EnvironmentController
//
// 문제점:
// - 검증 코드가 길어서 기대 상태를 한눈에 보기 어렵다.
// - assertTrue, assertFalse를 번갈아 읽어야 한다.
// - 테스트의 관심사는 "추우면 어떤 장치가 켜지는가"인데 세부 장치 확인 코드가 과하게 드러난다.
// - 비슷한 테스트가 많아지면 중복이 커진다.
class EnvironmentControllerBadTest {

  Hardware hardware;
  EnvironmentController controller;

  @BeforeEach
  void setUp() {
    hardware = new Hardware();
    controller = new EnvironmentController(hardware);
  }

  @Test
  void 온도가_너무_낮으면_히터와_송풍기와_저온경보가_켜진다() {
    hardware.setTemperature(-10);
    controller.tick();

    assertTrue(hardware.isHeaterOn());
    assertTrue(hardware.isBlowerOn());
    assertFalse(hardware.isCoolerOn());
    assertFalse(hardware.isHighTempAlarmOn());
    assertTrue(hardware.isLowTempAlarmOn());
  }

  @Test
  void 온도가_너무_높으면_냉각기와_송풍기와_고온경보가_켜진다() {
    hardware.setTemperature(45);
    controller.tick();

    assertFalse(hardware.isHeaterOn());
    assertTrue(hardware.isBlowerOn());
    assertTrue(hardware.isCoolerOn());
    assertTrue(hardware.isHighTempAlarmOn());
    assertFalse(hardware.isLowTempAlarmOn());
  }
}
