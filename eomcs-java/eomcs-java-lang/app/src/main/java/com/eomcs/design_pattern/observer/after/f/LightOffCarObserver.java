package com.eomcs.design_pattern.observer.after.f;

public class LightOffCarObserver implements CarObserver {

  @Override
  public void carStarted() {}

  @Override
  public void carStopped() {
    System.out.println("전조등을 끈다.");
  }

}
