package com.eomcs.oop.ex02.step11;

// 데이터 타입 정의  
// 1) 클래스 사용전: 낱개 변수 사용
//
public class App {
  public static void main(String[] args) {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    name = "홍길동";
    kor = 100;
    eng = 90;
    math = 85;
    sum = kor + eng + math;
    aver = (float) sum / 3;

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", name, kor, eng, math, sum, aver);

    name = "임꺽정";
    kor = 90;
    eng = 80;
    math = 75;
    sum = kor + eng + math;
    aver = (float) sum / 3;

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", name, kor, eng, math, sum, aver);

    name = "유관순";
    kor = 80;
    eng = 70;
    math = 65;
    sum = kor + eng + math;
    aver = (float) sum / 3;

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", name, kor, eng, math, sum, aver);
  }
}
