package com.eomcs.oop.ex10.d;

// 추상 클래스와 추상 메서드 문법을 이용하여 
// 템플릿 역할을 할 클래스를 정의하는 예.
// => 이런 설계 기법을 "템플릿 메서드 패턴" 이라고 한다.
//
public abstract class Student {

  public void introduce() {
    // 이렇게 수퍼 클래스는 학생들이 자신을 소개할 때 어떻게 행동해야 하는지 
    // 기본 흐름을 정의한다.

    greet(); // 먼저 인사하기

    say(); // 자신에 대해 소개한다.

    fighting(); // 어떤 각오로 임할 것인지 얘기한다.
  }

  public abstract void greet();
  public abstract void say();
  public abstract void fighting();
}






