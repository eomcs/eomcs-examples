package com.eomcs.oop.ex11.overview.step5;

// 컬렉션에서 데이터를 조회하는 객체 사용법을 정의
// 
public interface Iterator {
  boolean hasNext(); // 컬렉션에서 꺼낼 데이터가 있는지 확인
  Object next(); // 컬렉션에서 데이터를 꺼내기
}
