// java.io.Serializable 처럼 사용되는 인터페이스 : java.lang.Cloneable 인터페이스
package com.eomcs.io.ex11.b;

public class Exam0230 {

  static class Score {
    String name;
  }

  public static void main(String[] args) throws Exception {

    Score obj = new Score();

    //    Score copy = obj.clone(); // 컴파일 오류!
    // Object 클래스에서 상속 받은 clone()은 기본적으로 protected 이다.
    // 따라서 외부에서 호출할 수 없다.
  }

}
