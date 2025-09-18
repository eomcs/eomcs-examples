// String - toString(): 다형적 변수와 형변환
package com.eomcs.basic.ex02;

public class Exam0141 {

  public static void main(String[] args) {

    Object obj = new String("Hello"); // 인스턴스 주소가 100이라 가정하자;

    String x1 = (String) obj; // x1 <--- 100
    System.out.println(obj == x1);

    // obj에 대해 toString()을 호출할 때,
    // => 일단 obj 클래스에 선언된 멤버(필드와 메서드)만 사용할 수 있다.
    // => 단 멤버는 실제 obj가 가리키는 클래스부터 찾아 올라 간다.
    // => 위 예에서 obj가 가리키는 것은 String 이기 때문에
    // => 이 경우 toString()을 호출할 때 String 클래스에서부터 찾는다.
    // => String 클래스가 toString()을 오버라이딩 했기 때문에
    //    결국 이 오버라이딩 메서드를 호출할 것이다.
    String x2 = obj.toString(); // x2 <---- 100

    System.out.println(x1 == x2);

    // 레퍼런스를 통해 메서드를 호출할 때
    // => 레퍼런스가 가리키는 객체의 클래스부터 메서드를 찾아 올라간다.
    // => 따라서 obj가 가리키는 객체의 클래스가 String이기 때문에
    // obj.toString()은 String 클래스부터 해당 메서드를 찾는다.
  }
}


