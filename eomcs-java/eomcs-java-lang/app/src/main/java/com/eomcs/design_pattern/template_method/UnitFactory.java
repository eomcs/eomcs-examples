package com.eomcs.design_pattern.template_method;

// 템플릿 메서드 패턴 + 팩토리 메서드 패턴 
public class UnitFactory {
  
  public static final int RESTAURANT = 1;
  public static final int TRAINING_CENTER = 2;
  
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
    unit.setName("군인식당");
    unit.setArea(50);
    unit.setType(Unit.GENERAL_BUILDING);
    return unit;
  }
  
  private Unit createTrainingCenter() {
    Unit unit = new TrainingCenter();
    unit.setName("훈련소");
    unit.setArea(500);
    unit.setType(Unit.GENERAL_BUILDING);
    return unit;
  }
}



