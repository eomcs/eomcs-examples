//# 캡슐화 문법 사용 후 - 필드의 값을 변경할 때 마다 특정 작업을 수행하게 만들기
package com.eomcs.oop.ex08.a;

class Score3 {
  String name;

  // 국, 영, 수 점수를 바꿀 때 마다 자동으로 합계, 평균을 계산해야 한다.
  // 방법?
  // - 직접 필드의 값을 바꾸게 하지 말고 메서드를 통해 바꾸도록 유도한다.
  // - 이렇게 필드의 값을 바꿀 때 마다 뭔가를 수행해야 하는 경우,
  //   해당 필드의 직접 접근을 막아라!
  private int kor;
  private int eng;
  private int math;

  // 대신 메서드를 통해 값을 설정하게 하라!
  // 보통 필드의 값을 설정하는 메서드는 'set필드명()'으로 이름을 짓는다.
  // - 이런 메서드를 "세터(setter)"라 부른다.
  // - 외부에서 호출할 수 있도록 공개 모드로 설정한다.
  // - 필드를 비공개로 막으면 값을 조회할 수 없기 때문에
  //   getter도 추가해야 한다.
  //
  public void setKor(int kor) {
    this.kor = kor;
    this.compute();
  }

  public int getKor() {
    return this.kor;
  }

  public void setEng(int eng) {
    this.eng = eng;
    this.compute();
  }

  public int getEng() {
    return this.eng;
  }

  public void setMath(int math) {
    this.math = math;
    this.compute();
  }

  public int getMath() {
    return this.math;
  }

  private int sum;
  private float aver;

  public int getSum() {
    return this.sum;
  }

  public float getAver() {
    return this.aver;
  }

  // 공개할 필요가 없는 메서드는 private으로 막아라.
  // 보통 private 으로 막는 메서드는 해당 클래스 내부에서만 사용되는 메서드이다.
  private void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}

public class Exam0311 {
  public static void main(String[] args) {

    Score3 s1 = new Score3();

    s1.name = "홍길동";
    s1.setKor(100);
    s1.setEng(90);
    s1.setMath(80);

    // 세터를 통해서 국, 영, 수 값을 설정할 때마다
    // 합계와 평균을 자동으로 계산하기 때문에 직접 compute()를 호출할 필요가 없다.
    //    s1.compute();

    // 다음과 같이 언제든지 국, 영, 수 점수를 변경하더라도
    // 합계와 평균이 자동계산될 것이다.
    s1.setEng(100);
    s1.setMath(100);

    // 이제 kor, eng, math 도 비공개 모드이기 때문에
    // 값을 조회하려면 게터를 사용해야 한다.
    //
    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name,
        s1.getKor(), s1.getEng(), s1.getMath(),
        s1.getSum(), s1.getAver());
  }
}











