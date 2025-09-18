package com.eomcs.oop.ex05.x1.upgrade5;

// 5) 계산기 기능 확장 방법5 - 의존 객체 주입 방식을 적용하여 기능 확장
//    - 곱하기 계산 기능 추가
//    - 기존의 Calculator 객체를 포함한 후 새 기능을 추가하기
//
public class CalculatorTest {
  public static void main(String[] args) {

    com.eomcs.oop.ex05.x1.Calculator calculator = new com.eomcs.oop.ex05.x1.Calculator(); 

    Calculator2 upgradeCalculator = new Calculator2(calculator);

    upgradeCalculator.plus(100);
    upgradeCalculator.minus(200);
    upgradeCalculator.multiple(2);

    System.out.println(upgradeCalculator.getResult());
  }
}












