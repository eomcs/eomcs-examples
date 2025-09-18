package com.eomcs.design_pattern.template_method;

public class Restaurant extends Unit {

  @Override
  public void prepare() {
    System.out.println("간단히 땅을 고른다.");
  }

  @Override
  public void construct() {
    System.out.println("조립 파넬을 세운다.");
  }

  @Override
  public void install() {
    System.out.println("내부 배선과 창을 붙인다.");
    
  }

  @Override
  public void interio() {
    System.out.println("식탁과 의자를 배치한다.");
  }
  
}
