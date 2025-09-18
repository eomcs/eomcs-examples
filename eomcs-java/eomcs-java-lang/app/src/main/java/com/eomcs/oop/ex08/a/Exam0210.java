//# 캡슐화 문법 사용 전 - 클래스를 작성한 사람의 의도와 다르게 사용할 때
package com.eomcs.oop.ex08.a;

public class Exam0210 {
  public static void main(String[] args) {

    Score s1 = new Score();

    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 80;

    s1.compute();

    // 계산을 한 후에 임의적으로 합계나 평균을 변경한다면?
    s1.aver = 100f;

    // 원래 프로그램에서 의도한 대로 계산 결과가 나오지 않는다!
    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name, s1.kor, s1.eng, s1.math, s1.sum, s1.aver);

    // 해결책!
    // - sum이나 aver 필드처럼
    //   기존 필드의 값을 연산해서 나온 결과를 저장하는 경우,
    //   직접 해당 변수의 값을 변경하지 못하도록 막아야 한다.
    // - 오직 메서드를 통해서만 변경하도록 해야 한다.
  }
}











