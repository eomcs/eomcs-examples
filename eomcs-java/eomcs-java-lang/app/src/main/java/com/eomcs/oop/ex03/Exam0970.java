// 종합 정리 - 생성자에서 다른 생성자 호출하기: this()
package com.eomcs.oop.ex03;

public class Exam0970 {

  static class Monitor {

    int bright; // 밝기 (0% ~ 100%)
    int contrast = 50; // 명암 (0% ~ 100%)
    int widthRes; // 해상도 너비
    int heightRes = 1080; // 해상도 높이

    Monitor() {
      // 다른 생성자를 호출할 수 있다.
      // 단 첫 문장으로 와야 한다.
      // => 이 경우 변수 초기화 문장이 삽입되지 않는다.
      this(50, 50, 2560, 1200);
      System.out.println("Monitor()");
    }

    Monitor(int bright, int contrast) {
      // 다른 생성자를 호출할 수 있다.
      // 단 첫 문장으로 와야 한다.
      // => 이 경우 변수 초기화 문장이 삽입되지 않는다.
      this(bright, contrast, 2560, 1200);
      System.out.println("Monitor(int, int)");
    }

    Monitor(int bright, int contrast, int widthRes, int heightRes) {
      // 다른 생성자를 호출하는 코드가 없다면 
      // 변수 초기화 문장이 생성자 맨 앞에 삽입된다.
      //      this.contrast = 50;
      //      this.heightRes = 1080;

      this.bright = bright;
      this.contrast = contrast;
      this.widthRes = widthRes;
      this.heightRes = heightRes;
      System.out.println("Monitor(int, int, int, int)");
    }

    void display() {
      //      this(); // 일반 메서드에서는 생성자를 호출할 수 없다.
      System.out.println("----------------------------------");
      System.out.printf("밝기(%d)\n", this.bright);
      System.out.printf("명암(%d)\n", this.contrast);
      System.out.printf("해상도(%d x %d)\n", this.widthRes, this.heightRes);
      System.out.println("----------------------------------");
    }
  }

  public static void main(String[] args) {
    // 인스턴스 생성

    Monitor m1 = new Monitor();
    System.out.println("--------------------------------");

    Monitor m2 = new Monitor(60, 60);
    System.out.println("--------------------------------");

    Monitor m3 = new Monitor(70, 70, 1920, 1080);
    System.out.println("--------------------------------");

    m1.display();
    m2.display();
    m3.display();

    // 주의!
    // 임의로 생성자를 호출할 수 없다.
    //    m1.Monitor(); // 컴파일 오류!
  }
}


