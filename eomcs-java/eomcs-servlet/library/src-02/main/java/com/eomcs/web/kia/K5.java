package com.eomcs.web.kia;

import com.eomcs.web.Car;

public class K5 implements Car {
  @Override
  public void run() {
    System.out.println("K5.run().... 호출됨!");
  }
}
