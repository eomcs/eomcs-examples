// 추상 클래스와 추상 메서드의 활용 : Template Method 패턴(GoF)
package com.eomcs.oop.ex07.a;

abstract class Letter {

  // 수퍼 클래스는 서브 클래스에게 구현된 멤버를 상속해준다.
  String content;

  public void setContent(String content) {
    this.content = content;
  }

  // 수퍼 클래스에서 기능이 어떻게 동작하는지 정의한다.
  // => 템플릿의 역할을 하는 메서드를 수퍼 클래스에 둔다.
  // => 자세한 구현은 서브 클래스에 맡긴다.
  // => 동작의 흐름은 변경되면 안되기 때문에 final 로 선언하여 오버라이딩을 막는다.
  //
  public final void print() { // <== 템플릿 메서드 디자인 패턴에서 "템플릿 메서드"에 해당한다.
    this.printHeader();
    System.out.println(this.content);
    System.out.println();
    System.out.printf("              From %s!\n", this.getSign());
    System.out.println();
    this.printFooter();
  }

  // 세부 사항에 대한 것은
  // 서브 클래스에게 구현을 맡긴다.
  public abstract void printHeader();
  public abstract void printFooter();
  public abstract String getSign();
}

// 상세한 기능에 대한 구현은 다음과 같이 서브 클래스에게 맡긴다.
class LoveLetter extends Letter {

  @Override
  public void printHeader() {
    System.out.println("♥♥♥♥♥♥♥♥♥♥♥♥♥♥ [사랑을 그대에게] ♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥");
  }

  @Override
  public String getSign() {
    return "당신의 영원한 팔로워, 홍길동";
  }

  @Override
  public void printFooter() {
    System.out.println("♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪♪");

  }

  public void x() {}
}

class ReportLetter extends Letter {
  @Override
  public void printHeader() {
    System.out.println("[비트캠프 강의 보고서]");
  }

  @Override
  public String getSign() {
    return "강사 홍길동";
  }

  @Override
  public void printFooter() {
    System.out.println("==========================================");

  }
}

public class Exam05 {
  public static void main(String[] args) {
    Letter letter = new LoveLetter();
    letter.setContent("눈이 녹으면 무엇이 될까요?\n"
        + "봄이 온다 합니다.\n"
        + "따뜻한 봄이 기다려지네요.");
    letter.print();

    Letter letter2 = new ReportLetter();
    letter2.setContent("강의중!");
    letter2.print();
  }
}
