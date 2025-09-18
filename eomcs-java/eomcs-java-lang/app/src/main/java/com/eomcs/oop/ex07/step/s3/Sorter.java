package com.eomcs.oop.ex07.step.s3;

// 정렬을 수행하는 클래스를 한 타입으로 묶기 위해
// 다음 클래스를 정의하였다.
// => 추상 클래스로 선언하면 인스턴스 생성을 막을 수 있다.
// => 즉 서브 클래스를 정의할 때 상속 받아 쓰는 용도로 한정할 수 있다.
public abstract class Sorter {
  public void sort(int[] values) {};
}
