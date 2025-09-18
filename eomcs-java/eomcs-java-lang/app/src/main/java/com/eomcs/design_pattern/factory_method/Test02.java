// 팩토리 메서드 적용 후
package com.eomcs.design_pattern.factory_method;

class Car2 {
  String model;
  int cc;
  boolean sunroof;
}


// 인스턴스 생성과정이 복잡할 때 사용하는 설계 기법이다.
// 복잡한 객체 생성 코드를 메서드에 캡슐화한다.
// 메서드 호출을 통해 인스턴스를 리턴 받는다.
// => 이런 메서드를 '팩토리 메서드'라 부른다.
// => 보통 인스턴스의 팩토리 역할을 하는 클래스는 'XxxFactory'라는 이름을 짓는다.
//
class Car2Factory {

  // 인스턴스를 생성해주는 메서드를 정의한다.
  // => 이렇게 설계하는 기법을 "팩토리 메서드"라 한다.
  public static Car2 create(String product) {
    Car2 c = new Car2();

    switch (product) {
      case "tc":
        c.model = "티코";
        c.cc = 890;
        c.sunroof = false;
        break;
      case "tcg":
        c.model = "티코 골드";
        c.cc = 890;
        c.sunroof = true;
        break;
      case "sn":
        c.model = "소나타";
        c.cc = 1980;
        c.sunroof = false;
        break;
      case "sng":
        c.model = "소나타 골드";
        c.cc = 1980;
        c.sunroof = true;
        break;
      default:
        return null;
    }

    return c;
  }
}


public class Test02 {

  public static void main(String[] args) {

    Car2 c1 = Car2Factory.create("tc");
    Car2 c2 = Car2Factory.create("tcg");
    Car2 c3 = Car2Factory.create("sn");
    Car2 c4 = Car2Factory.create("sng");
    Car2 c5 = Car2Factory.create("ok");

    System.out.println(c1.model);
    System.out.println(c2.model);
    System.out.println(c3.model);
    System.out.println(c4.model);
    System.out.println(c5.model);


  }

}


