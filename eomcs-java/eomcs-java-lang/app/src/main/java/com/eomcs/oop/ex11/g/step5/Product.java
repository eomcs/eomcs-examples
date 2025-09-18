package com.eomcs.oop.ex11.g.step5;

public class Product {
  // 카테고리 상수가 많을 경우 별도의 클래스로 분리하는 것이
  // 상수 관리에 좋다.
  // 그래서 Product 클래스에 정의되었던 상수를
  // Category 클래스로 분리한다.
  //
  int category;
  String name;
  String maker;
  int price;
}
