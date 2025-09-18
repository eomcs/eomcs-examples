// 값을 꺼내주는 객체의 사용 규칙 정의 - 값을 일관성 있게 꺼내기 위해 사용 규칙을 정의한다.
package com.eomcs.design_pattern.iterator.after;

public interface Iterator<E> {
  // 데이터 목록에서 꺼낼 값이 있다면 true, 없다면 false
  boolean hasNext();
  
  // 데이터 목록에서 값을 꺼낸다.
  E next();
}
