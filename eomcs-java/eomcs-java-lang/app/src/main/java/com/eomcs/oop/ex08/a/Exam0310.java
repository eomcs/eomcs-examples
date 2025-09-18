//# 캡슐화 문법 사용 전 - 개발자가 특정 필드를 직접 접근하지 못하게 막기
package com.eomcs.oop.ex08.a;

public class Exam0310 {
  public static void main(String[] args) {

    Score2 s1 = new Score2();

    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 80;

    s1.compute();

    // 캡슐화 문법으로 sum과 aver의 값을 임의적으로 조작하는 것은 막았다.
    // 그런데 또 다른 문제가 있다.
    // 만약 개발자가 국, 영, 수 점수를 변경한 후
    // compute()를 호출하지 않는다면?
    //
    s1.eng = 100;
    s1.math = 100;

    // 원래 Score2의 개발자 의도대로 사용한다면
    // 다음과 같이 국, 영, 수 점수를 변경한 후 compute()를 호출했어야 한다.
    //    s1.compute();
    // 그런데 이렇게 하지 않는 경우가 문제가 되는 것이다.

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name, s1.kor, s1.eng, s1.math, s1.getSum(), s1.getAver());
  }
}











