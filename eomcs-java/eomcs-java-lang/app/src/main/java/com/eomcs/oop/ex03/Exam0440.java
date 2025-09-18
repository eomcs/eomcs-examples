// 생성자 - this()
package com.eomcs.oop.ex03;

public class Exam0440 {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float average;

    Score() {
      // 다른 생성자를 호출할 때는 this()를 사용한다.
      // 단, 생성자의 첫 문장으로 와야 한다.

      this("이름없음"); // Score(String) 생성자 호출
      System.out.println("Score()");
    }

    Score(String name) {
      // 다른 생성자를 호출할 때는 this()를 사용한다.
      // 단, 생성자의 첫 문장으로 와야 한다.
      this(name, 0, 0, 0); // Score(String, int, int, int) 생성자 호출

      System.out.println("Score(String)");
    }

    Score(String name, int kor, int eng, int math) {
      System.out.println("Score(String,int,int,int) 호출!");
      this.name = name;
      this.kor = kor;
      this.eng = eng;
      this.math = math;
      this.compute();
    }

    public void compute() {
      //      this("오호라"); // 일반 메서드에서 생성자를 호출할 수 없다!
      this.sum = this.kor + this.eng + this.math;
      this.average = this.sum / 3f;
    }
  }

  public static void main(String[] args) {

    // 생성자가 여러 개 일 때 파라미터에 전달하는 값으로 구분한다.
    Score s1 = new Score();

    // 인스턴스 생성 후에 나중에 따로 생성자를 호출할 수 없다!
    //    s1.Score("홍길동", 100, 90, 77); // 컴파일 오류!
    System.out.println("--------------------------------");


    Score s2 = new Score("유관순");
    System.out.println("--------------------------------");

    Score s3 = new Score("홍길동", 100, 90, 77);
    System.out.println("--------------------------------");
    // Score s4 = new Score(true); // 논리 값을 받는 생성자는 없다!

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", s1.name, s1.kor, s1.eng, s1.math, s1.sum,
        s1.average);

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", s2.name, s2.kor, s2.eng, s2.math, s2.sum,
        s2.average);

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", s3.name, s3.kor, s3.eng, s3.math, s3.sum,
        s3.average);
  }
}

// 생성자?
// => 인스턴스(객체)를 생성한 후에 사용하기 전에 유효한 값으로 초기화시키 작업을 수행한다.
//


