package com.eomcs.design_pattern.decorator.ex02.before;

public class HybridConvertible extends Hybrid {

  // Hybrid를 상속 받았기 때문에 
  // Hybrid 기능은 그래도 사용한다.
  // 문제는 Convertible 기능을 갖기 위해 
  // Convertible 클래스에 있는 코드를 그대로 중복해서 작성해야 한다.
  // => 코드 중복은 항상 문제가 된다.
  //
  boolean openRoof;
  
  public void open(boolean open) {
    this.openRoof = open;
  }
  
  public void run2() {
    if (openRoof) {
      System.out.println("뚜껑 연다.");
    } else {
      System.out.println("뚜껑 닫는다.");
    }
    this.run();
  }
}
