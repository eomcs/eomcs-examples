package com.eomcs.design_pattern.abstract_factory;

public class TrainingCenter extends Unit {

  @Override
  public void prepare() {
    System.out.println(this.name + " : 운동장을 고르고, 숙소 건물 땅에 콘크리트 포장한다.");
  }

  @Override
  public void construct() {
    System.out.println(this.name + " : 운동장에 잔디를 깔고, 숙소 건물을 짓는다.");
  }

  @Override
  public void install() {
    System.out.println(this.name + " : 운동장에 연설대를 설치하고 숙소의 내부 배선과 창을 붙인다.");
    
  }

  @Override
  public void interio() {
    System.out.println(this.name + " : 숙소의 내부 침실을 꾸민다.");
  }
  
}
