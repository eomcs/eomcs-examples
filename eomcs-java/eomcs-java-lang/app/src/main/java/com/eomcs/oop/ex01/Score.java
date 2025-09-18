package com.eomcs.oop.ex01;

//# 패키지 클래스
// - 다른 클래스 안에 선언되지 않고 별도로 선언된 클래스를 "패키지 클래스"라 부른다.
// - 한 소스 파일에 여러 개의 클래스를 정의할 수 있지만,
//   유지보수를 쉽게 하기 위해 보통 한 소스 파일에 한 클래스를 정의한다.
// - 패키지 클래스란? 패키지에 직접 소속된 클래스라는 의미다.
// - 이전에 만든 Score 클래스는 main() {} 블록 안에 정의하였다.
//   main() {} 블록 안에 정의한 클래스는
//   main() {} 블록 안에서만 사용할 수 있다.
//
public class Score {
  public String name;
  public int kor;
  public int eng;
  public int math;
  public int sum;
  public float aver;
}