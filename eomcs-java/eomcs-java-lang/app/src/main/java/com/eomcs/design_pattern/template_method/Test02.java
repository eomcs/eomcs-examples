// 템플릿 메서드 패턴 + 팩토리 메서드 패턴 
package com.eomcs.design_pattern.template_method;

public class Test02 {

  public static void main(String[] args) {
    
    // 유닛 객체를 직접 생성하지 않고 공장 객체를 통해 생성한다.

    UnitFactory unitFactory = new UnitFactory();
    
    // 식당 짓기
    Unit u1 = unitFactory.createUnit(UnitFactory.RESTAURANT);
    u1.build(); // 수퍼 클래스에서 상속 받은 메서드를 호출한다.
    
    System.out.println("-----------------------------");
    
    // 훈련소 짓기
    Unit u2 = unitFactory.createUnit(UnitFactory.TRAINING_CENTER);
    u2.build(); 
  }

}
