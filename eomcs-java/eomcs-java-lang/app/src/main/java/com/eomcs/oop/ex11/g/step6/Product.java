package com.eomcs.oop.ex11.g.step6;

public class Product {
  // 카테고리 상수가 많을 경우 별도의 클래스로 분리하는 것이
  // 상수 관리에 좋다.
  // 또한 각 카테고리 별로 클래스를 분리하면 더 관리하기 쉬워진다.
  //
  int category;
  String name;
  String maker;
  int price;
}
