package com.eomcs.oop.ex10.d;

public class Kim extends Student {
  @Override
  public void greet() {
    System.out.println("안녕하십니까! 모두 식사 하셨지요?");
  }
  @Override

  public void say() {
    System.out.println("저는 ..어쩌구...저쩌구...입니다.");
  }

  @Override
  public void fighting() {
    System.out.println("아자아자!! 우리 모두 화이팅!");

  }
}
