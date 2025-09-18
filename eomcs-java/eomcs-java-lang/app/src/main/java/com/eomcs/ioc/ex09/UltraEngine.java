package com.eomcs.ioc.ex09;

public class UltraEngine extends Engine {
  public UltraEngine() {
    System.out.println("===> UltraEngine()");
  }

  @Override
  public void run() {
    super.run();
    System.out.println("쌩쌩...달린다....");
  }
}
