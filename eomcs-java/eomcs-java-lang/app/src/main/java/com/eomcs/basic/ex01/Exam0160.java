// Object 클래스 - getClass() : 해당 클래스의 정보를 리턴한다.
package com.eomcs.basic.ex01;


public class Exam0160 {

  static class My {
  }

  public static void main(String[] args) {
    My obj1 = new My();

    // 레퍼런스를 통해서 인스턴스의 클래스 정보를 알아낼 수 있다.
    Class classInfo = obj1.getClass();

    // 클래스 정보로부터 다양한 값을 꺼낼 수 있다.
    System.out.println(classInfo.getName()); // 패키지명 + 바깥 클래스명 + 클래스명
    System.out.println(classInfo.getSimpleName()); // 클래스명

    // 빌트인 스태틱 필드를 이용하여 클래스 정보를 알아낼 수 있다.
    // => 자바의 모든 클래스는 'class' 이름의 빌트인 스태틱 필드를 갖고 있다.
    // => 'class' 필드의 타입은 클래스를 정보를 가리키는 Class 이다.
    //    public static Class class = ...;
    //
    Class classInfo2 = My.class;
    System.out.println(classInfo2.getName());
    System.out.println(classInfo2.getSimpleName());

  }
}







