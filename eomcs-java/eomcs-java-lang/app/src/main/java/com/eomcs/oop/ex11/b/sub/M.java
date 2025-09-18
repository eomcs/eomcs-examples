package com.eomcs.oop.ex11.b.sub;

public class M {
  // 다른 패키지에서 접근 할 수 있도록 public 으로 공개
  public static int v2;

  public static void m2() {}

  public static class Y {
    public void test() {
      System.out.println("M.Y.test()");
    }
  }
}
