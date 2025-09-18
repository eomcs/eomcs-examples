// 메서드 레퍼런스 - 생성자 레퍼런스
package com.eomcs.oop.ex12;

public class Exam0730 {

  static class Message {
    String name;

    public Message() {
      this.name = "이름없음";
    }

    public Message(String name) {
      this.name = name;
    }

    public void print() {
      System.out.printf("%s님 반갑습니다!\n", name);
    }
  }

  static interface Factory1 {
    Message get();
  }

  static interface Factory2 {
    Message get(String name);
  }

  public static void main(String[] args) {
    // 생성자 레퍼런스를 지정할 때,
    // 인터페이스 메서드의 파라미터에 따라 호출할 생성자가 결정된다.

    Factory1 f1 = Message::new; // Message() 생성자를 가리킨다.

    //    Factory1 f1 = () -> new Message(); // 위의 문장과 같다.
    //
    //    class $1 implements Factory1 { // 즉 다음의 로컬 클래스를 자동 생성한다.
    //      @Override
    //      public Message get() {
    //        return new Message();
    //      }
    //    }

    Factory2 f2 = Message::new; // Message(String) 생성자를 가리킨다.

    //    Factory2 f2 = n -> new Message(n); // 위의 문장과 같다.
    //
    //    class $1 implements Factory2 { // 즉 다음의 로컬 클래스를 자동 생성한다.
    //      @Override
    //      public Message get(String name) {
    //        return new Message(name);
    //      }
    //    }

    Message msg = f1.get(); // ==> new Message()
    msg.print();

    msg = f2.get("홍길동"); // ==> new Message("홍길동")
    msg.print();

  }
}


