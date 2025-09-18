// 팩토리 메서드 적용 전
package com.eomcs.design_pattern.factory_method;

class Car {
  String model;
  int cc;
  boolean sunroof;
}

public class Test01 {

  public static void main(String[] args) {
    // 인스턴스를 만들 때 사용에 적합하도록 직접 초기화시켜야 한다.
    Car c1 = new Car();
    c1.model = "티코";
    c1.cc = 890;
    c1.sunroof = false;
    
    Car c2 = new Car();
    c2.model = "티코 골드";
    c2.cc = 890;
    c2.sunroof = true;
    
    Car c3 = new Car();
    c3.model = "소나타";
    c3.cc = 1980;
    c3.sunroof = false;
    
    Car c4 = new Car();
    c4.model = "소나타 골드";
    c4.cc = 1980;
    c4.sunroof = true;
  }

}










