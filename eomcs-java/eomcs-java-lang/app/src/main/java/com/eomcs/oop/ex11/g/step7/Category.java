// static nested class 문법을 이용하여 상수를 효과적으로 관리하기
package com.eomcs.oop.ex11.g.step7;

//상수를 관리하는 용도로만 사용될 클래스라면 서브 클래스를 만들지 못하도록 막아야 한다.
//=> final 제한자를 사용하라!
public class Category {

  //
  // static nested class 를 사용하여
  // 여러 상수를 그룹 별로 묶어 관리한다.
  // 이점:
  // - 패키지 멤버 클래스로 만들면 작은 클래스가 여러 개 생성된다.
  //   즉 소스 파일이 너무 많이 생기기 때문에 관리하기가 번거롭다.
  //   예) Computer.java, Mouse.java, Appliance.java, Book.java
  // - 이런 작은 클래스들은
  //   오히려 한 클래스 안에 static 중첩 클래스로 선언하는 것이
  //   소스 파일 관리에 좋다.
  //
  // 이렇게 분류의 각 계층을 static 중첩 클래스로 선언하면
  // 상수의 관리가 편리해진다.
  //
  // 보통 클래스 이름은 대문자로 시작해야 한다.
  // 그런데 소문자로 선언하였다. 그 이유는?
  // - OGNL 표기법에서 하위 프로퍼티(필드)를 지정하는 것처럼 보이게 하려고
  //   일부러 소문자로 이름을 지었다.
  //   예) int value = Category.computer.CPU;
  // - 바깥 클래스를 패키지처럼 생각할 수 있어 이해하고 관리하기 편하다.
  //
  //
  // static nested class를 이런 식으로 적용한 대표적인 예가
  // 안드로이드 컴파일러가 자동 생성하는 R.java 이다.
  //
  public final static class computer {
    public static final int CPU = 1;
    public static final int VGA = 2;
    public static final int RAM = 3;
    public static final int MOUSE = 4;
    public static final int KEYBOARD = 5;
  }

  public final static class appliance {
    public static final int TV = 10;
    public static final int AUDIO = 11;
    public static final int DVD = 12;
    public static final int VACUUMCLEANER = 13;
  }

  public final static class book {
    public static final int POET = 100;
    public static final int NOVEL = 101;
    public static final int ESSAY = 102;
    public static final int IT = 103;
    public static final int LANG = 104;
  }
}
