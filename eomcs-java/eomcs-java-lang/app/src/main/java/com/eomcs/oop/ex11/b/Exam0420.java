//static nested class : import static 사용 후
package com.eomcs.oop.ex11.b;

// 스태틱 멤버를 임포트하기
import static com.eomcs.oop.ex11.b.E.m1;
import static com.eomcs.oop.ex11.b.E.v1;
import static com.eomcs.oop.ex11.b.sub.M.m2;
import static com.eomcs.oop.ex11.b.sub.M.v2;
// 중첩 클래스를 import 할 때는 static을 적지 않는다.
import com.eomcs.oop.ex11.b.E.X;
import com.eomcs.oop.ex11.b.sub.M.Y;

class E {
  static int v1;

  static void m1() {}

  static class X {
    void test() {
      System.out.println("test()");
    }
  }
}

public class Exam0420 {

  public static void main(String[] args) {
    // 같은 패키지 클래스의 스태틱 멤버
    // => import static 으로 미리 스태틱 멤버의 패키지 정보를 알려주면,
    //    마치 같은 클래스의 멤버인양
    //    클래스 이름 없이 사용할 수 있다.
    v1 = 100;
    m1();
    X obj = new X();

    // 다른 패키지 클래스의 스태틱 멤버
    // => import static 으로 미리 스태틱 멤버의 패키지 정보를 알려주면,
    //    마치 같은 클래스의 멤버인양
    //    클래스 이름 없이 사용할 수 있다.
    v2 = 200;
    m2();
    Y obj2 = new Y();
  }

}
