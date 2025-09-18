package com.eomcs.oop.ex01;

//# 레퍼런스 배열 - 사용 후
//
public class Exam0221 {
  public static void main(String[] args) {
    // 여러 개의 인스턴스 주소 저장하기

    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    Score[] arr = new Score[3];

    arr[0] = new Score();
    arr[1] = new Score();
    arr[2] = new Score();

    // 배열의 각 항목에 저장된 인스턴스는 '배열명[인덱스]'를 사용하여 지정할 수 있다.
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

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
        arr[0].name, arr[0].kor, arr[0].eng, arr[0].math, arr[0].sum, arr[0].aver);
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
        arr[1].name, arr[1].kor, arr[1].eng, arr[1].math, arr[1].sum, arr[1].aver);
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
        arr[2].name, arr[2].kor, arr[2].eng, arr[2].math, arr[2].sum, arr[2].aver);
  }
}








