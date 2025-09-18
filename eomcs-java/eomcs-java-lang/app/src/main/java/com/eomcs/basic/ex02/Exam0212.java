// Wrapper 클래스 - wrapper 객체에 들어 있는 primitive type의 값 꺼내기
package com.eomcs.basic.ex02;

public class Exam0212 {
  public static void main(String[] args) {

    Long obj1 = Long.valueOf(100L);
    Double obj2 = Double.valueOf(3.14);
    Boolean obj3 = Boolean.valueOf(true);

    long l = obj1.longValue();
    double d = obj2.doubleValue();
    boolean bool = obj3.booleanValue();

    // 문자열로 꺼내기
    // Wrapper 클래스도 String 클래스처럼 Object의 toString()을 오버라이딩 했다.
    System.out.println(obj1.toString()); // Object의 toString()이 아니다. Long에서 오버라이딩한 toString()이다.
    System.out.println(obj2.toString()); // Object의 toString()이 아니다. Double에서 오버라이딩한 toString()이다.
    System.out.println(obj3.toString()); // Object의 toString()이 아니다. Boolean에서 오버라이딩한 toString()이다.


    // 다른 진수로 변환하여 문자열로 꺼내기
    System.out.println(Long.toHexString(l));
    System.out.println(Long.toOctalString(l));
    System.out.println(Long.toBinaryString(l));
  }
}






