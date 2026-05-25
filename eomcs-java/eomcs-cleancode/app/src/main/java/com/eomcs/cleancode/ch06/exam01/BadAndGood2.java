package com.eomcs.cleancode.ch06.exam01;

// 예제 2: 자료 추상화 - Vehicle
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: 구체적인 연료 정보를 그대로 노출한다.
  // - 연료 탱크 용량과 현재 휘발유 갤런 수를 직접 드러낸다.
  // - 단위가 Gallons라는 사실이 외부에 노출된다.
  // - 전기차나 수소차로 바꾸면 인터페이스 자체를 바꿔야 한다.
  interface BadVehicle {
    double getFuelTankCapacityInGallons();
    double getGallonsOfGasoline();
  }

  static class GasCar implements BadVehicle {
    private double tankCapacityGallons;
    private double currentFuelGallons;

    GasCar(double tankCapacityGallons, double currentFuelGallons) {
      this.tankCapacityGallons = tankCapacityGallons;
      this.currentFuelGallons = currentFuelGallons;
    }

    @Override public double getFuelTankCapacityInGallons() { return tankCapacityGallons; }
    @Override public double getGallonsOfGasoline() { return currentFuelGallons; }
  }

  // Good: 추상적인 연료 상태만 제공한다.
  // - 사용자는 "남은 연료 비율"만 알면 된다.
  // - 갤런으로 저장하는지, 리터로 저장하는지, 전기차 배터리인지 알 필요 없다.
  // - 내부 구현이 바뀌어도 인터페이스는 그대로다.
  interface GoodVehicle {
    double getPercentFuelRemaining();
  }

  static class GoodGasCar implements GoodVehicle {
    private double tankCapacityGallons;
    private double currentFuelGallons;

    GoodGasCar(double tankCapacityGallons, double currentFuelGallons) {
      this.tankCapacityGallons = tankCapacityGallons;
      this.currentFuelGallons = currentFuelGallons;
    }

    @Override
    public double getPercentFuelRemaining() {
      return (currentFuelGallons / tankCapacityGallons) * 100.0;
    }
  }

  // 전기차도 동일한 인터페이스로 표현된다. 내부 구현만 다를 뿐이다.
  static class ElectricCar implements GoodVehicle {
    private double batteryCapacityKwh;
    private double currentChargeKwh;

    ElectricCar(double batteryCapacityKwh, double currentChargeKwh) {
      this.batteryCapacityKwh = batteryCapacityKwh;
      this.currentChargeKwh = currentChargeKwh;
    }

    @Override
    public double getPercentFuelRemaining() {
      return (currentChargeKwh / batteryCapacityKwh) * 100.0;
    }
  }
}
