package com.eomcs.oop.ex09.uc05;

public class Exam01 {

  public static void main(String[] args) {
    
    // 인터페이스를 구현한 조상을 갖는 클래스도 
    // 해당 인터페이스를 구현한 것과 같다.
    // 왜? 조상으로부터 인터페이스에 선언된 모든 메서드를 상속 받았기 때문이다.
    Pen pen = new MyPen(); // 인터페이스를 직접 구현한 클래스 
    Pen pen2 = new BallPen153(); // 인터페이스를 간접 구현 
    
  }

}
