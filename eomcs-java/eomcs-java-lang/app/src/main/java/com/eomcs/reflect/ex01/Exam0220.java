// Reflection API : forName() 활용
package com.eomcs.reflect.ex01;

import java.util.Scanner;

class X {
  static {
    System.out.println("X 클래스 로딩");
  }
}


class Y {
  static {
    System.out.println("Y 클래스 로딩");
  }
}


class Z {
  static {
    System.out.println("Z 클래스 로딩");
  }
}


public class Exam0220 {

  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);
    System.out.println("로딩할 클래스명을 입력하시오? ");
    String className = keyScan.nextLine();
    keyScan.close();

    // forName()은
    // 이렇게 프로그램 아규먼트나 키보드 입력을 통해
    // 클래스 이름을 입력 받아서 로딩할 수 있다.
    Class clazz1 = Class.forName(className);

    // 그러나 다음과 같이 "class" 변수를 사용하게 되면
    // 코드로 이름을 고정하기 때문에 임의의 클래스를 로딩할 수 없다.
    // 다른 클래스를 가져오려면 소스 코드를 변경해야 한다.
    //    Class clazz2 = X.class;
  }

}


