// String - mutable vs immutable 객체
package com.eomcs.basic.ex02;

public class Exam0161 {
  public static void main(String[] args) {
    // StringBuffer 객체는 mutable 객체이다.
    // 인스턴스의 데이터를 변경할 수 있다.
    // 원래의 문자열을 변경하고 싶을 때 사용하는 클래스이다.
    //
    StringBuffer buf = new StringBuffer("Hello");
    System.out.println(buf);

    buf.replace(2, 4, "xxxx");// 원본을 바꾼다.
    System.out.println(buf);

    // println() 메서드에 넘겨주는 파라미터 값이 String 타입이 아닐 경우,
    // println() 메서드 내부에서 해당 값에 대해 toString() 호출하여
    // 그 리턴 값(String)을 출력한다.
    // 만약에 해당 클래스에 toString() 메서드가 없으면 어떡하나요?
    // => 자바의 모든 클래스는 toString()이 있다.
    // => 왜? 자바의 모든 클래스는 Object 클래스를 상속 받는다.
    // => Object 클래스에 toString()이 정의되어 있다.
  }
}


