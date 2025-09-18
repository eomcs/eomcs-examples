package com.eomcs.oop.ex01;

//# 레퍼런스 배열 - 반복문 결합
//
public class Exam0230 {
  public static void main(String[] args) {
    // 여러 개의 인스턴스 주소 저장하기
    //
    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    // 레퍼런스 배열 준비
    Score[] arr = new Score[3];

    // 반복문을 이용하여 레퍼런스 배열에 인스턴스 주소를 저장한다.
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new Score();
    }

    // 값을 저장할 때는 일일이 값을 지정해야 한다.
    arr[0].name = "홍길동";
    arr[0].kor = 100;
    arr[0].eng = 100;
    arr[0].math = 100;
    arr[0].sum = arr[0].kor + arr[0].eng + arr[0].math;
    arr[0].aver = arr[0].sum / 3f;

    arr[1].name = "임꺽정";
    arr[1].kor = 90;
    arr[1].eng = 90;
    arr[1].math = 90;
    arr[1].sum = arr[1].kor + arr[1].eng + arr[1].math;
    arr[1].aver = arr[1].sum / 3f;

    arr[2].name = "유관순";
    arr[2].kor = 80;
    arr[2].eng = 80;
    arr[2].math = 80;
    arr[2].sum = arr[2].kor + arr[2].eng + arr[2].math;
    arr[2].aver = arr[2].sum / 3f;

    // 반복문을 사용하면 배열의 인스턴스 값을 꺼내기 쉽다.
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
          arr[i].name, arr[i].kor, arr[i].eng, arr[i].math, arr[i].sum, arr[i].aver);
    }
  }
}

// 결론!
// - 배열은 반복문과 함께 쓸 때 특히 유용한다.





