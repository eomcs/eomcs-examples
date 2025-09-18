// Object 클래스 - equals()에 대하여  
package com.eomcs.basic.ex01;

public class Exam0130 {

  static class My {
    String name;
    int age;
  }

  public static void main(String[] args) {
    My obj1 = new My();
    obj1.name = "홍길동";
    obj1.age = 20;

    My obj2 = new My();
    obj2.name = "홍길동";
    obj2.age = 20;

    System.out.println(obj1 == obj2);

    // Object에서 상속 받은 equals()는 == 연산자와 마찬가지로 인스턴스가 같은지를 비교한다.
    // 만약 그 내용물이 같은지 비교하고 싶다면 equals()를 재정의 하라!
    System.out.println(obj1.equals(obj2));

  }

}







