package com.eomcs.design_pattern.test01.step6;

public class K7Factory implements CarFactory {

  // 객체를 직접 생성하지 못하도록 생성자를 private으로 제한한다.
  private K7Factory() {}

  // 생성한 객체를 저장할 스태틱 필드를 준비
  // => 오직 한 개의 객체만 저장할 의도
  private static K7Factory instance;

  // => 객체를 만들어 리턴해주는 메서드 준비
  public static K7Factory getInstance() {
    if (instance == null) {
      instance = new K7Factory();
    }
    return instance;
  }

  @Override
  public Sedan createCar() {
    Sedan s = new Sedan();
    s.maker = "기아자동차";
    s.model = "K7";
    s.cc = 2500;
    s.auto = true;
    s.sunroof = true;
    return s;
  }
}
