package com.eomcs.design_pattern.abstract_factory;

// 추상 팩토리 : 팩토리의 사용 규칙만 정의한다. 
// => 생산 방식은 같지만 주제에 따라 생산품은 달라진다.
// => 생산 방식이 같으려면 메서드의 사용법이 같아야 한다.
//    그래서 인터페이스로 팩토리 사용 규칙을 정의하는 것이다.
//
public interface UnitFactory {
  
  // 인터페이스의 필드는 모두 public static final 이다.
  int RESTAURANT = 1;
  int TRAINING_CENTER = 2;
  
  // 공장 객체의 사용 규칙
  // => 유닛을 생성하고 싶다면 createUnit()을 호출하라!
  // => 사용 규칙은 메서드 시그너처만 선언한다. 구현하지 않는다.
  // => 규칙이기에 무조건 public 으로 공개된다.
  // => 추상 메서드이다.
  Unit createUnit(int building);
}



