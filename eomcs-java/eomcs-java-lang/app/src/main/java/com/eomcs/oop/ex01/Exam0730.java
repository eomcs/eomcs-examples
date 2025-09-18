package com.eomcs.oop.ex01;

// # import - 사용 후 II
//
import com.eomcs.oop.ex01.sub.*;
import java.util.*; 
import java.net.*;
import java.io.*;

public class Exam0730 {
  public static void main(String[] args) {

    // 특정 패키지에 소속된 여러 클래스를 사용한다면 
    // 다음과 같이 패키지명 다음에 wildcard(*)를 지정하면 편리하다.
    // 문법:
    //      import 패키지명.*;
    //
    // - 이 경우 사용하는 클래스 마다 import를 따로 지정할 필요가 없다.
    // - (주의!) 서브 패키지는 해당이 안된다.
    // - 단 소스 코드를 읽을 때 어떤 클래스가 어떤 패키지 있는지 바로 확인할 수 없는 불편함이 있다.
    //   그래서 대부분의 자바 개발자들은 널리 알려진 클래스가 아닌 경우
    //   가능한 wildcard(*)를 사용하지 않고 패키지명과 클래스명을 정확하게 명시한다.
    //
    Y2 obj;
    obj = new Y2();

    Z2 obj2;
    obj2 = new Z2();
  }
}
// 결론!
// - 가능한 import 문을 선언할 때 * 대신 구체적인 클래스명을 적어라!
// - 같은 패키지의 여러 클래스를 사용하더라도 귀찮지만 클래스명을 적어라!
// - 왜?
//   소스 코드를 읽을 때 어떤 클래스가 어떤 패키지에 있는지 빠르게 파악할 수 있기 때문이다.
//   wildcard(*) 를 사용하게 되면 어떤 패키지인지 바로 확인할 수 없다.
//