// FilenameFilter 를 정의하는 다양한 방법 : 패키지 멤버 클래스(top-level class) 사용
package com.eomcs.oop.ex11.f;

import java.io.File;

// 물론 Exam0110 또한 패키지 멤버 클래스이다.
public class Exam0110 {

  public static void main(String[] args) throws Exception {
    // 현재 폴더에 들어있는 하위 폴더 및 파일들의 이름을 출력하라!

    File dir = new File(".");

    // list() 메서드
    // - 파라미터 값으로 파일 이름을 걸러주는 필터를 제공한다면
    //   필터의 승인 여부에 따라 리턴 배열에 포함될 이름이 결정된다.
    // - 필터를 주지 않은 상태에서 list()를 호출하면 모든 파일과 디렉토리 이름을 리턴하지만
    //   특정 조건에 해당되는 이름만 통과시키는 필터를 준 상태에서 list()를 호출하면
    //   리턴 값은 해당 조건에 만족되는 이름만 포함할 것이다.
    // - 필터의 타입은?
    //      FilenameFilter 인터페이스이다.
    //   이 인터페이스에 따라 동작하는 클래스를 만들고,
    //   그 클래스의 인스턴스를 생성한 후
    //   list() 메서드에 전달하면 된다.
    //
    // MyFilenameFilter 클래스
    // - com.eomcs.oop.ex11.f 패키지에 소속된 멤버 클래스이다.
    // - FilenameFilter의 구현체이다.
    // - 이름이 .java 로 끝나는 파일만 꺼낸다.
    // - File.list()를 호출할 때 파라미터로 넘겨준다.
    //   그러면 list() 메서드가 리턴할 이름을 준비할 때 FilenameFilter의 결정에 따라
    //   리턴 목록에 포함시킬지 말지 결정할 것이다.
    //
    MyFilenameFilter filter = new MyFilenameFilter();

    String[] names = dir.list(filter);
    for (String name : names) {
      System.out.println(name);
    }

    // 문제점:
    // 몇 일, 몇 달, 몇 년이 지난 후
    // 도대체 저 필터(MyFilenameFilter)가 무엇을 걸러내는지 알려면
    // 해당 클래스의 소스 코드를 봐야 한다.
    // 즉 별도의 파일로 떨어져 있어서 바로 이해하기가 어렵다.

  }

}







