package com.eomcs.algorithm.data_structure.array;

//1) 인스턴스/객체 (의 주소)를 담을 레퍼런스 배열을 준비한다.
//2) 인스턴스를 추가하는 add() 메서드 정의
public class MyArrayList02 {

  static Object[] elementData = new Object[5];
  static int size;

  static public boolean add(Object e) {
    elementData[size++] = e;
    return true;
  }
}
