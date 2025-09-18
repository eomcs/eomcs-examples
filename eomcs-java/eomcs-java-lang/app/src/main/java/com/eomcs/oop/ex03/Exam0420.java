// 생성자 - 파라미터를 받는 생성자 
package com.eomcs.oop.ex03;

public class Exam0420 {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float average;

    // 생성자가 한 개라도 있으면 컴파일러는 자동으로 기본 생성자를 만들어주지 않는다.
    //    public Score() {}

    Score(String name, int kor, int eng, int math) {
      System.out.println("Score(String,int,int,int) 호출!");
      // 파라미터로 받은 값을 새로 만든 인스턴스 변수에 저장한다.
      // => 이렇게 생성자는 새로 만든 인스턴스 변수를 초기화시키는 일을 한다.
      this.name = name;
      this.kor = kor;
      this.eng = eng;
      this.math = math;
      this.compute();
    }

    public void compute() {
      this.sum = this.kor + this.eng + this.math;
      this.average = this.sum / 3f;
    }
  }

  public static void main(String[] args) {
    // Score 클래스에는 기본 생성자가 없기 때문에
    // 다음 문장은 컴파일 오류이다!
    //    Score s0 = new Score();

    // Score 인스턴스를 만든 후에는 
    // 생성자를 호출할 때 그 생성자의 파라미터 값을 주어야 한다.
    Score s1 = new Score("홍길동", 100, 90, 77);
    Score s2 = new Score("임꺽정", 80, 88, 87);

    // 생성자에서 이미 계산을 수행했기 때문에 
    // 합계와 평균을 계산하기 위해 따로 compute()를 호출할 필요가 없다.
    // 이것이 생성자를 사용하는 이유이다.
    // 생성자를 사용하면 좀 더 코드가 간결해진다.
    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name, s1.kor, s1.eng, s1.math, s1.sum, s1.average);

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s2.name, s2.kor, s2.eng, s2.math, s2.sum, s2.average);
  }
}

// 생성자?
// => 인스턴스(객체)를 생성한 후에 사용하기 전에(제대로 쓰일 수 있도록) 
//    유효한(적당한) 값으로 초기화시키는 작업을 수행한다.
// 







