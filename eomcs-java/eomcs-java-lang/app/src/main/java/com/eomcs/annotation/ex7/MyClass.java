// 애노테이션 중복 사용
package com.eomcs.annotation.ex7;

@Company("비트캠프")
//@Company("비트캠프") // 중복 사용 불가!
public class MyClass {

  @Company("비트캠프")
  //@Company("비트캠프") // 중복 사용 불가!
  public void m1(int p) {}

  @Company("비트캠프")
  //@Company("비트캠프") // 중복 사용 불가!
  public void m2(int p) {}
}
