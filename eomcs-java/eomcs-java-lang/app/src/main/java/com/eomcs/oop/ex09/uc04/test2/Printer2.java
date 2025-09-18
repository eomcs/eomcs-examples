package com.eomcs.oop.ex09.uc04.test2;

import com.eomcs.oop.ex09.uc04.Printer;

// 신규 프로젝트에서는 워터마킹 기능을 갖는 프린터가 필요하다.
// => 기존 인터페이스인 Printer 에 워터마팅 기능을 추가하게 되면
//    기존에 구현한 모든 클래스들도 새 규칙을 구현해야 하는 문제가 발생한다.
//    만약 구현하지 않으면 컴파일 오류가 발생할 것이다.
// => 이를 해결하는 전통적인 방식에서는
//    다음과 같이 새 기능을 추가하여 인터페이스를 새로 만드는 것이다.
//
public interface Printer2 extends Printer {
  // 기존 프린터의 규칙은 그대로 사용해야 하기 때문에
  // 상속 받는다.
  // 그리고 다음과 같이 새 규칙을 추가한다.
  void watermark(String title);
}
