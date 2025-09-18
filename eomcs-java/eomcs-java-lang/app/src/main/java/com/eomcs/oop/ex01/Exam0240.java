package com.eomcs.oop.ex01;

//# 레퍼런스 배열 - 메서드 활용
//
public class Exam0240 {

  // 여러 메서드에서 공유하려면 클래스를 메서드 밖에 선언해야 한다.
  // => static 메서드들이 사용할 수 있게 클래스도 static으로 선언한다.
  // => static에 대한 의미는 나중에 설명한다.
  //
  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;
  }

  public static void main(String[] args) {

    Score[] arr = new Score[3];

    // 메서드에서 생성한 Score 객체를 레퍼런스 배열의 각 항목에 저장한다.
    arr[0] = createScore("홍길동", 100, 100, 100);
    arr[1] = createScore("임꺽정", 90, 90, 90);
    arr[2] = createScore("유관순", 80, 80, 80);

    // 메서드에 배열을 통째로 넘길 수 있다.
    // => 정확하게 표현하면, arr 변수에 저장된 레퍼런스 배열의 주소를 넘긴다.
    printScoreList(arr);

  }

  // 클래스를 이용하면 성적 정보를 하나로 묶어 리턴할 수 있다.
  // 참고!
  // - 다음과 같이 메서드를 통해 인스턴스를 생성하는 코딩 기법을 
  //   "팩토리 메서드(factory method)" 패턴이라 부른다.
  static Score createScore(String name, int kor, int eng, int math) {
    Score s = new Score();

    s.name = name;
    s.kor = kor;
    s.eng = eng;
    s.math = math;
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3f;

    return s;
  }

  static void printScoreList(Score[] arr) {
    // main()에서 넘겨준 레퍼런스 배열의 주소를 arr 변수에 받는다.
    // 결국 main()에서 만든 레퍼런스 배열을 사용하는 것이다.
    //
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
          arr[i].name, arr[i].kor, arr[i].eng, arr[i].math, arr[i].sum, arr[i].aver);
    }
  }
}






