package com.eomcs.design_pattern.observer.after.d;

public class BrakeOilCarObserver implements CarObserver {

  @Override
  public void carStarted() {
    System.out.println("브레이크 오일 유무 검사");
  }

  @Override
  public void carStopped() {}

}
