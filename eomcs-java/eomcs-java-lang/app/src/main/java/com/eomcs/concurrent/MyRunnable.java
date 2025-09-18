package com.eomcs.concurrent;

public class MyRunnable implements Runnable {

  int count;

  public MyRunnable(int count) {
    this.count = count;
  }

  @Override
  public void run() {
    for (int i = 0; i < count; i++) {
      System.out.println("==> " + i);
    }
  }
}
