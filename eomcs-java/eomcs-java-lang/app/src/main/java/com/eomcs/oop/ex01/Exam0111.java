package com.eomcs.oop.ex01;

// # 클래스 사용 : 2) 메서드 활용
//
public class Exam0111 {
  public static void main(String[] args) {

    String name;
    int kor;
    int eng;
    int math;

    name = "홍길동";
    kor = 100;
    eng = 90;
    math = 85;

    // 메서드 추출
    // - 학생 정보를 출력하는 명령어를 별도의 블록으로 뺐다.
    // - 성적을 출력하는 명령어를 별도의 블록으로 빼는 이유는 유지보수를 쉽게 하기 위함이다.
    // - 출력 형식을 바꾸고 싶으면 그 블록으로 가서 변경하면 된다.
    //
    printScore(name, kor, eng, math);

    name = "임꺽정";
    kor = 90;
    eng = 80;
    math = 75;

    printScore(name, kor, eng, math);

    name = "유관순";
    kor = 80;
    eng = 70;
    math = 65;

    printScore(name, kor, eng, math);
  }

  static void printScore(String name, int kor, int eng, int math) {
    int sum = kor + eng + math;
    float aver = sum / 3f;
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", name, kor, eng, math, sum, aver);
  }
}
// 만약 과목이 12개라면 어떨것 같아?
// => 음.. 그건 좀.. 값이 많아지면, 메서드의 파라미터가 많아지고,
// => 어... 메서드 선언할 때 복잡할 것 같은데!
// => 근데... 이 성적 데이터 있지.. 그냥 한 봉다리에 담을 수는 없나?
//
// 자바는 모든 경우를 고려해서 다양한 종류의 값을 담을 수 있는 변수를 제공하지는 않는다.
// 대신 여러 종류의 데이터를 묶어 새로운 형태의 데이터 타입을 만들 수 있는 문법을 제공한다.
// 그 문법의 이름이 "클.래.스"이다.
//
// 클래스란?
// - 개발자가 자신이 개발하는 프로그램에서 사용할 특별한 형식의 데이터를 다룰 수 있는
//   새로운 데이터 타입을 정의하게 해주는 문법이다.
// - 즉 byte, short, int, long, float, double, boolean, char 외에
//   새 형식의 새로운 메모리 구조를 갖는 데이터 타입을 정의할 수 있다.
// - 이렇게 정의한 새 데이터 타입을
//   "사용자 정의 데이터 타입(user defined data type)" 이라 한다.
//
// 사용자?
// - 아니~~ 자바 언어를 사용하는 사람이 누군가?
// - 개발자를 가리킨다.
//
// 클래스 문법의 활용
// 1) 사용자 정의 데이터 타입과 그 타입의 값을 다루는 연산자를 정의할 때 사용한다.
// 2) 서로 관련된 일을 하는 메서드를 관리하기 쉽게 분류하여 묶는 용도로 사용한다.
