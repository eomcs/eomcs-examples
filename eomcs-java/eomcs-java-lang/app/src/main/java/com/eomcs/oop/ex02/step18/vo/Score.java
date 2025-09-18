package com.eomcs.oop.ex02.step18.vo;

public class Score {

  public String name;
  public int kor;
  public int eng;
  public int math;
  public int sum;
  public float aver;

  // 클래스에 생성자가 없으면, 
  // 컴파일러는 파라미터가 없는 기본 생성자를 자동 추가한다.
  //  public Score() {
  //  }

  // new 연산자를 이용하여 인스턴스를 만들 때 자동으로 호출되는 특별한 문법의 메서드
  // => 생성자(constructor)
  //    - 메서드명은 클래스 이름과 같아야 한다.
  //    - 리턴 타입은 없다.
  //    - 오직 new 명령을 실행할 때 호출할 수 있다. 나중에 따로 호출할 수 없다.
  public Score(String name, int kor, int eng, int math) {
    this.name = name;
    this.kor = kor;
    this.eng = eng;
    this.math = math;

    this.compute(); // 컴파일 할 때 컴파일러가 compute() 앞에 생성자가 받은 인스턴스 주소를 넘깁니다.
  }

  public void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}
