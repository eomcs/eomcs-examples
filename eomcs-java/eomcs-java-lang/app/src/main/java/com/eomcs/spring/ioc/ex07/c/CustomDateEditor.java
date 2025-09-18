// 프로퍼티 에디터 만들기
package com.eomcs.spring.ioc.ex07.c;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

// 프로퍼티 에디터 만드는 방법
// => java.beans.PropertyEditor 인터페이스를 구현하면 된다.
// => 그러나 이 인터페이스를 직접 구현하려면 
//    많은 메서드(12개의 메서드)를 모두 구현해야 한다.
//    너무 번거롭다.
// => 그래서 자바는 PropertyEditor를 미리 구현한 
//    PropertyEditorSupport 라는 클래스를 제공한다.
//    따라서 인터페이스를 직접 구현하지 말고 이 클래스를 상속 받아라!
//
// 1) java.beans.PropertyEditorSupport를 상속 받는다.
//
public class CustomDateEditor extends PropertyEditorSupport {
  // 이 메서드는 스프링 IoC 컨테이너가 String 타입의 프로퍼티 값을
  // 다른 타입의 값으로 바꿀 때 호출하는 메서드이다.
  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    System.out.println("CustomDateEditor.setAsText()");
    // 파라미터로 넘어온 String 타입의 프로퍼티 값을
    // 원하는 타입(java.sql.Date)의 값으로 바꿔 내부에 저장한다.
    // => 그러면 스프링 IoC 컨테이너를 이 값을 꺼내서 객체에 주입할 것이다.
    this.setValue(Date.valueOf(text));
  }

  // 언제 어떤 메서드가 호출되는지 확인하기 위해 상속 받은 메서드를 오버라이딩 해보자!
  @Override
  public void setValue(Object value) {
    // 이 메서드는 호출되었을 때 그것을 표시하기 위해 오버라이딩 했다.
    System.out.println("CustomDateEditor.setValue()");
    super.setValue(value); // 원래 하던 일을 수행한다.
  }

  @Override
  public Object getValue() {
    // 이 메서드는 호출되었을 때 그것을 표시하기 위해 오버라이딩 했다.
    System.out.println("CustomDateEditor.getValue()");
    return super.getValue(); // 원래 하던 일을 수행한다.
  }
}
