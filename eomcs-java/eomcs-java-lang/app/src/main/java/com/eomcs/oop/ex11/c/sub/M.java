package com.eomcs.oop.ex11.c.sub;

public class M {
  // 다른 패키지에서 접근 할 수 있도록 public 으로 공개
  public class Y {
    public void test() {
      System.out.println("M.Y.test()");
    }
  }
}
