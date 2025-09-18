package com.eomcs.oop.ex09.uc01.project1;

import com.eomcs.oop.ex09.uc01.Computer;

public class FirstComputer implements Computer {
  @Override
  public void compute() {
    System.out.println("단순히 계산을 수행한다!");
  }
}
