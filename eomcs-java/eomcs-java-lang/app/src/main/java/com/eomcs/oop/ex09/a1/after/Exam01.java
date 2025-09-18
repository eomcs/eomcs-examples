// Worker 구현체 사용
package com.eomcs.oop.ex09.a1.after;

// 작업:
// 1) worker 객체의 사용 규칙을 정의한다.
//    => Worker 인터페이스 정의
// 2) 클래스를 정의할 때 Worker 규칙에 따라 만든다.
//    => BlueWorker, WhiteWorker, JubuWorker 클래스 변경
// 3) worker를 사용하는 측에서는 Worker 인터페이스에 정의된 대로 메서드를 호출한다.
//    => Exam01 클래스 변경
//
public class Exam01 {
  public static void main(String[] args) {

    // 같은 사용규칙에 따라 만든 클래스는 
    // 한 인터페이스 레퍼런스에 그 객체 주소를 저장할 수 있어 편리하다.
    //
    // 인터페이스 레퍼런스
    // => 인터페이스를 구현한 클래스의 객체 주소를 저장하는 변수
    Worker w1 = new BlueWorker();
    Worker w2 = new WhiteWorker();
    Worker w3 = new JubuWorker();

    // Worker 인터페이스에 선언된 메서드를 구현한다고 해서 되는 게 아니다.
    // 반드시 implements 키워드를 사용해서 
    // Worker 인터페이스를 구현한다고 선언해야 한다.
    // 다음 HulWorker 클래스처럼 Worker 인터페이스 구현을 선언하지 않는다면,
    // 공식적으로 Worker 구현체가 아니기 때문에 
    // 레퍼런스에 담을 수 없다. 
    //    Worker w4 = new HulWorker(); // 컴파일 오류!

    // => 인터페이스를 구현하지 않은 클래스의 인스턴스 주소는 저장할 수 없다. 
    //    Worker w5 = new String();// 컴파일 오류!

    // 사용하려는 객체가 같은 인터페이스를 구현(사용규칙에 따라 작성)하였다면,
    // 다음과 같이 동일한 사용규칙에 따라 메서드를 호출할 수 있어 편리하다.
    // 호출에 일관성이 있어 유지보수에 좋다.
    w1.execute();
    w2.execute();
    w3.execute();

    // 역할?
    // => 사용 규칙: Worker
    // => caller : Exam01
    // => callee : BlueWorker, WhiteWorker, JubuWorker

    // 참고!
    Worker w;

    // 인터페이스 레퍼런스?
    // => 해당 인터페이스에 따라 작성된(사용규칙을 준수하는) 클래스의 인스턴스 주소를 저장한다.
    // 
    // 위 예제에서 w 레퍼런스를말로 표현하는 방법:
    // => Worker 사용규칙에 따라 작성된 클래스의 인스턴스 주소를 저장하는 변수 w.
    // => Worker 인터페이스를 구현한 클래스의 인스턴스 주소를 저장하는 변수 w.
    // => Worker 구현체의 인스턴스 주소를 저장하는 변수 w.
    // => Worker 구현체의 객체 주소를 저장하는 변수 w.
    // => Worker 구현 객체를 저장하는 변수 w.
    // => Worker 객체를 저장하는 (변수) w.
    // => Worker 객체를 가리키는 (변수) w.
    // => Worker 타입 객체 w.

    // 프로그램을 짜다가 인터페이스를 만나게 되면,
    // - 누가 호출자이고 누가 피호출자인지 확인하라.
    // - 본인이 맡은 개발 일이 호출자를 만드는 것인지 
    //   아니면 피호출자를 만드는 것인지 확인하라.

  }
}










