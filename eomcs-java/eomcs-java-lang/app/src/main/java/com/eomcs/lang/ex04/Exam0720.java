package com.eomcs.lang.ex04;

//# 변수의 종류 - 종류에 따른 변수의 사용 범위
//
class MyClass {
  int a = 11; // 인스턴스 변수
  static int b = 22; // 클래스 변수

  static void m1(int v1/*로컬변수=파라미터*/) {
    int v2; // 로컬 변수
    v2 = 200;

    System.out.println(v1);
    System.out.println(v2);
  }
}

public class Exam0720 {
  public static void main(String[] args) {

    //1) 인스턴스 변수를 사용하는 방법
    MyClass obj1 = new MyClass(); // new 명령이 실행될 때 변수가 준비된다.
    System.out.println(obj1.a); // 인스턴스를 가리키는 레퍼런스를 통해 변수를 사용할 수 있다.

    //2) 클래스 변수를 사용하는 방법
    System.out.println(MyClass.b); // 클래스를 사용(로딩)하는 시점에 준비된다.


    //3) 로컬 변수를 사용하는 방법
    MyClass.m1(100); // 메서드를 실행할 때 변수가 준비된다. 메서드를 실행하는 동안만 

    // 메서드 호출이 끝나면, 그 메서드의 로컬 변수를 사용할 방법이 없다.
    //    v1 = 300; // 오류!
    // 메서드를 실행하는 동안, 그 메서드 안에서만 사용할 수 있다.
  }
}

