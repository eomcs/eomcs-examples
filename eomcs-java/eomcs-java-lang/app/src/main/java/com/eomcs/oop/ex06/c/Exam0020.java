// 오버라이딩(overriding) - 오버라이딩을 하는 이유?
package com.eomcs.oop.ex06.c;

public class Exam0020 {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    public void compute() {
      this.sum = this.kor + this.eng + this.math;
      this.aver = this.sum / 3f;
    }
  }

  static class Score2 extends Score {
    int music;
    int art;

    // 오버라이딩(overriding)?
    // => 상속 받은 메서드를 서브 클래스의 역할에 맞게 재정의하는 문법
    // => 상속 받은 메서드와 똑 같은 시그너처(메서드명,파라미터 타입/개수/순서)로 메서드를 정의한다.
    // => 오버라이딩 메서드는 원래의 메서드 보다 접근 범위가 같거나 커야 한다.
    //    원래의 메서드 보다 접근 범위가 줄어들면 안된다.
    public void compute() {
      this.sum = this.kor + this.eng + this.math + this.music + this.art;
      this.aver = this.sum / 5f;
    }
  }

  public static void main(String[] args) {

    Score2 score = new Score2();

    score.kor = 100;
    score.eng = 100;
    score.math = 100;
    score.music = 50;
    score.art = 50;

    score.compute(); // Score의 compute() 가 아니라 Score2에서 재정의한 메서드

    System.out.printf("%d(%f)\n", score.sum, score.aver);

    // Score2 클래스의 변경 사항에 맞춰
    // compute() 메서드를 재정의 하였다.
    // 그래서 합계와 평균이 제대로 나오는 것이다.
  }
}
