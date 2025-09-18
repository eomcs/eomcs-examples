// 기존의 클래스를 손대지 않고 새 기능만 추가한다.
// 어떻게? 상속 문법을 이용한다.
package com.eomcs.oop.ex05.d;

// 상속
// => 기존 클래스의 코드를 손대지 않고 기능을 확장하게 도와주는 문법이다.
// => 재사용할 기존 클래스를 지정한다.
// => 새 클래스에는 추가할 기능을 덧붙인다.
public class Sedan extends Car { // "Sedan을 통해서 Car의 기능을 사용할 수 있다" 

  // 인스턴스 변수 추가
  public boolean sunroof;
  public boolean auto;

  public Sedan(String model, String maker, int capacity,
      boolean sunroof, boolean auto) {
    this.model = model;
    this.maker = maker;
    this.capacity = capacity;
    this.sunroof = sunroof;
    this.auto = auto;
  }

}
