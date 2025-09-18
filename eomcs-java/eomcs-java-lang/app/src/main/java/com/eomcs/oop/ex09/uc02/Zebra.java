package com.eomcs.oop.ex09.uc02;

public class Zebra {
  // 방향(direction)이 숫자이기 때문에 나중에 소스 코드를 보면 이해가 안될 것이다.
  // 숫자 보다는 문자가 직관적이기 때문에 변수를 활용한다.
  public static final int ON = 1;
  public static final int OFF = 2;

  boolean use;

  public void rotate(int direction) {
    if (direction == ON) { // 오른쪽 회전
      this.use = true;
    } else if (direction == OFF) { // 왼쪽 회전
      this.use = false;
    }
  }
}
