// Object 클래스의 메서드를 오버라이딩 하기 - toString()
package com.eomcs.basic.ex01;

public class Exam0123 {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    public Score(String name, int kor, int eng, int math) {
      this.name = name;
      this.kor = kor;
      this.eng = eng;
      this.math = math;
      this.sum = kor + eng + math;
      this.aver = this.sum / 3f;
    }

    // Object로부터 상속 받은 toString()의 리턴 값이 마음에 들지 않는다면 
    // 재정의하라!
    // => 보통 인스턴스의 내부 데이터를 문자열로 리턴하도록 변경한다.
    // => 프로그램을 실행하는 중에 인스턴스의 내부 값을 빠르게 확인하고 싶을 때 
    //    개발자들이 종종 이 메서드를 오버라이딩 한다.
    //
    @Override
    public String toString() {
      return String.format("%s,%d,%d,%d,%d,%.1f",
          this.name, this.kor, this.eng, this.math, 
          this.sum, this.aver);
    }
  }

  public static void main(String[] args) throws Exception {

    Score s1 = new Score("홍길동", 100, 100, 100);

    String str = s1.toString();
    // Score 클래스에서 Object의 toString()을 오버라이딩 했기 때문에,
    // Score의 toString()을 호출한다.

    System.out.println(str);

    // println()에 String이 아닌 객체를 넘기면,
    // println()에서 내부적으로 그 객체에 대해 toString()을 호출하여
    // 그 리턴 값을 출력한다.
    // 따라서, 다음 코드는 위의 코드와 같은 결과를 출력한다.
    System.out.println(s1);
    // 그러니 println()으로 객체의 값을 출력할 때 
    // 굳이 toString()을 번거롭게 호출하지 말라! 
  }
}






