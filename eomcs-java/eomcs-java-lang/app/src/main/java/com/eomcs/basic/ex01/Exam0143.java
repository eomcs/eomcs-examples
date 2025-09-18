// Object 클래스의 메서드를 오버라이딩 하기 - hashCode()
package com.eomcs.basic.ex01;

public class Exam0143 {

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
  }

  public static void main(String[] args) {
    Score s1 = new Score("홍길동", 100, 100, 100);
    Score s2 = new Score("홍길동", 100, 100, 100);

    // s1, s2의 Score 인스턴스는 서로 다른 인스턴스이다. 
    System.out.println(s1 == s2);
    
    // Object에서 상속 받아 사용하는 hashCode()는 
    // 기본적으로 인스턴스 마다 고유의 값을 리턴한다.
    // 그래서 다음 출력은 비록 같은 값을 갖고 있다하더라도 
    // 인스턴스가 다르기 때문에 해시코드의 값이 다르다!
    // 주의! 
    // => 인스턴스 주소가 아니다!
    System.out.printf("%d, %d\n", 
        s1.hashCode(), s2.hashCode());

  }
}






