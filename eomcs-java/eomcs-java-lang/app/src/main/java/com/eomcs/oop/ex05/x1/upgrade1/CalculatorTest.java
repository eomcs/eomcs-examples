package com.eomcs.oop.ex05.x1.upgrade1;

// 1) 계산기 기능 확장 방법1 - 기존 코드 변경하기
//    - 곱하기 계산 기능 추가
//    - 기존 Calculator 클래스에 multiple() 메서드 추가 
// 
public class CalculatorTest {
  public static void main(String[] args) {
    com.eomcs.oop.ex05.x1.Calculator c = new com.eomcs.oop.ex05.x1.Calculator();

    c.plus(100);
    c.minus(200);
    c.multiple(2); // 기존 클래스에 새로 추가한 메서드를 호출한다.

    System.out.println(c.result);
  }
}

// 새 기능을 추가하기 위해 기존 코드를 변경하는 방식의 문제점:
// 1) 새 기능을 추가하거나 변경하면서 기존 코드를 손대는 경우, 없던 오류가 발생할 수 있다.
// 2) 기존 코드에 계속 코드를 추가하다보면 코드의 덩치가 커지고 복잡해져서 유지보수가 어려워진다.
// 3) 새로 추가한 기능이나 변경한 기능이 다른 프로젝트에서 필요없을 때 기존 코드에서 제거하기 힘들다.
//    왜? 이전에 만든 프로젝트에서 그 기능을 사용하고 있기 때문이다.
// 4) 기존 소스가 없으면 이 방식을 사용할 수 없다.
// 
// 이런 문제점을 해결하기 위해 나온 문법이 "상속(inheritance)"이다.
//















