// 클래스에 제네릭(generic) 적용하기 : 적용 전
package com.eomcs.generic.ex01;

// 제네릭 문법이 없다면 각 타입의 객체를 저장하고 꺼내기 위해
// 다음과 같이 각 타입에 대한 클래스를 정의해야 한다.
//
class MemberBox {
  Member value;

  public void set(Member value) {
    this.value = value;
  }

  public Member get() {
    return value;
  }
}

class StringBox {
  String value;

  public void set(String value) {
    this.value = value;
  }

  public String get() {
    return value;
  }
}

class IntegerBox {
  Integer value;

  public void set(Integer value) {
    this.value = value;
  }

  public Integer get() {
    return value;
  }
}

public class Exam0210 {

  public static void main(String[] args) {

    // Member 객체를 저장하려면 MemberBox를 사용해야 한다.
    MemberBox box1 = new MemberBox();
    box1.set(new Member("홍길동", 20)); // 값 저장
    //    box1.set(new String("Hello")); // 컴파일 오류!
    Member m = box1.get(); // 값 꺼내기
    System.out.println(m);

    // String 객체를 저장하려면 StringBox를 사용해야 한다.
    StringBox box2 = new StringBox();
    box2.set("Hello");
    String str = box2.get();
    System.out.println(str);

    // Integer 객체는 IntegerBox를 사용해서 저장한다.
    IntegerBox box3 = new IntegerBox();
    box3.set(100); // auto-boxing ==> box3.set(Integer.valueOf(100)) 문장으로 변경된다.
    int i = box3.get(); // auto-unboxing ==> box3.get().intValue()
    System.out.println(i);

    // 이렇게 객체를 저장하려면 각 객체의 타입 별로 Box 클래스를 생성해야 한다.
    // 문제는 이런 식으로 코딩을 하면 타입 별로 만들어야 할 Box 클래스가 무한정으로 늘어난다.
    // 즉 같은 일을 하는 클래스임에도 불구하고
    // 다루는 객체의 타입이 다르다는 이유만으로
    // 여러 개의 유사 클래스를 반복적으로 정의해야 하는 문제가 발생한다.
    // 해결책?
    // => 다양한 타입의 객체를 저장할 수 있도록 다형성의 다형적 변수 특징을 이용하여
    //    값을 저장하는 인스턴스 변수를 Object 타입으로 정의한다.

  }
}
