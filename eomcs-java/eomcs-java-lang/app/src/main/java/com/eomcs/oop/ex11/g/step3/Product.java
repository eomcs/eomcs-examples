package com.eomcs.oop.ex11.g.step3;

public class Product {
  // 카테고리 값을 잘못 입력할 경우를 방지하기 위해
  // 문자열을 상수로 정의한다.
  public static final String appliance_tv = "appliance/tv";
  public static final String computer_ram = "computer/ram";
  public static final String book_novel = "book/novel";

  String category;
  String name;
  String maker;
  int price;
}
