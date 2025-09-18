package com.eomcs.design_pattern.abstract_factory;

// 추상 팩토리 규칙에 따라 공장 클래스를 만들기
// => 직접 UnitFactory 인터페이스를 구현해도 되지만,
//    서브 클래스에게 상속 해줄 필드나 메서드가 있다면 중간에 추상 클래스 문법을 적용하여 
//    추상 클래스를 만드는 것이 좋다.
// => 서브 클래스에게 상속 해줄 필드나 메서드가 없다면 직접 인터페이스를 구현하라.
//
public class ZergUnitFactory implements UnitFactory{
  
  // 객체 생성 과정이 복잡한 경우 직접 객체를 생성하기 보다는 
  // 객체를 생성해주는 메서드를 호출하는 것이 유지보수에 좋다.
  //
  public Unit createUnit(int building) {
    switch (building) {
      case RESTAURANT:
        return createRestaurant();
      case TRAINING_CENTER:
        return createTrainingCenter();
    }
    
    return null;
  }
  
  private Unit createRestaurant() {
    Unit unit = new Restaurant();
    unit.setName("저그: 군인식당");
    unit.setArea(200);
    unit.setType(Unit.ATTACK_BUILDING);
    return unit;
  }
  
  private Unit createTrainingCenter() {
    Unit unit = new TrainingCenter();
    unit.setName("저그: 훈련소");
    unit.setArea(100);
    unit.setType(Unit.ATTACK_BUILDING);
    return unit;
  }
}



