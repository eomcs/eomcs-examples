// 인터페이스 구현 - 호출 규칙에 따라 메서드를 정의한다.
package com.eomcs.oop.ex10.b;

public class Exam01 implements Servlet {
  // 인터페이스를 구현한다는 것은 
  // 인터페이스에 선언된 모든 메서드를 구현한다는 뜻이다!
  public void init() {}
  public void service() {}
  public void destroy() {}
  public String getServletInfo() {return null;}
  public String getServletConfig() {return null;}
}
