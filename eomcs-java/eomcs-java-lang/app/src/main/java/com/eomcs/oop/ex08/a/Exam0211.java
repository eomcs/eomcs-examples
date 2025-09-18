//# 캡슐화 문법 사용 후 - 개발자가 특정 필드를 직접 접근하지 못하게 막기
package com.eomcs.oop.ex08.a;

class Score2 {
  String name;
  int kor;
  int eng;
  int math;

  // sum 이나 aver 필드는 kor, eng, math 값을 연산한 결과를 보관하기 때문에
  // 직접 접근하여 값을 변경하는 것을 허용해서는 안된다.
  // 허용하는 순간 개발자의 잘못된 명령으로
  // 국,영,수 점수와 합계, 평균이 서로 맞지 않는 문제가 발생할 수 있다.
  // 그래서 자바는 필드나 메서드의 외부 접근 범위를 조정하는 문법을 제공한다.
  // 그 문법을 '캡슐화(encapsulation)'라 부른다.
  //
  private int sum;
  private float aver;

  // sum과 aver의 값을 직접 변경하지 못하게 접근을 막았으면,
  // 외부에서 이 값들을 조회할 수 있는 방법/수단(method)은 제공해야 한다.
  // => 보통 이렇게 필드의 값을 조회하는 용도로 사용하기 위해 메서드를 만들 경우
  //    메서드의 용도를 이해하기 쉽도록 getXxx() 형태로 이름을 짓는다.
  //       get필드명() {...}
  // => 메서드의 이름이 get 으로 시작한다고 해서 "게터(getter)"라고 부른다.
  // => 그리고 이런 getter는 공개 모드로 설정한다.
  //
  public int getSum() {
    return this.sum;
  }

  public float getAver() {
    return this.aver;
  }

  void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}

public class Exam0211 {
  public static void main(String[] args) {

    Score2 s1 = new Score2();

    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 80;

    s1.compute();

    // 계산을 한 후에 임의적으로 합계나 평균을 변경한다면?
    // => sum과 aver 필드는 private 접근만 허용한다.
    // => 즉 해당 필드와 같은 클래스에 소속된 멤버만이 접근할 수 있고
    //    외부 클래스에서는 접근할 수 없다.
    // => 그래서 다음과 같이 임의로 접근하여 값을 변경할 수 없다.
    //
    //    s1.sum = s1.kor + s1.eng + s1.math; // 컴파일 오류!
    //    s1.aver = s1.sum / 4f; // 컴파일 오류!

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name, s1.kor, s1.eng, s1.math, s1.getSum(), s1.getAver());
  }
}











