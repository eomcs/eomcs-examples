package com.eomcs.cleancode.ch09.exam03;

// 예제 2 & 3: 도메인 특화 테스트 언어 & 이중 표준 - Hardware/EnvironmentController
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Hardware {
    private int temperature;
    private boolean heaterOn;
    private boolean blowerOn;
    private boolean coolerOn;
    private boolean highTempAlarmOn;
    private boolean lowTempAlarmOn;

    int getTemperature() { return temperature; }
    void setTemperature(int temperature) { this.temperature = temperature; }
    boolean isHeaterOn() { return heaterOn; }
    void setHeaterOn(boolean heaterOn) { this.heaterOn = heaterOn; }
    boolean isBlowerOn() { return blowerOn; }
    void setBlowerOn(boolean blowerOn) { this.blowerOn = blowerOn; }
    boolean isCoolerOn() { return coolerOn; }
    void setCoolerOn(boolean coolerOn) { this.coolerOn = coolerOn; }
    boolean isHighTempAlarmOn() { return highTempAlarmOn; }
    void setHighTempAlarmOn(boolean on) { this.highTempAlarmOn = on; }
    boolean isLowTempAlarmOn() { return lowTempAlarmOn; }
    void setLowTempAlarmOn(boolean on) { this.lowTempAlarmOn = on; }
  }

  static class EnvironmentController {
    private static final int TOO_COLD_THRESHOLD = -5;
    private static final int TOO_HOT_THRESHOLD = 35;

    private final Hardware hardware;

    EnvironmentController(Hardware hardware) {
      this.hardware = hardware;
    }

    void tick() {
      boolean tooCold = hardware.getTemperature() < TOO_COLD_THRESHOLD;
      boolean tooHot = hardware.getTemperature() > TOO_HOT_THRESHOLD;

      hardware.setHeaterOn(tooCold);
      hardware.setBlowerOn(tooCold || tooHot);
      hardware.setCoolerOn(tooHot);
      hardware.setHighTempAlarmOn(tooHot);
      hardware.setLowTempAlarmOn(tooCold);
    }
  }
}
