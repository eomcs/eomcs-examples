package com.eomcs.algorithm.data_structure.linkedlist2.step4;

// 컬렉션의 값을 조회하는 객체 사용법
//
public interface Iterator {
  boolean hasNext(); // 꺼낼 값이 있는지 알고 싶을 때
  Object next(); // 값을 꺼낼 때
}
