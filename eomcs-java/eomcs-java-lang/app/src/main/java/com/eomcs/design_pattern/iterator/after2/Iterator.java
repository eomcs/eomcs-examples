package com.eomcs.design_pattern.iterator.after2;

// 데이터 조회를 수행하는 객체 사용법 정의 
public interface Iterator<E> {

  // 목록에 조회할 데이터가 있는지 검사할 때 호출
  boolean hasNext();

  // 목록에서 데이터를 한 개 꺼낼 때 호출
  E next();
}
