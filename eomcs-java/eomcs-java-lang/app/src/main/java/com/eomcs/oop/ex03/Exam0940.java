// 종합 정리 - 생성자(constructor) 적용
package com.eomcs.oop.ex03;

public class Exam0940 {

  static class Monitor {

    int bright = 10; // 밝기 (0% ~ 100%)
    int contrast = 10; // 명암 (0% ~ 100%)
    int widthRes = 480; // 해상도 너비
    int heightRes = 320; // 해상도 높이

    // 호출될 생성자는 new 명령에서 지정한다.
    //   예) new 클래스명(호출할 생성자의 전달할 파라미터 값)
    // => 다음과 같이 파라미터의 값을 주지 않으면 파라미터 값을 안 받는 생성자가 호출된다.
    //   예) Monitor4()
    // => 파라미터를 받지 않는 생성자(파라미터 없는 생성자)를 
    //    기본 생성자(default constructor)라 부른다.
    // => new 명령을 실행하여 인스턴스를 생성할 때는 반드시 호출될 생성자를 지정해야 한다.
    //    예) new 클래스명();
    // => 다음과 같이 생성자를 지정하지 않으면 컴파일 오류가 발생한다.
    //    예) new 클래스명;
    Monitor() {  
      System.out.println("생성자 실행!");
      this.bright = 50;
      this.contrast = 50;
      this.widthRes = 1920;
      this.heightRes = 1080;
      // 인스턴스 초기화 블럭이라는 문법이 있는데 왜 생성자가 필요한가?
      // - 생성자는 외부에서 초기화시킬 값을 받을 수 있다.
      // - 다만 현재의 생성자는 기본 생성자로서 외부로부터 값을 받지 않기 때문에 
      //   초기화 블록이랑 다를 바가 없다.
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
    // 모니터 인스턴스 생성
    // 1) Monitor 설계도에 따라 인스턴스 필드를 Heap 에 생성한다.
    // 2) 필드의 초기화 문장을 실행한다.
    //    예) int bright = 10;
    // 3) 인스턴스 초기화 블록을 실행한다.
    //    예) 
    //    {
    //      System.out.println("인스턴스 초기화 블록 실행!");
    //      bright = 20;
    //      ...
    //     }
    // 4) 파라미터를 안 받는 생성자(default constructor)를 호출한다.
    //    예) Monitor() {...}
    //
    Monitor m1 = new Monitor();

    // 주의!
    // => 다음과 같이 호출할 생성자를 지정하지 않는다면 컴파일 오류가 발생한다.
    // => 반드시 존재하는 생성자를 지정해야 한다.
    //Monitor m2 = new Monitor; // 컴파일 오류!

    // 인스턴스 필드의 값이 생성자를 통해 유효한 기본 값들로 
    // 미리 초기화 되었기 때문에 바로 사용할 수 있다.
    m1.display(); 

    // 물론 특정 속성의 값을 바꾼 후에 사용해도 된다.
    m1.bright = 40;

    m1.display();
  }
}


