package com.eomcs.oop.ex01;

// # 패키지 멤버 클래스
//
public class Exam0410 {

  public static void main(String[] args) {
    // 바깥 쪽에 별도 선언한 클래스를 사용하기
    Score s = new Score();

    s.name = "홍길동";
    s.kor = 100;
    s.eng = 90;
    s.math = 80;
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3;

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
        s.name, s.kor, s.eng, s.math, s.sum, s.aver);
  }
}

// Exam0410.java 컴파일 오류!
// - 콘솔에서 이 소스 파일을 컴파일 할 때
//   Score 클래스를 찾을 수 없다고 오류가 발생할 것이다.
//   > javac -encoding UTF-8 -d bin/main src/main/java/com/eomcs/oop/ex01/Exam0410.java
// - 이유?
//   Exam0410.java 에서 Score 클래스를 사용하는데,
//   컴파일할 때 Score 클래스 정보가 필요하다.
//   그런데 컴파일러 Score 클래스에 대한 정보를 모르기 때문에
//   Exam0410.java를 제대로 컴파일 할 수 없는 것이다.
// - 해결책?
//   컴파일러에 Score 클래스에 대한 정보를 알려줘라!
// 방법1) Score.class 파일을 갖고 있다면 그 파일이 있는 위치를 알려줘라.
//   > javac -encoding UTF-8 -d bin/main -classpath bin/main src/main/com/eomcs/oop/ex01/Exam0410.java
//
// 방법2) Score 클래스의 소스 파일이 있다면 소스 파일의 위치를 알려줘라.
//   > javac -encoding UTF-8 -d bin/main -sourcepath src/main/java src/main/com/eomcs/oop/ex01/Exam0410.java
//
// 언제 이렇게 해야 하는가?
// - 자바에서 기본으로 제공하는 클래스가 아닌 다른 클래스를 사용할 때!
//