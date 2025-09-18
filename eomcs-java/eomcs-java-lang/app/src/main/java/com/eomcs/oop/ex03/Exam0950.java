// 종합 정리 - 파라미터가 있는 생성자(constructor) 적용
package com.eomcs.oop.ex03;

public class Exam0950 {

  static class Monitor {

    int bright; // 밝기 (0% ~ 100%)
    int contrast = 50; // 명암 (0% ~ 100%)
    int widthRes; // 해상도 너비
    int heightRes = 1080; // 해상도 높이

    // 만약 생성자가 한 개라도 있으면 컴파일러는 기본 생성자를 추가하지 않는다.
    //
    Monitor(int bright, int contrast) {
      // 변수 초기화 문장(variable initializer)은 생성자의 앞부분으로 복사된다.
      //      contrast = 50;
      //      heightRes = 1080;
      this.bright = bright;
      this.contrast = contrast;
    }

    void display() {
      System.out.println("----------------------------------");
      System.out.printf("밝기(%d)\n", this.bright);
      System.out.printf("명암(%d)\n", this.contrast);
      System.out.printf("해상도(%d x %d)\n", this.widthRes, this.heightRes);
      System.out.println("----------------------------------");
    }
  }

  public static void main(String[] args) {
    // 인스턴스 생성

    // 기본 생성자가 없는 경우 다음과 같이 기본 생성자를 지정할 수 없다.
    //    new Monitor(); // 컴파일 오류!

    // 생성자가 원하는 타입의 원하는 개수의 값을 줘야 한다.
    //    new Monitor(3f, 2); // 컴파일 오류!
    //    new Monitor(2); // 컴파일 오류!

    // 존재하는 생성자를 지정해야 하고, 그 생성자의 파라미터에 맞춰 값을 넘겨야 한다.
    // => Monitor 설계도에 따라 인스턴스를 생성한 후, 
    //    int 값 두 개를 받는 생성자를 호출하라는 의미다.
    Monitor m = new Monitor(50, 50); // OK!
    m.display();

  }
}


