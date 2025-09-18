package com.eomcs.oop.ex11.g.step4;

public class Product {
  // 상수를 문자열로 정의하면
  // 값을 저장할 때 메모리를 많이 차지한다.
  // 그래서 실무에서는 보통 정수 값으로 처리한다.
  // 그러면 다음과 같이 상수를 정의할 수 있다.
  // 이때 상수의 이름을 대문자로 지어서 구분하기 쉽도록 한다.
  //
  public static final int COMPUTER_CPU = 1;
  public static final int COMPUTER_VGA = 2;
  public static final int COMPUTER_RAM = 3;
  public static final int COMPUTER_MOUSE = 4;
  public static final int COMPUTER_KEYBOARD = 5;

  public static final int APPLIANCE_TV = 10;
  public static final int APPLIANCE_AUDIO = 11;
  public static final int APPLIANCE_DVD = 12;
  public static final int APPLIANCE_VACUUMCLEANER = 13;

  public static final int BOOK_POET = 100;
  public static final int BOOK_NOVEL = 101;
  public static final int BOOK_ESSAY = 102;
  public static final int BOOK_IT = 103;
  public static final int BOOK_LANG = 104;

  int category;
  String name;
  String maker;
  int price;
}
