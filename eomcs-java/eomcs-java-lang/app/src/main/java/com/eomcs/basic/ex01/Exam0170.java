// Object 클래스 - clone() 사용법 I
package com.eomcs.basic.ex01;

// clone()은 인스턴스를 복제할 때 호출하는 메서드이다.

public class Exam0170 {

  static class Score {

    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    public Score() {}

    public Score(String name, int kor, int eng, int math) {
      this.name = name;
      this.kor = kor;
      this.eng = eng;
      this.math = math;
      this.sum = this.kor + this.eng + this.math;
      this.aver = this.sum / 3f;
    }

    @Override
    public String toString() {
      return "Score [name=" + name + ", kor=" + kor + ", eng=" + eng + ", math=" + math + ", sum="
          + sum + ", aver=" + aver + "]";
    }

  }

  public static void main(String[] args) {

    Score s1 = new Score("홍길동", 100, 100, 100);
    System.out.println(s1);

    // 인스턴스 복제
    // 방법1:
    // => 직접 복제한다. 즉 새 객체를 만들어 기존 객체의 값을 저장한다.
    Score s2 = new Score(s1.name, s1.kor, s1.eng, s1.math);

    // s1과 s2는 서로 다른 인스턴스이다.
    System.out.println(s1 == s2);

    // s1과 s2에 들어 있는 값은 같다.
    System.out.println(s1);
    System.out.println(s2);

    // 방법2:
    // Object에서 상속 받은 clone()을 호출한다.
    //    Score s3 = (Score) s1.clone(); // 컴파일 오류!
    //
    // Object에서 상속 받은 clone()은 protected 이다.
    // 따라서 같은 패키지에 소속된 클래스이거나 상속 받은 서브 클래스가 아니면 호출할 수 없다.
    // 비록 Object의 서브 클래스라 할지라도 남의 인스턴스로 protected 멤버를 사용할 수 없다.
    // 자신이 상속 받은 protected 멤버인 경우에만 접근할 수 있다.

    try {
      Exam0170 test = new Exam0170();
      test.clone(); // OK!
    } catch (Exception e) {

    }

    // 해결책:
    // => Object에서 상속 받은 clone()을 오버라이딩 하라!
    // => Exam0171.java 를 살펴보라!

  }
}







