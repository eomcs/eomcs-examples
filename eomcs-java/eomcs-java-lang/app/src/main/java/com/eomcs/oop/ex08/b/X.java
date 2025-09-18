package com.eomcs.oop.ex08.b;

public class X {
  private int privateVar;
  int defaultVar;
  protected int protectedVar;
  public int publicVar;

  private void privateMethod() {}
  void defaultMethod() {}
  protected void protectedMethod() {}
  public void publicMethod() {}

  public void test() {
    // 같은 클래스의 멤버(필드나 메서드)인 경우 제한없이 모든 멤버에 접근할 수 있다.
    this.privateVar = 100;
    this.defaultVar = 100;
    this.protectedVar = 100;
    this.publicVar = 100;

    this.privateMethod();
    this.defaultMethod();
    this.protectedMethod();
    this.publicMethod();
  }
}
