// 인터페이스 구현 - 추상 클래스를 이용하여 인터페이스 구현을 도와줄 수 있다.
package com.eomcs.oop.ex10.b;

// 인터페이스에 메서드가 많지만, 그 중에 특정 메서드만 주로 구현한다면,
// => 개발자가 인터페이스를 구현하기 편하도록
//    서브 클래스에서 오버라이딩할 메서드를 제외한 나머지 메서드를 추상 클래스에서 구현한다.
//
public abstract class AbstractServlet implements Servlet {
  
  // 추상 클래스에서 특정 메서드를 제외한 나머지 메서드를 구현한다.
  // => 이 추상 클래스를 상속 받는 서브 클래스에서는 단 한 개의 메서드만 구현하면 된다.
  public void init() {}
  public void destroy() {}
  public String getServletInfo() {
    return null;
  }
  public String getServletConfig() {
    return null;
  }
}
