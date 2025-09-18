// String - 문자열 객체 만들기
package com.eomcs.basic.ex02;

public class Exam0110 {
  public static void main(String[] args) {
    // String 레퍼런스
    // - String은 자바 기본 타입이 아니다.
    // - 클래스이다.
    String s1; // s1은 String 인스턴스 주소를 담는 레퍼런스이다.

    // String 인스턴스
    // - 힙에 Hello 문자 코드를 저장할 메모리를 만들고 그 주소를 리턴한다.
    // - 내용물의 동일 여부를 검사하지 않고 무조건 인스턴스를 생성한다.
    // - 가비지가 되면 가비지 컬렉터에 의해 제거된다.
    s1 = new String("Hello");
    String s2 = new String("Hello");

    // 인스턴스가 같은지를 비교해보면,
    System.out.println(s1 == s2); // false => 서로 다른 인스턴스이다.
  }
}


