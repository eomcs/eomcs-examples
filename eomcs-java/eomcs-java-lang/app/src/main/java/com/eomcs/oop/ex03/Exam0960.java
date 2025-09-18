// 종합 정리 - 생성자(constructor)를 여러 개 정의하기
package com.eomcs.oop.ex03;

public class Exam0960 {

  static class Monitor {

    int bright; // 밝기 (0% ~ 100%)
    int contrast = 50; // 명암 (0% ~ 100%)
    int widthRes; // 해상도 너비
    int heightRes = 1080; // 해상도 높이

    // 여러 개의 생성자를 정의할 수 있다.
    Monitor() {
      // 변수 초기화 문장(variable initializer)은 생성자의 앞부분으로 복사된다.
      //      contrast = 50;
      //      heightRes = 1080;
      this.bright = 50;
      this.contrast = 50;
      this.widthRes = 2560;
      this.heightRes = 1200;
      System.out.println("Monitor()");
    }

    Monitor(int bright, int contrast) {
      // 변수 초기화 문장(variable initializer)은 생성자의 앞부분으로 복사된다.
      //      contrast = 50;
      //      heightRes = 1080;
      this.bright = bright;
      this.contrast = contrast;
      System.out.println("Monitor(int, int)");
    }

    Monitor(int bright, int contrast, int widthRes, int heightRes) {
      // 변수 초기화 문장(variable initializer)은 생성자의 앞부분으로 복사된다.
      //      contrast = 50;
      //      heightRes = 1080;
      this.bright = bright;
      this.contrast = contrast;
      this.widthRes = widthRes;
      this.heightRes = heightRes;
      System.out.println("Monitor(int, int, int, int)");
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

    // 생성자를 지정할 때 파라미터의 값에 결정된다.
    Monitor m1 = new Monitor(); // 기본 생성자를 지정한다.
    Monitor m2 = new Monitor(50, 50); // Monitor(int, int) 생성자를 지정한다.
    Monitor m3 = new Monitor(50, 50, 1920, 1080); // Monitor(int, int, int, int) 생성자를 지정한다.

    // 파라미터 타입이나 개수가 일치하지 않는 생성자는 호출할 수 없다.
    //    new Monitor(50); // 컴파일 오류!

    m1.display();
    m2.display();
    m3.display();
  }
}


