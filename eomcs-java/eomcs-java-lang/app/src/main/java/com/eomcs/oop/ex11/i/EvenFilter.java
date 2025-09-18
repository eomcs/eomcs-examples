package com.eomcs.oop.ex11.i;

public class EvenFilter implements Filter {

  @Override
  public boolean accept(int value) {
    return (value % 2) == 0;
  }

}
