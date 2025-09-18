// Object 클래스 - clone() 사용법 III
package com.eomcs.basic.ex01;

public class Exam0172 {

  // 인스턴스 복제 기능을 활성화하려면 Cloneable 인터페이스를 구현해야 한다.
  // => 이 인터페이스에는 메서드가 선언되어 있지 않다.
  // => 따라서 클래스는 따로 메서드를 구현할 필요가 없다.
  // => Cloneable을 구현하는 이유는 
  //    JVM에게 이 클래스의 인스턴스를 복제할 수 있음을 표시하기 위함이다.
  //    이 표시가 안된 클래스는 JVM이 인스턴스를 복제해 주지 않는다. 즉 clone()을 호출할 수 없다.
  static class Score implements Cloneable {
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

    @Override
    public Score clone() throws CloneNotSupportedException {
      // Object의 clone() 메서드는 Heap의 존재하는 인스턴스 변수들을 그대로 복제한다.
      return (Score) super.clone();
    }
  }

  public static void main(String[] args) throws Exception {

    Score s1 = new Score("홍길동", 100, 100, 100);
    Score s2 = s1.clone(); // 이제 예외가 발생하지 않는다!
    // 복제 실행 오류가 발생하지 않는 이유?
    // => Score 클래스의 복제 기능을 활성화시켰기 때문이다.
    //    class Score implements Cloneable {...}
    //
    System.out.println(s1 == s2);
    System.out.println(s1);
    System.out.println(s2);
  }
}







